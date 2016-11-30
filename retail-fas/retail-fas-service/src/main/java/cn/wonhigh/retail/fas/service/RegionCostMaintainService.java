package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
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
public interface RegionCostMaintainService extends BaseCrudService {
	
	/**
	 * 查询商品地区价格
	 * @param itemNo 货号
	 * @param zoneNo 地区编码
	 * @param effectiveDate 生效日期
	 * @return 商品的地区价
	 * @throws ServiceException
	 */
	public BigDecimal findRegionCost(String itemNo,String zoneNo,Date effectiveDate) throws ServiceException;
	
	public List<RegionCostMaintain> findZoneRegionCost(Map<String, Object> params) throws ServiceException;
	
	public void findAreaPriceExport(SimplePage page,Map<String, Object> params, Function<Object, Boolean> handler)throws ServiceException;
	
	/**
	 * 根据大区、货号、日期获取地区价
	 * @param zoneNo
	 * @param itemNo
	 * @param date
	 * @return
	 */
	RegionCostMaintain getRegionCost(String zoneNo, String itemNo, Date date) throws ServiceException;
	
	List<RegionCostMaintain> findRegionCostReport(SimplePage page,String orderByField, String orderBy,Map<String, Object> params)throws ServiceException;

	public int batchGenetareCostByRule(Map<String, Object> params)throws ServiceException;

}