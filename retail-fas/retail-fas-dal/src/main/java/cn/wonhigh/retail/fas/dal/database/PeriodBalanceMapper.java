package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;

import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-05-06 19:05:06
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface PeriodBalanceMapper extends BaseCrudMapper {

	public int getCompanyPeriodCount(@Param("params") Map<String, Object> params);
	public List<PeriodBalance> getCompanyPeriodByPage(@Param("page") SimplePage page,@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params") Map<String, Object> params);
	
	public int getCompanyPeriodSubTotalCount(@Param("params") Map<String, Object> params);
	public List<PeriodBalance> getCompanyPeriodSubTotalByPage(@Param("page") SimplePage page,@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params") Map<String, Object> params);
	
	public int getCompanyPeriodByPageCount(@Param("params") Map<String, Object> params);
	public List<PeriodBalance> getCompanyPeriodByPageNo(@Param("page") SimplePage page,@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params") Map<String, Object> params);
	public List<PeriodBalance> selectTotalRow(@Param("page") SimplePage page,@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params") Map<String, Object> params);

	public int deleteCompanyMonthBalance(@Param("params") Map<String, Object> params);
	public int deleteCompanyMonthPeriodBalance(@Param("params") Map<String, Object> currentMap);
	
	public int transferLastPeriodBalanceToCurrent(@Param("params") ItemCostConditionDto invCostGenerateDto);
	public void transferInventoryBookToPeriodBalance(@Param("params") ItemCostConditionDto invCostGenerateDto);
	  
	public int selectWeightedCostCount(@Param("params") Map<String, Object> params);
	public List<PeriodBalance> selectWeightedCostByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);

	public void batchUpdateBalanceItemCostByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);
	public int queryPeriodBalanceJoinItemCostCount(@Param("params") Map<String, Object> params);
	
	public int selectSdBalanceCount(@Param("params") Map<String, Object> params);
	public List<PeriodBalance> selectSdBalanceByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);
	
	/**
	 * 分页查询公司汇总数据
	 * @param params
	 * @return
	 */
	public List<PeriodBalance> queryCompanyPeriodByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);
	
	public List<PeriodBalance> selectTheFinalCostByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);
	
	public int batchUpdateWriteBackAmount(@Param("params") ItemCostConditionDto invCostGenerateDto );
	
	public int UpdateBackCostAmount(@Param("params")Map<String, Object> params);
	
	public int batchHandleCostDjustmentAmount(@Param("params")Map<String, Object> params);
	
	public int findStoreBalanceCount(@Param("params")Map<String, Object> params);
	
	public List<PeriodBalance> findStoreBalanceList(@Param("page")SimplePage page,@Param("orderBy")String orderBy,@Param("orderByField")String orderByField,@Param("params")Map<String, Object> params);
	
	public int queryStoreBalanceCount(@Param("params")Map<String, Object> params);
	
	public List<PeriodBalance> queryStoreBalanceList(@Param("page")SimplePage page,
			@Param("orderByField")String orderByField, @Param("orderBy")String orderBy, @Param("params")Map<String, Object> params);
	
	public PeriodBalance queryStoreBalanceAll(@Param("params")Map<String, Object> params);
	
	public List<String> getAllItemNos(@Param("params")Map<String, Object> params);
	
	public PeriodBalance getPeriodBalanceBLK(@Param("params")Map<String, Object> params);
	
	public int checkPeriodBalanceData(@Param("params")Map<String, Object> params);
}