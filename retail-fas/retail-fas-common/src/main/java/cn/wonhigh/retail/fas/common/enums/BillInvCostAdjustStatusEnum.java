package cn.wonhigh.retail.fas.common.enums;

public enum BillInvCostAdjustStatusEnum {
	CREATE(0, "制单"), 
	CONFIRM(1,"确认");
	
	private Integer value;
	
	private String text;

	private BillInvCostAdjustStatusEnum(Integer value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
