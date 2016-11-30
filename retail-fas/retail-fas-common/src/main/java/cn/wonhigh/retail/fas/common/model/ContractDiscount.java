package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.AlgorithmEnums;
import cn.wonhigh.retail.fas.common.enums.NotTaxAlgorithmEnums;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-24 14:59:22
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
public class ContractDiscount implements Serializable {
    /**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -3582788569790104052L;

	/**
     * 主键ID
     */
    private String id;

    /**
     * 合同折扣编码
     */
    private String contractDiscountNo;

    /**
     * 合同折扣类型(1,供应商合同折扣;2,公司合同折扣;3,指定价折扣)
     */
    private Integer contractDiscountType;

    /**
     * 卖方编码
     */
    private String salerNo;

    /**
     * 卖方名称
     */
    private String salerName;

    /**
     * 买方编码
     */
    private String buyerNo;

    /**
     * 买方名称
     */
    private String buyerName;

    /**
     * 订货类型(1,期货;2,现货;3,全部)
     */
    private String orderType;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 大类编码
     */
    private String categoryNo;

    /**
     * 大类名称
     */
    private String categoryName;

    /**
     * 商品ID
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
     * 采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 年份
     */
    private String years;

    /**
     * 季节
     */
    private String season;

    /**
     * 定价依据(1,牌价;2,终端销售金额;3,供应商结算价)
     */
    private String priceBasis;

    /**
     * 折扣1
     */
    private BigDecimal discount1;

    /**
     * 折扣2
     */
    private BigDecimal discount2;

    /**
     * 参考折扣1
     */
    private BigDecimal referDiscount1;

    /**
     * 参考折扣2
     */
    private BigDecimal referDiscount2;
    
    /**
     * 加价
     */
    private BigDecimal addPrice;

    /**
     * 算法(含税单价)
     */
    private String algorithm;

    /**
     * 算法(不含税金额)
     */
    private String notTaxAlgorithm;
    
    /**
     * 生效日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date effectiveDate;

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

    /**扩展字段*/
    
    /**
     * 算法描述
     */
    private String algorithmDesc = "";
    
    /**
     * 算法描述
     */
    private String notTaxAlgorithmDesc = "";
    
    public BigDecimal getReferDiscount1() {
		return referDiscount1;
	}

	public void setReferDiscount1(BigDecimal referDiscount1) {
		this.referDiscount1 = referDiscount1;
	}

	public BigDecimal getReferDiscount2() {
		return referDiscount2;
	}

	public void setReferDiscount2(BigDecimal referDiscount2) {
		this.referDiscount2 = referDiscount2;
	}

	public String getNotTaxAlgorithm() {
		return notTaxAlgorithm;
	}

	public void setNotTaxAlgorithm(String notTaxAlgorithm) {
		this.notTaxAlgorithm = notTaxAlgorithm;
		setNotTaxAlgorithmDesc(NotTaxAlgorithmEnums.getNameByNo(notTaxAlgorithm));
	}

	public String getNotTaxAlgorithmDesc() {
		return notTaxAlgorithmDesc;
	}

	public void setNotTaxAlgorithmDesc(String notTaxAlgorithmDesc) {
		this.notTaxAlgorithmDesc = notTaxAlgorithmDesc;
	}

	public String getAlgorithmDesc() {
		return algorithmDesc;
	}

