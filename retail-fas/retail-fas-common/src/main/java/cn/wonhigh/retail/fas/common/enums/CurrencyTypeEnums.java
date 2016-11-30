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
public enum CurrencyTypeEnums {
	
	RMB(0, "人民币"), 
	MY(1,"美元"),
	OY(2,"欧元"),
	RY(3, "日元"), 
	YB(4,"英镑"),	
	GB(5,"港币");
	

	
	
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

	private CurrencyTypeEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	public static String getNameByNo(int id){
		CurrencyTypeEnums enums[] = CurrencyTypeEnums.values();
		for (CurrencyTypeEnums type : enums) {
			if(type.getTypeNo() == id){
				return type.getTypeName();
			}
		}
		return "";
	}
}
