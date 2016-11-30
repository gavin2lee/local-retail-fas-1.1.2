package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-13 15:21:28
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
public class BillAskPaymentDtl implements Serializable,SequenceId {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8932390570155312150L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 结算方式
     */
    private String settleMethodNo;

    /**
     * 结算方式名称
     */
    private String settleMethodName;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 对方银行
     */
    private String otherBank;

    /**
     * 对方银行账号
     */
    private String otherBankAccount;
    
    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 大类编码
     */
    private String categoryNo;

    /**
     * 大类名称
     */
    private String categoryName;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 系统到期日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
    private Date sysExpirationDate;
    
    /**
     * 要求付款日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
    private Date paymentDate;

    public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_ask_payment_dtl.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_ask_payment_dtl.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_ask_payment_dtl.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_ask_payment_dtl.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #settleMethodNo}
     *
     * @return the value of bill_ask_payment_dtl.settle_method_no
     */
    public String getSettleMethodNo() {
        return settleMethodNo;
    }

    /**
     * 
     * {@linkplain #settleMethodNo}
     * @param settleMethodNo the value for bill_ask_payment_dtl.settle_method_no
     */
    public void setSettleMethodNo(String settleMethodNo) {
        this.settleMethodNo = settleMethodNo;
    }

    /**
     * 
     * {@linkplain #settleMethodName}
     *
     * @return the value of bill_ask_payment_dtl.settle_method_name
     */
    public String getSettleMethodName() {
        return settleMethodName;
    }

    /**
     * 
     * {@linkplain #settleMethodName}
     * @param settleMethodName the value for bill_ask_payment_dtl.settle_method_name
     */
    public void setSettleMethodName(String settleMethodName) {
        this.settleMethodName = settleMethodName;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     *
     * @return the value of bill_ask_payment_dtl.invoice_no
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     * @param invoiceNo the value for bill_ask_payment_dtl.invoice_no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of bill_ask_payment_dtl.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for bill_ask_payment_dtl.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #otherBank}
     *
     * @return the value of bill_ask_payment_dtl.other_bank
     */
    public String getOtherBank() {
        return otherBank;
    }

    /**
     * 
     * {@linkplain #otherBank}
     * @param otherBank the value for bill_ask_payment_dtl.other_bank
     */
    public void setOtherBank(String otherBank) {
        this.otherBank = otherBank;
    }

    /**
     * 
     * {@linkplain #otherBankAccount}
     *
     * @return the value of bill_ask_payment_dtl.other_bank_account
     */
    public String getOtherBankAccount() {
        return otherBankAccount;
    }

    /**
     * 
     * {@linkplain #otherBankAccount}
     * @param otherBankAccount the value for bill_ask_payment_dtl.other_bank_account
     */
    public void setOtherBankAccount(String otherBankAccount) {
        this.otherBankAccount = otherBankAccount;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_ask_payment_dtl.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_ask_payment_dtl.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public Date getSysExpirationDate() {
		return sysExpirationDate;
	}

	public void setSysExpirationDate(Date sysExpirationDate) {
		this.sysExpirationDate = sysExpirationDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
    
    
}