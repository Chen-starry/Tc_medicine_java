package com.tcmedicine.service.impl;

import com.tcmedicine.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * 邮件服务实现类
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.title}")
    private String mailTitle;

    @Value("${spring.mail.template}")
    private String mailTemplate;

    @Value("${spring.mail.valid}")
    private int validMinutes;

    @Override
    public boolean sendVerificationCode(String email, String verificationCode, String purpose) {
        try {
            String subject = mailTitle;
            String content = String.format(mailTemplate, verificationCode, validMinutes);
            
            // 根据用途调整邮件标题
            switch (purpose) {
                case "REGISTER":
                    subject = mailTitle + " - 注册验证";
                    break;
                case "RESET_PASSWORD":
                    subject = mailTitle + " - 密码重置";
                    break;
                case "CHANGE_EMAIL":
                    subject = mailTitle + " - 邮箱变更";
                    break;
            }

            return sendHtmlMail(email, subject, content);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendMail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 发送HTML邮件
     */
    private boolean sendHtmlMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true表示支持HTML

            mailSender.send(message);
            System.out.println("邮件发送成功: " + to + " - " + subject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("邮件发送失败: " + to + " - " + e.getMessage());
            return false;
        }
    }
} 