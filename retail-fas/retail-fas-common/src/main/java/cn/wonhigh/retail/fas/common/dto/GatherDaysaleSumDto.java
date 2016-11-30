package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 汇总商场门店结算每日销售汇总数据的对象
 * 
 * @author yang.y
 */
public class GatherDaysaleSumDto implements Serializable {

	private static final long serialVersionUID = 4428164085920571501L;

	/** 销售总金额之和 */
	private BigDecimal totalSaleAmount;
	
	/** 金额之和 */
	private BigDecimal totalAmount;

	public BigDecimal getTotalSaleAmount() {
		return totalSaleAmount;
	}

	public void setTotalSaleAmount(BigDecimal totalSaleAmount) {
		this.totalSaleAmount = totalSaleAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
}
