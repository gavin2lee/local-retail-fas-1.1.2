package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-29 13:20:36
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
public class BillSplitLog extends FasBaseModel {
  
	private static final long serialVersionUID = 4751952439580740356L;

    /**
     * 原单据编码
     */
    private String refBillNo;

    /**
     * 单据类型,Kinds1 入库单类型:0直接、1合同、2订货、3补货、4返修入库、5退厂
     */
    private Integer billType;
    
    /**
     * 单据类型名称
     */
    private String billTypeName;
    
    /**
     * 发货时间
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date sendOutDate;

    /**
     * 状态标志(0：成功， 1：失败)
     */
    private Integer processStatus;
    
    /**
     * 状态标志名称
     */
    private String processStatusName;

    /**
     * 拆单时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
    private Date splitTime;

    /**
     * 失败原因
     */
    private String failureReason;
    
    /** 品牌编码 */
    private String brandNo;
    
    private String organTypeNo;
    
    public String getBrandNo() {
		return brandNo;
	}

	public String getBillTypeName() {
		for(BillTypeEnums s : BillTypeEnums.values()) {
			if(getBillType() != null && 
					s.getRequestId().intValue() == getBillType().intValue()) {
				return s.getDesc();
			}
		}
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getProcessStatusName() {
		if(getProcessStatus() != null && getProcessStatus().intValue() == 0) {
			return "成功";
		}
		return "失败";
	}

	public void setProcessStatusName(String processStatusName) {
		this.processStatusName = processStatusName;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	/**
     * 
     * {@linkplain #refBillNo}
     *
     * @return the value of bill_split_log.ref_bill_no
     */
    public String getRefBillNo() {
        return refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     * @param refBillNo the value for bill_split_log.ref_bill_no
     */
    public void setRefBillNo(String refBillNo) {
        this.refBillNo = refBillNo;
    }

    public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public Integer getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	/**
     * 
     * {@linkplain #splitTime}
     *
     * @return the value of bill_split_log.split_time
     */
    public Date getSplitTime() {
        return splitTime;
    }

    /**
     * 
     * {@linkplain #splitTime}
     * @param splitTime the value for bill_split_log.split_time
     */
    public void setSplitTime(Date splitTime) {
        this.splitTime = splitTime;
    }

    /**
     * 
     * {@linkplain #failureReason}
     *
     * @return the value of bill_split_log.failure_reason
     */
    public String getFailureReason() {
        return failureReason;
    }

    /**
     * 
     * {@linkplain #failureReason}
     * @param failureReason the value for bill_split_log.failure_reason
     */
    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

	public Date getSendOutDate() {
		return sendOutDate;
	}

	public void setSendOutDate(Date sendOutDate) {
		this.sendOutDate = sendOutDate;
	}

	public String getOrganTypeNo() {
		return organTypeNo;
	}

	public void setOrganTypeNo(String organTypeNo) {
		this.organTypeNo = organTypeNo;
	}
}