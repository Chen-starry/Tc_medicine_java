package com.tcmedicine.service.impl;

import com.tcmedicine.entity.Medicine;
import com.tcmedicine.entity.MedicineCategory;
import com.tcmedicine.mapper.MedicineMapper;
import com.tcmedicine.dao.MedicineCategoryMapper;
import com.tcmedicine.service.MedicineService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

/**
 * 药材服务实现类
 */
@Service
public class MedicineServiceImpl implements MedicineService {
    
    @Autowired
    private MedicineMapper medicineMapper;
    
    @Autowired
    private MedicineCategoryMapper medicineCategoryMapper;
    
    @Override
    public List<Medicine> getAllMedicines() {
        try {
            return medicineMapper.getAllMedicines();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询药材数据失败: " + e.getMessage());
        }
    }
    
    @Override
    public List<Medicine> getMedicinesByCategory(Integer categoryId) {
        try {
            return medicineMapper.getMedicinesByCategory(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("根据分类查询药材失败: " + e.getMessage());
        }
    }
    
    @Override
    public List<Medicine> searchMedicinesByName(String name) {
        try {
            return medicineMapper.searchMedicinesByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("搜索药材失败: " + e.getMessage());
        }
    }

    // === 以下方法提供基础实现，待后续完善 ===
    
    @Override
    public Page<Medicine> getMedicineList(int page, int size, String keyword, Integer categoryId) {
        try {
            Page<Medicine> medicinesPage = new Page<>(page, size);
            QueryWrapper<Medicine> queryWrapper = new QueryWrapper<>();
            
            if (keyword != null && !keyword.trim().isEmpty()) {
                queryWrapper.like("medicine_name", keyword)
                           .or()
                           .like("effects", keyword);
            }
            
            if (categoryId != null) {
                queryWrapper.eq("category_id", categoryId);
            }
            
            return medicineMapper.selectPage(medicinesPage, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("分页查询药材失败: " + e.getMessage());
        }
    }

    @Override
    public Medicine getMedicineById(Integer id) {
        try {
            return medicineMapper.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("根据ID查询药材失败: " + e.getMessage());
        }
    }

    @Override
    public Medicine createMedicine(Medicine medicine) {
        try {
            medicineMapper.insert(medicine);
            return medicine;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("创建药材失败: " + e.getMessage());
        }
    }

    @Override
    public Medicine updateMedicine(Medicine medicine) {
        try {
            medicineMapper.updateById(medicine);
            return medicine;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("更新药材失败: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteMedicine(Integer id) {
        try {
            int result = medicineMapper.deleteById(id);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除药材失败: " + e.getMessage());
        }
    }

    @Override
    public List<MedicineCategory> getAllCategories() {
        try {
            return medicineCategoryMapper.getAllMedicineCategories();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取药材分类失败: " + e.getMessage());
        }
    }

    @Override
    public Medicine predictMedicineByImage(String imageUrl) {
        // TODO: 待实现图片识别功能后完善
        throw new RuntimeException("图片识别功能尚未实现");
    }
} 