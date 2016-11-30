/**  
* 项目名称：retail-fas-common  
* 类名称：BalanceDto  
* 类描述：用于查询显示结算相关信息的DTO
* 创建人：wang.m 
* 创建时间：2014-10-17 下午1:13:48  
* @version       
*/ 
package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;


public class PrintBalanceEntry{
	
		private String type;
		private String sourse;
		private String title;
		private Integer qty = 0;
		private BigDecimal amount = new BigDecimal(0);
		private BigDecimal cost = new BigDecimal(0);
		
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSourse() {
		return sourse;
	}
	public void setSourse(String sourse) {
		this.sourse = sourse;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
		
}
