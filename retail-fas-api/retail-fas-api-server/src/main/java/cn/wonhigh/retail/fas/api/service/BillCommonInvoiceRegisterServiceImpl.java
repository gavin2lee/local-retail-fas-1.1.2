package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.BillCommonInvoiceRegisterMapper;
import cn.wonhigh.retail.fas.api.model.RegisterInvoiceModel;

import com.yougou.logistics.base.common.exception.ServiceException;

@Service("billCommonInvoiceRegisterService")
public class BillCommonInvoiceRegisterServiceImpl implements
		BillCommonInvoiceRegisterService {

	@Resource
	private BillCommonInvoiceRegisterMapper billCommonInvoiceRegisterMapper;
	
	@Override
	public List<RegisterInvoiceModel> queryBillCommonInvoiceRegister(
			Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		return billCommonInvoiceRegisterMapper.queryCommonInvoiceRegisterList(params);
	}

	@Override
	public Integer queryBillCommonInvoiceTotal(Map<String, Object> params)
			throws ServiceException {
		// TODO Auto-generated method stub
		return billCommonInvoiceRegisterMapper.queryBillCommonInvoiceTotal(params);
	}

	@Override
	public int updateBillStatus(Map<String, Object> params)
			throws ServiceException {
		// TODO Auto-generated method stub
		return billCommonInvoiceRegisterMapper.updateBillStatus(params);
	}

}
