package cn.wonhigh.retail.fas.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.PricingRangeMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("pricingRangeService")
class PricingRangeServiceImpl extends BaseCrudServiceImpl implements PricingRangeService {
    @Resource
    private PricingRangeMapper pricingRangeMapper;

    @Override
    public BaseCrudMapper init() {
        return pricingRangeMapper;
    }

	@Override
	public Integer getPricingItemCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return pricingRangeMapper.getPricingItemCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
	}
}