/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：TotalDto  
 * 类描述：结算单合计行
 * 创建人：ouyang.zm  
 * 创建时间：2014-10-13 下午2:12:40  
 * @version       
 */
package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

public class BAReceiptDto {

	private String id;
	private String supplierNo;
	public String getReceiveStoreName() {
		return receiveStoreName;
	}

	public void setReceiveStoreName(String receiveStoreName) {
		this.receiveStoreName = receiveStoreName;
	}

	public String getReceiveStoreNo() {
		return receiveStoreNo;
	}

	public void setReceiveStoreNo(String receiveStoreNo) {
		this.receiveStoreNo = receiveStoreNo;
	}

	private String supplierName;
	private String salerNo;
	private String remark;
	private String receiveStoreName;
	private String receiveStoreNo;
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	private String salerName;
	private String buyerNo;
	private String buyerName;
	private String styleNo;

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	private String originalBillNo;
	private String itemNo;
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date sendDate;
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date receiveDate;
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date receiveDateEnd;
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date receiveDateStart;
	private String brandName;
	private String brandNo;
	private String brandUnitName;

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	public String getBrandUnitNo() {
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}

	private String brandUnitNo;
	private String itemCode;
	private String itemName;
	
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal tagPrice;
	
	private Integer receiveQty;
	
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal cost;
	
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal purchaseAmount;
	
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal tagAmount;
	
	private String currencyName;
	private String standardCurrencyName;
	
	private String exchangeRate;
	
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal totalAmount;
	private String orderUnitName;
	private String organName;
	private String orderNo;
	private String categoryName;
	private String categoryNo;

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	private String oneLevelCategoryName;
	private String twoLevelCategoryName;
	private String balanceNo;

	public Date getReceiveDateEnd() {
		return receiveDateEnd;
	}

	public void setReceiveDateEnd(Date receiveDateEnd) {
		this.receiveDateEnd = receiveDateEnd;
	}

	public Date getReceiveDateStart() {
		return receiveDateStart;
	}

	public void setReceiveDateStart(Date receiveDateStart) {
		this.receiveDateStart = receiveDateStart;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSalerNo() {
		return salerNo;
	}

	public void setSalerNo(String salerNo) {
		this.salerNo = salerNo;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getBuyerNo() {
		return buyerNo;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
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

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getStandardCurrencyName() {
		return standardCurrencyName;
	}

	public void setStandardCurrencyName(String standardCurrencyName) {
		this.standardCurrencyName = standardCurrencyName;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	public String getOrderUnitName() {
		return orderUnitName;
	}

	public void setOrderUnitName(String orderUnitName) {
		this.orderUnitName = orderUnitName;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTwoLevelCategoryName() {
		return twoLevelCategoryName;
	}

	public void setTwoLevelCategoryName(String twoLevelCategoryName) {
		this.twoLevelCategoryName = twoLevelCategoryName;
	}

	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}

	public String getOriginalBillNo() {
		return originalBillNo;
	}

	public void setOriginalBillNo(String originalBillNo) {
		this.originalBillNo = originalBillNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public Integer getReceiveQty() {
		return receiveQty;
	}

	public void setReceiveQty(Integer receiveQty) {
		this.receiveQty = receiveQty;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public BigDecimal getTagAmount() {
		return tagAmount;
	}

	public void setTagAmount(BigDecimal tagAmount) {
		this.tagAmount = tagAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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

	public String getOneLevelCategoryName() {
		return oneLevelCategoryName;
	}

	public void setOneLevelCategoryName(String oneLevelCategoryName) {
		this.oneLevelCategoryName = oneLevelCategoryName;
	}
}
