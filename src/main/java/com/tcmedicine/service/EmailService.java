package com.tcmedicine.service;

/**
 * 邮件服务接口
 */
public interface EmailService {

    /**
     * 发送验证码邮件
     * @param email 收件人邮箱
     * @param verificationCode 验证码
     * @param purpose 验证码用途
     * @return 是否发送成功
     */
    boolean sendVerificationCode(String email, String verificationCode, String purpose);

    /**
     * 发送普通邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @return 是否发送成功
     */
    boolean sendMail(String to, String subject, String content);
} 