package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.wonhigh.retail.fas.common.model.OrderDtl;
import cn.wonhigh.retail.fas.common.model.OrderMain;
import cn.wonhigh.retail.fas.common.model.ReturnExchangeDtl;
import cn.wonhigh.retail.fas.common.model.ReturnExchangeMain;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 */
public interface POSRedundanceService extends BaseCrudService {

	/**
	 * 根据更新时间范围查询POS销售订单
	 * @param startTime
	 * @param endTime
	 * @param shardingFlag
	 * @return
	 */
	List<OrderMain> getOrderMain(Date startTime, Date endTime, String shardingFlag) throws ServiceException;
	
	/**
	 * 根据订单编号查询POS销售订单
	 * @param orderNo
	 * @param shardingFlag
	 * @return
	 * @throws ServiceException
	 */
	OrderMain getOrderMainByOrderNo(String orderNo, String shardingFlag) throws ServiceException;
	
	/**
	 * 更新POS销售订单
	 * @param id
	 * @param companyNo
	 * @param companyName
	 * @param shardingFlag
	 * @return
	 */
	int updateOrderMain(String id, String fasBillCode, String companyNo, String companyName, String shardingFlag) throws ServiceException;
	
	/**
	 * 根据更新时间范围查询POS销售订单明细
	 * @param orderNo
	 * @param shardingFlag
	 * @return
	 */
	List<OrderDtl> getOrderDtl(Date startTime, Date endTime, String shardingFlag) throws ServiceException;
	
	/**
	 * 更新POS销售订单明细
	 * @param id
	 * @param brandUnitNo
	 * @param brandUnitName
	 * @param unitCost
	 * @param regionCost
	 * @param headquarterCost
	 * @param shardingFlag
	 * @return
	 */
	int updateOrderDtl(String id, String brandUnitNo, String brandUnitName, BigDecimal unitCost, BigDecimal regionCost, BigDecimal headquarterCost, BigDecimal tagPriceNation, BigDecimal purchasePrice, BigDecimal materialPrice, BigDecimal factoryPrice, String shardingFlag) throws ServiceException;
	
	/**
	 * 根据更新时间范围查询POS退换货订单
	 * @param startTime
	 * @param endTime
	 * @param shardingFlag
	 * @return
	 */
	List<ReturnExchangeMain> getReturnExchangeMain(Date startTime, Date endTime, String shardingFlag) throws ServiceException;
	
	/**
	 * 根据订单编号查询POS退换货订单
	 * @param businessNo
	 * @param shardingFlag
	 * @return
	 * @throws ServiceException
	 */
	ReturnExchangeMain getReturnExchangeMainByBuinessNo(String businessNo, String shardingFlag) throws ServiceException;
	
	/**
	 * 更新POS退换货订单
	 * @param id
	 * @param companyNo
	 * @param companyName
	 * @param shardingFlag
	 * @return
	 */
	int updateReturnExchangeMain(String id, String fasBillCode, String companyNo, String companyName, String shardingFlag) throws ServiceException;
	
	/**
	 * 根据更新时间范围查询POS退换货订单明细
	 * @param businessNo
	 * @param shardingFlag
	 * @return
	 */
	List<ReturnExchangeDtl> getReturnExchangeDtl(Date startTime, Date endTime, String shardingFlag) throws ServiceException;
	
	/**
	 * 更新POS退换货订单明细
	 * @param id
	 * @param brandUnitNo
	 * @param brandUnitName
	 * @param unitCost
	 * @param regionCost
	 * @param headquarterCost
	 * @param shardingFlag
	 * @return
	 */
	int updateReturnExchangeDtl(String id, String brandUnitNo, String brandUnitName, BigDecimal unitCost, BigDecimal regionCost, BigDecimal headquarterCost, BigDecimal tagPriceNation, BigDecimal purchasePrice, BigDecimal materialPrice, BigDecimal factoryPrice, String shardingFlag) throws ServiceException;
	
	
}