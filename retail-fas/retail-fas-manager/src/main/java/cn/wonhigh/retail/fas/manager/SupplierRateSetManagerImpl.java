package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;
import cn.wonhigh.retail.fas.common.model.SupplierRateSet;
import cn.wonhigh.retail.fas.service.SupplierRateSetService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-05-05 09:10:13
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("supplierRateSetManager")
class SupplierRateSetManagerImpl extends BaseCrudManagerImpl implements SupplierRateSetManager {
    @Resource
    private SupplierRateSetService supplierRateSetService;

    @Override
    public BaseCrudService init() {
        return supplierRateSetService;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
    public int saveAll(Map<CommonOperatorEnum, List<SupplierRateSet>> dataMap) 
    		throws ManagerException{
    	try {
			int count = 0;
			List<SupplierRateSet> delList = dataMap.get(CommonOperatorEnum.DELETED);
			
			if(null != delList && delList.size() > 0) {
				for(SupplierRateSet modelType : delList) {
					count += this.supplierRateSetService.deleteById(modelType);
				}
			}
			List<SupplierRateSet> updateList = dataMap.get(CommonOperatorEnum.UPDATED);
			if(null != updateList && updateList.size() > 0) {
				for(SupplierRateSet modelType : updateList) {
					count += this.supplierRateSetService.modifyById(modelType);
				}
			}
			List<SupplierRateSet> insertList = dataMap.get(CommonOperatorEnum.INSERTED);
			if(null != insertList && insertList.size() > 0) {
				for(SupplierRateSet modelType : insertList) {
					this.supplierRateSetService.add(modelType);
				}
			}
			return count;
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
    }
    
	@Override
	public List<SupplierRateSet> selectByParams(Map<String, Object> params)
		throws ManagerException {
			try {
				return this.supplierRateSetService.selectByParams(params);
			} catch (ServiceException e) {
				throw new ManagerException(e.getMessage(), e);
			}
	}

	@Override
	public Integer findCount() throws ManagerException {
		try {
			return this.supplierRateSetService.findCount();
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<SupplierRateSet> findSupplierByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String,Object> params) throws ManagerException {
		try {
			return this.supplierRateSetService.findSupplierByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}

	}

	@Override
	public boolean exist(String supplierNo) throws ManagerException {
		try {
			return this.supplierRateSetService.exist(supplierNo);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}