/**
 * title:HqOtherInBalanceStatusEnums.java
 * package:cn.wonhigh.retail.fas.common.enums
 * description:TODO
 * auther:user
 * date:2015-4-22 上午11:08:52
 */
package cn.wonhigh.retail.fas.common.enums;

public enum HqOtherInBalanceStatusEnums {
	NO_CONFIRM(0, "制单"), SEND_FINANCE_CONFIRM(2, "地区财务确认"), RECEIVE_FINANCE_CONFIRM(
			4, "总部财务确认"), 	ASK_PAYMENT(5, "已请款"),  INVALID(99, "打回");

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

	private HqOtherInBalanceStatusEnums(Integer statusNo, String statusName) {
		this.statusNo = statusNo;
		this.statusName = statusName;
	}

	public static String getTypeName(int typeNo) {
		HqOtherInBalanceStatusEnums enums[] = HqOtherInBalanceStatusEnums
				.values();
		for (HqOtherInBalanceStatusEnums status : enums) {
			if (status.getStatusNo() == typeNo) {
				return status.getStatusName();
			}
		}
		return "";
	}
}
