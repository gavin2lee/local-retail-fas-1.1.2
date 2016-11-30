package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

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
public class RateExpenseRemind implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5191182261942059228L;

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
     * 起始结算月
     */
    private String startMonth;

    /**
     * 终止结算月
     */
    private String endMonth;

    /**
     * 总保底计划
     */
    private BigDecimal totalGuaranteeAmount;

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
    @JsonSerialize(using = JsonDateSerializer$19.class)
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
    @JsonSerialize(using = JsonDateSerializer$19.class)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;
    
    
    /**
     * 扩展字段
     */
    private String companyNo;
    
    private String companyName;

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

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of rate_expense_remind.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for rate_expense_remind.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #concractNo}
     *
     * @return the value of rate_expense_remind.concract_no
     */
    public String getConcractNo() {
        return concractNo;
    }

    /**
     * 
     * {@linkplain #concractNo}
     * @param concractNo the value for rate_expense_remind.concract_no
     */
    public void setConcractNo(String concractNo) {
        this.concractNo = concractNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of rate_expense_remind.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for rate_expense_remind.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneName}
     *
     * @return the value of rate_expense_remind.zone_name
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * {@linkplain #zoneName}
     * @param zoneName the value for rate_expense_remind.zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of rate_expense_remind.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for rate_expense_remind.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of rate_expense_remind.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for rate_expense_remind.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of rate_expense_remind.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for rate_expense_remind.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of rate_expense_remind.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for rate_expense_remind.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of rate_expense_remind.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for rate_expense_remind.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of rate_expense_remind.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for rate_expense_remind.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of rate_expense_remind.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for rate_expense_remind.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of rate_expense_remind.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for rate_expense_remind.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #startMonth}
     *
     * @return the value of rate_expense_remind.start_month
     */
    public String getStartMonth() {
        return startMonth;
    }

    /**
     * 
     * {@linkplain #startMonth}
     * @param startMonth the value for rate_expense_remind.start_month
     */
    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    /**
     * 
     * {@linkplain #endMonth}
     *
     * @return the value of rate_expense_remind.end_month
     */
    public String getEndMonth() {
        return endMonth;
    }

    /**
     * 
     * {@linkplain #endMonth}
     * @param endMonth the value for rate_expense_remind.end_month
     */
    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    /**
     * 
     * {@linkplain #totalGuaranteeAmount}
     *
     * @return the value of rate_expense_remind.total_guarantee_amount
     */
    public BigDecimal getTotalGuaranteeAmount() {
        return totalGuaranteeAmount;
    }

    /**
     * 
     * {@linkplain #totalGuaranteeAmount}
     * @param totalGuaranteeAmount the value for rate_expense_remind.total_guarantee_amount
     */
    public void setTotalGuaranteeAmount(BigDecimal totalGuaranteeAmount) {
        this.totalGuaranteeAmount = totalGuaranteeAmount;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of rate_expense_remind.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for rate_expense_remind.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of rate_expense_remind.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for rate_expense_remind.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of rate_expense_remind.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for rate_expense_remind.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of rate_expense_remind.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for rate_expense_remind.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     *
     * @return the value of rate_expense_remind.update_user_no
     */
    public String getUpdateUserNo() {
        return updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     * @param updateUserNo the value for rate_expense_remind.update_user_no
     */
    public void setUpdateUserNo(String updateUserNo) {
        this.updateUserNo = updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of rate_expense_remind.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for rate_expense_remind.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of rate_expense_remind.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for rate_expense_remind.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of rate_expense_remind.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for rate_expense_remind.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}