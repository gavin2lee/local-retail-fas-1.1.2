package cn.wonhigh.retail.fas.common.model;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;


/**
 * 结算大类
 * @author yang.y
 * @date  2014-08-22 14:57:26
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
public class SettleCategory extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = 3684173426200414435L;

	/**
     * 分类编码,财务系统手动输入
     */
    private String settleCategoryNo;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    private String organTypeNo;
    
    /**
     * 
     * {@linkplain #settleCategoryNo}
     *
     * @return the value of settle_category.settle_category_no
     */
    public String getSettleCategoryNo() {
        return settleCategoryNo;
    }

    /**
     * 
     * {@linkplain #settleCategoryNo}
     * @param settleCategoryNo the value for settle_category.settle_category_no
     */
    public void setSettleCategoryNo(String settleCategoryNo) {
        this.settleCategoryNo = settleCategoryNo;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of settle_category.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for settle_category.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of settle_category.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for settle_category.remark
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