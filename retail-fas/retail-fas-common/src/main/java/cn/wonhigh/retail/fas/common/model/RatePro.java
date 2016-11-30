package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-06-25 10:44:22
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
public class RatePro implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4572215435906861315L;

	/**
     * 主键ID,UUID
     */
    private String id;

    /**
     * 商场(品牌)活动单号
     */
    private String activityNo;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动描述
     */
    private String activityDescribe;

    /**
     * 活动来源,1-公司活动 2-商场活动
     */
    private Byte launchType;

    /**
     * 活动类型,1-买换 2-打折 3-其他
     */
    private Byte activityType;

    /**
     * 单据类型,1-扣率设置单 2-扣率补录单
     */
    private Byte activityBillType;

    /**
     * 地区编码
     */
    private String zoneNo;

    /**
     * 地区名称
     */
    private String zoneName;

    /**
     * 管理城市编码
     */
    private String manageOrganNo;

    /**
     * 管理城市名称
     */
    private String manageOrganName;

    /**
     * 商场编码
     */
    private String mallNo;

    /**
     * 商场名称
     */
    private String mallName;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 开始日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date startDate;

    /**
     * 结束日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date endDate;

    /**
     * 扣率
     */
    private BigDecimal rate;

    /**
     * 扣率代码,A B...
     */
    private String rateCode;

    /**
     * 扣率名称
     */
    private String rateName;

    /**
     * 取合同扣标志,0-取合同扣 1-不取合同扣
     */
    private Byte basicRateFlag;

    /**
     * 取促销阶梯扣标志,0-取促销阶梯扣 1-不取促销阶梯扣
     */
    private Byte stepRateFlag;

    /**
     * 费用扣取方式 1-票前 2-票后
     */
    private Byte debitedMode;

    /**
     * 费用交款方式 1-账款 2-现价
     */
    private Byte paymentMode;

    /**
     * 当前结算日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date settlementDate;

    /**
     * 结算码
     */
    private String billingCode;

    /**
     * 虚实,0-虚数 1-实数
     */
    private Byte virtualFlag;

    /**
     * 结算基数,0-销售金额 1-牌价金额
     */
    private Byte balanceBase;

    /**
     * 属性,1-满送 2-满减 3-折扣 4-其他
     */
    private Byte properties;

    /**
     * 满值
     */
    private BigDecimal fullValue;

    /**
     * 送减额
     */
    private BigDecimal deductionValue;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 力度
     */
    private BigDecimal strength;

    /**
     * 促销单
     */
    private String proNo;

    /**
     * 状态,0-编辑,10-确认 100-已创建
     */
    private Byte status;

    /**
     * 建档人
     */
    private String createUserNo;

    /**
     * 建档人姓名
     */
    private String createUser;

    /**
     * 建档时间
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
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of rate_pro.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for rate_pro.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #activityNo}
     *
     * @return the value of rate_pro.activity_no
     */
    public String getActivityNo() {
        return activityNo;
    }

    /**
     * 
     * {@linkplain #activityNo}
     * @param activityNo the value for rate_pro.activity_no
     */
    public void setActivityNo(String activityNo) {
        this.activityNo = activityNo;
    }

    /**
     * 
     * {@linkplain #activityName}
     *
     * @return the value of rate_pro.activity_name
     */
    public String getActivityName() {
        return activityName;
    }

    /**
     * 
     * {@linkplain #activityName}
     * @param activityName the value for rate_pro.activity_name
     */
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    /**
     * 
     * {@linkplain #activityDescribe}
     *
     * @return the value of rate_pro.activity_describe
     */
    public String getActivityDescribe() {
        return activityDescribe;
    }

    /**
     * 
     * {@linkplain #activityDescribe}
     * @param activityDescribe the value for rate_pro.activity_describe
     */
    public void setActivityDescribe(String activityDescribe) {
        this.activityDescribe = activityDescribe;
    }

    /**
     * 
     * {@linkplain #launchType}
     *
     * @return the value of rate_pro.launch_type
     */
    public Byte getLaunchType() {
        return launchType;
    }

    /**
     * 
     * {@linkplain #launchType}
     * @param launchType the value for rate_pro.launch_type
     */
    public void setLaunchType(Byte launchType) {
        this.launchType = launchType;
    }

    /**
     * 
     * {@linkplain #activityType}
     *
     * @return the value of rate_pro.activity_type
     */
    public Byte getActivityType() {
        return activityType;
    }

    /**
     * 
     * {@linkplain #activityType}
     * @param activityType the value for rate_pro.activity_type
     */
    public void setActivityType(Byte activityType) {
        this.activityType = activityType;
    }

    /**
     * 
     * {@linkplain #activityBillType}
     *
     * @return the value of rate_pro.activity_bill_type
     */
    public Byte getActivityBillType() {
        return activityBillType;
    }

    /**
     * 
     * {@linkplain #activityBillType}
     * @param activityBillType the value for rate_pro.activity_bill_type
     */
    public void setActivityBillType(Byte activityBillType) {
        this.activityBillType = activityBillType;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of rate_pro.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for rate_pro.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneName}
     *
     * @return the value of rate_pro.zone_name
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * {@linkplain #zoneName}
     * @param zoneName the value for rate_pro.zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * {@linkplain #manageOrganNo}
     *
     * @return the value of rate_pro.manage_organ_no
     */
    public String getManageOrganNo() {
        return manageOrganNo;
    }

    /**
     * 
     * {@linkplain #manageOrganNo}
     * @param manageOrganNo the value for rate_pro.manage_organ_no
     */
    public void setManageOrganNo(String manageOrganNo) {
        this.manageOrganNo = manageOrganNo;
    }

    /**
     * 
     * {@linkplain #manageOrganName}
     *
     * @return the value of rate_pro.manage_organ_name
     */
    public String getManageOrganName() {
        return manageOrganName;
    }

    /**
     * 
     * {@linkplain #manageOrganName}
     * @param manageOrganName the value for rate_pro.manage_organ_name
     */
    public void setManageOrganName(String manageOrganName) {
        this.manageOrganName = manageOrganName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of rate_pro.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for rate_pro.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of rate_pro.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for rate_pro.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of rate_pro.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for rate_pro.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of rate_pro.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for rate_pro.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of rate_pro.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for rate_pro.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of rate_pro.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for rate_pro.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #startDate}
     *
     * @return the value of rate_pro.start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 
     * {@linkplain #startDate}
     * @param startDate the value for rate_pro.start_date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     *
     * @return the value of rate_pro.end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     * @param endDate the value for rate_pro.end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 
     * {@linkplain #rate}
     *
     * @return the value of rate_pro.rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 
     * {@linkplain #rate}
     * @param rate the value for rate_pro.rate
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 
     * {@linkplain #rateCode}
     *
     * @return the value of rate_pro.rate_code
     */
    public String getRateCode() {
        return rateCode;
    }

    /**
     * 
     * {@linkplain #rateCode}
     * @param rateCode the value for rate_pro.rate_code
     */
    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    /**
     * 
     * {@linkplain #rateName}
     *
     * @return the value of rate_pro.rate_name
     */
    public String getRateName() {
        return rateName;
    }

    /**
     * 
     * {@linkplain #rateName}
     * @param rateName the value for rate_pro.rate_name
     */
    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    /**
     * 
     * {@linkplain #basicRateFlag}
     *
     * @return the value of rate_pro.basic_rate_flag
     */
    public Byte getBasicRateFlag() {
        return basicRateFlag;
    }

    /**
     * 
     * {@linkplain #basicRateFlag}
     * @param basicRateFlag the value for rate_pro.basic_rate_flag
     */
    public void setBasicRateFlag(Byte basicRateFlag) {
        this.basicRateFlag = basicRateFlag;
    }

    /**
     * 
     * {@linkplain #stepRateFlag}
     *
     * @return the value of rate_pro.step_rate_flag
     */
    public Byte getStepRateFlag() {
        return stepRateFlag;
    }

    /**
     * 
     * {@linkplain #stepRateFlag}
     * @param stepRateFlag the value for rate_pro.step_rate_flag
     */
    public void setStepRateFlag(Byte stepRateFlag) {
        this.stepRateFlag = stepRateFlag;
    }

    /**
     * 
     * {@linkplain #debitedMode}
     *
     * @return the value of rate_pro.debited_mode
     */
    public Byte getDebitedMode() {
        return debitedMode;
    }

    /**
     * 
     * {@linkplain #debitedMode}
     * @param debitedMode the value for rate_pro.debited_mode
     */
    public void setDebitedMode(Byte debitedMode) {
        this.debitedMode = debitedMode;
    }

    /**
     * 
     * {@linkplain #paymentMode}
     *
     * @return the value of rate_pro.payment_mode
     */
    public Byte getPaymentMode() {
        return paymentMode;
    }

    /**
     * 
     * {@linkplain #paymentMode}
     * @param paymentMode the value for rate_pro.payment_mode
     */
    public void setPaymentMode(Byte paymentMode) {
        this.paymentMode = paymentMode;
    }

    /**
     * 
     * {@linkplain #settlementDate}
     *
     * @return the value of rate_pro.settlement_date
     */
    public Date getSettlementDate() {
        return settlementDate;
    }

    /**
     * 
     * {@linkplain #settlementDate}
     * @param settlementDate the value for rate_pro.settlement_date
     */
    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    /**
     * 
     * {@linkplain #billingCode}
     *
     * @return the value of rate_pro.billing_code
     */
    public String getBillingCode() {
        return billingCode;
    }

    /**
     * 
     * {@linkplain #billingCode}
     * @param billingCode the value for rate_pro.billing_code
     */
    public void setBillingCode(String billingCode) {
        this.billingCode = billingCode;
    }

    /**
     * 
     * {@linkplain #virtualFlag}
     *
     * @return the value of rate_pro.virtual_flag
     */
    public Byte getVirtualFlag() {
        return virtualFlag;
    }

    /**
     * 
     * {@linkplain #virtualFlag}
     * @param virtualFlag the value for rate_pro.virtual_flag
     */
    public void setVirtualFlag(Byte virtualFlag) {
        this.virtualFlag = virtualFlag;
    }

    /**
     * 
     * {@linkplain #balanceBase}
     *
     * @return the value of rate_pro.balance_base
     */
    public Byte getBalanceBase() {
        return balanceBase;
    }

    /**
     * 
     * {@linkplain #balanceBase}
     * @param balanceBase the value for rate_pro.balance_base
     */
    public void setBalanceBase(Byte balanceBase) {
        this.balanceBase = balanceBase;
    }

    /**
     * 
     * {@linkplain #properties}
     *
     * @return the value of rate_pro.properties
     */
    public Byte getProperties() {
        return properties;
    }

    /**
     * 
     * {@linkplain #properties}
     * @param properties the value for rate_pro.properties
     */
    public void setProperties(Byte properties) {
        this.properties = properties;
    }

    /**
     * 
     * {@linkplain #fullValue}
     *
     * @return the value of rate_pro.full_value
     */
    public BigDecimal getFullValue() {
        return fullValue;
    }

    /**
     * 
     * {@linkplain #fullValue}
     * @param fullValue the value for rate_pro.full_value
     */
    public void setFullValue(BigDecimal fullValue) {
        this.fullValue = fullValue;
    }

    /**
     * 
     * {@linkplain #deductionValue}
     *
     * @return the value of rate_pro.deduction_value
     */
    public BigDecimal getDeductionValue() {
        return deductionValue;
    }

    /**
     * 
     * {@linkplain #deductionValue}
     * @param deductionValue the value for rate_pro.deduction_value
     */
    public void setDeductionValue(BigDecimal deductionValue) {
        this.deductionValue = deductionValue;
    }

    /**
     * 
     * {@linkplain #discount}
     *
     * @return the value of rate_pro.discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 
     * {@linkplain #discount}
     * @param discount the value for rate_pro.discount
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 
     * {@linkplain #strength}
     *
     * @return the value of rate_pro.strength
     */
    public BigDecimal getStrength() {
        return strength;
    }

    /**
     * 
     * {@linkplain #strength}
     * @param strength the value for rate_pro.strength
     */
    public void setStrength(BigDecimal strength) {
        this.strength = strength;
    }

    /**
     * 
     * {@linkplain #proNo}
     *
     * @return the value of rate_pro.pro_no
     */
    public String getProNo() {
        return proNo;
    }

    /**
     * 
     * {@linkplain #proNo}
     * @param proNo the value for rate_pro.pro_no
     */
    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of rate_pro.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for rate_pro.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of rate_pro.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for rate_pro.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of rate_pro.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for rate_pro.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of rate_pro.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for rate_pro.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     *
     * @return the value of rate_pro.update_user_no
     */
    public String getUpdateUserNo() {
        return updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     * @param updateUserNo the value for rate_pro.update_user_no
     */
    public void setUpdateUserNo(String updateUserNo) {
        this.updateUserNo = updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of rate_pro.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for rate_pro.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of rate_pro.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for rate_pro.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #auditNo}
     *
     * @return the value of rate_pro.audit_no
     */
    public String getAuditNo() {
        return auditNo;
    }

    /**
     * 
     * {@linkplain #auditNo}
     * @param auditNo the value for rate_pro.audit_no
     */
    public void setAuditNo(String auditNo) {
        this.auditNo = auditNo;
    }

    /**
     * 
     * {@linkplain #auditor}
     *
     * @return the value of rate_pro.auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 
     * {@linkplain #auditor}
     * @param auditor the value for rate_pro.auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /**
     * 
     * {@linkplain #auditTime}
     *
     * @return the value of rate_pro.audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 
     * {@linkplain #auditTime}
     * @param auditTime the value for rate_pro.audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of rate_pro.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for rate_pro.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}