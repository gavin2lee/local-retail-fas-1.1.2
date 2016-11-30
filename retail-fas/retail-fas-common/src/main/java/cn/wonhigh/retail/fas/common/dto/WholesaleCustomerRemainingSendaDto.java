package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.WholeSaleSendaTypeEnums;
import cn.wonhigh.retail.fas.common.model.SequenceId;
import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingSum;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 森达客户余额明细查询结果集
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
public class WholesaleCustomerRemainingSendaDto  implements Serializable{
	private static final long serialVersionUID = 8703041348810857086L;   
	
	/**
     * 公司编码
     */
    private String companyNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 客户编码
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;
    
    /**
     * 交易日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
 	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date billDate;
    
    /**
     * 业务类型
     */
    private Integer bizType;
    
    /**
     * 业务类型名称
     */
    private String bizTypeName;
    
    /**
     * 单据编码
     */
    private String billNo;

    /**
     * 期初客户余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal firstRemaining ;

    /**
     * 期初未开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal firstUninvoiced;

    /**
     * 批发出库数量
     */
	private Integer sendQty           ;

    /**
     * 批发出库金额
     */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal sendAmount     ;

    /**
     * 批发退货金额
     */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal returnAmount   ;

    /**
     * 扣项金额
     */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal deductionAmount;

    /**
     * 收款金额
     */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal receiveAmount  ;

    /**
     * 客户余额
     */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal remainingAmount;

    /**
     * 开票金额
     */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal invoicedAmount ;

    /**
     * 累计未开票金额
     */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal totalUninvoiced;

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
		setBizTypeName(WholeSaleSendaTypeEnums.getNameByNo(bizType));
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public BigDecimal getFirstRemaining() {
		return firstRemaining;
	}

	public void setFirstRemaining(BigDecimal firstRemaining) {
		this.firstRemaining = firstRemaining;
	}

	public BigDecimal getFirstUninvoiced() {
		return firstUninvoiced;
	}

	public void setFirstUninvoiced(BigDecimal firstUninvoiced) {
		this.firstUninvoiced = firstUninvoiced;
	}

	public Integer getSendQty() {
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}

	public BigDecimal getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount = sendAmount;
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

	public BigDecimal getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(BigDecimal remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public BigDecimal getInvoicedAmount() {
		return invoicedAmount;
	}

	public void setInvoicedAmount(BigDecimal invoicedAmount) {
		this.invoicedAmount = invoicedAmount;
	}

	public BigDecimal getTotalUninvoiced() {
		return totalUninvoiced;
	}

	public void setTotalUninvoiced(BigDecimal totalUninvoiced) {
		this.totalUninvoiced = totalUninvoiced;
	}

	public String getBizTypeName() {
		return bizTypeName;
	}

	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}
	
}