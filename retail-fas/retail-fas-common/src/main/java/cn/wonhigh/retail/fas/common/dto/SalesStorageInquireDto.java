/**
 * title:SalesStorageInquireDto.java
 * package:cn.wonhigh.retail.fas.common.dto
 * description:代销商品销存Dto
 * auther:user
 * date:2016-4-21 下午2:40:22
 */
package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

public class SalesStorageInquireDto {
	private String billNo;
	
	private Integer billType;
	
	private String billTypeName;
	
	private String supplierNo;

	private String supplierName;
	
    @JsonSerialize(using = JsonDateSerializer$10.class)  
	private Date sendDate;

	private String buyerNo;

	private String buyerName;
	
	private String zoneNo;
	
	private String  zoneName;
	
	private String organNo;
	
	private String organName;
	
    private String orderUnitNo;
    
    private String orderUnitName;
    
    private String receiveStoreNo;
    
    private String receiveStoreName;
    
    private String itemNo;
	
	private String itemCode;
	
	private String itemName;
	
	private String brandNo;
	
	private String brandName;
	
	private String categoryNo;
	
	private String categoryName;
	
	private Integer sendQty;
	
	/** 牌价 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal tagPrice;

	/** 牌价额 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal tagPriceAmount;
	
	/** 结算金额 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal balanceAmount;
	
	/**
	 * 折扣1
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal discount1;
	
	/**
	 * 折扣2
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal discount2;
	
	/**
	 * 指定价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal specifiedPrice;
	
	/**
	 * 终端销售金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal terminalSalesAmount;

	/**
	 * 期初数量
	 */
    private Integer earlyQty;

    /**
	 * 期初牌价额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal earlyTagPriceAmount;
	
     /**
	 * 期初结算额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal earlyBalanceAmount;
	
	/**
	 * 到货数量
	 */
	private Integer arrivedQty;
	
	/**
	 * 到货牌价额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal arrivedTagPriceAmount;
	
	/**
	 * 到货结算额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal arrivedBalanceAmount;
	
	/**
	 * 销售数量
	 */
	private Integer salesQty;
	
	/**
	 * 销售牌价额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal salesTagPriceAmount;
	
	/**
	 * 销售结算额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal salesBalanceAmount;
	
	/**
	 * 盘差数量
	 */
	private Integer checkQty;
	
	/**
	 * 盘差牌价额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal checkTagPriceAmount;
	
	/**
	 * 盘差结算额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal checkBalanceAmount;
	
	/**
	 * 结存数量
	 */
	private Integer inventoryQty;
	
	/**
	 * 结存牌价额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal inventoryTagPriceAmount;
	
	/**
	 * 结存结算额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal inventoryBalanceAmount;
	
	/**
	 * 扣项金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal deductionAmount;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
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

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
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

	public Integer getSendQty() {
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public BigDecimal getTagPriceAmount() {
		return tagPriceAmount;
	}

	public void setTagPriceAmount(BigDecimal tagPriceAmount) {
		this.tagPriceAmount = tagPriceAmount;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public BigDecimal getDiscount1() {
		return discount1;
	}

	public void setDiscount1(BigDecimal discount1) {
		this.discount1 = discount1;
	}

	public BigDecimal getDiscount2() {
		return discount2;
	}

	public void setDiscount2(BigDecimal discount2) {
		this.discount2 = discount2;
	}

	public BigDecimal getSpecifiedPrice() {
		return specifiedPrice;
	}

	public void setSpecifiedPrice(BigDecimal specifiedPrice) {
		this.specifiedPrice = specifiedPrice;
	}

	public BigDecimal getTerminalSalesAmount() {
		return terminalSalesAmount;
	}

	public void setTerminalSalesAmount(BigDecimal terminalSalesAmount) {
		this.terminalSalesAmount = terminalSalesAmount;
	}

	public Integer getEarlyQty() {
		return earlyQty;
	}

	public void setEarlyQty(Integer earlyQty) {
		this.earlyQty = earlyQty;
	}

	public BigDecimal getEarlyTagPriceAmount() {
		return earlyTagPriceAmount;
	}

	public void setEarlyTagPriceAmount(BigDecimal earlyTagPriceAmount) {
		this.earlyTagPriceAmount = earlyTagPriceAmount;
	}

	public BigDecimal getEarlyBalanceAmount() {
		return earlyBalanceAmount;
	}

	public void setEarlyBalanceAmount(BigDecimal earlyBalanceAmount) {
		this.earlyBalanceAmount = earlyBalanceAmount;
	}

	public Integer getArrivedQty() {
		return arrivedQty;
	}

	public void setArrivedQty(Integer arrivedQty) {
		this.arrivedQty = arrivedQty;
	}

	public BigDecimal getArrivedTagPriceAmount() {
		return arrivedTagPriceAmount;
	}

	public void setArrivedTagPriceAmount(BigDecimal arrivedTagPriceAmount) {
		this.arrivedTagPriceAmount = arrivedTagPriceAmount;
	}

	public BigDecimal getArrivedBalanceAmount() {
		return arrivedBalanceAmount;
	}

	public void setArrivedBalanceAmount(BigDecimal arrivedBalanceAmount) {
		this.arrivedBalanceAmount = arrivedBalanceAmount;
	}

	public Integer getSalesQty() {
		return salesQty;
	}

	public void setSalesQty(Integer salesQty) {
		this.salesQty = salesQty;
	}

	public BigDecimal getSalesTagPriceAmount() {
		return salesTagPriceAmount;
	}

	public void setSalesTagPriceAmount(BigDecimal salesTagPriceAmount) {
		this.salesTagPriceAmount = salesTagPriceAmount;
	}

	public BigDecimal getSalesBalanceAmount() {
		return salesBalanceAmount;
	}

	public void setSalesBalanceAmount(BigDecimal salesBalanceAmount) {
		this.salesBalanceAmount = salesBalanceAmount;
	}

	public Integer getCheckQty() {
		return checkQty;
	}

	public void setCheckQty(Integer checkQty) {
		this.checkQty = checkQty;
	}

	public BigDecimal getCheckTagPriceAmount() {
		return checkTagPriceAmount;
	}

	public void setCheckTagPriceAmount(BigDecimal checkTagPriceAmount) {
		this.checkTagPriceAmount = checkTagPriceAmount;
	}

	public BigDecimal getCheckBalanceAmount() {
		return checkBalanceAmount;
	}

	public void setCheckBalanceAmount(BigDecimal checkBalanceAmount) {
		this.checkBalanceAmount = checkBalanceAmount;
	}

	public Integer getInventoryQty() {
		return inventoryQty;
	}

	public void setInventoryQty(Integer inventoryQty) {
		this.inventoryQty = inventoryQty;
	}

	public BigDecimal getInventoryTagPriceAmount() {
		return inventoryTagPriceAmount;
	}

	public void setInventoryTagPriceAmount(BigDecimal inventoryTagPriceAmount) {
		this.inventoryTagPriceAmount = inventoryTagPriceAmount;
	}

	public BigDecimal getInventoryBalanceAmount() {
		return inventoryBalanceAmount;
	}

	public void setInventoryBalanceAmount(BigDecimal inventoryBalanceAmount) {
		this.inventoryBalanceAmount = inventoryBalanceAmount;
	}

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}
	
	
}
