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
public enum OrderTypeEnums {
	
	SPOT(1, "现货"), 
	FORWARD(2,"期货"), 
	FORWARD_SPOT(3, "期货;现货");  
	
	
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


	private OrderTypeEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	
	public static String getNameByNo(int id){
		OrderTypeEnums enums[] = OrderTypeEnums.values();
		for (OrderTypeEnums type : enums) {
			if(type.getTypeNo() == id){
				return type.getTypeName();
			}
		}
		return "";
	}
	
}
