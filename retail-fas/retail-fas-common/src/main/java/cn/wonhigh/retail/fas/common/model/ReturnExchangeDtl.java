package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:13:46
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
public class ReturnExchangeDtl implements Serializable {
	
	private static final long serialVersionUID = -5534343563638732349L;

	/**
     * 主键ID,uuid生成
     */
    private String id;

    /**
     * 订单编号
     */
    private String businessNo;

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
     * 商品名称
     */
    private String itemName;

    /**
     * 商品条码
     */
    private String barcode;

    /**
     * 所属品牌
     */
    private String brandNo;

    /**
     * 商品类型,0-正常 1-赠品 促销标识
     */
    private Boolean itemFlag;

    /**
     * 商品牌价
     */
    private BigDecimal tagPrice;
    
    /** 全国统一牌价 */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
	private BigDecimal tagPriceNation;

    /**
     * 商品现价
     */
    private BigDecimal salePrice;

    /**
     * 商品折扣价
     */
    private BigDecimal discPrice;

    /**
     * 商品结算价
     */
    private BigDecimal settlePrice;

    /**
     * 减价,单价减价
     */
    private BigDecimal reducePrice;

    /**
     * 外卡折让金额
     */
    private BigDecimal rebateAmount;

    /**
     * 商品数量
     */
    private Integer qty;

    /**
     * 商品总金额,(结算价-减价)*数量
     */
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
	@JsonSerialize(using = BigDecimalSerializer$2.class)
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
    private BigDecimal prefPrice;

    /**
     * 促销活动编号，多个以逗号隔开
     */
    private String proNo;

    /**
     * 促销活动名称，多个以逗号隔开
     */
    private String proName;

    /**
     * 最终扣率,如17.00代表17.00%
     */
    private BigDecimal discount;

    /**
     * 扣率代码，如A,B
     */
    private String discountCode;

    /**
     * 扣率名称
     */
    private String discountName;

    /**
     * 扣率类型 ( 1-合同正价扣 2-合同阶梯扣 3-促销扣率)
     */
    private Boolean discountType;

    /**
     * 最终扣率来源名称
     */
    private String discountTypeName;

    /**
     * 最终扣率来源编号
     */
    private String discountSourceId;

    /**
     * 商场结算码
     */
    private String billingCode;

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
     * 消费积分
     */
    private Integer costScore;

    /**
     * 可二次销售,0-可二次销售 1-不可二次销售
     */
    private Boolean availableFlag;

    /**
     * 商品折数
     */
    private BigDecimal itemDiscount;

    /**
     * 退换货原因
     */
    private String reason;

    /**
     * 原订单明细ID
     */
    private String orderDtlId;

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

    /**
     * 尺码种类
     */
    private String sizeKind;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 是否已经退仓,0-未退 1-已退 默认为0-未退
     */
    private Boolean returnFlag;

    /**
     * 店转货单号,只有跨店销售（本店自提）才有数据
     */
    private String billTransferNo;

    /**
     * 变价单单号
     */
    private String priceChangeBillNo;

    /**
     * 退换货数量，该条明细退换货数量(只有换货订单才有)
     */
    private Short returnExchangeNum;

    /**
     * 建档人姓名
     */
    private String createUser;

    /**
     * 建档时间
     */
    private Date createTime;

    /**
     * 最后修改人姓名
     */
    private String updateUser;

    /**
     * 最后修改时间
     */
    private Date updateTime;
    
    /**
     * 店铺编码
     */
	private String shopNo;

	/** 
	 * 店铺名称
	 */
	private String shopName;
	
	/**
     * 销售日期
     */
    private Date outDate;
    
    /**
	 * 结算公司编码
	 */
	private String companyNo;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of return_exchange_dtl.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for return_exchange_dtl.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #businessNo}
     *
     * @return the value of return_exchange_dtl.business_no
     */
    public String getBusinessNo() {
        return businessNo;
    }

