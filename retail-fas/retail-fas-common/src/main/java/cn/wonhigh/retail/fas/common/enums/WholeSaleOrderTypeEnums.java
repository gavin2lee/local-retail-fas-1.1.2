package cn.wonhigh.retail.fas.common.enums;


/**
 * 
 * 批发订单类型枚举
 * 
 * @author dong.j
 * @date 2013-11-29 下午2:17:35
 * @version 0.1.0
 * @copyright yougou.com
 */
public enum WholeSaleOrderTypeEnums {
	
	ORDERING(0, "订货"),
	RESTOCK(2, "补货"),
	SPOT(1, "发货");  
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


	private WholeSaleOrderTypeEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	
	public static String getNameByNo(int id){
		WholeSaleOrderTypeEnums enums[] = WholeSaleOrderTypeEnums.values();
		for (WholeSaleOrderTypeEnums type : enums) {
			if(type.getTypeNo() == id){
				return type.getTypeName();
			}
		}
		return "";
	}
	
}
