package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-12-28 11:09:32
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
public class SettlementBodyChangeRecord {
    private Integer id;
    
    /**
     * 商品编号
     */
    private String itemNo;

    /**
     * 商品编码
     */
    @ExcelCell("A")
    private String itemCode;
    
    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 品牌编号
     */
    @ExcelCell("B")
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌部编号
     */
    private String brandUnitNo;

    /**
     * 品牌部名称
     */
    private String brandUnitName;
    
    /**
	 * 大类编码
	 */
	private String categoryNo;
	
	/**
	 * 大类名称
	 */
	private String categoryName;
	
	/**
	 * 一级大类编码
	 */
	private String firstLevelCategoryNo;
	
	/**
	 * 一级大类名称
	 */
	private String firstLevelCategoryName;
	
	/**
	 * 二级大类编码
	 */
	private String secondLevelCategoryNo;
	
	/**
	 * 二级大类名称
	 */
	private String secondLevelCategoryName;
	
    /**
     * 数量
     */
    @ExcelCell("C")
    private Integer qty;

    /**
     * 存货属性(0-正品,1-次品,2-原残,4-客残)
     */
    @ExcelCell("D")
    private Integer stockType;
    
    private String stockTypeName;

    /**
     * 存货成本
     */
    @ExcelCell("E")
    private BigDecimal stockCost;

    /**
     * 变更价格
     */
    @ExcelCell("F")
    private BigDecimal changePrice;
    
    private BigDecimal stockCostSum;
    
    private BigDecimal changePriceSum;

    /**
     * 原公司编号
     */
    @ExcelCell("G")
    private String originalCompanyNo;

    /**
     * 原公司名称
     */
    private String originalCompanyName;

    /**
     * 原订货单位编号
     */
    @ExcelCell("H")
    private String originalOrderUnitNo;

    /**
     * 原订货单位名称
     */
    private String originalOrderUnitName;

    /**
     * 原机构编号
     */
    @ExcelCell("I")
    private String originalStoreNo;

    /**
     * 原机构名称
     */
    private String originalStoreName;

    /**
     * 目标公司编号
     */
    @ExcelCell("J")
    private String targetCompanyNo;

    /**
     * 目标公司名称
     */
    private String targetCompanyName;

    /**
     * 目标订货单位编号
     */
    @ExcelCell("K")
    private String targetOrderUnitNo;

    /**
     * 目标订货单位名称
     */
    private String targetOrderUnitName;

    /**
     * 目标机构编号
     */
    @ExcelCell("L")
    private String targetStoreNo;

    /**
     * 目标机构名称
     */
    private String targetStoreName;

    /**
     * 变更日期
     */
    @ExcelCell("M")
    @JsonSerialize(using = JsonDateSerializer$10.class)  
   	@JsonDeserialize(using = JsonDateDeserialize$10.class) 
    private Date changeDate;

    /**
     * 目的价格
     */
    private BigDecimal targetPrice;

    /**
     * 备注
     */
    @ExcelCell("N")
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBrandNo() {
        return brandNo;
    }

    public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandUnitNo() {
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

    public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getFirstLevelCategoryNo() {
		return firstLevelCategoryNo;
	}

	public void setFirstLevelCategoryNo(String firstLevelCategoryNo) {
		this.firstLevelCategoryNo = firstLevelCategoryNo;
	}

	public String getFirstLevelCategoryName() {
		return firstLevelCategoryName;
	}

	public void setFirstLevelCategoryName(String firstLevelCategoryName) {
		this.firstLevelCategoryName = firstLevelCategoryName;
	}

	public String getSecondLevelCategoryNo() {
		return secondLevelCategoryNo;
	}

	public void setSecondLevelCategoryNo(String secondLevelCategoryNo) {
		this.secondLevelCategoryNo = secondLevelCategoryNo;
	}

	public String getSecondLevelCategoryName() {
		return secondLevelCategoryName;
	}

	public void setSecondLevelCategoryName(String secondLevelCategoryName) {
		this.secondLevelCategoryName = secondLevelCategoryName;
	}

	public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public String getStockTypeName() {
		return stockTypeName;
	}

	public void setStockTypeName(String stockTypeName) {
		this.stockTypeName = stockTypeName;
	}

	public BigDecimal getStockCost() {
        return stockCost;
    }

    public void setStockCost(BigDecimal stockCost) {
        this.stockCost = stockCost;
    }

    public BigDecimal getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(BigDecimal changePrice) {
        this.changePrice = changePrice;
    }

    public BigDecimal getStockCostSum() {
		return stockCostSum;
	}

	public void setStockCostSum(BigDecimal stockCostSum) {
		this.stockCostSum = stockCostSum;
	}

	public BigDecimal getChangePriceSum() {
		return changePriceSum;
	}

	public void setChangePriceSum(BigDecimal changePriceSum) {
		this.changePriceSum = changePriceSum;
	}

	public String getOriginalCompanyNo() {
        return originalCompanyNo;
    }

    public void setOriginalCompanyNo(String originalCompanyNo) {
        this.originalCompanyNo = originalCompanyNo;
    }

    public String getOriginalCompanyName() {
        return originalCompanyName;
    }

    public void setOriginalCompanyName(String originalCompanyName) {
        this.originalCompanyName = originalCompanyName;
    }

    public String getOriginalOrderUnitNo() {
        return originalOrderUnitNo;
    }

    public void setOriginalOrderUnitNo(String originalOrderUnitNo) {
        this.originalOrderUnitNo = originalOrderUnitNo;
    }

    public String getOriginalOrderUnitName() {
        return originalOrderUnitName;
    }

    public void setOriginalOrderUnitName(String originalOrderUnitName) {
        this.originalOrderUnitName = originalOrderUnitName;
    }

    public String getOriginalStoreNo() {
        return originalStoreNo;
    }

    public void setOriginalStoreNo(String originalStoreNo) {
        this.originalStoreNo = originalStoreNo;
    }

    public String getOriginalStoreName() {
        return originalStoreName;
    }

    public void setOriginalStoreName(String originalStoreName) {
        this.originalStoreName = originalStoreName;
    }

    public String getTargetCompanyNo() {
        return targetCompanyNo;
    }

    public void setTargetCompanyNo(String targetCompanyNo) {
        this.targetCompanyNo = targetCompanyNo;
    }

    public String getTargetCompanyName() {
        return targetCompanyName;
    }

    public void setTargetCompanyName(String targetCompanyName) {
        this.targetCompanyName = targetCompanyName;
    }

    public String getTargetOrderUnitNo() {
        return targetOrderUnitNo;
    }

    public void setTargetOrderUnitNo(String targetOrderUnitNo) {
        this.targetOrderUnitNo = targetOrderUnitNo;
    }

    public String getTargetOrderUnitName() {
        return targetOrderUnitName;
    }

    public void setTargetOrderUnitName(String targetOrderUnitName) {
        this.targetOrderUnitName = targetOrderUnitName;
    }

    public String getTargetStoreNo() {
        return targetStoreNo;
    }

    public void setTargetStoreNo(String targetStoreNo) {
        this.targetStoreNo = targetStoreNo;
    }

    public String getTargetStoreName() {
        return targetStoreName;
    }

    public void setTargetStoreName(String targetStoreName) {
        this.targetStoreName = targetStoreName;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}