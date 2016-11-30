package cn.wonhigh.retail.fas.common.dto;

import java.math.BigDecimal;

import cn.wonhigh.retail.fas.common.exportformat.BillInvoiceApplyExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.BillInvoiceApply;


/**
 * 地区批发-开票申请单(dto)
 * 
 * @author yang.y
 */
@ExportFormat(className=BillInvoiceApplyExportFormat.class)
public class BillInvoiceApplyDto extends BillInvoiceApply {

	private static final long serialVersionUID = 3397481086612988897L;

	/** 总金额 */
	private BigDecimal totalAmount;

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
}
