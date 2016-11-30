/**
 * title:TsAskPaymentStatusEnums.java
 * package:cn.wonhigh.retail.fas.common.enums
 * description:体育请款单状态
 * auther:user
 * date:2016-4-26 下午3:25:14
 */
package cn.wonhigh.retail.fas.common.enums;


public enum TsAskPaymentStatusEnums {
	CREATE_STATUS(0,"制单"),
	NO_ADUIT_STATUS(1, "已审核");
	
	private Integer value;
	private String text;

	private TsAskPaymentStatusEnums(Integer value, String text) {
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
	
	public static String getTypeName(int typeNo){
		TsAskPaymentStatusEnums enums[] = TsAskPaymentStatusEnums.values();
		for (TsAskPaymentStatusEnums status : enums) {
			if(status.getValue() == typeNo){
				return status.getText();
			}
		}
		return "";
	}
}
