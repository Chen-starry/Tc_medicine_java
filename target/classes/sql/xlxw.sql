-- MySQL dump 10.13  Distrib 8.1.0, for macos13 (arm64)
--
-- Host: 127.0.0.1    Database: tc_medicine
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'外感六淫','2025-04-06 14:52:25','2025-04-06 14:52:25');
INSERT INTO `categories` VALUES (2,'内伤七情','2025-04-06 14:52:25','2025-04-06 14:52:25');
INSERT INTO `categories` VALUES (3,'妇人诸疾','2025-04-06 14:52:25','2025-04-06 14:52:25');
INSERT INTO `categories` VALUES (4,'小儿病症','2025-04-06 14:52:25','2025-04-06 14:52:25');
INSERT INTO `categories` VALUES (5,'皮肤疮疡','2025-04-06 14:52:25','2025-04-06 14:52:25');
INSERT INTO `categories` VALUES (6,'金创跌仆','2025-04-06 14:52:25','2025-04-06 14:52:25');
INSERT INTO `categories` VALUES (7,'气血津液','2025-04-06 14:52:25','2025-04-06 14:52:25');
INSERT INTO `categories` VALUES (8,'经络肢体','2025-04-06 14:52:25','2025-04-06 14:52:25');
INSERT INTO `categories` VALUES (9,'五官七窍','2025-04-06 14:52:25','2025-04-06 14:52:25');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '提交用户',
  `contact` varchar(100) NOT NULL COMMENT '联系方式',
  `content` text NOT NULL COMMENT '反馈内容',
  `feedback_time` datetime NOT NULL COMMENT '反馈时间',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '处理状态（0-未处理，1-已处理）',
  PRIMARY KEY (`id`),
  KEY `idx_time` (`feedback_time` DESC),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户反馈表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,'fhy','18031266876','111','2025-06-02 02:13:27',1);
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `health_profile`
--

DROP TABLE IF EXISTS `health_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `health_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `age` int DEFAULT NULL COMMENT '年龄',
  `gender` tinyint DEFAULT NULL COMMENT '性别：0-女，1-男',
  `height` decimal(5,2) DEFAULT NULL COMMENT '身高(cm)',
  `weight` decimal(5,2) DEFAULT NULL COMMENT '体重(kg)',
  `blood_type` varchar(10) DEFAULT NULL COMMENT '血型',
  `allergies` text COMMENT '过敏史',
  `chronic_diseases` text COMMENT '慢性疾病',
  `current_medications` text COMMENT '当前用药',
  `health_notes` text COMMENT '健康备注',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='健康档案表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `health_profile`
--

