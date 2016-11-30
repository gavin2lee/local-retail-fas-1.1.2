package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 凭证类型
 * @author ouyang.zm
 * @date  2014-10-29 15:09:14
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
public class VoucherType extends FasBaseModel implements SequenceStrId {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6821662116783578152L;

	/**
     * 凭证类型编码
     */
    private String vouchTypeCode;

    /**
     * 凭证类别名称
     */
    private String vouchTypeName;

    /**
     * 类别简称
     */
    private String shortName;

    /**
     * 显示顺序
     */
    private Byte showOrder;

    /**
     * 公司编码
     */
    private String companyNo;

    /**
     * 默认币种
     */
    private String currencyCode;

    /**
     * 封存标志
     */
    private Byte sealFlag;

    /**
     * 主体账簿主键
     */
    private String glorgBook;

    /**
     * 凭证分类
     */
    private Byte vouchType;

    /**
     * 科目限制标志
     */
    private Byte restrictFlag;

    /**
     * 默认打印模板
     */
    private String printTemplate;

    /**
     * 删除标志
     */
    private Byte delFlag;

    /**
     * 启用标志 1、启用 0、未启用
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

	//公司名称
	private String companyName;
	//币种名称
	private String currencyName;
	
	
    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	/**
     * 
     * {@linkplain #vouchTypeCode}
     *
     * @return the value of voucher_type.vouch_type_code
     */
    public String getVouchTypeCode() {
        return vouchTypeCode;
    }

    /**
     * 
     * {@linkplain #vouchTypeCode}
     * @param vouchTypeCode the value for voucher_type.vouch_type_code
     */
    public void setVouchTypeCode(String vouchTypeCode) {
        this.vouchTypeCode = vouchTypeCode;
    }

    /**
     * 
     * {@linkplain #vouchTypeName}
     *
     * @return the value of voucher_type.vouch_type_name
     */
    public String getVouchTypeName() {
        return vouchTypeName;
    }

    /**
     * 
     * {@linkplain #vouchTypeName}
     * @param vouchTypeName the value for voucher_type.vouch_type_name
     */
    public void setVouchTypeName(String vouchTypeName) {
        this.vouchTypeName = vouchTypeName;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of voucher_type.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for voucher_type.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #showOrder}
     *
     * @return the value of voucher_type.show_order
     */
    public Byte getShowOrder() {
        return showOrder;
    }

    /**
     * 
     * {@linkplain #showOrder}
     * @param showOrder the value for voucher_type.show_order
     */
    public void setShowOrder(Byte showOrder) {
        this.showOrder = showOrder;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of voucher_type.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for voucher_type.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #currencyCode}
     *
     * @return the value of voucher_type.currency_code
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * 
     * {@linkplain #currencyCode}
     * @param currencyCode the value for voucher_type.currency_code
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * 
     * {@linkplain #sealFlag}
     *
     * @return the value of voucher_type.seal_flag
     */
    public Byte getSealFlag() {
        return sealFlag;
    }

    /**
     * 
     * {@linkplain #sealFlag}
     * @param sealFlag the value for voucher_type.seal_flag
     */
    public void setSealFlag(Byte sealFlag) {
        this.sealFlag = sealFlag;
    }

    /**
     * 
     * {@linkplain #glorgBook}
     *
     * @return the value of voucher_type.glorg_book
     */
    public String getGlorgBook() {
        return glorgBook;
    }

    /**
     * 
     * {@linkplain #glorgBook}
     * @param glorgBook the value for voucher_type.glorg_book
     */
    public void setGlorgBook(String glorgBook) {
        this.glorgBook = glorgBook;
    }

    /**
     * 
     * {@linkplain #vouchType}
     *
     * @return the value of voucher_type.vouch_type
     */
    public Byte getVouchType() {
        return vouchType;
    }

    /**
     * 
     * {@linkplain #vouchType}
     * @param vouchType the value for voucher_type.vouch_type
     */
    public void setVouchType(Byte vouchType) {
        this.vouchType = vouchType;
    }

    /**
     * 
     * {@linkplain #restrictFlag}
     *
     * @return the value of voucher_type.restrict_flag
     */
    public Byte getRestrictFlag() {
        return restrictFlag;
    }

    /**
     * 
     * {@linkplain #restrictFlag}
     * @param restrictFlag the value for voucher_type.restrict_flag
     */
    public void setRestrictFlag(Byte restrictFlag) {
        this.restrictFlag = restrictFlag;
    }

    /**
     * 
     * {@linkplain #printTemplate}
     *
     * @return the value of voucher_type.print_template
     */
    public String getPrintTemplate() {
        return printTemplate;
    }

    /**
     * 
     * {@linkplain #printTemplate}
     * @param printTemplate the value for voucher_type.print_template
     */
    public void setPrintTemplate(String printTemplate) {
        this.printTemplate = printTemplate;
    }

    /**
     * 
     * {@linkplain #delFlag}
     *
     * @return the value of voucher_type.del_flag
     */
    public Byte getDelFlag() {
        return delFlag;
    }

    /**
     * 
     * {@linkplain #delFlag}
     * @param delFlag the value for voucher_type.del_flag
     */
    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 
     * {@linkplain #stopFlag}
     *
     * @return the value of voucher_type.stop_flag
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #stopFlag}
     * @param stopFlag the value for voucher_type.stop_flag
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of voucher_type.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for voucher_type.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of voucher_type.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for voucher_type.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of voucher_type.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for voucher_type.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of voucher_type.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for voucher_type.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}