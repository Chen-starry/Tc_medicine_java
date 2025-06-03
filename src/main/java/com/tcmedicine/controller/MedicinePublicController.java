package com.tcmedicine.controller;

import com.tcmedicine.service.MedicineService;
import com.tcmedicine.entity.Medicine;
import com.tcmedicine.entity.MedicineCategory;
import com.tcmedicine.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 公共药材查询API控制器
 * 对应Flask中的/api/medicines接口
 */
@RestController
@RequestMapping("/api/public/medicines")
@CrossOrigin(originPatterns = "*")
public class MedicinePublicController {
    
    @Autowired
    private MedicineService medicineService;
    
    /**
     * 获取所有药材和分类数据
     * 对应Flask的/api/medicines接口
     */
    @GetMapping
    public ApiResponse<Map<String, Object>> getMedicines() {
        try {
            // 获取所有分类
            List<MedicineCategory> categories = medicineService.getAllCategories();
            
            // 获取所有药材
            List<Medicine> medicines = medicineService.getAllMedicines();
            
            // 构建响应数据
            Map<String, Object> data = new HashMap<>();
            data.put("categories", categories);
            data.put("medicines", medicines);
            data.put("total", medicines.size());
            
            return ApiResponse.success("获取中药数据成功", data);
        } catch (Exception e) {
            return ApiResponse.error("获取中药数据失败，请稍后重试");
        }
    }
    
    /**
     * 根据分类ID获取药材列表
     */
    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<Medicine>> getMedicinesByCategory(@PathVariable Integer categoryId) {
        try {
            List<Medicine> medicines = medicineService.getMedicinesByCategory(categoryId);
            return ApiResponse.success("获取分类药材成功", medicines);
        } catch (Exception e) {
            return ApiResponse.error("获取分类药材失败");
        }
    }
    
    /**
     * 根据药材名称搜索
     */
    @GetMapping("/search")
    public ApiResponse<List<Medicine>> searchMedicines(@RequestParam String keyword) {
        try {
            List<Medicine> medicines = medicineService.searchMedicinesByName(keyword);
            return ApiResponse.success("搜索药材成功", medicines);
        } catch (Exception e) {
            return ApiResponse.error("搜索药材失败");
        }
    }
    
    /**
     * 获取药材详情
     */
    @GetMapping("/{id}")
    public ApiResponse<Medicine> getMedicineDetail(@PathVariable Integer id) {
        try {
            Medicine medicine = medicineService.getMedicineById(id);
            if (medicine != null) {
                return ApiResponse.success("获取药材详情成功", medicine);
            } else {
                return ApiResponse.error("药材不存在");
            }
        } catch (Exception e) {
            return ApiResponse.error("获取药材详情失败");
        }
    }
} 