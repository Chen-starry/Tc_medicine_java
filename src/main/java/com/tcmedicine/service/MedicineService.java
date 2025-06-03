package com.tcmedicine.service;

import com.tcmedicine.entity.Medicine;
import com.tcmedicine.entity.MedicineCategory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

/**
 * 药材服务接口
 */
public interface MedicineService {
    
    /**
     * 分页查询药材列表
     * @param page 页码
     * @param size 页大小
     * @param keyword 搜索关键词（根据药材名称或功效搜索）
     * @param categoryId 分类ID
     */
    Page<Medicine> getMedicineList(int page, int size, String keyword, Integer categoryId);
    
    /**
     * 根据ID获取药材详情
     */
    Medicine getMedicineById(Integer id);
    
    /**
     * 获取所有药材
     */
    List<Medicine> getAllMedicines();
    
    /**
     * 根据分类ID获取药材列表
     */
    List<Medicine> getMedicinesByCategory(Integer categoryId);
    
    /**
     * 根据药材名称搜索
     */
    List<Medicine> searchMedicinesByName(String keyword);
    
    /**
     * 创建新药材
     */
    Medicine createMedicine(Medicine medicine);
    
    /**
     * 更新药材信息
     */
    Medicine updateMedicine(Medicine medicine);
    
    /**
     * 删除药材
     */
    boolean deleteMedicine(Integer id);
    
    /**
     * 获取所有药材分类
     */
    List<MedicineCategory> getAllCategories();
    
    /**
     * 根据图片预测药材
     */
    Medicine predictMedicineByImage(String imageUrl);
} 