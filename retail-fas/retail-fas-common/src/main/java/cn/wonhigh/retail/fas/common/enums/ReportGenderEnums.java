/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：AskPaymentStatusEnums  
 * 类描述：请款单状态
 * 创建人：ouyang.zm  
 * 创建时间：2014-9-26 下午3:29:12  
 * @version       
 */
package cn.wonhigh.retail.fas.common.enums;

import org.apache.commons.lang.StringUtils;

public enum ReportGenderEnums {
	
	MALE("1", "男鞋"), FEMALE("2", "女鞋");

	private String typeNo;
	private String typeName;
	public String getTypeNo() {
		return typeNo;
	}
	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	private ReportGenderEnums(String typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	public static String getNameByNo(String typeNo){
		if(StringUtils.isNotBlank(typeNo)){
			for (ReportGenderEnums type : ReportGenderEnums.values()) {
				if(type.getTypeNo().equals(typeNo)){
					return type.getTypeName();
				}
			}	
		}
		return "";
	}
}
