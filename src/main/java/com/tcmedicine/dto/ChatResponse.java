package com.tcmedicine.dto;

import lombok.Data;

/**
 * 聊天响应数据传输对象
 */
@Data
public class ChatResponse {
    private boolean success;
    private String message;
    
    public static ChatResponse success(String message) {
        ChatResponse response = new ChatResponse();
        response.setSuccess(true);
        response.setMessage(message);
        return response;
    }
    
    public static ChatResponse error(String message) {
        ChatResponse response = new ChatResponse();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
} 