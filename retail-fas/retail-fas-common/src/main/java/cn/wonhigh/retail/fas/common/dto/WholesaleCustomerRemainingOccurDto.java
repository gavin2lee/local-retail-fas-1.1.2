package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.model.SequenceId;
import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingSum;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

/**
 * 批发客户余额发生查询结果集
 * @author user
 * @date  2016-06-06 17:11:35
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class WholesaleCustomerRemainingOccurDto extends WholesaleCustomerRemainingSum implements Serializable{
    
	private static final long serialVersionUID = 8703041348810957086L;
	
	/**
	 * 客户全称
	 */
    private String fullName;
    
    /**
     * 品牌部
     */
    private String brandUnitNames;
    
    /**
     * 收款
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal paidAmount;
    
    /**  
     * 本期出库
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal sendAmount;
    
    /**
     * 期初冻结金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal periodFirstFrozen;
    
    /**
     * 期初余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal periodFirstAmount;
    
    /**
     * 期初账面余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal periodFirstBookAmount;
    
    /**
     * 本期收款
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal periodInAmount;
    
    /**
     * 本期出库额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal periodOutAmount;
    
    /**
     * 本期冻结金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal periodFrozenAmount;
    
    /**
     * 本期解冻金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal periodUnfrozenAmount;
    
    /**
     * 本期返利金额     
     */ 
    @JsonSerialize(using = BigDecimalSerializer$2.class)      
    private BigDecimal   periodRebateAmount ;
    
    /**
     * 本期其他费用    
     */ 
    @JsonSerialize(using = BigDecimalSerializer$2.class)      
    private BigDecimal   periodOtherPrice ;
    
    /**
     * 本期扣项         
     * */ 
    @JsonSerialize(using = BigDecimalSerializer$2.class)      
    private BigDecimal   periodDeduction ;
    
    /**
     * 本期退货   
     * */ 
    @JsonSerialize(using = BigDecimalSerializer$2.class)      
    private BigDecimal   returnAmount ;
    
    /**
     * 本期使用信贷次数 
     * */     
    private Integer  periodCredit ;
    
    /**
     * 期末冻结余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal periodLastFrozen;
    
    /**
     * 期末余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal periodLastAmount;
    
    /**
     * 期末账面余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal periodLastBookAmount;

    /**
     * 冻结客户金额
     */
    private BigDecimal frozenCustomerAmount;
    
    /**
     * 信贷额度
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal creditAmount;
    
    /**
     * 信贷限制次数
     */
    private Integer creditLimit;
    
    /**
     * 可用信贷次数(年)
     */
    private Integer creditPerYear;
    
    /**
     * 已用信贷次数(年)
     */
    private Integer usedCreditCount;
    
    /**
     * 期末保证金余额   
     * */ 
    @JsonSerialize(using = BigDecimalSerializer$2.class)      
    private BigDecimal   periodLastMarginAmount ;
    
    /**
     * 期末可用信贷次数 
     * */      
    private Integer      periodLastCredit ;     
    
    /**
     * 期末前年度已用信贷总次数  
     * */      
    private Integer      totalCreditCounts ;

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount = sendAmount;
	}

	public BigDecimal getPeriodFirstFrozen() {
		return periodFirstFrozen;
	}

	public void setPeriodFirstFrozen(BigDecimal periodFirstFrozen) {
		this.periodFirstFrozen = periodFirstFrozen;
	}

	public BigDecimal getPeriodFirstAmount() {
		return periodFirstAmount;
	}

	public void setPeriodFirstAmount(BigDecimal periodFirstAmount) {
		this.periodFirstAmount = periodFirstAmount;
	}

	public BigDecimal getPeriodFirstBookAmount() {
		return periodFirstBookAmount;
	}

	public void setPeriodFirstBookAmount(BigDecimal periodFirstBookAmount) {
		this.periodFirstBookAmount = periodFirstBookAmount;
	}

	public BigDecimal getPeriodInAmount() {
		return periodInAmount;
	}

	public void setPeriodInAmount(BigDecimal periodInAmount) {
		this.periodInAmount = periodInAmount;
	}

	public BigDecimal getPeriodOutAmount() {
		return periodOutAmount;
	}

	public void setPeriodOutAmount(BigDecimal periodOutAmount) {
		this.periodOutAmount = periodOutAmount;
	}

	public BigDecimal getPeriodFrozenAmount() {
		return periodFrozenAmount;
	}

	public void setPeriodFrozenAmount(BigDecimal periodFrozenAmount) {
		this.periodFrozenAmount = periodFrozenAmount;
	}

	public BigDecimal getPeriodUnfrozenAmount() {
		return periodUnfrozenAmount;
	}

	public void setPeriodUnfrozenAmount(BigDecimal periodUnfrozenAmount) {
		this.periodUnfrozenAmount = periodUnfrozenAmount;
	}

	public BigDecimal getPeriodRebateAmount() {
		return periodRebateAmount;
	}

	public void setPeriodRebateAmount(BigDecimal periodRebateAmount) {
		this.periodRebateAmount = periodRebateAmount;
	}

	public BigDecimal getPeriodDeduction() {
		return periodDeduction;
	}

	public void setPeriodDeduction(BigDecimal periodDeduction) {
		this.periodDeduction = periodDeduction;
	}

	public Integer getPeriodCredit() {
		return periodCredit;
	}

	public void setPeriodCredit(Integer periodCredit) {
		this.periodCredit = periodCredit;
	}

	public BigDecimal getPeriodLastAmount() {
		return periodLastAmount;
	}

	public void setPeriodLastAmount(BigDecimal periodLastAmount) {
		this.periodLastAmount = periodLastAmount;
	}

	public BigDecimal getPeriodLastBookAmount() {
		return periodLastBookAmount;
	}

	public void setPeriodLastBookAmount(BigDecimal periodLastBookAmount) {
		this.periodLastBookAmount = periodLastBookAmount;
	}

	public BigDecimal getFrozenCustomerAmount() {
		return frozenCustomerAmount;
	}

	public void setFrozenCustomerAmount(BigDecimal frozenCustomerAmount) {
		this.frozenCustomerAmount = frozenCustomerAmount;
	}

	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Integer getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Integer creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Integer getCreditPerYear() {
		return creditPerYear;
	}

	public void setCreditPerYear(Integer creditPerYear) {
		this.creditPerYear = creditPerYear;
	}

	public Integer getUsedCreditCount() {
		return usedCreditCount;
	}

	public void setUsedCreditCount(Integer usedCreditCount) {
		this.usedCreditCount = usedCreditCount;
	}

	public BigDecimal getPeriodLastMarginAmount() {
		return periodLastMarginAmount;
	}

	public void setPeriodLastMarginAmount(BigDecimal periodLastMarginAmount) {
		this.periodLastMarginAmount = periodLastMarginAmount;
	}

	public Integer getPeriodLastCredit() {
		return periodLastCredit;
	}

	public void setPeriodLastCredit(Integer periodLastCredit) {
		this.periodLastCredit = periodLastCredit;
	}

	public Integer getTotalCreditCounts() {
		return totalCreditCounts;
	}

	public void setTotalCreditCounts(Integer totalCreditCounts) {
		this.totalCreditCounts = totalCreditCounts;
	}

	public BigDecimal getPeriodLastFrozen() {
		return periodLastFrozen;
	}

	public void setPeriodLastFrozen(BigDecimal periodLastFrozen) {
		this.periodLastFrozen = periodLastFrozen;
	}

	public BigDecimal getPeriodOtherPrice() {
		return periodOtherPrice;
	}

	public void setPeriodOtherPrice(BigDecimal periodOtherPrice) {
		this.periodOtherPrice = periodOtherPrice;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBrandUnitNames() {
		return brandUnitNames;
	}

	public void setBrandUnitNames(String brandUnitNames) {
		this.brandUnitNames = brandUnitNames;
	}  

}