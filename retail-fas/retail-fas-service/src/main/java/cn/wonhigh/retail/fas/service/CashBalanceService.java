package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CashBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

public interface CashBalanceService extends BaseCrudService {
	
	public List<CashBalance> findCashBalanceList(SimplePage page, String orderBy,String orderByField, Map<String, Object> params) throws ServiceException;

	public int findCashBalanceCount(Map<String, Object> params) throws ServiceException;

}
