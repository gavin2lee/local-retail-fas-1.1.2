package cn.wonhigh.retail.fas.manager;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.service.LookupEntryService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-26 15:21:01
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
@Service("lookupEntryManager")
class LookupEntryManagerImpl extends BaseCrudManagerImpl implements LookupEntryManager {
    @Resource
    private LookupEntryService lookupEntryService;

    @Override
    public BaseCrudService init() {
        return lookupEntryService;
    }

	@Override
	public String getEntryNameByLookupIdEntryCode(Map<String, Object> params)
			throws ManagerException {
		try {
			return lookupEntryService.getEntryNameByLookupIdEntryCode(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}