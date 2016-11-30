package cn.wonhigh.retail.fas.common.enums;

public enum OtherDeductionTypeEnum {

	OtherDeduction(0, "其他扣项"),
	RebateAmount(1, "返利"),
	OtherPrice(2, "其他费用");
	
	private int value;
	
	private String text;

	private OtherDeductionTypeEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public static String getTextByValue(int value){
		for (OtherDeductionTypeEnum type : OtherDeductionTypeEnum.values()) {
			if(type.getValue() == value){
				return type.getText();
			}
		}
		return "";
	}
}
