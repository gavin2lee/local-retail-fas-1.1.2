package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BLKPeriodBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

public interface BaroqueCompanyPeriodBalanceService extends BaseCrudService{

	List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceByPage(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException;
	
	List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceByItemNO(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException;

	List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceFooter(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException;
	
	List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceFooterItem(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException;
	
	Integer getBaroqueCompanyPeriodBalanceCount(Map<String, Object> params) throws ServiceException;
	
	Integer getBaroqueCompanyPeriodBalanceCountItem(Map<String, Object> params) throws ServiceException;

}
