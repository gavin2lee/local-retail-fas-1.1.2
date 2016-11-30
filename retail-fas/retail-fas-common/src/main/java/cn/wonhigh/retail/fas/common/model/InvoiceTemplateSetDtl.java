package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-31 15:46:28
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
public class InvoiceTemplateSetDtl extends FasBaseModel implements SequenceStrId{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

    /**
     * 模版编号
     */
    private String invoicetempDtlNo;

    /**
     * 发票模版编号
     */
    private String invoiceTempNo;

    /**
     * 类别编码产品大类
     */
    private String categoryNo;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 规格型号
     */
    private String typeModel;

    /**
     * 开票名称
     */
    private String invoiceName;

    /**
     * 是否启用数量控制（0：否，1：是）
     */
    private Byte qtyControlFlag;

    /**
     * 备注
     */
    private String remark;


    /**
     * 
     * {@linkplain #invoicetempDtlNo}
     *
     * @return the value of invoice_template_set_dtl.invoicetemp_dtl_no
     */
    public String getInvoicetempDtlNo() {
        return invoicetempDtlNo;
    }

    /**
     * 
     * {@linkplain #invoicetempDtlNo}
     * @param invoicetempDtlNo the value for invoice_template_set_dtl.invoicetemp_dtl_no
     */
    public void setInvoicetempDtlNo(String invoicetempDtlNo) {
        this.invoicetempDtlNo = invoicetempDtlNo;
    }

    /**
     * 
     * {@linkplain #invoiceTempNo}
     *
     * @return the value of invoice_template_set_dtl.invoice_temp_no
     */
    public String getInvoiceTempNo() {
        return invoiceTempNo;
    }

    /**
     * 
     * {@linkplain #invoiceTempNo}
     * @param invoiceTempNo the value for invoice_template_set_dtl.invoice_temp_no
     */
    public void setInvoiceTempNo(String invoiceTempNo) {
        this.invoiceTempNo = invoiceTempNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of invoice_template_set_dtl.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for invoice_template_set_dtl.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of invoice_template_set_dtl.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for invoice_template_set_dtl.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #typeModel}
     *
     * @return the value of invoice_template_set_dtl.type_model
     */
    public String getTypeModel() {
        return typeModel;
    }

    /**
     * 
     * {@linkplain #typeModel}
     * @param typeModel the value for invoice_template_set_dtl.type_model
     */
    public void setTypeModel(String typeModel) {
        this.typeModel = typeModel;
    }

    /**
     * 
     * {@linkplain #invoiceName}
     *
     * @return the value of invoice_template_set_dtl.invoice_name
     */
    public String getInvoiceName() {
        return invoiceName;
    }

    /**
     * 
     * {@linkplain #invoiceName}
     * @param invoiceName the value for invoice_template_set_dtl.invoice_name
     */
    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    /**
     * 
     * {@linkplain #qtyControlFlag}
     *
     * @return the value of invoice_template_set_dtl.qty_control_flag
     */
    public Byte getQtyControlFlag() {
        return qtyControlFlag;
    }

    /**
     * 
     * {@linkplain #qtyControlFlag}
     * @param qtyControlFlag the value for invoice_template_set_dtl.qty_control_flag
     */
    public void setQtyControlFlag(Byte qtyControlFlag) {
        this.qtyControlFlag = qtyControlFlag;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of invoice_template_set_dtl.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for invoice_template_set_dtl.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}