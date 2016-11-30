package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * TODO: 现金存入实体类
 * 
 * @author you.jp
 * @date 2014-9-4 下午3:45:41
 * @version 0.1.0 
 * @copyright Wonhigh Information Technology (Shenzhen) Co.,Ltd.
 */

public class POSDepositCashDto implements Serializable{
   
	private static final long serialVersionUID = -3686618332913333110L;

	/**
     * 主键ID,uuid生成
     */
    private String id;

    /**
     * 单据编号
     */
    private String billNo;
    
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
     * 起始销售日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date startOutDate;

    /**
     * 结束销售日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date endOutDate;
    
    /**
     * 币种,0-人民币
     */
    private Short currencyType;

    /**
     * 存入账户
     */
    private String account;

    /**
     * 存入金额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态,0-有效 1-无效 默认为0
     */
    private Integer status;

    /**
     * 是否确定,0-未确定 1-已确定 默认为0
     */
    private Short confirmFlag;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人编号
     */
    private String createUserNo;

    /**
     * 创建人姓名
     */
    private String createUser;

    /**
     * 确定时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date auditTime;

    /**
     * 确定人编号
     */
    private String auditorNo;

    /**
     * 确定人姓名
     */
    private String auditor;
    
    /**
     * 存入日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date depositDate;
    
    /**
     * 销售金额
     */
    private BigDecimal saleAmount;
    
    /**
     * 最后修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 最后修改人姓名
     */
    private String updateUser;
    
    /**
     * 币种,0-人民币(转换)
     */
    private String currencyTypeStr;
    
    /**
     * 是否确定,0-未确定 1-已确定 默认为0(转换)
     */
    private String confirmFlagStr;
    

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of deposit_cash.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for deposit_cash.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of deposit_cash.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for deposit_cash.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of deposit_cash.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for deposit_cash.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #assistantId}
     *
     * @return the value of deposit_cash.assistant_id
     */
    public String getAssistantId() {
        return assistantId;
    }

    /**
     * 
     * {@linkplain #assistantId}
     * @param assistantId the value for deposit_cash.assistant_id
     */
    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     *
     * @return the value of deposit_cash.assistant_no
     */
    public String getAssistantNo() {
        return assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     * @param assistantNo the value for deposit_cash.assistant_no
     */
    public void setAssistantNo(String assistantNo) {
        this.assistantNo = assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantName}
     *
     * @return the value of deposit_cash.assistant_name
     */
    public String getAssistantName() {
        return assistantName;
    }

    /**
     * 
     * {@linkplain #assistantName}
     * @param assistantName the value for deposit_cash.assistant_name
     */
    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    public Date getStartOutDate() {
		return startOutDate;
	}

	public void setStartOutDate(Date startOutDate) {
		this.startOutDate = startOutDate;
	}

	public Date getEndOutDate() {
		return endOutDate;
	}

	public void setEndOutDate(Date endOutDate) {
		this.endOutDate = endOutDate;
	}

	/**
     * 
     * {@linkplain #currencyType}
     *
     * @return the value of deposit_cash.currency_type
     */
    public Short getCurrencyType() {
        return currencyType;
    }

    /**
     * 
     * {@linkplain #currencyType}
     * @param currencyType the value for deposit_cash.currency_type
     */
    public void setCurrencyType(Short currencyType) {
        this.currencyType = currencyType;
    }

    /**
     * 
     * {@linkplain #account}
     *
     * @return the value of deposit_cash.account
     */
    public String getAccount() {
        return account;
    }

    /**
     * 
     * {@linkplain #account}
     * @param account the value for deposit_cash.account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of deposit_cash.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for deposit_cash.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of deposit_cash.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for deposit_cash.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of deposit_cash.status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for deposit_cash.status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #confirmFlag}
     *
     * @return the value of deposit_cash.confirm_flag
     */
    public Short getConfirmFlag() {
        return confirmFlag;
    }

    /**
     * 
     * {@linkplain #confirmFlag}
     * @param confirmFlag the value for deposit_cash.confirm_flag
     */
    public void setConfirmFlag(Short confirmFlag) {
        this.confirmFlag = confirmFlag;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of deposit_cash.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for deposit_cash.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of deposit_cash.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for deposit_cash.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of deposit_cash.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for deposit_cash.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #auditTime}
     *
     * @return the value of deposit_cash.audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 
     * {@linkplain #auditTime}
     * @param auditTime the value for deposit_cash.audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 
     * {@linkplain #auditorNo}
     *
     * @return the value of deposit_cash.auditor_no
     */
    public String getAuditorNo() {
        return auditorNo;
    }

    /**
     * 
     * {@linkplain #auditorNo}
     * @param auditorNo the value for deposit_cash.auditor_no
     */
    public void setAuditorNo(String auditorNo) {
        this.auditorNo = auditorNo;
    }

    /**
     * 
     * {@linkplain #auditor}
     *
     * @return the value of deposit_cash.auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 
     * {@linkplain #auditor}
     * @param auditor the value for deposit_cash.auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

	public String getCurrencyTypeStr() {
		return currencyTypeStr;
	}

	public void setCurrencyTypeStr(String currencyTypeStr) {
		this.currencyTypeStr = currencyTypeStr;
	}

	public String getConfirmFlagStr() {
		return confirmFlagStr;
	}

	public void setConfirmFlagStr(String confirmFlagStr) {
		this.confirmFlagStr = confirmFlagStr;
	}

	public Date getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}  
    
}