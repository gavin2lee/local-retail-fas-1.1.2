package cn.wonhigh.retail.fas.common.constans;


/**
 * 常量类
 * @author wei.hj
 * @date 2013-7-10
 * @version 0.1.0
 * @copyright yougou.com
 *
 */
public class POSPublicConstans {
	
	
	public static final String COMMON_ZERO = "0";
	
	public static final String COMMON_ONE = "1";

	public static final String COMMON_HANDUP = "handUp";
	
	public static final String COMMON_TRUE = "true";
	
	public static final String COMMON_FALSE = "false";
	
	//成功标识符
	public static final String COMMON_SUCCESS = "success";
	
	//失败标识符
	public static final String COMMON_FAIL = "fail";
	
	public static final String SYSTEM_CODE_KEY = "OC";
	
	public static final String SEPARATOR_COMMA_CH = "，";
	
	public static final String SEPARATOR_COMMA_EN = ",";
	
	public static final String SEPARATOR_VERTICAL = "\\|";
	
	public static final String SEPARATOR_UNDER_LINE = "_";
	
	
	/*****************************字典常量****************************/
	//自定义销售类型
	public static final String STANDARD_ORDER_BILL_TYPE = "ORDER_BILL_TYPE";
	//销售业务类型
	public static final String STANDARD_BUSINESS_TYPE = "BUSINESS_TYPE";
	//改单申请状态
	public static final String STANDARD_ADJUST_STATUS = "ADJUST_STATUS";
	//改单申请类型
	public static final String STANDARD_ADJUST_BILL_TYPE = "ADJUST_BILL_TYPE";
	//销售订单状态
	public static final String STANDARD_ORDER_STATUS = "ORDER_STATUS";
	//是否日结
	public static final String STANDARD_DAILYFLAG_TYPE = "DAILYFLAG_TYPE";
	//是否已改单
	public static final String STANDARD_ADJUST_FLAG = "ADJUST_FLAG";
	//订单来源
	public static final String STANDARD_ORDER_SOURCE = "ORDER_SOURCE";
	//销售订单类型
	public static final String STANDARD_ORDER_TYPE = "ORDER_TYPE";
	//提货方式
	public static final String STANDARD_TAKE_MODE = "TAKE_MODE";	
	
	//现金存入记录查询排序的字段  deposit_date
 	public static final String ORDER_BY_FIELD_DEPOSIT_DATE = "deposit_date";

	/***************************** 时间日期格式  *************************************/
	public static final String DATE_TIME_FORMATER_19 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_FORMATER_10 = "yyyy-MM-dd";
	
	/***************************** 数据是否确认 0-未确认 1-确认  ****************************/
	public static final Short DATA_UNCONFIRM = 0;
	public static final Short DATA_CONFIRM = 1;
	
	/***************************** 退换货类型,1-换货  2-退货  ***************************/
	public static final Short BUSINESS_MODE_EXCHANGE = 1;
	
	public static final Short BUSINESS_MODE_RETURN = 2;
	
	/***************************** 0-正常销售订单 1-换货产生的销售订单  ***************/
	public static final Short ORDER_MAIN_NORMAL = 0;
	
	public static final Short NORMAL_ORDER_EXCHANGE = 1;
	
	
	/***************************** 销售订单/退换货状态类型,0-已创建 10-已挂起 11-已取消 20-已审核 30-已收银未发货 41-已收银已发货 99-已完结****************************/
	public static final Short ORDER_STATUS_NEW = 0;
	public static final Short ORDER_STATUS_SUSPNED = 10;
	public static final Short ORDER_STATUS_CANCEL = 11;
	public static final Short ORDER_STATUS_AUDIT = 20;
	public static final Short ORDER_STATUS_CASHIER = 30;
	public static final Short ORDER_STATUS_SEND = 41;
	public static final Short ORDER_STATUS_FINISHED = 99;
	
	public static final String ORDER_STATUS_NEW_STR = "NEW";
	public static final String ORDER_STATUS_SUSPNED_STR = "SUSPNED";
	public static final String ORDER_STATUS_CANCEL_STR = "CANCEL";
	public static final String ORDER_STATUS_AUDIT_STR = "AUDIT";
	public static final String ORDER_STATUS_CASHIER_STR = "CASHIER";
	public static final String ORDER_STATUS_SENDWAIT_STR = "SENDWAIT";
	public static final String ORDER_STATUS_SEND_STR = "SEND";
	public static final String ORDER_STATUS_REACH_STR = "REACH";
	public static final String ORDER_STATUS_FINISHED_STR = "FINISHED";
	
	/***************************** 销售订单是否锁单 0-未锁单 1-已锁单 ***************/
	public static final Short ORDER_MAIN_UNLOCK = 0;
	public static final Short ORDER_MAIN_LOCK = 1;
	
	/***************************** 支付方式 ****************************/
	//预收款
	public static final String PAYWAY_EXPECT_CASH = "09";
	
	/***************************** 预收款业务标识  1-收款 2-退款****************************/
	public static final Short EXPECT_CASH_RECEIPT = 1;
	public static final Short EXPECT_CASH_REFUND = 2;
	
