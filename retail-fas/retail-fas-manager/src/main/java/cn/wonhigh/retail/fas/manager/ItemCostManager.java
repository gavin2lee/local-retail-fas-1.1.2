package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.vo.ItemPriceCost;
import cn.wonhigh.retail.gms.common.dto.PeriodBalanceManagerAuditDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-08-25 13:45:42
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface ItemCostManager extends BaseCrudManager {
	
	/**
	 * 校验成本核算的单据是否存在异常
	 * @param itemCost
	 * @return
	 * @throws ManagerException
	 */
	public boolean checkHasBillException(ItemCost itemCost) throws ManagerException;
	
	/**
	 * 生成成本-巴洛克
	 * @param itemCost
	 * @return
	 * @throws ManagerException
	 */
	public boolean generateBLKItemCost(ItemCost itemCost) throws ManagerException;
	
	public int findItemCostUnmatchRegionPriceCount(Map<String, Object> params) throws ManagerException;

	public List<ItemCost> findItemCostUnmatchRegionPriceByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;
	
	public int findItemsCostAuditCount(PeriodBalanceManagerAuditDto params) throws ManagerException;
	
	public List<PeriodBalanceManagerAuditDto> findItemsCostAudit(SimplePage page, PeriodBalanceManagerAuditDto params) throws ManagerException;

	/**
	 * 分配成本到出入库单据池以及销售退货单
	 * @param params
	 * @return
	 */
	public void assignBillItemCost(Company company,String brandUnitNo,Date start ,Date end) throws ManagerException;
	
	/**
	 * 分配地区价到据池以及销售退货单
	 * @param params
	 * @return
	 */
	public void assignBillRegionCost(Company company,String brandUnitNo,Date start ,Date end) throws ManagerException;
	
	/**
	 * 分配总部价到据池以及销售退货单
	 * @param params
	 * @return
	 */
	public void assignBillHQCost(Company company,String brandUnitNo,Date start ,Date end) throws ManagerException;

	
//	/**
//	 * 定时任务分配成本、地区价、总部价，手动设置shardingFlag
//	 * @param params
//	 * @return
//	 */
//	public int assignBillItemCostSchedule(Company company, Date start,Date end) throws ManagerException;
//	
//	public int assignBillRegionCostSchedule(Company company, Date start,Date end) throws ManagerException;
//	
//	public int assignBillHQCostSchedule(Company company, Date start,Date end) throws ManagerException;
	
	public boolean checkGMSClosingDate(ItemCost itemCost) throws ManagerException;

	public void generateStoreItemCost(ItemCostConditionDto conditionDto) throws ManagerException;
	public void generateCompanyItemCost(ItemCostConditionDto conditionDto) throws ManagerException;
	
	public void generateCostAndPeriodCase(ItemCostConditionDto dto, Map<String, Object> params, ItemCost itemCost)throws ManagerException;
	
	
	/**
	 * 获取成本和价格
	 * @param companyNo
	 * @param itemNo
	 * @param outDate
	 * @return
	 * @throws ManagerException
	 */
	ItemPriceCost getItemPriceAndCost(String companyNo,String itemNo,Date outDate) throws ManagerException;
	
	
	ItemPriceCost getItemAllPrice(String zoneNo,String itemNo,Date outDate) throws ManagerException;
	
	
	public BigDecimal getItemCost(String companyNo, String itemNo, Date date) throws ManagerException;

	public void genearateSelfUnitCost(ItemCost itemCost) throws ManagerException;

	public void runSql(Map<String, Object> params, String type);



}