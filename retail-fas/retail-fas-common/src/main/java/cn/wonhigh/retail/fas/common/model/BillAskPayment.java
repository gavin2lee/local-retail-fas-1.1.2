package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.AuditStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.TsAskPaymentStatusEnums;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;


/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-28 13:49:07
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
public class BillAskPayment implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -631284903137457515L;

	/**
     * 主键(UUID)
     */
    private String id;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 买方编号
     */
    private String buyerNo;

    /**
     * 卖方编号
     */
    private String salerNo;

    /**
     * 买方名称
     */
    private String buyerName;

    /**
     * 卖方名称
     */
    private String salerName;

    /**
     * 单据日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
    private Date billDate;

    /**
     * 币别
     */
    private String currencyNo;
    
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
     * 请款金额
     */
    private BigDecimal allAmount;

    /**
     * 结算单金额
     */
    private BigDecimal balanceAmount;
    
    /**
     * 结算单号
     */
    private String balanceNo;

    /**
     * 单据状态(0-制单,1-审核)
     */
    private Integer status;

    /**
     * 结算类型(1、总部厂商结算,2、总部批发结算, 3、总部其他结算,4、地区采购结算 5、地区间结算 6、地区自购结算 7、地区批发结算 8、地区团购结算 9、地区员购结算 10、地区商场结算 11、地区其他出库结算)
     */
    private Integer balanceType;

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
     * 修改日期
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)  
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date updateTime;

    /**
     * 审批人
     */
    private String auditor;

    /**
     * 审批日期
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)  
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date auditTime;

    /**
     * 单据状态名称
     */
    private String statusName;
    
    /**
     * id集合
     */
    private String idList;
    
    /**
     * 单据日期开始
     */
    private Date billDateStart;
    
    /**
     * 单据日期结束
     */
    private Date billDateEnd;
    
    /**
     * 创建时间开始
     */
    private Date createTimeStart;
    
    /**
     * 创建时间结束
     */
    private Date createTimeEnd;
    
    /**
     * 审批时间开始
     */
    private Date auditTimeStart;
    
    /**
     * 审批时间结束
     */
    private Date auditTimeEnd;
 
    /**
     * 是否生成
     */
    private Integer isGenerate;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 结算类型名称
     */
    private String balanceTypeName;
    
    /**
     * 数量 
     */
    private Integer allQty;
    
    /**
     * 体总请款单状态
     */
    private String tsStatusName;
    
	public Integer getAllQty() {
		return allQty;
	}

	public void setAllQty(Integer allQty) {
		this.allQty = allQty;
	}

	public String getBalanceTypeName() {
		return balanceTypeName;
	}

	public void setBalanceTypeName(String balanceTypeName) {
		this.balanceTypeName = balanceTypeName;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Integer getIsGenerate() {
		return isGenerate;
	}

	public void setIsGenerate(Integer isGenerate) {
		this.isGenerate = isGenerate;
	}

	public Date getBillDateStart() {
		return billDateStart;
	}

	public void setBillDateStart(Date billDateStart) {
		this.billDateStart = billDateStart;
	}

	public Date getBillDateEnd() {
		return billDateEnd;
	}

	public void setBillDateEnd(Date billDateEnd) {
		this.billDateEnd = billDateEnd;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getAuditTimeStart() {
		return auditTimeStart;
	}

	public void setAuditTimeStart(Date auditTimeStart) {
		this.auditTimeStart = auditTimeStart;
	}

	public Date getAuditTimeEnd() {
		return auditTimeEnd;
	}

	public void setAuditTimeEnd(Date auditTimeEnd) {
		this.auditTimeEnd = auditTimeEnd;
	}

	public String getStatusName() {
		return statusName;
    }

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
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
     * @return the value of bill_ask_payment.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_ask_payment.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_ask_payment.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_ask_payment.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of bill_ask_payment.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for bill_ask_payment.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of bill_ask_payment.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for bill_ask_payment.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of bill_ask_payment.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for bill_ask_payment.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of bill_ask_payment.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for bill_ask_payment.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #billDate}
     *
     * @return the value of bill_ask_payment.bill_date
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     * @param billDate the value for bill_ask_payment.bill_date
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * {@linkplain #currencyNo}
     *
     * @return the value of bill_ask_payment.currency_no
     */
    public String getCurrencyNo() {
        return currencyNo;
    }

    /**
     * 
     * {@linkplain #currencyNo}
     * @param currencyNo the value for bill_ask_payment.currency_no
     */
    public void setCurrencyNo(String currencyNo) {
        this.currencyNo = currencyNo;
    }

    /**
     * 
     * {@linkplain #allAmount}
     *
     * @return the value of bill_ask_payment.all_amount
     */
    public BigDecimal getAllAmount() {
        return allAmount;
    }

    /**
     * 
     * {@linkplain #allAmount}
     * @param allAmount the value for bill_ask_payment.all_amount
     */
    public void setAllAmount(BigDecimal allAmount) {
        this.allAmount = allAmount;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_ask_payment.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_ask_payment.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of bill_ask_payment.status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for bill_ask_payment.status
     */
    public void setStatus(Integer status) {
        this.status = status;
        if(null != status){
        	setStatusName(AuditStatusEnums.getNameByNo(status));
        	setTsStatusName(TsAskPaymentStatusEnums.getTypeName(status));
        }
    }

    /**
     * 
     * {@linkplain #balanceType}
     *
     * @return the value of bill_ask_payment.balance_type
     */
    public Integer getBalanceType() {
        return balanceType;
    }

    /**
     * 
     * {@linkplain #balanceType}
     * @param balanceType the value for bill_ask_payment.balance_type
     */
    public void setBalanceType(Integer balanceType) {
        this.balanceType = balanceType;
        if(balanceType!=null){
        	setBalanceTypeName(BalanceTypeEnums.getTypeNameByNo(balanceType));
        }
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of bill_ask_payment.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for bill_ask_payment.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of bill_ask_payment.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for bill_ask_payment.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of bill_ask_payment.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for bill_ask_payment.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of bill_ask_payment.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for bill_ask_payment.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #auditor}
     *
     * @return the value of bill_ask_payment.auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 
     * {@linkplain #auditor}
     * @param auditor the value for bill_ask_payment.auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /**
     * 
     * {@linkplain #auditTime}
     *
     * @return the value of bill_ask_payment.audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 
     * {@linkplain #auditTime}
     * @param auditTime the value for bill_ask_payment.audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

	public String getTargetCurrencyNo() {
		return targetCurrencyNo;
	}

	public void setTargetCurrencyNo(String targetCurrencyNo) {
		this.targetCurrencyNo = targetCurrencyNo;
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

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getTargetCurrencyName() {
		return targetCurrencyName;
	}

	public void setTargetCurrencyName(String targetCurrencyName) {
		this.targetCurrencyName = targetCurrencyName;
	}

	public String getTsStatusName() {
		return tsStatusName;
	}

	public void setTsStatusName(String tsStatusName) {
		this.tsStatusName = tsStatusName;
	}
	
}