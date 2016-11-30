package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 单据对象，单据头和单据明细的组合
 * 
 * @author yang.y
 */
public class BillBalanceApiDto implements Serializable {

	private static final long serialVersionUID = -2001203329506874526L;

	/** 主键ID */
    private String id; 

    /** 单据编号 */
    private String billNo;

    /** 单据类型(1301-到货单,1333-调货出库单,1335-客残销售出库单) */
    private Integer billType;
    
    /** 到货单(订货、补货类型) */
    private Integer bizType;
    
    /**
     * 批发订单类型(0-订货,1-现货,2-补货)
     */
    private Integer wholesaleOrderType;

    /** 相关单号 */
    private String refBillNo;

    /** 相关单据类型 */
    private Integer refBillType;

    /** 原单据编号(接口可以不传递该参数) */
    private String originalBillNo;
    
    /** 单据状态 */
    private Integer status;

    /** 买方编号 */
    private String buyerNo;

    /** 买方名称 */
    private String buyerName;

    /** 卖方编号 */
    private String salerNo;

    /** 卖方名称 */
    private String salerName;

    /** 发出日期  */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    private Date sendDate;

    /** 接收日期 */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    private Date receiveDate;

    /** 发出地编号 */
    private String sendStoreNo;

    /** 发出地名称  */
    private String sendStoreName;

    /** 收货地编号 */
    private String receiveStoreNo;

    /** 收货地名称 */
    private String receiveStoreName;

    /** 供应商编号 */
    private String supplierNo;
    
    /** 供应商名称 */
    private String supplierName;

    /** 商品编号 */
    private String itemNo;

    /** 商品名称 */
    private String itemName;
    
    /** itemCode */
    private String itemCode;

    /** 品牌编号 */
    private String brandNo;

    /** 品牌名称 */
    private String brandName;

    /** 大类编号 */
    private String categoryNo;

    /** 大类名称  */
    private String categoryName;

    /** 含税单价(结算价格、默认原单据价格) */
    private BigDecimal cost;

    /** 单据价格(原单据价格) */
    private BigDecimal billCost;
    
    /** 分摊价格(初始为原单据价格) */
    private BigDecimal actualCost;

    /** 税率 */
    private BigDecimal taxRate;

    /** 不含税单价 */
    private BigDecimal exclusiveCost;

    /** 发出数量 */
    private Integer sendQty;

    /** 接受数量 */
    private Integer receiveQty;

    /** 订单号 */
    private String orderNo;
    
    /** 年份 */
	private String years;
	
	/** 季节 */
	private String season;
	
	/** 是否是拆单的标志 */
	private Integer isSplit;
	
	/** 结算类型 */
    private Integer balanceType;
    
    /** 结算路径编码(接口不用传数据，由fas查询出结算路径) */
    private String pathNo;
    
    /** 发出方订货单位编码 */
    private String orderUnitNoFrom;
    
    /** 发出方订货单位名称 */
    private String orderUnitNameFrom;
    
    /** 发出方管理城市编号 */
	private String organNoFrom;

	/** 发出方管理城市名称 */
	private String organNameFrom;
    
	/** 接受方订货单位编码 */
	private String orderUnitNo;
	
	/** 接受方订货单位名称 */
	private String orderUnitName;
	
	/** 接受方管理城市编号 */
	private String organNo;
	
	/** 接受方管理城市名称 */
	private String organName;
	
	/**
     * 建档人
     */
    private String createUser;

    /**
     * 建档时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date createTime;
	
    /**
     * 分库字段
     */
    private String shardingFlag;
    
    /**
     * 是否期货
     */
    private Integer isfutures;// 1 现货
    
    /**
     * 结算号
     */
    private String invoiceNo;
    
    /**
     * 索赔编号
     */
    private String claimNo;
    
    /**
     * 退货编号
     */
    private String returnNo;
    