	/***************************** 订单业务类型,0-正常销售 1-跨店销售 2-商场团购 3-公司团购 4-员购 5-专柜团购 9-其他 默认为0****************************/
	public static final Short ORDER_BUSINESS_TYPE_NORMAL = 0;
	public static final Short ORDER_BUSINESS_TYPE_OVERSHOP = 1;
	public static final Short ORDER_BUSINESS_TYPE_MALL = 2;
	public static final Short ORDER_BUSINESS_TYPE_COMPANY = 3;
	public static final Short ORDER_BUSINESS_TYPE_ASSISTANT= 4;
	public static final Short ORDER_BUSINESS_TYPE_COUNTERS= 5;
	public static final Short ORDER_BUSINESS_TYPE_OTHER = 9;
	
	/***************************** 自提方式 0-本店自提 1-跨店快递 2-跨店自提****************************/
	public static final String ORDER_TAKE_MODE_LOCAL_EXTRACT = "0";
	public static final String ORDER_TAKE_MODE_DELIVER = "1";
	public static final String ORDER_TAKE_MODE_OVER_EXTRACT = "2";
	
	/***************************** 提货验证方式 0-本店自提 1-跨店快递 2-跨店自提****************************/
	public static final String ORDER_VALIDATEWAY_TICKET = "ticket";
	public static final String ORDER_VALIDATEWAY_MESSAGE = "message";
	public static final String ORDER_VALIDATEWAY_EXPRESS = "express";
	
	/***************************** 订单预占业务类型,0-本店 1-跨店 ****************************/
	public static final String BUSINESS_TYPE_NORMAL = "1";
	public static final String BUSINESS_TYPE_OVERSHOP = "2";
	
	/***************************** 日结标识,0-未日结 1-已日结 默认为0****************************/
	public static final Short ORDER_DAILYFLAG_FALSE = 0;
	public static final Short ORDER_DAILYFLAG_TRUE = 1;
	
	/***************************** 改单标识 0-未改单，1-日结前改单 2-日结后改单****************************/
	public static final Integer ADJUST_FLAG_FALSE = 0;
	public static final Integer ADJUST_FLAG_BEFORE = 1;
	public static final Integer ADJUST_FLAG_AFTER = 2;
	
	/***************************** 页面改单标识 0-正常 1-日结前改单 2-日结后改单(改单) 3-日结后改单(补单)****************************/
	public static final Integer PAGE_ADJUST_FLAG_NORMALE = 0;
	public static final Integer PAGE_ADJUST_FLAG_BEFORE = 1;
	public static final Integer PAGE_ADJUST_FLAG_AFTER_ADJUST = 2;
	public static final Integer PAGE_ADJUST_FLAG_AFTER_ADD = 3;
	
	/***************************** 日结后改单操作类型 ****************************/
	public static final String ADJUST_ORDER_OPERATETYPE_ADD = "add";
	public static final String ADJUST_ORDER_OPERATETYPE_MODIFY = "modify";
	
	/***************************** 是否可二次销售****************************/
	//可二次销售
	public static final Integer AFFECT_FLAG_TRUE= 0;
	//不可二次销售
	public static final Integer AFFECT_FLAG_FLASE = 1;
	
	/***************************** 改单申请单状态,0-制单 10-作废 20-待审核 30-退回 40-审核通过 50-审核不通过****************************/
	public static final Integer ADJUST_STATUS_NEW= 0;
	public static final Integer ADJUST_STATUS_CANCEL = 10;
	public static final Integer ADJUST_STATUS_WAIT = 20;
	public static final Integer ADJUST_STATUS_REJECT = 30;
	public static final Integer ADJUST_STATUS_APPROVE = 40;
	public static final Integer ADJUST_STATUS_UNAPPROVE = 50;
	
	/***************************** 改单申请单类型,0-改单 1-补单 2-删单****************************/
	public static final Integer ADJUST_BILL_TYPE_ADJUST= 0;
	public static final Integer ADJUST_BILL_TYPE_ADD = 1;
	public static final Integer ADJUST_BILL_TYPE_DELETE = 2;
	
	/***************************** 订单类型,0-销售 1-换货 2-退货****************************/
	public static final Integer ADJUST_ORDER_TYPE_NORMAL= 0;
	public static final Integer ADJUST_ORDER_TYPE_EXCHANGE = 1;
	public static final Integer ADJUST_ORDER_TYPE_RETURN = 2;

	/***************************** 改单的订单标识，0-改单前，1-改单后****************************/
	public static final Integer ADJUST_ORDER_TYPE_BEFORE = 0;
	public static final Integer ADJUST_ORDER_TYPE_AFTER = 1;
	
	/***************************** 券业务范围0-通用 1-常规销售 2-内购销售****************************/
	public static final Short TICKET_APPLYTYPE_GENERAL = 0;
	public static final Short TICKET_APPLYTYPE_NORMAL = 1;
	public static final Short TICKET_APPLYTYPE_GROUPON = 2;
	
	/***************************** 类型,1-赠券 2-使用券 **************************/
	public static final Integer TICKET_FLAG_GIVE = 1;
	public static final Integer TICKET_TYPE_USE = 2;
	
