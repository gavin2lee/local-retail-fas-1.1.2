package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillShopSaleOrder;
import cn.wonhigh.retail.fas.dal.database.BillShopSaleOrderMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-05 10:01:20
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
@Service("billShopSaleOrderService")
class BillShopSaleOrderServiceImpl extends BaseCrudServiceImpl implements BillShopSaleOrderService {
    @Resource
    private BillShopSaleOrderMapper billShopSaleOrderMapper;

    @Override
    public BaseCrudMapper init() {
        return billShopSaleOrderMapper;
    }

	@Override
	public int updateBalanceNo(BillShopSaleOrder billShopSaleOrder) {
		return billShopSaleOrderMapper.updateBalanceNo(billShopSaleOrder);
	}

	@Override
	public List<BillShopSaleOrder> selSumByCategory(
			BillShopSaleOrder billShopSaleOrder) {
		return billShopSaleOrderMapper.selSumByCategory(billShopSaleOrder);
	}

	@Override
	public List<BillShopSaleOrder> selectPromotions(
			BillShopSaleOrder billShopSaleOrder) {
		return billShopSaleOrderMapper.selectPaymentMethod(billShopSaleOrder);
	}

	@Override
	public List<BillShopSaleOrder> selectPaymentMethod(
			BillShopSaleOrder billShopSaleOrder) {
		return billShopSaleOrderMapper.selectPaymentMethod(billShopSaleOrder);
	}

	@Override
	public int selectDiscountSumCount(Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectDiscountSumCount(params);
	}

	@Override
	public List<String> selectDiscountSumColumn(Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectDiscountSumColumn(params);
	}

	@Override
	public Map<String, Object> selectDiscountSumTotal(Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectDiscountSumTotal(params);
	}

	@Override
	public List<Map<String, Object>> selectDiscountSumData(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectDiscountSumData(page, orderByField, orderBy, params);
	}

	@Override
	public int selectPayWaySumCount(Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWaySumCount(params);
	}

	@Override
	public List<Map<String, Object>> selectPayWaySumColumn(Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWaySumColumn(params);
	}

	@Override
	public Map<String, Object> selectPayWaySumTotal(Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWaySumTotal(params);
	}

	@Override
	public List<Map<String, Object>> selectPayWaySumData(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWaySumData(page, orderByField, orderBy, params);
	}
	
	@Override
	public int selectPayWayOrderCount(Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWayOrderCount(params);
	}

	@Override
	public Map<String, Object> selectPayWayOrderTotal(Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWayOrderTotal(params);
	}

	@Override
	public List<Map<String, Object>> selectPayWayOrderData(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWayOrderData(page, orderByField, orderBy, params);
	}

	@Override
	public int selectPayWayOrderBrandCount(Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWayOrderBrandCount(params);
	}

	@Override
	public Map<String, Object> selectPayWayOrderBrandTotal(
			Map<String, Object> params) throws ServiceException {
		return billShopSaleOrderMapper.selectPayWayOrderBrandTotal(params);
	}

	@Override
	public List<Map<String, Object>> selectPayWayOrderBrandData(
			SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		return billShopSaleOrderMapper.selectPayWayOrderBrandData(page, orderByField, orderBy, params);
	}

	@Override
	public int selectPayWayDayCount(Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWayDayCount(params);
	}

	@Override
	public Map<String, Object> selectPayWayDayTotal(Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWayDayTotal(params);
	}

	@Override
	public List<Map<String, Object>> selectPayWayDayData(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWayDayData(page, orderByField, orderBy, params);
	}

	@Override
	public int selectPayWayMonthCount(Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWayMonthCount(params);
	}

	@Override
	public Map<String, Object> selectPayWayMonthTotal(Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWayMonthTotal(params);
	}

	@Override
	public List<Map<String, Object>> selectPayWayMonthData(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return billShopSaleOrderMapper.selectPayWayMonthData(page, orderByField, orderBy, params);
	}
}