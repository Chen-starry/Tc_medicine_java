package com.tcmedicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcmedicine.entity.RecognitionHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 识别历史数据访问层
 */
@Mapper
public interface RecognitionHistoryMapper extends BaseMapper<RecognitionHistory> {

    /**
     * 获取用户识别历史列表（包含药材信息）
     */
    @Select("SELECT rh.id, rh.user_id as userId, rh.image_url as imageUrl, " +
            "rh.result_medicine_id as resultMedicineId, rh.result_name as resultName, " +
            "rh.confidence, rh.recognition_time as recognitionTime, " +
            "m.effects as medicineEffects, m.source as medicineSource " +
            "FROM recognition_history rh " +
            "LEFT JOIN medicines m ON rh.result_medicine_id = m.medicine_id " +
            "WHERE rh.user_id = #{userId} " +
            "ORDER BY rh.recognition_time DESC")
    List<RecognitionHistory> getUserRecognitionHistoryWithMedicineInfo(Long userId);
} 