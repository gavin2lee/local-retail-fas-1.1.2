package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.wonhigh.retail.fas.common.annotation.ClassReflect;
import cn.wonhigh.retail.fas.common.annotation.FieldReflect;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;

/**
 * 商场结算单-按大类显示页脚对象
 * 
 * @author yang.y
 */
@ClassReflect(reflectClass=BillShopBalanceCateSum.class)
public class BillShopBalanceCateSumFooterDto implements Serializable {

	private static final long serialVersionUID = -1020633233779292608L;

	@FieldReflect(name="saleQty")
	private Integer totalSaleQty; 
	
	@FieldReflect(name="saleAmount")
	private BigDecimal totalSaleAmount;
	
	@FieldReflect(name="prepaymentAmount")
	private BigDecimal totalPrepaymentAmount;
	
	@FieldReflect(name="diffAmount")
	private BigDecimal totalDiffAmount;
	
	@FieldReflect(name="systemDeductAmount")
	private BigDecimal totalSystemDeductAmount;
	
	@FieldReflect(name="balanceDeductAmount")
	private BigDecimal totalBalanceDeductAmount;
	
	@FieldReflect(name="balanceDiffAmount")
	private BigDecimal totalBalanceDiffAmount;
	
	@FieldReflect(name="deductAmount")
	private BigDecimal totalDeductAmount;
	
	@FieldReflect(name="billingAmount")
	private BigDecimal totalBillingAmount;
	
	@FieldReflect(name="actualBillingAmount")
	private BigDecimal actualBillingAmount;
	

	public Integer getTotalSaleQty() {
		return totalSaleQty;
	}

	public void setTotalSaleQty(Integer totalSaleQty) {
		this.totalSaleQty = totalSaleQty;
	}

	public BigDecimal getTotalSaleAmount() {
		return totalSaleAmount;
	}

	public void setTotalSaleAmount(BigDecimal totalSaleAmount) {
		this.totalSaleAmount = totalSaleAmount;
	}

	public BigDecimal getTotalBillingAmount() {
		return totalBillingAmount;
	}

	public void setTotalBillingAmount(BigDecimal totalBillingAmount) {
		this.totalBillingAmount = totalBillingAmount;
	}

	public BigDecimal getTotalPrepaymentAmount() {
		return totalPrepaymentAmount;
	}

	public void setTotalPrepaymentAmount(BigDecimal totalPrepaymentAmount) {
		this.totalPrepaymentAmount = totalPrepaymentAmount;
	}

	public BigDecimal getTotalDiffAmount() {
		return totalDiffAmount;
	}

	public void setTotalDiffAmount(BigDecimal totalDiffAmount) {
		this.totalDiffAmount = totalDiffAmount;
	}

	public BigDecimal getTotalSystemDeductAmount() {
		return totalSystemDeductAmount;
	}

	public void setTotalSystemDeductAmount(BigDecimal totalSystemDeductAmount) {
		this.totalSystemDeductAmount = totalSystemDeductAmount;
	}

	public BigDecimal getTotalBalanceDeductAmount() {
		return totalBalanceDeductAmount;
	}

	public void setTotalBalanceDeductAmount(BigDecimal totalBalanceDeductAmount) {
		this.totalBalanceDeductAmount = totalBalanceDeductAmount;
	}

	public BigDecimal getTotalBalanceDiffAmount() {
		return totalBalanceDiffAmount;
	}

	public void setTotalBalanceDiffAmount(BigDecimal totalBalanceDiffAmount) {
		this.totalBalanceDiffAmount = totalBalanceDiffAmount;
	}

	public BigDecimal getTotalDeductAmount() {
		return totalDeductAmount;
	}

	public void setTotalDeductAmount(BigDecimal totalDeductAmount) {
		this.totalDeductAmount = totalDeductAmount;
	}

	public BigDecimal getActualBillingAmount() {
		return actualBillingAmount;
	}

	public void setActualBillingAmount(BigDecimal actualBillingAmount) {
		this.actualBillingAmount = actualBillingAmount;
	}
}
