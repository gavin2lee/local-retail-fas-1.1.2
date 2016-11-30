package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;

import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途
 * 
 * @author wangxy
 * @date 2015-12-15 16:12:30
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public interface CompanyPeriodBalanceMapper extends BaseCrudMapper {

	public int getCompanyPeriodSubTotalCount(
			@Param("params") Map<String, Object> params);

	public List<CompanyPeriodBalance> getCompanyPeriodSubTotalByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	public List<CompanyPeriodBalance> selectTotalRow(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	public List<CompanyPeriodBalance> getCompanyPeriodByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	public int deleteMonthlyBalance(@Param("params") Map<String, Object> params);
	
	public int deleteCompanyMonthPeriodBalance(@Param("params") Map<String, Object> currentMap);

	public int summaryInventoryBook(@Param("params") ItemCostConditionDto invCostGenerateDto);

	public int transferLastPeriodBalanceToCurrent(@Param("params") ItemCostConditionDto invCostGenerateDto);
	  
	public int queryPeriodBalanceJoinItemCostCount(@Param("params") Map<String, Object> params);

	public void batchUpdateBalanceItemCostByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);
	
	public int batchUpdateWriteBackAmount(@Param("params") ItemCostConditionDto invCostGenerateDto );
	
	public int batchHandleCostDjustmentAmount(@Param("params")Map<String, Object> params);
	
	public int ShopSummaryToCompany(@Param("params") ItemCostConditionDto invCostGenerateDto);
	
	public int selectWeightedCostCount(@Param("params") Map<String, Object> params);
	
	public List<CompanyPeriodBalance> selectTheFinalCostByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);

}