package cn.wonhigh.retail.fas.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.api.dal.CompanyAccountingPeriodMapper;
import cn.wonhigh.retail.fas.common.model.CompanyBrandSettlePeriod;
import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;

import com.yougou.logistics.base.common.exception.ServiceException;

@Service("companyAccountingPeriodService")
public class CompanyAccountingPeriodServiceImpl implements CompanyAccountingPeriodService {
	
	@Resource
	private CompanyAccountingPeriodMapper companyAccountingPeriodMapper;
	
	private Logger log = Logger.getLogger(getClass());

	@Override
	public CompanySettlePeriod findBalanceDate(String companyNo)
			throws ServiceException {
		try{
			CompanySettlePeriod settlePeriod = null;
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("companyNo", companyNo);
			List<CompanySettlePeriod> settlePeriods = companyAccountingPeriodMapper.findBalanceDate(params);
			if (!CollectionUtils.isEmpty(settlePeriods)) {
				settlePeriod = settlePeriods.get(0);
			}
			return settlePeriod;
		}catch(Exception e)
		{
			log.debug("系统错误-->获取公司的结账期间失败", e);
			throw new ServiceException("获取公司的结账期间失败",e);
		}
	}

	@Override
	public CompanyBrandSettlePeriod getCompanyBrandBalanceDate(Map<String, Object> params) throws ServiceException {
		try{
			CompanyBrandSettlePeriod brandSettlePeriod = null;
			List<CompanyBrandSettlePeriod> brandSettlePeriods = companyAccountingPeriodMapper.getCompanyBrandBalanceDate(params);
			if (!CollectionUtils.isEmpty(brandSettlePeriods)) {
				brandSettlePeriod = brandSettlePeriods.get(0);
			}
			return brandSettlePeriod;
		}catch(Exception e)
		{
			log.debug("系统错误-->获取公司的结账期间失败", e);
			throw new ServiceException("获取公司的结账期间失败",e);
		}
	}

}
