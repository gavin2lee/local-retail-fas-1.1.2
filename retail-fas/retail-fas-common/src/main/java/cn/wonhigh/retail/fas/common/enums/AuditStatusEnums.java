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
public enum AuditStatusEnums {
	
	CREATE(0, "制单", "撤销"), 
	CONFIRM(1,"提交审批", "提交审批"), 
	ONE_LEVEL_AUDIT(2, "业务主管已审批", "业务主管审批"), 
	TWO_LEVEL_AUDIT(3, "财务人员已审批", "财务人员审批"), 
	THREE_LEVEL_AUDIT(4, "财务经理已审批", "财务经理审批"),
	FOUR_LEVEL_AUDIT(5, "财务总监已审批", "财务总监审批"),
	BACK(99, "打回", "打回");  
	

	
	
	/**
	 * SERIALNO_CONFIG 表 REQUESTID 的值
	 */
	private Integer typeNo;
	/**
	 * 模块描述
	 */
	private String typeName;

	/**
	 * 操作名称
	 */
	private String operateName;
	
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

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	private AuditStatusEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	private AuditStatusEnums(Integer typeNo, String typeName, String operateName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
		this.operateName = operateName;
	}
	
	public static String getNameByNo(int id){
		AuditStatusEnums enums[] = AuditStatusEnums.values();
		for (AuditStatusEnums type : enums) {
			if(type.getTypeNo() == id){
				return type.getTypeName();
			}
		}
		return "";
	}
	
	public static String getOperateNameByNo(int id){
		AuditStatusEnums enums[] = AuditStatusEnums.values();
		for (AuditStatusEnums type : enums) {
			if(type.getTypeNo() == id){
				return type.getOperateName();
			}
		}
		return "";
	}
}
