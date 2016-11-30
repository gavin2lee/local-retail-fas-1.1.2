package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.dto.BAReceiptDto;
import cn.wonhigh.retail.fas.common.dto.BillBuyBalanceAdditionDto;
import cn.wonhigh.retail.fas.common.dto.RegionCostCaculatorDto;
import cn.wonhigh.retail.fas.common.dto.TaxRateDto;
import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;
import cn.wonhigh.retail.fas.common.strategy.BaroqueRegionCostStrategy;
import cn.wonhigh.retail.fas.common.strategy.RegionCostCaculateStrategy;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.domain.TagPriceArgument;
import cn.wonhigh.retail.fas.service.BAReceiptService;
import cn.wonhigh.retail.fas.service.BillBuyBalanceAdditionalService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;


@Service("baroquePurchaseRegionCostManager")
class BaroquePurchaseRegionCostManagerImpl extends BaseCrudManagerImpl implements BaroquePurchaseRegionCostManager {
	@Resource
	private BillBuyBalanceAdditionalService billBuyBalanceAdditionalService;
	
	@Resource
	private BAReceiptService baReceiptService;

	private String defaultStandardCode = "001";// TODO

	private String defaultStandardName = "人民币";// TODO

	private RegionCostCaculateStrategy regionCostCaculateStrategy;

	@Resource
	private FinancialAccountManager financialAccountManager;
	
	@Resource
	private CompanySettlePeriodManager companySettlePeriodManager;

