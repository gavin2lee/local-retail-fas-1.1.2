package cn.wonhigh.retail.fas.common.model;

/**
 * 结算大类明细
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
public class SettleCategoryDtl extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = -1267504593395385585L;

	/**
     * 大类编码
     */
    private String categoryNo;

    /**
     * 大类名称
     */
    private String name;

    /**
     * 大类编码
     */
    private String settleCategoryNo;

    public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSettleCategoryNo() {
		return settleCategoryNo;
	}

	public void setSettleCategoryNo(String settleCategoryNo) {
		this.settleCategoryNo = settleCategoryNo;
	}
}