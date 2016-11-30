package cn.wonhigh.retail.fas.api.dal;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 更新单据表冗余的成本字段
 * @author user
 *
 */
public interface UpdateCostMapper {

	/**
	 * 查询order_dtl表更新条数
	 * @param map
	 * @return
	 */
	public int selectOrderDtlCount(@Param("params")Map<String,Object> params);
	
	/**
	 * 更新order_dtl表成本
	 * @param map
	 * @return
	 */
	public int updateOrderDtl(@Param("params")Map<String,Object> params);
	
	/**
	 * 查询return_exchange_dtl表更新条数
	 * @param map
	 * @return
	 */
	public int selectReturnExchangeDtlCount(@Param("params")Map<String,Object> params);
	
	/**
	 * 更新return_exchange_dtl表成本
	 * @param map
	 * @return
	 */
	public int updateReturnExchangeDtl(@Param("params")Map<String, Object> params);
	
	/**
	 * 查询bill_buy_balance表更新条数
	 * @param map
	 * @return
	 */
	public int selectBillBuyBalanceCount(@Param("params")Map<String,Object> params);
	
	/**
	 * 更新bill_buy_balance表成本
	 * @param map
	 * @return
	 */
	public int updateBillBuyBalance(@Param("params")Map<String, Object> params);
	
	/**
	 * 查询bill_sale_balance表更新条数
	 * @param map
	 * @return
	 */
	public int selectBillSaleBalanceCount(@Param("params")Map<String,Object> params);
	
	/**
	 * 更新bill_sale_balance表成本
	 * @param map
	 * @return
	 */
	public int updateBillSaleBalance(@Param("params")Map<String, Object> params);
	
	/**
	 * 查询period_balance表更新条数
	 * @param map
	 * @return
	 */
	public int selectPeriodBalanceCount(@Param("params")Map<String,Object> params);
	
	/**
	 * 更新period_balance表成本
	 * @param map
	 * @return
	 */
	public int updatePeriodBalance(@Param("params")Map<String, Object> params);
	
	/**
	 * 更新地区价
	 * @param map
	 * @return
	 */
	public int updatePosOrderRegionCost(@Param("params") Map<String, Object> params);
	public int updatePosReturnExchangeRegionCost(@Param("params") Map<String, Object> params);
	public int updateBuyBalanceRegionCost(@Param("params") Map<String, Object> params);
	public int updateSaleBalanceRegionCost(@Param("params") Map<String, Object> params);

	/**
	 * 更新总部价
	 * @param map
	 * @return
	 */
	public int updatePosOrderHeadquaterCost(@Param("params") Map<String, Object> params);
	public int updatePosReturnExchangeHeadquaterCost(@Param("params") Map<String, Object> params);
	public int updateBuyBalanceHeadquaterCost(@Param("params") Map<String, Object> params);
	public int updateSaleBalanceHeadquaterCost(@Param("params") Map<String, Object> params);
	
	/**
	 * 更新单位成本表
	 * @param map
	 * @return
	 */
	public int updatePosOrderItemCost(@Param("params") Map<String, Object> params);
	public int updatePosReturnExchangeItemCost(@Param("params") Map<String, Object> params);
	public int updateBuyBalanceItemCost(@Param("params") Map<String, Object> params);
	public int updateSaleBalanceItemCost(@Param("params") Map<String, Object> params);
	
}
