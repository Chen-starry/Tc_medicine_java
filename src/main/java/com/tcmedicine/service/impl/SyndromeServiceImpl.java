package com.tcmedicine.service.impl;

import com.tcmedicine.entity.Syndrome;
import com.tcmedicine.mapper.SyndromeMapper;
import com.tcmedicine.service.SyndromeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 证候服务实现类
 */
@Service
public class SyndromeServiceImpl implements SyndromeService {

    @Autowired
    private SyndromeMapper syndromeMapper;

    @Override
    public List<Syndrome> getAllSyndromes() {
        return syndromeMapper.getAllSyndromes();
    }

    @Override
    public List<Syndrome> getSyndromesByCategory(Integer categoryId) {
        return syndromeMapper.getSyndromesByCategory(categoryId);
    }
} 