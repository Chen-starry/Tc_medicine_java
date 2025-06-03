package com.tcmedicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcmedicine.entity.HealthProfile;
import org.apache.ibatis.annotations.Mapper;

/**
 * 健康档案数据访问层
 */
@Mapper
public interface HealthProfileMapper extends BaseMapper<HealthProfile> {
} 