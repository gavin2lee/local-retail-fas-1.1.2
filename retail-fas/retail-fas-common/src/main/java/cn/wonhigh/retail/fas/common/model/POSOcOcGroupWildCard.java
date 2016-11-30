package cn.wonhigh.retail.fas.common.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TODO: 销售订单按外卡每日销售汇总接口Dto
 * 
 * @author zhang.lh
 * @date 2014-11-25 下午4:45:11
 * @version 0.1.0 
 * @copyright Wonhigh Information Technology (Shenzhen) Co.,Ltd.
 */
public class POSOcOcGroupWildCard implements Serializable{
 
	private static final long serialVersionUID = -6814567776277199668L;
	//门店NO
 	private String shopNo;
   	 // 销售日期
 	private Date outDate;
 	//外卡销售总金额
 	private BigDecimal saleAmount ;
 	//外卡结算金额
 	private BigDecimal  settleAmount;
 	//外卡VIP折扣金额
 	private BigDecimal  discAmount;
 	
// 	本单结算总金额
 	private BigDecimal  setAmount;
// 	 应收总金额
 	private BigDecimal  allAmount;
// 	   实收总金额
 	private BigDecimal  amount;
	/**
	 * @return the shopNo
	 */
	public String getShopNo() {
		return shopNo;
	}
	/**
	 * @param shopNo the shopNo to set
	 */
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	/**
	 * @return the outDate
	 */
	public Date getOutDate() {
		return outDate;
	}
	/**
	 * @param outDate the outDate to set
	 */
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	/**
	 * @return the saleAmount
	 */
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}
	/**
	 * @param saleAmount the saleAmount to set
	 */
	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
	/**
	 * @return the settleAmount
	 */
	public BigDecimal getSettleAmount() {
		return settleAmount;
	}
	/**
	 * @param settleAmount the settleAmount to set
	 */
	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}
	/**
	 * @return the discAmount
	 */
	public BigDecimal getDiscAmount() {
		return discAmount;
	}
	/**
	 * @param discAmount the discAmount to set
	 */
	public void setDiscAmount(BigDecimal discAmount) {
		this.discAmount = discAmount;
	}
	public BigDecimal getSetAmount() {
		return setAmount;
	}
	public void setSetAmount(BigDecimal setAmount) {
		this.setAmount = setAmount;
	}
	public BigDecimal getAllAmount() {
		return allAmount;
	}
	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
 
  
	 
 	
}
