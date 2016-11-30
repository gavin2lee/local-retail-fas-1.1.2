package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CashBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface CashBalanceManager extends BaseCrudManager {

	public List<CashBalance> findCashBalanceList(SimplePage page, String orderBy,String orderByField, Map<String, Object> params) throws ManagerException;

	public int findCashBalanceCount(Map<String, Object> params) throws ManagerException;

}
