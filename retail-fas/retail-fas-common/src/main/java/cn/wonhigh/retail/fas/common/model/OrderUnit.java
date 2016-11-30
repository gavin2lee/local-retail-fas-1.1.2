package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author huang.xb1
 * @date  2014-07-28 14:19:21
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
public class OrderUnit implements Serializable {

	private static final long serialVersionUID = 1652783339564301472L;

	/**
	 * 订货单位ID
	 */
	private Integer id;

	/**
	 * 订货单位编号
	 */
	private String orderUnitNo;

	/**
	 * 订货单位编码
	 */
	private String orderUnitCode;

	/**
	 * 管理城市编号
	 */
	private String organNo;

	/**
	 * 管理城市名称
	 */
	private String organName;

	/**
	 * 订货单位名称
	 */
	private String name;

	/**
	 * 结算公司
	 */
	private String companyNo;

	/**
	 * 结算公司名称
	 */
	private String companyName;
	
	/**
	 * 建档人
	 */
	private String createUser;

	/**
	 * 建档时间
	 */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	private Date createTime;

	/**
	 * 修改人
	 */
	private String updateUser;

	/**
	 * 修改时间
	 */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	private Date updateTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 条件organNo
	 */
	private String organNoCon;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 
	 * {@linkplain #id}
	 *
	 * @return the value of order_unit.id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * {@linkplain #id}
	 * @param id the value for order_unit.id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * {@linkplain #orderUnitNo}
	 *
	 * @return the value of order_unit.order_unit_no
	 */
	public String getOrderUnitNo() {
		return orderUnitNo;
	}

	/**
	 * 
	 * {@linkplain #orderUnitNo}
	 * @param orderUnitNo the value for order_unit.order_unit_no
	 */
	public void setOrderUnitNo(String orderUnitNo) {
		this.orderUnitNo = orderUnitNo;
	}

	/**
	 * 
	 * {@linkplain #orderUnitCode}
	 *
	 * @return the value of order_unit.order_unit_code
	 */
	public String getOrderUnitCode() {
		return orderUnitCode;
	}

	/**
	 * 
	 * {@linkplain #orderUnitCode}
	 * @param orderUnitCode the value for order_unit.order_unit_code
	 */
	public void setOrderUnitCode(String orderUnitCode) {
		this.orderUnitCode = orderUnitCode;
	}

	/**
	 * 
	 * {@linkplain #organNo}
	 *
	 * @return the value of order_unit.organ_no
	 */
	public String getOrganNo() {
		return organNo;
	}

	/**
	 * 
	 * {@linkplain #organNo}
	 * @param organNo the value for order_unit.organ_no
	 */
	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}

	/**
	 * 
	 * {@linkplain #name}
	 *
	 * @return the value of order_unit.name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * {@linkplain #name}
	 * @param name the value for order_unit.name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * {@linkplain #createUser}
	 *
	 * @return the value of order_unit.create_user
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 
	 * {@linkplain #createUser}
	 * @param createUser the value for order_unit.create_user
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 
	 * {@linkplain #createTime}
	 *
	 * @return the value of order_unit.create_time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 
	 * {@linkplain #createTime}
	 * @param createTime the value for order_unit.create_time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 
	 * {@linkplain #updateUser}
	 *
	 * @return the value of order_unit.update_user
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * 
	 * {@linkplain #updateUser}
	 * @param updateUser the value for order_unit.update_user
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 
	 * {@linkplain #updateTime}
	 *
	 * @return the value of order_unit.update_time
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 
	 * {@linkplain #updateTime}
	 * @param updateTime the value for order_unit.update_time
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 
	 * {@linkplain #remark}
	 *
	 * @return the value of order_unit.remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 
	 * {@linkplain #remark}
	 * @param remark the value for order_unit.remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getOrganNoCon() {
		return organNoCon;
	}

	public void setOrganNoCon(String organNoCon) {
		this.organNoCon = organNoCon;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

}