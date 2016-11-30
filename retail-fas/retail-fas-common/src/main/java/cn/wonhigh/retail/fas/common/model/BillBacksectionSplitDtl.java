package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
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
 * @date  2015-02-27 10:37:34
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
public class BillBacksectionSplitDtl  extends  FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 回款明细编号
     */
    private String backsectionDtlNo;

    /**
     * 回款编号
     */
    private String backsectionNo;
    
    /**
     * 回款方编码
     */
    private String backNo;

    /**
     * 回款方名称
     */
    private String backName;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺简称
     */
    private String shortName;

    /**
     * 结算月份
     */
    private String month;

    /**
     * 结算单编号
     */
    private String balanceNo;

    /**
     * 开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal billingAmount;

    /**
     * 票后帐扣费用
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal ticketDeductionAmount;

    /**
     * 应回款金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal receiveAmount;

    /**
     * 已回款金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal alreadyReceiveAmount;

    /**
     * 未回款金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal notReceiveAmount;

    /**
     * 本次回款金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal thePaymentAmount;

    /**
     * 回款日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date backDate;

    /**
     * 回款差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffAmount;

    /**
     * 差异原因
     */
    private String diffReason;

    /**
     * 商场编码
     */
    private String mallNo;

    /**
     * 商场名称
     */
    private String mallName;

    /**
     * 商业集团编码
     */
    private String bsgroupsNo;

    /**
     * 商业集团名称
     */
    private String bsgroupsName;

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
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 分库字段
     */
    private String shardingFlag;
    
    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌中文名称
     */
    private String brandName;
    
    /**
     * 销售收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal systemSalesAmount;
    
    /**
     * 银行回款金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal bankBackAmount;
    
    /**
     * 银行回款日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date bankBackDate;
    
    /**
     * 开票申请日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceApplyDate;
    
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
   
    public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	/**
     * 
     * {@linkplain #backsectionDtlNo}
     *
     * @return the value of bill_backsection_split_dtl.backsection_dtl_no
     */
    public String getBacksectionDtlNo() {
        return backsectionDtlNo;
    }

    /**
     * 
     * {@linkplain #backsectionDtlNo}
     * @param backsectionDtlNo the value for bill_backsection_split_dtl.backsection_dtl_no
     */
    public void setBacksectionDtlNo(String backsectionDtlNo) {
        this.backsectionDtlNo = backsectionDtlNo;
    }

    /**
     * 
     * {@linkplain #backsectionNo}
     *
     * @return the value of bill_backsection_split_dtl.backsection_no
     */
    public String getBacksectionNo() {
        return backsectionNo;
    }

    /**
     * 
     * {@linkplain #backsectionNo}
     * @param backsectionNo the value for bill_backsection_split_dtl.backsection_no
     */
    public void setBacksectionNo(String backsectionNo) {
        this.backsectionNo = backsectionNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_backsection_split_dtl.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_backsection_split_dtl.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_backsection_split_dtl.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_backsection_split_dtl.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of bill_backsection_split_dtl.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for bill_backsection_split_dtl.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_backsection_split_dtl.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_backsection_split_dtl.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     *
     * @return the value of bill_backsection_split_dtl.billing_amount
     */
    public BigDecimal getBillingAmount() {
        return billingAmount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     * @param billingAmount the value for bill_backsection_split_dtl.billing_amount
     */
    public void setBillingAmount(BigDecimal billingAmount) {
        this.billingAmount = billingAmount;
    }

    /**
     * 
     * {@linkplain #ticketDeductionAmount}
     *
     * @return the value of bill_backsection_split_dtl.ticket_deduction_amount
     */
    public BigDecimal getTicketDeductionAmount() {
        return ticketDeductionAmount;
    }

    /**
     * 
     * {@linkplain #ticketDeductionAmount}
     * @param ticketDeductionAmount the value for bill_backsection_split_dtl.ticket_deduction_amount
     */
    public void setTicketDeductionAmount(BigDecimal ticketDeductionAmount) {
        this.ticketDeductionAmount = ticketDeductionAmount;
    }

    /**
     * 
     * {@linkplain #receiveAmount}
     *
     * @return the value of bill_backsection_split_dtl.receive_amount
     */
    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    /**
     * 
     * {@linkplain #receiveAmount}
     * @param receiveAmount the value for bill_backsection_split_dtl.receive_amount
     */
    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    /**
     * 
     * {@linkplain #alreadyReceiveAmount}
     *
     * @return the value of bill_backsection_split_dtl.already_receive_amount
     */
    public BigDecimal getAlreadyReceiveAmount() {
        return alreadyReceiveAmount;
    }

    /**
     * 
     * {@linkplain #alreadyReceiveAmount}
     * @param alreadyReceiveAmount the value for bill_backsection_split_dtl.already_receive_amount
     */
    public void setAlreadyReceiveAmount(BigDecimal alreadyReceiveAmount) {
        this.alreadyReceiveAmount = alreadyReceiveAmount;
    }

    /**
     * 
     * {@linkplain #notReceiveAmount}
     *
     * @return the value of bill_backsection_split_dtl.not_receive_amount
     */
    public BigDecimal getNotReceiveAmount() {
        return notReceiveAmount;
    }

    /**
     * 
     * {@linkplain #notReceiveAmount}
     * @param notReceiveAmount the value for bill_backsection_split_dtl.not_receive_amount
     */
    public void setNotReceiveAmount(BigDecimal notReceiveAmount) {
        this.notReceiveAmount = notReceiveAmount;
    }

    /**
     * 
     * {@linkplain #thePaymentAmount}
     *
     * @return the value of bill_backsection_split_dtl.the_payment_amount
     */
    public BigDecimal getThePaymentAmount() {
        return thePaymentAmount;
    }

    /**
     * 
     * {@linkplain #thePaymentAmount}
     * @param thePaymentAmount the value for bill_backsection_split_dtl.the_payment_amount
     */
    public void setThePaymentAmount(BigDecimal thePaymentAmount) {
        this.thePaymentAmount = thePaymentAmount;
    }

    /**
     * 
     * {@linkplain #backDate}
     *
     * @return the value of bill_backsection_split_dtl.back_date
     */
    public Date getBackDate() {
        return backDate;
    }

    /**
     * 
     * {@linkplain #backDate}
     * @param backDate the value for bill_backsection_split_dtl.back_date
     */
    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    /**
     * 
     * {@linkplain #diffAmount}
     *
     * @return the value of bill_backsection_split_dtl.diff_amount
     */
    public BigDecimal getDiffAmount() {
        return diffAmount;
    }

    /**
     * 
     * {@linkplain #diffAmount}
     * @param diffAmount the value for bill_backsection_split_dtl.diff_amount
     */
    public void setDiffAmount(BigDecimal diffAmount) {
        this.diffAmount = diffAmount;
    }

    /**
     * 
     * {@linkplain #diffReason}
     *
     * @return the value of bill_backsection_split_dtl.diff_reason
     */
    public String getDiffReason() {
        return diffReason;
    }

    /**
     * 
     * {@linkplain #diffReason}
     * @param diffReason the value for bill_backsection_split_dtl.diff_reason
     */
    public void setDiffReason(String diffReason) {
        this.diffReason = diffReason;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of bill_backsection_split_dtl.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for bill_backsection_split_dtl.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of bill_backsection_split_dtl.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for bill_backsection_split_dtl.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     *
     * @return the value of bill_backsection_split_dtl.bsgroups_no
     */
    public String getBsgroupsNo() {
        return bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     * @param bsgroupsNo the value for bill_backsection_split_dtl.bsgroups_no
     */
    public void setBsgroupsNo(String bsgroupsNo) {
        this.bsgroupsNo = bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     *
     * @return the value of bill_backsection_split_dtl.bsgroups_name
     */
    public String getBsgroupsName() {
        return bsgroupsName;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     * @param bsgroupsName the value for bill_backsection_split_dtl.bsgroups_name
     */
    public void setBsgroupsName(String bsgroupsName) {
        this.bsgroupsName = bsgroupsName;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of bill_backsection_split_dtl.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for bill_backsection_split_dtl.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneName}
     *
     * @return the value of bill_backsection_split_dtl.zone_name
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * {@linkplain #zoneName}
     * @param zoneName the value for bill_backsection_split_dtl.zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of bill_backsection_split_dtl.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for bill_backsection_split_dtl.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of bill_backsection_split_dtl.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for bill_backsection_split_dtl.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_backsection_split_dtl.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_backsection_split_dtl.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_backsection_split_dtl.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_backsection_split_dtl.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_backsection_split_dtl.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_backsection_split_dtl.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getBackNo() {
		return backNo;
	}

	public void setBackNo(String backNo) {
		this.backNo = backNo;
	}

	public String getBackName() {
		return backName;
	}

	public void setBackName(String backName) {
		this.backName = backName;
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

	public BigDecimal getSystemSalesAmount() {
		return systemSalesAmount;
	}

	public void setSystemSalesAmount(BigDecimal systemSalesAmount) {
		this.systemSalesAmount = systemSalesAmount;
	}

	public BigDecimal getBankBackAmount() {
		return bankBackAmount;
	}

	public void setBankBackAmount(BigDecimal bankBackAmount) {
		this.bankBackAmount = bankBackAmount;
	}

	public Date getBankBackDate() {
		return bankBackDate;
	}

	public void setBankBackDate(Date bankBackDate) {
		this.bankBackDate = bankBackDate;
	}

	public Date getInvoiceApplyDate() {
		return invoiceApplyDate;
	}

	public void setInvoiceApplyDate(Date invoiceApplyDate) {
		this.invoiceApplyDate = invoiceApplyDate;
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
}