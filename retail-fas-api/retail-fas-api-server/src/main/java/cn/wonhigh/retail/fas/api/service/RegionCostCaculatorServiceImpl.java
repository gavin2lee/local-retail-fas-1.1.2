package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.BillBuyBalanceAdditionalMapper;
import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.manager.BillBuyBalanceApiManagerImpl;
import cn.wonhigh.retail.fas.api.vo.BillBuyBalanceAdditional;
import cn.wonhigh.retail.fas.common.dto.RegionCostCaculatorDto;
import cn.wonhigh.retail.fas.common.dto.TaxRateDto;
import cn.wonhigh.retail.fas.common.strategy.BaroqueRegionCostStrategy;
import cn.wonhigh.retail.fas.common.strategy.RegionCostCaculateStrategy;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;

@Service("regionCostCaculatorServiceImpl")
public class RegionCostCaculatorServiceImpl implements RegionCostCaculatorService {

	private final String defaultStandardCode = "001";
	private final String defaultStandardName = "人民币";
	private Logger logger = Logger.getLogger(BillBuyBalanceApiManagerImpl.class);

	@Resource
	private BillBuyBalanceAdditionalMapper billBuyBalanceAdditionalMapper;

	private RegionCostCaculateStrategy regionCostAccountingStrategy;

