package cn.wonhigh.retail.fas.common.model;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;


/**
 * 新旧款
 * @author yang.y
 * @date  2014-08-26 15:42:01
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
public class SettleNewStyle extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = 1069175128111759940L;

	/**
     * 分类编码
     */
    private String styleNo;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    private String organTypeNo;
    
    public SettleNewStyle() {}
    
    public SettleNewStyle(String styleNo, String name) {
		super();
		this.styleNo = styleNo;
		this.name = name;
	}

	/**
     * 
     * {@linkplain #styleNo}
     *
     * @return the value of settle_new_style.style_no
     */
    public String getStyleNo() {
        return styleNo;
    }

    /**
     * 
     * {@linkplain #styleNo}
     * @param styleNo the value for settle_new_style.style_no
     */
    public void setStyleNo(String styleNo) {
        this.styleNo = styleNo;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of settle_new_style.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for settle_new_style.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of settle_new_style.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for settle_new_style.remark
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