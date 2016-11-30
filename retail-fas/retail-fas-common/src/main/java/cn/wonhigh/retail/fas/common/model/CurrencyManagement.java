package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 币种管理
 * @author ouyang.zm
 * @date  2015-01-16 14:34:30
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
public class CurrencyManagement extends FasBaseModel implements SequenceStrId {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6638150951425949989L;


    /**
     * 币种编码
     */
    private String currencyCode;

    /**
     * 币种名称
     */
    private String currencyName;

    /**
     * 币种标识
     */
    private String currencySymbol;

    /**
     * 是否本位币：0、否 1、是
     */
    private Byte standardMoney;

    /**
     * 启用标志 1、已启用 0、已停用
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

    /**
     * 
     * {@linkplain #currencyCode}
     *
     * @return the value of currency_management.currency_code
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * 
     * {@linkplain #currencyCode}
     * @param currencyCode the value for currency_management.currency_code
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * 
     * {@linkplain #currencyName}
     *
     * @return the value of currency_management.currency_name
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 
     * {@linkplain #currencyName}
     * @param currencyName the value for currency_management.currency_name
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 
     * {@linkplain #currencySymbol}
     *
     * @return the value of currency_management.currency_symbol
     */
    public String getCurrencySymbol() {
        return currencySymbol;
    }

    /**
     * 
     * {@linkplain #currencySymbol}
     * @param currencySymbol the value for currency_management.currency_symbol
     */
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    /**
     * 
     * {@linkplain #standardMoney}
     *
     * @return the value of currency_management.standard_money
     */
    public Byte getStandardMoney() {
        return standardMoney;
    }

    /**
     * 
     * {@linkplain #standardMoney}
     * @param standardMoney the value for currency_management.standard_money
     */
    public void setStandardMoney(Byte standardMoney) {
        this.standardMoney = standardMoney;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of currency_management.status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for currency_management.status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of currency_management.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for currency_management.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of currency_management.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for currency_management.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of currency_management.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for currency_management.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of currency_management.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for currency_management.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}