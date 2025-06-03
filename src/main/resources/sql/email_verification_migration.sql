-- 邮箱验证功能数据库迁移脚本（优化版 - 无需验证码表）
-- 执行时间：2024-06-02

-- 1. 为用户表添加邮箱字段
ALTER TABLE usr_user 
ADD COLUMN email VARCHAR(100) DEFAULT NULL COMMENT '邮箱地址' AFTER user_name,
ADD COLUMN email_verified TINYINT(1) DEFAULT 0 COMMENT '邮箱是否已验证：0-未验证，1-已验证' AFTER email;

-- 2. 为邮箱字段添加唯一索引（可选，如果要求邮箱唯一）
-- ALTER TABLE usr_user ADD UNIQUE INDEX uk_email (email);

-- 验证执行结果
SELECT 'Migration completed successfully - Email fields added to usr_user table' as status;

-- 说明：验证码采用内存缓存存储，无需创建数据库表 