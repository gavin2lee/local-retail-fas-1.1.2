/**  
 *   
 * 项目名称：retail-fas-common  
 * 类名称：InvoiceConfirm  
 * 类描述：到票确认
 * 创建人：ouyang.zm  
 * 创建时间：2014-12-3 下午2:49:49  
 * @version       
 */
package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

public class InvoiceConfirm implements Serializable {

	private static final long serialVersionUID = -5071414497371767779L;

	/**
	 * 结算金额
	 */
    @JsonSerialize(using=BigDecimalSerializer$2.class)
	private BigDecimal balanceAmount;

    /**
     * 结算单号
     */
    private String BalanceNo;
    
	/**
	 * 确认人
	 */
	private String auditor;

	/**
	 * 确认日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date auditDate;


	public String getBalanceNo() {
		return BalanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		BalanceNo = balanceNo;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
}
