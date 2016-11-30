package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.PEAPDto;
import cn.wonhigh.retail.fas.service.PEReportService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-25 11:34:16
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
@Service("peReportManager")
class PEReportManagerImpl implements PEReportManager {

	@Resource
	private PEReportService peReportService;
	
	@Override
	public int findAPAgingCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return peReportService.findAPAgingCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<PEAPDto> findAPAgingByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return peReportService.findAPAgingByPage(page,sortColumn,sortOrder,params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<PEAPDto> findAPAgingFooter(
			Map<String, Object> params) throws ManagerException {
		try {
			return peReportService.findAPAgingFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int findAPPlanCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return peReportService.findAPPlanCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<Map<String, Object>> findAPPlanByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return peReportService.findAPPlanByPage(page,sortColumn,sortOrder,params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<Map<String, Object>> findAPPlanFooter(Map<String, Object> params) throws ManagerException {
		try {
			return peReportService.findAPPlanFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<Map<String, Object>> findAPPlanColumn(Map<String, Object> params)
			throws ManagerException {
		try {
			return peReportService.findAPPlanColumn(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int findReportSendCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return peReportService.findReportSendCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<PEAPDto> findReportSendByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return peReportService.findReportSendByPage(page,sortColumn,sortOrder,params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<PEAPDto> findReportSendFooter(Map<String, Object> params)
			throws ManagerException {
		try {
			return peReportService.findReportSendFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
	
}