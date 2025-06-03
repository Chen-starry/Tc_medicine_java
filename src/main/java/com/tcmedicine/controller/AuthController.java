package com.tcmedicine.controller;

import com.tcmedicine.dto.ApiResponse;
import com.tcmedicine.dto.LoginRequest;
import com.tcmedicine.dto.RegisterRequest;
import com.tcmedicine.entity.User;
import com.tcmedicine.service.UserService;
import com.tcmedicine.service.VerificationCodeService;
import com.tcmedicine.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 发送邮箱验证码
     */
    @PostMapping("/send-verification-code")
    public ApiResponse<String> sendVerificationCode(@RequestParam String email) {
        try {
            // 基本邮箱格式验证
            if (!isValidEmail(email)) {
                return ApiResponse.error("邮箱格式不正确");
            }

            // 检查邮箱是否已注册
            User existingUser = userService.findByEmail(email);
            if (existingUser != null) {
                return ApiResponse.error("该邮箱已被注册");
            }

            // 检查发送频率
            if (!verificationCodeService.canSendCode(email, "REGISTER")) {
                return ApiResponse.error("发送过于频繁，请稍后再试");
            }

            // 发送验证码
            boolean sent = verificationCodeService.sendVerificationCode(email, "REGISTER");
            if (sent) {
                return ApiResponse.success("验证码已发送到您的邮箱", null);
            } else {
                return ApiResponse.error("验证码发送失败，请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("系统错误: " + e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.login(loginRequest);
            
            if (user != null) {
                // 生成JWT token
                String token = jwtUtil.generateToken(user.getUserName(), user.getUserType());
                
                Map<String, Object> data = new HashMap<>();
                data.put("token", token);
                data.put("user", user);
                
                return ApiResponse.success("登录成功", data);
            } else {
                return ApiResponse.error("用户名或密码错误");
            }
        } catch (Exception e) {
            return ApiResponse.error("登录失败: " + e.getMessage());
        }
    }

    /**
     * 用户注册（带邮箱验证）
     */
    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // 基本参数验证
            if (registerRequest.getUserName() == null || registerRequest.getUserName().trim().isEmpty()) {
                return ApiResponse.error("用户名不能为空");
            }
            if (registerRequest.getEmail() == null || !isValidEmail(registerRequest.getEmail())) {
                return ApiResponse.error("邮箱格式不正确");
            }
            if (registerRequest.getVerificationCode() == null || registerRequest.getVerificationCode().trim().isEmpty()) {
                return ApiResponse.error("验证码不能为空");
            }
            if (registerRequest.getPassword() == null || registerRequest.getPassword().length() < 6) {
                return ApiResponse.error("密码长度不能少于6位");
            }

            // 使用带邮箱验证的注册方法
            boolean success = userService.registerWithEmailVerification(registerRequest);
            if (success) {
                return ApiResponse.success("注册成功", null);
            } else {
                return ApiResponse.error("注册失败，请检查验证码是否正确或用户名/邮箱是否已存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("注册失败: " + e.getMessage());
        }
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpSession session) {
        String username = (String) session.getAttribute("username");
        session.invalidate();
        return ApiResponse.success("登出成功", null);
    }

    /**
     * 验证token
     */
    @PostMapping("/validate")
    public ApiResponse<Map<String, Object>> validateToken(@RequestParam String token) {
        try {
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                User user = userService.findByUsername(username);
                
                Map<String, Object> data = new HashMap<>();
                data.put("valid", true);
                data.put("user", user);
                
                return ApiResponse.success("Token有效", data);
            } else {
                return ApiResponse.error("Token无效或已过期");
            }
        } catch (Exception e) {
            return ApiResponse.error("Token验证失败: " + e.getMessage());
        }
    }

    /**
     * 简单的邮箱格式验证
     */
    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
} 