/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：HqAreaReconciliationDto  
 * 类描述：总部地区明细对账Dto
 * 创建人：ouyang.zm  
 * 创建时间：2015-3-9 下午2:10:27  
 * @version       
 */
package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.ReportBizTypeEnums;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

public class HqAreaReconciliationDto {
	/**
	 * 单据编号
	 */
	private String billNo;

	/**
	 * 类型
	 */
	private Integer bizType;

	/**
	 * 类型名称
	 */
	private String bizTypeName;

	/**
	 * 单据类型(1301-到货单,1333-调货出库单,1335-客残销售出库单)
	 */
	private Integer billType;

	/**
	 * 买方编号
	 */
	private String buyerNo;

	/**
	 * 买方名称
	 */
	private String buyerName;

	/**
	 * 卖方编号
	 */
	private String salerNo;

	/**
	 * 卖方名称
	 */
	private String salerName;

	/**
	 * 发出日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date sendDate;

	/**
	 * 接收日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date receiveDate;

	/**
	 * 商品编号
	 */
	private String itemNo;

	/**
	 * 商品名称
	 */
	private String itemName;

	/**
	 * 品牌编号
	 */
	private String brandNo;

	/**
	 * 品牌名称
	 */
	private String brandName;

	/**
	 * 大类编号
	 */
	private String categoryNo;

	/**
	 * 大类名称
	 */
	private String categoryName;

	/**
	 * 含税单价(结算价格、默认原单据价格)
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal cost;

	/**
	 * 单据价格(原单据价格)
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal billCost;

	/**
	 * 发出数量
	 */
	private Integer sendQty;

	/**
	 * 接受数量
	 */
	private Integer receiveQty;

	/**
	 * 是否拆分单据
	 */
	private Integer isSplit;

	/**
	 * 商品编码
	 */
	private String itemCode;

	/** 发出方订货单位编码 */
	private String orderUnitNoFrom;

	/** 发出方订货单位名称 */
	private String orderUnitNameFrom;

	/** 发出方管理城市编号 */
	private String organNoFrom;

	/** 发出方管理城市名称 */
	private String organNameFrom;

	/** 发出方所属大区编码 */
	private String zoneNoFrom;

	/** 发出方所属大区名称 */
	private String zoneNameFrom;

	/** 接受方订货单位编码 */
	private String orderUnitNo;

	/** 接受方订货单位名称 */
	private String orderUnitName;

	/** 接受方管理城市编号 */
	private String organNo;

	/** 接受方管理城市名称 */
	private String organName;

	/** 接收方所属大区编码 */
	private String zoneNo;

	/** 接收方所属大区名称 */
	private String zoneName;

	/** 年份 */
	private String years;

	/** 季节 */
	private String season;

	/** 订货形式 */
	private String orderfrom;

	/** 性别 */
	private String gender;

	/** 牌价 */
	private BigDecimal tagPrice;

	/**
	 * 异常价格是否已更新
	 */
	private Integer costChecked;

	/* ############################扩展属性############################ */

	/**
	 * 单据类型显示名称
	 */
	private String billTypeName;

	/**
	 * 一级大类
	 */
	private String oneLevelCategoryNo;

	/**
	 * 一级大类名称
	 */
	private String oneLevelCategoryName;

	/**
	 * 二级大类
	 */
	private String twoLevelCategoryNo;

	/**
	 * 二级大类名称
	 */
	private String twoLevelCategoryName;

	/**
	 * 品牌部名称
	 */
	private String brandUnitName;

	/**
	 * 地区价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal areaCost;

	/**
	 * 采购价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal purchaseCost;

	/**
	 * 厂进金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal factoryPurchaseSum;

	/**
	 * 采购金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal purchaseAmount;

	/**
	 * 地区金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal areaAmount;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
		if (null != bizType) {
			setBizTypeName(ReportBizTypeEnums.getNameByNo(bizType));
		}
	}

	public String getBizTypeName() {
		return bizTypeName;
	}

	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
		if (null != billType) {
			setBillTypeName(BillTypeEnums.getNameByNo(billType));
		}
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

	public String getSalerNo() {
		return salerNo;
	}

	public void setSalerNo(String salerNo) {
		this.salerNo = salerNo;
	}

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getBillCost() {
		return billCost;
	}

	public void setBillCost(BigDecimal billCost) {
		this.billCost = billCost;
	}

	public Integer getSendQty() {
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}

	public Integer getReceiveQty() {
		return receiveQty;
	}

	public void setReceiveQty(Integer receiveQty) {
		this.receiveQty = receiveQty;
	}

	public Integer getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(Integer isSplit) {
		this.isSplit = isSplit;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getOrderUnitNoFrom() {
		return orderUnitNoFrom;
	}

	public void setOrderUnitNoFrom(String orderUnitNoFrom) {
		this.orderUnitNoFrom = orderUnitNoFrom;
	}

	public String getOrderUnitNameFrom() {
		return orderUnitNameFrom;
	}

	public void setOrderUnitNameFrom(String orderUnitNameFrom) {
		this.orderUnitNameFrom = orderUnitNameFrom;
	}

	public String getOrganNoFrom() {
		return organNoFrom;
	}

	public void setOrganNoFrom(String organNoFrom) {
		this.organNoFrom = organNoFrom;
	}

	public String getOrganNameFrom() {
		return organNameFrom;
	}

	public void setOrganNameFrom(String organNameFrom) {
		this.organNameFrom = organNameFrom;
	}

	public String getZoneNoFrom() {
		return zoneNoFrom;
	}

	public void setZoneNoFrom(String zoneNoFrom) {
		this.zoneNoFrom = zoneNoFrom;
	}

	public String getZoneNameFrom() {
		return zoneNameFrom;
	}

	public void setZoneNameFrom(String zoneNameFrom) {
		this.zoneNameFrom = zoneNameFrom;
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

	public String getOrganNo() {
		return organNo;
	}

	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
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

	public String getOrderfrom() {
		return orderfrom;
	}

	public void setOrderfrom(String orderfrom) {
		this.orderfrom = orderfrom;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public Integer getCostChecked() {
		return costChecked;
	}

	public void setCostChecked(Integer costChecked) {
		this.costChecked = costChecked;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getOneLevelCategoryNo() {
		return oneLevelCategoryNo;
	}

	public void setOneLevelCategoryNo(String oneLevelCategoryNo) {
		this.oneLevelCategoryNo = oneLevelCategoryNo;
	}

	public String getOneLevelCategoryName() {
		return oneLevelCategoryName;
	}

	public void setOneLevelCategoryName(String oneLevelCategoryName) {
		this.oneLevelCategoryName = oneLevelCategoryName;
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

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	public BigDecimal getAreaCost() {
		return areaCost;
	}

	public void setAreaCost(BigDecimal areaCost) {
		this.areaCost = areaCost;
	}

	public BigDecimal getPurchaseCost() {
		return purchaseCost;
	}

	public void setPurchaseCost(BigDecimal purchaseCost) {
		this.purchaseCost = purchaseCost;
	}

	public BigDecimal getFactoryPurchaseSum() {
		return factoryPurchaseSum;
	}

	public void setFactoryPurchaseSum(BigDecimal factoryPurchaseSum) {
		this.factoryPurchaseSum = factoryPurchaseSum;
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public BigDecimal getAreaAmount() {
		return areaAmount;
	}

	public void setAreaAmount(BigDecimal areaAmount) {
		this.areaAmount = areaAmount;
	}

}
