package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.model.RegionPriceRule;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
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
public interface RegionCostMaintainManager extends BaseCrudManager {
	
	/**
	 * 根据商品属性信息和总部加价规则设置来生成地区价
	 * @param params
	 * @return
	 */
	public void genetareRegionCost(RegionCostMaintain regionCost) throws ManagerException;
	
	/**
	 * 批量生成地区价
	 * @param brandMap 
	 * @param zoneMap 
	 * @param params
	 * @return
	 */
	public RegionCostMaintain batchGenetareRegionCost(RegionCostMaintain regionCost, Map<String, ZoneInfo> zoneMap, Map<String, Brand> brandMap) throws ManagerException;

	public Map<String, Object> getPriceRuleData(
			RegionCostMaintain regionCostMaintain) throws ManagerException;

	public List<RegionPriceRule> findRegionAddRulesByBiz(
			RegionCostMaintain regionCostMaintain) throws ManagerException;
	
	/**
	 * 根据公司编码，商品，单据日期查询地区价
	 * @param params
	 * @return
	 */
	public BigDecimal findRegionCostForBalanceBill(RegionCostMaintain regionCost) throws ManagerException;

	/**
	 * 根据公司编码，查询价格大区
	 * @param params
	 * @return
	 */
	public String findPriceZoneByCompanyNo(String string) throws ManagerException;
	
	
	public List<RegionCostMaintain> findZoneRegionCost(Map<String, Object> params) throws ManagerException;

	/**
	 * 新增地区价
	 * @param params
	 * @return
	 */
	public RegionCostMaintain addRegionCost(RegionCostMaintain regionCost) throws ManagerException;
	
	
	public void findAreaPriceExport(SimplePage page,Map<String,Object> params,Function<Object, Boolean> handler) throws ManagerException;
	
	List<RegionCostMaintain> findRegionCostReport(SimplePage page,String orderByField, String orderBy,Map<String, Object> params) throws ManagerException;

	public int batchGenetareCostByRule(Map<String, Object> params)throws ManagerException;

	public int batchGenetareCostNew(Map<String, Object> params)throws Exception;
	
}