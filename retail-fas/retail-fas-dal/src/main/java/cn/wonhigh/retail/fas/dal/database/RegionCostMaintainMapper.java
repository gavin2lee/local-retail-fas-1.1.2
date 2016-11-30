package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
public interface RegionCostMaintainMapper extends BaseCrudMapper {

	public BigDecimal findRegionCost(@Param("params") Map<String, Object> params);
	
	public List<RegionCostMaintain> findZoneRegionCost(@Param("params") Map<String, Object> params);
	
	/**
	 * 根据大区、货号、日期获取地区价
	 * @param params
	 * @return
	 */
	RegionCostMaintain getRegionCost(@Param("params") Map<String, Object> params);
	
	List<RegionCostMaintain> selectRegionCostReport(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params") Map<String, Object> params);

	public List<RegionCostMaintain> selectItemByRuleNo(@Param("page") SimplePage page, @Param("addRuleNo")String addRuleNo,@Param("zoneNo")String zoneNo);
	
}