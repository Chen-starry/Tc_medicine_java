package com.tcmedicine.controller;

import com.tcmedicine.service.MedicineService;
import com.tcmedicine.entity.Medicine;
import com.tcmedicine.entity.MedicineCategory;
import com.tcmedicine.dto.ApiResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 药材管理控制器
 */
@RestController
@RequestMapping("/api/medicines")
public class MedicineController {
    
    @Autowired
    private MedicineService medicineService;
    
    /**
     * 分页查询药材列表
     */
    @GetMapping
    public ApiResponse<Page<Medicine>> getMedicineList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId) {
        
        Page<Medicine> medicines = medicineService.getMedicineList(page, size, keyword, categoryId);
        return ApiResponse.success("获取药材列表成功", medicines);
    }
    
    /**
     * 根据ID获取药材详情
     */
    @GetMapping("/{id}")
    public ApiResponse<Medicine> getMedicineById(@PathVariable Integer id) {
        Medicine medicine = medicineService.getMedicineById(id);
        if (medicine == null) {
            return ApiResponse.error("药材不存在");
        }
        return ApiResponse.success("获取药材详情成功", medicine);
    }
    
    /**
     * 创建新药材
     */
    @PostMapping
    public ApiResponse<Medicine> createMedicine(@RequestBody Medicine medicine) {
        try {
            Medicine created = medicineService.createMedicine(medicine);
            return ApiResponse.success("创建药材成功", created);
        } catch (Exception e) {
            return ApiResponse.error("创建药材失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新药材信息
     */
    @PutMapping("/{id}")
    public ApiResponse<Medicine> updateMedicine(@PathVariable Integer id, @RequestBody Medicine medicine) {
        try {
            medicine.setMedicineId(id);
            Medicine updated = medicineService.updateMedicine(medicine);
            return ApiResponse.success("更新药材成功", updated);
        } catch (Exception e) {
            return ApiResponse.error("更新药材失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除药材
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMedicine(@PathVariable Integer id) {
        try {
            boolean success = medicineService.deleteMedicine(id);
            if (success) {
                return ApiResponse.success("删除药材成功", null);
            } else {
                return ApiResponse.error("删除药材失败，药材不存在");
            }
        } catch (Exception e) {
            return ApiResponse.error("删除药材失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取所有药材分类
     */
    @GetMapping("/categories")
    public ApiResponse<List<MedicineCategory>> getAllCategories() {
        List<MedicineCategory> categories = medicineService.getAllCategories();
        return ApiResponse.success("获取分类列表成功", categories);
    }
    
    /**
     * 根据图片预测药材
     */
    @PostMapping("/predict")
    public ApiResponse<Medicine> predictMedicine(@RequestParam String imageUrl) {
        try {
            Medicine medicine = medicineService.predictMedicineByImage(imageUrl);
            return ApiResponse.success("药材预测成功", medicine);
        } catch (Exception e) {
            return ApiResponse.error("药材预测失败: " + e.getMessage());
        }
    }
} 