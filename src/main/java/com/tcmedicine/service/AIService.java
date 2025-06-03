package com.tcmedicine.service;

import com.tcmedicine.dto.ChatRequest;
import com.tcmedicine.dto.ChatResponse;

/**
 * AI服务接口
 */
public interface AIService {
    /**
     * 调用AI进行聊天
     * @param request 聊天请求
     * @return 聊天响应
     */
    ChatResponse chat(ChatRequest request);
} 