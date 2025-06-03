package com.tcmedicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcmedicine.entity.HerbalMedicine;
import org.apache.ibatis.annotations.Mapper;

/**
 * 中草药数据访问层
 */
@Mapper
public interface HerbalMedicineMapper extends BaseMapper<HerbalMedicine> {
    
} 