package cn.wonhigh.retail.fas.api.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yougou.logistics.base.common.exception.ServiceException;

import cn.wonhigh.retail.fas.api.dal.UpdateCostMapper;

/**
 * 更新单据表冗余的成本字段
 * @author user
 *
 */
@Service("updateCostService")
class UpdateCostServiceImpl implements UpdateCostService {
	
	private Logger log = Logger.getLogger(getClass());
	
	private int pageSize = 1000;
   
	@Resource
    private UpdateCostMapper updateCostMapper;

	@Override
	public int updateOrderDtl(Map<String, Object> params) {
		
		int totalCount = updateCostMapper.selectOrderDtlCount(params);
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		int resultCount = 0;
		for(int i = 0; i < totalPage; i++) {
			long startRowNum = i * pageSize;
			params.put("startRowNum", Long.valueOf(startRowNum));
			params.put("selectCount", Integer.valueOf(pageSize));
			resultCount += updateCostMapper.updateOrderDtl(params);
		}
		
		return resultCount;
	}

	@Override
	public int updateReturnExchangeDtl(Map<String, Object> params) {
		
		int totalCount = updateCostMapper.selectReturnExchangeDtlCount(params);
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		int resultCount = 0;
		for(int i = 0; i < totalPage; i++) {
			long startRowNum = i * pageSize;
			params.put("startRowNum", Long.valueOf(startRowNum));
			params.put("selectCount", Integer.valueOf(pageSize));
			resultCount += updateCostMapper.updateReturnExchangeDtl(params);
		}
		
		return resultCount;
	}

	@Override
	public int updateBillBuyBalance(Map<String, Object> params) {
		
		int totalCount = updateCostMapper.selectBillBuyBalanceCount(params);
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		int resultCount = 0;
		for(int i = 0; i < totalPage; i++) {
			long startRowNum = i * pageSize;
			params.put("startRowNum", Long.valueOf(startRowNum));
			params.put("selectCount", Integer.valueOf(pageSize));
			resultCount += updateCostMapper.updateBillBuyBalance(params);
		}
		
		return resultCount;
	}

	@Override
	public int updateBillSaleBalance(Map<String, Object> params) {
		
		int totalCount = updateCostMapper.selectBillSaleBalanceCount(params);
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		int resultCount = 0;
		for(int i = 0; i < totalPage; i++) {
			long startRowNum = i * pageSize;
			params.put("startRowNum", Long.valueOf(startRowNum));
			params.put("selectCount", Integer.valueOf(pageSize));
			resultCount += updateCostMapper.updateBillSaleBalance(params);
		}
		
		return resultCount;
	}

	@Override
	public int updatePeriodBalance(Map<String, Object> params) {
		
		int totalCount = updateCostMapper.selectPeriodBalanceCount(params);
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		int resultCount = 0;
		for(int i = 0; i < totalPage; i++) {
			long startRowNum = i * pageSize;
			params.put("startRowNum", Long.valueOf(startRowNum));
			params.put("selectCount", Integer.valueOf(pageSize));
			resultCount += updateCostMapper.updatePeriodBalance(params);
		}
		
		return resultCount;
	}

	@Override
	public int updateRegionCost(Map<String, Object> params)
			throws ServiceException {
		try {
			//更新POS订单表地区成本价
			updateCostMapper.updatePosOrderRegionCost(params);
			//更新POS退换货表地区成本价
			updateCostMapper.updatePosReturnExchangeRegionCost(params);
			//更新BILL_BUY_BALANCE表地区成本价
			updateCostMapper.updateBuyBalanceRegionCost(params);
			//更新BILL_SALE_BALANCE表地区成本价
			updateCostMapper.updateSaleBalanceRegionCost(params);
			return 1;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int updateHeadquaterCost(Map<String, Object> params)
			throws ServiceException {
		try {
			//更新POS订单表总部成本价
			updateCostMapper.updatePosOrderHeadquaterCost(params);
			//更新POS退换货表总部成本价
			updateCostMapper.updatePosReturnExchangeHeadquaterCost(params);
			//更新BILL_BUY_BALANCE表总部成本价
			updateCostMapper.updateBuyBalanceHeadquaterCost(params);
			//更新BILL_SALE_BALANCE表总部成本价
			updateCostMapper.updateSaleBalanceHeadquaterCost(params);
			return 1;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int updateItemCost(Map<String, Object> params)
			throws ServiceException {
		try {
			//更新POS订单表单位成本价
			int POCount = updateCostMapper.updatePosOrderItemCost(params);
			log.info("共更新了 ORDER_DTL 成本记录" + POCount);
			//更新POS退换货表单位成本价
			int PRCount = updateCostMapper.updatePosReturnExchangeItemCost(params);
			log.info("共更新了 RETURN_EXCHANGE_DTL 成本记录" + PRCount);
			//更新BILL_BUY_BALANCE表单位成本价
			int BBCount = updateCostMapper.updateBuyBalanceItemCost(params);
			log.info("共更新了 BILL_BUY_BALANCE 成本记录" + BBCount);
			//更新BILL_SALE_BALANCE表单位成本价
			int SBCount = updateCostMapper.updateSaleBalanceItemCost(params);
			log.info("共更新了 BILL_SALE_BALANCE 成本记录" + SBCount);
			return 1;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	
}