/**
 * title:PriceCheckAndUpdateServiceImpl.java
 * package:cn.wonhigh.retail.fas.service
 * description:TODO
 * auther:user
 * date:2016-8-2 下午3:12:55
 */
package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.PriceCheckAndUpdateDto;
import cn.wonhigh.retail.fas.dal.database.PriceCheckAndUpdateMapper;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("priceCheckAndUpdateServiceImpl")
public class PriceCheckAndUpdateServiceImpl extends BaseCrudServiceImpl
		implements PriceCheckAndUpdateService {

	@Resource
	private PriceCheckAndUpdateMapper priceCheckAndUpdateMapper;
	
	@Override
	public BaseCrudMapper init() {
		return priceCheckAndUpdateMapper;
	}

	@Override
	public int findHqSaleBillCostIsZeroCount(Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectHqSaleBillCostIsZeroCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findHqSaleBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectHqSaleBillCostIsZeroByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findHqSaleBillCostIsDiffCount(Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectHqSaleBillCostIsDiffCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findHqSaleBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectHqSaleBillCostIsDiffByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findAreaSaleBillCostIsZeroCount(Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectAreaSaleBillCostIsZeroCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findAreaSaleBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectAreaSaleBillCostIsZeroByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findAreaSaleBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectAreaSaleBillCostIsDiffByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findAreaSaleBillCostIsDiffCount(Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectAreaSaleBillCostIsDiffCount(params);
	}

	@Override
	public int findAreaBuyBillCostIsZeroCount(Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectAreaBuyBillCostIsZeroCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findAreaBuyBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectAreaBuyBillCostIsZeroByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findAreaBuyBillCostIsDiffCount(Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectAreaBuyBillCostIsDiffCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findAreaBuyBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectAreaBuyBillCostIsDiffByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public void modifyBillBuyBalanceCost(PriceCheckAndUpdateDto pcauDto) {
		priceCheckAndUpdateMapper.updateBillBuyBalanceCost(pcauDto);
	}

	@Override
	public void modifyBillSaleBalanceCost(PriceCheckAndUpdateDto pcauDto) {
		priceCheckAndUpdateMapper.updateBillSaleBalanceCost(pcauDto);
	}

	@Override
	public int findHqBuyBillCostIsZeroCount(Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectHqBuyBillCostIsZeroCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findHqBuyBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectHqBuyBillCostIsZeroByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findHqBuyBillCostIsDiffCount(Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectHqBuyBillCostIsDiffCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findHqBuyBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectHqBuyBillCostIsDiffByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findHqSaleBillCostIsNullCount(Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectHqSaleBillCostIsNullCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findHqSaleBillCostIsNullByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectHqSaleBillCostIsNullByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public BigDecimal findAsnBillCost(PriceCheckAndUpdateDto pcauDto) {
		return priceCheckAndUpdateMapper.selectAsnBillCost(pcauDto);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findAreaBuyBillOneByPage(
			Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectAreaBuyBillOneByPage(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findAreaBuyBillTwoByPage(
			Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectAreaBuyBillTwoByPage(params);
	}

	@Override
	public BigDecimal findTransferAndReturnBillCost(
			PriceCheckAndUpdateDto pcauDto) {
		return priceCheckAndUpdateMapper.selectTransferAndReturnBillCost(pcauDto);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findAreaBuyBillThreeByPage(
			Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectAreaBuyBillThreeByPage(params);
	}

	@Override
	public BigDecimal findSaleAsnBillCost(PriceCheckAndUpdateDto pcauDto) {
		return priceCheckAndUpdateMapper.selectSaleAsnBillCost(pcauDto);
	}

	@Override
	public int findAreaBuyDiffOutBillCount(Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectAreaBuyDiffOutBillCount(params);
	}

	@Override
	public List<PriceCheckAndUpdateDto> findAreaBuyDiffOutBillByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) {
		return priceCheckAndUpdateMapper.selectAreaBuyDiffOutBillByPage(page,sortColumn,sortOrder,params);
	}

}
