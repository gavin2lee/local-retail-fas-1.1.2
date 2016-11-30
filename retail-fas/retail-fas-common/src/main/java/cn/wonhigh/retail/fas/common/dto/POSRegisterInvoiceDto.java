package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * TODO: 发票登记实体类
 * 
 * @author you.jp
 * @date 2014-9-4 下午3:45:41
 * @version 0.1.0 
 * @copyright Wonhigh Information Technology (Shenzhen) Co.,Ltd.
 */

public class POSRegisterInvoiceDto implements Serializable{
  
	private static final long serialVersionUID = -3189718682080421966L;

	/**
     * 主键ID,uuid生成
     */
    private String id;
    
	/**
     * 单据编号
     */
    private String billNo;
    
    /**
     * 销售订单编号
     */
    private String orderNo;

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
     * 销售日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date outDate;

    /**
     * 开票登记日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date registerDate;

    /**
     * 应开票金额
     */
    private BigDecimal shouldAmount;

    /**
     * 实际开票金额
     */
    private BigDecimal actualAmount;

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
     * 差异金额
     */
    private BigDecimal diffAmount;
    
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
     * 是否确定,0-未确定 1-已确定 默认为0(转换)
     */
    private String confirmFlagStr;
    
    
    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of register_invoice.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for register_invoice.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of register_invoice.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }
     
    public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for register_invoice.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of register_invoice.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for register_invoice.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #assistantId}
     *
     * @return the value of register_invoice.assistant_id
     */
    public String getAssistantId() {
        return assistantId;
    }

    /**
     * 
     * {@linkplain #assistantId}
     * @param assistantId the value for register_invoice.assistant_id
     */
    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     *
     * @return the value of register_invoice.assistant_no
     */
    public String getAssistantNo() {
        return assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     * @param assistantNo the value for register_invoice.assistant_no
     */
    public void setAssistantNo(String assistantNo) {
        this.assistantNo = assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantName}
     *
     * @return the value of register_invoice.assistant_name
     */
    public String getAssistantName() {
        return assistantName;
    }

    /**
     * 
     * {@linkplain #assistantName}
     * @param assistantName the value for register_invoice.assistant_name
     */
    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    /**
     * 
     * {@linkplain #outDate}
     *
     * @return the value of register_invoice.out_date
     */
    public Date getOutDate() {
        return outDate;
    }

    /**
     * 
     * {@linkplain #outDate}
     * @param outDate the value for register_invoice.out_date
     */
    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    /**
     * 
     * {@linkplain #registerDate}
     *
     * @return the value of register_invoice.register_date
     */
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * 
     * {@linkplain #registerDate}
     * @param registerDate the value for register_invoice.register_date
     */
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * 
     * {@linkplain #shouldAmount}
     *
     * @return the value of register_invoice.should_amount
     */
    public BigDecimal getShouldAmount() {
        return shouldAmount;
    }

    /**
     * 
     * {@linkplain #shouldAmount}
     * @param shouldAmount the value for register_invoice.should_amount
     */
    public void setShouldAmount(BigDecimal shouldAmount) {
        this.shouldAmount = shouldAmount;
    }

    /**
     * 
     * {@linkplain #actualAmount}
     *
     * @return the value of register_invoice.actual_amount
     */
    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    /**
     * 
     * {@linkplain #actualAmount}
     * @param actualAmount the value for register_invoice.actual_amount
     */
    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of register_invoice.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for register_invoice.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of register_invoice.status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for register_invoice.status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #confirmFlag}
     *
     * @return the value of register_invoice.confirm_flag
     */
    public Short getConfirmFlag() {
        return confirmFlag;
    }

    /**
     * 
     * {@linkplain #confirmFlag}
     * @param confirmFlag the value for register_invoice.confirm_flag
     */
    public void setConfirmFlag(Short confirmFlag) {
        this.confirmFlag = confirmFlag;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of register_invoice.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for register_invoice.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of register_invoice.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for register_invoice.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of register_invoice.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for register_invoice.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #auditTime}
     *
     * @return the value of register_invoice.audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 
     * {@linkplain #auditTime}
     * @param auditTime the value for register_invoice.audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 
     * {@linkplain #auditorNo}
     *
     * @return the value of register_invoice.auditor_no
     */
    public String getAuditorNo() {
        return auditorNo;
    }

    /**
     * 
     * {@linkplain #auditorNo}
     * @param auditorNo the value for register_invoice.auditor_no
     */
    public void setAuditorNo(String auditorNo) {
        this.auditorNo = auditorNo;
    }

    /**
     * 
     * {@linkplain #auditor}
     *
     * @return the value of register_invoice.auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 
     * {@linkplain #auditor}
     * @param auditor the value for register_invoice.auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

	public BigDecimal getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(BigDecimal diffAmount) {
		this.diffAmount = diffAmount;
	}

	public String getConfirmFlagStr() {
		return confirmFlagStr;
	}

	public void setConfirmFlagStr(String confirmFlagStr) {
		this.confirmFlagStr = confirmFlagStr;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
    
    
}