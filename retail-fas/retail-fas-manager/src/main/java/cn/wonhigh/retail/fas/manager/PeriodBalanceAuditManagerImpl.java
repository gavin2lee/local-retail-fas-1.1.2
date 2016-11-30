package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.PeriodBalanceAudit;
import cn.wonhigh.retail.fas.service.PeriodBalanceAuditService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

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
@Service("periodBalanceAuditManager")
class PeriodBalanceAuditManagerImpl extends BaseCrudManagerImpl implements PeriodBalanceAuditManager {
    @Resource
    private PeriodBalanceAuditService periodBalanceAuditService;

    @Override
    public BaseCrudService init() {
        return periodBalanceAuditService;
    }

	@Override
	public int auditPeriodBalance(Map<String, Object> params, Boolean update)
			throws ManagerException {
		try {
			return periodBalanceAuditService.auditPeriodBalance(params, update);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int countPeriodBalanceManagerAuditByPage(PeriodBalanceAudit dto)
			throws ManagerException {
		try {
			return periodBalanceAuditService.countPeriodBalanceManagerAuditByPage(dto);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<PeriodBalanceAudit> findPeriodBalanceManagerAuditByPage(
			SimplePage page, PeriodBalanceAudit dto) throws ManagerException {
		try {
			return periodBalanceAuditService.findPeriodBalanceManagerAuditByPage(page, dto);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
}