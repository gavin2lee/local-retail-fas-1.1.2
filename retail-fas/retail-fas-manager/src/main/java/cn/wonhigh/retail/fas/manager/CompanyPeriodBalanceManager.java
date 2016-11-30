package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface CompanyPeriodBalanceManager extends BaseCrudManager {
	
	/**
	 * 生成销售汇总表
	 * @param params
	 * @throws ManagerException
	 */
	public void generateCompanySalesSum(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 生成累计欠客汇总表
	 * @param params
	 * @throws ManagerException
	 */
	public void generateCompanyOwerGuest(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 汇总公司结存-小计
	 * @param params
	 * @throws ManagerException
	 */
	public int findCompanyPeriodBalanceSubTotalCount(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 汇总公司结存-小计
	 * @param params
	 * @throws ManagerException
	 */
	public List<CompanyPeriodBalance> findCompanyPeriodBalanceSubTotalPages(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;

	/**
	 * 分页查询公司结存，关联欠客、销售分类汇总
	 * @param params
	 * @throws ManagerException
	 */
	public List<CompanyPeriodBalance> findCompanyPeriodBalancePages(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;

	/**
	 * 设置结存对象其他额外属性
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<CompanyPeriodBalance> setExtendsPeriodBalanceProperties(List<CompanyPeriodBalance> periodBalances);

	/**
	 * 汇总公司结存-总计
	 * @param params
	 * @throws ManagerException
	 */
	public List<CompanyPeriodBalance> selectTotalRow(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;
	
}