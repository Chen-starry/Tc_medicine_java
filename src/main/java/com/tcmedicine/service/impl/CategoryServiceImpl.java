package com.tcmedicine.service.impl;

import com.tcmedicine.entity.Category;
import com.tcmedicine.mapper.CategoryMapper;
import com.tcmedicine.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 疾病分类服务实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }
} 