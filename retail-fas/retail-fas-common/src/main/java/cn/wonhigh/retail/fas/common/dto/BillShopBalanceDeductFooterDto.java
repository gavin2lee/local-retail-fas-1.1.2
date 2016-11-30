package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.wonhigh.retail.fas.common.annotation.ClassReflect;
import cn.wonhigh.retail.fas.common.annotation.FieldReflect;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;

/**
 * 费用页脚汇总对象
 * 
 * @author yang.y
 */
@ClassReflect(reflectClass=BillShopBalanceDeduct.class)
public class BillShopBalanceDeductFooterDto implements Serializable {

	private static final long serialVersionUID = 4673255290504989357L;

	@FieldReflect(name="deductAmount")
	private BigDecimal totalDeductAmount;
	
	@FieldReflect(name="actualAmount")
	private BigDecimal totalActualAmount;
	
	@FieldReflect(name="diffAmount")
	private BigDecimal totalDiffAmount;

	public BigDecimal getTotalDeductAmount() {
		return totalDeductAmount;
	}

	public void setTotalDeductAmount(BigDecimal totalDeductAmount) {
		this.totalDeductAmount = totalDeductAmount;
	}

	public BigDecimal getTotalActualAmount() {
		return totalActualAmount;
	}

	public void setTotalActualAmount(BigDecimal totalActualAmount) {
		this.totalActualAmount = totalActualAmount;
	}

	public BigDecimal getTotalDiffAmount() {
		return totalDiffAmount;
	}

	public void setTotalDiffAmount(BigDecimal totalDiffAmount) {
		this.totalDiffAmount = totalDiffAmount;
	}
}
