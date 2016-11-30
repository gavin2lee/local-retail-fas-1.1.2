package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
public interface AreaOtherStockOutBalanceManager extends BaseCrudManager {
	
	/**
	 * 修改结算单状态
	 * @param ids
	 * @param statusNo
	 * @return
	 */
	int modifyBillStatus(String[] ids, Map<String,Object> map);

	/**
	 * 删除结算单
	 * @param ids
	 * @return
	 * @throws ManagerException 
	 */
	int deleteBalanceBill(String[] ids) throws ManagerException;

	/**
	 * 保存结算单
	 * @param billBalance
	 * @return
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	BillBalance addBalanceBill(BillBalance billBalance) throws ServiceException, Exception;

	/**
	 * 查询结算单金额合计
	 * @return
	 */
	List<BillBalance> findTotalRow(Map<String, Object> params);

	/**
	 * 批量生成结算单
	 * @param list
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String,Object> addBalanceBillByBatch(BillBalance formData) throws ServiceException, Exception;

	/**
	 * 保存结算单(选单)
	 * @param lstItem
	 * @param billBalance
	 * @throws ServiceException 
	 * @throws Exception 
	 */
	Map<String,Object> addBalanceBillBySelect(List<Object> lstItem, BillBalance billBalance) throws ServiceException, Exception;

	/**
	 * 查询结算单
	 * @param formData
	 * @return
	 */
	BillBalance findBalanceBill(BillBalance formData);
	
}