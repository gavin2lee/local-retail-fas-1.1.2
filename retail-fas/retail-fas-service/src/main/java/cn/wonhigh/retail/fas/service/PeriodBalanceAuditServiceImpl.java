package cn.wonhigh.retail.fas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.PeriodBalanceAudit;
import cn.wonhigh.retail.fas.dal.database.PeriodBalanceAuditMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("periodBalanceAuditService")
class PeriodBalanceAuditServiceImpl extends BaseCrudServiceImpl implements PeriodBalanceAuditService {
    @Resource
    private PeriodBalanceAuditMapper periodBalanceAuditMapper;

    @Override
    public BaseCrudMapper init() {
        return periodBalanceAuditMapper;
    }

	@Override
	public int auditPeriodBalance(Map<String, Object> params, Boolean update)
			throws ServiceException {
		try {
			if(update){
				return periodBalanceAuditMapper.updatePeriodBalanceAudit(params);
			} else {
				return periodBalanceAuditMapper.insertPeriodBalanceAudit(params);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int countPeriodBalanceManagerAuditByPage(PeriodBalanceAudit dto)
			throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNos", dto.getCompanyNos());
		params.put("month", dto.getMonth());
		params.put("year", dto.getYear());
		return periodBalanceAuditMapper.countPeriodBalanceAudit(params);
	}

	@Override
	public List<PeriodBalanceAudit> findPeriodBalanceManagerAuditByPage(
			SimplePage page, PeriodBalanceAudit dto) throws ServiceException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNos", dto.getCompanyNos());
			params.put("month", dto.getMonth());
			params.put("year", dto.getYear());
			return periodBalanceAuditMapper.queryPeriodBalanceAuditByPage(page, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}