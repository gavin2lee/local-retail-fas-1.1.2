package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.BillShopBalanceDeductExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
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
@ExportFormat(className=BillShopBalanceDeductExportFormat.class)
public class BillShopBalanceDeduct extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
     * 管理城市编号
     */
    private String organNo;

    /**
     * 管理城市名称
     */
    private String organName;

    /**
     * 商业集团编码
     */
    private String bsgroupsNo;

    /**
     * 商业集团名称
     */
    private String bsgroupsName;

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
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌编码
     */
    private String brandName;

    /**
     * 费用所属期间-会计期间结算月
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
     * NC月份
     */
    private String ncPeriod;

    /**
     * 费用性质(1-合同内,2-合同外)
     */
    private Byte costType;
    
    private String costTypeStr;
    

    /**
     * 费用扣取方式(1-票前,2-票后)
     */
    private Byte costDeductType;
    
    private String costDeductTypeStr;

    /**
     * 费用交款方式(1-帐扣,2-现金)
     */
    private Byte costPayType;
    
    private String costPayTypeStr;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 发票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal invoiceAmount;

    /**
     * 账扣金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal accountDeductAmount;

    /**
     * 费用类别编码
     */
    private String costCateCode;

    /**
     * 费用类别名称
     */
    private String costCateName;

    /**
     * 商场扣费编码
     */
    private String deductionCode;

    /**
     * 商场扣费名称
     */
    private String deductionName;

    /**
     * 业务单据日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date billDate;

    /**
     * 扣费日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date deductDate;

    /**
     * 系统扣费金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal deductAmount;

    /**
     * 实际金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal actualAmount;

    /**
     * 扣费差异金额=系统扣费金额-实际金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffAmount;

    /**
     * 支付方式
     */
    private String basePayCode;

    /**
     * 其他
     */
    private String baseOther;

    /**
     * 费率
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal rate;

    /**
     * 费率金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal rateAmount;

    /**
     * 补差费率
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceRate;

    /**
     * 补差金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceRateAmount;

    /**
     * 差异原因
     */
    private String diffReason;

    /**
     * 唯一标示，历史发生的记录
     */
    private String markId;

    /**
     * 上期结算编号，滚动add
     */
    private String parBalanceNo;

    /**
     * 结算人员
     */
    private String balanceUser;

    /**
     * 结算状态(1-未结算、2-已结算、3-作废、4-已开票)
     */
    private Byte balanceStatus;

    /**
     * 处理状态(1-未完成、2-已完成)
     */
    private Byte processStatus;
    
    private String processStatusStr;

    /**
     * 费用类型(1-固定额度,2-扣率计算额度)
     */
    private Byte deductType;

    /**
     * 备注
     */
    private String remark;

    /**
     *  结算期内销售总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal systemSalesAmount;
    
    /**
     * 结算期内促销销售总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal proSumSalesAmount;
    
    /**
     * 结算期正常销售总额     销售总金额 - 促销期间的销售总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal notProSumSalesAmount;
    
    /**
     * 生成方式（0：系统自动生成，1：在界面上新增）
     */
    private Integer generateType;
    
    private String accountsNo;
    private String rateId;
    private Integer rateType;
    
    /**
     * 对应商场扣费名称  手工录入
      */
    private String mallDeductionName;
    
    /**
     * 合同基础扣销售额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal conBaseDeductAmount;  

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

	public Integer getGenerateType() {
		return generateType;
	}

	public void setGenerateType(Integer generateType) {
		this.generateType = generateType;
	}

	/**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_shop_balance_deduct.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_shop_balance_deduct.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_shop_balance_deduct.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_shop_balance_deduct.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_shop_balance_deduct.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_shop_balance_deduct.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_shop_balance_deduct.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_shop_balance_deduct.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of bill_shop_balance_deduct.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for bill_shop_balance_deduct.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of bill_shop_balance_deduct.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for bill_shop_balance_deduct.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     *
     * @return the value of bill_shop_balance_deduct.bsgroups_no
     */
    public String getBsgroupsNo() {
        return bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     * @param bsgroupsNo the value for bill_shop_balance_deduct.bsgroups_no
     */
    public void setBsgroupsNo(String bsgroupsNo) {
        this.bsgroupsNo = bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     *
     * @return the value of bill_shop_balance_deduct.bsgroups_name
     */
    public String getBsgroupsName() {
        return bsgroupsName;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     * @param bsgroupsName the value for bill_shop_balance_deduct.bsgroups_name
     */
    public void setBsgroupsName(String bsgroupsName) {
        this.bsgroupsName = bsgroupsName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of bill_shop_balance_deduct.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for bill_shop_balance_deduct.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of bill_shop_balance_deduct.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for bill_shop_balance_deduct.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_shop_balance_deduct.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_shop_balance_deduct.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_shop_balance_deduct.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_shop_balance_deduct.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_shop_balance_deduct.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_shop_balance_deduct.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_shop_balance_deduct.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_shop_balance_deduct.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of bill_shop_balance_deduct.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for bill_shop_balance_deduct.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     *
     * @return the value of bill_shop_balance_deduct.balance_start_date
     */
    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     * @param balanceStartDate the value for bill_shop_balance_deduct.balance_start_date
     */
    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     *
     * @return the value of bill_shop_balance_deduct.balance_end_date
     */
    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     * @param balanceEndDate the value for bill_shop_balance_deduct.balance_end_date
     */
    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * 
     * {@linkplain #ncPeriod}
     *
     * @return the value of bill_shop_balance_deduct.nc_period
     */
    public String getNcPeriod() {
        return ncPeriod;
    }

    /**
     * 
     * {@linkplain #ncPeriod}
     * @param ncPeriod the value for bill_shop_balance_deduct.nc_period
     */
    public void setNcPeriod(String ncPeriod) {
        this.ncPeriod = ncPeriod;
    }

    /**
     * 
     * {@linkplain #costType}
     *
     * @return the value of bill_shop_balance_deduct.cost_type
     */
    public Byte getCostType() {
        return costType;
    }

    /**
     * 
     * {@linkplain #costType}
     * @param costType the value for bill_shop_balance_deduct.cost_type
     */
    public void setCostType(Byte costType) {
        this.costType = costType;
    }

    /**
     * 
     * {@linkplain #costDeductType}
     *
     * @return the value of bill_shop_balance_deduct.cost_deduct_type
     */
    public Byte getCostDeductType() {
        return costDeductType;
    }

    /**
     * 
     * {@linkplain #costDeductType}
     * @param costDeductType the value for bill_shop_balance_deduct.cost_deduct_type
     */
    public void setCostDeductType(Byte costDeductType) {
        this.costDeductType = costDeductType;
    }

    /**
     * 
     * {@linkplain #costPayType}
     *
     * @return the value of bill_shop_balance_deduct.cost_pay_type
     */
    public Byte getCostPayType() {
        return costPayType;
    }

    /**
     * 
     * {@linkplain #costPayType}
     * @param costPayType the value for bill_shop_balance_deduct.cost_pay_type
     */
    public void setCostPayType(Byte costPayType) {
        this.costPayType = costPayType;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     *
     * @return the value of bill_shop_balance_deduct.invoice_no
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     * @param invoiceNo the value for bill_shop_balance_deduct.invoice_no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     *
     * @return the value of bill_shop_balance_deduct.invoice_amount
     */
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     * @param invoiceAmount the value for bill_shop_balance_deduct.invoice_amount
     */
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * 
     * {@linkplain #accountDeductAmount}
     *
     * @return the value of bill_shop_balance_deduct.account_deduct_amount
     */
    public BigDecimal getAccountDeductAmount() {
        return accountDeductAmount;
    }

    /**
     * 
     * {@linkplain #accountDeductAmount}
     * @param accountDeductAmount the value for bill_shop_balance_deduct.account_deduct_amount
     */
    public void setAccountDeductAmount(BigDecimal accountDeductAmount) {
        this.accountDeductAmount = accountDeductAmount;
    }

    /**
     * 
     * {@linkplain #costCateCode}
     *
     * @return the value of bill_shop_balance_deduct.cost_cate_code
     */
    public String getCostCateCode() {
        return costCateCode;
    }

    /**
     * 
     * {@linkplain #costCateCode}
     * @param costCateCode the value for bill_shop_balance_deduct.cost_cate_code
     */
    public void setCostCateCode(String costCateCode) {
        this.costCateCode = costCateCode;
    }

    /**
     * 
     * {@linkplain #costCateName}
     *
     * @return the value of bill_shop_balance_deduct.cost_cate_name
     */
    public String getCostCateName() {
        return costCateName;
    }

    /**
     * 
     * {@linkplain #costCateName}
     * @param costCateName the value for bill_shop_balance_deduct.cost_cate_name
     */
    public void setCostCateName(String costCateName) {
        this.costCateName = costCateName;
    }

    /**
     * 
     * {@linkplain #deductionCode}
     *
     * @return the value of bill_shop_balance_deduct.deduction_code
     */
    public String getDeductionCode() {
        return deductionCode;
    }

    /**
     * 
     * {@linkplain #deductionCode}
     * @param deductionCode the value for bill_shop_balance_deduct.deduction_code
     */
    public void setDeductionCode(String deductionCode) {
        this.deductionCode = deductionCode;
    }

    /**
     * 
     * {@linkplain #deductionName}
     *
     * @return the value of bill_shop_balance_deduct.deduction_name
     */
    public String getDeductionName() {
        return deductionName;
    }

    /**
     * 
     * {@linkplain #deductionName}
     * @param deductionName the value for bill_shop_balance_deduct.deduction_name
     */
    public void setDeductionName(String deductionName) {
        this.deductionName = deductionName;
    }

    /**
     * 
     * {@linkplain #billDate}
     *
     * @return the value of bill_shop_balance_deduct.bill_date
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     * @param billDate the value for bill_shop_balance_deduct.bill_date
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * {@linkplain #deductDate}
     *
     * @return the value of bill_shop_balance_deduct.deduct_date
     */
    public Date getDeductDate() {
        return deductDate;
    }

    /**
     * 
     * {@linkplain #deductDate}
     * @param deductDate the value for bill_shop_balance_deduct.deduct_date
     */
    public void setDeductDate(Date deductDate) {
        this.deductDate = deductDate;
    }

    /**
     * 
     * {@linkplain #deductAmount}
     *
     * @return the value of bill_shop_balance_deduct.deduct_amount
     */
    public BigDecimal getDeductAmount() {
        return deductAmount;
    }

    /**
     * 
     * {@linkplain #deductAmount}
     * @param deductAmount the value for bill_shop_balance_deduct.deduct_amount
     */
    public void setDeductAmount(BigDecimal deductAmount) {
        this.deductAmount = deductAmount;
    }

    /**
     * 
     * {@linkplain #actualAmount}
     *
     * @return the value of bill_shop_balance_deduct.actual_amount
     */
    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    /**
     * 
     * {@linkplain #actualAmount}
     * @param actualAmount the value for bill_shop_balance_deduct.actual_amount
     */
    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    /**
     * 
     * {@linkplain #diffAmount}
     *
     * @return the value of bill_shop_balance_deduct.diff_amount
     */
    public BigDecimal getDiffAmount() {
        return diffAmount;
    }

    /**
     * 
     * {@linkplain #diffAmount}
     * @param diffAmount the value for bill_shop_balance_deduct.diff_amount
     */
    public void setDiffAmount(BigDecimal diffAmount) {
        this.diffAmount = diffAmount;
    }

    /**
     * 
     * {@linkplain #basePayCode}
     *
     * @return the value of bill_shop_balance_deduct.base_pay_code
     */
    public String getBasePayCode() {
        return basePayCode;
    }

    /**
     * 
     * {@linkplain #basePayCode}
     * @param basePayCode the value for bill_shop_balance_deduct.base_pay_code
     */
    public void setBasePayCode(String basePayCode) {
        this.basePayCode = basePayCode;
    }

    /**
     * 
     * {@linkplain #baseOther}
     *
     * @return the value of bill_shop_balance_deduct.base_other
     */
    public String getBaseOther() {
        return baseOther;
    }

    /**
     * 
     * {@linkplain #baseOther}
     * @param baseOther the value for bill_shop_balance_deduct.base_other
     */
    public void setBaseOther(String baseOther) {
        this.baseOther = baseOther;
    }

    /**
     * 
     * {@linkplain #rate}
     *
     * @return the value of bill_shop_balance_deduct.rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 
     * {@linkplain #rate}
     * @param rate the value for bill_shop_balance_deduct.rate
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 
     * {@linkplain #rateAmount}
     *
     * @return the value of bill_shop_balance_deduct.rate_amount
     */
    public BigDecimal getRateAmount() {
        return rateAmount;
    }

    /**
     * 
     * {@linkplain #rateAmount}
     * @param rateAmount the value for bill_shop_balance_deduct.rate_amount
     */
    public void setRateAmount(BigDecimal rateAmount) {
        this.rateAmount = rateAmount;
    }

    /**
     * 
     * {@linkplain #balanceRate}
     *
     * @return the value of bill_shop_balance_deduct.balance_rate
     */
    public BigDecimal getBalanceRate() {
        return balanceRate;
    }

    /**
     * 
     * {@linkplain #balanceRate}
     * @param balanceRate the value for bill_shop_balance_deduct.balance_rate
     */
    public void setBalanceRate(BigDecimal balanceRate) {
        this.balanceRate = balanceRate;
    }

    /**
     * 
     * {@linkplain #balanceRateAmount}
     *
     * @return the value of bill_shop_balance_deduct.balance_rate_amount
     */
    public BigDecimal getBalanceRateAmount() {
        return balanceRateAmount;
    }

    /**
     * 
     * {@linkplain #balanceRateAmount}
     * @param balanceRateAmount the value for bill_shop_balance_deduct.balance_rate_amount
     */
    public void setBalanceRateAmount(BigDecimal balanceRateAmount) {
        this.balanceRateAmount = balanceRateAmount;
    }

    /**
     * 
     * {@linkplain #diffReason}
     *
     * @return the value of bill_shop_balance_deduct.diff_reason
     */
    public String getDiffReason() {
        return diffReason;
    }

    /**
     * 
     * {@linkplain #diffReason}
     * @param diffReason the value for bill_shop_balance_deduct.diff_reason
     */
    public void setDiffReason(String diffReason) {
        this.diffReason = diffReason;
    }

 

    /**
     * 
     * {@linkplain #parBalanceNo}
     *
     * @return the value of bill_shop_balance_deduct.par_balance_no
     */
    public String getParBalanceNo() {
        return parBalanceNo;
    }

    /**
     * 
     * {@linkplain #parBalanceNo}
     * @param parBalanceNo the value for bill_shop_balance_deduct.par_balance_no
     */
    public void setParBalanceNo(String parBalanceNo) {
        this.parBalanceNo = parBalanceNo;
    }

    /**
     * 
     * {@linkplain #balanceUser}
     *
     * @return the value of bill_shop_balance_deduct.balance_user
     */
    public String getBalanceUser() {
        return balanceUser;
    }

    /**
     * 
     * {@linkplain #balanceUser}
     * @param balanceUser the value for bill_shop_balance_deduct.balance_user
     */
    public void setBalanceUser(String balanceUser) {
        this.balanceUser = balanceUser;
    }

    /**
     * 
     * {@linkplain #balanceStatus}
     *
     * @return the value of bill_shop_balance_deduct.balance_status
     */
    public Byte getBalanceStatus() {
        return balanceStatus;
    }

    /**
     * 
     * {@linkplain #balanceStatus}
     * @param balanceStatus the value for bill_shop_balance_deduct.balance_status
     */
    public void setBalanceStatus(Byte balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    /**
     * 
     * {@linkplain #processStatus}
     *
     * @return the value of bill_shop_balance_deduct.process_status
     */
    public Byte getProcessStatus() {
        return processStatus;
    }

    /**
     * 
     * {@linkplain #processStatus}
     * @param processStatus the value for bill_shop_balance_deduct.process_status
     */
    public void setProcessStatus(Byte processStatus) {
        this.processStatus = processStatus;
    }

    /**
     * 
     * {@linkplain #deductType}
     *
     * @return the value of bill_shop_balance_deduct.deduct_type
     */
    public Byte getDeductType() {
        return deductType;
    }

    /**
     * 
     * {@linkplain #deductType}
     * @param deductType the value for bill_shop_balance_deduct.deduct_type
     */
    public void setDeductType(Byte deductType) {
        this.deductType = deductType;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_shop_balance_deduct.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_shop_balance_deduct.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getCostTypeStr() {
		return costTypeStr;
	}

	public void setCostTypeStr(String costTypeStr) {
		this.costTypeStr = costTypeStr;
	}

	public String getCostDeductTypeStr() {
		return costDeductTypeStr;
	}

	public void setCostDeductTypeStr(String costDeductTypeStr) {
		this.costDeductTypeStr = costDeductTypeStr;
	}

	public String getCostPayTypeStr() {
		return costPayTypeStr;
	}

	public void setCostPayTypeStr(String costPayTypeStr) {
		this.costPayTypeStr = costPayTypeStr;
	}

	public BigDecimal getSystemSalesAmount() {
		return systemSalesAmount;
	}

	public void setSystemSalesAmount(BigDecimal systemSalesAmount) {
		this.systemSalesAmount = systemSalesAmount;
	}

	public BigDecimal getProSumSalesAmount() {
		return proSumSalesAmount;
	}

	public void setProSumSalesAmount(BigDecimal proSumSalesAmount) {
		this.proSumSalesAmount = proSumSalesAmount;
	}

	public BigDecimal getNotProSumSalesAmount() {
		return notProSumSalesAmount;
	}

	public void setNotProSumSalesAmount(BigDecimal notProSumSalesAmount) {
		this.notProSumSalesAmount = notProSumSalesAmount;
	}

	public BigDecimal getConBaseDeductAmount() {
		return conBaseDeductAmount;
	}

	public void setConBaseDeductAmount(BigDecimal conBaseDeductAmount) {
		this.conBaseDeductAmount = conBaseDeductAmount;
	}

	public String getAccountsNo() {
		return accountsNo;
	}

	public void setAccountsNo(String accountsNo) {
		this.accountsNo = accountsNo;
	}

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	public Integer getRateType() {
		return rateType;
	}

	public void setRateType(Integer rateType) {
		this.rateType = rateType;
	}

	public String getMallDeductionName() {
		return mallDeductionName;
	}

	public void setMallDeductionName(String mallDeductionName) {
		this.mallDeductionName = mallDeductionName;
	}

	public String getMarkId() {
		return markId;
	}

	public void setMarkId(String markId) {
		this.markId = markId;
	}

	public String getProcessStatusStr() {
		return processStatusStr;
	}

	public void setProcessStatusStr(String processStatusStr) {
		this.processStatusStr = processStatusStr;
	}
}