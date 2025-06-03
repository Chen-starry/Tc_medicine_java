-- 中医药材识别系统数据库初始化脚本
-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS tc_medicine CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE tc_medicine;

-- 用户表
CREATE TABLE IF NOT EXISTS usr_user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码（MD5加密）',
    user_type INT DEFAULT 0 COMMENT '用户类型：0-普通用户，1-管理员',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 药材分类表
CREATE TABLE IF NOT EXISTS medicine_categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称',
    description TEXT COMMENT '分类描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='药材分类表';

-- 药材信息表
CREATE TABLE IF NOT EXISTS medicines (
    medicine_id INT AUTO_INCREMENT PRIMARY KEY,
    medicine_name VARCHAR(100) NOT NULL COMMENT '药材名称',
    category_id INT COMMENT '分类ID',
    effects TEXT COMMENT '功效',
    usage_method TEXT COMMENT '用法用量',
    contraindications TEXT COMMENT '禁忌',
    image_path VARCHAR(500) COMMENT '图片路径',
    description TEXT COMMENT '详细描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (category_id) REFERENCES medicine_categories(category_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='药材信息表';

-- 用户反馈表
CREATE TABLE IF NOT EXISTS feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT COMMENT '用户ID',
    medicine_id INT COMMENT '药材ID',
    feedback_type VARCHAR(50) COMMENT '反馈类型',
    content TEXT COMMENT '反馈内容',
    rating INT DEFAULT 0 COMMENT '评分（1-5）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (user_id) REFERENCES usr_user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (medicine_id) REFERENCES medicines(medicine_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户反馈表';

-- 插入默认管理员用户
INSERT IGNORE INTO usr_user (user_name, password, user_type) VALUES 
('admin', MD5('admin123'), 1),
('test', MD5('test123'), 0);

-- 插入药材分类数据
INSERT IGNORE INTO medicine_categories (category_id, category_name, description) VALUES 
(1, '补益类', '具有补益人体气血阴阳不足的药材'),
(2, '清热类', '具有清热泻火、凉血解毒等功效的药材'),
(3, '解表类', '具有发汗解表、疏散风邪功效的药材'),
(4, '理气类', '具有疏理气机、调畅气血功效的药材'),
(5, '活血化瘀类', '具有活血化瘀、通经止痛功效的药材'),
(6, '止咳化痰类', '具有止咳、化痰、平喘功效的药材'),
(7, '消食类', '具有消食化积、健脾开胃功效的药材'),
(8, '安神类', '具有安神定志、镇静安眠功效的药材');

-- 插入示例药材数据
INSERT IGNORE INTO medicines (medicine_name, category_id, effects, usage_method, contraindications, image_path, description) VALUES 
('人参', 1, '大补元气，复脉固脱，补脾益肺，生津安神', '煎汤，3-9g；研末，1-3g；或入丸散', '实热证、湿热证及正虚邪实证忌服', '/images/renshen.jpg', '人参为五加科植物人参的干燥根和根茎。具有大补元气，复脉固脱，补脾益肺，生津安神的功效。'),

('黄芪', 1, '补气升阳，固表止汗，利水消肿，生津养血', '煎汤，9-30g；或入丸散', '表实邪盛，气滞湿阻，食积停滞，痈疽初起或溃后热毒尚盛等实证忌服', '/images/huangqi.jpg', '黄芪为豆科植物蒙古黄芪或膜荚黄芪的干燥根。具有补气升阳，固表止汗，利水消肿，生津养血的功效。'),

('当归', 5, '补血活血，调经止痛，润肠通便', '煎汤，6-12g；或入丸散', '湿盛中满及大便溏泄者慎服', '/images/danggui.jpg', '当归为伞形科植物当归的干燥根。具有补血活血，调经止痛，润肠通便的功效。'),

('川芎', 5, '活血行气，祛风止痛', '煎汤，3-10g；或入丸散', '阴虚火旺，上盛下虚及气弱之人忌服', '/images/chuanxiong.jpg', '川芎为伞形科植物川芎的干燥根茎。具有活血行气，祛风止痛的功效。'),

('金银花', 2, '清热解毒，疏散风热', '煎汤，6-15g；或入丸散', '脾胃虚寒及气虚疮疡脓清者忌服', '/images/jinyinhua.jpg', '金银花为忍冬科植物忍冬的干燥花蕾或带初开的花。具有清热解毒，疏散风热的功效。'),

('连翘', 2, '清热解毒，消肿散结，疏散风热', '煎汤，6-15g；或入丸散', '脾胃虚弱，气虚发热，痈疽已溃、脓稀者忌服', '/images/lianqiao.jpg', '连翘为木犀科植物连翘的干燥果实。具有清热解毒，消肿散结，疏散风热的功效。'),

('麻黄', 3, '发汗散寒，宣肺平喘，利水消肿', '煎汤，2-10g；或入丸散', '体虚自汗、盗汗、虚喘及阴虚阳亢者忌服', '/images/mahuang.jpg', '麻黄为麻黄科植物草麻黄、中麻黄或木贼麻黄的干燥草质茎。具有发汗散寒，宣肺平喘，利水消肿的功效。'),

('桂枝', 3, '发汗解肌，温通经脉，助阳化气', '煎汤，3-10g；或入丸散', '温热病及阴虚火旺者忌服', '/images/guizhi.jpg', '桂枝为樟科植物肉桂的干燥嫩枝。具有发汗解肌，温通经脉，助阳化气的功效。'),

('陈皮', 4, '理气健脾，燥湿化痰', '煎汤，3-10g；或入丸散', '气虚体燥、阴虚燥咳、吐血及内有实热者慎服', '/images/chenpi.jpg', '陈皮为芸香科植物橘及其栽培变种的干燥成熟果皮。具有理气健脾，燥湿化痰的功效。'),

('枳壳', 4, '理气宽中，行滞消胀', '煎汤，3-10g；或入丸散', '孕妇及气虚下陷者忌服', '/images/zhike.jpg', '枳壳为芸香科植物酸橙及其栽培变种的干燥未成熟果实。具有理气宽中，行滞消胀的功效。');

-- 插入示例反馈数据
INSERT IGNORE INTO feedback (user_id, medicine_id, feedback_type, content, rating) VALUES 
(2, 1, '使用效果', '人参确实有很好的补气效果，服用后精神状态明显改善', 5),
(2, 2, '使用效果', '黄芪泡水喝，对提高免疫力很有帮助', 4),
(2, 3, '使用效果', '当归对调理月经很有效果', 5),
(2, 5, '使用效果', '金银花茶清热解毒效果不错', 4);

-- 创建索引以提高查询性能
CREATE INDEX idx_medicine_name ON medicines(medicine_name);
CREATE INDEX idx_medicine_category ON medicines(category_id);
CREATE INDEX idx_user_name ON usr_user(user_name);
CREATE INDEX idx_feedback_user ON feedback(user_id);
CREATE INDEX idx_feedback_medicine ON feedback(medicine_id);

-- 显示创建的表
SHOW TABLES; 