LOCK TABLES `health_profile` WRITE;
/*!40000 ALTER TABLE `health_profile` DISABLE KEYS */;
INSERT INTO `health_profile` VALUES (1,10,18,1,165.00,70.00,'AB','无','哮喘','布地奈德福莫特罗吸入粉雾剂',NULL,'2025-06-02 19:04:15','2025-06-02 19:04:15');
/*!40000 ALTER TABLE `health_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `herbal_medicine`
--

DROP TABLE IF EXISTS `herbal_medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `herbal_medicine` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pinyin_name` varchar(50) NOT NULL,
  `result` varchar(100) NOT NULL,
  `origin` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `effects` varchar(255) NOT NULL,
  `disease` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pinyin_name` (`pinyin_name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `herbal_medicine`
--

LOCK TABLES `herbal_medicine` WRITE;
/*!40000 ALTER TABLE `herbal_medicine` DISABLE KEYS */;
INSERT INTO `herbal_medicine` VALUES (1,'aiye','艾叶','中国','菊科植物艾的干燥叶子。','温经止血，散寒止痛。','月经不调，腹痛');
INSERT INTO `herbal_medicine` VALUES (2,'baibiandou','白扁豆','中国','豆科植物扁豆的成熟种子。','健脾化湿，消暑解毒。','脾虚腹泻，暑湿吐泻');
INSERT INTO `herbal_medicine` VALUES (3,'baibu','白鲍','中国','百部科植物直立百部的干燥块根。','润肺止咳，杀虫灭虱。','肺虚久咳，蛲虫病');
INSERT INTO `herbal_medicine` VALUES (4,'baidoukou','白豆蔻','中国','姜科植物白豆蔻的成熟果实。','化湿行气，温中止呕。','湿阻脾胃，食欲不振，呕吐');
INSERT INTO `herbal_medicine` VALUES (5,'baihe','百合','中国','百合科植物百合的鳞茎。','养阴润肺，清心安神。','阴虚咳嗽，失眠多梦');
INSERT INTO `herbal_medicine` VALUES (6,'cangzhu','苍术','中国','菊科植物茅苍术或北苍术的干燥根茎。','燥湿健脾，祛风散寒。','湿阻中焦，风湿痹痛');
INSERT INTO `herbal_medicine` VALUES (7,'cansha','蚕沙','中国','蚕蛾科昆虫家蚕幼虫的干燥粪便。','祛风湿，和胃化浊。','风湿痹痛，湿疹瘙痒');
INSERT INTO `herbal_medicine` VALUES (8,'dangshen','党参','中国','桔梗科植物党参的干燥根。','补中益气，健脾益肺。','脾肺虚弱，气短心悸，食少便溏，虚喘咳嗽，内热消渴');
INSERT INTO `herbal_medicine` VALUES (9,'ezhu','莪术','中国','姜科植物莪术的干燥根茎。','行气破血，消积止痛。','气滞血瘀，食积腹痛');
INSERT INTO `herbal_medicine` VALUES (10,'foshou','佛手','中国','芸香科植物佛手的干燥果实。','疏肝理气，和胃止痛。','肝胃不和，胸胁胀痛，脘腹胀满');
INSERT INTO `herbal_medicine` VALUES (11,'gancao','甘草','中国','豆科植物甘草的干燥根及根茎。','补脾益气，清热解毒。','脾胃虚弱，咳嗽，咽喉肿痛');
INSERT INTO `herbal_medicine` VALUES (12,'gouqi','枸杞','中国','茄科植物枸杞的成熟果实。','滋补肝肾，益精明目。','肝肾阴虚，目昏，腰膝酸软');
INSERT INTO `herbal_medicine` VALUES (13,'honghua','红花','中国','菊科植物红花的干燥花瓣。','活血通经，散瘀止痛。','经闭，痛经，瘀滞胸痛');
INSERT INTO `herbal_medicine` VALUES (14,'hongteng','红藤','中国','豆科植物鸡血藤的干燥藤茎。','活血舒筋，养血调经。','月经不调，风湿痹痛');
INSERT INTO `herbal_medicine` VALUES (15,'huaihua','槐花','中国','豆科植物槐树的干燥花蕾。','凉血止血，清肝泻火。','肠风下血，痔血，肝热目赤');
INSERT INTO `herbal_medicine` VALUES (16,'jiangcan','僵蚕','中国','蚕蛾科昆虫家蚕的干燥幼虫尸体。','息风止痉，化痰散结。','中风，癫痫，咽喉肿痛');
INSERT INTO `herbal_medicine` VALUES (17,'jingjie','荆芥','中国','唇形科植物荆芥的干燥地上部分。','解表散风，透疹消疮。','感冒发热，麻疹不透，风疹瘙痒');
INSERT INTO `herbal_medicine` VALUES (18,'jinyinhua','金银花','中国','忍冬科植物忍冬的干燥花蕾或带初开的花。','清热解毒，疏散风热。','温病发热，热毒血痢，咽喉肿痛');
INSERT INTO `herbal_medicine` VALUES (19,'mudanpi','牡丹皮','中国','毛茛科植物牡丹的干燥根皮。','清热凉血，活血散瘀。','温病发斑，吐血，衄血');
INSERT INTO `herbal_medicine` VALUES (20,'niubangzi','牛蒡子','中国','菊科植物牛蒡的干燥成熟果实。','疏散风热，宣肺透疹。','风热感冒，咽喉肿痛，麻疹不透');
INSERT INTO `herbal_medicine` VALUES (21,'zhuling','猪苓','中国','多孔菌科真菌猪苓的干燥菌核。','利水渗湿。','小便不利，水肿，泄泻');
INSERT INTO `herbal_medicine` VALUES (22,'zhuru','竹茹','中国','禾本科植物淡竹的干燥中层。','清热化痰，除烦止呕。','痰热咳嗽，胃热呕吐，妊娠恶阻');
INSERT INTO `herbal_medicine` VALUES (23,'zhuye','竹叶','中国','禾本科植物淡竹或苦竹的干燥叶。','清热除烦，生津利尿。','热病烦渴，口舌生疮，小便短赤');
INSERT INTO `herbal_medicine` VALUES (24,'zicao','紫草','中国','紫草科植物紫草的干燥根。','凉血活血，解毒透疹。','温毒发斑，麻疹不透，湿疹瘙痒');
/*!40000 ALTER TABLE `herbal_medicine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine_categories`
--

DROP TABLE IF EXISTS `medicine_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicine_categories` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) NOT NULL,
  `category_description` text,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine_categories`
--

LOCK TABLES `medicine_categories` WRITE;
/*!40000 ALTER TABLE `medicine_categories` DISABLE KEYS */;
INSERT INTO `medicine_categories` VALUES (1,'解表药','能发散表邪，解除表证的药物');
INSERT INTO `medicine_categories` VALUES (2,'清热药','以清解里热为主要作用的药物');
INSERT INTO `medicine_categories` VALUES (3,'泻下药','能引起腹泻或滑利大肠，促进排便的药物');
INSERT INTO `medicine_categories` VALUES (4,'祛风湿药','以祛除风寒湿邪，治疗风湿痹证为主的药物');
INSERT INTO `medicine_categories` VALUES (5,'化湿药','气味芳香，性偏温燥，以化湿运脾为主要作用的药物');
INSERT INTO `medicine_categories` VALUES (6,'利水渗湿药','以通利水道，渗泄水湿为主要作用的药物');
INSERT INTO `medicine_categories` VALUES (7,'温里药','以温里祛寒，治疗里寒证为主的药物');
INSERT INTO `medicine_categories` VALUES (8,'补虚药','能补益正气，增强体质，提高抗病能力的药物');
/*!40000 ALTER TABLE `medicine_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicines`
--

DROP TABLE IF EXISTS `medicines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicines` (
  `medicine_id` int NOT NULL AUTO_INCREMENT,
  `category_id` int DEFAULT NULL,
  `medicine_name` varchar(50) NOT NULL,
  `source` text,
  `properties` varchar(100) DEFAULT NULL,
  `taste` varchar(50) DEFAULT NULL,
  `channels` varchar(100) DEFAULT NULL,
  `effects` text,
  `contraindications` text,
  `image_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`medicine_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `medicines_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `medicine_categories` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicines`
--

LOCK TABLES `medicines` WRITE;
/*!40000 ALTER TABLE `medicines` DISABLE KEYS */;
INSERT INTO `medicines` VALUES (1,1,'麻黄','麻黄科植物草麻黄、中麻黄或木贼麻黄的草质茎','温','辛、微苦','肺、膀胱经','发汗解表，宣肺平喘，利水消肿','体虚自汗、盗汗及虚喘者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E9%BA%BB%E9%BB%84.jpg');
INSERT INTO `medicines` VALUES (2,1,'桂枝','樟科植物肉桂的嫩枝','温','辛、甘','心、肺、膀胱经','发汗解肌，温通经脉，助阳化气','温热病及阴虚阳盛之证、血证忌用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E6%A1%82%E6%9E%9D.jpg');
INSERT INTO `medicines` VALUES (3,1,'紫苏叶','唇形科植物紫苏的叶','温','辛','肺、脾经','解表散寒，行气和胃','温病及气弱表虚者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E7%B4%AB%E8%8B%8F%E5%8F%B6.jpg');
INSERT INTO `medicines` VALUES (4,1,'防风','伞形科植物防风的根','微温','辛、甘','膀胱、肝、脾经','祛风解表，胜湿止痛，止痉','阴血亏虚、热病动风者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E9%98%B2%E9%A3%8E.jpg');
INSERT INTO `medicines` VALUES (5,1,'白芷','伞形科植物白芷或杭白芷的根','温','辛','肺、胃、大肠经','解表散寒，祛风止痛，宣通鼻窍，燥湿止带','阴虚血热者忌用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E7%99%BD%E8%8A%B7.jpg');
INSERT INTO `medicines` VALUES (6,2,'石膏','硫酸盐类矿物硬石膏族石膏','大寒','甘、辛','肺、胃经','清热泻火，除烦止渴','脾胃虚寒及血虚、阴虚发热者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E7%9F%B3%E8%86%8F.jpg');
INSERT INTO `medicines` VALUES (7,2,'知母','百合科植物知母的根茎','寒','苦、甘','肺、胃、肾经','清热泻火，滋阴润燥','脾虚便溏者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E7%9F%A5%E6%AF%8D.jpg');
INSERT INTO `medicines` VALUES (8,2,'黄芩','唇形科植物黄芩的根','寒','苦','肺、胆、脾、大肠、小肠经','清热燥湿，泻火解毒，止血安胎','脾胃虚寒者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E9%BB%84%E5%B2%91.jpg');
INSERT INTO `medicines` VALUES (9,2,'黄连','毛茛科植物黄连、三角叶黄连或云连的根茎','寒','苦','心、脾、胃、肝、胆、大肠经','清热燥湿，泻火解毒','脾胃虚寒者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E9%BB%84%E8%BF%9E.jpg');
INSERT INTO `medicines` VALUES (10,2,'金银花','忍冬科植物忍冬的花蕾','寒','甘','肺、心、胃经','清热解毒，疏散风热','脾胃虚寒及气虚疮疡脓清者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E9%87%91%E9%93%B6%E8%8A%B1.jpg');
INSERT INTO `medicines` VALUES (11,2,'连翘','木犀科植物连翘的果实','微寒','苦','肺、心、小肠经','清热解毒，消肿散结，疏散风热','脾胃虚寒及气虚脓清者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E8%BF%9E%E7%BF%98.jpg');
INSERT INTO `medicines` VALUES (12,3,'大黄','蓼科植物掌叶大黄、唐古特大黄或药用大黄的根茎','寒','苦','脾、胃、大肠、肝、心包经','泻下攻积，清热泻火，凉血解毒，逐瘀通经','孕妇及月经期、哺乳期慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E5%A4%A7%E9%BB%84.jpg');
INSERT INTO `medicines` VALUES (13,3,'芒硝','硫酸盐类矿物芒硝族芒硝，经加工精制而成的结晶体','寒','咸、苦','胃、大肠经','泻下通便，润燥软坚，清火消肿','孕妇及哺乳期妇女慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E8%8A%92%E7%A1%9D.jpg');
INSERT INTO `medicines` VALUES (14,3,'火麻仁','桑科植物大麻的成熟种子','平','甘','脾、胃、大肠经','润肠通便','大量服用可致中毒','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E7%81%AB%E9%BA%BB%E4%BB%81.jpg');
INSERT INTO `medicines` VALUES (15,3,'甘遂','大戟科植物甘遂的块根','寒','苦','肺、肾、大肠经','泻水逐饮，消肿散结','虚弱者及孕妇忌用，不宜与甘草同用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E7%94%98%E9%81%82.jpg');
INSERT INTO `medicines` VALUES (16,3,'巴豆','大戟科植物巴豆的成熟果实','热','辛','胃、大肠经','峻下冷积，逐水退肿，祛痰利咽','孕妇及体弱者忌用，不宜与牵牛子同用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E5%B7%B4%E8%B1%86.jpg');
INSERT INTO `medicines` VALUES (17,4,'独活','伞形科植物重齿毛当归的根','微温','辛、苦','肾、膀胱经','祛风除湿，通痹止痛','阴虚血燥者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E7%8B%AC%E6%B4%BB.jpg');
INSERT INTO `medicines` VALUES (18,4,'威灵仙','毛茛科植物威灵仙、棉团铁线莲或东北铁线莲的根茎','温','辛、咸','膀胱经','祛风湿，通经络','气血虚弱者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E5%A8%81%E7%81%B5%E4%BB%99.jpg');
INSERT INTO `medicines` VALUES (19,4,'川乌','毛茛科植物乌头的母根','热','辛、苦','心、肝、肾、脾经','祛风除湿，温经止痛','孕妇忌用，不宜与贝母、半夏等同用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E5%B7%9D%E4%B9%8C.jpg');
INSERT INTO `medicines` VALUES (20,4,'木瓜','蔷薇科植物贴梗海棠的近成熟果实','温','酸','肝、脾经','舒筋活络，和胃化湿','胃酸过多者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E6%9C%A8%E7%93%9C.jpg');
INSERT INTO `medicines` VALUES (21,4,'秦艽','龙胆科植物秦艽、麻花秦艽、粗茎秦艽或小秦艽的根','平','辛、苦','胃、肝、胆经','祛风湿，清湿热，止痹痛','久痛虚羸、溲多便滑者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E7%A7%A6%E8%89%BD.jpg');
INSERT INTO `medicines` VALUES (22,5,'藿香','唇形科植物广藿香的地上部分','微温','辛','脾、胃、肺经','芳香化浊，和中止呕，发表解暑','阴虚火旺者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E8%97%BF%E9%A6%99.jpg');
INSERT INTO `medicines` VALUES (23,5,'佩兰','菊科植物佩兰的地上部分','平','辛','脾、胃、肺经','芳香化湿，醒脾开胃，发表解暑','阴虚血燥者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E4%BD%A9%E5%85%B0.jpg');
INSERT INTO `medicines` VALUES (24,5,'苍术','菊科植物茅苍术或北苍术的根茎','温','辛、苦','脾、胃、肝经','燥湿健脾，祛风散寒，明目','阴虚内热、气虚多汗者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E8%8B%8D%E6%9C%AF.jpg');
INSERT INTO `medicines` VALUES (25,5,'厚朴','木兰科植物厚朴或凹叶厚朴的干皮','温','苦、辛','脾、胃、肺、大肠经','燥湿消痰，下气除满','孕妇慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E5%8E%9A%E6%9C%B4.jpg');
INSERT INTO `medicines` VALUES (26,5,'砂仁','姜科植物阳春砂、绿壳砂或海南砂的成熟果实','温','辛','脾、胃、肾经','化湿开胃，温脾止泻，理气安胎','阴虚血燥者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E7%A0%82%E4%BB%81.jpg');
INSERT INTO `medicines` VALUES (27,6,'茯苓','多孔菌科真菌茯苓的菌核','平','甘、淡','心、肺、脾、肾经','利水渗湿，健脾宁心','虚寒精滑者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E8%8C%AF%E8%8B%93.jpg');
INSERT INTO `medicines` VALUES (28,6,'薏苡仁','禾本科植物薏苡的成熟种仁','微寒','甘、淡','脾、胃、肺经','利水渗湿，健脾止泻，除痹排脓','孕妇慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E8%96%8F%E8%8B%A1%E4%BB%81.jpg');
INSERT INTO `medicines` VALUES (29,6,'泽泻','泽泻科植物泽泻的块茎','寒','甘','肾、膀胱经','利水渗湿，泄热化浊','肾虚精滑者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E6%B3%BD%E6%B3%BB.jpg');
INSERT INTO `medicines` VALUES (30,6,'车前子','车前科植物车前或平车前的成熟种子','微寒','甘','肝、肾、肺、小肠经','清热利尿通淋，渗湿止泻，明目祛痰','肾虚遗精者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E8%BD%A6%E5%89%8D%E5%AD%90.jpg');
INSERT INTO `medicines` VALUES (31,6,'滑石','硅酸盐类矿物滑石族滑石','寒','甘、淡','膀胱、肺、胃经','利尿通淋，清热解暑，祛湿敛疮','脾虚气弱、精滑者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E6%BB%91%E7%9F%B3.jpg');
INSERT INTO `medicines` VALUES (32,7,'附子','毛茛科植物乌头的子根的加工品','热','辛、甘','心、肾、脾经','回阳救逆，补火助阳，散寒止痛','孕妇慎用，不宜与半夏、瓜蒌等同用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E9%99%84%E5%AD%90.jpg');
INSERT INTO `medicines` VALUES (33,7,'干姜','姜科植物姜的干燥根茎','热','辛','脾、胃、肾、心、肺经','温中散寒，回阳通脉，温肺化饮','阴虚内热、血热妄行者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E5%B9%B2%E5%A7%9C.jpg');
INSERT INTO `medicines` VALUES (34,7,'肉桂','樟科植物肉桂的树皮','热','辛、甘','肾、脾、心、肝经','补火助阳，散寒止痛，温通经脉','阴虚火旺者忌用，孕妇慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E8%82%89%E6%A1%82.jpg');
INSERT INTO `medicines` VALUES (35,7,'吴茱萸','芸香科植物吴茱萸的近成熟果实','热','辛、苦','肝、脾、胃、肾经','散寒止痛，降逆止呕，助阳止泻','阴虚有热者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E5%90%B4%E8%8C%B1%E8%90%B8.jpg');
INSERT INTO `medicines` VALUES (36,7,'小茴香','伞形科植物茴香的成熟果实','温','辛','肝、肾、脾、胃经','散寒止痛，理气和胃','阴虚火旺者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E5%B0%8F%E8%8C%B4%E9%A6%99.jpg');
INSERT INTO `medicines` VALUES (37,8,'人参','五加科植物人参的根','微温','甘、微苦','脾、肺、心经','大补元气，复脉固脱，补脾益肺，生津安神','实证、热证而正气不虚者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E4%BA%BA%E5%8F%82.jpg');
INSERT INTO `medicines` VALUES (38,8,'黄芪','豆科植物蒙古黄芪或膜荚黄芪的根','微温','甘','肺、脾经','补气固表，利尿托毒，排脓敛疮','表实邪盛、食积停滞者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E9%BB%84%E8%8A%AA.jpg');
INSERT INTO `medicines` VALUES (39,8,'当归','伞形科植物当归的根','温','甘、辛','肝、心、脾经','补血活血，调经止痛，润肠通便','湿盛中满、大便溏泄者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E5%BD%93%E5%BD%92.jpg');
INSERT INTO `medicines` VALUES (40,8,'熟地黄','玄参科植物地黄的块根经加工炮制而成','微温','甘','肝、肾经','补血滋阴，益精填髓','气滞痰多、脘腹胀痛者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E7%86%9F%E5%9C%B0%E9%BB%84.jpg');
INSERT INTO `medicines` VALUES (41,8,'枸杞子','茄科植物宁夏枸杞的成熟果实','平','甘','肝、肾经','滋补肝肾，益精明目','外邪实热、脾虚有湿者慎用','https://fhy-tc-medicine.oss-cn-beijing.aliyuncs.com/medicine/%E8%8D%AF%E6%9D%90/%E6%9E%B8%E6%9D%9E%E5%AD%90.jpg');
/*!40000 ALTER TABLE `medicines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recognition_history`
--

DROP TABLE IF EXISTS `recognition_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recognition_history` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `image_url` varchar(500) NOT NULL COMMENT '识别图片URL',
  `result_medicine_id` int DEFAULT NULL COMMENT '识别结果药材ID',
  `result_name` varchar(100) DEFAULT NULL COMMENT '识别结果名称',
  `confidence` decimal(5,2) DEFAULT NULL COMMENT '识别置信度',
  `recognition_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '识别时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_recognition_time` (`recognition_time`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='识别历史表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recognition_history`
--

LOCK TABLES `recognition_history` WRITE;
/*!40000 ALTER TABLE `recognition_history` DISABLE KEYS */;
INSERT INTO `recognition_history` VALUES (4,10,'/uploads/recognition/niubangzi_(23).jpg_09612cf0-82ce-4084-a50b-388f96a29ebd.jpg',20,'牛蒡子',0.95,'2025-06-02 19:20:52');
/*!40000 ALTER TABLE `recognition_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syndromes`
--

DROP TABLE IF EXISTS `syndromes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `syndromes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `etiology` text NOT NULL,
  `symptoms` text NOT NULL,
  `treatment` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `syndromes_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syndromes`
--

LOCK TABLES `syndromes` WRITE;
/*!40000 ALTER TABLE `syndromes` DISABLE KEYS */;
INSERT INTO `syndromes` VALUES (1,1,'风寒表虚证','营卫失和，腠理疏松，风邪乘虚而入。《伤寒论》云：\"太阳病，发热汗出者，此为荣弱卫强\"','发热恶风、汗出头痛、鼻鸣干呕、脉浮缓。舌苔薄白而润，此证与表实证鉴别要点在于有汗无汗','解肌发表 - 桂枝汤（桂枝三两 芍药三两 甘草二两 生姜三两 大枣十二枚）。服后啜热稀粥助药力，取微汗为度','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (2,1,'暑湿困脾证','夏月感暑，贪凉饮冷，湿困中焦。叶天士云：\"长夏湿令，暑必兼湿\"','身热不扬、头重如裹、胸闷脘痞、便溏尿浊。舌苔白腻而厚，脉濡数。此证常见于梅雨季节','清暑化湿 - 新加香薷饮（香薷二钱 厚朴二钱 金银花三钱 连翘二钱）。配合六一散冲服，忌食生冷','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (3,1,'风寒表实证','风寒外袭，卫阳被遏，腠理闭塞','恶寒重发热轻、无汗头痛、肢节酸痛、脉浮紧','辛温解表 - 麻黄汤（麻黄三两 桂枝二两 杏仁七十个 甘草一两）','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (4,1,'风热犯肺证','风热之邪侵袭肺卫，肺失清肃','发热重恶寒轻、咽喉肿痛、咳嗽痰黄、舌尖红','辛凉解表 - 银翘散（金银花 连翘 薄荷 牛蒡子）','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (5,2,'肝阳上亢证','情志不遂，肝郁化火，上扰清窍','头晕目眩、面红目赤、急躁易怒、脉弦数','平肝潜阳 - 天麻钩藤饮（天麻 钩藤 石决明 栀子）','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (6,2,'心脾两虚证','思虑过度，暗耗心血，损伤脾气','心悸健忘、食少便溏、面色萎黄、舌淡苔白','补益心脾 - 归脾汤（白术 茯神 黄芪 龙眼肉）','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (7,2,'心肾不交证','劳神过度，肾水亏于下，心火亢于上。《傅青主女科》云：\"心肾两虚，水火不济\"','心悸失眠、腰膝酸软、潮热盗汗、健忘耳鸣。舌尖红少苔，脉细数。此证多见于思虑过度之中年文人','交通心肾 - 黄连阿胶汤（黄连四两 黄芩二两 芍药二两 鸡子黄二枚）。配合涌泉穴艾灸，戌时服药最佳','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (8,2,'痰迷心窍证','七情郁结，痰浊蒙蔽清窍。《丹溪心法》云：\"痰之为物，随气升降，无处不到\"','神识昏蒙、喉中痰鸣、喃喃自语、举止失常。舌苔厚腻，脉滑。常见于癫证、痫证发作期','涤痰开窍 - 涤痰汤（制半夏三钱 胆南星二钱 枳实二钱 茯苓三钱）。配合苏合香丸鼻饲，禁食肥甘厚味','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (9,3,'血瘀胞宫证','寒凝气滞，瘀阻冲任','经行腹痛、经色紫暗、血块下则痛减','活血化瘀 - 少腹逐瘀汤（小茴香 干姜 延胡索 没药）','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (10,3,'带下湿热证','湿热下注，任带失约','带下黄稠、阴部瘙痒、小便短赤','清热利湿 - 止带方（猪苓 车前子 茵陈 黄柏）','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (11,3,'崩漏证','冲任不固，经血非时暴下。《妇科玉尺》云：\"崩漏之疾，本乎血热\"','经血淋漓不断或忽然大下、血色深红质稠、头晕面赤。舌红苔黄，脉滑数。危急时可见血脱之象','固冲止血 - 固经丸（黄芩一两 白芍一两 龟板一两 椿根皮七钱）。血止后改用归脾汤调理，慎用辛燥之品','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (12,3,'产后血晕证','产时失血过多，气随血脱。《金匮要略》云：\"新产妇人有三病，一者病痉，二者病郁冒，三者大便难\"','突然晕厥、面色苍白、冷汗淋漓、恶露量多。脉微欲绝，舌淡无华。此证属产科危急重症','益气固脱 - 独参汤（高丽参一两浓煎）。配合艾灸百会、关元，急用铁器淬醋熏鼻促醒','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (13,4,'惊风急症','热极生风，肝风内动','高热抽搐、两目上视、牙关紧闭','凉肝息风 - 羚角钩藤汤（羚羊角 钩藤 菊花 生地）','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (14,4,'疳积证','喂养不当，脾胃受损','面黄肌瘦、肚腹膨胀、毛发焦枯','消疳健脾 - 肥儿丸（人参 白术 使君子 神曲）','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (15,4,'五迟五软证','先天不足，后天失养。《幼幼集成》云：\"五迟者，禀受先天不足\"','立迟行迟、发稀齿迟、语迟智力低下。舌淡苔白，指纹淡滞。常见于早产或父母高龄所生子','补肾填髓 - 加味六味地黄丸（熟地八钱 山萸肉四钱 鹿角胶三钱）。配合捏脊疗法，用猪脊髓入药引','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (16,5,'湿热浸淫证','湿热毒邪，蕴结肌肤','红斑水疱、渗出瘙痒、舌苔黄腻','清热燥湿 - 萆薢渗湿汤（萆薢 薏苡仁 黄柏 丹皮）','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (17,5,'热毒壅盛证','火毒炽盛，腐肉成脓','痈疽红肿、灼热疼痛、伴发热口渴','清热解毒 - 五味消毒饮（金银花 野菊花 蒲公英 紫花地丁）','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (18,5,'白疕证（银屑病）','血分伏热，外受风邪。《外科正宗》云：\"白疕之形，疹色白而痒多\"','红斑覆银屑、刮之见薄膜现象与点状出血、冬重夏轻。舌紫暗有瘀斑，脉涩','凉血解毒 - 犀角地黄汤（水牛角一两 生地八钱 赤芍六钱）。外用青黛散麻油调敷，忌食发物','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (19,6,'瘀血肿痛证','跌打损伤，血溢脉外','局部青紫、肿胀刺痛、活动受限','活血止痛 - 七厘散（血竭 乳香 没药 红花）','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (20,6,'骨折筋伤证','外力冲击，骨断筋伤','畸形肿胀、骨擦音、功能障碍','接骨续筋 - 接骨丹（自然铜 土鳖虫 骨碎补 续断）','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (21,6,'金疮痉证（破伤风）','创伤之后，风毒之邪侵入肌腠经脉。《诸病源候论》云：\"金疮得风，则变痉\"','牙关紧闭、角弓反张、苦笑面容、抽搐频发。此证七日为期，最险在第十四日','息风解痉 - 五虎追风散（蝉蜕一两 南星二钱 天麻二钱）。配合艾灸伤口，朱砂外敷脐部','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (22,7,'气滞血瘀证','情志抑郁，气机阻滞，血行不畅。《医林改错》云：\"气无形不能结块，结块者必有形之血也\"','胸胁刺痛拒按、月经紫暗有块、面色晦暗。舌质紫暗有瘀斑，脉涩或结代。常见于癥瘕积聚初期','行气活血 - 血府逐瘀汤（桃仁四钱 红花三钱 柴胡二钱 枳壳二钱）。配合三棱、莪术醋制增效，经期慎用','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (23,7,'痰饮停肺证','脾失健运，聚湿成痰，上贮于肺。《金匮要略》云：\"病痰饮者，当以温药和之\"','咳嗽痰多色白、胸闷喘促、喉中痰鸣。舌苔白腻，脉滑。晨起症状加重，痰出则缓','燥湿化痰 - 二陈汤合三子养亲汤（半夏三钱 陈皮二钱 白芥子一钱）。痰黏难咯加海浮石，忌食肥甘','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (24,8,'风寒湿痹证','风寒湿三气杂至，痹阻经络。《素问》云：\"风寒湿三气杂至，合而为痹也\"','关节冷痛重着、遇寒加重、晨僵明显。舌淡苔白腻，脉弦紧。阴雨天气症状加剧','祛风散寒除湿 - 蠲痹汤（羌活三钱 姜黄二钱 当归三钱）。配合雷火灸疗法，外用川乌酒擦涂','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (25,8,'痿证（湿热浸淫）','湿热浸淫筋脉，肌肉失养。《素问》云：\"湿热不攘，大筋软短，小筋弛长\"','肢体困重无力、肌肉萎缩、足胫热气上腾。舌红苔黄腻，脉濡数。多见夏秋湿温时节','清热利湿 - 四妙丸（苍术三钱 黄柏二钱 牛膝四钱）。配合刺络拔罐，取阳陵泉、足三里','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (26,9,'肝火上炎目赤','肝郁化火，上攻目窍。《审视瑶函》云：\"目病属火，火性炎上\"','目赤肿痛、畏光流泪、视物模糊。伴口苦咽干，脉弦数。寅时症状加重','清肝明目 - 龙胆泻肝汤（龙胆草二钱 栀子三钱 木通一钱）。配合菊花决明子茶，冷敷太阳穴','2025-04-06 14:52:44','2025-04-06 14:52:44');
INSERT INTO `syndromes` VALUES (27,9,'虚火喉痹证','肺肾阴虚，虚火上炎。《喉科指掌》云：\"虚火喉痹，其本在肾\"','咽喉干痛午后加重、黏膜暗红、时有异物感。舌红少苔，脉细数。教师、歌者多见','滋阴降火 - 养阴清肺汤（生地五钱 麦冬三钱 玄参三钱）。配合少商穴点刺出血，忌大声言语','2025-04-06 14:52:44','2025-04-06 14:52:44');
/*!40000 ALTER TABLE `syndromes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_favorites`
--

DROP TABLE IF EXISTS `user_favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_favorites` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `medicine_id` int NOT NULL COMMENT '药材ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_medicine` (`user_id`,`medicine_id`) COMMENT '用户+药材唯一约束',
  KEY `idx_user_id` (`user_id`),
  KEY `idx_medicine_id` (`medicine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_favorites`
--

LOCK TABLES `user_favorites` WRITE;
/*!40000 ALTER TABLE `user_favorites` DISABLE KEYS */;
INSERT INTO `user_favorites` VALUES (7,10,2,'2025-06-02 19:21:10');
/*!40000 ALTER TABLE `user_favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usr_user`
--

DROP TABLE IF EXISTS `usr_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usr_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_NAME` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱地址',
  `email_verified` tinyint(1) DEFAULT '0' COMMENT '邮箱是否已验证：0-未验证，1-已验证',
  `PASSWORD` varchar(100) NOT NULL COMMENT '密码',
  `creat_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_type` int NOT NULL,
  `avatar_url` varchar(500) DEFAULT NULL COMMENT '用户头像URL',
  `profile_description` text COMMENT '个人简介',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usr_user`
--

LOCK TABLES `usr_user` WRITE;
/*!40000 ALTER TABLE `usr_user` DISABLE KEYS */;
INSERT INTO `usr_user` VALUES (1,'fhy',NULL,0,'e10adc3949ba59abbe56e057f20f883e','2025-04-05 07:14:33',1,NULL,NULL);
INSERT INTO `usr_user` VALUES (6,'lyq',NULL,0,'e10adc3949ba59abbe56e057f20f883e','2025-04-05 07:17:49',0,NULL,NULL);
INSERT INTO `usr_user` VALUES (7,'zhao',NULL,0,'e10adc3949ba59abbe56e057f20f883e','2025-04-06 14:37:23',0,NULL,NULL);
INSERT INTO `usr_user` VALUES (10,'chen','xiaoyujiang0421@163.com',1,'96e79218965eb72c92a549dd5a330112','2025-06-02 06:48:22',0,'/uploads/avatars/avatar_10_6eae6625-e652-484a-b321-fbb73735306c.jpeg','热爱生活 热爱学习');
/*!40000 ALTER TABLE `usr_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-02 20:37:53
