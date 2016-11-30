package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author yu.y
 * @date  2014-11-13 15:44:38
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
public class TicketCollect implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2682052285002554759L;

	/**
     * 主键ID,UUID
     */
    private String id;

    /**
     * 收款单号
     */
    private String collectNo;

    /**
     * 客户编号
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 券生成编号
     */
    private String ticketDefineNo;

    /**
     * 券名称
     */
    private String ticketName;

    /**
     * 收款日期
     */
    private Date collectDate;

    /**
     * 销售数量
     */
    private Integer sellQty;

    /**
     * 收款金额
     */
    private BigDecimal collectAmount;

    /**
     * 收款原因
     */
    private String collectReason;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 券类型,1-优惠券，2-现金券
     */
    private Byte ticketType;

    /**
     * 优惠类型,1-金额，2-折扣
     */
    private Byte discType;

    /**
     * 金额/折扣
     */
    private BigDecimal amount;

    /**
     * 最大折扣金额
     */
    private BigDecimal maxAmount;

    /**
     * 实收金额
     */
    private Integer buyAmount;

    /**
     * 抵扣类型,1-抵牌价，2-抵结算价
     */
    private Byte deductionType;

    /**
     * 发行机构编号
     */
    private String publishOrganNo;

    /**
     * 发行机构名称
     */
    private String publishOrganName;

    /**
     * 发行数量
     */
    private Integer publishQty;

    /**
     * 状态,0-制单，100-确认,110-作废
     */
    private Byte status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建机构
     */
    private String organNo;

    /**
     * 创建机构名称
     */
    private String organName;

    /**
     * 建档人
     */
    private String createUserNo;

    /**
     * 建档人姓名
     */
    private String createUser;

    /**
     * 建档时间
     */
    private Date createTime;

    /**
     * 最后修改人
     */
    private String updateUserNo;

    /**
     * 最后修改人姓名
     */
    private String updateUser;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 确认人
     */
    private String auditNo;

    /**
     * 确认人姓名
     */
    private String auditor;

    /**
     * 确认时间
     */
    private Date auditTime;

    /**
     * 开票日期
     */
    private Date invoiceDate;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of ticket_collect.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for ticket_collect.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #collectNo}
     *
     * @return the value of ticket_collect.collect_no
     */
    public String getCollectNo() {
        return collectNo;
    }

    /**
     * 
     * {@linkplain #collectNo}
     * @param collectNo the value for ticket_collect.collect_no
     */
    public void setCollectNo(String collectNo) {
        this.collectNo = collectNo;
    }

    /**
     * 
     * {@linkplain #customerNo}
     *
     * @return the value of ticket_collect.customer_no
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 
     * {@linkplain #customerNo}
     * @param customerNo the value for ticket_collect.customer_no
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 
     * {@linkplain #customerName}
     *
     * @return the value of ticket_collect.customer_name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 
     * {@linkplain #customerName}
     * @param customerName the value for ticket_collect.customer_name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 
     * {@linkplain #ticketDefineNo}
     *
     * @return the value of ticket_collect.ticket_define_no
     */
    public String getTicketDefineNo() {
        return ticketDefineNo;
    }

    /**
     * 
     * {@linkplain #ticketDefineNo}
     * @param ticketDefineNo the value for ticket_collect.ticket_define_no
     */
    public void setTicketDefineNo(String ticketDefineNo) {
        this.ticketDefineNo = ticketDefineNo;
    }

    /**
     * 
     * {@linkplain #ticketName}
     *
     * @return the value of ticket_collect.ticket_name
     */
    public String getTicketName() {
        return ticketName;
    }

    /**
     * 
     * {@linkplain #ticketName}
     * @param ticketName the value for ticket_collect.ticket_name
     */
    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    /**
     * 
     * {@linkplain #collectDate}
     *
     * @return the value of ticket_collect.collect_date
     */
    public Date getCollectDate() {
        return collectDate;
    }

    /**
     * 
     * {@linkplain #collectDate}
     * @param collectDate the value for ticket_collect.collect_date
     */
    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    /**
     * 
     * {@linkplain #sellQty}
     *
     * @return the value of ticket_collect.sell_qty
     */
    public Integer getSellQty() {
        return sellQty;
    }

    /**
     * 
     * {@linkplain #sellQty}
     * @param sellQty the value for ticket_collect.sell_qty
     */
    public void setSellQty(Integer sellQty) {
        this.sellQty = sellQty;
    }

    /**
     * 
     * {@linkplain #collectAmount}
     *
     * @return the value of ticket_collect.collect_amount
     */
    public BigDecimal getCollectAmount() {
        return collectAmount;
    }

    /**
     * 
     * {@linkplain #collectAmount}
     * @param collectAmount the value for ticket_collect.collect_amount
     */
    public void setCollectAmount(BigDecimal collectAmount) {
        this.collectAmount = collectAmount;
    }

    /**
     * 
     * {@linkplain #collectReason}
     *
     * @return the value of ticket_collect.collect_reason
     */
    public String getCollectReason() {
        return collectReason;
    }

    /**
     * 
     * {@linkplain #collectReason}
     * @param collectReason the value for ticket_collect.collect_reason
     */
    public void setCollectReason(String collectReason) {
        this.collectReason = collectReason;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     *
     * @return the value of ticket_collect.invoice_no
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     * @param invoiceNo the value for ticket_collect.invoice_no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 
     * {@linkplain #ticketType}
     *
     * @return the value of ticket_collect.ticket_type
     */
    public Byte getTicketType() {
        return ticketType;
    }

    /**
     * 
     * {@linkplain #ticketType}
     * @param ticketType the value for ticket_collect.ticket_type
     */
    public void setTicketType(Byte ticketType) {
        this.ticketType = ticketType;
    }

    /**
     * 
     * {@linkplain #discType}
     *
     * @return the value of ticket_collect.disc_type
     */
    public Byte getDiscType() {
        return discType;
    }

    /**
     * 
     * {@linkplain #discType}
     * @param discType the value for ticket_collect.disc_type
     */
    public void setDiscType(Byte discType) {
        this.discType = discType;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of ticket_collect.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for ticket_collect.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #maxAmount}
     *
     * @return the value of ticket_collect.max_amount
     */
    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    /**
     * 
     * {@linkplain #maxAmount}
     * @param maxAmount the value for ticket_collect.max_amount
     */
    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    /**
     * 
     * {@linkplain #buyAmount}
     *
     * @return the value of ticket_collect.buy_amount
     */
    public Integer getBuyAmount() {
        return buyAmount;
    }

    /**
     * 
     * {@linkplain #buyAmount}
     * @param buyAmount the value for ticket_collect.buy_amount
     */
    public void setBuyAmount(Integer buyAmount) {
        this.buyAmount = buyAmount;
    }

    /**
     * 
     * {@linkplain #deductionType}
     *
     * @return the value of ticket_collect.deduction_type
     */
    public Byte getDeductionType() {
        return deductionType;
    }

    /**
     * 
     * {@linkplain #deductionType}
     * @param deductionType the value for ticket_collect.deduction_type
     */
    public void setDeductionType(Byte deductionType) {
        this.deductionType = deductionType;
    }

    /**
     * 
     * {@linkplain #publishOrganNo}
     *
     * @return the value of ticket_collect.publish_organ_no
     */
    public String getPublishOrganNo() {
        return publishOrganNo;
    }

    /**
     * 
     * {@linkplain #publishOrganNo}
     * @param publishOrganNo the value for ticket_collect.publish_organ_no
     */
    public void setPublishOrganNo(String publishOrganNo) {
        this.publishOrganNo = publishOrganNo;
    }

    /**
     * 
     * {@linkplain #publishOrganName}
     *
     * @return the value of ticket_collect.publish_organ_name
     */
    public String getPublishOrganName() {
        return publishOrganName;
    }

    /**
     * 
     * {@linkplain #publishOrganName}
     * @param publishOrganName the value for ticket_collect.publish_organ_name
     */
    public void setPublishOrganName(String publishOrganName) {
        this.publishOrganName = publishOrganName;
    }

    /**
     * 
     * {@linkplain #publishQty}
     *
     * @return the value of ticket_collect.publish_qty
     */
    public Integer getPublishQty() {
        return publishQty;
    }

    /**
     * 
     * {@linkplain #publishQty}
     * @param publishQty the value for ticket_collect.publish_qty
     */
    public void setPublishQty(Integer publishQty) {
        this.publishQty = publishQty;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of ticket_collect.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for ticket_collect.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of ticket_collect.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for ticket_collect.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of ticket_collect.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for ticket_collect.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of ticket_collect.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for ticket_collect.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of ticket_collect.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for ticket_collect.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of ticket_collect.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for ticket_collect.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of ticket_collect.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for ticket_collect.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     *
     * @return the value of ticket_collect.update_user_no
     */
    public String getUpdateUserNo() {
        return updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     * @param updateUserNo the value for ticket_collect.update_user_no
     */
    public void setUpdateUserNo(String updateUserNo) {
        this.updateUserNo = updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of ticket_collect.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for ticket_collect.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of ticket_collect.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for ticket_collect.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #auditNo}
     *
     * @return the value of ticket_collect.audit_no
     */
    public String getAuditNo() {
        return auditNo;
    }

    /**
     * 
     * {@linkplain #auditNo}
     * @param auditNo the value for ticket_collect.audit_no
     */
    public void setAuditNo(String auditNo) {
        this.auditNo = auditNo;
    }

    /**
     * 
     * {@linkplain #auditor}
     *
     * @return the value of ticket_collect.auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 
     * {@linkplain #auditor}
     * @param auditor the value for ticket_collect.auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /**
     * 
     * {@linkplain #auditTime}
     *
     * @return the value of ticket_collect.audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 
     * {@linkplain #auditTime}
     * @param auditTime the value for ticket_collect.audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     *
     * @return the value of ticket_collect.invoice_date
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     * @param invoiceDate the value for ticket_collect.invoice_date
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}