package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.CardReturnCheckExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.PaySaleCheckExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-19 20:40:27
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
@ExportFormat(className=CardReturnCheckExportFormat.class)
public class CardReturnCheck extends FasBaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5746349116101229824L;

	/**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺名称
     */
    private String shopName;
    
    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

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
     * 原单日期
     */
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date paywayTime;
    
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date oldOutDate;

    /**
     * 退换货日期
     */
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date returnTime;
    
    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date outDate;

    /**
     * 支付方式
     */
    private String returnPayType;
    private String payName;

    /**
     * 退款金额
     */
    private BigDecimal returnAmount;
    private BigDecimal amount;
    
    /**
     * 费率
     */
    private BigDecimal creditCardRate;
    
    /**
     * 财务确认费率
     */
    private BigDecimal rate;
    
    /**
     * 手续费
     */
    private BigDecimal poundage;
    
    /**
     * 实收金额
     */
    private BigDecimal paidinAmount;

    /**
     * 销售订单编码
     */
    private String saleOrderNo;
    private String businessNo;

    /**
     * 实际退款日期
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date actualReturnTime;

    /**
     * 商户编码
     */
    private String merchantsNo;

    /**
     * 刷卡账号
     */
    private String creditCardAccount;

    /**
     * 建档人
     */
    private String createUser;

    /**
     * 建档时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date createTime;
    
    /**
     * 总记录数
     */
    private int total;
    
    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of card_return_check.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for card_return_check.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of card_return_check.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for card_return_check.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #terminalNumber}
     *
     * @return the value of card_return_check.terminal_number
     */
    public String getTerminalNumber() {
        return terminalNumber;
    }

    /**
     * 
     * {@linkplain #terminalNumber}
     * @param terminalNumber the value for card_return_check.terminal_number
     */
    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    /**
     * 
     * {@linkplain #paywayTime}
     *
     * @return the value of card_return_check.payway_time
     */
    public Date getPaywayTime() {
        return paywayTime;
    }

    /**
     * 
     * {@linkplain #paywayTime}
     * @param paywayTime the value for card_return_check.payway_time
     */
    public void setPaywayTime(Date paywayTime) {
        this.paywayTime = paywayTime;
    }

    /**
     * 
     * {@linkplain #returnTime}
     *
     * @return the value of card_return_check.return_time
     */
    public Date getReturnTime() {
        return returnTime;
    }

    /**
     * 
     * {@linkplain #returnTime}
     * @param returnTime the value for card_return_check.return_time
     */
    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    /**
     * 
     * {@linkplain #returnPayType}
     *
     * @return the value of card_return_check.return_pay_type
     */
    public String getReturnPayType() {
        return returnPayType;
    }

    /**
     * 
     * {@linkplain #returnPayType}
     * @param returnPayType the value for card_return_check.return_pay_type
     */
    public void setReturnPayType(String returnPayType) {
        this.returnPayType = returnPayType;
    }

    /**
     * 
     * {@linkplain #returnAmount}
     *
     * @return the value of card_return_check.return_amount
     */
    public BigDecimal getReturnAmount() {
        return returnAmount;
    }

    /**
     * 
     * {@linkplain #returnAmount}
     * @param returnAmount the value for card_return_check.return_amount
     */
    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }

    /**
     * 
     * {@linkplain #saleOrderNo}
     *
     * @return the value of card_return_check.sale_order_no
     */
    public String getSaleOrderNo() {
        return saleOrderNo;
    }

    /**
     * 
     * {@linkplain #saleOrderNo}
     * @param saleOrderNo the value for card_return_check.sale_order_no
     */
    public void setSaleOrderNo(String saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    /**
     * 
     * {@linkplain #actualReturnTime}
     *
     * @return the value of card_return_check.actual_return_time
     */
    public Date getActualReturnTime() {
        return actualReturnTime;
    }

    /**
     * 
     * {@linkplain #actualReturnTime}
     * @param actualReturnTime the value for card_return_check.actual_return_time
     */
    public void setActualReturnTime(Date actualReturnTime) {
        this.actualReturnTime = actualReturnTime;
    }

    /**
     * 
     * {@linkplain #merchantsNo}
     *
     * @return the value of card_return_check.merchants_no
     */
    public String getMerchantsNo() {
        return merchantsNo;
    }

    /**
     * 
     * {@linkplain #merchantsNo}
     * @param merchantsNo the value for card_return_check.merchants_no
     */
    public void setMerchantsNo(String merchantsNo) {
        this.merchantsNo = merchantsNo;
    }

    /**
     * 
     * {@linkplain #creditCardAccount}
     *
     * @return the value of card_return_check.credit_card_account
     */
    public String getCreditCardAccount() {
        return creditCardAccount;
    }

    /**
     * 
     * {@linkplain #creditCardAccount}
     * @param creditCardAccount the value for card_return_check.credit_card_account
     */
    public void setCreditCardAccount(String creditCardAccount) {
        this.creditCardAccount = creditCardAccount;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of card_return_check.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for card_return_check.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of card_return_check.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for card_return_check.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public BigDecimal getPaidinAmount() {
		return paidinAmount;
	}

	public void setPaidinAmount(BigDecimal paidinAmount) {
		this.paidinAmount = paidinAmount;
	}

	public BigDecimal getCreditCardRate() {
		return creditCardRate;
	}

	public void setCreditCardRate(BigDecimal creditCardRate) {
		this.creditCardRate = creditCardRate;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
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

	public Date getOldOutDate() {
		return oldOutDate;
	}

	public void setOldOutDate(Date oldOutDate) {
		this.oldOutDate = oldOutDate;
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