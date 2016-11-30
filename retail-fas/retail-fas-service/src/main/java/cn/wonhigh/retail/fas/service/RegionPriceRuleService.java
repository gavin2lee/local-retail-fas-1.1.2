package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.RegionPriceRule;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface RegionPriceRuleService extends BaseCrudService {

	int checkIsRuleRefered(Map<String, Object> params) throws ServiceException;

	List<RegionPriceRule>  findRuleByItemNoAndZoneNo(String itemNo,String zoneNo)throws ServiceException;

	List<RegionPriceRule> findRuleByParams(Map<String, Object> params)throws ServiceException;
	
}