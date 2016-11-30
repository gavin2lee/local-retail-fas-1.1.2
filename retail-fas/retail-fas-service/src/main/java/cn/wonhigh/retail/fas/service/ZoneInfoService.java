package cn.wonhigh.retail.fas.service;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.ZoneInfo;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface ZoneInfoService extends BaseCrudService {

	/**
	 * @return
	 */
	List<ZoneInfo> findPriceZones();

	String findZoneNameByNo(String zoneNo) throws ServiceException;

	String findSpecailZoneNameByNo(String zoneNo) throws ServiceException;

	ZoneInfo getPriceZoneByNo(String zoneNo)throws ServiceException;
	
}