package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.WholesaleRemainingTypeEnums;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

/**
 * 批发客户余额明细查询结果集
 * @author user
 * @date  2016-06-06 17:11:35
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class CustomerRemainDtlDto implements Serializable{
	
	private static final long serialVersionUID = 8703041348810957066L;   
	
	/**公司编号 */ 
	private String companyNo;
	/**公司名称 */ 
	private String companyName;
	/**客户编号 */ 
	private String customerNo;
	/**客户名称 */ 
	private String customerName;
	/**日期 */  
	private Date billDate;
	/**业务类型 */ 
	private Integer bizType; 
    /**业务类型*/
    private String bizTypeName;
	/**单据编码 */ 
	private String billNo;
	/**期初客户余额 */  
	@JsonSerialize(using = BigDecimalSerializer$2.class) 
	private BigDecimal firstCustomerRemain;
	/**期初未开票金额 */  
	@JsonSerialize(using = BigDecimalSerializer$2.class) 
	private BigDecimal firstInvoiceAmount;
	/**批发出库数量 */  
	private Integer    sendQty;
	/**批发出库金额 */  
	@JsonSerialize(using = BigDecimalSerializer$2.class) 
	private BigDecimal outAmount;
	/**批发退货金额 */  
	@JsonSerialize(using = BigDecimalSerializer$2.class) 
	private BigDecimal returnAmount;
	/**扣项金额 */  
	@JsonSerialize(using = BigDecimalSerializer$2.class) 
	private BigDecimal deductionAmount;
	/**收款金额 */  
	@JsonSerialize(using = BigDecimalSerializer$2.class) 
	private BigDecimal receiveAmount;
	/**客户余额 */  
	@JsonSerialize(using = BigDecimalSerializer$2.class) 
	private BigDecimal customerRemain;
	/**开票金额 */  
	@JsonSerialize(using = BigDecimalSerializer$2.class) 
	private BigDecimal invoiceAmount;
	/**累计未开票金额 */  
	@JsonSerialize(using = BigDecimalSerializer$2.class) 
	private BigDecimal uninvoiceAmount;
	public String getCompanyNo() {
		return companyNo;
	}
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public Integer getBizType() {
		return bizType;
	}
	public void setBizType(Integer bizType) {
		this.bizType = bizType;
		if (null != bizType) {
			setBizTypeName(WholesaleRemainingTypeEnums.getTypeNameByNo(bizType));
		}
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public BigDecimal getFirstCustomerRemain() {
		return firstCustomerRemain;
	}
	public void setFirstCustomerRemain(BigDecimal firstCustomerRemain) {
		this.firstCustomerRemain = firstCustomerRemain;
	}
	public BigDecimal getFirstInvoiceAmount() {
		return firstInvoiceAmount;
	}
	public void setFirstInvoiceAmount(BigDecimal firstInvoiceAmount) {
		this.firstInvoiceAmount = firstInvoiceAmount;
	}
	public Integer getSendQty() {
		return sendQty;
	}
	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}
	public BigDecimal getOutAmount() {
		return outAmount;
	}
	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}
	public BigDecimal getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}
	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}
	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}
	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}
	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public BigDecimal getCustomerRemain() {
		return customerRemain;
	}
	public void setCustomerRemain(BigDecimal customerRemain) {
		this.customerRemain = customerRemain;
	}
	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public BigDecimal getUninvoiceAmount() {
		return uninvoiceAmount;
	}
	public void setUninvoiceAmount(BigDecimal uninvoiceAmount) {
		this.uninvoiceAmount = uninvoiceAmount;
	}

	public String getBizTypeName() {
		return bizTypeName;
	}
	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}
	
}