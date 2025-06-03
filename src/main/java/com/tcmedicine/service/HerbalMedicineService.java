package com.tcmedicine.service;

import com.tcmedicine.entity.HerbalMedicine;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 中草药服务接口
 */
public interface HerbalMedicineService {

    /**
     * 获取中草药列表（分页）
     */
    Page<HerbalMedicine> getHerbalMedicineList(int page, int size, String keyword);

    /**
     * 根据ID获取中草药详情
     */
    HerbalMedicine getHerbalMedicineById(Integer id);

    /**
     * 根据拼音名称搜索中草药
     */
    Page<HerbalMedicine> searchByPinyinName(String pinyinName, int page, int size);

    /**
     * 根据拼音名称查找中药信息
     * @param pinyinName 拼音名称
     * @return 中药信息
     */
    HerbalMedicine findByPinyinName(String pinyinName);
    
    /**
     * 模拟图像识别，根据图片返回可能的中药名称
     * @param fileName 图片文件名
     * @return 识别到的拼音名称
     */
    String predictFromImage(String fileName);

}
