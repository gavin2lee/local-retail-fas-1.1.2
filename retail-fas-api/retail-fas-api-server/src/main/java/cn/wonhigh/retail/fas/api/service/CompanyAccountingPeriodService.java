package cn.wonhigh.retail.fas.api.service;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CompanyBrandSettlePeriod;
import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;

import com.yougou.logistics.base.common.exception.ServiceException;

public interface CompanyAccountingPeriodService {
	
	CompanySettlePeriod findBalanceDate(String companyNo) throws ServiceException;

	CompanyBrandSettlePeriod getCompanyBrandBalanceDate(Map<String, Object> params) throws ServiceException;
	
}
