package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.model.FasBaseModel;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 结算差异跟踪相关的dto类
 * @author Administrator
 * @date  2015-01-22 10:14:42
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
public class BillShopBalanceDiffTrackDto  extends FasBaseModel {

    /** 根结算差异id */
    private String rootDiffId;

    /** 差异编号 */
    private String diffBillNo;

    /** 结算单据编号 */
    private String balanceNo;

    /** 调整金额 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal adjustAmount;
    
    /** 调整原因 */
    private String adjustReason;
    
    /** 调整日期 */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date adjustDate;
    
    /** 调整类型: 1-差异调整 2-差异回款*/
    private Integer adjustType;

    /**
     * 差异结算日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date diffDate;

    /**
     * 结算日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceDate;

    /**
     * 费用所属期间-会计期间结算月
     */
    private String month;
    
    
	public String getRootDiffId() {
		return rootDiffId;
	}

	public void setRootDiffId(String rootDiffId) {
		this.rootDiffId = rootDiffId;
	}

	public String getDiffBillNo() {
		return diffBillNo;
	}

	public void setDiffBillNo(String diffBillNo) {
		this.diffBillNo = diffBillNo;
	}

	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}

	public BigDecimal getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(BigDecimal adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	public Date getAdjustDate() {
		return adjustDate;
	}

	public void setAdjustDate(Date adjustDate) {
		this.adjustDate = adjustDate;
	}

	public Integer getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(Integer adjustType) {
		this.adjustType = adjustType;
	}

	public Date getDiffDate() {
		return diffDate;
	}

	public void setDiffDate(Date diffDate) {
		this.diffDate = diffDate;
	}

	public Date getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}