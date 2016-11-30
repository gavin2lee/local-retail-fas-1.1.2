package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-10-17 15:00:59
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
public class BillReceipt implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2294271291936301063L;

	/**
     * 主键(UUID)
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
     * 单据状态 (0-制单,1-出货,2-收货,98-删除,99-作废,100-完结)
     */
    private Byte status;

    /**
     * 相关单号,订货单单号
     */
    private String refBillNo;

    /**
     * 相关单据类型
     */
    private Integer refBillType;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 收货方结算公司名称
     */
    private String companyNo;

    /**
     * 订货单位
     */
    private String orderUnitNo;

    /**
     * 制单机构
     */
    private String sysNo;

    /**
     * 收货方机构编码
     */
    private String storeNo;

    /**
     * 供应商编码
     */
    private String supplierNo;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 货运单号
     */
    private String transportNo;

    /**
     * 收货日期
     */
    private Date stockInDate;

    /**
     * 业务员
     */
    private String merchandiser;

    /**
     * 制单人
     */
    private String createUser;

    /**
     * 制单时间
     */
    private Date createTime;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_receipt.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_receipt.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_receipt.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_receipt.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #billType}
     *
     * @return the value of bill_receipt.bill_type
     */
    public Integer getBillType() {
        return billType;
    }

    /**
     * 
     * {@linkplain #billType}
     * @param billType the value for bill_receipt.bill_type
     */
    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of bill_receipt.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for bill_receipt.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     *
     * @return the value of bill_receipt.ref_bill_no
     */
    public String getRefBillNo() {
        return refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillNo}
     * @param refBillNo the value for bill_receipt.ref_bill_no
     */
    public void setRefBillNo(String refBillNo) {
        this.refBillNo = refBillNo;
    }

    /**
     * 
     * {@linkplain #refBillType}
     *
     * @return the value of bill_receipt.ref_bill_type
     */
    public Integer getRefBillType() {
        return refBillType;
    }

    /**
     * 
     * {@linkplain #refBillType}
     * @param refBillType the value for bill_receipt.ref_bill_type
     */
    public void setRefBillType(Integer refBillType) {
        this.refBillType = refBillType;
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of bill_receipt.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for bill_receipt.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_receipt.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_receipt.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     *
     * @return the value of bill_receipt.order_unit_no
     */
    public String getOrderUnitNo() {
        return orderUnitNo;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     * @param orderUnitNo the value for bill_receipt.order_unit_no
     */
    public void setOrderUnitNo(String orderUnitNo) {
        this.orderUnitNo = orderUnitNo;
    }

    /**
     * 
     * {@linkplain #sysNo}
     *
     * @return the value of bill_receipt.sys_no
     */
    public String getSysNo() {
        return sysNo;
    }

    /**
     * 
     * {@linkplain #sysNo}
     * @param sysNo the value for bill_receipt.sys_no
     */
    public void setSysNo(String sysNo) {
        this.sysNo = sysNo;
    }

    /**
     * 
     * {@linkplain #storeNo}
     *
     * @return the value of bill_receipt.store_no
     */
    public String getStoreNo() {
        return storeNo;
    }

    /**
     * 
     * {@linkplain #storeNo}
     * @param storeNo the value for bill_receipt.store_no
     */
    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of bill_receipt.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for bill_receipt.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierName}
     *
     * @return the value of bill_receipt.supplier_name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 
     * {@linkplain #supplierName}
     * @param supplierName the value for bill_receipt.supplier_name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 
     * {@linkplain #contractNo}
     *
     * @return the value of bill_receipt.contract_no
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * 
     * {@linkplain #contractNo}
     * @param contractNo the value for bill_receipt.contract_no
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     *
     * @return the value of bill_receipt.invoice_no
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     * @param invoiceNo the value for bill_receipt.invoice_no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 
     * {@linkplain #transportNo}
     *
     * @return the value of bill_receipt.transport_no
     */
    public String getTransportNo() {
        return transportNo;
    }

    /**
     * 
     * {@linkplain #transportNo}
     * @param transportNo the value for bill_receipt.transport_no
     */
    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo;
    }

    /**
     * 
     * {@linkplain #stockInDate}
     *
     * @return the value of bill_receipt.stock_in_date
     */
    public Date getStockInDate() {
        return stockInDate;
    }

    /**
     * 
     * {@linkplain #stockInDate}
     * @param stockInDate the value for bill_receipt.stock_in_date
     */
    public void setStockInDate(Date stockInDate) {
        this.stockInDate = stockInDate;
    }

    /**
     * 
     * {@linkplain #merchandiser}
     *
     * @return the value of bill_receipt.merchandiser
     */
    public String getMerchandiser() {
        return merchandiser;
    }

    /**
     * 
     * {@linkplain #merchandiser}
     * @param merchandiser the value for bill_receipt.merchandiser
     */
    public void setMerchandiser(String merchandiser) {
        this.merchandiser = merchandiser;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of bill_receipt.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for bill_receipt.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of bill_receipt.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for bill_receipt.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #auditor}
     *
     * @return the value of bill_receipt.auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 
     * {@linkplain #auditor}
     * @param auditor the value for bill_receipt.auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /**
     * 
     * {@linkplain #auditTime}
     *
     * @return the value of bill_receipt.audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 
     * {@linkplain #auditTime}
     * @param auditTime the value for bill_receipt.audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_receipt.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_receipt.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}