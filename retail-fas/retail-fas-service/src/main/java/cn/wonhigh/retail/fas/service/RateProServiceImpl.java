package cn.wonhigh.retail.fas.service;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.RatePro;
import cn.wonhigh.retail.fas.dal.database.RateProMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

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
@Service("rateProService")
class RateProServiceImpl extends BaseCrudServiceImpl implements RateProService {
    @Resource
    private RateProMapper rateProMapper;

    @Override
    public BaseCrudMapper init() {
        return rateProMapper;
    }

	@Override
	public RatePro findByRatePro(Map<String, Object> params)
			throws ServiceException {
		// TODO Auto-generated method stub
		return rateProMapper.findByRatePro(params);
	}
}