	/***************************** 销售订单完成标识 0-完成 1-未完成(挂起) 2-未完成(其他状态)****************************/
	public static final String ORDER_FINISHED = "0";
	public static final String ORDER_UNFINISHED_SUSPNED = "1";
	
	/***************************** 单据类型 ******************************/
	//单据类型- 销售
	public static final String BILLTYPE_SALE = "1375";
	
	// 单据类型-店转货
	public static final int BILLTYPE_TRANSFER = 1317;
	
	//接调口时必填移过项
	public static final String PROJECT_NAME = "oc";
	public static final String COMMON_ERROR_MSG = "参数校验失败,必填项不能为空";
	
	//分页排序
 	public static final String ORDER_BY_DESC = "desc";
 	 
	//销售列表接口中销售时间
 	public static final String ORDER_BY_FIELD_OUT_DATE = "out_date";
 	
 	//销售列表接口中销售时间
 	public static final String ORDER_BY_FIELD_SHOP_TERMINAL_NO = "shop_terminal_no";
 	
 	/***************************** 支付方式代号,01-现金 02-IC卡 03-现金券 04-银行卡 05-商场卡 06-其他 07-公司折扣券 08-商场券 09预收款****************************/
	public static final String PAYCODE_CASH = "01";
	public static final String PAYCODE_IC = "02";
	public static final String PAYCODE_CASH_TICKET = "03";
	public static final String PAYCODE_BANK_CARD = "04";
	public static final String PAYCODE_MALL = "05";
	public static final String PAYCODE_OTHER = "06";
	public static final String PAYCODE_COMPANY = "07";
	public static final String PAYCODE_MALL_TICKET = "08";
	public static final String PAYCODE_EXPECT_CASH = "09";
	
	/***************************** 店转货单状态  0=制单 1-出货确定 2-收货待确定 3-收货确定 99-作废 100-完结  **************************/
	//  0=制单
	public static final int BILL_STATUS_NEW = 0;
	//  1-出货确定
	public static final int BILL_STATUS_SHIPMENT = 1;
	// 2-收货确定
	public static final int BILL_STATUS_RECEIVED = 2;
	
	//  99-作废
	public static final int BILL_STATUS_CANCEL = 99;	
	// 100-完结 
	public static final int BILL_STATUS_FINISH = 100;	
	
	/***************************** 券类型,1-赠券 2-使用券 ****************************/
	public static final Short TICKET_FLAG_GIVING = 1;
	public static final Short TICKET_FLAG_USE = 2;
	
	//支付方式代号 04表示银行卡支付
    public static final int PAY_CODE_BRANK_BARD = 04;
    
    /***************************** 调转货单创建类型****************************/
	public static final Integer BILL_TRANSFER_MANUAL = 0;
	public static final Integer BILL_TRANSFER_OVERSHOP = 1;
	
	/***************************** 物流模式类型 物流模式,1=快递 2=中转 3=自提****************************/
 	public static final Integer LOGISTICS_MODE_DELIVER= 1;
 	public static final Integer LOGISTICS_MODE_TRANSIT = 2;
 	public static final Integer LOGISTICS_MODE_LOCAL = 3;
 	
	/***************************** 调货类型 0：正常调货，1过季调货 2原残调货  ***************/
	public static final Integer BILL_TRANSFER_BIZ_TYPE_NORMAL = 0;
	public static final Integer BILL_TRANSFER_BIZ_TYPE_OVERDUE = 1;
	public static final Integer BILL_TRANSFER_BIZ_TYPE_INCOMPLETE = 2;
 	
 	/***************************** 短信验证码位数*************************s***/
 	public static final Integer MESSAGE_CODE_DIGITS = 6;
 	
 	/***************************** 销售出库单状态,0-制单 1-出库确定 99-作废****************************/
	public static final Short ORDER_DELIVERY_STATUS_NEW = 0;
	public static final Short ORDER_DELIVERY_STATUS_SHIPMENT = 1;
	public static final Short ORDER_DELIVERY_STATUS_CANCEL = 99;
	
	/***************************** 销售出库单类型,销售出库单-1335，退货入库单-1374****************************/
	public static final Integer SALE_OUT_STORAGE_OUT = 1335;
	public static final Integer SALE_OUT_STORAGE_IN = 1374;
	
	/***************************** 销售类型,0-正常 1-换货 2-退货  默认为0****************************/
	public static final Short ORDER_TYPE_NORMAL = 0;
	public static final Short ORDER_TYPE_EXCHANGE = 1;
	public static final Short ORDER_TYPE_RETURN = 2;
	
	/***************************** 跨店开单产生店转货单备注****************************/
	public static final String BILL_TRANSFER_SENDOUT_REMARK = "跨店开单调货生成";

	/*****************************现金存入记录查询参数字段 **************************/
	public static final String COMMON_STATUS  = "status";
 	public static final String COMMON_SHOPLIST = "shopNoList";
	
	//查出接口统一出错code
 	public static final int COMMON_QUERY_ERROR = 100010;
 	
 	public static final String ORDER_BY_FIELD_TIME_SEQ = "time_seq";

}