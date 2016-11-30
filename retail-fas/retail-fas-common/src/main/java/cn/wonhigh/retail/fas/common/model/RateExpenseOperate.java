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
public class RateExpenseOperate implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 53859986022750730L;

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
     * 结算月
     */
    private String settlementPeriod;

    /**
     * 结算类型,0-保底扣率 1-阶段扣率 2-纯租金 3-最大值(租金、扣率) 4-和值(租金+扣率)
     */
    private Byte settlementType;

    /**
     * 扣费类别
     */
    private String debitedType;

    /**
     * 费用扣取方式 1-票前 2-票后
     */
    private Byte debitedMode;

    /**
     * 费用交款方式 1-账款 2-现价
     */
    private Byte paymentMode;

    /**
     * 租金
     */
    private BigDecimal rental;

    /**
     * 扣率
     */
    private BigDecimal rate;

    /**
     * 起始额度
     */
    private BigDecimal startAmount;

    /**
     * 终止额度
     */
    private BigDecimal endAmount;

    /**
     * 超额统一扣率 0-统一 1-不统
     */
    private Byte unityRateFlag;

    /**
     * 促销计保底 0-计保底 1-不计保底(用于保底扣率类型)
     */
    private Byte minimumFlag;

    /**
     * 状态,0-未结算，1-已生成,2-已结算
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
    
    private String endSettlementPeriod;
    
    
    private Date settlementDate;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of rate_expense_operate.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for rate_expense_operate.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #concractNo}
     *
     * @return the value of rate_expense_operate.concract_no
     */
    public String getConcractNo() {
        return concractNo;
    }

    /**
     * 
     * {@linkplain #concractNo}
     * @param concractNo the value for rate_expense_operate.concract_no
     */
    public void setConcractNo(String concractNo) {
        this.concractNo = concractNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of rate_expense_operate.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for rate_expense_operate.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneName}
     *
     * @return the value of rate_expense_operate.zone_name
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * {@linkplain #zoneName}
     * @param zoneName the value for rate_expense_operate.zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of rate_expense_operate.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for rate_expense_operate.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of rate_expense_operate.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for rate_expense_operate.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of rate_expense_operate.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for rate_expense_operate.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of rate_expense_operate.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for rate_expense_operate.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of rate_expense_operate.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for rate_expense_operate.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of rate_expense_operate.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for rate_expense_operate.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of rate_expense_operate.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for rate_expense_operate.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of rate_expense_operate.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for rate_expense_operate.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #settlementPeriod}
     *
     * @return the value of rate_expense_operate.settlement_period
     */
    public String getSettlementPeriod() {
        return settlementPeriod;
    }

    /**
     * 
     * {@linkplain #settlementPeriod}
     * @param settlementPeriod the value for rate_expense_operate.settlement_period
     */
    public void setSettlementPeriod(String settlementPeriod) {
        this.settlementPeriod = settlementPeriod;
    }

    /**
     * 
     * {@linkplain #settlementType}
     *
     * @return the value of rate_expense_operate.settlement_type
     */
    public Byte getSettlementType() {
        return settlementType;
    }

    /**
     * 
     * {@linkplain #settlementType}
     * @param settlementType the value for rate_expense_operate.settlement_type
     */
    public void setSettlementType(Byte settlementType) {
        this.settlementType = settlementType;
    }

    /**
     * 
     * {@linkplain #debitedType}
     *
     * @return the value of rate_expense_operate.debited_type
     */
    public String getDebitedType() {
        return debitedType;
    }

    /**
     * 
     * {@linkplain #debitedType}
     * @param debitedType the value for rate_expense_operate.debited_type
     */
    public void setDebitedType(String debitedType) {
        this.debitedType = debitedType;
    }

    /**
     * 
     * {@linkplain #debitedMode}
     *
     * @return the value of rate_expense_operate.debited_mode
     */
    public Byte getDebitedMode() {
        return debitedMode;
    }

    /**
     * 
     * {@linkplain #debitedMode}
     * @param debitedMode the value for rate_expense_operate.debited_mode
     */
    public void setDebitedMode(Byte debitedMode) {
        this.debitedMode = debitedMode;
    }

    /**
     * 
     * {@linkplain #paymentMode}
     *
     * @return the value of rate_expense_operate.payment_mode
     */
    public Byte getPaymentMode() {
        return paymentMode;
    }

    /**
     * 
     * {@linkplain #paymentMode}
     * @param paymentMode the value for rate_expense_operate.payment_mode
     */
    public void setPaymentMode(Byte paymentMode) {
        this.paymentMode = paymentMode;
    }

    /**
     * 
     * {@linkplain #rental}
     *
     * @return the value of rate_expense_operate.rental
     */
    public BigDecimal getRental() {
        return rental;
    }

    /**
     * 
     * {@linkplain #rental}
     * @param rental the value for rate_expense_operate.rental
     */
    public void setRental(BigDecimal rental) {
        this.rental = rental;
    }

    /**
     * 
     * {@linkplain #rate}
     *
     * @return the value of rate_expense_operate.rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 
     * {@linkplain #rate}
     * @param rate the value for rate_expense_operate.rate
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 
     * {@linkplain #startAmount}
     *
     * @return the value of rate_expense_operate.start_amount
     */
    public BigDecimal getStartAmount() {
        return startAmount;
    }

    /**
     * 
     * {@linkplain #startAmount}
     * @param startAmount the value for rate_expense_operate.start_amount
     */
    public void setStartAmount(BigDecimal startAmount) {
        this.startAmount = startAmount;
    }

    /**
     * 
     * {@linkplain #endAmount}
     *
     * @return the value of rate_expense_operate.end_amount
     */
    public BigDecimal getEndAmount() {
        return endAmount;
    }

    /**
     * 
     * {@linkplain #endAmount}
     * @param endAmount the value for rate_expense_operate.end_amount
     */
    public void setEndAmount(BigDecimal endAmount) {
        this.endAmount = endAmount;
    }

    /**
     * 
     * {@linkplain #unityRateFlag}
     *
     * @return the value of rate_expense_operate.unity_rate_flag
     */
    public Byte getUnityRateFlag() {
        return unityRateFlag;
    }

    /**
     * 
     * {@linkplain #unityRateFlag}
     * @param unityRateFlag the value for rate_expense_operate.unity_rate_flag
     */
    public void setUnityRateFlag(Byte unityRateFlag) {
        this.unityRateFlag = unityRateFlag;
    }

    /**
     * 
     * {@linkplain #minimumFlag}
     *
     * @return the value of rate_expense_operate.minimum_flag
     */
    public Byte getMinimumFlag() {
        return minimumFlag;
    }

    /**
     * 
     * {@linkplain #minimumFlag}
     * @param minimumFlag the value for rate_expense_operate.minimum_flag
     */
    public void setMinimumFlag(Byte minimumFlag) {
        this.minimumFlag = minimumFlag;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of rate_expense_operate.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for rate_expense_operate.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of rate_expense_operate.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for rate_expense_operate.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of rate_expense_operate.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for rate_expense_operate.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of rate_expense_operate.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for rate_expense_operate.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     *
     * @return the value of rate_expense_operate.update_user_no
     */
    public String getUpdateUserNo() {
        return updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     * @param updateUserNo the value for rate_expense_operate.update_user_no
     */
    public void setUpdateUserNo(String updateUserNo) {
        this.updateUserNo = updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of rate_expense_operate.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for rate_expense_operate.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of rate_expense_operate.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for rate_expense_operate.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of rate_expense_operate.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for rate_expense_operate.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getEndSettlementPeriod() {
		return endSettlementPeriod;
	}

	public void setEndSettlementPeriod(String endSettlementPeriod) {
		this.endSettlementPeriod = endSettlementPeriod;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}
}