    /**
     * 
     * {@linkplain #businessNo}
     * @param businessNo the value for return_exchange_dtl.business_no
     */
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     *
     * @return the value of return_exchange_dtl.sku_no
     */
    public String getSkuNo() {
        return skuNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     * @param skuNo the value for return_exchange_dtl.sku_no
     */
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of return_exchange_dtl.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for return_exchange_dtl.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     *
     * @return the value of return_exchange_dtl.size_no
     */
    public String getSizeNo() {
        return sizeNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     * @param sizeNo the value for return_exchange_dtl.size_no
     */
    public void setSizeNo(String sizeNo) {
        this.sizeNo = sizeNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of return_exchange_dtl.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for return_exchange_dtl.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of return_exchange_dtl.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for return_exchange_dtl.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #barcode}
     *
     * @return the value of return_exchange_dtl.barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * 
     * {@linkplain #barcode}
     * @param barcode the value for return_exchange_dtl.barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of return_exchange_dtl.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for return_exchange_dtl.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #itemFlag}
     *
     * @return the value of return_exchange_dtl.item_flag
     */
    public Boolean getItemFlag() {
        return itemFlag;
    }

    /**
     * 
     * {@linkplain #itemFlag}
     * @param itemFlag the value for return_exchange_dtl.item_flag
     */
    public void setItemFlag(Boolean itemFlag) {
        this.itemFlag = itemFlag;
    }

    /**
     * 
     * {@linkplain #tagPrice}
     *
     * @return the value of return_exchange_dtl.tag_price
     */
    public BigDecimal getTagPrice() {
        return tagPrice;
    }

    /**
     * 
     * {@linkplain #tagPrice}
     * @param tagPrice the value for return_exchange_dtl.tag_price
     */
    public void setTagPrice(BigDecimal tagPrice) {
        this.tagPrice = tagPrice;
    }

    /**
     * 
     * {@linkplain #salePrice}
     *
     * @return the value of return_exchange_dtl.sale_price
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * 
     * {@linkplain #salePrice}
     * @param salePrice the value for return_exchange_dtl.sale_price
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * 
     * {@linkplain #discPrice}
     *
     * @return the value of return_exchange_dtl.disc_price
     */
    public BigDecimal getDiscPrice() {
        return discPrice;
    }

    /**
     * 
     * {@linkplain #discPrice}
     * @param discPrice the value for return_exchange_dtl.disc_price
     */
    public void setDiscPrice(BigDecimal discPrice) {
        this.discPrice = discPrice;
    }

    /**
     * 
     * {@linkplain #settlePrice}
     *
     * @return the value of return_exchange_dtl.settle_price
     */
    public BigDecimal getSettlePrice() {
        return settlePrice;
    }

    /**
     * 
     * {@linkplain #settlePrice}
     * @param settlePrice the value for return_exchange_dtl.settle_price
     */
    public void setSettlePrice(BigDecimal settlePrice) {
        this.settlePrice = settlePrice;
    }

    /**
     * 
     * {@linkplain #reducePrice}
     *
     * @return the value of return_exchange_dtl.reduce_price
     */
    public BigDecimal getReducePrice() {
        return reducePrice;
    }

    /**
     * 
     * {@linkplain #reducePrice}
     * @param reducePrice the value for return_exchange_dtl.reduce_price
     */
    public void setReducePrice(BigDecimal reducePrice) {
        this.reducePrice = reducePrice;
    }

    /**
     * 
     * {@linkplain #rebateAmount}
     *
     * @return the value of return_exchange_dtl.rebate_amount
     */
    public BigDecimal getRebateAmount() {
        return rebateAmount;
    }

    /**
     * 
     * {@linkplain #rebateAmount}
     * @param rebateAmount the value for return_exchange_dtl.rebate_amount
     */
    public void setRebateAmount(BigDecimal rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of return_exchange_dtl.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for return_exchange_dtl.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of return_exchange_dtl.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for return_exchange_dtl.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #prefPrice}
     *
     * @return the value of return_exchange_dtl.pref_price
     */
    public BigDecimal getPrefPrice() {
        return prefPrice;
    }

    /**
     * 
     * {@linkplain #prefPrice}
     * @param prefPrice the value for return_exchange_dtl.pref_price
     */
    public void setPrefPrice(BigDecimal prefPrice) {
        this.prefPrice = prefPrice;
    }

    /**
     * 
     * {@linkplain #proNo}
     *
     * @return the value of return_exchange_dtl.pro_no
     */
    public String getProNo() {
        return proNo;
    }

    /**
     * 
     * {@linkplain #proNo}
     * @param proNo the value for return_exchange_dtl.pro_no
     */
    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    /**
     * 
     * {@linkplain #proName}
     *
     * @return the value of return_exchange_dtl.pro_name
     */
    public String getProName() {
        return proName;
    }

    /**
     * 
     * {@linkplain #proName}
     * @param proName the value for return_exchange_dtl.pro_name
     */
    public void setProName(String proName) {
        this.proName = proName;
    }

    /**
     * 
     * {@linkplain #discount}
     *
     * @return the value of return_exchange_dtl.discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 
     * {@linkplain #discount}
     * @param discount the value for return_exchange_dtl.discount
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 
     * {@linkplain #discountCode}
     *
     * @return the value of return_exchange_dtl.discount_code
     */
    public String getDiscountCode() {
        return discountCode;
    }

    /**
     * 
     * {@linkplain #discountCode}
     * @param discountCode the value for return_exchange_dtl.discount_code
     */
    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    /**
     * 
     * {@linkplain #discountName}
     *
     * @return the value of return_exchange_dtl.discount_name
     */
    public String getDiscountName() {
        return discountName;
    }

    /**
     * 
     * {@linkplain #discountName}
     * @param discountName the value for return_exchange_dtl.discount_name
     */
    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    /**
     * 
     * {@linkplain #discountType}
     *
     * @return the value of return_exchange_dtl.discount_type
     */
    public Boolean getDiscountType() {
        return discountType;
    }

    /**
     * 
     * {@linkplain #discountType}
     * @param discountType the value for return_exchange_dtl.discount_type
     */
    public void setDiscountType(Boolean discountType) {
        this.discountType = discountType;
    }

    /**
     * 
     * {@linkplain #discountTypeName}
     *
     * @return the value of return_exchange_dtl.discount_type_name
     */
    public String getDiscountTypeName() {
        return discountTypeName;
    }

    /**
     * 
     * {@linkplain #discountTypeName}
     * @param discountTypeName the value for return_exchange_dtl.discount_type_name
     */
    public void setDiscountTypeName(String discountTypeName) {
        this.discountTypeName = discountTypeName;
    }

    /**
     * 
     * {@linkplain #discountSourceId}
     *
     * @return the value of return_exchange_dtl.discount_source_id
     */
    public String getDiscountSourceId() {
        return discountSourceId;
    }

    /**
     * 
     * {@linkplain #discountSourceId}
     * @param discountSourceId the value for return_exchange_dtl.discount_source_id
     */
    public void setDiscountSourceId(String discountSourceId) {
        this.discountSourceId = discountSourceId;
    }

    /**
     * 
     * {@linkplain #billingCode}
     *
     * @return the value of return_exchange_dtl.billing_code
     */
    public String getBillingCode() {
        return billingCode;
    }

    /**
     * 
     * {@linkplain #billingCode}
     * @param billingCode the value for return_exchange_dtl.billing_code
     */
    public void setBillingCode(String billingCode) {
        this.billingCode = billingCode;
    }

    /**
     * 
     * {@linkplain #vipDiscount}
     *
     * @return the value of return_exchange_dtl.vip_discount
     */
    public BigDecimal getVipDiscount() {
        return vipDiscount;
    }

    /**
     * 
     * {@linkplain #vipDiscount}
     * @param vipDiscount the value for return_exchange_dtl.vip_discount
     */
    public void setVipDiscount(BigDecimal vipDiscount) {
        this.vipDiscount = vipDiscount;
    }

    /**
     * 
     * {@linkplain #baseScore}
     *
     * @return the value of return_exchange_dtl.base_score
     */
    public Integer getBaseScore() {
        return baseScore;
    }

    /**
     * 
     * {@linkplain #baseScore}
     * @param baseScore the value for return_exchange_dtl.base_score
     */
    public void setBaseScore(Integer baseScore) {
        this.baseScore = baseScore;
    }

    /**
     * 
     * {@linkplain #proScore}
     *
     * @return the value of return_exchange_dtl.pro_score
     */
    public Integer getProScore() {
        return proScore;
    }

    /**
     * 
     * {@linkplain #proScore}
     * @param proScore the value for return_exchange_dtl.pro_score
     */
    public void setProScore(Integer proScore) {
        this.proScore = proScore;
    }

    /**
     * 
     * {@linkplain #costScore}
     *
     * @return the value of return_exchange_dtl.cost_score
     */
    public Integer getCostScore() {
        return costScore;
    }

    /**
     * 
     * {@linkplain #costScore}
     * @param costScore the value for return_exchange_dtl.cost_score
     */
    public void setCostScore(Integer costScore) {
        this.costScore = costScore;
    }

    /**
     * 
     * {@linkplain #availableFlag}
     *
     * @return the value of return_exchange_dtl.available_flag
     */
    public Boolean getAvailableFlag() {
        return availableFlag;
    }

    /**
     * 
     * {@linkplain #availableFlag}
     * @param availableFlag the value for return_exchange_dtl.available_flag
     */
    public void setAvailableFlag(Boolean availableFlag) {
        this.availableFlag = availableFlag;
    }

    /**
     * 
     * {@linkplain #itemDiscount}
     *
     * @return the value of return_exchange_dtl.item_discount
     */
    public BigDecimal getItemDiscount() {
        return itemDiscount;
    }

    /**
     * 
     * {@linkplain #itemDiscount}
     * @param itemDiscount the value for return_exchange_dtl.item_discount
     */
    public void setItemDiscount(BigDecimal itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    /**
     * 
     * {@linkplain #reason}
     *
     * @return the value of return_exchange_dtl.reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * 
     * {@linkplain #reason}
     * @param reason the value for return_exchange_dtl.reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 
     * {@linkplain #orderDtlId}
     *
     * @return the value of return_exchange_dtl.order_dtl_id
     */
    public String getOrderDtlId() {
        return orderDtlId;
    }

    /**
     * 
     * {@linkplain #orderDtlId}
     * @param orderDtlId the value for return_exchange_dtl.order_dtl_id
     */
    public void setOrderDtlId(String orderDtlId) {
        this.orderDtlId = orderDtlId;
    }

    /**
     * 
     * {@linkplain #colorNo}
     *
     * @return the value of return_exchange_dtl.color_no
     */
    public String getColorNo() {
        return colorNo;
    }

    /**
     * 
     * {@linkplain #colorNo}
     * @param colorNo the value for return_exchange_dtl.color_no
     */
    public void setColorNo(String colorNo) {
        this.colorNo = colorNo;
    }

    /**
     * 
     * {@linkplain #colorName}
     *
     * @return the value of return_exchange_dtl.color_name
     */
    public String getColorName() {
        return colorName;
    }

    /**
     * 
     * {@linkplain #colorName}
     * @param colorName the value for return_exchange_dtl.color_name
     */
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of return_exchange_dtl.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for return_exchange_dtl.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #sizeKind}
     *
     * @return the value of return_exchange_dtl.size_kind
     */
    public String getSizeKind() {
        return sizeKind;
    }

    /**
     * 
     * {@linkplain #sizeKind}
     * @param sizeKind the value for return_exchange_dtl.size_kind
     */
    public void setSizeKind(String sizeKind) {
        this.sizeKind = sizeKind;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of return_exchange_dtl.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for return_exchange_dtl.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #returnFlag}
     *
     * @return the value of return_exchange_dtl.return_flag
     */
    public Boolean getReturnFlag() {
        return returnFlag;
    }

    /**
     * 
     * {@linkplain #returnFlag}
     * @param returnFlag the value for return_exchange_dtl.return_flag
     */
    public void setReturnFlag(Boolean returnFlag) {
        this.returnFlag = returnFlag;
    }

    /**
     * 
     * {@linkplain #billTransferNo}
     *
     * @return the value of return_exchange_dtl.bill_transfer_no
     */
    public String getBillTransferNo() {
        return billTransferNo;
    }

    /**
     * 
     * {@linkplain #billTransferNo}
     * @param billTransferNo the value for return_exchange_dtl.bill_transfer_no
     */
    public void setBillTransferNo(String billTransferNo) {
        this.billTransferNo = billTransferNo;
    }

    /**
     * 
     * {@linkplain #priceChangeBillNo}
     *
     * @return the value of return_exchange_dtl.price_change_bill_no
     */
    public String getPriceChangeBillNo() {
        return priceChangeBillNo;
    }

    /**
     * 
     * {@linkplain #priceChangeBillNo}
     * @param priceChangeBillNo the value for return_exchange_dtl.price_change_bill_no
     */
    public void setPriceChangeBillNo(String priceChangeBillNo) {
        this.priceChangeBillNo = priceChangeBillNo;
    }

    /**
     * 
     * {@linkplain #returnExchangeNum}
     *
     * @return the value of return_exchange_dtl.return_exchange_num
     */
    public Short getReturnExchangeNum() {
        return returnExchangeNum;
    }

    /**
     * 
     * {@linkplain #returnExchangeNum}
     * @param returnExchangeNum the value for return_exchange_dtl.return_exchange_num
     */
    public void setReturnExchangeNum(Short returnExchangeNum) {
        this.returnExchangeNum = returnExchangeNum;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of return_exchange_dtl.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for return_exchange_dtl.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of return_exchange_dtl.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for return_exchange_dtl.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of return_exchange_dtl.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for return_exchange_dtl.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of return_exchange_dtl.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for return_exchange_dtl.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
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