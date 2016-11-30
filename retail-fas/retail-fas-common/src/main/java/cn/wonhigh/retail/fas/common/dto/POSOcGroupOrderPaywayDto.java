package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TODO: 支付方式分组
 * 
 * @author zhang.lh
 * @date 2014-12-3 下午4:33:45
 * @version 0.1.0 
 * @copyright wonhigh.cn
 */
public class POSOcGroupOrderPaywayDto implements Serializable {

	private static final long serialVersionUID = 1375169102143819398L;

	/**
	* 店铺代码
	*/
	private String shopNo;

	/**
	* 店铺名称
	*/
	private String shopName;

	/**
	* 结算公司编码
	*//*
	private String companyNo;*/

	/**
	* 销售日期
	*/
	private Date outDate;

	/**
	* 订单编号
	*//*
	private String orderNo;
*/
	/**
	 * 支付方式代号
	 */
	private String payCode;

	/**
	 * 支付方式名称
	 */
	private String payName;

	/**
	 * 汇总金额
	 */
	private BigDecimal groupAmount;

	/**
	* 支付方式编号 卡号、券号、结算号
	*/
	private String paywayNum;
	
	/**
	 * 总金额
	 */
	private BigDecimal allAmount;

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
  

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
 
 	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public BigDecimal getGroupAmount() {
		return groupAmount;
	}

	public void setGroupAmount(BigDecimal groupAmount) {
		this.groupAmount = groupAmount;
	}

	public String getPaywayNum() {
		return paywayNum;
	}

	public void setPaywayNum(String paywayNum) {
		this.paywayNum = paywayNum;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public BigDecimal getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}

}