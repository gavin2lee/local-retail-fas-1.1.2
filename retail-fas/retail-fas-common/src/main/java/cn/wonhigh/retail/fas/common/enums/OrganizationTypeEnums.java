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

public enum OrganizationTypeEnums {
	COMPANY(0, "公司"), 
	SUPPLIER(1, "供应商"), 
	CUSTOMER(2, "客户");

	private OrganizationTypeEnums(int typeNo, String typeName) {
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
		OrganizationTypeEnums enums[] = OrganizationTypeEnums.values();
		for (OrganizationTypeEnums status : enums) {
			if(status.getTypeNo() == typeNo){
				return status.getTypeName();
			}
		}
		return "";
	}
}
