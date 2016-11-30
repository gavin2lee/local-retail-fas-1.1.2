package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.service.SettlePathDtlService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
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
@Service("settlePathDtlManager")
class SettlePathDtlManagerImpl extends BaseCrudManagerImpl implements SettlePathDtlManager {
	
    @Resource
    private SettlePathDtlService settlePathDtlService;

    @Override
    public BaseCrudService init() {
        return settlePathDtlService;
    }
    
    @Override
	public int findCompanyCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return settlePathDtlService.findCompanyCount(params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object> findCompanyPage(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ManagerException {
		try {
			return settlePathDtlService.findCompanyPage(page, orderByField, orderBy, params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}