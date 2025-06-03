package com.tcmedicine.dto;

import lombok.Data;

/**
 * 用户注册请求DTO
 */
@Data
public class RegisterRequest {
    private String userName;
    private String email;
    private String verificationCode;
    private String password;
    private Integer userType; // 0-普通用户, 1-管理员
} 