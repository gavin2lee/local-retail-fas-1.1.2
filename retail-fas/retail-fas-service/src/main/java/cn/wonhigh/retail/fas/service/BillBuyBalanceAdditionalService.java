package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BillBuyBalanceAdditionDto;
import cn.wonhigh.retail.fas.common.dto.RegionCostCaculatorDto;
import cn.wonhigh.retail.fas.common.dto.TaxRateDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author user
 * @date 2016-06-06 10:02:44
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public interface BillBuyBalanceAdditionalService extends BaseCrudService {

	List<BillBuyBalanceAdditionDto> selectByParams(Map<String, Object> params) throws ServiceException;

	Integer getCount(Map<String, Object> params) throws ServiceException;

	List<BillBuyBalanceAdditionDto> findBillBuyBalanceAdditionalByPage(
			com.yougou.logistics.base.common.utils.SimplePage arg0, java.lang.String arg1, java.lang.String arg2,
			java.util.Map<String, Object> arg3) throws ServiceException;

	List<BillBuyBalanceAdditionDto> findBaroqueRegionCostBill(com.yougou.logistics.base.common.utils.SimplePage arg0,
			java.lang.String arg1, java.lang.String arg2, java.util.Map<String, Object> arg3) throws ServiceException;

	List<BillBuyBalanceAdditionDto> findGroupBaroqueRegionCostBill(
			com.yougou.logistics.base.common.utils.SimplePage arg0, java.lang.String arg1, java.lang.String arg2,
			java.util.Map<String, Object> arg3) throws ServiceException;

	List<BillBuyBalanceAdditionDto> findBaroqueDistributeCostBill(
			com.yougou.logistics.base.common.utils.SimplePage arg0, java.lang.String arg1, java.lang.String arg2,
			java.util.Map<String, Object> arg3) throws ServiceException;

	void updatePurchaseFee(Map<String, Object> params) throws ServiceException;

	Integer findGroupBaroqueDistributeCostBillCount(Map<String, Object> params) throws ServiceException;

	Integer findBaroqueRegionCostBillCount(Map<String, Object> params) throws ServiceException;

	Integer findGroupBaroqueRegionCostBillCount(Map<String, Object> params) throws ServiceException;

	BillBuyBalanceAdditionDto getBillBuyBalanceAdditionDto(String originalId, String itemNo, String companyNos)
			throws ServiceException;
	
	List<BillBuyBalanceAdditionDto> getBillBuyBalanceAdditionDtoList(String companyNos,List<BillBuyBalanceAdditionDto> dto) throws ServiceException;

	BigDecimal getExchangeRage(String currencyCode, Date receiveDate) throws ServiceException;

	BigDecimal getContractDiscount(Map<String, Object> params) throws ServiceException;

	TaxRateDto getTaxRate(String supplierNo, String itemNo, Date effectiveDate) throws ServiceException;

	BigDecimal getPurchasePrice(Map<String, Object> params) throws ServiceException;

	void updateRegionCost(RegionCostCaculatorDto dto) throws ServiceException;
	
	void updateRegionCost(List<RegionCostCaculatorDto> list) throws ServiceException;
	
	boolean updateBuyAdl(BigDecimal exchange,BigDecimal tariffRate,BigDecimal vatRate, String id) throws ServiceException;
}