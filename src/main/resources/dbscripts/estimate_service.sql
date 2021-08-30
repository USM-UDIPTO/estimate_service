/*
SQLyog Community v13.1.3  (64 bit)
MySQL - 8.0.21 : Database - estimate-service
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
USE `estimate-service`;

/*Table structure for table `dept_estimate_type` */

DROP TABLE IF EXISTS `dept_estimate_type`;

CREATE TABLE `dept_estimate_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dept_id` bigint NOT NULL,
  `flow_type` varchar(255) DEFAULT NULL,
  `estimate_type_id` bigint DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_dept_estimate_type__estimate_type_id` (`estimate_type_id`),
  CONSTRAINT `fk_dept_estimate_type__estimate_type_id` FOREIGN KEY (`estimate_type_id`) REFERENCES `estimate_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `dept_head_of_account` */

DROP TABLE IF EXISTS `dept_head_of_account`;

CREATE TABLE `dept_head_of_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dept_id` bigint NOT NULL,
  `head_of_account` varchar(255) NOT NULL,
  `hoa_description` varchar(255) DEFAULT NULL,
  `active_yn` bit(1) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `estimate_type` */

DROP TABLE IF EXISTS `estimate_type`;

CREATE TABLE `estimate_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estimate_type_value` varchar(255) NOT NULL,
  `value_type` varchar(255) NOT NULL,
  `active_yn` bit(1) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `scheme_code` */

DROP TABLE IF EXISTS `scheme_code`;

