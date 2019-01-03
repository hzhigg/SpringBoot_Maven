/*
Navicat MySQL Data Transfer

Source Server         : ServiceA
Source Server Version : 50642
Source Host           : 192.168.13.135:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2019-01-02 17:04:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '无意义自增主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint(10) NOT NULL DEFAULT '0' COMMENT '创建用户',
  `is_valid` int(1) NOT NULL DEFAULT '0' COMMENT '是否启用，1:启用     0:不启用',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` bigint(10) NOT NULL DEFAULT '0' COMMENT '更新用户',
  `version` int(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  `address` varchar(255) NOT NULL COMMENT '具体地址',
  `city` varchar(32) NOT NULL COMMENT '城市',
  `country` varchar(16) NOT NULL COMMENT '国家',
  `label` varchar(16) NOT NULL COMMENT '地址标签（家、公司）',
  `province` varchar(32) NOT NULL COMMENT '省份',
  `web_user_id` bigint(30) DEFAULT NULL COMMENT '无意义自增主键',
  PRIMARY KEY (`id`),
  KEY `FK6kglr8wulaoxbs32v9pydrw2f` (`web_user_id`),
  CONSTRAINT `FK6kglr8wulaoxbs32v9pydrw2f` FOREIGN KEY (`web_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('1', '2019-01-02 17:04:10', '0', '0', '2019-01-02 17:04:10', '0', '0', '广东', '深圳', '中国', '1', '广东', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '无意义自增主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint(10) NOT NULL DEFAULT '0' COMMENT '创建用户',
  `is_valid` int(1) NOT NULL DEFAULT '0' COMMENT '是否启用，1:启用     0:不启用',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` bigint(10) NOT NULL DEFAULT '0' COMMENT '更新用户',
  `version` int(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  `email` varchar(64) NOT NULL DEFAULT '' COMMENT '邮箱',
  `nick_name` varchar(32) DEFAULT '' COMMENT '昵称',
  `password` varchar(32) NOT NULL DEFAULT '000000' COMMENT '密码',
  `user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '2019-01-02 17:01:39', '0', '0', '2019-01-02 17:01:39', '0', '0', '222@qq.com', '22', '000000', '222');
