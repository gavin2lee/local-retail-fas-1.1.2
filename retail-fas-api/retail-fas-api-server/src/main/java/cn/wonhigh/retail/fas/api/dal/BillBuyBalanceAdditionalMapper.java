package cn.wonhigh.retail.fas.api.dal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.RegionCostCaculatorDto;
import cn.wonhigh.retail.fas.common.dto.TaxRateDto;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途
 * 
 * @author user
 * @date 2016-05-31 10:08:12
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
public interface BillBuyBalanceAdditionalMapper extends BaseCrudMapper {

	void update(@Param("params") Map<String, Object> params) throws Exception;

	BigDecimal getExchangeRate(@Param("params") Map<String, Object> params) throws Exception;

	/*
	 * String brandNo, String categoryNo, String buyerNo, String salerNo)
	 */
	BigDecimal getContractDiscount(@Param("params") Map<String, Object> params) throws Exception;

	TaxRateDto getTaxRate(@Param("params") Map<String, Object> params) throws Exception;

	TaxRateDto getTariffRate(@Param("params") Map<String, Object> params) throws Exception;

	BigDecimal getPurchasePrice(@Param("params") Map<String, Object> params) throws Exception;

	Integer isBaroqueBill(String itemCode) throws Exception;

	Map<String, Object> getLatestTaxRate(RegionCostCaculatorDto regionCostCaculatorDtoList) throws Exception;

	BigDecimal getLatestContractDiscount(RegionCostCaculatorDto regionCostCaculatorDtoList) throws Exception;

	BigDecimal getLatestExchangeRate(RegionCostCaculatorDto regionCostCaculatorDtoList) throws Exception;

	RegionCostCaculatorDto selectByOriginalBillNo(RegionCostCaculatorDto dto) throws Exception;

	List<RegionCostCaculatorDto> selectRegionCostCaculatorDtoList(@Param("originalIds") String originalIds)
			throws Exception;

	void updateRegionCost(RegionCostCaculatorDto dto) throws Exception;
	
	void deleteByOriginalBillNoAndItemNo(@Param("params") Map<String, Object> params) throws Exception;
}
