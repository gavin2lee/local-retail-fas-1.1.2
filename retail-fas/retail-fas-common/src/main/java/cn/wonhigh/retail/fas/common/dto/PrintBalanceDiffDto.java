/**  
* 项目名称：retail-fas-common  
* 类名称：BalanceDto  
* 类描述：用于查询显示结算相关信息的DTO
* 创建人：wang.m 
* 创建时间：2014-10-17 下午1:13:48  
* @version       
*/ 
package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;


public class PrintBalanceDiffDto{
	
	private String companyNo;
	
	private String companyName;
	
	private String brandUnitNo;
	
	private String brandUnitName;
	
	private String year;
	
	private String month;
	
    private Integer allInQty;
    
    private Integer purchaseInQty;
	
    private Integer outerTransferInQty;
    
    private Integer hqPurchaseInQty;
    
    private Integer areaPurchaseInQty;
	
	private Integer areaBuyInQty;
	
	private Integer purchaseReturnQty;

    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal purchaseReturnAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal allInAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal purchaseInAmount;
	
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal outerTransferInAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal hqPurchaseInAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal areaPurchaseInAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal areaBuyInAmount;
    
 	private Integer allOutQty;    
    
	private Integer salesOutQty;
      
	private Integer outerTransferOutQty;
    
	private Integer outerWholesaleOutQty;
    
	private Integer outerOtherOutQty;
    
	private Integer outerWithinOutQty;
    
	private Integer outerPosOutQty;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal allOutAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal allOutCost;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal salesOutCost;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal salesOutAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal outerTransferOutCost;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal outerTransferOutAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal outerWholesaleOutCost;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal outerWholesaleOutAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal outerOtherOutCost;
 
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal outerOtherOutAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal outerWithinOutCost;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal outerWithinOutAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal outerPosOutCost;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal outerPosOutAmount;
    
    private Integer thisOweOffQty;

    private Integer preOweOffOutQty;

    private Integer beginningOweOffQty;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal thisOweOffCost;

    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal preOweOffOutCost;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal beginningOweOffCost;

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

	public Integer getAllInQty() {
		return allInQty;
	}

	public void setAllInQty(Integer allInQty) {
		this.allInQty = allInQty;
	}

	public Integer getPurchaseInQty() {
		return purchaseInQty;
	}

	public void setPurchaseInQty(Integer purchaseInQty) {
		this.purchaseInQty = purchaseInQty;
	}

	public Integer getOuterTransferInQty() {
		return outerTransferInQty;
	}

	public void setOuterTransferInQty(Integer outerTransferInQty) {
		this.outerTransferInQty = outerTransferInQty;
	}

	public Integer getHqPurchaseInQty() {
		return hqPurchaseInQty;
	}

	public void setHqPurchaseInQty(Integer hqPurchaseInQty) {
		this.hqPurchaseInQty = hqPurchaseInQty;
	}

	public Integer getAreaPurchaseInQty() {
		return areaPurchaseInQty;
	}

	public void setAreaPurchaseInQty(Integer areaPurchaseInQty) {
		this.areaPurchaseInQty = areaPurchaseInQty;
	}

	public Integer getAreaBuyInQty() {
		return areaBuyInQty;
	}

	public void setAreaBuyInQty(Integer areaBuyInQty) {
		this.areaBuyInQty = areaBuyInQty;
	}

	public BigDecimal getAllInAmount() {
		return allInAmount;
	}

	public void setAllInAmount(BigDecimal allInAmount) {
		this.allInAmount = allInAmount;
	}

	public BigDecimal getPurchaseInAmount() {
		return purchaseInAmount;
	}

	public void setPurchaseInAmount(BigDecimal purchaseInAmount) {
		this.purchaseInAmount = purchaseInAmount;
	}

	public BigDecimal getOuterTransferInAmount() {
		return outerTransferInAmount;
	}

	public void setOuterTransferInAmount(BigDecimal outerTransferInAmount) {
		this.outerTransferInAmount = outerTransferInAmount;
	}

	public BigDecimal getHqPurchaseInAmount() {
		return hqPurchaseInAmount;
	}

	public void setHqPurchaseInAmount(BigDecimal hqPurchaseInAmount) {
		this.hqPurchaseInAmount = hqPurchaseInAmount;
	}

	public BigDecimal getAreaPurchaseInAmount() {
		return areaPurchaseInAmount;
	}

	public void setAreaPurchaseInAmount(BigDecimal areaPurchaseInAmount) {
		this.areaPurchaseInAmount = areaPurchaseInAmount;
	}

	public BigDecimal getAreaBuyInAmount() {
		return areaBuyInAmount;
	}

	public void setAreaBuyInAmount(BigDecimal areaBuyInAmount) {
		this.areaBuyInAmount = areaBuyInAmount;
	}

	public Integer getAllOutQty() {
		return allOutQty;
	}

	public void setAllOutQty(Integer allOutQty) {
		this.allOutQty = allOutQty;
	}

	public Integer getSalesOutQty() {
		return salesOutQty;
	}

	public void setSalesOutQty(Integer salesOutQty) {
		this.salesOutQty = salesOutQty;
	}

	public Integer getOuterTransferOutQty() {
		return outerTransferOutQty;
	}

	public void setOuterTransferOutQty(Integer outerTransferOutQty) {
		this.outerTransferOutQty = outerTransferOutQty;
	}

	public Integer getOuterWholesaleOutQty() {
		return outerWholesaleOutQty;
	}

