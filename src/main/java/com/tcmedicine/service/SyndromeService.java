package com.tcmedicine.service;

import com.tcmedicine.entity.Syndrome;
import java.util.List;

/**
 * 证候服务接口
 */
public interface SyndromeService {
    
    /**
     * 获取所有证候
     */
    List<Syndrome> getAllSyndromes();
    
    /**
     * 根据分类ID获取证候
     */
    List<Syndrome> getSyndromesByCategory(Integer categoryId);
} 