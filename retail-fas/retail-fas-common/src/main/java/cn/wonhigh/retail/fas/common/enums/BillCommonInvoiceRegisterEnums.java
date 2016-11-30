package cn.wonhigh.retail.fas.common.enums;

public enum BillCommonInvoiceRegisterEnums {
	NO(0, "未确认"), 
	OK(1, "已确认");
	
	private Integer useFlag;
	private String text;
	
	private BillCommonInvoiceRegisterEnums(Integer useFlag, String text) {
		this.useFlag = useFlag;
		this.text = text;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public String getText() {
		return text;
	}
	
	public static String getTextByStatus(byte useFlag) {
		BillCommonInvoiceRegisterEnums[] statusArr = values();
		for (BillCommonInvoiceRegisterEnums billCommonInvoice : statusArr) {
			if (billCommonInvoice.getUseFlag().equals(useFlag)) {
				return billCommonInvoice.getText();
			}
		}
		return null;
	}

}
