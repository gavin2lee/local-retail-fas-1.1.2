package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-08-28 09:02:52
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
public interface PeriodBalanceManager extends BaseCrudManager {
	
	/**
	 * 设置结存对象其他额外属性
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<PeriodBalance> setExtendsPeriodBalanceProperties(List<PeriodBalance> periodBalances);

	public int findCompanyPeriodBalanceCount(Map<String, Object> params) throws ManagerException;

	public List<PeriodBalance> findCompanyPeriodBalancePages(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;
	
	public int findCompanyPeriodBalanceSubTotalCount(Map<String, Object> params) throws ManagerException;
	
	public List<PeriodBalance> findCompanyPeriodBalanceSubTotalPages(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;
	
	public int findCompanyPeriodBalanceSubTotalCount1(Map<String, Object> params) throws ManagerException;
	
	public List<PeriodBalance> findCompanyPeriodBalanceSubTotalPages1(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;
	
	public List<PeriodBalance> selectTotalRow(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;

	public void generateCompanyOwerGuest(Map<String, Object> params) throws ManagerException;
	
	public void generateCompanySalesSum(Map<String, Object> params) throws ManagerException;

	public void storeBalanceExport(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler) throws ManagerException;
	
	public void storeSdBalanceExport(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler) throws ManagerException;
	
	public int findSdBalanceCount(Map<String, Object> params) throws ManagerException;

	public List<PeriodBalance> findSdBalanceByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;
	
	public int findStoreBalanceCount(Map<String, Object> params) throws ManagerException;
	
	public List<PeriodBalance> findStoreBalanceList(SimplePage page,String orderBy,String orderByField,Map<String, Object> params) throws ManagerException;

	public int queryStoreBalanceCount(Map<String, Object> params) throws ManagerException;

	public List<PeriodBalance> queryStoreBalanceList(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ManagerException;
	
	public PeriodBalance queryStoreBalanceAll(Map<String, Object> params) throws ManagerException;

	public List<String> getAllItemNos(Map<String, Object> params) throws ManagerException ;

	public List<PeriodBalance> setStoreBalanceReportProperties(List<PeriodBalance> list);
	
	/**
	 * 店铺结算导出（鞋）
	 * @param page
	 * @param params
	 * @author wangyj
	 */
	public void queryStoreBalanceList(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler) throws ManagerException;

	public int checkPeriodBalanceData(CompanySettlePeriod settlePeriod) throws ManagerException;
	
}