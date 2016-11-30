package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.service.InventoryClassifyService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author wang.yj
 * @date 2016-08-28 09:02:52
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("inventoryClassifyManager")
class InventoryClassifyManagerImpl extends BaseCrudManagerImpl implements InventoryClassifyManager {

	@Resource
	private InventoryClassifyService inventoryClassifyService;

	@Override
	public BaseCrudService init() {
		return inventoryClassifyService;
	}

	@Override
	public int findInventoryClassInicationCount(Map<String, Object> params) throws ManagerException {
		try {
			return inventoryClassifyService.findInventoryClassInicationCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> findInventoryClassInicationByPage(
			SimplePage page, Map<String, Object> params) throws ManagerException {
		try {
			return inventoryClassifyService.findInventoryClassInicationByPage(page, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<PeriodBalance> findCateGoryCloumn(String sortColumn,
			String sortOrder, Map<String, Object> params) throws ManagerException {
		try {
			return inventoryClassifyService.findCateGoryCloumn(sortColumn,sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> findInventoryClassInicationTotal(
			Map<String, Object> params) throws ManagerException {
		try {
			return inventoryClassifyService.findInventoryClassInicationTotal(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findPeInventoryClassInicationCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return inventoryClassifyService.findPeInventoryClassInicationCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> findPeInventoryClassInicationByPage(
			SimplePage page, Map<String, Object> params) throws ManagerException {
		try {
			return inventoryClassifyService.findPeInventoryClassInicationByPage(page, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> findPeInventoryClassInicationTotal(
			Map<String, Object> params) throws ManagerException {
		try {
			return inventoryClassifyService.findPeInventoryClassInicationTotal(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

}