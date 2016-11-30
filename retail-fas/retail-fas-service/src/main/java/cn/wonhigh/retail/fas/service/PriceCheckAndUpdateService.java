/**
 * title:PriceCheckAndUpdateService.java
 * package:cn.wonhigh.retail.fas.service
 * description:TODO
 * auther:user
 * date:2016-8-2 下午3:11:19
 */
package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.PriceCheckAndUpdateDto;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 
 */
public interface PriceCheckAndUpdateService extends BaseCrudService {

	/**
	 * @param params
	 * @return
	 */
	int findHqSaleBillCostIsZeroCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findHqSaleBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findHqSaleBillCostIsDiffCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findHqSaleBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findAreaSaleBillCostIsZeroCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findAreaSaleBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findAreaSaleBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findAreaSaleBillCostIsDiffCount(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findAreaBuyBillCostIsZeroCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findAreaBuyBillCostIsZeroByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findAreaBuyBillCostIsDiffCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findAreaBuyBillCostIsDiffByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * @param pcauDto
	 */
	void modifyBillBuyBalanceCost(PriceCheckAndUpdateDto pcauDto);

	/**
	 * @param pcauDto
	 */
	void modifyBillSaleBalanceCost(PriceCheckAndUpdateDto pcauDto);

	/**
	 * @param params
	 * @return
	 */
	int findHqBuyBillCostIsZeroCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findHqBuyBillCostIsZeroByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findHqBuyBillCostIsDiffCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findHqBuyBillCostIsDiffByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findHqSaleBillCostIsNullCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findHqSaleBillCostIsNullByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params);

	/**
	 * @param pcauDto
	 * @return
	 */
	BigDecimal findAsnBillCost(PriceCheckAndUpdateDto pcauDto);

	/**
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findAreaBuyBillOneByPage(
			Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findAreaBuyBillTwoByPage(
			Map<String, Object> params);

	/**
	 * @param pcauDto
	 * @return
	 */
	BigDecimal findTransferAndReturnBillCost(PriceCheckAndUpdateDto pcauDto);

	/**
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findAreaBuyBillThreeByPage(
			Map<String, Object> params);

	/**
	 * @param pcauDto
	 * @return
	 */
	BigDecimal findSaleAsnBillCost(PriceCheckAndUpdateDto pcauDto);

	/**
	 * @param params
	 * @return
	 */
	int findAreaBuyDiffOutBillCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> findAreaBuyDiffOutBillByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

}
