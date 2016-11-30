/**  
*   
* 项目名称：retail-fas-common  
* 类名称：AreaOtherOutBalanceStatusEnums  
* 类描述：地区其他出库结算单状态
* 创建人：ouyang.zm  
* 创建时间：2014-12-11 上午11:12:32  
* @version       
*/ 
package cn.wonhigh.retail.fas.common.enums;

public enum AreaOtherOutBalanceStatusEnums {
	    NO_CONFIRM(0, "制单"), 
	 	SEND_FINANCE_CONFIRM(2, "地区财务确认"),
	 	RECEIVE_FINANCE_CONFIRM(4, "总部财务确认"),
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

		private AreaOtherOutBalanceStatusEnums(Integer statusNo, String statusName) {
			this.statusNo = statusNo;
			this.statusName = statusName;
		}
		
		public static String getTypeName(int typeNo){
			AreaOtherOutBalanceStatusEnums enums[] = AreaOtherOutBalanceStatusEnums.values();
			for (AreaOtherOutBalanceStatusEnums status : enums) {
				if(status.getStatusNo() == typeNo){
					return status.getStatusName();
				}
			}
			return "";
		}
}