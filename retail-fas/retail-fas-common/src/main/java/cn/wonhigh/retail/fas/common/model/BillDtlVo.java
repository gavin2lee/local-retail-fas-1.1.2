package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;


/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-29 13:20:36
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.;Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development; without the 
 * company's written consent; and any other individuals and 
 * organizations shall not be used; Copying; Modify or distribute 
 * the software.
 * 
 */
public class BillDtlVo implements Serializable {

	private static final long serialVersionUID = -5925424434462018342L;
	
		private String  salerNo;
		
		private String   buyerNo;
		
		private String   storeNo;
		
		private String   categoryNo;
		
		private String   billNo;
		
		@JsonSerialize(using = JsonDateSerializer$10.class)
		private Date     billDate;
		
		private String   brandNo;
		
		private String   itemNo;
		
		private String   itemName;
		
		private BigDecimal   supplierCost;
		
		private BigDecimal   cost;
		
		private int    inQty;

		private int    outQty;
		
		private BigDecimal inAmount;
		
		private BigDecimal outAmount;
		
		
		private int   billType;

		private String   billTypeName;
		
		private String   orderNo;

		private int balanceType;

		private String  salerName;
		
		private String   buyerName;
		
		private String brandName;
		
		private String storeName;
		
		private String categoryName;
		
		private int exceptionType;
		
		private String exceptionName;
		
		public int getExceptionType() {
			return exceptionType;
		}
		
		public String getBillTypeName() {
			for(BillTypeEnums e : BillTypeEnums.values()) {
				if(e.getRequestId() == billType) {
					return e.getDesc();
				}
			}
			return billTypeName;
		}

		public void setBillTypeName(String billTypeName) {
			this.billTypeName = billTypeName;
		}


		public void setExceptionType(int exceptionType) {
			this.exceptionType = exceptionType;
		}

		public String getExceptionName() {
			return exceptionName;
		}

		public void setExceptionName(String exceptionName) {
			this.exceptionName = exceptionName;
		}

		public String getBrandName() {
			return brandName;
		}

		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}

		public String getStoreName() {
			return storeName;
		}

		public void setStoreName(String storeName) {
			this.storeName = storeName;
		}

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		public String getSalerName() {
			return salerName;
		}

		public void setSalerName(String salerName) {
			this.salerName = salerName;
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

		public String getBuyerNo() {
			return buyerNo;
		}

		public void setBuyerNo(String buyerNo) {
			this.buyerNo = buyerNo;
		}

		public String getStoreNo() {
			return storeNo;
		}

		public void setStoreNo(String storeNo) {
			this.storeNo = storeNo;
		}

		public String getCategoryNo() {
			return categoryNo;
		}

		public void setCategoryNo(String categoryNo) {
			this.categoryNo = categoryNo;
		}

		public String getBillNo() {
			return billNo;
		}

		public void setBillNo(String billNo) {
			this.billNo = billNo;
		}

		public Date getBillDate() {
			return billDate;
		}

		public void setBillDate(Date billDate) {
			this.billDate = billDate;
		}

		public String getBrandNo() {
			return brandNo;
		}

		public void setBrandNo(String brandNo) {
			this.brandNo = brandNo;
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

		public BigDecimal getSupplierCost() {
			return supplierCost;
		}

		public void setSupplierCost(BigDecimal supplierCost) {
			this.supplierCost = supplierCost;
		}

		public BigDecimal getCost() {
			return cost;
		}

		public void setCost(BigDecimal cost) {
			this.cost = cost;
		}

		public int getBillType() {
			return billType;
		}

		public void setBillType(int billType) {
			this.billType = billType;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public int getBalanceType() {
			return balanceType;
		}

		public void setBalanceType(int balanceType) {
			this.balanceType = balanceType;
		}

		public int getInQty() {
			return inQty;
		}

		public void setInQty(int inQty) {
			this.inQty = inQty;
		}

		public int getOutQty() {
			return outQty;
		}

		public void setOutQty(int outQty) {
			this.outQty = outQty;
		}

		public BigDecimal getInAmount() {
			return inAmount;
		}

		public void setInAmount(BigDecimal inAmount) {
			this.inAmount = inAmount;
		}

		public BigDecimal getOutAmount() {
			return outAmount;
		}

		public void setOutAmount(BigDecimal outAmount) {
			this.outAmount = outAmount;
		}

	  
		
}