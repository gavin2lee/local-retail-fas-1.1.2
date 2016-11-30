package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillShopSaleOrder;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface BillShopSaleOrderService extends BaseCrudService {
	
	public int updateBalanceNo(BillShopSaleOrder  billShopSaleOrder);
	
	
	public List<BillShopSaleOrder> selSumByCategory(BillShopSaleOrder  billShopSaleOrder);
	
	
    public List<BillShopSaleOrder> selectPromotions(BillShopSaleOrder  billShopSaleOrder);
	
	public List<BillShopSaleOrder> selectPaymentMethod(BillShopSaleOrder  billShopSaleOrder);
	
	/**
	 * 扣率汇总-查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectDiscountSumCount(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 扣率汇总-查询动态列
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<String> selectDiscountSumColumn(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 扣率汇总-合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectDiscountSumTotal(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 扣率汇总-数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectDiscountSumData(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按扣率汇总-查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectPayWaySumCount(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按扣率汇总-查询动态列
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectPayWaySumColumn(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按扣率汇总-合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectPayWaySumTotal(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按扣率汇总-数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectPayWaySumData(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按销售订单查询-查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectPayWayOrderCount(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按销售订单查询-合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectPayWayOrderTotal(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按销售订单查询-数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectPayWayOrderData(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按销售订单查询-明细分摊，品牌汇总-查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectPayWayOrderBrandCount(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按销售订单查询-明细分摊，品牌汇总-合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectPayWayOrderBrandTotal(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按销售订单查询-明细分摊，品牌汇总-数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectPayWayOrderBrandData(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按明细分摊，日统计-查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectPayWayDayCount(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按明细分摊，日统计-合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectPayWayDayTotal(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按明细分摊，日统计-数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectPayWayDayData(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按明细分摊，月统计-查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectPayWayMonthCount(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按明细分摊，月统计-合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectPayWayMonthTotal(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 支付金额按明细分摊，月统计-数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectPayWayMonthData(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ServiceException;
	
}