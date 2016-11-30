package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;

/**
 * 单据头对象，用于作废单据
 * 
 * @author yang.y
 */
public class BillHeaderApiDto implements Serializable {

	private static final long serialVersionUID = -3823213337677961342L;

	/** 单据编码 */
	private String billNo;
	
	/** 单据类型 */
	private Integer billType;
	
	/** 业务类型 */
	private Integer bizType;

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

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}
}
