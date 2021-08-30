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
/*Table structure for table `ra_configuration` */

USE `estimate-service`;

DROP TABLE IF EXISTS `ra_configuration`;

CREATE TABLE `ra_configuration` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dept_id` bigint NOT NULL,
  `ra_param_id` bigint NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_ts` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `ra_formula` */

DROP TABLE IF EXISTS `ra_formula`;

CREATE TABLE `ra_formula` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dept_id` bigint NOT NULL,
  `work_type` varchar(50) DEFAULT NULL,
  `formula` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `aw_formula` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `royalty_formula` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_ts` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `ra_parameters` */

DROP TABLE IF EXISTS `ra_parameters`;

CREATE TABLE `ra_parameters` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `param_name` varchar(50) DEFAULT NULL,
  `param_table` varchar(50) DEFAULT NULL,
  `param_field` varchar(50) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_ts` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_estimate_additional_charges` */

DROP TABLE IF EXISTS `work_estimate_additional_charges`;

CREATE TABLE `work_estimate_additional_charges` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_item_id` bigint NOT NULL,
  `additional_charges_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `additional_charges_rate` decimal(18,4) NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_ts` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_estimate_lead_charges` */

DROP TABLE IF EXISTS `work_estimate_lead_charges`;

CREATE TABLE `work_estimate_lead_charges` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_id` bigint NOT NULL,
  `work_estimate_item_id` bigint NOT NULL,
  `cat_work_sor_item_id` bigint NOT NULL,
  `material_master_id` bigint NOT NULL,
  `nhwy_road_type_master_id` bigint DEFAULT NULL,
  `sub_estimate_id` bigint NOT NULL,
  `quarry` varchar(50) DEFAULT NULL,
  `lead_in_m` decimal(6,2) DEFAULT NULL,
  `lead_in_km` decimal(6,2) DEFAULT NULL,
  `lead_charges_m` decimal(10,2) DEFAULT NULL,
  `lead_charges_km` decimal(10,2) DEFAULT NULL,
  `initial_lead_required_yn` bit(1) NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_ts` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_estimate_lift_charges` */

DROP TABLE IF EXISTS `work_estimate_lift_charges`;

CREATE TABLE `work_estimate_lift_charges` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_item_id` bigint NOT NULL,
  `material_master_id` bigint NOT NULL,
  `lift_distance` decimal(10,2) NOT NULL,
  `lift_charges` decimal(10,2) NOT NULL,
  `quantity` decimal(18,4) NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_ts` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_estimate_load_unload_charges` */

DROP TABLE IF EXISTS `work_estimate_load_unload_charges`;

CREATE TABLE `work_estimate_load_unload_charges` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_id` bigint NOT NULL,
  `work_estimate_item_id` bigint NOT NULL,
  `cat_work_sor_item_id` bigint NOT NULL,
  `material_master_id` bigint NOT NULL,
  `load_unload_rate_master_id` bigint NOT NULL,
  `sub_estimate_id` bigint NOT NULL,
  `selected_load_charges` bit(1) NOT NULL,
  `loading_charges` decimal(8,4) DEFAULT NULL,
  `selected_unload_charges` bit(1) NOT NULL,
  `unloading_charges` decimal(8,4) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_ts` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_estimate_market_rate` */

DROP TABLE IF EXISTS `work_estimate_market_rate`;

CREATE TABLE `work_estimate_market_rate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_id` bigint NOT NULL,
  `sub_estimate_id` bigint NOT NULL,
  `work_estimate_item_id` bigint NOT NULL,
  `material_master_id` bigint NOT NULL,
  `difference` decimal(10,4) NOT NULL,
  `prevailing_market_rate` decimal(10,4) NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_ts` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_estimate_other_addn_lift_charges` */

DROP TABLE IF EXISTS `work_estimate_other_addn_lift_charges`;

CREATE TABLE `work_estimate_other_addn_lift_charges` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_item_id` bigint NOT NULL,
  `work_estimate_rate_analysis_id` bigint NOT NULL,
  `notes_master_id` bigint NOT NULL,
  `selected` bit(1) NOT NULL,
  `addn_charges` decimal(10,2) NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_ts` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_estimate_rate_analysis` */

DROP TABLE IF EXISTS `work_estimate_rate_analysis`;

CREATE TABLE `work_estimate_rate_analysis` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_id` bigint NOT NULL,
  `work_estimate_item_id` bigint NOT NULL,
  `area_weightage_master_id` bigint DEFAULT NULL,
  `area_weightage_circle_id` bigint DEFAULT NULL,
  `area_weightage_percentage` decimal(10,2) DEFAULT NULL,
  `sor_financial_year` varchar(255) DEFAULT NULL,
  `basic_rate` decimal(21,4) NOT NULL,
  `net_rate` decimal(21,4) NOT NULL,
  `floor_no` bigint DEFAULT NULL,
  `contractor_profit_percentage` decimal(10,2) DEFAULT NULL,
  `overhead_percentage` decimal(10,4) DEFAULT NULL,
  `tax_percentage` decimal(10,4) DEFAULT NULL,
  `lift_charges` decimal(10,4) DEFAULT NULL,
  `locality_allowance` decimal(10,4) DEFAULT NULL,
  `employees_cost` decimal(10,4) DEFAULT NULL,
  `contingencies` decimal(10,4) DEFAULT NULL,
  `transportation_cost` decimal(10,4) DEFAULT NULL,
  `service_tax` decimal(10,4) DEFAULT NULL,
  `provident_fund_charges` decimal(10,4) DEFAULT NULL,
  `esi_charges` decimal(10,4) DEFAULT NULL,
  `idc_charges` decimal(10,4) DEFAULT NULL,
  `watch_and_ward_cost` decimal(10,4) DEFAULT NULL,
  `insurance_cost` decimal(10,4) DEFAULT NULL,
  `statutory_charges` decimal(10,4) DEFAULT NULL,
  `compensation_cost` decimal(10,4) DEFAULT NULL,
  `ra_completed_yn` bit(1) NOT NULL DEFAULT b'0',
  `created_by` varchar(100) DEFAULT NULL,
  `created_ts` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `work_estimate_royalty_charges` */

DROP TABLE IF EXISTS `work_estimate_royalty_charges`;

CREATE TABLE `work_estimate_royalty_charges` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_id` bigint NOT NULL,
  `work_estimate_item_id` bigint NOT NULL,
  `cat_work_sor_item_id` bigint NOT NULL,
  `material_master_id` bigint NOT NULL,
  `sub_estimate_id` bigint NOT NULL,
  `royalty_rate_master_id` bigint NOT NULL,
  `sr_royalty_charges` decimal(10,2) NOT NULL,
  `prevailing_royalty_charges` decimal(10,2) NOT NULL,
  `density_factor` decimal(10,2) NOT NULL,
  `difference` decimal(10,2) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_ts` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
