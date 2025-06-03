package com.tcmedicine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcmedicine.entity.MedicineCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 药材分类数据访问接口
 */
@Mapper
public interface MedicineCategoryMapper extends BaseMapper<MedicineCategory> {

    /**
     * 获取所有药材分类（从medicine_categories表）
     */
    @Select("SELECT category_id as id, category_name as name, category_description as description FROM medicine_categories ORDER BY category_id")
    List<MedicineCategory> getAllMedicineCategories();

    /**
     * 根据ID获取药材分类
     */
    @Select("SELECT category_id as id, category_name as name, category_description as description FROM medicine_categories WHERE category_id = #{id}")
    MedicineCategory getMedicineCategoryById(Integer id);

    /**
     * 统计药材分类总数
     */
    @Select("SELECT COUNT(*) FROM medicine_categories")
    Long countMedicineCategories();
} 