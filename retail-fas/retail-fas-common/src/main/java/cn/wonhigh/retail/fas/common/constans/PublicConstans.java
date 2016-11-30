package cn.wonhigh.retail.fas.common.constans;

import java.math.BigDecimal;

/**
 * 常量类
 * @author wei.hj
 * @date 2013-7-10
 * @version 0.1.0
 * @copyright yougou.com
 *
 */
public class PublicConstans {

	//数据状态
	public static final Long DATA_STATUS_0 = 0L; //有效
	public static final Long DATA_STATUS_1 = 1L; //无效

	//承运商类型
	public static final String SUPPLIER_TYPE_0 = "0"; //全国
	public static final String SUPPLIER_TYPE_1 = "1"; //省级
	public static final String SUPPLIER_TYPE_2 = "2"; //地级
	public static final String SUPPLIER_TYPE_3 = "3";//国际

	//重点标识
	public static final String PRIMARY_FLAG_0 = "0"; //普通
	public static final String PRIMARY_FLAG_1 = "1"; //重要
	public static final String PRIMARY_FLAG_2 = "2"; //一般

	//配送点的类别 门店还是仓库
	public static Short STORE_TYPE_22 = 22; //配送中心

	//0-待执行 10-已配单  40-配送中 60-等待回单 65-回单处理中 70-已回单完成 90-已完结 默认为0
	public static final int TRUCK_STATUS_0 = 0;
	public static final int TRUCK_STATUS_10 = 10;
	public static final int TRUCK_STATUS_40 = 40;
	public static final int TRUCK_STATUS_60 = 60;
	public static final int TRUCK_STATUS_65 = 65;
	public static final int TRUCK_STATUS_70 = 70;
	public static final int TRUCK_STATUS_90 = 90;

	//托运单的状态
	//0-待处理  10-已审核 11-审核不通过 12-取消托运  13-拆单配送   20-已派单  21待装车 22装车中  23在途 30-签收 31-拒签 32-部分签收  90-已完结   默认为0

	public static final int CURRENT_BILLSTATUS_0 = 0;
	public static final int CURRENT_BILLSTATUS_10 = 10;
	public static final int CURRENT_BILLSTATUS_11 = 11;
	public static final int CURRENT_BILLSTATUS_12 = 12;
	public static final int CURRENT_BILLSTATUS_13 = 13;

	public static final int CURRENT_BILLSTATUS_20 = 20;
	public static final int CURRENT_BILLSTATUS_21 = 21;
	public static final int CURRENT_BILLSTATUS_22 = 22;
	public static final int CURRENT_BILLSTATUS_23 = 23;

	public static final int CURRENT_BILLSTATUS_30 = 30;
	public static final int CURRENT_BILLSTATUS_31 = 31;
	public static final int CURRENT_BILLSTATUS_32 = 32;

	public static final int CURRENT_BILLSTATUS_90 = 90;

	//托运单业务类型
	//	1318-配送单 1317-店间调拨单 1308-店退仓 9000-其他单  1326-店退仓申请单 */
	public static final int CURRENT_BUSINESS_TYPE_1318 = 1318;
	public static final int CURRENT_BUSINESS_TYPE_1317 = 1317;
	public static final int CURRENT_BUSINESS_TYPE_1308 = 1308;
	public static final int CURRENT_BUSINESS_TYPE_1326 = 1326;
	public static final int CURRENT_BUSINESS_TYPE_9000 = 9000;

	//拆单标识 0-否 1-已拆单 默认为0
	public static final int OPEN_FLAG_0 = 0;
	public static final int OPEN_FLAG_1 = 1;

	//插单标识 0-否 1-插单 默认为0
	public static final int INSERT_FLAG_0 = 0;
	public static final int INSERT_FLAG_1 = 1;

	//补单标识 0-否 1-补单 默认为0
	public static final int APPEND_FLAG_0 = 0;
	public static final int APPEND_FLAG_1 = 1;

	//是否在派车单中已经存在已签收的托运单
	public static final String IS_SIGN_FLAG_1 = "1";

	//回单标识 0-未回单 1-已回单 默认0
	public static final short CONFIRM_FLAG_0 = 0;
	public static final short CONFIRM_FLAG_1 = 1;

	public static final String OPERATEFLAG_SAVE = "save"; // 保存类型
	public static final String OPERATEFLAG_EDIT = "edit"; // 编辑类型

	public static final int WIDTH_80 = 80;
	public static final int WIDTH_SIZETYPE_50 = 50;

	//配送单的传输标识0-未传输  1-已传输  默认为0
	public static final int ACCFLAG_0 = 0;
	public static final int ACCFLAG_1 = 1;

