/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：BalanceTypeEnums  
 * 类描述：结算类型
 * 创建人：ouyang.zm  
 * 创建时间：2014-9-12 下午12:05:57  
 * @version       
 */
package cn.wonhigh.retail.fas.common.enums;


public enum BalanceTypeEnums {
	HQ_VENDOR(1, "总部厂商结算", "GP", " AND bill_type in (1301,1333)"),
	HQ_WHOLESALE(2, "总部地区结算","GS", " AND ( (bill_type = "+BillTypeEnums.ASN.getRequestId()+" AND biz_type in (60,"+BizTypeEnums.FIRST_ORDER.getStatus()+","+BizTypeEnums.REPLENISH_ORDER.getStatus()+")) OR  bill_type in ("+BillTypeEnums.TRANSFER_OUT.getRequestId()+","+BillTypeEnums.RETURNOWN.getRequestId()+") )"), 
	HQ_OTHER(3, "总部其他结算", "ZQ"," AND( (bill_type = 1335 AND biz_type = '3') OR (bill_type = 1371 AND is_split IS NULL) OR (bill_type = 1361 AND biz_type IN ('7', '25')) )"),  
	AREA_PURCHASE(4, "地区采购结算","DC",""),  
	AREA_AMONG(5, "地区间结算", "DJ"), 
	AREA_BUY(6, "地区自购结算" ,"ZZ"), 
	AREA_WHOLESALE(7, "批发结算","RS","AND bill_type = "+BillTypeEnums.SALEOUTS.getRequestId()+" AND biz_type IN ("+BizTypeEnums.WHOLESALE.getStatus()+","+BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus()+","+BizTypeEnums.WHOLESALE_RETURN.getStatus()+","+BizTypeEnums.CUSTOMER_RETURN.getStatus()+","+BizTypeEnums.WHOLESALE_RECALL.getStatus()+" )"),
	AREA_GROUP_BUY(8, "内购结算", "DT"), 
	AREA_MEMBERS_PURCHASE(9,"地区员购结算", "DY"), 
	AREA_MALL(10, "地区门店结算", "DS"),
	AREA_OTHER(11, "地区其他出库结算", "DQ"),
	ALONE_SHOP(12, "独立店铺结算", "DD"),
	HQ_INSTEADOF_BUY(13, "总部代采购结算", "ZD"," AND ((bill_type = '1301' AND biz_type IN ('0', '1')) OR bill_type = '1333') AND (is_split IS NULL OR is_split != 1)"),
	HQ_OTHER_STOCK_OUT(14, "总部其他出库结算", "ZC"),
	AREA_SALEORDER(23, "GMS团购内购结算", "DO"),
	APPLY_BILL_BLANK(99, "开票申请空白单", "KB"),
	OTHER_DEDUCTION(100, "其他扣项类型","OD",""),
	PE_SUPPLIER(16, "体总厂商结算", "TS"),
	BAROQUE_RECEIPT(17,"巴洛克结算","BG");

	private BalanceTypeEnums(int typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}

	private BalanceTypeEnums(int typeNo, String typeName, String typeCode) {
		this.typeNo = typeNo;
		this.typeName = typeName;
		this.typeCode = typeCode;
	}
	
	private BalanceTypeEnums(int typeNo, String typeName, String typeCode, String queryCondition) {
		this.typeNo = typeNo;
		this.typeName = typeName;
		this.typeCode = typeCode;
		this.queryCondition = queryCondition;
	}
	
	private int typeNo;// 结算编号
	private String typeCode; // 结算代码
	private String typeName; // 结算名称
	private String queryCondition;// 查询条件

	
	
	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition =queryCondition;
	}

	public int getTypeNo() {
		return typeNo;
	}

	public void setTypeNo(int typeNo) {
		this.typeNo = typeNo;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
	public static String getTypeNameByNo(int typeNo){
		for (BalanceTypeEnums balanceType : BalanceTypeEnums.values()) {
			if(balanceType.getTypeNo() == typeNo){
				return balanceType.getTypeName();
			}
		}
		return "";
	}
	
	public static String getTypeCodeByNo(int typeNo){
		for (BalanceTypeEnums balanceType : BalanceTypeEnums.values()) {
			if(balanceType.getTypeNo() == typeNo){
				return balanceType.getTypeCode();
			}
		}
		return "";
	}
	
	public static String getQueryConditionByNo(int typeNo){
		for (BalanceTypeEnums balanceType : BalanceTypeEnums.values()) {
			if(balanceType.getTypeNo() == typeNo){
				return balanceType.getQueryCondition();
			}
		}
		return "";
	}
	
	
}
