package cn.wonhigh.retail.fas.dal.database;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.ZoneInfo;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 12:10:54
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
public interface ZoneInfoMapper extends BaseCrudMapper {

	/**
	 * 查询默认价格区
	 * @return
	 */
	List<ZoneInfo> selectPriceZones();

	String findZoneNameByNo(String zoneNo);

	String findSpecailZoneNameByNo(String zoneNo);

	ZoneInfo getPriceZoneByNo(String zoneNo);
	
}