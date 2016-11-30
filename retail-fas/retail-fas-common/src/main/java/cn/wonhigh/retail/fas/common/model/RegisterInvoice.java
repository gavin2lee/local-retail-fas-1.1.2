package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

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
public class RegisterInvoice extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9142818609045574371L;

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
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date outDate;

    /**
     * 开票登记日期
     */
    private Date registerDate;
    
    /**
     * 单据销售金额
     */
    private BigDecimal saleAmount;

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
     * 是否确定,0-未确定 1-已确定 默认为0
     */
    private Short confirmFlag;
    
    /**
     * 销售单据编号
     */
    private String orderNo;

    /**
     * 创建时间
     */
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
     * 最后修改人
     */
    private String updateUser;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 终端号
     */
    private String terminalNumber;
    
    /**
     * 发票差异
     */
    private BigDecimal diffAmount;
    
    public BigDecimal getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(BigDecimal diffAmount) {
		this.diffAmount = diffAmount;
	}

	public String getTerminalNumber() {
		return terminalNumber;
	}

	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
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

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of register_invoice.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for register_invoice.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of register_invoice.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for register_invoice.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
}