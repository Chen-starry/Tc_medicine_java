package com.tcmedicine.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("usr_user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long userId;

    @TableField("USER_NAME")
    private String userName;

    @TableField("email")
    private String email;

    @TableField("email_verified")
    private Boolean emailVerified;

    @TableField("PASSWORD")
    private String password;

    @TableField("user_type")
    private Integer userType; // 1-管理员, 0-普通用户

    @TableField("creat_time")
    private LocalDateTime createTime;

    @TableField("avatar_url")
    private String avatarUrl;

    @TableField("profile_description")
    private String profileDescription;

    // update_time字段在数据库中不存在，先注释掉
    // @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    // private LocalDateTime updateTime;

} 