package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.BLKPeriodBalance;

import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface BLKPeriodBalanceMapper extends BaseCrudMapper {

	public BLKPeriodBalance findBLKPeriodBalanceSubTotalCount(@Param("params")Map<String, Object> params);

	public List<BLKPeriodBalance> findBLKPeriodBalanceSubTotalPages(@Param("page")SimplePage page, @Param("orderBy")String orderBy, @Param("orderByField")String orderByField, @Param("params")Map<String, Object> params);

	public BLKPeriodBalance findBLKPeriodBalanceCount(@Param("params")Map<String, Object> params);

	public List<BLKPeriodBalance> findBLKPeriodBalancePages(@Param("page")SimplePage page, @Param("orderBy")String orderBy, @Param("orderByField")String orderByField, @Param("params")Map<String, Object> params);

	public void transferInventoryBookToPeriodBalance(@Param("params")ItemCostConditionDto itemCostConditionDto);
 
	public int deleteCompanyMonthPeriodBalance(@Param("params")ItemCostConditionDto dto);

	public int selectStoreBanalceCount(@Param("params")Map<String, Object> params);

	public List<BLKPeriodBalance> selectStoreBalanceList(@Param("page")SimplePage page, @Param("orderBy")String orderBy, @Param("orderByField")String orderByField, @Param("params")Map<String, Object> params);

	public void transferLastPeriodBalanceToCurrent(@Param("params")ItemCostConditionDto itemCostConditionDto);
}
