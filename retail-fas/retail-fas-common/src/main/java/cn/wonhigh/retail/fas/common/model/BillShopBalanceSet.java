package cn.wonhigh.retail.fas.common.model;

import cn.wonhigh.retail.fas.common.enums.BalanceDiffTypeEnum;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;

/**
 * 门店结算差异生成方式配置表 
 * @author yang.y
 * @date  2015-01-05 14:11:37
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
@ExportFormat(className=AbstractExportFormat.class)
public class BillShopBalanceSet extends FasBaseModel implements SequenceStrId{

	private static final long serialVersionUID = -8556881537270848115L;

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
     * 店铺全称
     */
    private String fullName;

    /**
     * 结算差异方式设置(1-按明细、2-按促销活动、3-按销售)
     */
    private Integer balanceDiffType;
    
    /**
     * 结算差异方式设置名称(1-按明细、2-按促销活动、3-按销售)
     */
    private String balanceDiffTypeName;

    /**
     * 备注
     */
    private String remark;

    public String getBalanceDiffTypeName() {
    	for(BalanceDiffTypeEnum s : BalanceDiffTypeEnum.values()) {
			if(getBalanceDiffType() != null && 
					s.getTypeNo().intValue() == getBalanceDiffType().intValue()) {
				return s.getTypeName();
			}
		}
		return balanceDiffTypeName;
	}

	public void setBalanceDiffTypeName(String balanceDiffTypeName) {
		this.balanceDiffTypeName = balanceDiffTypeName;
	}

	/**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_shop_balance_set.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_shop_balance_set.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_shop_balance_set.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_shop_balance_set.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_shop_balance_set.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_shop_balance_set.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_shop_balance_set.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_shop_balance_set.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of bill_shop_balance_set.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for bill_shop_balance_set.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBalanceDiffType() {
		return balanceDiffType;
	}

	public void setBalanceDiffType(Integer balanceDiffType) {
		this.balanceDiffType = balanceDiffType;
	}

	/**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_shop_balance_set.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_shop_balance_set.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}