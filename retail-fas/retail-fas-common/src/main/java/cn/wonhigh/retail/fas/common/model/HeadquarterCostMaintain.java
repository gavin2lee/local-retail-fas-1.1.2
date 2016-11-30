package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.HeadquarterCostMaintainExportFormat;
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
@ExportFormat(className=HeadquarterCostMaintainExportFormat.class)
public class HeadquarterCostMaintain extends FasBaseModel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3572434865497746818L;

    /**
     * 商品编号
     */
    private String itemNo;
    
    /**
     * 商品出厂编号
     */
    @ExcelCell("A")
    private String itemCode;
    
    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 商品品牌
     */
    @ExcelCell("B")
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
     * MPS牌价
     */
    private BigDecimal tagPrice;
    
    /**
     * 生效日期
     */
    @ExcelCell("D")
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date effectiveTime;

    /**
     * 加价规则编号
     */
    private String addRuleNo;

    /**
     * 总部成本
     */
    @ExcelCell("C")
    private BigDecimal headquarterCost;
    
    /**
     * 生产成本
     */
    private BigDecimal productionCost;
    
    /**
     * 加价依据,1厂进价，2牌价
     */
    private String addBasis;

    /**
     * 加价依据,1厂进价，2牌价
     */
    private String addBasisName;
    
    /**
     * 厂进价
     */
    private BigDecimal factoryPrice;
    
    /**
     * 采购价
     */
    private BigDecimal purchasePrice;
    
    /**
     * 物料价
     */
    private BigDecimal materialPrice;
    
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

    private List<HeadquarterPriceRule> matchedHeadquarterPriceRule;
    
    /**
     * 品牌组多选下拉用
     */
    private String[] brandUnitNos;
    
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

	public String[] getBrandUnitNos() {
		return brandUnitNos;
	}

	public void setBrandUnitNos(String[] brandUnitNos) {
		this.brandUnitNos = brandUnitNos;
	}

	/**
     * 匹配失败原因
     */
    private String failMessage;
    
    /**
     * 是否首增
     * @return
     */
    private String firstNew;

    public String getTwoLevelCategoryNo() {
		return twoLevelCategoryNo;
	}

	public void setTwoLevelCategoryNo(String twoLevelCategoryNo) {
		this.twoLevelCategoryNo = twoLevelCategoryNo;
	}

	public String getFailMessage() {
		return failMessage;
	}

	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}

	public List<HeadquarterPriceRule> getMatchedHeadquarterPriceRule() {
		return matchedHeadquarterPriceRule;
	}

	public void setMatchedHeadquarterPriceRule(
			List<HeadquarterPriceRule> matchedHeadquarterPriceRule) {
		this.matchedHeadquarterPriceRule = matchedHeadquarterPriceRule;
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
			return this.addBasis.equals("2") ? "牌价" : "厂进价";
		}
		return addBasisName;
	}

	public void setAddBasisName(String addBasisName) {
		this.addBasisName = addBasisName;
	}
	

	public BigDecimal getFactoryPrice() {
		return factoryPrice;
	}

	public void setFactoryPrice(BigDecimal factoryPrice) {
		this.factoryPrice = factoryPrice;
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

    /**
     * 
     * {@linkplain #headquarterCost}
     *
     * @return the value of headquarter_cost_maintain.headquarter_cost
     */
    public BigDecimal getHeadquarterCost() {
        return headquarterCost;
    }

    /**
     * 
     * {@linkplain #headquarterCost}
     * @param headquarterCost the value for headquarter_cost_maintain.headquarter_cost
     */
    public void setHeadquarterCost(BigDecimal headquarterCost) {
        this.headquarterCost = headquarterCost;
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

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public String getFirstNew() {
		return firstNew;
	}

	public void setFirstNew(String firstNew) {
		this.firstNew = firstNew;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(BigDecimal materialPrice) {
		this.materialPrice = materialPrice;
	}
	
	
}