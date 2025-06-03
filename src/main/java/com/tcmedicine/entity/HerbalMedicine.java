package com.tcmedicine.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 中草药实体类
 */
@Data
@TableName("herbal_medicine")
public class HerbalMedicine {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("pinyin_name")
    private String pinyinName;

    @TableField("result")
    private String result;

    @TableField("origin")
    private String origin;

    @TableField("description")
    private String description;

    @TableField("effects")
    private String effects;

    @TableField("disease")
    private String disease;

}
