package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.PaidTypeEnums;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;


/**
 * 预收款缴款通知单
 * @author admin
 * @date  2014-09-22 12:14:38
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@ExportFormat(className=AbstractExportFormat.class)
public class BillPrePaymentNt extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = 2235631499677619391L;

	/**
     * 单据编码
     */
    private String billNo;

    /**
     * 结算类型
     */
    private Integer balanceType;

	/**
     * 结算主体编码
     */
    private String companyNo;

    /**
     * 结算主体名称
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
     * 单据日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date billDate;

    /**
     * 销售订单编码
     */
    private String saleOrderNo;

    /**
     * 订单金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal orderAmount;

    /**
     * 应收金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal receivableAmount;

    /**
     * 实收金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal paidAmount;

    /**
     * 收款类型编号
     */
    private Integer paidType;
    
    /**
     * 订单预收款余额（订单已收款）
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal prePaymentOver;

    /**
     * 冲销金额
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal reversalAmount;

    /**
     * 是否冲销客户预收余额（0 = 是 1 = 否）
     */
    private Integer reversalAmountFlag;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 发票号
     */
    private String invoiceNo;
    
    /**
     * 发票类型
     */
    private String invoiceType;
    
    /**
     * 发票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
 	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date invoiceDate;

    /**
     * 订单数量
     */
    private Integer orderQty;
    
    /**
     * 收款条款编码
     */
    private String termNo;
    
    /**
     * 收款条款
     */
    private String termName;
    
    /**
     * 返利额度
     */
    private BigDecimal rebateAmount;
    
    /**
     * 订货预收款
     */
    private BigDecimal preOrderAmount;
    
    /**
     * 发货预收款
     */
    private BigDecimal preSendAmount;
    
    /**
     * 收款类型名称
     */
    private String paidTypeName;
    
    /**
     * 收款日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
 	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date paidDate;
    
    public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getPaidTypeName() {
		return paidTypeName;
	}

	public void setPaidTypeName(String paidTypeName) {
		this.paidTypeName = paidTypeName;
	}

	public Integer getPaidType() {
		return paidType;
	}

	public void setPaidType(Integer paidType) {
		this.paidType = paidType;
		setPaidTypeName(PaidTypeEnums.getNameByNo(paidType));
	}

	public Integer getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(Integer orderQty) {
		this.orderQty = orderQty;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public BigDecimal getPreOrderAmount() {
		return preOrderAmount;
	}

	public void setPreOrderAmount(BigDecimal preOrderAmount) {
		this.preOrderAmount = preOrderAmount;
	}

	public BigDecimal getPreSendAmount() {
		return preSendAmount;
	}

	public void setPreSendAmount(BigDecimal preSendAmount) {
		this.preSendAmount = preSendAmount;
	}

	/**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_pre_payment_nt.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_pre_payment_nt.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
    
    public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
	}

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_pre_payment_nt.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_pre_payment_nt.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_pre_payment_nt.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_pre_payment_nt.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #customerNo}
     *
     * @return the value of bill_pre_payment_nt.customer_no
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 
     * {@linkplain #customerNo}
     * @param customerNo the value for bill_pre_payment_nt.customer_no
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 
     * {@linkplain #customerName}
     *
     * @return the value of bill_pre_payment_nt.customer_name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 
     * {@linkplain #customerName}
     * @param customerName the value for bill_pre_payment_nt.customer_name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 
     * {@linkplain #billDate}
     *
     * @return the value of bill_pre_payment_nt.bill_date
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     * @param billDate the value for bill_pre_payment_nt.bill_date
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * {@linkplain #saleOrderNo}
     *
     * @return the value of bill_pre_payment_nt.sale_order_no
     */
    public String getSaleOrderNo() {
        return saleOrderNo;
    }

    /**
     * 
     * {@linkplain #saleOrderNo}
     * @param saleOrderNo the value for bill_pre_payment_nt.sale_order_no
     */
    public void setSaleOrderNo(String saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    /**
     * 
     * {@linkplain #orderAmount}
     *
     * @return the value of bill_pre_payment_nt.order_amount
     */
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    /**
     * 
     * {@linkplain #orderAmount}
     * @param orderAmount the value for bill_pre_payment_nt.order_amount
     */
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(BigDecimal receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	/**
     * 
     * {@linkplain #paidAmount}
     *
     * @return the value of bill_pre_payment_nt.paid_amount
     */
    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    /**
     * 
     * {@linkplain #paidAmount}
     * @param paidAmount the value for bill_pre_payment_nt.paid_amount
     */
    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * 
     * {@linkplain #prePaymentOver}
     *
     * @return the value of bill_pre_payment_nt.pre_payment_over
     */
    public BigDecimal getPrePaymentOver() {
        return prePaymentOver;
    }

    /**
     * 
     * {@linkplain #prePaymentOver}
     * @param prePaymentOver the value for bill_pre_payment_nt.pre_payment_over
     */
    public void setPrePaymentOver(BigDecimal prePaymentOver) {
        this.prePaymentOver = prePaymentOver;
    }

    /**
     * 
     * {@linkplain #reversalAmount}
     *
     * @return the value of bill_pre_payment_nt.reversal_amount
     */
    public BigDecimal getReversalAmount() {
        return reversalAmount;
    }

    /**
     * 
     * {@linkplain #reversalAmount}
     * @param reversalAmount the value for bill_pre_payment_nt.reversal_amount
     */
    public void setReversalAmount(BigDecimal reversalAmount) {
        this.reversalAmount = reversalAmount;
    }

    /**
     * 
     * {@linkplain #reversalAmountFlag}
     *
     * @return the value of bill_pre_payment_nt.reversal_amount_flag
     */
    public Integer getReversalAmountFlag() {
        return reversalAmountFlag;
    }

    /**
     * 
     * {@linkplain #reversalAmountFlag}
     * @param reversalAmountFlag the value for bill_pre_payment_nt.reversal_amount_flag
     */
    public void setReversalAmountFlag(Integer reversalAmountFlag) {
        this.reversalAmountFlag = reversalAmountFlag;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_pre_payment_nt.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_pre_payment_nt.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
}