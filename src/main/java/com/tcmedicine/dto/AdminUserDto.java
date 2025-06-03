package com.tcmedicine.dto;

import lombok.Data;

/**
 * 管理员用户数据传输对象
 */
@Data
public class AdminUserDto {
    private Long id;
    private String username;
    private Integer userType;
    private String registerTime;
} 