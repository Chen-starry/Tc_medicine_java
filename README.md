# 杏林析微 - 智慧中医药科普平台

<div align="center">

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.16-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![Java](https://img.shields.io/badge/Java-8+-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

**"诸药所生，皆有境界 • 草木有灵，人间有情，相逢则境界生"**

[在线体验](http://localhost:8080) | [API文档](#api接口) | [部署指南](#部署说明)

</div>

## 📖 项目简介

杏林析微是一个现代化的中医药智慧科普平台，融合传统中医药文化与现代信息技术。平台以"数字解构经络智慧，传统文化润泽未来"为理念，为用户提供全方位的中医药学习、识别、问诊体验。

## 📝 最新更新

### 2025年6月更新
- ✅ **用户后台系统** - 完整的个人中心功能
  - 个人资料管理（用户名、简介、头像）
  - 收藏夹管理（药材收藏/取消收藏）
  - 识别历史记录（自动保存识别结果）
  - 健康档案管理（个人健康信息）
  - 密码修改（邮箱验证码验证）
- ✅ **邮箱验证系统** - 安全的密码修改流程
- ✅ **文件上传服务** - 支持阿里云OSS和本地存储
- ✅ **数据库优化** - 新增用户后台相关数据表
- ❌ **移除功能** - 删除问诊记录功能（根据用户需求）

### 🌟 核心特色

- **🔍 AI药材识别** - 基于深度学习的中药材图像识别
- **🤖 智能问诊** - AI中医问诊系统
- **📚 药食同源** - 传统药膳与现代营养的结合
- **🎨 中医六艺** - 针灸、推拿、拔罐等传统疗法展示
- **🌍 药材分布图** - 中国草药地理分布可视化
- **💡 中药+创新** - 中药与现代生活场景的创新融合

## 🛠️ 技术架构

### 后端技术栈
```
Spring Boot 2.7.16     # 核心框架
├── Spring Security     # 安全认证
├── JWT                # 令牌认证
├── MyBatis Plus       # ORM框架
├── Spring WebFlux     # 响应式编程
├── MySQL 8.0+         # 数据存储
├── Aliyun OSS         # 对象存储
└── Maven              # 项目管理
```

### 前端技术栈
```
现代化Web技术栈
├── HTML5/CSS3/ES6     # 基础技术
├── Responsive Design  # 响应式设计
├── Leaflet.js        # 地图可视化
├── 主题切换系统       # 浅色/深色/系统主题
└── 现代化UI/UX       # 优雅的用户体验
```

### 集成服务
- **Python AI服务** - 药材识别模型
- **阿里云OSS** - 图片存储服务
- **地理信息系统** - 药材分布数据

## 🚀 功能特性

### 🎯 核心功能

#### 🔐 用户系统
- JWT无状态认证
- 角色权限管理（普通用户/管理员）
- 用户注册/登录/登出
- 个人信息管理
- **用户后台功能** - 完整的个人中心管理

#### 🌿 药材百科
- **药材管理** - 完整的药材信息库（名称、来源、性味、归经、功效等）
- **分类体系** - 补益类、清热类、解表类等8大分类
- **搜索功能** - 支持药材名称、功效、归经等多维度搜索
- **详情展示** - 药材图片、用法用量、禁忌事项等详细信息
- **收藏功能** - 用户可收藏感兴趣的药材

#### 🤖 智能识别
- **图像识别** - 上传药材照片，AI智能识别
- **多格式支持** - 支持JPG、PNG、GIF等图片格式
- **识别结果** - 提供药材名称、相似度、详细信息
- **历史记录** - 自动保存用户识别历史（需登录）
- **图片存储** - 支持阿里云OSS和本地存储

#### 👤 用户后台系统
- **个人资料管理** - 用户名修改、个人简介编辑、头像上传
- **收藏夹管理** - 查看和管理收藏的药材
- **识别历史** - 查看历史识别记录和结果
- **健康档案** - 个人健康信息管理（年龄、性别、身高、体重、血型、过敏史等）
- **密码修改** - 邮箱验证码安全修改密码

#### 🏥 疾病百科
- **疾病信息** - 常见疾病的中医认识
- **症状对照** - 疾病症状与中医证候的对应
- **调理方案** - 中医药调理建议
- **预防保健** - 中医养生预防知识

#### 💬 AI问诊系统
- **智能对话** - 基于中医理论的AI问诊
- **症状分析** - 智能分析用户症状描述
- **建议生成** - 提供中医调理建议
- **安全提醒** - 强调专业医生诊断的重要性

### 🎨 特色展示

#### 🍲 药食同源
- **食疗卡片** - 枸杞明目茶、山药养胃粥等5种药膳
- **配方详情** - 详细的食材配比和制作方法
- **功效说明** - 每种药膳的中医功效解析
- **轮播展示** - 美观的卡片轮播效果

#### 🌍 中国草药分布图
- **地理可视化** - 基于Leaflet的交互式地图
- **省份信息** - 点击查看各省特色药材
- **分布数据** - 中药材的地理分布信息
- **响应式地图** - 支持缩放、拖拽等操作

#### 🎭 中医六艺展示
- **传统疗法** - 针灸、艾灸、推拿、拔罐、刮痧、中药
- **卡片堆叠** - 创意的卡片堆叠展示效果
- **交互体验** - 点击卡片查看详细信息
- **文化传承** - 展示中医传统技艺

#### 💡 "中药+"创新产品
- **中药奶茶** - 传统药材与现代饮品的结合
- **中药面包** - 健康烘焙的创新尝试
- **中药火锅** - 滋补与美食的完美融合
- **中药皂** - 天然护肤的新选择
- **中药香囊** - 传统香囊的现代应用
- **中药面膜** - 古法美容的现代演绎

### 🎨 用户体验

#### 🌈 主题系统
- **三种主题** - 浅色、深色、跟随系统
- **无缝切换** - 流畅的主题切换体验
- **本地存储** - 记住用户的主题偏好
- **智能适配** - 根据系统偏好自动切换

#### 📱 响应式设计
- **多设备适配** - 支持桌面端、平板、手机
- **流式布局** - 自适应不同屏幕尺寸
- **触控优化** - 针对移动设备的交互优化
- **性能优化** - 图片懒加载、资源压缩

#### 🔄 反馈系统
- **用户反馈** - 便捷的意见建议提交
- **反馈管理** - 管理员可查看和处理反馈
- **邮件通知** - 重要反馈的邮件提醒
- **用户体验** - 持续改进用户体验

## 🏗️ 项目结构

```
tc-medicine-java/
├── src/main/java/com/tcmedicine/
│   ├── TcMedicineApplication.java    # 主启动类
│   ├── controller/                   # 控制器层
│   │   ├── AuthController.java       # 用户认证
│   │   ├── MedicineController.java   # 药材管理
│   │   ├── PredictController.java    # AI识别
│   │   ├── ChatController.java       # AI问诊
│   │   ├── DiseaseController.java    # 疾病管理
│   │   ├── FeedbackController.java   # 反馈管理
│   │   ├── AdminController.java      # 管理员功能
│   │   ├── UserDashboardController.java # 用户后台功能
│   │   ├── PageController.java       # 页面路由
│   │   └── HealthController.java     # 健康检查
│   ├── service/                      # 服务层
│   │   ├── UserDashboardService.java # 用户后台服务
│   │   ├── OSSService.java          # 文件上传服务
│   │   ├── VerificationCodeService.java # 验证码服务
│   │   └── ...                      # 其他服务
│   ├── entity/                       # 实体类
│   │   ├── UserFavorite.java        # 用户收藏实体
│   │   ├── RecognitionHistory.java  # 识别历史实体
│   │   ├── HealthProfile.java       # 健康档案实体
│   │   └── ...                      # 其他实体
│   ├── mapper/                       # 数据访问层
│   │   ├── UserFavoriteMapper.java  # 收藏数据访问
│   │   ├── RecognitionHistoryMapper.java # 识别历史数据访问
│   │   ├── HealthProfileMapper.java # 健康档案数据访问
│   │   └── ...                      # 其他Mapper
│   ├── dto/                          # 数据传输对象
│   ├── config/                       # 配置类
│   │   ├── SecurityConfig.java      # Spring Security配置
│   │   ├── WebConfig.java           # Web配置
│   │   └── JwtAuthenticationFilter.java # JWT过滤器
│   ├── filter/                       # 过滤器
│   └── util/                         # 工具类
├── src/main/resources/
│   ├── static/                       # 静态资源
│   │   ├── index.html               # 主页
│   │   ├── login.html               # 登录页
│   │   ├── register.html            # 注册页
│   │   ├── medicine.html            # 药材页面
│   │   ├── diseases.html            # 疾病页面
│   │   ├── diagnosis.html           # 问诊页面
│   │   ├── predict.html             # 识别页面
│   │   ├── dashboard.html           # 管理后台
│   │   ├── user-dashboard.html      # 用户后台
│   │   └── style.css                # 样式文件
│   ├── sql/                         # SQL脚本
│   │   ├── init.sql                 # 初始化脚本
│   │   └── user_dashboard_tables.sql # 用户后台表结构
│   ├── mapper/                      # MyBatis映射文件
│   └── application.yml              # 配置文件
└── pom.xml                          # Maven配置
```

## 📊 数据库设计

### 核心数据表

#### 用户表 (usr_user)
```sql
CREATE TABLE `usr_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL UNIQUE,
  `email` varchar(100),
  `email_verified` tinyint DEFAULT '0' COMMENT '邮箱验证状态',
  `password` varchar(100) NOT NULL,
  `user_type` tinyint DEFAULT '0' COMMENT '0-普通用户, 1-管理员',
  `creat_time` timestamp DEFAULT CURRENT_TIMESTAMP,
  `avatar_url` varchar(255) COMMENT '头像URL',
  `profile_description` text COMMENT '个人简介',
  PRIMARY KEY (`id`)
);
```

#### 用户收藏表 (user_favorites)
```sql
CREATE TABLE `user_favorites` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `medicine_id` INT NOT NULL COMMENT '药材ID',
  `medicine_name` VARCHAR(100) COMMENT '药材名称',
  `favorite_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  INDEX idx_user_id (user_id),
  INDEX idx_medicine_id (medicine_id),
  INDEX idx_favorite_time (favorite_time)
) COMMENT='用户收藏表';
```

#### 识别历史表 (recognition_history)
```sql
CREATE TABLE `recognition_history` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `image_url` VARCHAR(500) COMMENT '识别图片URL',
  `result_medicine_id` INT COMMENT '识别结果药材ID',
  `result_name` VARCHAR(100) COMMENT '识别结果名称',
  `confidence` DECIMAL(5,2) COMMENT '置信度',
  `recognition_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '识别时间',
  INDEX idx_user_id (user_id),
  INDEX idx_recognition_time (recognition_time)
) COMMENT='识别历史表';
```

#### 健康档案表 (health_profile)
```sql
CREATE TABLE `health_profile` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `age` INT COMMENT '年龄',
  `gender` VARCHAR(10) COMMENT '性别',
  `height` DECIMAL(5,2) COMMENT '身高(cm)',
  `weight` DECIMAL(5,2) COMMENT '体重(kg)',
  `blood_type` VARCHAR(5) COMMENT '血型',
  `allergies` TEXT COMMENT '过敏史',
  `medical_history` TEXT COMMENT '病史',
  `current_medications` TEXT COMMENT '当前用药',
  `lifestyle_habits` TEXT COMMENT '生活习惯',
  `emergency_contact` VARCHAR(200) COMMENT '紧急联系人',
  `last_updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  UNIQUE KEY uk_user_id (user_id)
) COMMENT='用户健康档案表';
```

