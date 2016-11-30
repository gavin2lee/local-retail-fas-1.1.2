package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.CashBalance;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.service.CashBalanceService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("cashBalanceManager")
public class CashBalanceManagerImpl extends BaseCrudManagerImpl implements CashBalanceManager {
	@Resource
	private CashBalanceService cashBalanceService;
	
	@Override
	protected BaseCrudService init() {
		return cashBalanceService;
	}

	@Override
	public List<CashBalance> findCashBalanceList(SimplePage page,
			String orderBy, String orderByField, Map<String, Object> params) throws ManagerException {
		try {
			return cashBalanceService.findCashBalanceList(page, orderBy, orderByField, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findCashBalanceCount(Map<String, Object> params) throws ManagerException {
		try {
			return cashBalanceService.findCashBalanceCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}


}
