/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：BalanceTypeEnums  
 * 类描述：结算状态
 * 创建人：ouyang.zm  
 * 创建时间：2014-9-12 下午12:05:57  
 * @version       
 */
package cn.wonhigh.retail.fas.common.enums;

public enum BillBalanceZoneStatusEnums {
	NO_CONFIRM(0, "制单", "制单"), 
	RECEIVE_BUSSINESS_CONFIRM(3, "业务确认", "业务确认"), 
	RECEIVE_FINANCE_CONFIRM(4, "财务确认", "财务确认"),
	ASK_PAYMENT(5, "已请款"), 
	MAKE_INVOICE(6, "已开票申请"), 
	MAKE_APPLY_INVOICE(7, "已开票"), 
	INVALID(99, "打回", "打回");

	private BillBalanceZoneStatusEnums(int typeNo, String typeName) {
		this.typeNo = typeNo;
		TypeName = typeName;
	}

	private BillBalanceZoneStatusEnums(int typeNo, String typeName, String operateName) {
		this.typeNo = typeNo;
		this.TypeName = typeName;
		this.operateName = operateName;
	}

	
	private int typeNo;
	
	private String TypeName;

	/**
	 * 操作名称
	 */
	private String operateName;
	
	public int getTypeNo() {
		return typeNo;
	}

	public void setTypeNo(int typeNo) {
		this.typeNo = typeNo;
	}

	public String getTypeName() {
		return TypeName;
	}

	public void setTypeName(String typeName) {
		TypeName = typeName;
	}
	
	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public static String getTypeNameByNo(int typeNo){
		BillBalanceZoneStatusEnums enums[] = BillBalanceZoneStatusEnums.values();
		for (BillBalanceZoneStatusEnums status : enums) {
			if(status.getTypeNo() == typeNo){
				return status.getTypeName();
			}
		}
		return "";
	}

	public static String getOperateNameByNo(int typeNo){
		BillBalanceZoneStatusEnums enums[] = BillBalanceZoneStatusEnums.values();
		for (BillBalanceZoneStatusEnums status : enums) {
			if(status.getTypeNo() == typeNo){
				return status.getOperateName();
			}
		}
		return "";
	}
}
