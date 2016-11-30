package cn.wonhigh.retail.fas.common.enums;


/**
 * 
 * 收款类型
 * 
 * @author dong.j
 * @date 2013-11-29 下午2:17:35
 * @version 0.1.0
 * @copyright yougou.com
 */
public enum PaidTypeEnums {
	
	ORDER(0, "批发订单收款"), 
	MARGIN(1,"保证金收款"),
	OTHER(2, "客户收款");
	

	
	
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

	private PaidTypeEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	public static String getNameByNo(int id){
		PaidTypeEnums enums[] = PaidTypeEnums.values();
		for (PaidTypeEnums type : enums) {
			if(type.getTypeNo() == id){
				return type.getTypeName();
			}
		}
		return "";
	}
}
