/**
 * title:ParamControlTypeEnums.java
 * package:cn.wonhigh.retail.fas.common.enums
 * description:控制级别
 * auther:user
 * date:2015-10-22 下午2:21:41
 */
package cn.wonhigh.retail.fas.common.enums;

public enum ParamControlTypeEnums {
	MALL(0, "通用"), 
	COMPANY(1, "大区"), 
	SHOP(2, "公司"), 
	MALL_GROUP(3, "店铺");

	private Integer typeNo;
	private String typeName;

	private ParamControlTypeEnums(Integer typeNo, String typeName) {
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
