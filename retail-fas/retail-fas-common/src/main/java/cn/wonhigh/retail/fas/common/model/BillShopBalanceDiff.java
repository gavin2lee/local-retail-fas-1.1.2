package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.BillShopBalanceDiffExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$4;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-01-29 11:58:31
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
@ExportFormat(className=BillShopBalanceDiffExportFormat.class)
public class BillShopBalanceDiff extends FasBaseModel {

	private static final long serialVersionUID = 2904330724989050035L;

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
     * 经营区域编号
     */
    private String zoneNo;

    /**
     * 经营区域名称
     */
    private String zoneName;

    /**
     * 管理城市编号
     */
    private String organNo;

    /**
     * 管理城市名称
     */
    private String organName;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺简称
     */
    private String shortName;

    /**
     * 品牌编码
     */
    private String brandNo;

    public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	private String  discountCode;
    /**
     * 品牌中文名称
     */
    private String brandName;
    
    /**
     * 结算码
     */
    private String billingCode;

    /**
     * 差异类型
     */
    private String diffTypeCode;

    /**
     * 差异类型
     */
    private String diffTypeName;

    /**
     * 差异结算日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date diffDate;

    /**
     * 结算日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceDate;

    /**
     * 费用所属期间-会计期间结算月
     */
    private String month;

    /**
     * 业务单据日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date billDate;

    /**
     * 促销活动编号
     */
    private String proNo;

    /**
     * 促销活动名称
     */
    private String proName;

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
     * 扣率,如0.17
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal discount;

    /**
     * 系统扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal deductDiffAmount;

    /**
     * 商场报数
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal mallNumber;

    /**
     * 系统收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal salesAmount;

    /**
     * 差异金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal salesDiffamount;

    /**
     * 扣费差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffAmount;
    
    /** 初始扣费差异，值等于扣费差异，用于改变扣费差异、调整金额时使用 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffAmountVal;

    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceDiffAmount;
    
    
    /**
     * 差异原因
     */
    private String diffReason;

    /**
     * 本期差异余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffBalance;
    
    /** 初始本期差异余额，值等于扣费差异，用于改变扣费差异、调整金额时使用 */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffBalanceVal;

    /**
     * 上期差异余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal preDiffBalance;
    
    /**
     * 差异回款
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffBackAmount;

    /**
     * 本次结算金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceAmount;

    /**
     * 原因
     */
    private String reason;

    /**
     * 调整金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal changeAmount;

    /**
     * 调整日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date changeDate;

    /**
     * 调整原因
     */
    private String changeReason;


    /**
     * 唯一标示，历史发生的记录
     */
    private String markId;
    
    /**
     * 最初的差异的id
     */
    private String rootDiffId;

    /**
     * 上期结算编号，滚动add
     */
    private String parBalanceNo;

    /**
     * 备注
     */
    private String remark;
   
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
     * 累计差异余额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal  diffBalanceSum;
    
    /*
     * 累计差异回款
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffBackAmountSum;
    
    private String  discountN;
    
    /**
     * 报数差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal reportDiffAmount;
	
    
    /**
     * 生成方式（0：系统自动生成，1：在界面上新增）
     */
    private Integer generateType;
    
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

	public BigDecimal getReportDiffAmount() {
		return reportDiffAmount;
	}

	public void setReportDiffAmount(BigDecimal reportDiffAmount) {
		this.reportDiffAmount = reportDiffAmount;
	}

	public String getRootDiffId() {
		return rootDiffId;
	}

	public void setRootDiffId(String rootDiffId) {
		this.rootDiffId = rootDiffId;
	}

	public BigDecimal getDiffAmountVal() {
    	this.diffAmountVal = this.getDiffAmount();
		return diffAmountVal;
	}

	public void setDiffAmountVal(BigDecimal diffAmountVal) {
		this.diffAmountVal = diffAmountVal;
	}