#### 药材表 (medicines)
```sql
CREATE TABLE `medicines` (
  `medicine_id` int NOT NULL AUTO_INCREMENT,
  `medicine_name` varchar(100) NOT NULL,
  `category_id` int DEFAULT NULL,
  `source` text COMMENT '来源',
  `properties` varchar(50) COMMENT '性味',
  `taste` varchar(50) COMMENT '味道',
  `channels` varchar(100) COMMENT '归经',
  `effects` text COMMENT '功效',
  `usage_method` text COMMENT '用法用量',
  `contraindications` text COMMENT '禁忌',
  `image_path` varchar(255) COMMENT '图片路径',
  PRIMARY KEY (`medicine_id`)
);
```

#### 疾病表 (diseases)
```sql
CREATE TABLE `diseases` (
  `disease_id` int NOT NULL AUTO_INCREMENT,
  `disease_name` varchar(100) NOT NULL,
  `symptoms` text COMMENT '症状',
  `tcm_pattern` varchar(200) COMMENT '中医证型',
  `treatment_principle` text COMMENT '治疗原则',
  `recommended_medicines` text COMMENT '推荐药材',
  PRIMARY KEY (`disease_id`)
);
```

#### 反馈表 (feedback)
```sql
CREATE TABLE `feedback` (
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `contact` varchar(100) DEFAULT NULL,
  `content` text NOT NULL,
  `feedback_time` timestamp DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint DEFAULT '0' COMMENT '0-未处理, 1-已处理',
  PRIMARY KEY (`feedback_id`)
);
```

