package com.tcmedicine.service;

import com.tcmedicine.entity.Category;
import java.util.List;

/**
 * 疾病分类服务接口
 */
public interface CategoryService {
    
    /**
     * 获取所有疾病分类
     */
    List<Category> getAllCategories();
} 