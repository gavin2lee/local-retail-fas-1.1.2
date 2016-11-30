package cn.wonhigh.retail.fas.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.RuleMatchFail;
import cn.wonhigh.retail.fas.dal.database.RuleMatchFailMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-15 17:42:50
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
@Service("ruleMatchFailService")
class RuleMatchFailServiceImpl extends BaseCrudServiceImpl implements RuleMatchFailService {
    @Resource
    private RuleMatchFailMapper ruleMatchFailMapper;

    @Override
    public BaseCrudMapper init() {
        return ruleMatchFailMapper;
    }

	@Override
	public RuleMatchFail findHQItemUnmatched(String itemNo)
			throws ServiceException {
		try {
			return ruleMatchFailMapper.findHQItemUnmatched(itemNo);
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	@Override
	public RuleMatchFail findRegionItemUnmatched(String itemNo, String zoneNo)
			throws ServiceException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("itemNo", itemNo);
			params.put("zoneNo", zoneNo);
			return ruleMatchFailMapper.findRegionItemUnmatched(params);
		} catch (Exception e) {
			throw new ServiceException();
		}
	}
}