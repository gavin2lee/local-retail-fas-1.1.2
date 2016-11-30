package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BillBuyBalanceAdditionDto;
import cn.wonhigh.retail.fas.common.dto.RegionCostCaculatorDto;
import cn.wonhigh.retail.fas.common.dto.TaxRateDto;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
public interface BillBuyBalanceAdditionalMapper extends BaseCrudMapper {

	/**
	 * 获取BillBuyBalanceAdditionDto列表
	 * 
	 * @param params
	 * @return
	 */
	List<BillBuyBalanceAdditionDto> selectByParams(@Param("params") Map<String, Object> params);

	void updateRegionCost(RegionCostCaculatorDto dto);

	/**
	 * 
	 * @param params
	 * @return
	 */
	Integer getCount(@Param("params") Map<String, Object> params);

	List<BillBuyBalanceAdditionDto> findBillBuyBalanceAdditionalByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	List<BillBuyBalanceAdditionDto> findBaroqueRegionCostBill(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	Integer findBaroqueRegionCostBillCount(@Param("params") Map<String, Object> params);

	List<BillBuyBalanceAdditionDto> findGroupBaroqueRegionCostBill(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	List<BillBuyBalanceAdditionDto> findGroupBaroqueDistributeCostBill(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	Integer findGroupBaroqueDistributeCostBillCount(@Param("params") Map<String, Object> params);

	void updatePurchaseFee(@Param("params") Map<String, Object> params);

	Integer findGroupBaroqueRegionCostBillCount(@Param("params") Map<String, Object> params);

	BigDecimal getExchangeRate(@Param("params") Map<String, Object> params);

	BigDecimal getContractDiscount(@Param("params") Map<String, Object> params);

	TaxRateDto getTaxRate(@Param("params") Map<String, Object> params);

	BigDecimal getPurchasePrice(@Param("params") Map<String, Object> params);

	BillBuyBalanceAdditionDto getBillBuyBalanceAdditionDto(@Param("params") Map<String, Object> params);
	
	List<BillBuyBalanceAdditionDto> getBillBuyBalanceAdditionDtoList(@Param("companyNos")String companyNos,@Param("list") List<BillBuyBalanceAdditionDto> list);

	TaxRateDto getTariffRate(@Param("params") Map<String, Object> params);

	void updatePurchasePrice(RegionCostCaculatorDto dto);

	void updateBuyTableRegionCost(RegionCostCaculatorDto dto);

	void updateSaleCost(RegionCostCaculatorDto dto);

	void batchUpdateRegionCost(List<RegionCostCaculatorDto> list);

	void batchUpdatePurchasePrice(List<RegionCostCaculatorDto> list);

	void batchUpdateBuyTableRegionCost(List<RegionCostCaculatorDto> list);

	void batchUpdateSaleCost(List<RegionCostCaculatorDto> list);
	
	void updateBuyAdl(@Param("exchange")BigDecimal exchange, @Param("tariffRate")BigDecimal tariffRate, @Param("vatRate")BigDecimal vatRate, @Param("id")String id);
}