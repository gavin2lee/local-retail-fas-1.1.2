package cn.wonhigh.retail.fas.api.strategy;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.wonhigh.retail.fas.api.service.RegionCostCaculatorService;
import cn.wonhigh.retail.fas.common.dto.RegionCostCaculatorDto;
import cn.wonhigh.retail.fas.common.dto.TaxRateDto;
import cn.wonhigh.retail.fas.common.strategy.BaroqueRegionCostStrategy;
import cn.wonhigh.retail.fas.common.strategy.RegionCostCaculateStrategy;

import com.yougou.logistics.base.common.exception.ServiceException;

/**
 * 巴洛克地区价计算实现
 * 
 * @author user
 * 
 */
@Component("baroqueRegionCostAccountingStrategy")
public class BaroqueRegionCostAccountingStrategy implements RegionCostAccountingStrategy {

	@Resource
	private RegionCostCaculatorService regionCostCaculatorService;

	private RegionCostCaculateStrategy regionCostCaculateStrategy;

	@Override
	public BigDecimal caculateRegionCost(String brandNo, String categoryNo, String buyerNo, String salerNo,
			String supplierNo, String itemNo, Integer qty, Date receiveDate) throws ServiceException {
		BigDecimal result = BigDecimal.ZERO;
		try {
			BigDecimal tariffRate = BigDecimal.ZERO;
			BigDecimal vat = BigDecimal.ZERO;
			String currencyCode = "";
			BigDecimal exchange = BigDecimal.ZERO;

			TaxRateDto taxRateDto = this.regionCostCaculatorService.getTaxRate(supplierNo, itemNo, receiveDate);
			if (taxRateDto != null) {
				tariffRate = taxRateDto.getTariffRate();
				vat = taxRateDto.getVatRate();
				currencyCode = taxRateDto.getCurrencyCode();
			} else {
				return result;
			}// 没有维护税率，无需继续计算
			if (StringUtils.hasText(currencyCode)) {
				exchange = this.regionCostCaculatorService.getExchangeRage(currencyCode, receiveDate);
				if (exchange == null) {
					exchange = BigDecimal.ONE;
				}
			} else {
				return result;
			}// 没有维护汇率，无需继续计算

			Map<String, Object> purchasePriceParams = new HashMap<String, Object>();
			purchasePriceParams.put("supplierNo", supplierNo);
			purchasePriceParams.put("itemNo", itemNo);
			purchasePriceParams.put("effectiveDate", receiveDate);
			BigDecimal purchasePrice = this.regionCostCaculatorService.getPurchasePrice(purchasePriceParams);// TODO
			if (purchasePrice == null) {
				return result;
			}// 没有维护价格，无需继续计算

			Map<String, Object> discountParams = new HashMap<String, Object>();
			discountParams.put("brandNo", brandNo);
			discountParams.put("categoryNo", categoryNo);
			discountParams.put("buyerNo", buyerNo);
			discountParams.put("salerNo", salerNo);
			discountParams.put("effectiveDate", receiveDate);
			BigDecimal discount = this.regionCostCaculatorService.getContractDiscount(discountParams);
			if (discount == null) {
				discount = new BigDecimal(1.3);// 默认为1.3
			}
			RegionCostCaculatorDto dto = this.getRegionCostCaculatorDto(purchasePrice, exchange, tariffRate, vat,
					discount, qty);
			if (dto != null) {
				result = dto.getRegionCost();
			}
			return result;

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public RegionCostCaculatorDto getRegionCostCaculatorDto(BigDecimal purchasePrice, BigDecimal exchange,
			BigDecimal tariff, BigDecimal vat, BigDecimal discount, Integer qty) throws ServiceException {
		try {
			if (purchasePrice != null && exchange != null && tariff != null && vat != null && discount != null) {
				RegionCostCaculatorDto dto = new RegionCostCaculatorDto();
				dto.setPurchasePrice(purchasePrice);
				dto.setExchangeRate(exchange);
				dto.setTariffRate(tariff);
				dto.setVatRate(vat);
				dto.setContractDiscount(discount);
				dto.setQty(qty);
				this.regionCostCaculateStrategy = new BaroqueRegionCostStrategy();// TODO
				return this.regionCostCaculateStrategy.process(dto);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return null;
	}
}
