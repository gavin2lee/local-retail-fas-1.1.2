package cn.wonhigh.retail.fas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;
import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author wangxy
 * @date 2015-05-06 19:05:06
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public interface PeriodBalanceService extends BaseCrudService {

	int findCompanyPeriodBalanceCount(Map<String, Object> params)
			throws ServiceException;

	List<PeriodBalance> findCompanyPeriodBalancePages(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException;
	
	int findCompanyPeriodBalanceSubTotalCount(Map<String, Object> params) 
			throws ServiceException;
	
	List<PeriodBalance> findCompanyPeriodBalanceSubTotalPages(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) 
					throws ServiceException;
	
	int findCompanyPeriodBalanceSubTotalCount1(Map<String, Object> params)
			throws ServiceException;

	List<PeriodBalance> findCompanyPeriodBalanceSubTotalPages1(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) 
					throws ServiceException;
	
	List<PeriodBalance> selectTotalRow(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException;

	int deleteCompanyMonthBalance(Map<String, Object> params) throws ServiceException;
	
	int findSdBalanceCount(Map<String, Object> params)
			throws ServiceException;

	List<PeriodBalance> findSdBalanceByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException;
	
	/**
	 * 汇总本月结存数量
	 * @param condionDto
	 * @return
	 * @throws ServiceException
	 */
	public void accountMonthAllData(ItemCostConditionDto condionDto, Map<String, Object> currentMap) throws ServiceException;

	/**
	 * 生成本月加权成本
	 * @param condionDto
	 * @return
	 * @throws ServiceException
	 */
	public void generateMonthEndWeightedCost(ItemCostConditionDto condionDto, Map<String, Object> currentMap) throws ServiceException;

	/**
	 * 回写成本
	 * @param condionDto
	 * @return
	 * @throws ServiceException
	 */
	public void itemCostFeedbackToPeriodBalance(Map<String, Object> currentMap) throws ServiceException;

	public void storeBalanceExport(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler) throws ServiceException;

	public void storeSdBalanceExport(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler) throws ServiceException;
	
	public int selectWeightedCostCount(Map<String, Object> params) throws ServiceException;
	
	public List<PeriodBalance> selectWeightedCostByPage(SimplePage page,String orderByField,String orderBy, Map<String, Object> params,AuthorityParams authorityParams)throws ServiceException;
	
	/**
	 * 获取上月期末
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @param authorityParams
	 * @return
	 * @throws ServiceException
	 */
	public List<PeriodBalance> selectTheFinalCostByPage(SimplePage page,String orderByField,String orderBy, Map<String, Object> params,AuthorityParams authorityParams)throws ServiceException;
	
	/**
	 * 生成成本
	 * @param list
	 * @param dto
	 * @param params
	 * @param periodBalanceHashMap
	 * @param billBuyBalanceHashMap
	 * @throws ServiceException
	 */
	public void generateItemCost(List<String> list,ItemCostConditionDto dto,Map<String, Object> params,
			HashMap<String,CompanyPeriodBalance> periodBalanceHashMap,HashMap<String,BillBuyBalance> billBuyBalanceHashMap) throws ServiceException;
	
	/**
	 * 回写入库金额
	 * @param invCostGenerateDto
	 * @throws ServiceException
	 */
	public void writeBackAmount(ItemCostConditionDto invCostGenerateDto) throws ServiceException;
	
	public int findStoreBalanceCount(Map<String, Object> params) throws ServiceException;
	
	public List<PeriodBalance> findStoreBalanceList(SimplePage page,String orderBy,String orderByField,Map<String, Object> params) throws ServiceException;
	
	/**
	 * 删除店铺本月结存
	 * @param currentMap
	 * @return
	 * @throws ServiceException 
	 */
	public int deleteCompanyMonthPeriodBalance(Map<String, Object> currentMap) throws ServiceException;
	
	/**
	 * 从流水表汇总本期结存
	 * @param invCostGenerateDto
	 * @throws ServiceException 
	 */
	public void transferInventoryBookToPeriodBalance(ItemCostConditionDto itemCostConditionDto, Map<String, Object> currentMap) throws ServiceException;
	
	/**
	 * 新方案-查询店铺结存报表总计
	 * @param params
	 * @return
	 */
	public int queryStoreBalanceCount(Map<String, Object> params) throws ServiceException;

	/**
	 * 新方案-查询店铺结存报表明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ServiceException 
	 */
	public List<PeriodBalance> queryStoreBalanceList(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ServiceException;
	
	/**
	 * 店铺结存报表显示合计 
	 * @param params
	 * @return
	 * @throws ServiceException 
	 */
	public PeriodBalance queryStoreBalanceAll(Map<String, Object> params) throws ServiceException;

	/**
	 * 从period_balance表中获取当前查询条件所有ItemNos
	 * @param params
	 * @return
	 * @throws ServiceException 
	 */
	public List<String> getAllItemNos(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 店铺结算导出（鞋）
	 * @param page
	 * @param params
	 * @author wangyj
	 */
	public void queryStoreBalanceList(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler)throws ServiceException;

	/**
	 * 店铺结存合计行导出
	 * @param params
	 * @param handler
	 * @throws ServiceException 
	 */
	void queryStoreBalanceListAll(Map<String, Object> params, Function<Object, Boolean> handler) throws ServiceException;
	
	/**
	 * 检查当前period_balance表中期间净发生量与流水中是否一致
	 * @param settlePeriod
	 * @return
	 * @throws ServiceException 
	 */
	public int checkPeriodBalanceData(Map<String, Object> params) throws ServiceException;
}