package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface ShopBalanceDateManager extends BaseCrudManager {

	ShopBalanceDate findAndConvertBalanceDate(String shopNo, String month) throws ManagerException;
	
//	Map<String,Object> generatorBillBalanDate(Map<String,Object> params)throws ManagerException;
	
	/**
	 * 查询指定店铺、指定结算月维护的结算期
	 * @param shopNo 店铺编码
	 * @param month 结算月
	 * @return 结算期对象
	 */
	ShopBalanceDate findBalanceDate(String shopNo, String month) throws ManagerException;
	
	public Map<String,String> validationShopBalanceDate(ShopBalanceDate shopBalanceDate) throws ManagerException;

	void generateBalanceDate(ShopBalanceDate shopBalanceDate,
			String currentMonth, String lastMonth) throws ManagerException;

	int findNewShopDateCount(Map<String, Object> params) throws ManagerException;

	List<ShopBalanceDate> findNewShopDateByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;
	
	int selectNoSetShopBalanceDateCount(Map<String, Object> params) throws ManagerException;

	List<ShopBalanceDate> selectNoSetShopBalanceDateByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;
	
	int selectShopBalanceDatePartOfRightCount(Map<String, Object> params) throws ManagerException;

	List<ShopBalanceDate> selectShopBalanceDatePartOfRightByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;
	
	
}