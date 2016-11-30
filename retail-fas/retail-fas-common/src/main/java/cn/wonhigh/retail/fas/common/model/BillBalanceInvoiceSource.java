package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-01-05 15:30:06
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class BillBalanceInvoiceSource implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1712708735056513544L;

    /**
     * 分库字段
     */
    private String shardingFlag;
    
	/**
     * 主键
     */
    private String id;

    /**
     * 发票申请单号
     */
    private String billNo;

    /**
     * 结算单据编号
     */
    private String balanceNo;

    /**
     * 结算类型(1、总部厂商结算,2、总部批发结算, 3、总部其他结算,4、地区采购结算 5、地区间结算 6、地区自购结算 7、地区批发结算 8、地区团购结算 9、地区员购结算 10、地区商场结算 11、地区其他出库结算)
     */
    private Integer balanceType;

    /**
     * 开票金额
     */
    private BigDecimal amount;
    
    /**
     * 结算名称
     */
    private String balanceTypeStr;
    
    /**
     * 单据类型名称
     */
    private String billTypeName;
    
    /**
     * 公司编码(开票方)-卖方
     */
    private String salerNo;

    public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_balance_invoice_source.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_balance_invoice_source.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_balance_invoice_source.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_balance_invoice_source.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_balance_invoice_source.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_balance_invoice_source.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceType}
     *
     * @return the value of bill_balance_invoice_source.balance_type
     */
    public Integer getBalanceType() {
        return balanceType;
    }

    /**
     * 
     * {@linkplain #balanceType}
     * @param balanceType the value for bill_balance_invoice_source.balance_type
     */
    public void setBalanceType(Integer balanceType) {
    	this.balanceTypeStr = BalanceTypeEnums.getTypeNameByNo(balanceType);
        this.balanceType = balanceType;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of bill_balance_invoice_source.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for bill_balance_invoice_source.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

	public String getBalanceTypeStr() {
		return balanceTypeStr;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}
	
	/**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of bill_balance_invoice_source.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for bill_balance_invoice_source.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(salerNo));
    }
}