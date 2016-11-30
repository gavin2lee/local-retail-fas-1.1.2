package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.RegionCostMaintainExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-01 09:25:14
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
@ExportFormat(className=RegionCostMaintainExportFormat.class)
public class RegionCostMaintain extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5773219623701588913L;

    /**
     * 经营区域编号
     */
	@ExcelCell("A")
    private String zoneNo;
    
    /**
     * 经营区域名称
     */
    private String zoneName;

    /**
     * 地区成本
     */
    @ExcelCell("D")
    private BigDecimal regionCost;
    
    /**
     * 厂进价
     */
    private BigDecimal factoryPrice;
    
    /**
     * 总部成本
     */
    private BigDecimal headquarterCost;

    /**
     * 生产成本
     */
    private BigDecimal productionCost;
    
    List<RegionPriceRule> matchedRegionPriceRule;
    
    /**
     * 地区多选下拉用
     */
    private String[] zoneNos;
	
	 /**
     * 商品编号
     */
    private String itemNo;
    
    /**
     * 商品出厂编号
     */
    @ExcelCell("B")
    private String itemCode;
    
    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 商品品牌
     */
    @ExcelCell("C")
    private String brandNo;
    
    /**
     * 品牌名称
     */
    private String brandName;
    
    /**
     * 商品供应商
     */
    private String supplierNo;
    
    /**
     * 供应商名称
     */
    private String supplierName;
    
    /**
     * 商品大类
     */
    private String categoryNo;
    
    /**
     * 二级大类
     */
    private String twoLevelCategoryNo;
    
    /**
     * 商品年份
     */
    private String year;
    
    /**
     * 商品季节
     */
    private String season;
    
    /**
     * 商品牌价
     */
    private BigDecimal suggestTagPrice;
    
    /**
     * 生效日期
     */
    @ExcelCell("E")
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date effectiveTime;

    /**
     * 加价规则编号
     */
    private String addRuleNo;

    /**
     * 加价依据,3总部成本，2牌价
     */
    private String addBasis;

    /**
     * 加价依据,3总部成本，2牌价
     */
    private String addBasisName;
    
    /**
     * 加价
     */
    private BigDecimal addPrice;

    /**
     * 加折
     */
    private BigDecimal addDiscount;

    /**
     * 折扣
     */
    private BigDecimal discountRate;
    
    /**
     * 匹配结果
     */
    private  boolean matchResult;

    /**
     * 品牌组多选下拉用
     */
    private String[] brandUnitNos;

	/**
     * 匹配失败原因
     */
    private String failMessage;
    
    /**
     * 公司编码
     */
    private String companyNo;
    
    /**
     * 是否首增
     * @return
     */
    private String firstNew;
    
    /**
     * MPS牌价
     */
    private BigDecimal tagPrice;
    
    /**
     * 是否首增
     */
    private Integer isFirst;
    
    public BigDecimal getProductionCost() {
		return productionCost;
	}

	public void setProductionCost(BigDecimal productionCost) {
		this.productionCost = productionCost;
	}

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public BigDecimal getFactoryPrice() {
		return factoryPrice;
	}

	public void setFactoryPrice(BigDecimal factoryPrice) {
		this.factoryPrice = factoryPrice;
	}

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	/**
     * 分区使用
     */
    private String shardingFlag;
    
	public String getTwoLevelCategoryNo() {
		return twoLevelCategoryNo;
	}

	public void setTwoLevelCategoryNo(String twoLevelCategoryNo) {
		this.twoLevelCategoryNo = twoLevelCategoryNo;
	}

	public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	public String[] getZoneNos() {
		return zoneNos;
	}

	public void setZoneNos(String[] zoneNos) {
		this.zoneNos = zoneNos;
	}

	public List<RegionPriceRule> getMatchedRegionPriceRule() {
		return matchedRegionPriceRule;
	}

	public void setMatchedRegionPriceRule(
			List<RegionPriceRule> matchedRegionPriceRule) {
		this.matchedRegionPriceRule = matchedRegionPriceRule;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public BigDecimal getRegionCost() {
		return regionCost;
	}

	public void setRegionCost(BigDecimal regionCost) {
		this.regionCost = regionCost;
	}
	
    public BigDecimal getHeadquarterCost() {
		return headquarterCost;
	}

	public void setHeadquarterCost(BigDecimal headquarterCost) {
		this.headquarterCost = headquarterCost;
	}

	public String[] getBrandUnitNos() {
		return brandUnitNos;
	}

	public void setBrandUnitNos(String[] brandUnitNos) {
		this.brandUnitNos = brandUnitNos;
	}

    public String getFailMessage() {
		return failMessage;
	}

	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public boolean getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(boolean matchResult) {
		this.matchResult = matchResult;
	}

	public String getAddBasis() {
		return addBasis;
	}

	public void setAddBasis(String addBasis) {
		this.addBasis = addBasis;
	}

	public String getAddBasisName() {
		if (null != this.addBasis) {
			return this.addBasis.equals("1") ? "厂进价" : "总部成本";
		}
		return addBasisName;
	}

	public void setAddBasisName(String addBasisName) {
		this.addBasisName = addBasisName;
	}

	public BigDecimal getAddPrice() {
		return addPrice;
	}

	public void setAddPrice(BigDecimal addPrice) {
		this.addPrice = addPrice;
	}

	public BigDecimal getAddDiscount() {
		return addDiscount;
	}

	public void setAddDiscount(BigDecimal addDiscount) {
		this.addDiscount = addDiscount;
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of headquarter_cost_maintain.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for headquarter_cost_maintain.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
     * 
     * {@linkplain #effectiveTime}
     *
     * @return the value of headquarter_cost_maintain.effective_time
     */
    public Date getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * 
     * {@linkplain #effectiveTime}
     * @param effectiveTime the value for headquarter_cost_maintain.effective_time
     */
    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    /**
     * 
     * {@linkplain #addRuleNo}
     *
     * @return the value of headquarter_cost_maintain.add_rule_no
     */
    public String getAddRuleNo() {
        return addRuleNo;
    }

    /**
     * 
     * {@linkplain #addRuleNo}
     * @param addRuleNo the value for headquarter_cost_maintain.add_rule_no
     */
    public void setAddRuleNo(String addRuleNo) {
        this.addRuleNo = addRuleNo;
    }

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public BigDecimal getSuggestTagPrice() {
		return suggestTagPrice;
	}

	public void setSuggestTagPrice(BigDecimal suggestTagPrice) {
		this.suggestTagPrice = suggestTagPrice;
	}

	public String getFirstNew() {
		return firstNew;
	}

	public void setFirstNew(String firstNew) {
		this.firstNew = firstNew;
	}
	
}