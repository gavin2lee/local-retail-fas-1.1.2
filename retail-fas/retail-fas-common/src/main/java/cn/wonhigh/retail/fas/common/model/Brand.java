package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 品牌信息
 * @author ouyang.zm
 * @date  2014-08-25 13:52:36
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
public class Brand implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7595051530520281509L;

	/**
     * 品牌ID
     */
    private Integer id;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌中文名称
     */
    private String name;

    /**
     * 品牌英文名称
     */
    private String enName;

    /**
     * 品牌英文简称
     */
    private String enShortName;

    /**
     * 商品编码特征码(用于鞋类商品编码的首位字母,必须输入且只能输入一位字符)
     */
    private String opcode;

    /**
     * 品牌类别(D:代理品牌，S:自有品牌)
     */
    private String category;

    /**
     * 品牌归属(X:鞋，S:体)
     */
    private String belonger;

    /**
     * 品牌状态(0 = 撤消 1 = 正常)
     */
    private Byte status;

    /**
     * 所属业务单元(即定义由哪一个群体或组织来操作相关品牌业务，来自BU_INFO表)
     */
    private String sysNo;

    /**
     * 检索码
     */
    private String searchCode;

    /**
     * 父品牌
     */
    private Integer parentBrandId;

    /**
     * LOGO链接地址
     */
    private String logoUrl;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 建档时间
     */
    /** 修改时间 */
	@JsonSerialize(using = JsonDateSerializer$19.class)
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    /** 修改时间 */
	@JsonSerialize(using = JsonDateSerializer$19.class)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 品牌部编号
     */
    private String brandUnitNo;
    
    /**
     * 品牌部名称
     */
    private String brandUnitName;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of brand.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for brand.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of brand.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for brand.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of brand.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for brand.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #enName}
     *
     * @return the value of brand.en_name
     */
    public String getEnName() {
        return enName;
    }

    /**
     * 
     * {@linkplain #enName}
     * @param enName the value for brand.en_name
     */
    public void setEnName(String enName) {
        this.enName = enName;
    }

    /**
     * 
     * {@linkplain #enShortName}
     *
     * @return the value of brand.en_short_name
     */
    public String getEnShortName() {
        return enShortName;
    }

    /**
     * 
     * {@linkplain #enShortName}
     * @param enShortName the value for brand.en_short_name
     */
    public void setEnShortName(String enShortName) {
        this.enShortName = enShortName;
    }

    /**
     * 
     * {@linkplain #opcode}
     *
     * @return the value of brand.opcode
     */
    public String getOpcode() {
        return opcode;
    }

    /**
     * 
     * {@linkplain #opcode}
     * @param opcode the value for brand.opcode
     */
    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    /**
     * 
     * {@linkplain #category}
     *
     * @return the value of brand.category
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * {@linkplain #category}
     * @param category the value for brand.category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * {@linkplain #belonger}
     *
     * @return the value of brand.belonger
     */
    public String getBelonger() {
        return belonger;
    }

    /**
     * 
     * {@linkplain #belonger}
     * @param belonger the value for brand.belonger
     */
    public void setBelonger(String belonger) {
        this.belonger = belonger;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of brand.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for brand.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #sysNo}
     *
     * @return the value of brand.sys_no
     */
    public String getSysNo() {
        return sysNo;
    }

    /**
     * 
     * {@linkplain #sysNo}
     * @param sysNo the value for brand.sys_no
     */
    public void setSysNo(String sysNo) {
        this.sysNo = sysNo;
    }

    /**
     * 
     * {@linkplain #searchCode}
     *
     * @return the value of brand.search_code
     */
    public String getSearchCode() {
        return searchCode;
    }

    /**
     * 
     * {@linkplain #searchCode}
     * @param searchCode the value for brand.search_code
     */
    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    /**
     * 
     * {@linkplain #parentBrandId}
     *
     * @return the value of brand.parent_brand_id
     */
    public Integer getParentBrandId() {
        return parentBrandId;
    }

    /**
     * 
     * {@linkplain #parentBrandId}
     * @param parentBrandId the value for brand.parent_brand_id
     */
    public void setParentBrandId(Integer parentBrandId) {
        this.parentBrandId = parentBrandId;
    }

    /**
     * 
     * {@linkplain #logoUrl}
     *
     * @return the value of brand.logo_url
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * 
     * {@linkplain #logoUrl}
     * @param logoUrl the value for brand.logo_url
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of brand.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for brand.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of brand.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for brand.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of brand.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for brand.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of brand.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for brand.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of brand.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for brand.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	public String getBrandUnitNo() {
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}
}