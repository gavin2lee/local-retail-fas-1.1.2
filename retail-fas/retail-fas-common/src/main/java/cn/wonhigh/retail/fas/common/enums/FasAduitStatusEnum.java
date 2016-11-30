package cn.wonhigh.retail.fas.common.enums;

/**
 * 单据状态的枚举类
 * 
 * @author 杨勇
 * @date 2014-7-16 下午3:57:39
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public enum FasAduitStatusEnum {

	NO_ADUIT_STATUS(0, "未审核"),
	ADUIT_STATUS(1, "已审核");
	
	private Integer value;
	
	private String text;

	private FasAduitStatusEnum(Integer value, String text) {
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