	//订单非空默认值
	public static final int STATUS_TRANS = 0; //传输状态
	public static final String GRADE_NO = "0"; //等级定义
	public static final String PROPERTY_NO = "0"; //属性定义
	public static final String STORE_NO2 = "-"; //结算地
	public static final String CONTRACT_NO = "0"; //合同号
	public static final int PAYMENT_ID = 0; //结算方式
	public static final int LOGISTICS = 1; //物流模式1=直送 2=直通
	public static final Double TAXRATE = 17D; //税率
	public static final String CAR_NO = "0"; //车牌号

	public static final int PRINTCOUNT = 0; //打印次数
	public static final int ACCFLAG = 0;
	public static final String STORE_NO = "-"; //收货方
	public static final String STORE_NO_TRANSIT = "-"; //中转地
	public static final int PACK_QTY = 0; //包装数量
	public static final int ORDER_QTY = 0; //订货数量
	public static final int SEND_QTY = 0; //发货数量
	public static final int RECEIPT_QTY = 0; //收货数量
	public static final String BOX_NO = "''"; //箱号
	public static final String REFBILL_NO = "-"; //订货单号

	public static final String BATCH_NO = "-"; //批次号
	public static final String BATCH_NO_GIFT = "-"; //赠品批次号

	public static final int MEMU_ROOT_NODE_ID = 1; //

	public static final String SESSION_USER = "session_user";

	public static final String SESSION_SYSTEMID = "systemid";
	public static final String SESSION_AREASYSTEMID = "areasystemid";
	public static final String APPKEY = "cb8f51cc16f1cd6b6d441aed74e5317a";

	/**
	 * 登录用户Cookie的ID值
	 */
	public static final String LOGIN_SYSTEM_USER_COOKIE_ID = "login_system_user_cookie_id_tms";

	public static final int LOGIN_COOKIE_TIME = 7 * 24 * 60 * 60;

	//1-手机签收  2-PC机签收 3-手签 4-代签
	public static final Short SIGN_TYPE_1 = 1;
	public static final Short SIGN_TYPE_2 = 2;
	public static final Short SIGN_TYPE_3 = 3;
	public static final Short SIGN_TYPE_4 = 4;

	//回单信息
	public static String TMS_BILL_TRUCK_WAITTING_STR = "派车计划单等待回单";
	public static String RECEIPT_PROCESS_STR = "回单处理中";
	public static String RECEIPT_FINISHED_STR = "回单处理已完成";
	public static String TMS_BILL_TRUCK_FINISHED_STR = "派车计划单已完结";

	//托运单号前缀
	public static final String BOOKING_NOTE_PRE = "BN";

	//配送单前缀
	public static final String VEHICLE_DISPATCH_PRE = "VD";

	//派车计划编号前缀
	public static final String BILL_OF_DISTRIBUTION_PRE = "BD";

	//店退仓单
	public static final String BILL_TRANS_SHOP_QUIT_STORAGE = "1A";

	//店间调拨
	public static final String BILL_TRANS_SHOP_TRANSFER_SHOP = "1B";

	//配送单
	public static final String BILL_TRANS_DISTRIBUTION = "1C";

	//其它
	public static final String BILL_TRANS_OTHER = "1D";

	//已回单标识
	//public static final String CONFIRM_FLAG = "1";

	//托运单新建是否自动审核  0--否  1--自动审核  默认为0
	public static final String TRANSPORT_IS_AUTO_ADUIT = "transportIsAutoAduit";

	public static final String TRANSPORT_IS_AUTO_ADUIT_VALUE_0 = "0";

	public static final String TRANSPORT_IS_AUTO_ADUIT_VALUE_1 = "1";

	// 派车计划创建是否自动审核  0--否  1--自动审核  默认为0
	public static final String TRUCK_IS_AUTO_ADUIT = "truckIsAutoAduit";

	public static final String TRUCK_IS_AUTO_ADUIT_VALUE_0 = "0";

	public static final String TRUCK_IS_AUTO_ADUIT_VALUE_1 = "1";

	//托运单是否在装车完成后才能签收 0--否(不做限制) 1--是
	public static final String IS_SIGN_ON_TRUCK_COMPLETED = "isSignOnTruckCompleted";
	public static final String IS_SIGN_ON_TRUCK_COMPLETED_0 = "0";
	public static final String IS_SIGN_ON_TRUCK_COMPLETED_1 = "1";

	//调度中心编码
	public static final short STORE_TYPE_QUARTZ = 50;

