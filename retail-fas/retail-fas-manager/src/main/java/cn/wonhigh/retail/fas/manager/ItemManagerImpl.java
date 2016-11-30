package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.service.ItemService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author ouyang.zm
 * @date 2014-09-02 12:17:21
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
@Service("itemManager")
class ItemManagerImpl extends BaseCrudManagerImpl implements ItemManager {
	@Resource
	private ItemService itemService;

	@Override
	public BaseCrudService init() {
		return itemService;
	}

	@Override
	public List<Item> findComboGridDataByCondition(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params, AuthorityParams authorityParams) throws ManagerException {
		try {
			return itemService.findComboGridDataByCondition(page, orderByField, orderBy, params, authorityParams);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public Integer findStyleNoCount(Map<String, Object> params) throws ManagerException {
		try {
			return itemService.findStyleNoCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<Item> findStyleNo(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException{
		try {
			return itemService.findStyleNo(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int findStyleCount(Map<String, Object> params) throws ManagerException {
		try {
			return itemService.findStyleCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<Item> findStyleList(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ManagerException {
		try {
			return itemService.findStyleList(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
}