package com.tcmedicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcmedicine.entity.Syndrome;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 证候数据访问层
 */
@Mapper
public interface SyndromeMapper extends BaseMapper<Syndrome> {
    
    /**
     * 查询所有证候
     */
    @Select("SELECT * FROM syndromes ORDER BY id")
    List<Syndrome> getAllSyndromes();
    
    /**
     * 根据分类ID查询证候
     */
    @Select("SELECT name, etiology, symptoms, treatment FROM syndromes WHERE category_id = #{categoryId}")
    List<Syndrome> getSyndromesByCategory(Integer categoryId);
} 