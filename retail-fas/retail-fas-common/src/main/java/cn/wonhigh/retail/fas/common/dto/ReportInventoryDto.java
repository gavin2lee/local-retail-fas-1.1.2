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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

public class ReportInventoryDto implements Serializable{

		private static final long serialVersionUID = -3433732941104973588L;
		
	    private String buyerNo;
	    
	    private String buyerName;
	    
	    private String itemNo;
	    
	    private String itemCode;
	    
	    private String itemName;
	    
	    private String brandNo;
	    
	    private String brandName;
	    
	    private String categoryNo;
	    
	    private String categoryName;
	    
	    private String companyNo;
	    
	    private String companyName;
	    
	    private String supplierNo;
	    
	    private String supplierName;
	    
	    private String supplierGroupName;
	    
	    private String storeNo;
	    
	    private String storeName;
	    
	    private Integer startReceiveQty;
	    
	    @JsonSerialize(using = BigDecimalSerializer$2.class)
	    private BigDecimal startReceiveAmount;
	    
	    private Integer startSendQty;
	    
	    @JsonSerialize(using = BigDecimalSerializer$2.class)
	    private BigDecimal startSendAmount;
	    
	    private Integer startDiffQty;
	    
	    @JsonSerialize(using = BigDecimalSerializer$2.class)
	    private BigDecimal startDiffAmount;
	    
	    private Integer currentReceiveQty;
	    
	    @JsonSerialize(using = BigDecimalSerializer$2.class)
	    private BigDecimal currentReceiveAmount;
	    
	    private Integer currentSendQty;
	    
	    @JsonSerialize(using = BigDecimalSerializer$2.class)
	    private BigDecimal currentSendAmount;
	    
	    private Integer currentDiffQty;
	    
	    @JsonSerialize(using = BigDecimalSerializer$2.class)
	    private BigDecimal currentDiffAmount;
	    
	    private Integer endDiffQty;
	    
	    @JsonSerialize(using = BigDecimalSerializer$2.class)
	    private BigDecimal endDiffAmount;

	    @JsonSerialize(using = BigDecimalSerializer$2.class)
	    private BigDecimal purchasePrice;
	    
	    @JsonSerialize(using = BigDecimalSerializer$2.class)
	    private BigDecimal materialPrice;
	    
	    @JsonSerialize(using = BigDecimalSerializer$2.class)
	    private BigDecimal factoryPrice;
	    
		public String getSupplierGroupName() {
			return supplierGroupName;
		}


		public void setSupplierGroupName(String supplierGroupName) {
			this.supplierGroupName = supplierGroupName;
		}


		public BigDecimal getPurchasePrice() {
			return purchasePrice;
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



		public void setPurchasePrice(BigDecimal purchasePrice) {
			this.purchasePrice = purchasePrice;
		}

		public BigDecimal getMaterialPrice() {
			return materialPrice;
		}

		public void setMaterialPrice(BigDecimal materialPrice) {
			this.materialPrice = materialPrice;
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

		public Integer getStartReceiveQty() {
			return startReceiveQty;
		}

		public void setStartReceiveQty(Integer startReceiveQty) {
			this.startReceiveQty = startReceiveQty;
		}

		public BigDecimal getStartReceiveAmount() {
			return startReceiveAmount;
		}

		public void setStartReceiveAmount(BigDecimal startReceiveAmount) {
			this.startReceiveAmount = startReceiveAmount;
		}

		public Integer getStartSendQty() {
			return startSendQty;
		}

		public void setStartSendQty(Integer startSendQty) {
			this.startSendQty = startSendQty;
		}

		public BigDecimal getStartSendAmount() {
			return startSendAmount;
		}

		public void setStartSendAmount(BigDecimal startSendAmount) {
			this.startSendAmount = startSendAmount;
		}

		public Integer getStartDiffQty() {
			return startDiffQty;
		}

		public void setStartDiffQty(Integer startDiffQty) {
			this.startDiffQty = startDiffQty;
		}

		public BigDecimal getStartDiffAmount() {
			return startDiffAmount;
		}

		public void setStartDiffAmount(BigDecimal startDiffAmount) {
			this.startDiffAmount = startDiffAmount;
		}

		public Integer getCurrentReceiveQty() {
			return currentReceiveQty;
		}

		public void setCurrentReceiveQty(Integer currentReceiveQty) {
			this.currentReceiveQty = currentReceiveQty;
		}

		public BigDecimal getCurrentReceiveAmount() {
			return currentReceiveAmount;
		}

		public void setCurrentReceiveAmount(BigDecimal currentReceiveAmount) {
			this.currentReceiveAmount = currentReceiveAmount;
		}

		public Integer getCurrentSendQty() {
			return currentSendQty;
		}

		public void setCurrentSendQty(Integer currentSendQty) {
			this.currentSendQty = currentSendQty;
		}

		public BigDecimal getCurrentSendAmount() {
			return currentSendAmount;
		}

		public void setCurrentSendAmount(BigDecimal currentSendAmount) {
			this.currentSendAmount = currentSendAmount;
		}

		public Integer getCurrentDiffQty() {
			return currentDiffQty;
		}

		public void setCurrentDiffQty(Integer currentDiffQty) {
			this.currentDiffQty = currentDiffQty;
		}

		public BigDecimal getCurrentDiffAmount() {
			return currentDiffAmount;
		}

		public void setCurrentDiffAmount(BigDecimal currentDiffAmount) {
			this.currentDiffAmount = currentDiffAmount;
		}

		public Integer getEndDiffQty() {
			return endDiffQty;
		}

		public void setEndDiffQty(Integer endDiffQty) {
			this.endDiffQty = endDiffQty;
		}

		public BigDecimal getEndDiffAmount() {
			return endDiffAmount;
		}

		public void setEndDiffAmount(BigDecimal endDiffAmount) {
			this.endDiffAmount = endDiffAmount;
		}

		public BigDecimal getFactoryPrice() {
			return factoryPrice;
		}

		public void setFactoryPrice(BigDecimal factoryPrice) {
			this.factoryPrice = factoryPrice;
		}
		
		
}
