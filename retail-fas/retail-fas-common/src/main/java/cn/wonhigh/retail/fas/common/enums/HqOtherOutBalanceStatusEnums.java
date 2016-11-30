package cn.wonhigh.retail.fas.common.enums;

public enum HqOtherOutBalanceStatusEnums {
	    NO_CONFIRM(0, "制单"), 
	 	SEND_FINANCE_CONFIRM(2, "总部财务确认"),
	 	RECEIVE_FINANCE_CONFIRM(4, "地区财务确认"),
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

		private HqOtherOutBalanceStatusEnums(Integer statusNo, String statusName) {
			this.statusNo = statusNo;
			this.statusName = statusName;
		}
		
		public static String getTypeName(int typeNo){
			HqOtherOutBalanceStatusEnums enums[] = HqOtherOutBalanceStatusEnums.values();
			for (HqOtherOutBalanceStatusEnums status : enums) {
				if(status.getStatusNo() == typeNo){
					return status.getStatusName();
				}
			}
			return "";
		}
}