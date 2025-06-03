package com.tcmedicine.controller;

import com.tcmedicine.dto.AdminUserDto;
import com.tcmedicine.dto.ApiResponse;
import com.tcmedicine.entity.User;
import com.tcmedicine.entity.Medicine;
import com.tcmedicine.entity.MedicineCategory;
import com.tcmedicine.entity.Feedback;
import com.tcmedicine.entity.Category;
import com.tcmedicine.entity.Syndrome;
import com.tcmedicine.service.UserService;
import com.tcmedicine.service.MedicineService;
import com.tcmedicine.service.FeedbackService;
import com.tcmedicine.service.CategoryService;
import com.tcmedicine.service.SyndromeService;
import com.tcmedicine.service.OSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private MedicineService medicineService;
    
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SyndromeService syndromeService;

    @Autowired
    private OSSService ossService;

    /**
     * 获取所有用户（管理员版本 - 字段格式化）
     */
    @GetMapping("/users")
    public ApiResponse<List<AdminUserDto>> getAllUsers() {
        try {
            List<AdminUserDto> users = userService.getAllUsersForAdmin();
            return ApiResponse.success(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取用户列表失败，请稍后重试");
        }
    }

    /**
     * 获取所有药材（管理员版本）
     */
    @GetMapping("/medicines")
    public ApiResponse<List<Medicine>> getAllMedicines() {
        try {
            List<Medicine> medicines = medicineService.getAllMedicines();
            return ApiResponse.success(medicines);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取药材列表失败，请稍后重试");
        }
    }

    /**
     * 获取所有疾病分类（使用真实数据）
     */
    @GetMapping("/disease-categories")
    public ApiResponse<List<Category>> getDiseaseCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ApiResponse.success(categories);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取疾病分类失败，请稍后重试");
        }
    }

    /**
     * 获取所有证候（疾病数据，使用真实数据）
     */
    @GetMapping("/diseases")
    public ApiResponse<List<Syndrome>> getAllDiseases() {
        try {
            List<Syndrome> syndromes = syndromeService.getAllSyndromes();
            return ApiResponse.success(syndromes);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取疾病列表失败，请稍后重试");
        }
    }

    /**
     * 获取所有反馈
     */
    @GetMapping("/feedbacks")
    public ApiResponse<List<Feedback>> getAllFeedbacks() {
        try {
            List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
            return ApiResponse.success("获取反馈列表成功", feedbacks);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取反馈列表失败，请稍后重试");
        }
    }

    /**
     * 根据ID获取反馈详情
     */
    @GetMapping("/feedbacks/{id}")
    public ApiResponse<Feedback> getFeedbackById(@PathVariable Integer id) {
        try {
            Feedback feedback = feedbackService.getFeedbackById(id);
            if (feedback != null) {
                return ApiResponse.success("获取反馈详情成功", feedback);
            } else {
                return ApiResponse.error("反馈不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取反馈详情失败，请稍后重试");
        }
    }

    /**
     * 更新反馈状态
     */
    @PutMapping("/feedbacks/{id}")
    public ApiResponse<String> updateFeedbackStatus(@PathVariable Integer id, @RequestBody Map<String, Object> request) {
        try {
            // 从请求体中获取状态值
            Object statusObj = request.get("status");
            if (statusObj == null) {
                return ApiResponse.error("状态参数不能为空");
            }
            
            Integer status;
            if (statusObj instanceof String) {
                status = Integer.parseInt((String) statusObj);
            } else if (statusObj instanceof Integer) {
                status = (Integer) statusObj;
            } else {
                return ApiResponse.error("状态参数格式错误");
            }
            
            // 验证状态值的有效性
            if (status != 0 && status != 1) {
                return ApiResponse.error("状态值必须为0（未处理）或1（已处理）");
            }
            
            boolean success = feedbackService.updateFeedbackStatus(id, status);
            if (success) {
                return ApiResponse.success("反馈状态更新成功");
            } else {
                return ApiResponse.error("反馈状态更新失败");
            }
        } catch (NumberFormatException e) {
            return ApiResponse.error("状态参数必须是数字");
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("更新反馈状态失败，请稍后重试");
        }
    }

    /**
     * 获取系统统计信息
     */
    @GetMapping("/stats")
    public ApiResponse<Map<String, Object>> getSystemStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // 用户统计
            List<User> users = userService.getAllUsers();
            stats.put("totalUsers", users.size());
            
            // 药材统计
            List<Medicine> medicines = medicineService.getAllMedicines();
            stats.put("totalMedicines", medicines.size());
            
            // 反馈统计（从数据库获取真实数据）
            Long totalFeedbacks = feedbackService.getTotalFeedbackCount();
            stats.put("totalFeedbacks", totalFeedbacks != null ? totalFeedbacks : 0);
            
            // 药材分类统计
            List<MedicineCategory> medicineCategories = medicineService.getAllCategories();
            stats.put("totalCategories", medicineCategories.size());
            
            // 疾病分类统计（使用真实数据）
            List<Category> diseaseCategories = categoryService.getAllCategories();
            stats.put("totalDiseaseCategories", diseaseCategories.size());
            
            // 证候统计（使用真实数据）
            List<Syndrome> syndromes = syndromeService.getAllSyndromes();
            stats.put("totalDiseases", syndromes.size());
            
            // 其他统计信息
            stats.put("monthlyActiveUsers", 128);
            
            return ApiResponse.success("获取统计信息成功", stats);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取统计信息失败，请稍后重试");
        }
    }

    /**
     * 获取药材分类
     */
    @GetMapping("/medicine-categories")
    public ApiResponse<List<MedicineCategory>> getMedicineCategories() {
        try {
            List<MedicineCategory> categories = medicineService.getAllCategories();
            return ApiResponse.success("获取药材分类成功", categories);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取药材分类失败，请稍后重试");
        }
    }

    // ================== 药材管理接口 ==================

    /**
     * 根据ID获取单个药材详情
     */
    @GetMapping("/medicines/{id}")
    public ApiResponse<Medicine> getMedicineById(@PathVariable Integer id) {
        try {
            Medicine medicine = medicineService.getMedicineById(id);
            if (medicine != null) {
                return ApiResponse.success("获取药材详情成功", medicine);
            } else {
                return ApiResponse.error("药材不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取药材详情失败，请稍后重试");
        }
    }

    /**
     * 更新药材信息
     */
    @PutMapping("/medicines/{id}")
    public ApiResponse<String> updateMedicine(@PathVariable Integer id, 
            @RequestParam("name") String name,
            @RequestParam("category_id") Integer categoryId,
            @RequestParam("source") String source,
            @RequestParam("properties") String properties,
            @RequestParam("taste") String taste,
            @RequestParam("channels") String channels,
            @RequestParam("effects") String effects,
            @RequestParam("contraindications") String contraindications,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Medicine medicine = new Medicine();
            medicine.setMedicineId(id);
            medicine.setMedicineName(name);
            medicine.setCategoryId(categoryId);
            medicine.setSource(source);
            medicine.setProperties(properties);
            medicine.setTaste(taste);
            medicine.setChannels(channels);
            medicine.setEffects(effects);
            medicine.setContraindications(contraindications);
            
            // 如果有新图片上传，处理图片上传逻辑
            if (image != null && !image.isEmpty()) {
                try {
                    // 获取旧药材信息，用于删除旧图片
                    Medicine oldMedicine = medicineService.getMedicineById(id);
                    if (oldMedicine != null && oldMedicine.getImagePath() != null && !oldMedicine.getImagePath().isEmpty()) {
                        // 删除旧图片
                        ossService.deleteFile(oldMedicine.getImagePath());
                    }
                    
                    // 上传新图片
                    String imageUrl = ossService.uploadMedicineImage(image, id);
                    medicine.setImagePath(imageUrl);
                    logger.info("药材图片上传成功，ID: {}, URL: {}", id, imageUrl);
                } catch (Exception e) {
                    logger.error("药材图片上传失败，ID: {}, 错误: {}", id, e.getMessage(), e);
                    return ApiResponse.error("图片上传失败: " + e.getMessage());
                }
            }
            
            Medicine updated = medicineService.updateMedicine(medicine);
            if (updated != null) {
                logger.info("药材更新成功，ID: {}", id);
                return ApiResponse.success("更新药材成功");
            } else {
                return ApiResponse.error("更新药材失败");
            }
        } catch (Exception e) {
            logger.error("更新药材失败，ID: {}, 错误: {}", id, e.getMessage(), e);
            return ApiResponse.error("更新药材失败: " + e.getMessage());
        }
    }

    /**
     * 新增药材
     */
    @PostMapping("/medicines")
    public ApiResponse<String> createMedicine(@RequestParam("name") String name,
            @RequestParam("category_id") Integer categoryId,
            @RequestParam("source") String source,
            @RequestParam("properties") String properties,
            @RequestParam("taste") String taste,
            @RequestParam("channels") String channels,
            @RequestParam("effects") String effects,
            @RequestParam("contraindications") String contraindications,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Medicine medicine = new Medicine();
            medicine.setMedicineName(name);
            medicine.setCategoryId(categoryId);
            medicine.setSource(source);
            medicine.setProperties(properties);
            medicine.setTaste(taste);
            medicine.setChannels(channels);
            medicine.setEffects(effects);
            medicine.setContraindications(contraindications);
            
            // 先创建药材获取ID
            Medicine created = medicineService.createMedicine(medicine);
            if (created == null) {
                return ApiResponse.error("创建药材失败");
            }
            logger.info("药材创建成功，ID: {}, 名称: {}", created.getMedicineId(), name);
            
            // 如果有图片上传，处理图片上传逻辑
            if (image != null && !image.isEmpty()) {
                try {
                    // 使用创建后的药材ID上传图片
                    String imageUrl = ossService.uploadMedicineImage(image, created.getMedicineId());
                    created.setImagePath(imageUrl);
                    
                    // 更新药材的图片路径
                    medicineService.updateMedicine(created);
                    logger.info("药材图片上传成功，ID: {}, URL: {}", created.getMedicineId(), imageUrl);
                } catch (Exception e) {
                    logger.error("药材图片上传失败，ID: {}, 错误: {}", created.getMedicineId(), e.getMessage(), e);
                    return ApiResponse.error("药材创建成功，但图片上传失败: " + e.getMessage());
                }
            }
            
            return ApiResponse.success("创建药材成功");
        } catch (Exception e) {
            logger.error("创建药材失败，名称: {}, 错误: {}", name, e.getMessage(), e);
            return ApiResponse.error("创建药材失败: " + e.getMessage());
        }
    }

    /**
     * 删除药材
     */
    @DeleteMapping("/medicines/{id}")
    public ApiResponse<String> deleteMedicine(@PathVariable Integer id) {
        try {
            // 先获取药材信息用于删除图片
            Medicine medicine = medicineService.getMedicineById(id);
            if (medicine == null) {
                return ApiResponse.error("药材不存在");
            }
            
            // 删除药材记录
            boolean success = medicineService.deleteMedicine(id);
            if (success) {
                // 删除成功后，尝试删除关联的图片文件
                if (medicine.getImagePath() != null && !medicine.getImagePath().isEmpty()) {
                    try {
                        ossService.deleteFile(medicine.getImagePath());
                        logger.info("药材图片删除成功，ID: {}, URL: {}", id, medicine.getImagePath());
                    } catch (Exception e) {
                        logger.warn("药材图片删除失败，ID: {}, 错误: {}", id, e.getMessage());
                        // 图片删除失败不影响药材删除的成功状态
                    }
                }
                
                logger.info("药材删除成功，ID: {}, 名称: {}", id, medicine.getMedicineName());
                return ApiResponse.success("删除药材成功");
            } else {
                return ApiResponse.error("删除药材失败");
            }
        } catch (Exception e) {
            logger.error("删除药材失败，ID: {}, 错误: {}", id, e.getMessage(), e);
            return ApiResponse.error("删除药材失败: " + e.getMessage());
        }
    }

    // ================== 用户管理接口 ==================

    /**
     * 根据ID获取单个用户详情
     */
    @GetMapping("/users/{id}")
    public ApiResponse<User> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                return ApiResponse.success("获取用户详情成功", user);
            } else {
                return ApiResponse.error("用户不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取用户详情失败，请稍后重试");
        }
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/users/{id}")
    public ApiResponse<String> updateUser(@PathVariable Integer id, @RequestBody User user) {
        try {
            user.setUserId(Long.valueOf(id));
            boolean success = userService.updateUser(user);
            if (success) {
                return ApiResponse.success("更新用户成功");
            } else {
                return ApiResponse.error("更新用户失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("更新用户失败: " + e.getMessage());
        }
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/users/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Integer id) {
        try {
            // 先检查用户是否存在
            User user = userService.getUserById(id);
            if (user == null) {
                return ApiResponse.error("用户不存在");
            }
            
            boolean success = userService.deleteUser(id);
            if (success) {
                logger.info("用户删除成功，ID: {}, 用户名: {}", id, user.getUserName());
                return ApiResponse.success("删除用户成功");
            } else {
                return ApiResponse.error("删除用户失败");
            }
        } catch (Exception e) {
            logger.error("删除用户失败，ID: {}, 错误: {}", id, e.getMessage(), e);
            return ApiResponse.error("删除用户失败: " + e.getMessage());
        }
    }

    // ================== 疾病管理接口 ==================

    /**
     * 根据ID获取单个疾病详情
     */
    @GetMapping("/diseases/{id}")
    public ApiResponse<Syndrome> getDiseaseById(@PathVariable Integer id) {
        try {
            Syndrome syndrome = syndromeService.getSyndromeById(id);
            if (syndrome != null) {
                return ApiResponse.success("获取疾病详情成功", syndrome);
            } else {
                return ApiResponse.error("疾病不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("获取疾病详情失败，请稍后重试");
        }
    }

    /**
     * 更新疾病信息
     */
    @PutMapping("/diseases/{id}")
    public ApiResponse<Syndrome> updateDisease(@PathVariable Integer id, @RequestBody Syndrome syndrome) {
        try {
            syndrome.setId(id);
            Syndrome updated = syndromeService.updateSyndrome(syndrome);
            return ApiResponse.success("更新疾病成功", updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("更新疾病失败: " + e.getMessage());
        }
    }

    /**
     * 新增疾病
     */
    @PostMapping("/diseases")
    public ApiResponse<Syndrome> createDisease(@RequestBody Syndrome syndrome) {
        try {
            Syndrome created = syndromeService.createSyndrome(syndrome);
            return ApiResponse.success("创建疾病成功", created);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("创建疾病失败: " + e.getMessage());
        }
    }

    /**
     * 删除疾病
     */
    @DeleteMapping("/diseases/{id}")
    public ApiResponse<String> deleteDisease(@PathVariable Integer id) {
        try {
            // 先检查疾病是否存在
            Syndrome syndrome = syndromeService.getSyndromeById(id);
            if (syndrome == null) {
                return ApiResponse.error("疾病不存在");
            }
            
            boolean success = syndromeService.deleteSyndrome(id);
            if (success) {
                logger.info("疾病删除成功，ID: {}, 名称: {}", id, syndrome.getName());
                return ApiResponse.success("删除疾病成功");
            } else {
                return ApiResponse.error("删除疾病失败");
            }
        } catch (Exception e) {
            logger.error("删除疾病失败，ID: {}, 错误: {}", id, e.getMessage(), e);
            return ApiResponse.error("删除疾病失败: " + e.getMessage());
        }
    }
} 