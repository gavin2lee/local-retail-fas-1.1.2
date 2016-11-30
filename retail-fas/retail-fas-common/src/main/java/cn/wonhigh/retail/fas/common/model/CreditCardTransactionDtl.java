package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 17:35:01
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
public class CreditCardTransactionDtl extends FasBaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6940966326870841227L;

    /**
     * 流水号
     */
    private String seqNo;

    /**
     * 终端号
     */
    private String terminalNumber;

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 交易时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date dealTime;

    /**
     * 消费金额
     */
    private BigDecimal amount;
    
    private BigDecimal actualIncomeAmount;

    /**
     * 发卡行
     */
    private String givenBank;

    /**
     * 实际交易时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date realityDealTime;

    /**
     * 回扣费
     */
    private BigDecimal rebateAmount;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核时间
     */
    private Date auditTime;

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
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #seqNo}
     *
     * @return the value of credit_card_transaction_dtl.seq_no
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * 
     * {@linkplain #seqNo}
     * @param seqNo the value for credit_card_transaction_dtl.seq_no
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * 
     * {@linkplain #terminalNumber}
     *
     * @return the value of credit_card_transaction_dtl.terminal_number
     */
    public String getTerminalNumber() {
        return terminalNumber;
    }

    /**
     * 
     * {@linkplain #terminalNumber}
     * @param terminalNumber the value for credit_card_transaction_dtl.terminal_number
     */
    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    /**
     * 
     * {@linkplain #cardNumber}
     *
     * @return the value of credit_card_transaction_dtl.card_number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * 
     * {@linkplain #cardNumber}
     * @param cardNumber the value for credit_card_transaction_dtl.card_number
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * 
     * {@linkplain #dealTime}
     *
     * @return the value of credit_card_transaction_dtl.deal_time
     */
    public Date getDealTime() {
        return dealTime;
    }

    /**
     * 
     * {@linkplain #dealTime}
     * @param dealTime the value for credit_card_transaction_dtl.deal_time
     */
    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of credit_card_transaction_dtl.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for credit_card_transaction_dtl.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #givenBank}
     *
     * @return the value of credit_card_transaction_dtl.given_bank
     */
    public String getGivenBank() {
        return givenBank;
    }

    /**
     * 
     * {@linkplain #givenBank}
     * @param givenBank the value for credit_card_transaction_dtl.given_bank
     */
    public void setGivenBank(String givenBank) {
        this.givenBank = givenBank;
    }

    /**
     * 
     * {@linkplain #realityDealTime}
     *
     * @return the value of credit_card_transaction_dtl.reality_deal_time
     */
    public Date getRealityDealTime() {
        return realityDealTime;
    }

    /**
     * 
     * {@linkplain #realityDealTime}
     * @param realityDealTime the value for credit_card_transaction_dtl.reality_deal_time
     */
    public void setRealityDealTime(Date realityDealTime) {
        this.realityDealTime = realityDealTime;
    }

    /**
     * 
     * {@linkplain #rebateAmount}
     *
     * @return the value of credit_card_transaction_dtl.rebate_amount
     */
    public BigDecimal getRebateAmount() {
        return rebateAmount;
    }

    /**
     * 
     * {@linkplain #rebateAmount}
     * @param rebateAmount the value for credit_card_transaction_dtl.rebate_amount
     */
    public void setRebateAmount(BigDecimal rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    /**
     * 
     * {@linkplain #auditor}
     *
     * @return the value of credit_card_transaction_dtl.auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 
     * {@linkplain #auditor}
     * @param auditor the value for credit_card_transaction_dtl.auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /**
     * 
     * {@linkplain #auditTime}
     *
     * @return the value of credit_card_transaction_dtl.audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 
     * {@linkplain #auditTime}
     * @param auditTime the value for credit_card_transaction_dtl.audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of credit_card_transaction_dtl.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for credit_card_transaction_dtl.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of credit_card_transaction_dtl.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for credit_card_transaction_dtl.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of credit_card_transaction_dtl.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for credit_card_transaction_dtl.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public BigDecimal getActualIncomeAmount() {
		return actualIncomeAmount;
	}

	public void setActualIncomeAmount(BigDecimal actualIncomeAmount) {
		this.actualIncomeAmount = actualIncomeAmount;
	}
}