## 🔗 API接口

### 认证接口
| 方法 | 路径 | 说明 | 参数 |
|------|------|------|------|
| POST | `/api/auth/login` | 用户登录 | username, password |
| POST | `/api/auth/register` | 用户注册 | username, password |
| POST | `/api/auth/logout` | 用户登出 | - |

### 药材接口
| 方法 | 路径 | 说明 | 参数 |
|------|------|------|------|
| GET | `/api/medicines` | 获取药材列表 | page, size, keyword |
| GET | `/api/medicines/{id}` | 获取药材详情 | id |
| GET | `/api/medicines/categories` | 获取分类列表 | - |
| GET | `/api/medicines/public/search` | 公开搜索药材 | keyword |

### AI功能接口
| 方法 | 路径 | 说明 | 参数 |
|------|------|------|------|
| POST | `/api/predict` | 药材图像识别 | file |
| POST | `/api/chat` | AI问诊对话 | message |

### 用户后台接口
| 方法 | 路径 | 说明 | 参数 |
|------|------|------|------|
| GET | `/api/user/dashboard/profile` | 获取用户资料 | - |
| PUT | `/api/user/dashboard/profile` | 更新用户资料 | userData |
| POST | `/api/user/dashboard/avatar` | 上传头像 | file |
| POST | `/api/user/change-password` | 修改密码 | oldPassword, newPassword, code |
| GET | `/api/user/favorites` | 获取收藏列表 | - |
| POST | `/api/user/favorites` | 添加收藏 | medicineId |
| DELETE | `/api/user/favorites/{id}` | 取消收藏 | id |
| GET | `/api/user/recognition-history` | 获取识别历史 | - |
| POST | `/api/user/recognition-history` | 添加识别记录 | historyData |
| GET | `/api/user/health-profile` | 获取健康档案 | - |
| PUT | `/api/user/health-profile` | 更新健康档案 | profileData |
| POST | `/api/user/send-verification` | 发送验证码 | email |

