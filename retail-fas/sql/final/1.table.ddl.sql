#   FAS 初始化表结构
#   version:FAS-0.4.2
#   MODIFIED			(MM/DD/YY)
#   YANG.H				12/31/14
#   
#   
#   *********************FAS初始化表结构*******************

####accounting_subject	科目表;
####assist_project	辅助项目表;
####assist_project_type	辅助项目类别;
####balance_date	结算日设置-应付;
####bank_account_info	银行账户基本信息;
####bill_ask_payment	请款单;
####bill_ask_payment_dtl	请款单明细;
####bill_ask_payment_payinfo	;
####bill_asn	到货单-表头;
####bill_asn_dtl	到货单-表体;
####bill_backsection_split	回款拆分主表;
####bill_backsection_split_dtl	回款拆分明细表;
####bill_balance	结算单;
####bill_balance_cash_payment	结算收款付款登记;
####bill_balance_deduction_dtl	;
####bill_balance_dtl	;
####bill_balance_invoice	结算发票登记;
####bill_balance_invoice_apply	开票申请单;
####bill_balance_invoice_info	开票申请单-发票信息;
####bill_balance_invoice_register	普通发票登记;
####bill_balance_invoice_source	开票申请单-源结算单;
####bill_buy_balance	;
####bill_cust_fine_nt	保证金罚没通知单;
####bill_cust_margin_nt	客户保证金催缴通知单;
####bill_diff_receipt	采购差异单-表头;
####bill_diff_receipt_dtl	采购差异单-表体;
####bill_differences	差异报表;
####bill_mall_deduction_cost	商场费用登记表;
####bill_other_deduction	其他扣项;
####bill_other_deduction_dtl	其他扣项明细;
####bill_pre_payment_nt	预收款缴款通知单;
####bill_receipt	验收单-表头;
####bill_receipt_dtl	验收单-表体;
####bill_return	退厂单-表头;
####bill_return_dtl	退货单-表体;
####bill_sale_balance	;
####bill_sale_out	销售出库单;
####bill_sale_out_dtl	销售出库表体;
####bill_settlement	结算单;
####bill_shop_balance	商场门店结算主表;
####bill_shop_balance_account	商场门店结算收款表;
####bill_shop_balance_advance	商场门店结算商场预收款表;
####bill_shop_balance_deduct	商场门店结算扣费票前费用信息表;
####bill_shop_balance_diff	商场门店结算结算差异表;
####bill_shop_balance_dtl	商场门店结算明细表;
####bill_shop_balance_list	开票申请单列表;
####bill_shop_balance_vip	商场门店结算商场VIP分摊表;
####bill_shop_sale_order	商场门店销售订单数据表;
####bill_split	拆分的单据汇总表;
####bill_split_dtl	拆分的单据明细表;
####bill_split_log	拆单日志表;
####bill_transfer	调货单;
####bill_transfer_dtl	调货单（移仓单 店转货单）;
####brand	品牌信息表;
####bsgroups	商业集团信息表(Business groups);
####cash_check	现金核对表;
####cash_transaction_dtl	现金交易明细;
####category	类别信息表;
####company	结算公司信息表;
####company_cost_method	公司成本方法设置;
####company_currency	币别;
####company_currency_rel	公司和币别的关联;
####company_currency_set	外币设置;
####company_foreign_currency	外币汇率设置;
####company_settle_period	公司结账期间;
####cost_category_setting	总账费用类别设置;
####cost_method_type	成本核算方法;
####credit_card_check	银行卡核对表;
####credit_card_transaction_dtl	银联刷卡交易明细;
####currency_management	币种管理;
####customer	加盟批发客户信息表;
####deposit_cash	34. 现金存入;
####drop_bill_invoice_apply	开票申请单-表头;
####drop_bill_invoice_apply_dtl	开票申请单-表体;
####exchange_rate_setup	汇率设置;
####expect_cash	预收款单;
####factory_price_maintain	厂进价维护;
####factory_price_unique	;
####financial_account	财务帐套;
####headquarter_cost_maintain	总部成本维护;
####headquarter_price_rule	总部加价规则;
####initial_amount	初始余额(应收/应付);
####invoice_rule_set	开票申请规则设置表;
####invoice_rule_shop_set	开票申请店铺分组设置表;
####invoice_template_set	发票模板设置主表;
####invoice_template_set_dtl	发票模板设置明细表;
####item	商品资料表;
####item_cost	成本主表;
####lookup	码表主表;
####lookup_entry	数据字典项;
####lookup_rel	数据字典关系表;
####mall	商场信息表;
####mall_balance_diff_type	商场结算差异类型设置表;
####mall_balance_setting	商场门店结算期设置;
####mall_deduction_set	商场扣费名目设置;
####open_inventory_init	期初库存初始化;
####order_assistant	27. 销售订单明细-营业员;
####order_coupon	28. 销售订单-优惠券;
####order_dtl	26. 销售订单明细;
####order_main	25.销售订单;
####order_unit	订货单位表;
####organ	组织关系表;
####payment_term	付款条款;
####period_balance	本期结存表;
####region	片区信息表;
####region_cost_maintain	地区成本维护;
####region_price_rule	地区加价规则;
####register_invoice	发票登记;
####rule_match_fail	加价规则匹配失败表;
####sale_settlement_deduction	结算期扣费设置;
####self_shop_bank_info	独立店铺账户管理表;
####settle_brand_group	品牌组;
####settle_brand_group_rel	品牌组和品牌的关联表;
####settle_category	结算大类;
####settle_category_dtl	结算大类明细;
####settle_method	结算方法表;
####settle_new_style	新旧款;
####settle_new_style_dtl	新旧款明细;
####settle_path	结算路径设置;
####settle_path_brand_rel	结算路径和品牌的关联表;
####settle_path_dtl	结算路径设置明细;
####shop	门店信息表;
####shop_balance_date	商场门店结算期设置表;
####store	机构信息表;
####supplier	供应商信息表;
####supplier_group	供应商组设置;
####supplier_group_rel	供应商组与供应商关联表;
####voucher_type	凭证类型表;
####wholesale_margin_init	客户保证金及预收款初始化;
####wholesale_prepay_warn	客户保证金预收款预警;
####wholesale_rece_term	地区批发收款条款-表头;
####wholesale_rece_term_dtl	地区批发收款条款-表体;
####zone_info	经营区域信息表;
#   --------------------------------------------------------------


tee 1.table.ddl.log

select
'FAS 初始化表结构'			as name,
'FAS-0.4.2'				as version \G


-- ----------------------------
-- Table structure for accounting_subject
-- ----------------------------
DROP TABLE IF EXISTS `accounting_subject`;
CREATE TABLE `accounting_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `subject_code` char(18) COLLATE utf8_bin NOT NULL COMMENT '科目编码',
  `subject_level` tinyint(3) NOT NULL COMMENT '科目级次',
  `subject_name` varchar(40) COLLATE utf8_bin NOT NULL COMMENT '科目名称',
  `subject_type` tinyint(3) NOT NULL COMMENT '科目类型',
  `subject_en_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '科目英文名称',
  `balance_direction` tinyint(3) DEFAULT NULL COMMENT '余额方向 1、借 2、贷',
  `currency` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '科目默认币种',
  `display_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '显示名称',
  `unit` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '计量单位',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '公司编码',
  `balance_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '余额方向控制',
  `mnemonic_code` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '助记码',
  `cancel_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '是否核销科目',
  `begin_period` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '启用期间',
  `begin_year` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '启用年度',
  `both_direction` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '帐簿余额双向显示',
  `cash_bank_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '现金银行科目',
  `incur_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '发生额方向控制',
  `glorg_book_creation` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '创建会计主体账簿',
  `create_year` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '创建年度',
  `create_period` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '创建月份',
  `control_system` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '控制系统',
  `del_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '删除标志',
  `end_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '末级标志',
  `end_period` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '终止期间',
  `end_year` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '终止年度',
  `recorded_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '内部交易信息是否必录',
  `inner_subject` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '内部交易科目',
  `out_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '表外科目',
  `glorg_book` char(20) COLLATE utf8_bin DEFAULT NULL COMMENT '会计主体账簿',
  `group_subject` char(20) COLLATE utf8_bin DEFAULT NULL COMMENT '对应集团科目',
  `subject_scheme` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '科目方案',
  `seal_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '封存标志',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态 1、已启用 0、已停用',
  `print_level` tinyint(3) DEFAULT NULL COMMENT '汇总打印级次',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_subject_code` (`subject_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会计科目表';

-- ----------------------------
-- Table structure for assist_project
-- ----------------------------
DROP TABLE IF EXISTS `assist_project`;
CREATE TABLE `assist_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` char(18) COLLATE utf8_bin NOT NULL COMMENT '辅助项目编码',
  `name` varchar(60) COLLATE utf8_bin NOT NULL COMMENT '辅助项目名称',
  `type` tinyint(3) DEFAULT NULL COMMENT '辅助项目类型',
  `status` tinyint(3) DEFAULT '1' COMMENT '启用标志 1、已启用 0、已停用',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '公司编码',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `fkAssistprojectType` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='辅助项目表';

-- ----------------------------
-- Table structure for assist_project_type
-- ----------------------------
DROP TABLE IF EXISTS `assist_project_type`;
CREATE TABLE `assist_project_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type_code` char(18) COLLATE utf8_bin NOT NULL COMMENT '辅助项目类型编码',
  `type_name` varchar(60) COLLATE utf8_bin NOT NULL COMMENT '辅助项目类型名称',
  `status` tinyint(3) DEFAULT '1' COMMENT '启用标志 1、已启用 0、已停用',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '公司编码',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='辅助项目类别';

-- ----------------------------
-- Table structure for balance_date
-- ----------------------------
DROP TABLE IF EXISTS `balance_date`;
CREATE TABLE `balance_date` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `balance_type` tinyint(3) DEFAULT NULL COMMENT '结算类型(1-总部厂商结算,2-总部批发结算,3-其他结算)',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算主体编号',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算主体名称',
  `supplier_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商名称',
  `customer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '客户名称',
  `balance_date` char(2) COLLATE utf8_bin DEFAULT NULL COMMENT '结算日',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算日设置-应付';

-- ----------------------------
-- Table structure for bank_account_info
-- ----------------------------
DROP TABLE IF EXISTS `bank_account_info`;
CREATE TABLE `bank_account_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '账户编码',
  `name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '账户名称',
  `company_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '开户公司',
  `account_no` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '账号',
  `company_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '公司编码',
  `bank_name` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '开户银行',
  `bank_area` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '开户地区',
  `zone_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '地区代码',
  `city_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '城市编码',
  `account_class` tinyint(3) DEFAULT NULL COMMENT '账户分类 1、基本账户 2、一般账户 3、临时账户 4、专用账户',
  `property` tinyint(3) DEFAULT NULL COMMENT '账户性质',
  `account_attribute` tinyint(3) DEFAULT NULL COMMENT '账户属性',
  `payment_mark` tinyint(3) DEFAULT NULL COMMENT '收付标志,1收，2付',
  `open_date` date DEFAULT NULL COMMENT '开户日期',
  `account_status` tinyint(3) DEFAULT NULL COMMENT '账户状态  1、启用 2、禁用',
  `type` tinyint(3) DEFAULT NULL COMMENT '账户类型',
  `cancel_date` date DEFAULT NULL COMMENT '销户日期',
  `status` tinyint(3) DEFAULT '1' COMMENT '启用标志 1、已启用 0、已停用',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='银行账户基本信息';

