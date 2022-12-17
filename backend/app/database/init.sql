/*
 Navicat Premium Data Transfer

 Source Server         : 服务器
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 81.70.252.183:3306
 Source Schema         : test_registration

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 20/05/2022 16:51:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

create database if not exists exam_apply;

use exam_apply;

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (
                         `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                         `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '考试名称',
                         `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
                         `create_time` bigint(255) NULL DEFAULT NULL COMMENT '创建时间',
                         `update_time` bigint(255) NULL DEFAULT NULL COMMENT '更新时间',
                         `start_time` bigint(255) NULL DEFAULT NULL COMMENT '考试开始时间',
                         `end_time` bigint(255) NULL DEFAULT NULL COMMENT '考试结束时间',
                         `status` tinyint(255) NULL DEFAULT NULL COMMENT '状态:0未开始，1报名中，2结束报名，',
                         `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '考试费用',
                         `apply_start_time` bigint(20) NULL DEFAULT NULL COMMENT '报名开始时间',
                         `apply_end_time` bigint(255) NULL DEFAULT NULL COMMENT '报名结束时间',
                         `announcements` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注意事项',
                         `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
                         `exam_type` tinyint(255) NULL DEFAULT NULL COMMENT '类型',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of exam
-- ----------------------------
INSERT INTO `exam` VALUES ('1510601527343972352', '普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1648990444395, 1648990444395, 1680278400000, 1680451200000, 2, 40.00, 1648990860000, 1648992840000, '考试严禁作弊及代考,一经发现,取消本次测试成绩及一年内的应试资格。', '2923791245@qq.com', 6);
INSERT INTO `exam` VALUES ('1510623816471019520', '普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1648995758537, 1648995758537, 1681833600000, 1651161600000, 3, 40.00, 1649077260000, 1649165640000, '考试严禁作弊及代考,一经发现,取消本次测试成绩及一年内的应试资格。', '1521930938@qq.com', 6);
INSERT INTO `exam` VALUES ('1510827548362145792', '英语四级口语', '　　CET口试每年开考两次，分别于5月和11月举行，具体考试时间我中心将会在每年年初另行通知。', 1649044332005, 1649044332005, 1649779200000, 1650988800000, 3, 45.00, 1647360000000, 1648569600000, '口语考试采用机考形式，凡已经报考笔试科目的考生均可报考对应级别的口试科目。', '444238219@qq.com', 7);
INSERT INTO `exam` VALUES ('1510982742123216896', '计算机等级考试', '面向社会，用于考查应试人员计算机应用知识与技能的全国性计算机水平考试体系。', 1649081333081, 1649081333081, 1648915200000, 1648947600000, 3, 40.00, 1648822082000, 1648908960000, '2022年暂停三级Linux应用与开发技术、四级Linux应用与开发工程师两个科目考试。', '2923791245@qq.com', 5);
INSERT INTO `exam` VALUES ('1513853055060148224', '英语六级', '2022年上半年全国大学英语四、六级考试（CET）笔试及口试将分别于6月11日，5月21至22日举行，各地报名工作陆续启动。在新冠肺炎疫情常态化防控时期，考生须严格遵守当地疫情防控规定，做好个人防护。如局部地区发生疫情多点散发时，考试可能会被延迟或取消，届时请以各省级承办机构及所在学校通知为准。', 1649765669013, 1649765669013, 1650124800000, 1650297600000, 3, 40.00, 1649174400000, 1649952000000, '请考生按所在学校规定时间登陆CET全国网上报名系统（cet-bm.neea.edu.cn）完成资格审核、笔试报名缴费及口试报名缴费。', '2923791245@qq.com', 3);
INSERT INTO `exam` VALUES ('1520373957490900992', '英语四级口语', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1651320373305, 1651320373305, 1652284800000, 1652457600000, 3, 50.00, 1651075200000, 1651766400000, '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', '2923791245@qq.com', 7);
INSERT INTO `exam` VALUES ('1524594652303327232', '英语六级口语考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1652326665365, 1652326665365, 1653753600000, 1653926400000, 2, 40.00, 1652198400000, 1652976000000, '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', '2923791245@qq.com', 7);
INSERT INTO `exam` VALUES ('1525464657362419712', '德州社区工作者考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1652534090735, 1652534090735, 1652976000000, 1653408000000, 3, 50.00, 1649260800000, 1652630400000, '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', '2923791245@qq.com', 8);
INSERT INTO `exam` VALUES ('1530104799695994880', '青岛市普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1653640386877, 1653640386877, 1653667200000, 1653840000000, 1, 56.00, 1652803200000, 1748617200000, '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 'admin@qq.com', 1);
INSERT INTO `exam` VALUES ('1530107522315190272', '德城区普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1653641036000, 1653641036000, 1810742400000, 1842969600000, 2, 56.90, 1617724800000, 1653577200000, '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 'admin@qq.com', 1);
INSERT INTO `exam` VALUES ('1530108947183173632', '山亭区普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1653641375715, 1653641375715, 1653494400000, 1653494400000, 3, 45.00, 1652803200000, 1652886000000, '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 'admin@qq.com', 1);
INSERT INTO `exam` VALUES ('1530110550149693440', '崂山区普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1653641757892, 1653641757892, 1653667200000, 1653667200000, 1, 56.00, 1653408000000, 1843376135000, '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 'admin@qq.com', 1);

-- ----------------------------
-- Table structure for exam_introduction
-- ----------------------------
DROP TABLE IF EXISTS `exam_introduction`;
CREATE TABLE `exam_introduction`  (
                                      `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
                                      `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
                                      `rule` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则',
                                      `create_time` bigint(255) NULL DEFAULT NULL COMMENT '创建时间',
                                      `update_time` bigint(255) NULL DEFAULT NULL COMMENT '修改时间',
                                      `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
                                      `exam_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '考试内容',
                                      `grade` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '等级',
                                      `certificate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证书',
                                      `exam_type` tinyint(255) NULL DEFAULT NULL COMMENT '类型',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of exam_introduction
-- ----------------------------
INSERT INTO `exam_introduction` VALUES ('1463824369615623344323', '计算机等级考试', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', 1637837900915, 1637838177695, NULL, '英语', '四级', '英语四级证书', 5);
INSERT INTO `exam_introduction` VALUES ('146382436961563233432', '英语四级考试', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', 1637837900915, 1637838177695, NULL, '英语', '四级', '英语四级证书', 2);
INSERT INTO `exam_introduction` VALUES ('146382436961563234432', '会计考试', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', 1637837900915, 1652635659165, NULL, '英语', '四级', '英语四级证书', 4);
INSERT INTO `exam_introduction` VALUES ('146382436961563324432', '教师资格证考试', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', 1637837900915, 1637838177695, NULL, '英语', '四级', '英语四级证书', 6);
INSERT INTO `exam_introduction` VALUES ('1463824369615633244432', '英语口语考试', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', 1637837900915, 1637838177695, NULL, '英语', '四级', '英语四级证书', 7);
INSERT INTO `exam_introduction` VALUES ('14638243696156344323', '英语六级考试', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', 1637837900915, 1637838177695, NULL, '英语', '四级', '英语四级证书', 3);
INSERT INTO `exam_introduction` VALUES ('1510825482667425792', '普通话考试', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', 1649043839505, 1649046750538, NULL, 'sad方法都是', '案发当时', '暗室逢灯', 1);
INSERT INTO `exam_introduction` VALUES ('1525465283924328448', '其他考试', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 问题的关键究竟为何? 而这些并不是完全重要，更加重要的问题是， 啊啊，发生了会如何，不发生又会如何。 每个人都不得不面对这些问题。 在面对这种问题时， 就我个人来说，啊啊对我的意义，不能不说非常重大。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 既然如何， 生活中，若啊啊出现了，我们就不得不考虑它出现了的事实。 每个人都不得不面对这些问题。 在面对这种问题时， 在这种困难的抉择下，本人思来想去，寝食难安。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解', 1652534240119, 1652534240119, NULL, '不许作弊', '四级', '四级', 8);

-- ----------------------------
-- Table structure for exam_user
-- ----------------------------
DROP TABLE IF EXISTS `exam_user`;
CREATE TABLE `exam_user`  (
                              `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `exam_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '考试id',
                              `create_time` bigint(20) NULL DEFAULT NULL COMMENT '报名时间',
                              `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
                              `score` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分数',
                              `status` tinyint(255) NULL DEFAULT NULL COMMENT '状态：0未报名，1已报名，未支付，2报名已支付，3取消报名',
                              `apply_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '考试人姓名',
                              `id_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
                              `exam_room` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '考场',
                              `exam_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '准考证号',
                              `identification_photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件照',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of exam_user
-- ----------------------------
INSERT INTO `exam_user` VALUES ('1530107762262933504', '1530107522315190272', 1653641093208, 'admin@qq.com', NULL, 2, '牧尘', '3789213131311', '756637', '1530107762262933505', 'http://82.157.42.25/5c4871a556a74c57aa2d1ae8b2cac644.jpg');
INSERT INTO `exam_user` VALUES ('1530109215627018240', '1530108947183173632', 1653641439717, 'admin@qq.com', '95（优秀）', 2, '林动', '3704647456464644', '118650', '1530109215627018241', 'http://82.157.42.25/5c4871a556a74c57aa2d1ae8b2cac644.jpg');

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
                             `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
                             `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
                             `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
                             `type` tinyint(255) NULL DEFAULT NULL COMMENT '类型：1bug反馈，2建议，3投诉',
                             `create_time` bigint(255) NULL DEFAULT NULL COMMENT '创建时间',
                             `reply_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复内容',
                             `reply_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复人id',
                             `reply_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复时间',
                             `status` tinyint(255) NULL DEFAULT NULL COMMENT '状态：0未回复，1已回复',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '问题反馈' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES ('1462332909271646208', '英语六级', '系统管理', '1111', 1, 1637482309059, '对于我来说，对于团队来说，适用Node的原因其实很简单：开发起来快。熟悉JS的前端同学可以很快上手，节省成本。选一个http server库起一个server，选择合适的中间件，匹配好请求路由，看情况合理使用ORM库链接数据库、增删改查即可。', '2222', '1637635913902', 0);
INSERT INTO `feedback` VALUES ('2', '计算机等级考试', '系统反馈', '1', 1, NULL, '统计当时的用户消费情况', NULL, NULL, 1);

-- ----------------------------
-- Table structure for info
-- ----------------------------
DROP TABLE IF EXISTS `info`;
CREATE TABLE `info`  (
                         `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                         `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '信息标题',
                         `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
                         `type` tinyint(255) NULL DEFAULT NULL COMMENT '类型：1报名时间公告，2打印准考证公告，3成绩查询公告',
                         `status` tinyint(255) NULL DEFAULT NULL COMMENT '状态：0上架，1下架',
                         `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                         `update_time` bigint(255) NULL DEFAULT NULL COMMENT '修改时间',
                         `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                         `exam_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '考试id',
                         `thumb_up` int(255) NULL DEFAULT NULL COMMENT '点赞数',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of info
-- ----------------------------
INSERT INTO `info` VALUES ('1530092271834234880', '德州社区工作者考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 3, NULL, 1653637400008, NULL, NULL, '1525464657362419712', NULL);
INSERT INTO `info` VALUES ('1530092271884566528', '英语六级口语考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 2, NULL, 1653637400021, NULL, NULL, '1524594652303327232', NULL);
INSERT INTO `info` VALUES ('1530092271934898176', '英语四级口语', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 3, NULL, 1653637400030, NULL, NULL, '1520373957490900992', NULL);
INSERT INTO `info` VALUES ('1530092271968452608', '英语六级', '2022年上半年全国大学英语四、六级考试（CET）笔试及口试将分别于6月11日，5月21至22日举行，各地报名工作陆续启动。在新冠肺炎疫情常态化防控时期，考生须严格遵守当地疫情防控规定，做好个人防护。如局部地区发生疫情多点散发时，考试可能会被延迟或取消，届时请以各省级承办机构及所在学校通知为准。', 3, NULL, 1653637400039, NULL, NULL, '1513853055060148224', NULL);
INSERT INTO `info` VALUES ('1530092272006201344', '计算机等级考试', '面向社会，用于考查应试人员计算机应用知识与技能的全国性计算机水平考试体系。', 3, NULL, 1653637400048, NULL, NULL, '1510982742123216896', NULL);
INSERT INTO `info` VALUES ('1530092272043950080', '英语四级口语', '　　CET口试每年开考两次，分别于5月和11月举行，具体考试时间我中心将会在每年年初另行通知。', 3, NULL, 1653637400057, NULL, NULL, '1510827548362145792', NULL);
INSERT INTO `info` VALUES ('1530092272090087424', '普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 2, NULL, 1653637400068, NULL, NULL, '1510623816471019520', NULL);
INSERT INTO `info` VALUES ('1530092272132030464', '普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 3, NULL, 1653637400078, NULL, NULL, '1510623816471019520', NULL);
INSERT INTO `info` VALUES ('1530092314070876160', '普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 2, NULL, 1653637410080, NULL, NULL, '1510601527343972352', NULL);
INSERT INTO `info` VALUES ('1530095753106554880', '2022年普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1, NULL, 1653638230012, NULL, NULL, '1530095726095237120', NULL);
INSERT INTO `info` VALUES ('1530104812799000576', '青岛市普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1, NULL, 1653640390009, NULL, NULL, '1530104799695994880', NULL);
INSERT INTO `info` VALUES ('1530107539096600576', '德城区普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1, NULL, 1653641040015, NULL, NULL, '1530107522315190272', NULL);
INSERT INTO `info` VALUES ('1530108126303354880', '德城区普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 2, NULL, 1653641180017, NULL, NULL, '1530107522315190272', NULL);
INSERT INTO `info` VALUES ('1530108965164154880', '山亭区普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1, NULL, 1653641380014, NULL, NULL, '1530108947183173632', NULL);
INSERT INTO `info` VALUES ('1530109594309754880', '山亭区普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 3, NULL, 1653641530008, NULL, NULL, '1530108947183173632', NULL);
INSERT INTO `info` VALUES ('1530110558999674880', '崂山区普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 2, NULL, 1653641760016, NULL, NULL, '1530110550149693440', NULL);
INSERT INTO `info` VALUES ('1530110726771834880', '崂山区普通话考试', '应试者应试时,须携带相关指定证件(二代身份证原件及准考证，学生还应带上有效学生证原件),否则不予报名测试。', 1, NULL, 1653641800027, NULL, NULL, '1530110550149693440', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
                         `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
                         `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
                         `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
                         `identity_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
                         `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地址',
                         `role` tinyint(5) NOT NULL COMMENT '角色：1普通用户，2管理员',
                         `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
                         `update_time` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
                         `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
                         `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
                         `deleted` tinyint(255) NULL DEFAULT NULL COMMENT '注销：逻辑删除',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1521930938@qq.com', '$2a$10$KqiqCR.Ik7DtrGtjOKiWeOXG/wPNcaR.aG7BJOY06VdKT52EFbICS', '曹', '17662812343', '371326199901220430', '山东省临沂市平邑县', 3, 1645925775066, 1648996372534, '1497747372518670336', 'http://1.116.106.177/92a37ce4d241424889fea5fa9b1cb31a.png', NULL);
INSERT INTO `user` VALUES ('admin@qq.com', '$2a$10$hX9mUIiU7mhGLKTQAIZSSeyElnisFDY6HBv.RM.qLkHaot7cOpUuW', '小龙管理员', NULL, NULL, NULL, 2, 1645926477464, 1653637940100, '1497750318589411320', 'http://1.116.106.177/960f283eea834871888f16a3db7d7b9e.gif', NULL);
INSERT INTO `user` VALUES ('444238219@qq.com', '$2a$10$hX9mUIiU7mhGLKTQAIZSSeyElnisFDY6HBv.RM.qLkHaot7cOpUuW', '歌歌', NULL, NULL, NULL, 3, 1645926477464, 1648598993218, '1497750318589411328', 'http://1.116.106.177/960f283eea834871888f16a3db7d7b9e.gif', NULL);
INSERT INTO `user` VALUES ('2923791245@qq.com', '$2a$10$hX9mUIiU7mhGLKTQAIZSSeyElnisFDY6HBv.RM.qLkHaot7cOpUuW', '小斐', '17865275314', '371427200101097891', NULL, 2, 1648989799250, 1650527006397, '1510598821409718272', 'http://1.116.106.177/353062bb22014f01ad921ec29eddafb0.jpg', NULL);
INSERT INTO `user` VALUES ('18798086175@qq.com', '$2a$10$CDf3eGJ/jrKumIISbIJGw./b8YVj2.QUChAY/fj53mCeuf.8dTncG', '小考呀', NULL, NULL, NULL, 1, 1652628587455, 1652628587455, '1525861005333102592', 'http://1.116.106.177/960f283eea834871888f16a3db7d7b9e.gif', NULL);
INSERT INTO `user` VALUES ('3300755918@qq.com', '$2a$10$r4RT3LO527PNTOx91Lh3keRfB7pu4iHJWNFnIRqLQPYXse6QgqQ0q', '小考呀', NULL, NULL, NULL, 2, 1653126590554, 1653135158015, '1527949781723250688', 'http://1.116.106.177/960f283eea834871888f16a3db7d7b9e.gif', NULL);
INSERT INTO `user` VALUES ('754746761@qq.com', '$2a$10$DEtcTJdda21DrZtmcWJhyOknlbeSt5KjuJn9vwTxUBZ7fiIotn3te', '小考呀', NULL, NULL, NULL, 1, 1653315109305, 1653315109305, '1528740486674644992', 'http://1.116.106.177/960f283eea834871888f16a3db7d7b9e.gif', NULL);
INSERT INTO `user` VALUES ('576131344@qq.com', '$2a$10$P1GnC4zqDFtH695T3ChcLO5lWJAC53t0GPZuvBr4Ln4/nh.K6Yb5W', '小考呀', NULL, NULL, NULL, 1, 1653373736968, 1653373736968, '1528986388916076544', 'http://1.116.106.177/960f283eea834871888f16a3db7d7b9e.gif', NULL);
INSERT INTO `user` VALUES ('851051010@qq.com', '$2a$10$Ur3QQSL1xCGM7kDhxo4YjuEBi3zzCXIavDxgqXStmHehP5qISYjYa', '小考呀', NULL, NULL, NULL, 1, 1653385834256, 1653385834256, '1529037128619524096', 'http://1.116.106.177/960f283eea834871888f16a3db7d7b9e.gif', NULL);
INSERT INTO `user` VALUES ('44137149@qq.com', '$2a$10$aMVhFuIuDOkssHo.F9L/tOX50DpBLv8BjHUSWoZgYYkVkUbhAB9BS', '小考呀', NULL, NULL, NULL, 1, 1653577394559, 1653577394559, '1529840590764638208', 'http://1.116.106.177/960f283eea834871888f16a3db7d7b9e.gif', NULL);
INSERT INTO `user` VALUES ('4161695@qq.com', '$2a$10$EJjDTq22mAyuA.6E9m3HbuwhTk91yzdez/VDPZAaN0/5wpn4a8nkq', '小考呀', NULL, NULL, NULL, 1, 1653618938605, 1653618938605, '1530014839122952192', 'http://1.116.106.177/960f283eea834871888f16a3db7d7b9e.gif', NULL);

SET FOREIGN_KEY_CHECKS = 1;
