package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-05 10:01:20
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
public class BillShopSaleOrder extends FasBaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1203224105375504989L;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 销售类型,0-正常 1-换货 2-退货 默认为0
     */
    private Integer orderType;

    /**
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 经营区域编号
     */
    private String zoneNo;

    /**
     * 经营城市编号
     */
    private String bizCityNo;

    /**
     * 商业集团编码
     */
    private String bsgroupsNo;

    /**
     * 片区编码
     */
    private String regionNo;

    /**
     * 商场编码
     */
    private String mallNo;

    public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
     * 店铺编码
     */
    private String shopNo;
    
    private String shopName;

    /**
     * (销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)
     */
    private String retailType;

    /**
     * 销售日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date saleDate;
    public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date startDate;
	@JsonSerialize(using = JsonDateSerializer$10.class)
    private Date endDate;

    /**
     * 订单业务类型,0-正常销售 1-跨店销售 2-商场团购 3-公司团购 4-员购 9-其他 默认为0
     */
    private Integer businessType;

    /**
     * 订单状态,0-已创建 10-已挂起 11-已取消 20-已审核 30-已收银 40-待配货41-已发货 50-已提货 99-已完结
     */

    /**
     * 营业员工号,与HR工号代码一致
     */
    private String assistantNo;

    /**
     * 营业员姓名
     */
    private String assistantName;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 品牌编码
     */
    private String brandNo;

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
     * 商品数量
     */
    private Integer qty;

    public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}
	/**
     * 商品类型,0-正常 1-赠品 促销标识
     */
    private Integer itemFlag;

    /**
     * 商品牌价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tagPrice;

    /**
     * 商品现价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal salePrice;

    /**
     * 商品折扣价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal discPrice;

    /**
     * 商品结算价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal settlePrice;

    /**
     * 减价,单价减价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal reducePrice;

    /**
     * 商品总金额,(结算价-减价)*数量
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal amount;

    /**
     * 牌价额,牌价*数量
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tagAmount;

    /**
     * 现价额,现价*数量
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal saleAmount;

    /**
     * 结算额,结算价*数量
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal settleAmount;

    /**
     * 折扣额,商品折扣价*数量
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal discAmount;

    /**
     * 促销优惠单价,促销优惠单价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal prefPrice;

    /**
     * 促销活动编号
     */
    private String proNo;

    /**
     * 促销活动名称
     */
    private String proName;

    /**
     * 扣率,如0.17
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal discount;

    /**
     * 会员折数
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
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
     * 是否影响销售,0-正常 1-不可销售 默认为0
     */
    private Integer affectFlag;

    /**
     * 商品折数
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal itemDiscount;

    /**
     * 是否已结算(1-未结算，2-已结算)
     */
    private String isBalance;

    /**
     * 结算单号码
     */
    private String balanceNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 商场扣额(结算额*扣率)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal mallDeductAmount;

	/*//结算公司名称
	private String companyName;*/
	//单据日期
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date outDate;
	//商品编码
	private String itemCode;
	//商品名称
	private String itemName;
	/*//营业员
	private String assistant;*/
	//牌价额
	@JsonSerialize(using = BigDecimalSerializer$2.class)
	private BigDecimal tagPriceAmount;
	
	//现价额
	@JsonSerialize(using = BigDecimalSerializer$2.class)
		private BigDecimal salePriceAmount;
		//结算金额
		@JsonSerialize(using = BigDecimalSerializer$2.class)
	 	private BigDecimal reduceAmount;
		//现金券总金额
		@JsonSerialize(using = BigDecimalSerializer$2.class)
		private BigDecimal couponAmount;
		//订单总金额
		@JsonSerialize(using = BigDecimalSerializer$2.class)
		private BigDecimal allAmount;
		//财务确认标志
		private Integer monthlyFlag;
		//订单业务类型
		private Integer orderBillType;
		//发票号
		private String invoiceNo;
		//发票日期
		private Date invoiceDate;
		//原单号
		private String oldOrderNo;
		//发票金额
		@JsonSerialize(using = BigDecimalSerializer$2.class)
		private BigDecimal invoiceAmount;
	
		private String orderBillTypeName;

		
		private String  launchType;
		private String  rateCode;
		private String  activityDescribe;
		
		@JsonSerialize(using = BigDecimalSerializer$2.class)
		private BigDecimal regionCost;
		
		@JsonSerialize(using = BigDecimalSerializer$2.class)
		private BigDecimal unitCost;
		
		@JsonSerialize(using = JsonDateSerializer$10.class)
		private Date proStartDate;
		
		@JsonSerialize(using = JsonDateSerializer$10.class)
		private Date proEndDate;
		
	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
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

	public BigDecimal getTagPriceAmount() {
		return tagPriceAmount;
	}

	public void setTagPriceAmount(BigDecimal tagPriceAmount) {
		this.tagPriceAmount = tagPriceAmount;
	}

	public BigDecimal getSalePriceAmount() {
		return salePriceAmount;
	}

	public void setSalePriceAmount(BigDecimal salePriceAmount) {
		this.salePriceAmount = salePriceAmount;
	}

	public BigDecimal getReduceAmount() {
		return reduceAmount;
	}

	public void setReduceAmount(BigDecimal reduceAmount) {
		this.reduceAmount = reduceAmount;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public BigDecimal getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}

	public Integer getMonthlyFlag() {
		return monthlyFlag;
	}

	public void setMonthlyFlag(Integer monthlyFlag) {
		this.monthlyFlag = monthlyFlag;
	}

	public Integer getOrderBillType() {
		return orderBillType;
	}

	public void setOrderBillType(Integer orderBillType) {
		this.orderBillType = orderBillType;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getOldOrderNo() {
		return oldOrderNo;
	}

	public void setOldOrderNo(String oldOrderNo) {
		this.oldOrderNo = oldOrderNo;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	
    public String getOrderBillTypeName() {
		return orderBillTypeName;
	}

	public void setOrderBillTypeName(String orderBillTypeName) {
		this.orderBillTypeName = orderBillTypeName;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_shop_sale_order.id
//     */
//    public Integer getId() {
//        return id;
//    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of bill_shop_sale_order.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for bill_shop_sale_order.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #orderType}
     *
     * @return the value of bill_shop_sale_order.order_type
     */
    public Integer getOrderType() {
        return orderType;
    }

    /**
     * 
     * {@linkplain #orderType}
     * @param orderType the value for bill_shop_sale_order.order_type
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_shop_sale_order.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_shop_sale_order.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of bill_shop_sale_order.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for bill_shop_sale_order.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #bizCityNo}
     *
     * @return the value of bill_shop_sale_order.biz_city_no
     */
    public String getBizCityNo() {
        return bizCityNo;
    }

    /**
     * 
     * {@linkplain #bizCityNo}
     * @param bizCityNo the value for bill_shop_sale_order.biz_city_no
     */
    public void setBizCityNo(String bizCityNo) {
        this.bizCityNo = bizCityNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     *
     * @return the value of bill_shop_sale_order.bsgroups_no
     */
    public String getBsgroupsNo() {
        return bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     * @param bsgroupsNo the value for bill_shop_sale_order.bsgroups_no
     */
    public void setBsgroupsNo(String bsgroupsNo) {
        this.bsgroupsNo = bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #regionNo}
     *
     * @return the value of bill_shop_sale_order.region_no
     */
    public String getRegionNo() {
        return regionNo;
    }

    /**
     * 
     * {@linkplain #regionNo}
     * @param regionNo the value for bill_shop_sale_order.region_no
     */
    public void setRegionNo(String regionNo) {
        this.regionNo = regionNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of bill_shop_sale_order.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for bill_shop_sale_order.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_shop_sale_order.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_shop_sale_order.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #retailType}
     *
     * @return the value of bill_shop_sale_order.retail_type
     */
    public String getRetailType() {
        return retailType;
    }

    /**
     * 
     * {@linkplain #retailType}
     * @param retailType the value for bill_shop_sale_order.retail_type
     */
    public void setRetailType(String retailType) {
        this.retailType = retailType;
    }

    /**
     * 
     * {@linkplain #saleDate}
     *
     * @return the value of bill_shop_sale_order.sale_date
     */
    public Date getSaleDate() {
        return saleDate;
    }

    /**
     * 
     * {@linkplain #saleDate}
     * @param saleDate the value for bill_shop_sale_order.sale_date
     */
    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    /**
     * 
     * {@linkplain #businessType}
     *
     * @return the value of bill_shop_sale_order.business_type
     */
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     * 
     * {@linkplain #businessType}
     * @param businessType the value for bill_shop_sale_order.business_type
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of bill_shop_sale_order.status
     */
//    public Integer getStatus() {
//        return status;
//    }


    /**
     * 
     * {@linkplain #assistantNo}
     *
     * @return the value of bill_shop_sale_order.assistant_no
     */
    public String getAssistantNo() {
        return assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     * @param assistantNo the value for bill_shop_sale_order.assistant_no
     */
    public void setAssistantNo(String assistantNo) {
        this.assistantNo = assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantName}
     *
     * @return the value of bill_shop_sale_order.assistant_name
     */
    public String getAssistantName() {
        return assistantName;
    }

    /**
     * 
     * {@linkplain #assistantName}
     * @param assistantName the value for bill_shop_sale_order.assistant_name
     */
    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of bill_shop_sale_order.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for bill_shop_sale_order.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_shop_sale_order.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_shop_sale_order.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     *
     * @return the value of bill_shop_sale_order.sku_no
     */
    public String getSkuNo() {
        return skuNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     * @param skuNo the value for bill_shop_sale_order.sku_no
     */
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of bill_shop_sale_order.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for bill_shop_sale_order.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     *
     * @return the value of bill_shop_sale_order.size_no
     */
    public String getSizeNo() {
        return sizeNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     * @param sizeNo the value for bill_shop_sale_order.size_no
     */
    public void setSizeNo(String sizeNo) {
        this.sizeNo = sizeNo;
    }

    /**
     * 
     * {@linkplain #itemFlag}
     *
     * @return the value of bill_shop_sale_order.item_flag
     */
    public Integer getItemFlag() {
        return itemFlag;
    }

    /**
     * 
     * {@linkplain #itemFlag}
     * @param itemFlag the value for bill_shop_sale_order.item_flag
     */
    public void setItemFlag(Integer itemFlag) {
        this.itemFlag = itemFlag;
    }

    /**
     * 
     * {@linkplain #tagPrice}
     *
     * @return the value of bill_shop_sale_order.tag_price
     */
    public BigDecimal getTagPrice() {
        return tagPrice;
    }

    /**
     * 
     * {@linkplain #tagPrice}
     * @param tagPrice the value for bill_shop_sale_order.tag_price
     */
    public void setTagPrice(BigDecimal tagPrice) {
        this.tagPrice = tagPrice;
    }

    /**
     * 
     * {@linkplain #salePrice}
     *
     * @return the value of bill_shop_sale_order.sale_price
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * 
     * {@linkplain #salePrice}
     * @param salePrice the value for bill_shop_sale_order.sale_price
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * 
     * {@linkplain #discPrice}
     *
     * @return the value of bill_shop_sale_order.disc_price
     */
    public BigDecimal getDiscPrice() {
        return discPrice;
    }

    /**
     * 
     * {@linkplain #discPrice}
     * @param discPrice the value for bill_shop_sale_order.disc_price
     */
    public void setDiscPrice(BigDecimal discPrice) {
        this.discPrice = discPrice;
    }

    /**
     * 
     * {@linkplain #settlePrice}
     *
     * @return the value of bill_shop_sale_order.settle_price
     */
    public BigDecimal getSettlePrice() {
        return settlePrice;
    }

    /**
     * 
     * {@linkplain #settlePrice}
     * @param settlePrice the value for bill_shop_sale_order.settle_price
     */
    public void setSettlePrice(BigDecimal settlePrice) {
        this.settlePrice = settlePrice;
    }

    /**
     * 
     * {@linkplain #reducePrice}
     *
     * @return the value of bill_shop_sale_order.reduce_price
     */
    public BigDecimal getReducePrice() {
        return reducePrice;
    }

    /**
     * 
     * {@linkplain #reducePrice}
     * @param reducePrice the value for bill_shop_sale_order.reduce_price
     */
    public void setReducePrice(BigDecimal reducePrice) {
        this.reducePrice = reducePrice;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of bill_shop_sale_order.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for bill_shop_sale_order.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #tagAmount}
     *
     * @return the value of bill_shop_sale_order.tag_amount
     */
    public BigDecimal getTagAmount() {
        return tagAmount;
    }

    /**
     * 
     * {@linkplain #tagAmount}
     * @param tagAmount the value for bill_shop_sale_order.tag_amount
     */
    public void setTagAmount(BigDecimal tagAmount) {
        this.tagAmount = tagAmount;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     *
     * @return the value of bill_shop_sale_order.sale_amount
     */
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     * @param saleAmount the value for bill_shop_sale_order.sale_amount
     */
    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    /**
     * 
     * {@linkplain #settleAmount}
     *
     * @return the value of bill_shop_sale_order.settle_amount
     */
    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    /**
     * 
     * {@linkplain #settleAmount}
     * @param settleAmount the value for bill_shop_sale_order.settle_amount
     */
    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    /**
     * 
     * {@linkplain #discAmount}
     *
     * @return the value of bill_shop_sale_order.disc_amount
     */
    public BigDecimal getDiscAmount() {
        return discAmount;
    }

    /**
     * 
     * {@linkplain #discAmount}
     * @param discAmount the value for bill_shop_sale_order.disc_amount
     */
    public void setDiscAmount(BigDecimal discAmount) {
        this.discAmount = discAmount;
    }

    /**
     * 
     * {@linkplain #prefPrice}
     *
     * @return the value of bill_shop_sale_order.pref_price
     */
    public BigDecimal getPrefPrice() {
        return prefPrice;
    }

    /**
     * 
     * {@linkplain #prefPrice}
     * @param prefPrice the value for bill_shop_sale_order.pref_price
     */
    public void setPrefPrice(BigDecimal prefPrice) {
        this.prefPrice = prefPrice;
    }

    /**
     * 
     * {@linkplain #proNo}
     *
     * @return the value of bill_shop_sale_order.pro_no
     */
    public String getProNo() {
        return proNo;
    }

    /**
     * 
     * {@linkplain #proNo}
     * @param proNo the value for bill_shop_sale_order.pro_no
     */
    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    /**
     * 
     * {@linkplain #proName}
     *
     * @return the value of bill_shop_sale_order.pro_name
     */
    public String getProName() {
        return proName;
    }

    /**
     * 
     * {@linkplain #proName}
     * @param proName the value for bill_shop_sale_order.pro_name
     */
    public void setProName(String proName) {
        this.proName = proName;
    }

    /**
     * 
     * {@linkplain #discount}
     *
     * @return the value of bill_shop_sale_order.discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 
     * {@linkplain #discount}
     * @param discount the value for bill_shop_sale_order.discount
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 
     * {@linkplain #vipDiscount}
     *
     * @return the value of bill_shop_sale_order.vip_discount
     */
    public BigDecimal getVipDiscount() {
        return vipDiscount;
    }

    /**
     * 
     * {@linkplain #vipDiscount}
     * @param vipDiscount the value for bill_shop_sale_order.vip_discount
     */
    public void setVipDiscount(BigDecimal vipDiscount) {
        this.vipDiscount = vipDiscount;
    }

    /**
     * 
     * {@linkplain #baseScore}
     *
     * @return the value of bill_shop_sale_order.base_score
     */
    public Integer getBaseScore() {
        return baseScore;
    }

    /**
     * 
     * {@linkplain #baseScore}
     * @param baseScore the value for bill_shop_sale_order.base_score
     */
    public void setBaseScore(Integer baseScore) {
        this.baseScore = baseScore;
    }

    /**
     * 
     * {@linkplain #proScore}
     *
     * @return the value of bill_shop_sale_order.pro_score
     */
    public Integer getProScore() {
        return proScore;
    }

    /**
     * 
     * {@linkplain #proScore}
     * @param proScore the value for bill_shop_sale_order.pro_score
     */
    public void setProScore(Integer proScore) {
        this.proScore = proScore;
    }

    /**
     * 
     * {@linkplain #affectFlag}
     *
     * @return the value of bill_shop_sale_order.affect_flag
     */
    public Integer getAffectFlag() {
        return affectFlag;
    }

    /**
     * 
     * {@linkplain #affectFlag}
     * @param affectFlag the value for bill_shop_sale_order.affect_flag
     */
    public void setAffectFlag(Integer affectFlag) {
        this.affectFlag = affectFlag;
    }

    /**
     * 
     * {@linkplain #itemDiscount}
     *
     * @return the value of bill_shop_sale_order.item_discount
     */
    public BigDecimal getItemDiscount() {
        return itemDiscount;
    }

    /**
     * 
     * {@linkplain #itemDiscount}
     * @param itemDiscount the value for bill_shop_sale_order.item_discount
     */
    public void setItemDiscount(BigDecimal itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    /**
     * 
     * {@linkplain #isBalance}
     *
     * @return the value of bill_shop_sale_order.is_balance
     */
    public String getIsBalance() {
        return isBalance;
    }

    /**
     * 
     * {@linkplain #isBalance}
     * @param isBalance the value for bill_shop_sale_order.is_balance
     */
    public void setIsBalance(String isBalance) {
        this.isBalance = isBalance;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_shop_sale_order.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_shop_sale_order.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_shop_sale_order.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_shop_sale_order.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #mallDeductAmount}
     *
     * @return the value of bill_shop_sale_order.mall_deduct_amount
     */
    public BigDecimal getMallDeductAmount() {
        return mallDeductAmount;
    }

    /**
     * 
     * {@linkplain #mallDeductAmount}
     * @param mallDeductAmount the value for bill_shop_sale_order.mall_deduct_amount
     */
    public void setMallDeductAmount(BigDecimal mallDeductAmount) {
        this.mallDeductAmount = mallDeductAmount;
    }

	public String getLaunchType() {
		return launchType;
	}

	public void setLaunchType(String launchType) {
		this.launchType = launchType;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public String getActivityDescribe() {
		return activityDescribe;
	}

	public void setActivityDescribe(String activityDescribe) {
		this.activityDescribe = activityDescribe;
	}

	public Date getProStartDate() {
		return proStartDate;
	}

	public void setProStartDate(Date proStartDate) {
		this.proStartDate = proStartDate;
	}

	public Date getProEndDate() {
		return proEndDate;
	}

	public void setProEndDate(Date proEndDate) {
		this.proEndDate = proEndDate;
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
}