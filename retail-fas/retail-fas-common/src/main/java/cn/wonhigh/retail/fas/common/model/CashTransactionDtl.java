package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途
 * 
 * @author zhouxm
 * @date 2014-10-13 17:36:27
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public class CashTransactionDtl extends FasBaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1890653702170906546L;

	/**
	 * 卡号
	 */
	private String cardNumber;

	/**
	 * 存现日期
	 */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date depositCashTime;

	/**
	 * 存现金额
	 */
	private BigDecimal depositAmount;
	
	/**
	 * 存现地点
	 */
	private String depositSite;

	/**
	 * 店铺编码
	 */
	private String shopNo;

	/**
	 * 店铺名称
	 */
	private String shopName;

	/**
	 * 商场编码
	 */
	private String mallNo;

	/**
	 * 商场名称
	 */
	private String mallName;

	/**
	 * 审核人
	 */
	private String auditor;

	/**
	 * 审核时间
	 */
	private Date auditTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 
	 * {@linkplain #cardNumber}
	 * 
	 * @return the value of cash_transaction_dtl.card_number
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * 
	 * {@linkplain #cardNumber}
	 * 
	 * @param cardNumber
	 *            the value for cash_transaction_dtl.card_number
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * 
	 * {@linkplain #depositCashTime}
	 * 
	 * @return the value of cash_transaction_dtl.deposit_cash_time
	 */
	public Date getDepositCashTime() {
		return depositCashTime;
	}

	/**
	 * 
	 * {@linkplain #depositCashTime}
	 * 
	 * @param depositCashTime
	 *            the value for cash_transaction_dtl.deposit_cash_time
	 */
	public void setDepositCashTime(Date depositCashTime) {
		this.depositCashTime = depositCashTime;
	}

	/**
	 * 
	 * {@linkplain #depositAmount}
	 * 
	 * @return the value of cash_transaction_dtl.deposit_amount
	 */
	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	/**
	 * 
	 * {@linkplain #depositAmount}
	 * 
	 * @param depositAmount
	 *            the value for cash_transaction_dtl.deposit_amount
	 */
	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	/**
	 * 
	 * {@linkplain #mallNo}
	 * 
	 * @return the value of cash_transaction_dtl.mall_no
	 */
	public String getMallNo() {
		return mallNo;
	}

	/**
	 * 
	 * {@linkplain #mallNo}
	 * 
	 * @param mallNo
	 *            the value for cash_transaction_dtl.mall_no
	 */
	public void setMallNo(String mallNo) {
		this.mallNo = mallNo;
	}

	/**
	 * 
	 * {@linkplain #mallName}
	 * 
	 * @return the value of cash_transaction_dtl.mall_name
	 */
	public String getMallName() {
		return mallName;
	}

	/**
	 * 
	 * {@linkplain #mallName}
	 * 
	 * @param mallName
	 *            the value for cash_transaction_dtl.mall_name
	 */
	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	/**
	 * 
	 * {@linkplain #auditor}
	 * 
	 * @return the value of cash_transaction_dtl.auditor
	 */
	public String getAuditor() {
		return auditor;
	}

	/**
	 * 
	 * {@linkplain #auditor}
	 * 
	 * @param auditor
	 *            the value for cash_transaction_dtl.auditor
	 */
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	/**
	 * 
	 * {@linkplain #auditTime}
	 * 
	 * @return the value of cash_transaction_dtl.audit_time
	 */
	public Date getAuditTime() {
		return auditTime;
	}

	/**
	 * 
	 * {@linkplain #auditTime}
	 * 
	 * @param auditTime
	 *            the value for cash_transaction_dtl.audit_time
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * 
	 * {@linkplain #remark}
	 * 
	 * @return the value of cash_transaction_dtl.remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 
	 * {@linkplain #remark}
	 * 
	 * @param remark
	 *            the value for cash_transaction_dtl.remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShopNo() {
		return shopNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getDepositSite() {
		return depositSite;
	}

	public void setDepositSite(String depositSite) {
		this.depositSite = depositSite;
	}
	
}