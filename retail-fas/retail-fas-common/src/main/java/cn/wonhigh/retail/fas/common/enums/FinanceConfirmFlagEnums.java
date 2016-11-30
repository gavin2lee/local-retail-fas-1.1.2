package cn.wonhigh.retail.fas.common.enums;

/**
 * 基础资料机构状态枚举类
 * 
 * @author su.yq
 * @date 2013-9-4 下午12:10:31
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public enum FinanceConfirmFlagEnums {
	
	CONFIRM(1, "已确认"), NOT_CONFIRM(0, "未确认");

	private FinanceConfirmFlagEnums(Integer status, String text) {
		this.status = status;
		this.text = text;
	}

	private Integer status;
	private String text;

	public Integer getStatus() {
		return status;
	}

	public String getText() {
		return text;
	}

	public static String getTextByStatus(int status) {
		FinanceConfirmFlagEnums[] statusArr = FinanceConfirmFlagEnums.values();
		for (FinanceConfirmFlagEnums baseInfoStoreStatusEnums : statusArr) {
			if (baseInfoStoreStatusEnums.getStatus().equals(status)) {
				return baseInfoStoreStatusEnums.getText();
			}
		}
		return "";
	}
}
