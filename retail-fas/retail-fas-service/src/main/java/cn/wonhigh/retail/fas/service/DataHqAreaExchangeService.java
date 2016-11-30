package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface DataHqAreaExchangeService extends BaseCrudService {
	
	/**
	 * 查询需要对调的跨区调货单总数
	 * @param params
	 * @return
	 */
	int selectSaleTransferBillCount(Map<String,Object> params) throws ServiceException;
	
	/**
	 * 查询需要对调的跨区调货单明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectSaleTransferBill(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ServiceException;
	
	/**
	 * 查询需要对调的跨区调货单总数
	 * @param params
	 * @return
	 */
	int selectBuyTransferBillCount(Map<String,Object> params) throws ServiceException;
	
	/**
	 * 查询需要对调的跨区调货单明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectBuyTransferBill(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ServiceException;
	
	/**
	 * 对调Sale表调货出库单
	 * @param billSaleBalance
	 * @return
	 */
	int updateSaleTransferBill(BillSaleBalance billSaleBalance) throws ServiceException;
	
	/**
	 * 对调buy表调货入库单
	 * @param billBuyBalance
	 * @return
	 */
	int updateBuyTransferBill(BillBuyBalance billBuyBalance) throws ServiceException;

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