package cn.wonhigh.retail.fas.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.ItemCost;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface ItemCostService extends BaseCrudService {

	public int findItemCostUnmatchRegionPriceCount(Map<String, Object> params) throws ServiceException;

	public List<ItemCost> findItemCostUnmatchRegionPriceByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) throws ServiceException;

	public int deleteCompanyMonthCost(ItemCostConditionDto invCostGenerateDto) throws ServiceException;
	
	/**
	 * 根据公司编号、货号、日期获取单位成本
	 * @param zoneNo
	 * @param itemNo
	 * @param date
	 * @return
	 */
	ItemCost getItemCost(String companyNo, String itemNo, Date date) throws ServiceException;
	
	/**
	 * 分配成本价、地区价、总部价
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int updateRegionCost(Company company,String brandUnitNo,Date start ,Date end) throws ServiceException;
	
	public int updateHeadquaterCost(Company company,String brandUnitNo,Date start ,Date end) throws ServiceException;
	
	public int updateItemCost(Company company,String brandUnitNo,Date start ,Date end) throws ServiceException;
	
	
	public int updatePOSOrderHQSchedule(Map<String, Object> params)
			throws ServiceException;

	public int updateReturnExchangeHQSchedule(Map<String, Object> params)
			throws ServiceException;

	public int updateBuyBalanceHeadquaterCost(Map<String, Object> params)
			throws ServiceException;

	public int updateSaleBalanceHeadquaterCost(Map<String, Object> params)
			throws ServiceException;
	
	/**
	 * 分配成本价、地区价、总部价--定时任务用，需要手动设置shardingFlag
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int updateRegionCostSchedule(Map<String, Object> params) throws ServiceException;
	
	//public int updateHeadquaterCostSchedule(Map<String, Object> params) throws ServiceException;
	
	public int updateItemCostSchedule(Map<String, Object> params) throws ServiceException;

	/**
	 * 巴洛克查询成本
	 * @param params
	 * @return
	 * @throws ServiceException 
	 */
	public List<ItemCost> findItemCostForCompanyPeriodBalance(Map<String, Object> params) throws ServiceException;

	public int batchInsertItemCost(List<ItemCost> itemCostList);
	
	/**
	 * 巴洛克查询总计
	 * @param params
	 * @return
	 * @throws ServiceException 
	 */
	public int findBLKItemCostCount(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 巴洛克查询明细
	 * @param page
	 * @param orderBy
	 * @param orderByField
	 * @param params
	 * @return
	 * @throws ServiceException 
	 */
	public List<ItemCost> findBLKItemCostList(SimplePage page,String orderBy,String orderByField,Map<String, Object> params) throws ServiceException;

	public void runSql(Map<String, Object> params, String type);

}