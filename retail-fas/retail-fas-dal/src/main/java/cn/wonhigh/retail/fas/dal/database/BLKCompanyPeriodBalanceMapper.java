package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface BLKCompanyPeriodBalanceMapper  extends BaseCrudMapper {

	List<CompanyPeriodBalance> selectTheFinalCostByPage(@Param("params")Map<String, Object> params);

	public void deleteCompanyMonthPeriodBalance(@Param("params")ItemCostConditionDto dto);

	public void shopSummaryToCompany(@Param("params")ItemCostConditionDto conditionDto);

	public void batchUpdateWriteBackAmount(@Param("params")ItemCostConditionDto invCostGenerateDto);

	public void batchHandleCostDjustmentAmount(@Param("params")ItemCostConditionDto invCostGenerateDto);

	public int queryPeriodBalanceJoinItemCostCount(@Param("params")ItemCostConditionDto invCostGenerateDto);

	public void batchUpdateBalanceItemCostByPage(@Param("page")SimplePage page, @Param("orderByField")String orderByField, @Param("orderBy")String orderBy,@Param("params")ItemCostConditionDto dto);

	public List<CompanyPeriodBalance> selectCompanyPeriodBalanceGroupByStyleNo(@Param("params")ItemCostConditionDto dto,@Param("itemNos")List<String> itemNos);

	public List<String> findCompanyPeriodBalanceList(@Param("params")ItemCostConditionDto conditionDto);

}
