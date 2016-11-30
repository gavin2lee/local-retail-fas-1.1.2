package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.SupplierGroupRel;
import cn.wonhigh.retail.fas.service.SupplierGroupRelService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-08-25 17:35:45
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
@Service("supplierGroupRelManager")
class SupplierGroupRelManagerImpl extends BaseCrudManagerImpl implements SupplierGroupRelManager {
    @Resource
    private SupplierGroupRelService supplierGroupRelService;

    @Override
    public BaseCrudService init() {
        return supplierGroupRelService;
    }

	@Override
	public int findRelationCount(Map<String, Object> params)
			throws ManagerException {
		try {
   			return this.supplierGroupRelService.findRelationCount(params);
   		} catch(ServiceException e) {
   			throw new ManagerException(e.getMessage(), e);
   		}
	}

	@Override
	public List<SupplierGroupRel> findRelationByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
   			return this.supplierGroupRelService.findRelationByPage(page, sortColumn, sortOrder, params);
   		} catch(ServiceException e) {
   			throw new ManagerException(e.getMessage(), e);
   		}
	}
}