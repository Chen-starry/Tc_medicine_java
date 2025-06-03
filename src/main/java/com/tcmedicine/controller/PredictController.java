package com.tcmedicine.controller;

import com.tcmedicine.dto.PredictResponse;
import com.tcmedicine.entity.HerbalMedicine;
import com.tcmedicine.entity.RecognitionHistory;
import com.tcmedicine.service.HerbalMedicineService;
import com.tcmedicine.service.UserDashboardService;
import com.tcmedicine.service.OSSService;
import com.tcmedicine.utils.JwtUtil;
import com.tcmedicine.dao.UserMapper;
import com.tcmedicine.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 图像识别控制器 - 中药识别功能
 */
@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class PredictController {

    private static final Logger logger = LoggerFactory.getLogger(PredictController.class);

    @Autowired
    private HerbalMedicineService herbalMedicineService;

    @Autowired
    private UserDashboardService userDashboardService;

    @Autowired
    private OSSService ossService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    /**
     * 中药图像识别接口
     * @param file 上传的图像文件
     * @return 识别结果
     */
    @PostMapping("/predict")
    public PredictResponse predict(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        logger.info("=== 开始识别请求 ===");
        
        try {
            // 验证文件
            if (file == null || file.isEmpty()) {
                logger.warn("文件验证失败：文件为空");
                return PredictResponse.error("请上传图像文件");
            }

            // 验证文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                logger.warn("文件类型验证失败：{}", contentType);
                return PredictResponse.error("请上传有效的图像文件（JPG、PNG等格式）");
            }

            // 验证文件大小（限制10MB）
            if (file.getSize() > 10 * 1024 * 1024) {
                logger.warn("文件大小验证失败：{} bytes", file.getSize());
                return PredictResponse.error("文件大小不能超过10MB");
            }

            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                logger.warn("文件名验证失败");
                return PredictResponse.error("文件名无效");
            }

            logger.info("文件验证通过：{}, 大小：{} bytes, 类型：{}", originalFilename, file.getSize(), contentType);

            // 检查用户登录状态
            Long userId = getUserIdFromToken(request);
            logger.info("用户登录检查：userId = {}", userId);

            // 上传图片到OSS
            String imageUrl = null;
            try {
                logger.info("开始上传图片到OSS...");
                imageUrl = ossService.uploadFile(file, "recognition", originalFilename);
                logger.info("图片上传成功：{}", imageUrl);
            } catch (Exception e) {
                logger.error("图片上传失败: {}", e.getMessage(), e);
                // 上传失败时继续识别，但不保存记录
            }

            // 调用识别服务
            logger.info("开始调用识别服务...");
            String predictedPinyinName = herbalMedicineService.predictFromImage(originalFilename);
            logger.info("识别服务返回结果：{}", predictedPinyinName);
            
            // 根据识别结果查询数据库
            logger.info("开始查询药材数据库...");
            HerbalMedicine herbalMedicine = herbalMedicineService.findByPinyinName(predictedPinyinName);
            
            if (herbalMedicine == null) {
                logger.warn("数据库中未找到对应药材：{}", predictedPinyinName);
                return PredictResponse.error("未识别到有效的中药信息");
            }

            logger.info("找到药材信息：ID={}, 名称={}", herbalMedicine.getId(), herbalMedicine.getResult());

            // 如果用户已登录且图片上传成功，保存识别历史
            if (userId != null && imageUrl != null) {
                try {
                    logger.info("开始保存识别历史记录...");
                    
                    // 保存识别历史记录
                    RecognitionHistory history = new RecognitionHistory();
                    history.setUserId(userId);
                    history.setImageUrl(imageUrl);
                    history.setResultName(herbalMedicine.getResult());
                    history.setResultMedicineId(herbalMedicine.getId());
                    history.setConfidence(BigDecimal.valueOf(0.95)); // 默认置信度
                    
                    logger.info("识别历史对象创建完成：userId={}, imageUrl={}, resultName={}, medicineId={}", 
                        history.getUserId(), history.getImageUrl(), history.getResultName(), history.getResultMedicineId());
                    
                    boolean saveResult = userDashboardService.addRecognitionHistory(history);
                    
                    if (saveResult) {
                        logger.info("识别历史保存成功 - 用户ID: {}, 结果: {}", userId, herbalMedicine.getResult());
                    } else {
                        logger.error("识别历史保存失败 - 用户ID: {}, 结果: {}", userId, herbalMedicine.getResult());
                    }
                } catch (Exception e) {
                    logger.error("保存识别历史时发生异常 - 用户ID: {}, 错误: {}", userId, e.getMessage(), e);
                    // 保存失败不影响识别功能
                }
            } else if (userId != null) {
                logger.info("用户已登录但图片上传失败，跳过识别历史保存");
            } else {
                logger.info("用户未登录，跳过识别历史保存");
            }

            // 返回识别结果
            logger.info("识别完成，返回结果");
            return PredictResponse.success(
                herbalMedicine.getResult(),
                herbalMedicine.getOrigin(),
                herbalMedicine.getDescription(),
                herbalMedicine.getEffects(),
                herbalMedicine.getDisease()
            );

        } catch (Exception e) {
            logger.error("识别过程中发生异常：{}", e.getMessage(), e);
            return PredictResponse.error("识别过程中发生错误：" + e.getMessage());
        }
    }

    /**
     * 从Token中获取用户ID（可选，不会抛出异常）
     */
    private Long getUserIdFromToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            logger.debug("Authorization头：{}", authHeader);
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                logger.debug("提取的token：{}", token.substring(0, Math.min(20, token.length())) + "...");
                
                String username = jwtUtil.getUsernameFromToken(token);
                logger.debug("从token解析的用户名：{}", username);
                
                if (username != null) {
                    User user = userMapper.findByUsername(username);
                    if (user != null) {
                        logger.debug("找到用户：ID={}, 用户名={}", user.getUserId(), user.getUserName());
                        return user.getUserId();
                    } else {
                        logger.warn("数据库中未找到用户：{}", username);
                    }
                } else {
                    logger.warn("token中无法解析出用户名");
                }
            } else {
                logger.debug("未提供Authorization头或格式不正确");
            }
        } catch (Exception e) {
            logger.warn("从token获取用户ID时发生异常：{}", e.getMessage());
        }
        return null;
    }

    /**
     * 健康检查接口
     */
    @GetMapping("/predict/health")
    public PredictResponse health() {
        return PredictResponse.success("系统运行正常", "", "", "", "");
    }
} 