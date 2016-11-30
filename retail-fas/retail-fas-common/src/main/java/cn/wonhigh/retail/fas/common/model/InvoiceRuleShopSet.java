package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-15 14:29:23
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
public class InvoiceRuleShopSet extends FasBaseModel  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6940327409334430421L;

    /**
     * 店铺分组编号
     */
    private String shopGroupNo;

    /**
     * 开票申请规则编号
     */
    private String invoiceRuleNo;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺简称
     */
    private String shortName;

    /**
     * 店铺全称
     */
    private String fullName;

    /**
     * 分组标识
     */
    private Byte groupFlag;

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
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #shopGroupNo}
     *
     * @return the value of invoice_rule_shop_set.shop_group_no
     */
    public String getShopGroupNo() {
        return shopGroupNo;
    }

    /**
     * 
     * {@linkplain #shopGroupNo}
     * @param shopGroupNo the value for invoice_rule_shop_set.shop_group_no
     */
    public void setShopGroupNo(String shopGroupNo) {
        this.shopGroupNo = shopGroupNo;
    }

    /**
     * 
     * {@linkplain #invoiceRuleNo}
     *
     * @return the value of invoice_rule_shop_set.invoice_rule_no
     */
    public String getInvoiceRuleNo() {
        return invoiceRuleNo;
    }

    /**
     * 
     * {@linkplain #invoiceRuleNo}
     * @param invoiceRuleNo the value for invoice_rule_shop_set.invoice_rule_no
     */
    public void setInvoiceRuleNo(String invoiceRuleNo) {
        this.invoiceRuleNo = invoiceRuleNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of invoice_rule_shop_set.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for invoice_rule_shop_set.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of invoice_rule_shop_set.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for invoice_rule_shop_set.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of invoice_rule_shop_set.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for invoice_rule_shop_set.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #groupFlag}
     *
     * @return the value of invoice_rule_shop_set.group_flag
     */
    public Byte getGroupFlag() {
        return groupFlag;
    }

    /**
     * 
     * {@linkplain #groupFlag}
     * @param groupFlag the value for invoice_rule_shop_set.group_flag
     */
    public void setGroupFlag(Byte groupFlag) {
        this.groupFlag = groupFlag;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of invoice_rule_shop_set.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for invoice_rule_shop_set.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of invoice_rule_shop_set.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for invoice_rule_shop_set.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of invoice_rule_shop_set.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for invoice_rule_shop_set.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of invoice_rule_shop_set.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for invoice_rule_shop_set.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of invoice_rule_shop_set.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for invoice_rule_shop_set.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}