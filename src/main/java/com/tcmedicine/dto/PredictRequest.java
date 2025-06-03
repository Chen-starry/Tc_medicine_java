package com.tcmedicine.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图像识别请求数据传输对象
 */
@Data
public class PredictRequest {
    private MultipartFile file;
    private String description; // 可选的描述信息
} 