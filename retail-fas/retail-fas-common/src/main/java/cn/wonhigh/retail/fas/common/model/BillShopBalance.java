package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.BillShopBalanceExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$4;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-01-29 14:18:33
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
@ExportFormat(className=BillShopBalanceExportFormat.class)
public class BillShopBalance  extends FasBaseModel {

	private static final long serialVersionUID = -3084039257396200715L;

	/**
     * 结算单据编号
     */
    private String balanceNo;

    /**
     * 单据名称
     */
    private String billName;
    
    private int  balanceStatus;

    /**
     * 单据日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date billDate;

    /**
     * 结算单类型(1-正式，2-预估)
     */
    private Byte balanceType;
    
    private String  balanceTypeName;
    
    public String getBalanceTypeName() {
    	if(getBalanceType() == null) {
    		return balanceTypeName;
    	}
		if(1 == getBalanceType().intValue()) {
			balanceTypeName = "正式";
		} else if(2 == getBalanceType().intValue()) {
			balanceTypeName = "预估";
		}
		return balanceTypeName;
	}
    
	public void setBalanceTypeName(String balanceTypeName) {
		this.balanceTypeName = balanceTypeName;
	}
    /**
     * 单据状态(1-制单、2-确认、3-作废、4、已开票)
     */

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
     * 商业集团编码
     */
    private String bsgroupsNo;

    /**
     * 商业集团名称
     */
    private String bsgroupsName;

    /**
     * 片区编码
     */
    private String regionNo;

    /**
     * 片区名称
     */
    private String regionName;

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
     * (销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)
     */
    private String retailType;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌中文名称
     */
    private String brandName;

    /**
     * 月份
     */
    private String month;
    private String startMonth;
    private String endMonth;

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
    
    
    private String startEndDate;

    /**
     * 开票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date billingDate;

    /**
     * 打印次数
     */
    private Integer printCount;

    /**
     * 合同扣率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal contractRate;

    /**
     * 实际扣率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal actualRate;

    private String  contractRateName;
	
    private String  actualRateName = "";
    
    private String isEqureTrue;
    
    private String isEqureTrueName;
    
    /**
     * 商场报数
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal otherDeduct;
    
    /**
     * 商场报数
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal mallNumberAmount;

    /**
     * 系统收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal systemSalesAmount; 

    /**
     * 报数差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal salesDiffamount;

    /**
     * 扣费金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal mallDeductAmount;

    /**
     * 开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal billingAmount;

    /**
     * 商场开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal mallBillingAmount;

    /**
     * 系统开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal systemBillingAmount;

    /**
     * 未开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal noBillingAmount;

    /**
     * 预收款金额合计
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal expectCashAmount;

    /**
     * 票前费用扣款金额合计
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceDeductAmount;

    /**
     * 结算差异金额合计
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceDiffAmount;

    /**
     * 其他合计
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal otherTotalAmount;

    /**
     * 收款差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal differenceAmount;

    /**
     * 预估差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal estimateAmount;

    /**
     * 回款金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal paymentAmount;

    /**
     * 未回款金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal nonPaymentAmount;

    /**
     * 源结算单号-预估转正式赋值
     */
    private String sourceBalanceNo;

    /**
     * 合同正价扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal conpriceDeductAmount;

    /**
     * 促销扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal promDeductAmount;

    /**
     * 促销加扣
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal promPlusbuckleAmount;

    /**
     * 票前费用调整金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal adjustDeductAmount;

    /**
     * 结算差异调整金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal adjustDiffAmount;
    
    /**
     * 结算差异方式设置(1-按明细、2-按促销活动、3-按销售)
     */
    private Byte balanceDiffType;

    /**
     * 结算描述
     */
    private String balanceDesc;

    /**
     * 备注
     */
    private String remark;

    /**
     * 开票申请单号
     */
    private String invoiceApplyNo;

    /**
     * 发票登记申请单号
     */
    private String invoiceRegisterNo;

    /**
     * 申请开票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceApplyDate;
    
    /**
     * 发票号
     */
    private String invoiceNo;
    
    /** 开票名称 */
    private String invoiceName;
    
    private String payCode;
	
    /** 提示消息 */
	private String errorInfo;
	
	/** 提示消息类型 */
	private Integer errorType;
	
	private String billStatusName;
    
	/** 结算期主键 */
	private String shopBalanceDateId;
	
	  /**
     * 合同基础扣销售额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal conBaseDeductAmount;
	
    public Integer getErrorType() {
		return errorType;
	}

	public void setErrorType(Integer errorType) {
		this.errorType = errorType;
	}

	/**
     * 大区年月
     */
    private String zoneYyyymm;
    
