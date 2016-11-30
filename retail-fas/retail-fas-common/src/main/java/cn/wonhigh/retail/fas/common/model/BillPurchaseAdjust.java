package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BillAdjustStatusEnum;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-21 14:38:45
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
public class BillPurchaseAdjust   extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private String id;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
     * 发方公司编号
     */
    private String salerNo;

    /**
     * 发方公司名称
     */
    private String salerName;
    /**
     * 收方公司编号
     */
    private String buyerNo;

    /**
     * 收方公司名称
     */
    private String buyerName;
    
    /**
     * 供应商编号
     */
    private String supplierNo;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date purchaseDate;


    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 状态名称
     */
    private String statusStr;
    /**
     * 结算单号
     */
    private String balanceNo;
    
    /**
     * 地区结算单号
     */
    private String areaBalanceNo;
    
    /**
     * 到期日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date dueDate;
    /**
     * 总金额
     */
    private BigDecimal allAmount;
    /**
     * 未付款金额
     */
    private BigDecimal noPayAmount;
    /**
     * 已付款金额
     */
    private BigDecimal payAmount;
    /**
     * 付款状态
     */
    private Integer payStatus;
    
    private String idList;
    
	public String getAreaBalanceNo() {
		return areaBalanceNo;
	}

	public void setAreaBalanceNo(String areaBalanceNo) {
		this.areaBalanceNo = areaBalanceNo;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}

	public BigDecimal getNoPayAmount() {
		return noPayAmount;
	}

	public void setNoPayAmount(BigDecimal noPayAmount) {
		this.noPayAmount = noPayAmount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
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
		for(BillAdjustStatusEnum s : BillAdjustStatusEnum.values()) {
			if(status!=null && s.getValue().intValue() == status.intValue()) {
				this.statusStr = s.getText();
			}
		}
		this.status = status;
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

	/**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of bill_purchase_adjust.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for bill_purchase_adjust.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of bill_purchase_adjust.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 
     * {@linkplain #supplierName}
     * @param supplierName the value for bill_purchase_adjust.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 
     * {@linkplain #purchaseDate}
     *
     * @return the value of bill_purchase_adjust.purchase_date
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * 
     * {@linkplain #purchaseDate}
     * @param purchaseDate the value for bill_purchase_adjust.purchase_date
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }


    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_purchase_adjust.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_purchase_adjust.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}