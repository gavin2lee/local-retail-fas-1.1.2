package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.BillBuyBalanceAdditionDto;
import cn.wonhigh.retail.fas.common.dto.RegionCostCaculatorDto;
import cn.wonhigh.retail.fas.common.dto.TaxRateDto;
import cn.wonhigh.retail.fas.dal.database.BillBuyBalanceAdditionalMapper;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途
 * 
 * @author user
 * @date 2016-06-06 10:02:44
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("billBuyBalanceAdditionalService")
class BillBuyBalanceAdditionalServiceImpl extends BaseCrudServiceImpl implements BillBuyBalanceAdditionalService {

	@Resource
	private BillBuyBalanceAdditionalMapper billBuyBalanceAdditionalMapper;

	@Override
	public BaseCrudMapper init() {
		return billBuyBalanceAdditionalMapper;
	}

	@Override
	public List<BillBuyBalanceAdditionDto> selectByParams(Map<String, Object> params) throws ServiceException {
		return this.billBuyBalanceAdditionalMapper.selectByParams(params);
	}

	@Override
	public List<BillBuyBalanceAdditionDto> findBillBuyBalanceAdditionalByPage(SimplePage arg0, String arg1,
			String arg2, Map<String, Object> arg3) throws ServiceException {
		return this.billBuyBalanceAdditionalMapper.findBillBuyBalanceAdditionalByPage(arg0, arg1, arg2, arg3);
	}

	@Override
	public void updatePurchaseFee(Map<String, Object> params) throws ServiceException {
		this.billBuyBalanceAdditionalMapper.updatePurchaseFee(params);
	}

	@Override
	public Integer getCount(Map<String, Object> params) throws ServiceException {
		return this.billBuyBalanceAdditionalMapper.getCount(params);
	}

	@Override
	public List<BillBuyBalanceAdditionDto> findBaroqueRegionCostBill(SimplePage arg0, String arg1, String arg2,
			Map<String, Object> arg3) throws ServiceException {
		return this.billBuyBalanceAdditionalMapper.findBaroqueRegionCostBill(arg0, arg1, arg2, arg3);
	}

	@Override
	public Integer findBaroqueRegionCostBillCount(Map<String, Object> params) throws ServiceException {
		return this.billBuyBalanceAdditionalMapper.findBaroqueRegionCostBillCount(params);
	}

	@Override
	public List<BillBuyBalanceAdditionDto> findBaroqueDistributeCostBill(SimplePage arg0, String arg1, String arg2,
			Map<String, Object> arg3) throws ServiceException {
		return this.billBuyBalanceAdditionalMapper.findGroupBaroqueDistributeCostBill(arg0, arg1, arg2, arg3);
	}

	@Override
	public List<BillBuyBalanceAdditionDto> findGroupBaroqueRegionCostBill(SimplePage arg0, String arg1, String arg2,
			Map<String, Object> arg3) throws ServiceException {
		return this.billBuyBalanceAdditionalMapper.findGroupBaroqueRegionCostBill(arg0, arg1, arg2, arg3);
	}

	@Override
	public Integer findGroupBaroqueDistributeCostBillCount(Map<String, Object> params) throws ServiceException {
		return this.billBuyBalanceAdditionalMapper.findGroupBaroqueDistributeCostBillCount(params);
	}

	@Override
	public Integer findGroupBaroqueRegionCostBillCount(Map<String, Object> params) throws ServiceException {
		return this.billBuyBalanceAdditionalMapper.findGroupBaroqueRegionCostBillCount(params);
	}

	@Override
	public BillBuyBalanceAdditionDto getBillBuyBalanceAdditionDto(String originalId, String itemNo, String companyNos) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("originalBillNo", originalId);
		params.put("itemNo", itemNo);
		params.put("HQCompanyNo", companyNos);
		return this.billBuyBalanceAdditionalMapper.getBillBuyBalanceAdditionDto(params);
	}

	@Override
	public BigDecimal getExchangeRage(String currencyCode, Date receiveDate) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("currencyCode", currencyCode);
		params.put("effectiveDate", receiveDate);
		return this.billBuyBalanceAdditionalMapper.getExchangeRate(params);
	}

	@Override
	public BigDecimal getContractDiscount(Map<String, Object> params) throws ServiceException {
		return this.billBuyBalanceAdditionalMapper.getContractDiscount(params);
	}

	@Override
	public TaxRateDto getTaxRate(String supplierNo, String itemNo, Date effectiveDate) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierNo", supplierNo);
		TaxRateDto vatDto = this.billBuyBalanceAdditionalMapper.getTaxRate(params);
		if (vatDto != null) {
			params.put("itemNo", itemNo);
			params.put("effectiveDate", effectiveDate);
			TaxRateDto tariffDto = this.billBuyBalanceAdditionalMapper.getTariffRate(params);
			if (tariffDto != null) {
				vatDto.setItemNo(tariffDto.getItemNo());
				vatDto.setStyleNo(tariffDto.getStyleNo());
				vatDto.setTariffRate(tariffDto.getTariffRate());
			} else {
				vatDto.setTariffRate(BigDecimal.ZERO);
			}
		}
		return vatDto;
	}

	@Override
	public BigDecimal getPurchasePrice(Map<String, Object> params) throws ServiceException {
		return this.billBuyBalanceAdditionalMapper.getPurchasePrice(params);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void updateRegionCost(RegionCostCaculatorDto dto) throws ServiceException {
		this.billBuyBalanceAdditionalMapper.updateRegionCost(dto);// 更新bill_buy_balance_additional
		this.billBuyBalanceAdditionalMapper.updatePurchasePrice(dto); // 更新buy表cost字段为采购价
		this.billBuyBalanceAdditionalMapper.updateBuyTableRegionCost(dto);// 更新buy表cost字段为地区价
		this.billBuyBalanceAdditionalMapper.updateSaleCost(dto);// 更新sale表cost字段为地区价
		// TODO update region cost in buy, and update region cost in sale;
	}
	/**
	 * 批量更新，但mycat有限制，只能更新第一条
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void updateRegionCost(List<RegionCostCaculatorDto> list) throws ServiceException {
//		this.billBuyBalanceAdditionalMapper.batchUpdateRegionCost(list);// 更新bill_buy_balance_additional
//		this.billBuyBalanceAdditionalMapper.batchUpdatePurchasePrice(list); // 更新 buy表cost字段为采购价
//		this.billBuyBalanceAdditionalMapper.batchUpdateBuyTableRegionCost(list);// 更新buy表cost字段为地区价
//		this.billBuyBalanceAdditionalMapper.batchUpdateSaleCost(list);// 更新sale表cost字段为地区价
		if(null!=list){
			for(RegionCostCaculatorDto dto : list){
				this.updateRegionCost(dto);
			}
		}
	}

	@Override
	public List<BillBuyBalanceAdditionDto> getBillBuyBalanceAdditionDtoList(String companyNos,List<BillBuyBalanceAdditionDto> dto) throws ServiceException {
		return this.billBuyBalanceAdditionalMapper.getBillBuyBalanceAdditionDtoList(companyNos, dto);
	}

	@Override
	public boolean updateBuyAdl(BigDecimal exchange, BigDecimal tariffRate, BigDecimal vatRate, String id) throws ServiceException {
		 this.billBuyBalanceAdditionalMapper.updateBuyAdl( exchange, tariffRate,vatRate,id);
		 return true;
	}
}