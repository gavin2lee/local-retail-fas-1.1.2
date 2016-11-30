/**
 * title:HqInsteadBuyBalanceStatusEnums.java
 * package:cn.wonhigh.retail.fas.common.enums
 * description:总部代采单据状态
 * auther:user
 * date:2015-9-30 下午12:12:45
 */
package cn.wonhigh.retail.fas.common.enums;

public enum HqInsteadBuyBalanceStatusEnums {
	NO_CONFIRM(0, "制单"), 
	RECEIVE_BUSSINESS_CONFIRM(1, "总部确认"), 
	RECEIVE_FINANCE_CONFIRM(2, "地区财务确认"),
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

	private HqInsteadBuyBalanceStatusEnums(Integer statusNo, String statusName) {
		this.statusNo = statusNo;
		this.statusName = statusName;
	}

	/**
	 * @param parseInt
	 * @return
	 */
	public static String getTypeName(int typeNo) {
		HqInsteadBuyBalanceStatusEnums enums[] = HqInsteadBuyBalanceStatusEnums.values();
		for (HqInsteadBuyBalanceStatusEnums status : enums) {
			if(status.getStatusNo() == typeNo){
				return status.getStatusName();
			}
		}
		return "";
	}
}
