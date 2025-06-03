package com.tcmedicine.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * 阿里云OSS文件上传服务
 */
@Service
public class OSSService {

    private static final Logger logger = LoggerFactory.getLogger(OSSService.class);

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    // 本地文件存储路径
    private static final String LOCAL_UPLOAD_PATH = "uploads";

    /**
     * 上传头像文件
     * @param file 上传的文件
     * @param userId 用户ID
     * @return 文件访问URL
     */
    public String uploadAvatar(MultipartFile file, Long userId) throws IOException {
        return uploadFile(file, "avatars", "avatar_" + userId);
    }

    /**
     * 上传药材图片
     * @param file 上传的文件
     * @param medicineId 药材ID
     * @return 文件访问URL
     */
    public String uploadMedicineImage(MultipartFile file, Integer medicineId) throws IOException {
        return uploadFile(file, "medicines", "medicine_" + medicineId);
    }

    /**
     * 通用文件上传方法
     * @param file 上传的文件
     * @param folder 文件夹名称
     * @param prefix 文件名前缀
     * @return 文件访问URL
     */
    public String uploadFile(MultipartFile file, String folder, String prefix) throws IOException {
        // 验证文件
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        // 验证文件类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("只支持图片文件格式");
        }

        // 验证文件大小（限制10MB）
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("文件大小不能超过10MB");
        }

        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName = folder + "/" + prefix + "_" + UUID.randomUUID().toString() + extension;

        // 首先尝试OSS上传
        try {
            return uploadToOSS(file, fileName, contentType);
        } catch (Exception e) {
            logger.warn("OSS上传失败，切换到本地存储: {}", e.getMessage());
            // OSS上传失败，使用本地存储
            return uploadToLocal(file, fileName);
        }
    }

    /**
     * 上传到阿里云OSS
     */
    private String uploadToOSS(MultipartFile file, String fileName, String contentType) throws Exception {
        // 检查OSS配置
        if (accessKeyId == null || accessKeyId.isEmpty() || 
            accessKeySecret == null || accessKeySecret.isEmpty() ||
            bucketName == null || bucketName.isEmpty()) {
            throw new IllegalStateException("OSS配置不完整");
        }

        // 创建OSS客户端
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 上传文件
            InputStream inputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);
            
            // 设置文件访问权限为公共读
            putObjectRequest.getMetadata().setContentType(contentType);
            
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            
            // 返回文件访问URL
            String url = "https://" + bucketName + "." + endpoint.replace("https://", "") + "/" + fileName;
            logger.info("OSS上传成功: {}", url);
            return url;
            
        } finally {
            // 关闭OSS客户端
            ossClient.shutdown();
        }
    }

    /**
     * 上传到本地文件系统
     */
    private String uploadToLocal(MultipartFile file, String fileName) throws IOException {
        // 创建上传目录
        Path uploadPath = Paths.get(LOCAL_UPLOAD_PATH);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 创建文件夹
        Path folderPath = uploadPath.resolve(fileName).getParent();
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        // 保存文件
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 返回访问URL（相对路径）
        String url = "/uploads/" + fileName;
        logger.info("本地上传成功: {}", url);
        return url;
    }

    /**
     * 删除文件
     * @param fileUrl 文件URL
     */
    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return;
        }

        try {
            if (fileUrl.startsWith("/uploads/")) {
                // 删除本地文件
                deleteLocalFile(fileUrl);
            } else {
                // 删除OSS文件
                deleteOSSFile(fileUrl);
            }
        } catch (Exception e) {
            logger.error("删除文件失败: {}", e.getMessage());
        }
    }

    /**
     * 删除本地文件
     */
    private void deleteLocalFile(String fileUrl) {
        try {
            String fileName = fileUrl.substring("/uploads/".length());
            Path filePath = Paths.get(LOCAL_UPLOAD_PATH, fileName);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                logger.info("本地文件删除成功: {}", fileUrl);
            }
        } catch (Exception e) {
            logger.error("删除本地文件失败: {}", e.getMessage());
        }
    }

    /**
     * 删除OSS文件
     */
    private void deleteOSSFile(String fileUrl) {
        try {
            // 从URL中提取文件名
            String fileName = extractFileNameFromUrl(fileUrl);
            if (fileName == null) {
                return;
            }

            // 创建OSS客户端
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            try {
                // 删除文件
                ossClient.deleteObject(bucketName, fileName);
                logger.info("OSS文件删除成功: {}", fileUrl);
            } finally {
                // 关闭OSS客户端
                ossClient.shutdown();
            }
        } catch (Exception e) {
            logger.error("删除OSS文件失败: {}", e.getMessage());
        }
    }

    /**
     * 从URL中提取文件名
     * @param fileUrl 文件URL
     * @return 文件名
     */
    private String extractFileNameFromUrl(String fileUrl) {
        try {
            String domainPrefix = "https://" + bucketName + "." + endpoint.replace("https://", "") + "/";
            if (fileUrl.startsWith(domainPrefix)) {
                return fileUrl.substring(domainPrefix.length());
            }
        } catch (Exception e) {
            logger.error("解析文件URL失败: {}", e.getMessage());
        }
        return null;
    }
} 