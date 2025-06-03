package com.tcmedicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcmedicine.entity.UserFavorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户收藏数据访问层
 */
@Mapper
public interface UserFavoriteMapper extends BaseMapper<UserFavorite> {

    /**
     * 获取用户收藏列表（包含药材信息）
     */
    @Select("SELECT uf.id, uf.user_id as userId, uf.medicine_id as medicineId, uf.create_time as createTime, " +
            "m.medicine_name as medicineName, m.image_path as medicineImagePath, m.effects as medicineEffects " +
            "FROM user_favorites uf " +
            "LEFT JOIN medicines m ON uf.medicine_id = m.medicine_id " +
            "WHERE uf.user_id = #{userId} " +
            "ORDER BY uf.create_time DESC")
    List<UserFavorite> getUserFavoritesWithMedicineInfo(Long userId);

    /**
     * 检查用户是否已收藏某个药材
     */
    @Select("SELECT COUNT(*) FROM user_favorites WHERE user_id = #{userId} AND medicine_id = #{medicineId}")
    int checkUserFavorite(Long userId, Integer medicineId);
} 