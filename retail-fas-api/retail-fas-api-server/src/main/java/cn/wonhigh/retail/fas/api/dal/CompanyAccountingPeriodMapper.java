package cn.wonhigh.retail.fas.api.dal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.CompanyBrandSettlePeriod;
import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;

public interface CompanyAccountingPeriodMapper {
	
	List<CompanySettlePeriod> findBalanceDate(@Param("params") Map<String,Object> params);

	List<CompanyBrandSettlePeriod> getCompanyBrandBalanceDate(@Param("params") Map<String, Object> params);
	
}
