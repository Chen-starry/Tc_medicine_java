package com.tcmedicine.dto;

import lombok.Data;

/**
 * 聊天请求数据传输对象
 */
@Data
public class ChatRequest {
    private String content;
    private String system_prompt;
} 