package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-02 11:22:09
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
public class FactoryPriceMaintain implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8364765614301559027L;

	/**
     * 序列号
     */
    private Long id;

    /**
     * 商品货号
     */
    private String itemNo;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 供应商名称
     */
    private String supplierName;
    
    /**
     * 供应商编码
     */
    private String supplierNo;

    /**
     * 采购价
     */
    private BigDecimal purchasePrice;
    
    /**
     * 物料价
     */
    private BigDecimal materialPrice;
    
    /**
     * 进货价
     */
    private BigDecimal factoryPrice;

    /**
     * 有效时间（有效开始时间）
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date effectiveDate;

    /**
     * 是否启用（0启用，1为不启用）
     */
    private String status;
    
    /**
     * 创建人
     */
    private String createUser;
    
    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date createTime;
    
    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date updateTime;
    
    /**
     * 商品货号
     */
    private String itemCode;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of purchase_price.id
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for purchase_price.id
     */
    public void setId(Long id) {
        this.id = id;
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
     * {@linkplain #status}
     *
     * @return the value of purchase_price.status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for purchase_price.status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    
    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of purchase_price.create_User
     */
    public String getCreateUser() {
		return createUser;
	}

    /**
     * 
     * {@linkplain #createUser}
     * @param status the value for purchase_price.create_user
     */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of purchase_price.create_time
     */
	public Date getCreateTime() {
		return createTime;
	}

	/**
     * 
     * {@linkplain #createTime}
     * @param status the value for purchase_price.create_time
     */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
     * {@linkplain #purchasePrice}
     * @return the value purchase_price.purchase_price
     */
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	/**
	 * {@linkplain #status}
	 * @param purchasePrice the value for purchase_price.purchase_price
	 */
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	/**
	 * {@linkplain #materialPrice}
	 * @return the value purchase_price.material_price
	 */
	public BigDecimal getMaterialPrice() {
		return materialPrice;
	}

	/**
	 * {@linkplain #materialPrice}
	 * @param materialPrice the value for purchase_price.material_price
	 */
	public void setMaterialPrice(BigDecimal materialPrice) {
		this.materialPrice = materialPrice;
	}

	/**
	 * {@linkplain #factoryPrice}
	 * @return the value purchase_price.factory_price
	 */
	public BigDecimal getFactoryPrice() {
		return factoryPrice;
	}

	/**
	 * {@linkplain #factoryPrice}
	 * @param factoryPrice the value for purchase_price.factory_Price
	 */
	public void setFactoryPrice(BigDecimal factoryPrice) {
		this.factoryPrice = factoryPrice;
	}

	
	/**
	 * {@linkplain #effectiveDate}
	 * @return the value purchase_price.effective_date
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * {@linkplain #effectiveDate}
	 * @param effectiveDate the value for purchase_price.effective_date
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	
	/**
	 * {@linkplain #updateUser}
	 * @return the value purchase_price.update_user
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * {@linkplain #updateUser}
	 * @param updateUser the value for purchase_price.update_user
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	
	/**
	 * {@linkplain #updateTime}
	 * @return the value purchase_price.update_time
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * {@linkplain #updateTime}
	 * @param updateTime the value for purchase_price.update_time
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

}