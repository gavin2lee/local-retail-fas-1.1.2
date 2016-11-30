package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

public interface BLKCompanyPeriodBalanceService extends BaseCrudService {

	public List<CompanyPeriodBalance> selectTheFinalCostByPage(Map<String, Object> params) throws ServiceException;

	public void itemCostFeedbackToPeriodBalance(ItemCostConditionDto dto) throws ServiceException;

	public void writeBackAmount(ItemCostConditionDto dto) throws ServiceException;

	public void writeBackCostAdjustmentAmout(ItemCostConditionDto invCostGenerateDto) throws ServiceException;
	
	public Map<String, BigDecimal> selectCompanyPeriodBalanceGroupByStyleNo(ItemCostConditionDto dto,List<String> itemNos);

	public List<String> findCompanyPeriodBalanceList(ItemCostConditionDto dto) throws ServiceException;

	public void deleteCompanyMonthPeriodBalance(ItemCostConditionDto dto) throws ServiceException;
	
	public void generateCompanyPeriodBalance(ItemCostConditionDto dto) throws ServiceException;

}