CREATE TABLE `scheme_code` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `location_id` bigint NOT NULL,
  `schemetype` varchar(255) NOT NULL,
  `scheme_code` varchar(255) NOT NULL,
  `scheme_status` varchar(255) DEFAULT NULL,
  `work_name` varchar(255) DEFAULT NULL,
  `work_val` varchar(255) DEFAULT NULL,
  `source_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `sub_estimate` */

DROP TABLE IF EXISTS `sub_estimate`;

CREATE TABLE `sub_estimate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_id` bigint NOT NULL,
  `sor_wor_category_id` bigint DEFAULT NULL,
  `sub_estimate_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `estimate_total` decimal(21,2) DEFAULT NULL,
  `area_weightage_id` bigint DEFAULT NULL,
  `area_weightage_circle` bigint DEFAULT NULL,
  `area_weightage_description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `completed_yn` tinyint(1) DEFAULT '0',
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_sub_estimate__work_estimate_id` (`work_estimate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_category` */

DROP TABLE IF EXISTS `work_category`;

CREATE TABLE `work_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  `category_code` varchar(255) NOT NULL,
  `active_yn` bit(1) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_category_attribute` */

DROP TABLE IF EXISTS `work_category_attribute`;

CREATE TABLE `work_category_attribute` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `attribute_name` varchar(255) NOT NULL,
  `work_category_id` bigint DEFAULT NULL,
  `work_type_id` bigint DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_work_category_attribute__work_category_id` (`work_category_id`),
  KEY `fk_work_category_attribute__work_type_id` (`work_type_id`),
  CONSTRAINT `fk_work_category_attribute__work_category_id` FOREIGN KEY (`work_category_id`) REFERENCES `work_category` (`id`),
  CONSTRAINT `fk_work_category_attribute__work_type_id` FOREIGN KEY (`work_type_id`) REFERENCES `work_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_estimate` */

DROP TABLE IF EXISTS `work_estimate`;

CREATE TABLE `work_estimate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dept_id` bigint NOT NULL,
  `location_id` bigint NOT NULL,
  `file_number` varchar(255) NOT NULL,
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `estimate_type_id` bigint NOT NULL,
  `wotk_type_id` bigint NOT NULL,
  `work_category_id` bigint NOT NULL,
  `work_category_attribute` bigint DEFAULT NULL,
  `work_category_attribute_value` decimal(21,2) DEFAULT NULL,
  `budget_reference_id` bigint DEFAULT NULL,
  `admin_sanction_accorded_yn` tinyint(1) DEFAULT '0',
  `tech_sanction_accorded_yn` tinyint(1) DEFAULT '0',
  `line_estimate_total` decimal(21,2) DEFAULT NULL,
  `estimate_total` decimal(21,2) DEFAULT NULL,
  `lumpsum_total` decimal(21,2) DEFAULT NULL,
  `addl_overhead_total` decimal(21,2) DEFAULT NULL,
  `normal_overhead_total` decimal(21,2) DEFAULT NULL,
  `group_overhead_total` decimal(21,2) DEFAULT NULL,
  `admin_sanction_ref_number` varchar(255) DEFAULT NULL,
  `admin_sanction_ref_date` datetime(6),
  `admin_sanctioned_date` datetime(6),
  `tech_sanction_ref_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `tech_sanctioned_date` datetime(6),
  `approved_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `approved_ts` datetime(6),
  `hkrdb_funded_yn` tinyint(1) DEFAULT '0',
  `scheme_id` bigint DEFAULT NULL,
  `approved_budget_yn` tinyint(1) NOT NULL DEFAULT '0',
  `grant_allocated_amount` decimal(21,2) DEFAULT NULL,
  `document_reference` varchar(255) DEFAULT NULL,
  `provisional_amount` decimal(21,2) DEFAULT NULL,
  `head_of_account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_work_estimate__work_estimate_number` (`work_estimate_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_estimate_item` */

DROP TABLE IF EXISTS `work_estimate_item`;

CREATE TABLE `work_estimate_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sub_estimate_id` bigint NOT NULL,
  `cat_work_sor_item_id` bigint DEFAULT NULL,
  `work_category_id` bigint DEFAULT NULL,
  `entry_order` int DEFAULT NULL,
  `item_code` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `uom_id` bigint NOT NULL,
  `base_rate` decimal(21,2) NOT NULL,
  `final_rate` decimal(21,2) DEFAULT NULL,
  `quantity` decimal(21,2) DEFAULT NULL,
  `floor_number` int DEFAULT NULL,
  `labour_rate` decimal(21,2) DEFAULT NULL,
  `lbd_performed_yn` bit(1) DEFAULT NULL,
  `ra_performed_yn` bit(1) DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_work_estimate_item__sub_estimate_id` (`sub_estimate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_estimate_item_lbd` */

DROP TABLE IF EXISTS `work_estimate_item_lbd`;

CREATE TABLE `work_estimate_item_lbd` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_item_id` bigint NOT NULL,
  `lbd_particulars` varchar(255) DEFAULT NULL,
  `lbd_nos` decimal(21,2) NOT NULL,
  `lbd_length` decimal(21,2) DEFAULT NULL,
  `lbd_length_formula` varchar(255) DEFAULT NULL,
  `lbd_bredth` decimal(21,2) DEFAULT NULL,
  `lbd_bredth_formula` varchar(255) DEFAULT NULL,
  `lbd_depth` decimal(21,2) DEFAULT NULL,
  `lbd_depth_formula` varchar(255) DEFAULT NULL,
  `lbd_quantity` decimal(21,2) NOT NULL,
  `lbd_total` decimal(21,2) DEFAULT NULL,
  `addition_deduction` varchar(255) DEFAULT NULL,
  `calculated_yn` bit(1) DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_work_estimate_item_lbd__work_estimate_item_id` (`work_estimate_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_location` */

DROP TABLE IF EXISTS `work_location`;

CREATE TABLE `work_location` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sub_estimate_id` bigint NOT NULL,
  `district_id` bigint DEFAULT NULL,
  `taluq_id` bigint DEFAULT NULL,
  `loksabha_cont_id` bigint DEFAULT NULL,
  `assembly_cont_id` bigint DEFAULT NULL,
  `location_description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `latitude_degrees` int DEFAULT NULL,
  `latitude_minutes` int DEFAULT NULL,
  `latitude_seconds` int DEFAULT NULL,
  `longitude_degrees` int DEFAULT NULL,
  `longitude_minutes` int DEFAULT NULL,
  `longitude_seconds` int DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_work_location__sub_estimate_id` (`sub_estimate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_sub_estimate_group` */

DROP TABLE IF EXISTS `work_sub_estimate_group`;

CREATE TABLE `work_sub_estimate_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_id` bigint NOT NULL,
  `description` varchar(255) NOT NULL,
  `overhead_total` decimal(21,2) DEFAULT NULL,
  `lumpsum_total` decimal(21,2) DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_work_sub_estimate_group__work_estimate_id` (`work_estimate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_sub_estimate_group_lumpsum` */

DROP TABLE IF EXISTS `work_sub_estimate_group_lumpsum`;

CREATE TABLE `work_sub_estimate_group_lumpsum` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_sub_estimate_group_id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  `approx_rate` decimal(21,2) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_group_lumpsum__work_sub_estimate_group_id` (`work_sub_estimate_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_sub_estimate_group_overhead` */

DROP TABLE IF EXISTS `work_sub_estimate_group_overhead`;

CREATE TABLE `work_sub_estimate_group_overhead` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_sub_estimate_group_id` bigint NOT NULL,
  `description` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `value_type` varchar(255) NOT NULL,
  `entered_value` decimal(21,2) DEFAULT NULL,
  `value_fixed_yn` bit(1) NOT NULL,
  `construct` varchar(255) DEFAULT NULL,
  `overhead_value` decimal(21,2) NOT NULL,
  `final_yn` bit(1) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_group_overhead__work_sub_estimate_group_id` (`work_sub_estimate_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_type` */

DROP TABLE IF EXISTS `work_type`;

CREATE TABLE `work_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_type_name` varchar(255) NOT NULL,
  `work_type_value` varchar(255) NOT NULL,
  `value_type` varchar(255) NOT NULL,
  `active_yn` bit(1) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
