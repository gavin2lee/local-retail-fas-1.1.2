package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.wonhigh.retail.fas.common.annotation.ClassReflect;
import cn.wonhigh.retail.fas.common.annotation.FieldReflect;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;

@ClassReflect(reflectClass=BillShopBalanceDiff.class)
public class BillShopBalanceDiffFooterDto implements Serializable {

	private static final long serialVersionUID = 1162744437835789321L;

	@FieldReflect(name="deductDiffAmount")
	private BigDecimal totalDeductDiffAmount;

	@FieldReflect(name="mallNumber")
	private BigDecimal totalMallNumber;

	@FieldReflect(name="salesDiffamount")
	private BigDecimal totalSalesDiffamount;

	@FieldReflect(name="reportDiffAmount")
	private BigDecimal totalReportDiffAmount;
	
	@FieldReflect(name="diffAmount")
	private BigDecimal totalDiffAmount;

	@FieldReflect(name="diffBalance")
	private BigDecimal totalDiffBalance;

	@FieldReflect(name="changeAmount")
	private BigDecimal totalChangeAmount;
	
	@FieldReflect(name="salesAmount")
	private BigDecimal totalSalesAmount;
	
	@FieldReflect(name="preDiffBalance")
	private BigDecimal totalPreDiffBalance;
	
	@FieldReflect(name="diffBackAmount")
	private BigDecimal totalDiffBackAmount;

	public BigDecimal getTotalReportDiffAmount() {
		return totalReportDiffAmount;
	}

	public void setTotalReportDiffAmount(BigDecimal totalReportDiffAmount) {
		this.totalReportDiffAmount = totalReportDiffAmount;
	}

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

	public BigDecimal getTotalSalesDiffamount() {
		return totalSalesDiffamount;
	}

	public void setTotalSalesDiffamount(BigDecimal totalSalesDiffamount) {
		this.totalSalesDiffamount = totalSalesDiffamount;
	}

	public BigDecimal getTotalDiffAmount() {
		return totalDiffAmount;
	}

	public void setTotalDiffAmount(BigDecimal totalDiffAmount) {
		this.totalDiffAmount = totalDiffAmount;
	}

	public BigDecimal getTotalDiffBalance() {
		return totalDiffBalance;
	}

	public void setTotalDiffBalance(BigDecimal totalDiffBalance) {
		this.totalDiffBalance = totalDiffBalance;
	}

	public BigDecimal getTotalChangeAmount() {
		return totalChangeAmount;
	}

	public void setTotalChangeAmount(BigDecimal totalChangeAmount) {
		this.totalChangeAmount = totalChangeAmount;
	}

	public BigDecimal getTotalSalesAmount() {
		return totalSalesAmount;
	}

	public void setTotalSalesAmount(BigDecimal totalSalesAmount) {
		this.totalSalesAmount = totalSalesAmount;
	}

	public BigDecimal getTotalPreDiffBalance() {
		return totalPreDiffBalance;
	}

	public void setTotalPreDiffBalance(BigDecimal totalPreDiffBalance) {
		this.totalPreDiffBalance = totalPreDiffBalance;
	}

	public BigDecimal getTotalDiffBackAmount() {
		return totalDiffBackAmount;
	}

	public void setTotalDiffBackAmount(BigDecimal totalDiffBackAmount) {
		this.totalDiffBackAmount = totalDiffBackAmount;
	}
}
