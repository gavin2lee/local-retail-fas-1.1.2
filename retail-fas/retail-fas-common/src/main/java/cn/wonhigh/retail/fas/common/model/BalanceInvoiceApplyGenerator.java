package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-25 16:31:54
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class BalanceInvoiceApplyGenerator implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4967377549993963571L;
	
	/**
	 * 销售明细的流水号Id
	 */
	private String dtlId;

	/**
     * 单据编号(结算单号)
     */
    private String billNo;

    /**
     * 单据类型
     */
    private Integer billType;
    
    /**
     * 结算类型
     */
    private Integer balanceType;
    
    /**
     * 结算类型名称
     */
    private String balanceTypeName;
    
    /**
     * 结算月
     */
    private String balanceMonth;
    
    /**
     * 单据状态
     */
    private Integer status;
    
    /**
     * 单据状态名称
     */ 
    private String statusName;
    
	/**
     * 结算单买方编号
     */
    private String buyerNo;

    /**
     * 结算单买方名称
     */
    private String buyerName;

    /**
     * 结算单卖方编号
     */
    private String salerNo;

    /**
     * 结算单卖方名称
     */
    private String salerName;

    /**
     * 店铺编号
     */
    private String shopNo;

    /**
     * 店铺名称
     */
    private String shopName;
    
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
     * 管理城市编号
     */
    private String organNo;
    
    /**
     * 管理城市名称
     */
    private String organName;
    
    /**
     * 大类编码
     */
    private String categoryNo;

    /**
     * 大类名称
     */
    private String categoryName;
    
    /**
     * 单据日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    @JsonDeserialize(using = JsonDateDeserialize$10.class)  
    private Date balanceDate;
    
    /**
     * 结算开始时间
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    @JsonDeserialize(using = JsonDateDeserialize$10.class)  
    private Date balanceStartDate;

    /**
     * 结算结束日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    @JsonDeserialize(using = JsonDateDeserialize$10.class)  
    private Date balanceEndDate;
    
    /**
     * 数量
     */
    private Integer qty;
    
    /**
     * 单价
     */
    private BigDecimal cost;
    
    /**
     * 税率
     */
    private BigDecimal taxRate;
    
    /**
     * 税额
     */
    private BigDecimal taxAmount;

    /**
     * 不含税单价
     */
    private BigDecimal exclusiveCost;
    
    /**
     * 开票金额
     */
    private BigDecimal amount;
    
    /**
     * 其他扣项金额
     */
    private BigDecimal deductionAmount;
    
    /**
     * 成本价
     */
    private BigDecimal primeCost;
    
    /**
     * 地区价
     */
    private BigDecimal areaCost;
    
    /**
     * 总部价
     */
    private BigDecimal headquareCost;
   
    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)   
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)   
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date updateTime;
    
    /**
     * 牌价
     */
    private BigDecimal tagPrice;
    
    /**
     * 现价
     */
    private BigDecimal salePrice;
    
    /**
     * 活动名称
     */
    private String proName;
    
    /**
     * 扣率
     */
    private BigDecimal discount;
    
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
	}

	public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
	}

	public String getBalanceTypeName() {
		return balanceTypeName;
	}

	public void setBalanceTypeName(String balanceTypeName) {
		this.balanceTypeName = balanceTypeName;
	}

	public String getBalanceMonth() {
		return balanceMonth;
	}

	public void setBalanceMonth(String balanceMonth) {
		this.balanceMonth = balanceMonth;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
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

	public Date getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}

	public Date getBalanceStartDate() {
		return balanceStartDate;
	}

	public void setBalanceStartDate(Date balanceStartDate) {
		this.balanceStartDate = balanceStartDate;
	}

	public Date getBalanceEndDate() {
		return balanceEndDate;
	}

	public void setBalanceEndDate(Date balanceEndDate) {
		this.balanceEndDate = balanceEndDate;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getPrimeCost() {
		return primeCost;
	}

	public BigDecimal getAreaCost() {
		return areaCost;
	}

	public BigDecimal getHeadquareCost() {
		return headquareCost;
	}

	public void setPrimeCost(BigDecimal primeCost) {
		this.primeCost = primeCost;
	}

	public void setAreaCost(BigDecimal areaCost) {
		this.areaCost = areaCost;
	}

	public void setHeadquareCost(BigDecimal headquareCost) {
		this.headquareCost = headquareCost;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public BigDecimal getExclusiveCost() {
		return exclusiveCost;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public void setExclusiveCost(BigDecimal exclusiveCost) {
		this.exclusiveCost = exclusiveCost;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getOrganNo() {
		return organNo;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getDtlId() {
		return dtlId;
	}

	public void setDtlId(String dtlId) {
		this.dtlId = dtlId;
	}

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	
	
    
}