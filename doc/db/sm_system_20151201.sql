-- ----------------------------
-- Table structure for t_sm_system
-- ----------------------------
DROP TABLE IF EXISTS `t_sm_system`;
CREATE TABLE `t_sm_system` (
  `APP_ID` varchar(16) NOT NULL,
  `APP_NAME` varchar(32) DEFAULT NULL,
  `APP_DESCRIBE` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`APP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_sm_system
-- ----------------------------
INSERT INTO `t_sm_system` VALUES ('mds', '元数据', '元数据');
INSERT INTO `t_sm_system` VALUES ('sm', '系统管理', '系统管理');
