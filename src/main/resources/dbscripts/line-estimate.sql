CREATE TABLE `line_estimate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  `approx_rate` decimal(21,2) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11;

CREATE TABLE `over_head` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `over_head_type` varchar(255) NOT NULL,
  `over_head_name` varchar(255) NOT NULL,
  `active_yn` tinyint(1) NOT NULL DEFAULT '0',
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11;

CREATE TABLE `work_estimate_overhead` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_estimate_id` bigint NOT NULL,
  `over_head_type` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `over_head_value` decimal(21,2) NOT NULL,
  `fixed_yn` tinyint(1) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_ts` timestamp NULL DEFAULT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11;

insert  into `over_head`(`id`,`over_head_type`,`over_head_name`,`active_yn`,`created_by`,`created_ts`,`last_modified_by`,`last_modified_ts`,`version`) values 
(11,'NORMAL','Environment Impact',1,'anonymousUser','2021-07-06 16:24:54','anonymousUser','2021-07-06 16:24:54',0),
(12,'NORMAL','Municipal Taxes',1,'anonymousUser','2021-07-06 16:25:11','anonymousUser','2021-07-06 16:25:11',0),
(13,'NORMAL','T & P',1,'anonymousUser','2021-07-06 16:25:20','anonymousUser','2021-07-06 16:25:20',0),
(14,'NORMAL','Q C',1,'anonymousUser','2021-07-06 16:25:26','anonymousUser','2021-07-06 16:25:26',0),
(15,'NORMAL','Price Escalation',1,'anonymousUser','2021-07-06 16:25:34','anonymousUser','2021-07-06 16:25:34',0),
(16,'ADDITIONAL','Establishment Charges',1,'anonymousUser','2021-07-06 16:26:06','anonymousUser','2021-07-06 16:26:06',0);