-- ----------------------------
-- Table structure for bill_ask_payment
-- ----------------------------
DROP TABLE IF EXISTS `bill_ask_payment`;
CREATE TABLE `bill_ask_payment` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键(UUID)',
  `bill_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '单据编号',
  `buyer_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '买方编号',
  `saler_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方编号',
  `buyer_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '买方名称',
  `saler_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方名称',
  `bill_date` date DEFAULT NULL COMMENT '单据日期',
  `currency_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '币别',
  `all_amount` decimal(12,2) DEFAULT '0.00' COMMENT '请款金额',
  `balance_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单号',
  `balance_amount` decimal(12,2) DEFAULT '0.00' COMMENT '结算单金额',
  `status` tinyint(3) DEFAULT '0' COMMENT '单据状态(0-制单,1-审核)',
  `balance_type` tinyint(3) DEFAULT '0' COMMENT '结算类型(1、总部厂商结算,2、总部批发结算, 3、总部其他结算,4、地区采购结算 5、地区间结算 6、地区自购结算 7、地区批发结算 8、地区团购结算 9、地区员购结算 10、地区商场结算 11、地区其他出库结算)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改日期',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审批人',
  `audit_time` datetime DEFAULT NULL COMMENT '审批日期',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `is_generate` int(11) DEFAULT '0' COMMENT '是否生成',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bill_no` (`bill_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='请款单';

-- ----------------------------
-- Table structure for bill_ask_payment_dtl
-- ----------------------------
DROP TABLE IF EXISTS `bill_ask_payment_dtl`;
CREATE TABLE `bill_ask_payment_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '单据编号',
  `settle_method_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算方式',
  `settle_method_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算方式名称',
  `invoice_no` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '发票号',
  `amount` decimal(12,2) DEFAULT '0.00' COMMENT '金额',
  `other_bank` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '对方银行',
  `other_bank_account` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '对方银行账号',
  `qty` int(11) DEFAULT '0' COMMENT '请款数量',
  `brand_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `category_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品大类编码',
  `category_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品大类名称',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='请款单明细';

-- ----------------------------
-- Table structure for bill_backsection_split
-- ----------------------------
DROP TABLE IF EXISTS `bill_backsection_split`;
CREATE TABLE `bill_backsection_split` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `backsection_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '回款单据编号',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `back_type` tinyint(3) unsigned DEFAULT NULL COMMENT '回款方类型(1-商场，2-商业集团)',
  `back_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '回款方编码',
  `back_name` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '回款方名称',
  `back_amount` decimal(12,2) DEFAULT '0.00' COMMENT '回款金额',
  `back_date` date DEFAULT NULL COMMENT '回款日期',
  `receive_amount` decimal(12,2) DEFAULT '0.00' COMMENT '应回金额',
  `notreceive_amount` decimal(12,2) DEFAULT '0.00' COMMENT '未收金额',
  `diff_amount` decimal(12,2) DEFAULT '0.00' COMMENT '回款差异',
  `diff_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '差异原因',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '单据状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='回款登记主表';

-- ----------------------------
-- Table structure for bill_backsection_split_dtl
-- ----------------------------
DROP TABLE IF EXISTS `bill_backsection_split_dtl`;
CREATE TABLE `bill_backsection_split_dtl` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `backsection_dtl_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '回款明细编号',
  `backsection_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '回款编号',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `month` char(15) COLLATE utf8_bin DEFAULT NULL COMMENT '结算月份',
  `balance_no` char(20) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单编号',
  `billing_amount` decimal(12,2) DEFAULT '0.00' COMMENT '开票金额',
  `ticket_deduction_amount` decimal(12,2) DEFAULT '0.00' COMMENT '票后帐扣费用',
  `receive_amount` decimal(12,2) DEFAULT '0.00' COMMENT '应回款金额',
  `already_receive_amount` decimal(12,2) DEFAULT '0.00' COMMENT '已回款金额',
  `not_receive_amount` decimal(12,2) DEFAULT '0.00' COMMENT '未回款金额',
  `the_payment_amount` decimal(12,2) DEFAULT '0.00' COMMENT '本次回款金额',
  `back_date` date DEFAULT NULL COMMENT '回款日期',
  `diff_amount` decimal(12,2) DEFAULT '0.00' COMMENT '回款差异',
  `diff_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '差异原因',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '商场名称',
  `bsgroups_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团编码',
  `bsgroups_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团名称',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `zone_name` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域名称',
  `organ_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市编号',
  `organ_name` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市名称',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='回款登记明细表';

-- ----------------------------
-- Table structure for bill_balance
-- ----------------------------
DROP TABLE IF EXISTS `bill_balance`;
CREATE TABLE `bill_balance` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键(uuid)',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号(结算单号)',
  `bill_name` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '单据名称',
  `status` tinyint(3) DEFAULT '0' COMMENT '单据状态',
  `extend_status` tinyint(3) DEFAULT '0' COMMENT '扩展状态',
  `balance_type` tinyint(3) DEFAULT NULL COMMENT '结算类型(1、总部厂商结算,2、总部批发结算, 3、总部其他结算,4、地区采购结算 5、地区间结算 6、地区自购结算 7、地区批发结算 8、地区团购结算 9、地区员购结算 10、地区商场结算 11、地区其他出库结算)',
  `buyer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单买方编号',
  `buyer_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单买方名称',
  `saler_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单卖方编号',
  `saler_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单卖方名称',
  `brand_no` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编号',
  `brand_name` varchar(600) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `brand_unit_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌部编号',
  `brand_unit_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌部名称',
  `category_no` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '大类编码',
  `category_name` varchar(600) COLLATE utf8_bin DEFAULT NULL COMMENT '大类名称',
  `balance_date` date DEFAULT NULL COMMENT '结算日期',
  `balance_start_date` date DEFAULT NULL COMMENT '结算开始时间',
  `balance_end_date` date DEFAULT NULL COMMENT '结算结束日期',
  `currency` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '币别',
  `out_amount` decimal(20,2) DEFAULT '0.00' COMMENT '发出金额',
  `entry_amount` decimal(20,2) DEFAULT '0.00' COMMENT '接收金额',
  `return_amount` decimal(20,2) DEFAULT '0.00' COMMENT '退残金额',
  `deduction_amount` decimal(20,2) DEFAULT '0.00' COMMENT '其他扣项金额',
  `balance_amount` decimal(20,2) DEFAULT '0.00' COMMENT '结算金额',
  `invoice_amount` decimal(20,2) DEFAULT '0.00' COMMENT '发票金额',
  `has_balance_amount` decimal(20,2) DEFAULT '0.00' COMMENT '已结算金额(付款金额/收款金额)',
  `out_qty` int(11) DEFAULT '0' COMMENT '发出数(备用)',
  `entry_qty` int(11) DEFAULT '0' COMMENT '接收数(备用)',
  `return_qty` int(11) DEFAULT '0' COMMENT '退残数(备用)',
  `deduction_qty` int(11) DEFAULT '0' COMMENT '扣项数(备用)',
  `balance_qty` int(11) DEFAULT '0' COMMENT '结算数量(备用)',
  `ask_payment_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '请款单号',
  `invoice_apply_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '开票申请单号',
  `invoice_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发票号',
  `is_user_defined` tinyint(3) DEFAULT '0' COMMENT '是否自定义',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  KEY `uk_bill_no` (`bill_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算单';

-- ----------------------------
-- Table structure for bill_balance_cash_payment
-- ----------------------------
DROP TABLE IF EXISTS `bill_balance_cash_payment`;
CREATE TABLE `bill_balance_cash_payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '(收款单号/付款单号)',
  `balance_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单号',
  `balance_type` tinyint(3) DEFAULT NULL COMMENT '结算类型(1-应收,2-应付)',
  `amount` decimal(20,2) DEFAULT '0.00' COMMENT '金额',
  `bill_date` datetime DEFAULT NULL COMMENT '单据日期',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `ask_payment_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '请款单号',
  `ask_payment_date` datetime DEFAULT NULL COMMENT '请款单日期',
  `auditor` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '付款确认人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算收款付款登记';

-- ----------------------------
-- Table structure for bill_balance_deduction_dtl
-- ----------------------------
DROP TABLE IF EXISTS `bill_balance_deduction_dtl`;
CREATE TABLE `bill_balance_deduction_dtl` (
  `id` int(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `buyer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '买方编号',
  `buyer_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '买方名称',
  `saler_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方编号',
  `saler_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方名称',
  `balance_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单号',
  `balance_type` tinyint(3) DEFAULT NULL COMMENT '结算单状态',
  `balance_status` tinyint(3) DEFAULT NULL COMMENT '结算单状态',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编号',
  `brand_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '产品大类编号',
  `category_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '产品大类名称',
  `currency_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '币别',
  `currency_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '币别名称',
  `deduction_date` date DEFAULT NULL COMMENT '扣项日期',
  `deduction_amount` decimal(12,2) DEFAULT '0.00' COMMENT '扣项金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='其他扣项明细';

-- ----------------------------
-- Table structure for bill_balance_dtl
-- ----------------------------
DROP TABLE IF EXISTS `bill_balance_dtl`;
CREATE TABLE `bill_balance_dtl` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `bill_type` int(11) DEFAULT NULL COMMENT '单据类型(1301-到货单,1333-调货出库单,1335-客残销售出库单)',
  `ref_bill_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '相关单号',
  `ref_bill_type` int(11) DEFAULT NULL COMMENT '相关单据类型',
  `status` int(11) DEFAULT NULL COMMENT '单据状态',
  `buyer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '买方编号',
  `buyer_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '买方名称',
  `saler_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方编号',
  `saler_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方名称',
  `send_date` datetime DEFAULT NULL COMMENT '发出日期',
  `receive_date` datetime DEFAULT NULL COMMENT '接收日期',
  `send_store_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发出地编号',
  `send_store_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '发出地名称',
  `receive_store_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '收货地编号',
  `receive_store_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '收货地名称',
  `sku_id` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '明细id',
  `sku_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'SKU编号',
  `supplier_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商名称',
  `item_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编号',
  `item_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码(出厂时的商品编码)',
  `item_name` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编号',
  `brand_name` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '大类编号',
  `category_name` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '大类名称',
  `cost` decimal(12,2) DEFAULT '0.00' COMMENT '含税单价(结算价格、默认原单据价格)',
  `bill_cost` decimal(12,2) DEFAULT '0.00' COMMENT '单据价格(原单据价格)',
  `tax_rate` decimal(4,2) DEFAULT NULL COMMENT '税率',
  `exclusive_cost` decimal(12,2) DEFAULT NULL COMMENT '不含税单价',
  `send_qty` int(11) DEFAULT '0' COMMENT '发出数量',
  `receive_qty` int(11) DEFAULT '0' COMMENT '接受数量',
  `order_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `balance_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单号',
  `is_split` tinyint(3) DEFAULT NULL COMMENT '是否拆分单据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算单详情';

-- ----------------------------
-- Table structure for bill_balance_invoice
-- ----------------------------
DROP TABLE IF EXISTS `bill_balance_invoice`;
CREATE TABLE `bill_balance_invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `balance_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单号',
  `express_date` datetime DEFAULT NULL COMMENT '快递日期',
  `express_bill_no` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '快递单号',
  `invoice_no` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '发票号',
  `amount` decimal(12,2) DEFAULT '0.00' COMMENT '金额',
  `balance_type` tinyint(3) DEFAULT NULL COMMENT '结算类型(1-应收,2-应付)',
  `express` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '快递公司',
  `invoice_date` datetime DEFAULT NULL COMMENT '发票日期',
  `receive_date` datetime DEFAULT NULL COMMENT '接受日期',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算发票登记';

-- ----------------------------
-- Table structure for bill_balance_invoice_apply
-- ----------------------------
DROP TABLE IF EXISTS `bill_balance_invoice_apply`;
CREATE TABLE `bill_balance_invoice_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '发票申请单号',
  `saler_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '公司编码(开票方)-卖方',
  `saler_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方名称',
  `buyer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '客户编码(收票方)-买方',
  `buyer_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '买方名称',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '开票名称',
  `invoice_apply_date` date DEFAULT NULL COMMENT '申请开票日期',
  `post_pay_date` date DEFAULT NULL COMMENT '交票日期',
  `tax_registry_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '税务登记号',
  `bank_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '银行帐号',
  `bank_account_name` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '银行账户名',
  `currency` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '币别',
  `amount` decimal(20,2) DEFAULT '0.00' COMMENT '开票金额',
  `invoice_type` tinyint(3) DEFAULT '1' COMMENT '发票类型(0 = 普通发票 1 = 增值票)',
  `pre_invoice` tinyint(3) DEFAULT '1' COMMENT '是否预开票(2 = 是 1 = 否)',
  `status` tinyint(3) DEFAULT '1' COMMENT '单据状态',
  `export_num` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '引出次数',
  `buyer_address` varchar(240) CHARACTER SET utf8 DEFAULT NULL COMMENT '客户-买方地址',
  `buyer_tel` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '客户-买方电话',
  `mailing_address` varchar(240) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮寄地址',
  `contact_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `tel` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人电话',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '审核时间',
  `invoice_register_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发票登记单号',
  `invoice_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '开票名称',
  `use_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '发票是否已使用：0 未使用，1 已使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='开票申请单';

-- ----------------------------
-- Table structure for bill_balance_invoice_dtl
-- ----------------------------
DROP TABLE IF EXISTS `bill_balance_invoice_dtl`;
CREATE TABLE `bill_balance_invoice_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '开票申请单号',
  `balance_no` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单号',
  `balance_start_date` datetime DEFAULT NULL COMMENT '结算起始日期',
  `balance_end_date` datetime DEFAULT NULL COMMENT '结算终止日期',
  `brand_Name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌',
  `brand_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编号',
  `category_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '大类编号',
  `category_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '大类',
  `organ_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市编号',
  `organ_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市名称',
  `saler_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '开票名称',
  `qty` int(11) DEFAULT NULL COMMENT '数量',
  `send_amount` decimal(12,2) DEFAULT NULL COMMENT '金额',
  `tax_rate` decimal(9,4) DEFAULT NULL COMMENT '税率',
  `tax_amount` decimal(12,2) DEFAULT NULL COMMENT '税额',
  `no_tax_amount` decimal(12,2) DEFAULT NULL COMMENT '不含税金额',
  `estimated_amount` decimal(12,2) DEFAULT NULL COMMENT '预估成本',
  `pos_earning_amount` decimal(12,2) DEFAULT NULL COMMENT '终端收入金额',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺全称',
  `shop_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺编码',
  `retail_type` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺小类 (销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场\n\n特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)',
  `contract_rate` decimal(12,2) DEFAULT NULL COMMENT '合同扣率',
  `actual_rate` decimal(12,2) DEFAULT NULL COMMENT '实际扣率',
  `cost_total` decimal(12,2) DEFAULT NULL COMMENT '销售明细成本汇总',
  `remark` varchar(225) COLLATE utf8_bin DEFAULT NULL COMMENT '备注，在销售明细成本有为0时，需要增加备注说明',
  `cate_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '一级大类编号',
  `cate_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '一级大类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='开票申请－按大类明细';

-- ----------------------------
-- Table structure for bill_balance_invoice_info
-- ----------------------------
DROP TABLE IF EXISTS `bill_balance_invoice_info`;
CREATE TABLE `bill_balance_invoice_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '发票申请单号',
  `balance_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单据编号',
  `invoice_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发票号',
  `amount` decimal(20,2) DEFAULT '0.00' COMMENT '发票金额',
  `tax_amount` decimal(20,2) DEFAULT '0.00' COMMENT '含税金额',
  `invoice_date` date DEFAULT NULL COMMENT '发票日期',
  `delivery_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '快递日期',
  `express` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '快递公司',
  `delivery_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '快递单号',
  `receive_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '接收日期',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '确认人',
  `audit_date` datetime DEFAULT NULL COMMENT '确认日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='开票申请单-发票信息';

-- ----------------------------
-- Table structure for bill_balance_invoice_source
-- ----------------------------
DROP TABLE IF EXISTS `bill_balance_invoice_source`;
CREATE TABLE `bill_balance_invoice_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '发票申请单号',
  `balance_no` char(100) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单据编号',
  `balance_type` int(11) DEFAULT NULL COMMENT '结算类型(1、总部厂商结算,2、总部批发结算, 3、总部其他结算,4、地区采购结算 5、地区间结算 6、地区自购结算 7、地区批发结算 8、地区团购结算 9、地区员购结算 10、地区商场结算 11、地区其他出库结算)',
  `amount` decimal(12,2) DEFAULT '0.00' COMMENT '开票金额',
  `zone_yyyymm` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '大区年月',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='开票申请单-源结算单';

-- ----------------------------
-- Table structure for bill_buy_balance
-- ----------------------------
DROP TABLE IF EXISTS `bill_buy_balance`;
CREATE TABLE `bill_buy_balance` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `bill_type` int(11) DEFAULT NULL COMMENT '单据类型(1301-到货单,1333-调货出库单,1335-客残销售出库单)',
  `biz_type` int(11) DEFAULT NULL COMMENT '业务类型(0：订货 ，1：补货  ，2直接 4、跨区调货 5、差异处理)',
  `ref_bill_no` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '相关单号，多个已，隔开',
  `ref_bill_type` int(11) DEFAULT NULL COMMENT '相关单据类型',
  `original_bill_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '原单据编号',
  `status` int(11) DEFAULT NULL COMMENT '单据状态 1、已发未收 2、已收货 3、审核 5、确认',
  `buyer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '买方编号',
  `buyer_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '买方名称',
  `saler_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方编号',
  `saler_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方名称',
  `send_date` date DEFAULT NULL COMMENT '发出日期',
  `receive_date` date DEFAULT NULL COMMENT '接收日期',
  `send_store_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发出地编号',
  `send_store_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '发出地名称',
  `receive_store_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '收货地编号',
  `receive_store_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '收货地名称',
  `sku_id` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '明细id',
  `sku_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'SKU编号',
  `supplier_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商名称',
  `item_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编号',
  `item_code` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码',
  `item_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编号',
  `brand_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '大类编号',
  `category_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '大类名称',
  `cost` decimal(12,2) DEFAULT '0.00' COMMENT '含税单价(结算价格、默认原单据价格)',
  `bill_cost` decimal(12,2) DEFAULT '0.00' COMMENT '单据价格(原单据价格)',
  `tax_rate` decimal(4,2) DEFAULT NULL COMMENT '税率',
  `exclusive_cost` decimal(12,2) DEFAULT '0.00' COMMENT '不含税单价',
  `send_qty` int(11) DEFAULT '0' COMMENT '发出数量',
  `receive_qty` int(11) DEFAULT '0' COMMENT '接受数量',
  `order_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `balance_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单号',
  `balance_status` tinyint(3) DEFAULT NULL COMMENT '结算单状态',
  `balance_type` tinyint(3) DEFAULT NULL COMMENT '结算类型（总部-地区，总部-供应商）',
  `is_split` tinyint(3) DEFAULT NULL COMMENT '是否拆分单据',
  `path_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算路径编码',
  `order_unit_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方订货单位编号',
  `order_unit_name` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方订货单位名称',
  `organ_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方管理城市编号',
  `organ_name` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方管理城市名称',
  `order_unit_no_from` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方订货单位编号',
  `order_unit_name_from` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方订货单位名称',
  `organ_no_from` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方管理城市编号',
  `organ_name_from` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方管理城市名称',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方所属大区编码',
  `zone_name` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方所属大区名称',
  `zone_no_from` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方所属大区编码',
  `zone_name_from` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方所属大区名称',
  `years` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '年份(指上市的年份,下拉框选择,值: 2006~2026,默认当年)',
  `season` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '季节(下拉框选择,A:春 B:夏 C:秋 D:冬)',
  `orderfrom` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '订货形式(下拉框选择,值: 1:自产 2:外购 3:地区自购)',
  `gender` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别(下拉框选择,值: 男, 女, 童, 无性别)',
  `tag_price` decimal(10,2) DEFAULT '0.00' COMMENT '牌价',
  `zone_yyyymm` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '大区年月',
  PRIMARY KEY (`id`),
  KEY `idx_bill_no` (`bill_no`),
  KEY `idx_item_no` (`item_no`),
  KEY `IDX_1` (`bill_type`,`bill_no`,`item_no`),
  KEY `uk_saler_no` (`saler_no`),
  KEY `uk_buyer_no` (`buyer_no`),
  KEY `uk_brand_no` (`brand_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='采购类单据池';

-- ----------------------------
-- Table structure for bill_common_invoice_register
-- ----------------------------
DROP TABLE IF EXISTS `bill_common_invoice_register`;
CREATE TABLE `bill_common_invoice_register` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '发票单号',
  `saler_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '公司编码(开票方)-卖方',
  `saler_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方名称',
  `buyer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '客户编码(收票方)-买方',
  `buyer_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '买方名称',
  `bill_date` date DEFAULT NULL COMMENT '单据日期',
  `amount` decimal(20,2) DEFAULT '0.00' COMMENT '开票总金额',
  `status` tinyint(3) DEFAULT '1' COMMENT '单据状态(0-制单、1-确认)',
  `invoice_type` int(11) DEFAULT NULL COMMENT '发票类型（1 = 普通发票 2 = 增值票）',
  `pre_invoice` tinyint(3) unsigned DEFAULT '1' COMMENT '是否预开票(2 = 是 1 = 否)',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '审核时间',
  `balance_type` int(11) DEFAULT NULL COMMENT '源单类型(1、总部厂商结算,2、总部批发结算, 3、总部其他结算,4、地区采购结算 5、地区间结算 6、地区自购结算 7、地区批发结算 8、地区团购结算 9、地区员购结算 10、地区商场结算 11、地区其他出库结算)',
  `invoice_flag` tinyint(3) DEFAULT '0' COMMENT '采购发票生成标志：0、未生成 1、已生成',
  `use_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '发票是否已使用：0 未使用，1 已使用',
  `shop_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺编号',
  `shop_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺名称',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '商场名称',
  `balance_start_date` date DEFAULT NULL COMMENT '结算起始日期',
  `balance_end_date` date DEFAULT NULL COMMENT '结算终止日期',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `zone_yyyymm` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '大区年月',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='普通发票登记';

-- ----------------------------
-- Table structure for bill_common_register_dtl
-- ----------------------------
DROP TABLE IF EXISTS `bill_common_register_dtl`;
CREATE TABLE `bill_common_register_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据号',
  `invoice_code` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发票编号',
  `invoice_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发票号',
  `invoice_date` date DEFAULT NULL COMMENT '发票日期',
  `amount` decimal(20,2) DEFAULT '0.00' COMMENT '发票金额',
  `estimated_amount` decimal(20,2) DEFAULT '0.00' COMMENT '预估成本',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '类别编码',
  `category_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '类别名称',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌中文名称',
  `qty` int(11) DEFAULT '0' COMMENT '商品数量',
  `tax_rate` decimal(9,4) DEFAULT NULL COMMENT '税率',
  `no_tax_amount` decimal(18,8) DEFAULT NULL COMMENT '不含税金额',
  `tax_amount` decimal(18,8) DEFAULT NULL COMMENT '税额',
  `price` decimal(12,2) DEFAULT NULL COMMENT '价格',
  `delivery_date` datetime DEFAULT NULL COMMENT '快递日期',
  `express` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '快递公司',
  `delivery_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '快递单号',
  `receive_date` datetime DEFAULT NULL COMMENT '接收日期',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '确认人',
  `audit_date` datetime DEFAULT NULL COMMENT '确认日期',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `zone_yyyymm` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '大区年月',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='普通发票登记明细记录';

-- ----------------------------
-- Table structure for bill_cust_fine_nt
-- ----------------------------
DROP TABLE IF EXISTS `bill_cust_fine_nt`;
CREATE TABLE `bill_cust_fine_nt` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '单据编码',
  `status` tinyint(3) DEFAULT NULL COMMENT '单据状态',
  `bill_date` date DEFAULT NULL COMMENT '单据日期',
  `company_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '结算主体编码',
  `company_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '结算主体名称',
  `customer_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '客户编码',
  `customer_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '客户名称',
  `margin_amount` decimal(12,2) DEFAULT '0.00' COMMENT '保证金金额',
  `reced_margin_amount` decimal(12,2) DEFAULT '0.00' COMMENT '已收保证金金额',
  `fine_amount` decimal(12,2) DEFAULT '0.00' COMMENT '罚没金额',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_status` tinyint(3) DEFAULT NULL COMMENT '审核状态(0 = 未审核 1 = 已审核)',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='保证金罚没通知单';

-- ----------------------------
-- Table structure for bill_cust_margin_nt
-- ----------------------------
DROP TABLE IF EXISTS `bill_cust_margin_nt`;
CREATE TABLE `bill_cust_margin_nt` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '单据编码',
  `company_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '结算主体编码',
  `company_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '结算主体名称',
  `customer_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '客户编码',
  `customer_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '客户名称',
  `bill_date` date DEFAULT NULL COMMENT '单据日期',
  `margin_amount` decimal(12,2) DEFAULT '0.00' COMMENT '保证金金额',
  `reced_margin_amount` decimal(12,2) DEFAULT '0.00' COMMENT '已收保证金金额',
  `rece_margin_amount` decimal(12,2) DEFAULT '0.00' COMMENT '应收保证金',
  `paid_margin_amount` decimal(12,2) DEFAULT '0.00' COMMENT '实收保证金金额',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_status` tinyint(3) DEFAULT NULL COMMENT '审核状态(0 = 未审核 1 = 已审核)',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) DEFAULT NULL COMMENT '单据状态',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='客户保证金催缴通知单';

-- ----------------------------
-- Table structure for bill_differences
-- ----------------------------
DROP TABLE IF EXISTS `bill_differences`;
CREATE TABLE `bill_differences` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT 'UUID',
  `bill_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '单据编号,来源单据的单据编号',
  `handle_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '处理单号',
  `bill_type` int(11) NOT NULL COMMENT '单据类型',
  `asn_bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '到货单单据编号',
  `status` tinyint(3) DEFAULT '0' COMMENT '单据状态 (0-制单,1-出货,2-收货,98-删除,99-作废,100-完结)',
  `adjustment_type` tinyint(3) DEFAULT NULL COMMENT '调整类型:1.数据出错,2:实物丢失',
  `sys_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '制单机构',
  `order_unit_no_from` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发货订货单位',
  `order_unit_name_from` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方订货单位名称',
  `order_unit_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '收货订货单位',
  `order_unit_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方订货单位名称',
  `store_no_from` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '机构编码,调出机构',
  `store_name_from` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方机构名称(如果是店铺则保存店铺名称其他则保存仓库名称)',
  `shop_no_from` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺,调出店铺',
  `store_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '调入机构编码',
  `store_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方机构名称(如果是店铺则保存店铺名称其他则保存仓库名称)',
  `shop_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '调入店铺编码',
  `supplier_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商名称',
  `send_date` date DEFAULT NULL COMMENT '发出时间',
  `receipt_date` date DEFAULT NULL COMMENT '接受时间',
  `item_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '商品内码',
  `item_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码',
  `item_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `color_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '颜色编码',
  `color_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '颜色名称',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `sku_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'sku编码',
  `size_no` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '尺寸编号',
  `send_out_qty` int(11) NOT NULL DEFAULT '0' COMMENT '发出数量',
  `stock_in_qty` int(11) NOT NULL DEFAULT '0' COMMENT '接收数量',
  `differences_qty` int(11) NOT NULL DEFAULT '0' COMMENT '差异数量',
  `adjustment_qty` int(11) NOT NULL DEFAULT '0' COMMENT '调整数量',
  `adjustment_direction` int(11) DEFAULT NULL COMMENT '调整方向:1.收货方,2:发货方',
  `cost` decimal(12,2) DEFAULT '0.00' COMMENT '含税单价',
  `adjustment_cost` decimal(12,2) DEFAULT '0.00' COMMENT '调整金额',
  `merchandiser` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '业务员',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `box_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '箱号',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='差异报表';

-- ----------------------------
-- Table structure for bill_invoice
-- ----------------------------
DROP TABLE IF EXISTS `bill_invoice`;
CREATE TABLE `bill_invoice` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `balance_type` tinyint(3) DEFAULT NULL COMMENT '结算类型',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `bill_date` date DEFAULT NULL COMMENT '单据日期',
  `status` tinyint(3) DEFAULT '0' COMMENT '单据状态',
  `saler_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '买方编码',
  `saler_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '买方名称',
  `buyer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方编码',
  `buyer_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方名称',
  `amount` decimal(12,2) DEFAULT '0.00' COMMENT '金额',
  `currency` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '币别',
  `ref_bill_no` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '源单编码',
  `ref_amount` decimal(12,2) DEFAULT '0.00' COMMENT '源单金额',
  `payment_no` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '付款单号',
  `payment_amount` decimal(12,2) DEFAULT '0.00' COMMENT '付款金额',
  `create_user` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `auditor` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审批人',
  `audit_time` datetime DEFAULT NULL COMMENT '审批时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `is_generate` int(11) DEFAULT '0' COMMENT '是否生成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='发票';

-- ----------------------------
-- Table structure for bill_invoice_dtl
-- ----------------------------
DROP TABLE IF EXISTS `bill_invoice_dtl`;
CREATE TABLE `bill_invoice_dtl` (
  `id` int(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `invoice_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发票号',
  `invoice_code` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '发票编码',
  `invoice_date` date DEFAULT NULL COMMENT '发票日期',
  `invoice_amount` decimal(12,2) DEFAULT '0.00' COMMENT '发票金额',
  `tax_rate` decimal(9,4) DEFAULT NULL COMMENT '税率',
  `no_tax_amount` decimal(20,8) DEFAULT NULL COMMENT '不含税税额',
  `tax_amount` decimal(20,8) DEFAULT NULL COMMENT '税额',
  `qty` int(11) DEFAULT '0' COMMENT '数量',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '大类编码',
  `category_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '大类名称',
  `price` decimal(12,2) DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for bill_mall_deduction_cost
-- ----------------------------
DROP TABLE IF EXISTS `bill_mall_deduction_cost`;
CREATE TABLE `bill_mall_deduction_cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `organ_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市编号',
  `organ_name` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市名称',
  `bsgroups_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团编码',
  `bsgroups_name` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团名称',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '商场名称',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `shop_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '品牌编码',
  `period` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '费用所属期间-会计期间结算月',
  `nc_period` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT 'NC月份',
  `cost_type` tinyint(3) DEFAULT '1' COMMENT '费用性质(1-合同内,2-合同外)',
  `cost_deduct_type` tinyint(3) DEFAULT '1' COMMENT '费用扣取方式(1-票前,2-票后)',
  `cost_pay_type` tinyint(3) DEFAULT '1' COMMENT '费用交款方式(1-帐扣,2-现金)',
  `cost_cate_code` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '费用类别编码',
  `cost_cate_name` varchar(60) COLLATE utf8_bin DEFAULT 'null' COMMENT '费用类别名称',
  `deduction_code` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '商场扣费编码',
  `deduction_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '商场扣费名称',
  `bill_date` date DEFAULT NULL COMMENT '业务单据日期',
  `deduct_amount` decimal(20,2) DEFAULT '0.00' COMMENT '扣费金额',
  `actual_amount` decimal(20,2) DEFAULT NULL COMMENT '实际金额',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场费用表';

-- ----------------------------
-- Table structure for bill_payment
-- ----------------------------
DROP TABLE IF EXISTS `bill_payment`;
CREATE TABLE `bill_payment` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `balance_type` tinyint(3) DEFAULT NULL COMMENT '结算类型',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `bill_date` date DEFAULT NULL COMMENT '单据日期',
  `status` tinyint(3) DEFAULT '0' COMMENT '单据状态',
  `saler_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '买方编码',
  `saler_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '买方名称',
  `buyer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方编码',
  `buyer_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方名称',
  `amount` decimal(12,2) DEFAULT '0.00' COMMENT '金额',
  `currency` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '币别',
  `ref_bill_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '源单编码',
  `ref_amount` decimal(12,2) DEFAULT '0.00' COMMENT '源单金额',
  `create_user` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `auditor` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审批人',
  `audit_time` datetime DEFAULT NULL COMMENT '审批时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `is_generate` int(11) DEFAULT '0' COMMENT '是否生成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='发票';

-- ----------------------------
-- Table structure for bill_payment_dtl
-- ----------------------------
DROP TABLE IF EXISTS `bill_payment_dtl`;
CREATE TABLE `bill_payment_dtl` (
  `id` int(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `settle_method_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算方式编码',
  `settle_method_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算方式名称',
  `payment_application` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '付款用途',
  `advance_payment_order_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '预付订单号',
  `pay_amount` decimal(12,2) DEFAULT '0.00' COMMENT '应付金额',
  `money_discount` decimal(10,2) DEFAULT '0.00' COMMENT '现金折扣',
  `discount_amount` decimal(12,2) DEFAULT '0.00' COMMENT '折后金额',
  `fee` decimal(10,2) DEFAULT '0.00' COMMENT '手续费',
  `balance_amount` decimal(12,2) DEFAULT '0.00' COMMENT '实付金额',
  `bank_account` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '银行帐号',
  `bank_balance_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '银行结算号',
  `remark` char(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for bill_pre_payment_nt
-- ----------------------------
DROP TABLE IF EXISTS `bill_pre_payment_nt`;
CREATE TABLE `bill_pre_payment_nt` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '单据编码',
  `balance_type` tinyint(3) NOT NULL COMMENT '结算类型',
  `status` tinyint(3) DEFAULT NULL COMMENT '单据状态',
  `company_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '结算主体编码',
  `company_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '结算主体名称',
  `customer_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '客户编码',
  `customer_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '客户名称',
  `bill_date` date DEFAULT NULL COMMENT '单据日期',
  `sale_order_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '销售订单编码',
  `order_qty` int(11) DEFAULT '0' COMMENT '订单数量',
  `order_amount` decimal(12,2) DEFAULT '0.00' COMMENT '订单金额',
  `rebate_amount` decimal(12,2) DEFAULT '0.00' COMMENT '返利金额',
  `pre_order_amount` decimal(12,2) DEFAULT '0.00' COMMENT '订货预收款',
  `pre_send_amount` decimal(12,2) DEFAULT '0.00' COMMENT '发货预收款',
  `paid_type` tinyint(3) DEFAULT NULL COMMENT '收款类型',
  `term_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '收款条款',
  `receivable_amount` decimal(12,2) DEFAULT '0.00' COMMENT '应收金额',
  `paid_amount` decimal(12,2) DEFAULT '0.00' COMMENT '实收金额',
  `pre_payment_over` decimal(12,2) DEFAULT '0.00' COMMENT '订单预收款余额',
  `reversal_amount` decimal(12,2) DEFAULT '0.00' COMMENT '冲销金额',
  `reversal_amount_flag` tinyint(3) unsigned DEFAULT NULL COMMENT '是否冲销客户预收余额（0 = 是 1 = 否）',
  `invoice_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发票号',
  `invoice_type` char(2) COLLATE utf8_bin DEFAULT '0' COMMENT '发票类型',
  `invoice_date` date DEFAULT NULL COMMENT '发票日期',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_status` tinyint(3) DEFAULT NULL COMMENT '审核状态(0 = 未审核 1 = 已审核)',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='预收款缴款通知单';

-- ----------------------------
-- Table structure for bill_sale_balance
-- ----------------------------
DROP TABLE IF EXISTS `bill_sale_balance`;
CREATE TABLE `bill_sale_balance` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `bill_type` int(11) DEFAULT NULL COMMENT '单据类型(1301-到货单,1333-调货出库单,1335-客残销售出库单)',
  `biz_type` int(11) DEFAULT NULL COMMENT '业务类型(0：订货 ，1：补货  ，2直接 4、跨区调货 5、差异处理)',
  `ref_bill_no` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '相关单号，多个已，隔开',
  `ref_bill_type` int(11) DEFAULT NULL COMMENT '相关单据类型',
  `original_bill_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '原单据编号',
  `status` int(11) DEFAULT NULL COMMENT '单据状态 1、已发未收 2、已收货 3、审核 5、确认',
  `buyer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '买方编号',
  `buyer_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '买方名称',
  `saler_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方编号',
  `saler_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方名称',
  `send_date` date DEFAULT NULL COMMENT '发出日期',
  `receive_date` date DEFAULT NULL COMMENT '接收日期',
  `send_store_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发出地编号',
  `send_store_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '发出地名称',
  `receive_store_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '收货地编号',
  `receive_store_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '收货地名称',
  `sku_id` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '明细id',
  `sku_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'SKU编号',
  `supplier_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商名称',
  `item_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编号',
  `item_code` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码',
  `item_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编号',
  `brand_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '大类编号',
  `category_name` char(200) COLLATE utf8_bin DEFAULT NULL COMMENT '大类名称',
  `cost` decimal(12,2) DEFAULT '0.00' COMMENT '含税单价(结算价格、默认原单据价格)',
  `bill_cost` decimal(12,2) DEFAULT '0.00' COMMENT '单据价格(原单据价格)',
  `tax_rate` decimal(4,2) DEFAULT NULL COMMENT '税率',
  `exclusive_cost` decimal(12,2) DEFAULT '0.00' COMMENT '不含税单价',
  `send_qty` int(11) DEFAULT '0' COMMENT '发出数量',
  `receive_qty` int(11) DEFAULT '0' COMMENT '接受数量',
  `order_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `balance_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单号',
  `balance_type` tinyint(3) DEFAULT NULL COMMENT '结算单类型',
  `balance_status` tinyint(3) DEFAULT NULL COMMENT '结算单状态',
  `is_split` tinyint(3) DEFAULT NULL COMMENT '是否拆分单据',
  `path_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算路径编码',
  `order_unit_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方订货单位编号',
  `order_unit_name` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方订货单位名称',
  `organ_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方管理城市编号',
  `organ_name` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方管理城市名称',
  `order_unit_no_from` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方订货单位编号',
  `order_unit_name_from` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方订货单位名称',
  `organ_no_from` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方管理城市编号',
  `organ_name_from` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方管理城市名称',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方所属大区编码',
  `zone_name` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '收货方所属大区名称',
  `zone_no_from` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方所属大区编码',
  `zone_name_from` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方所属大区名称',
  `is_bill_invoice` tinyint(3) DEFAULT NULL COMMENT '是否已开票(0:已生成 1:未生成)',
  `invoice_apply_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '开票申请编号',
  `invoice_apply_date` date DEFAULT NULL COMMENT '开票申请日期',
  `years` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '年份(指上市的年份,下拉框选择,值: 2006~2026,默认当年)',
  `season` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '季节(下拉框选择,A:春 B:夏 C:秋 D:冬)',
  `orderfrom` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '订货形式(下拉框选择,值: 1:自产 2:外购 3:地区自购)',
  `gender` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别(下拉框选择,值: 男, 女, 童, 无性别)',
  `tag_price` decimal(10,2) DEFAULT '0.00' COMMENT '牌价',
  `zone_yyyymm` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '大区年月',
  PRIMARY KEY (`id`),
  KEY `IDX_1` (`bill_type`,`bill_no`,`item_no`),
  KEY `uk_saler_no` (`saler_no`),
  KEY `uk_buyer_no` (`buyer_no`),
  KEY `uk_bill_no` (`bill_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='出库类单据池';

-- ----------------------------
-- Table structure for bill_sales_sum
-- ----------------------------
DROP TABLE IF EXISTS `bill_sales_sum`;
CREATE TABLE `bill_sales_sum` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `region_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '片区编码',
  `region_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '片区名称',
  `province_no` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '行政省编码',
  `province_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '行政省名称',
  `organ_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市编号',
  `organ_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市名称',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `zone_name` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域名称',
  `major` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '主营品类(门店必填, 1:男鞋 2:女鞋 3:童鞋 4:综合)',
  `multi` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺细类 单品多品(门店必填,C:多品店 D:单品店)',
  `category_code` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '经营类型（男鞋，女鞋，综合）',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '品牌中文名称',
  `category` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '品牌类别(D:代理品牌，S:自有品牌)',
  `belonger` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '品牌归属(X:鞋，S:体)',
  `brand_flag` tinyint(3) DEFAULT '0' COMMENT '是否主营品牌(1是，0否)',
  `year` char(4) COLLATE utf8_bin DEFAULT NULL COMMENT '年份',
  `month` char(2) COLLATE utf8_bin DEFAULT NULL COMMENT '月份',
  `balance_start_date` date DEFAULT NULL COMMENT '结算起始日期',
  `balance_end_date` date DEFAULT NULL COMMENT '结算终止日期',
  `billing_date` date DEFAULT NULL COMMENT '开票日期',
  `bill_date` date DEFAULT NULL COMMENT '单据日期',
  `bsgroups_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团编码',
  `bsgroups_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团名称',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺全称',
  `sale_mode` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺大类 批发零售(门店必填,1:零售；2:批发)',
  `retail_type` varchar(20) COLLATE utf8_bin DEFAULT 'A0' COMMENT '店铺小类 (销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场\n\n特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)',
  `shop_classify` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺类别',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '店铺状态( 0:冻结,1:正常,9:撤销)',
  `open_date` date DEFAULT NULL COMMENT '成立日期(店铺正式营业的日期)',
  `employe_amount` int(8) DEFAULT NULL COMMENT '店员配备数(门店必填,指标准的店员配备数量)',
  `area` decimal(10,2) DEFAULT '0.00' COMMENT '卖场面积',
  `area_left` decimal(10,2) DEFAULT '0.00' COMMENT '仓库面积',
  `area_total` decimal(10,2) DEFAULT '0.00' COMMENT '总面积',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '类别编码',
  `shop_level` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '门店级别( A、B、C、D、E)',
  `category_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '类别名称',
  `lma_period_salesnum` decimal(20,2) DEFAULT '0.00' COMMENT '上月结算期后销售量',
  `lma_period_salesamount` decimal(20,2) DEFAULT '0.00' COMMENT '上月结算期后销售收入',
  `lma_period_salescost` decimal(20,2) DEFAULT '0.00' COMMENT '上月结算期后销售成本',
  `lma_period_salesdeductions` decimal(20,2) DEFAULT '0.00' COMMENT '上月结算期后商场扣费',
  `lma_period_balanceamount` decimal(20,2) DEFAULT '0.00' COMMENT '上月结算期后结算收入',
  `tmi_period_salesnum` decimal(20,2) DEFAULT '0.00' COMMENT '本月结算期内销售数量',
  `tmi_period_salesamount` decimal(20,2) DEFAULT '0.00' COMMENT '本月结算期内销售收入',
  `tmi_period_salescost` decimal(20,2) DEFAULT '0.00' COMMENT '本月结算期内销售成本',
  `tmi_period_salesdeductions` decimal(20,2) DEFAULT '0.00' COMMENT '本月结算期内商场扣费',
  `tmi_period_balanceamount` decimal(20,2) DEFAULT '0.00' COMMENT '本月结算期内结算收入',
  `bi_period_salesnum` decimal(20,2) DEFAULT '0.00' COMMENT '结算期内合计销售数量',
  `bi_period_salesamount` decimal(20,2) DEFAULT '0.00' COMMENT '结算期内合计销售收入',
  `bi_period_salescost` decimal(20,2) DEFAULT '0.00' COMMENT '结算期内合计销售成本',
  `bi_period_salesdeductions` decimal(20,2) DEFAULT '0.00' COMMENT '结算期内合计商场扣费',
  `bi_period_balanceamount` decimal(20,2) DEFAULT '0.00' COMMENT '结算期内合计结算收入',
  `qty` int(11) DEFAULT NULL COMMENT '数量',
  `send_amount` decimal(12,2) DEFAULT NULL COMMENT '金额',
  `invoice_apply_date` date DEFAULT NULL COMMENT '申请开票日期',
  `saler_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方名称',
  `buyer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '客户编码(收票方)-买方',
  `tma_period_salesnum` decimal(20,2) DEFAULT '0.00' COMMENT '本月结算期后销售量',
  `tma_period_salesamount` decimal(20,2) DEFAULT '0.00' COMMENT '本月结算期后销售收入',
  `tma_period_salescost` decimal(20,2) DEFAULT '0.00' COMMENT '本月结算期后销售成本',
  `tma_period_salesdeductions` decimal(20,2) DEFAULT '0.00' COMMENT '本月结算期后商场扣费',
  `tma_period_balanceamount` decimal(20,2) DEFAULT '0.00' COMMENT '本月结算期后结算收入',
  `tm_salesnum` decimal(20,2) DEFAULT '0.00' COMMENT '本月合计销售量',
  `tm_salesamount` decimal(20,2) DEFAULT '0.00' COMMENT '本月合计销售收入',
  `tm_salescost` decimal(20,2) DEFAULT '0.00' COMMENT '本月合计销售成本',
  `tm_salesdeductions` decimal(20,2) DEFAULT '0.00' COMMENT '本月合计商场扣费',
  `tm_balanceamount` decimal(20,2) DEFAULT '0.00' COMMENT '本月合计结算收入',
  `contract_rate` decimal(8,4) DEFAULT NULL COMMENT '合同扣率',
  `actual_rate` decimal(8,4) DEFAULT NULL COMMENT '实际扣率',
  `tm_mall_deductions` decimal(20,2) DEFAULT '0.00' COMMENT '本月商场扣费合计',
  `tm_salesamount_discount` decimal(20,2) DEFAULT NULL COMMENT '本月商场正价折扣',
  `tm_salesamount_prodiscount` decimal(20,2) DEFAULT NULL COMMENT '本月商场促销折扣',
  `balance_diff_amount` decimal(20,2) DEFAULT '0.00' COMMENT '本月结算差异',
  `balance_salesamount` decimal(12,2) DEFAULT '0.00' COMMENT '本月结算收入',
  `non_tax_salesamount` decimal(12,2) DEFAULT '0.00' COMMENT '本月无税收入',
  `tax_cost` decimal(12,2) DEFAULT '0.00' COMMENT '本月含税成本',
  `no_tax_costs` decimal(12,2) DEFAULT '0.00' COMMENT '本月无税成本',
  `headquarter_cost` decimal(12,2) DEFAULT '0.00' COMMENT '总部成本',
  `tag_price_amount` decimal(20,2) DEFAULT NULL COMMENT '本月合计销售牌价',
  `no_billing_amount` decimal(20,2) DEFAULT '0.00' COMMENT '未开票金额',
  `balance_qty` int(11) DEFAULT '0' COMMENT '实物库存（正品库存量）',
  `balance_qty_amount` decimal(20,2) DEFAULT '0.00' COMMENT '库存金额',
  `channel_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '销售渠道编码',
  `channel_name` char(50) COLLATE utf8_bin DEFAULT NULL COMMENT '销售渠道名称',
  `organ_level` int(11) DEFAULT NULL COMMENT '组织级别,1 管理城市,2 经营城市',
  `mall_deduct_amount` decimal(20,2) DEFAULT '0.00' COMMENT '扣费金额',
  `billing_amount` decimal(20,2) DEFAULT '0.00' COMMENT '开票金额',
  `str_desc` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `str_remark` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `num_desc` decimal(20,2) DEFAULT '0.00' COMMENT '金额描述',
  `num_remark` decimal(20,2) DEFAULT '0.00' COMMENT '金额备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_salessum` (`company_no`,`zone_no`,`organ_no`,`bsgroups_no`,`region_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='销售汇总表';

-- ----------------------------
-- Table structure for bill_shop_balance
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance`;
CREATE TABLE `bill_shop_balance` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `balance_no` char(20) COLLATE utf8_bin NOT NULL COMMENT '结算单据编号',
  `bill_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '单据名称',
  `bill_date` date DEFAULT NULL COMMENT '单据日期',
  `balance_type` tinyint(3) unsigned DEFAULT NULL COMMENT '结算单类型(1-正式，2-预估)',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '单据状态(1-制单、2-确认、3-作废、4、已开票)',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `zone_name` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域名称',
  `organ_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市编号',
  `organ_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市名称',
  `bsgroups_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团编码',
  `bsgroups_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团名称',
  `region_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '片区编码',
  `region_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '片区名称',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '商场名称',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺全称',
  `retail_type` varchar(20) COLLATE utf8_bin DEFAULT 'A0' COMMENT '(销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '类别编码',
  `category_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '类别名称',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '品牌中文名称',
  `month` char(15) COLLATE utf8_bin DEFAULT NULL COMMENT '月份',
  `balance_start_date` date DEFAULT NULL COMMENT '结算起始日期',
  `balance_end_date` date DEFAULT NULL COMMENT '结算终止日期',
  `billing_date` date DEFAULT NULL COMMENT '开票日期',
  `print_count` int(11) DEFAULT '0' COMMENT '打印次数',
  `contract_rate` decimal(8,4) DEFAULT NULL COMMENT '合同扣率',
  `actual_rate` decimal(8,4) DEFAULT NULL COMMENT '实际扣率',
  `mall_number_amount` decimal(20,2) DEFAULT NULL COMMENT '商场报数',
  `system_sales_amount` decimal(20,2) DEFAULT NULL COMMENT '系统收入',
  `sales_diffamount` decimal(12,2) DEFAULT NULL COMMENT '报数差异',
  `mall_deduct_amount` decimal(20,2) DEFAULT '0.00' COMMENT '扣费金额',
  `billing_amount` decimal(20,2) DEFAULT '0.00' COMMENT '开票金额',
  `mall_billing_amount` decimal(20,2) DEFAULT '0.00' COMMENT '商场开票金额',
  `system_billing_amount` decimal(20,2) DEFAULT '0.00' COMMENT '系统开票金额',
  `no_billing_amount` decimal(20,2) DEFAULT '0.00' COMMENT '未开票金额',
  `expect_cash_amount` decimal(20,2) DEFAULT '0.00' COMMENT '预收款金额合计',
  `balance_deduct_amount` decimal(20,2) DEFAULT '0.00' COMMENT '票前费用扣款金额合计',
  `balance_diff_amount` decimal(20,2) DEFAULT '0.00' COMMENT '结算差异金额合计',
  `other_total_amount` decimal(20,2) DEFAULT NULL COMMENT '其他合计',
  `difference_amount` decimal(20,2) DEFAULT '0.00' COMMENT '收款差异',
  `estimate_amount` decimal(20,2) DEFAULT '0.00' COMMENT '预估差异',
  `payment_amount` decimal(20,2) DEFAULT '0.00' COMMENT '回款金额',
  `non_payment_amount` decimal(20,2) DEFAULT '0.00' COMMENT '未回款金额',
  `balance_diff_type` tinyint(3) unsigned DEFAULT '1' COMMENT '结算差异方式设置(1-按明细、2-按促销活动、3-按销售)',
  `balance_desc` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '结算描述',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `invoice_apply_no` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '开票申请单号',
  `invoice_register_no` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '发票登记申请单号',
  `invoice_apply_date` date DEFAULT NULL COMMENT '申请开票日期',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `zone_yyyymm` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '大区年月',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_shopbalance` (`company_no`,`zone_no`,`organ_no`,`bsgroups_no`,`region_no`,`mall_no`,`balance_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算主表';

-- ----------------------------
-- Table structure for bill_shop_balance_account
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_account`;
CREATE TABLE `bill_shop_balance_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '收款单号',
  `advance_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '预收单号(主要是批发)',
  `balance_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '结算单据编号',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `amount` decimal(12,2) DEFAULT '0.00' COMMENT '金额',
  `proceeds_date` date DEFAULT NULL COMMENT '收款日期',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算收款表';

-- ----------------------------
-- Table structure for bill_shop_balance_advance
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_advance`;
CREATE TABLE `bill_shop_balance_advance` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `balance_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '结算单据编号',
  `advance_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '预收单号编码',
  `advance_date` date DEFAULT NULL COMMENT '预收日期',
  `advance_amount` decimal(20,2) DEFAULT '0.00' COMMENT '预收金额',
  `advance_balance` decimal(10,2) DEFAULT NULL COMMENT '预收款余额',
  `order_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `customer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '客户编码',
  `wash_amount` decimal(20,2) DEFAULT '0.00' COMMENT '冲销金额',
  `wash_date` date DEFAULT NULL COMMENT '冲销日期',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算商场预收款表';

-- ----------------------------
-- Table structure for bill_shop_balance_back
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_back`;
CREATE TABLE `bill_shop_balance_back` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `diff_bill_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '差异编号',
  `root_diff_id` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '初始差异id',
  `balance_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单据编号',
  `back_date` date DEFAULT NULL COMMENT '回款日',
  `back_amount` decimal(12,2) DEFAULT '0.00' COMMENT '回款金额',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算差异回款列表';

-- ----------------------------
-- Table structure for bill_shop_balance_cate_sum
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_cate_sum`;
CREATE TABLE `bill_shop_balance_cate_sum` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `balance_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单据编号',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '商场名称',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺全称',
  `month` char(15) COLLATE utf8_bin DEFAULT NULL COMMENT '月份',
  `balance_start_date` date DEFAULT NULL COMMENT '结算起始日期',
  `balance_end_date` date DEFAULT NULL COMMENT '结算终止日期',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '品牌中文名称',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '类别编码',
  `category_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '类别名称',
  `sale_qty` int(11) DEFAULT NULL COMMENT '商品数量',
  `sale_amount` decimal(20,2) DEFAULT NULL COMMENT '销售金额',
  `billing_amount` decimal(20,2) DEFAULT '0.00' COMMENT '开票金额',
  `zone_yyyymm` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '大区年月',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算期大类汇总数据';

-- ----------------------------
-- Table structure for bill_shop_balance_daysale_sum
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_daysale_sum`;
CREATE TABLE `bill_shop_balance_daysale_sum` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,uuid生成',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `balance_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单据编号',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '商场名称',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺全称',
  `out_date` date DEFAULT NULL COMMENT '销售日期',
  `sale_amount` decimal(20,2) DEFAULT NULL COMMENT '销售总金额',
  `pay_code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式代号',
  `pay_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式名称',
  `payway_num` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式编号,卡号、券号、结算号',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '金额',
  `zone_yyyymm` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '大区年月',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算每日销售汇总数据';

-- ----------------------------
-- Table structure for bill_shop_balance_deduct
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_deduct`;
CREATE TABLE `bill_shop_balance_deduct` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `balance_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单据编号',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `organ_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市编号',
  `organ_name` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市名称',
  `bsgroups_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团编码',
  `bsgroups_name` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团名称',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '商场名称',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '品牌编码',
  `month` char(15) COLLATE utf8_bin DEFAULT NULL COMMENT '费用所属期间-会计期间结算月',
  `balance_start_date` date DEFAULT NULL COMMENT '结算起始日期',
  `balance_end_date` date DEFAULT NULL COMMENT '结算终止日期',
  `nc_period` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT 'NC月份',
  `cost_type` tinyint(3) DEFAULT NULL COMMENT '费用性质(1-合同内,2-合同外)',
  `cost_deduct_type` tinyint(3) DEFAULT NULL COMMENT '费用扣取方式(1-票前,2-票后)',
  `cost_pay_type` tinyint(3) DEFAULT NULL COMMENT '费用交款方式(1-帐扣,2-现金)',
  `invoice_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发票号',
  `invoice_amount` decimal(20,2) DEFAULT '0.00' COMMENT '发票金额',
  `account_deduct_amount` decimal(20,2) DEFAULT '0.00' COMMENT '账扣金额',
  `cost_cate_code` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '费用类别编码',
  `cost_cate_name` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '费用类别名称',
  `deduction_code` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '商场扣费编码',
  `deduction_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '商场扣费名称',
  `bill_date` date DEFAULT NULL COMMENT '业务单据日期',
  `deduct_date` date DEFAULT NULL COMMENT '扣费日期',
  `deduct_amount` decimal(20,2) DEFAULT '0.00' COMMENT '系统扣费金额',
  `actual_amount` decimal(20,2) DEFAULT NULL COMMENT '实际金额',
  `diff_amount` decimal(20,2) DEFAULT NULL COMMENT '扣费差异金额=系统扣费金额-实际金额',
  `base_pay_code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式',
  `base_other` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '其他',
  `rate` decimal(8,4) DEFAULT NULL COMMENT '费率',
  `rate_amount` decimal(20,2) DEFAULT '0.00' COMMENT '费率金额',
  `balance_rate` decimal(8,4) DEFAULT NULL COMMENT '补差费率',
  `balance_rate_amount` decimal(20,2) DEFAULT '0.00' COMMENT '补差金额',
  `diff_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '差异原因',
  `mark_id` int(11) DEFAULT NULL COMMENT '唯一标示，历史发生的记录',
  `par_balance_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '上期结算编号，滚动add',
  `balance_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '结算人员',
  `balance_status` tinyint(3) DEFAULT '1' COMMENT '结算状态(1-未结算、2-已结算、3-作废、4-已开票)',
  `process_status` tinyint(3) DEFAULT '1' COMMENT '处理状态(1-未完成、2-已完成)',
  `deduct_type` tinyint(3) DEFAULT NULL COMMENT '费用类型(1-固定额度,2-扣率计算额度)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '审核时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算扣费表';

-- ----------------------------
-- Table structure for bill_shop_balance_diff
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_diff`;
CREATE TABLE `bill_shop_balance_diff` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `balance_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单据编号',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `zone_name` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域名称',
  `organ_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市编号',
  `organ_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '管理城市名称',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '品牌中文名称',
  `diff_type_code` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '差异类型',
  `diff_type_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '差异类型',
  `diff_date` date DEFAULT NULL COMMENT '差异结算日',
  `balance_date` date DEFAULT NULL COMMENT '结算日',
  `month` char(15) COLLATE utf8_bin DEFAULT NULL COMMENT '费用所属期间-会计期间结算月',
  `bill_date` date DEFAULT NULL COMMENT '业务单据日期',
  `pro_no` varchar(250) COLLATE utf8_bin DEFAULT NULL COMMENT '促销活动编号',
  `pro_name` varchar(350) COLLATE utf8_bin DEFAULT NULL COMMENT '促销活动名称',
  `pro_start_date` date DEFAULT NULL COMMENT '活动起始日',
  `pro_end_date` date DEFAULT NULL COMMENT '活动终止日',
  `discount` decimal(10,4) DEFAULT '0.0000' COMMENT '扣率,如0.17',
  `deduct_diff_amount` decimal(12,2) DEFAULT '0.00' COMMENT '系统扣费',
  `mall_number` decimal(10,2) DEFAULT NULL COMMENT '商场报数',
  `sales_amount` decimal(20,2) DEFAULT '0.00' COMMENT '系统收入',
  `sales_diffamount` decimal(12,2) DEFAULT NULL COMMENT '报数差异',
  `diff_amount` decimal(12,2) DEFAULT '0.00' COMMENT '扣费差异',
  `diff_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '差异原因',
  `diff_balance` decimal(10,2) DEFAULT NULL COMMENT '差异余额',
  `pre_diff_balance` decimal(10,2) DEFAULT NULL COMMENT '上期差异余额',
  `diff_back_amount` decimal(12,2) DEFAULT '0.00' COMMENT '差异回款',
  `balance_amount` decimal(12,2) DEFAULT '0.00' COMMENT '本次结算金额',
  `reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '原因',
  `change_amount` decimal(20,2) DEFAULT '0.00' COMMENT '调整金额',
  `change_date` date DEFAULT NULL COMMENT '调整日期',
  `change_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '调整原因',
  `status` tinyint(3) DEFAULT '0' COMMENT '状态(0 = 未完成 1 = 完成)',
  `mark_id` int(11) DEFAULT NULL COMMENT '唯一标示，历史发生的记录',
  `par_balance_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '上期结算编号，滚动add',
  `root_diff_id` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '初始差异id',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算结算差异表';

-- ----------------------------
-- Table structure for bill_shop_balance_dtl
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_dtl`;
CREATE TABLE `bill_shop_balance_dtl` (
  `id` char(32) NOT NULL COMMENT '主键ID,uuid生成',
  `balance_no` char(25) NOT NULL COMMENT '结算单据编号',
  `balance_dtl_no` char(18) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '结算明细编号',
  `order_no` char(18) DEFAULT NULL COMMENT '订单号',
  `sku_no` char(32) DEFAULT NULL COMMENT '商品SKU',
  `item_no` char(18) DEFAULT NULL COMMENT '商品内码',
  `size_no` varchar(10) NOT NULL COMMENT '商品尺码',
  `item_code` varchar(18) DEFAULT NULL COMMENT '商品编号',
  `category_no` char(18) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '类别编码',
  `item_name` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `brand_no` char(18) DEFAULT NULL COMMENT '所属品牌',
  `item_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '商品类型,0-正常 1-赠品 促销标识',
  `tag_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品牌价',
  `sale_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品现价',
  `discount_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品折扣价',
  `settle_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品结算价',
  `reduce_cost` decimal(12,2) DEFAULT '0.00' COMMENT '减价,单价减价',
  `qty` int(11) DEFAULT '0' COMMENT '商品数量',
  `amount` decimal(20,2) DEFAULT '0.00' COMMENT '商品总金额-销售收入,(结算价-减价)*数量',
  `pref_cost` decimal(12,2) DEFAULT '0.00' COMMENT '促销优惠单价',
  `pro_no` varchar(36) DEFAULT NULL COMMENT '促销活动编号',
  `pro_name` varchar(50) DEFAULT NULL COMMENT '促销活动名称',
  `discount` decimal(10,4) DEFAULT '0.0000' COMMENT '扣率,如0.17',
  `vip_discount` decimal(10,4) DEFAULT NULL COMMENT '会员折数',
  `base_score` int(11) NOT NULL DEFAULT '0' COMMENT '基本积分',
  `pro_score` int(11) NOT NULL DEFAULT '0' COMMENT '整单分摊积分',
  `affect_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '是否影响销售,0-正常 1-不可销售 默认为0',
  `item_discount` decimal(10,4) DEFAULT NULL COMMENT '商品折数',
  `out_date` date NOT NULL COMMENT '销售日期',
  `pay_code` varchar(20) NOT NULL COMMENT '支付方式代号',
  `pay_name` varchar(50) NOT NULL COMMENT '支付方式名称',
  `card_num` varchar(50) DEFAULT NULL COMMENT '卡号(或券号)',
  `pay_amount` decimal(20,2) DEFAULT '0.00' COMMENT '支付金额',
  `assistant_no` varchar(20) NOT NULL COMMENT '营业员工号,与HR工号代码一致',
  `assistant_name` varchar(10) NOT NULL COMMENT '营业员姓名',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `zone_yyyymm` char(8) DEFAULT NULL COMMENT '大区年月',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商场门店结算明细表';

-- ----------------------------
-- Table structure for bill_shop_balance_list
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_list`;
CREATE TABLE `bill_shop_balance_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `invoicing_party` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '开票方',
  `invoice_type` tinyint(3) unsigned DEFAULT '1' COMMENT '发票类型(0 = 普通发票 1 = 增值票)',
  `company_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '结算公司编码(开票方编码)',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `customer_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '客户编码',
  `customer_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '客户名称',
  `contact_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `tel` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `address` varchar(240) CHARACTER SET utf8 DEFAULT NULL COMMENT '联系地址',
  `pay_unit` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '收票方',
  `tax_registry_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '税务登记号',
  `bank_name` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '银行帐号',
  `invoice_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '发票号',
  `invoice_apply_time` datetime DEFAULT NULL COMMENT '申请开票日期',
  `pay_time` datetime DEFAULT NULL COMMENT '客户交票日期',
  `pre_invoice` tinyint(3) unsigned DEFAULT '1' COMMENT '是否预开票(0 = 是 1 = 否)',
  `mailing_address` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '邮寄地址',
  `delivery_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '快递日期',
  `delivery_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '快递单号',
  `express` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '快递公司',
  `receive_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '接收日期',
  `ask_date` date NOT NULL COMMENT '申请日期',
  `currency` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '币别',
  `amount` decimal(20,2) DEFAULT '0.00' COMMENT '金额',
  `taxpayer_id` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '纳税人识别号',
  `post_pay_date` date NOT NULL COMMENT '交票日期',
  `status` tinyint(3) DEFAULT '0' COMMENT '单据状态',
  `export_count` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '引出次数',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='开票申请单列表';

-- ----------------------------
-- Table structure for bill_shop_balance_pay_sum
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_pay_sum`;
CREATE TABLE `bill_shop_balance_pay_sum` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,uuid生成',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `balance_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '结算单据编号',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '商场名称',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺全称',
  `month` char(15) COLLATE utf8_bin DEFAULT NULL COMMENT '月份',
  `balance_start_date` date DEFAULT NULL COMMENT '结算起始日期',
  `balance_end_date` date DEFAULT NULL COMMENT '结算终止日期',
  `pay_code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '支付方式代号收款方式',
  `pay_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '支付方式名称',
  `payway_num` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式编号,卡号、券号、结算号',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '金额',
  `billing_amount` decimal(20,2) DEFAULT '0.00' COMMENT '开票金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算期按收款方式汇总数据';

-- ----------------------------
-- Table structure for bill_shop_balance_pro_sum
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_pro_sum`;
CREATE TABLE `bill_shop_balance_pro_sum` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,uuid生成',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `balance_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单据编号',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '商场名称',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺全称',
  `month` char(15) COLLATE utf8_bin DEFAULT NULL COMMENT '月份',
  `balance_start_date` date DEFAULT NULL COMMENT '结算起始日期',
  `balance_end_date` date DEFAULT NULL COMMENT '结算终止日期',
  `pro_start_date` date DEFAULT NULL COMMENT '活动起始日',
  `pro_end_date` date DEFAULT NULL COMMENT '活动终止日',
  `order_no` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '业务编号,订单号或退换货号',
  `pro_no` varchar(250) COLLATE utf8_bin DEFAULT NULL COMMENT '促销活动编号',
  `pro_name` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '促销活动名称',
  `sale_amount` decimal(20,2) DEFAULT NULL COMMENT '销售金额收入',
  `deduct_amount` decimal(20,2) DEFAULT '0.00' COMMENT '扣费',
  `billing_amount` decimal(20,2) DEFAULT '0.00' COMMENT '开票金额',
  `cost_deduct_type` tinyint(3) DEFAULT '1' COMMENT '费用扣取方式(1-票前,2-票后)',
  `cost_pay_type` tinyint(3) DEFAULT '1' COMMENT '费用交款方式(1-帐扣,2-现金)',
  `discount_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '扣率名称',
  `discount` decimal(10,4) DEFAULT NULL COMMENT '扣率,如17.00代表17.00%',
  `discount_type` tinyint(1) DEFAULT NULL COMMENT '扣率来源方式,0-取合同扣率 1-促销活动扣率',
  `discount_source_id` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '扣率来源编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算期按活动方式汇总数据';

-- ----------------------------
-- Table structure for bill_shop_balance_set
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_set`;
CREATE TABLE `bill_shop_balance_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺全称',
  `balance_diff_type` tinyint(3) unsigned DEFAULT '1' COMMENT '结算差异方式设置(1-按明细、2-按促销活动、3-按销售)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='门店结算差异生成方式配置表';

-- ----------------------------
-- Table structure for bill_shop_balance_vip
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_vip`;
CREATE TABLE `bill_shop_balance_vip` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '单据编号',
  `balance_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '结算单据编号',
  `vip_sale_amount` decimal(20,2) DEFAULT '0.00' COMMENT '商场VIP销售金额',
  `vip_discount_amount` decimal(20,2) DEFAULT '0.00' COMMENT '商场VIP折扣金额',
  `share_rule` varchar(150) COLLATE utf8_bin DEFAULT NULL COMMENT '分摊规则',
  `base_amount` decimal(20,2) DEFAULT '0.00' COMMENT '计算基数',
  `our_proportion` decimal(10,2) DEFAULT NULL COMMENT '我方承担比例',
  `our_amount` decimal(20,2) DEFAULT '0.00' COMMENT '我方承担金额',
  `mall_amount` decimal(20,2) DEFAULT '0.00' COMMENT '商场返利金额',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算商场VIP分摊表';

-- ----------------------------
-- Table structure for bill_shop_balance_wildsale_sum
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_balance_wildsale_sum`;
CREATE TABLE `bill_shop_balance_wildsale_sum` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `bill_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `balance_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '结算单据编号',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '结算公司名称',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '商场名称',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺全称',
  `out_date` date NOT NULL COMMENT '销售日期',
  `sale_amount` decimal(20,2) DEFAULT NULL COMMENT '外卡销售总金额',
  `settle_amount` decimal(20,2) DEFAULT NULL COMMENT '外卡结算金额',
  `disc_amount` decimal(20,2) DEFAULT NULL COMMENT '外卡VIP折扣金额',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算外卡每日结算汇总数据';

-- ----------------------------
-- Table structure for bill_shop_sale_order
-- ----------------------------
DROP TABLE IF EXISTS `bill_shop_sale_order`;
CREATE TABLE `bill_shop_sale_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '单据编号',
  `order_no` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '订单编号',
  `order_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '销售类型,0-正常 1-换货 2-退货 默认为0',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `biz_city_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营城市编号',
  `bsgroups_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团编码',
  `region_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '片区编码',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `shop_no` char(18) CHARACTER SET utf8 NOT NULL COMMENT '店铺编码',
  `retail_type` varchar(20) COLLATE utf8_bin DEFAULT 'A0' COMMENT '(销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)',
  `sale_date` date NOT NULL COMMENT '销售日期',
  `business_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单业务类型,0-正常销售 1-跨店销售 2-商场团购 3-公司团购 4-员购 9-其他 默认为0',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态,0-已创建 10-已挂起 11-已取消 20-已审核 30-已收银 40-待配货41-已发货 50-已提货 99-已完结',
  `assistant_no` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '营业员工号,与HR工号代码一致',
  `assistant_name` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '营业员姓名',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '类别编码',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `sku_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '商品SKU',
  `item_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品内码',
  `item_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码(出厂时的商品编码)',
  `size_no` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '商品尺码',
  `qty` int(11) DEFAULT '0' COMMENT '商品数量',
  `item_flag` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '商品类型,0-正常 1-赠品 促销标识',
  `tag_price` decimal(20,2) DEFAULT '0.00' COMMENT '商品牌价',
  `sale_price` decimal(20,2) DEFAULT '0.00' COMMENT '商品现价',
  `disc_price` decimal(20,2) DEFAULT '0.00' COMMENT '商品折扣价',
  `settle_price` decimal(20,2) DEFAULT '0.00' COMMENT '商品结算价',
  `reduce_price` decimal(20,2) DEFAULT '0.00' COMMENT '减价,单价减价',
  `amount` decimal(20,2) DEFAULT '0.00' COMMENT '商品总金额,(结算价-减价)*数量',
  `tag_amount` decimal(20,2) DEFAULT '0.00' COMMENT '牌价额,牌价*数量',
  `sale_amount` decimal(20,2) DEFAULT '0.00' COMMENT '现价额,现价*数量',
  `settle_amount` decimal(20,2) DEFAULT '0.00' COMMENT '结算额,结算价*数量',
  `disc_amount` decimal(20,2) DEFAULT '0.00' COMMENT '折扣额,商品折扣价*数量',
  `pref_price` decimal(20,2) DEFAULT '0.00' COMMENT '促销优惠单价,促销优惠单价',
  `pro_no` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '促销活动编号',
  `pro_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '促销活动名称',
  `discount` decimal(10,4) DEFAULT '0.0000' COMMENT '扣率,如0.17',
  `vip_discount` decimal(10,4) DEFAULT NULL COMMENT '会员折数',
  `base_score` int(11) NOT NULL DEFAULT '0' COMMENT '基本积分',
  `pro_score` int(11) NOT NULL DEFAULT '0' COMMENT '整单分摊积分',
  `affect_flag` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否影响销售,0-正常 1-不可销售 默认为0',
  `item_discount` decimal(10,4) DEFAULT NULL COMMENT '商品折数',
  `is_balance` char(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否已结算(1-未结算，2-已结算)',
  `balance_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单号码',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `mall_deduct_amount` decimal(12,2) DEFAULT NULL COMMENT '商场扣额(结算额*扣率)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_saleorder` (`company_no`,`zone_no`,`biz_city_no`,`bsgroups_no`,`region_no`,`mall_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店销售订单数据表';

-- ----------------------------
-- Table structure for bill_split
-- ----------------------------
DROP TABLE IF EXISTS `bill_split`;
CREATE TABLE `bill_split` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '拆分后的单据编码',
  `ref_bill_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '原单据编码',
  `saler_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '卖方编码',
  `buyer_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '买方编码',
  `bill_flag` tinyint(3) NOT NULL COMMENT '单据标志(0：应收， 1：应付)',
  `total_qty` int(11) DEFAULT '0' COMMENT '发货总数量',
  `total_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '发货总金额(不含税)',
  `total_tax_amount` decimal(12,2) DEFAULT '0.00' COMMENT '税收总额',
  `send_out_date` date DEFAULT NULL COMMENT '发货日期',
  `recon_flag` tinyint(2) DEFAULT NULL COMMENT '对账标志',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_bill_no` (`bill_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='拆分的单据汇总表';

-- ----------------------------
-- Table structure for bill_split_dtl
-- ----------------------------
DROP TABLE IF EXISTS `bill_split_dtl`;
CREATE TABLE `bill_split_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '单据编码',
  `ref_bill_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '原单据编码',
  `bill_type` int(11) NOT NULL COMMENT '单据类型,Kinds1 入库单类型:0直接、1合同、2订货、3补货、4返修入库、5退厂',
  `item_no` char(32) COLLATE utf8_bin NOT NULL COMMENT '货品编码',
  `item_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码(出厂时的商品编码)',
  `item_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `brand_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '品牌编码',
  `saler_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '卖方编码',
  `buyer_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '买方编码',
  `store_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算地',
  `order_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `bill_flag` tinyint(3) NOT NULL COMMENT '单据标志(0：应收， 1：应付)',
  `send_out_qty` int(11) DEFAULT '0' COMMENT '发货数量',
  `send_out_date` date NOT NULL COMMENT '发货日期',
  `cost` decimal(12,2) DEFAULT '0.00' COMMENT '含税价格',
  `tax_rate` decimal(4,2) NOT NULL COMMENT '税率',
  `exclusive_cost` decimal(12,2) DEFAULT '0.00' COMMENT '不含税价格',
  `balance_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单号',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '大类编号',
  `supplier_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商名称',
  `supplier_cost` decimal(12,2) DEFAULT NULL COMMENT '厂商价',
  `supplier_group_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '厂商组',
  `path_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算路径编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='拆分的单据明细表';

-- ----------------------------
-- Table structure for bill_split_log
-- ----------------------------
DROP TABLE IF EXISTS `bill_split_log`;
CREATE TABLE `bill_split_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ref_bill_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '原单据编码',
  `bill_type` int(11) NOT NULL COMMENT '单据类型,Kinds1 入库单类型:0直接、1合同、2订货、3补货、4返修入库、5退厂',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `send_out_date` date DEFAULT NULL COMMENT '发货时间',
  `status` tinyint(3) NOT NULL COMMENT '状态标志(0：成功， 1：失败)',
  `split_time` datetime DEFAULT NULL COMMENT '拆单时间',
  `failure_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '失败原因',
  `zone_yyyymm` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '大区年月',
  PRIMARY KEY (`id`),
  KEY `ref_bill_no` (`ref_bill_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='拆单日志表';

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '品牌ID',
  `brand_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '品牌编码',
  `name` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '品牌中文名称',
  `en_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌英文名称',
  `en_short_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌英文简称',
  `opcode` char(2) CHARACTER SET utf8 DEFAULT NULL COMMENT '商品编码特征码(用于鞋类商品编码的首位字母,必须输入且只能输入一位字\n\n符)',
  `category` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '品牌类别(D:代理品牌，S:自有品牌)',
  `belonger` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '品牌归属(X:鞋，S:体)',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '品牌状态(0 = 撤消 1 = 正常)',
  `sys_no` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '00' COMMENT '所属业务单元(关联品牌部编码)',
  `search_code` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '检索码',
  `parent_brand_id` int(11) DEFAULT NULL COMMENT '父品牌',
  `logo_url` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT 'LOGO链接地址',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_brand_no` (`brand_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='品牌信息表';

-- ----------------------------
-- Table structure for brand_unit
-- ----------------------------
DROP TABLE IF EXISTS `brand_unit`;
CREATE TABLE `brand_unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `brand_unit_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '品牌部编号',
  `code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌部代号',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌部名称',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '品牌部状态(0 = 撤消 1 = 正常)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_brand_unit_no` (`brand_unit_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='品牌部表';

-- ----------------------------
-- Table structure for bsgroups
-- ----------------------------
DROP TABLE IF EXISTS `bsgroups`;
CREATE TABLE `bsgroups` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商业集团ID',
  `bsgroups_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '商业集团编码',
  `name` varchar(120) CHARACTER SET utf8 NOT NULL COMMENT '商业集团名称',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bsgroups_no` (`bsgroups_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商业集团信息表(Business groups)';

-- ----------------------------
-- Table structure for cash_transaction_dtl
-- ----------------------------
DROP TABLE IF EXISTS `cash_transaction_dtl`;
CREATE TABLE `cash_transaction_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `card_number` char(20) COLLATE utf8_bin DEFAULT NULL COMMENT '卡号',
  `deposit_cash_time` date DEFAULT NULL COMMENT '存现日期',
  `deposit_amount` decimal(12,2) DEFAULT NULL COMMENT '存现金额',
  `shop_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺编码',
  `shop_name` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺名称',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '商场名称',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='现金交易明细';

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '货品分类ID',
  `category_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '类别编码',
  `code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '类别外码',
  `levelid` int(11) DEFAULT NULL COMMENT '类别级别(第一级为1)',
  `opcode` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '类别特征码(1位,当类别级别为1大类时，必须输入且长度必须1位)',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '类别名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级类别ID',
  `path` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '分类路径',
  `no_path` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '分类编码路径',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '类别状态(0 = 禁用 1 = 正常)',
  `search_code` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '检索码',
  `sys_no` varchar(10) COLLATE utf8_bin DEFAULT '00' COMMENT '所属业务单元',
  `type` tinyint(3) DEFAULT NULL COMMENT '类型(0 普通节点,1 叶子节点)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_cate_no` (`category_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='类别信息表';

-- ----------------------------
-- Table structure for coding_rule
-- ----------------------------
DROP TABLE IF EXISTS `coding_rule`;
CREATE TABLE `coding_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `request_id` varchar(30) NOT NULL COMMENT '请求id，该id为业务系统预先知道的',
  `request_name` varchar(50) DEFAULT NULL COMMENT '请求名称，如仓库引进单',
  `prefix` varchar(10) DEFAULT '' COMMENT '前缀',
  `current_serial_no` int(11) NOT NULL DEFAULT '1' COMMENT '当前序号 ',
  `serial_no_length` int(11) NOT NULL DEFAULT '6' COMMENT '序号长度',
  `reset_mode` int(11) NOT NULL DEFAULT '1' COMMENT '重置模式(0:永不重置 1:按天重置 2:按月重置 3:按年重置)',
  `allow_break_no` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否允许断号，1表示允许，0表示不允许，允许时会在应用程序中缓存10个\n\n序号，不允许时，每次都从数据库请求，允许断号\r\n\r\n性能更好，但如果服务器重启，可能缓存中没用过的序号丢失',
  `is_abstract` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否抽象。1表抽象，抽象的不能建立实例化，只能作为模板供其他复制',
  `reset_time` datetime DEFAULT NULL COMMENT '重置时间，在判断是否需要重置时有用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_coding_rule` (`request_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='单据号配置表';

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '结算公司ID',
  `company_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '结算公司编码',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态(0 = 撤消 1 = 正常)',
  `bank_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '银行帐号',
  `bank_account_name` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '银行账户名',
  `contact_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `tel` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `tax_registry_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '税务登记号',
  `tax_level` varchar(20) COLLATE utf8_bin DEFAULT '0' COMMENT '纳税级别(0:一般纳税人 1:小规模纳税人)',
  `legal_person` varchar(80) COLLATE utf8_bin DEFAULT NULL COMMENT '法人代表',
  `identity_card` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '营业证号/身份证号',
  `fax` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '传真号',
  `email` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮箱',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  `search_code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '检索码',
  `address` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_company_no` (`company_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算公司信息表';

-- ----------------------------
-- Table structure for company_currency
-- ----------------------------
DROP TABLE IF EXISTS `company_currency`;
CREATE TABLE `company_currency` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(10) NOT NULL COMMENT '币别代码',
  `name` varchar(30) NOT NULL COMMENT '币别名称',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币别';

-- ----------------------------
-- Table structure for company_currency_rel
-- ----------------------------
DROP TABLE IF EXISTS `company_currency_rel`;
CREATE TABLE `company_currency_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_no` char(18) NOT NULL COMMENT '公司编码',
  `company_name` varchar(100) NOT NULL COMMENT '公司名称',
  `currency_id` int(11) NOT NULL COMMENT '币别主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司和币别的关联';

-- ----------------------------
-- Table structure for company_currency_set
-- ----------------------------
DROP TABLE IF EXISTS `company_currency_set`;
CREATE TABLE `company_currency_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_no` char(18) NOT NULL COMMENT '公司编码',
  `base_currency` varchar(40) NOT NULL COMMENT '本位币',
  `foreign_currency` varchar(40) NOT NULL COMMENT '外币',
  `exchange_rate` decimal(10,7) DEFAULT NULL COMMENT '汇率',
  `effective_date` date DEFAULT NULL COMMENT '生效日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='外币设置';

-- ----------------------------
-- Table structure for company_foreign_currency
-- ----------------------------
DROP TABLE IF EXISTS `company_foreign_currency`;
CREATE TABLE `company_foreign_currency` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `base_currency` varchar(40) NOT NULL COMMENT '本位币',
  `foreign_currency` varchar(40) NOT NULL COMMENT '外币',
  `exchange_rate` decimal(10,7) DEFAULT NULL COMMENT '汇率',
  `effective_date` date DEFAULT NULL COMMENT '生效日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='外币汇率设置';

-- ----------------------------
-- Table structure for company_settle_period
-- ----------------------------
DROP TABLE IF EXISTS `company_settle_period`;
CREATE TABLE `company_settle_period` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '结账公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `supplier_settle_time` date NOT NULL COMMENT '厂商结账日期',
  `account_settle_time` date NOT NULL COMMENT '财务结账日期',
  `sale_settle_time` date NOT NULL COMMENT '销售结账日期',
  `transfer_settle_time` date NOT NULL COMMENT '跨区结账日期',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公司结账期间';

-- ----------------------------
-- Table structure for cost_category_setting
-- ----------------------------
DROP TABLE IF EXISTS `cost_category_setting`;
CREATE TABLE `cost_category_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cost_cate_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `code` char(18) COLLATE utf8_bin NOT NULL COMMENT '费用类别编码',
  `name` varchar(60) COLLATE utf8_bin NOT NULL DEFAULT 'null' COMMENT '费用类别名称',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态(0 = 停用 1 = 启用)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='总账费用类别设置';

-- ----------------------------
-- Table structure for credit_card_transaction_dtl
-- ----------------------------
DROP TABLE IF EXISTS `credit_card_transaction_dtl`;
CREATE TABLE `credit_card_transaction_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq_no` char(20) COLLATE utf8_bin DEFAULT NULL COMMENT '流水号',
  `terminal_number` char(20) COLLATE utf8_bin DEFAULT NULL COMMENT '终端号',
  `card_number` char(20) COLLATE utf8_bin DEFAULT NULL COMMENT '卡号',
  `deal_time` datetime DEFAULT NULL COMMENT '交易时间',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '金额',
  `actual_income_amount` decimal(12,2) DEFAULT NULL COMMENT '实收金额',
  `givenBank` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '发卡行',
  `reality_deal_time` datetime DEFAULT NULL COMMENT '实际交易时间',
  `rebate_amount` decimal(12,2) DEFAULT NULL COMMENT '回扣费',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_seq_no` (`seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='银联刷卡交易明细';

-- ----------------------------
-- Table structure for currency_management
-- ----------------------------
DROP TABLE IF EXISTS `currency_management`;
CREATE TABLE `currency_management` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `currency_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '币种编码',
  `currency_name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '币种名称',
  `currency_symbol` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '币种标识',
  `standard_money` tinyint(3) DEFAULT '0' COMMENT '是否本位币：0、否 1、是',
  `status` tinyint(3) DEFAULT '1' COMMENT '启用标志 1、已启用 0、已停用',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_currency_code` (`currency_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='币种管理';

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '顾客ID',
  `customer_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '客户编码',
  `code` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT '检索码',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '客户简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '客户全称',
  `en_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '客户英文名称',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '客户状态(0 = 撤消 1 = 正常)',
  `category` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '客户性质(1:加盟 2:代销 3:批发)',
  `type` varchar(20) COLLATE utf8_bin DEFAULT '1' COMMENT '客户类型(1:个人 2:公司)',
  `level` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '客户等级(A B C D四种)',
  `creditlines` decimal(12,2) DEFAULT '0.00' COMMENT '信用额度',
  `discount` decimal(5,2) NOT NULL COMMENT '批发折扣(1-100之间,可以为小数,用于计算时除以100)',
  `bank_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '银行帐号',
  `bank_account_name` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '银行账户名',
  `contact_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `tel` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `tax_registry_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '税务登记号',
  `tax_level` varchar(20) COLLATE utf8_bin DEFAULT '0' COMMENT '纳税级别(0:一般纳税人 1:小规模纳税人)',
  `legal_person` varchar(80) COLLATE utf8_bin DEFAULT NULL COMMENT '法人代表',
  `identity_card` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '营业证号/身份证号',
  `fax` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '传真号',
  `email` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮箱',
  `province_no` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '行政省编码',
  `city_no` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '行政市编码',
  `county_no` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '行政县编码',
  `address` varchar(240) CHARACTER SET utf8 DEFAULT NULL COMMENT '联系地址',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_customer_no` (`customer_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='加盟批发客户信息表';

-- ----------------------------
-- Table structure for customer_rece_rel
-- ----------------------------
DROP TABLE IF EXISTS `customer_rece_rel`;
CREATE TABLE `customer_rece_rel` (
  `id` char(36) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `term_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '条款编码',
  `term_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '条款名称',
  `customer_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '客户编码',
  `customer_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '客户名称',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算主体编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算主体名称',
  `margin_control_flag` tinyint(3) unsigned DEFAULT NULL COMMENT '启用保证金控制(0 : 未启用， 1 ： 已启用)',
  `margin_amount` decimal(20,2) DEFAULT '0.00' COMMENT '保证金额度',
  `margin_remainder_amount` decimal(20,2) DEFAULT '0.00' COMMENT '保证金余额',
  `status` tinyint(3) unsigned DEFAULT NULL COMMENT '状态(0 ： 停用 1 ： 启用)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='客户-收款条款关联表';

-- ----------------------------
-- Table structure for customer_rece_rel_dtl
-- ----------------------------
DROP TABLE IF EXISTS `customer_rece_rel_dtl`;
CREATE TABLE `customer_rece_rel_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ref_id` char(36) COLLATE utf8_bin NOT NULL COMMENT '主表关联建',
  `year` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '年份',
  `rebate_amount` decimal(20,2) DEFAULT NULL COMMENT '返利额度',
  `has_rebate_amount` decimal(20,2) DEFAULT NULL COMMENT '已返利金额',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='客户-收款条款关联详情';

-- ----------------------------
-- Table structure for deposit_cash
-- ----------------------------
DROP TABLE IF EXISTS `deposit_cash`;
CREATE TABLE `deposit_cash` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,uuid生成',
  `bill_no` char(32) COLLATE utf8_bin NOT NULL COMMENT '单据编号',
  `shop_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '门店编号',
  `shop_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '门店名称',
  `assistant_id` char(36) COLLATE utf8_bin NOT NULL COMMENT '营业员主键ID',
  `assistant_no` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '营业员工号,与HR工号代码一致',
  `assistant_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '营业员姓名',
  `start_out_date` date NOT NULL COMMENT '起始销售日期',
  `end_out_date` date NOT NULL COMMENT '结束销售日期',
  `currency_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '币种,0-人民币',
  `account` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '存入账户',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '存入金额',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态,0-有效 1-无效 默认为0',
  `confirm_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否确定,0-未确定 1-已确定 默认为0',
  `deposit_date` date NOT NULL COMMENT '存入日期',
  `sale_amount` decimal(20,2) DEFAULT NULL COMMENT '销售金额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人编号',
  `create_user` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `audit_time` datetime DEFAULT NULL COMMENT '确定时间',
  `auditor_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '确定人编号',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '确定人姓名',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='现金存入';

-- ----------------------------
-- Table structure for exception_price_bill
-- ----------------------------
DROP TABLE IF EXISTS `exception_price_bill`;
CREATE TABLE `exception_price_bill` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '流水号',
  `bill_no` char(25) COLLATE utf8_bin NOT NULL COMMENT '单据编号',
  `bill_type` int(11) NOT NULL COMMENT '单据类型',
  `order_unit_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '订货单位',
  `bill_date` date NOT NULL COMMENT '业务发生日期(审核日期、销售日期)',
  `item_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '商品编码',
  `item_code` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码',
  `item_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `sku_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'sku码',
  `size_no` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '尺码',
  `qty` int(11) DEFAULT NULL COMMENT '数量',
  `cost` decimal(12,2) DEFAULT NULL COMMENT '单据价格',
  `cost_from` tinyint(3) NOT NULL COMMENT '取价来源（1: 采购价，2: 地区价等）',
  `base_cost` decimal(12,2) DEFAULT NULL COMMENT '实际价格',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态 1:价格已更新 2:价格未更新',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_sku_bill_no_type` (`sku_no`,`bill_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for exchange_rate_setup
-- ----------------------------
DROP TABLE IF EXISTS `exchange_rate_setup`;
CREATE TABLE `exchange_rate_setup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `exchange_rate_code` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '汇率编码',
  `source_currency` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '源货币',
  `target_currency` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '目标货币',
  `conversion_factor` decimal(12,2) NOT NULL COMMENT '转换系数',
  `effective_date` date DEFAULT NULL COMMENT '生效日期',
  `company_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '公司编码',
  `status` tinyint(3) DEFAULT '1' COMMENT '启用标志 1、已启用 0、已停用',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_exchange_rate_code` (`exchange_rate_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='汇率设置';

-- ----------------------------
-- Table structure for expect_cash
-- ----------------------------
DROP TABLE IF EXISTS `expect_cash`;
CREATE TABLE `expect_cash` (
  `id` varchar(36) NOT NULL COMMENT '主键ID,uuid生成',
  `bill_no` char(25) NOT NULL COMMENT '单据编号',
  `settle_code` varchar(20) NOT NULL COMMENT '结算编码',
  `shop_no` varchar(18) NOT NULL COMMENT '门店编号',
  `shop_name` varchar(50) DEFAULT NULL COMMENT '门店名称',
  `assistant_id` varchar(40) NOT NULL COMMENT '营业员主键ID',
  `assistant_no` varchar(20) DEFAULT NULL COMMENT '营业员工号,与HR工号代码一致',
  `assistant_name` varchar(10) DEFAULT NULL COMMENT '营业员姓名',
  `business_date` date NOT NULL COMMENT '日期',
  `currency_type` smallint(1) NOT NULL DEFAULT '0' COMMENT '币种,0-人民币',
  `business_type` smallint(1) NOT NULL DEFAULT '0' COMMENT '业务类型,1-商场预收 2-定金预收',
  `business_flag` smallint(1) NOT NULL DEFAULT '0' COMMENT '业务标识,1-收款 2-退款',
  `used_amount` decimal(20,2) DEFAULT NULL,
  `amount` decimal(20,2) NOT NULL COMMENT '金额',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `contact_name` varchar(32) DEFAULT NULL COMMENT '联系人',
  `tel` varchar(20) DEFAULT NULL COMMENT '联系方式',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` smallint(1) NOT NULL DEFAULT '0' COMMENT '状态,0-有效 1-无效 默认为0',
  `confirm_flag` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否确定,0-未确定 1-已确定 默认为0',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_no` varchar(20) DEFAULT NULL COMMENT '创建人编号',
  `create_user` varchar(32) NOT NULL COMMENT '创建人姓名',
  `audit_time` datetime DEFAULT NULL COMMENT '确定时间',
  `auditor_no` varchar(20) DEFAULT NULL COMMENT '确定人编号',
  `auditor` varchar(32) DEFAULT NULL COMMENT '确定人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '最后人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预收款单';

-- ----------------------------
-- Table structure for financial_account
-- ----------------------------
DROP TABLE IF EXISTS `financial_account`;
CREATE TABLE `financial_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_no` char(18) NOT NULL COMMENT '公司编号',
  `group_lead_role` tinyint(3) DEFAULT NULL COMMENT '承担总部职能  1、是 0、否',
  `nc_id` char(32) DEFAULT NULL COMMENT 'NC账套编号',
  `parent_company` char(18) DEFAULT NULL COMMENT '父项公司编号',
  `child_company` tinyint(3) DEFAULT NULL COMMENT '是否属于分公司 1、是 0、否',
  `supplier_no` char(18) DEFAULT NULL COMMENT '相关供应商',
  `revenue_journal_type` char(18) DEFAULT NULL COMMENT '结转收入凭证类别',
  `cost_journal_type` char(18) DEFAULT NULL COMMENT '结转成本凭证类别',
  `liabilities_journal_type` char(18) DEFAULT NULL COMMENT '存贷负债凭证类别',
  `internal_journal_type` char(18) DEFAULT NULL COMMENT '结转对内收入凭证类别',
  `assist_project_01` char(18) DEFAULT NULL COMMENT '辅助核算类别1',
  `assist_project_02` char(18) DEFAULT NULL COMMENT '辅助核算类别2',
  `assist_project_03` char(18) DEFAULT NULL COMMENT '辅助核算类别3',
  `assist_project_04` char(18) DEFAULT NULL COMMENT '辅助核算类别4',
  `assist_project_05` char(18) DEFAULT NULL COMMENT '辅助核算类别5',
  `assist_project_06` char(18) DEFAULT NULL COMMENT '辅助核算类别6',
  `assist_project_07` char(18) DEFAULT NULL COMMENT '辅助核算类别7',
  `assist_project_08` char(18) DEFAULT NULL COMMENT '辅助核算类别8',
  `status` tinyint(3) DEFAULT '1' COMMENT '启用标志 1、已启用 0、已停用',
  `create_user` varchar(32) DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='财务帐套';

-- ----------------------------
-- Table structure for financial_category
-- ----------------------------
DROP TABLE IF EXISTS `financial_category`;
CREATE TABLE `financial_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `financial_category_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '财务大类编码',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '分类名称',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `is_default` tinyint(3) DEFAULT '0' COMMENT '是否是系统默认值(0：否，1：是)',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态（0 = 撤消 1 = 正常）',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_category_no` (`financial_category_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='财务大类设置';

-- ----------------------------
-- Table structure for financial_category_dtl
-- ----------------------------
DROP TABLE IF EXISTS `financial_category_dtl`;
CREATE TABLE `financial_category_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `financial_category_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '财务大类编码',
  `category_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '大类编码',
  `qty_control_flag` tinyint(3) DEFAULT '0' COMMENT '是否启用数量控制（0：否，1：是）',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_category_no` (`financial_category_no`,`category_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='财务大类明细';

-- ----------------------------
-- Table structure for group_sale_apply_invoice_rel
-- ----------------------------
DROP TABLE IF EXISTS `group_sale_apply_invoice_rel`;
CREATE TABLE `group_sale_apply_invoice_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `invoice_apply_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '开票申请单据号',
  `invoice_apply_date` datetime DEFAULT NULL COMMENT '开票申请日期',
  `invoice_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发票单据号',
  `invoice_date` datetime DEFAULT NULL COMMENT '发票登记日期',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='团购批发开票发票关联表';

-- ----------------------------
-- Table structure for headquarter_cost_maintain
-- ----------------------------
DROP TABLE IF EXISTS `headquarter_cost_maintain`;
CREATE TABLE `headquarter_cost_maintain` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `item_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '商品编码',
  `item_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品出厂编码',
  `item_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌',
  `brand_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `supplier_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商名称',
  `effective_time` date NOT NULL COMMENT '生效日期',
  `add_rule_no` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '加价规则编号',
  `headquarter_cost` decimal(12,2) DEFAULT '0.00' COMMENT '总部成本',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_item_no` (`item_no`,`effective_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='总部成本维护';

-- ----------------------------
-- Table structure for headquarter_price_rule
-- ----------------------------
DROP TABLE IF EXISTS `headquarter_price_rule`;
CREATE TABLE `headquarter_price_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `add_rule_no` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '规则编号',
  `supplier_group_no` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '多选结算供应商组代码',
  `supplier_group_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '多选供应商组名称',
  `brand_unit_no` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '品牌部列表,字符分割形式BL,TT,FD',
  `brand_unit_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌部名称列表',
  `category_no` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '多选结算大类',
  `category_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '多选结算大类名称',
  `new_style_flag` tinyint(3) unsigned DEFAULT NULL COMMENT '是否新旧款，0否，1是',
  `style_type` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '新旧款类型',
  `year_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '年份',
  `year` char(4) COLLATE utf8_bin DEFAULT NULL COMMENT '年份',
  `season` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '季节，A春，B夏，C秋，D冬',
  `season_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '季节名称',
  `add_basis` char(3) COLLATE utf8_bin NOT NULL COMMENT '加价依据,1厂进价，2牌价',
  `effective_time` date NOT NULL COMMENT '生效日期',
  `add_price` decimal(10,2) DEFAULT NULL COMMENT '加价',
  `add_discount` decimal(10,2) DEFAULT NULL COMMENT '加折',
  `discount_rate` decimal(10,2) DEFAULT NULL COMMENT '折扣',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `add_rule_no` (`add_rule_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='总部加价规则';

-- ----------------------------
-- Table structure for initial_amount
-- ----------------------------
DROP TABLE IF EXISTS `initial_amount`;
CREATE TABLE `initial_amount` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算主体编号',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算主体名称',
  `supplier_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商名称',
  `customer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '内部客户编号',
  `customer_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '内部客户名称',
  `initial_date` date DEFAULT NULL COMMENT '初始日期',
  `item_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编号',
  `item_code` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码',
  `item_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `currency` char(32) COLLATE utf8_bin DEFAULT '0' COMMENT '币别',
  `qty` int(11) DEFAULT '0' COMMENT '数量',
  `cost` decimal(12,2) DEFAULT '0.00' COMMENT '单价',
  `amount` decimal(12,2) DEFAULT '0.00' COMMENT '合计金额',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `balance_type` tinyint(3) DEFAULT NULL COMMENT '结算类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='初始余额(应收/应付)';

-- ----------------------------
-- Table structure for inside_purchase_balance_date
-- ----------------------------
DROP TABLE IF EXISTS `inside_purchase_balance_date`;
CREATE TABLE `inside_purchase_balance_date` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算主体编号',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算主体名称',
  `balance_month` char(15) COLLATE utf8_bin DEFAULT NULL COMMENT '结算月份',
  `balance_start_date` date DEFAULT NULL COMMENT '结算起始日期',
  `balance_end_date` date DEFAULT NULL COMMENT '结算终止日期',
  `invoice_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '否生成开票申请(0-未生成，1-已生成)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='内购结算期设置';

-- ----------------------------
-- Table structure for invoice_info
-- ----------------------------
DROP TABLE IF EXISTS `invoice_info`;
CREATE TABLE `invoice_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '公司名称',
  `client_type` tinyint(3) DEFAULT NULL COMMENT '客户类型 1、公司 2、客户 3、商场 4、商业集团 5、供应商',
  `client_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '客户编码',
  `client_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '客户名称',
  `invoice_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '开票名称',
  `invoice_type` tinyint(3) DEFAULT NULL COMMENT '发票类型 1、普通发票 2、增值票',
  `nc_client_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT 'nc客户编码',
  `nc_client_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'NC客户档案',
  `taxpayer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '纳税人识别号',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `telephone_number` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `bank_name` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '开户行',
  `account_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `post_address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '收票邮寄地址',
  `contact_person` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '收票联系人',
  `contact_number` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '收票联系电话',
  `status` tinyint(3) DEFAULT '0' COMMENT '状态 1、已启用 0、已停用',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='开票信息表';

-- ----------------------------
-- Table structure for invoice_rule_set
-- ----------------------------
DROP TABLE IF EXISTS `invoice_rule_set`;
CREATE TABLE `invoice_rule_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `invoice_rule_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '申请编号',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `bsgroups_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团编码',
  `bsgroups_name` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团名称',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '商场名称',
  `invoice_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '开票名称',
  `tax_registry_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '税务登记号',
  `buyer_address` varchar(240) CHARACTER SET utf8 DEFAULT NULL COMMENT '客户-地址',
  `buyer_tel` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '客户-电话',
  `bank_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '银行帐号',
  `bank_account_name` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '银行账户名',
  `mailing_address` varchar(240) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮寄地址',
  `contact_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `tel` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人电话',
  `billing_method` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '开票方式',
  `shop_group_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺分组',
  `invoice_temp_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发票模板',
  `status` tinyint(3) DEFAULT NULL COMMENT '1启用、0停用状态',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='开票申请规则设置表';

-- ----------------------------
-- Table structure for invoice_template_set
-- ----------------------------
DROP TABLE IF EXISTS `invoice_template_set`;
CREATE TABLE `invoice_template_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `invoice_temp_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发票模版编号',
  `code` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '模板编码',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '模板名称',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `bsgroups_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团编码',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='发票模板设置主表';

-- ----------------------------
-- Table structure for invoice_template_set_dtl
-- ----------------------------
DROP TABLE IF EXISTS `invoice_template_set_dtl`;
CREATE TABLE `invoice_template_set_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `invoicetemp_dtl_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '模版编号',
  `invoice_temp_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发票模版编号',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '类别编码产品大类',
  `category_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '类别名称',
  `invoice_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '开票名称',
  `qty_control_flag` tinyint(3) DEFAULT NULL COMMENT '是否启用数量控制（0：否，1：是）',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='发票模板设置明细表';

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_no` char(18) CHARACTER SET utf8 NOT NULL COMMENT '商品系统唯一编码(系统编码,对用户不可见)',
  `code` varchar(18) CHARACTER SET utf8 NOT NULL COMMENT '商品编码(出厂时的商品编码)',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品全称(默认与商品名称一致)',
  `en_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品英文名',
  `sys_no` varchar(10) COLLATE utf8_bin DEFAULT '00' COMMENT '所属业务单元',
  `style_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品款号(必须输入且长度必须6位)',
  `brand_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '品牌编码',
  `shoe_model` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '款型(下拉框选择,值: 1.5, 2, 2.5, 无)',
  `ingredients` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '主料(下拉框选择,值: 1:皮 2:布 3:绒 4:PU 5:木 6:弹力 7:\n\n网 8:其它 9:不涉及)',
  `mainqdb` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '面料成份',
  `lining` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '内里(D:单里 M:毛里 R:绒里 F:仿毛里)',
  `main_color` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '主色',
  `color_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '颜色编码',
  `category_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '类别编码',
  `root_category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品大类编码',
  `repeatlisting` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '重复上市(下拉框选择,值: 是, 否)',
  `gender` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '性别(下拉框选择,值: 男, 女, 童, 无性别)',
  `heeltype` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '跟型(下拉框选择,值: 高, 中, 低, 平, 不涉及)',
  `bottomtype` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '底型(下拉框选择,值: 成型底, 片底, 成型片, 不涉及)',
  `size_kind` char(2) COLLATE utf8_bin NOT NULL COMMENT '尺寸分类',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态(0 = 禁用 1 = 正常 2 = 临时)',
  `tag_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '牌价',
  `suggest_tag_price` decimal(10,2) NOT NULL COMMENT '建议牌价',
  `purchase_tax_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '含税采购价',
  `costtaxrate` decimal(4,2) DEFAULT NULL COMMENT '进项税率',
  `saletaxrate` decimal(4,2) DEFAULT NULL COMMENT '销项税率',
  `material_price` decimal(8,2) NOT NULL COMMENT '物料价',
  `supplier_no` char(18) CHARACTER SET utf8 NOT NULL COMMENT '供应商编码',
  `supplier_item_no` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '供应商货号',
  `supplier_item_name` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商商品名称',
  `orderfrom` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '订货形式(下拉框选择,值: 1:自产 2:外购 3:地区自购)',
  `years` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '年份(指上市的年份,下拉框选择,值: 2006~2026,默认当年)',
  `sell_season` varchar(20) CHARACTER SET utf8 NOT NULL DEFAULT 'A' COMMENT '季节(下拉框选择,A:春 B:夏 C:秋 D:冬)',
  `purchase_season` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '采购季节',
  `sale_date` date DEFAULT NULL COMMENT '建议上柜日',
  `search_code` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '检索码',
  `style` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '风格',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_item_no` (`item_no`) USING BTREE,
  UNIQUE KEY `uk_index_brand_category_item` (`brand_no`,`category_no`,`item_no`) USING BTREE,
  UNIQUE KEY `uk_index_category_brand_item` (`category_no`,`brand_no`,`item_no`) USING BTREE,
  UNIQUE KEY `uk_code` (`code`,`brand_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品资料表';

-- ----------------------------
-- Table structure for item_cost
-- ----------------------------
DROP TABLE IF EXISTS `item_cost`;
CREATE TABLE `item_cost` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `company_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '公司编号',
  `item_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '货品编号',
  `item_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码',
  `item_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `store_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '仓库编号(预留)',
  `location` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '库位(预留)',
  `lot` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '批次(预留)',
  `cost_method` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '成本方法',
  `unit_cost` decimal(12,2) DEFAULT '0.00' COMMENT '单位成本',
  `unit` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '计量单位',
  `year` char(4) COLLATE utf8_bin NOT NULL COMMENT '年份',
  `month` char(2) COLLATE utf8_bin NOT NULL COMMENT '月份',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `company_no` (`company_no`,`item_no`,`year`,`month`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='成本主表';

-- ----------------------------
-- Table structure for lookup
-- ----------------------------
DROP TABLE IF EXISTS `lookup`;
CREATE TABLE `lookup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `code` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '字典编码',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '字典名称',
  `type` tinyint(3) DEFAULT '0' COMMENT '字典类型(1 系统;0 普通)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lookup_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='码表主表';

-- ----------------------------
-- Table structure for lookup_entry
-- ----------------------------
DROP TABLE IF EXISTS `lookup_entry`;
CREATE TABLE `lookup_entry` (
  `lookup_entry_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '字典项编号',
  `lookup_id` int(11) NOT NULL COMMENT '字典ID',
  `code` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '字典编码',
  `opcode` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '特征码',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '字典项名称',
  `order_no` int(8) DEFAULT NULL COMMENT '序号',
  `type` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '字典类型(1 系统;0 普通)',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态(1正常,0无效)',
  `default_flag` char(1) CHARACTER SET utf8 DEFAULT NULL COMMENT '是否选中',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`lookup_entry_no`),
  UNIQUE KEY `uk_lookup` (`lookup_id`,`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='数据字典项';

-- ----------------------------
-- Table structure for lookup_rel
-- ----------------------------
DROP TABLE IF EXISTS `lookup_rel`;
CREATE TABLE `lookup_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关系ID',
  `lookup_id` int(11) NOT NULL COMMENT '字典编号',
  `lookup_entry_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '字典项编号',
  `sub_lookup_id` int(8) DEFAULT NULL COMMENT '子字典编号',
  `sub_lp_entry_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '子字典项编号',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lookup_entry` (`sub_lookup_id`,`lookup_id`,`sub_lp_entry_no`,`lookup_entry_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='数据字典关系表';

-- ----------------------------
-- Table structure for mall
-- ----------------------------
DROP TABLE IF EXISTS `mall`;
CREATE TABLE `mall` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商场ID',
  `mall_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '商场编码',
  `name` varchar(120) CHARACTER SET utf8 NOT NULL COMMENT '商场名称',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `bsgroups_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团编码',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  `organ_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营城市编码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mall_no` (`mall_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场信息表';

-- ----------------------------
-- Table structure for mall_balance_diff_type
-- ----------------------------
DROP TABLE IF EXISTS `mall_balance_diff_type`;
CREATE TABLE `mall_balance_diff_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `diff_type_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司名称',
  `code` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '差异类型编码',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '差异类型名称',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态(0 = 停用 1 = 启用)',
  `system_init` tinyint(3) DEFAULT '1' COMMENT '是否系统初始参数设置(0 = 系统初始化 1 = 创建)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场结算差异类型设置表';

-- ----------------------------
-- Table structure for mall_balance_setting
-- ----------------------------
DROP TABLE IF EXISTS `mall_balance_setting`;
CREATE TABLE `mall_balance_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `biz_city_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营城市编号',
  `bsgroups_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团编码',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `shop_no` char(18) CHARACTER SET utf8 NOT NULL COMMENT '店铺编码',
  `mall_type` tinyint(3) DEFAULT '1' COMMENT '商场类型(下拉选择 1:百货商场 2:超市商场 3:购物中心 4:街铺 5:运动城 6:其它)',
  `bill_unit` varchar(150) COLLATE utf8_bin DEFAULT NULL COMMENT '开票单位',
  `month` char(4) COLLATE utf8_bin DEFAULT NULL COMMENT '月份',
  `balance_start_date` date DEFAULT NULL COMMENT '结算起始日期',
  `balance_end_date` date DEFAULT NULL COMMENT '结算终止日期',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算期设置';

-- ----------------------------
-- Table structure for mall_deduction_set
-- ----------------------------
DROP TABLE IF EXISTS `mall_deduction_set`;
CREATE TABLE `mall_deduction_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `deduction_no` char(25) COLLATE utf8_bin DEFAULT NULL COMMENT '单据编号',
  `code` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '扣费编码',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '扣费名称',
  `cost_code` char(18) COLLATE utf8_bin NOT NULL COMMENT '总账费用类别编码',
  `cost_name` varchar(60) COLLATE utf8_bin NOT NULL DEFAULT 'null' COMMENT '总账费用类别名称',
  `type` tinyint(3) DEFAULT NULL COMMENT '扣费类型',
  `system_init` tinyint(3) DEFAULT '1' COMMENT '是否系统初始参数设置(0 = 系统初始化 1 = 创建)',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态(0 = 停用 1 = 启用)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场扣费名目设置';

-- ----------------------------
-- Table structure for member_order_dtl
-- ----------------------------
DROP TABLE IF EXISTS `member_order_dtl`;
CREATE TABLE `member_order_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '订单编号',
  `shop_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '店铺编号',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态',
  `invoice_amount` decimal(12,2) DEFAULT NULL COMMENT '发票金额',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `out_start_date` date DEFAULT NULL COMMENT '销售日期',
  `out_end_date` date DEFAULT NULL COMMENT '销售日期',
  `company_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '公司编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='明细';

-- ----------------------------
-- Table structure for operate_log
-- ----------------------------
DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE `operate_log` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID',
  `data_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '数据编码',
  `module_no` tinyint(3) NOT NULL COMMENT '模块编码(枚举类表示)',
  `operate_no` tinyint(3) DEFAULT NULL COMMENT '操作编码(枚举表示:新增、修改、删除...)',
  `status` tinyint(3) DEFAULT NULL COMMENT '单据状态',
  `status_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '单据状态名称',
  `operate_status` tinyint(3) DEFAULT NULL COMMENT '操作状态(1、通过 0、打回)',
  `operate_status_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '操作状态名称',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='操作日志表';

-- ----------------------------
-- Table structure for order_assistant
-- ----------------------------
DROP TABLE IF EXISTS `order_assistant`;
CREATE TABLE `order_assistant` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,uuid生成',
  `order_no` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '订单编号',
  `order_dtl_id` char(32) COLLATE utf8_bin NOT NULL COMMENT '订单明细主键ID',
  `assistant_id` char(32) COLLATE utf8_bin NOT NULL COMMENT '营业员主键ID',
  `assistant_no` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '营业员工号',
  `assistant_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '营业员姓名',
  `share_amount` decimal(20,2) DEFAULT NULL COMMENT '分摊金额,按营业员分摊金额',
  `counts` tinyint(1) NOT NULL DEFAULT '1' COMMENT '营业员人数,单条营业员人数',
  `order_type` tinyint(1) DEFAULT NULL COMMENT '订单类型，0-销售，1-换货，2-退货',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='销售订单明细-营业员';

-- ----------------------------
-- Table structure for order_dtl
-- ----------------------------
DROP TABLE IF EXISTS `order_dtl`;
CREATE TABLE `order_dtl` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,uuid生成',
  `order_no` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '订单编号',
  `sku_no` char(32) COLLATE utf8_bin NOT NULL COMMENT '商品SKU',
  `item_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '商品内码',
  `size_no` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '商品尺码',
  `item_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编号',
  `item_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `barcode` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '商品条码',
  `brand_no` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '所属品牌',
  `item_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '商品类型,0-正常 1-赠品 促销标识',
  `tag_price` decimal(20,2) DEFAULT NULL COMMENT '商品牌价',
  `sale_price` decimal(20,2) DEFAULT NULL COMMENT '商品现价',
  `disc_price` decimal(20,2) DEFAULT NULL COMMENT '商品折扣价',
  `settle_price` decimal(20,2) DEFAULT NULL COMMENT '商品结算价',
  `reduce_price` decimal(20,2) DEFAULT NULL COMMENT '减价,单价减价',
  `rebate_amount` decimal(20,2) DEFAULT NULL COMMENT '外卡折让金额',
  `qty` int(11) DEFAULT NULL COMMENT '商品数量',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '商品总金额,(结算价-减价)*数量',
  `pref_price` decimal(20,2) DEFAULT NULL COMMENT '促销优惠单价,促销优惠单价',
  `pro_no` varchar(130) COLLATE utf8_bin DEFAULT NULL COMMENT '促销活动编号，多个以逗号隔开',
  `pro_name` varchar(310) COLLATE utf8_bin DEFAULT NULL COMMENT '促销活动名称，多个以逗号隔开',
  `discount` decimal(10,4) DEFAULT NULL COMMENT '最终扣率,如17.00代表17.00%',
  `discount_code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '扣率代码，如A,B',
  `discount_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '扣率名称',
  `discount_type` tinyint(1) DEFAULT NULL COMMENT '扣率类型 ( 1-合同正价扣 2-合同阶梯扣 3-促销扣率)',
  `discount_type_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '最终扣率来源名称',
  `discount_source_id` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '最终扣率来源编号',
  `billing_code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '商场结算码',
  `balance_base` tinyint(1) DEFAULT '0' COMMENT '结算基数,0-销售金额 1-牌价金额',
  `vip_discount` decimal(10,4) DEFAULT NULL COMMENT '会员折数',
  `base_score` int(11) NOT NULL DEFAULT '0' COMMENT '基本积分',
  `pro_score` int(11) NOT NULL DEFAULT '0' COMMENT '整单分摊积分',
  `cost_score` int(11) DEFAULT '0' COMMENT '消费积分',
  `item_discount` decimal(10,4) DEFAULT NULL COMMENT '商品折数,如25.00代表25.00%',
  `color_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '颜色编码',
  `color_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '颜色名称',
  `brand_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `reduce_price_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '减价原因,当减价不为空，减价原因必填',
  `size_kind` char(2) COLLATE utf8_bin DEFAULT NULL COMMENT '尺码种类',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '类别编码',
  `bill_transfer_no` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '店转货单号,只有跨店销售（本店自提）才有数据',
  `price_change_bill_no` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '变价单单号',
  `return_exchange_num` smallint(4) DEFAULT '0' COMMENT '退换货数量，该条明细退换货数量',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='销售订单明细';

-- ----------------------------
-- Table structure for order_main
-- ----------------------------
DROP TABLE IF EXISTS `order_main`;
CREATE TABLE `order_main` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,主键',
  `order_no` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '订单编号',
  `shop_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '销售门店编号',
  `shop_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '销售门店名称',
  `shop_no_from` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方—店铺编号',
  `shop_name_from` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '发货方-店铺名称',
  `serial_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '流水号',
  `out_date` date NOT NULL COMMENT '销售日期',
  `qty` int(11) DEFAULT NULL COMMENT '总商品数量',
  `tag_price_amount` decimal(20,2) DEFAULT NULL COMMENT '本单牌价总金额',
  `sale_price_amount` decimal(20,2) DEFAULT NULL COMMENT '本单现价总金额',
  `disc_price_amount` decimal(20,2) DEFAULT NULL COMMENT '本单折后价总金额',
  `settle_amount` decimal(20,2) DEFAULT NULL COMMENT '本单结算总金额',
  `reduce_amount` decimal(20,2) DEFAULT NULL COMMENT '本单减价总金额,(单商品减价*数量)之和',
  `total_rebate_amount` decimal(20,2) DEFAULT NULL COMMENT '外卡总折让金额 ',
  `pref_amount` decimal(20,2) DEFAULT NULL COMMENT '促销优惠总金额',
  `coupon_amount` decimal(20,2) DEFAULT NULL COMMENT '优惠券总金额',
  `all_amount` decimal(20,2) DEFAULT NULL COMMENT '订单应收总金额',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '订单实收总金额,订单应收总金额+优惠券总金额+找零金额',
  `remain_amount` decimal(20,2) DEFAULT NULL COMMENT '订单找零金额',
  `assistant_name` varchar(330) COLLATE utf8_bin DEFAULT NULL COMMENT '营业员姓名,营业员姓名，多个营业员使用逗号隔开',
  `assistant_id` varchar(330) COLLATE utf8_bin DEFAULT NULL COMMENT '营业员主键ID',
  `assistant_no` varchar(210) COLLATE utf8_bin DEFAULT NULL COMMENT '营业员工号',
  `vip_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '会员卡号',
  `vip_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '会员姓名',
  `wildcard_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '外卡编号',
  `wildcard_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '外卡名称',
  `wildcard_discount` decimal(10,4) DEFAULT NULL COMMENT '外卡折数',
  `base_score` int(11) NOT NULL DEFAULT '0' COMMENT '基本总积分',
  `pro_score` int(11) NOT NULL DEFAULT '0' COMMENT '整单分摊总积分',
  `cost_score` int(11) DEFAULT '0' COMMENT '消费总积分',
  `order_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否换货的订单,0-正常 1-换货 默认为0 1-为换货时产生的新订单',
  `business_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单业务类型,0-正常销售 1-跨店销售 2-商场团购 3-公司团购 4-员购 5-专柜团购 默认为0',
  `order_source` tinyint(1) DEFAULT '0' COMMENT '订单来源,0-pos,1-移动pos',
  `daily_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '日结标识,0-未日结 1-已日结 默认为0',
  `daily_datetime` datetime DEFAULT NULL COMMENT '日结时间',
  `monthly_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '月结账标识,0-未月结 1-已月结 默认为0',
  `monthly_datetime` datetime DEFAULT NULL COMMENT '月结账时间',
  `balance_no` char(20) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单据编号',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '订单状态,0-已创建 10-已挂起 11-已取消 20-已审核 30-已收银未发货 41-已收银已发货 99-已完结',
  `actual_postage` decimal(20,2) DEFAULT NULL COMMENT '实收运费',
  `message_code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '短信验证码',
  `express_company` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '快递公司',
  `bill_transfer_no` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '店转货单号,只有跨店销售（本店自提）才有数据',
  `express_no` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '快递单号',
  `take_mode` tinyint(1) DEFAULT NULL COMMENT '提货方式,0-本店自提 1-快递 2邻店自提',
  `market_ticket_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '商场小票号',
  `lock_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '锁单标识,0-未锁单 1-已锁单',
  `return_exchange_time` tinyint(3) DEFAULT '0' COMMENT '退换货次数',
  `shop_terminal_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '门店收银终端号',
  `invoice_apply_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '开票申请单号,类型为团购才有值',
  `invoice_apply_date` datetime DEFAULT NULL COMMENT '开票申请日期,类型为团购才有值',
  `invoice_no` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '发票号',
  `invoice_amount` decimal(20,2) DEFAULT NULL COMMENT '发票金额',
  `invoice_date` date DEFAULT NULL COMMENT '开票日期',
  `pickup_date` date DEFAULT NULL COMMENT '提货日期',
  `check_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '提货经办人编号',
  `check_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '提货经办人名称',
  `create_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人编号',
  `create_user` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `create_time` datetime NOT NULL COMMENT '创建时间,订单创建',
  `update_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人编号',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `adjust_flag` tinyint(1) DEFAULT '0' COMMENT '是否改单，0-未改单，1-日结前改单， 2-日结后改单',
  `adjust_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '改单人',
  `adjust_time` datetime DEFAULT NULL COMMENT '改单日期',
  PRIMARY KEY (`id`),
  KEY `idx_shop_no` (`shop_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='销售订单';

-- ----------------------------
-- Table structure for order_payway
-- ----------------------------
DROP TABLE IF EXISTS `order_payway`;
CREATE TABLE `order_payway` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,uuid生成',
  `order_no` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '订单编号',
  `pay_code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '支付方式代号',
  `pay_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '支付方式名称',
  `payway_num` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式编号,卡号、券号、结算号',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '金额',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `payway_flag` tinyint(1) DEFAULT NULL COMMENT '支付类型,1-虚拟支付',
  `payway_ticket_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '支付小票流水号',
  `payway_datetime` datetime NOT NULL COMMENT '支付时间',
  `out_date` date DEFAULT NULL COMMENT '销售日期',
  `order_type` tinyint(1) DEFAULT NULL COMMENT '订单类型，0-销售，1-换货，2-退货',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='销售订单-支付记录';

-- ----------------------------
-- Table structure for order_promotion_dtl
-- ----------------------------
DROP TABLE IF EXISTS `order_promotion_dtl`;
CREATE TABLE `order_promotion_dtl` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,uuid生成',
  `seq_id` int(11) DEFAULT NULL COMMENT '顺序号',
  `order_no` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '业务编号,订单号或退换货号',
  `order_dtl_id` char(32) COLLATE utf8_bin NOT NULL COMMENT '业务明细ID',
  `pro_no` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '促销活动编号',
  `pro_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '促销活动名称',
  `discount` decimal(10,4) DEFAULT NULL COMMENT '扣率,如17.00代表17.00%',
  `discount_type` tinyint(1) DEFAULT NULL COMMENT '扣率类型 ( 1-合同正价扣 2-合同阶梯扣 3-促销扣率)',
  `discount_source_id` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '扣率来源编号',
  `order_type` tinyint(1) DEFAULT NULL COMMENT '订单类型，0-销售，1-换货，2-退货',
  `create_user_no` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '创建人编号',
  `create_user` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='销售订单-促销活动明细';

-- ----------------------------
-- Table structure for order_sale_wildcard_record
-- ----------------------------
DROP TABLE IF EXISTS `order_sale_wildcard_record`;
CREATE TABLE `order_sale_wildcard_record` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,主键',
  `wildcard_no` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '外卡编码',
  `wildcard_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '外卡名称',
  `create_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人编号',
  `create_user` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人编号',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_wildcard_no_record` (`wildcard_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='外卡记录';

-- ----------------------------
-- Table structure for order_unit
-- ----------------------------
DROP TABLE IF EXISTS `order_unit`;
CREATE TABLE `order_unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订货单位ID',
  `order_unit_no` char(18) CHARACTER SET utf8 NOT NULL COMMENT '订货单位编号',
  `order_unit_code` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT '订货单位编码',
  `organ_no` char(18) CHARACTER SET utf8 NOT NULL COMMENT '管理城市编号',
  `name` varchar(240) CHARACTER SET utf8 NOT NULL COMMENT '订货单位名称',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `status` tinyint(3) DEFAULT '1' COMMENT '1有效，0无效',
  `create_user` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  `search_code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '检索码',
  `type` tinyint(3) DEFAULT NULL COMMENT '订货单位类型(0 品牌部;1 批发客户)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_unit_no` (`order_unit_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订货单位表';

-- ----------------------------
-- Table structure for organ
-- ----------------------------
DROP TABLE IF EXISTS `organ`;
CREATE TABLE `organ` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组织ID',
  `organ_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '组织编号',
  `organ_code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '组织编码',
  `name` varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '组织名称',
  `parent_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '上级组织编码',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '1有效，0无效',
  `organ_level` int(11) DEFAULT NULL COMMENT '组织级别,1 管理城市,2 经营城市',
  `order_no` int(11) DEFAULT '0' COMMENT '序号',
  `path` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '组织路径',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_organ` (`organ_no`,`parent_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='组织关系表';

-- ----------------------------
-- Table structure for other_deduction
-- ----------------------------
DROP TABLE IF EXISTS `other_deduction`;
CREATE TABLE `other_deduction` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `buyer_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '买方编号',
  `buyer_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '买方名称',
  `saler_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方编号',
  `saler_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '卖方名称',
  `balance_no` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单号',
  `balance_type` tinyint(3) DEFAULT NULL COMMENT '结算单类型',
  `balance_status` tinyint(3) DEFAULT NULL COMMENT '结算单状态',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编号',
  `brand_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '产品大类编号',
  `category_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '产品大类名称',
  `currency_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '币别',
  `currency_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '币别名称',
  `deduction_category` tinyint(3) DEFAULT NULL COMMENT '扣项分类',
  `deduction_qty` int(11) DEFAULT NULL COMMENT '扣项数量',
  `deduction_date` date DEFAULT NULL COMMENT '扣项日期',
  `return_amount` decimal(12,2) DEFAULT '0.00' COMMENT '残鞋金额',
  `fine_amount` decimal(12,2) DEFAULT '0.00' COMMENT '罚款金额',
  `deduction_amount` decimal(12,2) DEFAULT '0.00' COMMENT '扣项金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` char(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='其他扣项明细';

-- ----------------------------
-- Table structure for payment_term
-- ----------------------------
DROP TABLE IF EXISTS `payment_term`;
CREATE TABLE `payment_term` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `payment_no` char(18) NOT NULL COMMENT '付款条款编码',
  `expressed` varchar(100) DEFAULT NULL COMMENT '付款条款描述',
  `credit_day` int(11) DEFAULT NULL COMMENT '信用天数',
  `discount` decimal(10,4) DEFAULT '0.0000' COMMENT '折率、扣率',
  `status` tinyint(3) DEFAULT '0' COMMENT '启用标志 1、启用 0、未启用',
  `create_user` varchar(32) DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_payment_no` (`payment_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='付款条款';

-- ----------------------------
-- Table structure for rate_basic
-- ----------------------------
DROP TABLE IF EXISTS `rate_basic`;
CREATE TABLE `rate_basic` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,UUID',
  `concract_no` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '合同号',
  `zone_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '大区编码',
  `zone_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '大区名称',
  `organ_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '城市编码',
  `organ_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '城市名称',
  `mall_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商场名称',
  `shop_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺编码',
  `shop_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺名称',
  `brand_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `rate_type` tinyint(4) DEFAULT NULL COMMENT '扣率类型,1-合同正价扣 2-合同阶梯扣',
  `rate_code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '扣率代码,A B...',
  `rate_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '扣率名称',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `rate` decimal(8,4) DEFAULT NULL COMMENT '扣率',
  `start_disc` decimal(12,2) DEFAULT NULL COMMENT '起始折>',
  `end_disc` decimal(12,2) DEFAULT NULL COMMENT '终止折<=',
  `settlement_date` date DEFAULT NULL COMMENT '最新结算日',
  `use_flag` tinyint(4) DEFAULT NULL COMMENT '是否使用,0-已使用 1-未使用',
  `billing_code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '商场结算码',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态,0-编辑,100-完结',
  `create_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_rate_basic` (`shop_no`,`start_date`,`end_date`),
  KEY `idx_rate_basic_concract_no` (`concract_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='基础扣率设置';

-- ----------------------------
-- Table structure for rate_expense_operate
-- ----------------------------
DROP TABLE IF EXISTS `rate_expense_operate`;
CREATE TABLE `rate_expense_operate` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,UUID',
  `concract_no` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '合同号',
  `zone_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '大区编码',
  `zone_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '大区名称',
  `organ_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '城市编码',
  `organ_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '城市名称',
  `mall_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商场名称',
  `shop_no` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '店铺编码',
  `shop_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺名称',
  `brand_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `settlement_period` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '结算月',
  `settlement_type` tinyint(4) DEFAULT '1' COMMENT '结算类型,1-阶段保底+扣率 2-纯租金 3-最大值(租金、扣率) 4-和值(租金+扣率)',
  `debited_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '扣费类别',
  `debited_mode` tinyint(4) DEFAULT NULL COMMENT '费用扣取方式 1-票前 2-票后',
  `payment_mode` tinyint(4) DEFAULT NULL COMMENT '费用交款方式 1-账款 2-现价',
  `rental` decimal(20,2) DEFAULT NULL COMMENT '租金',
  `rate` decimal(8,4) DEFAULT NULL COMMENT '扣率',
  `start_amount` decimal(20,2) DEFAULT NULL COMMENT '起始额度',
  `end_amount` decimal(20,2) DEFAULT NULL COMMENT '终止额度',
  `unity_rate_flag` tinyint(4) DEFAULT '1' COMMENT '超额统一扣率 0-统一 1-不统一',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态,0-未结算，1-已结算',
  `create_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_rate_expense_operate_shop_no` (`shop_no`),
  KEY `idx_rate_expense_operate_concract_no` (`concract_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算期扣费设置';

-- ----------------------------
-- Table structure for rate_expense_remind
-- ----------------------------
DROP TABLE IF EXISTS `rate_expense_remind`;
CREATE TABLE `rate_expense_remind` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,UUID',
  `concract_no` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '合同号',
  `zone_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '大区编码',
  `zone_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '大区名称',
  `organ_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '城市编码',
  `organ_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '城市名称',
  `mall_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商场名称',
  `shop_no` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '店铺编码',
  `shop_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺名称',
  `brand_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `start_month` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '起始结算月',
  `end_month` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '终止结算月',
  `total_guarantee_amount` decimal(20,2) DEFAULT NULL COMMENT '总保底计划',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态,0-未结算，1-已结算',
  `create_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_rate_expense_remind_shop_no` (`shop_no`),
  KEY `idx_rate_expense_remind_concract_no` (`concract_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算提醒';

-- ----------------------------
-- Table structure for rate_expense_sporadic
-- ----------------------------
DROP TABLE IF EXISTS `rate_expense_sporadic`;
CREATE TABLE `rate_expense_sporadic` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,UUID',
  `concract_no` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '合同号',
  `zone_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '大区编码',
  `zone_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '大区名称',
  `organ_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '城市编码',
  `organ_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '城市名称',
  `mall_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商场名称',
  `shop_no` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '店铺编码',
  `shop_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺名称',
  `brand_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `clause_type` tinyint(4) DEFAULT NULL COMMENT '条款类型',
  `start_date` date NOT NULL COMMENT '生效起始日期',
  `end_date` date NOT NULL COMMENT '生效终止日期',
  `debited_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '扣费类别 从FAS取',
  `debited_mode` tinyint(4) DEFAULT NULL COMMENT '费用扣取方式 1-票前 2-票后',
  `payment_mode` tinyint(4) DEFAULT NULL COMMENT '费用交款方式 1-账款 2-现价',
  `debited_rule` tinyint(4) DEFAULT NULL COMMENT '扣费规则 1-月定额 2-费率',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '金额',
  `base_pay_code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式',
  `base_other` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '其他',
  `rate` decimal(8,4) DEFAULT NULL COMMENT '费率',
  `balance_rate` decimal(8,4) DEFAULT NULL COMMENT '补差费率',
  `settlement_date` date DEFAULT NULL COMMENT '最新结算日',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态,0-未结算，1-已结算',
  `create_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_rate_expense_sporadic` (`shop_no`,`start_date`,`end_date`),
  KEY `idx_rate_expense_sporadic_concract_no` (`concract_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='其他零星费用';

-- ----------------------------
-- Table structure for rate_pro
-- ----------------------------
DROP TABLE IF EXISTS `rate_pro`;
CREATE TABLE `rate_pro` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,UUID',
  `activity_no` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '商场(品牌)活动单号',
  `activity_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '活动名称',
  `activity_describe` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '活动描述',
  `launch_type` tinyint(4) DEFAULT '2' COMMENT '活动来源,1-公司活动 2-商场活动',
  `activity_type` tinyint(4) DEFAULT NULL COMMENT '活动类型,1-买换 2-打折 3-其他',
  `activity_bill_type` tinyint(4) DEFAULT '1' COMMENT '单据类型,1-扣率设置单 2-扣率补录单',
  `zone_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '地区编码',
  `zone_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '地区名称',
  `mall_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商场名称',
  `shop_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺编码',
  `shop_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺名称',
  `brand_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `brand_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `rate` decimal(8,4) DEFAULT NULL COMMENT '扣率',
  `rate_code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '扣率代码,A B...',
  `rate_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '扣率名称',
  `basic_rate_flag` tinyint(4) DEFAULT '0' COMMENT '取合同扣标志,0-取合同扣 1-不取合同扣',
  `debited_mode` tinyint(4) DEFAULT NULL COMMENT '费用扣取方式 1-票前 2-票后',
  `payment_mode` tinyint(4) DEFAULT NULL COMMENT '费用交款方式 1-账款 2-现价',
  `settlement_date` date DEFAULT NULL COMMENT '当前结算日',
  `billing_code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '结算码',
  `virtual_flag` tinyint(4) DEFAULT NULL COMMENT '虚实,0-虚数 1-实数',
  `properties` tinyint(4) DEFAULT NULL COMMENT '属性,1-满送 2-满减 3-折扣 4-其他',
  `full_value` decimal(8,2) DEFAULT NULL COMMENT '满值',
  `deduction_value` decimal(8,2) DEFAULT NULL COMMENT '送减额',
  `discount` decimal(8,2) DEFAULT NULL COMMENT '折扣',
  `strength` decimal(8,2) DEFAULT NULL COMMENT '力度',
  `pro_no` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '促销单',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态,0-编辑,10-确认 100-已创建',
  `create_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `audit_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '确认人',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '确认人姓名',
  `audit_time` datetime DEFAULT NULL COMMENT '确认时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_rate_pro` (`shop_no`,`start_date`,`end_date`),
  KEY `idx_rate_pro_activity_no` (`activity_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='活动扣率设置';

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '片区ID',
  `region_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '片区编码',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '片区名称',
  `organ_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '组织编码(大区)',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态(0 = 撤消 1 = 正常)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  `search_code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '检索码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_region_no` (`region_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='片区信息表';

-- ----------------------------
-- Table structure for region_cost_maintain
-- ----------------------------
DROP TABLE IF EXISTS `region_cost_maintain`;
CREATE TABLE `region_cost_maintain` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `zone_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '经营区域编号',
  `zone_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域名称',
  `item_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '商品编码',
  `item_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品出厂编码',
  `item_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌',
  `brand_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `supplier_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商名称',
  `effective_time` date NOT NULL COMMENT '生效日期',
  `add_rule_no` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '加价规则编号',
  `region_cost` decimal(12,2) DEFAULT '0.00' COMMENT '地区成本',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `zone_yyyymm` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '大区年月',
  PRIMARY KEY (`id`),
  UNIQUE KEY `zone_no` (`zone_no`,`item_no`,`effective_time`),
  UNIQUE KEY `UK_ZONE_ITEM_EFF` (`item_no`,`zone_no`,`effective_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='地区成本维护';

-- ----------------------------
-- Table structure for region_price_rule
-- ----------------------------
DROP TABLE IF EXISTS `region_price_rule`;
CREATE TABLE `region_price_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `add_rule_no` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '规则编码',
  `zone_no` char(50) COLLATE utf8_bin NOT NULL COMMENT '经营区域编号',
  `zone_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '区域名称',
  `supplier_group_no` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '多选结算供应商组代码',
  `supplier_group_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '多选结算供应商组名称',
  `brand_unit_no` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '品牌部列表，逗号分割的字符列表',
  `brand_unit_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌部名称列表',
  `category_no` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '多选结算大类',
  `category_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '多选结算大类名称',
  `new_style_flag` tinyint(3) unsigned DEFAULT NULL COMMENT '是否新旧款，0否，1是',
  `style_type` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '新旧款类型',
  `year_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '年份',
  `year` char(4) COLLATE utf8_bin DEFAULT NULL COMMENT '年份',
  `season` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '季节，A春，B夏，C秋，D冬',
  `season_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '季节名称',
  `effective_time` date NOT NULL COMMENT '生效日期',
  `add_basis` char(3) COLLATE utf8_bin NOT NULL COMMENT '加价依据,2牌价，3总部成本',
  `add_price` decimal(10,2) DEFAULT NULL COMMENT '加价',
  `add_discount` decimal(10,2) DEFAULT NULL COMMENT '加折',
  `discount_rate` decimal(10,2) DEFAULT NULL COMMENT '折扣率',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `add_rule_no` (`add_rule_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='地区加价规则';

-- ----------------------------
-- Table structure for register_invoice
-- ----------------------------
DROP TABLE IF EXISTS `register_invoice`;
CREATE TABLE `register_invoice` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,uuid生成',
  `bill_no` char(32) COLLATE utf8_bin NOT NULL COMMENT '单据编号',
  `shop_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '门店编号',
  `shop_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '门店名称',
  `assistant_id` char(36) COLLATE utf8_bin NOT NULL COMMENT '营业员主键ID',
  `assistant_no` char(20) COLLATE utf8_bin NOT NULL COMMENT '营业员工号,与HR工号代码一致',
  `assistant_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '营业员姓名',
  `out_date` date NOT NULL COMMENT '销售日期',
  `register_date` date NOT NULL COMMENT '开票登记日期',
  `should_amount` decimal(20,2) DEFAULT NULL COMMENT '应开票金额',
  `actual_amount` decimal(20,2) DEFAULT NULL COMMENT '实际开票金额',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态,0-有效 1-无效 默认为0',
  `confirm_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否确定,0-未确定 1-已确定 默认为0',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人编号',
  `create_user` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `audit_time` datetime DEFAULT NULL COMMENT '确定时间',
  `auditor_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '确定人编号',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '确定人姓名',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='发票登记';

-- ----------------------------
-- Table structure for return_exchange_dtl
-- ----------------------------
DROP TABLE IF EXISTS `return_exchange_dtl`;
CREATE TABLE `return_exchange_dtl` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,uuid生成',
  `business_no` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '订单编号',
  `sku_no` char(32) COLLATE utf8_bin NOT NULL COMMENT '商品SKU',
  `item_no` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '商品内码',
  `size_no` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '商品尺码',
  `item_code` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编号',
  `item_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `barcode` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '商品条码',
  `brand_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '所属品牌',
  `item_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '商品类型,0-正常 1-赠品 促销标识',
  `tag_price` decimal(20,2) DEFAULT NULL COMMENT '商品牌价',
  `sale_price` decimal(20,2) DEFAULT NULL COMMENT '商品现价',
  `disc_price` decimal(20,2) DEFAULT NULL COMMENT '商品折扣价',
  `settle_price` decimal(20,2) DEFAULT NULL COMMENT '商品结算价',
  `reduce_price` decimal(20,2) DEFAULT NULL COMMENT '减价,单价减价',
  `rebate_amount` decimal(20,2) DEFAULT NULL COMMENT '外卡折让金额',
  `qty` int(11) DEFAULT NULL COMMENT '商品数量',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '商品总金额,(结算价-减价)*数量',
  `pref_price` decimal(20,2) DEFAULT NULL COMMENT '促销优惠单价,促销优惠单价',
  `pro_no` varchar(130) COLLATE utf8_bin DEFAULT NULL COMMENT '促销活动编号，多个以逗号隔开',
  `pro_name` varchar(310) COLLATE utf8_bin DEFAULT NULL COMMENT '促销活动名称，多个以逗号隔开',
  `discount` decimal(10,4) DEFAULT NULL COMMENT '最终扣率,如17.00代表17.00%',
  `discount_code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '扣率代码，如A,B',
  `discount_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '扣率名称',
  `discount_type` tinyint(1) DEFAULT NULL COMMENT '扣率类型 ( 1-合同正价扣 2-合同阶梯扣 3-促销扣率)',
  `discount_type_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '最终扣率来源名称',
  `discount_source_id` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '最终扣率来源编号',
  `billing_code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '商场结算码',
  `balance_base` tinyint(1) DEFAULT '0' COMMENT '结算基数,0-销售金额 1-牌价金额',
  `vip_discount` decimal(10,4) DEFAULT NULL COMMENT '会员折数',
  `base_score` int(11) NOT NULL DEFAULT '0' COMMENT '基本积分',
  `pro_score` int(11) NOT NULL DEFAULT '0' COMMENT '整单分摊积分',
  `cost_score` int(11) DEFAULT '0' COMMENT '消费积分',
  `available_flag` tinyint(1) DEFAULT '0' COMMENT '可二次销售,0-可二次销售 1-不可二次销售',
  `item_discount` decimal(10,4) DEFAULT NULL COMMENT '商品折数',
  `reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '退换货原因',
  `order_dtl_id` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '原订单明细ID',
  `color_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '颜色编码',
  `color_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '颜色名称',
  `brand_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌名称',
  `size_kind` char(2) COLLATE utf8_bin DEFAULT NULL COMMENT '尺码种类',
  `category_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '类别编码',
  `return_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已经退仓,0-未退 1-已退 默认为0-未退',
  `bill_transfer_no` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '店转货单号,只有跨店销售（本店自提）才有数据',
  `price_change_bill_no` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '变价单单号',
  `return_exchange_num` smallint(4) DEFAULT '0' COMMENT '退换货数量，该条明细退换货数量(只有换货订单才有)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '最后修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='退换货单明细';

-- ----------------------------
-- Table structure for return_exchange_main
-- ----------------------------
DROP TABLE IF EXISTS `return_exchange_main`;
CREATE TABLE `return_exchange_main` (
  `id` char(32) COLLATE utf8_bin NOT NULL COMMENT '主键ID,主键',
  `business_no` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '退换货单号,业务编号',
  `shop_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '门店编号',
  `shop_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '门店名称',
  `out_date` date NOT NULL COMMENT '退换货日期',
  `old_out_date` date DEFAULT NULL COMMENT '原单号日期',
  `qty` int(11) DEFAULT NULL COMMENT '总商品数量',
  `tag_price_amount` decimal(20,2) DEFAULT NULL COMMENT '本单牌价总金额',
  `sale_price_amount` decimal(20,2) DEFAULT NULL COMMENT '本单现价总金额',
  `disc_price_amount` decimal(20,2) DEFAULT NULL COMMENT '本单折后价总金额',
  `settle_amount` decimal(20,2) DEFAULT NULL COMMENT '本单结算总金额',
  `reduce_amount` decimal(20,2) DEFAULT NULL COMMENT '本单减价总金额,(单商品减价*数量)之和',
  `total_rebate_amount` decimal(20,2) DEFAULT NULL COMMENT '外卡总折让金额 ',
  `pref_amount` decimal(20,2) DEFAULT NULL COMMENT '促销优惠总金额',
  `coupon_amount` decimal(20,2) DEFAULT NULL COMMENT '优惠券总金额',
  `all_amount` decimal(20,2) DEFAULT NULL COMMENT '应收总金额',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '实收总金额,订单应收总金额+优惠券总金额+找零金额',
  `remain_amount` decimal(20,2) DEFAULT NULL COMMENT '订单找零金额',
  `assistant_name` varchar(330) COLLATE utf8_bin DEFAULT NULL COMMENT '营业员姓名,营业员姓名，多个营业员使用逗号隔开',
  `assistant_id` varchar(330) COLLATE utf8_bin DEFAULT NULL COMMENT '营业员主键ID',
  `assistant_no` varchar(210) COLLATE utf8_bin DEFAULT NULL COMMENT '营业员工号',
  `vip_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '会员卡号',
  `vip_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '会员姓名',
  `wildcard_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '外卡编号',
  `wildcard_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '外卡名称',
  `wildcard_discount` decimal(10,4) DEFAULT NULL COMMENT '外卡折数',
  `base_score` int(11) NOT NULL DEFAULT '0' COMMENT '基本总积分',
  `pro_score` int(11) NOT NULL DEFAULT '0' COMMENT '整单分摊总积分',
  `cost_score` int(11) DEFAULT '0' COMMENT '消费总积分',
  `business_mode` tinyint(1) NOT NULL DEFAULT '2' COMMENT '业务类别,1-换货 2-退货 默认为2',
  `business_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '业务类型,取原订单的业务类型',
  `order_source` tinyint(1) DEFAULT '0' COMMENT '订单来源,0-pos,1-移动pos',
  `daily_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '日结标识,0-未日结 1-已日结 默认为0',
  `daily_datetime` datetime DEFAULT NULL COMMENT '日结时间',
  `balance_no` char(20) COLLATE utf8_bin DEFAULT NULL COMMENT '结算单据编号',
  `monthly_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '月结账标识,0-未月结 1-已月结 默认为0',
  `monthly_datetime` datetime DEFAULT NULL COMMENT '月结账时间',
  `old_order_no` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '原订单号',
  `new_order_no` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '新订单号(换货),业务类型是换货时，由换货产生的新订单单号',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '退换货状态,0-已创建 10-已挂起 11-已取消 20-已审核 30-已收银 40-待配货41-已发货 50-已提货 99-已完结',
  `actual_postage` decimal(20,2) DEFAULT NULL COMMENT '实收运费',
  `express_no` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '快递单号',
  `take_mode` tinyint(1) DEFAULT NULL COMMENT '提货方式,0-自提 1-快递',
  `market_ticket_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '商场小票号',
  `lock_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '锁单标识,0-未锁单 1-已锁单',
  `return_exchange_time` tinyint(3) DEFAULT '0' COMMENT '退换货次数,只有换货订单才有',
  `shop_terminal_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '门店收银终端号',
  `invoice_no` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '发票号',
  `invoice_amount` decimal(20,2) DEFAULT NULL COMMENT '发票金额',
  `invoice_date` date DEFAULT NULL COMMENT '开票日期',
  `create_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人编号',
  `create_user` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '创建人姓名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人编号',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `adjust_flag` tinyint(1) DEFAULT '0' COMMENT '是否改单，0-未改单，1-日结前改单， 2-日结后改单',
  `adjust_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '改单人',
  `adjust_time` datetime DEFAULT NULL COMMENT '改单日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='退换货单';

-- ----------------------------
-- Table structure for rule_match_fail
-- ----------------------------
DROP TABLE IF EXISTS `rule_match_fail`;
CREATE TABLE `rule_match_fail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `item_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品唯一编码',
  `item_code` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商品编码',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '地区编码',
  `status` tinyint(3) unsigned DEFAULT '0' COMMENT '0-失败,1-成功',
  `match_type` tinyint(3) unsigned DEFAULT NULL COMMENT '0-总部匹配，1-地区匹配',
  `fail_reason` tinyint(3) DEFAULT NULL COMMENT '1-厂商组未匹配，其他参考枚举对象',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `zone_yyyymm` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '大区年月',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='加价规则匹配失败表';

-- ----------------------------
-- Table structure for sale_settlement_deduction
-- ----------------------------
DROP TABLE IF EXISTS `sale_settlement_deduction`;
CREATE TABLE `sale_settlement_deduction` (
  `id` varchar(40) NOT NULL COMMENT '主键ID,UUID',
  `zone_no` varchar(20) DEFAULT NULL COMMENT '大区编码',
  `zone_name` varchar(32) DEFAULT NULL COMMENT '大区名称',
  `organ_no` varchar(20) DEFAULT NULL COMMENT '城市编码',
  `organ_name` varchar(64) DEFAULT NULL COMMENT '城市名称',
  `shop_no` varchar(20) NOT NULL COMMENT '店铺编码',
  `shop_name` varchar(64) DEFAULT NULL COMMENT '店铺名称',
  `brand_no` varchar(20) NOT NULL COMMENT '品牌编码',
  `brand_name` varchar(64) DEFAULT NULL COMMENT '品牌名称',
  `settlement_period` varchar(10) NOT NULL COMMENT '结算期',
  `start_date` date NOT NULL COMMENT '结算起始日期',
  `end_date` date NOT NULL COMMENT '结算终止日期',
  `rate_type` smallint(6) DEFAULT '1' COMMENT '扣率类型,1-合同扣点，2-保底+扣率，3-租金，4-最大值(租金、扣率)，5-租金+扣率',
  `rental` decimal(20,2) DEFAULT NULL COMMENT '租金',
  `rate` decimal(8,4) DEFAULT NULL COMMENT '扣率',
  `base_amount` decimal(20,2) DEFAULT NULL COMMENT '保底额',
  `base_rate` decimal(8,4) DEFAULT NULL COMMENT '保底扣率',
  `exceed_base_rate` decimal(8,4) DEFAULT NULL COMMENT '超保底扣率',
  `status` smallint(6) DEFAULT '0' COMMENT '状态,0-未结算，1-已结算',
  `creator_no` varchar(20) DEFAULT NULL COMMENT '建档人',
  `creator` varchar(32) DEFAULT NULL COMMENT '建档人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `editor_no` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `editor` varchar(50) DEFAULT NULL COMMENT '最后修改人姓名',
  `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算期扣费设置';

-- ----------------------------
-- Table structure for sales_channel
-- ----------------------------
DROP TABLE IF EXISTS `sales_channel`;
CREATE TABLE `sales_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '销售渠道ID',
  `channel_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '销售渠道编码',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '销售渠道名称',
  `parent_id` int(8) NOT NULL COMMENT '上级渠道ID',
  `channel_path` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '渠道路径',
  `order_no` int(11) DEFAULT '0' COMMENT '序号',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '是否有效,1有效,0无效',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_channel_no` (`channel_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='销售渠道表';

-- ----------------------------
-- Table structure for self_shop_bank_info
-- ----------------------------
DROP TABLE IF EXISTS `self_shop_bank_info`;
CREATE TABLE `self_shop_bank_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `company_name` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '公司名称',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '商场名称',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺全称',
  `shop_account` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺账号',
  `deposit_account` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '存现账号',
  `terminal_number` char(20) COLLATE utf8_bin DEFAULT NULL COMMENT '终端号',
  `credit_card_bank` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '刷卡行',
  `credit_card_account` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '刷卡账号',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='独立店铺账户管理表';

-- ----------------------------
-- Table structure for settle_brand_group
-- ----------------------------
DROP TABLE IF EXISTS `settle_brand_group`;
CREATE TABLE `settle_brand_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '品牌组编码',
  `name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '品牌组名称',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态（0 = 撤消 1 = 正常）',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_group_no` (`group_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='品牌组';

-- ----------------------------
-- Table structure for settle_brand_group_rel
-- ----------------------------
DROP TABLE IF EXISTS `settle_brand_group_rel`;
CREATE TABLE `settle_brand_group_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '品牌组编码',
  `brand_unit_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌部编码',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_group_id` (`group_no`,`brand_unit_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='品牌组和品牌的关联表';

-- ----------------------------
-- Table structure for settle_category
-- ----------------------------
DROP TABLE IF EXISTS `settle_category`;
CREATE TABLE `settle_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `settle_category_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '分类编码,财务系统手动输入',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '分类名称',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态（0 = 撤消 1 = 正常）',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_category_no` (`settle_category_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算大类';

-- ----------------------------
-- Table structure for settle_category_dtl
-- ----------------------------
DROP TABLE IF EXISTS `settle_category_dtl`;
CREATE TABLE `settle_category_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `settle_category_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '分类编码',
  `category_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '大类编码',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_category_no` (`settle_category_no`,`category_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算大类明细';

-- ----------------------------
-- Table structure for settle_method
-- ----------------------------
DROP TABLE IF EXISTS `settle_method`;
CREATE TABLE `settle_method` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `settle_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算方式编码',
  `settle_name` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '结算方式名称',
  `settle_type` tinyint(3) DEFAULT '1' COMMENT '结算方式类别 1、现金 2、支票 3、汇兑 4、汇票 5、信用证 6、银行呈兑汇票',
  `business_type` tinyint(3) DEFAULT NULL COMMENT '业务类型 1、现金业务 2、银行业务 3、票据业务 ',
  `pay_fees_flag` tinyint(2) DEFAULT NULL COMMENT '是否支付手续费 1、是 2、否',
  `bearer` char(50) COLLATE utf8_bin DEFAULT NULL COMMENT '承担方',
  `payment_mode` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式',
  `status` tinyint(3) DEFAULT '1' COMMENT '启用标志 1、已启用 0、已停用',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `'uk_settle_code'` (`settle_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算方法表';

-- ----------------------------
-- Table structure for settle_new_style
-- ----------------------------
DROP TABLE IF EXISTS `settle_new_style`;
CREATE TABLE `settle_new_style` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `style_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '分类编码',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态（0 = 撤消 1 = 正常）',
  `name` varchar(40) COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_style_no` (`style_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='新旧款';

-- ----------------------------
-- Table structure for settle_new_style_dtl
-- ----------------------------
DROP TABLE IF EXISTS `settle_new_style_dtl`;
CREATE TABLE `settle_new_style_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `style_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '新旧款主表分类编码',
  `season_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '季节编码',
  `season_name` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '季节名称',
  `year_code` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '年份编码',
  `year` char(4) COLLATE utf8_bin DEFAULT NULL COMMENT '年份',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_style_id` (`style_no`,`season_no`,`year`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='新旧款明细';

-- ----------------------------
-- Table structure for settle_path
-- ----------------------------
DROP TABLE IF EXISTS `settle_path`;
CREATE TABLE `settle_path` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `path_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '结算路径编码',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '结算路径名称',
  `bill_basis` int(11) DEFAULT NULL COMMENT '单据依据',
  `bill_type` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '单据类型(可多选)',
  `return_own_flag` tinyint(3) DEFAULT '0' COMMENT '是否原厂退残(0 : 否 1 : 是)',
  `settle_category_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '结算分类编码',
  `style_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '新旧款编码',
  `start_date` date DEFAULT NULL COMMENT '启用日期',
  `end_date` date DEFAULT NULL COMMENT '终止日期',
  `audit_status` tinyint(3) DEFAULT '0' COMMENT '审核状态',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态（0 = 撤消 1 = 正常）',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `auditor` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_path_no` (`path_no`),
  KEY `style_type` (`style_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算路径设置';

-- ----------------------------
-- Table structure for settle_path_brand_rel
-- ----------------------------
DROP TABLE IF EXISTS `settle_path_brand_rel`;
CREATE TABLE `settle_path_brand_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `path_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '结算路径编码',
  `brand_unit_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌部编码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_brand_no` (`path_no`,`brand_unit_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算路径和品牌的关联表';

-- ----------------------------
-- Table structure for settle_path_dtl
-- ----------------------------
DROP TABLE IF EXISTS `settle_path_dtl`;
CREATE TABLE `settle_path_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `path_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '结算路径编码',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算主体编码',
  `company_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '结算主体名称',
  `financial_basis` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算依据',
  `financial_basis_text` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '结算依据文本',
  `path_order` int(9) NOT NULL COMMENT '结算次序',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_company_no` (`path_no`,`company_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='结算路径设置明细';

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_no` char(18) CHARACTER SET utf8 NOT NULL COMMENT '店铺编码',
  `code` varchar(18) CHARACTER SET utf8 NOT NULL COMMENT '店铺外码',
  `store_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '店铺所属仓库编码',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算公司编码',
  `search_code` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '检索码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺全称',
  `organ_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '组织编号',
  `biz_city_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营城市编号',
  `sys_no` varchar(10) COLLATE utf8_bin DEFAULT '00' COMMENT '所属业务单元',
  `open_date` date DEFAULT NULL COMMENT '成立日期(店铺正式营业的日期)',
  `close_date` date DEFAULT NULL COMMENT '撤销日期(店铺停止营运的日期)',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '店铺状态( 0:冻结,1:正常,9:撤销)',
  `area` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '卖场面积',
  `area_left` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '仓库面积',
  `area_total` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '总面积',
  `area_unit` varchar(6) CHARACTER SET utf8 NOT NULL DEFAULT '1' COMMENT '面积单位(1:㎡)',
  `province_no` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '行政省编码',
  `city_no` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '行政市编码',
  `county_no` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '行政县编码',
  `address` varchar(240) CHARACTER SET utf8 DEFAULT NULL COMMENT '地址(填写时不用包含省、市、县)',
  `zip_code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `contact_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `tel` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `fax` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '传真号',
  `email` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮箱',
  `channel_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '销售渠道编码',
  `location` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '品柜形式',
  `employe_amount` int(8) DEFAULT NULL COMMENT '店员配备数(门店必填,指标准的店员配备数量)',
  `pay_type` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算方式(门店必填, 1:扣费店 2:租金店 3:不结算)',
  `digits` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '收银位数(门店必填, 0:元 1:角 2:分',
  `startup_time` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '每天营业开始时间',
  `shutdown_time` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '每天营业关闭时间',
  `shop_level` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '门店级别( A、B、C、D、E)',
  `major` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '主营品类(门店必填, 1:男鞋 2:女鞋 3:童鞋 4:综合)',
  `multi` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '店铺细类 单品多品(门店必填,C:多品店 D:单品店)',
  `sale_mode` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺大类 批发零售(门店必填,1:零售；2:批发)',
  `retail_type` varchar(20) COLLATE utf8_bin DEFAULT 'A0' COMMENT '店铺小类 (销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场\n\n特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `region_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '片区编码',
  `cmcdist_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商圈编码',
  `category_code` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '经营类型（男鞋，女鞋，综合）',
  `shop_classify` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺类别',
  `price_adjust_level` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '调价级别',
  `on_line_flag` tinyint(3) unsigned DEFAULT '1' COMMENT '在线标志:1在线，0离线',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_shop_no` (`shop_no`) USING BTREE,
  KEY `idx_store_no` (`store_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='门店信息表';

-- ----------------------------
-- Table structure for shop_balance_date
-- ----------------------------
DROP TABLE IF EXISTS `shop_balance_date`;
CREATE TABLE `shop_balance_date` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '结算主体编号',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '结算主体名称',
  `bsgroups_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团编码',
  `bsgroups_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '商业集团名称',
  `mall_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '商场编码',
  `mall_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '商场名称',
  `shop_no` char(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺编码',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺全称',
  `retail_type` varchar(20) COLLATE utf8_bin DEFAULT 'A0' COMMENT '(销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)',
  `month` char(15) COLLATE utf8_bin DEFAULT NULL COMMENT '月份',
  `balance_start_date` date DEFAULT NULL COMMENT '结算起始日期',
  `balance_end_date` date DEFAULT NULL COMMENT '结算终止日期',
  `balance_flag` tinyint(3) unsigned DEFAULT '1' COMMENT '否生成结算单(1-未生成，2-已生成)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime DEFAULT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `bill_already` tinyint(3) DEFAULT '1' COMMENT '是否已开票(1-未开票,2-已开票)',
  `should_bill_date` date DEFAULT NULL COMMENT '应开票日期',
  `income_payments_date` date DEFAULT NULL COMMENT '应回款日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商场门店结算期设置表';

-- ----------------------------
-- Table structure for shop_brand
-- ----------------------------
DROP TABLE IF EXISTS `shop_brand`;
CREATE TABLE `shop_brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '门店品牌ID',
  `shop_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺编码',
  `brand_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌编码',
  `order_unit_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '默认订货单位',
  `brand_flag` tinyint(3) DEFAULT '0' COMMENT '是否主营品牌(1是，0否)',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_store_brand` (`shop_no`,`brand_no`,`order_unit_no`) USING BTREE,
  KEY `idx_order_unit_no` (`order_unit_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='店铺品牌信息表';

-- ----------------------------
-- Table structure for shop_group
-- ----------------------------
DROP TABLE IF EXISTS `shop_group`;
CREATE TABLE `shop_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `shop_group_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '店铺分组编号',
  `shop_group_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺分组名称',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '公司编码',
  `company_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '公司名称',
  `client_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '客户编码（商场和商业集团）',
  `client_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '客户编码（商场和商业集团）',
  `invoice_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '开票名称',
  `template_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '发票模板编码',
  `template_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '发票模板名称',
  `value_date` date DEFAULT NULL COMMENT '生效日',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `status` tinyint(3) DEFAULT '0' COMMENT '0-停用;1-启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='店铺开票规则';

-- ----------------------------
-- Table structure for shop_group_dtl
-- ----------------------------
DROP TABLE IF EXISTS `shop_group_dtl`;
CREATE TABLE `shop_group_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `shop_group_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺分组编号',
  `shop_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺编号',
  `shop_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '店铺名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='店铺分组详情';

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `store_no` char(18) CHARACTER SET utf8 NOT NULL COMMENT '仓库编码',
  `parent_no` char(18) COLLATE utf8_bin DEFAULT '0' COMMENT '所属主仓库',
  `store_code` varchar(18) CHARACTER SET utf8 NOT NULL COMMENT '机构代号',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '机构简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '机构全称',
  `sys_no` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '所属业务单元',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '机构状态( 0:冻结,1:正常,9:撤销)',
  `area` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '仓库面积',
  `province_no` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '行政省编码',
  `city_no` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '行政市编码',
  `county_no` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '行政县编码',
  `zone_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '经营区域编号',
  `address` varchar(240) CHARACTER SET utf8 DEFAULT NULL COMMENT '地址(填写时不用包含省、市、县)',
  `zip_code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `contact_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `tel` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `fax` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '传真号',
  `store_type` tinyint(3) unsigned NOT NULL DEFAULT '22' COMMENT '机构类型(21:店仓  22:仓库)',
  `storage_type` varchar(20) CHARACTER SET utf8 DEFAULT '1' COMMENT '仓库类别:1城市厂、2中转仓、3工厂仓',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  `search_code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '检索码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_store_no` (`store_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='机构信息表';

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  `code` varchar(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '供应商外码',
  `supplier_no` char(18) CHARACTER SET utf8 NOT NULL COMMENT '供应商编码',
  `opcode` varchar(8) CHARACTER SET utf8 NOT NULL COMMENT '供应商特征码(必须输入且只能输入2位)',
  `short_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商简称',
  `full_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商全称',
  `sys_no` varchar(10) COLLATE utf8_bin DEFAULT '00' COMMENT '所属业务单元',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '供应商状态(0 = 撤消 1 = 正常,2待确认,3确认)',
  `biz_type` varchar(20) COLLATE utf8_bin DEFAULT '0' COMMENT '经营性质(0:本厂 1:加工 2:外厂 3:样品)',
  `bank_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '银行帐号',
  `bank_account_name` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '银行账户名',
  `contact_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `tel` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `tax_registry_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '税务登记号',
  `tax_level` varchar(20) COLLATE utf8_bin DEFAULT '0' COMMENT '纳税级别(0:一般纳税人 1:小规模纳税人)',
  `legal_person` varchar(80) CHARACTER SET utf8 DEFAULT NULL COMMENT '法人代表',
  `identity_card` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '营业证号/身份证号',
  `fax` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '传真号',
  `email` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮箱',
  `province_no` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '行政省编码',
  `city_no` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '行政市编码',
  `county_no` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '行政县编码',
  `address` varchar(240) CHARACTER SET utf8 DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `costtaxrate` decimal(4,2) DEFAULT NULL COMMENT '进项税率',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `search_code` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '检索码',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_supplier_no` (`supplier_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='供应商信息表';

-- ----------------------------
-- Table structure for supplier_group
-- ----------------------------
DROP TABLE IF EXISTS `supplier_group`;
CREATE TABLE `supplier_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '组代号',
  `group_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '组名称',
  `enable_time` datetime DEFAULT NULL COMMENT '启用日期',
  `disable_time` datetime DEFAULT NULL COMMENT '终止日期',
  `status` tinyint(3) unsigned DEFAULT '0' COMMENT '是否审核，0未审核，1审核',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='供应商组设置';

-- ----------------------------
-- Table structure for supplier_group_rel
-- ----------------------------
DROP TABLE IF EXISTS `supplier_group_rel`;
CREATE TABLE `supplier_group_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '组代号',
  `supplier_no` char(18) CHARACTER SET utf8 NOT NULL COMMENT '供应商编码',
  `supplier_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '供应商名称',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_no` (`group_no`,`supplier_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='供应商组与供应商关联表';

-- ----------------------------
-- Table structure for voucher_type
-- ----------------------------
DROP TABLE IF EXISTS `voucher_type`;
CREATE TABLE `voucher_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `vouch_type_code` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '凭证类型编码',
  `vouch_type_name` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '凭证类别名称',
  `short_name` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '类别简称',
  `show_order` tinyint(3) DEFAULT NULL COMMENT '显示顺序',
  `company_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '公司编码',
  `currency_code` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '默认币种',
  `seal_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '封存标志',
  `glorg_book` char(20) COLLATE utf8_bin DEFAULT NULL COMMENT '主体账簿主键',
  `vouch_type` tinyint(3) DEFAULT NULL COMMENT '凭证分类',
  `restrict_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '科目限制标志',
  `print_template` char(20) COLLATE utf8_bin DEFAULT NULL COMMENT '默认打印模板',
  `del_flag` tinyint(3) unsigned DEFAULT '0' COMMENT '删除标志',
  `status` tinyint(3) DEFAULT '0' COMMENT '启用标志 1、已启用 0、已停用',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_vouch_type_code` (`vouch_type_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='凭证类型表';

-- ----------------------------
-- Table structure for wholesale_margin_init
-- ----------------------------
DROP TABLE IF EXISTS `wholesale_margin_init`;
CREATE TABLE `wholesale_margin_init` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '结算主体编码',
  `company_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '结算主体名称',
  `customer_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '客户编码',
  `customer_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '客户名称',
  `margin_amount` decimal(12,2) DEFAULT '0.00' COMMENT '保证金额度',
  `init_margin_amount` decimal(12,2) DEFAULT '0.00' COMMENT '初始保证金额度',
  `init_pre_payment` decimal(12,2) DEFAULT '0.00' COMMENT '初始预收款',
  `pre_order_no` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '预收订单号',
  `finish_flag` tinyint(3) DEFAULT '0' COMMENT '初始化完成标志（0：未完成 1：已完成）',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='客户保证金及预收款初始化';

-- ----------------------------
-- Table structure for wholesale_prepay_warn
-- ----------------------------
DROP TABLE IF EXISTS `wholesale_prepay_warn`;
CREATE TABLE `wholesale_prepay_warn` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '结算主体编码',
  `company_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '结算主体名称',
  `customer_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '客户编码',
  `customer_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '客户名称',
  `margin_amount` decimal(12,2) DEFAULT '0.00' COMMENT '保证金金额',
  `reced_margin_amount` decimal(12,2) DEFAULT '0.00' COMMENT '已收保证金金额',
  `margin_full` tinyint(3) DEFAULT NULL COMMENT '保证金是否足额（0 = 是 1 = 否）',
  `pre_payment` decimal(12,2) DEFAULT '0.00' COMMENT '预收款',
  `pre_order_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '预收订单号',
  `order_amount` decimal(12,2) DEFAULT '0.00' COMMENT '订单金额',
  `send_out_amount` decimal(12,2) DEFAULT '0.00' COMMENT '出库金额',
  `pre_order_full` tinyint(3) DEFAULT NULL COMMENT '订货预收是否足额（0 = 是 1 = 否）',
  `pre_send_out_full` tinyint(3) DEFAULT NULL COMMENT '发货预收是否足额（0 = 是 1 = 否）',
  `pre_payment_profit` decimal(12,2) DEFAULT '0.00' COMMENT '预收款盈余',
  `reversal_amount` decimal(12,2) DEFAULT '0.00' COMMENT '已冲销金额',
  `reversal_over_amount` decimal(12,2) DEFAULT '0.00' COMMENT '可冲销金额',
  `pre_payment_over` decimal(12,2) DEFAULT '0.00' COMMENT '预收款余额',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='客户保证金预收款预警';

-- ----------------------------
-- Table structure for wholesale_rece_term
-- ----------------------------
DROP TABLE IF EXISTS `wholesale_rece_term`;
CREATE TABLE `wholesale_rece_term` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `term_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '条款编码',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '条款名称',
  `company_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '结算主体编码',
  `company_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '结算主体名称',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态（0 = 撤消 1 = 正常）',
  `create_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL COMMENT '建档时间',
  `update_user` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='地区批发收款条款-表头';

-- ----------------------------
-- Table structure for wholesale_rece_term_dtl
-- ----------------------------
DROP TABLE IF EXISTS `wholesale_rece_term_dtl`;
CREATE TABLE `wholesale_rece_term_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `term_no` char(18) COLLATE utf8_bin NOT NULL COMMENT '条款编码',
  `advance_type` tinyint(3) DEFAULT NULL COMMENT '预收类型',
  `advance_scale` int(3) DEFAULT NULL COMMENT '预收比例',
  `control_point` tinyint(3) unsigned DEFAULT '1' COMMENT '控制点(0-订货 1-补货)',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='地区批发收款条款-表体';

-- ----------------------------
-- Table structure for zone_info
-- ----------------------------
DROP TABLE IF EXISTS `zone_info`;
CREATE TABLE `zone_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '经营区域ID',
  `zone_no` char(18) NOT NULL COMMENT '经营区域编号',
  `zone_code` char(1) NOT NULL COMMENT '区域编码(有且必须只能输入一位)',
  `name` varchar(10) NOT NULL COMMENT '经营区域名称',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '区域状态(0 = 撤消 1 = 正常)',
  `sys_no` varchar(10) DEFAULT '00' COMMENT '品牌库编码(00-通用定义 其他则为相应品牌库)',
  `create_user` varchar(32) DEFAULT NULL COMMENT '建档人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建档时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `time_seq` bigint(20) DEFAULT NULL COMMENT '时间序列',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_zone_no` (`zone_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='经营区域信息表';

ALTER TABLE `bill_shop_balance_diff` MODIFY COLUMN `par_balance_no`  char(25) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '上期结算编号，滚动add' AFTER `mark_id`;


ALTER TABLE `bill_balance` ADD COLUMN `rebate_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '返利金额' AFTER `balance_amount`;

ALTER TABLE `bill_common_invoice_register` ADD COLUMN `invoice_no_flag`  varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '采购发票号标志' AFTER `balance_type`;

ALTER TABLE `bill_common_invoice_register` DROP COLUMN `invoice_flag`;

ALTER TABLE `bill_pre_payment_nt` MODIFY COLUMN `balance_type`  tinyint(3) NULL DEFAULT NULL COMMENT '结算类型' AFTER `bill_no`;

ALTER TABLE `bill_pre_payment_nt` MODIFY COLUMN `sale_order_no`  char(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '销售订单编码' AFTER `bill_date`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `sale_type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '销售类别(1:零售 2:批发)' AFTER `multi`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `biz_type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型(1-调货、2-员购、3-团购、4-批发、5-门店、6-其他)' AFTER `sale_type`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `sale_month`  char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '销售月份' AFTER `month`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `bill_month`  char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '开票月份' AFTER `sale_month`;

ALTER TABLE `bill_sales_sum` MODIFY COLUMN `category_name`  varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '类别名称' AFTER `category_no`;

ALTER TABLE `bill_sales_sum` MODIFY COLUMN `lma_period_salesnum`  int(11) NULL DEFAULT 0 COMMENT '上月结算期后销售量' AFTER `shop_level`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `lma_period_tagamount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '上月结算期后牌价额' AFTER `lma_period_salesamount`;

ALTER TABLE `bill_sales_sum` MODIFY COLUMN `tmi_period_salesnum`  int(11) NULL DEFAULT 0 COMMENT '本月结算期内销售数量' AFTER `lma_period_balanceamount`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `tmi_period_tagamount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '本月结算期内牌价额' AFTER `tmi_period_salesamount`;

ALTER TABLE `bill_sales_sum` MODIFY COLUMN `bi_period_salesnum`  int(11) NULL DEFAULT 0 COMMENT '结算期内合计销售数量' AFTER `tmi_period_balanceamount`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `bi_period_tagamount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '结算期内合计牌价额' AFTER `bi_period_salesamount`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `bill_qty`  int(11) NULL DEFAULT NULL COMMENT '开票数量' AFTER `bi_period_balanceamount`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `bill_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '实际开票金额' AFTER `bill_qty`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `buyer_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '买方名称-开票单位' AFTER `buyer_no`;

ALTER TABLE `bill_sales_sum` MODIFY COLUMN `tma_period_salesnum`  int(11) NULL DEFAULT 0 COMMENT '本月结算期后销售量' AFTER `buyer_name`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `tma_period_tagamount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '本月结算期后合计牌价额' AFTER `tma_period_salesamount`;

ALTER TABLE `bill_sales_sum` MODIFY COLUMN `tm_salesnum`  int(11) NULL DEFAULT 0 COMMENT '本月合计销售量' AFTER `tma_period_balanceamount`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `tm_tagamount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '本月合计牌价额' AFTER `tm_salesamount`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `sum_changebalanceamount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '调整前期扣费' AFTER `tm_balanceamount`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `sum_salesdeductions`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '扣费合计' AFTER `sum_changebalanceamount`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `tm_billbef_deducamount`  decimal(20,2) NULL DEFAULT NULL COMMENT '本月商场票前费用' AFTER `tm_salesamount_prodiscount`;

ALTER TABLE `bill_sales_sum` ADD COLUMN `tm_nobilling_sumamount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '本月累计未开票' AFTER `headquarter_cost`;

ALTER TABLE `bill_shop_balance` MODIFY COLUMN `sales_diffamount`  decimal(12,2) NULL DEFAULT NULL COMMENT '报数差异 报数差异' AFTER `system_sales_amount`;

ALTER TABLE `bill_shop_balance` MODIFY COLUMN `mall_deduct_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '扣费总额' AFTER `sales_diffamount`;

ALTER TABLE `bill_shop_balance` MODIFY COLUMN `balance_diff_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '结算差异总额合计 ' AFTER `balance_deduct_amount`;

ALTER TABLE `bill_shop_balance` ADD COLUMN `source_balance_no`  char(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '源结算单号-预估转正式赋值' AFTER `non_payment_amount`;

ALTER TABLE `bill_shop_balance` ADD COLUMN `conprice_deduct_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '合同正价扣费' AFTER `source_balance_no`;

ALTER TABLE `bill_shop_balance` ADD COLUMN `prom_deduct_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '促销扣费' AFTER `conprice_deduct_amount`;

ALTER TABLE `bill_shop_balance` ADD COLUMN `prom_plusbuckle_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '促销加扣' AFTER `prom_deduct_amount`;

ALTER TABLE `bill_shop_balance` ADD COLUMN `adjust_deduct_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '票前费用调整金额' AFTER `prom_plusbuckle_amount`;

ALTER TABLE `bill_shop_balance` ADD COLUMN `adjust_diff_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '结算差异调整金额' AFTER `adjust_deduct_amount`;

ALTER TABLE `bill_shop_balance` ADD COLUMN `invoice_no`  varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '发票号' AFTER `invoice_register_no`;

ALTER TABLE `bill_shop_balance_deduct` ADD COLUMN `generate_type`  tinyint(3) NULL DEFAULT 0 COMMENT '生成方式（0：系统自动生成，1：在界面上新增）' AFTER `remark`;

ALTER TABLE `cost_category_setting` ADD COLUMN `accounts_no`  char(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '会计科目编码' AFTER `name`;

ALTER TABLE `cost_category_setting` ADD COLUMN `accounts_name`  varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '会计科目名称' AFTER `accounts_no`;

ALTER TABLE `cost_category_setting` ADD COLUMN `company_no`  char(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '结算公司编码' AFTER `accounts_name`;

ALTER TABLE `cost_category_setting` ADD COLUMN `company_name`  varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '结算公司名称' AFTER `company_no`;

ALTER TABLE `deposit_cash` MODIFY COLUMN `bill_no`  char(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '单据编号' AFTER `id`;

ALTER TABLE `exception_price_bill` ADD COLUMN `biz_type`  int(11) NULL DEFAULT NULL COMMENT '业务类型' AFTER `bill_type`;

ALTER TABLE `exception_price_bill` MODIFY COLUMN `order_unit_no`  char(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订货单位' AFTER `biz_type`;

ALTER TABLE `exception_price_bill` MODIFY COLUMN `bill_date`  date NULL DEFAULT NULL COMMENT '业务发生日期(审核日期、销售日期)' AFTER `order_unit_no`;

ALTER TABLE `exception_price_bill` ADD COLUMN `exception_reason`  varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '异常原因' AFTER `base_cost`;

ALTER TABLE `financial_account` ADD COLUMN `price_zone`  char(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认价格区' AFTER `supplier_no`;

ALTER TABLE `headquarter_cost_maintain` ADD COLUMN `add_basis`  char(3) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '加价依据,2牌价，1厂进价' AFTER `add_rule_no`;

ALTER TABLE `headquarter_cost_maintain` ADD COLUMN `add_price`  decimal(10,2) NULL DEFAULT NULL COMMENT '加价' AFTER `add_basis`;

ALTER TABLE `headquarter_cost_maintain` ADD COLUMN `add_discount`  decimal(10,2) NULL DEFAULT NULL COMMENT '加折' AFTER `add_price`;

ALTER TABLE `headquarter_cost_maintain` ADD COLUMN `discount_rate`  decimal(10,2) NULL DEFAULT NULL COMMENT '折扣率' AFTER `add_discount`;
DROP TABLE IF EXISTS `inside_biz_type`;
CREATE TABLE `inside_biz_type` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`company_no`  char(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '结算公司编号' ,
`company_name`  varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '结算公司名称' ,
`biz_type_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '业务类型编号' ,
`biz_type_name`  varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '业务类型名称' ,
`status`  tinyint(3) NULL DEFAULT NULL COMMENT '状态' ,
`carry_over_cost`  tinyint(3) NULL DEFAULT NULL COMMENT '是否结转成本' ,
`create_user`  varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '建档人' ,
`create_time`  datetime NULL DEFAULT NULL COMMENT '建档时间' ,
`update_user`  varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改人' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`remark`  varchar(225) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注，在销售明细成本有为0时，需要增加备注说明' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
ROW_FORMAT=Compact
;

ALTER TABLE `invoice_template_set_dtl` ADD COLUMN `type_model`  varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '规格型号' AFTER `category_name`;

ALTER TABLE `mall_deduction_set` ADD COLUMN `company_no`  char(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '结算公司编码' AFTER `deduction_no`;

ALTER TABLE `mall_deduction_set` ADD COLUMN `company_name`  varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '结算公司名称' AFTER `company_no`;

ALTER TABLE `mall_deduction_set` ADD COLUMN `debited_rental`  tinyint(3) NULL DEFAULT NULL COMMENT '是否是场地经营费用（0=不是 1=是）' AFTER `type`;

ALTER TABLE `order_assistant` MODIFY COLUMN `order_no`  varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单编号' AFTER `id`;

ALTER TABLE `order_assistant` ADD COLUMN `zone_yyyymm`  char(8) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '大区年月' AFTER `order_type`;

ALTER TABLE `order_dtl` MODIFY COLUMN `order_no`  varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单编号' AFTER `id`;

ALTER TABLE `order_dtl` ADD COLUMN `zone_yyyymm`  char(8) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '大区年月' AFTER `return_exchange_num`;

ALTER TABLE `order_main` DROP INDEX `idx_shop_no`;

ALTER TABLE `order_main` MODIFY COLUMN `order_no`  varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单编号' AFTER `id`;

ALTER TABLE `order_main` ADD COLUMN `biz_type_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '业务类型编号' AFTER `business_type`;

ALTER TABLE `order_main` ADD COLUMN `biz_type_name`  varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '业务类型名称' AFTER `biz_type_code`;

ALTER TABLE `order_main` ADD COLUMN `logistics_mode`  tinyint(1) NULL DEFAULT NULL COMMENT '物流方式，1-自提，2-快递' AFTER `take_mode`;

ALTER TABLE `order_main` ADD COLUMN `zone_yyyymm`  char(8) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '大区年月' AFTER `pickup_date`;

ALTER TABLE `order_payway` MODIFY COLUMN `order_no`  varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单编号' AFTER `id`;

ALTER TABLE `order_payway` ADD COLUMN `zone_yyyymm`  char(8) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '大区年月' AFTER `order_type`;

ALTER TABLE `order_promotion_dtl` MODIFY COLUMN `order_no`  varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '业务编号,订单号或退换货号' AFTER `seq_id`;

ALTER TABLE `order_promotion_dtl` ADD COLUMN `zone_yyyymm`  char(8) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '大区年月' AFTER `order_type`;

DROP TABLE IF EXISTS `purchase_price`;
CREATE TABLE `purchase_price` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT ' 主键ID' ,
`item_no`  char(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '货品编号' ,
`item_code`  varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '货品编码' ,
`supplier_no`  char(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商编码' ,
`purchase_price`  decimal(12,2) UNSIGNED NULL DEFAULT 0.00 COMMENT '采购价' ,
`material_price`  decimal(12,2) UNSIGNED NULL DEFAULT 0.00 COMMENT '物料价' ,
`factory_price`  decimal(12,2) UNSIGNED NULL DEFAULT 0.00 COMMENT '厂进价' ,
`effective_date`  date NULL DEFAULT NULL COMMENT '生效时间' ,
`status`  tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '是否可用（1可用 0不可用）' ,
`create_user`  varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '建档人' ,
`create_time`  datetime NULL DEFAULT NULL COMMENT '建档时间' ,
`update_user`  varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新人' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `idx_purchase_price` (`item_no`, `supplier_no`, `effective_date`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin 
;

ALTER TABLE `region_cost_maintain` ADD COLUMN `add_basis`  char(3) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '加价依据,2牌价，3总部成本' AFTER `add_rule_no`;

ALTER TABLE `region_cost_maintain` ADD COLUMN `add_price`  decimal(10,2) NULL DEFAULT NULL COMMENT '加价' AFTER `add_basis`;

ALTER TABLE `region_cost_maintain` ADD COLUMN `add_discount`  decimal(10,2) NULL DEFAULT NULL COMMENT '加折' AFTER `add_price`;

ALTER TABLE `region_cost_maintain` ADD COLUMN `discount_rate`  decimal(10,2) NULL DEFAULT NULL COMMENT '折扣率' AFTER `add_discount`;

ALTER TABLE `register_invoice` MODIFY COLUMN `bill_no`  char(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '单据编号' AFTER `id`;

ALTER TABLE `return_exchange_dtl` MODIFY COLUMN `business_no`  varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单编号' AFTER `id`;

ALTER TABLE `return_exchange_dtl` ADD COLUMN `zone_yyyymm`  char(8) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '大区年月' AFTER `return_exchange_num`;

ALTER TABLE `return_exchange_main` MODIFY COLUMN `business_no`  varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '退换货单号,业务编号' AFTER `id`;

ALTER TABLE `return_exchange_main` MODIFY COLUMN `old_order_no`  varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '原订单号' AFTER `monthly_datetime`;

ALTER TABLE `return_exchange_main` ADD COLUMN `zone_yyyymm`  char(8) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '大区年月' AFTER `invoice_date`;

ALTER TABLE `shop_balance_date` ADD COLUMN `invoice_should_send_date`  date NULL DEFAULT NULL COMMENT '发票应寄送日期' AFTER `income_payments_date`;

ALTER TABLE `shop_balance_date` ADD COLUMN `invoice_should_arrive_date`  date NULL DEFAULT NULL COMMENT '发票应收到日期' AFTER `invoice_should_send_date`;

DROP TABLE IF EXISTS `special_zone_info`;
CREATE TABLE `special_zone_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '经营区域ID' ,
`zone_no`  char(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '经营区域编号' ,
`zone_code`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区域编码(有且必须只能输入一位)' ,
`name`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '经营区域名称' ,
`status`  tinyint(3) UNSIGNED NULL DEFAULT 1 COMMENT '区域状态(0 = 撤消 1 = 正常)' ,
`sys_no`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '00' COMMENT '品牌库编码(00-通用定义 其他则为相应品牌库)' ,
`create_user`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '建档人' ,
`create_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建档时间' ,
`update_user`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人' ,
`update_time`  datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间' ,
`remark`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注' ,
`time_seq`  bigint(20) NULL DEFAULT NULL COMMENT '时间序列' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `uk_special_zone_no` (`zone_no`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci 
;
DROP TABLE IF EXISTS `bill_sales_outstanding_analysis`;
CREATE TABLE `bill_sales_outstanding_analysis` (
`id`  char(32) NOT NULL COMMENT '主键' ,
`zone_no`  char(18) NULL DEFAULT NULL COMMENT '经营区域编号' ,
`zone_name`  varchar(10) NULL DEFAULT NULL COMMENT '经营区域名称' ,
`province_no`  varchar(20) NULL DEFAULT NULL COMMENT '行政省编码' ,
`province_name`  varchar(50) NULL DEFAULT NULL COMMENT '行政省名称' ,
`organ_no1`  char(18) NULL DEFAULT NULL COMMENT '管理城市编号' ,
`organ_name1`  varchar(50) NULL DEFAULT NULL COMMENT '管理城市名称' ,
`organ_no2`  char(18) NULL DEFAULT NULL COMMENT '经营城市编号' ,
`organ_name2`  varchar(50) NULL DEFAULT NULL COMMENT '经营城市名称' ,
`bsgroups_no`  char(18) NULL DEFAULT NULL COMMENT '商业集团编码' ,
`bsgroups_name`  varchar(50) NULL DEFAULT NULL COMMENT '商业集团名称' ,
`mall_no`  char(18) NOT NULL COMMENT '商场编码' ,
`mall_name`  varchar(120) NOT NULL COMMENT '商场名称' ,
`cmcdist_no`  char(18) NOT NULL COMMENT '商圈编码' ,
`cmcdist_name`  varchar(50) NULL DEFAULT NULL COMMENT '商圈名称' ,
`region_no`  char(18) NULL DEFAULT NULL COMMENT '片区编码' ,
`region_name`  varchar(50) NULL DEFAULT NULL COMMENT '片区名称' ,
`sale_type`  varchar(20) NULL DEFAULT NULL COMMENT '销售类别(1:零售 2:批发)' ,
`biz_type`  varchar(20) NULL DEFAULT NULL COMMENT '业务类型(1-调货、2-员购、3-团购、4-批发、5-门店、6-其他)' ,
`shop_level`  varchar(20) NULL DEFAULT NULL COMMENT '门店级别( A、B、C、D、E)' ,
`sale_mode`  varchar(20) NULL DEFAULT NULL COMMENT '店铺大类 批发零售(门店必填,1:零售；2:批发)' ,
`retail_type`  varchar(20) NULL DEFAULT 'A0' COMMENT '店铺小类 (销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场\n\n特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)' ,
`major`  varchar(20) NOT NULL COMMENT '主营品类(门店必填, 1:男鞋 2:女鞋 3:童鞋 4:综合)' ,
`multi`  varchar(20) NOT NULL COMMENT '店铺细类 单品多品(门店必填,C:多品店 D:单品店)' ,
`shop_no`  char(18) NULL DEFAULT NULL COMMENT '店铺编码' ,
`short_name`  varchar(100) NULL DEFAULT NULL COMMENT '店铺简称' ,
`full_name`  varchar(200) NULL DEFAULT NULL COMMENT '店铺全称' ,
`assistant_no`  varchar(20) NOT NULL COMMENT '营业员工号' ,
`assistant_name`  varchar(32) NOT NULL COMMENT '营业员姓名' ,
`activity_no`  varchar(40) NULL DEFAULT NULL COMMENT '商场(品牌)活动单号' ,
`activity_type`  tinyint(4) NULL DEFAULT NULL COMMENT '活动类型,1-买换 2-打折 3-其他' ,
`pro_no`  varchar(40) NOT NULL COMMENT '促销活动编号' ,
`pro_name`  varchar(100) NOT NULL COMMENT '促销活动名称' ,
`pro_start_date`  date NULL DEFAULT NULL COMMENT '活动起始日' ,
`pro_end_date`  date NULL DEFAULT NULL COMMENT '活动终止日' ,
`strength`  decimal(8,2) NULL DEFAULT NULL COMMENT '力度' ,
`virtual_flag`  tinyint(4) NULL DEFAULT NULL COMMENT '虚实,0-虚数 1-实数' ,
`properties`  tinyint(4) NULL DEFAULT NULL COMMENT '属性,1-满送 2-满减 3-折扣 4-其他' ,
`rate`  decimal(8,4) NULL DEFAULT NULL COMMENT '扣率' ,
`rate_code`  varchar(10) NULL DEFAULT NULL COMMENT '扣率代码,A B...' ,
`rate_name`  varchar(32) NULL DEFAULT NULL COMMENT '扣率名称' ,
`billing_code`  varchar(32) NULL DEFAULT NULL COMMENT '商场结算码' ,
`sku_no`  char(32) NOT NULL COMMENT '商品SKU' ,
`item_no`  char(18) NOT NULL COMMENT '商品内码' ,
`size_no`  varchar(10) NOT NULL COMMENT '商品尺码' ,
`item_code`  varchar(18) NULL DEFAULT NULL COMMENT '商品编号' ,
`item_name`  varchar(200) NULL DEFAULT NULL COMMENT '商品名称' ,
`barcode`  varchar(50) NULL DEFAULT NULL COMMENT '商品条码' ,
`brand_no`  char(18) NULL DEFAULT NULL COMMENT '品牌编码' ,
`brand_name`  varchar(200) NULL DEFAULT NULL COMMENT '品牌中文名称' ,
`category_no`  char(18) NOT NULL COMMENT '类别编码' ,
`category_name`  varchar(200) NULL DEFAULT NULL COMMENT '类别中文名称' ,
`root_category_no`  char(18) NULL DEFAULT NULL COMMENT '商品大类编码' ,
`root_category_name`  varchar(200) NULL DEFAULT NULL COMMENT '商品大类名称' ,
`balance_base`  tinyint(1) NULL DEFAULT 0 COMMENT '结算基数,0-销售金额 1-牌价金额' ,
`season_no`  char(18) NULL DEFAULT NULL COMMENT '季节编码' ,
`season_name`  varchar(10) NULL DEFAULT NULL COMMENT '季节名称' ,
`year_code`  char(18) NULL DEFAULT NULL COMMENT '年份编码' ,
`year`  char(4) NULL DEFAULT NULL COMMENT '年份' ,
`sell_season`  varchar(20) NOT NULL DEFAULT 'A' COMMENT '季节(下拉框选择,A:春 B:夏 C:秋 D:冬)' ,
`style_no`  char(18) NOT NULL COMMENT '分类编码' ,
`style_name`  varchar(40) NOT NULL COMMENT '分类名称-新旧款' ,
`gender`  varchar(20) NOT NULL COMMENT '性别(下拉框选择,值: 男, 女, 童, 无性别)' ,
`orderfrom`  varchar(20) NOT NULL COMMENT '订货形式(下拉框选择,值: 1:自产 2:外购 3:地区自购)' ,
`style`  varchar(20) NULL DEFAULT NULL COMMENT '风格' ,
`color_no`  char(18) NULL DEFAULT NULL COMMENT '颜色编码' ,
`color_name`  char(30) NULL DEFAULT NULL COMMENT '颜色名称' ,
`out_date`  date NOT NULL COMMENT '销售日期' ,
`qty`  int(11) NULL DEFAULT NULL COMMENT '数量' ,
`tag_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '牌价额' ,
`disc_price`  decimal(20,2) NULL DEFAULT NULL COMMENT '商品折扣价' ,
`amount`  decimal(20,2) NULL DEFAULT NULL COMMENT '商品总金额,(结算价-减价)*数量' ,
`sales_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '终端销售收入' ,
`cost`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '成本' ,
`deduct_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '扣费' ,
`contract_rate`  decimal(8,4) NULL DEFAULT NULL COMMENT '合同扣率' ,
`conprice_deduct_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '合同正价扣费' ,
`conprice_ladder_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '合同阶梯加扣' ,
`prom_plusbuckle_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '促销活动加扣' ,
`back_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '回款额' ,
`discount_rate`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '折扣率' ,
`gross_margin_rate`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '毛利率' ,
`deduction_rate`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '扣费率' ,
`conprice_deduct_rate`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '合同正价扣费率' ,
`conprice_ladder_rate`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '合同阶梯扣加扣率' ,
`prom_plusbuckle_rate`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '促销活动加扣率' ,
`the_discount_rate`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '净折扣率' ,
`the_margin_rate`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '净毛利率' ,
`reimbursement_rate`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '回款率' ,
`salesamount_discount`  decimal(20,2) NULL DEFAULT NULL COMMENT '合同正价扣费' ,
`salesamount_prodiscount`  decimal(20,2) NULL DEFAULT NULL COMMENT '促销折扣' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
;
DROP TABLE IF EXISTS `cmcdist`;
CREATE TABLE `cmcdist` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '商圈ID' ,
`cmcdist_no`  char(18) NOT NULL COMMENT '商圈编码' ,
`name`  varchar(50) NULL DEFAULT NULL COMMENT '商圈名称' ,
`zone_no`  char(18) NULL DEFAULT NULL COMMENT '经营区域编号' ,
`create_user`  varchar(32) NULL DEFAULT NULL COMMENT '建档人' ,
`create_time`  datetime NOT NULL COMMENT '建档时间' ,
`update_user`  varchar(32) NULL DEFAULT NULL COMMENT '修改人' ,
`update_time`  datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间' ,
`remark`  varchar(255) NULL DEFAULT '' COMMENT '备注' ,
`time_seq`  bigint(20) NULL DEFAULT NULL COMMENT '时间序列' ,
`search_code`  varchar(10) NULL DEFAULT NULL COMMENT '检索码' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `uk_cmcdist_no` (`cmcdist_no`) USING BTREE 
)
ENGINE=InnoDB
;

ALTER TABLE `exception_price_bill` ADD COLUMN `balance_type`  tinyint(3) NULL DEFAULT NULL COMMENT '结算单类型' AFTER `exception_reason`;

ALTER TABLE `exception_price_bill` ADD COLUMN `supplier_no`  varchar(10) NULL DEFAULT NULL COMMENT '供应商编码' AFTER `balance_type`;

ALTER TABLE `exception_price_bill` ADD COLUMN `company_no`  varchar(10) NULL DEFAULT NULL COMMENT '公司编码' AFTER `supplier_no`;

ALTER TABLE `exception_price_bill` ADD COLUMN `zone_no`  varchar(10) NULL DEFAULT NULL COMMENT '地区编码' AFTER `company_no`;

ALTER TABLE `order_dtl` ADD COLUMN `special_sell_flag`  tinyint(4) NULL DEFAULT 0 COMMENT '是否特卖,0-正常 1-特卖' AFTER `pro_name`;

ALTER TABLE `order_promotion_dtl` ADD COLUMN `special_sell_flag`  tinyint(4) NULL DEFAULT 0 COMMENT '是否特卖,0-正常 1-特卖' AFTER `pro_name`;

ALTER TABLE `rate_expense_operate` MODIFY COLUMN `settlement_type`  tinyint(3) NULL DEFAULT 1 COMMENT '结算类型,0-保底扣率 1-阶段扣率 2-纯租金 3-最大值(租金、扣率) 4-和值(租金+扣率)' AFTER `settlement_period`;

ALTER TABLE `rate_expense_operate` MODIFY COLUMN `debited_mode`  tinyint(3) NULL DEFAULT NULL COMMENT '费用扣取方式 1-票前 2-票后' AFTER `debited_type`;

ALTER TABLE `rate_expense_operate` MODIFY COLUMN `payment_mode`  tinyint(3) NULL DEFAULT NULL COMMENT '费用交款方式 1-账款 2-现价' AFTER `debited_mode`;

ALTER TABLE `rate_expense_operate` MODIFY COLUMN `unity_rate_flag`  tinyint(3) NULL DEFAULT 1 COMMENT '超额统一扣率 0-统一 1-不统一' AFTER `end_amount`;

ALTER TABLE `rate_expense_operate` ADD COLUMN `minimum_flag`  tinyint(3) NULL DEFAULT NULL COMMENT '促销计保底 0-计保底 1-不计保底(用于保底扣率类型)' AFTER `unity_rate_flag`;

ALTER TABLE `rate_expense_operate` MODIFY COLUMN `status`  tinyint(3) NULL DEFAULT 0 COMMENT '状态,0-未结算，1-已结算' AFTER `minimum_flag`;

ALTER TABLE `return_exchange_dtl` ADD COLUMN `special_sell_flag`  tinyint(4) NULL DEFAULT 0 COMMENT '是否特卖,0-正常 1-特卖' AFTER `pro_name`;

ALTER TABLE `bill_buy_balance` ADD COLUMN `cost_checked`  tinyint(3) NULL DEFAULT 0 COMMENT '异常价格是否已更新 (0:未更新 1:已更新)' AFTER `zone_yyyymm`;

ALTER TABLE `bill_common_register_dtl` MODIFY COLUMN `no_tax_amount`  decimal(18,2) NULL DEFAULT NULL COMMENT '不含税金额' AFTER `tax_rate`;

ALTER TABLE `bill_common_register_dtl` MODIFY COLUMN `tax_amount`  decimal(18,2) NULL DEFAULT NULL COMMENT '税额' AFTER `no_tax_amount`;

ALTER TABLE `bill_pre_payment_nt` ADD COLUMN `term_no`  varchar(200) NULL DEFAULT NULL COMMENT '收款条款编码' AFTER `paid_type`;

ALTER TABLE `bill_sale_balance` ADD COLUMN `cost_checked`  tinyint(3) NULL DEFAULT 0 COMMENT '异常价格是否已更新  (0:未更新 1:已更新)' AFTER `zone_yyyymm`;

CREATE TABLE `deposit_cash_cumulative_difference` (
`id`  char(32) NOT NULL COMMENT '流水号' ,
`shop_no`  char(18) NOT NULL COMMENT '门店编号' ,
`shop_name`  char(100) NOT NULL COMMENT '门店名称' ,
`sale_out`  date NOT NULL COMMENT '销售日期' ,
`cash_amount`  decimal(20,2) NULL DEFAULT NULL COMMENT '现金金额' ,
`cash_coupon_amount`  decimal(20,2) NULL DEFAULT NULL COMMENT '现金券金额' ,
`bank_card_amount`  decimal(20,2) NULL DEFAULT NULL COMMENT '银行卡金额' ,
`mall_card_amount`  decimal(20,2) NULL DEFAULT NULL COMMENT '商场卡金额' ,
`mall_coupon_amount`  decimal(20,2) NULL DEFAULT NULL COMMENT '商场券金额' ,
`expect_cash_amount`  decimal(20,2) NULL DEFAULT NULL COMMENT '预收款金额' ,
`outside_card_amount`  decimal(20,2) NULL DEFAULT NULL COMMENT '外卡金额' ,
`other_amount`  decimal(20,2) NULL DEFAULT NULL COMMENT '其他金额' ,
`sale_total_amount`  decimal(20,2) NULL DEFAULT NULL COMMENT '销售总额' ,
`deposit_cash_total_amount`  decimal(20,2) NULL DEFAULT NULL COMMENT '存现总额' ,
`current_deposit_cash_defference`  decimal(20,2) NULL DEFAULT NULL COMMENT '当前存现差异' ,
`deposit_cash_difference`  decimal(20,2) NULL DEFAULT NULL COMMENT '累计存现差异' ,
`create_time`  datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `UNIQUE_shop_no_sale_out_type` (`shop_no`, `sale_out`) USING BTREE 
)
ENGINE=InnoDB
ROW_FORMAT=Compact
;

CREATE INDEX `item_no` ON `order_dtl`(`item_no`, `order_no`) USING BTREE ;

ALTER TABLE `order_main` ADD COLUMN `vip_brand_no`  char(18) NULL DEFAULT NULL COMMENT '会员所属卡品牌' AFTER `vip_no`;

CREATE INDEX `idx_shop_no` ON `order_main`(`shop_no`, `order_no`) USING BTREE ;

CREATE INDEX `idx_business_no` ON `return_exchange_dtl`(`business_no`, `category_no`) USING BTREE ;

ALTER TABLE `return_exchange_main` ADD COLUMN `vip_brand_no`  char(18) NULL DEFAULT NULL COMMENT '会员所属卡品牌' AFTER `vip_no`;

CREATE INDEX `idx_shop_no` ON `return_exchange_main`(`shop_no`, `status`, `business_type`) USING BTREE ;

CREATE INDEX `idx_shop_no` ON `shop_balance_date`(`shop_no`, `month`, `balance_start_date`, `balance_end_date`) USING BTREE ;

ALTER TABLE `bill_balance` ADD COLUMN `invoice_rebate_amount` decimal(20,2) NULL DEFAULT 0.00 COMMENT '票后返利金额' AFTER `balance_amount`;

ALTER TABLE return_exchange_dtl ADD COLUMN unit_cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '单位成本' AFTER amount;
ALTER TABLE return_exchange_dtl ADD COLUMN region_cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '地区成本' AFTER amount;
ALTER TABLE return_exchange_dtl ADD COLUMN headquarter_cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '总部成本' AFTER amount;

ALTER TABLE order_dtl ADD COLUMN unit_cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '单位成本' AFTER amount;
ALTER TABLE order_dtl ADD COLUMN region_cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '地区成本' AFTER amount;
ALTER TABLE order_dtl ADD COLUMN headquarter_cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '总部成本' AFTER amount;

ALTER TABLE bill_sale_balance ADD COLUMN unit_cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '单位成本' AFTER exclusive_cost;
ALTER TABLE bill_sale_balance ADD COLUMN region_cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '地区成本' AFTER exclusive_cost;
ALTER TABLE bill_sale_balance ADD COLUMN headquarter_cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '总部成本' AFTER exclusive_cost;

ALTER TABLE bill_buy_balance ADD COLUMN unit_cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '单位成本' AFTER exclusive_cost;
ALTER TABLE bill_buy_balance ADD COLUMN region_cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '地区成本' AFTER exclusive_cost;
ALTER TABLE bill_buy_balance ADD COLUMN headquarter_cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '总部成本' AFTER exclusive_cost;

ALTER TABLE `bill_balance` MODIFY COLUMN `rebate_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '票前返利金额' AFTER `invoice_rebate_amount`;

CREATE INDEX `idx_original_bill_no` ON `bill_buy_balance`(`original_bill_no`) USING BTREE ;

CREATE FULLTEXT INDEX `idx_ref_bill_no` ON `bill_buy_balance`(`ref_bill_no`) ;

ALTER TABLE `bill_sale_balance` MODIFY COLUMN `cost_checked`  tinyint(3) NULL DEFAULT 0 COMMENT '异常价格是否已更新 (0:未更新 1:已更新)' AFTER `zone_yyyymm`;

CREATE INDEX `idx_ref_bill_no` ON `bill_sale_balance`(`ref_bill_no`(255)) USING BTREE ;

CREATE INDEX `idx_original_bill_no` ON `bill_sale_balance`(`original_bill_no`) USING BTREE ;

CREATE INDEX `ux_sale_sum` ON `bill_shop_balance`(`shop_no`, `balance_start_date`, `balance_end_date`, `balance_type`, `create_time`) USING BTREE ;

ALTER TABLE `bill_shop_balance_diff` MODIFY COLUMN `sales_diffamount`  decimal(12,2) NULL DEFAULT NULL COMMENT '差异金额' AFTER `sales_amount`;

ALTER TABLE `bill_shop_balance_diff` ADD COLUMN `report_diff_amount`  decimal(12,2) NULL DEFAULT NULL COMMENT '报数差异' AFTER `sales_diffamount`;

CREATE TABLE `color_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '颜色ID' ,
`color_no`  char(18) NOT NULL COMMENT '颜色编码(必须输入且长度必须2位)' ,
`code`  varchar(18) NOT NULL COMMENT '颜色外码' ,
`name`  varchar(50) NULL DEFAULT NULL COMMENT '颜色名称' ,
`status`  tinyint(3) UNSIGNED NULL DEFAULT 1 COMMENT '状态(0 = 禁用 1 = 正常)' ,
`sys_no`  varchar(10) NULL DEFAULT '00' COMMENT '所属业务单元' ,
`rgb`  varchar(7) NULL DEFAULT NULL COMMENT 'rgb' ,
`create_user`  varchar(32) NULL DEFAULT NULL COMMENT '建档人' ,
`create_time`  datetime NOT NULL COMMENT '建档时间' ,
`update_user`  varchar(32) NULL DEFAULT NULL COMMENT '修改人' ,
`update_time`  datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间' ,
`remark`  varchar(255) NULL DEFAULT '' COMMENT '备注' ,
`time_seq`  bigint(20) NULL DEFAULT NULL COMMENT '时间序列' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `uk_color_no` (`color_no`) USING BTREE 
)
ENGINE=InnoDB
;

ALTER TABLE `invoice_template_set` ADD COLUMN `company_name`  varchar(250) NULL DEFAULT NULL AFTER `company_no`;

ALTER TABLE `order_dtl` ADD COLUMN `ref_bill_no`  char(25) NULL DEFAULT NULL COMMENT '特卖备货单号' AFTER `order_no`;

ALTER TABLE `return_exchange_dtl` ADD COLUMN `ref_bill_no`  char(25) NULL DEFAULT NULL COMMENT '特卖备货单号' AFTER `business_no`;


CREATE TABLE `period_balance` (
`id`  char(32) NOT NULL COMMENT '主键ID' ,
`company_no`  char(18) NOT NULL COMMENT '公司编码' ,
`order_unit_no`  char(18) NOT NULL COMMENT '订货单位' ,
`store_no`  char(18) NOT NULL COMMENT '机构编码' ,
`brand_no`  char(18) NULL DEFAULT NULL COMMENT '品牌编码' ,
`item_no`  char(18) NOT NULL COMMENT '商品编码' ,
`item_code`  varchar(18) NULL DEFAULT NULL COMMENT '商品编码' ,
`item_name`  varchar(200) NULL DEFAULT NULL COMMENT '商品名称' ,
`category_no`  char(18) NULL DEFAULT NULL COMMENT '大类编码' ,
`year`  char(4) NOT NULL COMMENT '年份' ,
`month`  char(2) NOT NULL COMMENT '月份' ,
`opening_qty`  int(11) NULL DEFAULT 0 COMMENT '期初数量' ,
`opening_balance`  decimal(18,2) NULL DEFAULT 0.00 COMMENT '期初余额' ,
`purchase_in_qty`  int(11) NULL DEFAULT 0 COMMENT '采购入库数量' ,
`purchase_in_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '采购入库金额' ,
`outer_transfer_in_qty`  int(11) NULL DEFAULT 0 COMMENT '跨区调入数量' ,
`outer_transfer_in_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '跨区调入金额' ,
`inner_transfer_in_qty`  int(11) NULL DEFAULT 0 COMMENT '区内调入数量' ,
`inner_transfer_in_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '区内调入金额' ,
`purchase_return_qty`  int(11) NULL DEFAULT 0 COMMENT '采购退厂数量' ,
`purchase_return_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '采购退厂金额' ,
`inv_surplus_qty`  int(11) NULL DEFAULT 0 COMMENT '盘盈数量' ,
`inv_surplus_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '盘盈金额' ,
`others_in_qty`  int(11) NULL DEFAULT 0 COMMENT '其他入库数量' ,
`others_in_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '其他入库金额' ,
`outer_way_qty`  int(11) NULL DEFAULT 0 COMMENT '外入在途数量' ,
`outer_way_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '外入在途金额' ,
`outer_diff_qty`  int(11) NULL DEFAULT 0 COMMENT '外在途差异数量' ,
`outer_diff_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '外在途差异金额' ,
`inner_way_qty`  int(11) NULL DEFAULT 0 COMMENT '区内入在途数量' ,
`inner_diff_qty`  int(11) NULL DEFAULT 0 COMMENT '区内入库差异数量' ,
`cost_adjustment_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '差异调整金额' ,
`sales_out_qty`  int(11) NULL DEFAULT 0 COMMENT '销售出库数量' ,
`sales_out_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '销售出库金额' ,
`inner_transfer_out_qty`  int(11) NULL DEFAULT 0 COMMENT '区内调出数量' ,
`inner_transfer_out_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '区内调出金额' ,
`outer_transfer_out_qty`  int(11) NULL DEFAULT 0 COMMENT '跨区调出数量' ,
`outer_transfer_out_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '跨区跳出金额' ,
`inventory_loss_qty`  int(11) NULL DEFAULT 0 COMMENT '盘亏数量' ,
`inventory_loss_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '盘亏金额' ,
`others_out_qty`  int(11) NULL DEFAULT 0 COMMENT '其他出库数量' ,
`others_out_amount`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '其他出库金额' ,
`during_net_qty`  int(11) NULL DEFAULT 0 COMMENT '期间净发生数量' ,
`during_net_amount`  decimal(18,2) NULL DEFAULT 0.00 COMMENT '期间净发生金额' ,
`closing_qty`  int(11) NULL DEFAULT 0 COMMENT '期末数量' ,
`closing_balance`  decimal(18,2) NULL DEFAULT 0.00 COMMENT '期末余额' ,
`unit_cost`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '单位成本' ,
`region_cost`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '地区成本价' ,
`headquarter_cost`  decimal(12,2) NULL DEFAULT 0.00 COMMENT '总部成本价' ,
`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`create_user`  varchar(32) NULL DEFAULT NULL COMMENT '创建人' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`update_user`  varchar(32) NULL DEFAULT NULL COMMENT '修改人' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `company_no` (`company_no`, `item_no`, `year`, `month`, `order_unit_no`, `store_no`) USING BTREE 
)
ENGINE=InnoDB 
;
ALTER TABLE `item_cost` MODIFY COLUMN `company_no`  char(18) NOT NULL COMMENT '结算公司编号' AFTER `id`;

ALTER TABLE `item_cost` ADD COLUMN `company_name`  varchar(200) NULL DEFAULT NULL COMMENT '结算公司名称' AFTER `company_no`;


CREATE INDEX `idx_send_date` ON `bill_buy_balance`(`send_date`) USING BTREE ;

CREATE INDEX `idx_receive_date` ON `bill_buy_balance`(`receive_date`) USING BTREE ;

CREATE INDEX `idx_send_date` ON `bill_sale_balance`(`send_date`) USING BTREE ;

CREATE INDEX `idx_receive_date` ON `bill_sale_balance`(`receive_date`) USING BTREE ;

ALTER TABLE `shop_brand` ADD COLUMN `status`  tinyint(3) NULL DEFAULT 1 COMMENT '状态(1有效，0无效)' AFTER `time_seq`;

ALTER TABLE `bill_balance_cash_payment` MODIFY COLUMN `bill_no`  char(25) NULL DEFAULT NULL COMMENT '收款单号、付款单号' AFTER `id`;

ALTER TABLE `bill_balance_cash_payment` ADD COLUMN `status`  tinyint(3) NULL DEFAULT NULL COMMENT '平衡标志 1、相等 0、不相等' AFTER `bill_no`;

ALTER TABLE `bill_balance_cash_payment` MODIFY COLUMN `balance_type`  tinyint(3) NULL DEFAULT NULL COMMENT '结算类型' AFTER `status`;

ALTER TABLE `bill_balance_cash_payment` ADD COLUMN `balance_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '应收金额、应付金额(结算金额)' AFTER `balance_no`;

ALTER TABLE `bill_balance_cash_payment` ADD COLUMN `receivable_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '收款金额' AFTER `balance_amount`;

ALTER TABLE `bill_balance_cash_payment` ADD COLUMN `payment_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '付款金额' AFTER `receivable_amount`;

ALTER TABLE `bill_balance_cash_payment` ADD COLUMN `overage_amount`  decimal(20,2) NULL DEFAULT 0.00 COMMENT '余额=结算金额-收/付款金额' AFTER `payment_amount`;

ALTER TABLE `bill_balance_cash_payment` ADD COLUMN `create_user`  varchar(32) NULL DEFAULT NULL COMMENT '创建人' AFTER `bill_date`;

ALTER TABLE `bill_balance_cash_payment` ADD COLUMN `create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `create_user`;

ALTER TABLE `bill_balance_cash_payment` ADD COLUMN `update_user`  varchar(32) NULL DEFAULT NULL COMMENT '修改人' AFTER `create_time`;

ALTER TABLE `bill_balance_cash_payment` ADD COLUMN `update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' AFTER `update_user`;

ALTER TABLE `bill_balance_cash_payment` DROP COLUMN `amount`;

ALTER TABLE `bill_balance_cash_payment` DROP COLUMN `ask_payment_no`;

ALTER TABLE `bill_balance_cash_payment` DROP COLUMN `ask_payment_date`;

ALTER TABLE `bill_balance_cash_payment` DROP COLUMN `auditor`;

notee