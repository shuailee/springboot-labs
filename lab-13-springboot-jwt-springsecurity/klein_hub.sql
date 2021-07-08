/*
 Navicat MySQL Data Transfer

 Source Server         : 192.168.5.15
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : 192.168.5.15:3306
 Source Schema         : klein_hub

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 02/07/2021 18:49:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户',
  `parent_id` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '父权限id',
  `permission_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '权限名称',
  `permission_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限类型[menu|button]',
  `permission_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '资源路径',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0：启用  1：禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0 未删除  1 已删除',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `created_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '1', 0, '用户管理', 'menu', 'userinfo/', 1, 0, '2021-07-02 10:44:15', '2021-07-01 04:40:13', '', '');
INSERT INTO `permission` VALUES (2, '1', 1, 'userinfo:add', 'button', 'userinfo/add', 1, 0, '2021-07-02 10:44:25', '2021-07-01 04:40:23', '', '');
INSERT INTO `permission` VALUES (3, '1', 1, '用户删除', 'button', 'userinfo/delete', 1, 0, '2021-07-01 05:03:12', '2021-07-01 04:41:38', '', '');
INSERT INTO `permission` VALUES (4, '1', 1, '用户查询', 'button', 'userinfo/query', 1, 0, '2021-07-01 05:03:14', '2021-07-01 05:00:46', '', '');

-- ----------------------------
-- Table structure for permission_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `permission_role_relation`;
CREATE TABLE `permission_role_relation`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户',
  `permission_id` int(0) NOT NULL COMMENT '权限id',
  `role_id` int(0) NOT NULL COMMENT '角色id',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0：启用  1：禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0 未删除  1 已删除',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `created_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission_role_relation
-- ----------------------------
INSERT INTO `permission_role_relation` VALUES (1, '1', 1, 1, 1, 0, '2021-07-01 05:04:09', '2021-07-01 05:04:09', '', '');
INSERT INTO `permission_role_relation` VALUES (2, '1', 2, 1, 1, 0, '2021-07-01 05:29:42', '2021-07-01 05:06:41', '', '');
INSERT INTO `permission_role_relation` VALUES (3, '1', 3, 1, 1, 0, '2021-07-01 05:29:40', '2021-07-01 05:29:40', '', '');
INSERT INTO `permission_role_relation` VALUES (4, '1', 4, 1, 1, 0, '2021-07-01 05:29:52', '2021-07-01 05:29:52', '', '');
INSERT INTO `permission_role_relation` VALUES (5, '1', 4, 3, 2, 0, '2021-07-01 05:30:42', '2021-07-01 05:30:42', '', '');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0：启用  1：禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0 未删除  1 已删除',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `created_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '1', '超级管理员', 1, 0, '2021-07-01 04:32:33', '2021-07-01 04:32:33', '', '');
INSERT INTO `role` VALUES (2, '1', '普通访客', 1, 0, '2021-07-01 04:32:55', '2021-07-01 04:32:55', '', '');

-- ----------------------------
-- Table structure for role_user_relation
-- ----------------------------
DROP TABLE IF EXISTS `role_user_relation`;
CREATE TABLE `role_user_relation`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `role_id` int(0) NOT NULL COMMENT '角色id',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0：启用  1：禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0 未删除  1 已删除',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `created_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_user_relation
-- ----------------------------
INSERT INTO `role_user_relation` VALUES (1, '1', 1, 1, 1, 0, '2021-07-01 04:39:31', '2021-07-01 04:39:31', '', '');
INSERT INTO `role_user_relation` VALUES (2, '1', 2, 2, 1, 0, '2021-07-01 04:39:52', '2021-07-01 04:39:52', '', '');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户',
  `user_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '会员编码',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '会员账户',
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '会员密码',
  `user_nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0：启用  1：禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0 未删除  1 已删除',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `created_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '会员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '1', 'admin', 'admin', '123456', 'admin', '18621568267', '919691653@qq.com', 1, 0, '2021-07-01 04:31:08', '2021-07-01 04:31:08', '', '');
INSERT INTO `user` VALUES (2, '1', 'gust', 'gust', '123456', 'gust', '13122222222', '12345678@qq.com', 1, 0, '2021-07-01 04:31:54', '2021-07-01 04:31:54', '', '');

SET FOREIGN_KEY_CHECKS = 1;
