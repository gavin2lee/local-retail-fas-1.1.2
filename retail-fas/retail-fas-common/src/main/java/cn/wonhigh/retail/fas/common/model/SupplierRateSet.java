package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途
 * 
 * @author user
 * @date 2016-05-05 09:10:13
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public class SupplierRateSet extends FasBaseModel implements Serializable {

	private static final long serialVersionUID = -8556881537270848115L;
	/**
	 * 主键
	 */
	// private int id;

	/**
	 * 供应商编号
	 */
	@ExcelCell("A")
	private String supplierNo;

	/**
	 * 供应商名称
	 */
	private String supplierName;

	/**
	 * 关税率
	 */
	private BigDecimal tariffRate;

	/**
	 * 增值税率
	 */
	@ExcelCell("B")
	private BigDecimal vatRate;

	/**
	 * 结算币种，关联currency_management表
	 */
	@ExcelCell("C")
	private String currencyCode;

	/*
     *
     **/
	private String currencyName;
	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date createTime;

	/**
	 * 修改人
	 */
	private String updateUser;

	/**
	 * 修改时间
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date updateTime;

	// /**
	// *
	// * {@linkplain #id}
	// *
	// * @return the value of supplier_rate_set.id
	// */
	// public String getId() {
	// return id;
	// }
	//
	// /**
	// *
	// * {@linkplain #id}
	// * @param id the value for supplier_rate_set.id
	// */
	// public void setId(int id) {
	// this.id = id;
	// }

	/**
	 * 
	 * {@linkplain #supplierNo}
	 * 
	 * @return the value of supplier_rate_set.supplier_no
	 */
	public String getSupplierNo() {
		return supplierNo;
	}

	/**
	 * 
	 * {@linkplain #supplierNo}
	 * 
	 * @param supplierNo
	 *            the value for supplier_rate_set.supplier_no
	 */
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	/**
	 * 
	 * {@linkplain #supplierName}
	 * 
	 * @return the value of supplier_rate_set.supplier_name
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * 
	 * {@linkplain #supplierName}
	 * 
	 * @param supplierName
	 *            the value for supplier_rate_set.supplier_name
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * 
	 * {@linkplain #tariffRate}
	 * 
	 * @return the value of supplier_rate_set.tariff_rate
	 */
	public BigDecimal getTariffRate() {
		return tariffRate;
	}

	/**
	 * 
	 * {@linkplain #tariffRate}
	 * 
	 * @param tariffRate
	 *            the value for supplier_rate_set.tariff_rate
	 */
	public void setTariffRate(BigDecimal tariffRate) {
		this.tariffRate = tariffRate;
	}

	/**
	 * 
	 * {@linkplain #vatRate}
	 * 
	 * @return the value of supplier_rate_set.vat_rate
	 */
	public BigDecimal getVatRate() {
		return vatRate;
	}

	/**
	 * 
	 * {@linkplain #vatRate}
	 * 
	 * @param vatRate
	 *            the value for supplier_rate_set.vat_rate
	 */
	public void setVatRate(BigDecimal vatRate) {
		this.vatRate = vatRate;
	}

	/**
	 * 
	 * {@linkplain #currencyCode}
	 * 
	 * @return the value of supplier_rate_set.currency_code
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 
	 * {@linkplain #currencyCode}
	 * 
	 * @param currencyCode
	 *            the value for supplier_rate_set.currency_code
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 
	 * {@linkplain #createUser}
	 * 
	 * @return the value of supplier_rate_set.create_user
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 
	 * {@linkplain #createUser}
	 * 
	 * @param createUser
	 *            the value for supplier_rate_set.create_user
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 
	 * {@linkplain #createTime}
	 * 
	 * @return the value of supplier_rate_set.create_time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 
	 * {@linkplain #createTime}
	 * 
	 * @param createTime
	 *            the value for supplier_rate_set.create_time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 
	 * {@linkplain #updateUser}
	 * 
	 * @return the value of supplier_rate_set.update_user
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * 
	 * {@linkplain #updateUser}
	 * 
	 * @param updateUser
	 *            the value for supplier_rate_set.update_user
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 
	 * {@linkplain #updateTime}
	 * 
	 * @return the value of supplier_rate_set.update_time
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 
	 * {@linkplain #updateTime}
	 * 
	 * @param updateTime
	 *            the value for supplier_rate_set.update_time
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
}