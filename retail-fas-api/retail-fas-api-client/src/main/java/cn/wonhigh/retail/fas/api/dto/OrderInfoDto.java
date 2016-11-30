package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;

public class OrderInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8506196104795999096L;
	
	/**
	 * 单据编码
	 */
	private String billNo;
	
	/**
	 * 单据类型(0-现金存入单1-销售订单 99-内购订单)
	 */
	private Integer billType;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}
}
