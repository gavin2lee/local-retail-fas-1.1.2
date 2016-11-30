/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：SubjectTypeEnums  
 * 类描述：科目类型
 * 创建人：ouyang.zm  
 * 创建时间：2014-10-15 上午10:45:21  
 * @version       
 */
package cn.wonhigh.retail.fas.common.enums;

public enum SubjectTypeEnums {
	PROPERTY(1, "资产"), LIABILITIES(2, "负债"), COMMON(3, "共同"), EQUITIES(4, "权益"), COST(
			5, "成本"), PROFIT_AND_LOSS(6, "损益");
	
	private Integer typeNo;
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

	private SubjectTypeEnums(int typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}

}
