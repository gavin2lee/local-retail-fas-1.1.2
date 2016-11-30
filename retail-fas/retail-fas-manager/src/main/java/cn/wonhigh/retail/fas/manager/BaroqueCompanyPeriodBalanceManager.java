package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BLKPeriodBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface BaroqueCompanyPeriodBalanceManager  extends BaseCrudManager {
	
	List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceByPage(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException;
	
	List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceByItemNo(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException;

	List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceFooter(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params)  throws ManagerException;
	
	List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceFooterItem(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params)  throws ManagerException;

	Integer getBaroqueCompanyPeriodBalanceCount(Map<String, Object> params) throws ManagerException;

	Integer getBaroqueCompanyPeriodBalanceCountItem(Map<String, Object> params) throws ManagerException;

	public List<BLKPeriodBalance> setExtendsPeriodBalanceProperties(List<BLKPeriodBalance> blkPeriodBalances);
}
