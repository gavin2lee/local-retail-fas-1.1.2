package cn.wonhigh.retail.fas.common.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import cn.wonhigh.retail.fas.common.dto.RegionCostCaculatorDto;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;

@Component("baroqueRegionCostStrategy")
public class BaroqueRegionCostStrategy implements RegionCostCaculateStrategy {

	@Override
	public RegionCostCaculatorDto process(RegionCostCaculatorDto dto) throws Exception {
		// 总部价 = 采购价本位币单价*(1+关税率)（1+增值税率)
		// 总金额= 总部价*数量
		// 地区价 = 采购价本位币单价*（1+增值税率）*1.3+采购价本位币单价*关税率*（1+增值税率）
		// 地区总金额 = 地区价 * 数量
		if (dto != null) {
			BigDecimal vat = BigDecimalUtil.add(BigDecimal.ONE, dto.getVatRate(),false);
			BigDecimal tariff = BigDecimalUtil.add(BigDecimal.ONE, dto.getTariffRate(),false);
			BigDecimal baseCost = BigDecimalUtil.mutiAll(dto.getPurchasePrice(), 
														dto.getExchangeRate(),
														vat,
														tariff);
			BigDecimal regionCost = BigDecimalUtil.add(
					BigDecimalUtil.mutiAll(false,dto.getPurchasePrice(), 
											dto.getExchangeRate(), 
											vat,
											dto.getContractDiscount()), 
											BigDecimalUtil.mutiAll(false,dto.getPurchasePrice(), 
																	dto.getExchangeRate(),
																	dto.getTariffRate(),
																	vat));
			dto.setRegionAmount(BigDecimalUtil.multiInt(regionCost, dto.getQty()));
			dto.setRegionCost(regionCost);
			dto.setStandardBasePrice(baseCost);
			dto.setStandardTotalAmount(BigDecimalUtil.multiInt(baseCost, dto.getQty()));
		}
		return dto;
	}
}
