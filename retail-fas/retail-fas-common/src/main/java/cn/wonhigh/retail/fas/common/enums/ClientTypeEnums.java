/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：ClientTypeEnums  
 * 类描述：客户类型
 * 创建人：ouyang.zm  
 * 创建时间：2015-2-3 下午3:01:07  
 * @version       
 */
package cn.wonhigh.retail.fas.common.enums;

public enum ClientTypeEnums {
	COMPANY(1, "公司"), CUSTOMER(2, "客户"), MALL(3, "商场"), MALL_GROUP(4, "商业集团"), SUPPLIER(
			5, "供应商");

	private Integer typeNo;
	private String typeName;

	private ClientTypeEnums(Integer typeNo, String typeName) {
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
