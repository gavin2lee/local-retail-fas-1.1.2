package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$3;

/**
 * 费用金额汇总数据
 * 
 * @author yang.y
 */
public class GatherBillShopBalanceDeductDto implements Serializable {

	private static final long serialVersionUID = -8118735868374889948L;

	/** 系统扣费金额之和  */
	 @JsonSerialize(using = BigDecimalSerializer$3.class)
	private BigDecimal totalDeductAmount;
	
	/** 实际金额之和 */
	 @JsonSerialize(using = BigDecimalSerializer$3.class)
	private BigDecimal totalActualAmount;
	
	/** 扣费差异金额之和=系统扣费金额之和-实际金额之和 */
	 @JsonSerialize(using = BigDecimalSerializer$3.class)
	private BigDecimal totalDiffAmount;

	public BigDecimal getTotalDeductAmount() {
		return totalDeductAmount;
	}

	public void setTotalDeductAmount(BigDecimal totalDeductAmount) {
		this.totalDeductAmount = totalDeductAmount;
	}

	public BigDecimal getTotalActualAmount() {
		return totalActualAmount;
	}

	public void setTotalActualAmount(BigDecimal totalActualAmount) {
		this.totalActualAmount = totalActualAmount;
	}

	public BigDecimal getTotalDiffAmount() {
		return totalDiffAmount;
	}

	public void setTotalDiffAmount(BigDecimal totalDiffAmount) {
		this.totalDiffAmount = totalDiffAmount;
	}
}
