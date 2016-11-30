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

public enum ReportOrderTypeEnums {
	
	SELF_PRODUCED("1", "自产"), OUT_PRODUCED("2", "外购"), AREA_BUY("3", "地区自购");

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
	private ReportOrderTypeEnums(String typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	
	public static String getNameByNo(String typeNo){
		if(StringUtils.isNotBlank(typeNo)){
			for (ReportOrderTypeEnums type : ReportOrderTypeEnums.values()) {
				if(type.getTypeNo().equals(typeNo)){
					return type.getTypeName();
				}
			}	
		}
		return "";
	}
}
