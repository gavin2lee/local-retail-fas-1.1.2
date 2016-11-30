package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ExceptionPriceCheckDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2015-03-07 11:07:26
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
public interface ExceptionPriceBillService extends BaseCrudService {
	
	public int batchUpdatePriceSchedule() throws ServiceException;
	
	public int findRegionPriceExceptionsCount(Map<String, Object> params) throws ServiceException;

	public List<ExceptionPriceCheckDto> findRegionPriceExceptionsByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException;

	public int findPurchasePriceExceptionsCount(Map<String, Object> params) throws ServiceException;

	public List<ExceptionPriceCheckDto> findPurchasePriceExceptionsByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException;
	
	public int findFasAllPriceExceptionsCount(Map<String, Object> params) throws ServiceException;

	public List<ExceptionPriceCheckDto> findFasAllPriceExceptionsByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException;

	public int findTagPriceExceptionsCount(Map<String, Object> params) throws ServiceException;

	public List<ExceptionPriceCheckDto> findTagPriceExceptionsByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException;

	/**
	 * 地区价更新，bill_buy_balance, 1301,1304,1333,1372
	 * @param params
	 * @return
	 */
	public List<BillBuyBalance> getBuyAsnRegionPrices(Map<String, Object> params) throws ServiceException;
	public List<BillBuyBalance> getBuyReceiptRegionPrices(Map<String, Object> params) throws ServiceException;
	public List<BillBuyBalance> getBuyReturnRegionPrices(Map<String, Object> params) throws ServiceException;
	public List<BillBuyBalance> getBuyTransferInRegionPrices(Map<String, Object> params) throws ServiceException;

	/**
	 * 地区价更新，bill_sale_balance, 1301,1371
	 * @param params
	 * @return
	 */
	public List<BillSaleBalance> getSaleAsnRegionPrices(Map<String, Object> params) throws ServiceException;
	public List<BillSaleBalance> getSaleTransferOutRegionPrices(Map<String, Object> params) throws ServiceException;

	/**
	 * 采购价更新,bill_buy_balance, 1301,1304,1333
	 * @param params
	 * @return
	 */
	public List<BillBuyBalance> getBuyAsnPurchasePrices(Map<String, Object> params) throws ServiceException;
	public List<BillBuyBalance> getBuyReceiptPurchasePrices(Map<String, Object> params) throws ServiceException;
	public List<BillBuyBalance> getBuyReturnPurchasePrices(Map<String, Object> params) throws ServiceException;

	public int updateBillBuyBalanceCost(BillBuyBalance buyBalance) throws ServiceException;
	public int updateBillSaleBalanceCost(BillBuyBalance buyBalance) throws ServiceException;
	public int updateBillSaleBalanceCost(BillSaleBalance saleBalance) throws ServiceException;
	
}