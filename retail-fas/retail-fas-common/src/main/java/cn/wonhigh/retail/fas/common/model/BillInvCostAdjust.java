package cn.wonhigh.retail.fas.common.model;

import cn.wonhigh.retail.fas.common.enums.BillInvCostAdjustStatusEnum;


/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-31 16:10:13
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class BillInvCostAdjust extends FasBaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9190700703519227575L;

	/**
     * 单据编码
     */
    private String billNo;

    /**
     * 单据类型
     */
    private Integer billType;

    /**
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 年份
     */
    private String year;

    /**
     * 月份
     */
    private String month;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 状态名称
     */
    private String statusStr;

    /**
     * 备注
     */
    private String remark;

    /**
     * 分库字段：本部+大区
     */
    private String shardingFlag;

    /**
     * 明细数量总量
     */
    private Long stockDetailTotal;

    /**
     * 分库字段：本部+大区
     */
    private String zoneYyyymm;

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_inv_cost_adjust.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_inv_cost_adjust.bill_no
     */
    public void setBillNo(String billNo) {
    	super.setBillNo(billNo);
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #billType}
     *
     * @return the value of bill_inv_cost_adjust.bill_type
     */
    public Integer getBillType() {
        return billType;
    }

    /**
     * 
     * {@linkplain #billType}
     * @param billType the value for bill_inv_cost_adjust.bill_type
     */
    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_inv_cost_adjust.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_inv_cost_adjust.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_inv_cost_adjust.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_inv_cost_adjust.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #year}
     *
     * @return the value of bill_inv_cost_adjust.year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * {@linkplain #year}
     * @param year the value for bill_inv_cost_adjust.year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of bill_inv_cost_adjust.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for bill_inv_cost_adjust.month
     */
    public void setMonth(String month) {
        this.month = month;
    }


    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_inv_cost_adjust.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_inv_cost_adjust.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     *
     * @return the value of bill_inv_cost_adjust.sharding_flag
     */
    public String getShardingFlag() {
        return shardingFlag;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     * @param shardingFlag the value for bill_inv_cost_adjust.sharding_flag
     */
    public void setShardingFlag(String shardingFlag) {
        this.shardingFlag = shardingFlag;
    }

    /**
     * 
     * {@linkplain #stockDetailTotal}
     *
     * @return the value of bill_inv_cost_adjust.stock_detail_total
     */
    public Long getStockDetailTotal() {
        return stockDetailTotal;
    }

    /**
     * 
     * {@linkplain #stockDetailTotal}
     * @param stockDetailTotal the value for bill_inv_cost_adjust.stock_detail_total
     */
    public void setStockDetailTotal(Long stockDetailTotal) {
        this.stockDetailTotal = stockDetailTotal;
    }

    /**
     * 
     * {@linkplain #zoneYyyymm}
     *
     * @return the value of bill_inv_cost_adjust.zone_yyyymm
     */
    public String getZoneYyyymm() {
        return zoneYyyymm;
    }

    /**
     * 
     * {@linkplain #zoneYyyymm}
     * @param zoneYyyymm the value for bill_inv_cost_adjust.zone_yyyymm
     */
    public void setZoneYyyymm(String zoneYyyymm) {
        this.zoneYyyymm = zoneYyyymm;
    }

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		for(BillInvCostAdjustStatusEnum s : BillInvCostAdjustStatusEnum.values()) {
			if(status!=null && s.getValue().intValue() == status.intValue()) {
				this.statusStr = s.getText();
			}
		}
		this.status = status;
	}
}