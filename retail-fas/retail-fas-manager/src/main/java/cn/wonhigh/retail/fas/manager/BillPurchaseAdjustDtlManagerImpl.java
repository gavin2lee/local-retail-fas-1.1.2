package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillPurchaseAdjustDtl;
import cn.wonhigh.retail.fas.service.BillPurchaseAdjustDtlService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
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
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("billPurchaseAdjustDtlManager")
class BillPurchaseAdjustDtlManagerImpl extends BaseCrudManagerImpl implements BillPurchaseAdjustDtlManager {
    @Resource
    private BillPurchaseAdjustDtlService billPurchaseAdjustDtlService;

    @Override
    public BaseCrudService init() {
        return billPurchaseAdjustDtlService;
    }

	@Override
	public int findDtlCount(Map<String, Object> params)throws ManagerException {
		try {
			return billPurchaseAdjustDtlService.findDtlCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
		
	}

	@Override
	public List<BillPurchaseAdjustDtl> findDtlList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ManagerException  {
		try {
			return billPurchaseAdjustDtlService.findDtlList(page,sortColumn,sortOrder,params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<BillPurchaseAdjustDtl> findDtlFooter(Map<String, Object> params)throws ManagerException {
		try {
			return billPurchaseAdjustDtlService.findDtlFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

}