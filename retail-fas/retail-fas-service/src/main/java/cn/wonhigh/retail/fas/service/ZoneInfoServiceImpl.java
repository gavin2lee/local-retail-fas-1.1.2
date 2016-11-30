package cn.wonhigh.retail.fas.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.dal.database.ZoneInfoMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("zoneInfoService")
class ZoneInfoServiceImpl extends BaseCrudServiceImpl implements ZoneInfoService {
    @Resource
    private ZoneInfoMapper zoneInfoMapper;

    @Override
    public BaseCrudMapper init() {
        return zoneInfoMapper;
    }

	@Override
	public List<ZoneInfo> findPriceZones() {
		return zoneInfoMapper.selectPriceZones();
	}

	@Override
	public String findZoneNameByNo(String zoneNo) throws ServiceException {
		try {
			return zoneInfoMapper.findZoneNameByNo(zoneNo);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public String findSpecailZoneNameByNo(String zoneNo)
			throws ServiceException {
		try {
			return zoneInfoMapper.findSpecailZoneNameByNo(zoneNo);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public ZoneInfo getPriceZoneByNo(String zoneNo) throws ServiceException {
		try {
			return zoneInfoMapper.getPriceZoneByNo(zoneNo);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}
}