package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.BillShopBalanceDeductExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-11-06 14:39:19
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
public class BillShopBalanceCateSum  extends FasBaseModel{
  
	private static final long serialVersionUID = -7545514404941138179L;

	
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
    private Date balanceStartDate;

    /**
     * 结算终止日期
     */
    private Date balanceEndDate;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌中文名称
     */
    private String brandName;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 商品数量
     */
    private Integer saleQty;

    /**
     * 销售金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal saleAmount;

    /**
     * 开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal billingAmount;

    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal sumAmount;
    /**
     * 预收款金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal prepaymentAmount;

    /**
     * 已使用金额(冲销)
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal usedPrepaymentAmount;

    /**
     * 系统扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal systemDeductAmount;

    /**
     * 票前费用
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceDeductAmount;

    /**
     * 结算差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceDiffAmount;

    /**
     * 扣费总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal deductAmount;

    /**
     * 扣费差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffAmount;

    /**
     * 系统差异额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal sysDeductAmount;

    /**
     * 调整金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal changeAmount;

    /**
     * 大区年月
     */
    private String zoneYyyymm;

    /**
     * 分库字段(本部加大区)
     */
    private String shardingFlag;
    
    
    private String category1;
    private String categoryName1;
    private String category2;
    private String categoryName2;
    private String category3;
    private String categoryName3;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal actualBillingAmount;
    /**
     * 预收款金额--扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal prepaymentDeductAmount;

    /**
     * 已使用金额(冲销)-扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal usedPrepaymentDeductAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal  usedDeductAmount;
    
    /**
     * 公司编码(开票方)-卖方
     */
    private String salerNo;

    /**
     * 卖方名称
     */
    private String salerName;

    /**
     * 客户编码(收票方)-买方
     */
    private String buyerNo;

    /**
     * 买方名称
     */
    private String buyerName;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal  mallNumberAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal  salesDiffamount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal  expenseOperateAmount;
    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_shop_balance_cate_sum.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_shop_balance_cate_sum.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_shop_balance_cate_sum.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_shop_balance_cate_sum.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_shop_balance_cate_sum.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_shop_balance_cate_sum.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_shop_balance_cate_sum.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_shop_balance_cate_sum.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_shop_balance_cate_sum.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_shop_balance_cate_sum.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of bill_shop_balance_cate_sum.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for bill_shop_balance_cate_sum.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of bill_shop_balance_cate_sum.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for bill_shop_balance_cate_sum.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_shop_balance_cate_sum.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_shop_balance_cate_sum.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_shop_balance_cate_sum.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_shop_balance_cate_sum.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of bill_shop_balance_cate_sum.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for bill_shop_balance_cate_sum.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of bill_shop_balance_cate_sum.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for bill_shop_balance_cate_sum.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     *
     * @return the value of bill_shop_balance_cate_sum.balance_start_date
     */
    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     * @param balanceStartDate the value for bill_shop_balance_cate_sum.balance_start_date
     */
    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     *
     * @return the value of bill_shop_balance_cate_sum.balance_end_date
     */
    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     * @param balanceEndDate the value for bill_shop_balance_cate_sum.balance_end_date
     */
    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_shop_balance_cate_sum.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_shop_balance_cate_sum.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_shop_balance_cate_sum.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_shop_balance_cate_sum.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of bill_shop_balance_cate_sum.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for bill_shop_balance_cate_sum.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of bill_shop_balance_cate_sum.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for bill_shop_balance_cate_sum.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #saleQty}
     *
     * @return the value of bill_shop_balance_cate_sum.sale_qty
     */
    public Integer getSaleQty() {
        return saleQty;
    }

