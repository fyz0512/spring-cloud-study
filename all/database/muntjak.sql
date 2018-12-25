-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.59-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 muntjak 的数据库结构
CREATE DATABASE IF NOT EXISTS `muntjak` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `muntjak`;

-- 导出  表 muntjak.sys_dict 结构
CREATE TABLE IF NOT EXISTS `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) NOT NULL COMMENT '参数名',
  `value` varchar(100) DEFAULT NULL COMMENT '参数值',
  `type` varchar(100) DEFAULT NULL COMMENT '类型',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序（升序）',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父级编号',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0:无效，1：有效',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- 正在导出表  muntjak.sys_dict 的数据：~16 rows (大约)
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remark`, `status`) VALUES
	(1, '性别', 'sex', 'sex_type', '性别', 2, 0, 1, NULL, 1, NULL, NULL, 1),
	(2, '用户状态', 'user_status', 'user_status', '用户状态', 3, 0, 1, NULL, NULL, NULL, NULL, 1),
	(3, '正常', '1', 'user_status', '用户可以正常使用', 10, 2, 1, NULL, 1, NULL, NULL, 1),
	(4, '禁用', '0', 'user_status', '用户无法登录', 20, 2, 1, NULL, 1, NULL, NULL, 1),
	(5, '是', '1', 'yes_no', '是/否', 10, 0, 1, NULL, 1, NULL, NULL, 1),
	(6, '否', '0', 'yes_no', '是/否', 20, 0, 1, NULL, 1, NULL, NULL, 1),
	(7, '默认密码', '888888', 'defaultPwd', '重置密码时的默认密码', 10, 0, 1, NULL, 1, NULL, NULL, 1),
	(8, '菜单类型', 'menu_type', 'menu', '系统菜单', 10, 0, 1, NULL, 1, NULL, NULL, 1),
	(9, '固定功能', '-1', 'menu_type', '菜单类型', 0, 8, 1, NULL, 1, NULL, NULL, 1),
	(10, '系统', '0', 'menu_type', '菜单类型', 1, 8, 1, NULL, 1, NULL, NULL, 1),
	(11, '模块', '1', 'menu_type', '菜单类型', 2, 8, 1, NULL, 1, NULL, NULL, 1),
	(12, '按钮', '2', 'menu_type', '菜单类型', 3, 8, 1, NULL, 1, NULL, NULL, 1),
	(13, '附加功能', '3', 'menu_type', '菜单类型', 4, 8, 1, NULL, 1, NULL, NULL, 1),
	(14, '男', '1', 'sex', '性别', 10, 1, 1, NULL, 1, NULL, NULL, 1),
	(15, '女', '2', 'sex', '性别', 20, 1, 1, NULL, 1, NULL, NULL, 1),
	(16, '菜单图标', 'icon', 'menu_icon', '菜单图标', 4, 0, 1, NULL, NULL, NULL, NULL, 1);
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;