	@Override
	public BigDecimal getExchangeRage(String currencyCode, Date receiveDate) throws ServiceException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("currencyCode", currencyCode);
			params.put("effectiveDate", receiveDate);
			return this.billBuyBalanceAdditionalMapper.getExchangeRate(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public BigDecimal getContractDiscount(Map<String, Object> params) throws ServiceException {
		try {
			return this.billBuyBalanceAdditionalMapper.getContractDiscount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public BigDecimal getPurchasePrice(Map<String, Object> params) throws ServiceException {
		try {
			return this.billBuyBalanceAdditionalMapper.getPurchasePrice(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void batchInsert(List<BillBuyBalanceAdditional> list) throws ServiceException {
		try {
			if (list != null) {
				for (BillBuyBalanceAdditional item : list) {
					item.setId(UUIDHexGenerator.generate());
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("originalBillNo", item.getOriginalBillNo());
					params.put("itemNo", item.getItemCode());
					this.billBuyBalanceAdditionalMapper.deleteByOriginalBillNoAndItemNo(params);
					this.billBuyBalanceAdditionalMapper.insert(item);
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	private BillBuyBalanceAdditional defaultBillBuyBalanceAdditional(BillBalanceApiDto dto) {
		BillBuyBalanceAdditional additional = new BillBuyBalanceAdditional();
		additional.setOriginalBillNo(dto.getOriginalBillNo());
		additional.setCurrencyCode(defaultStandardCode);
		additional.setCurrencyName(defaultStandardName);
		additional.setTariffRate(BigDecimal.ZERO);
		additional.setVatRate(BigDecimal.ZERO);
		additional.setStandardCurrencyCode(defaultStandardCode);
		additional.setStandardCurrencyName(defaultStandardName);
		additional.setExchangeRate(BigDecimal.ONE);
		additional.setRegionPriceGap(new BigDecimal(1.3));
		additional.setItemCode(dto.getItemNo());
		additional.setUpdateTime(new Date());
		additional.setTotalAmount(BigDecimal.ZERO);// 人民币本位币总金额
		additional.setRegionCost(BigDecimal.ZERO);
		additional.setRegionAmount(BigDecimal.ZERO);
		return additional;
	}

	private BillBuyBalanceAdditional getBillBuyBalanceAdditional(BillBalanceApiDto dto) throws ServiceException {
		BillBuyBalanceAdditional additional = new BillBuyBalanceAdditional();
		try {
			if (dto != null) {
				additional.setOriginalBillNo(dto.getOriginalBillNo());
				TaxRateDto taxRateDto = this.getTaxRate(dto.getSupplierNo(), dto.getItemNo(), dto.getReceiveDate());
				if (taxRateDto != null) {
					additional.setCurrencyCode(taxRateDto.getCurrencyCode());
					additional.setCurrencyName(taxRateDto.getCurrencyName());
					additional.setTariffRate(taxRateDto.getTariffRate());
					additional.setVatRate(taxRateDto.getVatRate());
					additional.setStandardCurrencyCode(taxRateDto.getStandardCurrencyCode());
					additional.setStandardCurrencyName(taxRateDto.getStandardCurrencyName());
				} else {
					additional.setCurrencyCode(defaultStandardCode);
					additional.setCurrencyName(defaultStandardName);
					additional.setTariffRate(BigDecimal.ZERO);
					additional.setVatRate(BigDecimal.ZERO);
					additional.setStandardCurrencyCode(defaultStandardCode);
					additional.setStandardCurrencyName(defaultStandardName);
				}

				BigDecimal exchange = this.getExchangeRage(additional.getCurrencyCode(), dto.getReceiveDate());
				if (exchange != null) {
					additional.setExchangeRate(exchange);
				} else {
					additional.setExchangeRate(BigDecimal.ONE);
					exchange = BigDecimal.ONE;
				}
				Map<String, Object> purchasePriceParams = new HashMap<String, Object>();
				purchasePriceParams.put("itemNo", dto.getItemNo());
				purchasePriceParams.put("supplierNo", dto.getSupplierNo());
				purchasePriceParams.put("effectiveDate", dto.getReceiveDate());

				BigDecimal purchasePrice = this.getPurchasePrice(purchasePriceParams);
				if (purchasePrice == null) {
					purchasePrice = BigDecimal.ZERO;
				}

				Map<String, Object> discountParams = new HashMap<String, Object>();
				discountParams.put("brandNo", dto.getBrandNo());
				discountParams.put("categoryNo", dto.getCategoryNo());
				discountParams.put("buyerNo", dto.getBuyerNo());
				discountParams.put("salerNo", dto.getSalerNo());
				discountParams.put("effectiveDate", dto.getReceiveDate());

				BigDecimal contractDiscount = this.getContractDiscount(discountParams);
				if (contractDiscount == null) {
					contractDiscount = new BigDecimal(1.3);
				}

				additional.setRegionPriceGap(contractDiscount);
				additional.setItemCode(dto.getItemNo());

				additional.setUpdateTime(new Date());
				this.regionCostAccountingStrategy = new BaroqueRegionCostStrategy();
				RegionCostCaculatorDto regionCostCaculatorDto = new RegionCostCaculatorDto();

				regionCostCaculatorDto.setPurchasePrice(purchasePrice);
				regionCostCaculatorDto.setExchangeRate(exchange);
				regionCostCaculatorDto.setTariffRate(taxRateDto.getTariffRate());
				regionCostCaculatorDto.setVatRate(taxRateDto.getVatRate());
				regionCostCaculatorDto.setContractDiscount(contractDiscount);
				regionCostCaculatorDto.setQty(dto.getReceiveQty());
				regionCostCaculatorDto.setOriginalBillNo(dto.getOriginalBillNo());
				regionCostCaculatorDto.setItemNo(dto.getItemNo());
				regionCostCaculatorDto.setId(dto.getId());
				regionCostCaculatorDto.setCurrencyCode(taxRateDto.getCurrencyCode());
				regionCostCaculatorDto.setCurrencyName(taxRateDto.getCurrencyName());
				regionCostCaculatorDto.setStandardCurrencyCode(taxRateDto.getStandardCurrencyCode());
				regionCostCaculatorDto.setStandardCurrencyName(taxRateDto.getStandardCurrencyName());
				this.regionCostAccountingStrategy = new BaroqueRegionCostStrategy();
				regionCostCaculatorDto = this.regionCostAccountingStrategy.process(regionCostCaculatorDto);

				additional.setTotalAmount(regionCostCaculatorDto.getStandardTotalAmount());// 人民币本位币总金额
				additional.setRegionCost(regionCostCaculatorDto.getRegionCost());
				additional.setRegionAmount(regionCostCaculatorDto.getRegionAmount());

			}
			return additional;
		} catch (Exception e) {
			logger.error("调用getBillBuyBalanceAdditional方法失败:" + e.getMessage(), e);
			additional = this.defaultBillBuyBalanceAdditional(dto);
			return additional;
		}
	}

	@Override
	public List<BillBuyBalanceAdditional> getBillBuyBalanceAdditional(List<BillBalanceApiDto> list)
			throws ServiceException {
		List<BillBuyBalanceAdditional> additionalList = new ArrayList<BillBuyBalanceAdditional>();
		try {
			if (list != null) {
				for (BillBalanceApiDto dto : list) {
					additionalList.add(this.getBillBuyBalanceAdditional(dto));
				}
			}
			return additionalList;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Boolean isBaroqueBill(String itemCode) throws ServiceException {
		try {
			return this.billBuyBalanceAdditionalMapper.isBaroqueBill(itemCode) > 0;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	// private RegionCostCaculatorDto
	// getBillBuyBalanceAdditional(RegionCostCaculatorDto dto) throws
	// ServiceException {
	// try {
	// return this.billBuyBalanceAdditionalMapper.selectByOriginalBillNo(dto);
	// } catch (Exception e) {
	// throw new ServiceException(e);
	// }
	// }

	public void batchUpdate(List<RegionCostCaculatorDto> list) throws ServiceException {
		try {
			if (list != null) {
				for (RegionCostCaculatorDto dto : list) {
					this.billBuyBalanceAdditionalMapper.updateRegionCost(dto);
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<RegionCostCaculatorDto> selectRegionCostCaculatorDtoList(String originalBillNos)
			throws ServiceException {
		try {
			return this.billBuyBalanceAdditionalMapper.selectRegionCostCaculatorDtoList(originalBillNos);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public TaxRateDto getTaxRate(String supplierNo, String itemNo, Date effectiveDate) throws ServiceException {
		try {
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
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
