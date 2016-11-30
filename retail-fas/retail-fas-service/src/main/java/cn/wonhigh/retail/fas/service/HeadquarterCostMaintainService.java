package cn.wonhigh.retail.fas.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;

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
public interface HeadquarterCostMaintainService extends BaseCrudService {
	
	/**
	 * 获取最新的总部成本价
	 * @param params 查询参数
	 * @return 最新的总部成本价对象
	 * @throws ServiceException 异常
	 */
	HeadquarterCostMaintain getLastEffectiveModel(Map<String, Object> params) throws ServiceException;

	int findRegionPriceCount(Map<String, Object> params) throws ServiceException;
	
	public void batchAdd(List<HeadquarterCostMaintain> batchInsert) throws ServiceException;
	
	public void batchAddNUpdate(List<HeadquarterCostMaintain> batchInsert) throws ServiceException;
	/**
	 * 根据商品编号及日期查询总部价
	 * @param itemNo
	 * @param date
	 * @return
	 */
	HeadquarterCostMaintain getHeadquarterCost(String itemNo, Date date) throws ServiceException;

	int batchGenetareCostByRule(Map<String, Object> params)throws ServiceException;
}