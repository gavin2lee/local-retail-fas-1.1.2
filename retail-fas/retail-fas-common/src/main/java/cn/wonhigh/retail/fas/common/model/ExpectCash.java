package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-09-01 15:08:01
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
public class ExpectCash  extends FasBaseModel implements Serializable {
    /**
     * 主键ID,uuid生成
     */
    private String id;

    /**
     * 单据编号
     */
  /*  private String billNo;*/

    /**
     * 收款单据编号
     */
    private String refBillNo;

    /**
     * 结算编码
     */
    private String settleCode;

    /**
     * 门店编号
     */
    private String shopNo;

    /**
     * 门店名称
     */
    private String shopName;

    /**
     * 营业员主键ID
     */
    private String assistantId;

    /**
     * 营业员工号,与HR工号代码一致
     */
    private String assistantNo;

    /**
     * 营业员姓名
     */
    private String assistantName;

    /**
     * 日期
     */
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date businessDate;

    /**
     * 币种,0-人民币
     */
    private String currencyType;

    /**
     * 业务类型,1-商场预收 2-定金预收
     */
    private String businessType;

    /**
     * 凭证名称
     */
    private String businessName;

    /**
     * 业务标识,1-收款 2-退款
     */
    private String businessFlag;

    /**
     * 已使用金额
     */
    private BigDecimal usedAmount;

    /**
     * 金额
     */
    private BigDecimal amount;
    
    private BigDecimal expectCashAmount;
    
    private BigDecimal deductDiffAmount;
    
    private BigDecimal unUsedAmount;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系方式
     */
    private String tel;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态,0-有效 1-无效 默认为0
     */
    private Boolean status;

    /**
     * 是否确定,0-未确定 1-已确定 2-财务确认 默认为0
     */
    private Short confirmFlag;
    
    private String confirmFlagStr;

    /**
     * 促销活动编号
     */
    private String proNo;

    /**
     * 促销活动名称
     */
    private String proName;

    /**
     * 扣率编码
     */
    private String rateCode;

    /**
     * 扣率
     */
    private BigDecimal rate;

    /**
     * 品牌编号
     */
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;
    
    /**
     * 品牌编号
     */
    private String brandUnitNo;

    /**
     * 品牌名称
     */
    private String brandUnitName;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 类别名称
     */
    private String name;

//    /**
//     * 创建时间
//     */
//	@JsonSerialize(using = JsonDateSerializer$19.class)
//	@JsonDeserialize(using = JsonDateDeserialize$19.class)
//    private Date createTime;
//
    /**
     * 创建人编号
     */
    private String createUserNo;

//    /**
//     * 创建人姓名
//     */
//    private String createUser;

//    /**
//     * 确定时间
//     */
//	@JsonSerialize(using = JsonDateSerializer$19.class)
//	@JsonDeserialize(using = JsonDateDeserialize$19.class)
//    private Date auditTime;
//
    /**
     * 确定人编号
     */
    private String auditorNo;

//    /**
//     * 确定人姓名
//     */
//    private String auditor;

//    /**
//     * 最后修改时间
//     */
//	@JsonSerialize(using = JsonDateSerializer$19.class)
//	@JsonDeserialize(using = JsonDateDeserialize$19.class)
//    private Date updateTime;
//
//    /**
//     * 修改人姓名
//     */
//    private String updateUser;

    /**
     * 结算单据编号
     */
    private String balanceNo;

    /**
     * 结算状态 1-未结算、2-已结算
     */
    private Byte balanceStatus;
    
    private String balanceStatusStr;

    /**
     * 所属结算期
     */
    private String month;

    /**
     * 分库字段
     */
    private String shardingFlag;
    
    private String businessTypeExpectStr;
    private String businessFlagStr;
    private String currencyTypeStr;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of expect_cash.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for expect_cash.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of expect_cash.bill_no
     */
    /*public String getBillNo() {
        return billNo;
    }

    *//**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for expect_cash.bill_no
     *//*
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }*/

