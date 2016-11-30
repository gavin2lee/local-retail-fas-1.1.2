package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 拆单过程的数据源model(到厂单、退货单)
 * 
 * @author 杨勇
 * @date 2014-8-29 上午10:46:12
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class BillSplitData implements Serializable {

	private static final long serialVersionUID = 3164389448392610473L;
	
	/** 单据编码 */
	private String billNo;
	
	/** 单据依据 */
	private Integer billBasis;
	
	/** 单据类型 */
	private Integer billType;
	
	/** 发出方编码 */
	private String supplierNo;

	/** 发出方名称 */
	private String supplierName;
	
	/** 收货方编码 */
	private String companyNo;
	
	/** 发货日期 */
    @JsonSerialize(using = JsonDateSerializer$10.class)   
	private Date sendOutDate;
	
	/** 税率 */
	private BigDecimal taxRate;
	
	/** 发出地编号 */
	private String storeNo;
	
	/** 发出地名称 */
	private String storeName;
	
	/** 订单号 */
	private String orderNo;
	
	/** 品牌编码 */
	private String brandNo;
	
	/** 单据状态 */
	private Integer billStatus;
	
	public Integer getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getBillBasis() {
		return billBasis;
	}

	public void setBillBasis(Integer billBasis) {
		this.billBasis = billBasis;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public Date getSendOutDate() {
		return sendOutDate;
	}

	public void setSendOutDate(Date sendOutDate) {
		this.sendOutDate = sendOutDate;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
}