    /**
     * 
     * {@linkplain #saleQty}
     * @param saleQty the value for bill_shop_balance_cate_sum.sale_qty
     */
    public void setSaleQty(Integer saleQty) {
        this.saleQty = saleQty;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     *
     * @return the value of bill_shop_balance_cate_sum.sale_amount
     */
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     * @param saleAmount the value for bill_shop_balance_cate_sum.sale_amount
     */
    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     *
     * @return the value of bill_shop_balance_cate_sum.billing_amount
     */
    public BigDecimal getBillingAmount() {
        return billingAmount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     * @param billingAmount the value for bill_shop_balance_cate_sum.billing_amount
     */
    public void setBillingAmount(BigDecimal billingAmount) {
        this.billingAmount = billingAmount;
    }

    /**
     * 
     * {@linkplain #prepaymentAmount}
     *
     * @return the value of bill_shop_balance_cate_sum.prepayment_amount
     */
    public BigDecimal getPrepaymentAmount() {
        return prepaymentAmount;
    }

    /**
     * 
     * {@linkplain #prepaymentAmount}
     * @param prepaymentAmount the value for bill_shop_balance_cate_sum.prepayment_amount
     */
    public void setPrepaymentAmount(BigDecimal prepaymentAmount) {
      this.prepaymentAmount =prepaymentAmount;
    }
    /**
     * 
     * {@linkplain #usedPrepaymentAmount}
     *
     * @return the value of bill_shop_balance_cate_sum.used_prepayment_amount
     */
    public BigDecimal getUsedPrepaymentAmount() {
        return usedPrepaymentAmount;
    }

    /**
     * 
     * {@linkplain #usedPrepaymentAmount}
     * @param usedPrepaymentAmount the value for bill_shop_balance_cate_sum.used_prepayment_amount
     */
    public void setUsedPrepaymentAmount(BigDecimal usedPrepaymentAmount) {
        this.usedPrepaymentAmount = usedPrepaymentAmount;
    }

    /**
     * 
     * {@linkplain #systemDeductAmount}
     *
     * @return the value of bill_shop_balance_cate_sum.system_deduct_amount
     */
    public BigDecimal getSystemDeductAmount() {
        return systemDeductAmount;
    }

    /**
     * 
     * {@linkplain #systemDeductAmount}
     * @param systemDeductAmount the value for bill_shop_balance_cate_sum.system_deduct_amount
     */
    public void setSystemDeductAmount(BigDecimal systemDeductAmount) {
        this.systemDeductAmount = systemDeductAmount;
    }

    /**
     * 
     * {@linkplain #balanceDeductAmount}
     *
     * @return the value of bill_shop_balance_cate_sum.balance_deduct_amount
     */
    public BigDecimal getBalanceDeductAmount() {
        return balanceDeductAmount;
    }

    /**
     * 
     * {@linkplain #balanceDeductAmount}
     * @param balanceDeductAmount the value for bill_shop_balance_cate_sum.balance_deduct_amount
     */
    public void setBalanceDeductAmount(BigDecimal balanceDeductAmount) {
        this.balanceDeductAmount = balanceDeductAmount;
    }

    /**
     * 
     * {@linkplain #balanceDiffAmount}
     *
     * @return the value of bill_shop_balance_cate_sum.balance_diff_amount
     */
    public BigDecimal getBalanceDiffAmount() {
        return balanceDiffAmount;
    }

    /**
     * 
     * {@linkplain #balanceDiffAmount}
     * @param balanceDiffAmount the value for bill_shop_balance_cate_sum.balance_diff_amount
     */
    public void setBalanceDiffAmount(BigDecimal balanceDiffAmount) {
        this.balanceDiffAmount = balanceDiffAmount;
    }

    /**
     * 
     * {@linkplain #deductAmount}
     *
     * @return the value of bill_shop_balance_cate_sum.deduct_amount
     */
    public BigDecimal getDeductAmount() {
        return deductAmount;
    }

    /**
     * 
     * {@linkplain #deductAmount}
     * @param deductAmount the value for bill_shop_balance_cate_sum.deduct_amount
     */
    public void setDeductAmount(BigDecimal deductAmount) {
        this.deductAmount = deductAmount;
    }

    /**
     * 
     * {@linkplain #diffAmount}
     *
     * @return the value of bill_shop_balance_cate_sum.diff_amount
     */
    public BigDecimal getDiffAmount() {
        return diffAmount;
    }

    /**
     * 
     * {@linkplain #diffAmount}
     * @param diffAmount the value for bill_shop_balance_cate_sum.diff_amount
     */
    public void setDiffAmount(BigDecimal diffAmount) {
        this.diffAmount = diffAmount;
    }

    /**
     * 
     * {@linkplain #sysDeductAmount}
     *
     * @return the value of bill_shop_balance_cate_sum.sys_deduct_amount
     */
    public BigDecimal getSysDeductAmount() {
        return sysDeductAmount;
    }

    /**
     * 
     * {@linkplain #sysDeductAmount}
     * @param sysDeductAmount the value for bill_shop_balance_cate_sum.sys_deduct_amount
     */
    public void setSysDeductAmount(BigDecimal sysDeductAmount) {
        this.sysDeductAmount = sysDeductAmount;
    }

    /**
     * 
     * {@linkplain #changeAmount}
     *
     * @return the value of bill_shop_balance_cate_sum.change_amount
     */
    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    /**
     * 
     * {@linkplain #changeAmount}
     * @param changeAmount the value for bill_shop_balance_cate_sum.change_amount
     */
    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    /**
     * 
     * {@linkplain #zoneYyyymm}
     *
     * @return the value of bill_shop_balance_cate_sum.zone_yyyymm
     */
    public String getZoneYyyymm() {
        return zoneYyyymm;
    }

    /**
     * 
     * {@linkplain #zoneYyyymm}
     * @param zoneYyyymm the value for bill_shop_balance_cate_sum.zone_yyyymm
     */
    public void setZoneYyyymm(String zoneYyyymm) {
        this.zoneYyyymm = zoneYyyymm;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     *
     * @return the value of bill_shop_balance_cate_sum.sharding_flag
     */
    public String getShardingFlag() {
        return shardingFlag;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     * @param shardingFlag the value for bill_shop_balance_cate_sum.sharding_flag
     */
    public void setShardingFlag(String shardingFlag) {
        this.shardingFlag = shardingFlag;
    }

	public BigDecimal getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(BigDecimal sumAmount) {
		this.sumAmount = sumAmount;
	}

	public BigDecimal getPrepaymentDeductAmount() {
		return prepaymentDeductAmount;
	}

	public void setPrepaymentDeductAmount(BigDecimal prepaymentDeductAmount) {
		this.prepaymentDeductAmount = prepaymentDeductAmount;
	}

	public BigDecimal getUsedPrepaymentDeductAmount() {
		return usedPrepaymentDeductAmount;
	}

	public void setUsedPrepaymentDeductAmount(BigDecimal usedPrepaymentDeductAmount) {
		this.usedPrepaymentDeductAmount = usedPrepaymentDeductAmount;
	}

	public BigDecimal getUsedDeductAmount() {
		return usedDeductAmount;
	}

	public void setUsedDeductAmount(BigDecimal usedDeductAmount) {
		this.usedDeductAmount = usedDeductAmount;
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

	public BigDecimal getMallNumberAmount() {
		return mallNumberAmount;
	}

	public void setMallNumberAmount(BigDecimal mallNumberAmount) {
		this.mallNumberAmount = mallNumberAmount;
	}

	public BigDecimal getSalesDiffamount() {
		return salesDiffamount;
	}

	public void setSalesDiffamount(BigDecimal salesDiffamount) {
		this.salesDiffamount = salesDiffamount;
	}

	public BigDecimal getExpenseOperateAmount() {
		return expenseOperateAmount;
	}

	public void setExpenseOperateAmount(BigDecimal expenseOperateAmount) {
		this.expenseOperateAmount = expenseOperateAmount;
	}

	public String getCategory1() {
		return category1;
	}

	public void setCategory1(String category1) {
		this.category1 = category1;
	}

	public String getCategoryName1() {
		return categoryName1;
	}

	public void setCategoryName1(String categoryName1) {
		this.categoryName1 = categoryName1;
	}

	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public String getCategoryName2() {
		return categoryName2;
	}

	public void setCategoryName2(String categoryName2) {
		this.categoryName2 = categoryName2;
	}

	public String getCategory3() {
		return category3;
	}

	public void setCategory3(String category3) {
		this.category3 = category3;
	}

	public String getCategoryName3() {
		return categoryName3;
	}

	public void setCategoryName3(String categoryName3) {
		this.categoryName3 = categoryName3;
	}

	public BigDecimal getActualBillingAmount() {
		return actualBillingAmount;
	}

	public void setActualBillingAmount(BigDecimal actualBillingAmount) {
		this.actualBillingAmount = actualBillingAmount;
	}
}