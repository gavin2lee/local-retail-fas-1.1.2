package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 汇率设置
 * @author ouyang.zm
 * @date  2014-10-29 15:53:33
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class ExchangeRateSetup extends FasBaseModel implements SequenceStrId {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8982051774684842673L;

	/**
     * 汇率编码
     */
    private String exchangeRateCode;

    /**
     * 源货币
     */
    private String sourceCurrency;

    /**
     * 目标货币
     */
    private String targetCurrency;

    /**
     * 转换系数
     */
    private BigDecimal conversionFactor;

    /**
     * 生效日期
     */
	@JsonSerialize(using = JsonDateSerializer$10.class)
    private Date effectiveDate;

    /**
     * 公司编码
     */
    private String companyNo;

    /**
     * 启用标志 1、启用 0、未启用
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date updateTime;

	/********扩展属性**********/
	private String sourceCurrencyName;
	private String targetCurrencyName;
	private String companyName;
	
    public String getSourceCurrencyName() {
		return sourceCurrencyName;
	}

	public void setSourceCurrencyName(String sourceCurrencyName) {
		this.sourceCurrencyName = sourceCurrencyName;
	}

	public String getTargetCurrencyName() {
		return targetCurrencyName;
	}

	public void setTargetCurrencyName(String targetCurrencyName) {
		this.targetCurrencyName = targetCurrencyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
     * 
     * {@linkplain #exchangeRateCode}
     *
     * @return the value of exchange_rate_setup.exchange_rate_code
     */
    public String getExchangeRateCode() {
        return exchangeRateCode;
    }

    /**
     * 
     * {@linkplain #exchangeRateCode}
     * @param exchangeRateCode the value for exchange_rate_setup.exchange_rate_code
     */
    public void setExchangeRateCode(String exchangeRateCode) {
        this.exchangeRateCode = exchangeRateCode;
    }

    /**
     * 
     * {@linkplain #sourceCurrency}
     *
     * @return the value of exchange_rate_setup.source_currency
     */
    public String getSourceCurrency() {
        return sourceCurrency;
    }

    /**
     * 
     * {@linkplain #sourceCurrency}
     * @param sourceCurrency the value for exchange_rate_setup.source_currency
     */
    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    /**
     * 
     * {@linkplain #targetCurrency}
     *
     * @return the value of exchange_rate_setup.target_currency
     */
    public String getTargetCurrency() {
        return targetCurrency;
    }

    /**
     * 
     * {@linkplain #targetCurrency}
     * @param targetCurrency the value for exchange_rate_setup.target_currency
     */
    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    /**
     * 
     * {@linkplain #conversionFactor}
     *
     * @return the value of exchange_rate_setup.conversion_factor
     */
    public BigDecimal getConversionFactor() {
        return conversionFactor;
    }

    /**
     * 
     * {@linkplain #conversionFactor}
     * @param conversionFactor the value for exchange_rate_setup.conversion_factor
     */
    public void setConversionFactor(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    /**
     * 
     * {@linkplain #effectiveDate}
     *
     * @return the value of exchange_rate_setup.effective_date
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * 
     * {@linkplain #effectiveDate}
     * @param effectiveDate the value for exchange_rate_setup.effective_date
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of exchange_rate_setup.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for exchange_rate_setup.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }


    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of exchange_rate_setup.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for exchange_rate_setup.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of exchange_rate_setup.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for exchange_rate_setup.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of exchange_rate_setup.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for exchange_rate_setup.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of exchange_rate_setup.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for exchange_rate_setup.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}