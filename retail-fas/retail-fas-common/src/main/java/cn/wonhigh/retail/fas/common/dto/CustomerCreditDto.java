package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

/**
 * 客户信贷信息
 * @author ouyang.zm
 * @date  2015-01-04 11:46:00
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
public class CustomerCreditDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6151723681605300142L;

	/**客户Id*/        
	private String customerId;
	
	/**客户信贷额度*/
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal creditAmount;
	
	/**客户信贷次数*/
	private Integer creditCount;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Integer getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(Integer creditCount) {
		this.creditCount = creditCount;
	}
	
	
	
}