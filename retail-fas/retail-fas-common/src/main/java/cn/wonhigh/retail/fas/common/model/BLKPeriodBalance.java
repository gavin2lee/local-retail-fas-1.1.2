package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

import org.apache.xerces.impl.dv.xs.DecimalDV;

public class BLKPeriodBalance  extends FasBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3387765842153820619L;
	
	private BigDecimal lastUnitCost;
	private BigDecimal currentUnitCost;
	
	public BigDecimal getLastUnitCost() {
		return lastUnitCost;
	}

	public void setLastUnitCost(BigDecimal lastUnitCost) {
		this.lastUnitCost = lastUnitCost;
	}

	public BigDecimal getCurrentUnitCost() {
		return currentUnitCost;
	}

	public void setCurrentUnitCost(BigDecimal currentUnitCost) {
		this.currentUnitCost = currentUnitCost;
	}

	private Integer total;
	
	/**
     * 公司编码
     */
    private String companyNo;

    /**
     * 公司编码
     */
    private String companyName;
    
    /**
     * 机构编码
     */
    private String storeNo;
    
    /**
     * 管理城市
     */
    private String organName;
    
    /**
     * 机构名称
     */
    private String storeName;
    
    /**
     * 年份
     */
    private String year;

    /**
     * 月份
     */
    private String month;

    /**
     * 商品款号
     */
    private String styleNo;
    
    /**
     * 商品编号
     */
    private String itemNo;
    
    /**
     * 商品编码
     */
    private String itemCode;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
   	 * 品牌名称
   	 */
   	private String brandName;
   	
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
	 * 期初总部加权成本
	 */
	private BigDecimal preHeadquarterCost;
	
    /**
     * 期初数量
     */
    private Integer openingQty;

    /**
     * 期初余额
     */
    private BigDecimal openingBalance;

    /**
     * 采购入库数量
     */
    private Integer purchaseInQty;

    /**
     * 采购入库金额
     */
    private BigDecimal purchaseInAmount;

    /**
     * 跨区调入数量
     */
    private Integer outerTransferInQty;

    /**
     * 跨区调入金额
     */
    private BigDecimal outerTransferInAmount;

    /**
     * 采购退厂数量
     */
    private Integer purchaseReturnQty;

    /**
     * 采购退厂金额
     */
    private BigDecimal purchaseReturnAmount;
    
    /**
     * 区内调入数量
     */
    private Integer innerTransferInQty;
    
    /**
     * 区内入在途数量
     */
    private Integer innerWayQty;
    
    /**
     * 区内入在途差异数量
     */
    private Integer innerDiffQty;
    
    /**
     * 区内调出数量
     */
    private Integer innerTransferOutQty;

    /**
     * 盘盈数量
     */
    private Integer invSurplusQty;

    /**
     * 盘盈金额
     */
    private BigDecimal invSurplusAmount;

    /**
     * 其他入库数量
     */
    private Integer othersInQty;

    /**
     * 其他入库金额
     */
    private BigDecimal othersInAmount;

    /**
     * 外入在途数量
     */
    private Integer outerWayQty;

    /**
     * 外入在途金额
     */
    private BigDecimal outerWayAmount;

    /**
     * 外在途差异数量
     */
    private Integer outerDiffQty;

    /**
     * 外在途差异金额
     */
    private BigDecimal outerDiffAmount;

    /**
     * 差异调整金额
     */
    private BigDecimal costAdjustmentAmount;

    /**
     * 销售出库数量
     */
    private Integer salesOutQty;

    /**
     * 销售出库金额
     */
    private BigDecimal salesOutAmount;

    /**
     * 跨区调出数量
     */
    private Integer outerTransferOutQty;

    /**
     * 跨区跳出金额
     */
    private BigDecimal outerTransferOutAmount;

    /**
     * 盘亏数量
     */
    private Integer inventoryLossQty;

    /**
     * 盘亏金额
     */
    private BigDecimal inventoryLossAmount;

    /**
     * 其他出库数量
     */
    private Integer othersOutQty;

    /**
     * 其他出库金额
     */
    private BigDecimal othersOutAmount;

    /**
     * 期间净发生数量
     */
    private Integer duringNetQty;

    /**
     * 期间净发生金额
     */
    private BigDecimal duringNetAmount;

    /**
     * 期末数量
     */
    private Integer closingQty;

    /**
     * 期末余额
     */
    private BigDecimal closingBalance;

    /**
     * 单位成本
     */
    private BigDecimal unitCost;

    /**
     * 地区成本价
     */
    private BigDecimal regionCost;

    /**
     * 总部成本价
     */
    private BigDecimal headquarterCost;
    
    /**
     * 总部成本总金额
     */
    private BigDecimal closingHeadquarterCost;

    /**
     * 分库字段
     */
    private String shardingFlag;

    //前期欠客
    private Integer preSumOweQty;
    
    private Integer preAccountingQty;
    
    //期初财务存
    private BigDecimal preSumOweAmount;
    
    private BigDecimal preAccountingAmount;
    
    //本期欠客
    private Integer currSumOweQty;
    
    private Integer currAccountingQty;
    
    //财务存
    private BigDecimal currSumOweAmount;
    
    private BigDecimal currAccountingAmount;
    
    private BigDecimal weightedDifference;
    
    private BigDecimal closingBalanceReference;
    
    private Integer salesSumQty;
    private BigDecimal salesSumAmount;
    
    /**
	 * 商品年份
	 */
	private String years;
	
	/**
	 * 商品年份
	 */
	private String yearsName;
	
	/**
	 * 商品季节
	 */
	private String sellSeason;
	
	/**
	 * 采购季节
	 */
	private String purchaseSeason;
	
	/**
	 * 商品标志
	 */
	private String itemFlag;
	
	/**
	 * 季节
	 */
	private String seasonName;
	
	/**
	 * 性别
	 */
	private String gender;
	
	/**
	 * 性别
	 */
	private String orderfrom;
	
	/**
	 * 货管单位
	 */
	private String orderUnitNo;
	
	/**
	 * 货管单位名称
	 */
	private String orderUnitName;
	
	/**
     * 期间盘差数量
     */ 
    private Integer duringNetInventoryQty;
    
    /**
     * 期间盘差金额
     */
    private BigDecimal duringNetInventoryAmount;
    
    /**
     * 对账差异数量
     */
    private Integer balanceDiff;
    
    /**
     * 牌价
     */
    private BigDecimal tagPrice;
    

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	public String getBrandNo() {
		return brandNo;
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

	public Integer getOpeningQty() {
		return openingQty;
	}

	public void setOpeningQty(Integer openingQty) {
		this.openingQty = openingQty;
	}

	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	public Integer getPurchaseInQty() {
		return purchaseInQty;
	}

	public void setPurchaseInQty(Integer purchaseInQty) {
		this.purchaseInQty = purchaseInQty;
	}

	public BigDecimal getPurchaseInAmount() {
		return purchaseInAmount;
	}

	public void setPurchaseInAmount(BigDecimal purchaseInAmount) {
		this.purchaseInAmount = purchaseInAmount;
	}

	public Integer getOuterTransferInQty() {
		return outerTransferInQty;
	}

	public void setOuterTransferInQty(Integer outerTransferInQty) {
		this.outerTransferInQty = outerTransferInQty;
	}

	public BigDecimal getOuterTransferInAmount() {
		return outerTransferInAmount;
	}

	public void setOuterTransferInAmount(BigDecimal outerTransferInAmount) {
		this.outerTransferInAmount = outerTransferInAmount;
	}

	public Integer getPurchaseReturnQty() {
		return purchaseReturnQty;
	}

	public void setPurchaseReturnQty(Integer purchaseReturnQty) {
		this.purchaseReturnQty = purchaseReturnQty;
	}

	public BigDecimal getPurchaseReturnAmount() {
		return purchaseReturnAmount;
	}

	public void setPurchaseReturnAmount(BigDecimal purchaseReturnAmount) {
		this.purchaseReturnAmount = purchaseReturnAmount;
	}

	public Integer getInvSurplusQty() {
		return invSurplusQty;
	}

	public void setInvSurplusQty(Integer invSurplusQty) {
		this.invSurplusQty = invSurplusQty;
	}

	public BigDecimal getInvSurplusAmount() {
		return invSurplusAmount;
	}

	public void setInvSurplusAmount(BigDecimal invSurplusAmount) {
		this.invSurplusAmount = invSurplusAmount;
	}

	public Integer getOthersInQty() {
		return othersInQty;
	}

	public void setOthersInQty(Integer othersInQty) {
		this.othersInQty = othersInQty;
	}

	public BigDecimal getOthersInAmount() {
		return othersInAmount;
	}

	public void setOthersInAmount(BigDecimal othersInAmount) {
		this.othersInAmount = othersInAmount;
	}

	public Integer getOuterWayQty() {
		return outerWayQty;
	}

	public void setOuterWayQty(Integer outerWayQty) {
		this.outerWayQty = outerWayQty;
	}

	public BigDecimal getOuterWayAmount() {
		return outerWayAmount;
	}

	public void setOuterWayAmount(BigDecimal outerWayAmount) {
		this.outerWayAmount = outerWayAmount;
	}

	public Integer getOuterDiffQty() {
		return outerDiffQty;
	}

	public void setOuterDiffQty(Integer outerDiffQty) {
		this.outerDiffQty = outerDiffQty;
	}

	public BigDecimal getOuterDiffAmount() {
		return outerDiffAmount;
	}

	public void setOuterDiffAmount(BigDecimal outerDiffAmount) {
		this.outerDiffAmount = outerDiffAmount;
	}

	public BigDecimal getCostAdjustmentAmount() {
		return costAdjustmentAmount;
	}

	public void setCostAdjustmentAmount(BigDecimal costAdjustmentAmount) {
		this.costAdjustmentAmount = costAdjustmentAmount;
	}

	public Integer getSalesOutQty() {
		return salesOutQty;
	}

	public void setSalesOutQty(Integer salesOutQty) {
		this.salesOutQty = salesOutQty;
	}

	public BigDecimal getSalesOutAmount() {
		return salesOutAmount;
	}

	public void setSalesOutAmount(BigDecimal salesOutAmount) {
		this.salesOutAmount = salesOutAmount;
	}

	public Integer getOuterTransferOutQty() {
		return outerTransferOutQty;
	}

	public void setOuterTransferOutQty(Integer outerTransferOutQty) {
		this.outerTransferOutQty = outerTransferOutQty;
	}

	public BigDecimal getOuterTransferOutAmount() {
		return outerTransferOutAmount;
	}

	public void setOuterTransferOutAmount(BigDecimal outerTransferOutAmount) {
		this.outerTransferOutAmount = outerTransferOutAmount;
	}

	public Integer getInventoryLossQty() {
		return inventoryLossQty;
	}

	public void setInventoryLossQty(Integer inventoryLossQty) {
		this.inventoryLossQty = inventoryLossQty;
	}

	public BigDecimal getInventoryLossAmount() {
		return inventoryLossAmount;
	}

	public void setInventoryLossAmount(BigDecimal inventoryLossAmount) {
		this.inventoryLossAmount = inventoryLossAmount;
	}

	public Integer getOthersOutQty() {
		return othersOutQty;
	}

	public void setOthersOutQty(Integer othersOutQty) {
		this.othersOutQty = othersOutQty;
	}

	public BigDecimal getOthersOutAmount() {
		return othersOutAmount;
	}

	public void setOthersOutAmount(BigDecimal othersOutAmount) {
		this.othersOutAmount = othersOutAmount;
	}

	public Integer getDuringNetQty() {
		return duringNetQty;
	}

	public void setDuringNetQty(Integer duringNetQty) {
		this.duringNetQty = duringNetQty;
	}

	public BigDecimal getDuringNetAmount() {
		return duringNetAmount;
	}

	public void setDuringNetAmount(BigDecimal duringNetAmount) {
		this.duringNetAmount = duringNetAmount;
	}

	public Integer getClosingQty() {
		return closingQty;
	}

	public void setClosingQty(Integer closingQty) {
		this.closingQty = closingQty;
	}

	public BigDecimal getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(BigDecimal closingBalance) {
		this.closingBalance = closingBalance;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
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

	public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	public Integer getPreSumOweQty() {
		return preSumOweQty;
	}

	public void setPreSumOweQty(Integer preSumOweQty) {
		this.preSumOweQty = preSumOweQty;
	}

	public Integer getPreAccountingQty() {
		return preAccountingQty;
	}

	public void setPreAccountingQty(Integer preAccountingQty) {
		this.preAccountingQty = preAccountingQty;
	}

	public BigDecimal getPreSumOweAmount() {
		return preSumOweAmount;
	}

	public void setPreSumOweAmount(BigDecimal preSumOweAmount) {
		this.preSumOweAmount = preSumOweAmount;
	}

	public BigDecimal getPreAccountingAmount() {
		return preAccountingAmount;
	}

	public void setPreAccountingAmount(BigDecimal preAccountingAmount) {
		this.preAccountingAmount = preAccountingAmount;
	}

	public Integer getCurrSumOweQty() {
		return currSumOweQty;
	}

	public void setCurrSumOweQty(Integer currSumOweQty) {
		this.currSumOweQty = currSumOweQty;
	}

	public Integer getCurrAccountingQty() {
		return currAccountingQty;
	}

	public void setCurrAccountingQty(Integer currAccountingQty) {
		this.currAccountingQty = currAccountingQty;
	}

	public BigDecimal getCurrSumOweAmount() {
		return currSumOweAmount;
	}

	public void setCurrSumOweAmount(BigDecimal currSumOweAmount) {
		this.currSumOweAmount = currSumOweAmount;
	}

	public BigDecimal getCurrAccountingAmount() {
		return currAccountingAmount;
	}

	public void setCurrAccountingAmount(BigDecimal currAccountingAmount) {
		this.currAccountingAmount = currAccountingAmount;
	}

	public BigDecimal getWeightedDifference() {
		return weightedDifference;
	}

	public void setWeightedDifference(BigDecimal weightedDifference) {
		this.weightedDifference = weightedDifference;
	}

	public BigDecimal getClosingBalanceReference() {
		return closingBalanceReference;
	}

	public void setClosingBalanceReference(BigDecimal closingBalanceReference) {
		this.closingBalanceReference = closingBalanceReference;
	}

	public Integer getSalesSumQty() {
		return salesSumQty;
	}

	public void setSalesSumQty(Integer salesSumQty) {
		this.salesSumQty = salesSumQty;
	}

	public BigDecimal getSalesSumAmount() {
		return salesSumAmount;
	}

	public void setSalesSumAmount(BigDecimal salesSumAmount) {
		this.salesSumAmount = salesSumAmount;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getYearsName() {
		return yearsName;
	}

	public void setYearsName(String yearsName) {
		this.yearsName = yearsName;
	}

	public String getSellSeason() {
		return sellSeason;
	}

	public void setSellSeason(String sellSeason) {
		this.sellSeason = sellSeason;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOrderfrom() {
		return orderfrom;
	}

	public void setOrderfrom(String orderfrom) {
		this.orderfrom = orderfrom;
	}

	public Integer getDuringNetInventoryQty() {
		return duringNetInventoryQty;
	}

	public void setDuringNetInventoryQty(Integer duringNetInventoryQty) {
		this.duringNetInventoryQty = duringNetInventoryQty;
	}

	public BigDecimal getDuringNetInventoryAmount() {
		return duringNetInventoryAmount;
	}

	public void setDuringNetInventoryAmount(BigDecimal duringNetInventoryAmount) {
		this.duringNetInventoryAmount = duringNetInventoryAmount;
	}

	public Integer getBalanceDiff() {
		return balanceDiff;
	}

	public void setBalanceDiff(Integer balanceDiff) {
		this.balanceDiff = balanceDiff;
	}

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
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

	public String getOrderUnitNo() {
		return orderUnitNo;
	}

	public void setOrderUnitNo(String orderUnitNo) {
		this.orderUnitNo = orderUnitNo;
	}

	public String getOrderUnitName() {
		return orderUnitName;
	}

	public void setOrderUnitName(String orderUnitName) {
		this.orderUnitName = orderUnitName;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public Integer getInnerTransferInQty() {
		return innerTransferInQty;
	}

	public void setInnerTransferInQty(Integer innerTransferInQty) {
		this.innerTransferInQty = innerTransferInQty;
	}

	public Integer getInnerWayQty() {
		return innerWayQty;
	}

	public void setInnerWayQty(Integer innerWayQty) {
		this.innerWayQty = innerWayQty;
	}

	public Integer getInnerDiffQty() {
		return innerDiffQty;
	}

	public void setInnerDiffQty(Integer innerDiffQty) {
		this.innerDiffQty = innerDiffQty;
	}

	public Integer getInnerTransferOutQty() {
		return innerTransferOutQty;
	}

	public void setInnerTransferOutQty(Integer innerTransferOutQty) {
		this.innerTransferOutQty = innerTransferOutQty;
	}

	public BigDecimal getPreHeadquarterCost() {
		return preHeadquarterCost;
	}

	public void setPreHeadquarterCost(BigDecimal preHeadquarterCost) {
		this.preHeadquarterCost = preHeadquarterCost;
	}

	public String getPurchaseSeason() {
		return purchaseSeason;
	}

	public void setPurchaseSeason(String purchaseSeason) {
		this.purchaseSeason = purchaseSeason;
	}

	public String getItemFlag() {
		return itemFlag;
	}

	public void setItemFlag(String itemFlag) {
		this.itemFlag = itemFlag;
	}

	public BigDecimal getClosingHeadquarterCost() {
		return closingHeadquarterCost;
	}

	public void setClosingHeadquarterCost(BigDecimal closingHeadquarterCost) {
		this.closingHeadquarterCost = closingHeadquarterCost;
	}
}
