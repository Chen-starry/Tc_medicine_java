package com.tcmedicine.controller;

import com.tcmedicine.dto.AdminUserDto;
import com.tcmedicine.dto.ApiResponse;
import com.tcmedicine.entity.User;
import com.tcmedicine.entity.Medicine;
import com.tcmedicine.entity.MedicineCategory;
import com.tcmedicine.entity.Feedback;
import com.tcmedicine.entity.Category;
import com.tcmedicine.entity.Syndrome;
import com.tcmedicine.service.UserService;
import com.tcmedicine.service.MedicineService;
import com.tcmedicine.service.FeedbackService;
import com.tcmedicine.service.CategoryService;
import com.tcmedicine.service.SyndromeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class AdminController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private MedicineService medicineService;
    
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SyndromeService syndromeService;

    /**
     * 获取所有用户（管理员版本 - 字段格式化）
     */
    @GetMapping("/users")
    public ApiResponse<List<AdminUserDto>> getAllUsers() {
        try {
            List<AdminUserDto> users = userService.getAllUsersForAdmin();
            return ApiResponse.success(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取用户列表失败，请稍后重试");
        }
    }

    /**
     * 获取所有药材（管理员版本）
     */
    @GetMapping("/medicines")
    public ApiResponse<List<Medicine>> getAllMedicines() {
        try {
            List<Medicine> medicines = medicineService.getAllMedicines();
            return ApiResponse.success(medicines);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取药材列表失败，请稍后重试");
        }
    }

    /**
     * 获取所有疾病分类（使用真实数据）
     */
    @GetMapping("/disease-categories")
    public ApiResponse<List<Category>> getDiseaseCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ApiResponse.success(categories);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取疾病分类失败，请稍后重试");
        }
    }

    /**
     * 获取所有证候（疾病数据，使用真实数据）
     */
    @GetMapping("/diseases")
    public ApiResponse<List<Syndrome>> getAllDiseases() {
        try {
            List<Syndrome> syndromes = syndromeService.getAllSyndromes();
            return ApiResponse.success(syndromes);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取疾病列表失败，请稍后重试");
        }
    }

    /**
     * 获取所有反馈
     */
    @GetMapping("/feedbacks")
    public ApiResponse<List<Feedback>> getAllFeedbacks() {
        try {
            List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
            return ApiResponse.success("获取反馈列表成功", feedbacks);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取反馈列表失败，请稍后重试");
        }
    }

    /**
     * 根据ID获取反馈详情
     */
    @GetMapping("/feedbacks/{id}")
    public ApiResponse<Feedback> getFeedbackById(@PathVariable Integer id) {
        try {
            Feedback feedback = feedbackService.getFeedbackById(id);
            if (feedback != null) {
                return ApiResponse.success("获取反馈详情成功", feedback);
            } else {
                return ApiResponse.error("反馈不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取反馈详情失败，请稍后重试");
        }
    }

    /**
     * 更新反馈状态
     */
    @PutMapping("/feedbacks/{id}")
    public ApiResponse<String> updateFeedbackStatus(@PathVariable Integer id, @RequestBody Map<String, Object> request) {
        try {
            // 从请求体中获取状态值
            Object statusObj = request.get("status");
            if (statusObj == null) {
                return ApiResponse.error("状态参数不能为空");
            }
            
            Integer status;
            if (statusObj instanceof String) {
                status = Integer.parseInt((String) statusObj);
            } else if (statusObj instanceof Integer) {
                status = (Integer) statusObj;
            } else {
                return ApiResponse.error("状态参数格式错误");
            }
            
            // 验证状态值的有效性
            if (status != 0 && status != 1) {
                return ApiResponse.error("状态值必须为0（未处理）或1（已处理）");
            }
            
            boolean success = feedbackService.updateFeedbackStatus(id, status);
            if (success) {
                return ApiResponse.success("反馈状态更新成功");
            } else {
                return ApiResponse.error("反馈状态更新失败");
            }
        } catch (NumberFormatException e) {
            return ApiResponse.error("状态参数必须是数字");
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("更新反馈状态失败，请稍后重试");
        }
    }

    /**
     * 获取系统统计信息
     */
    @GetMapping("/stats")
    public ApiResponse<Map<String, Object>> getSystemStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // 用户统计
            List<User> users = userService.getAllUsers();
            stats.put("totalUsers", users.size());
            
            // 药材统计
            List<Medicine> medicines = medicineService.getAllMedicines();
            stats.put("totalMedicines", medicines.size());
            
            // 反馈统计（从数据库获取真实数据）
            Long totalFeedbacks = feedbackService.getTotalFeedbackCount();
            stats.put("totalFeedbacks", totalFeedbacks != null ? totalFeedbacks : 0);
            
            // 药材分类统计
            List<MedicineCategory> medicineCategories = medicineService.getAllCategories();
            stats.put("totalCategories", medicineCategories.size());
            
            // 疾病分类统计（使用真实数据）
            List<Category> diseaseCategories = categoryService.getAllCategories();
            stats.put("totalDiseaseCategories", diseaseCategories.size());
            
            // 证候统计（使用真实数据）
            List<Syndrome> syndromes = syndromeService.getAllSyndromes();
            stats.put("totalDiseases", syndromes.size());
            
            // 其他统计信息
            stats.put("monthlyActiveUsers", 128);
            
            return ApiResponse.success("获取统计信息成功", stats);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取统计信息失败，请稍后重试");
        }
    }

    /**
     * 获取药材分类
     */
    @GetMapping("/medicine-categories")
    public ApiResponse<List<MedicineCategory>> getMedicineCategories() {
        try {
            List<MedicineCategory> categories = medicineService.getAllCategories();
            return ApiResponse.success("获取药材分类成功", categories);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取药材分类失败，请稍后重试");
        }
    }
} 