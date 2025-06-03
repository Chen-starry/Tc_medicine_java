package com.tcmedicine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcmedicine.dto.AdminUserDto;
import com.tcmedicine.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户数据访问接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户
     */
    @Select("SELECT id AS userId, USER_NAME, email, email_verified, PASSWORD, user_type, creat_time, avatar_url, profile_description FROM usr_user WHERE user_name = #{username}")
    User findByUsername(String username);

    /**
     * 管理员查询所有用户 - 返回前端期望的字段格式
     */
    @Select("SELECT id, USER_NAME as username, user_type as userType, creat_time as registerTime FROM usr_user ORDER BY id")
    List<AdminUserDto> getAllUsersForAdmin();

} 