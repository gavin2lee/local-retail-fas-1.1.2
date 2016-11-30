package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.dal.database.BillCommonInvoiceRegisterMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-10 14:40:44
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
@Service("billCommonInvoiceRegisterService")
class BillCommonInvoiceRegisterServiceImpl extends BaseCrudServiceImpl implements BillCommonInvoiceRegisterService {
    @Resource
    private BillCommonInvoiceRegisterMapper billCommonInvoiceRegisterMapper;

    @Override
    public BaseCrudMapper init() {
        return billCommonInvoiceRegisterMapper;
    }
    
	public List<LookupVo> findByAllCustomer(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return billCommonInvoiceRegisterMapper.selectByAllCustomer(page, orderByField, orderBy, params,null);
	}
	@Override
	public int findAllCustomerCount(Map<String, Object> params)
			throws ServiceException {
		
		return billCommonInvoiceRegisterMapper.selectAllCustomerCount(params, null);
	}
	@Override
	public int selectCountByBalanceNo(Map<String, Object> params)
			throws ServiceException {
		try {
			return billCommonInvoiceRegisterMapper.selectCountByBalanceNo(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
	@Override
	public List<BillCommonInvoiceRegister> selectListByBalanceNo(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try {
			return billCommonInvoiceRegisterMapper.selectListByBalanceNo(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
	
	@Override
	public List<BillCommonInvoiceRegister> getByInvoice(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return billCommonInvoiceRegisterMapper.getByInvoice(page, orderByField, orderBy, params);
	}

	@Override
	public int getCountByInvoice(Map<String, Object> params) {
		return billCommonInvoiceRegisterMapper.getCountByInvoice(params);
	}
	
	@Override
	public void updateInvoice(String invoiceRegisterNo,String id, String user) {
		billCommonInvoiceRegisterMapper.updateConfirm(invoiceRegisterNo,id, user);
	}

	@Override
	public List<BillCommonInvoiceRegister> getShopAndBrand(
			Map<String, Object> params) {
		return billCommonInvoiceRegisterMapper.getShopAndBrand(params);
	}
	
//	/**
//	 * 在团购预付款登记里，根据结算公司和客户查询，取得发票号的信息
//	 * @param page
//	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录
//	 * @return
//	 */
//	@Override
//	public List<BillCommonInvoiceRegister> findInvoiceRegisterForPayment(SimplePage page, Map<String,Object> params) throws ServiceException{
//		try {
//			return billCommonInvoiceRegisterMapper.findInvoiceRegisterForPayment(page, params);
//		} catch (Exception e) {
//			throw new ServiceException(e.getMessage(),e);
//		}
//	}
//	
//	/**
//	 * 在团购预付款登记里，根据结算公司和客户查询，取得发票号的信息记录数
//	 * @param page
//	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录数
//	 * @return
//	 */
//	@Override
//	public int findInvoiceregisterCountForPayment(Map<String,Object> params) throws ServiceException{
//		try {
//			return billCommonInvoiceRegisterMapper.findInvoiceregisterCountForPayment(params);
//		} catch (Exception e) {
//			throw new ServiceException(e.getMessage(),e);
//		}
//	}
//	
//	/**
//	 * 根据发票单据号修改发票是否使用标识，0＝未使用；1＝已使用
//	 * @param params
//	 */
//	@Override
//	public void updateUseFlagByBillNo(Map<String,Object> params) throws ServiceException {
//		try {
//			billCommonInvoiceRegisterMapper.updateUseFlagByBillNo(params);
//		} catch (Exception e) {
//			throw new ServiceException(e.getMessage(),e);
//		}
//	}

}