package cn.wonhigh.retail.fas.common.enums;

/**
 * 单据价格类型的枚举类
 * 
 * @author yang.y
 */
public enum BillPriceTypeEnums {

	PURCHASE_PRICE("CGJ", "采购价");
	
	private String typeNo;
	private String typeName;
	
	private BillPriceTypeEnums(String typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}

	public String getTypeNo() {
		return typeNo;
	}

	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
