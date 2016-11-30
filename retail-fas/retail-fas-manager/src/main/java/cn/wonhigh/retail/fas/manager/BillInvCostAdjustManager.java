package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillInvCostAdjust;
import cn.wonhigh.retail.fas.common.model.BillInvCostAdjustDtl;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-31 16:10:13
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
public interface BillInvCostAdjustManager extends BaseCrudManager {
	BillInvCostAdjust addFetchId(BillInvCostAdjust model) throws ManagerException, Exception;

	int delete(List<BillInvCostAdjust> list) throws ManagerException;

	int findCompanyCount(Map<String, Object> params) throws ManagerException;

	List<Company> findCompanyByPage(SimplePage page, String orderByField, String orderBy, Map<String, Object> params)
		throws ManagerException;

	BillInvCostAdjust confirm(BillInvCostAdjust model) throws ManagerException, Exception;
	
	PeriodBalance findPeriodBalance(Map<String, Object> params) throws ManagerException;
	
	PeriodBalance findPeriodBalanceBLK(Map<String, Object> params) throws ManagerException;
	
	Map<String, String> getControllerFlag() throws ManagerException;

}