	public void setOuterWholesaleOutQty(Integer outerWholesaleOutQty) {
		this.outerWholesaleOutQty = outerWholesaleOutQty;
	}

	public Integer getOuterOtherOutQty() {
		return outerOtherOutQty;
	}

	public void setOuterOtherOutQty(Integer outerOtherOutQty) {
		this.outerOtherOutQty = outerOtherOutQty;
	}

	public Integer getOuterWithinOutQty() {
		return outerWithinOutQty;
	}

	public void setOuterWithinOutQty(Integer outerWithinOutQty) {
		this.outerWithinOutQty = outerWithinOutQty;
	}

	public Integer getOuterPosOutQty() {
		return outerPosOutQty;
	}

	public void setOuterPosOutQty(Integer outerPosOutQty) {
		this.outerPosOutQty = outerPosOutQty;
	}

	public BigDecimal getAllOutAmount() {
		return allOutAmount;
	}

	public void setAllOutAmount(BigDecimal allOutAmount) {
		this.allOutAmount = allOutAmount;
	}

	public BigDecimal getAllOutCost() {
		return allOutCost;
	}

	public void setAllOutCost(BigDecimal allOutCost) {
		this.allOutCost = allOutCost;
	}

	public BigDecimal getSalesOutCost() {
		return salesOutCost;
	}

	public void setSalesOutCost(BigDecimal salesOutCost) {
		this.salesOutCost = salesOutCost;
	}

	public BigDecimal getSalesOutAmount() {
		return salesOutAmount;
	}

	public void setSalesOutAmount(BigDecimal salesOutAmount) {
		this.salesOutAmount = salesOutAmount;
	}

	public BigDecimal getOuterTransferOutCost() {
		return outerTransferOutCost;
	}

	public void setOuterTransferOutCost(BigDecimal outerTransferOutCost) {
		this.outerTransferOutCost = outerTransferOutCost;
	}

	public BigDecimal getOuterTransferOutAmount() {
		return outerTransferOutAmount;
	}

	public void setOuterTransferOutAmount(BigDecimal outerTransferOutAmount) {
		this.outerTransferOutAmount = outerTransferOutAmount;
	}

	public BigDecimal getOuterWholesaleOutCost() {
		return outerWholesaleOutCost;
	}

	public void setOuterWholesaleOutCost(BigDecimal outerWholesaleOutCost) {
		this.outerWholesaleOutCost = outerWholesaleOutCost;
	}

	public BigDecimal getOuterWholesaleOutAmount() {
		return outerWholesaleOutAmount;
	}

	public void setOuterWholesaleOutAmount(BigDecimal outerWholesaleOutAmount) {
		this.outerWholesaleOutAmount = outerWholesaleOutAmount;
	}

	public BigDecimal getOuterOtherOutCost() {
		return outerOtherOutCost;
	}

	public void setOuterOtherOutCost(BigDecimal outerOtherOutCost) {
		this.outerOtherOutCost = outerOtherOutCost;
	}

	public BigDecimal getOuterOtherOutAmount() {
		return outerOtherOutAmount;
	}

	public void setOuterOtherOutAmount(BigDecimal outerOtherOutAmount) {
		this.outerOtherOutAmount = outerOtherOutAmount;
	}

	public BigDecimal getOuterWithinOutCost() {
		return outerWithinOutCost;
	}

	public void setOuterWithinOutCost(BigDecimal outerWithinOutCost) {
		this.outerWithinOutCost = outerWithinOutCost;
	}

	public BigDecimal getOuterWithinOutAmount() {
		return outerWithinOutAmount;
	}

	public void setOuterWithinOutAmount(BigDecimal outerWithinOutAmount) {
		this.outerWithinOutAmount = outerWithinOutAmount;
	}

	public BigDecimal getOuterPosOutCost() {
		return outerPosOutCost;
	}

	public void setOuterPosOutCost(BigDecimal outerPosOutCost) {
		this.outerPosOutCost = outerPosOutCost;
	}

	public BigDecimal getOuterPosOutAmount() {
		return outerPosOutAmount;
	}

	public void setOuterPosOutAmount(BigDecimal outerPosOutAmount) {
		this.outerPosOutAmount = outerPosOutAmount;
	}

	public Integer getThisOweOffQty() {
		return thisOweOffQty;
	}

	public void setThisOweOffQty(Integer thisOweOffQty) {
		this.thisOweOffQty = thisOweOffQty;
	}

	public Integer getPreOweOffOutQty() {
		return preOweOffOutQty;
	}

	public void setPreOweOffOutQty(Integer preOweOffOutQty) {
		this.preOweOffOutQty = preOweOffOutQty;
	}

	public Integer getBeginningOweOffQty() {
		return beginningOweOffQty;
	}

	public void setBeginningOweOffQty(Integer beginningOweOffQty) {
		this.beginningOweOffQty = beginningOweOffQty;
	}

	public BigDecimal getThisOweOffCost() {
		return thisOweOffCost;
	}

	public void setThisOweOffCost(BigDecimal thisOweOffCost) {
		this.thisOweOffCost = thisOweOffCost;
	}

	public BigDecimal getPreOweOffOutCost() {
		return preOweOffOutCost;
	}

	public void setPreOweOffOutCost(BigDecimal preOweOffOutCost) {
		this.preOweOffOutCost = preOweOffOutCost;
	}

	public BigDecimal getBeginningOweOffCost() {
		return beginningOweOffCost;
	}

	public void setBeginningOweOffCost(BigDecimal beginningOweOffCost) {
		this.beginningOweOffCost = beginningOweOffCost;
	}

}
