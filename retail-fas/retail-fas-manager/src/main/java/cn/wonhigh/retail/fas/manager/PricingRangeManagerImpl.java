package cn.wonhigh.retail.fas.manager;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.service.PricingRangeService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-07-11 17:43:44
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
@Service("pricingRangeManager")
class PricingRangeManagerImpl extends BaseCrudManagerImpl implements PricingRangeManager {
    @Resource
    private PricingRangeService pricingRangeService;

    @Override
    public BaseCrudService init() {
        return pricingRangeService;
    }

	@Override
	public Integer getPricingItemCount(Map<String, Object> params) throws ManagerException{
		try {
			return pricingRangeService.getPricingItemCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
		
	}
}