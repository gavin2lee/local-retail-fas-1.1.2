/**
 * title:PhysicalBrandMapper.java
 * package:cn.wonhigh.retail.fas.dal.database
 * description:已结算单据审核、代销商品销存查询、应付账款表
 * auther:user
 * date:2016-4-11 上午11:24:28
 */
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.PayableAccountDto;
import cn.wonhigh.retail.fas.common.dto.SettledBillsDto;
import cn.wonhigh.retail.fas.common.model.BillAskPayment;
import cn.wonhigh.retail.fas.common.model.BillAskPaymentDtl;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface PhysicalBrandMapper extends BaseCrudMapper {

	/**
	 * @param params
	 * @return
	 */
	int selectSettledBillsCount(@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> selectSettledBillsByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectPayableAccountCount(@Param("params")Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PayableAccountDto> selectPayableAccountByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectSettledBillsSumCount(@Param("params")Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> selectSettledBillsSumByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param no
	 * @return
	 */
	int updateBillsAuditStatus(@Param("params")Map<String,Object> params);

	/**
	 * @param no
	 * @return
	 */
	int updateBillsUnAuditStatus(@Param("params")Map<String,Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectPayableAccountSumCount(@Param("params")Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PayableAccountDto> selectPayableAccountSumByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectSalesStorageInquireCount(@Param("params")Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> selectSalesStorageInquireByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectSalesStorageSumCount(@Param("params")Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> selectSalesStorageSumByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int deleteAskpaymentBills(@Param("params")Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int auditAskpaymentBills(@Param("params")Map<String, Object> params);

	/**
	 * @param params
	 */
	int deleteAskPaymentBillsDtl(@Param("params")Map<String, Object> params);

	/**
	 * @param model
	 * @return
	 */
	List<BillAskPaymentDtl> selectBillAskPaymentDtl(BillAskPayment model);

	/**
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> selectSettledBillsSumTotalRow(@Param("params")
			Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> selectSettledBillsListTotalRow(@Param("params")
			Map<String, Object> params);

}