	public BigDecimal getDiffBalanceVal() {
		this.diffBalanceVal = this.getDiffBalance();
		return diffBalanceVal;
	}

	public void setDiffBalanceVal(BigDecimal diffBalanceVal) {
		this.diffBalanceVal = diffBalanceVal;
	}

	public BigDecimal getPreDiffBalance() {
		return preDiffBalance;
	}

	public void setPreDiffBalance(BigDecimal preDiffBalance) {
		this.preDiffBalance = preDiffBalance;
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

	public BigDecimal getDiffBalanceSum() {
		return diffBalanceSum;
	}

	public void setDiffBalanceSum(BigDecimal diffBalanceSum) {
		this.diffBalanceSum = diffBalanceSum;
	}

	public BigDecimal getDiffBackAmountSum() {
		return diffBackAmountSum;
	}

	public void setDiffBackAmountSum(BigDecimal diffBackAmountSum) {
		this.diffBackAmountSum = diffBackAmountSum;
	}

	public void setDiscountN(String discountN) {
		this.discountN = discountN;
	}
    
    
    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_shop_balance_diff.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_shop_balance_diff.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_shop_balance_diff.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_shop_balance_diff.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_shop_balance_diff.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_shop_balance_diff.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_shop_balance_diff.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_shop_balance_diff.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of bill_shop_balance_diff.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for bill_shop_balance_diff.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneName}
     *
     * @return the value of bill_shop_balance_diff.zone_name
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * {@linkplain #zoneName}
     * @param zoneName the value for bill_shop_balance_diff.zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of bill_shop_balance_diff.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for bill_shop_balance_diff.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of bill_shop_balance_diff.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for bill_shop_balance_diff.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_shop_balance_diff.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_shop_balance_diff.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_shop_balance_diff.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_shop_balance_diff.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_shop_balance_diff.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_shop_balance_diff.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_shop_balance_diff.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_shop_balance_diff.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #diffTypeCode}
     *
     * @return the value of bill_shop_balance_diff.diff_type_code
     */
    public String getDiffTypeCode() {
        return diffTypeCode;
    }

    /**
     * 
     * {@linkplain #diffTypeCode}
     * @param diffTypeCode the value for bill_shop_balance_diff.diff_type_code
     */
    public void setDiffTypeCode(String diffTypeCode) {
        this.diffTypeCode = diffTypeCode;
    }

    /**
     * 
     * {@linkplain #diffTypeName}
     *
     * @return the value of bill_shop_balance_diff.diff_type_name
     */
    public String getDiffTypeName() {
        return diffTypeName;
    }

    /**
     * 
     * {@linkplain #diffTypeName}
     * @param diffTypeName the value for bill_shop_balance_diff.diff_type_name
     */
    public void setDiffTypeName(String diffTypeName) {
        this.diffTypeName = diffTypeName;
    }

    /**
     * 
     * {@linkplain #diffDate}
     *
     * @return the value of bill_shop_balance_diff.diff_date
     */
    public Date getDiffDate() {
        return diffDate;
    }

    /**
     * 
     * {@linkplain #diffDate}
     * @param diffDate the value for bill_shop_balance_diff.diff_date
     */
    public void setDiffDate(Date diffDate) {
        this.diffDate = diffDate;
    }

    /**
     * 
     * {@linkplain #balanceDate}
     *
     * @return the value of bill_shop_balance_diff.balance_date
     */
    public Date getBalanceDate() {
        return balanceDate;
    }

    /**
     * 
     * {@linkplain #balanceDate}
     * @param balanceDate the value for bill_shop_balance_diff.balance_date
     */
    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of bill_shop_balance_diff.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for bill_shop_balance_diff.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #billDate}
     *
     * @return the value of bill_shop_balance_diff.bill_date
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     * @param billDate the value for bill_shop_balance_diff.bill_date
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * {@linkplain #proNo}
     *
     * @return the value of bill_shop_balance_diff.pro_no
     */
    public String getProNo() {
        return proNo;
    }

    /**
     * 
     * {@linkplain #proNo}
     * @param proNo the value for bill_shop_balance_diff.pro_no
     */
    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    /**
     * 
     * {@linkplain #proName}
     *
     * @return the value of bill_shop_balance_diff.pro_name
     */
    public String getProName() {
        return proName;
    }

    /**
     * 
     * {@linkplain #proName}
     * @param proName the value for bill_shop_balance_diff.pro_name
     */
    public void setProName(String proName) {
        this.proName = proName;
    }

    /**
     * 
     * {@linkplain #proStartDate}
     *
     * @return the value of bill_shop_balance_diff.pro_start_date
     */
    public Date getProStartDate() {
        return proStartDate;
    }

    /**
     * 
     * {@linkplain #proStartDate}
     * @param proStartDate the value for bill_shop_balance_diff.pro_start_date
     */
    public void setProStartDate(Date proStartDate) {
        this.proStartDate = proStartDate;
    }

    /**
     * 
     * {@linkplain #proEndDate}
     *
     * @return the value of bill_shop_balance_diff.pro_end_date
     */
    public Date getProEndDate() {
        return proEndDate;
    }

    /**
     * 
     * {@linkplain #proEndDate}
     * @param proEndDate the value for bill_shop_balance_diff.pro_end_date
     */
    public void setProEndDate(Date proEndDate) {
        this.proEndDate = proEndDate;
    }

    /**
     * 
     * {@linkplain #discount}
     *
     * @return the value of bill_shop_balance_diff.discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 
     * {@linkplain #discount}
     * @param discount the value for bill_shop_balance_diff.discount
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 
     * {@linkplain #deductDiffAmount}
     *
     * @return the value of bill_shop_balance_diff.deduct_diff_amount
     */
    public BigDecimal getDeductDiffAmount() {
        return deductDiffAmount;
    }

    /**
     * 
     * {@linkplain #deductDiffAmount}
     * @param deductDiffAmount the value for bill_shop_balance_diff.deduct_diff_amount
     */
    public void setDeductDiffAmount(BigDecimal deductDiffAmount) {
        this.deductDiffAmount = deductDiffAmount;
    }

    /**
     * 
     * {@linkplain #mallNumber}
     *
     * @return the value of bill_shop_balance_diff.mall_number
     */
    public BigDecimal getMallNumber() {
        return mallNumber;
    }

    /**
     * 
     * {@linkplain #mallNumber}
     * @param mallNumber the value for bill_shop_balance_diff.mall_number
     */
    public void setMallNumber(BigDecimal mallNumber) {
        this.mallNumber = mallNumber;
    }

    /**
     * 
     * {@linkplain #salesAmount}
     *
     * @return the value of bill_shop_balance_diff.sales_amount
     */
    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    /**
     * 
     * {@linkplain #salesAmount}
     * @param salesAmount the value for bill_shop_balance_diff.sales_amount
     */
    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

    /**
     * 
     * {@linkplain #salesDiffamount}
     *
     * @return the value of bill_shop_balance_diff.sales_diffamount
     */
    public BigDecimal getSalesDiffamount() {
        return salesDiffamount;
    }

    /**
     * 
     * {@linkplain #salesDiffamount}
     * @param salesDiffamount the value for bill_shop_balance_diff.sales_diffamount
     */
    public void setSalesDiffamount(BigDecimal salesDiffamount) {
        this.salesDiffamount = salesDiffamount;
    }

    /**
     * 
     * {@linkplain #diffAmount}
     *
     * @return the value of bill_shop_balance_diff.diff_amount
     */
    public BigDecimal getDiffAmount() {
        return diffAmount;
    }

    /**
     * 
     * {@linkplain #diffAmount}
     * @param diffAmount the value for bill_shop_balance_diff.diff_amount
     */
    public void setDiffAmount(BigDecimal diffAmount) {
        this.diffAmount = diffAmount;
    }

    /**
     * 
     * {@linkplain #diffReason}
     *
     * @return the value of bill_shop_balance_diff.diff_reason
     */
    public String getDiffReason() {
        return diffReason;
    }

    /**
     * 
     * {@linkplain #diffReason}
     * @param diffReason the value for bill_shop_balance_diff.diff_reason
     */
    public void setDiffReason(String diffReason) {
        this.diffReason = diffReason;
    }

    /**
     * 
     * {@linkplain #diffBalance}
     *
     * @return the value of bill_shop_balance_diff.diff_balance
     */
    public BigDecimal getDiffBalance() {
        return diffBalance;
    }

    /**
     * 
     * {@linkplain #diffBalance}
     * @param diffBalance the value for bill_shop_balance_diff.diff_balance
     */
    public void setDiffBalance(BigDecimal diffBalance) {
        this.diffBalance = diffBalance;
    }

    /**
     * 
     * {@linkplain #diffBackAmount}
     *
     * @return the value of bill_shop_balance_diff.diff_back_amount
     */
    public BigDecimal getDiffBackAmount() {
        return diffBackAmount;
    }

    /**
     * 
     * {@linkplain #diffBackAmount}
     * @param diffBackAmount the value for bill_shop_balance_diff.diff_back_amount
     */
    public void setDiffBackAmount(BigDecimal diffBackAmount) {
        this.diffBackAmount = diffBackAmount;
    }

    /**
     * 
     * {@linkplain #balanceAmount}
     *
     * @return the value of bill_shop_balance_diff.balance_amount
     */
    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    /**
     * 
     * {@linkplain #balanceAmount}
     * @param balanceAmount the value for bill_shop_balance_diff.balance_amount
     */
    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    /**
     * 
     * {@linkplain #reason}
     *
     * @return the value of bill_shop_balance_diff.reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * 
     * {@linkplain #reason}
     * @param reason the value for bill_shop_balance_diff.reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 
     * {@linkplain #changeAmount}
     *
     * @return the value of bill_shop_balance_diff.change_amount
     */
    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    /**
     * 
     * {@linkplain #changeAmount}
     * @param changeAmount the value for bill_shop_balance_diff.change_amount
     */
    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    /**
     * 
     * {@linkplain #changeDate}
     *
     * @return the value of bill_shop_balance_diff.change_date
     */
    public Date getChangeDate() {
        return changeDate;
    }

    /**
     * 
     * {@linkplain #changeDate}
     * @param changeDate the value for bill_shop_balance_diff.change_date
     */
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    /**
     * 
     * {@linkplain #changeReason}
     *
     * @return the value of bill_shop_balance_diff.change_reason
     */
    public String getChangeReason() {
        return changeReason;
    }

    /**
     * 
     * {@linkplain #changeReason}
     * @param changeReason the value for bill_shop_balance_diff.change_reason
     */
    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

 
    /**
     * 
     * {@linkplain #parBalanceNo}
     *
     * @return the value of bill_shop_balance_diff.par_balance_no
     */
    public String getParBalanceNo() {
        return parBalanceNo;
    }

    /**
     * 
     * {@linkplain #parBalanceNo}
     * @param parBalanceNo the value for bill_shop_balance_diff.par_balance_no
     */
    public void setParBalanceNo(String parBalanceNo) {
        this.parBalanceNo = parBalanceNo;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_shop_balance_diff.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_shop_balance_diff.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public Integer getGenerateType() {
		return generateType;
	}

	public void setGenerateType(Integer generateType) {
		this.generateType = generateType;
	}

	public String getBillingCode() {
		return billingCode;
	}

	public void setBillingCode(String billingCode) {
		this.billingCode = billingCode;
	}

	public void setMarkId(String markId) {
		this.markId = markId;
	}

	public String getMarkId() {
		return markId;
	}

	public BigDecimal getBalanceDiffAmount() {
		return balanceDiffAmount;
	}

	public void setBalanceDiffAmount(BigDecimal balanceDiffAmount) {
		this.balanceDiffAmount = balanceDiffAmount;
	}

 
}