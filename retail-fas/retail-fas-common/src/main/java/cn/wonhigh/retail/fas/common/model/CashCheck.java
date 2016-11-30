package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 17:36:27
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
public class CashCheck extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4531905228048341688L;

	private String shopNo;
	private String shopName;
	private String mallNo;
	private String mallName;
	private String orderNo;
	private String depositAccount;
	
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date outDate;
	
	private BigDecimal incomeAmount;
	private BigDecimal actualIncomeAmount;
	private BigDecimal endAmout;
	private BigDecimal depositAmount;
	private BigDecimal depositDiff;
	
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date depositDate;
	
	private String type;
	
	private Integer total;
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getDepositAccount() {
		return depositAccount;
	}
	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	public BigDecimal getIncomeAmount() {
		return incomeAmount;
	}
	public void setIncomeAmount(BigDecimal incomeAmount) {
		this.incomeAmount = incomeAmount;
	}
	public BigDecimal getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}
	public BigDecimal getDepositDiff() {
		return depositDiff;
	}
	public void setDepositDiff(BigDecimal depositDiff) {
		this.depositDiff = depositDiff;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getActualIncomeAmount() {
		return actualIncomeAmount;
	}
	public void setActualIncomeAmount(BigDecimal actualIncomeAmount) {
		this.actualIncomeAmount = actualIncomeAmount;
	}
	public BigDecimal getEndAmout() {
		return endAmout;
	}
	public void setEndAmout(BigDecimal endAmout) {
		this.endAmout = endAmout;
	}
	public Date getDepositDate() {
		return depositDate;
	}
	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
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
	
}