package com.tcmedicine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcmedicine.entity.Feedback;
import com.tcmedicine.mapper.FeedbackMapper;
import com.tcmedicine.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 反馈服务实现类
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public List<Feedback> getAllFeedbacks() {
        try {
            return feedbackMapper.getAllFeedbacksOrderByTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取反馈列表失败: " + e.getMessage());
        }
    }

    @Override
    public Page<Feedback> getFeedbackPage(int page, int size) {
        try {
            Page<Feedback> pageRequest = new Page<>(page, size);
            QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("feedback_time");
            return feedbackMapper.selectPage(pageRequest, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("分页获取反馈失败: " + e.getMessage());
        }
    }

    @Override
    public List<Feedback> getFeedbacksByStatus(Integer status) {
        try {
            return feedbackMapper.getFeedbacksByStatus(status);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("根据状态获取反馈失败: " + e.getMessage());
        }
    }

    @Override
    public Feedback getFeedbackById(Integer id) {
        try {
            return feedbackMapper.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取反馈详情失败: " + e.getMessage());
        }
    }

    @Override
    public boolean createFeedback(Feedback feedback) {
        try {
            feedback.setFeedbackTime(LocalDateTime.now());
            if (feedback.getStatus() == null) {
                feedback.setStatus(0); // 默认未处理状态
            }
            int result = feedbackMapper.insert(feedback);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("创建反馈失败: " + e.getMessage());
        }
    }

    @Override
    public boolean updateFeedbackStatus(Integer id, Integer status) {
        try {
            Feedback feedback = new Feedback();
            feedback.setId(id);
            feedback.setStatus(status);
            int result = feedbackMapper.updateById(feedback);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("更新反馈状态失败: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteFeedback(Integer id) {
        try {
            int result = feedbackMapper.deleteById(id);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除反馈失败: " + e.getMessage());
        }
    }

    @Override
    public Long getTotalFeedbackCount() {
        try {
            return feedbackMapper.countAllFeedbacks();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("统计反馈总数失败: " + e.getMessage());
        }
    }

    @Override
    public Long getUnprocessedFeedbackCount() {
        try {
            return feedbackMapper.countUnprocessedFeedbacks();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("统计未处理反馈数失败: " + e.getMessage());
        }
    }
} 