### 疾病接口
| 方法 | 路径 | 说明 | 参数 |
|------|------|------|------|
| GET | `/api/diseases` | 获取疾病列表 | page, size |
| GET | `/api/diseases/{id}` | 获取疾病详情 | id |

### 系统接口
| 方法 | 路径 | 说明 | 参数 |
|------|------|------|------|
| GET | `/api/health` | 健康检查 | - |
| POST | `/submit_feedback` | 提交反馈 | username, contact, content |

## 🚀 快速开始

### 环境要求
- ☕ Java 8+
- 🗄️ MySQL 8.0+
- 📦 Maven 3.6+
- 🐍 Python 3.8+（AI识别服务）

### 1. 数据库初始化
```bash
# 创建数据库
mysql -u root -p
CREATE DATABASE tc_medicine CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 执行初始化脚本
mysql -u root -p tc_medicine < src/main/resources/sql/init.sql
```

### 2. 配置文件修改
编辑 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/tc_medicine?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
  
  # 邮件配置（密码修改验证码功能）
  mail:
    host: smtp.163.com
    port: 587
    username: your_email@163.com
    password: your_email_password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

# 阿里云OSS配置（可选）
aliyun:
  oss:
    access-key-id: your_access_key
    access-key-secret: your_secret_key
    bucket-name: your_bucket
    endpoint: your_endpoint

