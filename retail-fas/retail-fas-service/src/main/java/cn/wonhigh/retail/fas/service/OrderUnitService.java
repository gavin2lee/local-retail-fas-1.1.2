package cn.wonhigh.retail.fas.service;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.OrderUnit;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 订货单位服务
 * @author huang.xb1
 * @date  2014-07-28 14:19:21
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
public interface OrderUnitService extends BaseCrudService {
	
	List<OrderUnit> getOrderUnitByShopNo(String shopNo) throws ServiceException;

	/**
	 * 根据编码查询名称
	 * @param orderUnitNo
	 * @return
	 * @throws ServiceException
	 */
	String findOrderUnitNameByNo(String orderUnitNo) throws ServiceException;
	
}