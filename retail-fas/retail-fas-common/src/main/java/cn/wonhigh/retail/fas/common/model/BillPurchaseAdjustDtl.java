package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

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
public class BillPurchaseAdjustDtl extends FasBaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8535427385381264469L;
	
	private String id;
	
	/**
     * 商品编号
     */
    private String itemNo;

    /**
     * 商品编码
     */
    private String itemCode;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 品牌编号
     */
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 类别名称
     */
    private String categoryName;
    
    /**
     * 品牌部编号
     */
    private String brandUnitNo;

    /**
     * 品牌部名称
     */
    private String brandUnitName;

    /**
     * 厂商额
     */
    private BigDecimal supplierAmount;
    
    /**
     * 中间额
     */
    private BigDecimal referAmount;
    
    
    /**
     * 地区额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 结算单号
     */
    private String balanceNo;

    /**
     * 地区结算单号
     */
    private String areaBalanceNo;

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

	public BigDecimal getSupplierAmount() {
		return supplierAmount;
	}

	public void setSupplierAmount(BigDecimal supplierAmount) {
		this.supplierAmount = supplierAmount;
	}

	public BigDecimal getReferAmount() {
		return referAmount;
	}

	public void setReferAmount(BigDecimal referAmount) {
		this.referAmount = referAmount;
	}

	public String getAreaBalanceNo() {
		return areaBalanceNo;
	}

	public void setAreaBalanceNo(String areaBalanceNo) {
		this.areaBalanceNo = areaBalanceNo;
	}

	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
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

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of bill_purchase_adjust_dtl.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for bill_purchase_adjust_dtl.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of bill_purchase_adjust_dtl.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for bill_purchase_adjust_dtl.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of bill_purchase_adjust_dtl.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for bill_purchase_adjust_dtl.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_purchase_adjust_dtl.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_purchase_adjust_dtl.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_purchase_adjust_dtl.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_purchase_adjust_dtl.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     *
     * @return the value of bill_purchase_adjust_dtl.brand_unit_no
     */
    public String getBrandUnitNo() {
        return brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     * @param brandUnitNo the value for bill_purchase_adjust_dtl.brand_unit_no
     */
    public void setBrandUnitNo(String brandUnitNo) {
        this.brandUnitNo = brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     *
     * @return the value of bill_purchase_adjust_dtl.brand_unit_name
     */
    public String getBrandUnitName() {
        return brandUnitName;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     * @param brandUnitName the value for bill_purchase_adjust_dtl.brand_unit_name
     */
    public void setBrandUnitName(String brandUnitName) {
        this.brandUnitName = brandUnitName;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of bill_purchase_adjust_dtl.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for bill_purchase_adjust_dtl.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_purchase_adjust_dtl.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_purchase_adjust_dtl.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}