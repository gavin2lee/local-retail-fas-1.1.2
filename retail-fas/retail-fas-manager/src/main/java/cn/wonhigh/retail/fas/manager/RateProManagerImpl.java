package cn.wonhigh.retail.fas.manager;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.RatePro;
import cn.wonhigh.retail.fas.service.RateProService;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2014-12-18 14:08:20
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
@Service("rateProManager")
class RateProManagerImpl extends BaseCrudManagerImpl implements RateProManager {
    @Resource
    private RateProService rateProService;

    @Override
    public BaseCrudService init() {
        return rateProService;
    }

	@Override
	public RatePro findByRatePro(Map<String, Object> params)
			throws ServiceException {
		return rateProService.findByRatePro(params);
	}
}