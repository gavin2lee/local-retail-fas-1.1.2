package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.PeriodBalanceAudit;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-28 17:10:06
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
public interface PeriodBalanceAuditManager extends BaseCrudManager {
	
	/**
	 * 写生成成本日志
	 * @param params
	 * @update 是否更新
	 * @return
	 * */
	public int auditPeriodBalance(Map<String, Object> params, Boolean update) throws ManagerException; 
	
	public int countPeriodBalanceManagerAuditByPage(PeriodBalanceAudit dto)  throws ManagerException;
	
	public List<PeriodBalanceAudit> findPeriodBalanceManagerAuditByPage(SimplePage page, PeriodBalanceAudit dto)   throws ManagerException;
	
}