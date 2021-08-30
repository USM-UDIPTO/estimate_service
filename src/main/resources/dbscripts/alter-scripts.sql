
#31-05-2021
ALTER TABLE `estimate-service`.`dept_estimate_type`   
	ADD COLUMN `active_yn` TINYINT(1) DEFAULT 0 NOT NULL AFTER `estimate_type_id`;
	
ALTER TABLE `estimate-service`.`scheme_code`   
	ADD COLUMN `active_yn` TINYINT(1) DEFAULT 0 NOT NULL AFTER `scheme_status`;

ALTER TABLE `estimate-service`.`work_category_attribute`   
	ADD COLUMN `active_yn` TINYINT(1) DEFAULT 0 NOT NULL AFTER `work_type_id`;

#Udipto 02-06-2021
ALTER TABLE `estimate-service`.`sub_estimate` 
ADD COLUMN `work_sub_estimate_group_id` BIGINT NULL AFTER `work_estimate_id`;

ALTER TABLE `estimate-service`.`work_estimate` 
CHANGE COLUMN `lumpsum_total` `group_lumpsum_total` DECIMAL(21,2) NULL DEFAULT NULL ;

ALTER TABLE `estimate-service`.`work_estimate` 
CHANGE COLUMN `wotk_type_id` `work_type_id` BIGINT NOT NULL ;

# Magesh 09-06-2021
ALTER TABLE `estimate-service`.`work_estimate`   
	CHANGE `work_category_attribute_value` `work_category_attribute_value` DECIMAL(21,4) NULL,
	CHANGE `line_estimate_total` `line_estimate_total` DECIMAL(21,4) NULL,
	CHANGE `estimate_total` `estimate_total` DECIMAL(21,4) NULL,
	CHANGE `group_lumpsum_total` `group_lumpsum_total` DECIMAL(21,4) NULL,
	CHANGE `addl_overhead_total` `addl_overhead_total` DECIMAL(21,4) NULL,
	CHANGE `normal_overhead_total` `normal_overhead_total` DECIMAL(21,4) NULL,
	CHANGE `group_overhead_total` `group_overhead_total` DECIMAL(21,4) NULL,
	CHANGE `grant_allocated_amount` `grant_allocated_amount` DECIMAL(21,4) NULL,
	CHANGE `provisional_amount` `provisional_amount` DECIMAL(21,4) NULL;

ALTER TABLE `estimate-service`.`work_estimate_item`   
	CHANGE `base_rate` `base_rate` DECIMAL(21,4) NOT NULL,
	CHANGE `final_rate` `final_rate` DECIMAL(21,4) NULL,
	CHANGE `quantity` `quantity` DECIMAL(21,4) NULL,
	CHANGE `labour_rate` `labour_rate` DECIMAL(21,4) NULL;

ALTER TABLE `estimate-service`.`work_estimate_item_lbd`   
	CHANGE `lbd_nos` `lbd_nos` DECIMAL(21,4) NOT NULL,
	CHANGE `lbd_length` `lbd_length` DECIMAL(21,4) NULL,
	CHANGE `lbd_bredth` `lbd_bredth` DECIMAL(21,4) NULL,
	CHANGE `lbd_depth` `lbd_depth` DECIMAL(21,4) NULL,
	CHANGE `lbd_quantity` `lbd_quantity` DECIMAL(21,4) NOT NULL,
	CHANGE `lbd_total` `lbd_total` DECIMAL(21,4) NULL;

ALTER TABLE `estimate-service`.`work_sub_estimate_group`   
	CHANGE `overhead_total` `overhead_total` DECIMAL(21,4) NULL,
	CHANGE `lumpsum_total` `lumpsum_total` DECIMAL(21,4) NULL;

ALTER TABLE `estimate-service`.`work_sub_estimate_group_lumpsum`   
	CHANGE `approx_rate` `approx_rate` DECIMAL(21,4) NOT NULL;

ALTER TABLE `estimate-service`.`work_sub_estimate_group_overhead`   
	CHANGE `entered_value` `entered_value` DECIMAL(21,4) NULL,
	CHANGE `overhead_value` `overhead_value` DECIMAL(21,4) NOT NULL;

ALTER TABLE `estimate-service`.`sub_estimate`   
	CHANGE `estimate_total` `estimate_total` DECIMAL(21,4) NULL;
	
ALTER TABLE `estimate-service`.`work_estimate_item`   
	CHANGE `work_category_id` `category_id` BIGINT NULL;

ALTER TABLE `estimate-service`.`work_estimate_item`   
	CHANGE `lbd_performed_yn` `lbd_performed_yn` TINYINT(1) DEFAULT 0 NULL,
	CHANGE `ra_performed_yn` `ra_performed_yn` TINYINT(1) DEFAULT 0 NULL;

ALTER TABLE `estimate-service`.`work_estimate_item_lbd`   
	CHANGE `calculated_yn` `calculated_yn` TINYINT(1) DEFAULT 0 NULL;

ALTER TABLE `estimate-service`.`work_sub_estimate_group_overhead`   
	CHANGE `value_fixed_yn` `value_fixed_yn` TINYINT(1) DEFAULT 0 NOT NULL,
	CHANGE `final_yn` `final_yn` TINYINT(1) DEFAULT 0 NOT NULL;

ALTER TABLE `estimate-service`.`work_estimate`   
	DROP COLUMN `budget_reference_id`;
	
#20-06-2021
ALTER TABLE `estimate-service`.`scheme_code`   
	CHANGE `schemetype` `scheme_type` VARCHAR(255) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL;

ALTER TABLE `estimate-service`.`scheme_code` DROP COLUMN `source_name`; 

ALTER TABLE `estimate-service`.`work_location`   
	ADD COLUMN `country_id` BIGINT NULL AFTER `sub_estimate_id`,
	ADD COLUMN `state_id` BIGINT NULL AFTER `country_id`;

#28-06-2021
ALTER TABLE `estimate-service`.`work_location`   
	ADD COLUMN `country_name` VARCHAR(100) NULL AFTER `country_id`,
	ADD COLUMN `state_name` VARCHAR(100) NULL AFTER `state_id`,
	ADD COLUMN `district_name` VARCHAR(100) NULL AFTER `district_id`,
	ADD COLUMN `taluq_name` VARCHAR(100) NULL AFTER `taluq_id`,
	ADD COLUMN `loksabha_cont_name` VARCHAR(100) NULL AFTER `loksabha_cont_id`,
	ADD COLUMN `assembly_cont_name` VARCHAR(100) NULL AFTER `assembly_cont_id`;

ALTER TABLE `estimate-service`.`work_estimate_item`   
	ADD COLUMN `uom_name` VARCHAR(255) NOT NULL AFTER `uom_id`;

	
ALTER TABLE `estimate-service`.`work_estimate_item`   
	ADD COLUMN `category_name` VARCHAR(255) NULL AFTER `category_id`;
	