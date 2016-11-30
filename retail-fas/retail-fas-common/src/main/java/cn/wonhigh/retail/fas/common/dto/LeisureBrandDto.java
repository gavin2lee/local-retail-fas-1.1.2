/**
 * title:LeisureBrandDto.java
 * package:cn.wonhigh.retail.fas.common.dto
 * description:
 * auther:user
 * date:2016-3-11 下午4:35:27
 */
package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.model.FasBaseModel;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

public class LeisureBrandDto extends FasBaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3827395579427485496L;

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
	 * 发出地编号
	 */
	private String sendStoreNo;

	/**
	 * 发出地名称
	 */
	private String sendStoreName;

	/**
	 * 收货地编号
	 */
	private String receiveStoreNo;

	/**
	 * 收货地名称
	 */
	private String receiveStoreName;

	/**
	 * 供应商编号
	 */
	private String supplierNo;

	/**
	 * 供应商名称
	 */
	private String supplierName;

	/**
	 * 商品编号
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
	 * 品牌编号
	 */
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
	 * 总部成本
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal headquarterCost;
	
	/**
	 * 地区成本
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal regionCost;
	
	/**
	 * 卖方地区价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal regionCostSecond;
	
	/**
	 * 单位成本
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal unitCost;
	
	/**
	 * 采购价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal purchasePrice;
	
	/**
	 * 采购额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal purchaseAmount;
	
	 /**
     * 发出金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal sendAmount;
    
    /**
     * 接收金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal receiveAmount;
    
    /**
     * 差异数量
     */
    private Integer differenceQty;
    
    /**
     * 差异金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal differenceAmount;
	
	/**
	 * 物料价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal materialPrice;
	
	/**
	 * 厂进价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal factoryPrice;

	/**
	 * 发出数量
	 */
	private Integer sendQty;

	/**
	 * 接受数量
	 */
	private Integer receiveQty;

	/**
	 * 结算单号
	 */
	private String balanceNo;

	/**
	 * 是否拆分单据
	 */
	private Integer isSplit;

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

	public String getSendStoreNo() {
		return sendStoreNo;
	}

	public void setSendStoreNo(String sendStoreNo) {
		this.sendStoreNo = sendStoreNo;
	}

	public String getSendStoreName() {
		return sendStoreName;
	}

	public void setSendStoreName(String sendStoreName) {
		this.sendStoreName = sendStoreName;
	}

	public String getReceiveStoreNo() {
		return receiveStoreNo;
	}

	public void setReceiveStoreNo(String receiveStoreNo) {
		this.receiveStoreNo = receiveStoreNo;
	}

	public String getReceiveStoreName() {
		return receiveStoreName;
	}

	public void setReceiveStoreName(String receiveStoreName) {
		this.receiveStoreName = receiveStoreName;
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

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
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

	public BigDecimal getRegionCostSecond() {
		return regionCostSecond;
	}

	public void setRegionCostSecond(BigDecimal regionCostSecond) {
		this.regionCostSecond = regionCostSecond;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
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

	public BigDecimal getFactoryPrice() {
		return factoryPrice;
	}

	public void setFactoryPrice(BigDecimal factoryPrice) {
		this.factoryPrice = factoryPrice;
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

	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}

	public Integer getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(Integer isSplit) {
		this.isSplit = isSplit;
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

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public BigDecimal getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount = sendAmount;
	}

	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public BigDecimal getDifferenceAmount() {
		return differenceAmount;
	}

	public void setDifferenceAmount(BigDecimal differenceAmount) {
		this.differenceAmount = differenceAmount;
	}

	public Integer getDifferenceQty() {
		return differenceQty;
	}

	public void setDifferenceQty(Integer differenceQty) {
		this.differenceQty = differenceQty;
	}

	
}