	public void setAlgorithmDesc(String algorithmDesc) {
		this.algorithmDesc = algorithmDesc;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of contract_discount.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for contract_discount.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #contractDiscountNo}
     *
     * @return the value of contract_discount.contract_discount_no
     */
    public String getContractDiscountNo() {
        return contractDiscountNo;
    }

    /**
     * 
     * {@linkplain #contractDiscountNo}
     * @param contractDiscountNo the value for contract_discount.contract_discount_no
     */
    public void setContractDiscountNo(String contractDiscountNo) {
        this.contractDiscountNo = contractDiscountNo;
    }

    /**
     * 
     * {@linkplain #contractDiscountType}
     *
     * @return the value of contract_discount.contract_discount_type
     */
    public Integer getContractDiscountType() {
        return contractDiscountType;
    }

    /**
     * 
     * {@linkplain #contractDiscountType}
     * @param contractDiscountType the value for contract_discount.contract_discount_type
     */
    public void setContractDiscountType(Integer contractDiscountType) {
        this.contractDiscountType = contractDiscountType;
    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of contract_discount.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for contract_discount.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of contract_discount.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for contract_discount.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of contract_discount.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for contract_discount.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of contract_discount.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for contract_discount.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #orderType}
     *
     * @return the value of contract_discount.order_type
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * 
     * {@linkplain #orderType}
     * @param orderType the value for contract_discount.order_type
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of contract_discount.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for contract_discount.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of contract_discount.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for contract_discount.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of contract_discount.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for contract_discount.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of contract_discount.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for contract_discount.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of contract_discount.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for contract_discount.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of contract_discount.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for contract_discount.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of contract_discount.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for contract_discount.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #purchasePrice}
     *
     * @return the value of contract_discount.purchase_price
     */
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * 
     * {@linkplain #purchasePrice}
     * @param purchasePrice the value for contract_discount.purchase_price
     */
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * 
     * {@linkplain #years}
     *
     * @return the value of contract_discount.years
     */
    public String getYears() {
        return years;
    }

    /**
     * 
     * {@linkplain #years}
     * @param years the value for contract_discount.years
     */
    public void setYears(String years) {
        this.years = years;
    }

    /**
     * 
     * {@linkplain #season}
     *
     * @return the value of contract_discount.season
     */
    public String getSeason() {
        return season;
    }

    /**
     * 
     * {@linkplain #season}
     * @param season the value for contract_discount.season
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     * 
     * {@linkplain #priceBasis}
     *
     * @return the value of contract_discount.price_basis
     */
    public String getPriceBasis() {
        return priceBasis;
    }

    /**
     * 
     * {@linkplain #priceBasis}
     * @param priceBasis the value for contract_discount.price_basis
     */
    public void setPriceBasis(String priceBasis) {
        this.priceBasis = priceBasis;
    }

    /**
     * 
     * {@linkplain #discount1}
     *
     * @return the value of contract_discount.discount1
     */
    public BigDecimal getDiscount1() {
        return discount1;
    }

    /**
     * 
     * {@linkplain #discount1}
     * @param discount1 the value for contract_discount.discount1
     */
    public void setDiscount1(BigDecimal discount1) {
        this.discount1 = discount1;
    }

    /**
     * 
     * {@linkplain #discount2}
     *
     * @return the value of contract_discount.discount2
     */
    public BigDecimal getDiscount2() {
        return discount2;
    }

    /**
     * 
     * {@linkplain #discount2}
     * @param discount2 the value for contract_discount.discount2
     */
    public void setDiscount2(BigDecimal discount2) {
        this.discount2 = discount2;
    }

    /**
     * 
     * {@linkplain #addPrice}
     *
     * @return the value of contract_discount.add_price
     */
    public BigDecimal getAddPrice() {
        return addPrice;
    }

    /**
     * 
     * {@linkplain #addPrice}
     * @param addPrice the value for contract_discount.add_price
     */
    public void setAddPrice(BigDecimal addPrice) {
        this.addPrice = addPrice;
    }

    /**
     * 
     * {@linkplain #algorithm}
     *
     * @return the value of contract_discount.algorithm
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * 
     * {@linkplain #algorithm}
     * @param algorithm the value for contract_discount.algorithm
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        setAlgorithmDesc(AlgorithmEnums.getNameByNo(algorithm));
    }

    /**
     * 
     * {@linkplain #effectiveDate}
     *
     * @return the value of contract_discount.effective_date
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * 
     * {@linkplain #effectiveDate}
     * @param effectiveDate the value for contract_discount.effective_date
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of contract_discount.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for contract_discount.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of contract_discount.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for contract_discount.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of contract_discount.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for contract_discount.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of contract_discount.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for contract_discount.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}