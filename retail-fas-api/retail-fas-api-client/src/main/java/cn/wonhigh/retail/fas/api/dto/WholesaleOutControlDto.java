package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WholesaleOutControlDto implements Serializable {

	private static final long serialVersionUID = 7404595563152299991L;

	/**订单编号*/private String orderNo         ;
    /**公司编号*/private String companyNo       ;
    /**客户编号*/private String customerNo      ;
    /**出库金额*/private BigDecimal outAmount   ;
    /**返利金额*/private BigDecimal rebateAmount;
    /**贷款额度*/private BigDecimal creditAmount;
    /**贷款次数*/private Integer creditCount    ;
    
	public String getCompanyNo() {
		return companyNo;
	}
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public BigDecimal getOutAmount() {
		return outAmount;
	}
	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}
	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}
	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}
	public Integer getCreditCount() {
		return creditCount;
	}
	public void setCreditCount(Integer creditCount) {
		this.creditCount = creditCount;
	}


}
