package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2014-12-15 14:22:41
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
@ExportFormat(className=AbstractExportFormat.class)
public class BillShopBalanceProSum implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键ID,uuid生成
     */
    private String id;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 结算单据编号
     */
    private String balanceNo;

    /**
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 商场编码
     */
    private String mallNo;

    /**
     * 商场名称
     */
    private String mallName;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺简称
     */
    private String shortName;

    /**
     * 店铺全称
     */
    private String fullName;

    /**
     * 月份
     */
    private String month;

    /**
     * 结算起始日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceStartDate;

    /**
     * 结算终止日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceEndDate;

    /**
     * 活动起始日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date proStartDate;

    /**
     * 活动终止日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date proEndDate;

    /**
     * 业务编号,订单号或退换货号
     */
    private String orderNo;

    /**
     * 促销活动编号
     */
    private String proNo;

    /**
     * 促销活动名称
     */
    private String proName;

    private String discountCode;
    
    /**
     * 销售金额收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal saleAmount;

    /**
     * 扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal deductAmount;

    /**
     * 开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal billingAmount;
    
    /**
     * 开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal systemBillingAmount;

    /**
     * 费用扣取方式(1-票前,2-票后)
     */
    private Byte costDeductType;

    /**
     * 费用交款方式(1-帐扣,2-现金)
     */
    private Byte costPayType;

    /**
     * 扣率名称
     */
    private String discountName;

    /**
     * 扣率,如17.00代表17.00%
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal discount;
    
    /**
     * 分库字段
     */
    private String shardingFlag;
    
    public String getShardingFlag() {
		return shardingFlag;
	}
	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}
	public String getDiscountN() {
    	BigDecimal discount = getDiscount();
    	if(discount != null) {
    		BigDecimal discount0 = discount.setScale(0,RoundingMode.FLOOR);
        	BigDecimal discount4 = discount.setScale(4, RoundingMode.HALF_UP);
        	
        	if(discount0.compareTo(discount4)==0){
    			discountN=discount0.toString()+"%";	
        	}else{
        		DecimalFormat df = new DecimalFormat("0.000#");
    			discountN=df.format(discount)+"%";
        	}
    	}
		return discountN;
	}
	public void setDiscountN(String discountN) {
		this.discountN = discountN;
	}

	private String  discountN;

    /**
     * 扣率来源方式,0-取合同扣率 1-促销活动扣率
     */
    private Boolean discountType;

    /**
     * 扣率来源编号
     */
    private String discountSourceId;
    
    /**
     * 品牌编码
     */
    private String brandNo;
    
    /**
     * 品牌中文名称
     */
    private String brandName;
    
    /**
     * 结算码
     */
    private String billingCode;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_shop_balance_pro_sum.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_shop_balance_pro_sum.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_shop_balance_pro_sum.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_shop_balance_pro_sum.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_shop_balance_pro_sum.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_shop_balance_pro_sum.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_shop_balance_pro_sum.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_shop_balance_pro_sum.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_shop_balance_pro_sum.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_shop_balance_pro_sum.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of bill_shop_balance_pro_sum.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for bill_shop_balance_pro_sum.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of bill_shop_balance_pro_sum.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for bill_shop_balance_pro_sum.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_shop_balance_pro_sum.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_shop_balance_pro_sum.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_shop_balance_pro_sum.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_shop_balance_pro_sum.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of bill_shop_balance_pro_sum.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for bill_shop_balance_pro_sum.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of bill_shop_balance_pro_sum.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for bill_shop_balance_pro_sum.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     *
     * @return the value of bill_shop_balance_pro_sum.balance_start_date
     */
    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     * @param balanceStartDate the value for bill_shop_balance_pro_sum.balance_start_date
     */
    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     *
     * @return the value of bill_shop_balance_pro_sum.balance_end_date
     */
    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     * @param balanceEndDate the value for bill_shop_balance_pro_sum.balance_end_date
     */
    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * 
     * {@linkplain #proStartDate}
     *
     * @return the value of bill_shop_balance_pro_sum.pro_start_date
     */
    public Date getProStartDate() {
        return proStartDate;
    }

    /**
     * 
     * {@linkplain #proStartDate}
     * @param proStartDate the value for bill_shop_balance_pro_sum.pro_start_date
     */
    public void setProStartDate(Date proStartDate) {
        this.proStartDate = proStartDate;
    }

    /**
     * 
     * {@linkplain #proEndDate}
     *
     * @return the value of bill_shop_balance_pro_sum.pro_end_date
     */
    public Date getProEndDate() {
        return proEndDate;
    }

    /**
     * 
     * {@linkplain #proEndDate}
     * @param proEndDate the value for bill_shop_balance_pro_sum.pro_end_date
     */
    public void setProEndDate(Date proEndDate) {
        this.proEndDate = proEndDate;
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of bill_shop_balance_pro_sum.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for bill_shop_balance_pro_sum.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #proNo}
     *
     * @return the value of bill_shop_balance_pro_sum.pro_no
     */
    public String getProNo() {
        return proNo;
    }

    /**
     * 
     * {@linkplain #proNo}
     * @param proNo the value for bill_shop_balance_pro_sum.pro_no
     */
    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    /**
     * 
     * {@linkplain #proName}
     *
     * @return the value of bill_shop_balance_pro_sum.pro_name
     */
    public String getProName() {
        return proName;
    }

    /**
     * 
     * {@linkplain #proName}
     * @param proName the value for bill_shop_balance_pro_sum.pro_name
     */
    public void setProName(String proName) {
        this.proName = proName;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     *
     * @return the value of bill_shop_balance_pro_sum.sale_amount
     */
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     * @param saleAmount the value for bill_shop_balance_pro_sum.sale_amount
     */
    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    /**
     * 
     * {@linkplain #deductAmount}
     *
     * @return the value of bill_shop_balance_pro_sum.deduct_amount
     */
    public BigDecimal getDeductAmount() {
        return deductAmount;
    }

    /**
     * 
     * {@linkplain #deductAmount}
     * @param deductAmount the value for bill_shop_balance_pro_sum.deduct_amount
     */
    public void setDeductAmount(BigDecimal deductAmount) {
        this.deductAmount = deductAmount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     *
     * @return the value of bill_shop_balance_pro_sum.billing_amount
     */
    public BigDecimal getBillingAmount() {
        return billingAmount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     * @param billingAmount the value for bill_shop_balance_pro_sum.billing_amount
     */
    public void setBillingAmount(BigDecimal billingAmount) {
        this.billingAmount = billingAmount;
    }

    /**
     * 
     * {@linkplain #costDeductType}
     *
     * @return the value of bill_shop_balance_pro_sum.cost_deduct_type
     */
    public Byte getCostDeductType() {
        return costDeductType;
    }

    /**
     * 
     * {@linkplain #costDeductType}
     * @param costDeductType the value for bill_shop_balance_pro_sum.cost_deduct_type
     */
    public void setCostDeductType(Byte costDeductType) {
        this.costDeductType = costDeductType;
    }

    /**
     * 
     * {@linkplain #costPayType}
     *
     * @return the value of bill_shop_balance_pro_sum.cost_pay_type
     */
    public Byte getCostPayType() {
        return costPayType;
    }

    /**
     * 
     * {@linkplain #costPayType}
     * @param costPayType the value for bill_shop_balance_pro_sum.cost_pay_type
     */
    public void setCostPayType(Byte costPayType) {
        this.costPayType = costPayType;
    }

    /**
     * 
     * {@linkplain #discountName}
     *
     * @return the value of bill_shop_balance_pro_sum.discount_name
     */
    public String getDiscountName() {
        return discountName;
    }

    /**
     * 
     * {@linkplain #discountName}
     * @param discountName the value for bill_shop_balance_pro_sum.discount_name
     */
    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    /**
     * 
     * {@linkplain #discount}
     *
     * @return the value of bill_shop_balance_pro_sum.discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 
     * {@linkplain #discount}
     * @param discount the value for bill_shop_balance_pro_sum.discount
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 
     * {@linkplain #discountType}
     *
     * @return the value of bill_shop_balance_pro_sum.discount_type
     */
    public Boolean getDiscountType() {
        return discountType;
    }

    /**
     * 
     * {@linkplain #discountType}
     * @param discountType the value for bill_shop_balance_pro_sum.discount_type
     */
    public void setDiscountType(Boolean discountType) {
        this.discountType = discountType;
    }

    /**
     * 
     * {@linkplain #discountSourceId}
     *
     * @return the value of bill_shop_balance_pro_sum.discount_source_id
     */
    public String getDiscountSourceId() {
        return discountSourceId;
    }

    /**
     * 
     * {@linkplain #discountSourceId}
     * @param discountSourceId the value for bill_shop_balance_pro_sum.discount_source_id
     */
    public void setDiscountSourceId(String discountSourceId) {
        this.discountSourceId = discountSourceId;
    }
	public String getDiscountCode() {
		return discountCode;
	}
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
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
	public String getBillingCode() {
		return billingCode;
	}
	public void setBillingCode(String billingCode) {
		this.billingCode = billingCode;
	}
	public BigDecimal getSystemBillingAmount() {
		return systemBillingAmount;
	}
	public void setSystemBillingAmount(BigDecimal systemBillingAmount) {
		this.systemBillingAmount = systemBillingAmount;
	}
}