	@Override
	public BaseCrudService init() {
		return billBuyBalanceAdditionalService;
	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	private String getCacheKey(String... args) {
		String cacheKey = "";
		if (null != args) {
			for (String key : args) {
				cacheKey += key;
				cacheKey += "_";
			}
			cacheKey = cacheKey.substring(0, cacheKey.length() - 1);
		}
		return cacheKey;
	}

	/**
	 * 
	 * @param supplierNo
	 * @param itemNo
	 * @param receiveDate
	 * @param cache
	 * @return
	 */
	private TaxRateDto getTaxRate(String supplierNo, String itemNo, Date receiveDate, Map<String, TaxRateDto> cache)
			throws ServiceException {
		TaxRateDto result = null;
		if (null == cache) {
			cache = new HashMap<String, TaxRateDto>();
		}
		String cacheKey = this.getCacheKey(supplierNo, itemNo, receiveDate.toString());
		if (cache.containsKey(cacheKey)) {
			result = cache.get(cacheKey);
		} else {
			result = this.billBuyBalanceAdditionalService.getTaxRate(supplierNo, itemNo, receiveDate);
			if (null == result) {
				result = new TaxRateDto();
				result.setCurrencyCode(defaultStandardCode);
				result.setCurrencyName(defaultStandardName);
				result.setTariffRate(BigDecimal.ZERO);
				result.setVatRate(BigDecimal.ZERO);
				result.setStandardCurrencyCode(defaultStandardCode);
				result.setStandardCurrencyName(defaultStandardName);
			}
			cache.put(cacheKey, result);
		}
		return result;
	}

	/**
	 * 
	 * @param currencyCode
	 * @param receiveDate
	 * @param cache
	 * @return
	 * @throws ServiceException
	 */
	public BigDecimal getExchange(String currencyCode, Date receiveDate, Map<String, BigDecimal> cache)
			throws ServiceException {
		BigDecimal exchange = BigDecimal.ONE;
		if (null == cache) {
			cache = new HashMap<String, BigDecimal>();
		}
		String cacheKey = this.getCacheKey(currencyCode, receiveDate.toString());
		if (cache.containsKey(cacheKey)) {
			exchange = cache.get(cacheKey);
		} else {
			exchange = this.billBuyBalanceAdditionalService.getExchangeRage(currencyCode, receiveDate);
			if (null == exchange) {
				exchange = BigDecimal.ONE;
			}
			cache.put(cacheKey, exchange);
		}
		return exchange;
	}

	/**
	 * 
	 * @param brandNo
	 * @param categoryNo
	 * @param buyerNo
	 * @param salerNo
	 * @param receiveDate
	 * @param discountContractCache
	 * @return
	 * @throws ServiceException
	 */
	private BigDecimal getContractDiscount(String brandNo, String categoryNo, String buyerNo, String salerNo,
			Date receiveDate, Map<String, BigDecimal> discountContractCache) throws ServiceException {
		BigDecimal discountContract = new BigDecimal(1.3);
		if (null == discountContractCache) {
			discountContractCache = new HashMap<String, BigDecimal>();
		}
		String cacheKey = this.getCacheKey(brandNo, categoryNo, buyerNo, salerNo);
		if (discountContractCache.containsKey(cacheKey)) {
			discountContract = discountContractCache.get(cacheKey);
		} else {
			Map<String, Object> discountParams = new HashMap<String, Object>();
			discountParams.put("brandNo", brandNo);
			discountParams.put("categoryNo", categoryNo);
			discountParams.put("buyerNo", buyerNo);
			discountParams.put("salerNo", salerNo);
			discountParams.put("effectiveDate", receiveDate);
			discountContract = this.billBuyBalanceAdditionalService.getContractDiscount(discountParams);
			if (discountContract == null) {
				discountContract = new BigDecimal(1.3);
			}
			discountContractCache.put(cacheKey, discountContract);
		}
		return discountContract;
	}

	/**
	 * 
	 * @param itemNo
	 * @param supplierNo
	 * @param receiveDate
	 * @param purchasePriceCache
	 * @return
	 * @throws ServiceException
	 */
	private BigDecimal getPurchasePrice(String itemNo, String supplierNo, Date receiveDate,
			Map<String, BigDecimal> purchasePriceCache) throws ServiceException {
		BigDecimal purchasePrice = BigDecimal.ZERO;
		if (null == purchasePriceCache) {
			purchasePriceCache = new HashMap<String, BigDecimal>();
		}
		String cacheKey = this.getCacheKey(itemNo, supplierNo, receiveDate.toString());
		if (purchasePriceCache.containsKey(cacheKey)) {
			purchasePrice = purchasePriceCache.get(cacheKey);
		} else {
			Map<String, Object> purchasePriceParams = new HashMap<String, Object>();
			purchasePriceParams.put("itemNo", itemNo);
			purchasePriceParams.put("supplierNo", supplierNo);
			purchasePriceParams.put("effectiveDate", receiveDate);
			purchasePrice = this.billBuyBalanceAdditionalService.getPurchasePrice(purchasePriceParams);
			if (purchasePrice == null) {
				purchasePrice = BigDecimal.ZERO;
			}
			purchasePriceCache.put(cacheKey, purchasePrice);
		}
		return purchasePrice;
	}

	@Override
	public boolean updateRegionCost(String originalBillNo, String itemNo) throws ManagerException {
		boolean result = false;
		try {
			String companyNos = financialAccountManager.findLeadRoleCompanyNos();
			BillBuyBalanceAdditionDto dto = this.billBuyBalanceAdditionalService.getBillBuyBalanceAdditionDto(
					originalBillNo, itemNo, companyNos);
			if (dto != null) {
				Map<String, TaxRateDto> taxRateDtoCache = new HashMap<String, TaxRateDto>();
				TaxRateDto taxRateDto = this.getTaxRate(dto.getSupplierNo(), dto.getItemNo(), dto.getReceiveDate(),
						taxRateDtoCache);
				dto.setCurrencyName(taxRateDto.getCurrencyName());
				dto.setCurrencyCode(taxRateDto.getCurrencyCode());
				dto.setTariffRate(taxRateDto.getTariffRate());
				dto.setVatRate(taxRateDto.getVatRate());
				dto.setStandardCurrencyCode(taxRateDto.getStandardCurrencyCode());
				dto.setStandardCurrencyName(taxRateDto.getStandardCurrencyName());

				BigDecimal exchange = this.billBuyBalanceAdditionalService.getExchangeRage(dto.getCurrencyCode(),
						dto.getReceiveDate());
				if (exchange == null) {
					exchange = BigDecimal.ONE;
				}
				dto.setExchangeRate(exchange);

				Map<String, Object> purchasePriceParams = new HashMap<String, Object>();
				purchasePriceParams.put("itemNo", dto.getItemNo());
				purchasePriceParams.put("supplierNo", dto.getSupplierNo());
				purchasePriceParams.put("effectiveDate", dto.getReceiveDate());

				BigDecimal purchasePrice = this.billBuyBalanceAdditionalService.getPurchasePrice(purchasePriceParams);
				if (purchasePrice == null) {
					purchasePrice = BigDecimal.ZERO;
				}
				dto.setPurchasePrice(purchasePrice);

				Map<String, Object> discountParams = new HashMap<String, Object>();
				discountParams.put("brandNo", dto.getBrandNo());
				discountParams.put("categoryNo", dto.getCategoryNo());
				discountParams.put("buyerNo", dto.getBuyerNo());
				discountParams.put("salerNo", dto.getSalerNo());
				discountParams.put("effectiveDate", dto.getReceiveDate());

				BigDecimal contractDiscount = this.billBuyBalanceAdditionalService.getContractDiscount(discountParams);
				if (contractDiscount == null) {
					contractDiscount = new BigDecimal(1.3);
				}

				dto.setRegionPriceGap(contractDiscount);
				dto.setItemCode(dto.getItemNo());
				dto.setUpdateTime(new Date());

				RegionCostCaculatorDto regionCostCaculatorDto = new RegionCostCaculatorDto();
				regionCostCaculatorDto.setPurchasePrice(dto.getPurchasePrice());
				regionCostCaculatorDto.setExchangeRate(dto.getExchangeRate());
				regionCostCaculatorDto.setTariffRate(dto.getTariffRate());
				regionCostCaculatorDto.setVatRate(dto.getVatRate());
				regionCostCaculatorDto.setContractDiscount(dto.getRegionPriceGap());
				regionCostCaculatorDto.setQty(dto.getReceiveQty());
				regionCostCaculatorDto.setOriginalBillNo(originalBillNo);
				regionCostCaculatorDto.setItemNo(itemNo);
				regionCostCaculatorDto.setId(dto.getId());
				regionCostCaculatorDto.setCurrencyCode(dto.getCurrencyCode());
				regionCostCaculatorDto.setCurrencyName(dto.getCurrencyName());
				regionCostCaculatorDto.setStandardCurrencyCode(dto.getStandardCurrencyCode());
				regionCostCaculatorDto.setStandardCurrencyName(dto.getStandardCurrencyName());
				regionCostCaculatorDto.setSalerNo(dto.getSalerNo());
				regionCostCaculatorDto.setBuyerNo(dto.getBuyerNo());
				this.regionCostCaculateStrategy = new BaroqueRegionCostStrategy();
				regionCostCaculatorDto = this.regionCostCaculateStrategy.process(regionCostCaculatorDto);

				dto.setTotalAmount(regionCostCaculatorDto.getStandardTotalAmount());// 人民币本位币总金额
				dto.setRegionCost(regionCostCaculatorDto.getRegionCost());
				dto.setRegionAmount(regionCostCaculatorDto.getRegionAmount());
				this.billBuyBalanceAdditionalService.updateRegionCost(regionCostCaculatorDto);
			}
			result = true;
		} catch (Exception e) {
			throw new ManagerException(e);
		}
		return result;
	}


	private boolean checkCompanySettleDate(String companyNo,Map<String, List<CompanySettlePeriod>> cache)
			throws ManagerException {
		boolean  result  = true;
		if(cache==null){
			cache = new HashMap<String,List<CompanySettlePeriod>>();
		}
		List<CompanySettlePeriod> settlePeriods = cache.get(companyNo);
		if(null == settlePeriods)
		{
			Map<String, Object> companySettleParams = new HashMap<String, Object>();
			companySettleParams.put("companyNo", companyNo);
			settlePeriods = companySettlePeriodManager.findByBiz(null, companySettleParams);
			cache.put(companyNo, settlePeriods);
		}
		if (!CollectionUtils.isEmpty(settlePeriods)) {
			// 判断财务关账日
			int year = Calendar.getInstance().get(Calendar.YEAR);;
			int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
			if (settlePeriods.get(0).getAccountSettleTime().after(DateUtil.getFirstDayOfMonth(year, month))) {
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}
	
	@Override
	public Map<String, Integer> updateRegionCost(String ids) throws ManagerException {
		Map<String, Integer> result = new HashMap<String,Integer>();
		try {
			String[] pair = ids.split(";");
			if (pair != null && pair.length > 0) {
				
				String companyNos = financialAccountManager.findLeadRoleCompanyNos();
				Map<String, TaxRateDto> taxRateDtoCache = new HashMap<String, TaxRateDto>();
				Map<String, BigDecimal> exchangeCache = new HashMap<String, BigDecimal>();
				Map<String, BigDecimal> purchasePriceCache = new HashMap<String, BigDecimal>();
				Map<String, BigDecimal> discountContractCache = new HashMap<String, BigDecimal>();
				Map<String, List<CompanySettlePeriod>> companySettleCache = new HashMap<String,List<CompanySettlePeriod>>();
				List<BillBuyBalanceAdditionDto> params = new ArrayList<BillBuyBalanceAdditionDto>();
				List<RegionCostCaculatorDto> regionDtoList = new ArrayList<RegionCostCaculatorDto>();
				
				for (String item : pair) {
					String[] keyValue = item.split("@");
					String originalBillNo = keyValue[0];
					String itemNo = keyValue[1];
					BillBuyBalanceAdditionDto tempDto = new BillBuyBalanceAdditionDto();
					tempDto.setOriginalBillNo(originalBillNo);
					tempDto.setItemNo(itemNo);
					params.add(tempDto);
				}
				Integer total = 0;
				Integer pass = 0;
				if (params.size() > 0) {
					List<BillBuyBalanceAdditionDto> dtoList = this.billBuyBalanceAdditionalService
							.getBillBuyBalanceAdditionDtoList(companyNos, params);
					total = dtoList.size();
					for (BillBuyBalanceAdditionDto dto : dtoList) {
						if(!this.checkCompanySettleDate(dto.getBuyerNo(),companySettleCache)){
							pass++;
							continue;
						}
						BigDecimal purchasePrice = this.getPurchasePrice(dto.getItemNo(), dto.getSalerNo(),
								dto.getReceiveDate(), purchasePriceCache);
						dto.setPurchasePrice(purchasePrice);// 不能使用supplier_no字段，需要使用salerNo
						
						if(dto.getAdlStatus()==0)//手工修改不获取新的值
						{
							TaxRateDto taxRateDto = this.getTaxRate(dto.getSalerNo(), dto.getItemNo(),
									dto.getReceiveDate(), taxRateDtoCache);// 不能使用supplier_no字段，需要使用salerNo
							dto.setCurrencyName(taxRateDto.getCurrencyName());
							dto.setCurrencyCode(taxRateDto.getCurrencyCode());
							dto.setTariffRate(taxRateDto.getTariffRate());
							dto.setVatRate(taxRateDto.getVatRate());
							dto.setStandardCurrencyCode(taxRateDto.getStandardCurrencyCode());
							dto.setStandardCurrencyName(taxRateDto.getStandardCurrencyName());
	
							BigDecimal exchange = this.getExchange(dto.getCurrencyCode(), dto.getReceiveDate(),
									exchangeCache);
							dto.setExchangeRate(exchange);
	
							BigDecimal contractDiscount = this.getContractDiscount(dto.getBrandNo(), dto.getCategoryNo(),
									dto.getBuyerNo(), dto.getSalerNo(), dto.getReceiveDate(), discountContractCache);
	
							dto.setRegionPriceGap(contractDiscount);
							dto.setItemCode(dto.getItemNo());
							dto.setUpdateTime(new Date());
						}
						RegionCostCaculatorDto regionCostCaculatorDto = new RegionCostCaculatorDto();
						regionCostCaculatorDto.setPurchasePrice(dto.getPurchasePrice());
						regionCostCaculatorDto.setExchangeRate(dto.getExchangeRate());
						regionCostCaculatorDto.setTariffRate(dto.getTariffRate());
						regionCostCaculatorDto.setVatRate(dto.getVatRate());
						regionCostCaculatorDto.setContractDiscount(dto.getRegionPriceGap());
						regionCostCaculatorDto.setQty(dto.getReceiveQty());
						regionCostCaculatorDto.setOriginalBillNo(dto.getOriginalBillNo());
						regionCostCaculatorDto.setItemNo(dto.getItemNo());
						regionCostCaculatorDto.setId(dto.getId());
						regionCostCaculatorDto.setCurrencyCode(dto.getCurrencyCode());
						regionCostCaculatorDto.setCurrencyName(dto.getCurrencyName());
						regionCostCaculatorDto.setStandardCurrencyCode(dto.getStandardCurrencyCode());
						regionCostCaculatorDto.setStandardCurrencyName(dto.getStandardCurrencyName());
						regionCostCaculatorDto.setSalerNo(dto.getSalerNo());
						regionCostCaculatorDto.setBuyerNo(dto.getBuyerNo());
						// TODO replay
						this.regionCostCaculateStrategy = new BaroqueRegionCostStrategy();
						regionCostCaculatorDto = this.regionCostCaculateStrategy.process(regionCostCaculatorDto);
						regionDtoList.add(regionCostCaculatorDto);
						// this.billBuyBalanceAdditionalService.updateRegionCost(regionCostCaculatorDto);
					}
					this.billBuyBalanceAdditionalService.updateRegionCost(regionDtoList);
					result.put("total", total);
					result.put("pass", pass);
				}
			}
			return result;
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public boolean updateTagPrice(TagPriceArgument args) throws ManagerException {
		try{
			Map<String, Object> params = args.toMap();
			List<BAReceiptDto> updateList = this.baReceiptService.getReceiptForUpdate(params);
		
			if(null != updateList){
				for(BAReceiptDto item : updateList){
					Map<String, Object> updateParams = new HashMap<String,Object>();
					updateParams.put("id", item.getId());
					updateParams.put("tagPrice", item.getTagPrice());
					this.baReceiptService.updateBuyBalanceTagPrice(updateParams);
				}
			}
			return true;
		}catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public boolean updateBuyAdl(BigDecimal exchange, BigDecimal tariffRate, BigDecimal vatRate,String id) throws ManagerException {
		try {
			return this.billBuyBalanceAdditionalService.updateBuyAdl(exchange, tariffRate, vatRate,id);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
}
