package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-11-27 10:46:43
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
public class BillMallDeductionCost extends FasBaseModel   implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 管理城市编号
     */
    private String organNo;

    /**
     * 管理城市名称
     */
    private String organName;

    /**
     * 商业集团编码
     */
    private String bsgroupsNo;

    /**
     * 商业集团名称
     */
    private String bsgroupsName;

    /**
     * 商场编码
     */
    private String mallNo;

    /**
     * 商场名称
     */
    private String mallName;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺简称
     */
    private String shopName;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌编码
     */
    private String brandName;

    /**
     * 费用所属期间-会计期间结算月
     */
    private String period;

    /**
     * NC月份
     */
    private String ncPeriod;

    /**
     * 费用性质(1-合同内,2-合同外)
     */
    private Byte costType;

    /**
     * 费用扣取方式(1-票前,2-票后)
     */
    private Byte costDeductType;

    /**
     * 费用交款方式(1-帐扣,2-现金)
     */
    private Byte costPayType;

    /**
     * 费用类别编码
     */
    private String costCateCode;

    /**
     * 费用类别名称
     */
    private String costCateName;

    /**
     * 商场扣费编码
     */
    private String deductionCode;

    /**
     * 商场扣费名称
     */
    private String deductionName;
    /**
     * 业务单据日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date billDate;

    /**
     * 扣费金额
     */
    private BigDecimal deductAmount;

    /**
     * 实际金额
     */
    private BigDecimal actualAmount;

