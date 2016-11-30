package cn.wonhigh.retail.fas.common.model;

import org.apache.commons.lang.StringUtils;

import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.RegionPriceRuleExportFormat;


/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-01 09:25:14
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
@ExportFormat(className=RegionPriceRuleExportFormat.class)
public class RegionPriceRule extends HeadquarterPriceRule {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5827828128588163200L;

    /**
     * 经营区域编号
     */
    private String zoneNo;
    
    /**
     * 经营地区名称
     */
    private String zoneName;

    /**
     * 品牌组多选下拉用
     */
    private String[] zoneNos;
    
    public String[] getZoneNos() {
    	if(StringUtils.isNotEmpty(zoneNo)) {
			return StringUtils.split(zoneNo, ",");
		}
		return zoneNos;
	}

	public void setZoneNos(String[] zoneNos) {
		this.zoneNos = zoneNos;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	/**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of region_price_rule.zone_no
     */
    public String getZoneNo() {
    	if(zoneNos != null && zoneNos.length > 0) {
    		return StringUtils.join(zoneNos, ",");
    	}
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for region_price_rule.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }
    
}