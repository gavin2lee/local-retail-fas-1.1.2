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
public enum BusinessTypeEnums {
	
	UNIFY(1, "统采"), 
	TRANSFER_1(2,"总部调给地区"),
	TRANSFER_2(3,"地区调给总部"),
	REPLACE(4,"代采");
	

	
	
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

	private BusinessTypeEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	public static String getNameByNo(int id){
		BusinessTypeEnums enums[] = BusinessTypeEnums.values();
		for (BusinessTypeEnums type : enums) {
			if(type.getTypeNo() == id){
				return type.getTypeName();
			}
		}
		return "";
	}
}
