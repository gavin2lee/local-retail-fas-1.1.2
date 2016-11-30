package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.enums.BalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceSource;
import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.dal.database.BillBalanceInvoiceApplyMapper;
import cn.wonhigh.retail.fas.dal.database.BillBalanceInvoiceDtlMapper;
import cn.wonhigh.retail.fas.dal.database.BillBalanceInvoiceInfoMapper;
import cn.wonhigh.retail.fas.dal.database.BillBalanceInvoiceSourceMapper;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-30 11:19:51
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("billBalanceInvoiceApplyService")
class BillBalanceInvoiceApplyServiceImpl extends BaseCrudServiceImpl implements BillBalanceInvoiceApplyService {
	@Resource
	private BillBalanceInvoiceApplyMapper billBalanceInvoiceApplyMapper;

	@Resource
	private BillBalanceInvoiceSourceMapper billBalanceInvoiceSourceMapper;
	
	@Resource
	private BillBalanceInvoiceInfoMapper billBalanceInvoiceInfoMapper;

	@Resource
	private BillShopBalanceMapper billShopBalanceMapper;

	@Resource
	private BillBalanceInvoiceDtlMapper billBalanceInvoiceDtlMapper;

	@Override
	public BaseCrudMapper init() {
		return billBalanceInvoiceApplyMapper;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int delete(String[] ids) throws ServiceException {
		try {
			int iCount = 0;
			for (String str : ids) {
				String id = str.split(",")[0];
				String billNo = str.split(",")[1];
				String balanceType = str.split(",")[2];
				List<BillBalanceInvoiceSource> invoiceSource;
				Map<String, Object> buyerparams = new HashMap<String, Object>();
				buyerparams.put("billNo", billNo);
				invoiceSource = billBalanceInvoiceSourceMapper.selectByParams(null, buyerparams);
				//				String balanceType = billBalanceInvoiceApplyMapper.getBalanceType(billNo);
				if (null != balanceType && "" != balanceType) {
					if (balanceType.equals(String.valueOf(BalanceTypeEnums.AREA_MALL.getTypeNo()))) {
						BillShopBalance shopBalance = new BillShopBalance();
						shopBalance.setBalanceNo(invoiceSource.get(0).getBalanceNo());
						shopBalance.setInvoiceApplyDate(null);
						shopBalance.setInvoiceApplyNo("");
						shopBalance.setStatus(BalanceStatusEnums.SEND_FINANCE_CONFIRM.getTypeNo());
						billShopBalanceMapper.updateInvoiceByBalanceNo(shopBalance);
					} else {
						BillBalanceInvoiceApply invoiceApply = new BillBalanceInvoiceApply();
						invoiceApply.setBillNo(invoiceSource.get(0).getBalanceNo());
						invoiceApply.setInvoiceApplyNo("");
						invoiceApply.setStatus(BalanceStatusEnums.RECEIVE_FINANCE_CONFIRM.getTypeNo());
						billBalanceInvoiceApplyMapper.updateInvoiceApplyNo(invoiceApply);
					}
				}
				iCount = billBalanceInvoiceApplyMapper.deleteByPrimaryKey(Integer.valueOf(id));
				billBalanceInvoiceSourceMapper.deleteInvoiceSource(billNo);
				billBalanceInvoiceInfoMapper.deleteInvoiceInfo(billNo);
				billBalanceInvoiceDtlMapper.deleteInvoiceDtl(billNo);
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillSaleBalance> getBillSaleBalanceSum(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) {
		return billBalanceInvoiceApplyMapper.getBillSaleBalanceSum(page, orderByField, orderBy, params);
	}

	@Override
	public List<BillSaleBalance> getBillSaleBalanceDtl(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) {
		return billBalanceInvoiceApplyMapper.getBillSaleBalanceDtl(page, orderByField, orderBy, params);
	}

	@Override
	public int selectBillSaleBalanceCount(Map<String, Object> params) throws ServiceException {
		try {
			return billBalanceInvoiceApplyMapper.selectBillSaleBalanceCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalanceInvoiceApply> getByPage(SimplePage page, Map<String, Object> params) {
		return billBalanceInvoiceApplyMapper.getByPage(page, params);
	}

	@Override
	public int getCount(Map<String, Object> params) {
		return billBalanceInvoiceApplyMapper.getCount(params);
	}

	@Override
	public void updateInvoiceApplyNo(BillBalanceInvoiceApply invoiceApply) {
		billBalanceInvoiceApplyMapper.updateInvoiceApplyNo(invoiceApply);
	}
	

	@Override
	public void updateByPrimaryKeySelective(BillBalanceInvoiceApply billBalanceInvoiceApply) {
		billBalanceInvoiceApplyMapper.updateByPrimaryKeySelective(billBalanceInvoiceApply);
	}

	@Override
	public void updateInvoiceRegisterNo(BillBalanceInvoiceApply billBalanceInvoiceApply) {
		billBalanceInvoiceApplyMapper.updateInvoiceRegisterNo(billBalanceInvoiceApply);
	}

	@Override
	public List<BillBalanceInvoiceApply> getByBalanceNo(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) {
		return billBalanceInvoiceApplyMapper.getByBalanceNo(page, orderByField, orderBy, params);
	}

	@Override
	public int getCountByBalanceNo(Map<String, Object> params){
		return billBalanceInvoiceApplyMapper.getCountByBalanceNo(params);
	}

	@Override
	public List<BillSaleBalance> getBillSaleOrderSum(Map<String, Object> params) {
		return billBalanceInvoiceApplyMapper.getBillSaleOrderSum(params);
	}

	@Override
	public List<BillSaleBalance> getBillSaleSummaryByBalanceNos(Map<String, Object> params) throws ServiceException {
		try {
			return billBalanceInvoiceApplyMapper.getBillSaleSummaryByBalanceNos(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	/**
	 * 在团购预付款登记里，根据结算公司和客户查询，取得发票号的信息
	 * @param page
	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录
	 * @return
	 */
	@Override
	public List<BillCommonInvoiceRegister> findInvoiceApplyForPayment(SimplePage page, Map<String,Object> params) throws ServiceException{
		try {
			return billBalanceInvoiceApplyMapper.findInvoiceApplyForPayment(page, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}
	
	/**
	 * 在团购预付款登记里，根据结算公司和客户查询，取得发票号的信息记录数
	 * @param page
	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录数
	 * @return
	 */
	@Override
	public int findInvoiceApplyCountForPayment(Map<String,Object> params) throws ServiceException{
		try {
			return billBalanceInvoiceApplyMapper.findInvoiceApplyCountForPayment(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}
	
	/**
	 * 根据发票单据号修改发票是否使用标识，0＝未使用；1＝已使用
	 * @param params
	 */
	@Override
	public void updateUseFlagByBillNo(Map<String,Object> params) throws ServiceException {
		try {
			billBalanceInvoiceApplyMapper.updateUseFlagByBillNo(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public BigDecimal findDeductionAmountByBalanceNos(Map<String, Object> params) throws ServiceException {
		try {
			return billBalanceInvoiceApplyMapper.getDeductionAmountByBalanceNos(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}
	
	/**
	 * 导出税控系统的模板
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	@Override
	public List<BillBalanceInvoiceApply> findTaxExportList(Map<String,Object> params) throws ServiceException{
		try {
			return billBalanceInvoiceApplyMapper.findTaxExportList(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}
	
	/**
	 * 查询华东税控模板信息
	 * @param params
	 * @return
	 */
	public List<BillBalanceInvoiceApply> findHdTaxExportList(Map<String,Object> params) throws ServiceException{
		try {
			return billBalanceInvoiceApplyMapper.findHdTaxExportList(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}
}