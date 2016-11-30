package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BillStatusEnums;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-27 11:02:16
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
public class BillInvoice implements Serializable {
    /**
	 * 序列ID
	 */
	private static final long serialVersionUID = 1244745222310852048L;

	/**
     * 主键
     */
    private String id;

    /**
     * 结算类型
     */
    private Integer balanceType;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 单据日期
     */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class) 
    private Date billDate;

    /**
     * 单据状态
     */
    private Integer status;

    /**
     * 买方编码
     */
    private String salerNo;

    /**
     * 买方名称
     */
    private String salerName;

    /**
     * 卖方编码
     */
    private String buyerNo;

    /**
     * 卖方名称
     */
    private String buyerName;

    /**
     * 数量
     */
    private int qty;
    
    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 源单金额
     */
    private BigDecimal refAmount;
    
    /**
     * 付款数量
     */
    private int paymentQty;
    
    /**
     * 付款金额
     */
    private BigDecimal paymentAmount;
    
    /**
     * 币别
     */
    private String currency;
    
    /**
     * 币别名
     */
    private String currencyName;
    
    /**
     * 本位币
     */
    private String targetCurrencyNo;
    
    /**
     * 本位币名称
     */
    private String targetCurrencyName;
    
    /**
     * 本位币金额
     */
    private BigDecimal targetCurrencyAmount;
    
    /**
     * 汇率
     */
    private BigDecimal conversionFactor;

    /**
     * 源单编码
     */
    private String refBillNo;

    /**
     * 付款单号
     */
    private String paymentNo;
    
    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)   
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)   
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date updateTime;

    /**
     * 审批人
     */
    private String auditor;

    /**
     * 审批时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)   
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date auditTime;

    /**
     * 是否生成
     */
    private Integer isGenerate;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 单据状态
     */
    private String statusName;
    
    /**
     * id 集合
     */
    private String idList;
    
	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getPaymentQty() {
		return paymentQty;
	}

	public void setPaymentQty(int paymentQty) {
		this.paymentQty = paymentQty;
	}

	public BigDecimal getRefAmount() {
		return refAmount;
	}

	public void setRefAmount(BigDecimal refAmount) {
		this.refAmount = refAmount;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getIsGenerate() {
		return isGenerate;
	}

	public void setIsGenerate(Integer isGenerate) {
		this.isGenerate = isGenerate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_invoice.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_invoice.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #balanceType}
     *
     * @return the value of bill_invoice.balance_type
     */
    public Integer getBalanceType() {
        return balanceType;
    }

    /**
     * 
     * {@linkplain #balanceType}
     * @param balanceType the value for bill_invoice.balance_type
     */
    public void setBalanceType(Integer balanceType) {
        this.balanceType = balanceType;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_invoice.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_invoice.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #billDate}
     *
     * @return the value of bill_invoice.bill_date
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     * @param billDate the value for bill_invoice.bill_date
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of bill_invoice.status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for bill_invoice.status
     */
    public void setStatus(Integer status) {
        this.status = status;
        if(status != null){
        	setStatusName(BillStatusEnums.getNameByNo(status));
        }
    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of bill_invoice.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for bill_invoice.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of bill_invoice.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for bill_invoice.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of bill_invoice.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for bill_invoice.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of bill_invoice.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for bill_invoice.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of bill_invoice.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for bill_invoice.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #currency}
     *
     * @return the value of bill_invoice.currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 
     * {@linkplain #currency}
     * @param currency the value for bill_invoice.currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     *
     * @return the value of bill_invoice.ref_bill_no
     */
    public String getRefBillNo() {
        return refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     * @param refBillNo the value for bill_invoice.ref_bill_no
     */
    public void setRefBillNo(String refBillNo) {
        this.refBillNo = refBillNo;
    }
    
    /**
     * 
     * {@linkplain #paymentNo}
     * @param paymentNo the value for bill_invoice.payment_no
     */
    public String getPaymentNo() {
		return paymentNo;
	}
    
    /**
     * 
     * {@linkplain #paymentNo}
     * @param paymentNo the value for bill_invoice.payment_no
     */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	/**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of bill_invoice.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for bill_invoice.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of bill_invoice.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for bill_invoice.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of bill_invoice.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for bill_invoice.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of bill_invoice.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for bill_invoice.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #auditor}
     *
     * @return the value of bill_invoice.auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 
     * {@linkplain #auditor}
     * @param auditor the value for bill_invoice.auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /**
     * 
     * {@linkplain #auditTime}
     *
     * @return the value of bill_invoice.audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 
     * {@linkplain #auditTime}
     * @param auditTime the value for bill_invoice.audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getTargetCurrencyNo() {
		return targetCurrencyNo;
	}

	public void setTargetCurrencyNo(String targetCurrencyNo) {
		this.targetCurrencyNo = targetCurrencyNo;
	}

	public String getTargetCurrencyName() {
		return targetCurrencyName;
	}

	public void setTargetCurrencyName(String targetCurrencyName) {
		this.targetCurrencyName = targetCurrencyName;
	}

	public BigDecimal getTargetCurrencyAmount() {
		return targetCurrencyAmount;
	}

	public void setTargetCurrencyAmount(BigDecimal targetCurrencyAmount) {
		this.targetCurrencyAmount = targetCurrencyAmount;
	}

	public BigDecimal getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(BigDecimal conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
}