package cn.wonhigh.retail.fas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.OrderUnit;
import cn.wonhigh.retail.fas.dal.database.OrderUnitMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("orderUnitService")
class OrderUnitServiceImpl extends BaseCrudServiceImpl implements OrderUnitService {
	@Resource
	private OrderUnitMapper orderUnitMapper;

	@Override
	public BaseCrudMapper init() {
		return orderUnitMapper;
	}

	@Override
	public List<OrderUnit> getOrderUnitByShopNo(String shopNo) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", shopNo);
		OrderUnit orderUnit = new OrderUnit();
		return orderUnitMapper.selectByShopNo(orderUnit, params);
	}

	@Override
	public String findOrderUnitNameByNo(String orderUnitNo)
			throws ServiceException {
		if (StringUtils.isEmpty(orderUnitNo)) {
			return null;
		}
		return orderUnitMapper.findOrderUnitNameByNo(orderUnitNo);
	}
}