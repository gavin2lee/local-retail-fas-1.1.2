package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.HeadquarterPeriod;
import cn.wonhigh.retail.fas.common.model.SupplierRateSet;
import cn.wonhigh.retail.fas.service.HeadquarterPeriodService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-07-08 10:17:18
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
@Service("headquarterPeriodManager")
class HeadquarterPeriodManagerImpl extends BaseCrudManagerImpl implements HeadquarterPeriodManager {
    @Resource
    private HeadquarterPeriodService headquarterPeriodService;

    @Override
    public BaseCrudService init() {
        return headquarterPeriodService;
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int saveAll(Map<String, Object> params) throws ManagerException{
	    	try {
	    		return	this.headquarterPeriodService.add(params);
			} catch(ServiceException e) {
				throw new ManagerException(e.getMessage(), e);
			}
	}
}