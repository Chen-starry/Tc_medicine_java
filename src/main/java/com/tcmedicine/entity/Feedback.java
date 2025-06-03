package com.tcmedicine.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户反馈实体类
 */
@Data
@TableName("feedback")
public class Feedback {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("contact")
    private String contact;

    @TableField("content")
    private String content;

    @TableField("feedback_time")
    private LocalDateTime feedbackTime;

    @TableField("status")
    private Integer status; // 0-未处理，1-已处理

} 