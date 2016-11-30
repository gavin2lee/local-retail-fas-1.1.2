package cn.wonhigh.retail.fas.dal.database.cost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.logistics.base.common.utils.SimplePage;

public interface ItemCostMapperExt {
	
	public Integer findOrderBillNoCount(@Param("params")Map<String, Object> params);
	
	public List<String> findOrderBillNo(@Param("params")Map<String, Object> params,@Param("page") SimplePage page);
	
	public void updateOrderItemCost(@Param("params")Map<String, Object> params);
	
	
	public Integer findExchangeBillNoCount(@Param("params")Map<String, Object> params);
	
	public List<String> findExchangeBillNo(@Param("params")Map<String, Object> params,@Param("page") SimplePage page);
	
	public void updateExchangeItemCost(@Param("params")Map<String, Object> params);
}
