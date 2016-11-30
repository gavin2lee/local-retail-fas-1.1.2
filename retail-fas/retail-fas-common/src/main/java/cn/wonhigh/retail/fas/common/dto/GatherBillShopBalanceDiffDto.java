package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$3;

/**
 * 汇总结算差异金额数据
 * 
 * @author yang.y
 */
public class GatherBillShopBalanceDiffDto implements Serializable {

	private static final long serialVersionUID = -7246526800548066472L;

	/** 系统扣费之和 */
	 @JsonSerialize(using = BigDecimalSerializer$3.class)
	private BigDecimal totalDeductDiffAmount;
	
	/** 商场报数之和 */
	 @JsonSerialize(using = BigDecimalSerializer$3.class)
	private BigDecimal totalMallNumber;
	
	/** 系统收入之和 */
	 @JsonSerialize(using = BigDecimalSerializer$3.class)
	private BigDecimal totalSalesAmount;
	
	/** 报数差异之和 */
	 @JsonSerialize(using = BigDecimalSerializer$3.class)
	private BigDecimal totalSalesDiffAmount;
	
	/** 扣费差异之和 */
	 @JsonSerialize(using = BigDecimalSerializer$3.class)
	private BigDecimal totalDiffAmount;
	
	/** 调整金额之和 */
	 @JsonSerialize(using = BigDecimalSerializer$3.class)
	private BigDecimal totalChangeAmount;

	public BigDecimal getTotalDeductDiffAmount() {
		return totalDeductDiffAmount;
	}

	public void setTotalDeductDiffAmount(BigDecimal totalDeductDiffAmount) {
		this.totalDeductDiffAmount = totalDeductDiffAmount;
	}

	public BigDecimal getTotalMallNumber() {
		return totalMallNumber;
	}

	public void setTotalMallNumber(BigDecimal totalMallNumber) {
		this.totalMallNumber = totalMallNumber;
	}

	public BigDecimal getTotalSalesAmount() {
		return totalSalesAmount;
	}

	public void setTotalSalesAmount(BigDecimal totalSalesAmount) {
		this.totalSalesAmount = totalSalesAmount;
	}

	public BigDecimal getTotalSalesDiffAmount() {
		return totalSalesDiffAmount;
	}

	public void setTotalSalesDiffAmount(BigDecimal totalSalesDiffAmount) {
		this.totalSalesDiffAmount = totalSalesDiffAmount;
	}

	public BigDecimal getTotalDiffAmount() {
		return totalDiffAmount;
	}

	public void setTotalDiffAmount(BigDecimal totalDiffAmount) {
		this.totalDiffAmount = totalDiffAmount;
	}

	public BigDecimal getTotalChangeAmount() {
		return totalChangeAmount;
	}

	public void setTotalChangeAmount(BigDecimal totalChangeAmount) {
		this.totalChangeAmount = totalChangeAmount;
	}
}