    /**
	 * 返利金额
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal billRebateAmount;
	
	/**
	 * 返利后折扣
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal billRebateDiscount;
	
	/**
	 * 出库返利
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal rebatePrice;
	
	/**
	 * 其它费用
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal otherPrice;
	
	/** 牌价 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal tagPrice;
	
	/**
	 * 备注
	 */
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	/**
	 * 采购价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal purchasePrice;
    
	public BigDecimal getRebatePrice() {
		return rebatePrice;
	}

	public void setRebatePrice(BigDecimal rebatePrice) {
		this.rebatePrice = rebatePrice;
	}

	public BigDecimal getBillRebateAmount() {
		return billRebateAmount;
	}

	public void setBillRebateAmount(BigDecimal billRebateAmount) {
		this.billRebateAmount = billRebateAmount;
	}

	public BigDecimal getBillRebateDiscount() {
		return billRebateDiscount;
	}

	public void setBillRebateDiscount(BigDecimal billRebateDiscount) {
		this.billRebateDiscount = billRebateDiscount;
	}

	public Integer getIsfutures() {
		return isfutures;
	}

	public void setIsfutures(Integer isfutures) {
		this.isfutures = isfutures;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
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

	public String getPathNo() {
		return pathNo;
	}

	public void setPathNo(String pathNo) {
		this.pathNo = pathNo;
	}

	public String getOriginalBillNo() {
		return originalBillNo;
	}

	public void setOriginalBillNo(String originalBillNo) {
		this.originalBillNo = originalBillNo;
	}

	public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public Integer getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(Integer isSplit) {
		this.isSplit = isSplit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getRefBillNo() {
		return refBillNo;
	}

	public void setRefBillNo(String refBillNo) {
		this.refBillNo = refBillNo;
	}

	public Integer getRefBillType() {
		return refBillType;
	}

	public void setRefBillType(Integer refBillType) {
		this.refBillType = refBillType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getExclusiveCost() {
		return exclusiveCost;
	}

	public void setExclusiveCost(BigDecimal exclusiveCost) {
		this.exclusiveCost = exclusiveCost;
	}

	public Integer getSendQty() {
		if(this.sendQty == null) {
			return 0;
		}
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		if(sendQty != null) {
			this.sendQty = sendQty;
		} else {
			this.sendQty = 0;
		}
	}

	public Integer getReceiveQty() {
		if(this.receiveQty == null) {
			return 0;
		}
		return receiveQty;
	}

	public void setReceiveQty(Integer receiveQty) {
		if(receiveQty != null) {
			this.receiveQty = receiveQty;
		} else {
			this.receiveQty = 0;
		}
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public BigDecimal getActualCost() {
		return actualCost;
	}

	public void setActualCost(BigDecimal actualCost) {
		this.actualCost = actualCost;
	}
	
	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

	
	
	public BigDecimal getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(BigDecimal otherPrice) {
		this.otherPrice = otherPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyerNo == null) ? 0 : buyerNo.hashCode());
		result = prime * result
				+ ((refBillNo == null) ? 0 : refBillNo.hashCode());
		result = prime * result + ((salerNo == null) ? 0 : salerNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillBalanceApiDto other = (BillBalanceApiDto) obj;
		if (buyerNo == null) {
			if (other.buyerNo != null)
				return false;
		} else if (!buyerNo.equals(other.buyerNo))
			return false;
		if (refBillNo == null) {
			if (other.refBillNo != null)
				return false;
		} else if (!refBillNo.equals(other.refBillNo))
			return false;
		if (salerNo == null) {
			if (other.salerNo != null)
				return false;
		} else if (!salerNo.equals(other.salerNo))
			return false;
		return true;
	}

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public Integer getWholesaleOrderType() {
		return wholesaleOrderType;
	}

	public void setWholesaleOrderType(Integer wholesaleOrderType) {
		this.wholesaleOrderType = wholesaleOrderType;
	}

}
