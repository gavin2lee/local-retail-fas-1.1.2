package cn.wonhigh.retail.fas.common.enums;

/**
 * bill_sale_out表biz_type 枚举
 * 
 * @author yao.gl
 * @date 2014-9-1 上午10:07:33
 * @version 0.1.0
 * @copyright wonhigh.cn
 */
public enum SaleOutBizTypeEnums {
	RETAIL_OUT(1, "零售出库"), AREABEAR(2, "地区承担"), HEADQUARTERSBEAR(3, "总部承担"), WHOLESALE_OUT(5, "批发出库"), GROUP_OUT(6,
			"团购出库"), BORROW_OUT(7, "借用出库"), CLAIM(8, "索赔单"), BILL_LOSS(9, "报废单"), MATERIAL_SALE(13, "物料领用出库"), WHOLESALE_SALE(21, "批发销售"), WHOLESALE_RETURN(
			22, "批发退货"), GROUP_SALE(23, "团购销售"), GROUP_RETURN(24, "团购退货"), BORROW_OUT_CROSS(25, "跨结算公司借用出库单"), RETURNCLAIM(26, "客残内销"), WHOLESALE_SHOP_OUT(29, "批发出库(店出)");
	private Integer status;
	private String text;

	private SaleOutBizTypeEnums(Integer status, String text) {
		this.status = status;
		this.text = text;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getText() {
		return this.text;
	}

	public static String getTextByStatus(Integer getRequestId) {
		SaleOutBizTypeEnums[] bizType = values();
		for (SaleOutBizTypeEnums handleTypeEnums : bizType) {
			if (handleTypeEnums.getStatus().equals(getRequestId)) {
				return handleTypeEnums.getText();
			}
		}
		return null;
	}
}
