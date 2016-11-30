package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-10 16:41:24
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
public class DepositSet extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String shopNo;

    private String shopName;

    private BigDecimal prepareCash;
    
    @JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date initDate;

    private BigDecimal startAmount;

    private Integer beyondLastDepositDate;

    private BigDecimal amount;

    private BigDecimal depositDiff;

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

    public BigDecimal getPrepareCash() {
        return prepareCash;
    }

    public void setPrepareCash(BigDecimal prepareCash) {
        this.prepareCash = prepareCash;
    }

    public BigDecimal getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(BigDecimal startAmount) {
        this.startAmount = startAmount;
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

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}
}