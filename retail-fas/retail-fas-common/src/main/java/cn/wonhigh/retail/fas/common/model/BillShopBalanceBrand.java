package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-11-06 14:39:19
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
public class BillShopBalanceBrand  extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private String id;

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

    /**
     * 品牌中文名称
     */
    private String brandName;

    /**
     * 费用所属期间-会计期间结算月
     */
    private String month;

    /**
     * 结算起始日
     */
    private Date balanceStartDate;

    /**
     * 结算终止日
     */
    private Date balanceEndDate;

    /**
     * 系统扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal deductDiffAmount;

    /**
     * 报数差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal reportDiffAmount;

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

    /**
     * 原因
     */
    private String reason;

    /**
     * 差异原因
     */
    private String diffReason;

    /**
     * 实际扣费金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal actualAmount;

    /**
     * 扣费总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal deductAllamount;

    /**
     * 开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal billingAmount;

    /**
     * 商场开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal mallBillingAmount;

    /**
     * 系统开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal systemBillingAmount;

    /**
     * 结算差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceDiffAmount;

    /**
     * 票前费用
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceDeductAmount;

    /**
     * 预收款金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal prepaymentAmount;

    /**
     * 已使用金额(冲销)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal usedPrepaymentAmount;

    /**
     * 系统差异额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal sysDeductAmount;

    /**
     * 调整金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal changeAmount;

    /**
     * 单据状态(1-未确认、2-已确认、3-已开票)
     */
    private Byte status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 建档人
     */
    private String createUser;

    /**
     * 建档时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 分库字段(本部加大区)
     */
    private String shardingFlag;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_shop_balance_brand.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_shop_balance_brand.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_shop_balance_brand.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_shop_balance_brand.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_shop_balance_brand.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_shop_balance_brand.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_shop_balance_brand.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_shop_balance_brand.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_shop_balance_brand.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_shop_balance_brand.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of bill_shop_balance_brand.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for bill_shop_balance_brand.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneName}
     *
     * @return the value of bill_shop_balance_brand.zone_name
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * {@linkplain #zoneName}
     * @param zoneName the value for bill_shop_balance_brand.zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of bill_shop_balance_brand.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for bill_shop_balance_brand.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of bill_shop_balance_brand.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for bill_shop_balance_brand.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_shop_balance_brand.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_shop_balance_brand.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_shop_balance_brand.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_shop_balance_brand.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_shop_balance_brand.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_shop_balance_brand.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_shop_balance_brand.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_shop_balance_brand.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of bill_shop_balance_brand.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for bill_shop_balance_brand.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     *
     * @return the value of bill_shop_balance_brand.balance_start_date
     */
    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     * @param balanceStartDate the value for bill_shop_balance_brand.balance_start_date
     */
    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     *
     * @return the value of bill_shop_balance_brand.balance_end_date
     */
    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     * @param balanceEndDate the value for bill_shop_balance_brand.balance_end_date
     */
    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * 
     * {@linkplain #deductDiffAmount}
     *
     * @return the value of bill_shop_balance_brand.deduct_diff_amount
     */
    public BigDecimal getDeductDiffAmount() {
        return deductDiffAmount;
    }

    /**
     * 
     * {@linkplain #deductDiffAmount}
     * @param deductDiffAmount the value for bill_shop_balance_brand.deduct_diff_amount
     */
    public void setDeductDiffAmount(BigDecimal deductDiffAmount) {
        this.deductDiffAmount = deductDiffAmount;
    }

    /**
     * 
     * {@linkplain #reportDiffAmount}
     *
     * @return the value of bill_shop_balance_brand.report_diff_amount
     */
    public BigDecimal getReportDiffAmount() {
        return reportDiffAmount;
    }

    /**
     * 
     * {@linkplain #reportDiffAmount}
     * @param reportDiffAmount the value for bill_shop_balance_brand.report_diff_amount
     */
    public void setReportDiffAmount(BigDecimal reportDiffAmount) {
        this.reportDiffAmount = reportDiffAmount;
    }

    /**
     * 
     * {@linkplain #mallNumber}
     *
     * @return the value of bill_shop_balance_brand.mall_number
     */
    public BigDecimal getMallNumber() {
        return mallNumber;
    }

    /**
     * 
     * {@linkplain #mallNumber}
     * @param mallNumber the value for bill_shop_balance_brand.mall_number
     */
    public void setMallNumber(BigDecimal mallNumber) {
        this.mallNumber = mallNumber;
    }

    /**
     * 
     * {@linkplain #salesAmount}
     *
     * @return the value of bill_shop_balance_brand.sales_amount
     */
    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    /**
     * 
     * {@linkplain #salesAmount}
     * @param salesAmount the value for bill_shop_balance_brand.sales_amount
     */
    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

    /**
     * 
     * {@linkplain #salesDiffamount}
     *
     * @return the value of bill_shop_balance_brand.sales_diffamount
     */
    public BigDecimal getSalesDiffamount() {
        return salesDiffamount;
    }

    /**
     * 
     * {@linkplain #salesDiffamount}
     * @param salesDiffamount the value for bill_shop_balance_brand.sales_diffamount
     */
    public void setSalesDiffamount(BigDecimal salesDiffamount) {
        this.salesDiffamount = salesDiffamount;
    }

    /**
     * 
     * {@linkplain #diffAmount}
     *
     * @return the value of bill_shop_balance_brand.diff_amount
     */
    public BigDecimal getDiffAmount() {
        return diffAmount;
    }

    /**
     * 
     * {@linkplain #diffAmount}
     * @param diffAmount the value for bill_shop_balance_brand.diff_amount
     */
    public void setDiffAmount(BigDecimal diffAmount) {
        this.diffAmount = diffAmount;
    }

    /**
     * 
     * {@linkplain #reason}
     *
     * @return the value of bill_shop_balance_brand.reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * 
     * {@linkplain #reason}
     * @param reason the value for bill_shop_balance_brand.reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 
     * {@linkplain #diffReason}
     *
     * @return the value of bill_shop_balance_brand.diff_reason
     */
    public String getDiffReason() {
        return diffReason;
    }

    /**
     * 
     * {@linkplain #diffReason}
     * @param diffReason the value for bill_shop_balance_brand.diff_reason
     */
    public void setDiffReason(String diffReason) {
        this.diffReason = diffReason;
    }

    /**
     * 
     * {@linkplain #actualAmount}
     *
     * @return the value of bill_shop_balance_brand.actual_amount
     */
    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    /**
     * 
     * {@linkplain #actualAmount}
     * @param actualAmount the value for bill_shop_balance_brand.actual_amount
     */
    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    /**
     * 
     * {@linkplain #deductAllamount}
     *
     * @return the value of bill_shop_balance_brand.deduct_allamount
     */
    public BigDecimal getDeductAllamount() {
        return deductAllamount;
    }

    /**
     * 
     * {@linkplain #deductAllamount}
     * @param deductAllamount the value for bill_shop_balance_brand.deduct_allamount
     */
    public void setDeductAllamount(BigDecimal deductAllamount) {
        this.deductAllamount = deductAllamount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     *
     * @return the value of bill_shop_balance_brand.billing_amount
     */
    public BigDecimal getBillingAmount() {
        return billingAmount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     * @param billingAmount the value for bill_shop_balance_brand.billing_amount
     */
    public void setBillingAmount(BigDecimal billingAmount) {
        this.billingAmount = billingAmount;
    }

    /**
     * 
     * {@linkplain #mallBillingAmount}
     *
     * @return the value of bill_shop_balance_brand.mall_billing_amount
     */
    public BigDecimal getMallBillingAmount() {
        return mallBillingAmount;
    }

    /**
     * 
     * {@linkplain #mallBillingAmount}
     * @param mallBillingAmount the value for bill_shop_balance_brand.mall_billing_amount
     */
    public void setMallBillingAmount(BigDecimal mallBillingAmount) {
        this.mallBillingAmount = mallBillingAmount;
    }

    /**
     * 
     * {@linkplain #systemBillingAmount}
     *
     * @return the value of bill_shop_balance_brand.system_billing_amount
     */
    public BigDecimal getSystemBillingAmount() {
        return systemBillingAmount;
    }

    /**
     * 
     * {@linkplain #systemBillingAmount}
     * @param systemBillingAmount the value for bill_shop_balance_brand.system_billing_amount
     */
    public void setSystemBillingAmount(BigDecimal systemBillingAmount) {
        this.systemBillingAmount = systemBillingAmount;
    }

    /**
     * 
     * {@linkplain #balanceDiffAmount}
     *
     * @return the value of bill_shop_balance_brand.balance_diff_amount
     */
    public BigDecimal getBalanceDiffAmount() {
        return balanceDiffAmount;
    }

    /**
     * 
     * {@linkplain #balanceDiffAmount}
     * @param balanceDiffAmount the value for bill_shop_balance_brand.balance_diff_amount
     */
    public void setBalanceDiffAmount(BigDecimal balanceDiffAmount) {
        this.balanceDiffAmount = balanceDiffAmount;
    }

    /**
     * 
     * {@linkplain #balanceDeductAmount}
     *
     * @return the value of bill_shop_balance_brand.balance_deduct_amount
     */
    public BigDecimal getBalanceDeductAmount() {
        return balanceDeductAmount;
    }

    /**
     * 
     * {@linkplain #balanceDeductAmount}
     * @param balanceDeductAmount the value for bill_shop_balance_brand.balance_deduct_amount
     */
    public void setBalanceDeductAmount(BigDecimal balanceDeductAmount) {
        this.balanceDeductAmount = balanceDeductAmount;
    }

    /**
     * 
     * {@linkplain #prepaymentAmount}
     *
     * @return the value of bill_shop_balance_brand.prepayment_amount
     */
    public BigDecimal getPrepaymentAmount() {
        return prepaymentAmount;
    }

    /**
     * 
     * {@linkplain #prepaymentAmount}
     * @param prepaymentAmount the value for bill_shop_balance_brand.prepayment_amount
     */
    public void setPrepaymentAmount(BigDecimal prepaymentAmount) {
        this.prepaymentAmount = prepaymentAmount;
    }

    /**
     * 
     * {@linkplain #usedPrepaymentAmount}
     *
     * @return the value of bill_shop_balance_brand.used_prepayment_amount
     */
    public BigDecimal getUsedPrepaymentAmount() {
        return usedPrepaymentAmount;
    }

    /**
     * 
     * {@linkplain #usedPrepaymentAmount}
     * @param usedPrepaymentAmount the value for bill_shop_balance_brand.used_prepayment_amount
     */
    public void setUsedPrepaymentAmount(BigDecimal usedPrepaymentAmount) {
        this.usedPrepaymentAmount = usedPrepaymentAmount;
    }

    /**
     * 
     * {@linkplain #sysDeductAmount}
     *
     * @return the value of bill_shop_balance_brand.sys_deduct_amount
     */
    public BigDecimal getSysDeductAmount() {
        return sysDeductAmount;
    }

    /**
     * 
     * {@linkplain #sysDeductAmount}
     * @param sysDeductAmount the value for bill_shop_balance_brand.sys_deduct_amount
     */
    public void setSysDeductAmount(BigDecimal sysDeductAmount) {
        this.sysDeductAmount = sysDeductAmount;
    }

    /**
     * 
     * {@linkplain #changeAmount}
     *
     * @return the value of bill_shop_balance_brand.change_amount
     */
    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    /**
     * 
     * {@linkplain #changeAmount}
     * @param changeAmount the value for bill_shop_balance_brand.change_amount
     */
    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }


    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_shop_balance_brand.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_shop_balance_brand.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of bill_shop_balance_brand.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for bill_shop_balance_brand.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of bill_shop_balance_brand.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for bill_shop_balance_brand.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of bill_shop_balance_brand.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for bill_shop_balance_brand.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of bill_shop_balance_brand.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for bill_shop_balance_brand.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #auditor}
     *
     * @return the value of bill_shop_balance_brand.auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 
     * {@linkplain #auditor}
     * @param auditor the value for bill_shop_balance_brand.auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /**
     * 
     * {@linkplain #auditTime}
     *
     * @return the value of bill_shop_balance_brand.audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 
     * {@linkplain #auditTime}
     * @param auditTime the value for bill_shop_balance_brand.audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     *
     * @return the value of bill_shop_balance_brand.sharding_flag
     */
    public String getShardingFlag() {
        return shardingFlag;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     * @param shardingFlag the value for bill_shop_balance_brand.sharding_flag
     */
    public void setShardingFlag(String shardingFlag) {
        this.shardingFlag = shardingFlag;
    }
}