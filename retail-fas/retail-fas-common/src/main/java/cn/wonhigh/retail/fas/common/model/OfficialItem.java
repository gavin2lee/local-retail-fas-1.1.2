package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-11 11:15:11
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
public class OfficialItem implements Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 报价单号
     */
    private String quoteNo;

    /**
     * 商品编码
     */
    private String itemCode;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 供应商编码
     */
    private String supplierNo;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌部编码
     */
    private String brandUnitNo;

    /**
     * 品牌部名称
     */
    private String brandUnitName;

    /**
     * 小类编码
     */
    private String categoryNo;

    /**
     * 小类名称
     */
    private String categoryName;

    /**
     * 大类编码
     */
    private String rootCategoryNo;

    /**
     * 大类名称
     */
    private String rootCategoryName;

    /**
     * 年份编码
     */
    private String yearNo;

    /**
     * 年份
     */
    private String year;

    /**
     * 季节编码
     */
    private String seasonNo;

    /**
     * 货号
     */
    private String styleNo;
    
    /**
     * 季节
     */
    private String season;

    /**
     * 核定价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal aprice;

    /**
     * 报价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal qprice;

    /**
     * 系统进货价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal sysPrice;

    /**
     * OA超额状态
     */
    private Byte excessStatus;

    /**
     * OA超额状态名称
     */
    private String excessStatusName;

    /**
     * 更新时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)   
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date updateTime;

    
    public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of official_item.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for official_item.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #quoteNo}
     *
     * @return the value of official_item.quote_no
     */
    public String getQuoteNo() {
        return quoteNo;
    }

    /**
     * 
     * {@linkplain #quoteNo}
     * @param quoteNo the value for official_item.quote_no
     */
    public void setQuoteNo(String quoteNo) {
        this.quoteNo = quoteNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of official_item.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for official_item.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of official_item.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for official_item.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of official_item.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for official_item.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of official_item.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 
     * {@linkplain #supplierName}
     * @param supplierName the value for official_item.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of official_item.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for official_item.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of official_item.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for official_item.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     *
     * @return the value of official_item.brand_unit_no
     */
    public String getBrandUnitNo() {
        return brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     * @param brandUnitNo the value for official_item.brand_unit_no
     */
    public void setBrandUnitNo(String brandUnitNo) {
        this.brandUnitNo = brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     *
     * @return the value of official_item.brand_unit_name
     */
    public String getBrandUnitName() {
        return brandUnitName;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     * @param brandUnitName the value for official_item.brand_unit_name
     */
    public void setBrandUnitName(String brandUnitName) {
        this.brandUnitName = brandUnitName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of official_item.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for official_item.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of official_item.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for official_item.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #rootCategoryNo}
     *
     * @return the value of official_item.root_category_no
     */
    public String getRootCategoryNo() {
        return rootCategoryNo;
    }

    /**
     * 
     * {@linkplain #rootCategoryNo}
     * @param rootCategoryNo the value for official_item.root_category_no
     */
    public void setRootCategoryNo(String rootCategoryNo) {
        this.rootCategoryNo = rootCategoryNo;
    }

    /**
     * 
     * {@linkplain #rootCategoryName}
     *
     * @return the value of official_item.root_category_name
     */
    public String getRootCategoryName() {
        return rootCategoryName;
    }

    /**
     * 
     * {@linkplain #rootCategoryName}
     * @param rootCategoryName the value for official_item.root_category_name
     */
    public void setRootCategoryName(String rootCategoryName) {
        this.rootCategoryName = rootCategoryName;
    }

    /**
     * 
     * {@linkplain #yearNo}
     *
     * @return the value of official_item.year_no
     */
    public String getYearNo() {
        return yearNo;
    }

    /**
     * 
     * {@linkplain #yearNo}
     * @param yearNo the value for official_item.year_no
     */
    public void setYearNo(String yearNo) {
        this.yearNo = yearNo;
    }

    /**
     * 
     * {@linkplain #year}
     *
     * @return the value of official_item.year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * {@linkplain #year}
     * @param year the value for official_item.year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * {@linkplain #seasonNo}
     *
     * @return the value of official_item.season_no
     */
    public String getSeasonNo() {
        return seasonNo;
    }

    /**
     * 
     * {@linkplain #seasonNo}
     * @param seasonNo the value for official_item.season_no
     */
    public void setSeasonNo(String seasonNo) {
        this.seasonNo = seasonNo;
    }

    /**
     * 
     * {@linkplain #season}
     *
     * @return the value of official_item.season
     */
    public String getSeason() {
        return season;
    }

    /**
     * 
     * {@linkplain #season}
     * @param season the value for official_item.season
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     * 
     * {@linkplain #aprice}
     *
     * @return the value of official_item.aprice
     */
    public BigDecimal getAprice() {
        return aprice;
    }

    /**
     * 
     * {@linkplain #aprice}
     * @param aprice the value for official_item.aprice
     */
    public void setAprice(BigDecimal aprice) {
        this.aprice = aprice;
    }

    /**
     * 
     * {@linkplain #qprice}
     *
     * @return the value of official_item.qprice
     */
    public BigDecimal getQprice() {
        return qprice;
    }

    /**
     * 
     * {@linkplain #qprice}
     * @param qprice the value for official_item.qprice
     */
    public void setQprice(BigDecimal qprice) {
        this.qprice = qprice;
    }

    /**
     * 
     * {@linkplain #sysPrice}
     *
     * @return the value of official_item.sys_price
     */
    public BigDecimal getSysPrice() {
        return sysPrice;
    }

    /**
     * 
     * {@linkplain #sysPrice}
     * @param sysPrice the value for official_item.sys_price
     */
    public void setSysPrice(BigDecimal sysPrice) {
        this.sysPrice = sysPrice;
    }

    /**
     * 
     * {@linkplain #excessStatus}
     *
     * @return the value of official_item.excess_status
     */
    public Byte getExcessStatus() {
        return excessStatus;
    }

    /**
     * 
     * {@linkplain #excessStatus}
     * @param excessStatus the value for official_item.excess_status
     */
    public void setExcessStatus(Byte excessStatus) {
        this.excessStatus = excessStatus;
    }

    /**
     * 
     * {@linkplain #excessStatusName}
     *
     * @return the value of official_item.excess_status_name
     */
    public String getExcessStatusName() {
        return excessStatusName;
    }

    /**
     * 
     * {@linkplain #excessStatusName}
     * @param excessStatusName the value for official_item.excess_status_name
     */
    public void setExcessStatusName(String excessStatusName) {
        this.excessStatusName = excessStatusName;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of official_item.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for official_item.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}