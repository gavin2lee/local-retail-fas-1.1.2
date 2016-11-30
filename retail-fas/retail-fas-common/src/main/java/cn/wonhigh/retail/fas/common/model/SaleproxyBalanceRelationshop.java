package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2016-08-17 14:05:33
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
public class SaleproxyBalanceRelationshop extends FasBaseModel implements Comparable<SaleproxyBalanceRelationshop>{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -2920547469855441258L;

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
     * 品牌部编号
     */
    private String brandUnitNo;

    /**
     * 品牌部名称
     */
    private String brandUnitName;

    /**
     * 受托代销公司编号
     */
    private String buyerNo;

    /**
     * 受托代销公司名称
     */
    private String buyerName;

    /**
     * 委托代销公司编号
     */
    private String salerNo;

    /**
     * 委托代销公司名称
     */
    private String salerName;

    /**
     * 生效日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date effectiveDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 分库字段(本部加大区)
     */
    private String shardingFlag;

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of saleproxy_balance_relationshop.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for saleproxy_balance_relationshop.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of saleproxy_balance_relationshop.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for saleproxy_balance_relationshop.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of saleproxy_balance_relationshop.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for saleproxy_balance_relationshop.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     *
     * @return the value of saleproxy_balance_relationshop.brand_unit_no
     */
    public String getBrandUnitNo() {
        return brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     * @param brandUnitNo the value for saleproxy_balance_relationshop.brand_unit_no
     */
    public void setBrandUnitNo(String brandUnitNo) {
        this.brandUnitNo = brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     *
     * @return the value of saleproxy_balance_relationshop.brand_unit_name
     */
    public String getBrandUnitName() {
        return brandUnitName;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     * @param brandUnitName the value for saleproxy_balance_relationshop.brand_unit_name
     */
    public void setBrandUnitName(String brandUnitName) {
        this.brandUnitName = brandUnitName;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of saleproxy_balance_relationshop.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for saleproxy_balance_relationshop.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(buyerNo));
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of saleproxy_balance_relationshop.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for saleproxy_balance_relationshop.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of saleproxy_balance_relationshop.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for saleproxy_balance_relationshop.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of saleproxy_balance_relationshop.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for saleproxy_balance_relationshop.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #effectiveDate}
     *
     * @return the value of saleproxy_balance_relationshop.effective_date
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * 
     * {@linkplain #effectiveDate}
     * @param effectiveDate the value for saleproxy_balance_relationshop.effective_date
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of saleproxy_balance_relationshop.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for saleproxy_balance_relationshop.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     *
     * @return the value of saleproxy_balance_relationshop.sharding_flag
     */
    public String getShardingFlag() {
        return shardingFlag;
    }

    /**
     * 
     * {@linkplain #shardingFlag}
     * @param shardingFlag the value for saleproxy_balance_relationshop.sharding_flag
     */
    public void setShardingFlag(String shardingFlag) {
        this.shardingFlag = shardingFlag;
    }

	@Override
	public int compareTo(SaleproxyBalanceRelationshop o) {
		// TODO Auto-generated method stub
		return 0;
	}
}