package com.tcmedicine.service;

/**
 * 验证码服务接口
 */
public interface VerificationCodeService {

    /**
     * 发送验证码
     * @param email 邮箱地址
     * @param purpose 用途
     * @return 是否发送成功
     */
    boolean sendVerificationCode(String email, String purpose);

    /**
     * 验证验证码
     * @param email 邮箱地址
     * @param code 验证码
     * @param purpose 用途
     * @return 是否验证成功
     */
    boolean verifyCode(String email, String code, String purpose);

    /**
     * 检查邮箱发送频率限制
     * @param email 邮箱地址
     * @param purpose 用途
     * @return 是否可以发送
     */
    boolean canSendCode(String email, String purpose);
} 