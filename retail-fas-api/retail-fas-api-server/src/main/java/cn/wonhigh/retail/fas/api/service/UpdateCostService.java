package cn.wonhigh.retail.fas.api.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.logistics.base.common.exception.ServiceException;

/**
 * 更新单据表冗余的成本字段
 * @author user
 *
 */
public interface UpdateCostService {

	/**
	 * 更新order_dtl表成本
	 * @param map
	 * @return
	 */
	public int updateOrderDtl(Map<String, Object> params);
	
	/**
	 * 更新return_exchange_dtl表成本
	 * @param map
	 * @return
	 */
	public int updateReturnExchangeDtl(Map<String, Object> params);
	
	/**
	 * 更新bill_buy_balance表成本
	 * @param map
	 * @return
	 */
	public int updateBillBuyBalance(Map<String, Object> params);
	
	/**
	 * 更新bill_sale_balance表成本
	 * @param map
	 * @return
	 */
	public int updateBillSaleBalance(Map<String, Object> params);
	
	/**
	 * 更新period_balance表成本
	 * @param map
	 * @return
	 */
	public int updatePeriodBalance(@Param("params")Map<String, Object> params);

	public int updateRegionCost(Map<String, Object> params) throws ServiceException;

	public int updateHeadquaterCost(Map<String, Object> params) throws ServiceException;

	public int updateItemCost(Map<String, Object> params) throws ServiceException;
	
}