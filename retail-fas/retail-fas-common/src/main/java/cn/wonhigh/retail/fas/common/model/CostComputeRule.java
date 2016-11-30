package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 参考价格计算规则
 * @author user
 * @date  2015-06-09 14:48:18
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
public class CostComputeRule extends FasBaseModel implements SequenceStrId {

    /**
	 * 
	 */
	private static final long serialVersionUID = -858825700737164265L;

	/**
     * 规则编码
     */
    private String ruleCode;

    /**
     * 公司编码
     */
    private String companyNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 起始年份编码
     */
    private String startYearCode;

    /**
     * 起始年份名称
     */
    private String startYearName;

    /**
     * 起始季节编码
     */
    private String startSeasonCode;

    /**
     * 起始季节名称
     */
    private String startSeasonName;

    /**
     * 终止年份编码
     */
    private String endYearCode;

    /**
     * 终止年份名称
     */
    private String endYearName;

    /**
     * 终止季节编码
     */
    private String endSeasonCode;

    /**
     * 终止季节名称
     */
    private String endSeasonName;

    /**
     * 结算规则 1、乘 2、加
     */
    private Byte computeRule;

    /**
     * 加权成本系数
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal weightedCostCoeff;

    /**
     * 总部成本系数
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal hqCostCoeff;

    /**
     * 地区成本系数
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal areaCostCoeff;

    /**
     * 启用状态 1、启用 0、停用
     */
    private Integer status;

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
     * 品牌部编码(备用)
     */
    private String brandUnitNo;

    /**
     * 品牌部名称(备用)
     */
    private String brandUnitName;

    /**
     * 起始年份季节(备用)
     */
    private String startYearSeason;

    /**
     * 终止年份季节(备用)
     */
    private String endYearSeason;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #ruleCode}
     *
     * @return the value of cost_compute_rule.rule_code
     */
    public String getRuleCode() {
        return ruleCode;
    }

    /**
     * 
     * {@linkplain #ruleCode}
     * @param ruleCode the value for cost_compute_rule.rule_code
     */
    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of cost_compute_rule.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for cost_compute_rule.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of cost_compute_rule.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for cost_compute_rule.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of cost_compute_rule.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for cost_compute_rule.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of cost_compute_rule.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for cost_compute_rule.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #startYearCode}
     *
     * @return the value of cost_compute_rule.start_year_code
     */
    public String getStartYearCode() {
        return startYearCode;
    }

    /**
     * 
     * {@linkplain #startYearCode}
     * @param startYearCode the value for cost_compute_rule.start_year_code
     */
    public void setStartYearCode(String startYearCode) {
        this.startYearCode = startYearCode;
    }

    /**
     * 
     * {@linkplain #startYearName}
     *
     * @return the value of cost_compute_rule.start_year_name
     */
    public String getStartYearName() {
        return startYearName;
    }

    /**
     * 
     * {@linkplain #startYearName}
     * @param startYearName the value for cost_compute_rule.start_year_name
     */
    public void setStartYearName(String startYearName) {
        this.startYearName = startYearName;
    }

    /**
     * 
     * {@linkplain #startSeasonCode}
     *
     * @return the value of cost_compute_rule.start_season_code
     */
    public String getStartSeasonCode() {
        return startSeasonCode;
    }

    /**
     * 
     * {@linkplain #startSeasonCode}
     * @param startSeasonCode the value for cost_compute_rule.start_season_code
     */
    public void setStartSeasonCode(String startSeasonCode) {
        this.startSeasonCode = startSeasonCode;
    }

    /**
     * 
     * {@linkplain #startSeasonName}
     *
     * @return the value of cost_compute_rule.start_season_name
     */
    public String getStartSeasonName() {
        return startSeasonName;
    }

    /**
     * 
     * {@linkplain #startSeasonName}
     * @param startSeasonName the value for cost_compute_rule.start_season_name
     */
    public void setStartSeasonName(String startSeasonName) {
        this.startSeasonName = startSeasonName;
    }

    /**
     * 
     * {@linkplain #endYearCode}
     *
     * @return the value of cost_compute_rule.end_year_code
     */
    public String getEndYearCode() {
        return endYearCode;
    }

    /**
     * 
     * {@linkplain #endYearCode}
     * @param endYearCode the value for cost_compute_rule.end_year_code
     */
    public void setEndYearCode(String endYearCode) {
        this.endYearCode = endYearCode;
    }

    /**
     * 
     * {@linkplain #endYearName}
     *
     * @return the value of cost_compute_rule.end_year_name
     */
    public String getEndYearName() {
        return endYearName;
    }

    /**
     * 
     * {@linkplain #endYearName}
     * @param endYearName the value for cost_compute_rule.end_year_name
     */
    public void setEndYearName(String endYearName) {
        this.endYearName = endYearName;
    }