# JWT配置
jwt:
  secret: your_jwt_secret_key
  expiration: 86400000  # 24小时

# 文件上传配置
file:
  upload:
    path: /uploads/  # 本地上传路径
    max-size: 10MB   # 最大文件大小
```

### 3. 启动应用
```bash
# 安装依赖
mvn clean install

# 启动应用
mvn spring-boot:run

# 或者打包后运行
mvn clean package
java -jar target/tc-medicine-1.0.0.jar
```

### 4. Python AI服务（可选）
```bash
# 启动Python识别服务（端口8001）
cd python-service
python app.py
```

### 5. 访问应用
- 🌐 **主页**: http://localhost:8080
- 👤 **登录**: http://localhost:8080/login
- 📊 **管理后台**: http://localhost:8080/dashboard
- 👨‍💼 **用户后台**: http://localhost:8080/user-dashboard
- 🔍 **药材识别**: http://localhost:8080/predict
- 💬 **AI问诊**: http://localhost:8080/diagnosis
- 📚 **药材百科**: http://localhost:8080/medicine
- 🏥 **疾病信息**: http://localhost:8080/diseases

### 默认账户
- **管理员**: admin / admin123
- **测试用户**: test / test123

## 🎯 使用指南

### 🔍 药材识别使用流程
1. 访问 `/predict` 页面
2. 上传清晰的药材照片
3. 等待AI识别结果
4. 查看识别的药材信息和相似度
5. 点击查看详细的药材资料
6. 登录用户的识别记录会自动保存

### 💬 AI问诊使用流程
1. 访问 `/diagnosis` 页面
2. 描述症状或健康问题
3. AI分析症状并提供中医角度的建议
4. 获得初步的调理建议
5. 重要提醒：建议就医确诊

### 📚 药材学习流程
1. 浏览 `/medicine` 页面的药材百科
2. 使用搜索功能查找特定药材
3. 查看药材的详细信息（性味、归经、功效等）
4. 了解用法用量和注意事项
5. 登录用户可以收藏感兴趣的药材

### 👤 用户后台使用流程
1. 登录后访问 `/user-dashboard` 页面
2. **个人资料管理**：
   - 修改用户名和个人简介
   - 上传个人头像
   - 通过邮箱验证码修改密码
3. **收藏夹管理**：
   - 查看已收藏的药材列表
   - 取消不需要的收藏
   - 快速访问收藏的药材详情
4. **识别历史**：
   - 查看历史识别记录
   - 重新查看识别结果
   - 访问识别药材的详细信息
5. **健康档案**：
   - 填写个人健康信息
   - 记录过敏史和病史
   - 管理当前用药情况
   - 设置紧急联系人

## 🎨 特色功能详解

### 🌍 中国草药分布图
- 基于真实地理数据的中药材分布
- 交互式地图支持缩放和拖拽
- 鼠标悬停显示省份和特色药材
- 审图号：GS（2024）0650号

### 🍲 药食同源展示
5种精选药膳的详细介绍：
- **枸杞明目茶** - 滋补肝肾，益精明目
- **山药养胃粥** - 健脾益胃，补肺益肾
- **红枣补血汤** - 补中益气，养血安神
- **薏米祛湿饮** - 利水渗湿，健脾止泻
- **银耳润肺羹** - 滋阴润肺，养胃生津

### 🎭 中医六艺
传统中医疗法的现代化展示：
- **针灸** - 经络穴位的神奇疗法
- **艾灸** - 温经散寒的传统技艺
- **推拿** - 手法治疗的精妙艺术
- **拔罐** - 祛湿排毒的古老智慧
- **刮痧** - 疏通经络的有效方法
- **中药** - 草木精华的治病良方

## 🔧 开发指南

### 添加新药材
1. 在管理后台添加药材信息
2. 上传药材图片到OSS
3. 配置药材的详细属性
4. 设置分类和标签

### 扩展AI功能
1. 在Python服务中训练新模型
2. 更新 `PredictController` 的识别接口
3. 添加新的识别类型和结果处理
4. 优化识别准确率

### 主题定制
1. 修改CSS变量定义主题色彩
2. 在JavaScript中添加主题切换逻辑
3. 保存用户主题偏好到localStorage
4. 支持系统主题的自动跟随

## 📦 部署指南

### Nginx配置
压缩项目到宝塔一键部署，修改数据库配置

## 🔍 故障排除

### 常见问题及解决方案

#### 🗄️ 数据库连接问题
```
问题：数据库连接失败
解决：
1. 检查MySQL服务是否启动
2. 验证数据库连接配置
3. 确认网络连接正常
4. 检查防火墙设置
```

#### 🔑 JWT认证问题
```
问题：Token验证失败
解决：
1. 检查JWT密钥配置
2. 验证Token过期时间
3. 清除浏览器缓存
4. 重新登录获取新Token
```

#### 🖼️ 图片上传问题
```
问题：图片上传失败
解决：
1. 检查OSS配置信息
2. 验证文件大小限制
3. 确认网络连接正常
4. 检查文件格式支持
```

#### 🤖 AI识别问题
```
问题：识别服务不可用
解决：
1. 检查Python服务是否启动
2. 验证模型文件完整性
3. 检查服务端口配置
4. 查看服务日志信息
```

## 📈 性能优化

### 前端优化
- ⚡ 图片懒加载
- 🗜️ CSS/JS压缩
- 📦 资源合并
- 🔄 浏览器缓存

### 后端优化
- 数据库连接池优化
- 分页查询支持
- 索引优化策略
- Redis缓存机制

### 数据库优化
```sql
-- 为常用查询字段添加索引
CREATE INDEX idx_medicine_name ON medicines(medicine_name);
CREATE INDEX idx_category_id ON medicines(category_id);
CREATE INDEX idx_user_name ON usr_user(user_name);
```

## 🤝 贡献指南

### 提交代码
1. Fork 本仓库
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

### 代码规范
- 遵循阿里巴巴Java开发规范
- 使用有意义的变量和方法名
- 添加必要的注释和文档
- 编写单元测试

### Bug报告
请使用 [Issue模板](https://github.com/your-repo/issues/new) 报告Bug：
- 描述问题现象
- 提供复现步骤
- 附加错误日志
- 说明运行环境

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 👥 团队成员

- **项目负责人** - 系统架构设计
- **后端开发** - Spring Boot应用开发
- **前端开发** - 用户界面设计与实现
- **AI工程师** - 药材识别模型训练
- **产品经理** - 需求分析与用户体验设计

## 📧 联系我们

- **Email**: 2224240962@henu.edu.cn
- **地址**: 河南大学郑州校区
- **电话**: 19513201850

## 🙏 致谢

感谢以下开源项目和服务商：
- [Spring Boot](https://spring.io/projects/spring-boot) - 强大的Java开发框架
- [MyBatis Plus](https://baomidou.com/) - 优秀的ORM框架
- [Leaflet](https://leafletjs.com/) - 开源地图库
- [阿里云OSS](https://www.aliyun.com/product/oss) - 对象存储服务
- [Bootstrap Icons](https://icons.getbootstrap.com/) - 图标库

---

<div align="center">

**杏林析微 © 2025**

*承岐黄精诚，以数字解构经络智慧*

*将千年草木灵韵融入现代诊疗*

*析微见真，守护健康日常；匠心传承，点亮生命本真*

[⬆ 回到顶部](#杏林析微---智慧中医药科普平台)

</div> 