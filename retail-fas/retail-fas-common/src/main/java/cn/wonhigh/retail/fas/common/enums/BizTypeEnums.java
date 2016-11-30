package cn.wonhigh.retail.fas.common.enums;

/**
 * 业务类型(批发团购)标志 BillSaleOut表中的bizType 50到59 是给POS端的销售单使用 请勿重复
 * 
 * @author yu.y
 * @date 2014-9-1 上午10:07:33
 * @version 0.1.0
 * @copyright yougou.com
 */
public enum BizTypeEnums {
	FIRST_ORDER(0, "订货"), REPLENISH_ORDER(1, "补货"), DIRECT(2, "直接"), TRANSFERCARGO(4, "跨区调货"), DIFFERENCE(5, "差异处理"), SHOPRETURN(6, "店退仓"), 
	BORROW_OUT(7, "公司内部借用"), CLAIM(8, "索赔单"), FIRST_ORDER_DIFF(10, "订货差异"), REPLENISH_ORDER_DIFF(11,"补货差异"), DIRECT_DIFF(12, "直接差异"), 
	WHOLESALE(21, "批发销售"), WHOLESALE_RETURN(22, "过季退货"), GROUPSALE(23, "团购销售"), GROUPSALE_RETURN(24, "团购退货"), WHOLESALE_RETURN_DEFECTIVE(26, "批发退残"), 
	BORROW_OUT_COMPANY(25, "外部公司借用"), WHOLESALE_SHOP_OUT(29, "批发出库(店出)"), CUSTOMER_RETURN(30, "客残退货"), STORE_TO_SHOP(31, "仓到店配货单明细"), STORE_TO_STORE(32, "仓到仓配货单明细"), 
	PURCHASE(33, "收购"), PURCHASE_DIFF(34, "收购差异"), ZONE_CUSTOMER_SALE(35, "地区客残销售"), HQ_CUSTOMER_SALE(36, "总部客残销售"), TRANSFERETURN(40, "残次跨区"), TRANSFERETURNDIFF(41, "残次跨区差异")
	, WHOLESALE_RECALL(43, "召回退货"),ZONGBUPURCHASE(60, "总部收购"),YISHOUFANHUO(6, "溢收返货"),YISHOUBUCHULI(8, "溢收不处理"),SUOPEI(9, "索赔");

	private Integer status;
	private String text;

	private BizTypeEnums(Integer status, String text) {
		this.status = status;
		this.text = text;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getText() {
		return this.text;
	}
	
	public static String getNameByNo(int no){
		for (BizTypeEnums item : BizTypeEnums.values()) {
			if(item.getStatus().intValue() == no){
				return item.getText();
			}
		}
		return "";
	}

}
