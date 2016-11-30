package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author yu.y
 * @date  2014-11-13 14:45:01
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
public class Ticket implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8903776960820723010L;

	/**
     * 主键ID,UUID
     */
    private String id;

    /**
     * 券号
     */
    private String ticketNo;

    /**
     * 券验证码
     */
    private String ticketCode;

    /**
     * 券生成单号
     */
    private String ticketDefineNo;

    /**
     * 券名称
     */
    private String ticketName;

    /**
     * 申请类型1-品牌 2-公司
     */
    private Byte applyType;

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
     * 抵扣类型,1-抵牌价，2-抵结算价
     */
    private Byte deductionType;

    /**
     * 是否购券标志 1-否 0-是
     */
    private Byte buyFlag;

    /**
     * 实收金额
     */
    private Integer buyAmount;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 禁退货,1-不允许退货，0-允许退货
     */
    private Byte returnFlag;

    /**
     * 禁换货,1-不允许换货，0-允许换货
     */
    private Byte changeFlag;

    /**
     * 券合用,1-不合用，0-合用
     */
    private Byte ticketShareFlag;

    /**
     * 是否可积分,1-否，0-是
     */
    private Byte scoreFlag;

    /**
     * 是否已卖,1-否，0-是
     */
    private Byte soldFlag;

    /**
     * 创建机构编号
     */
    private String organNo;

    /**
     * 创建机构名称
     */
    private String organName;

    /**
     * 发行机构编号
     */
    private String publishOrganNo;

    /**
     * 发行机构名称
     */
    private String publishOrganName;

    /**
     * 发券机构编号
     */
    private String giveOrganNo;

    /**
     * 发券机构名称
     */
    private String giveOrganName;

    /**
     * 发券时间
     */
    private Date giveTime;

    /**
     * 发券单号
     */
    private String giveBillNo;

    /**
     * 发券单据类型
     */
    private String giveBillType;

    /**
     * 用券机构编号
     */
    private String useOrganNo;

    /**
     * 用券机构名称
     */
    private String useOrganName;

    /**
     * 用券时间
     */
    private Date useTime;

    /**
     * 用券单号
     */
    private String useBillNo;

    /**
     * 用券单据类型
     */
    private String useBillType;

    /**
     * 状态,0-发行，1-已领，2-已用，3-过期，4-作废
     */
    private Byte status;

    /**
     * 使用说明
     */
    private String instructions;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createUserNo;

    /**
     * 创建人姓名
     */
    private String createUser;

    /**
     * 创建时间
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
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of ticket.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for ticket.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #ticketNo}
     *
     * @return the value of ticket.ticket_no
     */
    public String getTicketNo() {
        return ticketNo;
    }

    /**
     * 
     * {@linkplain #ticketNo}
     * @param ticketNo the value for ticket.ticket_no
     */
    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    /**
     * 
     * {@linkplain #ticketCode}
     *
     * @return the value of ticket.ticket_code
     */
    public String getTicketCode() {
        return ticketCode;
    }

    /**
     * 
     * {@linkplain #ticketCode}
     * @param ticketCode the value for ticket.ticket_code
     */
    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    /**
     * 
     * {@linkplain #ticketDefineNo}
     *
     * @return the value of ticket.ticket_define_no
     */
    public String getTicketDefineNo() {
        return ticketDefineNo;
    }

    /**
     * 
     * {@linkplain #ticketDefineNo}
     * @param ticketDefineNo the value for ticket.ticket_define_no
     */
    public void setTicketDefineNo(String ticketDefineNo) {
        this.ticketDefineNo = ticketDefineNo;
    }

    /**
     * 
     * {@linkplain #ticketName}
     *
     * @return the value of ticket.ticket_name
     */
    public String getTicketName() {
        return ticketName;
    }

    /**
     * 
     * {@linkplain #ticketName}
     * @param ticketName the value for ticket.ticket_name
     */
    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    /**
     * 
     * {@linkplain #applyType}
     *
     * @return the value of ticket.apply_type
     */
    public Byte getApplyType() {
        return applyType;
    }

    /**
     * 
     * {@linkplain #applyType}
     * @param applyType the value for ticket.apply_type
     */
    public void setApplyType(Byte applyType) {
        this.applyType = applyType;
    }

    /**
     * 
     * {@linkplain #ticketType}
     *
     * @return the value of ticket.ticket_type
     */
    public Byte getTicketType() {
        return ticketType;
    }

    /**
     * 
     * {@linkplain #ticketType}
     * @param ticketType the value for ticket.ticket_type
     */
    public void setTicketType(Byte ticketType) {
        this.ticketType = ticketType;
    }

    /**
     * 
     * {@linkplain #discType}
     *
     * @return the value of ticket.disc_type
     */
    public Byte getDiscType() {
        return discType;
    }

    /**
     * 
     * {@linkplain #discType}
     * @param discType the value for ticket.disc_type
     */
    public void setDiscType(Byte discType) {
        this.discType = discType;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of ticket.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for ticket.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #maxAmount}
     *
     * @return the value of ticket.max_amount
     */
    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    /**
     * 
     * {@linkplain #maxAmount}
     * @param maxAmount the value for ticket.max_amount
     */
    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    /**
     * 
     * {@linkplain #deductionType}
     *
     * @return the value of ticket.deduction_type
     */
    public Byte getDeductionType() {
        return deductionType;
    }

    /**
     * 
     * {@linkplain #deductionType}
     * @param deductionType the value for ticket.deduction_type
     */
    public void setDeductionType(Byte deductionType) {
        this.deductionType = deductionType;
    }

    /**
     * 
     * {@linkplain #buyFlag}
     *
     * @return the value of ticket.buy_flag
     */
    public Byte getBuyFlag() {
        return buyFlag;
    }

    /**
     * 
     * {@linkplain #buyFlag}
     * @param buyFlag the value for ticket.buy_flag
     */
    public void setBuyFlag(Byte buyFlag) {
        this.buyFlag = buyFlag;
    }

    /**
     * 
     * {@linkplain #buyAmount}
     *
     * @return the value of ticket.buy_amount
     */
    public Integer getBuyAmount() {
        return buyAmount;
    }

    /**
     * 
     * {@linkplain #buyAmount}
     * @param buyAmount the value for ticket.buy_amount
     */
    public void setBuyAmount(Integer buyAmount) {
        this.buyAmount = buyAmount;
    }

    /**
     * 
     * {@linkplain #startTime}
     *
     * @return the value of ticket.start_time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 
     * {@linkplain #startTime}
     * @param startTime the value for ticket.start_time
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     * {@linkplain #endTime}
     *
     * @return the value of ticket.end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 
     * {@linkplain #endTime}
     * @param endTime the value for ticket.end_time
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 
     * {@linkplain #returnFlag}
     *
     * @return the value of ticket.return_flag
     */
    public Byte getReturnFlag() {
        return returnFlag;
    }

    /**
     * 
     * {@linkplain #returnFlag}
     * @param returnFlag the value for ticket.return_flag
     */
    public void setReturnFlag(Byte returnFlag) {
        this.returnFlag = returnFlag;
    }

    /**
     * 
     * {@linkplain #changeFlag}
     *
     * @return the value of ticket.change_flag
     */
    public Byte getChangeFlag() {
        return changeFlag;
    }

    /**
     * 
     * {@linkplain #changeFlag}
     * @param changeFlag the value for ticket.change_flag
     */
    public void setChangeFlag(Byte changeFlag) {
        this.changeFlag = changeFlag;
    }

    /**
     * 
     * {@linkplain #ticketShareFlag}
     *
     * @return the value of ticket.ticket_share_flag
     */
    public Byte getTicketShareFlag() {
        return ticketShareFlag;
    }

    /**
     * 
     * {@linkplain #ticketShareFlag}
     * @param ticketShareFlag the value for ticket.ticket_share_flag
     */
    public void setTicketShareFlag(Byte ticketShareFlag) {
        this.ticketShareFlag = ticketShareFlag;
    }

    /**
     * 
     * {@linkplain #scoreFlag}
     *
     * @return the value of ticket.score_flag
     */
    public Byte getScoreFlag() {
        return scoreFlag;
    }

    /**
     * 
     * {@linkplain #scoreFlag}
     * @param scoreFlag the value for ticket.score_flag
     */
    public void setScoreFlag(Byte scoreFlag) {
        this.scoreFlag = scoreFlag;
    }

    /**
     * 
     * {@linkplain #soldFlag}
     *
     * @return the value of ticket.sold_flag
     */
    public Byte getSoldFlag() {
        return soldFlag;
    }

    /**
     * 
     * {@linkplain #soldFlag}
     * @param soldFlag the value for ticket.sold_flag
     */
    public void setSoldFlag(Byte soldFlag) {
        this.soldFlag = soldFlag;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of ticket.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for ticket.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of ticket.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for ticket.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #publishOrganNo}
     *
     * @return the value of ticket.publish_organ_no
     */
    public String getPublishOrganNo() {
        return publishOrganNo;
    }

    /**
     * 
     * {@linkplain #publishOrganNo}
     * @param publishOrganNo the value for ticket.publish_organ_no
     */
    public void setPublishOrganNo(String publishOrganNo) {
        this.publishOrganNo = publishOrganNo;
    }

    /**
     * 
     * {@linkplain #publishOrganName}
     *
     * @return the value of ticket.publish_organ_name
     */
    public String getPublishOrganName() {
        return publishOrganName;
    }

    /**
     * 
     * {@linkplain #publishOrganName}
     * @param publishOrganName the value for ticket.publish_organ_name
     */
    public void setPublishOrganName(String publishOrganName) {
        this.publishOrganName = publishOrganName;
    }

    /**
     * 
     * {@linkplain #giveOrganNo}
     *
     * @return the value of ticket.give_organ_no
     */
    public String getGiveOrganNo() {
        return giveOrganNo;
    }

    /**
     * 
     * {@linkplain #giveOrganNo}
     * @param giveOrganNo the value for ticket.give_organ_no
     */
    public void setGiveOrganNo(String giveOrganNo) {
        this.giveOrganNo = giveOrganNo;
    }

    /**
     * 
     * {@linkplain #giveOrganName}
     *
     * @return the value of ticket.give_organ_name
     */
    public String getGiveOrganName() {
        return giveOrganName;
    }

    /**
     * 
     * {@linkplain #giveOrganName}
     * @param giveOrganName the value for ticket.give_organ_name
     */
    public void setGiveOrganName(String giveOrganName) {
        this.giveOrganName = giveOrganName;
    }

    /**
     * 
     * {@linkplain #giveTime}
     *
     * @return the value of ticket.give_time
     */
    public Date getGiveTime() {
        return giveTime;
    }

    /**
     * 
     * {@linkplain #giveTime}
     * @param giveTime the value for ticket.give_time
     */
    public void setGiveTime(Date giveTime) {
        this.giveTime = giveTime;
    }

    /**
     * 
     * {@linkplain #giveBillNo}
     *
     * @return the value of ticket.give_bill_no
     */
    public String getGiveBillNo() {
        return giveBillNo;
    }

    /**
     * 
     * {@linkplain #giveBillNo}
     * @param giveBillNo the value for ticket.give_bill_no
     */
    public void setGiveBillNo(String giveBillNo) {
        this.giveBillNo = giveBillNo;
    }

    /**
     * 
     * {@linkplain #giveBillType}
     *
     * @return the value of ticket.give_bill_type
     */
    public String getGiveBillType() {
        return giveBillType;
    }

    /**
     * 
     * {@linkplain #giveBillType}
     * @param giveBillType the value for ticket.give_bill_type
     */
    public void setGiveBillType(String giveBillType) {
        this.giveBillType = giveBillType;
    }

    /**
     * 
     * {@linkplain #useOrganNo}
     *
     * @return the value of ticket.use_organ_no
     */
    public String getUseOrganNo() {
        return useOrganNo;
    }

    /**
     * 
     * {@linkplain #useOrganNo}
     * @param useOrganNo the value for ticket.use_organ_no
     */
    public void setUseOrganNo(String useOrganNo) {
        this.useOrganNo = useOrganNo;
    }

    /**
     * 
     * {@linkplain #useOrganName}
     *
     * @return the value of ticket.use_organ_name
     */
    public String getUseOrganName() {
        return useOrganName;
    }

    /**
     * 
     * {@linkplain #useOrganName}
     * @param useOrganName the value for ticket.use_organ_name
     */
    public void setUseOrganName(String useOrganName) {
        this.useOrganName = useOrganName;
    }

    /**
     * 
     * {@linkplain #useTime}
     *
     * @return the value of ticket.use_time
     */
    public Date getUseTime() {
        return useTime;
    }

    /**
     * 
     * {@linkplain #useTime}
     * @param useTime the value for ticket.use_time
     */
    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    /**
     * 
     * {@linkplain #useBillNo}
     *
     * @return the value of ticket.use_bill_no
     */
    public String getUseBillNo() {
        return useBillNo;
    }

    /**
     * 
     * {@linkplain #useBillNo}
     * @param useBillNo the value for ticket.use_bill_no
     */
    public void setUseBillNo(String useBillNo) {
        this.useBillNo = useBillNo;
    }

    /**
     * 
     * {@linkplain #useBillType}
     *
     * @return the value of ticket.use_bill_type
     */
    public String getUseBillType() {
        return useBillType;
    }

    /**
     * 
     * {@linkplain #useBillType}
     * @param useBillType the value for ticket.use_bill_type
     */
    public void setUseBillType(String useBillType) {
        this.useBillType = useBillType;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of ticket.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for ticket.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #instructions}
     *
     * @return the value of ticket.instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * 
     * {@linkplain #instructions}
     * @param instructions the value for ticket.instructions
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of ticket.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for ticket.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of ticket.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for ticket.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of ticket.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for ticket.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of ticket.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for ticket.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     *
     * @return the value of ticket.update_user_no
     */
    public String getUpdateUserNo() {
        return updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     * @param updateUserNo the value for ticket.update_user_no
     */
    public void setUpdateUserNo(String updateUserNo) {
        this.updateUserNo = updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of ticket.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for ticket.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of ticket.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for ticket.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #auditNo}
     *
     * @return the value of ticket.audit_no
     */
    public String getAuditNo() {
        return auditNo;
    }

    /**
     * 
     * {@linkplain #auditNo}
     * @param auditNo the value for ticket.audit_no
     */
    public void setAuditNo(String auditNo) {
        this.auditNo = auditNo;
    }

    /**
     * 
     * {@linkplain #auditor}
     *
     * @return the value of ticket.auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 
     * {@linkplain #auditor}
     * @param auditor the value for ticket.auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /**
     * 
     * {@linkplain #auditTime}
     *
     * @return the value of ticket.audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 
     * {@linkplain #auditTime}
     * @param auditTime the value for ticket.audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
}