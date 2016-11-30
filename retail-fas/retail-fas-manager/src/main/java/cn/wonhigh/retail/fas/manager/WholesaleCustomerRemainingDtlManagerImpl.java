package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingDtl;
import cn.wonhigh.retail.fas.service.WholesaleCustomerRemainingDtlService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-07-06 12:15:59
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
@Service("wholesaleCustomerRemainingDtlManager")
class WholesaleCustomerRemainingDtlManagerImpl extends BaseCrudManagerImpl implements WholesaleCustomerRemainingDtlManager {
    @Resource
    private WholesaleCustomerRemainingDtlService wholesaleCustomerRemainingDtlService;

    @Override
    public BaseCrudService init() {
        return wholesaleCustomerRemainingDtlService;
    }

	@Override
	public WholesaleCustomerRemainingDtl selectCustomerRemainingTotalByDate(
			Map<String, Object> params) throws ManagerException {
		try {
			return wholesaleCustomerRemainingDtlService.selectCustomerRemainingTotalByDate(params);
		} catch (ServiceException e) {
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}

	@Override
	public List<WholesaleCustomerRemainingDtl> selectDtlByPage(SimplePage page, Map<String, Object> params) throws ManagerException {
		try {
			return wholesaleCustomerRemainingDtlService.selectDtlByPage(page, params);
		} catch (ServiceException e) {
	    	throw new ManagerException(e.getMessage(), e);
	    }
	}
}