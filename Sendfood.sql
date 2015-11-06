/*
SQLyog Ultimate v8.32 
MySQL - 5.1.28-rc-community : Database - sendfood
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sendfood` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `sendfood`;

/*Table structure for table `building` */

DROP TABLE IF EXISTS `building`;

CREATE TABLE `building` (
  `building_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '楼栋自增id',
  `school_id` bigint(20) DEFAULT NULL COMMENT '学校自增id',
  `building_name` varchar(50) DEFAULT NULL COMMENT '楼栋名',
  `building_code` varchar(10) DEFAULT NULL COMMENT '楼栋代码',
  `building_status` int(11) DEFAULT NULL COMMENT '楼栋状态，10表示可用，0表示不可用',
  PRIMARY KEY (`building_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `building` */

insert  into `building`(`building_id`,`school_id`,`building_name`,`building_code`,`building_status`) values (14,9,'25栋','25',10),(15,10,'28栋A','28A',10),(16,10,'25栋','25',10),(17,11,'这个是测试数据','test',10),(18,9,'24栋','24',10);

/*Table structure for table `building_staff` */

DROP TABLE IF EXISTS `building_staff`;

CREATE TABLE `building_staff` (
  `staff_id` bigint(20) NOT NULL COMMENT '员工编号',
  `building_id` bigint(20) NOT NULL COMMENT '楼栋编号',
  `building_start_time` datetime DEFAULT NULL COMMENT '管理的起始时间',
  `building_staff_status` int(11) DEFAULT NULL COMMENT '管理状态，0-不可用，10-可用',
  PRIMARY KEY (`staff_id`,`building_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `building_staff` */

insert  into `building_staff`(`staff_id`,`building_id`,`building_start_time`,`building_staff_status`) values (17,15,'2015-08-12 10:25:32',10),(20,14,'2015-08-01 12:52:05',10),(20,16,'2015-08-13 21:40:44',10),(20,17,'2015-08-13 21:41:10',10),(27,18,'2015-08-13 22:09:07',10);

/*Table structure for table `country` */

DROP TABLE IF EXISTS `country`;

CREATE TABLE `country` (
  `country_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country_name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `country_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`country_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `country` */

insert  into `country`(`country_id`,`country_name`,`country_status`) values (1,'总部',10);

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `customer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `school_id` bigint(20) DEFAULT NULL,
  `building_id` bigint(20) DEFAULT NULL,
  `dormitory_id` bigint(20) DEFAULT NULL,
  `customer_name` varchar(10) DEFAULT NULL COMMENT '姓名',
  `customer_tel` varchar(15) DEFAULT NULL COMMENT '电话',
  `customer_money` float DEFAULT NULL COMMENT '余额',
  `customer_email` varchar(15) DEFAULT NULL COMMENT '邮箱',
  `customer_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `customer` */

insert  into `customer`(`customer_id`,`school_id`,`building_id`,`dormitory_id`,`customer_name`,`customer_tel`,`customer_money`,`customer_email`,`customer_status`) values (6,9,14,814,'张三','13618301808',NULL,NULL,0);

/*Table structure for table `default_order` */

DROP TABLE IF EXISTS `default_order`;

CREATE TABLE `default_order` (
  `default_order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `school_id` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `default_order_name` varchar(50) DEFAULT NULL COMMENT '套餐名',
  `default_order_status` int(11) DEFAULT NULL COMMENT '套餐状态',
  PRIMARY KEY (`default_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

/*Data for the table `default_order` */

insert  into `default_order`(`default_order_id`,`school_id`,`item_id`,`default_order_name`,`default_order_status`) values (54,9,NULL,'可用',1),(55,9,NULL,'可用',1),(56,9,NULL,'可用',1),(57,9,NULL,'可用',1),(58,9,NULL,'可用',1),(59,9,NULL,'可用',1),(60,9,NULL,'可用',1),(61,9,NULL,'可用',1),(62,9,NULL,'可用',1),(63,9,NULL,'可用',1),(64,9,NULL,'可用',1),(65,9,NULL,'可用',1),(66,9,NULL,'可用',1),(67,9,NULL,'可用',1),(68,9,NULL,'可用',1),(69,9,NULL,'可用',1),(70,9,NULL,'可用',1),(71,9,NULL,'可用',1),(72,9,NULL,'0807第一期',1),(73,9,NULL,'0807第一期',1),(74,9,NULL,'0807第一期',10);

/*Table structure for table `dormitory` */

DROP TABLE IF EXISTS `dormitory`;

CREATE TABLE `dormitory` (
  `dormitory_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '宿舍自增id',
  `building_id` bigint(20) DEFAULT NULL COMMENT '楼栋自增id',
  `staff_id` bigint(20) DEFAULT NULL COMMENT '配送人员自增id',
  `dormitory_code` varchar(10) DEFAULT NULL COMMENT '宿舍代码',
  `dormitory_name` varchar(50) DEFAULT NULL COMMENT '宿舍名',
  `dormitory_password` varchar(20) DEFAULT NULL COMMENT '宿舍登陆密码',
  `dormitory_status` int(11) DEFAULT NULL COMMENT '宿舍状态，10表示可用，0表示不可用',
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`dormitory_id`)
) ENGINE=InnoDB AUTO_INCREMENT=816 DEFAULT CHARSET=utf8;

/*Data for the table `dormitory` */

insert  into `dormitory`(`dormitory_id`,`building_id`,`staff_id`,`dormitory_code`,`dormitory_name`,`dormitory_password`,`dormitory_status`,`date`) values (814,14,18,'609寝室','609','123456',10,'3'),(815,15,18,'709女生寝室','709','123456',10,'2');

/*Table structure for table `limits` */

DROP TABLE IF EXISTS `limits`;

CREATE TABLE `limits` (
  `limits_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限自增id',
  `limits_name` varchar(20) DEFAULT NULL COMMENT '权限名',
  `limits_note` text COMMENT '权限备注',
  `limits_url` text COMMENT '权限路径',
  `limits_pid` bigint(20) DEFAULT NULL COMMENT '上级权限id',
  `limits_pname` varchar(20) DEFAULT NULL COMMENT '上级权限名',
  `limits_status` int(11) DEFAULT NULL COMMENT '权限状态，10表示可用，0表示不可用',
  PRIMARY KEY (`limits_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `limits` */

insert  into `limits`(`limits_id`,`limits_name`,`limits_note`,`limits_url`,`limits_pid`,`limits_pname`,`limits_status`) values (9,'管理用户','对用户的增删改查','/userManage/*/stuff/stuffList',0,'权限管理',10),(10,'管理角色','对用户的增删改查','/roleManage/*/role/manage',0,'权限管理',10),(11,'管理权限','对权限的增删改查','/limitsManage/*/limits/manageLimits',0,'权限管理',10),(12,'管理用户角色','对用户角色的配置','/roleManage/*/role/manageStaffRole',0,'权限管理',10),(13,'管理角色权限','对角色权限的配置','/limitsManage/*/manageRoleLimits',0,'权限管理',10),(14,'查看组织架构','配置组织和人员','/organizationManage/*/orgStructure/viewOrg',1,'组织架构管理',10),(15,'管理组织架构','新增或修改组织架构和人员','/organizationManage/*/addOrg',1,'组织架构管理',10),(16,'库存管理','对库存的增删改','/food/*/food/storageIndex',2,'库存管理',10),(17,'新增或修改标准订单','对订单的新增或修改','/orderManage/*/orderManage/addStandardOrder',3,'订单管理',10),(18,'管理所有订单','对订单的管理','/orderManage/*/orderManage/manageStandardOrder',3,'订单管理',10),(19,'管理消费者','对消费者的管理','/userManage/*/customer/customer',0,'消费者管理',10);

/*Table structure for table `order_item` */

DROP TABLE IF EXISTS `order_item`;

CREATE TABLE `order_item` (
  `item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `default_order_id` bigint(20) DEFAULT NULL,
  `snacks_id` bigint(20) DEFAULT NULL,
  `snacks_number` int(11) DEFAULT NULL COMMENT '零食数量',
  `snacks_sell_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

/*Data for the table `order_item` */

insert  into `order_item`(`item_id`,`default_order_id`,`snacks_id`,`snacks_number`,`snacks_sell_number`) values (16,35,87,1,NULL),(17,36,87,1,NULL),(18,37,87,1,NULL),(19,37,88,1,NULL),(20,39,87,1,NULL),(21,42,87,1,NULL),(22,45,87,1,NULL),(23,46,88,1,NULL),(24,49,87,1,NULL),(25,50,87,1,NULL),(26,52,87,1,NULL),(27,53,87,1,NULL),(28,54,87,1,NULL),(29,56,87,1,NULL),(30,57,87,10,NULL),(31,57,88,10,NULL),(32,58,87,10,NULL),(33,58,88,10,NULL),(34,59,87,10,NULL),(35,59,88,10,NULL),(36,59,89,10,NULL),(37,60,87,10,NULL),(38,60,88,10,NULL),(39,60,89,10,NULL),(40,61,87,10,NULL),(41,61,88,10,NULL),(42,61,89,10,NULL),(43,62,87,10,NULL),(44,62,88,10,NULL),(45,62,89,10,NULL),(46,63,87,101,NULL),(47,63,88,10,NULL),(48,63,89,10,NULL),(49,64,87,1011,NULL),(50,64,88,10,NULL),(51,64,89,10,NULL),(52,65,87,10,NULL),(53,65,88,10,NULL),(54,65,89,10,NULL),(55,66,87,5,NULL),(56,66,88,10,NULL),(57,66,89,10,NULL),(58,67,87,5,NULL),(59,67,88,10,NULL),(60,67,89,10,NULL),(61,68,87,5,NULL),(62,68,88,10,NULL),(63,69,87,5,NULL),(64,69,88,10,NULL),(65,70,87,5,NULL),(66,70,88,10,NULL),(67,70,89,0,NULL),(68,71,87,5,NULL),(69,71,88,10,NULL),(70,71,89,10,NULL),(71,72,87,0,NULL),(72,73,87,10,NULL),(73,73,88,10,NULL),(74,73,89,10,NULL),(75,74,87,10,NULL),(76,74,88,10,NULL),(77,74,89,10,NULL),(78,74,90,10,NULL);

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_query_id` varchar(20) DEFAULT NULL COMMENT '组成方式为，学校code+楼栋code+寝室code',
  `dormitory_id` bigint(20) DEFAULT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `default_order_id` bigint(20) DEFAULT NULL,
  `order_create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `order_cost_money` float DEFAULT NULL COMMENT '成本金额',
  `order_sell_money` float DEFAULT NULL COMMENT '销售金额',
  `order_note` text COMMENT '备注',
  `order_status` int(11) DEFAULT NULL COMMENT '订单状态，10表示未配送，20表示配送中，30表示配送完成，40表示结算完成，50表示返回入库，60表示已返回入库',
  `order_push_count` int(11) DEFAULT '0' COMMENT '推送次数，包括配送推送和结算推送，每次更新订单状态时将其修改成0',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

insert  into `orders`(`order_id`,`order_query_id`,`dormitory_id`,`staff_id`,`default_order_id`,`order_create_time`,`order_cost_money`,`order_sell_money`,`order_note`,`order_status`,`order_push_count`) values (86,'cqupt-25-609寝室',814,18,56,'2015-08-10 16:35:30',11,12,NULL,40,0),(134,'cqupt-25-609寝室',814,18,74,'2015-08-12 23:50:35',NULL,NULL,NULL,10,1),(135,'cqupt-25-609寝室',814,18,74,'2015-08-12 23:52:13',NULL,NULL,NULL,10,2);

/*Table structure for table `region` */

DROP TABLE IF EXISTS `region`;

CREATE TABLE `region` (
  `region_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '区自增id',
  `region_name` varchar(50) DEFAULT NULL COMMENT '区名',
  `region_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`region_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `region` */

insert  into `region`(`region_id`,`region_name`,`region_status`) values (1,'南岸区',10),(2,'北碚区',10);

/*Table structure for table `region_staff` */

DROP TABLE IF EXISTS `region_staff`;

CREATE TABLE `region_staff` (
  `staff_id` bigint(20) NOT NULL COMMENT '员工编号',
  `region_id` bigint(20) NOT NULL COMMENT '区编号',
  `region_start_time` datetime DEFAULT NULL COMMENT '管理的其实时间',
  `region_staff_status` int(11) DEFAULT NULL COMMENT '管理状态，0-不可用，10-可用',
  PRIMARY KEY (`staff_id`,`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `region_staff` */

insert  into `region_staff`(`staff_id`,`region_id`,`region_start_time`,`region_staff_status`) values (7,1,'2015-08-01 10:54:51',10);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL,
  `role_note` text,
  `role_status` int(11) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='这个是角色表';

/*Data for the table `role` */

insert  into `role`(`role_id`,`role_name`,`role_note`,`role_status`) values (18,'订单管理人员','只管理订单',10),(19,'校级副主管','协助校级主管分管其它工作',10),(20,'财务运营总监','主管整体集团财务与融资',10),(21,'刘智鹏','高级合伙人',10),(22,'校区仓库主管','管理一个校区的库房与物流',10),(25,'测试人员角色','专门给测试用的',10);

/*Table structure for table `roleoflimit` */

DROP TABLE IF EXISTS `roleoflimit`;

CREATE TABLE `roleoflimit` (
  `roleoflimit_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `limits_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`roleoflimit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;

/*Data for the table `roleoflimit` */

insert  into `roleoflimit`(`roleoflimit_id`,`role_id`,`limits_id`) values (39,13,15),(64,12,18),(65,11,9),(66,11,10),(67,11,11),(68,11,12),(69,11,13),(70,11,14),(71,11,15),(72,11,16),(73,11,17),(74,11,18),(75,11,19),(76,18,14),(77,18,15),(78,18,18),(79,18,19),(80,19,9),(81,19,14),(82,19,16),(83,19,17),(84,19,18),(85,19,19),(86,20,14),(87,20,16),(88,20,17),(89,20,18),(90,20,19),(91,22,14),(92,22,16),(93,23,14),(94,23,16),(95,23,17),(96,23,18),(97,23,19),(98,25,9),(99,25,14),(100,25,15),(101,25,16),(102,25,17),(103,25,18),(104,25,19);

/*Table structure for table `school` */

DROP TABLE IF EXISTS `school`;

CREATE TABLE `school` (
  `school_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '学校自增id',
  `region_id` bigint(20) DEFAULT NULL,
  `school_name` varchar(50) DEFAULT NULL COMMENT '学校名',
  `school_code` varchar(10) DEFAULT NULL COMMENT '学校代码',
  `school_status` int(11) DEFAULT NULL COMMENT '学校状态，10表示可用，0表示不可用',
  `defaultorder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `school` */

insert  into `school`(`school_id`,`region_id`,`school_name`,`school_code`,`school_status`,`defaultorder_id`) values (9,1,'重庆邮电大学','cqupt',10,NULL),(10,1,'重庆工商大学','cqgs',10,NULL),(11,1,'重庆第二师范大学','cadier',10,NULL),(12,1,'重庆大学','cqpt',10,NULL);

/*Table structure for table `school_staff` */

DROP TABLE IF EXISTS `school_staff`;

CREATE TABLE `school_staff` (
  `staff_id` bigint(20) NOT NULL COMMENT '员工编号',
  `school_id` bigint(20) NOT NULL COMMENT '学校编号',
  `school_start_time` datetime DEFAULT NULL COMMENT '管理的起始时间',
  `school_staff_status` int(11) DEFAULT NULL COMMENT '管理状态，0-不可用，10-可用',
  PRIMARY KEY (`staff_id`,`school_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `school_staff` */

insert  into `school_staff`(`staff_id`,`school_id`,`school_start_time`,`school_staff_status`) values (20,9,'2015-08-01 11:20:30',10),(20,11,'2015-08-13 21:40:25',10),(20,12,'2015-08-13 21:41:28',10),(26,10,'2015-08-12 10:24:34',10),(27,9,'2015-08-13 22:08:49',10);

/*Table structure for table `sell_detail` */

DROP TABLE IF EXISTS `sell_detail`;

CREATE TABLE `sell_detail` (
  `order_id` bigint(20) NOT NULL,
  `sell_item_id` bigint(20) NOT NULL,
  PRIMARY KEY (`order_id`,`sell_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sell_detail` */

insert  into `sell_detail`(`order_id`,`sell_item_id`) values (126,1),(127,2);

/*Table structure for table `sell_item` */

DROP TABLE IF EXISTS `sell_item`;

CREATE TABLE `sell_item` (
  `sell_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `snacks_id` bigint(20) DEFAULT NULL,
  `snacks_number` int(11) DEFAULT NULL COMMENT '零食数量',
  `order_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sell_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8;

/*Data for the table `sell_item` */

insert  into `sell_item`(`sell_item_id`,`snacks_id`,`snacks_number`,`order_id`) values (1,87,1,126),(2,88,2,127),(147,87,1,86);

/*Table structure for table `snacks` */

DROP TABLE IF EXISTS `snacks`;

CREATE TABLE `snacks` (
  `snacks_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `storage_id` bigint(20) DEFAULT NULL,
  `snacks_name` varchar(50) DEFAULT NULL COMMENT '零食名',
  `snacks_bar_code` varchar(20) DEFAULT NULL COMMENT '零食条形码',
  `snacks_pic` varchar(50) DEFAULT NULL COMMENT '零食图片路径',
  `snacks_cost_price` float DEFAULT NULL COMMENT '零食成本价',
  `snacks_sell_price` float DEFAULT NULL COMMENT '零食销售价',
  `snacks_stock_number` int(11) DEFAULT NULL COMMENT '零食库存数量',
  `snacks_status` int(11) DEFAULT NULL COMMENT '零食状态，10表示可用，0表示不可用',
  PRIMARY KEY (`snacks_id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;

/*Data for the table `snacks` */

insert  into `snacks`(`snacks_id`,`storage_id`,`snacks_name`,`snacks_bar_code`,`snacks_pic`,`snacks_cost_price`,`snacks_sell_price`,`snacks_stock_number`,`snacks_status`) values (87,5,'可口可乐','123456',NULL,11,12,10,10),(88,5,'薯片','122',NULL,1,2,123,10),(89,5,'饼干','123466',NULL,10,12,10,10),(90,5,'可乐1','12345',NULL,11,12,100,10),(91,5,'可乐2','111',NULL,12,20,10,10),(92,5,'可乐3','111',NULL,11,19,11,10),(93,6,'1','2',NULL,1,2,2,10),(94,6,'3','4',NULL,4,5,4,10);

/*Table structure for table `staff` */

DROP TABLE IF EXISTS `staff`;

CREATE TABLE `staff` (
  `staff_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `staff_name` varchar(15) DEFAULT NULL COMMENT '姓名',
  `staff_tel` varchar(15) DEFAULT NULL COMMENT '电话',
  `staff_password` varchar(20) DEFAULT NULL COMMENT '登陆密码',
  `staff_rank` int(11) DEFAULT NULL COMMENT '本字段用来区别用户的管理权限，1表示仓库管理员，10表示管理寝室，20表示楼栋，30表示学校，40表示区县，再通过管理部分关联自己负责的部分',
  `staff_manage_partid` bigint(20) DEFAULT NULL COMMENT '外联他所负责的部分自增的id，如宿舍，楼栋，学校，南岸区，仓库自增id',
  `staff_email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `staff_join_time` datetime DEFAULT NULL COMMENT '创建时间',
  `staff_status` int(11) DEFAULT NULL COMMENT '员工状态，10表示正常，0表示离职',
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Data for the table `staff` */

insert  into `staff`(`staff_id`,`staff_name`,`staff_tel`,`staff_password`,`staff_rank`,`staff_manage_partid`,`staff_email`,`staff_join_time`,`staff_status`) values (2,'华东南','15213309887','316586',50,1,'375903883@qq.com','2015-05-24 14:00:29',0),(7,'吉鹏','1111','1111',40,1,'360514706@qq.com','2015-06-04 00:00:00',10),(8,'费知情','123456','12345',20,40,'232','2015-06-04 00:00:00',10),(16,'王葶亦','123321','88888888',40,1,'123321','2015-07-08 22:39:07',0),(17,'陈龙','13627696670','88888888',20,NULL,'330516538@qq.com','2015-07-16 21:47:49',10),(18,'付雪君','1234','88888888',10,NULL,'316980890@qq.com','2015-07-17 01:44:44',10),(19,'杨润峰','18280172330','88888888',0,NULL,'475267401@qq.com','2015-07-18 01:02:47',0),(20,'刘智鹏','13629722691','88888888',40,1,'123@qq.com','2015-07-19 14:10:19',10),(21,'梁志勇','15123339065','88888888',40,1,'1948944890@qq.com','2015-07-26 20:27:36',10),(25,'张三','11','88888888',0,NULL,'11','2015-08-07 19:18:08',10),(26,'李四','113','88888888',30,NULL,'11','2015-08-07 19:18:25',10),(27,'封传陶','18883856739','123',30,NULL,'18883856739','2015-08-13 22:06:52',10);

/*Table structure for table `staff_role` */

DROP TABLE IF EXISTS `staff_role`;

CREATE TABLE `staff_role` (
  `role_id` bigint(20) NOT NULL,
  `staff_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `staff_role` */

insert  into `staff_role`(`role_id`,`staff_id`) values (11,2),(11,7),(11,8),(11,9),(11,12),(11,13),(13,11),(13,15),(19,0),(19,17),(19,18),(20,19),(24,24),(25,27);

/*Table structure for table `storage` */

DROP TABLE IF EXISTS `storage`;

CREATE TABLE `storage` (
  `storage_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `school_id` bigint(20) DEFAULT NULL,
  `storage_name` varchar(50) DEFAULT NULL COMMENT '仓库名',
  PRIMARY KEY (`storage_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `storage` */

insert  into `storage`(`storage_id`,`school_id`,`storage_name`) values (5,9,'重庆邮电大学仓库'),(6,10,'重庆工商大学仓库'),(7,11,'重庆第二师范大学仓库'),(8,12,'重庆大学仓库');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
