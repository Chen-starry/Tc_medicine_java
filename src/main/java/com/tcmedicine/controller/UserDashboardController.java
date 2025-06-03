package com.tcmedicine.controller;

import com.tcmedicine.dto.ApiResponse;
import com.tcmedicine.entity.*;
import com.tcmedicine.service.UserDashboardService;
import com.tcmedicine.service.VerificationCodeService;
import com.tcmedicine.utils.JwtUtil;
import com.tcmedicine.dao.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户后台控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserDashboardController {

    private static final Logger logger = LoggerFactory.getLogger(UserDashboardController.class);

    @Autowired
    private UserDashboardService userDashboardService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    // ================== 收藏夹功能 ==================

    /**
     * 获取用户收藏列表
     */
    @GetMapping("/favorites")
    public ApiResponse<List<UserFavorite>> getUserFavorites(HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            List<UserFavorite> favorites = userDashboardService.getUserFavorites(userId);
            return ApiResponse.success("获取收藏列表成功", favorites);
        } catch (Exception e) {
            return ApiResponse.error("获取收藏列表失败: " + e.getMessage());
        }
    }

    /**
     * 添加收藏
     */
    @PostMapping("/favorites/{medicineId}")
    public ApiResponse<String> addFavorite(@PathVariable Integer medicineId, HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            boolean success = userDashboardService.addFavorite(userId, medicineId);
            if (success) {
                return ApiResponse.success("收藏成功", null);
            } else {
                return ApiResponse.error("该药材已在收藏夹中");
            }
        } catch (Exception e) {
            return ApiResponse.error("收藏失败: " + e.getMessage());
        }
    }

    /**
     * 取消收藏
     */
    @DeleteMapping("/favorites/{medicineId}")
    public ApiResponse<String> removeFavorite(@PathVariable Integer medicineId, HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            boolean success = userDashboardService.removeFavorite(userId, medicineId);
            if (success) {
                return ApiResponse.success("取消收藏成功", null);
            } else {
                return ApiResponse.error("取消收藏失败");
            }
        } catch (Exception e) {
            return ApiResponse.error("取消收藏失败: " + e.getMessage());
        }
    }

    /**
     * 检查是否已收藏
     */
    @GetMapping("/favorites/check/{medicineId}")
    public ApiResponse<Boolean> checkFavorite(@PathVariable Integer medicineId, HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            boolean isFavorited = userDashboardService.isFavorited(userId, medicineId);
            return ApiResponse.success("查询成功", isFavorited);
        } catch (Exception e) {
            return ApiResponse.error("查询失败: " + e.getMessage());
        }
    }

    // ================== 识别历史功能 ==================

    /**
     * 获取用户识别历史
     */
    @GetMapping("/recognition-history")
    public ApiResponse<List<RecognitionHistory>> getRecognitionHistory(HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            List<RecognitionHistory> history = userDashboardService.getUserRecognitionHistory(userId);
            return ApiResponse.success("获取识别历史成功", history);
        } catch (Exception e) {
            return ApiResponse.error("获取识别历史失败: " + e.getMessage());
        }
    }

    /**
     * 添加识别历史记录
     */
    @PostMapping("/recognition-history")
    public ApiResponse<String> addRecognitionHistory(@RequestBody RecognitionHistory history, HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            history.setUserId(userId);
            boolean success = userDashboardService.addRecognitionHistory(history);
            if (success) {
                return ApiResponse.success("添加识别记录成功", null);
            } else {
                return ApiResponse.error("添加识别记录失败");
            }
        } catch (Exception e) {
            return ApiResponse.error("添加识别记录失败: " + e.getMessage());
        }
    }

    // ================== 健康档案功能 ==================

    /**
     * 获取用户健康档案
     */
    @GetMapping("/health-profile")
    public ApiResponse<HealthProfile> getHealthProfile(HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            HealthProfile profile = userDashboardService.getUserHealthProfile(userId);
            return ApiResponse.success("获取健康档案成功", profile);
        } catch (Exception e) {
            return ApiResponse.error("获取健康档案失败: " + e.getMessage());
        }
    }

    /**
     * 保存或更新健康档案
     */
    @PostMapping("/health-profile")
    public ApiResponse<String> saveHealthProfile(@RequestBody HealthProfile healthProfile, HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            healthProfile.setUserId(userId);
            boolean success = userDashboardService.saveOrUpdateHealthProfile(healthProfile);
            if (success) {
                return ApiResponse.success("保存健康档案成功", null);
            } else {
                return ApiResponse.error("保存健康档案失败");
            }
        } catch (Exception e) {
            return ApiResponse.error("保存健康档案失败: " + e.getMessage());
        }
    }

    // ================== 用户信息管理 ==================

    /**
     * 上传用户头像
     */
    @PostMapping("/avatar")
    public ApiResponse<String> uploadAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            String avatarUrl = userDashboardService.uploadUserAvatar(userId, file);
            return ApiResponse.success("头像上传成功", avatarUrl);
        } catch (Exception e) {
            return ApiResponse.error("头像上传失败: " + e.getMessage());
        }
    }

    /**
     * 更新用户名
     */
    @PutMapping("/username")
    public ApiResponse<String> updateUsername(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        try {
            Long userId = getUserIdFromToken(httpRequest);
            String newUsername = request.get("username");
            if (newUsername == null || newUsername.trim().isEmpty()) {
                return ApiResponse.error("用户名不能为空");
            }
            
            boolean success = userDashboardService.updateUsername(userId, newUsername);
            if (success) {
                return ApiResponse.success("用户名更新成功", null);
            } else {
                return ApiResponse.error("用户名更新失败");
            }
        } catch (Exception e) {
            return ApiResponse.error("用户名更新失败: " + e.getMessage());
        }
    }

    /**
     * 修改密码（发送验证码）
     */
    @PostMapping("/change-password/send-code")
    public ApiResponse<String> sendPasswordChangeCode(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            if (email == null || email.trim().isEmpty()) {
                return ApiResponse.error("邮箱不能为空");
            }

            boolean success = verificationCodeService.sendVerificationCode(email, "CHANGE_PASSWORD");
            if (success) {
                return ApiResponse.success("验证码已发送到您的邮箱", null);
            } else {
                return ApiResponse.error("验证码发送失败，请稍后重试");
            }
        } catch (Exception e) {
            return ApiResponse.error("验证码发送失败: " + e.getMessage());
        }
    }

    /**
     * 修改密码（验证码验证）
     */
    @PutMapping("/password")
    public ApiResponse<String> changePassword(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        try {
            Long userId = getUserIdFromToken(httpRequest);
            String newPassword = request.get("newPassword");
            String email = request.get("email");
            String verificationCode = request.get("verificationCode");

            if (newPassword == null || newPassword.trim().isEmpty()) {
                return ApiResponse.error("新密码不能为空");
            }
            if (email == null || email.trim().isEmpty()) {
                return ApiResponse.error("邮箱不能为空");
            }
            if (verificationCode == null || verificationCode.trim().isEmpty()) {
                return ApiResponse.error("验证码不能为空");
            }

            boolean success = userDashboardService.changePassword(userId, newPassword, email, verificationCode);
            if (success) {
                return ApiResponse.success("密码修改成功", null);
            } else {
                return ApiResponse.error("密码修改失败");
            }
        } catch (Exception e) {
            return ApiResponse.error("密码修改失败: " + e.getMessage());
        }
    }

    /**
     * 更新个人简介
     */
    @PutMapping("/profile-description")
    public ApiResponse<String> updateProfileDescription(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        try {
            Long userId = getUserIdFromToken(httpRequest);
            String description = request.get("description");
            
            boolean success = userDashboardService.updateProfileDescription(userId, description);
            if (success) {
                return ApiResponse.success("个人简介更新成功", null);
            } else {
                return ApiResponse.error("个人简介更新失败");
            }
        } catch (Exception e) {
            return ApiResponse.error("个人简介更新失败: " + e.getMessage());
        }
    }

    /**
     * 获取用户资料
     */
    @GetMapping("/dashboard/profile")
    public ResponseEntity<Map<String, Object>> getUserProfile(HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            logger.info("获取用户资料 - 解析到的用户ID: {}", userId);
            
            User user = userDashboardService.getUserProfile(userId);
            if (user == null) {
                logger.warn("用户不存在 - 用户ID: {}", userId);
                return ResponseEntity.ok(createResponse(false, "用户不存在", null));
            }
            
            // 构建返回的用户资料信息
            Map<String, Object> profileData = new HashMap<>();
            profileData.put("username", user.getUserName());
            profileData.put("email", user.getEmail());
            profileData.put("avatarUrl", user.getAvatarUrl());
            profileData.put("profileDescription", user.getProfileDescription());
            
            logger.info("获取用户资料成功 - 用户: {}, 邮箱: {}", user.getUserName(), user.getEmail());
            return ResponseEntity.ok(createResponse(true, "获取用户资料成功", profileData));
        } catch (Exception e) {
            logger.error("获取用户资料失败: " + e.getMessage(), e);
            return ResponseEntity.ok(createResponse(false, "获取用户资料失败: " + e.getMessage(), null));
        }
    }

    // ================== 工具方法 ==================

    /**
     * 从请求中获取用户ID
     */
    private Long getUserIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("未找到有效的认证token");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("token无效或已过期");
        }

        String username = jwtUtil.getUsernameFromToken(token);
        // 通过用户名查询数据库获取用户ID
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        
        return user.getUserId();
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("未找到有效的认证token");
        }
        return authHeader.substring(7);
    }

    private Long extractUserIdFromToken(String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        // 通过用户名查询数据库获取用户ID
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        return user.getUserId();
    }

    private Map<String, Object> createResponse(boolean success, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", message);
        response.put("data", data);
        return response;
    }
} 