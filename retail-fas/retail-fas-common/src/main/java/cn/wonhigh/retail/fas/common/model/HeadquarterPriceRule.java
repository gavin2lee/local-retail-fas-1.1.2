package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.HeadquarterPriceRuleExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
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
@ExportFormat(className=HeadquarterPriceRuleExportFormat.class)
public class HeadquarterPriceRule extends FasBaseModel implements SequenceStrId {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5608885757070189156L;

    /**
     * 规则编号
     */
    private String addRuleNo;

    /**
     * 供应商组代码
     */
    private String supplierGroupNo;
    
    /**
     * 供应商组编码（只用于页面显示）
     */
    private String supplierGroupName;

    /**
     * 品牌部列表,字符分割形式BL,TT,FD
     */
    private String brandUnitNo;

    /**
     * 品牌部名称列表
     */
    private String brandUnitName;
    
    /**
     * 品牌组多选下拉用
     */
    private String[] brandUnitNos;
    
    /**
     * 结算大类多选下拉用
     */
    private String[] settleCategoryNos;
    
    /**
     * 供应商组多选下拉用
     */
    private String[] supplierGroupNos;
    
    /**
     * 年份多选下拉用
     */
    private String[] yearCodes;
    
    /**
     * 大类
     */
    private String categoryNo;
    
    /**
     * 大类名称（仅用于页面展示）
     */
    private String categoryName;

    /**
     * 二级大类
     */
    private String twoLevelCategoryNo;
    
    /**
     * 二级大类名称（仅用于页面展示）
     */
    private String twoLevelCategoryName;
    
    /**
     * 是否新旧款，0否，1是
     */
    private Integer newStyleFlag;

    /**
     * 新旧款类型
     */
    private String styleType;
    
    /**
     * 新旧款名称（仅用于页面展示）
     */
    private String styleTypeName;

    /**
     * 年份
     */
    private String yearCode;
    
    /**
     * 年份
     */
    private String year;

    /**
     * 季节，A春，B夏，C秋，D冬
     */
    private String season;

    /**
     * 季节名称
     */
    private String seasonName;
    
    /**
     * 加价依据,1厂进价，2牌价
     */
    private String addBasis;

    /**
     * 生效日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date effectiveTime;

    /**
     * 加价
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal addPrice;

    /**
     * 加折
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal addDiscount;

    /**
     * 折扣
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal discountRate;

    
    public String[] getYearCodes() {
    	if(StringUtils.isNotEmpty(yearCode)) {
			return StringUtils.split(yearCode, ",");
		}
		return yearCodes;
	}

	public void setYearCodes(String[] yearCodes) {
		this.yearCodes = yearCodes;
	}

	public String getTwoLevelCategoryNo() {
		return twoLevelCategoryNo;
	}

	public void setTwoLevelCategoryNo(String twoLevelCategoryNo) {
		this.twoLevelCategoryNo = twoLevelCategoryNo;
	}

	public String getTwoLevelCategoryName() {
		return twoLevelCategoryName;
	}

	public void setTwoLevelCategoryName(String twoLevelCategoryName) {
		this.twoLevelCategoryName = twoLevelCategoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getStyleTypeName() {
		return styleTypeName;
	}

	public void setStyleTypeName(String styleTypeName) {
		this.styleTypeName = styleTypeName;
	}

	public String getSupplierGroupName() {
		return supplierGroupName;
	}

	public void setSupplierGroupName(String supplierGroupName) {
		this.supplierGroupName = supplierGroupName;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	/**
     * 
     * {@linkplain #addRuleNo}
     *
     * @return the value of headquarter_price_rule.add_rule_no
     */
    public String getAddRuleNo() {
        return addRuleNo;
    }

    /**
     * 
     * {@linkplain #addRuleNo}
     * @param addRuleNo the value for headquarter_price_rule.add_rule_no
     */
    public void setAddRuleNo(String addRuleNo) {
        this.addRuleNo = addRuleNo;
    }

    /**
     * 
     * {@linkplain #supplierGroupNo}
     *
     * @return the value of headquarter_price_rule.supplier_group_no
     */
    public String getSupplierGroupNo() {
    	if(supplierGroupNos != null && supplierGroupNos.length > 0) {
    		return StringUtils.join(supplierGroupNos, ",");
    	}
        return supplierGroupNo;
    }

    /**
     * 
     * {@linkplain #supplierGroupNo}
     * @param supplierGroupNo the value for headquarter_price_rule.supplier_group_no
     */
    public void setSupplierGroupNo(String supplierGroupNo) {
        this.supplierGroupNo = supplierGroupNo;
    }

