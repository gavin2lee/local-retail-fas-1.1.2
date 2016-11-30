package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.OrderDtl;
import cn.wonhigh.retail.fas.common.model.OrderMain;
import cn.wonhigh.retail.fas.common.model.ReturnExchangeDtl;
import cn.wonhigh.retail.fas.common.model.ReturnExchangeMain;
import cn.wonhigh.retail.fas.dal.database.POSRedundanceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-09-01 15:49:31
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
@Service("posRedundanceService")
class POSRedundanceServiceImpl extends BaseCrudServiceImpl implements POSRedundanceService {

    @Resource
    private POSRedundanceMapper posRedundanceMapper;

	@Override
	public BaseCrudMapper init() {
		return posRedundanceMapper;
	}
	
	@Override
	public List<OrderMain> getOrderMain(Date startTime, Date endTime,
			String shardingFlag) throws ServiceException {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("shardingFlag", shardingFlag);
		return posRedundanceMapper.getOrderMain(params);
	}

	@Override
	public int updateOrderMain(String id, String fasBillCode, String companyNo, String companyName,
			String shardingFlag) throws ServiceException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("fasBillCode", fasBillCode);
		params.put("companyNo", companyNo);
		params.put("companyName", companyName);
		params.put("shardingFlag", shardingFlag);
		return posRedundanceMapper.updateOrderMain(params);
	}

	@Override
	public List<OrderDtl> getOrderDtl(Date startTime, Date endTime,
			String shardingFlag) throws ServiceException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("shardingFlag", shardingFlag);
		return posRedundanceMapper.getOrderDtl(params);
	}

	@Override
	public int updateOrderDtl(String id, String brandUnitNo,
			String brandUnitName, BigDecimal unitCost, BigDecimal regionCost,
			BigDecimal headquarterCost, BigDecimal tagPriceNation, BigDecimal purchasePrice, BigDecimal materialPrice, BigDecimal factoryPrice, String shardingFlag) throws ServiceException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("brandUnitNo", brandUnitNo);
		params.put("brandUnitName", brandUnitName);
		params.put("unitCost", unitCost);
		params.put("regionCost", regionCost);
		params.put("headquarterCost", headquarterCost);
		params.put("tagPriceNation", tagPriceNation);
		params.put("purchasePrice", purchasePrice);
		params.put("materialPrice", materialPrice);
		params.put("factoryPrice", factoryPrice);
		params.put("shardingFlag", shardingFlag);
		return posRedundanceMapper.updateOrderDtl(params);
	}

	@Override
	public List<ReturnExchangeMain> getReturnExchangeMain(Date startTime,
			Date endTime, String shardingFlag) throws ServiceException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("shardingFlag", shardingFlag);
		return posRedundanceMapper.getReturnExchangeMain(params);
	}

	@Override
	public int updateReturnExchangeMain(String id, String fasBillCode, String companyNo,
			String companyName, String shardingFlag) throws ServiceException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("fasBillCode", fasBillCode);
		params.put("companyNo", companyNo);
		params.put("companyName", companyName);
		params.put("shardingFlag", shardingFlag);
		return posRedundanceMapper.updateReturnExchangeMain(params);
	}

	@Override
	public List<ReturnExchangeDtl> getReturnExchangeDtl(Date startTime, Date endTime,
			String shardingFlag) throws ServiceException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("shardingFlag", shardingFlag);
		return posRedundanceMapper.getReturnExchangeDtl(params);
	}

	@Override
	public int updateReturnExchangeDtl(String id, String brandUnitNo,
			String brandUnitName, BigDecimal unitCost, BigDecimal regionCost,
			BigDecimal headquarterCost, BigDecimal tagPriceNation, BigDecimal purchasePrice, BigDecimal materialPrice, BigDecimal factoryPrice, String shardingFlag) throws ServiceException{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("brandUnitNo", brandUnitNo);
		params.put("brandUnitName", brandUnitName);
		params.put("unitCost", unitCost);
		params.put("regionCost", regionCost);
		params.put("headquarterCost", headquarterCost);
		params.put("tagPriceNation", tagPriceNation);
		params.put("purchasePrice", purchasePrice);
		params.put("materialPrice", materialPrice);
		params.put("factoryPrice", factoryPrice);
		params.put("shardingFlag", shardingFlag);
		return posRedundanceMapper.updateReturnExchangeDtl(params);
	}

	@Override
	public OrderMain getOrderMainByOrderNo(String orderNo, String shardingFlag)
			throws ServiceException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderNo", orderNo);
		params.put("shardingFlag", shardingFlag);
		return posRedundanceMapper.getOrderMainByOrderNo(params);
	}

	@Override
	public ReturnExchangeMain getReturnExchangeMainByBuinessNo(
			String businessNo, String shardingFlag) throws ServiceException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("businessNo", businessNo);
		params.put("shardingFlag", shardingFlag);
		return posRedundanceMapper.getReturnExchangeMainByBuinessNo(params);
	}

}