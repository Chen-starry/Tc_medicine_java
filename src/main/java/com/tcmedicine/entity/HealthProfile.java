package com.tcmedicine.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 健康档案实体类
 */
@Data
@TableName("health_profile")
public class HealthProfile {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("age")
    private Integer age;

    @TableField("gender")
    private Integer gender; // 0-女，1-男

    @TableField("height")
    private BigDecimal height;

    @TableField("weight")
    private BigDecimal weight;

    @TableField("blood_type")
    private String bloodType;

    @TableField("allergies")
    private String allergies;

    @TableField("chronic_diseases")
    private String chronicDiseases;

    @TableField("current_medications")
    private String currentMedications;

    @TableField("health_notes")
    private String healthNotes;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("create_time")
    private LocalDateTime createTime;
} 