package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;


/**
 * 地区批发-开票申请单明细
 * @author admin
 * @date  2014-09-16 15:35:20
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
@ExportFormat(className=AbstractExportFormat.class)
public class BillInvoiceApplyDtl extends FasBaseModel {

	private static final long serialVersionUID = 52751057128379469L;

	/**
     * 开票申请单据编码
     */
    private String billNo;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 结算公司名称
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
     * 出库数量
     */
    private Integer sendOutQty;

    /**
     * 含税单价
     */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
    private BigDecimal cost;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 结算开始时间
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceStartDate;

    /**
     * 结算结束日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceEndDate;

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_invoice_apply_dtl.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_invoice_apply_dtl.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_invoice_apply_dtl.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_invoice_apply_dtl.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_invoice_apply_dtl.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_invoice_apply_dtl.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of bill_invoice_apply_dtl.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for bill_invoice_apply_dtl.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of bill_invoice_apply_dtl.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for bill_invoice_apply_dtl.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #sendOutQty}
     *
     * @return the value of bill_invoice_apply_dtl.send_out_qty
     */
    public Integer getSendOutQty() {
        return sendOutQty;
    }

    /**
     * 
     * {@linkplain #sendOutQty}
     * @param sendOutQty the value for bill_invoice_apply_dtl.send_out_qty
     */
    public void setSendOutQty(Integer sendOutQty) {
        this.sendOutQty = sendOutQty;
    }

    /**
     * 
     * {@linkplain #cost}
     *
     * @return the value of bill_invoice_apply_dtl.cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * 
     * {@linkplain #cost}
     * @param cost the value for bill_invoice_apply_dtl.cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * 
     * {@linkplain #taxRate}
     *
     * @return the value of bill_invoice_apply_dtl.tax_rate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * 
     * {@linkplain #taxRate}
     * @param taxRate the value for bill_invoice_apply_dtl.tax_rate
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

	public Date getBalanceStartDate() {
		return balanceStartDate;
	}

	public void setBalanceStartDate(Date balanceStartDate) {
		this.balanceStartDate = balanceStartDate;
	}

	public Date getBalanceEndDate() {
		return balanceEndDate;
	}

	public void setBalanceEndDate(Date balanceEndDate) {
		this.balanceEndDate = balanceEndDate;
	}
}