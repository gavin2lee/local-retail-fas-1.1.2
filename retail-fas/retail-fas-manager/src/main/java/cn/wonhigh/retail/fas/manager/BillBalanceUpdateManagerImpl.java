package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.service.BillBalanceUpdateService;

import com.yougou.logistics.base.common.exception.ManagerException;
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
@Service("billBalanceUpdateManager")
class BillBalanceUpdateManagerImpl extends BaseCrudManagerImpl implements BillBalanceUpdateManager {

    @Resource
    private BillBalanceUpdateService billBalanceUpdateService;
    
    @Override
    public BaseCrudService init() {
        return billBalanceUpdateService;
    }
	@Override
	public BillBalance add(BillBalance obj, Map<String, Object> params) throws ManagerException {
		try {
			return billBalanceUpdateService.add(obj, params);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public BillBalance update(BillBalance obj) throws ManagerException {
		try {
			return billBalanceUpdateService.update(obj);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public Integer delete(BillBalance obj) throws ManagerException {
		try {
			return billBalanceUpdateService.delete(obj);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public BillBalance createBalance(BillBalance obj, List<Object> lstItem)
			throws ManagerException {
		try {
			return billBalanceUpdateService.createBalance(obj, lstItem);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public BillBalance balanceAdjust(BillBalance obj, List<Object> lstItem)
			throws ManagerException {
		try {
			return billBalanceUpdateService.balanceAdjust(obj, lstItem);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
    
	
}