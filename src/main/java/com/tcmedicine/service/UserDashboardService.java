package com.tcmedicine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tcmedicine.entity.*;
import com.tcmedicine.mapper.*;
import com.tcmedicine.dao.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户后台业务逻辑服务
 */
@Service
public class UserDashboardService {

    private static final Logger logger = LoggerFactory.getLogger(UserDashboardService.class);

    @Autowired
    private UserFavoriteMapper userFavoriteMapper;

    @Autowired
    private RecognitionHistoryMapper recognitionHistoryMapper;

    @Autowired
    private HealthProfileMapper healthProfileMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OSSService ossService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    // ================== 收藏夹功能 ==================

    /**
     * 获取用户收藏列表
     */
    public List<UserFavorite> getUserFavorites(Long userId) {
        return userFavoriteMapper.getUserFavoritesWithMedicineInfo(userId);
    }

    /**
     * 添加收藏
     */
    @Transactional
    public boolean addFavorite(Long userId, Integer medicineId) {
        // 检查是否已经收藏
        if (userFavoriteMapper.checkUserFavorite(userId, medicineId) > 0) {
            return false; // 已经收藏
        }

        UserFavorite favorite = new UserFavorite();
        favorite.setUserId(userId);
        favorite.setMedicineId(medicineId);
        favorite.setCreateTime(LocalDateTime.now());
        
        return userFavoriteMapper.insert(favorite) > 0;
    }

    /**
     * 取消收藏
     */
    @Transactional
    public boolean removeFavorite(Long userId, Integer medicineId) {
        QueryWrapper<UserFavorite> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("medicine_id", medicineId);
        return userFavoriteMapper.delete(wrapper) > 0;
    }

    /**
     * 检查是否已收藏
     */
    public boolean isFavorited(Long userId, Integer medicineId) {
        return userFavoriteMapper.checkUserFavorite(userId, medicineId) > 0;
    }

    // ================== 识别历史功能 ==================

    /**
     * 获取用户识别历史
     */
    public List<RecognitionHistory> getUserRecognitionHistory(Long userId) {
        return recognitionHistoryMapper.getUserRecognitionHistoryWithMedicineInfo(userId);
    }

    /**
     * 添加识别历史记录
     */
    @Transactional
    public boolean addRecognitionHistory(RecognitionHistory history) {
        try {
            logger.info("UserDashboardService.addRecognitionHistory 开始执行");
            logger.info("传入的历史记录：userId={}, imageUrl={}, resultName={}, medicineId={}, confidence={}", 
                history.getUserId(), history.getImageUrl(), history.getResultName(), 
                history.getResultMedicineId(), history.getConfidence());
            
            history.setRecognitionTime(LocalDateTime.now());
            logger.info("设置识别时间：{}", history.getRecognitionTime());
            
            int insertResult = recognitionHistoryMapper.insert(history);
            logger.info("数据库插入结果：{}, 插入的记录ID：{}", insertResult, history.getId());
            
            boolean success = insertResult > 0;
            logger.info("addRecognitionHistory 执行结果：{}", success);
            
            return success;
        } catch (Exception e) {
            logger.error("addRecognitionHistory 执行异常：{}", e.getMessage(), e);
            throw e; // 重新抛出异常，让调用方知道失败原因
        }
    }

    // ================== 健康档案功能 ==================

    /**
     * 获取用户健康档案
     */
    public HealthProfile getUserHealthProfile(Long userId) {
        QueryWrapper<HealthProfile> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return healthProfileMapper.selectOne(wrapper);
    }

    /**
     * 保存或更新健康档案
     */
    @Transactional
    public boolean saveOrUpdateHealthProfile(HealthProfile healthProfile) {
        HealthProfile existing = getUserHealthProfile(healthProfile.getUserId());
        
        if (existing == null) {
            // 新建
            healthProfile.setCreateTime(LocalDateTime.now());
            healthProfile.setUpdateTime(LocalDateTime.now());
            return healthProfileMapper.insert(healthProfile) > 0;
        } else {
            // 更新
            healthProfile.setId(existing.getId());
            healthProfile.setCreateTime(existing.getCreateTime());
            healthProfile.setUpdateTime(LocalDateTime.now());
            return healthProfileMapper.updateById(healthProfile) > 0;
        }
    }

    // ================== 用户信息管理 ==================

    /**
     * 上传用户头像
     */
    @Transactional
    public String uploadUserAvatar(Long userId, MultipartFile file) throws IOException {
        // 先获取用户信息
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        // 删除旧头像（如果存在）
        if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
            ossService.deleteFile(user.getAvatarUrl());
        }

        // 上传新头像
        String avatarUrl = ossService.uploadAvatar(file, userId);

        // 更新用户头像URL
        user.setAvatarUrl(avatarUrl);
        userMapper.updateById(user);

        return avatarUrl;
    }

    /**
     * 更新用户名
     */
    @Transactional
    public boolean updateUsername(Long userId, String newUsername) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return false;
        }

        user.setUserName(newUsername);
        return userMapper.updateById(user) > 0;
    }

    /**
     * 修改密码（需要邮箱验证）
     */
    @Transactional
    public boolean changePassword(Long userId, String newPassword, String email, String verificationCode) {
        // 验证邮箱验证码
        if (!verificationCodeService.verifyCode(email, verificationCode, "CHANGE_PASSWORD")) {
            throw new IllegalArgumentException("邮箱验证码错误或已过期");
        }

        User user = userMapper.selectById(userId);
        if (user == null) {
            return false;
        }

        // 验证邮箱是否匹配
        if (!email.equals(user.getEmail())) {
            throw new IllegalArgumentException("邮箱不匹配");
        }

        user.setPassword(newPassword); // 注意：实际应用中应该加密密码
        return userMapper.updateById(user) > 0;
    }

    /**
     * 更新个人简介
     */
    @Transactional
    public boolean updateProfileDescription(Long userId, String description) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return false;
        }

        user.setProfileDescription(description);
        return userMapper.updateById(user) > 0;
    }

    /**
     * 根据用户ID获取用户信息
     */
    public User getUserProfile(Long userId) {
        return userMapper.selectById(userId);
    }
} 