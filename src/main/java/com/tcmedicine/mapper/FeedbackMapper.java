package com.tcmedicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcmedicine.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 反馈数据访问层
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

    /**
     * 获取所有反馈（按时间倒序）
     */
    @Select("SELECT * FROM feedback ORDER BY feedback_time DESC")
    List<Feedback> getAllFeedbacksOrderByTime();

    /**
     * 根据状态获取反馈
     */
    @Select("SELECT * FROM feedback WHERE status = #{status} ORDER BY feedback_time DESC")
    List<Feedback> getFeedbacksByStatus(Integer status);

    /**
     * 统计反馈总数
     */
    @Select("SELECT COUNT(*) FROM feedback")
    Long countAllFeedbacks();

    /**
     * 统计未处理反馈数量
     */
    @Select("SELECT COUNT(*) FROM feedback WHERE status = 0")
    Long countUnprocessedFeedbacks();
} 