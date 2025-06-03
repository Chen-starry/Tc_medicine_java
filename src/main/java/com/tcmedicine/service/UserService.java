package com.tcmedicine.service;

import com.tcmedicine.dto.AdminUserDto;
import com.tcmedicine.dto.LoginRequest;
import com.tcmedicine.dto.RegisterRequest;
import com.tcmedicine.entity.User;
import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户登录
     */
    User login(LoginRequest loginRequest);

    /**
     * 用户注册
     */
    boolean register(RegisterRequest registerRequest);

    /**
     * 带邮箱验证的用户注册
     */
    boolean registerWithEmailVerification(RegisterRequest registerRequest);

    /**
     * 根据用户名查找用户
     */
    User findByUsername(String username);

    /**
     * 根据邮箱查找用户
     */
    User findByEmail(String email);

    /**
     * 获取所有用户
     */
    List<User> getAllUsers();

    /**
     * 管理员获取所有用户（字段格式化）
     */
    List<AdminUserDto> getAllUsersForAdmin();

} 