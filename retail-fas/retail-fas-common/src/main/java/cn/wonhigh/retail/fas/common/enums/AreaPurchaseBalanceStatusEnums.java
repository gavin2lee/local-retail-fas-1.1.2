/**  
*   
* 项目名称：retail-fas-common  
* 类名称：BillBalanceStatusEnums  
* 类描述：地区自购结算单状态
* 创建人：ouyang.zm  
* 创建时间：2014-10-17 下午4:55:14  
* @version       
*/ 
package cn.wonhigh.retail.fas.common.enums;

public enum AreaPurchaseBalanceStatusEnums {
	NO_CONFIRM(0, "制单"), 
	RECEIVE_BUSSINESS_CONFIRM(1, "业务确认"), 
	RECEIVE_FINANCE_CONFIRM(2, "财务确认"),
	ASK_PAYMENT(5, "已请款"),
	INVALID(99, "打回");
	
	private Integer statusNo;
	private String statusName;

	public Integer getStatusNo() {
		return statusNo;
	}

	public void setStatusNo(Integer statusNo) {
		this.statusNo = statusNo;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	private AreaPurchaseBalanceStatusEnums(Integer statusNo, String statusName) {
		this.statusNo = statusNo;
		this.statusName = statusName;
	}

	/**
	 * @param parseInt
	 * @return
	 */
	public static String getTypeName(int typeNo) {
		AreaPurchaseBalanceStatusEnums enums[] = AreaPurchaseBalanceStatusEnums.values();
		for (AreaPurchaseBalanceStatusEnums status : enums) {
			if(status.getStatusNo() == typeNo){
				return status.getStatusName();
			}
		}
		return "";
	}
}
