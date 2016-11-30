package cn.wonhigh.retail.fas.web.utils;

import org.apache.commons.lang.StringUtils;

public enum ShopMallDtlShowTypeEnum {

	CATEGORY("1", new BillDtlCategoryHandler()),
	BALANCE_DTL("2", new BillDtlBalanceDtlHandler()),
	PROMOTIONS("3", new BillDtlPromotionsHandler()),
	PAYMENT_METHOD("4", new BillDtlPaymentMethodHandler()),
	BILLING_CODE("5", new BillDtlBillingCodeHandler());
	private ShopMallDtlShowTypeEnum(String showType, 
			AbstactBillDtlHandler handler) {
		this.showType = showType;
		this.handler = handler;
	}
	
	private String showType;
	
	private AbstactBillDtlHandler handler;

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public AbstactBillDtlHandler getHandler() {
		return handler;
	}

	public void setHandler(AbstactBillDtlHandler handler) {
		this.handler = handler;
	}
	
	public static AbstactBillDtlHandler getHandler(String showType) {
		if(StringUtils.isEmpty(showType)) {
			return null;
		}
		ShopMallDtlShowTypeEnum enums[] = ShopMallDtlShowTypeEnum.values();
		for(ShopMallDtlShowTypeEnum e : enums) {
			if(e.getShowType().equals(showType)) {
				return e.getHandler();
			}
		}
		return null;
	}
}
