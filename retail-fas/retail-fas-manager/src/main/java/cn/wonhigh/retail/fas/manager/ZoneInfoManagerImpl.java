package cn.wonhigh.retail.fas.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.service.ZoneInfoService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
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
@Service("zoneInfoManager")
class ZoneInfoManagerImpl extends BaseCrudManagerImpl implements ZoneInfoManager {
    @Resource
    private ZoneInfoService zoneInfoService;

    @Override
    public BaseCrudService init() {
        return zoneInfoService;
    }

	@Override
	public List<ZoneInfo> findPriceZones() {
		return zoneInfoService.findPriceZones();
	}

	@Override
	public String findZoneNameByNo(String zoneNo) throws ManagerException {
		try {
			return zoneInfoService.findZoneNameByNo(zoneNo);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(),e);
		}
	}

	@Override
	public String findSpecailZoneNameByNo(String zoneNo)
			throws ManagerException {
		try {
			return zoneInfoService.findSpecailZoneNameByNo(zoneNo);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(),e);
		}
	}
}