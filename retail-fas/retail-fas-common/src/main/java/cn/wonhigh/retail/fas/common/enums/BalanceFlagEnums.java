package cn.wonhigh.retail.fas.common.enums;


/**
 * 
 * 对账标记枚举
 * 
 * @author dong.j
 * @date 2013-11-29 下午2:17:35
 * @version 0.1.0
 * @copyright yougou.com
 */
public enum BalanceFlagEnums {
	
	NO(0, "未对账"), 
	YES(1,"已对账");  
	

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

	private BalanceFlagEnums(Integer typeNo) {
		this.typeNo = typeNo;
	}
	
	private BalanceFlagEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	
	public static String getNameByNo(Integer id){
		BalanceFlagEnums enums[] = BalanceFlagEnums.values();
		for (BalanceFlagEnums type : enums) {
			if(type.getTypeNo().intValue() == id.intValue()){
				return type.getTypeName();
			}
		}
		return "";
	}
	
}
