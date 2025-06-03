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

    @Override
    public Syndrome getSyndromeById(Integer id) {
        try {
            return syndromeMapper.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("根据ID查询疾病失败: " + e.getMessage());
        }
    }

    @Override
    public Syndrome updateSyndrome(Syndrome syndrome) {
        try {
            int result = syndromeMapper.updateById(syndrome);
            if (result > 0) {
                return syndromeMapper.selectById(syndrome.getId());
            } else {
                throw new RuntimeException("更新疾病失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("更新疾病失败: " + e.getMessage());
        }
    }

    @Override
    public Syndrome createSyndrome(Syndrome syndrome) {
        try {
            int result = syndromeMapper.insert(syndrome);
            if (result > 0) {
                return syndrome;
            } else {
                throw new RuntimeException("创建疾病失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("创建疾病失败: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteSyndrome(Integer id) {
        try {
            return syndromeMapper.deleteById(id) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除疾病失败: " + e.getMessage());
        }
    }
} 