//    /**
//     * 建档人
//     */
//    private String createUser;
//
//    /**
//     * 建档时间
//     */
//    private Date createTime;
//
//    /**
//     * 修改人
//     */
//    private String updateUser;
//
//    /**
//     * 修改时间
//     */
//    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


    /**
     * 
     * {@linkplain #billNo}
     *
     * @return the value of bill_mall_deduction_cost.bill_no
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 
     * {@linkplain #billNo}
     * @param billNo the value for bill_mall_deduction_cost.bill_no
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_mall_deduction_cost.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_mall_deduction_cost.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_mall_deduction_cost.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_mall_deduction_cost.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of bill_mall_deduction_cost.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for bill_mall_deduction_cost.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of bill_mall_deduction_cost.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for bill_mall_deduction_cost.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     *
     * @return the value of bill_mall_deduction_cost.bsgroups_no
     */
    public String getBsgroupsNo() {
        return bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     * @param bsgroupsNo the value for bill_mall_deduction_cost.bsgroups_no
     */
    public void setBsgroupsNo(String bsgroupsNo) {
        this.bsgroupsNo = bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     *
     * @return the value of bill_mall_deduction_cost.bsgroups_name
     */
    public String getBsgroupsName() {
        return bsgroupsName;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     * @param bsgroupsName the value for bill_mall_deduction_cost.bsgroups_name
     */
    public void setBsgroupsName(String bsgroupsName) {
        this.bsgroupsName = bsgroupsName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of bill_mall_deduction_cost.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for bill_mall_deduction_cost.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of bill_mall_deduction_cost.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for bill_mall_deduction_cost.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_mall_deduction_cost.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_mall_deduction_cost.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of bill_mall_deduction_cost.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for bill_mall_deduction_cost.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_mall_deduction_cost.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_mall_deduction_cost.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_mall_deduction_cost.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_mall_deduction_cost.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #period}
     *
     * @return the value of bill_mall_deduction_cost.period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * 
     * {@linkplain #period}
     * @param period the value for bill_mall_deduction_cost.period
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * 
     * {@linkplain #ncPeriod}
     *
     * @return the value of bill_mall_deduction_cost.nc_period
     */
    public String getNcPeriod() {
        return ncPeriod;
    }

    /**
     * 
     * {@linkplain #ncPeriod}
     * @param ncPeriod the value for bill_mall_deduction_cost.nc_period
     */
    public void setNcPeriod(String ncPeriod) {
        this.ncPeriod = ncPeriod;
    }

    /**
     * 
     * {@linkplain #costType}
     *
     * @return the value of bill_mall_deduction_cost.cost_type
     */
    public Byte getCostType() {
        return costType;
    }

    /**
     * 
     * {@linkplain #costType}
     * @param costType the value for bill_mall_deduction_cost.cost_type
     */
    public void setCostType(Byte costType) {
        this.costType = costType;
    }

    /**
     * 
     * {@linkplain #costDeductType}
     *
     * @return the value of bill_mall_deduction_cost.cost_deduct_type
     */
    public Byte getCostDeductType() {
        return costDeductType;
    }

    /**
     * 
     * {@linkplain #costDeductType}
     * @param costDeductType the value for bill_mall_deduction_cost.cost_deduct_type
     */
    public void setCostDeductType(Byte costDeductType) {
        this.costDeductType = costDeductType;
    }

    /**
     * 
     * {@linkplain #costPayType}
     *
     * @return the value of bill_mall_deduction_cost.cost_pay_type
     */
    public Byte getCostPayType() {
        return costPayType;
    }

    /**
     * 
     * {@linkplain #costPayType}
     * @param costPayType the value for bill_mall_deduction_cost.cost_pay_type
     */
    public void setCostPayType(Byte costPayType) {
        this.costPayType = costPayType;
    }

    /**
     * 
     * {@linkplain #costCateCode}
     *
     * @return the value of bill_mall_deduction_cost.cost_cate_code
     */
    public String getCostCateCode() {
        return costCateCode;
    }

    /**
     * 
     * {@linkplain #costCateCode}
     * @param costCateCode the value for bill_mall_deduction_cost.cost_cate_code
     */
    public void setCostCateCode(String costCateCode) {
        this.costCateCode = costCateCode;
    }

    /**
     * 
     * {@linkplain #costCateName}
     *
     * @return the value of bill_mall_deduction_cost.cost_cate_name
     */
    public String getCostCateName() {
        return costCateName;
    }

    /**
     * 
     * {@linkplain #costCateName}
     * @param costCateName the value for bill_mall_deduction_cost.cost_cate_name
     */
    public void setCostCateName(String costCateName) {
        this.costCateName = costCateName;
    }

    /**
     * 
     * {@linkplain #deductionCode}
     *
     * @return the value of bill_mall_deduction_cost.deduction_code
     */
    public String getDeductionCode() {
        return deductionCode;
    }

    /**
     * 
     * {@linkplain #deductionCode}
     * @param deductionCode the value for bill_mall_deduction_cost.deduction_code
     */
    public void setDeductionCode(String deductionCode) {
        this.deductionCode = deductionCode;
    }

    /**
     * 
     * {@linkplain #deductionName}
     *
     * @return the value of bill_mall_deduction_cost.deduction_name
     */
    public String getDeductionName() {
        return deductionName;
    }

    /**
     * 
     * {@linkplain #deductionName}
     * @param deductionName the value for bill_mall_deduction_cost.deduction_name
     */
    public void setDeductionName(String deductionName) {
        this.deductionName = deductionName;
    }

    /**
     * 
     * {@linkplain #billDate}
     *
     * @return the value of bill_mall_deduction_cost.bill_date
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     * @param billDate the value for bill_mall_deduction_cost.bill_date
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * {@linkplain #deductAmount}
     *
     * @return the value of bill_mall_deduction_cost.deduct_amount
     */
    public BigDecimal getDeductAmount() {
        return deductAmount;
    }

    /**
     * 
     * {@linkplain #deductAmount}
     * @param deductAmount the value for bill_mall_deduction_cost.deduct_amount
     */
    public void setDeductAmount(BigDecimal deductAmount) {
        this.deductAmount = deductAmount;
    }

    /**
     * 
     * {@linkplain #actualAmount}
     *
     * @return the value of bill_mall_deduction_cost.actual_amount
     */
    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    /**
     * 
     * {@linkplain #actualAmount}
     * @param actualAmount the value for bill_mall_deduction_cost.actual_amount
     */
    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

//    /**
//     * 
//     * {@linkplain #createUser}
//     *
//     * @return the value of bill_mall_deduction_cost.create_user
//     */
//    public String getCreateUser() {
//        return createUser;
//    }
//
//    /**
//     * 
//     * {@linkplain #createUser}
//     * @param createUser the value for bill_mall_deduction_cost.create_user
//     */
//    public void setCreateUser(String createUser) {
//        this.createUser = createUser;
//    }
//
//    /**
//     * 
//     * {@linkplain #createTime}
//     *
//     * @return the value of bill_mall_deduction_cost.create_time
//     */
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    /**
//     * 
//     * {@linkplain #createTime}
//     * @param createTime the value for bill_mall_deduction_cost.create_time
//     */
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateUser}
//     *
//     * @return the value of bill_mall_deduction_cost.update_user
//     */
//    public String getUpdateUser() {
//        return updateUser;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateUser}
//     * @param updateUser the value for bill_mall_deduction_cost.update_user
//     */
//    public void setUpdateUser(String updateUser) {
//        this.updateUser = updateUser;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateTime}
//     *
//     * @return the value of bill_mall_deduction_cost.update_time
//     */
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    /**
//     * 
//     * {@linkplain #updateTime}
//     * @param updateTime the value for bill_mall_deduction_cost.update_time
//     */
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_mall_deduction_cost.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_mall_deduction_cost.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}