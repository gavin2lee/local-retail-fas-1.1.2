package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.CashBalance;
import cn.wonhigh.retail.fas.dal.database.CashBalanceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("cashBalanceService")
public class CashBalanceServiceImpl extends BaseCrudServiceImpl implements CashBalanceService {
	
	@Resource
	private CashBalanceMapper cashBalanceMapper;

	@Override
	public List<CashBalance> findCashBalanceList(SimplePage page,
			String orderBy, String orderByField, Map<String, Object> params) throws ServiceException {
		try {
			return cashBalanceMapper.findCashBalanceList(page, orderBy, orderByField, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int findCashBalanceCount(Map<String, Object> params) throws ServiceException {
		try {
			return cashBalanceMapper.findCashBalanceCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BaseCrudMapper init() {
		return cashBalanceMapper;
	}

}
