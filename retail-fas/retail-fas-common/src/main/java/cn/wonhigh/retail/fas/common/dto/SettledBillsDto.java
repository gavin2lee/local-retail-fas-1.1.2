/**
 * title:SettledBillsDto.java
 * package:cn.wonhigh.retail.fas.common.dto
 * description:已结算单据
 * auther:user
 * date:2016-4-11 上午10:31:14
 */
package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.OrderTypeEnums;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$4;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

public class SettledBillsDto {
	private String supplierNo;
	
	private String supplierName;
	
	private Integer billType;
	
	private String billTypeName;
	
	private String billNo;
	
	private String itemNo;
	
	private String itemCode;
	
	private String itemName;
	
	private String brandNo;
	
	private String brandName;
	
	private String categoryNo;
	
	private String categoryName;
	
	/**
	 * 订货类型
	 */
	private Integer orderType;
	
	private String orderTypeName;
	
	private Integer sendQty;
	
    /**
     * 结算金额
     */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceAmount;
	
	/**
	 * 结算单号
	 */
	private String balanceNo;
	
	/**
	 * 结算号
	 */
	private String settlementNumber;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
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
    
    private String buyerNo;
    
    private String buyerName;
    
    private String orderUnitNo;
    
    private String orderUnitName;
    
    private String receiveStoreNo;
    
    private String receiveStoreName;
    
    /**
     * 审核状态
     */
    private Integer auditStatus;
    
    private String auditStatusName;
    
    /** 牌价 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal tagPrice;
	
    /** 牌价额 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal tagPriceAmount;
	
	/** 厂商折扣1 */
	@JsonSerialize(using = BigDecimalSerializer$4.class)
	private BigDecimal supplierDiscount1;
	
	/** 厂商折扣2 */
	@JsonSerialize(using = BigDecimalSerializer$4.class)
	private BigDecimal supplierDiscount2;
	
	/** 厂商金额 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal supplierAmount;
	
	/** 差异金额 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal diffAmount;
	
	
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

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
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

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
		if (null != orderType) {
			setOrderTypeName(OrderTypeEnums.getNameByNo(orderType));
		}
	}

	public Integer getSendQty() {
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
		if (auditStatus != null && auditStatus == 0) {
			setAuditStatusName("未审核");
		}else if(auditStatus != null && auditStatus == 1) {
			setAuditStatusName("已审核");
		}else{
			setAuditStatusName("");
		}
	}

	public String getAuditStatusName() {
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
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

	public String getSettlementNumber() {
		return settlementNumber;
	}

	public void setSettlementNumber(String settlementNumber) {
		this.settlementNumber = settlementNumber;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public BigDecimal getSupplierAmount() {
		return supplierAmount;
	}

	public void setSupplierAmount(BigDecimal supplierAmount) {
		this.supplierAmount = supplierAmount;
	}

	public BigDecimal getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(BigDecimal diffAmount) {
		this.diffAmount = diffAmount;
	}

	public BigDecimal getSupplierDiscount1() {
		return supplierDiscount1;
	}

	public void setSupplierDiscount1(BigDecimal supplierDiscount1) {
		this.supplierDiscount1 = supplierDiscount1;
	}

	public BigDecimal getSupplierDiscount2() {
		return supplierDiscount2;
	}

	public void setSupplierDiscount2(BigDecimal supplierDiscount2) {
		this.supplierDiscount2 = supplierDiscount2;
	}
	
}
