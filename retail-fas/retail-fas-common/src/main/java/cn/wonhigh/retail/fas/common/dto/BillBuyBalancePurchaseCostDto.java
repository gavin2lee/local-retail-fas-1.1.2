package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;

public class BillBuyBalancePurchaseCostDto {

	private String originalBillNo;

	private String itemNo;

	private BigDecimal purchaseCost;

	public void setOriginalBillNo(String originalBillNo) {
		this.originalBillNo = originalBillNo;
	}

	public String getOriginalBillNo() {
		return this.originalBillNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setPurchaseCost(BigDecimal purchaseCost) {
		this.purchaseCost = purchaseCost;
	}

	public BigDecimal getPurchaseCost() {
		return this.purchaseCost;
	}
}