	//配送点机构类型
	public static final short STORE_TYPE_TRANPORTPOINT = 51;

	public static final String DEFAULT_PASSWORD = "111111";

	//1:普通管理员  2: 超级管理员
	public static final String CATEGROY_1 = "1";
	public static final String CATEGROY_2 = "2";

	//默认角色代码   管理员-23000  调度员-23001 司机-23002 配送点(门店)用户-23003
	public static final int UC_ROLE_23000 = 23000;
	public static final int UC_ROLE_23001 = 23001;
	public static final int UC_ROLE_23002 = 23002;
	public static final int UC_ROLE_23003 = 23003;

	//tms项目名，供接口交互异常使用
	public static final String PROJECT_NAME = "tms";

	/**
	 * 托运单-单据来源： 0-接口同步    1-手工创建  CREATE_BILL_TYPE
	 */
	public static final Integer CREATE_BILL_TYPE_0 = 0;
	public static final Integer CREATE_BILL_TYPE_1 = 1;

	//实现业务过程中，如果是系统自动触发的情况，需写处理人、或者创建人时
	public static final String DEFAULT_SYSTEM_USER_ID = "tms_sys";
	public static final String DEFAULT_SYSTEM_USER_NAME = "系统";

	// 1-短信发送2-邮件发送3-其他
	public static final int SEND_MODE_1 = 1;
	public static final int SEND_MODE_2 = 2;
	public static final int SEND_MODE_3 = 3;

	public static final String MSG_TEMPLATE_PATH = "/WEB-INF/ftl/pages/msg_template/";

	public static final String MAIL_SUBJECT = "tms(城市配送)系统邮件提醒服务";

	//签收信息
	public static String CURRENT_BILLSTATUS_PART_SIGN = "部分签收";
	public static String CURRENT_BILLSTATUS_SIGN_STR = "签收";
	public static String CURRENT_BILLSTATUS_REJECT_SIGN = "拒签";

	//发送Eemail类型
	public static final int SEND_EMAIL_TYPE_1 = 1; //派车计划车审核完成
	public static final int SEND_EMAIL_TYPE_2 = 2; //配送单扫描更新
	public static final int SEND_EMAIL_TYPE_3 = 3; //派车计划车装车完成
	public static final int SEND_EMAIL_TYPE_4 = 4;//托运单签收

	//1-手机补单  2-PC机补单
	public static final String APPEND_TYPE_1 = "1";
	public static final String APPEND_TYPE_2 = "2";

	//1 签收，0拒签
	public static final String SIGN_ACIOTN_TYPE_0 = "0";
	public static final String SIGN_ACIOTN_TYPE_1 = "1";

	//0-发送成功  1-待发送 2-发送失败
	public static final short SEND_STATUS_0 = 0;
	public static final short SEND_STATUS_1 = 1;
	public static final short SEND_STATUS_2 = 2;

	//发送短信时要调用的发送短信接口的webserice要调用的方法
	public static final String SEND_SMS_MSG_METHOD = "InsertDownSms";
	//发送短信时的调用webserice时要调用的用户名和密码加密用的的key
	public static final String SEND_SMS_MSG_KEY = "chinagdn";

	//发送邮件成功标识
	public static final String SEND_EMAIL_RESULT = "success";

	//发送信息类型
	public static final String SEND_MSG_INFO_TYPE_EMIAL = "email";
	public static final String SEND_MSG_INFO_TYPE_SMS = "sms";

	//0 返回的code 0表示发送成功 不为0表示发送失败
	public static final String SEND_SMS_STATUS_SUCCESS = "0";
	public static final String SEND_SMS_STATUS_FAIL = "-1";

	//0 返回的XML文件中手机状态
	public static final String SEND_SMS_PHONE_STATUS = "0";

	//是否需要发送邮件    0--需要    1--不需要
	public static final String IS_NEED_SEND_EMAIL = "isNeedSendEmail";
	public static final String IS_NEED_SEND_EMAIL_0 = "0";
	public static final String IS_NEED_SEND_EMAIL_1 = "1";
	//邮件发送方
	public static final String SENDER_NO = "TMS.admin@belle.com.cn";

	//是否需要发送短信    0--需要    1--不需要
	public static final String IS_NEED_SEND_SMS = "isNeedSendSms";
	public static final String IS_NEED_SEND_SMS_0 = "0";
	public static final String IS_NEED_SEND_SMS_1 = "1";

	//0-未传输  1-已传输  默认为0
	public static final Long ACC_LMP_FLAG_0 = 0L;
	public static final Long ACC_LMP_FLAG_1 = 1L;

