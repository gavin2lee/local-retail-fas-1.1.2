package cn.wonhigh.retail.fas.common.enums;

public enum WholesaleRemainingTypeEnums {
	PRE_PAYMENT(0, "订单预收款"), 
	WHOLESALE_SALE(1, "订单出库"), 
	OTHER_PAYMENT(2, "其它收款"),
	WHOLESALE_RETURN(3, "过季退货"), 	 
	CUSTOMER_RETURN(4, "客残退货"), 
	DEDUCTION_REBATE(5, "扣项"),
	
	UNFROZEN_RETURN(6, "解冻退货"),
	CUSTOMER_PAYMENT(7, "客户收款"),
	ORDER_PAYMENT(8, "订单收款"),
	WHOLESALE_OUT1(21, "批发出库"),
	WHOLESALE_RETURN2(22, "过季退货"),
	WHOLESALE_OUT2(29, "批发出库"),
	WHOLESALE_RECALL(43, "召回退货"),
	FROZEN_RETURN(30, "客残退货"),
	
	CREDIT(88, "信贷一次"),
	CANCEL(99, "作废单据");

	private WholesaleRemainingTypeEnums(int typeNo, String typeName) {
		this.typeNo = typeNo;
		TypeName = typeName;
	}

	
	private int typeNo;
	
	private String TypeName;
	
	public int getTypeNo() {
		return typeNo;
	}

	public void setTypeNo(int typeNo) {
		this.typeNo = typeNo;
	}

	public String getTypeName() {
		return TypeName;
	}

	public void setTypeName(String typeName) {
		TypeName = typeName;
	}

	public static String getTypeNameByNo(int typeNo){
		WholesaleRemainingTypeEnums enums[] = WholesaleRemainingTypeEnums.values();
		for (WholesaleRemainingTypeEnums status : enums) {
			if(status.getTypeNo() == typeNo){
				return status.getTypeName();
			}
		}
		return "";
	}
}
