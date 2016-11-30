package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;


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
public class BillSplitDtl implements Serializable {

	private static final long serialVersionUID = -5925424434462018342L;

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
     * 单据编码
     */
    private String billNo;

    /**
     * 原单据编码
     */
    private String refBillNo;

    /**
     * 单据类型,Kinds1 入库单类型:0直接、1合同、2订货、3补货、4返修入库、5退厂
     */
    private Integer billType;

    /**
     * 货品编码
     */
    private String itemNo;
    
	/**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 卖方编码
     */
    private String salerNo;

    /**
     * 买方编码
     */
    private String buyerNo;

    /**
     * 单据标志(0：应收， 1：应付)
     */
    private Integer billFlag;

    /**
     * 发货数量
     */
    private Integer sendOutQty;

    /**
     * 发货日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date sendOutDate;

    /**
     * 含税价格
     */
    private BigDecimal cost;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 不含税价格
     */
    private BigDecimal exclusiveCost;
    
    /**
     * 结算地
     */
	private String storeNo;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 结算单号
	 */
	private String balanceNo;

    /**
     * 货品名称
     */
    private String itemName;
    
    /**
     * 货品类别
     */
    private String categoryNo;
    
    /**
     * 货品类别
     */
    private String categoryName;
    
    /**
     * 入库金额
     */  
	private BigDecimal entryAmount;
	
    /**
     * 退残金额
     */
	private BigDecimal returnAmount;
	
    /**
     * 均价
     */
	private BigDecimal sendOutCost;
	
    /**
     * 合计金额
     */
	private BigDecimal sendOutAmount;

    /**
     * 单据类型名称
     */
	private String billTypeName;
    
    /**
     * 卖方名称
     */
	private String salerName;
	
    /**
     * 买方名称
     */
	private String buyerName;
	
    /**
     * 品牌名称
     */
    private String brandName;
    
    /**
     * 异常类型
     */
    private int exceptionType;
    
    /**
     * 异常类型名称
     */
    private String exceptionName;
    
    /**
     * 供应商编号
     */
    private String supplierNo;
    
    /**
     * 供应商名称
     */
    private String supplierName;
    
    /**
     * 厂商价
     */
    private BigDecimal supplierCost;
    
    /**
     * 厂商组
     */
    private String supplierGroupNo;
    
    /**
     * 结算路径编码
     */
    private String pathNo;
    
	public String getPathNo() {
		return pathNo;
	}

	public void setPathNo(String pathNo) {
		this.pathNo = pathNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public BigDecimal getSupplierCost() {
		return supplierCost;
	}

	public void setSupplierCost(BigDecimal supplierCost) {
		this.supplierCost = supplierCost;
	}

	public String getSupplierGroupNo() {
		return supplierGroupNo;
	}

	public void setSupplierGroupNo(String supplierGroupNo) {
		this.supplierGroupNo = supplierGroupNo;
	}

	public int getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(int exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBillTypeName() {
		if(null !=billType){
			return BillTypeEnums.getNameByNo(billType);
		}
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	
    public BigDecimal getSendOutAmount() {
		return sendOutAmount;
	}

	public void setSendOutAmount(BigDecimal sendOutAmount) {
		this.sendOutAmount = sendOutAmount;
	}

	public BigDecimal getSendOutCost() {
		return sendOutCost;
	}

	public void setSendOutCost(BigDecimal sendOutCost) {
		this.sendOutCost = sendOutCost;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public BigDecimal getEntryAmount() {
		return entryAmount;
	}

	public void setEntryAmount(BigDecimal entryAmount) {
		this.entryAmount = entryAmount;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_split_dtl.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_split_dtl.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     *
     * @return the value of bill_split_dtl.ref_bill_no
     */
    public String getRefBillNo() {
        return refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     * @param refBillNo the value for bill_split_dtl.ref_bill_no
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

	/**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of bill_split_dtl.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for bill_split_dtl.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_split_dtl.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_split_dtl.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of bill_split_dtl.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for bill_split_dtl.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of bill_split_dtl.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for bill_split_dtl.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #billFlag}
     *
     * @return the value of bill_split_dtl.bill_flag
     */
    public Integer getBillFlag() {
        return billFlag;
    }

    /**
     * 
     * {@linkplain #billFlag}
     * @param billFlag the value for bill_split_dtl.bill_flag
     */
    public void setBillFlag(Integer billFlag) {
        this.billFlag = billFlag;
    }

    /**
     * 
     * {@linkplain #sendOutQty}
     *
     * @return the value of bill_split_dtl.send_out_qty
     */
    public Integer getSendOutQty() {
        return sendOutQty;
    }

    /**
     * 
     * {@linkplain #sendOutQty}
     * @param sendOutQty the value for bill_split_dtl.send_out_qty
     */
    public void setSendOutQty(Integer sendOutQty) {
        this.sendOutQty = sendOutQty;
    }

    /**
     * 
     * {@linkplain #sendOutDate}
     *
     * @return the value of bill_split_dtl.send_out_date
     */
    public Date getSendOutDate() {
        return sendOutDate;
    }

    /**
     * 
     * {@linkplain #sendOutDate}
     * @param sendOutDate the value for bill_split_dtl.send_out_date
     */
    public void setSendOutDate(Date sendOutDate) {
        this.sendOutDate = sendOutDate;
    }

    public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	/**
     * 
     * {@linkplain #taxRate}
     *
     * @return the value of bill_split_dtl.tax_rate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * 
     * {@linkplain #taxRate}
     * @param taxRate the value for bill_split_dtl.tax_rate
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * 
     * {@linkplain #exclusiveCost}
     *
     * @return the value of bill_split_dtl.exclusive_cost
     */
    public BigDecimal getExclusiveCost() {
        return exclusiveCost;
    }

    /**
     * 
     * {@linkplain #exclusiveCost}
     * @param exclusiveCost the value for bill_split_dtl.exclusive_cost
     */
    public void setExclusiveCost(BigDecimal exclusiveCost) {
        this.exclusiveCost = exclusiveCost;
    }
    
	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyerNo == null) ? 0 : buyerNo.hashCode());
		result = prime * result
				+ ((refBillNo == null) ? 0 : refBillNo.hashCode());
		result = prime * result + ((salerNo == null) ? 0 : salerNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillSplitDtl other = (BillSplitDtl) obj;
		if (buyerNo == null) {
			if (other.buyerNo != null)
				return false;
		} else if (!buyerNo.equals(other.buyerNo))
			return false;
		if (refBillNo == null) {
			if (other.refBillNo != null)
				return false;
		} else if (!refBillNo.equals(other.refBillNo))
			return false;
		if (salerNo == null) {
			if (other.salerNo != null)
				return false;
		} else if (!salerNo.equals(other.salerNo))
			return false;
		return true;
	}
}