	public static final String SEND_EMAIL_SMS_STATUS_SUCCESS = "0";

	public static final String SIGN_TRANSPORT_POINT_PASSWORD = "888888";

	public static final String PRINT_TOOLS_PATH = "resources/printTools/printTools.zip";

	public static final String DEFAULT_CURRENCY = "人民币"; // 默认币别 人名币
	
	public static final String ENCODE = "utf-8";
	
	public static final String DOWNLOAD_DIR = "/resources/download";
	
	public static final Integer AREA_SYSTEM_ID = 15;
	
	/**
	 * ALL THE PAYCODE
	 * 1:现金 3:现金券 4:刷卡 5:商场卡 6:其他 8:商场券 9预收款
	 */
	public static final String PAYCODE_CASHAMOUNT = "01";
	public static final String PAYCODE_CASHCOUPONAMOUNT = "03";
	public static final String PAYCODE_CREDITCARDAMOUNT = "04";
	public static final String PAYCODE_MALLCARDAMOUNT = "05";
	public static final String PAYCODE_OTHERAMOUNT = "06";
	public static final String PAYCODE_MALLCOUPONAMOUNT = "08";
	public static final String PAYCODE_ADVANCEPAY = "09";
	
	
	//销售订单状态
		public static final String STANDARD_ORDER_STATUS = "ORDER_STATUS";
		//销售订单类型
		public static final String STANDARD_ORDER_TYPE = "ORDER_TYPE";
		
		public static final String STANDARD_ORDER_DELIVERY_STATUS = "ORDER_DELIVERY_STATUS";
		
		public static final String STANDARD_BUSINESS_TYPE = "BUSINESS_TYPE";
		
		public static final String STANDARD_BUSINESS_TYPE_EXPECT = "BUSINESS_TYPE_EXPECT";
		
		public static final String STANDARD_BUSINESS_FLAG = "BUSINESS_FLAG";
		
		public static final String STANDARD_CURRENCY_TYPE = "CURRENCY_TYPE";
		
		public static final String STANDARD_CONFIRM_FLAG = "CONFIRM_FLAG";
		
		public static final String STANDARD_CHECK_FLAG= "CHECK_FLAG";
		
		public static final String STANDARD_INGREDIENTS = "INGREDIENTS";
		
		public static final String STANDARD_LINING = "LINING";
		
		
		//现金存入单据号编码
		public static final String DEPOSIT_CASH = "DEPOSIT_CASH";
		//预收款单单据号编码
		public static final String EXPECT_CASH = "EXPECT_CASH";
		
		/***************************** 预收款业务类型 1-商场预收 2-定金预收****************************/
		public static final Short EXPECT_CASH_MALL = 1;
		public static final Short EXPECT_CASH_BOOK = 2;
		
		/***************************** 预收款业务标识  1-收款 2-退款****************************/
		public static final Short EXPECT_CASH_RECEIPT = 1;
		public static final Short EXPECT_CASH_REFUND = 2;
		
		/***************************** 是否与商场对账  0-未对账 1-已对账****************************/
		public static final Short CHECK_ACCOUNT_FALSE = 0;
		public static final Short CHECK_ACCOUNT_TRUE = 1;
		
		/***************************** 数据是否确认 0-未确认 1-确认  ****************************/
		public static final Short DATA_UNCONFIRM = 0;
		public static final Short DATA_CONFIRM = 1;
		public static final Short FIN_CONFIRM = 2;
		
		/***************************** 币种  0-人民币 ****************************/
		public static final Short CURRENCY_TYPE_RMB = 0;
		
		//成功标识符
		public static final String COMMON_SUCCESS = "success";
		//失败标识符
		public static final String COMMON_FAIL = "fail";
		
		public static final String COMMON_TRUE = "true";
		
		public static final String COMMON_FALSE = "false";
	
		/***************************** 时间日期格式  *************************************/
		public static final String DATE_TIME_FORMATER_19 = "yyyy-MM-dd HH:mm:ss";
		public static final String DATE_TIME_FORMATER_10 = "yyyy-MM-dd";
		
		/***************************** 数据状态 0-有效 1-无效   2-作废****************************/
		public static final Short DATA_STATUS_VALID = 0;
		public static final Short DATA_STATUS_INVALID = 1;
		public static final Short DATA_STATUS_CANCEL = 2;
		
		/**************************** 支付类型 1-虚拟支付*****************************************/
		public static final Short PAY_TYPE_VIRTUAL = 1;
		
		/***************************** 金额初始值 ****************************/
		public static final BigDecimal BIGDECIMAL_ZERO = new BigDecimal("0.00");
}
		