-- 导出  表 muntjak.sys_menu 结构
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0，固定功能为-1',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `permit` varchar(200) DEFAULT NULL COMMENT '菜单许可',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型   -1: 固定功能 0：目录   1：菜单   2：按钮  3:附加功能',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `component` varchar(200) DEFAULT NULL COMMENT '组件',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最近修改时间',
  `user_id_create` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户',
  `user_id_modified` bigint(20) DEFAULT '0' COMMENT '最近修改用户',
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `rightname` (`permit`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 COMMENT='菜单信息';

-- 正在导出表  muntjak.sys_menu 的数据：~41 rows (大约)
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `permit`, `url`, `type`, `icon`, `order_num`, `component`, `gmt_create`, `gmt_modified`, `user_id_create`, `user_id_modified`) VALUES
	(2, 3, '系统菜单', 'admin:menu', '/service-admin/menu/list', 1, 'fa fa-th-list', 2, '/admin/menu.html', '2017-08-09 22:55:15', NULL, 0, 0),
	(3, 0, '权限管理', 'admin', NULL, 0, 'fa fa-desktop', 1, NULL, '2017-08-09 23:06:55', '2017-08-14 14:13:43', 0, 0),
	(6, 3, '用户管理', 'admin:user', '/service-admin/user/list', 1, 'fa fa-user', 0, '/admin/user.html', '2017-08-10 00:00:00', NULL, 0, 0),
	(7, 3, '角色管理', 'admin:role', '/service-admin/role/list', 1, 'fa fa-users', 1, '/admin/role.html', '2017-08-10 00:00:00', NULL, 0, 0),
	(8, 2, '菜单树', 'admin:menu:menuTree', '/service-admin/menu/menuTree', 3, NULL, 3, NULL, '2018-12-20 11:42:35', NULL, 0, 0),
	(12, 6, '新增', 'admin:user:add', '/service-admin/user/add', 2, 'fa fa-plus', 0, '/admin/user_add.html', '2017-08-14 00:00:00', NULL, 0, 0),
	(13, 6, '编辑', 'admin:user:edit', '/service-admin/user/edit', 2, 'fa fa-pencil', 0, '/admin/user_edit.html', '2017-08-14 00:00:00', NULL, 0, 0),
	(14, 6, '删除', 'admin:user:del', '/service-admin/user/del', 2, 'fa fa-minus', 0, NULL, '2017-08-14 00:00:00', NULL, 0, 0),
	(15, 7, '新增', 'admin:role:add', '/service-admin/role/add', 2, 'fa fa-plus', 0, '/admin/role_add.html', '2017-08-14 00:00:00', NULL, 0, 0),
	(16, 6, '重名校验', 'admin:user:uniqname', '/service-admin/user/uniqName', 3, NULL, 1, NULL, '2018-11-16 23:20:42', NULL, 0, 0),
	(17, 6, '详细', 'admin:user:get', '/service-admin/user/get', 3, NULL, NULL, NULL, '2018-12-04 20:31:36', NULL, 0, 0),
	(18, 7, '详细', 'admin:role:get', '/service-admin/role/get', 3, NULL, NULL, NULL, '2018-12-04 20:32:32', NULL, 0, 0),
	(19, 3, '详细', 'admin:menu:get', '/service-admin/menu/get', 3, NULL, NULL, NULL, NULL, NULL, 0, 0),
	(20, 2, '新增', 'admin:menu:add', '/service-admin/menu/add', 2, 'fa fa-plus', 0, '/admin/menu_add.html', '2017-08-14 00:00:00', NULL, 0, 0),
	(21, 2, '编辑', 'admin:menu:edit', '/service-admin/menu/edit', 2, 'fa fa-pencil', 0, '/admin/menu_edit.html', '2017-08-14 00:00:00', NULL, 0, 0),
	(22, 2, '删除', 'admin:menu:del', '/service-admin/menu/del', 2, 'fa fa-minus', 0, NULL, '2017-08-14 00:00:00', NULL, 0, 0),
	(23, 7, '设置菜单', 'admin:role:menu', '/service-admin/role/setMenu', 2, 'fa fa-puzzle-piece', 3, '/admin/role_menu.html', '2018-12-17 16:10:49', NULL, 0, 0),
	(24, 6, '启用', 'admin:user:enable', '/service-admin/user/enable', 2, 'fa fa-play', 0, NULL, '2017-08-14 00:00:00', NULL, 0, 0),
	(25, 6, '停用', 'admin:user:forbidden', '/service-admin/user/forbidden', 2, 'fa fa-stop', 0, NULL, '2017-08-14 00:00:00', NULL, 0, 0),
	(26, 6, '重置密码', 'admin:user:resetPwd', '/service-admin/user/resetPwd', 2, 'fa fa-key', 0, NULL, '2017-08-14 00:00:00', NULL, 0, 0),
	(55, 7, '编辑', 'admin:role:edit', '/service-admin/role/edit', 2, '', NULL, '/admin/role_edit.html', NULL, NULL, 0, 0),
	(56, 7, '删除', 'admin:role:del', '/service-admin/role/del', 2, NULL, NULL, NULL, NULL, NULL, 0, 0),
	(61, 2, '重名校验', 'admin:menu:uniqname', '/service-admin/menu/uniqName', 3, NULL, NULL, NULL, NULL, NULL, 0, 0),
	(62, 7, '重名校验', 'admin:role:uniqname', '/service-admin/role/uniqName', 3, NULL, NULL, NULL, NULL, NULL, 0, 0),
	(74, 90, '清除缓存', 'system:sysDept:add', '/service-admin/menu/clearCache', 1, 'fa fa-recycle', 1, NULL, NULL, NULL, 0, 0),
	(76, 6, '设置角色', 'admin:user:role', '/service-admin/user/setRole', 2, 'fa fa-user-plus', 6, '/admin/user_role.html', NULL, NULL, 0, 0),
	(78, 0, '基础数据', 'base', NULL, 0, 'fa fa-th-list', 2, NULL, NULL, NULL, 0, 0),
	(80, 78, '数据字典', 'base:dict', '/service-base/dictionary/list', 1, 'fa fa-book', NULL, NULL, NULL, NULL, 0, 0),
	(81, 78, '运行参数', 'base:param', '/service-base/param/list', 1, 'fa fa-cogs', NULL, NULL, NULL, NULL, 0, 0),
	(83, 78, 'OA流程', 'base:oa', '/service-base/oa/list', 1, 'fa fa-cubes', NULL, NULL, NULL, NULL, 0, 0),
	(84, 0, '日志信息', 'log', NULL, 0, 'fa fa-bars', 3, NULL, NULL, NULL, 0, 0),
	(85, 84, '系统日志', 'log:sys', '/service-base/log/list', 1, 'fa fa-file-text-o', NULL, NULL, NULL, NULL, 0, 0),
	(88, 0, '系统监控', 'monitor', NULL, 0, 'fa fa-video-camera', 4, NULL, NULL, NULL, 0, 0),
	(89, 88, '服务状态', 'monitor:state', '/service-base/state/list', 1, 'fa fa-server', NULL, NULL, NULL, NULL, 0, 0),
	(90, 0, '系统工具', 'tools', NULL, 0, 'fa fa-wrench', 5, NULL, NULL, NULL, 0, 0),
	(91, NULL, '用户菜单', 'usermenu', '/service-admin/menu/userMenus', -1, NULL, NULL, NULL, NULL, NULL, 0, 0),
	(92, NULL, '登出系统', 'logout', '/service-admin/logout', -1, NULL, NULL, NULL, NULL, NULL, 0, 0),
	(93, NULL, '修改密码', 'changePass', '/service-admin/user/modiPass', -1, NULL, NULL, NULL, NULL, NULL, 0, 0),
	(94, NULL, '重载菜单', 'reload', '/service-admin/reload', -1, NULL, NULL, NULL, NULL, NULL, 0, 0),
	(95, NULL, '首页', 'home', '/main.html', -1, NULL, NULL, '/main.html', NULL, NULL, 0, 0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

-- 导出  表 muntjak.sys_msgs 结构
CREATE TABLE IF NOT EXISTS `sys_msgs` (
  `msg_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msg_type` tinyint(1) DEFAULT '0' COMMENT '类型 0:系统信息，1:用户信息',
  `msg_level` tinyint(1) DEFAULT '0' COMMENT '等级 0:最低级，随着级数增加，越高级越重要。',
  `msg_content` text COMMENT '消息内容',
  `send_user` bigint(20) DEFAULT '0' COMMENT '发信人',
  `send_time` datetime NOT NULL COMMENT '发信时间',
  `recv_user` bigint(20) NOT NULL COMMENT '收信人',
  `read_time` datetime DEFAULT NULL COMMENT '读信时间',
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统消息';

-- 正在导出表  muntjak.sys_msgs 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_msgs` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_msgs` ENABLE KEYS */;

-- 导出  表 muntjak.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(100) DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `user_id_modify` bigint(20) DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='角色';

-- 正在导出表  muntjak.sys_role 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`role_id`, `role_name`, `role_sign`, `remark`, `user_id_create`, `gmt_create`, `user_id_modify`, `gmt_modified`) VALUES
	(1, '管理员角色', 'admin', '拥有最高权限', 2, '2017-08-12 00:00:00', NULL, '2017-08-12 00:00:00'),
	(48, '钻石会员', 'diamond', '高级用户', NULL, NULL, NULL, NULL),
	(56, '普通用户', 'ordinary', '普通用户，没啥权限', NULL, NULL, 2, '2018-12-06 12:22:33'),
	(57, '技术中心管理员', 'tech_admin', '技术中心负责维护的管理人员', NULL, '2018-12-06 12:05:41', NULL, NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- 导出  表 muntjak.sys_role_menu 结构
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色许可的菜单';

-- 正在导出表  muntjak.sys_role_menu 的数据：~39 rows (大约)
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
	(1, 2),
	(1, 3),
	(1, 6),
	(1, 7),
	(1, 8),
	(1, 12),
	(1, 13),
	(1, 14),
	(1, 15),
	(1, 16),
	(1, 17),
	(1, 18),
	(1, 19),
	(1, 20),
	(1, 21),
	(1, 22),
	(1, 23),
	(1, 24),
	(1, 25),
	(1, 26),
	(1, 55),
	(1, 56),
	(1, 61),
	(1, 62),
	(1, 74),
	(1, 76),
	(1, 77),
	(1, 78),
	(1, 80),
	(1, 81),
	(1, 82),
	(1, 83),
	(1, 84),
	(1, 85),
	(1, 86),
	(1, 87),
	(1, 88),
	(1, 89),
	(1, 90);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;

-- 导出  表 muntjak.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态 0:禁用，1:正常',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `sex` bigint(32) DEFAULT NULL COMMENT '性别',
  `birth` datetime DEFAULT NULL COMMENT '出生日期',
  `pic_id` bigint(32) DEFAULT NULL COMMENT '照片编号',
  `live_address` varchar(500) DEFAULT NULL COMMENT '现居住地',
  `hobby` varchar(255) DEFAULT NULL COMMENT '爱好',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '所在城市',
  `district` varchar(255) DEFAULT NULL COMMENT '所在地区',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8 COMMENT='用户信息';

-- 正在导出表  muntjak.sys_user 的数据：~17 rows (大约)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`user_id`, `username`, `name`, `password`, `email`, `mobile`, `status`, `user_id_create`, `gmt_create`, `gmt_modified`, `sex`, `birth`, `pic_id`, `live_address`, `hobby`, `province`, `city`, `district`) VALUES
	(1, 'admin', '超级管理员', '21218cca77804d2ba1922c33e0151105', 'admin@muntjak.net', '13912345678', 1, 1, '2017-08-15 21:40:39', '2017-08-15 21:41:00', 1, '2017-12-14 00:00:00', 138, 'ccc', '', '北京市', '北京市市辖区', '东城区'),
	(2, 'test', '临时用户', 'e10adc3949ba59abbe56e057f20f883e', 'test@muntjak.net', '13412345678', 1, 1, '2017-08-14 13:43:05', '2017-08-14 21:15:36', 1, '2018-08-06 00:00:00', NULL, '广东省深圳市', '喝酒', NULL, NULL, NULL),
	(36, 'ldh', '刘德华', 'e10adc3949ba59abbe56e057f20f883e', 'ldh@muntjak.net', '', 1, NULL, NULL, NULL, 1, '2018-08-06 15:46:50', NULL, NULL, NULL, NULL, NULL, NULL),
	(123, 'zxy', '张学友', 'e10adc3949ba59abbe56e057f20f883e', 'zxy@muntjak.net', NULL, 1, NULL, NULL, NULL, 1, '2018-08-06 15:46:48', NULL, NULL, NULL, NULL, NULL, NULL),
	(124, 'gfc', '郭富城', 'e10adc3949ba59abbe56e057f20f883e', 'gfc@muntjak.net', '', 0, NULL, NULL, NULL, 1, '2018-04-11 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL),
	(130, 'hf', '胡斐', '21218cca77804d2ba1922c33e0151105', 'hf@muntjak.net', '', 1, NULL, NULL, NULL, 1, '2018-04-11 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL),
	(131, 'lhc', '令狐冲', 'e10adc3949ba59abbe56e057f20f883e', 'lhc@muntjak.net', NULL, 0, NULL, NULL, NULL, 1, '2018-04-11 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL),
	(132, 'ybq', '岳不群', 'e10adc3949ba59abbe56e057f20f883e', 'ybq@muntjak.net', NULL, 1, NULL, NULL, NULL, 2, '2018-03-15 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL),
	(134, 'ldz', '李大嘴', 'e10adc3949ba59abbe56e057f20f883e', 'ldz@muntjak.net', NULL, 1, NULL, NULL, NULL, 1, '2018-03-18 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL),
	(135, 'ryy', '任盈盈', 'e10adc3949ba59abbe56e057f20f883e', 'ryy@muntjak.net', NULL, 1, NULL, NULL, NULL, 2, '2018-03-19 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL),
	(136, 'gdg2', '郭德纲', 'e10adc3949ba59abbe56e057f20f883e', 'gdg@muntjak.net', NULL, 1, NULL, NULL, NULL, 1, '2018-03-07 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL),
	(156, 'test2', '测试二', 'e10adc3949ba59abbe56e057f20f883e', 'test2@muntjak.net', NULL, 1, NULL, NULL, NULL, 1, '2018-03-06 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL),
	(157, 'abcde', '阿土伯', '21218cca77804d2ba1922c33e0151105', 'atb@muntjak.net', NULL, 1, NULL, NULL, NULL, 1, '2018-08-06 15:46:46', NULL, NULL, NULL, NULL, NULL, NULL),
	(158, 'lxy', '李逍遥', '21218cca77804d2ba1922c33e0151105', 'lxy@muntjak.net', '11387654321', 1, NULL, NULL, NULL, 1, '1999-01-01 00:00:00', NULL, '广东省广州市', '爱好广泛', NULL, NULL, NULL),
	(159, 'lb', '刘备', '21218cca77804d2ba1922c33e0151105', 'lb@muntjak.net', '12312345678', 1, NULL, NULL, NULL, 1, '1999-01-01 00:00:00', NULL, '广东省广州市', '爱哭', NULL, NULL, NULL),
	(160, 'lbai', '李白', '21218cca77804d2ba1922c33e0151105', '', '', 1, NULL, NULL, NULL, 1, '1099-01-01 00:00:00', NULL, '四川省广元市', '', NULL, NULL, NULL),
	(168, 'hz', '黄忠', '21218cca77804d2ba1922c33e0151105', '', '', 1, NULL, NULL, NULL, 1, NULL, NULL, '', '', NULL, NULL, NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- 导出  表 muntjak.sys_user_role 结构
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户拥有的角色';

-- 正在导出表  muntjak.sys_user_role 的数据：~22 rows (大约)
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
	(1, 1),
	(2, 1),
	(36, 1),
	(36, 48),
	(36, 56),
	(123, 48),
	(123, 56),
	(124, 48),
	(124, 56),
	(130, 56),
	(131, 1),
	(131, 48),
	(134, 1),
	(134, 48),
	(134, 56),
	(135, 1),
	(136, 1),
	(156, 1),
	(157, 56),
	(158, 48),
	(158, 56),
	(159, 48);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
