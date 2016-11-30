/**
 * title:PriceCheckAndUpdateMapper.java
 * package:cn.wonhigh.retail.fas.dal.database
 * description:TODO
 * auther:user
 * date:2016-8-2 下午3:14:33
 */
package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.PriceCheckAndUpdateDto;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 
 */
public interface PriceCheckAndUpdateMapper extends BaseCrudMapper {

	/**
	 * @param params
	 * @return
	 */
	int selectHqSaleBillCostIsZeroCount(
			@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> selectHqSaleBillCostIsZeroByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectHqSaleBillCostIsDiffCount(
			@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> selectHqSaleBillCostIsDiffByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectAreaSaleBillCostIsZeroCount(
			@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> selectAreaSaleBillCostIsZeroByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> selectAreaSaleBillCostIsDiffByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectAreaSaleBillCostIsDiffCount(
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectAreaBuyBillCostIsZeroCount(@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> selectAreaBuyBillCostIsZeroByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectAreaBuyBillCostIsDiffCount(@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> selectAreaBuyBillCostIsDiffByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);


	/**
	 * @param params
	 * @return
	 */
	int selectHqBuyBillCostIsZeroCount(@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> selectHqBuyBillCostIsZeroByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectHqBuyBillCostIsDiffCount(@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> selectHqBuyBillCostIsDiffByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);
	
	/**
	 * 更新buy表地区价
	 * @param pcauDto
	 */
	void updateBillBuyBalanceCost(PriceCheckAndUpdateDto pcauDto);
	
	/**
	 * 更新sale表地区价
	 * @param pcauDto
	 */
	void updateBillSaleBalanceCost(PriceCheckAndUpdateDto pcauDto);

	/**
	 * @param params
	 * @return
	 */
	int selectHqSaleBillCostIsNullCount(@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> selectHqSaleBillCostIsNullByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param pcauDto
	 * @return
	 */
	BigDecimal selectAsnBillCost(PriceCheckAndUpdateDto pcauDto);

	/**
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> selectAreaBuyBillOneByPage(@Param("params")
			Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> selectAreaBuyBillTwoByPage(@Param("params")
			Map<String, Object> params);

	/**
	 * @param pcauDto
	 * @return
	 */
	BigDecimal selectTransferAndReturnBillCost(PriceCheckAndUpdateDto pcauDto);

	/**
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> selectAreaBuyBillThreeByPage(@Param("params")
			Map<String, Object> params);

	/**
	 * @param pcauDto
	 * @return
	 */
	BigDecimal selectSaleAsnBillCost(PriceCheckAndUpdateDto pcauDto);

	/**
	 * @param params
	 * @return
	 */
	int selectAreaBuyDiffOutBillCount(@Param("params")Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PriceCheckAndUpdateDto> selectAreaBuyDiffOutBillByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

}
