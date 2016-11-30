package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2016-03-03 13:46:23
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
public class BillShopBalanceOperatelog implements Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺简称
     */
    private String shortName;

    /**
     * 月份
     */
    private String month;

    /**
     * 结算起始日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceStartDate;

    /**
     * 结算终止日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceEndDate;

    /**
     * 结算单据编号
     */
    private String balanceNo;

    /**
     * 操作编码(枚举表示:1-新增、2-修改、3-删除...)
     */
    private Byte operateNo;

    /**
     * 操作人
     */
    private String createUser;

    /**
     * 操作时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
    private Date createTime;

    /**
     * 分库字段(本部加大区)
     */
    private String shardingFlag;

    /**
     * 备注描述
     */
    private String remarkDesc;

    /**
     * 商场报数
     */
    private BigDecimal mallNumberAmount;

    /**
     * 商场开票金额
     */
    private BigDecimal mallBillingAmount;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of bill_shop_balance_operatelog.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for bill_shop_balance_operatelog.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_shop_balance_operatelog.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_shop_balance_operatelog.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_shop_balance_operatelog.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_shop_balance_operatelog.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_shop_balance_operatelog.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_shop_balance_operatelog.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_shop_balance_operatelog.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_shop_balance_operatelog.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of bill_shop_balance_operatelog.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for bill_shop_balance_operatelog.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     *
     * @return the value of bill_shop_balance_operatelog.balance_start_date
     */
    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     * @param balanceStartDate the value for bill_shop_balance_operatelog.balance_start_date
     */
    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     *
     * @return the value of bill_shop_balance_operatelog.balance_end_date
     */
    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     * @param balanceEndDate the value for bill_shop_balance_operatelog.balance_end_date
     */
    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of bill_shop_balance_operatelog.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for bill_shop_balance_operatelog.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #operateNo}
     *
     * @return the value of bill_shop_balance_operatelog.operate_no
     */
    public Byte getOperateNo() {
        return operateNo;
    }

    /**
     * 
     * {@linkplain #operateNo}
     * @param operateNo the value for bill_shop_balance_operatelog.operate_no
     */
    public void setOperateNo(Byte operateNo) {
        this.operateNo = operateNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of bill_shop_balance_operatelog.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for bill_shop_balance_operatelog.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of bill_shop_balance_operatelog.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for bill_shop_balance_operatelog.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     *
     * @return the value of bill_shop_balance_operatelog.sharding_flag
     */
    public String getShardingFlag() {
        return shardingFlag;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     * @param shardingFlag the value for bill_shop_balance_operatelog.sharding_flag
     */
    public void setShardingFlag(String shardingFlag) {
        this.shardingFlag = shardingFlag;
    }

    /**
     * 
     * {@linkplain #remarkDesc}
     *
     * @return the value of bill_shop_balance_operatelog.remark_desc
     */
    public String getRemarkDesc() {
        return remarkDesc;
    }

    /**
     * 
     * {@linkplain #remarkDesc}
     * @param remarkDesc the value for bill_shop_balance_operatelog.remark_desc
     */
    public void setRemarkDesc(String remarkDesc) {
        this.remarkDesc = remarkDesc;
    }

    /**
     * 
     * {@linkplain #mallNumberAmount}
     *
     * @return the value of bill_shop_balance_operatelog.mall_number_amount
     */
    public BigDecimal getMallNumberAmount() {
        return mallNumberAmount;
    }

    /**
     * 
     * {@linkplain #mallNumberAmount}
     * @param mallNumberAmount the value for bill_shop_balance_operatelog.mall_number_amount
     */
    public void setMallNumberAmount(BigDecimal mallNumberAmount) {
        this.mallNumberAmount = mallNumberAmount;
    }

    /**
     * 
     * {@linkplain #mallBillingAmount}
     *
     * @return the value of bill_shop_balance_operatelog.mall_billing_amount
     */
    public BigDecimal getMallBillingAmount() {
        return mallBillingAmount;
    }

    /**
     * 
     * {@linkplain #mallBillingAmount}
     * @param mallBillingAmount the value for bill_shop_balance_operatelog.mall_billing_amount
     */
    public void setMallBillingAmount(BigDecimal mallBillingAmount) {
        this.mallBillingAmount = mallBillingAmount;
    }
}