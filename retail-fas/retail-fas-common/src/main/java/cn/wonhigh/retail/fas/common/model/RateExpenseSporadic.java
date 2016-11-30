package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
public class RateExpenseSporadic implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1836170689310444651L;

	/**
     * 主键ID,UUID
     */
    private String id;

    /**
     * 合同号
     */
    private String concractNo;

    /**
     * 大区编码
     */
    private String zoneNo;

    /**
     * 大区名称
     */
    private String zoneName;

    /**
     * 城市编码
     */
    private String organNo;

    /**
     * 城市名称
     */
    private String organName;

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
     * 条款类型
     */
    private Byte clauseType;

    /**
     * 生效起始日期
     */
    private Date startDate;

    /**
     * 生效终止日期
     */
    private Date endDate;

    /**
     * 扣费类别 从FAS取
     */
    private String debitedType;

    /**
     * 扣费类别名称
     */
    private String debitedTypeName;

    /**
     * 商场扣费名称
     */
    private String mallDeductionName;

    /**
     * 费用扣取方式 1-票前 2-票后
     */
    private Byte debitedMode;

    /**
     * 费用交款方式 1-账款 2-现价
     */
    private Byte paymentMode;

    /**
     * 扣费规则 1-月定额 2-费率
     */
    private Byte debitedRule;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 支付方式
     */
    private String basePayCode;

    /**
     * 其他
     */
    private String baseOther;

    /**
     * 费率
     */
    private BigDecimal rate;

    /**
     * 补差费率
     */
    private BigDecimal balanceRate;

    /**
     * 最新结算日
     */
    private Date settlementDate;

    /**
     * 状态,0-未结算，1-已结算
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
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of rate_expense_sporadic.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for rate_expense_sporadic.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #concractNo}
     *
     * @return the value of rate_expense_sporadic.concract_no
     */
    public String getConcractNo() {
        return concractNo;
    }

    /**
     * 
     * {@linkplain #concractNo}
     * @param concractNo the value for rate_expense_sporadic.concract_no
     */
    public void setConcractNo(String concractNo) {
        this.concractNo = concractNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of rate_expense_sporadic.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for rate_expense_sporadic.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneName}
     *
     * @return the value of rate_expense_sporadic.zone_name
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * {@linkplain #zoneName}
     * @param zoneName the value for rate_expense_sporadic.zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of rate_expense_sporadic.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for rate_expense_sporadic.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of rate_expense_sporadic.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for rate_expense_sporadic.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of rate_expense_sporadic.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for rate_expense_sporadic.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of rate_expense_sporadic.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for rate_expense_sporadic.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of rate_expense_sporadic.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for rate_expense_sporadic.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of rate_expense_sporadic.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for rate_expense_sporadic.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of rate_expense_sporadic.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for rate_expense_sporadic.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of rate_expense_sporadic.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for rate_expense_sporadic.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #clauseType}
     *
     * @return the value of rate_expense_sporadic.clause_type
     */
    public Byte getClauseType() {
        return clauseType;
    }

    /**
     * 
     * {@linkplain #clauseType}
     * @param clauseType the value for rate_expense_sporadic.clause_type
     */
    public void setClauseType(Byte clauseType) {
        this.clauseType = clauseType;
    }

    /**
     * 
     * {@linkplain #startDate}
     *
     * @return the value of rate_expense_sporadic.start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 
     * {@linkplain #startDate}
     * @param startDate the value for rate_expense_sporadic.start_date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     *
     * @return the value of rate_expense_sporadic.end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     * @param endDate the value for rate_expense_sporadic.end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 
     * {@linkplain #debitedType}
     *
     * @return the value of rate_expense_sporadic.debited_type
     */
    public String getDebitedType() {
        return debitedType;
    }

    /**
     * 
     * {@linkplain #debitedType}
     * @param debitedType the value for rate_expense_sporadic.debited_type
     */
    public void setDebitedType(String debitedType) {
        this.debitedType = debitedType;
    }

    /**
     * 
     * {@linkplain #debitedTypeName}
     *
     * @return the value of rate_expense_sporadic.debited_type_name
     */
    public String getDebitedTypeName() {
        return debitedTypeName;
    }

    /**
     * 
     * {@linkplain #debitedTypeName}
     * @param debitedTypeName the value for rate_expense_sporadic.debited_type_name
     */
    public void setDebitedTypeName(String debitedTypeName) {
        this.debitedTypeName = debitedTypeName;
    }

    /**
     * 
     * {@linkplain #mallDeductionName}
     *
     * @return the value of rate_expense_sporadic.mall_deduction_name
     */
    public String getMallDeductionName() {
        return mallDeductionName;
    }

    /**
     * 
     * {@linkplain #mallDeductionName}
     * @param mallDeductionName the value for rate_expense_sporadic.mall_deduction_name
     */
    public void setMallDeductionName(String mallDeductionName) {
        this.mallDeductionName = mallDeductionName;
    }

    /**
     * 
     * {@linkplain #debitedMode}
     *
     * @return the value of rate_expense_sporadic.debited_mode
     */
    public Byte getDebitedMode() {
        return debitedMode;
    }

    /**
     * 
     * {@linkplain #debitedMode}
     * @param debitedMode the value for rate_expense_sporadic.debited_mode
     */
    public void setDebitedMode(Byte debitedMode) {
        this.debitedMode = debitedMode;
    }

    /**
     * 
     * {@linkplain #paymentMode}
     *
     * @return the value of rate_expense_sporadic.payment_mode
     */
    public Byte getPaymentMode() {
        return paymentMode;
    }

    /**
     * 
     * {@linkplain #paymentMode}
     * @param paymentMode the value for rate_expense_sporadic.payment_mode
     */
    public void setPaymentMode(Byte paymentMode) {
        this.paymentMode = paymentMode;
    }

    /**
     * 
     * {@linkplain #debitedRule}
     *
     * @return the value of rate_expense_sporadic.debited_rule
     */
    public Byte getDebitedRule() {
        return debitedRule;
    }

    /**
     * 
     * {@linkplain #debitedRule}
     * @param debitedRule the value for rate_expense_sporadic.debited_rule
     */
    public void setDebitedRule(Byte debitedRule) {
        this.debitedRule = debitedRule;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of rate_expense_sporadic.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for rate_expense_sporadic.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #basePayCode}
     *
     * @return the value of rate_expense_sporadic.base_pay_code
     */
    public String getBasePayCode() {
        return basePayCode;
    }

    /**
     * 
     * {@linkplain #basePayCode}
     * @param basePayCode the value for rate_expense_sporadic.base_pay_code
     */
    public void setBasePayCode(String basePayCode) {
        this.basePayCode = basePayCode;
    }

    /**
     * 
     * {@linkplain #baseOther}
     *
     * @return the value of rate_expense_sporadic.base_other
     */
    public String getBaseOther() {
        return baseOther;
    }

    /**
     * 
     * {@linkplain #baseOther}
     * @param baseOther the value for rate_expense_sporadic.base_other
     */
    public void setBaseOther(String baseOther) {
        this.baseOther = baseOther;
    }

    /**
     * 
     * {@linkplain #rate}
     *
     * @return the value of rate_expense_sporadic.rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 
     * {@linkplain #rate}
     * @param rate the value for rate_expense_sporadic.rate
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 
     * {@linkplain #balanceRate}
     *
     * @return the value of rate_expense_sporadic.balance_rate
     */
    public BigDecimal getBalanceRate() {
        return balanceRate;
    }

    /**
     * 
     * {@linkplain #balanceRate}
     * @param balanceRate the value for rate_expense_sporadic.balance_rate
     */
    public void setBalanceRate(BigDecimal balanceRate) {
        this.balanceRate = balanceRate;
    }

    /**
     * 
     * {@linkplain #settlementDate}
     *
     * @return the value of rate_expense_sporadic.settlement_date
     */
    public Date getSettlementDate() {
        return settlementDate;
    }

    /**
     * 
     * {@linkplain #settlementDate}
     * @param settlementDate the value for rate_expense_sporadic.settlement_date
     */
    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of rate_expense_sporadic.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for rate_expense_sporadic.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of rate_expense_sporadic.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for rate_expense_sporadic.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of rate_expense_sporadic.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for rate_expense_sporadic.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of rate_expense_sporadic.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for rate_expense_sporadic.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     *
     * @return the value of rate_expense_sporadic.update_user_no
     */
    public String getUpdateUserNo() {
        return updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     * @param updateUserNo the value for rate_expense_sporadic.update_user_no
     */
    public void setUpdateUserNo(String updateUserNo) {
        this.updateUserNo = updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of rate_expense_sporadic.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for rate_expense_sporadic.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of rate_expense_sporadic.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for rate_expense_sporadic.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of rate_expense_sporadic.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for rate_expense_sporadic.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}