package cn.wonhigh.retail.fas.common.enums;


/**
 * 
 * 合同折扣枚举
 * 
 * @author dong.j
 * @date 2013-11-29 下午2:17:35
 * @version 0.1.0
 * @copyright yougou.com
 */
public enum ContractDiscountTypeEnums {
	
	SUPPLIER(1, "供应商合同设置"), 
	COMPANY(2,"公司合同设置"), 
	ITEM(3, "商品指定价设置");  
	
	
	/**
	 * SERIALNO_CONFIG 表 REQUESTID 的值
	 */
	private Integer typeNo;
	/**
	 * 模块描述
	 */
	private String typeName;

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


	private ContractDiscountTypeEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	
	public static String getNameByNo(int id){
		ContractDiscountTypeEnums enums[] = ContractDiscountTypeEnums.values();
		for (ContractDiscountTypeEnums type : enums) {
			if(type.getTypeNo() == id){
				return type.getTypeName();
			}
		}
		return "";
	}
	
}
