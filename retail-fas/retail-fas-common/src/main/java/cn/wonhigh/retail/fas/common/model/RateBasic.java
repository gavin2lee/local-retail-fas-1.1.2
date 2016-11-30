package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-06-25 10:44:21
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
public class RateBasic implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3844410921306916579L;

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
     * 扣率类型,1-合同正价扣 2-合同阶梯扣
     */
    private Byte rateType;

    /**
     * 扣率代码,A B...
     */
    private String rateCode;

    /**
     * 扣率名称
     */
    private String rateName;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 扣率
     */
    private BigDecimal rate;

    /**
     * 起始折>
     */
    private BigDecimal startDisc;

    /**
     * 终止折<=
     */
    private BigDecimal endDisc;

    /**
     * 最新结算日
     */
    private Date settlementDate;

    /**
     * 是否使用,0-已使用 1-未使用
     */
    private Byte useFlag;

    /**
     * 商场结算码
     */
    private String billingCode;

    /**
     * 状态,0-编辑,100-完结
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
     * @return the value of rate_basic.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for rate_basic.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #concractNo}
     *
     * @return the value of rate_basic.concract_no
     */
    public String getConcractNo() {
        return concractNo;
    }

    /**
     * 
     * {@linkplain #concractNo}
     * @param concractNo the value for rate_basic.concract_no
     */
    public void setConcractNo(String concractNo) {
        this.concractNo = concractNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of rate_basic.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for rate_basic.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneName}
     *
     * @return the value of rate_basic.zone_name
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * {@linkplain #zoneName}
     * @param zoneName the value for rate_basic.zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of rate_basic.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for rate_basic.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of rate_basic.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for rate_basic.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of rate_basic.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for rate_basic.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of rate_basic.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for rate_basic.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of rate_basic.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for rate_basic.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of rate_basic.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for rate_basic.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of rate_basic.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for rate_basic.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of rate_basic.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for rate_basic.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #rateType}
     *
     * @return the value of rate_basic.rate_type
     */
    public Byte getRateType() {
        return rateType;
    }

    /**
     * 
     * {@linkplain #rateType}
     * @param rateType the value for rate_basic.rate_type
     */
    public void setRateType(Byte rateType) {
        this.rateType = rateType;
    }

    /**
     * 
     * {@linkplain #rateCode}
     *
     * @return the value of rate_basic.rate_code
     */
    public String getRateCode() {
        return rateCode;
    }

    /**
     * 
     * {@linkplain #rateCode}
     * @param rateCode the value for rate_basic.rate_code
     */
    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    /**
     * 
     * {@linkplain #rateName}
     *
     * @return the value of rate_basic.rate_name
     */
    public String getRateName() {
        return rateName;
    }

    /**
     * 
     * {@linkplain #rateName}
     * @param rateName the value for rate_basic.rate_name
     */
    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    /**
     * 
     * {@linkplain #startDate}
     *
     * @return the value of rate_basic.start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 
     * {@linkplain #startDate}
     * @param startDate the value for rate_basic.start_date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     *
     * @return the value of rate_basic.end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     * @param endDate the value for rate_basic.end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 
     * {@linkplain #rate}
     *
     * @return the value of rate_basic.rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 
     * {@linkplain #rate}
     * @param rate the value for rate_basic.rate
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 
     * {@linkplain #startDisc}
     *
     * @return the value of rate_basic.start_disc
     */
    public BigDecimal getStartDisc() {
        return startDisc;
    }

    /**
     * 
     * {@linkplain #startDisc}
     * @param startDisc the value for rate_basic.start_disc
     */
    public void setStartDisc(BigDecimal startDisc) {
        this.startDisc = startDisc;
    }

    /**
     * 
     * {@linkplain #endDisc}
     *
     * @return the value of rate_basic.end_disc
     */
    public BigDecimal getEndDisc() {
        return endDisc;
    }

    /**
     * 
     * {@linkplain #endDisc}
     * @param endDisc the value for rate_basic.end_disc
     */
    public void setEndDisc(BigDecimal endDisc) {
        this.endDisc = endDisc;
    }

    /**
     * 
     * {@linkplain #settlementDate}
     *
     * @return the value of rate_basic.settlement_date
     */
    public Date getSettlementDate() {
        return settlementDate;
    }

    /**
     * 
     * {@linkplain #settlementDate}
     * @param settlementDate the value for rate_basic.settlement_date
     */
    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    /**
     * 
     * {@linkplain #useFlag}
     *
     * @return the value of rate_basic.use_flag
     */
    public Byte getUseFlag() {
        return useFlag;
    }

    /**
     * 
     * {@linkplain #useFlag}
     * @param useFlag the value for rate_basic.use_flag
     */
    public void setUseFlag(Byte useFlag) {
        this.useFlag = useFlag;
    }

    /**
     * 
     * {@linkplain #billingCode}
     *
     * @return the value of rate_basic.billing_code
     */
    public String getBillingCode() {
        return billingCode;
    }

    /**
     * 
     * {@linkplain #billingCode}
     * @param billingCode the value for rate_basic.billing_code
     */
    public void setBillingCode(String billingCode) {
        this.billingCode = billingCode;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of rate_basic.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for rate_basic.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of rate_basic.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for rate_basic.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of rate_basic.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for rate_basic.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of rate_basic.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for rate_basic.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     *
     * @return the value of rate_basic.update_user_no
     */
    public String getUpdateUserNo() {
        return updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     * @param updateUserNo the value for rate_basic.update_user_no
     */
    public void setUpdateUserNo(String updateUserNo) {
        this.updateUserNo = updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of rate_basic.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for rate_basic.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of rate_basic.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for rate_basic.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of rate_basic.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for rate_basic.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}