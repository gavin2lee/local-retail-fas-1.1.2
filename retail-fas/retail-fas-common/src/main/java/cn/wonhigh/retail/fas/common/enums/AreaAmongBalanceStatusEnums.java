/**  
*   
* 项目名称：retail-fas-common  
* 类名称：AreaAmongBalanceStatusEnums  
* 类描述：地区间结算单状态(应收)
* 创建人：ouyang.zm  
* 创建时间：2014-11-10 下午2:44:19  
* @version       
*/ 
package cn.wonhigh.retail.fas.common.enums;

public enum AreaAmongBalanceStatusEnums {
    NO_CONFIRM(0, "制单"), 
//    SEND_BUSSINESS_CONFIRM(1, "调出方业务确认"), 
// 	RECEIVE_BUSSINESS_CONFIRM(3, "调入方业务确认"), 
 	SEND_FINANCE_CONFIRM(2, "调出方财务确认"),
 	RECEIVE_FINANCE_CONFIRM(4, "调入方财务确认"),
 	MAKE_INVOICE(6, "已开票"),
 	INVALID(99, "打回");
	
	private Integer statusNo;
	private String statusName;

	public Integer getStatusNo() {
		return statusNo;
	}

	public void setStatusNo(Integer statusNo) {
		this.statusNo = statusNo;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	private AreaAmongBalanceStatusEnums(Integer statusNo, String statusName) {
		this.statusNo = statusNo;
		this.statusName = statusName;
	}
	
	public static String getTypeName(int typeNo){
		AreaAmongBalanceStatusEnums enums[] = AreaAmongBalanceStatusEnums.values();
		for (AreaAmongBalanceStatusEnums status : enums) {
			if(status.getStatusNo() == typeNo){
				return status.getStatusName();
			}
		}
		return "";
	}
}