    /**
     * 
     * {@linkplain #refBillNo}
     *
     * @return the value of expect_cash.ref_bill_no
     */
    public String getRefBillNo() {
        return refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     * @param refBillNo the value for expect_cash.ref_bill_no
     */
    public void setRefBillNo(String refBillNo) {
        this.refBillNo = refBillNo;
    }

    /**
     * 
     * {@linkplain #settleCode}
     *
     * @return the value of expect_cash.settle_code
     */
    public String getSettleCode() {
        return settleCode;
    }

    /**
     * 
     * {@linkplain #settleCode}
     * @param settleCode the value for expect_cash.settle_code
     */
    public void setSettleCode(String settleCode) {
        this.settleCode = settleCode;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of expect_cash.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for expect_cash.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of expect_cash.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for expect_cash.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #assistantId}
     *
     * @return the value of expect_cash.assistant_id
     */
    public String getAssistantId() {
        return assistantId;
    }

    /**
     * 
     * {@linkplain #assistantId}
     * @param assistantId the value for expect_cash.assistant_id
     */
    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     *
     * @return the value of expect_cash.assistant_no
     */
    public String getAssistantNo() {
        return assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     * @param assistantNo the value for expect_cash.assistant_no
     */
    public void setAssistantNo(String assistantNo) {
        this.assistantNo = assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantName}
     *
     * @return the value of expect_cash.assistant_name
     */
    public String getAssistantName() {
        return assistantName;
    }

    /**
     * 
     * {@linkplain #assistantName}
     * @param assistantName the value for expect_cash.assistant_name
     */
    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    /**
     * 
     * {@linkplain #businessDate}
     *
     * @return the value of expect_cash.business_date
     */
    public Date getBusinessDate() {
        return businessDate;
    }

    /**
     * 
     * {@linkplain #businessDate}
     * @param businessDate the value for expect_cash.business_date
     */
    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    /**
     * 
     * {@linkplain #currencyType}
     *
     * @return the value of expect_cash.currency_type
     */
    public String getCurrencyType() {
        return currencyType;
    }

    /**
     * 
     * {@linkplain #currencyType}
     * @param currencyType the value for expect_cash.currency_type
     */
    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    /**
     * 
     * {@linkplain #businessType}
     *
     * @return the value of expect_cash.business_type
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * 
     * {@linkplain #businessType}
     * @param businessType the value for expect_cash.business_type
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     * 
     * {@linkplain #businessName}
     *
     * @return the value of expect_cash.business_name
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * 
     * {@linkplain #businessName}
     * @param businessName the value for expect_cash.business_name
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * 
     * {@linkplain #businessFlag}
     *
     * @return the value of expect_cash.business_flag
     */
    public String getBusinessFlag() {
        return businessFlag;
    }

    /**
     * 
     * {@linkplain #businessFlag}
     * @param businessFlag the value for expect_cash.business_flag
     */
    public void setBusinessFlag(String businessFlag) {
        this.businessFlag = businessFlag;
    }

    /**
     * 
     * {@linkplain #usedAmount}
     *
     * @return the value of expect_cash.used_amount
     */
    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    /**
     * 
     * {@linkplain #usedAmount}
     * @param usedAmount the value for expect_cash.used_amount
     */
    public void setUsedAmount(BigDecimal usedAmount) {
        this.usedAmount = usedAmount;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of expect_cash.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for expect_cash.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #customerName}
     *
     * @return the value of expect_cash.customer_name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 
     * {@linkplain #customerName}
     * @param customerName the value for expect_cash.customer_name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 
     * {@linkplain #contactName}
     *
     * @return the value of expect_cash.contact_name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 
     * {@linkplain #contactName}
     * @param contactName the value for expect_cash.contact_name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 
     * {@linkplain #tel}
     *
     * @return the value of expect_cash.tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * 
     * {@linkplain #tel}
     * @param tel the value for expect_cash.tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of expect_cash.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for expect_cash.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }


    /**
     * 
     * {@linkplain #confirmFlag}
     *
     * @return the value of expect_cash.confirm_flag
     */
    public Short getConfirmFlag() {
        return confirmFlag;
    }

    /**
     * 
     * {@linkplain #confirmFlag}
     * @param confirmFlag the value for expect_cash.confirm_flag
     */
    public void setConfirmFlag(Short confirmFlag) {
        this.confirmFlag = confirmFlag;
    }

    /**
     * 
     * {@linkplain #proNo}
     *
     * @return the value of expect_cash.pro_no
     */
    public String getProNo() {
        return proNo;
    }

    /**
     * 
     * {@linkplain #proNo}
     * @param proNo the value for expect_cash.pro_no
     */
    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    /**
     * 
     * {@linkplain #proName}
     *
     * @return the value of expect_cash.pro_name
     */
    public String getProName() {
        return proName;
    }

    /**
     * 
     * {@linkplain #proName}
     * @param proName the value for expect_cash.pro_name
     */
    public void setProName(String proName) {
        this.proName = proName;
    }

    /**
     * 
     * {@linkplain #rateCode}
     *
     * @return the value of expect_cash.rate_code
     */
    public String getRateCode() {
        return rateCode;
    }

    /**
     * 
     * {@linkplain #rateCode}
     * @param rateCode the value for expect_cash.rate_code
     */
    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    /**
     * 
     * {@linkplain #rate}
     *
     * @return the value of expect_cash.rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 
     * {@linkplain #rate}
     * @param rate the value for expect_cash.rate
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of expect_cash.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for expect_cash.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of expect_cash.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for expect_cash.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of expect_cash.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for expect_cash.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of expect_cash.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for expect_cash.name
     */
    public void setName(String name) {
        this.name = name;
    }

//    /**
//     * 
//     * {@linkplain #createTime}
//     *
//     * @return the value of expect_cash.create_time
//     */
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    /**
//     * 
//     * {@linkplain #createTime}
//     * @param createTime the value for expect_cash.create_time
//     */
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of expect_cash.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for expect_cash.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }
//
//    /**
//     * 
//     * {@linkplain #createUser}
//     *
//     * @return the value of expect_cash.create_user
//     */
//    public String getCreateUser() {
//        return createUser;
//    }
//
//    /**
//     * 
//     * {@linkplain #createUser}
//     * @param createUser the value for expect_cash.create_user
//     */
//    public void setCreateUser(String createUser) {
//        this.createUser = createUser;
//    }
//
//    /**
//     * 
//     * {@linkplain #auditTime}
//     *
//     * @return the value of expect_cash.audit_time
//     */
//    public Date getAuditTime() {
//        return auditTime;
//    }
//
//    /**
//     * 
//     * {@linkplain #auditTime}
//     * @param auditTime the value for expect_cash.audit_time
//     */
//    public void setAuditTime(Date auditTime) {
//        this.auditTime = auditTime;
//    }
//
    /**
     * 
     * {@linkplain #auditorNo}
     *
     * @return the value of expect_cash.auditor_no
     */
    public String getAuditorNo() {
        return auditorNo;
    }

    /**
     * 
     * {@linkplain #auditorNo}
     * @param auditorNo the value for expect_cash.auditor_no
     */
    public void setAuditorNo(String auditorNo) {
        this.auditorNo = auditorNo;
    }
//
//    /**
//     * 
//     * {@linkplain #auditor}
//     *
//     * @return the value of expect_cash.auditor
//     */
//    public String getAuditor() {
//        return auditor;
//    }
//
//    /**
//     * 
//     * {@linkplain #auditor}
//     * @param auditor the value for expect_cash.auditor
//     */
//    public void setAuditor(String auditor) {
//        this.auditor = auditor;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateTime}
//     *
//     * @return the value of expect_cash.update_time
//     */
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateTime}
//     * @param updateTime the value for expect_cash.update_time
//     */
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateUser}
//     *
//     * @return the value of expect_cash.update_user
//     */
//    public String getUpdateUser() {
//        return updateUser;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateUser}
//     * @param updateUser the value for expect_cash.update_user
//     */
//    public void setUpdateUser(String updateUser) {
//        this.updateUser = updateUser;
//    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of expect_cash.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for expect_cash.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceStatus}
     *
     * @return the value of expect_cash.balance_status
     */
    public Byte getBalanceStatus() {
        return balanceStatus;
    }

    /**
     * 
     * {@linkplain #balanceStatus}
     * @param balanceStatus the value for expect_cash.balance_status
     */
    public void setBalanceStatus(Byte balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of expect_cash.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for expect_cash.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     *
     * @return the value of expect_cash.sharding_flag
     */
    public String getShardingFlag() {
        return shardingFlag;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     * @param shardingFlag the value for expect_cash.sharding_flag
     */
    public void setShardingFlag(String shardingFlag) {
        this.shardingFlag = shardingFlag;
    }

	public BigDecimal getUnUsedAmount() {
		return unUsedAmount;
	}

	public void setUnUsedAmount(BigDecimal unUsedAmount) {
		this.unUsedAmount = unUsedAmount;
	}

	public BigDecimal getExpectCashAmount() {
		return expectCashAmount;
	}

	public void setExpectCashAmount(BigDecimal expectCashAmount) {
		this.expectCashAmount = expectCashAmount;
	}

	public BigDecimal getDeductDiffAmount() {
		return deductDiffAmount;
	}

	public void setDeductDiffAmount(BigDecimal deductDiffAmount) {
		this.deductDiffAmount = deductDiffAmount;
	}

	public String getBrandUnitNo() {
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	public String getConfirmFlagStr() {
		return confirmFlagStr;
	}

	public void setConfirmFlagStr(String confirmFlagStr) {
		this.confirmFlagStr = confirmFlagStr;
	}

	public String getBalanceStatusStr() {
		return balanceStatusStr;
	}

	public void setBalanceStatusStr(String balanceStatusStr) {
		this.balanceStatusStr = balanceStatusStr;
	}

	public String getBusinessTypeExpectStr() {
		return businessTypeExpectStr;
	}

	public void setBusinessTypeExpectStr(String businessTypeExpectStr) {
		this.businessTypeExpectStr = businessTypeExpectStr;
	}

	public String getBusinessFlagStr() {
		return businessFlagStr;
	}

	public void setBusinessFlagStr(String businessFlagStr) {
		this.businessFlagStr = businessFlagStr;
	}

	public String getCurrencyTypeStr() {
		return currencyTypeStr;
	}

	public void setCurrencyTypeStr(String currencyTypeStr) {
		this.currencyTypeStr = currencyTypeStr;
	}
	
}