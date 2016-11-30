package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.DepositCash;
import cn.wonhigh.retail.fas.common.model.DepositCheck;
import cn.wonhigh.retail.fas.dal.database.DepositCashMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-22 13:51:56
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
@Service("depositCashService")
class DepositCashServiceImpl extends BaseCrudServiceImpl implements DepositCashService {
    @Resource
    private DepositCashMapper depositCashMapper;

    @Override
    public BaseCrudMapper init() {
        return depositCashMapper;
    }

	@Override
	public DepositCash findDetailCount(Map<String, Object> params) {
		return depositCashMapper.findDetailCount(params);
	}

	@Override
	public List<DepositCash> findDetail(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) {
		return depositCashMapper.findDetail(page, orderByField, orderBy, params);
	}

	@Override
	public BigDecimal getExistAmount(Map<String, Object> params) {
		return depositCashMapper.getExistAmount(params);
	}

	@Override
	public List<DepositCash> getPaidinAmount(Map<String, Object> params) {
		return depositCashMapper.getPaidinAmount(params);
	}
	
	@Override
	public BigDecimal getPaidinAmountCount(Map<String, Object> params) {
		return depositCashMapper.getPaidinAmountCount(params);
	}

	@Override
	public List<DepositCash> findLastDepositDate(Map<String, Object> params) throws ServiceException {
		try {
			return depositCashMapper.findLastDepositDate(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public DepositCheck findBeyondDates(Map<String, Object> params) throws ServiceException {
		try {
			return depositCashMapper.findBeyondDates(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}