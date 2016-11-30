package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-29 13:20:36
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
public interface DataHqAreaExchangeManager extends BaseCrudManager {
	
	/**
	 * 查询需要对调的跨区调货单总数
	 * @param params
	 * @return
	 */
	int selectSaleTransferBillCount(Map<String,Object> params) throws ManagerException;
	
	/**
	 * 查询需要对调的跨区调货单明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectSaleTransferBill(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ManagerException;
	
	/**
	 * 查询需要对调的跨区调货单总数
	 * @param params
	 * @return
	 */
	int selectBuyTransferBillCount(Map<String,Object> params) throws ManagerException;
	
	/**
	 * 查询需要对调的跨区调货单明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectBuyTransferBill(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ManagerException;
	
	/**
	 * 对调的跨区调货单
	 * @param billList
	 * @param changeBillType
	 * @return
	 * @throws ManagerException
	 */
	Map<String, Object> changeBillType(List<Object> billList, String changeBillType) throws ManagerException;

	/**
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> findTotalRow(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> findBuyTotalRow(Map<String, Object> params);
	
}