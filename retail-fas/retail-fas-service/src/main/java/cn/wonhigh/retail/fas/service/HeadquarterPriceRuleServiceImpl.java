package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.HeadquarterPriceRule;
import cn.wonhigh.retail.fas.dal.database.HeadquarterPriceRuleMapper;

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
@Service("headquarterPriceRuleService")
class HeadquarterPriceRuleServiceImpl extends BaseServiceImpl implements HeadquarterPriceRuleService {
    @Resource
    private HeadquarterPriceRuleMapper headquarterPriceRuleMapper;

    @Override
    public BaseCrudMapper init() {
        return headquarterPriceRuleMapper;
    }

	@Override
	public int checkIsRuleRefered(Map<String, Object> params)
			throws ServiceException {
		return headquarterPriceRuleMapper.checkIsRuleRefered(params);
	}

	@Override
	public List<HeadquarterPriceRule> findRuleByItemNo(String itemNo)
			throws ServiceException {
		try {
			return headquarterPriceRuleMapper.findRuleByItemNo(itemNo);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<HeadquarterPriceRule> findRuleByParams(
			Map<String, Object> params) throws ServiceException {
		try {
			return headquarterPriceRuleMapper.findRuleByParams(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
}