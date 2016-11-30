/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：TotalDto  
 * 类描述：结算单合计行
 * 创建人：ouyang.zm  
 * 创建时间：2014-10-13 下午2:12:40  
 * @version       
 */
package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;

public class TotalDto {
	private BigDecimal totalSum;// 总金额
	private BigDecimal overAmount;// 余额

	private int totalOutQty;// 发出数量合计
	private int totalEntryQty;// 接收数量合计
	private BigDecimal totalOutAmount;// 出库金额合计
	private BigDecimal totalEntryAmount;// 入库金额合计
	private BigDecimal totalPurchaseAmount;// 采购金额合计

	public BigDecimal getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(BigDecimal totalSum) {
		this.totalSum = totalSum;
	}

	public BigDecimal getOverAmount() {
		return overAmount;
	}

	public void setOverAmount(BigDecimal overAmount) {
		this.overAmount = overAmount;
	}

	public int getTotalOutQty() {
		return totalOutQty;
	}

	public void setTotalOutQty(int totalOutQty) {
		this.totalOutQty = totalOutQty;
	}

	public int getTotalEntryQty() {
		return totalEntryQty;
	}

	public void setTotalEntryQty(int totalEntryQty) {
		this.totalEntryQty = totalEntryQty;
	}

	public BigDecimal getTotalOutAmount() {
		return totalOutAmount;
	}

	public void setTotalOutAmount(BigDecimal totalOutAmount) {
		this.totalOutAmount = totalOutAmount;
	}

	public BigDecimal getTotalEntryAmount() {
		return totalEntryAmount;
	}

	public void setTotalEntryAmount(BigDecimal totalEntryAmount) {
		this.totalEntryAmount = totalEntryAmount;
	}

	public BigDecimal getTotalPurchaseAmount() {
		return totalPurchaseAmount;
	}

	public void setTotalPurchaseAmount(BigDecimal totalPurchaseAmount) {
		this.totalPurchaseAmount = totalPurchaseAmount;
	}
}
