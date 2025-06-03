-- 用户后台功能数据库表扩展脚本

-- 1. 扩展用户表，添加头像和简介字段
ALTER TABLE usr_user 
ADD COLUMN avatar_url VARCHAR(500) COMMENT '用户头像URL',
ADD COLUMN profile_description TEXT COMMENT '个人简介';

-- 2. 创建用户收藏表
CREATE TABLE user_favorites (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    medicine_id INT NOT NULL COMMENT '药材ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    INDEX idx_user_id (user_id),
    INDEX idx_medicine_id (medicine_id),
    UNIQUE KEY uk_user_medicine (user_id, medicine_id) COMMENT '用户+药材唯一约束'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收藏表';

-- 3. 创建识别历史表
CREATE TABLE recognition_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    image_url VARCHAR(500) NOT NULL COMMENT '识别图片URL',
    result_medicine_id INT COMMENT '识别结果药材ID',
    result_name VARCHAR(100) COMMENT '识别结果名称',
    confidence DECIMAL(5,2) COMMENT '识别置信度',
    recognition_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '识别时间',
    INDEX idx_user_id (user_id),
    INDEX idx_recognition_time (recognition_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='识别历史表';

-- 4. 创建健康档案表
CREATE TABLE health_profile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    age INT COMMENT '年龄',
    gender TINYINT COMMENT '性别：0-女，1-男',
    height DECIMAL(5,2) COMMENT '身高(cm)',
    weight DECIMAL(5,2) COMMENT '体重(kg)',
    blood_type VARCHAR(10) COMMENT '血型',
    allergies TEXT COMMENT '过敏史',
    chronic_diseases TEXT COMMENT '慢性疾病',
    current_medications TEXT COMMENT '当前用药',
    health_notes TEXT COMMENT '健康备注',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康档案表';

-- ==================== 问诊记录表 ====================
-- 注释：已删除问诊记录功能，此表定义仅作参考保留
-- CREATE TABLE consultation_history (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
--     user_id BIGINT NOT NULL COMMENT '用户ID',
--     question TEXT COMMENT '问题描述',
--     answer TEXT COMMENT '回答',
--     consultation_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '问诊时间',
--     symptoms TEXT COMMENT '症状描述',
--     suggestions TEXT COMMENT '建议',
--     INDEX idx_consultation_time (consultation_time)
-- ) COMMENT='问诊记录表';

-- 外键约束（暂时注释掉，等数据稳定后再加）
-- ALTER TABLE user_favorites ADD CONSTRAINT fk_favorite_user FOREIGN KEY (user_id) REFERENCES usr_user(id);
-- ALTER TABLE recognition_history ADD CONSTRAINT fk_recognition_user FOREIGN KEY (user_id) REFERENCES usr_user(id);
-- ALTER TABLE health_profile ADD CONSTRAINT fk_health_user FOREIGN KEY (user_id) REFERENCES usr_user(id);
-- ALTER TABLE consultation_history ADD CONSTRAINT fk_consultation_user FOREIGN KEY (user_id) REFERENCES usr_user(id); 