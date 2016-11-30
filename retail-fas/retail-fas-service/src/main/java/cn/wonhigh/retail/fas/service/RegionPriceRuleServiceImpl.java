package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.RegionPriceRule;
import cn.wonhigh.retail.fas.dal.database.RegionPriceRuleMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-01 09:25:14
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
@Service("regionPriceRuleService")
class RegionPriceRuleServiceImpl extends BaseServiceImpl implements RegionPriceRuleService {
    @Resource
    private RegionPriceRuleMapper regionPriceRuleMapper;

    @Override
    public BaseCrudMapper init() {
        return regionPriceRuleMapper;
    }

	@Override
	public int checkIsRuleRefered(Map<String, Object> params)
			throws ServiceException {
		return regionPriceRuleMapper.checkIsRuleRefered(params);
	}

	@Override
	public List<RegionPriceRule> findRuleByItemNoAndZoneNo(String itemNo,String zoneNo)
			throws ServiceException {
		try {
			return regionPriceRuleMapper.findRuleByItemNoAndZoneNo(itemNo,zoneNo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<RegionPriceRule> findRuleByParams(Map<String, Object> params)
			throws ServiceException {
		try {
			return regionPriceRuleMapper.findRuleByParams(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}