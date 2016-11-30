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
public enum PayTermTypeEnums {
	
	FIXED_DATE(1, "每月固定日"), 
	SEND_DATE(2,"发货日XX天"), 
	INVOICE_DATE(3, "发票日XX天"), 
	MONTHLY_DATE(4, "月结XX天"), 
	SUPPLIER_SEND_DATE(5, "供应商发货日XX天");  
	

	
	
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


	private PayTermTypeEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	
	public static String getNameByNo(int id){
		PayTermTypeEnums enums[] = PayTermTypeEnums.values();
		for (PayTermTypeEnums type : enums) {
			if(type.getTypeNo() == id){
				return type.getTypeName();
			}
		}
		return "";
	}
	
}
