package com.tcmedicine.service.impl;

import com.tcmedicine.service.EmailService;
import com.tcmedicine.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现类 - 使用内存缓存
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.valid}")
    private int validMinutes;

    // 内存缓存：邮箱 -> 验证码信息
    private final ConcurrentHashMap<String, VerificationCodeInfo> codeCache = new ConcurrentHashMap<>();
    
    // 发送频率限制缓存：邮箱 -> 最后发送时间
    private final ConcurrentHashMap<String, LocalDateTime> sendTimeCache = new ConcurrentHashMap<>();
    
    // 定时清理服务
    private ScheduledExecutorService cleanupExecutor;

    /**
     * 验证码信息内部类
     */
    private static class VerificationCodeInfo {
        private final String code;
        private final String purpose;
        private final LocalDateTime expiresAt;
        private boolean used;

        public VerificationCodeInfo(String code, String purpose, LocalDateTime expiresAt) {
            this.code = code;
            this.purpose = purpose;
            this.expiresAt = expiresAt;
            this.used = false;
        }

        public boolean isValid() {
            return !used && LocalDateTime.now().isBefore(expiresAt);
        }

        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expiresAt);
        }

        // Getters
        public String getCode() { return code; }
        public String getPurpose() { return purpose; }
        public LocalDateTime getExpiresAt() { return expiresAt; }
        public boolean isUsed() { return used; }
        public void markAsUsed() { this.used = true; }
    }

    @PostConstruct
    public void init() {
        // 启动定时清理任务，每分钟清理一次过期数据
        cleanupExecutor = Executors.newSingleThreadScheduledExecutor();
        cleanupExecutor.scheduleAtFixedRate(this::cleanupExpiredCodes, 1, 1, TimeUnit.MINUTES);
    }

    @Override
    public boolean sendVerificationCode(String email, String purpose) {
        try {
            // 检查发送频率限制
            if (!canSendCode(email, purpose)) {
                return false;
            }

            // 生成6位数字验证码
            String code = generateVerificationCode();
            
            // 保存验证码到内存
            String cacheKey = buildCacheKey(email, purpose);
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(validMinutes);
            VerificationCodeInfo codeInfo = new VerificationCodeInfo(code, purpose, expiresAt);
            codeCache.put(cacheKey, codeInfo);
            
            // 更新发送时间
            sendTimeCache.put(email + ":" + purpose, LocalDateTime.now());

            // 发送邮件
            boolean emailSent = emailService.sendVerificationCode(email, code, purpose);
            if (emailSent) {
                System.out.println("验证码发送成功: " + email + " - " + code + " (缓存存储)");
                return true;
            } else {
                // 邮件发送失败，移除缓存
                codeCache.remove(cacheKey);
                sendTimeCache.remove(email + ":" + purpose);
                System.err.println("邮件发送失败: " + email);
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean verifyCode(String email, String code, String purpose) {
        try {
            String cacheKey = buildCacheKey(email, purpose);
            VerificationCodeInfo codeInfo = codeCache.get(cacheKey);
            
            if (codeInfo != null && codeInfo.isValid() && codeInfo.getCode().equals(code)) {
                // 标记为已使用
                codeInfo.markAsUsed();
                System.out.println("验证码验证成功: " + email + " - " + code + " (内存缓存)");
                return true;
            }
            
            System.out.println("验证码验证失败: " + email + " - " + code + " (无效或已过期)");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean canSendCode(String email, String purpose) {
        try {
            String timeKey = email + ":" + purpose;
            LocalDateTime lastSendTime = sendTimeCache.get(timeKey);
            
            if (lastSendTime == null) {
                return true;
            }
            
            // 检查是否在1分钟内发送过验证码
            LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);
            if (lastSendTime.isAfter(oneMinuteAgo)) {
                System.out.println("发送过于频繁: " + email + " - " + purpose);
                return false;
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true; // 出错时允许发送
        }
    }

    /**
     * 生成6位数字验证码
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // 生成100000-999999之间的随机数
        return String.valueOf(code);
    }

    /**
     * 构建缓存键
     */
    private String buildCacheKey(String email, String purpose) {
        return email + ":" + purpose + ":code";
    }

    /**
     * 清理过期的验证码
     */
    private void cleanupExpiredCodes() {
        try {
            LocalDateTime now = LocalDateTime.now();
            
            // 清理过期的验证码
            codeCache.entrySet().removeIf(entry -> entry.getValue().isExpired());
            
            // 清理过期的发送时间记录（超过1小时的记录）
            LocalDateTime oneHourAgo = now.minusHours(1);
            sendTimeCache.entrySet().removeIf(entry -> entry.getValue().isBefore(oneHourAgo));
            
            System.out.println("清理过期验证码完成，当前缓存大小: " + codeCache.size());
        } catch (Exception e) {
            System.err.println("清理过期验证码失败: " + e.getMessage());
        }
    }

    /**
     * 获取缓存统计信息（用于监控）
     */
    public String getCacheStats() {
        return String.format("验证码缓存: %d 条, 发送时间缓存: %d 条", 
            codeCache.size(), sendTimeCache.size());
    }

    /**
     * 服务关闭时的清理
     */
    public void destroy() {
        if (cleanupExecutor != null) {
            cleanupExecutor.shutdown();
        }
    }
} 