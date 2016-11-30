package cn.wonhigh.retail.fas.common.enums;


/**
 * 
 * 单据类型(配送/退仓/收货/退货/盘点)
 * 
 * @author dong.j
 * @date 2013-11-29 下午2:17:35
 * @version 0.1.0
 * @copyright yougou.com
 */
public enum ExceptionTypeEnums {
	
	COST_IS_ZERO(1, "单据单价为0"), 
	COST_IS_ONE(2, "单据单价为1"), 
	EXCEPTION_COST(2,"单据价格与价目表不符");
	

	
	
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

	private ExceptionTypeEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	public static String getNameByNo(int id){
		ExceptionTypeEnums enums[] = ExceptionTypeEnums.values();
		for (ExceptionTypeEnums type : enums) {
			if(type.getTypeNo() == id){
				return type.getTypeName();
			}
		}
		return "";
	}
}
