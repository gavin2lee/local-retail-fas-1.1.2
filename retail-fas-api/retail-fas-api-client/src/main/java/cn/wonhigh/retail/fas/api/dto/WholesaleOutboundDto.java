package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WholesaleOutboundDto implements Serializable {

	private static final long serialVersionUID = 7404595563152299991L;
	
	/**
	 * 订单编号
	 */
	private String billNo;
	
	/**
	 * 出库金额
	 */
	private BigDecimal amount;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
