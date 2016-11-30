package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-11 11:11:49
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
public class SaleSettlementDeduction implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4090910090936499114L;

	/**
     * 主键ID,UUID
     */
    private String id;

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
     * 结算期
     */
    private String settlementPeriod;

    /**
     * 结算起始日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date startDate;

    /**
     * 结算终止日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date endDate;

    /**
     * 扣率类型,1-合同扣点，2-保底+扣率，3-租金，4-最大值(租金、扣率)，5-租金+扣率
     */
    private Short rateType;

    /**
     * 租金
     */
    private BigDecimal rental;

    /**
     * 扣率
     */
    private BigDecimal rate;

    /**
     * 保底额
     */
    private BigDecimal baseAmount;

    /**
     * 保底扣率
     */
    private BigDecimal baseRate;

    /**
     * 超保底扣率
     */
    private BigDecimal exceedBaseRate;

    /**
     * 状态,0-未结算，1-已结算
     */
    private Short status;

    /**
     * 建档人
     */
    private String creatorNo;

    /**
     * 建档人姓名
     */
    private String creator;

    /**
     * 建档时间
     */
    private Date createTime;

    /**
     * 最后修改人
     */
    private String editorNo;

    /**
     * 最后修改人姓名
     */
    private String editor;

    /**
     * 最后修改时间
     */
    private Date editTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of sale_settlement_deduction.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for sale_settlement_deduction.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of sale_settlement_deduction.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for sale_settlement_deduction.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneName}
     *
     * @return the value of sale_settlement_deduction.zone_name
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * {@linkplain #zoneName}
     * @param zoneName the value for sale_settlement_deduction.zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of sale_settlement_deduction.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for sale_settlement_deduction.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of sale_settlement_deduction.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for sale_settlement_deduction.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of sale_settlement_deduction.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for sale_settlement_deduction.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of sale_settlement_deduction.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for sale_settlement_deduction.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of sale_settlement_deduction.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for sale_settlement_deduction.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of sale_settlement_deduction.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for sale_settlement_deduction.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #settlementPeriod}
     *
     * @return the value of sale_settlement_deduction.settlement_period
     */
    public String getSettlementPeriod() {
        return settlementPeriod;
    }

    /**
     * 
     * {@linkplain #settlementPeriod}
     * @param settlementPeriod the value for sale_settlement_deduction.settlement_period
     */
    public void setSettlementPeriod(String settlementPeriod) {
        this.settlementPeriod = settlementPeriod;
    }

    /**
     * 
     * {@linkplain #startDate}
     *
     * @return the value of sale_settlement_deduction.start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 
     * {@linkplain #startDate}
     * @param startDate the value for sale_settlement_deduction.start_date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     *
     * @return the value of sale_settlement_deduction.end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     * @param endDate the value for sale_settlement_deduction.end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 
     * {@linkplain #rateType}
     *
     * @return the value of sale_settlement_deduction.rate_type
     */
    public Short getRateType() {
        return rateType;
    }

    /**
     * 
     * {@linkplain #rateType}
     * @param rateType the value for sale_settlement_deduction.rate_type
     */
    public void setRateType(Short rateType) {
        this.rateType = rateType;
    }

    /**
     * 
     * {@linkplain #rental}
     *
     * @return the value of sale_settlement_deduction.rental
     */
    public BigDecimal getRental() {
        return rental;
    }

    /**
     * 
     * {@linkplain #rental}
     * @param rental the value for sale_settlement_deduction.rental
     */
    public void setRental(BigDecimal rental) {
        this.rental = rental;
    }

    /**
     * 
     * {@linkplain #rate}
     *
     * @return the value of sale_settlement_deduction.rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 
     * {@linkplain #rate}
     * @param rate the value for sale_settlement_deduction.rate
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 
     * {@linkplain #baseAmount}
     *
     * @return the value of sale_settlement_deduction.base_amount
     */
    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    /**
     * 
     * {@linkplain #baseAmount}
     * @param baseAmount the value for sale_settlement_deduction.base_amount
     */
    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    /**
     * 
     * {@linkplain #baseRate}
     *
     * @return the value of sale_settlement_deduction.base_rate
     */
    public BigDecimal getBaseRate() {
        return baseRate;
    }

    /**
     * 
     * {@linkplain #baseRate}
     * @param baseRate the value for sale_settlement_deduction.base_rate
     */
    public void setBaseRate(BigDecimal baseRate) {
        this.baseRate = baseRate;
    }

    /**
     * 
     * {@linkplain #exceedBaseRate}
     *
     * @return the value of sale_settlement_deduction.exceed_base_rate
     */
    public BigDecimal getExceedBaseRate() {
        return exceedBaseRate;
    }

    /**
     * 
     * {@linkplain #exceedBaseRate}
     * @param exceedBaseRate the value for sale_settlement_deduction.exceed_base_rate
     */
    public void setExceedBaseRate(BigDecimal exceedBaseRate) {
        this.exceedBaseRate = exceedBaseRate;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of sale_settlement_deduction.status
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for sale_settlement_deduction.status
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #creatorNo}
     *
     * @return the value of sale_settlement_deduction.creator_no
     */
    public String getCreatorNo() {
        return creatorNo;
    }

    /**
     * 
     * {@linkplain #creatorNo}
     * @param creatorNo the value for sale_settlement_deduction.creator_no
     */
    public void setCreatorNo(String creatorNo) {
        this.creatorNo = creatorNo;
    }

    /**
     * 
     * {@linkplain #creator}
     *
     * @return the value of sale_settlement_deduction.creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 
     * {@linkplain #creator}
     * @param creator the value for sale_settlement_deduction.creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of sale_settlement_deduction.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for sale_settlement_deduction.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #editorNo}
     *
     * @return the value of sale_settlement_deduction.editor_no
     */
    public String getEditorNo() {
        return editorNo;
    }

    /**
     * 
     * {@linkplain #editorNo}
     * @param editorNo the value for sale_settlement_deduction.editor_no
     */
    public void setEditorNo(String editorNo) {
        this.editorNo = editorNo;
    }

    /**
     * 
     * {@linkplain #editor}
     *
     * @return the value of sale_settlement_deduction.editor
     */
    public String getEditor() {
        return editor;
    }

    /**
     * 
     * {@linkplain #editor}
     * @param editor the value for sale_settlement_deduction.editor
     */
    public void setEditor(String editor) {
        this.editor = editor;
    }

    /**
     * 
     * {@linkplain #editTime}
     *
     * @return the value of sale_settlement_deduction.edit_time
     */
    public Date getEditTime() {
        return editTime;
    }

    /**
     * 
     * {@linkplain #editTime}
     * @param editTime the value for sale_settlement_deduction.edit_time
     */
    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    /**
     * 
     * {@linkplain #remarks}
     *
     * @return the value of sale_settlement_deduction.remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 
     * {@linkplain #remarks}
     * @param remarks the value for sale_settlement_deduction.remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}