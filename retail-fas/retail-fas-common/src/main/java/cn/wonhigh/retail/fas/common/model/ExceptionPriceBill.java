package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 异常价格实体类
 * @author zhouxm
 * @date  2015-03-07 11:07:26
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
public class ExceptionPriceBill implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1948555921434403494L;

	/**
     * 单据类型名称
     */
    private String shardingFlag;
    
	/**
     * 流水号
     */
    private String id;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 单据类型
     */
    private Integer billType;
    
    /**
     * 业务类型
     */
    private Integer bizType;
    
    /**
     * 单据类型名称
     */
    private String billTypeName;

    /**
     * 订货单位
     */
    private String orderUnitNo;

    /**
     * 业务发生日期(审核日期、销售日期)
     */
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date billDate;

    /**
     * 商品编码
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
     * sku码
     */
    private String skuNo;

    /**
     * 尺码
     */
    private String sizeNo;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 单据价格
     */
    private BigDecimal cost;

    /**
     * 取价来源（厂进价，地区价等）
     */
    private Integer costFrom;

    /**
     * 取价来源（厂进价，地区价等）
     */
    private String costFromName;
    
    /**
     * 实际价格
     */
    private BigDecimal baseCost;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 状态
     */
    private Integer costChecked;
    
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
     * 异常原因
     */
    private String exceptionReason;
    
    /**
     * 结算单类型
     */
    private Integer balanceType;
    
    /**
     * 供应商编码
     */
    private String supplierNo;
    
    /**
     * 公司编码
     */
    private String companyNo;
    
    /**
     * 公司编码
     */
    private String salerNo;
    
    /**
     * 公司编码
     */
    private String buyerNo;
    
    /**
     * 地区编码
     */
    private String zoneNo;
    
    /**
     * 地区编码
     */
    private String zoneName;
    
    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonSerialize(using=JsonDateSerializer$19.class)
    private Date createTime;

    public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of exception_price_bill.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for exception_price_bill.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of exception_price_bill.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for exception_price_bill.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #billType}
     *
     * @return the value of exception_price_bill.bill_type
     */
    public Integer getBillType() {
        return billType;
    }

    /**
     * 
     * {@linkplain #billType}
     * @param billType the value for exception_price_bill.bill_type
     */
    public void setBillType(Integer billType) {
        this.billType = billType;
        this.billTypeName = BillTypeEnums.getNameByNo(this.billType);
    }
    
    public String getBillTypeName(){
    	return this.billTypeName;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     *
     * @return the value of exception_price_bill.order_unit_no
     */
    public String getOrderUnitNo() {
        return orderUnitNo;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     * @param orderUnitNo the value for exception_price_bill.order_unit_no
     */
    public void setOrderUnitNo(String orderUnitNo) {
        this.orderUnitNo = orderUnitNo;
    }

    /**
     * 
     * {@linkplain #billDate}
     *
     * @return the value of exception_price_bill.bill_date
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     * @param billDate the value for exception_price_bill.bill_date
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of exception_price_bill.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for exception_price_bill.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of exception_price_bill.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for exception_price_bill.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of exception_price_bill.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for exception_price_bill.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #skuNo}
     *
     * @return the value of exception_price_bill.sku_no
     */
    public String getSkuNo() {
        return skuNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     * @param skuNo the value for exception_price_bill.sku_no
     */
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     *
     * @return the value of exception_price_bill.size_no
     */
    public String getSizeNo() {
        return sizeNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     * @param sizeNo the value for exception_price_bill.size_no
     */
    public void setSizeNo(String sizeNo) {
        this.sizeNo = sizeNo;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of exception_price_bill.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for exception_price_bill.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #cost}
     *
     * @return the value of exception_price_bill.cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * 
     * {@linkplain #cost}
     * @param cost the value for exception_price_bill.cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * 
     * {@linkplain #costFrom}
     *
     * @return the value of exception_price_bill.cost_from
     */
    public Integer getCostFrom() {
        return costFrom;
    }

    /**
     * 
     * {@linkplain #costFrom}
     * @param costFrom the value for exception_price_bill.cost_from
     */
    public void setCostFrom(Integer costFrom) {
        this.costFrom = costFrom;
    }

    public String getCostFromName() {
		return costFromName;
	}

	public void setCostFromName(String costFromName) {
		this.costFromName = costFromName;
	}

	/**
     * 
     * {@linkplain #baseCost}
     *
     * @return the value of exception_price_bill.base_cost
     */
    public BigDecimal getBaseCost() {
        return baseCost;
    }

    /**
     * 
     * {@linkplain #baseCost}
     * @param baseCost the value for exception_price_bill.base_cost
     */
    public void setBaseCost(BigDecimal baseCost) {
        this.baseCost = baseCost;
    }

    public Integer getCostChecked() {
		return costChecked;
	}

	public void setCostChecked(Integer costChecked) {
		this.costChecked = costChecked;
	}

	/**
	 * 
	 * {@linkplain #status}
	 *
	 * @return the value of exception_price_bill.status
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
     * 
     * {@linkplain #status}
     * @param status the value for exception_price_bill.status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of exception_price_bill.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for exception_price_bill.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of exception_price_bill.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for exception_price_bill.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getBrandNo() {
		return brandNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}

	public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public String getSalerNo() {
		return salerNo;
	}

	public void setSalerNo(String salerNo) {
		this.salerNo = salerNo;
	}

	public String getBuyerNo() {
		return buyerNo;
	}

	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	
}