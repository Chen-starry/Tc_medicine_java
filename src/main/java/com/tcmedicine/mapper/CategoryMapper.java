package com.tcmedicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcmedicine.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 疾病分类数据访问层
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    
    /**
     * 查询所有疾病分类
     */
    @Select("SELECT * FROM categories ORDER BY id")
    List<Category> getAllCategories();
} 