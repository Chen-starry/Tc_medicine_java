package com.tcmedicine.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcmedicine.dto.ChatRequest;
import com.tcmedicine.dto.ChatResponse;
import com.tcmedicine.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AI服务实现类 - 调用阿里云通义千问API
 */
@Service
public class AIServiceImpl implements AIService {

    @Autowired
    private RestTemplate restTemplate;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // 从配置文件读取阿里云通义千问API配置
    @Value("${dashscope.api.key}")
    private String apiKey;
    
    @Value("${dashscope.api.url}")
    private String apiUrl;
    
    @Value("${dashscope.api.model}")
    private String model;
    
    @Value("${dashscope.api.max-tokens}")
    private int maxTokens;
    
    @Value("${dashscope.api.temperature}")
    private double temperature;
    
    @Value("${dashscope.api.top-p}")
    private double topP;

    @Override
    public ChatResponse chat(ChatRequest request) {
        try {
            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);
            headers.set("X-DashScope-SSE", "disable");

            // 构建请求体 - 符合通义千问API规范
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            
            // 构建input参数
            Map<String, Object> input = new HashMap<>();
            
            // 构建消息列表
            List<Map<String, String>> messages = new ArrayList<>();
            
            // 系统消息
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", request.getSystem_prompt());
            messages.add(systemMessage);
            
            // 用户消息
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", request.getContent());
            messages.add(userMessage);
            
            input.put("messages", messages);
            requestBody.put("input", input);
            
            // 添加参数配置
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("result_format", "message");
            parameters.put("max_tokens", maxTokens);
            parameters.put("temperature", temperature);
            parameters.put("top_p", topP);
            requestBody.put("parameters", parameters);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // 调用API
            ResponseEntity<String> response = restTemplate.exchange(
                apiUrl, 
                HttpMethod.POST, 
                entity, 
                String.class
            );

            // 解析响应
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode responseJson = objectMapper.readTree(response.getBody());
                
                // 检查响应状态
                JsonNode outputNode = responseJson.path("output");
                if (outputNode.isMissingNode()) {
                    return ChatResponse.error("AI服务返回格式异常");
                }
                
                String aiMessage = outputNode.path("choices").get(0)
                    .path("message").path("content").asText();
                
                // 检查是否有使用量信息
                JsonNode usageNode = responseJson.path("usage");
                if (!usageNode.isMissingNode()) {
                    int totalTokens = usageNode.path("total_tokens").asInt();
                    System.out.println("本次调用使用Token数: " + totalTokens);
                }
                
                System.out.println("AI回复: " + aiMessage); // 调试日志
                return ChatResponse.success(aiMessage);
            } else {
                return ChatResponse.error("AI服务暂时不可用，状态码: " + response.getStatusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("调用通义千问API失败: " + e.getMessage());
            return ChatResponse.error("AI服务调用失败: " + e.getMessage());
        }
    }
} 