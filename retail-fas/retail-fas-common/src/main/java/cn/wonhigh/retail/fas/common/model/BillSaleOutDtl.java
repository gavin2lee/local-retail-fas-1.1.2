package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

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
public class BillSaleOutDtl extends FasBaseModel {
	
	private static final long serialVersionUID = 6075941913197635943L;

	private String billSaleOutDtlId;

    private String billNo;

    private Integer seqId;

    private String skuNo;

    private String itemNo;

    private BigDecimal cost;

    private String brandNo;

    private String sizeNo;

    private Integer discount;

    private Integer askQty;

    private Integer sendOutQty;

    private BigDecimal quotePrice;

    private String boxNo;

    private BigDecimal salePrice;

    private String handleType;

    private String remark;
    
    private String balanceNo;
    
    public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}

	public String getBillSaleOutDtlId() {
		return billSaleOutDtlId;
	}

	public void setBillSaleOutDtlId(String billSaleOutDtlId) {
		this.billSaleOutDtlId = billSaleOutDtlId;
	}

	public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    public String getSizeNo() {
        return sizeNo;
    }

    public void setSizeNo(String sizeNo) {
        this.sizeNo = sizeNo;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getAskQty() {
        return askQty;
    }

    public void setAskQty(Integer askQty) {
        this.askQty = askQty;
    }

    public Integer getSendOutQty() {
        return sendOutQty;
    }

    public void setSendOutQty(Integer sendOutQty) {
        this.sendOutQty = sendOutQty;
    }

    public BigDecimal getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(BigDecimal quotePrice) {
        this.quotePrice = quotePrice;
    }

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getHandleType() {
        return handleType;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}