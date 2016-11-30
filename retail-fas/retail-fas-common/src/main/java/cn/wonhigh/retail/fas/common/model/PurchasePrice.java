package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.PurchasePriceExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-05-29 18:03:44
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
@ExportFormat(className=PurchasePriceExportFormat.class)
public class PurchasePrice extends FasBaseModel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 429706231827321539L;

    /**
     * 货品编号
     */
    private String itemNo;

    /**
     * 货品编码
     */
    private String itemCode;

    /**
     * 供应商编码
     */
    private String supplierNo;
    
    /**
     * 品牌编码
     */
    private String brandNo;
    
    /**
     * 品牌中文名称
     */
    private String brandName;

    /**
     * 采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 物料价
     */
    private BigDecimal materialPrice;

    /**
     * 厂进价
     */
    private BigDecimal factoryPrice;
    
    /**
     * 总部牌价
     */
    private BigDecimal tagPrice;
    
    /**
     * 建议牌价
     */
    private BigDecimal suggestTagPrice;
    
    /**
     * 总部成本
     */
    private BigDecimal headquarterCost;
    
    /**
     * 加价
     */
    private BigDecimal headquarterAddPrice;
    
    /**
     * 生效时间
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date effectiveDate;

    /**
     * 货品名称
     */
    private String itemName;

    /**
     * 供应商名称
     */
    private String supplierName;
    

	public BigDecimal getHeadquarterAddPrice() {
		return headquarterAddPrice;
	}

	public void setHeadquarterAddPrice(BigDecimal headquarterAddPrice) {
		this.headquarterAddPrice = headquarterAddPrice;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of purchase_price.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for purchase_price.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of purchase_price.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for purchase_price.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of purchase_price.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for purchase_price.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #purchasePrice}
     *
     * @return the value of purchase_price.purchase_price
     */
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * 
     * {@linkplain #purchasePrice}
     * @param purchasePrice the value for purchase_price.purchase_price
     */
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * 
     * {@linkplain #materialPrice}
     *
     * @return the value of purchase_price.material_price
     */
    public BigDecimal getMaterialPrice() {
        return materialPrice;
    }

    /**
     * 
     * {@linkplain #materialPrice}
     * @param materialPrice the value for purchase_price.material_price
     */
    public void setMaterialPrice(BigDecimal materialPrice) {
        this.materialPrice = materialPrice;
    }

    /**
     * 
     * {@linkplain #factoryPrice}
     *
     * @return the value of purchase_price.factory_price
     */
    public BigDecimal getFactoryPrice() {
        return factoryPrice;
    }

    /**
     * 
     * {@linkplain #factoryPrice}
     * @param factoryPrice the value for purchase_price.factory_price
     */
    public void setFactoryPrice(BigDecimal factoryPrice) {
        this.factoryPrice = factoryPrice;
    }

    public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}
	

	public BigDecimal getSuggestTagPrice() {
		return suggestTagPrice;
	}

	public void setSuggestTagPrice(BigDecimal suggestTagPrice) {
		this.suggestTagPrice = suggestTagPrice;
	}

	public BigDecimal getHeadquarterCost() {
		return headquarterCost;
	}

	public void setHeadquarterCost(BigDecimal headquarterCost) {
		this.headquarterCost = headquarterCost;
	}

	/**
     * 
     * {@linkplain #effectiveDate}
     *
     * @return the value of purchase_price.effective_date
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * 
     * {@linkplain #effectiveDate}
     * @param effectiveDate the value for purchase_price.effective_date
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
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

}