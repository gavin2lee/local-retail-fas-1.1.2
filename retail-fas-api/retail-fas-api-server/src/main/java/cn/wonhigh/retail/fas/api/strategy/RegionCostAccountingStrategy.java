package cn.wonhigh.retail.fas.api.strategy;

import java.math.BigDecimal;
import java.util.Date;

import cn.wonhigh.retail.fas.common.dto.RegionCostCaculatorDto;

import com.yougou.logistics.base.common.exception.ServiceException;

public interface RegionCostAccountingStrategy {

	BigDecimal caculateRegionCost(String brandNo, String categoryNo, String buyerNo, String salerNo, String supplierNo,
			String itemCode, Integer qty, Date receiveDate) throws ServiceException;

	RegionCostCaculatorDto getRegionCostCaculatorDto(BigDecimal purchasePrice, BigDecimal exchange, BigDecimal tariff,
			BigDecimal vat, BigDecimal discount, Integer qty) throws ServiceException;
}
