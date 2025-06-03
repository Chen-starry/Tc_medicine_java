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

    /**
     * 根据ID获取单个证候
     */
    Syndrome getSyndromeById(Integer id);

    /**
     * 更新证候信息
     */
    Syndrome updateSyndrome(Syndrome syndrome);

    /**
     * 创建新证候
     */
    Syndrome createSyndrome(Syndrome syndrome);

    /**
     * 删除证候
     */
    boolean deleteSyndrome(Integer id);
} 