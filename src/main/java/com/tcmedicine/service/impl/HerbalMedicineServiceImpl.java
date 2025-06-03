package com.tcmedicine.service.impl;

import com.tcmedicine.service.HerbalMedicineService;
import com.tcmedicine.entity.HerbalMedicine;
import com.tcmedicine.mapper.HerbalMedicineMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 中草药服务实现类
 */
@Service
public class HerbalMedicineServiceImpl implements HerbalMedicineService {
    
    @Autowired
    private HerbalMedicineMapper herbalMedicineMapper;
    
    @Override
    public Page<HerbalMedicine> getHerbalMedicineList(int page, int size, String keyword) {
        Page<HerbalMedicine> pageInfo = new Page<>(page, size);
        
        LambdaQueryWrapper<HerbalMedicine> queryWrapper = new LambdaQueryWrapper<>();
        
        // 根据关键词搜索拼音名称、结果、功效或疾病
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(HerbalMedicine::getPinyinName, keyword)
                       .or()
                       .like(HerbalMedicine::getResult, keyword)
                       .or()
                       .like(HerbalMedicine::getEffects, keyword)
                       .or()
                       .like(HerbalMedicine::getDisease, keyword);
        }
        
        // 按ID排序
        queryWrapper.orderByAsc(HerbalMedicine::getId);
        
        return herbalMedicineMapper.selectPage(pageInfo, queryWrapper);
    }
    
    @Override
    public HerbalMedicine getHerbalMedicineById(Integer id) {
        return herbalMedicineMapper.selectById(id);
    }
    
    @Override
    public Page<HerbalMedicine> searchByPinyinName(String pinyinName, int page, int size) {
        Page<HerbalMedicine> pageInfo = new Page<>(page, size);
        QueryWrapper<HerbalMedicine> queryWrapper = new QueryWrapper<>();
        
        if (pinyinName != null && !pinyinName.trim().isEmpty()) {
            queryWrapper.like("pinyin_name", pinyinName);
        }
        
        return herbalMedicineMapper.selectPage(pageInfo, queryWrapper);
    }

    @Override
    public HerbalMedicine findByPinyinName(String pinyinName) {
        QueryWrapper<HerbalMedicine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pinyin_name", pinyinName);
        return herbalMedicineMapper.selectOne(queryWrapper);
    }

    @Override
    public String predictFromImage(String fileName) {
        // 模拟图像识别逻辑 - 从数据库中已有的中药拼音名称中随机选择
        // 实际应用中应该调用真实的AI模型
        List<String> availableHerbs = Arrays.asList(
            "aiye", "baibiandou", "baibu", "baidoukou", "baihe", 
            "cangzhu", "cansha", "dangshen", "ezhu", "foshou",
            "gancao", "gouqi", "honghua", "hongteng", "huaihua",
            "jiangcan", "jingjie", "jinyinhua", "mudanpi", "niubangzi",
            "zhuling", "zhuru", "zhuye", "zicao"
        );
        
        // 简单的文件名匹配逻辑
        String lowerFileName = fileName.toLowerCase();
        for (String herb : availableHerbs) {
            if (lowerFileName.contains(herb)) {
                return herb;
            }
        }
        
        // 如果没有匹配到，随机返回一个（模拟识别）
        Random random = new Random();
        return availableHerbs.get(random.nextInt(availableHerbs.size()));
    }
}
