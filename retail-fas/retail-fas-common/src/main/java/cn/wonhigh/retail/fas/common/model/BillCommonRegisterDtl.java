package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-01-05 15:30:06
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
public class BillCommonRegisterDtl extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2638953721919788986L;

    /**
     * 分库字段
     */
    private String shardingFlag;
    
	/**
     * 主键
     */
    private String id;

    /**
     * 单据号
     */
    private String billNo;

    /**
     * 发票编号
     */
    private String invoiceCode;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 发票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceDate;

    /**
     * 发票金额
     */
    private BigDecimal invoiceAmount;

    /**
     * 预估成本
     */
    private BigDecimal estimatedAmount;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 类别名称
     */
    private String categoryName;

	private String invoiceName;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌中文名称
     */
    private String brandName;

    /**
     * 商品数量
     */
    private Integer qty;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 不含税金额
     */
    private BigDecimal noTaxAmount;

    /**
     * 税额
     */
    private BigDecimal taxAmount;

    /**
     * 价格
     */
    private BigDecimal price;

	 /**
     * 快递日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date deliveryDate;

    /**
     * 快递公司
     */
    private String express;

    /**
     * 快递单号
     */
    private String deliveryNo;

    /**
     * 接收日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date receiveDate;

    /**
     * 确认人
     */
    private String confirmUser;

    /**
     * 确认日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date confirmTime;

    /**
     * 备注
     */
    private String remark;
    
    private String sourceNo;
    private String buyerNo;
    
    /**
     * 公司编码(开票方)-卖方
     */
    private String salerNo;

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
     * @return the value of bill_common_register_dtl.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_common_register_dtl.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_common_register_dtl.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_common_register_dtl.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

	 /**
     * 
     * {@linkplain #invoiceCode}
     *
     * @return the value of bill_common_register_dtl.invoice_code
     */
    public String getInvoiceCode() {
        return invoiceCode;
    }

    /**
     * 
     * {@linkplain #invoiceCode}
     * @param invoiceCode the value for bill_common_register_dtl.invoice_code
     */
    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     *
     * @return the value of bill_common_register_dtl.invoice_no
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     * @param invoiceNo the value for bill_common_register_dtl.invoice_no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     *
     * @return the value of bill_common_register_dtl.invoice_date
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     * @param invoiceDate the value for bill_common_register_dtl.invoice_date
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     *
     * @return the value of bill_common_register_dtl.amount
     */
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     * @param amount the value for bill_common_register_dtl.amount
     */
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * 
     * {@linkplain #estimatedAmount}
     *
     * @return the value of bill_common_register_dtl.estimated_amount
     */
    public BigDecimal getEstimatedAmount() {
        return estimatedAmount;
    }

    /**
     * 
     * {@linkplain #estimatedAmount}
     * @param estimatedAmount the value for bill_common_register_dtl.estimated_amount
     */
    public void setEstimatedAmount(BigDecimal estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of bill_common_register_dtl.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for bill_common_register_dtl.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of bill_common_register_dtl.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for bill_common_register_dtl.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

	public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_common_register_dtl.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_common_register_dtl.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_common_register_dtl.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_common_register_dtl.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of bill_common_register_dtl.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for bill_common_register_dtl.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #taxRate}
     *
     * @return the value of bill_common_register_dtl.tax_rate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * 
     * {@linkplain #taxRate}
     * @param taxRate the value for bill_common_register_dtl.tax_rate
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * 
     * {@linkplain #noTaxAmount}
     *
     * @return the value of bill_common_register_dtl.no_tax_amount
     */
    public BigDecimal getNoTaxAmount() {
        return noTaxAmount;
    }

    /**
     * 
     * {@linkplain #noTaxAmount}
     * @param noTaxAmount the value for bill_common_register_dtl.no_tax_amount
     */
    public void setNoTaxAmount(BigDecimal noTaxAmount) {
        this.noTaxAmount = noTaxAmount;
    }

    /**
     * 
     * {@linkplain #taxAmount}
     *
     * @return the value of bill_common_register_dtl.tax_amount
     */
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    /**
     * 
     * {@linkplain #taxAmount}
     * @param taxAmount the value for bill_common_register_dtl.tax_amount
     */
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * 
     * {@linkplain #price}
     *
     * @return the value of bill_common_register_dtl.price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 
     * {@linkplain #price}
     * @param price the value for bill_common_register_dtl.price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

	 /**
     * 
     * {@linkplain #deliveryDate}
     *
     * @return the value of bill_common_register_dtl.delivery_date
     */
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * 
     * {@linkplain #deliveryDate}
     * @param deliveryDate the value for bill_common_register_dtl.delivery_date
     */
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * 
     * {@linkplain #express}
     *
     * @return the value of bill_common_register_dtl.express
     */
    public String getExpress() {
        return express;
    }

    /**
     * 
     * {@linkplain #express}
     * @param express the value for bill_common_register_dtl.express
     */
    public void setExpress(String express) {
        this.express = express;
    }

    /**
     * 
     * {@linkplain #deliveryNo}
     *
     * @return the value of bill_common_register_dtl.delivery_no
     */
    public String getDeliveryNo() {
        return deliveryNo;
    }

    /**
     * 
     * {@linkplain #deliveryNo}
     * @param deliveryNo the value for bill_common_register_dtl.delivery_no
     */
    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    /**
     * 
     * {@linkplain #receiveDate}
     *
     * @return the value of bill_common_register_dtl.receive_date
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * 
     * {@linkplain #receiveDate}
     * @param receiveDate the value for bill_common_register_dtl.receive_date
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    

    public String getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	/**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_common_register_dtl.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_common_register_dtl.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}

	public String getBuyerNo() {
		return buyerNo;
	}

	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}
	
	 /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of bill_common_register_dtl.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for bill_common_register_dtl.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(salerNo));
    }
}