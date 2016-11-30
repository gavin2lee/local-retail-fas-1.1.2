package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-10-27 16:10:00
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
public class PriceQuotationList implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1907982656890070837L;

	/**
     * 主键ID,UUID
     */
    private String id;

    /**
     * 机构类型,0-全国，1-地区，2-城市,3-店铺
     */
    private Byte organType;

    /**
     * 机构编号
     */
    private String organId;

    /**
     * 机构名称
     */
    private String organName;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 产品依据,1-商品编码，2-尺码
     */
    private Byte itemFlag;

    /**
     * 商品ID
     */
    private String itemNo;

    /**
     * 商品编码
     */
    private String itemCode;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 尺码
     */
    private String sizeNo;

    /**
     * 建议牌价
     */
    private BigDecimal suggestTagPrice;

    /**
     * 牌价
     */
    private BigDecimal tagPrice;

    /**
     * 牌价策略,0-全国统一定价，1-地区统一定价
     */
    private Byte quotationTactics;

    /**
     * 是否自定价,0-是，1-否
     */
    private Byte ownPriceFlag;

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
     * 
     * {@linkplain #id}
     *
     * @return the value of price_quotation_list.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for price_quotation_list.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #organType}
     *
     * @return the value of price_quotation_list.organ_type
     */
    public Byte getOrganType() {
        return organType;
    }

    /**
     * 
     * {@linkplain #organType}
     * @param organType the value for price_quotation_list.organ_type
     */
    public void setOrganType(Byte organType) {
        this.organType = organType;
    }

    /**
     * 
     * {@linkplain #organId}
     *
     * @return the value of price_quotation_list.organ_id
     */
    public String getOrganId() {
        return organId;
    }

    /**
     * 
     * {@linkplain #organId}
     * @param organId the value for price_quotation_list.organ_id
     */
    public void setOrganId(String organId) {
        this.organId = organId;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of price_quotation_list.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for price_quotation_list.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of price_quotation_list.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for price_quotation_list.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of price_quotation_list.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for price_quotation_list.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of price_quotation_list.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for price_quotation_list.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #itemFlag}
     *
     * @return the value of price_quotation_list.item_flag
     */
    public Byte getItemFlag() {
        return itemFlag;
    }

    /**
     * 
     * {@linkplain #itemFlag}
     * @param itemFlag the value for price_quotation_list.item_flag
     */
    public void setItemFlag(Byte itemFlag) {
        this.itemFlag = itemFlag;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of price_quotation_list.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for price_quotation_list.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of price_quotation_list.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for price_quotation_list.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of price_quotation_list.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for price_quotation_list.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     *
     * @return the value of price_quotation_list.size_no
     */
    public String getSizeNo() {
        return sizeNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     * @param sizeNo the value for price_quotation_list.size_no
     */
    public void setSizeNo(String sizeNo) {
        this.sizeNo = sizeNo;
    }

    /**
     * 
     * {@linkplain #suggestTagPrice}
     *
     * @return the value of price_quotation_list.suggest_tag_price
     */
    public BigDecimal getSuggestTagPrice() {
        return suggestTagPrice;
    }

    /**
     * 
     * {@linkplain #suggestTagPrice}
     * @param suggestTagPrice the value for price_quotation_list.suggest_tag_price
     */
    public void setSuggestTagPrice(BigDecimal suggestTagPrice) {
        this.suggestTagPrice = suggestTagPrice;
    }

    /**
     * 
     * {@linkplain #tagPrice}
     *
     * @return the value of price_quotation_list.tag_price
     */
    public BigDecimal getTagPrice() {
        return tagPrice;
    }

    /**
     * 
     * {@linkplain #tagPrice}
     * @param tagPrice the value for price_quotation_list.tag_price
     */
    public void setTagPrice(BigDecimal tagPrice) {
        this.tagPrice = tagPrice;
    }

    /**
     * 
     * {@linkplain #quotationTactics}
     *
     * @return the value of price_quotation_list.quotation_tactics
     */
    public Byte getQuotationTactics() {
        return quotationTactics;
    }

    /**
     * 
     * {@linkplain #quotationTactics}
     * @param quotationTactics the value for price_quotation_list.quotation_tactics
     */
    public void setQuotationTactics(Byte quotationTactics) {
        this.quotationTactics = quotationTactics;
    }

    /**
     * 
     * {@linkplain #ownPriceFlag}
     *
     * @return the value of price_quotation_list.own_price_flag
     */
    public Byte getOwnPriceFlag() {
        return ownPriceFlag;
    }

    /**
     * 
     * {@linkplain #ownPriceFlag}
     * @param ownPriceFlag the value for price_quotation_list.own_price_flag
     */
    public void setOwnPriceFlag(Byte ownPriceFlag) {
        this.ownPriceFlag = ownPriceFlag;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of price_quotation_list.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for price_quotation_list.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of price_quotation_list.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for price_quotation_list.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of price_quotation_list.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for price_quotation_list.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     *
     * @return the value of price_quotation_list.update_user_no
     */
    public String getUpdateUserNo() {
        return updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     * @param updateUserNo the value for price_quotation_list.update_user_no
     */
    public void setUpdateUserNo(String updateUserNo) {
        this.updateUserNo = updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of price_quotation_list.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for price_quotation_list.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of price_quotation_list.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for price_quotation_list.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}