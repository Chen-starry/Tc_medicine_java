package com.tcmedicine.dto;

import lombok.Data;

/**
 * 提交反馈请求数据传输对象
 */
@Data
public class SubmitFeedbackRequest {
    private String username;
    private String contact;
    private String content;
    private String feedback_time; // 前端传递的时间字符串，可选
} 