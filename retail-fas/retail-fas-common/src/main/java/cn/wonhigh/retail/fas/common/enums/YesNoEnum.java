package cn.wonhigh.retail.fas.common.enums;

public enum YesNoEnum {

	YES(1, "是"),
	NO(0, "否");
	
	private int value;
	
	private String text;

	private YesNoEnum(int value, String text) {
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
		for (YesNoEnum type : YesNoEnum.values()) {
			if(type.getValue() == value){
				return type.getText();
			}
		}
		return "";
	}
}
