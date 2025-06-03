package com.tcmedicine.controller;

import com.tcmedicine.dto.ChatRequest;
import com.tcmedicine.dto.ChatResponse;
import com.tcmedicine.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 聊天控制器 - AI问诊功能
 */
@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class ChatController {

    @Autowired
    private AIService aiService;

    /**
     * AI聊天接口
     * @param request 聊天请求
     * @return 聊天响应
     */
    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        try {
            // 验证必要字段
            if (request.getContent() == null || request.getContent().trim().isEmpty()) {
                return ChatResponse.error("消息内容不能为空");
            }
            
            // 如果没有提供系统提示，使用默认的中医助手提示
            if (request.getSystem_prompt() == null || request.getSystem_prompt().trim().isEmpty()) {
                request.setSystem_prompt(getDefaultSystemPrompt());
            }

            // 调用AI服务
            return aiService.chat(request);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ChatResponse.error("处理请求时发生错误");
        }
    }

    /**
     * 获取默认的中医助手系统提示词 - 参考fhy项目
     */
    private String getDefaultSystemPrompt() {
        return "你是一位资深中医AI\"小岐\"，请严格遵循以下要求：\n" +
               "1. 专业范围：仅回答中医诊断、中药、针灸、养生相关问题\n" +
               "2. 回答结构：\n" +
               "    【症状分析】从中医理论分析症状原因\n" +
               "    【辨证分型】列出可能的证型(如肝郁气滞等)\n" +
               "    【调理原则】给出治疗原则(如疏肝理气等)\n" +
               "    【推荐方案】\n" +
               "        - 中药方剂(注明经典出处)\n" +
               "        - 食疗建议\n" +
               "        - 穴位按摩\n" +
               "    【注意事项】提醒禁忌和日常调养\n" +
               "3. 语言要求：专业但通俗，引用经典必须注明出处\n" +
               "4. 非中医问题回复：\"抱歉，作为中医助手，我无法回答这个问题\"\n" +
               "5. 非疾病相关问题可以不按照这个模版来回答";
    }
} 