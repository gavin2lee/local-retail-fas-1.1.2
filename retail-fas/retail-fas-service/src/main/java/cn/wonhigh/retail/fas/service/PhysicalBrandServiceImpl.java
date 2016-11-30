/**
 * title:PhysicalBrandServiceImpl.java
 * package:cn.wonhigh.retail.fas.service
 * description:TODO
 * auther:user
 * date:2016-4-11 上午10:25:15
 */
package cn.wonhigh.retail.fas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.PayableAccountDto;
import cn.wonhigh.retail.fas.common.dto.SettledBillsDto;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillAskPayment;
import cn.wonhigh.retail.fas.common.model.BillAskPaymentDtl;
import cn.wonhigh.retail.fas.dal.database.BillAskPaymentMapper;
import cn.wonhigh.retail.fas.dal.database.PhysicalBrandMapper;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("physicalBrandService")
public class PhysicalBrandServiceImpl extends BaseCrudServiceImpl implements
		PhysicalBrandService {
	@Resource
	private PhysicalBrandMapper physicalBrandMapper;
	@Resource
	private BillAskPaymentMapper billAskPaymentMapperv;
	
	@Override
	public BaseCrudMapper init() {
		return physicalBrandMapper;
	}

	@Override
	public int findSettledBillsCount(Map<String, Object> params) {
		return physicalBrandMapper.selectSettledBillsCount(params);
	}

	@Override
	public List<SettledBillsDto> findSettledBillsByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return physicalBrandMapper.selectSettledBillsByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findPayableAccountCount(Map<String, Object> params) {
		return physicalBrandMapper.selectPayableAccountCount(params);
	}

	@Override
	public List<PayableAccountDto> findPayableAccountByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return physicalBrandMapper.selectPayableAccountByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findSettledBillsSumCount(Map<String, Object> params) {
		return physicalBrandMapper.selectSettledBillsSumCount(params);
	}

	@Override
	public List<SettledBillsDto> findSettledBillsSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return physicalBrandMapper.selectSettledBillsSumByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int modifyBillsAuditStatus(String[] nos) {
		Map<String,Object> params=new HashMap<String, Object>();
		int n = 0;
		for (int i = 0; i < nos.length; i++) {
			params.put("billNo", nos[i]);
			n =  physicalBrandMapper.updateBillsAuditStatus(params);;
		}
		return n;
	}

	@Override
	public int modifyBillsUnAuditStatus(String[] nos) {
		Map<String,Object> params=new HashMap<String, Object>();
		int n = 0;
		for (int i = 0; i < nos.length; i++) {
			params.put("billNo", nos[i]);
			n =  physicalBrandMapper.updateBillsUnAuditStatus(params);;
		}
		return n;
	}

	@Override
	public int findPayableAccountSumCount(Map<String, Object> params) {
		return physicalBrandMapper.selectPayableAccountSumCount(params);
	}

	@Override
	public List<PayableAccountDto> findPayableAccountSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return physicalBrandMapper.selectPayableAccountSumByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findSalesStorageInquireCount(Map<String, Object> params) {
		return physicalBrandMapper.selectSalesStorageInquireCount(params);
	}

	@Override
	public List<SettledBillsDto> findSalesStorageInquireByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return physicalBrandMapper.selectSalesStorageInquireByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int findSalesStorageSumCount(Map<String, Object> params) {
		return physicalBrandMapper.selectSalesStorageSumCount(params);
	}

	@Override
	public List<SettledBillsDto> findSalesStorageSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return physicalBrandMapper.selectSalesStorageSumByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public int barchDelBills(String[] billNos) {
		Map<String,Object> params=new HashMap<>();
		int n=0;
		for (int i = 0; i < billNos.length; i++) {
			params.put("billNo", billNos[i]);
			n = physicalBrandMapper.deleteAskpaymentBills(params);
			physicalBrandMapper.deleteAskPaymentBillsDtl(params);
			billAskPaymentMapperv.clearBalanceAskPaymentNo(billNos[i], BalanceTypeEnums.PE_SUPPLIER.getTypeNo());
		}
		return n;
	}

	@Override
	public int barchAuditBills(String[] billNos,Map<String, Object> params) {
		int n=0;
		for (int i = 0; i < billNos.length; i++) {
			params.put("billNo", billNos[i]);
			n = physicalBrandMapper.auditAskpaymentBills(params);
		}
		return n;
	}

	@Override
	public List<BillAskPaymentDtl> findBillAskPaymentDtl(BillAskPayment model) {
		return physicalBrandMapper.selectBillAskPaymentDtl(model);
	}

	@Override
	public List<SettledBillsDto> findSettledBillsSumTotalRow(
			Map<String, Object> params) {
		return physicalBrandMapper.selectSettledBillsSumTotalRow(params);
	}

	@Override
	public List<SettledBillsDto> findSettledBillsListTotalRow(
			Map<String, Object> params) {
		return physicalBrandMapper.selectSettledBillsListTotalRow(params);
	}

}
