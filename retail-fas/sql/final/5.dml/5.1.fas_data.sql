#   FAS 初始化数据字典
#   version:FAS-0.1.1
#   MODIFIED				(MM/DD/YY)
#   yang.h    			10/30/14
#   
#   
#   ********************* FAS 初始化基础数据*******************
#   --------------------------------------------------------------


start transaction;

delete from mall_balance_diff_type;
delete from mall_deduction_set;
delete from cost_category_setting;
delete from coding_rule;

INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('1', 'BillOtherDeduction', '其他扣项单据编码', 'KX', '6', '6', '1', '0', '0', '2014-11-06 11:20:22', '');
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('2', 'BillAskPayment', '请款单单据编码', 'QK', '4', '6', '1', '0', '0', '2014-11-20 10:10:07', '');
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('3', 'BillBalance', '结算单单据编码', 'JS', '8', '6', '1', '0', '0', '2015-02-04 12:05:06', '');
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('4', 'BillShopBalance', '商场门店结算单据编码', 'MS', '11', '4', '1', '1', '0', '2015-03-05 16:58:17', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('5', 'BillBalanceInvoiceApply', '开票申请单据编码', 'IA', '31', '4', '1', '1', '0', '2015-02-04 16:01:02', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('6', 'BillBacksectionSplit', '回款拆分单据编码', 'BS', '11', '4', '1', '1', '0', '2015-01-31 15:13:42', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('7', 'BillMallDeductionCost', '商场门店费用登记单据编码', 'MC', '11', '6', '1', '1', '0', '2014-11-27 18:13:22', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('8', 'BillBalanceInvoiceRegister', '普通发票登记单据编码', 'RI', '21', '4', '1', '1', '0', '2015-03-03 15:23:52', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('9', 'BillPrePaymentNt', '团购预收款单', 'PP', '11', '6', '1', '1', '0', '2015-01-28 18:00:26', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('10', 'CostCategorySetting', '费用类别', 'CC', '21', '6', '1', '1', '0', '2015-02-03 11:24:27', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('11', 'BillShopBalanceDiff', '结算差异编码', 'BD', '11', '6', '1', '1', '0', '2015-03-05 16:59:52', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('12', 'RR', '地区间结算单', 'RR', '11', '4', '1', '1', '0', '2015-03-05 15:02:28', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('13', 'RP', '地区自购结算单', 'RP', '11', '4', '1', '1', '0', '2015-03-05 15:14:09', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('14', 'RO', '地区其他出库结算单', 'RO', '11', '4', '1', '1', '0', '2015-03-05 15:08:14', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('15', 'GA', '总部代采购结算单', 'GA', '11', '4', '1', '1', '0', '2015-03-05 15:18:30', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('16', 'PI', '采购发票', 'PI', '11', '4', '1', '1', '0', '2015-03-05 15:13:36', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('17', 'RI ', '销售发票', 'RI ', '1', '4', '1', '1', '0', NULL, NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('18', 'GP', '总部厂商结算单', 'GP', '11', '4', '1', '1', '0', '2015-03-05 15:12:19', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('19', 'GS', '总部地区结算单', 'GS', '11', '4', '1', '1', '0', '2015-03-05 15:08:07', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('20', 'RS', '地区批发结算单', 'RS', '1', '4', '1', '1', '0', NULL, NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('21', 'RC', '收款条款', 'RC', '1', '4', '1', '1', '0', NULL, NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('22', 'PA', '预收款单 结算单', 'PA', '1', '4', '1', '1', '0', NULL, NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('23', 'PR', '请款单', 'PR', '11', '4', '1', '1', '0', '2015-03-05 15:13:16', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('24', 'PC', '付款单', 'PC', '11', '4', '1', '1', '0', '2015-03-05 15:14:07', NULL);
INSERT INTO `coding_rule` (`id`, `request_id`, `request_name`, `prefix`, `current_serial_no`, `serial_no_length`, `reset_mode`, `allow_break_no`, `is_abstract`, `reset_time`, `remarks`) VALUES ('25', 'SG', '店铺开票规则编码', 'SG', '1', '4', '1', '1', '0', NULL, NULL);



/*Data for the table `cost_category_setting` */

insert  into `cost_category_setting`(`id`,`cost_cate_no`,`code`,`name`,`status`,`create_user`,`create_time`,`update_user`,`update_time`,`remark`) values (1,NULL,'1','营运费',1,'fas','2014-12-27 17:02:37','fas','2015-02-11 14:24:51',''),(2,NULL,'2','租赁费',1,'fas','2014-12-27 17:02:47','fas','2014-12-27 17:02:47',''),(3,NULL,'3','水电费',1,'fas','2014-12-27 17:03:00','fas','2014-12-27 17:03:00',''),(4,NULL,'4','包装费',1,'fas','2014-12-27 17:43:35','fas','2014-12-27 17:43:35',''),(5,NULL,'5','商场促销费',1,'fas','2015-01-08 15:13:15',NULL,'2015-01-08 15:16:06',''),(6,NULL,'6','促销收费',1,'fas','2015-01-08 15:13:42',NULL,'2015-01-08 15:16:33',''),(10,'CC20150211000081','7','促销费',1,'fas','2015-02-11 13:34:45',NULL,'2015-02-11 13:38:04',''),(11,'CC20150211000091','8','物业费',1,'fas','2015-02-11 14:06:43',NULL,'2015-02-11 14:03:46',''),(12,'CC20150211000092','9','工会经费',1,'fas','2015-02-11 14:06:48',NULL,'2015-02-11 14:03:51',''),(13,'CC20150211000093','10','其他',1,'fas','2015-02-11 14:07:07',NULL,'2015-02-11 14:04:10','');

/*Data for the table `mall_balance_diff_type` */

insert  into `mall_balance_diff_type`(`id`,`diff_type_no`,`company_no`,`company_name`,`code`,`name`,`status`,`system_init`,`create_user`,`create_time`,`update_user`,`update_time`,`remark`) values (12,NULL,'','集团通用','1001','费用差异',1,0,'fas','2015-02-11 11:38:19',NULL,'2015-02-11 11:35:22',''),(13,NULL,'','集团通用','1002','活动差异',1,0,'fas','2015-02-11 11:38:35',NULL,'2015-02-11 11:35:39',''),(14,NULL,'','集团通用','1003','报数差异',1,0,'fas','2015-02-11 11:38:48',NULL,'2015-02-11 11:35:51',''),(15,NULL,'','集团通用','1004','销售汇总',1,0,'fas','2015-02-11 11:39:00','fas','2015-02-11 11:43:39','');

/*Data for the table `mall_deduction_set` */

insert  into `mall_deduction_set`(`id`,`deduction_no`,`code`,`name`,`cost_code`,`cost_name`,`type`,`system_init`,`status`,`create_user`,`create_time`,`update_user`,`update_time`,`remark`) values (21,NULL,'1000','场地经营费','1','营运费',NULL,0,1,'fas','2015-02-11 10:56:25','fas','2015-02-11 11:05:40',''),(52,NULL,'1001','租金','2','租赁费',NULL,0,1,'fas','2015-02-11 14:11:26',NULL,NULL,'基础扣率/保底销售分成、场地使用费'),(53,NULL,'1002','仓储费','1','营运费',NULL,0,1,'fas','2015-02-11 14:13:17',NULL,NULL,'仓库、储物柜等租金'),(54,NULL,'1003','促销费','7','促销费',NULL,0,1,'fas','2015-02-11 14:13:37',NULL,NULL,'促销活动费、花车费、广告赞助费、宣传费'),(55,NULL,'1004','活动用品费','1','营运费',NULL,0,1,'fas','2015-02-11 14:13:57',NULL,NULL,'商场活动送的礼品，花车、道具使用费'),(56,NULL,'1005','商场会员卡','1','营运费',NULL,0,1,'fas','2015-02-11 14:16:07',NULL,NULL,'商场会员卡、积分卡、储值卡的手续、积分、返利、宣传等费用'),(57,NULL,'1006','刷卡费','10','其他',NULL,0,1,'fas','2015-02-11 14:16:07',NULL,NULL,'银行卡刷卡费'),(58,NULL,'1007','店庆费','10','其他',NULL,0,0,'fas','2015-02-11 14:16:07','fas','2015-02-11 14:16:24','店庆、节庆费'),(59,NULL,'1008','管理费','8','物业费',NULL,0,1,'fas','2015-02-11 14:16:08',NULL,NULL,'人员/综合管理费、物业费'),(60,NULL,'1009','办公费','1','营运费',NULL,0,1,'fas','2015-02-11 14:16:45',NULL,NULL,'员工工服、合同变更费、监控费、POS使用费、网上对账费、网络费'),(61,NULL,'1010','电话费','1','营运费',NULL,0,1,'fas','2015-02-11 14:17:28',NULL,NULL,'电话费'),(62,NULL,'1011','电汇手续费','1','营运费',NULL,0,1,'fas','2015-02-11 14:17:28',NULL,NULL,'电汇手续费'),(63,NULL,'1012','水电费','3','水电费',NULL,0,1,'fas','2015-02-11 14:17:42',NULL,NULL,'水电费'),(64,NULL,'1013','物料费','1','营运费',NULL,0,1,'fas','2015-02-11 14:19:06',NULL,NULL,'员工工牌、单据工本类、展架KT板'),(65,NULL,'1014','邮寄费','10','其他',NULL,0,1,'fas','2015-02-11 14:19:06',NULL,NULL,'邮寄快递费、运费'),(66,NULL,'1015','罚款','1','营运费',NULL,0,1,'fas','2015-02-11 14:19:06',NULL,NULL,'员工、工商罚款，违约金'),(67,NULL,'1016','其他','10','其他',NULL,0,1,'fas','2015-02-11 14:19:06',NULL,NULL,'保险费、餐费、维修安装费');

commit;