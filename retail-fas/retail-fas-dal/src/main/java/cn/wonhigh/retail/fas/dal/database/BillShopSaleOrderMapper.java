package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillShopSaleOrder;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
public interface BillShopSaleOrderMapper extends BaseCrudMapper {
	public int updateBalanceNo(BillShopSaleOrder  billShopSaleOrder);

	public List<BillShopSaleOrder> selSumByCategory(BillShopSaleOrder  billShopSaleOrder);
	
	public BillShopSaleOrder selectSumAmount(@Param("balanceNo")String balanceNo);
	
	public List<BillShopSaleOrder> selectPromotions(BillShopSaleOrder  billShopSaleOrder);
	
	public List<BillShopSaleOrder> selectPaymentMethod(BillShopSaleOrder  billShopSaleOrder);
	
	/**
	 * 扣率汇总-查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectDiscountSumCount(@Param("params")Map<String, Object> params);
	
	/**
	 * 扣率汇总-查询动态列
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<String> selectDiscountSumColumn(@Param("params")Map<String, Object> params);
	
	/**
	 * 扣率汇总-合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectDiscountSumTotal(@Param("params")Map<String, Object> params);
	
	/**
	 * 扣率汇总-数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectDiscountSumData(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按扣率汇总-查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectPayWaySumCount(@Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按扣率汇总-查询动态列
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectPayWaySumColumn(@Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按扣率汇总-合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectPayWaySumTotal(@Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按扣率汇总-数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectPayWaySumData(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按销售订单查询-查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectPayWayOrderCount(@Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按销售订单查询-合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectPayWayOrderTotal(@Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按销售订单查询-数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectPayWayOrderData(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按销售订单查询-明细分摊，品牌汇总-查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectPayWayOrderBrandCount(@Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按销售订单查询-明细分摊，品牌汇总-合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectPayWayOrderBrandTotal(@Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按销售订单查询-明细分摊，品牌汇总-数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectPayWayOrderBrandData(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按明细分摊，日统计-查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectPayWayDayCount(@Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按明细分摊，日统计-合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectPayWayDayTotal(@Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按明细分摊，日统计-数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectPayWayDayData(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按明细分摊，月统计-查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectPayWayMonthCount(@Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按明细分摊，月统计-合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectPayWayMonthTotal(@Param("params")Map<String, Object> params);
	
	/**
	 * 支付金额按明细分摊，月统计-数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectPayWayMonthData(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
};