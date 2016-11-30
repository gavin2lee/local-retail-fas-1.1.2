package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-15 16:12:30
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface CompanyPeriodBalanceService extends BaseCrudService {
	
	public int findCompanyPeriodBalanceSubTotalCount(Map<String, Object> params) 
			throws ServiceException;
	
	public List<CompanyPeriodBalance> findCompanyPeriodBalanceSubTotalPages(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) 
					throws ServiceException;
	
	public List<CompanyPeriodBalance> findCompanyPeriodBalancePages(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) 
					throws ServiceException;
	
	public List<CompanyPeriodBalance> selectTotalRow(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException;
	
	public int deleteMonthlyBalance(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 删除本月结存
	 * @param invCostGenerateDto
	 * @return
	 * @throws ServiceException
	 */
	public int deleteCompanyMonthPeriodBalance(Map<String, Object> currentMap) throws ServiceException;

	/**
	 * 汇总流水表的记录，生成结存预处理数据
	 * @param invCostGenerateDto
	 * @return
	 * @throws ServiceException
	 */
	public int summaryInventoryBook(ItemCostConditionDto conditionDto) throws ServiceException;

	/**
	 * 预处理, 结转上期的结存数据
	 * @param invCostGenerateDto
	 * @return
	 * @throws ServiceException
	 */
	public void transferLastPeriodBalance(ItemCostConditionDto conditionDto, Map<String, Object> currentMap) throws ServiceException;

	/**
	 * 生成本月加权成本
	 * @param invCostGenerateDto
	 * @return
	 * @throws ServiceException
	 */
	public void generateWeightedCost(ItemCostConditionDto conditionDto, Map<String, Object> currentMap) throws ServiceException;
	
	public boolean generateBalancePreData(ItemCostConditionDto conditionDto, Map<String, Object> currentMap)throws ServiceException;
	
	/**
	 * 回写出库类入库类金额
	 * @param currentMap
	 * @throws ServiceException
	 */
	public void itemCostFeedbackToPeriodBalance(Map<String, Object> currentMap)throws ServiceException;
	
	/**
	 * 回写库存调整金额
	 * @throws ServiceException
	 */
	public void writeBackCostAdjustmentAmout(Map<String, Object> params) throws ServiceException;
	
	public int selectWeightedCostCount(Map<String, Object> params)
			throws ServiceException;
	
	public List<CompanyPeriodBalance> selectTheFinalCostByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params,
			AuthorityParams authorityParams) throws ServiceException;

	public void writeBackAmount(ItemCostConditionDto invCostGenerateDto) throws ServiceException;

}