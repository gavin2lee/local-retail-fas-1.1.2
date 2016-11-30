/**  
*   
* 项目名称：retail-fas-common  
* 类名称：BillBalanceDto  
* 类描述：用于查询显示结算相关信息的DTO
* 创建人：wang.m 
* 创建时间：2014-10-17 下午1:13:48  
* @version       
*/ 
package cn.wonhigh.retail.fas.common.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.ReportBizTypeEnums;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

public class ReportDto implements Serializable{

	private static final long serialVersionUID = -3433732941104973588L;
	
	private Integer index;
	
	/**
	 * 大区编码
	 */
	private String zoneNo;
	
	/**
	 * 大区名称
	 */
	private String zoneName;
	
	/**
	 * 公司编码
	 */
	private String buyerNo;
	
	/**
	 * 公司名称
	 */
	private String buyerName;
	
	/**
	 * 供应商编码
	 */
	private String salerNo;
	
	/**
	 * 供应商名称
	 */
	private String salerName;

	/**
	 * 商品编码
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
	 * 品牌部编码
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
	 * 品牌编码
	 */
	private String brandNo;
	
	/**
	 * 品牌名称
	 */
	private String brandName;
	
	/**
	 * 性别
	 */
	private String gender;
	
	/**
	 * 年份
	 */
	private String years;
	
	/**
	 * 季节
	 */
	private String season;
	
	/**
	 * 管理城市编号
	 */
	private String organNo;
	
	/**
	 * 管理城市
	 */
	private String organName;
	
	/**
	 * 订货形式
	 */
	private String orderfrom;
	
	/**
	 * 类型
	 */
	private Integer bizType;
	
	
	/**
	 * 订货单位编号
	 */
	private String orderUnitNo;
	
	/**
	 * 订货单位
	 */
	private String orderUnitName;
	
	/**
	 * 单价
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal cost;
	
	/**
	 * 进货数量
	 */
	private Integer sendQty;
	
	/**
	 * 进货金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal sendAmount;
	
	/**
	 * 退货数量
	 */
	private Integer returnQty;
	
	/**
	 * 退货金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal returnAmount;
	
	/**
	 * 累计进货数量
	 */
	private Integer totalSendQty;
	
	/**
	 * 累计进货金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal totalSendAmount;
	
	/**
	 * 累计退货数量
	 */
	private Integer totalReturnQty;
	
	/**
	 * 累计退货金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal totalReturnAmount;
    
    /**
	 * 应付数量(今年累计)
	 */
	private Integer totalBalanceQty;
	
	/**
	 * 应付金额(今年累计)
	 */
	private BigDecimal totalBalanceAmount;
	
	/**
	 * 累计平均单价
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal totalCost;
	
	/**
	 * 其他扣项金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal deductionAmount;
    
    /**
	 * 应付数量
	 */
	private Integer balanceQty;
	
	/**
	 * 应收/应付金额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal balanceAmount;
	
	/**
	 * 类型
	 */
	private String bizTypeName;
	
	/**
	 * 订货形式
	 */
	private String orderfromName;
	
	/**
	 * 产品季节
	 */
	private String seasonName;
	
	/**
	 * 季节
	 */
	private String purchaseSeasonName;
	
	/**
	 * 性别
	 */
	private String genderName;
	
	/**
	 * 年份
	 */
	private String yearsName;
	
	/**
	 * 供应商编号
	 */
	private String supplierNo;
	
	/**
	 * 供应商
	 */
	private String supplierName;
	
	/**
	 * 供应商组名称
	 */
	private String supplierGroupName;
	
	/**
	 * 一级大类编码
	 */
	private String oneLevelCategoryNo;
	
	/**
	 * 一级大类
	 */
	private String oneLevelCategoryName;
	
	/**
	 * 二级大类编码
	 */
	private String twoLevelCategoryNo;
	
	/**
	 * 二级大类
	 */ 
	private String twoLevelCategoryName;
	
	/**
	 * 三级大类编码
	 */
	private String threeLevelCategoryNo;
	
