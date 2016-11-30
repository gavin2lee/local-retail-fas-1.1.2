package cn.wonhigh.retail.fas.common.model;


/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-04-02 14:16:40
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
public class InsideBizType extends FasBaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer insideId;

	/**
     * 结算公司编号
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 业务类型编号
     */
    private String bizTypeCode;

    /**
     * 业务类型名称
     */
    private String bizTypeName;

//    /**
//     * 状态
//     */
//    private Byte status;

    /**
     * 结转成本
     */
    private Integer carryOverCost;

    /**
     * 备注，在销售明细成本有为0时，需要增加备注说明
     */
    private String remark;


    public Integer getInsideId() {
		return insideId;
	}

	public void setInsideId(Integer insideId) {
		this.insideId = insideId;
	}

	/**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of inside_biz_type.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for inside_biz_type.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of inside_biz_type.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for inside_biz_type.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #bizTypeCode}
     *
     * @return the value of inside_biz_type.biz_type_code
     */
    public String getBizTypeCode() {
        return bizTypeCode;
    }

    /**
     * 
     * {@linkplain #bizTypeCode}
     * @param bizTypeCode the value for inside_biz_type.biz_type_code
     */
    public void setBizTypeCode(String bizTypeCode) {
        this.bizTypeCode = bizTypeCode;
    }

    /**
     * 
     * {@linkplain #bizTypeName}
     *
     * @return the value of inside_biz_type.biz_type_name
     */
    public String getBizTypeName() {
        return bizTypeName;
    }

    /**
     * 
     * {@linkplain #bizTypeName}
     * @param bizTypeName the value for inside_biz_type.biz_type_name
     */
    public void setBizTypeName(String bizTypeName) {
        this.bizTypeName = bizTypeName;
    }

//    /**
//     * 
//     * {@linkplain #status}
//     *
//     * @return the value of inside_biz_type.status
//     */
//    public Byte getStatus() {
//        return status;
//    }
//
//    /**
//     * 
//     * {@linkplain #status}
//     * @param status the value for inside_biz_type.status
//     */
//    public void setStatus(Byte status) {
//        this.status = status;
//    }

    /**
     * 
     * {@linkplain #carryOverCost}
     *
     * @return the value of inside_biz_type.carry_over_cost
     */
    public Integer getCarryOverCost() {
        return carryOverCost;
    }

    /**
     * 
     * {@linkplain #carryOverCost}
     * @param carryOverCost the value for inside_biz_type.carry_over_cost
     */
    public void setCarryOverCost(Integer carryOverCost) {
        this.carryOverCost = carryOverCost;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of inside_biz_type.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for inside_biz_type.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}