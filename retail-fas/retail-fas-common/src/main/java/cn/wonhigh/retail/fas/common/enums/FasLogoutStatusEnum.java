package cn.wonhigh.retail.fas.common.enums;

/**
 * 单据是否可用的枚举类
 * 
 * @author 杨勇
 * @date 2014-7-16 下午3:57:39
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public enum FasLogoutStatusEnum {

	ENABLE_STATUS(1, "已启用"),
	UNABLE_STATUS(0, "已停用");
	
	private Integer value;
	
	private String text;

	private FasLogoutStatusEnum(Integer value, String text) {
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