	/**
	 * 三级大类
	 */ 
	private String threeLevelCategoryName;
	
	/**
	 * 发货日期
	 */
	private Date sendDate;
	
	/**
	 * 采购价
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal purchasePrice;
    
	/**
	 * 厂进价
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal factoryPrice;
    
	/**
	 * 物料价
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal materialPrice;
    
	/**
	 * 牌价
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal tagPrice;
    
	/**
	 * 总部成本加价
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal headquarterAdd;
    
	/**
	 * 总部成本
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal headquarterCost;
    
	/**
	 * 接收方地区价加价
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal regionAdd;
    
	/**
	 * 接收方地区价
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal regionCost;
    
	/**
	 * 发出方地区价
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal regionCostFrom;
    
	/**
	 * 采购额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal purchaseAmount;
    
	/**
	 * 物料额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal materialAmount;
    
	/**
	 * 厂进额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal factoryAmount;
    
	/**
	 * 总部成本额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal headquarterAmount;
    
	/**
	 * 接收方地区额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal regionAmount;
    
	/**
	 * 发出方地区额
	 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal regionFromAmount;
    
	public String getPurchaseSeasonName() {
		return purchaseSeasonName;
	}

	public void setPurchaseSeasonName(String purchaseSeasonName) {
		this.purchaseSeasonName = purchaseSeasonName;
	}

	public String getThreeLevelCategoryNo() {
		return threeLevelCategoryNo;
	}

	public void setThreeLevelCategoryNo(String threeLevelCategoryNo) {
		this.threeLevelCategoryNo = threeLevelCategoryNo;
	}

	public String getThreeLevelCategoryName() {
		return threeLevelCategoryName;
	}

	public void setThreeLevelCategoryName(String threeLevelCategoryName) {
		this.threeLevelCategoryName = threeLevelCategoryName;
	}

	public String getSupplierGroupName() {
		return supplierGroupName;
	}

	public void setSupplierGroupName(String supplierGroupName) {
		this.supplierGroupName = supplierGroupName;
	}

	public BigDecimal getHeadquarterAdd() {
		return headquarterAdd;
	}

	public void setHeadquarterAdd(BigDecimal headquarterAdd) {
		this.headquarterAdd = headquarterAdd;
	}

	public BigDecimal getRegionAdd() {
		return regionAdd;
	}

	public void setRegionAdd(BigDecimal regionAdd) {
		this.regionAdd = regionAdd;
	}

	public BigDecimal getHeadquarterCost() {
		return headquarterCost;
	}

	public void setHeadquarterCost(BigDecimal headquarterCost) {
		this.headquarterCost = headquarterCost;
	}

	public BigDecimal getRegionCost() {
		return regionCost;
	}

	public void setRegionCost(BigDecimal regionCost) {
		this.regionCost = regionCost;
	}

	public BigDecimal getRegionCostFrom() {
		return regionCostFrom;
	}

	public void setRegionCostFrom(BigDecimal regionCostFrom) {
		this.regionCostFrom = regionCostFrom;
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public BigDecimal getMaterialAmount() {
		return materialAmount;
	}

	public void setMaterialAmount(BigDecimal materialAmount) {
		this.materialAmount = materialAmount;
	}

	public BigDecimal getFactoryAmount() {
		return factoryAmount;
	}

	public void setFactoryAmount(BigDecimal factoryAmount) {
		this.factoryAmount = factoryAmount;
	}

	public BigDecimal getHeadquarterAmount() {
		return headquarterAmount;
	}

	public void setHeadquarterAmount(BigDecimal headquarterAmount) {
		this.headquarterAmount = headquarterAmount;
	}

	public BigDecimal getRegionAmount() {
		return regionAmount;
	}

	public void setRegionAmount(BigDecimal regionAmount) {
		this.regionAmount = regionAmount;
	}

	public BigDecimal getRegionFromAmount() {
		return regionFromAmount;
	}

	public void setRegionFromAmount(BigDecimal regionFromAmount) {
		this.regionFromAmount = regionFromAmount;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public String getOneLevelCategoryNo() {
		return oneLevelCategoryNo;
	}

	public void setOneLevelCategoryNo(String oneLevelCategoryNo) {
		this.oneLevelCategoryNo = oneLevelCategoryNo;
	}

	public String getTwoLevelCategoryNo() {
		return twoLevelCategoryNo;
	}

	public void setTwoLevelCategoryNo(String twoLevelCategoryNo) {
		this.twoLevelCategoryNo = twoLevelCategoryNo;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
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

	public String getOrganNo() {
		return organNo;
	}

	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}

	public String getOrderUnitNo() {
		return orderUnitNo;
	}

	public void setOrderUnitNo(String orderUnitNo) {
		this.orderUnitNo = orderUnitNo;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getFactoryPrice() {
		return factoryPrice;
	}

	public void setFactoryPrice(BigDecimal factoryPrice) {
		this.factoryPrice = factoryPrice;
	}

	public BigDecimal getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(BigDecimal materialPrice) {
		this.materialPrice = materialPrice;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getBrandUnitNo() {
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}

	public String getSalerNo() {
		return salerNo;
	}

	public void setSalerNo(String salerNo) {
		this.salerNo = salerNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getYearsName() {
		return yearsName;
	}

	public void setYearsName(String yearsName) {
		this.yearsName = yearsName;
	}

	public String getOneLevelCategoryName() {
		return oneLevelCategoryName;
	}

	public void setOneLevelCategoryName(String oneLevelCategoryName) {
		this.oneLevelCategoryName = oneLevelCategoryName;
	}

	public String getTwoLevelCategoryName() {
		return twoLevelCategoryName;
	}

	public void setTwoLevelCategoryName(String twoLevelCategoryName) {
		this.twoLevelCategoryName = twoLevelCategoryName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBizTypeName() {
		return bizTypeName;
	}

	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}

	public String getOrderfromName() {
		return orderfromName;
	}

	public void setOrderfromName(String orderfromName) {
		this.orderfromName = orderfromName;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getOrderUnitName() {
		return orderUnitName;
	}

	public void setOrderUnitName(String orderUnitName) {
		this.orderUnitName = orderUnitName;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public Integer getSendQty() {
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}

	public BigDecimal getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount = sendAmount;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getOrderfrom() {
		return orderfrom;
	}

	public void setOrderfrom(String orderfrom) {
		this.orderfrom = orderfrom;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
		setBizTypeName(ReportBizTypeEnums.getNameByNo(bizType));
	}

	public Integer getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(Integer returnQty) {
		this.returnQty = returnQty;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public Integer getTotalSendQty() {
		return totalSendQty;
	}

	public void setTotalSendQty(Integer totalSendQty) {
		this.totalSendQty = totalSendQty;
	}

	public BigDecimal getTotalSendAmount() {
		return totalSendAmount;
	}

	public void setTotalSendAmount(BigDecimal totalSendAmount) {
		this.totalSendAmount = totalSendAmount;
	}

	public Integer getTotalReturnQty() {
		return totalReturnQty;
	}

	public void setTotalReturnQty(Integer totalReturnQty) {
		this.totalReturnQty = totalReturnQty;
	}

	public BigDecimal getTotalReturnAmount() {
		return totalReturnAmount;
	}

	public void setTotalReturnAmount(BigDecimal totalReturnAmount) {
		this.totalReturnAmount = totalReturnAmount;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getBuyerNo() {
		return buyerNo;
	}

	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	
	public Integer getBalanceQty() {
		return balanceQty;
	}

	public void setBalanceQty(Integer balanceQty) {
		this.balanceQty = balanceQty;
	}
	
	public Integer getTotalBalanceQty() {
		return totalBalanceQty;
	}

	public void setTotalBalanceQty(Integer totalBalanceQty) {
		this.totalBalanceQty = totalBalanceQty;
	}

	public BigDecimal getTotalBalanceAmount() {
		return totalBalanceAmount;
	}

	public void setTotalBalanceAmount(BigDecimal totalBalanceAmount) {
		this.totalBalanceAmount = totalBalanceAmount;
	}
}
