package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.MallDeductionSetExportFormat;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-04-01 10:19:05
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
@ExportFormat(className=MallDeductionSetExportFormat.class)
public class MallDeductionSet extends FasBaseModel implements SequenceStrId{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 单据编号
     */
    private String deductionNo;

    /**
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 扣费编码
     */
    private String code;

    /**
     * 扣费名称
     */
    private String name;

    /**
     * 总账费用类别编码
     */
    private String costCode;

    /**
     * 总账费用类别名称
     */
    private String costName;

    /**
     * 扣费类型
     */
    private Byte type;

    /**
     * 是否系统初始参数设置(0 = 系统初始化 1 = 创建)
     */
    private Byte systemInit;

    private Byte debitedRental;
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 会计科目编码
     */
    private String accountsNo;

    /**
     * 会计科目名称
     */
    private String accountsName;

    /**
     * 
     * {@linkplain #deductionNo}
     *
     * @return the value of mall_deduction_set.deduction_no
     */
    public String getDeductionNo() {
        return deductionNo;
    }

    /**
     * 
     * {@linkplain #deductionNo}
     * @param deductionNo the value for mall_deduction_set.deduction_no
     */
    public void setDeductionNo(String deductionNo) {
        this.deductionNo = deductionNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of mall_deduction_set.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for mall_deduction_set.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of mall_deduction_set.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for mall_deduction_set.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #code}
     *
     * @return the value of mall_deduction_set.code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * {@linkplain #code}
     * @param code the value for mall_deduction_set.code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of mall_deduction_set.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for mall_deduction_set.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #costCode}
     *
     * @return the value of mall_deduction_set.cost_code
     */
    public String getCostCode() {
        return costCode;
    }

    /**
     * 
     * {@linkplain #costCode}
     * @param costCode the value for mall_deduction_set.cost_code
     */
    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    /**
     * 
     * {@linkplain #costName}
     *
     * @return the value of mall_deduction_set.cost_name
     */
    public String getCostName() {
        return costName;
    }

    /**
     * 
     * {@linkplain #costName}
     * @param costName the value for mall_deduction_set.cost_name
     */
    public void setCostName(String costName) {
        this.costName = costName;
    }

    /**
     * 
     * {@linkplain #type}
     *
     * @return the value of mall_deduction_set.type
     */
    public Byte getType() {
        return type;
    }

    /**
     * 
     * {@linkplain #type}
     * @param type the value for mall_deduction_set.type
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 
     * {@linkplain #systemInit}
     *
     * @return the value of mall_deduction_set.system_init
     */
    public Byte getSystemInit() {
        return systemInit;
    }

    /**
     * 
     * {@linkplain #systemInit}
     * @param systemInit the value for mall_deduction_set.system_init
     */
    public void setSystemInit(Byte systemInit) {
        this.systemInit = systemInit;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of mall_deduction_set.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for mall_deduction_set.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public Byte getDebitedRental() {
		return debitedRental;
	}

	public void setDebitedRental(Byte debitedRental) {
		this.debitedRental = debitedRental;
	}

	public String getAccountsNo() {
		return accountsNo;
	}

	public void setAccountsNo(String accountsNo) {
		this.accountsNo = accountsNo;
	}

	public String getAccountsName() {
		return accountsName;
	}

	public void setAccountsName(String accountsName) {
		this.accountsName = accountsName;
	}
}