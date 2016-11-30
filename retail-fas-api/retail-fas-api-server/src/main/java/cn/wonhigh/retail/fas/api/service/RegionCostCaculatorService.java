package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.vo.BillBuyBalanceAdditional;
import cn.wonhigh.retail.fas.common.dto.RegionCostCaculatorDto;
import cn.wonhigh.retail.fas.common.dto.TaxRateDto;

import com.yougou.logistics.base.common.exception.ServiceException;

public interface RegionCostCaculatorService {

	BigDecimal getExchangeRage(String currencyCode, Date receiveDate) throws ServiceException;

	BigDecimal getContractDiscount(Map<String, Object> params) throws ServiceException;

	TaxRateDto getTaxRate(String supplierNo, String itemNo, Date effectiveDate) throws ServiceException;

	BigDecimal getPurchasePrice(Map<String, Object> params) throws ServiceException;

	// BillBuyBalanceAdditional getBillBuyBalanceAdditional(Map<String, Object>
	// params) throws ServiceException;

	List<BillBuyBalanceAdditional> getBillBuyBalanceAdditional(List<BillBalanceApiDto> list) throws ServiceException;

	void batchInsert(List<BillBuyBalanceAdditional> list) throws ServiceException;

	List<RegionCostCaculatorDto> selectRegionCostCaculatorDtoList(String originalBillNos) throws ServiceException;

	Boolean isBaroqueBill(String itemCode) throws ServiceException;
}
