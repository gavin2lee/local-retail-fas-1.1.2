package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ExceptionPriceCheckDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
public interface ExceptionPriceBillMapper extends BaseCrudMapper {

	public int updateBuyAsnForAreaAndHq();

	public int updateBuyAsnForHqAndSupplier();

	public int updateSaleAsnForHqAndArea();

	public int updateBuyReceiptForHq();

	public int updateBuyReceiptForArea();

	public int updateSaleTransferOut();

	public int updateBuyTransferIn();

	public int updateSaleTransferOutHQ();

	public int updateBuyTransferInHQ();
	
	public int updateSaleAreaOthers();

	public int updateBuyAsnCostCheckForAreaAndHq();

	public int updateBuyAsnCostCheckForHqAndSupplier();

	public int updateSaleAsnCostCheckForHqAndArea();

	public int updateBuyReceiptCostCheckForHq();

	public int updateBuyReceiptCostCheckForArea();

	public int updateSaleTransferOutCostCheck();

	public int updateBuyTransferInCostCheck();

	public int updateSaleAreaOthersCostCheck();
	
	//查询异常价格，直接查询大池表，不用异常表了
	public int getRegionPriceExceptionsCount(@Param("params")Map<String, Object> params);

	public List<ExceptionPriceCheckDto> getRegionPriceExceptionsByPage(
			@Param("page") SimplePage page,@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params") Map<String, Object> params);
	
	public int getPurchasePriceExceptionsCount(@Param("params")Map<String, Object> params);

	public List<ExceptionPriceCheckDto> getPurchasePriceExceptionsByPage(
			@Param("page") SimplePage page,@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params") Map<String, Object> params);

	public int getFasAllPriceExceptionsCount(@Param("params")Map<String, Object> params);

	public List<ExceptionPriceCheckDto> getFasAllPriceExceptionsByPage(
			@Param("page") SimplePage page,@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params") Map<String, Object> params);
	
	public void updateBuyAsnForAreaAndSupplier();

	public void updateBuyAsnCostCheckForAreaAndSupplier();

	public int findTagPriceExceptionsCount(@Param("params")Map<String, Object> params);

	public List<ExceptionPriceCheckDto> findTagPriceExceptionsByPage(
			@Param("page") SimplePage page,@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params") Map<String, Object> params);

	public List<BillBuyBalance> getBuyAsnRegionPrices(@Param("params") Map<String, Object> params);
	public List<BillBuyBalance> getBuyReceiptRegionPrices(@Param("params") Map<String, Object> params);
	public List<BillBuyBalance> getBuyReturnRegionPrices(@Param("params") Map<String, Object> params);
	public List<BillBuyBalance> getBuyTransferInRegionPrices(@Param("params") Map<String, Object> params);

	public List<BillSaleBalance> getSaleAsnRegionPrices(@Param("params") Map<String, Object> params);
	public List<BillSaleBalance> getSaleTransferOutRegionPrices(@Param("params") Map<String, Object> params);

	public List<BillBuyBalance> getBuyAsnPurchasePrices(@Param("params") Map<String, Object> params);
	public List<BillBuyBalance> getBuyReceiptPurchasePrices(@Param("params") Map<String, Object> params);
	public List<BillBuyBalance> getBuyReturnPurchasePrices(@Param("params") Map<String, Object> params);

	public int updateBillBuyBalanceCost(BillBuyBalance buyBalance);
	public int updateBillSaleBalanceCost(BillBuyBalance buyBalance);
	public int updateSaleBalanceCost(BillSaleBalance saleBalance);
	
}