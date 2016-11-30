package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.OrderDtl;
import cn.wonhigh.retail.fas.common.model.OrderMain;
import cn.wonhigh.retail.fas.common.model.ReturnExchangeDtl;
import cn.wonhigh.retail.fas.common.model.ReturnExchangeMain;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * 
 */
public interface POSRedundanceMapper extends BaseCrudMapper {

	/**
	 * 根据更新时间范围查询POS销售订单
	 * @param params
	 * @return
	 */
	List<OrderMain> getOrderMain(@Param("params")Map<String, Object> params);
	
	/**
	 * 根据订单编号查询POS销售订单
	 * @param params
	 * @return
	 */
	OrderMain getOrderMainByOrderNo(@Param("params")Map<String, Object> params);
	
	/**
	 * 更新POS销售订单
	 * @param params
	 * @return
	 */
	int updateOrderMain(@Param("params")Map<String, Object> params);
	
	/**
	 * 根据更新时间范围查询POS销售订单明细
	 * @param params
	 * @return
	 */
	List<OrderDtl> getOrderDtl(@Param("params")Map<String, Object> params);
	
	/**
	 * 更新POS销售订单明细
	 * @param params
	 * @return
	 */
	int updateOrderDtl(@Param("params")Map<String, Object> params);
	
	/**
	 * 根据更新时间范围查询POS退换货订单
	 * @param params
	 * @return
	 */
	List<ReturnExchangeMain> getReturnExchangeMain(@Param("params")Map<String, Object> params);
	
	/**
	 * 根据订单编号查询POS退换货订单
	 * @param params
	 * @return
	 */
	ReturnExchangeMain getReturnExchangeMainByBuinessNo(@Param("params")Map<String, Object> params);
	
	/**
	 * 更新POS退换货订单
	 * @param params
	 * @return
	 */
	int updateReturnExchangeMain(@Param("params")Map<String, Object> params);
	
	/**
	 * 根据更新时间范围查询POS退换货订单明细
	 * @param params
	 * @return
	 */
	List<ReturnExchangeDtl> getReturnExchangeDtl(@Param("params")Map<String, Object> params);
	
	/**
	 * 更新POS退换货订单明细
	 * @param params
	 * @return
	 */
	int updateReturnExchangeDtl(@Param("params")Map<String, Object> params);
	
}