package cn.wonhigh.retail.fas.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.service.BillBalanceService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
@Service("billBalanceManager")
class BillBalanceManagerImpl extends BaseCrudManagerImpl implements BillBalanceManager {
    
	@Resource
    private BillBalanceService billBalanceService;
    
    @Override
    public BaseCrudService init() {
        return billBalanceService;
    }

	@Override
	public int verify(BillBalance billBalance) throws ManagerException {
		try {
			return billBalanceService.verify(billBalance);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

}