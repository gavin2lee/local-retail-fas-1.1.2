package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ShopBalanceDateExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-12-26 10:35:56
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
@ExportFormat(className=ShopBalanceDateExportFormat.class)
public class ShopBalanceDate extends FasBaseModel implements Comparable<ShopBalanceDate>{
	
	private static final long serialVersionUID = -1407105888431948562L;

    /**
     * 结算主体编号
     */
    private String companyNo;

    /**
     * 结算主体名称
     */
    private String companyName;

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
     * 店铺全称
     */
    private String fullName;

    /**
     * (销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)
     */
    private String retailType;

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
     * 否生成结算单(1-未生成，2-已生成)
     */
    private Byte balanceFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否已开票(1-未开票,2-已开票)
     */
    private Byte billalready;

    /**
     * 应开票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date shouldBillDate;

    /**
     * 应回款日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date incomePaymentsDate;
    
    /**
     * 发票应寄送日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceShouldSendDate;
    
    /**
     * 发票应到日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceShouldArraiveDate;

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
	
	/**
	 * 扩展字段
	 */
    private Date minOutDate; 
	
    private Date maxOutDate; 
    
    private Integer partDays;
    
    private Integer totalDays;
    
    private Byte deductStatus;
    
	public Date getMinOutDate() {
		return minOutDate;
	}

	public void setMinOutDate(Date minOutDate) {
		this.minOutDate = minOutDate;
	}

	public Date getMaxOutDate() {
		return maxOutDate;
	}

	public void setMaxOutDate(Date maxOutDate) {
		this.maxOutDate = maxOutDate;
	}

	public Integer getPartDays() {
		return partDays;
	}

	public void setPartDays(Integer partDays) {
		this.partDays = partDays;
	}

	public Integer getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}

	/**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of shop_balance_date.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for shop_balance_date.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
        /*setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));*/
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of shop_balance_date.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for shop_balance_date.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     *
     * @return the value of shop_balance_date.bsgroups_no
     */
    public String getBsgroupsNo() {
        return bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     * @param bsgroupsNo the value for shop_balance_date.bsgroups_no
     */
    public void setBsgroupsNo(String bsgroupsNo) {
        this.bsgroupsNo = bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     *
     * @return the value of shop_balance_date.bsgroups_name
     */
    public String getBsgroupsName() {
        return bsgroupsName;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     * @param bsgroupsName the value for shop_balance_date.bsgroups_name
     */
    public void setBsgroupsName(String bsgroupsName) {
        this.bsgroupsName = bsgroupsName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of shop_balance_date.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for shop_balance_date.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of shop_balance_date.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for shop_balance_date.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of shop_balance_date.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for shop_balance_date.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of shop_balance_date.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for shop_balance_date.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of shop_balance_date.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for shop_balance_date.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #retailType}
     *
     * @return the value of shop_balance_date.retail_type
     */
    public String getRetailType() {
        return retailType;
    }

    /**
     * 
     * {@linkplain #retailType}
     * @param retailType the value for shop_balance_date.retail_type
     */
    public void setRetailType(String retailType) {
        this.retailType = retailType;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of shop_balance_date.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for shop_balance_date.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     *
     * @return the value of shop_balance_date.balance_start_date
     */
    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     * @param balanceStartDate the value for shop_balance_date.balance_start_date
     */
    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     *
     * @return the value of shop_balance_date.balance_end_date
     */
    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     * @param balanceEndDate the value for shop_balance_date.balance_end_date
     */
    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceFlag}
     *
     * @return the value of shop_balance_date.balance_flag
     */
    public Byte getBalanceFlag() {
        return balanceFlag;
    }

    /**
     * 
     * {@linkplain #balanceFlag}
     * @param balanceFlag the value for shop_balance_date.balance_flag
     */
    public void setBalanceFlag(Byte balanceFlag) {
        this.balanceFlag = balanceFlag;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of shop_balance_date.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for shop_balance_date.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #billalready}
     *
     * @return the value of shop_balance_date.billAlready
     */
    public Byte getBillalready() {
        return billalready;
    }

    /**
     * 
     * {@linkplain #billalready}
     * @param billalready the value for shop_balance_date.billAlready
     */
    public void setBillalready(Byte billalready) {
        this.billalready = billalready;
    }

    /**
     * 
     * {@linkplain #shouldBillDate}
     *
     * @return the value of shop_balance_date.should_bill_date
     */
    public Date getShouldBillDate() {
        return shouldBillDate;
    }

    /**
     * 
     * {@linkplain #shouldBillDate}
     * @param shouldBillDate the value for shop_balance_date.should_bill_date
     */
    public void setShouldBillDate(Date shouldBillDate) {
        this.shouldBillDate = shouldBillDate;
    }

    /**
     * 
     * {@linkplain #incomePaymentsDate}
     *
     * @return the value of shop_balance_date.Income_payments_date
     */
    public Date getIncomePaymentsDate() {
        return incomePaymentsDate;
    }

    /**
     * 
     * {@linkplain #incomePaymentsDate}
     * @param incomePaymentsDate the value for shop_balance_date.Income_payments_date
     */
    public void setIncomePaymentsDate(Date incomePaymentsDate) {
        this.incomePaymentsDate = incomePaymentsDate;
    }

	public Date getInvoiceShouldSendDate() {
		return invoiceShouldSendDate;
	}

	public Date getInvoiceShouldArraiveDate() {
		return invoiceShouldArraiveDate;
	}

	public void setInvoiceShouldSendDate(Date invoiceShouldSendDate) {
		this.invoiceShouldSendDate = invoiceShouldSendDate;
	}

	public void setInvoiceShouldArraiveDate(Date invoiceShouldArraiveDate) {
		this.invoiceShouldArraiveDate = invoiceShouldArraiveDate;
	}

	@Override
	public int compareTo(ShopBalanceDate o) {
		// TODO Auto-generated method stub
		return this.getBalanceStartDate().compareTo(o.getBalanceStartDate());
	}

	@Override
	public String toString() {
		return "ShopBalanceDate [companyNo=" + companyNo + ", companyName="
				+ companyName + ", bsgroupsNo=" + bsgroupsNo
				+ ", bsgroupsName=" + bsgroupsName + ", mallNo=" + mallNo
				+ ", mallName=" + mallName + ", shopNo=" + shopNo
				+ ", shortName=" + shortName + ", fullName=" + fullName
				+ ", retailType=" + retailType + ", month=" + month
				+ ", balanceStartDate=" + balanceStartDate
				+ ", balanceEndDate=" + balanceEndDate + ", balanceFlag="
				+ balanceFlag + ", remark=" + remark + ", billalready="
				+ billalready + ", shouldBillDate=" + shouldBillDate
				+ ", incomePaymentsDate=" + incomePaymentsDate
				+ ", invoiceShouldSendDate=" + invoiceShouldSendDate
				+ ", invoiceShouldArraiveDate=" + invoiceShouldArraiveDate
				+ "]";
	}

	public Byte getDeductStatus() {
		return deductStatus;
	}

	public void setDeductStatus(Byte deductStatus) {
		this.deductStatus = deductStatus;
	}
	
}