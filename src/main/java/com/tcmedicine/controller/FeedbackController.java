package com.tcmedicine.controller;

import com.tcmedicine.dto.ApiResponse;
import com.tcmedicine.dto.SubmitFeedbackRequest;
import com.tcmedicine.entity.Feedback;
import com.tcmedicine.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 公共反馈控制器
 */
@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 提交反馈
     */
    @PostMapping("/submit_feedback")
    public ApiResponse<String> submitFeedback(@RequestBody SubmitFeedbackRequest request) {
        try {
            // 验证必要字段
            if (request.getContact() == null || request.getContact().trim().isEmpty()) {
                return ApiResponse.error("联系方式不能为空");
            }
            if (request.getContent() == null || request.getContent().trim().isEmpty()) {
                return ApiResponse.error("反馈内容不能为空");
            }

            // 创建反馈实体
            Feedback feedback = new Feedback();
            feedback.setUsername(request.getUsername() != null ? request.getUsername() : "匿名用户");
            feedback.setContact(request.getContact().trim());
            feedback.setContent(request.getContent().trim());
            feedback.setFeedbackTime(LocalDateTime.now());
            feedback.setStatus(0); // 未处理状态

            // 保存反馈
            boolean success = feedbackService.createFeedback(feedback);
            
            if (success) {
                return ApiResponse.success("反馈提交成功，我们会认真处理您的建议！");
            } else {
                return ApiResponse.error("反馈提交失败，请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("反馈提交失败，请稍后重试");
        }
    }
} 