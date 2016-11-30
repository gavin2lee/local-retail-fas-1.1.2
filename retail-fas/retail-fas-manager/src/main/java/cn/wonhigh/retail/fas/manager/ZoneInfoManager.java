package cn.wonhigh.retail.fas.manager;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.ZoneInfo;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface ZoneInfoManager extends BaseCrudManager {

	/**
	 * @return
	 */
	List<ZoneInfo> findPriceZones();

	/**
	 * 根据编码查询大区名称
	 * @param zoneNo
	 * @return
	 * @throws ManagerException
	 */
	String findZoneNameByNo(String zoneNo) throws ManagerException;

	/**
	 * 根据编码查询特殊价格大区名称
	 * @param zoneNo
	 * @return
	 * @throws ManagerException
	 */
	String findSpecailZoneNameByNo(String zoneNo) throws ManagerException;
	
}