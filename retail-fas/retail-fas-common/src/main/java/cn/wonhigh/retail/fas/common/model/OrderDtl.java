package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.FinanceConfirmFlagEnums;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$4;

/**
 * 销售订单明细 
 * @author admin
 * @date  2014-09-23 15:21:34
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
public class OrderDtl extends FasBaseModel {
	
	private static final long serialVersionUID = -3478798308972884751L;

	/**
	 * 主键
	 */
	private String orderDtlId;
	
    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商品SKU
     */
    private String skuNo;

    /**
     * 商品内码
     */
    private String itemNo;

    /**
     * 商品尺码
     */
    private String sizeNo;

    /**
     * 商品编号
     */
    private String itemCode;

    /**
     * 商场名称
     */
    private String itemName;

    /**
     * 所属品牌
     */
    private String brandNo;

    /**
     * 商品类型,0-正常 1-赠品 促销标识
     */
    private Short itemFlag;

    /**
     * 商品牌价
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal tagPrice;
    
    /** 全国统一牌价 */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
	private BigDecimal tagPriceNation;

    /**
     * 商品现价
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal salePrice;

    /**
     * 商品折扣价
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal discPrice;

    /**
     * 商品结算价
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal settlePrice;

    /**
     * 减价,单价减价
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal reducePrice;

    /**
     * 商品数量
     */
    private Integer qty;

    /**
     * 商品总金额,(结算价-减价)*数量
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal amount;
    
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
	 * 单位成本
	 */
	@JsonSerialize(using = BigDecimalSerializer$4.class)
	private BigDecimal unitCost;
	
	/**
	 * 采购价
	 */
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal purchasePrice;
	
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
     * 促销优惠单价,促销优惠单价
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal prefPrice;

    /**
     * 最终促销活动编号
     */
    private String proNo;

    /**
     * 最终促销活动名称
     */
    private String proName;

    /**
     * 最终扣率
     */
    private BigDecimal discount;

    /**
     * 会员折数
     */
    private BigDecimal vipDiscount;

    /**
     * 基本积分
     */
    private Integer baseScore;

    /**
     * 整单分摊积分
     */
    private Integer proScore;

    /**
     * 商品折数
     */
    private BigDecimal itemDiscount;

    /**
     * 最终扣率来源方式 0-取合同扣率 1-促销活动扣率
     */
    private Short discountType;

    /**
     * 最终扣率来源编号
     */
    private String discountSourceId;

    /**
     * 颜色编码
     */
    private String colorNo;

    /**
     * 颜色名称
     */
    private String colorName;

    /**
     * 品牌名称
     */
    private String brandName;
    
    /** 财务确认标志(1 : 已确认 0 ： 未确认) */
	private Integer financeConfirmFlag;
	
	/** 发票号（可填多个） */
	private String invoiceNo;

	/** 发票金额 */
	private BigDecimal invoiceAmount;
	
	/**
	 * 确认状态
	 */
	private String financeConfirmFlagStr;

    public String getOrderDtlId() {
		return orderDtlId;
	}

	public void setOrderDtlId(String orderDtlId) {
		this.orderDtlId = orderDtlId;
	}

	/**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of order_dtl.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for order_dtl.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     *
     * @return the value of order_dtl.sku_no
     */
    public String getSkuNo() {
        return skuNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     * @param skuNo the value for order_dtl.sku_no
     */
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of order_dtl.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for order_dtl.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     *
     * @return the value of order_dtl.size_no
     */
    public String getSizeNo() {
        return sizeNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     * @param sizeNo the value for order_dtl.size_no
     */
    public void setSizeNo(String sizeNo) {
        this.sizeNo = sizeNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of order_dtl.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for order_dtl.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of order_dtl.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for order_dtl.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of order_dtl.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for order_dtl.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #itemFlag}
     *
     * @return the value of order_dtl.item_flag
     */
    public Short getItemFlag() {
        return itemFlag;
    }

    /**
     * 
     * {@linkplain #itemFlag}
     * @param itemFlag the value for order_dtl.item_flag
     */
    public void setItemFlag(Short itemFlag) {
        this.itemFlag = itemFlag;
    }

    /**
     * 
     * {@linkplain #tagPrice}
     *
     * @return the value of order_dtl.tag_price
     */
    public BigDecimal getTagPrice() {
        return tagPrice;
    }

    /**
     * 
     * {@linkplain #tagPrice}
     * @param tagPrice the value for order_dtl.tag_price
     */
    public void setTagPrice(BigDecimal tagPrice) {
        this.tagPrice = tagPrice;
    }

    /**
     * 
     * {@linkplain #salePrice}
     *
     * @return the value of order_dtl.sale_price
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * 
     * {@linkplain #salePrice}
     * @param salePrice the value for order_dtl.sale_price
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * 
     * {@linkplain #discPrice}
     *
     * @return the value of order_dtl.disc_price
     */
    public BigDecimal getDiscPrice() {
        return discPrice;
    }

    /**
     * 
     * {@linkplain #discPrice}
     * @param discPrice the value for order_dtl.disc_price
     */
    public void setDiscPrice(BigDecimal discPrice) {
        this.discPrice = discPrice;
    }

    /**
     * 
     * {@linkplain #settlePrice}
     *
     * @return the value of order_dtl.settle_price
     */
    public BigDecimal getSettlePrice() {
        return settlePrice;
    }

    /**
     * 
     * {@linkplain #settlePrice}
     * @param settlePrice the value for order_dtl.settle_price
     */
    public void setSettlePrice(BigDecimal settlePrice) {
        this.settlePrice = settlePrice;
    }

    /**
     * 
     * {@linkplain #reducePrice}
     *
     * @return the value of order_dtl.reduce_price
     */
    public BigDecimal getReducePrice() {
        return reducePrice;
    }

    /**
     * 
     * {@linkplain #reducePrice}
     * @param reducePrice the value for order_dtl.reduce_price
     */
    public void setReducePrice(BigDecimal reducePrice) {
        this.reducePrice = reducePrice;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of order_dtl.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for order_dtl.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of order_dtl.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for order_dtl.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #prefPrice}
     *
     * @return the value of order_dtl.pref_price
     */
    public BigDecimal getPrefPrice() {
        return prefPrice;
    }

    /**
     * 
     * {@linkplain #prefPrice}
     * @param prefPrice the value for order_dtl.pref_price
     */
    public void setPrefPrice(BigDecimal prefPrice) {
        this.prefPrice = prefPrice;
    }

    /**
     * 
     * {@linkplain #proNo}
     *
     * @return the value of order_dtl.pro_no
     */
    public String getProNo() {
        return proNo;
    }

    /**
     * 
     * {@linkplain #proNo}
     * @param proNo the value for order_dtl.pro_no
     */
    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    /**
     * 
     * {@linkplain #proName}
     *
     * @return the value of order_dtl.pro_name
     */
    public String getProName() {
        return proName;
    }

    /**
     * 
     * {@linkplain #proName}
     * @param proName the value for order_dtl.pro_name
     */
    public void setProName(String proName) {
        this.proName = proName;
    }

    /**
     * 
     * {@linkplain #discount}
     *
     * @return the value of order_dtl.discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 
     * {@linkplain #discount}
     * @param discount the value for order_dtl.discount
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 
     * {@linkplain #vipDiscount}
     *
     * @return the value of order_dtl.vip_discount
     */
    public BigDecimal getVipDiscount() {
        return vipDiscount;
    }

    /**
     * 
     * {@linkplain #vipDiscount}
     * @param vipDiscount the value for order_dtl.vip_discount
     */
    public void setVipDiscount(BigDecimal vipDiscount) {
        this.vipDiscount = vipDiscount;
    }

    /**
     * 
     * {@linkplain #baseScore}
     *
     * @return the value of order_dtl.base_score
     */
    public Integer getBaseScore() {
        return baseScore;
    }

    /**
     * 
     * {@linkplain #baseScore}
     * @param baseScore the value for order_dtl.base_score
     */
    public void setBaseScore(Integer baseScore) {
        this.baseScore = baseScore;
    }

    /**
     * 
     * {@linkplain #proScore}
     *
     * @return the value of order_dtl.pro_score
     */
    public Integer getProScore() {
        return proScore;
    }

    /**
     * 
     * {@linkplain #proScore}
     * @param proScore the value for order_dtl.pro_score
     */
    public void setProScore(Integer proScore) {
        this.proScore = proScore;
    }

    /**
     * 
     * {@linkplain #itemDiscount}
     *
     * @return the value of order_dtl.item_discount
     */
    public BigDecimal getItemDiscount() {
        return itemDiscount;
    }

    /**
     * 
     * {@linkplain #itemDiscount}
     * @param itemDiscount the value for order_dtl.item_discount
     */
    public void setItemDiscount(BigDecimal itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    /**
     * 
     * {@linkplain #discountType}
     *
     * @return the value of order_dtl.discount_type
     */
    public Short getDiscountType() {
        return discountType;
    }

    /**
     * 
     * {@linkplain #discountType}
     * @param discountType the value for order_dtl.discount_type
     */
    public void setDiscountType(Short discountType) {
        this.discountType = discountType;
    }

    /**
     * 
     * {@linkplain #discountSourceId}
     *
     * @return the value of order_dtl.discount_source_id
     */
    public String getDiscountSourceId() {
        return discountSourceId;
    }

    /**
     * 
     * {@linkplain #discountSourceId}
     * @param discountSourceId the value for order_dtl.discount_source_id
     */
    public void setDiscountSourceId(String discountSourceId) {
        this.discountSourceId = discountSourceId;
    }

    /**
     * 
     * {@linkplain #colorNo}
     *
     * @return the value of order_dtl.color_no
     */
    public String getColorNo() {
        return colorNo;
    }

    /**
     * 
     * {@linkplain #colorNo}
     * @param colorNo the value for order_dtl.color_no
     */
    public void setColorNo(String colorNo) {
        this.colorNo = colorNo;
    }

    /**
     * 
     * {@linkplain #colorName}
     *
     * @return the value of order_dtl.color_name
     */
    public String getColorName() {
        return colorName;
    }

    /**
     * 
     * {@linkplain #colorName}
     * @param colorName the value for order_dtl.color_name
     */
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of order_dtl.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for order_dtl.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

	public Integer getFinanceConfirmFlag() {
		return financeConfirmFlag;
	}

	public void setFinanceConfirmFlag(Integer financeConfirmFlag) {
		if(null != financeConfirmFlag){
			this.setFinanceConfirmFlagStr(FinanceConfirmFlagEnums.getTextByStatus(financeConfirmFlag));
		}
		
		this.financeConfirmFlag = financeConfirmFlag;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getFinanceConfirmFlagStr() {
		return financeConfirmFlagStr;
	}

	public void setFinanceConfirmFlagStr(String financeConfirmFlagStr) {
		this.financeConfirmFlagStr = financeConfirmFlagStr;
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

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public BigDecimal getTagPriceNation() {
		return tagPriceNation;
	}

	public void setTagPriceNation(BigDecimal tagPriceNation) {
		this.tagPriceNation = tagPriceNation;
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
}