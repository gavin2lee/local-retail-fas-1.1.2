package cn.wonhigh.retail.fas.service;

import java.util.List;

import java.util.Map;


import cn.wonhigh.retail.fas.common.model.ExpectCash;
import cn.wonhigh.retail.fas.dal.database.ExpectCashMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * ExpectCashServiceImpl
 * @author tang.yu
 * @date  2014-08-26 16:05:20
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
@Service("expectCashService")
class ExpectCashServiceImpl extends BaseCrudServiceImpl implements ExpectCashService {
    @Resource
    private ExpectCashMapper expectCashMapper;

    @Override
    public BaseCrudMapper init() {
        return expectCashMapper;
    }

	@Override
	public List<ExpectCash> findExpectCashByParams(SimplePage page,
			String sortColumn, String orderBy, Map<String, Object> params) throws ServiceException {
		try {
			return expectCashMapper.findExpectCashByParams(page, sortColumn, orderBy, params);
		} catch (ServiceException e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public int countExpectCashByParams(Map<String, Object> params) throws ServiceException {
		try {
			return expectCashMapper.countExpectCashByParams(params);
		} catch (ServiceException e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ExpectCash> findExpectCashByParams(Map<String, Object> params) throws ServiceException {
		try {
			return expectCashMapper.selectExpectCashByParams(params);
		} catch (ServiceException e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public int modifyById(ExpectCash expectCash) throws ServiceException {
		try {
			return expectCashMapper.updateByPrimaryKey(expectCash);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
		
	}

	@Override
	public List<ExpectCash> processExpectCashBalanceDiff(
			Map<String, Object> params) throws ServiceException {
		return expectCashMapper.processExpectCashBalanceDiff(params);
	}

	@Override
	public List<ExpectCash> processUseExpectCashBalanceDiff(
			Map<String, Object> params) throws ServiceException {
		return expectCashMapper.processUseExpectCashBalanceDiff(params);
	}

	@Override
	public ExpectCash getExpectCashAmount(Map<String, Object> params)
			throws ServiceException {
		return expectCashMapper.getExpectCashAmount(params);
	}
}