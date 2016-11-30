package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import cn.wonhigh.retail.fas.core.IBill;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2014-09-11 10:09:58
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
public class BillSaleOut extends FasBaseModel implements IBill {
	
	private static final long serialVersionUID = -5046225010616905552L;

	private String billSaleOutId;

    private String billNo;

    private Integer bizType;

    private Integer billType;

    private String refBillNo;

    private Integer refBillType;

    private Integer status;

    private String sysNo;

    private Byte saleType;

    private String customerNo;

    private String orderUnitNo;

    private String saleStoreNo;

    private String saleShopNo;

    private String sendStoreNo;

    private Date saleDate;

    private String merchandiser;

    private String remark;

    public String getBillSaleOutId() {
		return billSaleOutId;
	}

	public void setBillSaleOutId(String billSaleOutId) {
		this.billSaleOutId = billSaleOutId;
	}

	/* (non-Javadoc)
	 * @see cn.wonhigh.retail.fas.common.model.IBill#getBillNo()
	 */
	@Override
	public String getBillNo() {
        return billNo;
    }

    /* (non-Javadoc)
	 * @see cn.wonhigh.retail.fas.common.model.IBill#setBillNo(java.lang.String)
	 */
    @Override
	public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /* (non-Javadoc)
	 * @see cn.wonhigh.retail.fas.common.model.IBill#getBizType()
	 */
    @Override
	public Integer getBizType() {
		return bizType;
	}

	/* (non-Javadoc)
	 * @see cn.wonhigh.retail.fas.common.model.IBill#setBizType(java.lang.Integer)
	 */
	@Override
	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	/* (non-Javadoc)
	 * @see cn.wonhigh.retail.fas.common.model.IBill#getBillType()
	 */
	@Override
	public Integer getBillType() {
        return billType;
    }

    /* (non-Javadoc)
	 * @see cn.wonhigh.retail.fas.common.model.IBill#setBillType(java.lang.Integer)
	 */
    @Override
	public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public String getRefBillNo() {
        return refBillNo;
    }

    public void setRefBillNo(String refBillNo) {
        this.refBillNo = refBillNo;
    }

    public Integer getRefBillType() {
        return refBillType;
    }

    public void setRefBillType(Integer refBillType) {
        this.refBillType = refBillType;
    }

    /* (non-Javadoc)
	 * @see cn.wonhigh.retail.fas.common.model.IBill#getStatus()
	 */
    @Override
	public Integer getStatus() {
		return status;
	}

	/* (non-Javadoc)
	 * @see cn.wonhigh.retail.fas.common.model.IBill#setStatus(java.lang.Integer)
	 */
	@Override
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSysNo() {
        return sysNo;
    }

    public void setSysNo(String sysNo) {
        this.sysNo = sysNo;
    }

    public Byte getSaleType() {
        return saleType;
    }

    public void setSaleType(Byte saleType) {
        this.saleType = saleType;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getOrderUnitNo() {
        return orderUnitNo;
    }

    public void setOrderUnitNo(String orderUnitNo) {
        this.orderUnitNo = orderUnitNo;
    }

    public String getSaleStoreNo() {
        return saleStoreNo;
    }

    public void setSaleStoreNo(String saleStoreNo) {
        this.saleStoreNo = saleStoreNo;
    }

    public String getSaleShopNo() {
        return saleShopNo;
    }

    public void setSaleShopNo(String saleShopNo) {
        this.saleShopNo = saleShopNo;
    }

    public String getSendStoreNo() {
        return sendStoreNo;
    }

    public void setSendStoreNo(String sendStoreNo) {
        this.sendStoreNo = sendStoreNo;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getMerchandiser() {
        return merchandiser;
    }

    public void setMerchandiser(String merchandiser) {
        this.merchandiser = merchandiser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}