    public String[] getSettleCategoryNos() {
    	if(StringUtils.isNotEmpty(categoryNo)) {
			return StringUtils.split(categoryNo, ",");
		}
		return settleCategoryNos;
	}

	public void setSettleCategoryNos(String[] settleCategoryNos) {
		this.settleCategoryNos = settleCategoryNos;
	}

	public String[] getSupplierGroupNos() {
		if(StringUtils.isNotEmpty(supplierGroupNo)) {
			return StringUtils.split(supplierGroupNo, ",");
		}
		return supplierGroupNos;
	}

	public void setSupplierGroupNos(String[] supplierGroupNos) {
		this.supplierGroupNos = supplierGroupNos;
	}

	public String getBrandUnitNo() {
    	if(brandUnitNos != null && brandUnitNos.length > 0) {
    		return StringUtils.join(brandUnitNos, ",");
    	}
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	public String[] getBrandUnitNos() {
		if(StringUtils.isNotEmpty(brandUnitNo)) {
			return StringUtils.split(brandUnitNo, ",");
		}
		return brandUnitNos;
	}

	public void setBrandUnitNos(String[] brandUnitNos) {
		this.brandUnitNos = brandUnitNos;
	}

	/**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of headquarter_price_rule.category_no
     */
    public String getCategoryNo() {
    	if(settleCategoryNos != null && settleCategoryNos.length > 0) {
    		return StringUtils.join(settleCategoryNos, ",");
    	}
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for headquarter_price_rule.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #newTyleFlag}
     *
     * @return the value of headquarter_price_rule.new_tyle_flag
     */
    public Integer getNewStyleFlag() {
        return newStyleFlag;
    }

    /**
     * 
     * {@linkplain #newTyleFlag}
     * @param newTyleFlag the value for headquarter_price_rule.new_tyle_flag
     */
    public void setNewStyleFlag(Integer newStyleFlag) {
        this.newStyleFlag = newStyleFlag;
    }

    /**
     * 
     * {@linkplain #styleType}
     *
     * @return the value of headquarter_price_rule.style_type
     */
    public String getStyleType() {
        return styleType;
    }

    /**
     * 
     * {@linkplain #styleType}
     * @param styleType the value for headquarter_price_rule.style_type
     */
    public void setStyleType(String styleType) {
        this.styleType = styleType;
    }

    public String getYearCode() {
    	if(yearCodes != null && yearCodes.length > 0) {
    		return StringUtils.join(yearCodes, ",");
    	}
        return yearCode;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	/**
     * 
     * {@linkplain #year}
     *
     * @return the value of headquarter_price_rule.year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * {@linkplain #year}
     * @param year the value for headquarter_price_rule.year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * {@linkplain #season}
     *
     * @return the value of headquarter_price_rule.season
     */
    public String getSeason() {
        return season;
    }

    /**
     * 
     * {@linkplain #season}
     * @param season the value for headquarter_price_rule.season
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     * 
     * {@linkplain #addBasis}
     *
     * @return the value of headquarter_price_rule.add_basis
     */
    public String getAddBasis() {
        return addBasis;
    }

    /**
     * 
     * {@linkplain #addBasis}
     * @param addBasis the value for headquarter_price_rule.add_basis
     */
    public void setAddBasis(String addBasis) {
        this.addBasis = addBasis;
    }

    /**
     * 
     * {@linkplain #effectiveTime}
     *
     * @return the value of headquarter_price_rule.effective_time
     */
    public Date getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * 
     * {@linkplain #effectiveTime}
     * @param effectiveTime the value for headquarter_price_rule.effective_time
     */
    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    /**
     * 
     * {@linkplain #addPrice}
     *
     * @return the value of headquarter_price_rule.add_price
     */
    public BigDecimal getAddPrice() {
        return addPrice;
    }

    /**
     * 
     * {@linkplain #addPrice}
     * @param addPrice the value for headquarter_price_rule.add_price
     */
    public void setAddPrice(BigDecimal addPrice) {
        this.addPrice = addPrice;
    }

    /**
     * 
     * {@linkplain #addDiscount}
     *
     * @return the value of headquarter_price_rule.add_discount
     */
    public BigDecimal getAddDiscount() {
        return addDiscount;
    }

    /**
     * 
     * {@linkplain #addDiscount}
     * @param addDiscount the value for headquarter_price_rule.add_discount
     */
    public void setAddDiscount(BigDecimal addDiscount) {
        this.addDiscount = addDiscount;
    }

    /**
     * 
     * {@linkplain #discountRate}
     *
     * @return the value of headquarter_price_rule.discount_rate
     */
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    /**
     * 
     * {@linkplain #discountRate}
     * @param discountRate the value for headquarter_price_rule.discount_rate
     */
    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

}