/*
 Navicat Premium Data Transfer

 Source Server         : MySQL 5
 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Host           : localhost:3309
 Source Schema         : guest

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 30/01/2021 14:45:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for background
-- ----------------------------
DROP TABLE IF EXISTS `background`;
CREATE TABLE `background`  (
                               `back_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '后台管理的工号',
                               `password` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '后台登录密码',
                               PRIMARY KEY (`back_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of background
-- ----------------------------
INSERT INTO `background` VALUES ('admin', '981028');

-- ----------------------------
-- Table structure for book_msg
-- ----------------------------
DROP TABLE IF EXISTS `book_msg`;
CREATE TABLE `book_msg`  (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '预定信息id',
                             `guest_id_card` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户的身份证号',
                             `from_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '预计入住时间',
                             `to_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '预计退房时间',
                             `rank` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '预定的级别，分A,B,C,D级，级别一次降低',
                             `state` int NOT NULL DEFAULT 0 COMMENT '预定处理状态，0代表未处理，1代表已处理,11代表已入住(失效)',
                             `result_room` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预定到的房间',
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `type_id`(`rank` ASC) USING BTREE,
                             INDEX `guest_id_card`(`guest_id_card` ASC) USING BTREE,
                             INDEX `result_room`(`result_room` ASC) USING BTREE,
                             CONSTRAINT `book_msg_ibfk_1` FOREIGN KEY (`guest_id_card`) REFERENCES `guest` (`id_card`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of book_msg
-- ----------------------------
INSERT INTO `book_msg` VALUES (15, '362503199814782536', '2022-09-12 12:00:00', '2022-09-25 12:00:00', 'B', 1, '0001');
INSERT INTO `book_msg` VALUES (16, '362504200015697852', '2022-09-18 12:00:00', '2022-09-28 12:00:00', 'B', 1, '0002');
INSERT INTO `book_msg` VALUES (17, '362505200214895234', '2022-09-19 12:00:00', '2022-09-26 12:00:00', 'B', 1, '0003');

-- ----------------------------
-- Table structure for check_in
-- ----------------------------
DROP TABLE IF EXISTS `check_in`;
CREATE TABLE `check_in`  (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '入住情况的id',
                             `guest_id_card` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户的身份证号',
                             `room_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '房间号',
                             `from_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '入住日期',
                             `to_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '预计退房时间',
                             `state` int NOT NULL COMMENT '状态，0代表已退房，1代表正在入住',
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `guest_id_card`(`guest_id_card` ASC) USING BTREE,
                             INDEX `room_id`(`room_id` ASC) USING BTREE,
                             CONSTRAINT `check_in_ibfk_1` FOREIGN KEY (`guest_id_card`) REFERENCES `guest` (`id_card`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                             CONSTRAINT `check_in_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of check_in
-- ----------------------------
INSERT INTO `check_in` VALUES (16, '362503199814782536', '0001', '2022-09-12 12:00:00', '2022-09-25 12:00:00', 1);
INSERT INTO `check_in` VALUES (17, '362504200015697852', '0002', '2022-09-18 12:00:00', '2022-09-28 12:00:00', 1);
INSERT INTO `check_in` VALUES (18, '362505200214895234', '0003', '2022-09-19 12:00:00', '2022-09-26 12:00:00', 1);

-- ----------------------------
-- Table structure for cost
-- ----------------------------
DROP TABLE IF EXISTS `cost`;
CREATE TABLE `cost`  (
                         `id` int NOT NULL AUTO_INCREMENT COMMENT '消费信息的id',
                         `cost_type_id` int NOT NULL COMMENT '消费的名称',
                         `room_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '房间的id',
                         `num` int NOT NULL DEFAULT 1 COMMENT '数量',
                         `state` int NOT NULL DEFAULT 0 COMMENT '状态，0表示未结算，1表示已结算，11表示该次入住的用户全部结算',
                         PRIMARY KEY (`id`) USING BTREE,
                         INDEX `cost_type_id`(`cost_type_id` ASC) USING BTREE,
                         INDEX `room_id`(`room_id` ASC) USING BTREE,
                         CONSTRAINT `cost_ibfk_2` FOREIGN KEY (`cost_type_id`) REFERENCES `cost_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                         CONSTRAINT `cost_ibfk_3` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of cost
-- ----------------------------
INSERT INTO `cost` VALUES (3, 1, '0001', 1, 11);
INSERT INTO `cost` VALUES (4, 2, '0001', 1, 11);
INSERT INTO `cost` VALUES (5, 3, '0001', 1, 11);
INSERT INTO `cost` VALUES (6, 4, '0001', 1, 11);
INSERT INTO `cost` VALUES (7, 5, '0001', 1, 11);
INSERT INTO `cost` VALUES (8, 1, '0002', 1, 11);
INSERT INTO `cost` VALUES (9, 2, '0002', 1, 11);
INSERT INTO `cost` VALUES (10, 3, '0002', 1, 11);
INSERT INTO `cost` VALUES (11, 6, '0002', 1, 11);
INSERT INTO `cost` VALUES (12, 7, '0002', 2, 11);
INSERT INTO `cost` VALUES (13, 1, '0003', 1, 11);
INSERT INTO `cost` VALUES (14, 2, '0003', 1, 11);
INSERT INTO `cost` VALUES (15, 3, '0003', 1, 11);
INSERT INTO `cost` VALUES (16, 8, '0003', 1, 11);
INSERT INTO `cost` VALUES (17, 9, '0003', 1, 11);
INSERT INTO `cost` VALUES (18, 13, '0002', 1, 11);
INSERT INTO `cost` VALUES (19, 14, '0003', 1, 11);

-- ----------------------------
-- Table structure for cost_time
-- ----------------------------
DROP TABLE IF EXISTS `cost_time`;
CREATE TABLE `cost_time`  (
                              `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `cost_id` int NULL DEFAULT NULL COMMENT 'cost表id',
                              `time` datetime NULL DEFAULT NULL COMMENT '时间',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cost_time
-- ----------------------------

-- ----------------------------
-- Table structure for cost_type
-- ----------------------------
DROP TABLE IF EXISTS `cost_type`;
CREATE TABLE `cost_type`  (
                              `id` int NOT NULL AUTO_INCREMENT COMMENT '消费类型的id',
                              `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消费项目的名称',
                              `money` float NOT NULL COMMENT '金额',
                              PRIMARY KEY (`id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of cost_type
-- ----------------------------
INSERT INTO `cost_type` VALUES (1, '早餐', 50);
INSERT INTO `cost_type` VALUES (2, '晚餐', 50);
INSERT INTO `cost_type` VALUES (3, '午餐', 50);
INSERT INTO `cost_type` VALUES (4, '0001房间定金', -50);
INSERT INTO `cost_type` VALUES (5, '0001房间租金', 200);
INSERT INTO `cost_type` VALUES (6, '0002房间定金', -90);
INSERT INTO `cost_type` VALUES (7, '0002房间租金', 300);
INSERT INTO `cost_type` VALUES (8, '0003房间定金', -30);
INSERT INTO `cost_type` VALUES (9, '0003房间租金', 400);
INSERT INTO `cost_type` VALUES (10, '0004房间定金', -70);
INSERT INTO `cost_type` VALUES (11, '0004房间租金', 500);
INSERT INTO `cost_type` VALUES (12, '方便面', 5);
INSERT INTO `cost_type` VALUES (13, '矿泉水', 2);
INSERT INTO `cost_type` VALUES (14, '打扫房间', 20);

-- ----------------------------
-- Table structure for front
-- ----------------------------
DROP TABLE IF EXISTS `front`;
CREATE TABLE `front`  (
                          `front_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '前台管理员的工号',
                          `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前台管理员的姓名',
                          `password` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '前台管理的登录密码',
                          `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前台管理员的电话',
                          PRIMARY KEY (`front_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of front
-- ----------------------------
INSERT INTO `front` VALUES ('0001', '张雪萍', '123', '13361374723');
INSERT INTO `front` VALUES ('0002', '连宇豪', '981028', '17360031807');
INSERT INTO `front` VALUES ('0003', '李华华', '981028', '18827099738');

-- ----------------------------
-- Table structure for guest
-- ----------------------------
DROP TABLE IF EXISTS `guest`;
CREATE TABLE `guest`  (
                          `id_card` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户的身份证号',
                          `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户的姓名',
                          `contact` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户的联系方式',
                          PRIMARY KEY (`id_card`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of guest
-- ----------------------------
INSERT INTO `guest` VALUES ('362503199814782536', '张三丰', '13755987412');
INSERT INTO `guest` VALUES ('362504200015697852', '张无忌', '15248355973');
INSERT INTO `guest` VALUES ('362505200214895234', '张计科', '13254687956');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
                         `dish_id` int NOT NULL AUTO_INCREMENT COMMENT '菜品id',
                         `dish_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         `dish_price` decimal(10, 2) NOT NULL,
                         PRIMARY KEY (`dish_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '手撕包菜', 25.00);
INSERT INTO `menu` VALUES (2, '小炒杏鲍菇', 35.00);
INSERT INTO `menu` VALUES (3, '瘦肉炖鸽子', 56.00);
INSERT INTO `menu` VALUES (4, '臭豆腐烧四季豆', 32.00);
INSERT INTO `menu` VALUES (5, '银鱼蒸水蛋', 30.00);
INSERT INTO `menu` VALUES (6, '自制鸡肉卷', 35.00);
INSERT INTO `menu` VALUES (7, '大娃菜肉馅芥麦饺子', 36.00);
INSERT INTO `menu` VALUES (8, '双色花馒头', 10.00);
INSERT INTO `menu` VALUES (9, '杂菇蒸鸡', 45.00);
INSERT INTO `menu` VALUES (10, '凉拌金针菇葱丝', 29.00);
INSERT INTO `menu` VALUES (11, '红糖糍粑', 26.00);
INSERT INTO `menu` VALUES (12, '茴香肉包', 20.00);
INSERT INTO `menu` VALUES (13, '牛肉拌粉', 22.00);
INSERT INTO `menu` VALUES (14, '瓜丝麦饼', 22.00);
INSERT INTO `menu` VALUES (15, '韭菜鸡蛋素水饺', 20.00);
INSERT INTO `menu` VALUES (16, '墨鱼老鸭汤煮粉', 26.00);
INSERT INTO `menu` VALUES (17, '日式风味鳕鱼西京烧', 88.00);
INSERT INTO `menu` VALUES (18, '番茄虾尾意面', 42.00);
INSERT INTO `menu` VALUES (19, '萌能烤榴莲', 46.00);
INSERT INTO `menu` VALUES (20, '法式烤羊排', 47.00);
INSERT INTO `menu` VALUES (21, '西兰花鸡腿肉沙拉', 43.00);
INSERT INTO `menu` VALUES (22, '芒果鸡蛋三明治', 42.00);

-- ----------------------------
-- Table structure for reserver
-- ----------------------------
DROP TABLE IF EXISTS `reserver`;
CREATE TABLE `reserver`  (
                             `reserver_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `reserver_tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `eater_num` int NULL DEFAULT NULL,
                             `desk_num` int NULL DEFAULT NULL,
                             `state` int NULL DEFAULT 0,
                             `money` double NULL DEFAULT 0,
                             INDEX `reserver_name`(`reserver_name` ASC) USING BTREE,
                             INDEX `reserver_tel`(`reserver_tel` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reserver
-- ----------------------------
INSERT INTO `reserver` VALUES ('黄家', '15212356656', 5, 1, 0, 100);

-- ----------------------------
-- Table structure for reserver_menu
-- ----------------------------
DROP TABLE IF EXISTS `reserver_menu`;
CREATE TABLE `reserver_menu`  (
                                  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                                  `reserver_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预订人姓名',
                                  `reserver_tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预订人电话',
                                  `dish_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜品名称',
                                  `num` int NULL DEFAULT NULL,
                                  `price` double NULL DEFAULT NULL,
                                  PRIMARY KEY (`id`) USING BTREE,
                                  INDEX `reserver_menu_ibfk_1`(`reserver_name` ASC) USING BTREE,
                                  INDEX `reserver_menu_ibfk_2`(`reserver_tel` ASC) USING BTREE,
                                  INDEX `reserver_menu_ibfk_3`(`dish_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reserver_menu
-- ----------------------------
INSERT INTO `reserver_menu` VALUES (1, '黄家', '15212356656', '手撕白菜', 2, 25);
INSERT INTO `reserver_menu` VALUES (2, '黄家', '15212356656', '爆炒杏鲍菇', 1, 35);


-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
                         `room_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '房间编号',
                         `size` double NOT NULL COMMENT '房间大小，以平方米为单位',
                         `rank` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '级别，分A,B,C,D级，级别一次降低',
                         `rent` double NOT NULL COMMENT '租金，单位是人民币元',
                         `earnest` double NOT NULL COMMENT '入住定金，单位是人民币元',
                         `max_num` int NOT NULL COMMENT '最大人数',
                         `position` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地理位置',
                         PRIMARY KEY (`room_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES ('0001', 53, 'B', 200, 50, 3, '一楼');
INSERT INTO `room` VALUES ('0002', 53.2, 'B', 300, 90, 2, '二楼');
INSERT INTO `room` VALUES ('0003', 53.2, 'B', 400, 30, 2, '三楼');
INSERT INTO `room` VALUES ('0004', 53.2, 'A', 500, 70, 2, '二楼');

SET FOREIGN_KEY_CHECKS = 1;



-- 创建拦截器

CREATE TRIGGER cost_time_trigger AFTER INSERT ON cost
    FOR EACH ROW
BEGIN
    INSERT INTO cost_time VALUES(null,NEW.id , NOW());
END

create trigger cost_time_trigger2
    before DELETE  on cost
    for EACH row
begin
    delete from cost_time where cost_id = old.id;
end

    show triggers
