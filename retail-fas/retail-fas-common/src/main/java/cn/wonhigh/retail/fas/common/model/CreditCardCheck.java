package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

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
public class CreditCardCheck extends FasBaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8204226891623133251L;

    /**
     * 销售日期
     */
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date outDate;

    /**
     * 终端号
     */
    private String terminalNumber;
    
    /**
     * 刷卡费率
     */
    private BigDecimal creditCardRate;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺简称
     */
    private String shopName;

    /**
     * 品牌部编码
     */
    private String brandUnitNo;

    /**
     * 品牌部简称
     */
    private String brandUnitName;

    /**
     * 商场编码
     */
    private String mallNo;

    /**
     * 商场名称
     */
    private String mallName;

    /**
     * 进账金额
     */
    private BigDecimal incomeAmount;
    
    /**
     * 终端绑定账号
     */
    private String creditCardAccount;

    /**
     * 实际进账金额
     */
    private BigDecimal actualIncomeAmount;

    /**
     * 冲账单金额
     */
    private BigDecimal returnAmount;

    /**
     * 系统刷卡差异
     */
    private BigDecimal creditCardDiff;
    
    /**
     * 网银交易金额
     */
    private BigDecimal onlineIncomeAmount;
    
    /**
     * 实际刷卡差异
     */
    private BigDecimal actualCreditCardDiff;
    
    private Integer total;
    
    /**
     * 0-销售 1-换货 2-退货
     */
    private Integer type;
    
    /**
     * 0-销售 1-换货 2-退货
     */
    private String typeName;
    
    /**
     * 订单号
     */
    private String orderNo;

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public String getTerminalNumber() {
		return terminalNumber;
	}

	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}

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

	public BigDecimal getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(BigDecimal incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	public String getCreditCardAccount() {
		return creditCardAccount;
	}

	public void setCreditCardAccount(String creditCardAccount) {
		this.creditCardAccount = creditCardAccount;
	}

	public BigDecimal getActualIncomeAmount() {
		return actualIncomeAmount;
	}

	public void setActualIncomeAmount(BigDecimal actualIncomeAmount) {
		this.actualIncomeAmount = actualIncomeAmount;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public BigDecimal getCreditCardDiff() {
		return creditCardDiff;
	}

	public void setCreditCardDiff(BigDecimal creditCardDiff) {
		this.creditCardDiff = creditCardDiff;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public BigDecimal getCreditCardRate() {
		return creditCardRate;
	}

	public void setCreditCardRate(BigDecimal creditCardRate) {
		this.creditCardRate = creditCardRate;
	}

	public String getBrandUnitNo() {
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public BigDecimal getOnlineIncomeAmount() {
		return onlineIncomeAmount;
	}

	public void setOnlineIncomeAmount(BigDecimal onlineIncomeAmount) {
		this.onlineIncomeAmount = onlineIncomeAmount;
	}

	public BigDecimal getActualCreditCardDiff() {
		return actualCreditCardDiff;
	}

	public void setActualCreditCardDiff(BigDecimal actualCreditCardDiff) {
		this.actualCreditCardDiff = actualCreditCardDiff;
	}

}