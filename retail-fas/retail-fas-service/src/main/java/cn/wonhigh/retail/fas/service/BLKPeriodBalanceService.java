package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.BLKPeriodBalance;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.OrderUnit;
import cn.wonhigh.retail.fas.common.model.Organ;
import cn.wonhigh.retail.fas.common.model.Store;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

public interface BLKPeriodBalanceService extends BaseCrudService {

	/**
	 * 按照小计方式查询巴洛克-公司期间结存总计对象
	 * @param params
	 * @return
	 * @throws ServiceException 
	 */
	BLKPeriodBalance findBLKPeriodBalanceSubTotalCount(Map<String, Object> params) throws ServiceException;

	/**
	 * 按照小计方式查询巴洛克-公司期间结存明细
	 * @param page
	 * @param orderBy
	 * @param orderByField
	 * @param params
	 * @return
	 * @throws ServiceException 
	 */
	List<BLKPeriodBalance> findBLKPeriodBalanceSubTotalPages(SimplePage page, String orderBy, String orderByfield, Map<String, Object> params) throws ServiceException;

	/**
	 * 查询巴洛克-公司期间结存总计对象
	 * @param params
	 * @return
	 * @throws ServiceException 
	 */
	BLKPeriodBalance findBLKPeriodBalanceCount(Map<String, Object> params) throws ServiceException;

	/**
	 * 查询巴洛克-公司期间结存明细
	 * @param page
	 * @param orderBy
	 * @param orderByfield
	 * @param params
	 * @return
	 * @throws ServiceException 
	 */
	List<BLKPeriodBalance> findBLKPeriodBalancePages(SimplePage page, String orderBy, String orderByfield, Map<String, Object> params) throws ServiceException;

	int selectStoreBanalceCount(Map<String, Object> params) throws ServiceException;

	List<BLKPeriodBalance> selectStoreBalanceList(SimplePage page, String orderBy, String orderByField, Map<String, Object> params) throws ServiceException;

	public int deleteCompanyMonthPeriodBalance(ItemCostConditionDto dto) throws ServiceException;

	public void transferInventoryBookToPeriodBalance(ItemCostConditionDto dto) throws ServiceException;
	
}
