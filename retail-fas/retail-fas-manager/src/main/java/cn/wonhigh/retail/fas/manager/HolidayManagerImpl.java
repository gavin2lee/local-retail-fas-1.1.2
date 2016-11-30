package cn.wonhigh.retail.fas.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.Holiday;
import cn.wonhigh.retail.fas.service.HolidayService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-04-19 17:47:43
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
@Service("holidayManager")
class HolidayManagerImpl extends BaseCrudManagerImpl implements HolidayManager {
    @Resource
    private HolidayService holidayService;

    @Override
    public BaseCrudService init() {
        return holidayService;
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void batchAdd(List<Object> lstItem) throws ManagerException {
		try {
			for (Object object : lstItem) {
				Holiday holiday = (Holiday)object;
				holiday.setId(UUIDHexGenerator.generate());
				holidayService.add(holiday);
			}
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}