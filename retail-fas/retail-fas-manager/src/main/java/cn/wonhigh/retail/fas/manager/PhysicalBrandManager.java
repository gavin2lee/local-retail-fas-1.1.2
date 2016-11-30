/**
 * title:PhysicalBrandManager.java
 * package:cn.wonhigh.retail.fas.manager
 * description:TODO
 * auther:user
 * date:2016-4-11 上午10:16:56
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.PayableAccountDto;
import cn.wonhigh.retail.fas.common.dto.SettledBillsDto;
import cn.wonhigh.retail.fas.common.model.BillAskPayment;
import cn.wonhigh.retail.fas.common.model.BillAskPaymentDtl;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;


public interface PhysicalBrandManager extends BaseCrudManager {
	/**
	 * 已结算单据数量查询
	 * @param params
	 * @return
	 */
	int findSettledBillsCount(Map<String, Object> params);

	/**
	 * 已结算单据分页查询
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> findSettledBillsByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);
	
	/**
	 * 已结算单据汇总数量查询
	 * @param params
	 * @return
	 */
	int findSettledBillsSumCount(Map<String, Object> params);

	/**
	 * 已结算单据汇总分页查询
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> findSettledBillsSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);
	
	/**
	 * 应付账款表数量查询
	 * @param params
	 * @return
	 */
	int findPayableAccountCount(Map<String, Object> params);

	/**
	 * 应付账款表分页查询
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PayableAccountDto> findPayableAccountByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);
	
	/**
	 * 应付账款表汇总数量查询
	 * @param params
	 * @return
	 */
	int findPayableAccountSumCount(Map<String, Object> params);

	/**
	 * 应付账款表汇总分页查询
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PayableAccountDto> findPayableAccountSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * 审核
	 * @param no
	 * @return
	 */
	int modifyBillsAuditStatus(String[] nos);

	/**
	 * 反审核
	 * @param no
	 * @return
	 */
	int modifyBillsUnAuditStatus(String[] nos);

	/**
	 * 代销商品销存查询
	 * @param params
	 * @return
	 */
	int findSalesStorageInquireCount(Map<String, Object> params);

	/**
	 * 代销商品销存分页查询
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> findSalesStorageInquireByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);
	
	/**
	 * 代销商品销存汇总查询
	 * @param params
	 * @return
	 */
	int findSalesStorageSumCount(Map<String, Object> params);

	/**
	 * 代销商品销存汇总分页查询
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> findSalesStorageSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param billNos
	 * @return
	 */
	int barchDelBills(String[] billNos);

	/**
	 * @param billNos
	 * @return
	 */
	int barchAuditBills(String[] billNos,Map<String, Object> params);

	/**
	 * @param model
	 * @return
	 */
	List<BillAskPaymentDtl> findBillAskPaymentDtl(BillAskPayment model);

	/**
	 * 查询已结算单据审核汇总合计行
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> findSettledBillsSumTotalRow(Map<String, Object> params);

	/**
	 * 查询已结算单据审核明细合计行
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> findSettledBillsListTotalRow(
			Map<String, Object> params);
	
}
