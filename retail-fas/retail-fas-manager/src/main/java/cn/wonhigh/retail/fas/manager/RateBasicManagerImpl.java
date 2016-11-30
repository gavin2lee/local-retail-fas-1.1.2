package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.RateBasic;
import cn.wonhigh.retail.fas.service.RateBasicService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-27 11:56:41
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
@Service("rateBasicManager")
class RateBasicManagerImpl extends BaseCrudManagerImpl implements RateBasicManager {
    @Resource
    private RateBasicService rateBasicService;

    @Override
    public BaseCrudService init() {
        return rateBasicService;
    }

	@Override
	public List<RateBasic> findStairRateBasicList(Map<String, Object> params)
			throws ManagerException {
		try {
			return rateBasicService.findStairRateBasicList(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}