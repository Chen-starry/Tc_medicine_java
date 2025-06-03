package com.tcmedicine.controller;

import com.tcmedicine.dto.ApiResponse;
import com.tcmedicine.entity.Category;
import com.tcmedicine.entity.Syndrome;
import com.tcmedicine.service.CategoryService;
import com.tcmedicine.service.SyndromeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 疾病控制器
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class DiseaseController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SyndromeService syndromeService;

    /**
     * 获取所有疾病分类（与fhy/main.py的/api/categories对应）
     */
    @GetMapping("/categories")
    public List<Category> getCategories() {
        try {
            return categoryService.getAllCategories();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取分类失败");
        }
    }

    /**
     * 根据分类ID获取证候（与fhy/main.py的/api/syndromes/<int:category_id>对应）
     */
    @GetMapping("/syndromes/{categoryId}")
    public List<Syndrome> getSyndromes(@PathVariable Integer categoryId) {
        try {
            return syndromeService.getSyndromesByCategory(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取证候失败");
        }
    }
} 