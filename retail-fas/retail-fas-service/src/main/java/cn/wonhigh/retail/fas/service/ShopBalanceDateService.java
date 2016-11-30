package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-15 14:29:23
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
public interface ShopBalanceDateService extends BaseCrudService {
	
	int updateBalanceDateSet(ShopBalanceDate shopBalanceDate) throws ServiceException;
	
	int updateBalanceBillAlready(ShopBalanceDate shopBalanceDate) throws ServiceException;

	int findNewShopDateCount(Map<String, Object> params) throws ServiceException;

	List<ShopBalanceDate> findNewShopDateByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ServiceException;

	int selectNoSetShopBalanceDateCount(Map<String, Object> params) throws ServiceException;

	List<ShopBalanceDate> selectNoSetShopBalanceDateByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ServiceException;
	
	int selectShopBalanceDatePartOfRightCount(Map<String, Object> params) throws ServiceException;

	List<ShopBalanceDate> selectShopBalanceDatePartOfRightByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ServiceException;
	
}