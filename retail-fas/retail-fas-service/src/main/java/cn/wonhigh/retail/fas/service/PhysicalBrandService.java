/**
 * title:PhysicalBrandService.java
 * package:cn.wonhigh.retail.fas.service
 * description:TODO
 * auther:user
 * date:2016-4-11 上午10:24:16
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.PayableAccountDto;
import cn.wonhigh.retail.fas.common.dto.SettledBillsDto;
import cn.wonhigh.retail.fas.common.model.BillAskPayment;
import cn.wonhigh.retail.fas.common.model.BillAskPaymentDtl;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 
 */
public interface PhysicalBrandService extends BaseCrudService {

	/**
	 * @param params
	 * @return
	 */
	int findSettledBillsCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> findSettledBillsByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findPayableAccountCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PayableAccountDto> findPayableAccountByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findSettledBillsSumCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> findSettledBillsSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param no
	 * @return
	 */
	int modifyBillsAuditStatus(String[] nos);

	/**
	 * @param no
	 * @return
	 */
	int modifyBillsUnAuditStatus(String[] nos);

	/**
	 * @param params
	 * @return
	 */
	int findPayableAccountSumCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PayableAccountDto> findPayableAccountSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findSalesStorageInquireCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> findSalesStorageInquireByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int findSalesStorageSumCount(Map<String, Object> params);

	/**
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
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=ServiceException.class)
	int barchDelBills(String[] billNos);

	/**
	 * @param billNos
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=ServiceException.class)
	int barchAuditBills(String[] billNos,Map<String, Object> params);

	/**
	 * @param model
	 * @return
	 */
	List<BillAskPaymentDtl> findBillAskPaymentDtl(BillAskPayment model);

	/**
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> findSettledBillsSumTotalRow(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<SettledBillsDto> findSettledBillsListTotalRow(
			Map<String, Object> params);

}
