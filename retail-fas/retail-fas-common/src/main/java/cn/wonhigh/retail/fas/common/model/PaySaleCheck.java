package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.PaySaleCheckExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-18 10:55:57
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
@ExportFormat(className=PaySaleCheckExportFormat.class)
public class PaySaleCheck extends FasBaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6454379833242026482L;

	private String rowId;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺名称
     */
    private String shopName;
    
    /**
     * 公司编码
     */
    private String companyNo;
    
    /**
     * 公司名称
     */
    private String companyName;
    
    /**
     * 商场编码
     */
    private String mallNo;
    
    /**
     * 商场名称
     */
    private String mallName;

    /**
     * 终端号
     */
    private String terminalNumber;

    /**
     * 销售日期
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date saleTime;
    
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date outDate;

    /**
     * 支付方式
     */
    private String payType;
    
    private String payName;

    /**
     * 销售金额
     */
    private BigDecimal saleAmount;
    
    private BigDecimal amount;
    
    /**
     * 费率
     */
    private BigDecimal creditCardRate;
    
    /**
     * 手续费
     */
    private BigDecimal poundage;
    
    /**
     * 采取已确认手续费
     */
    private BigDecimal rate;
    
    /**
     * 实收金额
     */
    private BigDecimal paidinAmount;
    
    /**
     * 确认时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date auditorTime;

    /**
     * 销售订单编码
     */
    private String salesOrderNo;
    
    private String orderNo;

    /**
     * 商户编码
     */
    private String merchantsNo;

    /**
     * 刷卡账号
     */
    private String cardNumber;
    
    private String creditCardAccount;

    /**
     * 总记录数
     */
    private int total;


    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of pay_sale_check.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for pay_sale_check.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of pay_sale_check.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for pay_sale_check.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #terminalNumber}
     *
     * @return the value of pay_sale_check.terminal_number
     */
    public String getTerminalNumber() {
        return terminalNumber;
    }

    /**
     * 
     * {@linkplain #terminalNumber}
     * @param terminalNumber the value for pay_sale_check.terminal_number
     */
    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    /**
     * 
     * {@linkplain #saleTime}
     *
     * @return the value of pay_sale_check.sale_time
     */
    public Date getSaleTime() {
        return saleTime;
    }

    /**
     * 
     * {@linkplain #saleTime}
     * @param saleTime the value for pay_sale_check.sale_time
     */
    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }

    /**
     * 
     * {@linkplain #payType}
     *
     * @return the value of pay_sale_check.pay_type
     */
    public String getPayType() {
        return payType;
    }

    /**
     * 
     * {@linkplain #payType}
     * @param payType the value for pay_sale_check.pay_type
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     *
     * @return the value of pay_sale_check.sale_amount
     */
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    /**
     * 
     * {@linkplain #saleAmount}
     * @param saleAmount the value for pay_sale_check.sale_amount
     */
    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    /**
     * 
     * {@linkplain #salesOrderNo}
     *
     * @return the value of pay_sale_check.sales_order_no
     */
    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    /**
     * 
     * {@linkplain #salesOrderNo}
     * @param salesOrderNo the value for pay_sale_check.sales_order_no
     */
    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    /**
     * 
     * {@linkplain #merchantsNo}
     *
     * @return the value of pay_sale_check.merchants_no
     */
    public String getMerchantsNo() {
        return merchantsNo;
    }

    /**
     * 
     * {@linkplain #merchantsNo}
     * @param merchantsNo the value for pay_sale_check.merchants_no
     */
    public void setMerchantsNo(String merchantsNo) {
        this.merchantsNo = merchantsNo;
    }

    /**
     * 
     * {@linkplain #cardNumber}
     *
     * @return the value of pay_sale_check.card_number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * 
     * {@linkplain #cardNumber}
     * @param cardNumber the value for pay_sale_check.card_number
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public BigDecimal getCreditCardRate() {
		return creditCardRate;
	}

	public void setCreditCardRate(BigDecimal creditCardRate) {
		this.creditCardRate = creditCardRate;
	}

	/**
	 * {@linkplain #poundage}
     *
     * @return 
	 */
	public BigDecimal getPoundage() {
		return poundage;
	}
	
	/**
	 * {@linkplain #poundage}
     * @param 
	 */
	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	/**
	 * {@linkplain #amount}
	 * @return
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * {@linkplain #amount}
     * @param payType the value for pay_sale_check.pay_type
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreditCardAccount() {
		return creditCardAccount;
	}

	public void setCreditCardAccount(String creditCardAccount) {
		this.creditCardAccount = creditCardAccount;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public BigDecimal getPaidinAmount() {
		return paidinAmount;
	}

	public void setPaidinAmount(BigDecimal paidinAmount) {
		this.paidinAmount = paidinAmount;
	}

	public Date getAuditorTime() {
		return auditorTime;
	}

	public void setAuditorTime(Date auditorTime) {
		this.auditorTime = auditorTime;
	}

	public String getMallNo() {
		return mallNo;
	}

	public void setMallNo(String mallNo) {
		this.mallNo = mallNo;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
}