package cn.wonhigh.retail.fas.common.model;

/**
 * 结算路径与品牌关联
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
public class SettlePathBrandRel extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = 1922250970123386900L;

	/**
     * 结算路径编码
     */
    private String pathNo;

    /**
     * 品牌部编码
     */
    private String brandUnitNo;
    
    /**
     * 品牌部名称，数据库不存放，只用于页面显示
     */
    private String brandUnitName;

    public String getPathNo() {
		return pathNo;
	}

	public void setPathNo(String pathNo) {
		this.pathNo = pathNo;
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