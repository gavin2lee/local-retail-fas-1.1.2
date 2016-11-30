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


public class PrintBalanceDto{
	
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
	 * 订货单位编码
	 */
	private String orderUnitNo;

	/** 
	 * 订货单位名称
	 */
	private String orderUnitName;

	/** 
	 * 管理城市编号
	 */
	private String organNo;

	/** 
	 * 管理城市名称
	 */
	private String organName;
	
	private String groupNo;
	
	private String groupName;
	
	private String gender;
	
	private String genderName;
	
	private String salerNo;
	
	private String salerName;
	
	private String brandUnitNo;
	
	private String brandUnitName;
	
	private String categoryNo;
	
	private String categoryName;
	
	private String title;
	
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal returnAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal customReturnAmount;
	
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal materialPrice;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal purchasePrice;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal factoryPrice;
	
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal sendAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal deductionAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal balanceAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal cost;
    
	private Integer returnQty;
	
	private Integer customReturnQty;

	private Integer sendQty;

	private Integer balanceQty;
	
	private Integer iCount;
	
	public Integer getiCount() {
		return iCount;
	}

	public void setiCount(Integer iCount) {
		this.iCount = iCount;
	}

	public Integer getBalanceQty() {
		if(null == balanceQty){
			return new Integer(0);
		}
		return balanceQty;
	}

	public void setBalanceQty(Integer balanceQty) {
		this.balanceQty = balanceQty;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getCost() {
		if(null == cost){
			return new BigDecimal(0);
		}
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
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

	public BigDecimal getReturnAmount() {
		if(null == returnAmount){
			return new BigDecimal(0);
		}
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public BigDecimal getCustomReturnAmount() {
		if(null == customReturnAmount){
			return new BigDecimal(0);
		}
		return customReturnAmount;
	}

	public void setCustomReturnAmount(BigDecimal customReturnAmount) {
		this.customReturnAmount = customReturnAmount;
	}

	public BigDecimal getDeductionAmount() {
		if(null == deductionAmount){
			return new BigDecimal(0);
		}
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public BigDecimal getBalanceAmount() {
		if(null == balanceAmount){
			return new BigDecimal(0);
		}
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Integer getReturnQty() {
		if(null == returnQty){
			return new Integer(0);
		}
		return returnQty;
	}

	public void setReturnQty(Integer returnQty) {
		this.returnQty = returnQty;
	}

	public Integer getCustomReturnQty() {
		if(null == customReturnQty){
			return new Integer(0);
		}
		return customReturnQty;
	}

	public void setCustomReturnQty(Integer customReturnQty) {
		this.customReturnQty = customReturnQty;
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

	public BigDecimal getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(BigDecimal materialPrice) {
		this.materialPrice = materialPrice;
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

	public Integer getSendQty() {
		if(null == sendQty){
			return new Integer(0);
		}
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}

	public BigDecimal getSendAmount() {
		if(null == sendAmount){
			return new BigDecimal(0);
		}
		return sendAmount;
	}

	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount = sendAmount;
	}
	
	
}