    /**
     * 票后账扣费用合计
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceDeductAfterAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal ticketDeductionAmount;
    
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal conBaseDeduct;
    
    private String taxpayerNo;
    
    private String showRateExpenseRemind;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal prepaymentAmount;
    
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal usedPrepaymentAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal prepaymentAmountBrand;
    
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal usedPrepaymentAmountBrand;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal returnedAmount; 
    
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal noconpriceDeductAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal invoiceAmount;
    
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date isInvoiceDate;
    
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date actualInvoiceDate;
    
//    发票超期天数

    private int invoiceIsOverdue; 
//    发票应寄送日期
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceDate;
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceActualDate;
 
//    发票寄送超期天数
    private int  sendInvoiceOverdue; 
    
//    发票应送到日期
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceSentDate;
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date actualinvDelivDate;
    private int invoiceOverdue; 
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal amountPayment;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal theAmountPayment;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal receivableBalance; 
    
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date paymentDate; 
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date thePaymentDate; 
    
    private int overduePaymentdue;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal returnDifference; 
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal billAccount; 
//    账扣回票差异
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal accountDedReturnDiff;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal theCurrDiffAmountTotal;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal hasNotPaidBack;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal cumulativeReturn;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal cumulativeTotalVariance; 
    
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date accountsPayableDay;
    
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date  actualPaymentDate;
    
    private int salesNum;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal preAccountChar;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal invoiceOpened; 
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal invoicedAmount;
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal noInvoicedAmount;
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal noPaymentAmount;
    
    private int costDifferenceTag;
    private int deductionDiffTag;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal deductionDiffAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal postAccountExpAmount; 
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal costReceivTotal;
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal mallCostTotal; 
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal costDiffAmount; 

    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceDeduTotalAmount;
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal mallDeductTotal;
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceAmount;
    /**
     * 
     * 分库字段
     */
    private String shardingFlag;
    
	public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	public String getStartEndDate() {
    	String  startDate = DateUtil.format(this.getBalanceStartDate(), "yyyyMMdd");
    	String  endDate = DateUtil.format(this.getBalanceEndDate(),"yyyyMMdd");
    	startEndDate = startDate+" - "+endDate;
		return startEndDate;
	}

	public void setStartEndDate(String startEndDate) {
		this.startEndDate = startEndDate;
	}
	
    public String getShopBalanceDateId() {
		return shopBalanceDateId;
	}

	public void setShopBalanceDateId(String shopBalanceDateId) {
		this.shopBalanceDateId = shopBalanceDateId;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public void setContractRateName(String contractRateName) {
		this.contractRateName = contractRateName;
	}

	public void setActualRateName(String actualRateName) {
		this.actualRateName = actualRateName;
	}
    
    public String getContractRateName() {
    	BigDecimal  contractRate = getContractRate();
		if(contractRate != null) {
			BigDecimal discount0 = contractRate.setScale(0,RoundingMode.FLOOR);
			BigDecimal discount4 = contractRate.setScale(4, RoundingMode.HALF_UP);
			
			if(discount0.compareTo(discount4)==0){
				contractRateName=discount0.toString()+"%";	
			}else{
				DecimalFormat df = new DecimalFormat("0.000#");
				contractRateName=df.format(contractRate)+"%";
			}
//			contractRateName=df.format(contractRate)+"%";
	    }else{
	    	contractRateName="";
	    }
		
		return contractRateName;
	}
    
    public String getActualRateName() {
    	BigDecimal actualRate = getActualRate();
		if(actualRate != null) {
			BigDecimal discount0 = actualRate.setScale(0,RoundingMode.FLOOR);
			BigDecimal discount4 = actualRate.setScale(4, RoundingMode.HALF_UP);
			
			if(discount0.compareTo(discount4)==0){
				actualRateName=discount0.toString()+"%";	
			}else{
				DecimalFormat df = new DecimalFormat("0.000#");
				actualRateName=df.format(actualRate)+"%";
			}
//			 DecimalFormat df = new DecimalFormat("0.000#");
//			 actualRateName=df.format(actualRate)+"%";
	     }
		return actualRateName;
	}
	
    //(1-制单、2-确认、3-作废、4、已开票)'
    public String getBillStatusName() {
    	if(getStatus() == null) {
    		return billStatusName;
    	}
		if(1 == getStatus().intValue()) {
			billStatusName = "制单";
		} else if(2 == getStatus().intValue()) {
			billStatusName = "确认(财务)";
		} else if(3 == getStatus().intValue()) {
			billStatusName = "作废";
		} else if(4 == getStatus().intValue()) {
			billStatusName = "已开票申请";
		} else if(5 == getStatus().intValue()) {
			billStatusName = "确认(业务)";
		}
		return billStatusName;
	}
    
	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}
	
    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_shop_balance.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_shop_balance.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #billName}
     *
     * @return the value of bill_shop_balance.bill_name
     */
    public String getBillName() {
        return billName;
    }

