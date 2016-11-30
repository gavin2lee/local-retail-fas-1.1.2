package cn.wonhigh.retail.fas.api.dal;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.ItemCost;

public interface ItemCostMapper {

	/**
	 * 查询某个货品出库的单位成本
	 * @param map
	 * @return
	 */
	public BigDecimal findSkuUnitCost(Map<String, Object> params);
	
	/**
	 * 根据公司编号、货号、日期获取单位成本
	 * @param params
	 * @return
	 */
	ItemCost getItemCost(@Param("params") Map<String, Object> params);
}
