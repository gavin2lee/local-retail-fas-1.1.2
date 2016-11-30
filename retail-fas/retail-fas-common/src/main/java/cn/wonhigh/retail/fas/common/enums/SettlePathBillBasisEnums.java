package cn.wonhigh.retail.fas.common.enums;

/**
 * 结算路径相关的单据依据枚举类
 * 
 * @author yang.y
 */
public enum SettlePathBillBasisEnums {

	ORDERING("1301", "到货单"),
	RESTOCK("1313", "退厂单");
	
	private String value;
	private String text;
	
	private SettlePathBillBasisEnums(String value, String text) {
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
