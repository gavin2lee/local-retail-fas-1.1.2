package cn.wonhigh.retail.fas.manager;

import java.util.Map;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;

/**
 * 更新单据冗余成本字段Manager接口 
 * @author xia.j
 * @date  2015-05-11 15:04:50
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
public interface RedundancyCostManager {
	
	/**
	 * 更新单据成本
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateCost(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 更新bill_buy_balance表成本
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int updateBuyCost(Map<String,Object> params)throws ManagerException;
	
	/**
	 * 更新bill_sale_balance表成本
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int updateSaleCost(Map<String,Object> params)throws ManagerException;
	
	/**
	 * 更新order_dtl表成本
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int updateOrderCost(Map<String,Object> params)throws ManagerException;
	
	/**
	 * 更新return_exchange_dtl表成本
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int updateReturnCost(Map<String,Object> params) throws ManagerException;
	
	/**
	 * 更新period_balance表成本
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int updatePeriodCost(Map<String,Object> params) throws ManagerException;

	public Map<String, Object> allocateBillItemCost(Map<String, Object> params) throws ManagerException;
	
}