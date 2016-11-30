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


public enum ReportBizTypeEnums {
	
	FIRST_ORDER(0, "订货"), REPLENISH_ORDER(1, "补货"), DIRECT(2, "直接"), PURCHASE(33, "收购"), CUSTOMRETURN(99, "客残"),RETURN(100, "原残"),;

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
	private ReportBizTypeEnums(Integer typeNo, String typeName) {
		this.typeNo = typeNo;
		this.typeName = typeName;
	}
	public static String getNameByNo(Integer typeNo){
		if(null != typeNo){
			for (ReportBizTypeEnums type : ReportBizTypeEnums.values()) {
				if(type.getTypeNo().intValue() == typeNo.intValue()){
					return type.getTypeName();
				}
			}	
		}
		return "";
	
	}
}
