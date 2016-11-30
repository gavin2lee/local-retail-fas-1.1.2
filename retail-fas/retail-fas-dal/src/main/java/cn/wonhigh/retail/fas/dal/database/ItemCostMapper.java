package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.ItemCost;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-03-04 10:33:05
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
public interface ItemCostMapper extends BaseCrudMapper {

	int getItemCostUnmatchRegionPriceCount(@Param("params") Map<String, Object> params);

	List<ItemCost> getItemCostUnmatchRegionPriceByPage(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params") Map<String, Object> params);

	int deleteCompanyMonthCost(@Param("params") ItemCostConditionDto invCostGenerateDto);
	
	/**
	 * 根据公司编号、货号、日期获取单位成本
	 * @param params
	 * @return
	 */
	ItemCost getItemCost(@Param("params") Map<String, Object> params);

	/**
	 * 更新地区价
	 * @param map
	 * @return
	 */
	public List<String> findPosOrderRegionCostId(@Param("params") Map<String, Object> params);
	public int updatePosOrderRegionCost(@Param("params") Map<String, Object> params);
	public List<String> findPosReturnExchangeRegionCostId(@Param("params") Map<String, Object> params);
	public int updatePosReturnExchangeRegionCost(@Param("params") Map<String, Object> params);
	public List<String> findBuybalanceRegionCostId(@Param("params") Map<String, Object> params);
	public int updateBuyBalanceRegionCost(@Param("params") Map<String, Object> params);
	public List<String> findSaleBalanceRegionCostId(@Param("params") Map<String, Object> params);
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

	public int getBuyBalanceItemCostUnmatchCount(@Param("params") Map<String, Object> params);
	
	public int getSaleBalanceItemCostUnmatchCount(@Param("params") Map<String, Object> params);
	
	public List<BillBuyBalance> getBuyBalanceItemCostUnmatchByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);
	
	public List<BillSaleBalance> getSaleBalanceItemCostUnmatchByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);
	
	public List<BillBuyBalance> getBuyBalanceRegionCostUnmatchByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);

	public List<BillSaleBalance> getSaleBalanceRegionCostUnmatchByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);

	public List<BillBuyBalance> getBuyBalanceHeadquaterCostUnmatchByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);

	public List<BillSaleBalance> getSaleBalanceHeadquaterCostUnmatchByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);
	
	int updateBuyCostById(BillBuyBalance buyBalance);
	
	int updateSaleCostById(BillSaleBalance saleBalance);	

	/**
	 * 定时任务更新价格
	 * @param map
	 * @return
	 */
	public int updatePosOrderRegionCostSchedule(@Param("params") Map<String, Object> params);
	public int updatePosReturnExchangeRegionCostSchedule(@Param("params") Map<String, Object> params);
	public int updatePOSOrderHQSchedule(@Param("params") Map<String, Object> params);
	public int updateReturnExchangeHQSchedule(@Param("params") Map<String, Object> params);
	
	public int updatePosOrderItemCostSchedule(@Param("params") Map<String, Object> params);
	public int updateReturnExchangeItemCostSchedule(@Param("params") Map<String, Object> params);
	
	public int getBBICUnmatchCountSchedule(@Param("params") Map<String, Object> params);
	public List<BillBuyBalance> getBBICUnmatchByPageSchedule(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);
	
	public int getSBICUnmatchCountSchedule(@Param("params") Map<String, Object> params);
	public List<BillSaleBalance> getSBICUnmatchByPageSchedule(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);
	
	
	public int deleteCurrentMonthCost(@Param("params") Map<String, Object> params);

	public int batchInsertItemCost(@Param("subList") List<ItemCost> currentItemCosts);

	public int selectCompanyLastCostCount(@Param("params") Map<String, Object> lastMap);

	public List<ItemCost> selectCompanyLastCostByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	public List<ItemCost> findAdjustedItemCosts(@Param("params") Map<String, Object> currentMap);

	public int transferLastItemCostToCurrentMonth(@Param("params") ItemCostConditionDto invCostGenerateDto);

	public int updateItemCostInfo(@Param("params") Map<String, Object> currentMap);

	public int updateBLKPosOrderItemCost(@Param("params")Map<String, Object> params);

	public List<ItemCost> findItemCostForCompanyPeriodBalance(@Param("params") Map<String, Object> params);

	public int findBLKItemCostCount(@Param("params")Map<String, Object> params);
	
	public List<ItemCost> findBLKItemCostList(@Param("page") SimplePage page,
			@Param("orderBy") String orderBy,@Param("orderByField") String orderByField,
			@Param("params") Map<String, Object> params);

	void runSqlForC(@Param("params")Map<String, Object> params);

	void runSqlForU(@Param("params")Map<String, Object> params);

	void runSqlForD(@Param("params")Map<String, Object> params);

}