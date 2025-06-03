package com.tcmedicine.service;

import com.tcmedicine.entity.Feedback;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 反馈服务接口
 */
public interface FeedbackService {

    /**
     * 获取所有反馈
     */
    List<Feedback> getAllFeedbacks();

    /**
     * 分页获取反馈列表
     */
    Page<Feedback> getFeedbackPage(int page, int size);

    /**
     * 根据状态获取反馈
     */
    List<Feedback> getFeedbacksByStatus(Integer status);

    /**
     * 根据ID获取反馈详情
     */
    Feedback getFeedbackById(Integer id);

    /**
     * 创建新反馈
     */
    boolean createFeedback(Feedback feedback);

    /**
     * 更新反馈状态
     */
    boolean updateFeedbackStatus(Integer id, Integer status);

    /**
     * 删除反馈
     */
    boolean deleteFeedback(Integer id);

    /**
     * 获取反馈统计信息
     */
    Long getTotalFeedbackCount();

    /**
     * 获取未处理反馈数量
     */
    Long getUnprocessedFeedbackCount();
} 