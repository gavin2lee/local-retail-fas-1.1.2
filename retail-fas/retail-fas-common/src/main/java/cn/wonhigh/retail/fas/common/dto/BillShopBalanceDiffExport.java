package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.model.FasBaseModel;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$4;
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
public class BillShopBalanceDiffExport  extends FasBaseModel {
	
	/**
     * 单据编号
     */
    private String billNo;

    /**
     * 结算单据编号
     */
    private String balanceNo;

    /**
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 经营区域编号
     */
    private String zoneNo;

    /**
     * 经营区域名称
     */
    private String zoneName;

    /**
     * 管理城市编号
     */
    private String organNo;

    /**
     * 管理城市名称
     */
    private String organName;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺简称
     */
    private String shortName;

    /**
     * 品牌编码
     */
    private String brandNo;

    public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	private String  discountCode;
    /**
     * 品牌中文名称
     */
    private String brandName;
    
    /**
     * 结算码
     */
    private String billingCode;

    /**
     * 差异类型
     */
    private String diffTypeCode;

    /**
     * 差异类型
     */
    private String diffTypeName;

    /**
     * 费用所属期间-会计期间结算月
     */
    private String month;

    /**
     * 业务单据日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date billDate;

    /**
     * 促销活动编号
     */
    private String proNo;

    /**
     * 促销活动名称
     */
    private String proName;

    /**
     * 活动起始日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date proStartDate;

    /**
     * 活动终止日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date proEndDate;

    /**
     * 扣率,如0.17
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal discount;

    /**
     * 系统扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal deductDiffAmount;

    /**
     * 商场报数
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal mallNumber;

    /**
     * 系统收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal salesAmount;

    /**
     * 差异金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal salesDiffamount;

    /**
     * 扣费差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffAmount;
    
    /** 初始扣费差异，值等于扣费差异，用于改变扣费差异、调整金额时使用 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffAmountVal;

    /**
     * 差异原因
     */
    private String diffReason;

    /**
     * 本期差异余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffBalance;
    
    /** 初始本期差异余额，值等于扣费差异，用于改变扣费差异、调整金额时使用 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffBalanceVal;

    /**
     * 上期差异余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal preDiffBalance;
    
    /**
     * 差异回款
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffBackAmount;

    /**
     * 本次结算金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceAmount;

    /**
     * 原因
     */
    private String reason;

    /**
     * 调整金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal changeAmount;

    /**
     * 调整日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date changeDate;

    /**
     * 调整原因
     */
    private String changeReason;


    /**
     * 唯一标示，历史发生的记录
     */
    private String markId;
    
    /**
     * 最初的差异的id
     */
    private String rootDiffId;

    /**
     * 上期结算编号，滚动add
     */
    private String parBalanceNo;

    /**
     * 备注
     */
    private String remark;
   
    /**
     * 结算起始日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceStartDate;

    /**
     * 结算终止日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceEndDate;
    
    /**
     * 累计差异余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal  diffBalanceSum;
    
    /*
     * 累计差异回款
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffBackAmountSum;
    
    private String  discountN;
    
    /**
     * 报数差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal reportDiffAmount;
	
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceDiffAmount;
    
    /**
     * 生成方式（0：系统自动生成，1：在界面上新增）
     */
    private Integer generateType;
    
    /**
     * 分库字段
     */
    private String shardingFlag;

    /** 差异编号 */
    private String diffBillNoDiff;

    /** 结算单据编号 */
    private String balanceNoDiff;

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
    
    private String adjustTypeName;

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
    private String monthDiff;
    
    /**
     * 差异明细状态
     */
    private Integer statusDiff;
    
    /**
     * 差异明细状态
     */
    private String statusDiffName;
    
    
    
    
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getOrganNo() {
		return organNo;
	}

	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBillingCode() {
		return billingCode;
	}

	public void setBillingCode(String billingCode) {
		this.billingCode = billingCode;
	}

	public String getDiffTypeCode() {
		return diffTypeCode;
	}

	public void setDiffTypeCode(String diffTypeCode) {
		this.diffTypeCode = diffTypeCode;
	}

	public String getDiffTypeName() {
		return diffTypeName;
	}

