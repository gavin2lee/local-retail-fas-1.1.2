package cn.wonhigh.retail.fas.common.model;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;


/**
 * 请写出类的用途 
 * @author user
 * @date  2016-01-06 17:24:59
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
public class ShopNameReplace extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8127921892748079924L;

	private String id;

	@ExcelCell("A")
    private String shopNo;

    private String shopName;

    @ExcelCell("B")
    private String brandUnitNo;

    private String brandUnitName;

    @ExcelCell("C")
    private String replaceName;

    @ExcelCell("D")
    private String remark;
    
    @ExcelCell("E")
    private String replaceNo;

    private String shardingFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public String getReplaceName() {
        return replaceName;
    }

    public void setReplaceName(String replaceName) {
        this.replaceName = replaceName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShardingFlag() {
        return shardingFlag;
    }

    public void setShardingFlag(String shardingFlag) {
        this.shardingFlag = shardingFlag;
    }

	public String getReplaceNo() {
		return replaceNo;
	}

	public void setReplaceNo(String replaceNo) {
		this.replaceNo = replaceNo;
	}
}