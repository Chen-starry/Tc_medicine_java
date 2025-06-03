package com.tcmedicine.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户收藏实体类
 */
@Data
@TableName("user_favorites")
public class UserFavorite {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("medicine_id")
    private Integer medicineId;

    @TableField("create_time")
    private LocalDateTime createTime;

    // 关联药材信息（非数据库字段）
    @TableField(exist = false)
    private String medicineName;

    @TableField(exist = false)
    private String medicineImagePath;

    @TableField(exist = false)
    private String medicineEffects;
} 