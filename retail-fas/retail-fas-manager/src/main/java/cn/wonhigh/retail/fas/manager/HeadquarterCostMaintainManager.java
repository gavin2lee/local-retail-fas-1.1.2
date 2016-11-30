package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.model.HeadquarterPriceRule;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-01 09:25:14
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
public interface HeadquarterCostMaintainManager extends BaseCrudManager {
	
	/**
	 * 根据商品属性信息和总部加价规则设置来生成总部价
	 * @param params
	 * @return
	 */
	public void generateHeadquarterCost(HeadquarterCostMaintain headquarterCost) throws ManagerException;
	
	/**
	 * 批量生成总部价
	 * @param params
	 * @return
	 */
	public HeadquarterCostMaintain batchGenerateHeadquarterCost(HeadquarterCostMaintain headquarterCost, String batchType) throws ManagerException;

	public int findRegionPriceCount(Map<String, Object> params) throws ManagerException;

	public Map<String, Object> getPriceRuleData(HeadquarterCostMaintain headquarterCostMaintain) throws ManagerException;

	public List<HeadquarterPriceRule> findRegionAddRulesByBiz(
			HeadquarterCostMaintain headCostMaintain) throws ManagerException;
	
	/**
	 * 根据单据日期和商品编码查询总部成本价
	 * @param itemNo
	 * @param billDate
	 * @return
	 */
	public BigDecimal findHqCostForBalanceBill(String itemNo, Date billDate);
	
	public void batchAdd(List<HeadquarterCostMaintain> batchInsert)throws ManagerException;
	
	public void batchAddNUpdate(List<HeadquarterCostMaintain> batchInsert)throws ManagerException;
	
	public HeadquarterCostMaintain addHeadquarterCost(HeadquarterCostMaintain headquarterCost, String batchType)throws ManagerException;

	public int batchGenetareCostByRule(Map<String, Object> params)throws ManagerException;

	public int batchGenetareCostNew(Map<String, Object> params)throws Exception;
	
}