	public void setDiffTypeName(String diffTypeName) {
		this.diffTypeName = diffTypeName;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getProNo() {
		return proNo;
	}

	public void setProNo(String proNo) {
		this.proNo = proNo;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public Date getProStartDate() {
		return proStartDate;
	}

	public void setProStartDate(Date proStartDate) {
		this.proStartDate = proStartDate;
	}

	public Date getProEndDate() {
		return proEndDate;
	}

	public void setProEndDate(Date proEndDate) {
		this.proEndDate = proEndDate;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getDeductDiffAmount() {
		return deductDiffAmount;
	}

	public void setDeductDiffAmount(BigDecimal deductDiffAmount) {
		this.deductDiffAmount = deductDiffAmount;
	}

	public BigDecimal getMallNumber() {
		return mallNumber;
	}

	public void setMallNumber(BigDecimal mallNumber) {
		this.mallNumber = mallNumber;
	}

	public BigDecimal getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(BigDecimal salesAmount) {
		this.salesAmount = salesAmount;
	}

	public BigDecimal getSalesDiffamount() {
		return salesDiffamount;
	}

	public void setSalesDiffamount(BigDecimal salesDiffamount) {
		this.salesDiffamount = salesDiffamount;
	}

	public BigDecimal getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(BigDecimal diffAmount) {
		this.diffAmount = diffAmount;
	}

	public BigDecimal getDiffAmountVal() {
		return diffAmountVal;
	}

	public void setDiffAmountVal(BigDecimal diffAmountVal) {
		this.diffAmountVal = diffAmountVal;
	}

	public String getDiffReason() {
		return diffReason;
	}

	public void setDiffReason(String diffReason) {
		this.diffReason = diffReason;
	}

	public BigDecimal getDiffBalance() {
		return diffBalance;
	}

	public void setDiffBalance(BigDecimal diffBalance) {
		this.diffBalance = diffBalance;
	}

	public BigDecimal getDiffBalanceVal() {
		return diffBalanceVal;
	}

	public void setDiffBalanceVal(BigDecimal diffBalanceVal) {
		this.diffBalanceVal = diffBalanceVal;
	}

	public BigDecimal getPreDiffBalance() {
		return preDiffBalance;
	}

	public void setPreDiffBalance(BigDecimal preDiffBalance) {
		this.preDiffBalance = preDiffBalance;
	}

	public BigDecimal getDiffBackAmount() {
		return diffBackAmount;
	}

	public void setDiffBackAmount(BigDecimal diffBackAmount) {
		this.diffBackAmount = diffBackAmount;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public BigDecimal getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public String getMarkId() {
		return markId;
	}

	public void setMarkId(String markId) {
		this.markId = markId;
	}

	public String getParBalanceNo() {
		return parBalanceNo;
	}

	public void setParBalanceNo(String parBalanceNo) {
		this.parBalanceNo = parBalanceNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getBalanceStartDate() {
		return balanceStartDate;
	}

	public void setBalanceStartDate(Date balanceStartDate) {
		this.balanceStartDate = balanceStartDate;
	}

	public Date getBalanceEndDate() {
		return balanceEndDate;
	}

	public void setBalanceEndDate(Date balanceEndDate) {
		this.balanceEndDate = balanceEndDate;
	}

	public BigDecimal getDiffBalanceSum() {
		return diffBalanceSum;
	}

	public void setDiffBalanceSum(BigDecimal diffBalanceSum) {
		this.diffBalanceSum = diffBalanceSum;
	}

	public BigDecimal getDiffBackAmountSum() {
		return diffBackAmountSum;
	}

	public void setDiffBackAmountSum(BigDecimal diffBackAmountSum) {
		this.diffBackAmountSum = diffBackAmountSum;
	}

	public String getDiscountN() {
		return discountN;
	}

	public void setDiscountN(String discountN) {
		this.discountN = discountN;
	}

	public BigDecimal getReportDiffAmount() {
		return reportDiffAmount;
	}

	public void setReportDiffAmount(BigDecimal reportDiffAmount) {
		this.reportDiffAmount = reportDiffAmount;
	}

	public Integer getGenerateType() {
		return generateType;
	}

	public void setGenerateType(Integer generateType) {
		this.generateType = generateType;
	}

	public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	public String getRootDiffId() {
		return rootDiffId;
	}

	public void setRootDiffId(String rootDiffId) {
		this.rootDiffId = rootDiffId;
	}

	

	public String getDiffBillNoDiff() {
		return diffBillNoDiff;
	}

	public void setDiffBillNoDiff(String diffBillNoDiff) {
		this.diffBillNoDiff = diffBillNoDiff;
	}

	public String getBalanceNoDiff() {
		return balanceNoDiff;
	}

	public void setBalanceNoDiff(String balanceNoDiff) {
		this.balanceNoDiff = balanceNoDiff;
	}

	public String getMonthDiff() {
		return monthDiff;
	}

	public void setMonthDiff(String monthDiff) {
		this.monthDiff = monthDiff;
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

	public Integer getStatusDiff() {
		return statusDiff;
	}

	public void setStatusDiff(Integer statusDiff) {
		this.statusDiff = statusDiff;
	}

	public String getStatusDiffName() {
		return statusDiffName;
	}

	public void setStatusDiffName(String statusDiffName) {
		this.statusDiffName = statusDiffName;
	}

	public String getAdjustTypeName() {
		return adjustTypeName;
	}

	public void setAdjustTypeName(String adjustTypeName) {
		this.adjustTypeName = adjustTypeName;
	}

	public BigDecimal getBalanceDiffAmount() {
		return balanceDiffAmount;
	}

	public void setBalanceDiffAmount(BigDecimal balanceDiffAmount) {
		this.balanceDiffAmount = balanceDiffAmount;
	}
	
}