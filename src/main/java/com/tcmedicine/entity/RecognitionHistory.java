package com.tcmedicine.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 识别历史实体类
 */
@Data
@TableName("recognition_history")
public class RecognitionHistory {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("image_url")
    private String imageUrl;

    @TableField("result_medicine_id")
    private Integer resultMedicineId;

    @TableField("result_name")
    private String resultName;

    @TableField("confidence")
    private BigDecimal confidence;

    @TableField("recognition_time")
    private LocalDateTime recognitionTime;

    // 关联药材信息（非数据库字段）
    @TableField(exist = false)
    private String medicineEffects;

    @TableField(exist = false)
    private String medicineSource;
} 