package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.CashBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface CashBalanceMapper extends BaseCrudMapper  {
	
	public List<CashBalance> findCashBalanceList(@Param("page")SimplePage page, @Param("orderBy")String orderBy,@Param("orderByField")String orderByField, @Param("params")Map<String, Object> params);

	public int findCashBalanceCount(@Param("params")Map<String, Object> params);


}
