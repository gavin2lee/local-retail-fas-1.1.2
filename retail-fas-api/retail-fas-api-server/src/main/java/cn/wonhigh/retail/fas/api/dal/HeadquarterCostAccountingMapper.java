package cn.wonhigh.retail.fas.api.dal;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.api.exception.RpcException;

public interface HeadquarterCostAccountingMapper {
	/**
	 * 查询商品总部价格
	 * @return 商品的总部价
	 * @throws RpcException
	 */
	public BigDecimal findHeadquarterCost(Map<String, Object> params);
	
	/**
	 * 校验商品供应商的总部成本是否存在
	 * @param params
	 * @return
	 */
	Integer qryHeadquarterCostExist(@Param("params") Map<String, Object> params);
	
	/**
	 * 校验商品厂进价是否生效
	 * @param params
	 * @return
	 */
	Integer qryHeadquarterCostIsEffective(@Param("params") Map<String, Object> params);
	
}
