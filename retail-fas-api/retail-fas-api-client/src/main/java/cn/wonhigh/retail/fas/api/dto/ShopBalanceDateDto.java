package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

public class ShopBalanceDateDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6014515327315951266L;

	/**
     * 店铺编码
     */
    private String shopNo;
    
    /**
     * 月份
     */
    private String month;

    /**
     * 结算起始日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceStartDate;

    /**
     * 结算终止日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceEndDate;

	public String getShopNo() {
		return shopNo;
	}

	public String getMonth() {
		return month;
	}

	public Date getBalanceStartDate() {
		return balanceStartDate;
	}

	public Date getBalanceEndDate() {
		return balanceEndDate;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setBalanceStartDate(Date balanceStartDate) {
		this.balanceStartDate = balanceStartDate;
	}

	public void setBalanceEndDate(Date balanceEndDate) {
		this.balanceEndDate = balanceEndDate;
	}
}