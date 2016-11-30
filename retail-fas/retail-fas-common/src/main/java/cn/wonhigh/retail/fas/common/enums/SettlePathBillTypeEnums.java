package cn.wonhigh.retail.fas.common.enums;

/**
 * 结算路径相关的单据类型枚举类
 * 
 * @author yang.y
 */
public enum SettlePathBillTypeEnums {

	ORDERING("0", "订货"),
	RESTOCK("1", "补货"),
	HQ_BUY("60", "总部收购"),
	ORIGINAL_RESIDUES("1333", "原残退厂"),
	OFF_RESIDUAL("1334", "客残");
	
	private String value;
	private String text;
	
	private SettlePathBillTypeEnums(String value, String text) {
		this.value = value;
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
