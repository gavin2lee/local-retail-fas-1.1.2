package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.RegionPriceRule;

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
public interface RegionPriceRuleMapper extends BaseCrudMapper {
	
	int checkIsRuleRefered(@Param("params") Map<String, Object> params);

	List<RegionPriceRule> findRuleByItemNoAndZoneNo(@Param("itemNo")String itemNo,@Param("zoneNo")String zoneNo);

	List<RegionPriceRule> findRuleByParams(@Param("params") Map<String, Object> params);
	
}