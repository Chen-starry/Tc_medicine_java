package com.tcmedicine.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 药材实体类
 */
@Data
@TableName("medicines")
public class Medicine {

    @TableId(value = "medicine_id", type = IdType.AUTO)
    private Integer medicineId;

    @TableField("medicine_name")
    private String medicineName;

    @TableField("category_id")
    private Integer categoryId;

    @TableField("source")
    private String source;

    @TableField("properties")
    private String properties;

    @TableField("taste")
    private String taste;

    @TableField("channels")
    private String channels;

    @TableField("effects")
    private String effects;

    @TableField("contraindications")
    private String contraindications;

    @TableField("image_path")
    private String imagePath;

    // 关联分类名称（非数据库字段）
    @TableField(exist = false)
    private String categoryName;

} 