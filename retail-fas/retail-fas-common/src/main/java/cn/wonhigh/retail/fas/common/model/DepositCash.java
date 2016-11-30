package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.DepositCashExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-22 13:51:56
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
@ExportFormat(className=DepositCashExportFormat.class)
public class DepositCash extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2784064392066399992L;

	/**
     * 门店编号
     */
    private String shopNo;

    /**
     * 门店名称
     */
    private String shopName;
    
    /**
     * 公司编号
     */
    private String companyNo;
    
    /**
     * 公司名称
     */
    private String companyName;
    
    /**
     * 公司编号
     */
    private String mallNo;
    
    /**
     * 公司名称
     */
    private String mallName;
    
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
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date startOutDate;

    /**
     * 结束销售日期
     */
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date endOutDate;

    /**
     * 币种,0-人民币
     */
    private Short currencyType;
    
    /**
     * 币种 名称
     */
    private String currencyTypeName;

    /**
     * 存入账户
     */
    private String account;

    /**
     * 存入金额
     */
    private BigDecimal amount;
    
    /**
     * 已存金额
     */
    private BigDecimal existAmount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否确定,0-未确定 1-已确定 默认为0
     */
    private Short confirmFlag;

    /**
     * 创建人编号
     */
    private String createUserNo;

    /**
     * 确定人编号
     */
    private String auditorNo;

    /**
     * 存入日期
     */
	@JsonSerialize(using = JsonDateSerializer$10.class)
    private Date depositDate;
	
	/**
	 * 银行流水号
	 */
	private String transactionNo;

    /**
     * 销售金额
     */
    private BigDecimal saleAmount;

    /**
     * 存现差异
     */
    private BigDecimal depositDiff;
    
    /**
     * 差异原因
     */
    private String cashDiff;
    
    /**
     * 最后修改人姓名
     */
    private String updateUser;

    /**
     * 最后修改时间
     */
    private Date updateTime;
    

    private BigDecimal depositAmount;
    
    
    /**
     * 币种,0-人民币(转换)
     */
    private String currencyTypeStr;
    

	/**
     * 是否确定,0-未确定 1-已确定 默认为0(转换)
     */
    private String confirmFlagStr;
    
    /**
     * 总记录数
     */
    private int total;
    
    /**
     * 分库字段
     */
    private String shardingFlag;
    
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
    
    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of deposit_cash.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
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

    /**
     * 
     * {@linkplain #startOutDate}
     *
     * @return the value of deposit_cash.start_out_date
     */
    public Date getStartOutDate() {
        return startOutDate;
    }

    /**
     * 
     * {@linkplain #startOutDate}
     * @param startOutDate the value for deposit_cash.start_out_date
     */
    public void setStartOutDate(Date startOutDate) {
        this.startOutDate = startOutDate;
    }

    /**
     * 
     * {@linkplain #endOutDate}
     *
     * @return the value of deposit_cash.end_out_date
     */
    public Date getEndOutDate() {
        return endOutDate;
    }

    /**
     * 
     * {@linkplain #endOutDate}
     * @param endOutDate the value for deposit_cash.end_out_date
     */
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
     * {@linkplain #confirmFlag}
     * @param confirmFlag the value for deposit_cash.confirm_flag
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
     * {@linkplain #depositDate}
     *
     * @return the value of deposit_cash.deposit_date
     */
    public Date getDepositDate() {
        return depositDate;
    }

    /**
     * 
     * {@linkplain #depositDate}
     * @param depositDate the value for deposit_cash.deposit_date
     */
    public void setDepositDate(Date depositDate) {
        this.depositDate = depositDate;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     *
     * @return the value of deposit_cash.sale_amount
     */
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     * @param saleAmount the value for deposit_cash.sale_amount
     */
    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of deposit_cash.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for deposit_cash.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of deposit_cash.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for deposit_cash.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getCurrencyTypeName() {
		return currencyTypeName;
	}

	public void setCurrencyTypeName(String currencyTypeName) {
		this.currencyTypeName = currencyTypeName;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

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

	public String getCashDiff() {
		return cashDiff;
	}

	public void setCashDiff(String cashDiff) {
		this.cashDiff = cashDiff;
	}

	public BigDecimal getDepositDiff() {
		return depositDiff;
	}

	public void setDepositDiff(BigDecimal depositDiff) {
		this.depositDiff = depositDiff;
	}

	public String getMallNo() {
		return mallNo;
	}

	public void setMallNo(String mallNo) {
		this.mallNo = mallNo;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public BigDecimal getExistAmount() {
		return existAmount;
	}

	public void setExistAmount(BigDecimal existAmount) {
		this.existAmount = existAmount;
	}

	public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
}