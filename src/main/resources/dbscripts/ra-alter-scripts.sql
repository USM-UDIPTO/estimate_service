#Udipta 12-Jul-2021
ALTER TABLE `estimate-service`.`work_estimate_lead_charges` 
DROP COLUMN `lead_charges_km`,
DROP COLUMN `nhwy_road_type_master_id`,
CHANGE COLUMN `lead_charges_m` `lead_charges` DECIMAL(10,2) NULL DEFAULT NULL ;

ALTER TABLE `estimate-service`.`work_estimate_load_unload_charges` 
DROP COLUMN `load_unload_rate_master_id`;

ALTER TABLE `estimate-service`.`work_estimate_royalty_charges` 
DROP COLUMN `royalty_rate_master_id`;

ALTER TABLE `estimate-service`.`work_estimate_other_addn_lift_charges` 
DROP COLUMN `work_estimate_rate_analysis_id`,
ADD COLUMN `type` VARCHAR(100) NOT NULL AFTER `addn_charges`;

ALTER TABLE `estimate-service`.`work_estimate_rate_analysis` 
DROP COLUMN `lift_charges`;
