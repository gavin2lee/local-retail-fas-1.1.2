package cn.wonhigh.retail.fas.common.enums;


/**
 * 
 * 森达客户余额业务类型枚举
 * 
 * @author dong.j
 * @date 2013-11-29 下午2:17:35
 * @version 0.1.0
 * @copyright yougou.com
 */
public enum WholeSaleSendaTypeEnums {
	
	RECEIVE(2, "收款"),
	DEDUCTION(5, "扣项"),
	WHOLESALE_OUT(21, "批发出库"),
	WHOLESALE_RETURN(22, "批发退货"),
	WHOLESALE_SHOP_OUT(29, "批发出库"),
	CUSTOMER_RETURN(30, "批发退货"),
	WHOLESALE_RECALL(43, "批发退货"),
	INVOICED(68, "发票");

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


	private WholeSaleSendaTypeEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	
	public static String getNameByNo(int id){
		WholeSaleSendaTypeEnums enums[] = WholeSaleSendaTypeEnums.values();
		for (WholeSaleSendaTypeEnums type : enums) {
			if(type.getTypeNo() == id){
				return type.getTypeName();
			}
		}
		return "";
	}
	
}
