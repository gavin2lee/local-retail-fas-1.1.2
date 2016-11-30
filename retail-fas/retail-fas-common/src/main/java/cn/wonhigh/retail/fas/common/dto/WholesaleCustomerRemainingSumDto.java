package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.model.SequenceId;
import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingSum;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

/**
 * 批发客户余额查询结果集
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
public class WholesaleCustomerRemainingSumDto extends WholesaleCustomerRemainingSum implements Serializable{
    
	private static final long serialVersionUID = 8703041348810957086L;   
    
    /**
     * 客户账面余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal bookRemainingAmount;
    
    /**
     * 保证金金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal marginAmount;
    
    /**
     * 保证金余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal marginRemainderAmount;
    
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
     * 品牌部
     */
    private String brandUnitNames;
    
    /**
     * 可用信贷次数(年)
     */
    private Integer creditPerYear;
    
    /**
     * 已用信贷次数(年)
     */
    private Integer usedCreditCount;
    
    /**
     * 返利余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal rebateAmount;
    
	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public BigDecimal getBookRemainingAmount() {
		return bookRemainingAmount;
	}

	public void setBookRemainingAmount(BigDecimal bookRemainingAmount) {
		this.bookRemainingAmount = bookRemainingAmount;
	}

	public BigDecimal getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(BigDecimal marginAmount) {
		this.marginAmount = marginAmount;
	}

	public BigDecimal getMarginRemainderAmount() {
		return marginRemainderAmount;
	}

	public void setMarginRemainderAmount(BigDecimal marginRemainderAmount) {
		this.marginRemainderAmount = marginRemainderAmount;
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

	public String getBrandUnitNames() {
		return brandUnitNames;
	}

	public void setBrandUnitNames(String brandUnitNames) {
		this.brandUnitNames = brandUnitNames;
	}    

 
}