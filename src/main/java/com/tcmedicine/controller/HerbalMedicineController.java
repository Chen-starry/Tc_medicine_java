package com.tcmedicine.controller;

import com.tcmedicine.entity.HerbalMedicine;
import com.tcmedicine.service.HerbalMedicineService;
import com.tcmedicine.dto.ApiResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 中草药控制器
 */
@RestController
@RequestMapping("/api/herbal-medicines")
@CrossOrigin(originPatterns = "*")
public class HerbalMedicineController {

    @Autowired
    private HerbalMedicineService herbalMedicineService;

    /**
     * 获取中草药列表（分页）
     */
    @GetMapping
    public ApiResponse<Page<HerbalMedicine>> getHerbalMedicineList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        
        Page<HerbalMedicine> result = herbalMedicineService.getHerbalMedicineList(page, size, keyword);
        return ApiResponse.success("获取中草药列表成功", result);
    }

    /**
     * 根据ID获取中草药详情
     */
    @GetMapping("/{id}")
    public ApiResponse<HerbalMedicine> getHerbalMedicineById(@PathVariable Integer id) {
        HerbalMedicine herbalMedicine = herbalMedicineService.getHerbalMedicineById(id);
        if (herbalMedicine != null) {
            return ApiResponse.success("获取中草药详情成功", herbalMedicine);
        } else {
            return ApiResponse.error("中草药不存在");
        }
    }

    /**
     * 根据拼音名称搜索中草药
     */
    @GetMapping("/search")
    public ApiResponse<Page<HerbalMedicine>> searchByPinyinName(
            @RequestParam String pinyinName,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<HerbalMedicine> result = herbalMedicineService.searchByPinyinName(pinyinName, page, size);
        return ApiResponse.success("搜索中草药成功", result);
    }

}
