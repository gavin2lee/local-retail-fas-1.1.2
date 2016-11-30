/**
 * title:PhysicalBrandManagerImpl.java
 * package:cn.wonhigh.retail.fas.manager
 * description:TODO
 * auther:user
 * date:2016-4-11 上午10:21:04
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.PayableAccountDto;
import cn.wonhigh.retail.fas.common.dto.SettledBillsDto;
import cn.wonhigh.retail.fas.common.model.BillAskPayment;
import cn.wonhigh.retail.fas.common.model.BillAskPaymentDtl;
import cn.wonhigh.retail.fas.service.PhysicalBrandService;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("physicalBrandManager")
public class PhysicalBrandManagerImpl extends BaseCrudManagerImpl implements
		PhysicalBrandManager {
	@Resource
	private PhysicalBrandService physicalBrandService;
	
	@Override
	protected BaseCrudService init() {
		return physicalBrandService;
	}

	@Override
	public int findSettledBillsCount(Map<String, Object> params) {
		return physicalBrandService.findSettledBillsCount(params);
	}

	@Override
	public List<SettledBillsDto> findSettledBillsByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return physicalBrandService.findSettledBillsByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findPayableAccountCount(Map<String, Object> params) {
		return physicalBrandService.findPayableAccountCount(params);
	}

	@Override
	public List<PayableAccountDto> findPayableAccountByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return physicalBrandService.findPayableAccountByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findSettledBillsSumCount(Map<String, Object> params) {
		return physicalBrandService.findSettledBillsSumCount(params);
	}

	@Override
	public List<SettledBillsDto> findSettledBillsSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return physicalBrandService.findSettledBillsSumByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int modifyBillsAuditStatus(String[] nos) {
		return physicalBrandService.modifyBillsAuditStatus(nos);
	}

	@Override
	public int modifyBillsUnAuditStatus(String[] nos) {
		return physicalBrandService.modifyBillsUnAuditStatus(nos);
	}

	@Override
	public int findPayableAccountSumCount(Map<String, Object> params) {
		return physicalBrandService.findPayableAccountSumCount(params);
	}

	@Override
	public List<PayableAccountDto> findPayableAccountSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return physicalBrandService.findPayableAccountSumByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findSalesStorageInquireCount(Map<String, Object> params) {
		return physicalBrandService.findSalesStorageInquireCount(params);
	}

	@Override
	public List<SettledBillsDto> findSalesStorageInquireByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return physicalBrandService.findSalesStorageInquireByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findSalesStorageSumCount(Map<String, Object> params) {
		return physicalBrandService.findSalesStorageSumCount(params);
	}

	@Override
	public List<SettledBillsDto> findSalesStorageSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return physicalBrandService.findSalesStorageSumByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int barchDelBills(String[] billNos) {
		return physicalBrandService.barchDelBills(billNos);
	}

	@Override
	public int barchAuditBills(String[] billNos,Map<String, Object> params) {
		return physicalBrandService.barchAuditBills(billNos,params);
	}

	@Override
	public List<BillAskPaymentDtl> findBillAskPaymentDtl(BillAskPayment model) {
		List<BillAskPaymentDtl> list = physicalBrandService.findBillAskPaymentDtl(model);
		if(list != null && list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public List<SettledBillsDto> findSettledBillsSumTotalRow(
			Map<String, Object> params) {
		return physicalBrandService.findSettledBillsSumTotalRow(params);
	}

	@Override
	public List<SettledBillsDto> findSettledBillsListTotalRow(
			Map<String, Object> params) {
		return physicalBrandService.findSettledBillsListTotalRow(params);
	}

}
