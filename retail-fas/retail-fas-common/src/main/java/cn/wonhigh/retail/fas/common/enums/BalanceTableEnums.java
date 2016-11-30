/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：BalanceTableEnums  
 * 类描述：结算类型表名对应
 * 创建人：ouyang.zm  
 * 创建时间：2014-9-12 下午12:05:57  
 * @version       
 */
package cn.wonhigh.retail.fas.common.enums;

public enum BalanceTableEnums {
	HQ_VENDOR(1, "总部厂商结算", "bill_hq_supplier",""),
	HQ_WHOLESALE(2, "总部地区结算", "bill_hq_area","bill_hq_area_receive"), 
	HQ_INSTEADOF_BUY(13, "总部代采结算", "bill_hq_instead","bill_hq_instead_receive"), 
	AREA_AMONG(5, "地区间结算", "bill_area_among","bill_area_among_receive"),
	AREA_BUY(6, "地区自购结算" ,"bill_area_buy","bill_area_buy_receive"),
	AREA_WHOLESALE(7, "地区批发结算","bill_area_wholesale",""),
	AREA_OTHER(11, "地区其他出库结算", "bill_area_other",""),
	AREA_SALEORDER(23, "内购结算", "bill_area_saleorder",""),
	NO_BALANCE(101, "未结算", "bill_no_balance","bill_no_balance_receive");
	
	private BalanceTableEnums(int typeNo, String typeName, String sendTableName, String receiveTableName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
		this.sendTableName = sendTableName;
		this.receiveTableName = receiveTableName;
	}

	public int getTypeNo() {
		return typeNo;
	}
	public void setTypeNo(int typeNo) {
		this.typeNo = typeNo;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSendTableName() {
		return sendTableName;
	}

	public void setSendTableName(String sendTableName) {
		this.sendTableName = sendTableName;
	}

	public String getReceiveTableName() {
		return receiveTableName;
	}

	public void setReceiveTableName(String receiveTableName) {
		this.receiveTableName = receiveTableName;
	}



	private int typeNo;// 结算编号
	private String typeName; // 结算名称
	private String sendTableName;// 出库表名
	private String receiveTableName;// 入库表名
	
	public static String getTypeNameByNo(int typeNo){
		for (BalanceTableEnums balanceType : BalanceTableEnums.values()) {
			if(balanceType.getTypeNo() == typeNo){
				return balanceType.getTypeName();
			}
		}
		return "";
	}
	
	public static String getSendTableByNo(int typeNo){
		for (BalanceTableEnums balanceType : BalanceTableEnums.values()) {
			if(balanceType.getTypeNo() == typeNo){
				return balanceType.getSendTableName();
			}
		}
		return "";
	}
	
	public static String getReceiveTableByNo(int typeNo){
		for (BalanceTableEnums balanceType : BalanceTableEnums.values()) {
			if(balanceType.getTypeNo() == typeNo){
				return balanceType.getReceiveTableName();
			}
		}
		return "";
	}
	
}
