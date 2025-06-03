package com.tcmedicine.dto;

import lombok.Data;

/**
 * 图像识别响应数据传输对象
 */
@Data
public class PredictResponse {
    private boolean success;
    private String message;
    private String result; // 识别到的中药名称
    private String origin; // 产地
    private String description; // 描述
    private String effects; // 功效
    private String disease; // 主治疾病
    private Double confidence; // 识别置信度（可选）
    
    public static PredictResponse success(String result, String origin, String description, String effects, String disease) {
        PredictResponse response = new PredictResponse();
        response.setSuccess(true);
        response.setMessage("识别成功");
        response.setResult(result);
        response.setOrigin(origin);
        response.setDescription(description);
        response.setEffects(effects);
        response.setDisease(disease);
        response.setConfidence(0.95); // 默认置信度
        return response;
    }
    
    public static PredictResponse error(String message) {
        PredictResponse response = new PredictResponse();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
} 