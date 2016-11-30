package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-13 10:07:17
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
public class DepositCheck extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2059442852004189820L;

	private String id;

    private String shopNo;

    private String shopName;

    @JsonSerialize(using=JsonDateSerializer$10.class)
    private Date checkDate;

    private Integer beyondLastDepositDate;

    private BigDecimal amount;
    
    private BigDecimal saleAmount;

    private BigDecimal depositDiff;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getBeyondLastDepositDate() {
        return beyondLastDepositDate;
    }

    public void setBeyondLastDepositDate(Integer beyondLastDepositDate) {
        this.beyondLastDepositDate = beyondLastDepositDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDepositDiff() {
        return depositDiff;
    }

    public void setDepositDiff(BigDecimal depositDiff) {
        this.depositDiff = depositDiff;
    }

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
}