package cn.wonhigh.retail.fas.common.enums;

/**
 * 门店结算差异生成方式配置表枚举类
 * 
 * @author yang.y
 */
public enum BalanceDiffTypeEnum {

	DETAIL(1, "按明细"),
	PROMOTIONAL_MATERIAL(2, "按促销活动"),
	SALE(3, "按销售"),
	CODE(4, "按商场结算码");
	
	private Integer typeNo;
	private String typeName;
	
	private BalanceDiffTypeEnum(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}

	public Integer getTypeNo() {
		return typeNo;
	}

	public void setTypeNo(Integer typeNo) {
		this.typeNo = typeNo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
