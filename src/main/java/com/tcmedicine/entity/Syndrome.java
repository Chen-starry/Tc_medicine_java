package com.tcmedicine.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 证候实体类
 * 对应数据库中的syndromes表
 */
@Data
@TableName("syndromes")
public class Syndrome {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    @TableField("category_id")
    private Integer categoryId;
    
    /**
     * 证候名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 病因
     */
    @TableField("etiology")
    private String etiology;
    
    /**
     * 症状
     */
    @TableField("symptoms")
    private String symptoms;
    
    /**
     * 治疗方法
     */
    @TableField("treatment")
    private String treatment;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
} 