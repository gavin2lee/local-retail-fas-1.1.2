package cn.wonhigh.retail.fas.common.enums;


/**
 * 
 * 多级审批枚举
 * 
 * @author dong.j
 * @date 2013-11-29 下午2:17:35
 * @version 0.1.0
 * @copyright yougou.com
 */
public enum PriceBasisEnums {
	
	TAG_PRICE(1, "牌价"), 
	SALES_AMOUNT(2,"终端销售金额"), 
	SUPPLIER_BALANCE(3, "供应商结算价");  
	
	
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


	private PriceBasisEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	
	public static String getNameByNo(int id){
		PriceBasisEnums enums[] = PriceBasisEnums.values();
		for (PriceBasisEnums type : enums) {
			if(type.getTypeNo() == id){
				return type.getTypeName();
			}
		}
		return "";
	}
	
}
