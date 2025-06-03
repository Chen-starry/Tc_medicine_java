package com.tcmedicine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tcmedicine.dao.UserMapper;
import com.tcmedicine.dto.AdminUserDto;
import com.tcmedicine.dto.LoginRequest;
import com.tcmedicine.dto.RegisterRequest;
import com.tcmedicine.entity.User;
import com.tcmedicine.service.UserService;
import com.tcmedicine.service.VerificationCodeService;
import com.tcmedicine.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    private VerificationCodeService verificationCodeService;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User login(LoginRequest loginRequest) {
        User user = userMapper.findByUsername(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(MD5Util.encrypt(loginRequest.getPassword()))) {
            return user;
        }
        return null;
    }

    @Override
    public boolean register(RegisterRequest registerRequest) {
        // 检查用户名是否已存在
        if (userMapper.findByUsername(registerRequest.getUserName()) != null) {
            return false;
        }

        // 创建新用户
        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setPassword(MD5Util.encrypt(registerRequest.getPassword()));
        user.setUserType(0); // 默认为普通用户
        user.setCreateTime(LocalDateTime.now());

        return userMapper.insert(user) > 0;
    }

    @Override
    public boolean registerWithEmailVerification(RegisterRequest registerRequest) {
        try {
            // 1. 验证验证码
            boolean codeValid = verificationCodeService.verifyCode(
                registerRequest.getEmail(), 
                registerRequest.getVerificationCode(), 
                "REGISTER"
            );
            
            if (!codeValid) {
                System.out.println("验证码验证失败");
                return false;
            }

            // 2. 检查用户名是否已存在
            if (userMapper.findByUsername(registerRequest.getUserName()) != null) {
                System.out.println("用户名已存在");
                return false;
            }

            // 3. 检查邮箱是否已存在
            if (findByEmail(registerRequest.getEmail()) != null) {
                System.out.println("邮箱已存在");
                return false;
            }

            // 4. 创建新用户
            User user = new User();
            user.setUserName(registerRequest.getUserName());
            user.setEmail(registerRequest.getEmail());
            user.setEmailVerified(true); // 验证码验证通过，标记邮箱已验证
            user.setPassword(MD5Util.encrypt(registerRequest.getPassword()));
            user.setUserType(0); // 默认为普通用户
            user.setCreateTime(LocalDateTime.now());

            boolean result = userMapper.insert(user) > 0;
            if (result) {
                System.out.println("用户注册成功: " + registerRequest.getUserName());
            }
            return result;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectList(null);
    }

    @Override
    public List<AdminUserDto> getAllUsersForAdmin() {
        return userMapper.getAllUsersForAdmin();
    }

} 