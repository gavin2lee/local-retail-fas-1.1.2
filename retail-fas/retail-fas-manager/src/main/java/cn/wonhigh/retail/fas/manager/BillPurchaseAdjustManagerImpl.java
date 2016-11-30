package cn.wonhigh.retail.fas.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillPurchaseAdjust;
import cn.wonhigh.retail.fas.service.BillPurchaseAdjustService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-13 12:09:02
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("billPurchaseAdjustManager")
class BillPurchaseAdjustManagerImpl extends BaseCrudManagerImpl implements BillPurchaseAdjustManager {
    @Resource
    private BillPurchaseAdjustService billPurchaseAdjustService;
    
    @Override
    public BaseCrudService init() {
        return billPurchaseAdjustService;
    }
    
	@Override
	public int verifyBill(BillPurchaseAdjust obj) throws ManagerException {
		try {
			return billPurchaseAdjustService.verifyBill(obj);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
}