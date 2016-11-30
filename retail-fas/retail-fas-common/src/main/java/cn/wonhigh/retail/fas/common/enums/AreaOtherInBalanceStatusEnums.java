package cn.wonhigh.retail.fas.common.enums;

public enum AreaOtherInBalanceStatusEnums {
	NO_CONFIRM(0, "制单"), SEND_FINANCE_CONFIRM(2, "总部财务确认"), RECEIVE_FINANCE_CONFIRM(
			4, "地区财务确认"), 	ASK_PAYMENT(5, "已请款"),  INVALID(99, "打回");

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

	private AreaOtherInBalanceStatusEnums(Integer statusNo, String statusName) {
		this.statusNo = statusNo;
		this.statusName = statusName;
	}

	public static String getTypeName(int typeNo) {
		AreaOtherInBalanceStatusEnums enums[] = AreaOtherInBalanceStatusEnums
				.values();
		for (AreaOtherInBalanceStatusEnums status : enums) {
			if (status.getStatusNo() == typeNo) {
				return status.getStatusName();
			}
		}
		return "";
	}
}
