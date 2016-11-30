/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：BalanceTypeEnums  
 * 类描述：结算状态
 * 创建人：ouyang.zm  
 * 创建时间：2014-9-12 下午12:05:57  
 * @version       
 */
package cn.wonhigh.retail.fas.common.enums;

public enum ValidateTypeEnums {
	COMPANY(0, "公司"), 
	SUPPLIER(1, "供应商"), 
	CUSTOMER(2, "客户"),
	BRAND(3, "品牌"), 
	CATEGORY(4, "大类"), 
	ITEM(5, "商品"),
	FINANCIAL_CATEGORY(6, "财务大类"),
	NUMBER(7, "数字"),
	DATE(8, "日期"),
	BRAND_UNIT(9, "品牌部"), 
	NULL(99, "");

	private ValidateTypeEnums(int typeNo, String typeName) {
		this.typeNo = typeNo;
		TypeName = typeName;
	}

	
	private int typeNo;
	
	private String TypeName;
	
	public int getTypeNo() {
		return typeNo;
	}

	public void setTypeNo(int typeNo) {
		this.typeNo = typeNo;
	}

	public String getTypeName() {
		return TypeName;
	}

	public void setTypeName(String typeName) {
		TypeName = typeName;
	}

	public static String getTypeNameByNo(int typeNo){
		ValidateTypeEnums enums[] = ValidateTypeEnums.values();
		for (ValidateTypeEnums status : enums) {
			if(status.getTypeNo() == typeNo){
				return status.getTypeName();
			}
		}
		return "";
	}
}
