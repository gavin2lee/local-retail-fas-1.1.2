package cn.wonhigh.retail.fas.common.model;

/**
 * 品牌组与品牌部关联
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
public class SettleBrandGroupRel extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = -7668957646603610978L;

	/**
     * 品牌组主键
     */
    private String groupNo;

    /**
     * 品牌部编码
     */
    private String brandUnitNo;
    
    /**
     * 品牌部名称，通过品牌部编码与品牌部表关联获取，在数据库不存放该字段
     */
    private String brandUnitName;
    
	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getBrandUnitNo() {
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}
}