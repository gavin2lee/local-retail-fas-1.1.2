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
public enum ExcessStatusEnums {
	NOT_PRICING(0, "未核价"), 
	NOT_EXCESS(-1, "未超额"), 
	EXCESS_NOT_PROCESS(1,"超额OA未处理"), 
	EXCESS_IS_PROCESS(2, "超额OA已处理");  
	
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


	private ExcessStatusEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	
	public static String getNameByNo(int id){
		ExcessStatusEnums enums[] = ExcessStatusEnums.values();
		for (ExcessStatusEnums type : enums) {
			if(type.getTypeNo() == id){
				return type.getTypeName();
			}
		}
		return "";
	}
	
}
