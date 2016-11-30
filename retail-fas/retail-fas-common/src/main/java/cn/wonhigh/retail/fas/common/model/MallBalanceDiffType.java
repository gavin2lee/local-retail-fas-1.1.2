package cn.wonhigh.retail.fas.common.model;


import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.MallBalanceDiffTypeExportFormat;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-02-11 11:29:35
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
@ExportFormat(className=MallBalanceDiffTypeExportFormat.class)
public class MallBalanceDiffType extends FasBaseModel  implements SequenceStrId{

    /**
	 * 
	 */
	private static final long serialVersionUID = 9067776106218473462L;

    /**
     * 单据编号
     */
    private String diffTypeNo;

    /**
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 差异类型编码
     */
    private String code;

    /**
     * 差异类型名称
     */
    private String name;

    /**
     * 是否系统初始参数设置(0 = 系统初始化 1 = 创建)
     */
    private Byte systemInit;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #diffTypeNo}
     *
     * @return the value of mall_balance_diff_type.diff_type_no
     */
    public String getDiffTypeNo() {
        return diffTypeNo;
    }

    /**
     * 
     * {@linkplain #diffTypeNo}
     * @param diffTypeNo the value for mall_balance_diff_type.diff_type_no
     */
    public void setDiffTypeNo(String diffTypeNo) {
        this.diffTypeNo = diffTypeNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of mall_balance_diff_type.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for mall_balance_diff_type.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of mall_balance_diff_type.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for mall_balance_diff_type.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #code}
     *
     * @return the value of mall_balance_diff_type.code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * {@linkplain #code}
     * @param code the value for mall_balance_diff_type.code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of mall_balance_diff_type.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for mall_balance_diff_type.name
     */
    public void setName(String name) {
        this.name = name;
    }

   
    /**
     * 
     * {@linkplain #systemInit}
     *
     * @return the value of mall_balance_diff_type.system_init
     */
    public Byte getSystemInit() {
        return systemInit;
    }

    /**
     * 
     * {@linkplain #systemInit}
     * @param systemInit the value for mall_balance_diff_type.system_init
     */
    public void setSystemInit(Byte systemInit) {
        this.systemInit = systemInit;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of mall_balance_diff_type.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for mall_balance_diff_type.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}