    /**
     * 
     * {@linkplain #billName}
     * @param billName the value for bill_shop_balance.bill_name
     */
    public void setBillName(String billName) {
        this.billName = billName;
    }

    /**
     * 
     * {@linkplain #billDate}
     *
     * @return the value of bill_shop_balance.bill_date
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     * @param billDate the value for bill_shop_balance.bill_date
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * {@linkplain #balanceType}
     *
     * @return the value of bill_shop_balance.balance_type
     */
    public Byte getBalanceType() {
        return balanceType;
    }

    /**
     * 
     * {@linkplain #balanceType}
     * @param balanceType the value for bill_shop_balance.balance_type
     */
    public void setBalanceType(Byte balanceType) {
        this.balanceType = balanceType;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_shop_balance.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_shop_balance.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_shop_balance.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_shop_balance.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of bill_shop_balance.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for bill_shop_balance.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneName}
     *
     * @return the value of bill_shop_balance.zone_name
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * {@linkplain #zoneName}
     * @param zoneName the value for bill_shop_balance.zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of bill_shop_balance.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for bill_shop_balance.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of bill_shop_balance.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for bill_shop_balance.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     *
     * @return the value of bill_shop_balance.bsgroups_no
     */
    public String getBsgroupsNo() {
        return bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     * @param bsgroupsNo the value for bill_shop_balance.bsgroups_no
     */
    public void setBsgroupsNo(String bsgroupsNo) {
        this.bsgroupsNo = bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     *
     * @return the value of bill_shop_balance.bsgroups_name
     */
    public String getBsgroupsName() {
        return bsgroupsName;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     * @param bsgroupsName the value for bill_shop_balance.bsgroups_name
     */
    public void setBsgroupsName(String bsgroupsName) {
        this.bsgroupsName = bsgroupsName;
    }

    /**
     * 
     * {@linkplain #regionNo}
     *
     * @return the value of bill_shop_balance.region_no
     */
    public String getRegionNo() {
        return regionNo;
    }

    /**
     * 
     * {@linkplain #regionNo}
     * @param regionNo the value for bill_shop_balance.region_no
     */
    public void setRegionNo(String regionNo) {
        this.regionNo = regionNo;
    }

    /**
     * 
     * {@linkplain #regionName}
     *
     * @return the value of bill_shop_balance.region_name
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * 
     * {@linkplain #regionName}
     * @param regionName the value for bill_shop_balance.region_name
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of bill_shop_balance.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for bill_shop_balance.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of bill_shop_balance.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for bill_shop_balance.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_shop_balance.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_shop_balance.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_shop_balance.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_shop_balance.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of bill_shop_balance.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for bill_shop_balance.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #retailType}
     *
     * @return the value of bill_shop_balance.retail_type
     */
    public String getRetailType() {
        return retailType;
    }

    /**
     * 
     * {@linkplain #retailType}
     * @param retailType the value for bill_shop_balance.retail_type
     */
    public void setRetailType(String retailType) {
        this.retailType = retailType;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of bill_shop_balance.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for bill_shop_balance.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of bill_shop_balance.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for bill_shop_balance.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_shop_balance.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_shop_balance.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_shop_balance.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_shop_balance.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of bill_shop_balance.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for bill_shop_balance.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     *
     * @return the value of bill_shop_balance.balance_start_date
     */
    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     * @param balanceStartDate the value for bill_shop_balance.balance_start_date
     */
    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     *
     * @return the value of bill_shop_balance.balance_end_date
     */
    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     * @param balanceEndDate the value for bill_shop_balance.balance_end_date
     */
    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * 
     * {@linkplain #billingDate}
     *
     * @return the value of bill_shop_balance.billing_date
     */
    public Date getBillingDate() {
        return billingDate;
    }

    /**
     * 
     * {@linkplain #billingDate}
     * @param billingDate the value for bill_shop_balance.billing_date
     */
    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    /**
     * 
     * {@linkplain #printCount}
     *
     * @return the value of bill_shop_balance.print_count
     */
    public Integer getPrintCount() {
        return printCount;
    }

    /**
     * 
     * {@linkplain #printCount}
     * @param printCount the value for bill_shop_balance.print_count
     */
    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
    }

    /**
     * 
     * {@linkplain #contractRate}
     *
     * @return the value of bill_shop_balance.contract_rate
     */
    public BigDecimal getContractRate() {
        return contractRate;
    }

    /**
     * 
     * {@linkplain #contractRate}
     * @param contractRate the value for bill_shop_balance.contract_rate
     */
    public void setContractRate(BigDecimal contractRate) {
        this.contractRate = contractRate;
    }

    /**
     * 
     * {@linkplain #actualRate}
     *
     * @return the value of bill_shop_balance.actual_rate
     */
    public BigDecimal getActualRate() {
        return actualRate;
    }

    /**
     * 
     * {@linkplain #actualRate}
     * @param actualRate the value for bill_shop_balance.actual_rate
     */
    public void setActualRate(BigDecimal actualRate) {
        this.actualRate = actualRate;
    }

    /**
     * 
     * {@linkplain #mallNumberAmount}
     *
     * @return the value of bill_shop_balance.mall_number_amount
     */
    public BigDecimal getMallNumberAmount() {
        return mallNumberAmount;
    }

    public BigDecimal getOtherDeduct() {
		return otherDeduct;
	}

	public void setOtherDeduct(BigDecimal otherDeduct) {
		this.otherDeduct = otherDeduct;
	}

	/**
     * 
     * {@linkplain #mallNumberAmount}
     * @param mallNumberAmount the value for bill_shop_balance.mall_number_amount
     */
    public void setMallNumberAmount(BigDecimal mallNumberAmount) {
        this.mallNumberAmount = mallNumberAmount;
    }

    /**
     * 
     * {@linkplain #systemSalesAmount}
     *
     * @return the value of bill_shop_balance.system_sales_amount
     */
    public BigDecimal getSystemSalesAmount() {
        return systemSalesAmount;
    }

    /**
     * 
     * {@linkplain #systemSalesAmount}
     * @param systemSalesAmount the value for bill_shop_balance.system_sales_amount
     */
    public void setSystemSalesAmount(BigDecimal systemSalesAmount) {
        this.systemSalesAmount = systemSalesAmount;
    }

    /**
     * 
     * {@linkplain #salesDiffamount}
     *
     * @return the value of bill_shop_balance.sales_diffamount
     */
    public BigDecimal getSalesDiffamount() {
        return salesDiffamount;
    }

    /**
     * 
     * {@linkplain #salesDiffamount}
     * @param salesDiffamount the value for bill_shop_balance.sales_diffamount
     */
    public void setSalesDiffamount(BigDecimal salesDiffamount) {
        this.salesDiffamount = salesDiffamount;
    }

    /**
     * 
     * {@linkplain #mallDeductAmount}
     *
     * @return the value of bill_shop_balance.mall_deduct_amount
     */
    public BigDecimal getMallDeductAmount() {
        return mallDeductAmount;
    }

    /**
     * 
     * {@linkplain #mallDeductAmount}
     * @param mallDeductAmount the value for bill_shop_balance.mall_deduct_amount
     */
    public void setMallDeductAmount(BigDecimal mallDeductAmount) {
        this.mallDeductAmount = mallDeductAmount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     *
     * @return the value of bill_shop_balance.billing_amount
     */
    public BigDecimal getBillingAmount() {
        return billingAmount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     * @param billingAmount the value for bill_shop_balance.billing_amount
     */
    public void setBillingAmount(BigDecimal billingAmount) {
        this.billingAmount = billingAmount;
    }

    /**
     * 
     * {@linkplain #mallBillingAmount}
     *
     * @return the value of bill_shop_balance.mall_billing_amount
     */
    public BigDecimal getMallBillingAmount() {
        return mallBillingAmount;
    }

    /**
     * 
     * {@linkplain #mallBillingAmount}
     * @param mallBillingAmount the value for bill_shop_balance.mall_billing_amount
     */
    public void setMallBillingAmount(BigDecimal mallBillingAmount) {
        this.mallBillingAmount = mallBillingAmount;
    }

    /**
     * 
     * {@linkplain #systemBillingAmount}
     *
     * @return the value of bill_shop_balance.system_billing_amount
     */
    public BigDecimal getSystemBillingAmount() {
        return systemBillingAmount;
    }

    /**
     * 
     * {@linkplain #systemBillingAmount}
     * @param systemBillingAmount the value for bill_shop_balance.system_billing_amount
     */
    public void setSystemBillingAmount(BigDecimal systemBillingAmount) {
        this.systemBillingAmount = systemBillingAmount;
    }

    /**
     * 
     * {@linkplain #noBillingAmount}
     *
     * @return the value of bill_shop_balance.no_billing_amount
     */
    public BigDecimal getNoBillingAmount() {
        return noBillingAmount;
    }

    /**
     * 
     * {@linkplain #noBillingAmount}
     * @param noBillingAmount the value for bill_shop_balance.no_billing_amount
     */
    public void setNoBillingAmount(BigDecimal noBillingAmount) {
        this.noBillingAmount = noBillingAmount;
    }

    /**
     * 
     * {@linkplain #expectCashAmount}
     *
     * @return the value of bill_shop_balance.expect_cash_amount
     */
    public BigDecimal getExpectCashAmount() {
        return expectCashAmount;
    }

    /**
     * 
     * {@linkplain #expectCashAmount}
     * @param expectCashAmount the value for bill_shop_balance.expect_cash_amount
     */
    public void setExpectCashAmount(BigDecimal expectCashAmount) {
        this.expectCashAmount = expectCashAmount;
    }

    /**
     * 
     * {@linkplain #balanceDeductAmount}
     *
     * @return the value of bill_shop_balance.balance_deduct_amount
     */
    public BigDecimal getBalanceDeductAmount() {
        return balanceDeductAmount;
    }

    /**
     * 
     * {@linkplain #balanceDeductAmount}
     * @param balanceDeductAmount the value for bill_shop_balance.balance_deduct_amount
     */
    public void setBalanceDeductAmount(BigDecimal balanceDeductAmount) {
        this.balanceDeductAmount = balanceDeductAmount;
    }

    /**
     * 
     * {@linkplain #balanceDiffAmount}
     *
     * @return the value of bill_shop_balance.balance_diff_amount
     */
    public BigDecimal getBalanceDiffAmount() {
        return balanceDiffAmount;
    }

    /**
     * 
     * {@linkplain #balanceDiffAmount}
     * @param balanceDiffAmount the value for bill_shop_balance.balance_diff_amount
     */
    public void setBalanceDiffAmount(BigDecimal balanceDiffAmount) {
        this.balanceDiffAmount = balanceDiffAmount;
    }

    /**
     * 
     * {@linkplain #otherTotalAmount}
     *
     * @return the value of bill_shop_balance.other_total_amount
     */
    public BigDecimal getOtherTotalAmount() {
        return otherTotalAmount;
    }

    /**
     * 
     * {@linkplain #otherTotalAmount}
     * @param otherTotalAmount the value for bill_shop_balance.other_total_amount
     */
    public void setOtherTotalAmount(BigDecimal otherTotalAmount) {
        this.otherTotalAmount = otherTotalAmount;
    }

    /**
     * 
     * {@linkplain #differenceAmount}
     *
     * @return the value of bill_shop_balance.difference_amount
     */
    public BigDecimal getDifferenceAmount() {
        return differenceAmount;
    }

    /**
     * 
     * {@linkplain #differenceAmount}
     * @param differenceAmount the value for bill_shop_balance.difference_amount
     */
    public void setDifferenceAmount(BigDecimal differenceAmount) {
        this.differenceAmount = differenceAmount;
    }

    /**
     * 
     * {@linkplain #estimateAmount}
     *
     * @return the value of bill_shop_balance.estimate_amount
     */
    public BigDecimal getEstimateAmount() {
        return estimateAmount;
    }

    /**
     * 
     * {@linkplain #estimateAmount}
     * @param estimateAmount the value for bill_shop_balance.estimate_amount
     */
    public void setEstimateAmount(BigDecimal estimateAmount) {
        this.estimateAmount = estimateAmount;
    }

    /**
     * 
     * {@linkplain #paymentAmount}
     *
     * @return the value of bill_shop_balance.payment_amount
     */
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * 
     * {@linkplain #paymentAmount}
     * @param paymentAmount the value for bill_shop_balance.payment_amount
     */
    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * 
     * {@linkplain #nonPaymentAmount}
     *
     * @return the value of bill_shop_balance.non_payment_amount
     */
    public BigDecimal getNonPaymentAmount() {
        return nonPaymentAmount;
    }

    /**
     * 
     * {@linkplain #nonPaymentAmount}
     * @param nonPaymentAmount the value for bill_shop_balance.non_payment_amount
     */
    public void setNonPaymentAmount(BigDecimal nonPaymentAmount) {
        this.nonPaymentAmount = nonPaymentAmount;
    }

    /**
     * 
     * {@linkplain #balanceDiffType}
     *
     * @return the value of bill_shop_balance.balance_diff_type
     */
    public Byte getBalanceDiffType() {
        return balanceDiffType;
    }

    /**
     * 
     * {@linkplain #balanceDiffType}
     * @param balanceDiffType the value for bill_shop_balance.balance_diff_type
     */
    public void setBalanceDiffType(Byte balanceDiffType) {
        this.balanceDiffType = balanceDiffType;
    }

    /**
     * 
     * {@linkplain #balanceDesc}
     *
     * @return the value of bill_shop_balance.balance_desc
     */
    public String getBalanceDesc() {
        return balanceDesc;
    }

    /**
     * 
     * {@linkplain #balanceDesc}
     * @param balanceDesc the value for bill_shop_balance.balance_desc
     */
    public void setBalanceDesc(String balanceDesc) {
        this.balanceDesc = balanceDesc;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_shop_balance.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_shop_balance.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #invoiceApplyNo}
     *
     * @return the value of bill_shop_balance.invoice_apply_no
     */
    public String getInvoiceApplyNo() {
        return invoiceApplyNo;
    }

    /**
     * 
     * {@linkplain #invoiceApplyNo}
     * @param invoiceApplyNo the value for bill_shop_balance.invoice_apply_no
     */
    public void setInvoiceApplyNo(String invoiceApplyNo) {
        this.invoiceApplyNo = invoiceApplyNo;
    }

    /**
     * 
     * {@linkplain #invoiceRegisterNo}
     *
     * @return the value of bill_shop_balance.invoice_register_no
     */
    public String getInvoiceRegisterNo() {
        return invoiceRegisterNo;
    }

    /**
     * 
     * {@linkplain #invoiceRegisterNo}
     * @param invoiceRegisterNo the value for bill_shop_balance.invoice_register_no
     */
    public void setInvoiceRegisterNo(String invoiceRegisterNo) {
        this.invoiceRegisterNo = invoiceRegisterNo;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     *
     * @return the value of bill_shop_balance.invoice_apply_date
     */
    public Date getInvoiceApplyDate() {
        return invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     * @param invoiceApplyDate the value for bill_shop_balance.invoice_apply_date
     */
    public void setInvoiceApplyDate(Date invoiceApplyDate) {
        this.invoiceApplyDate = invoiceApplyDate;
    }

	public String getSourceBalanceNo() {
		return sourceBalanceNo;
	}

	public void setSourceBalanceNo(String sourceBalanceNo) {
		this.sourceBalanceNo = sourceBalanceNo;
	}

	public BigDecimal getConpriceDeductAmount() {
		return conpriceDeductAmount;
	}

	public void setConpriceDeductAmount(BigDecimal conpriceDeductAmount) {
		this.conpriceDeductAmount = conpriceDeductAmount;
	}

	public BigDecimal getPromDeductAmount() {
		return promDeductAmount;
	}

	public void setPromDeductAmount(BigDecimal promDeductAmount) {
		this.promDeductAmount = promDeductAmount;
	}

	public BigDecimal getPromPlusbuckleAmount() {
		return promPlusbuckleAmount;
	}

	public void setPromPlusbuckleAmount(BigDecimal promPlusbuckleAmount) {
		this.promPlusbuckleAmount = promPlusbuckleAmount;
	}

	public BigDecimal getAdjustDeductAmount() {
		return adjustDeductAmount;
	}

	public void setAdjustDeductAmount(BigDecimal adjustDeductAmount) {
		this.adjustDeductAmount = adjustDeductAmount;
	}

	public BigDecimal getAdjustDiffAmount() {
		return adjustDiffAmount;
	}

	public void setAdjustDiffAmount(BigDecimal adjustDiffAmount) {
		this.adjustDiffAmount = adjustDiffAmount;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getZoneYyyymm() {
		return zoneYyyymm;
	}

	public void setZoneYyyymm(String zoneYyyymm) {
		this.zoneYyyymm = zoneYyyymm;
	}

	public BigDecimal getBalanceDeductAfterAmount() {
		return balanceDeductAfterAmount;
	}

	public void setBalanceDeductAfterAmount(BigDecimal balanceDeductAfterAmount) {
		this.balanceDeductAfterAmount = balanceDeductAfterAmount;
	}

	public BigDecimal getConBaseDeductAmount() {
		return conBaseDeductAmount;
	}

	public void setConBaseDeductAmount(BigDecimal conBaseDeductAmount) {
		this.conBaseDeductAmount = conBaseDeductAmount;
	}

	public BigDecimal getConBaseDeduct() {
		return conBaseDeduct;
	}

	public void setConBaseDeduct(BigDecimal conBaseDeduct) {
		this.conBaseDeduct = conBaseDeduct;
	}

	public String getTaxpayerNo() {
		return taxpayerNo;
	}

	public void setTaxpayerNo(String taxpayerNo) {
		this.taxpayerNo = taxpayerNo;
	}

	public String getShowRateExpenseRemind() {
		return showRateExpenseRemind;
	}

	public void setShowRateExpenseRemind(String showRateExpenseRemind) {
		this.showRateExpenseRemind = showRateExpenseRemind;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getPrepaymentAmount() {
		return prepaymentAmount;
	}

	public void setPrepaymentAmount(BigDecimal prepaymentAmount) {
		this.prepaymentAmount = prepaymentAmount;
	}

	public BigDecimal getUsedPrepaymentAmount() {
		return usedPrepaymentAmount;
	}

	public void setUsedPrepaymentAmount(BigDecimal usedPrepaymentAmount) {
		this.usedPrepaymentAmount = usedPrepaymentAmount;
	}

	public int getBalanceStatus() {
		return balanceStatus;
	}

	public void setBalanceStatus(int balanceStatus) {
		this.balanceStatus = balanceStatus;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public BigDecimal getPrepaymentAmountBrand() {
		return prepaymentAmountBrand;
	}

	public void setPrepaymentAmountBrand(BigDecimal prepaymentAmountBrand) {
		this.prepaymentAmountBrand = prepaymentAmountBrand;
	}

	public BigDecimal getUsedPrepaymentAmountBrand() {
		return usedPrepaymentAmountBrand;
	}

	public void setUsedPrepaymentAmountBrand(BigDecimal usedPrepaymentAmountBrand) {
		this.usedPrepaymentAmountBrand = usedPrepaymentAmountBrand;
	}

	public String getIsEqureTrue() {
		return isEqureTrue;
	}

	public void setIsEqureTrue(String isEqureTrue) {
		this.isEqureTrue = isEqureTrue;
		 
		isEqureTrueName = "1".equals(isEqureTrue)?"已平":"未平";
	}

	public String getIsEqureTrueName() { 
		if( isEqureTrueName == null)
			isEqureTrueName = "1".equals(isEqureTrue)?"已平":"未平";
		return isEqureTrueName;
	}

	public void setIsEqureTrueName(String isEqureTrueName) {
		this.isEqureTrueName = isEqureTrueName;
	}

	public BigDecimal getReturnedAmount() {
		return returnedAmount;
	}

	public void setReturnedAmount(BigDecimal returnedAmount) {
		this.returnedAmount = returnedAmount;
	}

	public BigDecimal getNoconpriceDeductAmount() {
		return noconpriceDeductAmount;
	}

	public void setNoconpriceDeductAmount(BigDecimal noconpriceDeductAmount) {
		this.noconpriceDeductAmount = noconpriceDeductAmount;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Date getIsInvoiceDate() {
		return isInvoiceDate;
	}

	public void setIsInvoiceDate(Date isInvoiceDate) {
		this.isInvoiceDate = isInvoiceDate;
	}

	public Date getActualInvoiceDate() {
		return actualInvoiceDate;
	}

	public void setActualInvoiceDate(Date actualInvoiceDate) {
		this.actualInvoiceDate = actualInvoiceDate;
	}

	public int getInvoiceIsOverdue() {
		return invoiceIsOverdue;
	}

	public void setInvoiceIsOverdue(int invoiceIsOverdue) {
		this.invoiceIsOverdue = invoiceIsOverdue;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getInvoiceActualDate() {
		return invoiceActualDate;
	}

	public void setInvoiceActualDate(Date invoiceActualDate) {
		this.invoiceActualDate = invoiceActualDate;
	}

	public int getSendInvoiceOverdue() {
		return sendInvoiceOverdue;
	}

	public void setSendInvoiceOverdue(int sendInvoiceOverdue) {
		this.sendInvoiceOverdue = sendInvoiceOverdue;
	}

	public Date getInvoiceSentDate() {
		return invoiceSentDate;
	}

	public void setInvoiceSentDate(Date invoiceSentDate) {
		this.invoiceSentDate = invoiceSentDate;
	}

	public Date getActualinvDelivDate() {
		return actualinvDelivDate;
	}

	public void setActualinvDelivDate(Date actualinvDelivDate) {
		this.actualinvDelivDate = actualinvDelivDate;
	}

	public int getInvoiceOverdue() {
		return invoiceOverdue;
	}

	public void setInvoiceOverdue(int invoiceOverdue) {
		this.invoiceOverdue = invoiceOverdue;
	}

	public BigDecimal getAmountPayment() {
		return amountPayment;
	}

	public void setAmountPayment(BigDecimal amountPayment) {
		this.amountPayment = amountPayment;
	}

	public BigDecimal getTheAmountPayment() {
		return theAmountPayment;
	}

	public void setTheAmountPayment(BigDecimal theAmountPayment) {
		this.theAmountPayment = theAmountPayment;
	}

	public BigDecimal getReceivableBalance() {
		return receivableBalance;
	}

	public void setReceivableBalance(BigDecimal receivableBalance) {
		this.receivableBalance = receivableBalance;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getThePaymentDate() {
		return thePaymentDate;
	}

	public void setThePaymentDate(Date thePaymentDate) {
		this.thePaymentDate = thePaymentDate;
	}

	public int getOverduePaymentdue() {
		return overduePaymentdue;
	}

	public void setOverduePaymentdue(int overduePaymentdue) {
		this.overduePaymentdue = overduePaymentdue;
	}

	public BigDecimal getReturnDifference() {
		return returnDifference;
	}

	public void setReturnDifference(BigDecimal returnDifference) {
		this.returnDifference = returnDifference;
	}

	public BigDecimal getBillAccount() {
		return billAccount;
	}

	public void setBillAccount(BigDecimal billAccount) {
		this.billAccount = billAccount;
	}

	public BigDecimal getAccountDedReturnDiff() {
		return accountDedReturnDiff;
	}

	public void setAccountDedReturnDiff(BigDecimal accountDedReturnDiff) {
		this.accountDedReturnDiff = accountDedReturnDiff;
	}

	public BigDecimal getTheCurrDiffAmountTotal() {
		return theCurrDiffAmountTotal;
	}

	public void setTheCurrDiffAmountTotal(BigDecimal theCurrDiffAmountTotal) {
		this.theCurrDiffAmountTotal = theCurrDiffAmountTotal;
	}

	public BigDecimal getHasNotPaidBack() {
		return hasNotPaidBack;
	}

	public void setHasNotPaidBack(BigDecimal hasNotPaidBack) {
		this.hasNotPaidBack = hasNotPaidBack;
	}

	public BigDecimal getCumulativeReturn() {
		return cumulativeReturn;
	}

	public void setCumulativeReturn(BigDecimal cumulativeReturn) {
		this.cumulativeReturn = cumulativeReturn;
	}

	public BigDecimal getCumulativeTotalVariance() {
		return cumulativeTotalVariance;
	}

	public void setCumulativeTotalVariance(BigDecimal cumulativeTotalVariance) {
		this.cumulativeTotalVariance = cumulativeTotalVariance;
	}

	public Date getAccountsPayableDay() {
		return accountsPayableDay;
	}

	public void setAccountsPayableDay(Date accountsPayableDay) {
		this.accountsPayableDay = accountsPayableDay;
	}

	public Date getActualPaymentDate() {
		return actualPaymentDate;
	}

	public void setActualPaymentDate(Date actualPaymentDate) {
		this.actualPaymentDate = actualPaymentDate;
	}

	public int getSalesNum() {
		return salesNum;
	}

	public void setSalesNum(int salesNum) {
		this.salesNum = salesNum;
	}

	public BigDecimal getPreAccountChar() {
		return preAccountChar;
	}

	public void setPreAccountChar(BigDecimal preAccountChar) {
		this.preAccountChar = preAccountChar;
	}

	public BigDecimal getInvoiceOpened() {
		return invoiceOpened;
	}

	public void setInvoiceOpened(BigDecimal invoiceOpened) {
		this.invoiceOpened = invoiceOpened;
	}

	public BigDecimal getInvoicedAmount() {
		return invoicedAmount;
	}

	public void setInvoicedAmount(BigDecimal invoicedAmount) {
		this.invoicedAmount = invoicedAmount;
	}

	public BigDecimal getNoInvoicedAmount() {
		return noInvoicedAmount;
	}

	public void setNoInvoicedAmount(BigDecimal noInvoicedAmount) {
		this.noInvoicedAmount = noInvoicedAmount;
	}

	public BigDecimal getNoPaymentAmount() {
		return noPaymentAmount;
	}

	public void setNoPaymentAmount(BigDecimal noPaymentAmount) {
		this.noPaymentAmount = noPaymentAmount;
	}

	public int getCostDifferenceTag() {
		return costDifferenceTag;
	}

	public void setCostDifferenceTag(int costDifferenceTag) {
		this.costDifferenceTag = costDifferenceTag;
	}

	public int getDeductionDiffTag() {
		return deductionDiffTag;
	}

	public void setDeductionDiffTag(int deductionDiffTag) {
		this.deductionDiffTag = deductionDiffTag;
	}

	public BigDecimal getDeductionDiffAmount() {
		return deductionDiffAmount;
	}

	public void setDeductionDiffAmount(BigDecimal deductionDiffAmount) {
		this.deductionDiffAmount = deductionDiffAmount;
	}

	public BigDecimal getPostAccountExpAmount() {
		return postAccountExpAmount;
	}

	public void setPostAccountExpAmount(BigDecimal postAccountExpAmount) {
		this.postAccountExpAmount = postAccountExpAmount;
	}

	public BigDecimal getCostReceivTotal() {
		return costReceivTotal;
	}

	public void setCostReceivTotal(BigDecimal costReceivTotal) {
		this.costReceivTotal = costReceivTotal;
	}

	public BigDecimal getMallCostTotal() {
		return mallCostTotal;
	}

	public void setMallCostTotal(BigDecimal mallCostTotal) {
		this.mallCostTotal = mallCostTotal;
	}

	public BigDecimal getCostDiffAmount() {
		return costDiffAmount;
	}

	public void setCostDiffAmount(BigDecimal costDiffAmount) {
		this.costDiffAmount = costDiffAmount;
	}

	public BigDecimal getBalanceDeduTotalAmount() {
		return balanceDeduTotalAmount;
	}

	public void setBalanceDeduTotalAmount(BigDecimal balanceDeduTotalAmount) {
		this.balanceDeduTotalAmount = balanceDeduTotalAmount;
	}

	public BigDecimal getMallDeductTotal() {
		return mallDeductTotal;
	}

	public void setMallDeductTotal(BigDecimal mallDeductTotal) {
		this.mallDeductTotal = mallDeductTotal;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public BigDecimal getTicketDeductionAmount() {
		return ticketDeductionAmount;
	}

	public void setTicketDeductionAmount(BigDecimal ticketDeductionAmount) {
		this.ticketDeductionAmount = ticketDeductionAmount;
	}

	}