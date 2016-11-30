package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.api.model.RegisterInvoiceModel;

import com.yougou.logistics.base.common.exception.ServiceException;

public interface BillCommonInvoiceRegisterService {
	
	Integer queryBillCommonInvoiceTotal(Map<String,Object> params) throws ServiceException;
	
	List<RegisterInvoiceModel> queryBillCommonInvoiceRegister(Map<String,Object> params) throws ServiceException;
	
	int updateBillStatus(Map<String,Object> params) throws ServiceException;
	
}
