package com.tcmedicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcmedicine.entity.Medicine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 药材数据访问层
 */
@Mapper
public interface MedicineMapper extends BaseMapper<Medicine> {
    
    /**
     * 查询所有药材（包含分类名称）
     */
    @Select("SELECT m.medicine_id as medicineId, m.medicine_name as medicineName, m.category_id as categoryId, " +
            "m.source, m.properties, m.taste, m.channels, m.effects, m.contraindications, m.image_path as imagePath, " +
            "mc.category_name as categoryName " +
            "FROM medicines m " +
            "LEFT JOIN medicine_categories mc ON m.category_id = mc.category_id")
    List<Medicine> getAllMedicines();
    
    /**
     * 根据分类查询药材（包含分类名称）
     */
    @Select("SELECT m.medicine_id as medicineId, m.medicine_name as medicineName, m.category_id as categoryId, " +
            "m.source, m.properties, m.taste, m.channels, m.effects, m.contraindications, m.image_path as imagePath, " +
            "mc.category_name as categoryName " +
            "FROM medicines m " +
            "LEFT JOIN medicine_categories mc ON m.category_id = mc.category_id " +
            "WHERE m.category_id = #{categoryId}")
    List<Medicine> getMedicinesByCategory(Integer categoryId);
    
    /**
     * 根据名称搜索药材（包含分类名称）
     */
    @Select("SELECT m.medicine_id as medicineId, m.medicine_name as medicineName, m.category_id as categoryId, " +
            "m.source, m.properties, m.taste, m.channels, m.effects, m.contraindications, m.image_path as imagePath, " +
            "mc.category_name as categoryName " +
            "FROM medicines m " +
            "LEFT JOIN medicine_categories mc ON m.category_id = mc.category_id " +
            "WHERE m.medicine_name LIKE CONCAT('%', #{name}, '%')")
    List<Medicine> searchMedicinesByName(String name);
} 