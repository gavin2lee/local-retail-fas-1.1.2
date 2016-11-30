package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * TODO: 增加描述
 * 
 * @author yu.y
 * @date 2014-11-13 下午5:48:58
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class OrderTotalDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3544213229378886521L;
	
	/**
	 * 客户编码
	 */
	private String customerNo;
	
	/**
	 * 客户名称
	 */
	private String customerName;
	
	/**
	 * 券名称
	 */
	private String ticketName;
	
	/**
	 * 购券数量
	 */
	private Integer sellQty;
	
	/**
	 * 票面金额
	 */
	private BigDecimal amount;;
	
	/**
	 * 券总金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 实收购买价格
	 */
	private BigDecimal buyAmount;
	
	/**
	 * 开票金额
	 */
	private BigDecimal ticketAmount;
	
	/**
	 * 本期用券数量
	 */
	private Integer useQty;
	
	/**
	 * 本期用券金额
	 */
	private BigDecimal useTicketAmount;
	
	/**
	 * 本期实际用券金额
	 */
	private BigDecimal usedAmount;
	
	/**
	 * 累计用券金额
	 */
	private BigDecimal useTotalAmount;
	
	/**
	 * 累计实际用券金额
	 */
	private BigDecimal usedTotalAmount;
	
	/**
	 * 剩余券面额
	 */
	private BigDecimal residueAmount;
	
	/**
	 * 剩余实收金额
	 */
	private BigDecimal residueReciveAmount;
	
	/**
	 * 实收金额
	 */
	private BigDecimal buyTotalAmount;

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public Integer getSellQty() {
		return sellQty;
	}

	public void setSellQty(Integer sellQty) {
		this.sellQty = sellQty;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(BigDecimal buyAmount) {
		this.buyAmount = buyAmount;
	}

	public BigDecimal getTicketAmount() {
		return ticketAmount;
	}

	public void setTicketAmount(BigDecimal ticketAmount) {
		this.ticketAmount = ticketAmount;
	}

	public Integer getUseQty() {
		return useQty;
	}

	public void setUseQty(Integer useQty) {
		this.useQty = useQty;
	}

	public BigDecimal getUseTicketAmount() {
		return useTicketAmount;
	}

	public void setUseTicketAmount(BigDecimal useTicketAmount) {
		this.useTicketAmount = useTicketAmount;
	}

	public BigDecimal getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(BigDecimal usedAmount) {
		this.usedAmount = usedAmount;
	}

	public BigDecimal getUseTotalAmount() {
		return useTotalAmount;
	}

	public void setUseTotalAmount(BigDecimal useTotalAmount) {
		this.useTotalAmount = useTotalAmount;
	}

	public BigDecimal getUsedTotalAmount() {
		return usedTotalAmount;
	}

	public void setUsedTotalAmount(BigDecimal usedTotalAmount) {
		this.usedTotalAmount = usedTotalAmount;
	}

	public BigDecimal getResidueAmount() {
		return residueAmount;
	}

	public void setResidueAmount(BigDecimal residueAmount) {
		this.residueAmount = residueAmount;
	}

	public BigDecimal getResidueReciveAmount() {
		return residueReciveAmount;
	}

	public void setResidueReciveAmount(BigDecimal residueReciveAmount) {
		this.residueReciveAmount = residueReciveAmount;
	}

	public BigDecimal getBuyTotalAmount() {
		return buyTotalAmount;
	}

	public void setBuyTotalAmount(BigDecimal buyTotalAmount) {
		this.buyTotalAmount = buyTotalAmount;
	}
	
}
