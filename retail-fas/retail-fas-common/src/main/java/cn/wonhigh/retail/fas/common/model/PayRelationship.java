package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.enums.BalanceFlagEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.OrderTypeEnums;
import cn.wonhigh.retail.fas.common.enums.PriceBasisEnums;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-04-07 12:00:45
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
public class PayRelationship implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5824022481892935314L;

	/**
     * 主键ID
     */
    private String id;

    /**
     * 业务单据编码
     */
    private String businessBillNo;

    /**
     * 来源到货单号
     */
    private String refAsnBillNo;
    
    /**
     * 业务单据类型
     */
    private Integer businessBillType;
   
    /**
     * 业务单据业务类型
     */
    private Integer businessBizType;
    
    /**
     * 业务单据类型名
     */
    private String businessBillTypeName;

    /**
     * 买方公司编码
     */
    private String buyerNo;

    /**
     * 买方公司名称
     */
    private String buyerName;

    /**
     * 卖方公司编码
     */
    private String salerNo;

    /**
     * 卖方公司名称
     */
    private String salerName;

    /**
     * 供应商编码
     */
    private String supplierNo;

    /**
     * 供应商名称
     */
    private String supplierName;
    
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
     * 单据日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date billDate;

    /**
     * 到期日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date dueDate;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 金额(含税)
     */
    private BigDecimal amount;

    /**
     * 金额(不含税)
     */
    private BigDecimal notTaxAmount;
    
    /**
     * 已付款金额
     */
    private BigDecimal payAmount;

    /**
     * 未付款金额
     */
    private BigDecimal noPayAmount;

    /**
     * 预付款金额
     */
    private BigDecimal prepayAmount;

    /**
     * 状态（0:未付款;1:部分付款;2：全部付款）
     */
    private String payStatus;
    
    /**
     * 审核状态 0、未审核 1、已审核
     */
    private Integer auditStatus;

    /**
     * 结算单号
     */
    private String balanceNo;
    
    /**
     * 结算状态
     */
    private String balanceStatusStr;

    /**
     * 结算号
     */
    private String settlementNumber;

    /**
     * 厂商金额(含税)
     */
    private BigDecimal supplierAmount;

    /**
     * 厂商金额(不含税)
     */
    private BigDecimal notTaxSupplierAmount;
    
    /**
     * 订货类型
     */
    private String orderType;

    /**
     * 牌价额
     */
    private BigDecimal tagAmount;

    /**
     * 厂商折扣编码
     */
    private String supplierContractDiscountNo;

    /**
     * 公司折扣编码
     */
    private String companyContractDiscountNo;
    
    /**
     * 算法(含税)
     */
    private String algorithm;

    /**
     * 地区算法(含税)
     */
    private String zoneAlgorithm;
    
    /**
     * 算法(不含税)
     */
    private String notTaxAlgorithm;
    
    /**
     * 对账标记
     */
    private Integer balanceFlag;    
    
    /**
     * 地区定价依据(1:牌价;2:终端销售金额;3:排挤)
     */
    private String zonePriceBasis;

    /**
     * 地区原折扣1
     */
    private BigDecimal zoneOriginalDiscount1;

    /**
     * 地区原折扣2
     */
    private BigDecimal zoneOriginalDiscount2;
    
    /**
     * 地区加价
     */
    private BigDecimal zoneAddPrice;

    /**
     * 地区折扣
     */
    private BigDecimal zoneDiscount;

    /**
     * 地区额
     */
    private BigDecimal zoneAmount;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 供应商发货日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date supplierSendDate;

    /**
     * 仓库编码
     */
    private String storeNo;

    /**
     * 仓库名称
     */
    private String storeName;

    /**
     * 货管单位编码
     */
    private String orderUnitNo;

    /**
     * 货管单位名称
     */
    private String orderUnitName;

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
     * 订货类型
     */
    private String orderTypeName;
    
    /**
     * 总计
     */
    private int total;
    
    /**
     * 单据名称
     */
    private String billTypeName;

    /**
     * 地区定价依据
     */
    private String zonePriceBasisName;
   
    /**
     * 厂商价
     */
    private BigDecimal cost;
    
    /**
     * 地区价
     */
    private BigDecimal zoneCost;
   
    /**
     * 差异金额(含税)
     */
    private BigDecimal diffAmount;
    
    /**
     * 差异金额(不含税)
     */
    private BigDecimal notTaxDiffAmount;
    
    /**
     * 差异金额(不含税*1.17与含税差异)
     */
    private BigDecimal notTaxMultiDiffAmount;
    
    /**
     * 商品牌价额
     */
    private BigDecimal itemTagAmount;
    
    /**
     * 商品牌价
     */
    private BigDecimal tagPrice;
    
    /**
     * 一级大类
     */
    private String oneLevelCategoryName;
    
    /**
     * 发出数量
     */
    private Integer sendQty;
    
    /**
     * 对账标记名称
     */
    private String balanceFlagName;
    
    
    /**
     * 厂商折扣1
     */
    private BigDecimal supplierDiscount1;

    /**
     * 厂商折扣2
     */
    private BigDecimal supplierDiscount2;

    /**
     * 对账折扣1
     */
    private BigDecimal balanceDiscount1;

    /**
     * 对账折扣2
     */
    private BigDecimal balanceDiscount2;
    
    /**
     * 条件
     */
    private Map<String, Object> params;
    
    /**
     * 厂商牌价折扣
     */
    private BigDecimal supplierTagDiscount;

    /**
     * 地区牌价折扣
     */
    private BigDecimal zoneTagDiscount;
    
    /**
     * 品牌季节
     */
    private String brandSeason;
    
    public String getBrandSeason() {
		return brandSeason;
	}

	public void setBrandSeason(String brandSeason) {
		this.brandSeason = brandSeason;
	}

	public BigDecimal getSupplierTagDiscount() {
		return supplierTagDiscount;
	}

	public void setSupplierTagDiscount(BigDecimal supplierTagDiscount) {
		this.supplierTagDiscount = supplierTagDiscount;
	}

	public BigDecimal getZoneTagDiscount() {
		return zoneTagDiscount;
	}

	public void setZoneTagDiscount(BigDecimal zoneTagDiscount) {
		this.zoneTagDiscount = zoneTagDiscount;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public BigDecimal getNotTaxMultiDiffAmount() {
		return notTaxMultiDiffAmount;
	}

	public void setNotTaxMultiDiffAmount(BigDecimal notTaxMultiDiffAmount) {
		this.notTaxMultiDiffAmount = notTaxMultiDiffAmount;
	}

	public String getZoneAlgorithm() {
		return zoneAlgorithm;
	}

	public void setZoneAlgorithm(String zoneAlgorithm) {
		this.zoneAlgorithm = zoneAlgorithm;
	}

	public BigDecimal getZoneOriginalDiscount2() {
		return zoneOriginalDiscount2;
	}

	public void setZoneOriginalDiscount2(BigDecimal zoneOriginalDiscount2) {
		this.zoneOriginalDiscount2 = zoneOriginalDiscount2;
	}

	public BigDecimal getNotTaxDiffAmount() {
		return notTaxDiffAmount;
	}

	public void setNotTaxDiffAmount(BigDecimal notTaxDiffAmount) {
		this.notTaxDiffAmount = notTaxDiffAmount;
	}

	public BigDecimal getSupplierDiscount1() {
		return supplierDiscount1;
	}

	public void setSupplierDiscount1(BigDecimal supplierDiscount1) {
		this.supplierDiscount1 = supplierDiscount1;
	}

	public BigDecimal getSupplierDiscount2() {
		return supplierDiscount2;
	}

	public void setSupplierDiscount2(BigDecimal supplierDiscount2) {
		this.supplierDiscount2 = supplierDiscount2;
	}

	public BigDecimal getBalanceDiscount1() {
		return balanceDiscount1;
	}

	public void setBalanceDiscount1(BigDecimal balanceDiscount1) {
		this.balanceDiscount1 = balanceDiscount1;
	}

	public BigDecimal getBalanceDiscount2() {
		return balanceDiscount2;
	}

	public void setBalanceDiscount2(BigDecimal balanceDiscount2) {
		this.balanceDiscount2 = balanceDiscount2;
	}

	public BigDecimal getNotTaxAmount() {
		return notTaxAmount;
	}

	public void setNotTaxAmount(BigDecimal notTaxAmount) {
		this.notTaxAmount = notTaxAmount;
	}

	public BigDecimal getNotTaxSupplierAmount() {
		return notTaxSupplierAmount;
	}

	public void setNotTaxSupplierAmount(BigDecimal notTaxSupplierAmount) {
		this.notTaxSupplierAmount = notTaxSupplierAmount;
	}

	public String getNotTaxAlgorithm() {
		return notTaxAlgorithm;
	}

	public void setNotTaxAlgorithm(String notTaxAlgorithm) {
		this.notTaxAlgorithm = notTaxAlgorithm;
	}


	public Integer getBalanceFlag() {
		return balanceFlag;
	}

	public void setBalanceFlag(Integer balanceFlag) {
		this.balanceFlag = balanceFlag;
		if(null != balanceFlag){
			this.setBalanceFlagName(BalanceFlagEnums.getNameByNo(balanceFlag));
		}
	}

	public String getBalanceFlagName() {
		return balanceFlagName;
	}

	public void setBalanceFlagName(String balanceFlagName) {
		this.balanceFlagName = balanceFlagName;
	}

	public Integer getSendQty() {
		return sendQty;
	}

	public void setSendQty(Integer sendQty) {
		this.sendQty = sendQty;
	}

	public BigDecimal getItemTagAmount() {
		return itemTagAmount;
	}

	public void setItemTagAmount(BigDecimal itemTagAmount) {
		this.itemTagAmount = itemTagAmount;
	}

	public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public String getOneLevelCategoryName() {
		return oneLevelCategoryName;
	}

	public void setOneLevelCategoryName(String oneLevelCategoryName) {
		this.oneLevelCategoryName = oneLevelCategoryName;
	}

	public BigDecimal getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(BigDecimal diffAmount) {
		this.diffAmount = diffAmount;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getZoneCost() {
		return zoneCost;
	}

	public void setZoneCost(BigDecimal zoneCost) {
		this.zoneCost = zoneCost;
	}

	public String getZonePriceBasisName() {
		return zonePriceBasisName;
	}

	public void setZonePriceBasisName(String zonePriceBasisName) {
		this.zonePriceBasisName = zonePriceBasisName;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

    public String getSupplierContractDiscountNo() {
		return supplierContractDiscountNo;
	}

	public void setSupplierContractDiscountNo(String supplierContractDiscountNo) {
		this.supplierContractDiscountNo = supplierContractDiscountNo;
	}

	public String getCompanyContractDiscountNo() {
		return companyContractDiscountNo;
	}

	public void setCompanyContractDiscountNo(String companyContractDiscountNo) {
		this.companyContractDiscountNo = companyContractDiscountNo;
	}

	
	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of pay_relationship.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for pay_relationship.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #businessBillNo}
     *
     * @return the value of pay_relationship.business_bill_no
     */
    public String getBusinessBillNo() {
        return businessBillNo;
    }

    /**
     * 
     * {@linkplain #businessBillNo}
     * @param businessBillNo the value for pay_relationship.business_bill_no
     */
    public void setBusinessBillNo(String businessBillNo) {
        this.businessBillNo = businessBillNo;
    }

    /**
     * 
     * {@linkplain #businessBillType}
     *
     * @return the value of pay_relationship.business_bill_type
     */
    public Integer getBusinessBillType() {
        return businessBillType;
    }

    /**
     * 
     * {@linkplain #businessBillType}
     * @param businessBillType the value for pay_relationship.business_bill_type
     */
    public void setBusinessBillType(Integer businessBillType) {
        this.businessBillType = businessBillType;
        this.setBillTypeName(BillTypeEnums.getNameByNo(businessBillType));
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of pay_relationship.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for pay_relationship.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of pay_relationship.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for pay_relationship.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of pay_relationship.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for pay_relationship.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of pay_relationship.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for pay_relationship.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of pay_relationship.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for pay_relationship.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of pay_relationship.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 
     * {@linkplain #supplierName}
     * @param supplierName the value for pay_relationship.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of pay_relationship.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for pay_relationship.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of pay_relationship.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for pay_relationship.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of pay_relationship.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for pay_relationship.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of pay_relationship.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for pay_relationship.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #billDate}
     *
     * @return the value of pay_relationship.bill_date
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     * @param billDate the value for pay_relationship.bill_date
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * {@linkplain #dueDate}
     *
     * @return the value of pay_relationship.due_date
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * 
     * {@linkplain #dueDate}
     * @param dueDate the value for pay_relationship.due_date
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of pay_relationship.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for pay_relationship.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of pay_relationship.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for pay_relationship.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #payAmount}
     *
     * @return the value of pay_relationship.pay_amount
     */
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    /**
     * 
     * {@linkplain #payAmount}
     * @param payAmount the value for pay_relationship.pay_amount
     */
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * 
     * {@linkplain #noPayAmount}
     *
     * @return the value of pay_relationship.no_pay_amount
     */
    public BigDecimal getNoPayAmount() {
        return noPayAmount;
    }

    /**
     * 
     * {@linkplain #noPayAmount}
     * @param noPayAmount the value for pay_relationship.no_pay_amount
     */
    public void setNoPayAmount(BigDecimal noPayAmount) {
        this.noPayAmount = noPayAmount;
    }

    /**
     * 
     * {@linkplain #prepayAmount}
     *
     * @return the value of pay_relationship.prepay_amount
     */
    public BigDecimal getPrepayAmount() {
        return prepayAmount;
    }

    /**
     * 
     * {@linkplain #prepayAmount}
     * @param prepayAmount the value for pay_relationship.prepay_amount
     */
    public void setPrepayAmount(BigDecimal prepayAmount) {
        this.prepayAmount = prepayAmount;
    }

    /**
     * 
     * {@linkplain #payStatus}
     *
     * @return the value of pay_relationship.pay_status
     */
    public String getPayStatus() {
        return payStatus;
    }

    /**
     * 
     * {@linkplain #payStatus}
     * @param payStatus the value for pay_relationship.pay_status
     */
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of pay_relationship.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for pay_relationship.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #settlementNumber}
     *
     * @return the value of pay_relationship.settlement_number
     */
    public String getSettlementNumber() {
        return settlementNumber;
    }

    /**
     * 
     * {@linkplain #settlementNumber}
     * @param settlementNumber the value for pay_relationship.settlement_number
     */
    public void setSettlementNumber(String settlementNumber) {
        this.settlementNumber = settlementNumber;
    }

    /**
     * 
     * {@linkplain #supplierAmount}
     *
     * @return the value of pay_relationship.supplier_amount
     */
    public BigDecimal getSupplierAmount() {
        return supplierAmount;
    }

    /**
     * 
     * {@linkplain #supplierAmount}
     * @param supplierAmount the value for pay_relationship.supplier_amount
     */
    public void setSupplierAmount(BigDecimal supplierAmount) {
        this.supplierAmount = supplierAmount;
    }

    /**
     * 
     * {@linkplain #orderType}
     *
     * @return the value of pay_relationship.order_type
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * 
     * {@linkplain #orderType}
     * @param orderType the value for pay_relationship.order_type
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
        if(StringUtils.isNotBlank(orderType)){
        	this.setOrderTypeName(OrderTypeEnums.getNameByNo(Integer.parseInt(orderType)));
        }else{
        	this.setOrderTypeName("");
        }
    }

    /**
     * 
     * {@linkplain #tagAmount}
     *
     * @return the value of pay_relationship.tag_amount
     */
    public BigDecimal getTagAmount() {
        return tagAmount;
    }

    /**
     * 
     * {@linkplain #tagAmount}
     * @param tagAmount the value for pay_relationship.tag_amount
     */
    public void setTagAmount(BigDecimal tagAmount) {
        this.tagAmount = tagAmount;
    }

    /**
     * 
     * {@linkplain #algorithm}
     *
     * @return the value of pay_relationship.algorithm
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * 
     * {@linkplain #algorithm}
     * @param algorithm the value for pay_relationship.algorithm
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * 
     * {@linkplain #zonePriceBasis}
     *
     * @return the value of pay_relationship.zone_price_basis
     */
    public String getZonePriceBasis() {
        return zonePriceBasis;
    }

    /**
     * 
     * {@linkplain #zonePriceBasis}
     * @param zonePriceBasis the value for pay_relationship.zone_price_basis
     */
    public void setZonePriceBasis(String zonePriceBasis) {
        this.zonePriceBasis = zonePriceBasis;
        if(StringUtils.isNotBlank(zonePriceBasis)){
        	this.setZonePriceBasisName(PriceBasisEnums.getNameByNo(Integer.parseInt(zonePriceBasis)));
        }
    }

    /**
     * 
     * {@linkplain #zoneOriginalDiscount1}
     *
     * @return the value of pay_relationship.zone_original_discount1
     */
    public BigDecimal getZoneOriginalDiscount1() {
        return zoneOriginalDiscount1;
    }

    /**
     * 
     * {@linkplain #zoneOriginalDiscount1}
     * @param zoneOriginalDiscount1 the value for pay_relationship.zone_original_discount1
     */
    public void setZoneOriginalDiscount1(BigDecimal zoneOriginalDiscount1) {
        this.zoneOriginalDiscount1 = zoneOriginalDiscount1;
    }

    /**
     * 
     * {@linkplain #zoneAddPrice}
     *
     * @return the value of pay_relationship.zone_add_price
     */
    public BigDecimal getZoneAddPrice() {
        return zoneAddPrice;
    }

    /**
     * 
     * {@linkplain #zoneAddPrice}
     * @param zoneAddPrice the value for pay_relationship.zone_add_price
     */
    public void setZoneAddPrice(BigDecimal zoneAddPrice) {
        this.zoneAddPrice = zoneAddPrice;
    }

    /**
     * 
     * {@linkplain #zoneDiscount}
     *
     * @return the value of pay_relationship.zone_discount
     */
    public BigDecimal getZoneDiscount() {
        return zoneDiscount;
    }

    /**
     * 
     * {@linkplain #zoneDiscount}
     * @param zoneDiscount the value for pay_relationship.zone_discount
     */
    public void setZoneDiscount(BigDecimal zoneDiscount) {
        this.zoneDiscount = zoneDiscount;
    }

    /**
     * 
     * {@linkplain #zoneAmount}
     *
     * @return the value of pay_relationship.zone_amount
     */
    public BigDecimal getZoneAmount() {
        return zoneAmount;
    }

    /**
     * 
     * {@linkplain #zoneAmount}
     * @param zoneAmount the value for pay_relationship.zone_amount
     */
    public void setZoneAmount(BigDecimal zoneAmount) {
        this.zoneAmount = zoneAmount;
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of pay_relationship.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for pay_relationship.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #supplierSendDate}
     *
     * @return the value of pay_relationship.supplier_send_date
     */
    public Date getSupplierSendDate() {
        return supplierSendDate;
    }

    /**
     * 
     * {@linkplain #supplierSendDate}
     * @param supplierSendDate the value for pay_relationship.supplier_send_date
     */
    public void setSupplierSendDate(Date supplierSendDate) {
        this.supplierSendDate = supplierSendDate;
    }

    /**
     * 
     * {@linkplain #storeNo}
     *
     * @return the value of pay_relationship.store_no
     */
    public String getStoreNo() {
        return storeNo;
    }

    /**
     * 
     * {@linkplain #storeNo}
     * @param storeNo the value for pay_relationship.store_no
     */
    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    /**
     * 
     * {@linkplain #storeName}
     *
     * @return the value of pay_relationship.store_name
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * 
     * {@linkplain #storeName}
     * @param storeName the value for pay_relationship.store_name
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     *
     * @return the value of pay_relationship.order_unit_no
     */
    public String getOrderUnitNo() {
        return orderUnitNo;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     * @param orderUnitNo the value for pay_relationship.order_unit_no
     */
    public void setOrderUnitNo(String orderUnitNo) {
        this.orderUnitNo = orderUnitNo;
    }

    /**
     * 
     * {@linkplain #orderUnitName}
     *
     * @return the value of pay_relationship.order_unit_name
     */
    public String getOrderUnitName() {
        return orderUnitName;
    }

    /**
     * 
     * {@linkplain #orderUnitName}
     * @param orderUnitName the value for pay_relationship.order_unit_name
     */
    public void setOrderUnitName(String orderUnitName) {
        this.orderUnitName = orderUnitName;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of pay_relationship.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for pay_relationship.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of pay_relationship.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for pay_relationship.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of pay_relationship.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for pay_relationship.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of pay_relationship.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for pay_relationship.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
    

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getBalanceStatusStr() {
		if(null != this.payStatus){
			if(this.payStatus.equals("0")){
				this.balanceStatusStr = "未付款";
			}else if(this.payStatus.equals("1")){
				this.balanceStatusStr = "部分付款";
			}else if(this.payStatus.equals("2")){
				this.balanceStatusStr = "全部付款";
			}
		}
		return balanceStatusStr;
	}

	public void setBalanceStatusStr(String balanceStatusStr) {
		this.balanceStatusStr = balanceStatusStr;
	}

	public String getBusinessBillTypeName() {
		if(null != this.businessBillType){
			if(this.businessBillType.intValue() == 1301){
				this.businessBillTypeName = "到货单";
			}
		}
		return businessBillTypeName;
	}

	public Integer getBusinessBizType() {
		return businessBizType;
	}

	public void setBusinessBizType(Integer businessBizType) {
		this.businessBizType = businessBizType;
	}

	public void setBusinessBillTypeName(String businessBillTypeName) {
		this.businessBillTypeName = businessBillTypeName;
	}

	public String getRefAsnBillNo() {
		return refAsnBillNo;
	}

	public void setRefAsnBillNo(String refAsnBillNo) {
		this.refAsnBillNo = refAsnBillNo;
	}
}