    /**
     * 
     * {@linkplain #endSeasonCode}
     *
     * @return the value of cost_compute_rule.end_season_code
     */
    public String getEndSeasonCode() {
        return endSeasonCode;
    }

    /**
     * 
     * {@linkplain #endSeasonCode}
     * @param endSeasonCode the value for cost_compute_rule.end_season_code
     */
    public void setEndSeasonCode(String endSeasonCode) {
        this.endSeasonCode = endSeasonCode;
    }

    /**
     * 
     * {@linkplain #endSeasonName}
     *
     * @return the value of cost_compute_rule.end_season_name
     */
    public String getEndSeasonName() {
        return endSeasonName;
    }

    /**
     * 
     * {@linkplain #endSeasonName}
     * @param endSeasonName the value for cost_compute_rule.end_season_name
     */
    public void setEndSeasonName(String endSeasonName) {
        this.endSeasonName = endSeasonName;
    }

    /**
     * 
     * {@linkplain #computeRule}
     *
     * @return the value of cost_compute_rule.compute_rule
     */
    public Byte getComputeRule() {
        return computeRule;
    }

    /**
     * 
     * {@linkplain #computeRule}
     * @param computeRule the value for cost_compute_rule.compute_rule
     */
    public void setComputeRule(Byte computeRule) {
        this.computeRule = computeRule;
    }

    /**
     * 
     * {@linkplain #weightedCostCoeff}
     *
     * @return the value of cost_compute_rule.weighted_cost_coeff
     */
    public BigDecimal getWeightedCostCoeff() {
        return weightedCostCoeff;
    }

    /**
     * 
     * {@linkplain #weightedCostCoeff}
     * @param weightedCostCoeff the value for cost_compute_rule.weighted_cost_coeff
     */
    public void setWeightedCostCoeff(BigDecimal weightedCostCoeff) {
        this.weightedCostCoeff = weightedCostCoeff;
    }

    /**
     * 
     * {@linkplain #hqCostCoeff}
     *
     * @return the value of cost_compute_rule.hq_cost_coeff
     */
    public BigDecimal getHqCostCoeff() {
        return hqCostCoeff;
    }

    /**
     * 
     * {@linkplain #hqCostCoeff}
     * @param hqCostCoeff the value for cost_compute_rule.hq_cost_coeff
     */
    public void setHqCostCoeff(BigDecimal hqCostCoeff) {
        this.hqCostCoeff = hqCostCoeff;
    }

    /**
     * 
     * {@linkplain #areaCostCoeff}
     *
     * @return the value of cost_compute_rule.area_cost_coeff
     */
    public BigDecimal getAreaCostCoeff() {
        return areaCostCoeff;
    }

    /**
     * 
     * {@linkplain #areaCostCoeff}
     * @param areaCostCoeff the value for cost_compute_rule.area_cost_coeff
     */
    public void setAreaCostCoeff(BigDecimal areaCostCoeff) {
        this.areaCostCoeff = areaCostCoeff;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of cost_compute_rule.status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for cost_compute_rule.status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of cost_compute_rule.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for cost_compute_rule.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of cost_compute_rule.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for cost_compute_rule.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of cost_compute_rule.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for cost_compute_rule.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of cost_compute_rule.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for cost_compute_rule.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     *
     * @return the value of cost_compute_rule.brand_unit_no
     */
    public String getBrandUnitNo() {
        return brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitNo}
     * @param brandUnitNo the value for cost_compute_rule.brand_unit_no
     */
    public void setBrandUnitNo(String brandUnitNo) {
        this.brandUnitNo = brandUnitNo;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     *
     * @return the value of cost_compute_rule.brand_unit_name
     */
    public String getBrandUnitName() {
        return brandUnitName;
    }

    /**
     * 
     * {@linkplain #brandUnitName}
     * @param brandUnitName the value for cost_compute_rule.brand_unit_name
     */
    public void setBrandUnitName(String brandUnitName) {
        this.brandUnitName = brandUnitName;
    }

    /**
     * 
     * {@linkplain #startYearSeason}
     *
     * @return the value of cost_compute_rule.start_year_season
     */
    public String getStartYearSeason() {
        return startYearSeason;
    }

    /**
     * 
     * {@linkplain #startYearSeason}
     * @param startYearSeason the value for cost_compute_rule.start_year_season
     */
    public void setStartYearSeason(String startYearSeason) {
        this.startYearSeason = startYearSeason;
    }

    /**
     * 
     * {@linkplain #endYearSeason}
     *
     * @return the value of cost_compute_rule.end_year_season
     */
    public String getEndYearSeason() {
        return endYearSeason;
    }

    /**
     * 
     * {@linkplain #endYearSeason}
     * @param endYearSeason the value for cost_compute_rule.end_year_season
     */
    public void setEndYearSeason(String endYearSeason) {
        this.endYearSeason = endYearSeason;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of cost_compute_rule.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for cost_compute_rule.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}