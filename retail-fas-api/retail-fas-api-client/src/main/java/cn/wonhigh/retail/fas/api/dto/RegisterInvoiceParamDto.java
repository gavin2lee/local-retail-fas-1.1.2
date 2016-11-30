package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.util.Date;

public class RegisterInvoiceParamDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 145069414257650242L;

	/**
	 * 公司
	 */
	private String companyNo;
	
	/**
	 * 买方编号
	 */
	private String buyerNo;
	
	/**
	 * 申请开始时间
	 */
	private Date startApplyDate;
	
	/**
	 * 申请结束时间
	 */
	private Date endApplyDate;
	
	/**
	 * 1: 普通发票   2: 登记发票
	 */
	private int invoiceType;
	
	/**
	 * 结算类型：pos 内购
	 */
	private String balanceType;

	public String getCompanyNo() {
		return companyNo;
	}

	public String getBuyerNo() {
		return buyerNo;
	}

	public Date getStartApplyDate() {
		return startApplyDate;
	}

	public Date getEndApplyDate() {
		return endApplyDate;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}

	public void setStartApplyDate(Date startApplyDate) {
		this.startApplyDate = startApplyDate;
	}

	public void setEndApplyDate(Date endApplyDate) {
		this.endApplyDate = endApplyDate;
	}

	public int getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(int invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Override
	public String toString() {
		return "RegisterInvoiceParamDto [companyNo=" + companyNo + ", buyerNo="
				+ buyerNo + ", startApplyDate=" + startApplyDate
				+ ", endApplyDate=" + endApplyDate + ", invoiceType="
				+ invoiceType + "]";
	}

	public String getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}
}
