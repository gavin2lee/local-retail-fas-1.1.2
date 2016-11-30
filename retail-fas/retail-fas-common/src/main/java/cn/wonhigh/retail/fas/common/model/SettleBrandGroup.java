package cn.wonhigh.retail.fas.common.model;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;

/**
 * 品牌组
 * @author yang.y
 * @date  2014-08-26 10:36:46
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
@ExportFormat(className=AbstractExportFormat.class)
public class SettleBrandGroup extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = -2339448840977376314L;

	/**
     * 品牌组编码
     */
    private String groupNo;

    /**
     * 品牌组名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
    
    private String organTypeNo;

    /**
     * 
     * {@linkplain #groupNo}
     *
     * @return the value of settle_brand_group.group_no
     */
    public String getGroupNo() {
        return groupNo;
    }

    /**
     * 
     * {@linkplain #groupNo}
     * @param groupNo the value for settle_brand_group.group_no
     */
    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of settle_brand_group.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for settle_brand_group.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of settle_brand_group.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for settle_brand_group.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getOrganTypeNo() {
		return organTypeNo;
	}

	public void setOrganTypeNo(String organTypeNo) {
		this.organTypeNo = organTypeNo;
	}
}