package cn.wonhigh.retail.fas.api.dal;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.api.model.RegisterInvoiceModel;

public interface BillCommonInvoiceRegisterMapper {
	
	List<RegisterInvoiceModel> queryCommonInvoiceRegisterList(Map<String,Object> params);
	
	Integer queryBillCommonInvoiceTotal(Map<String,Object> params);
	
	int updateBillStatus(Map<String,Object> params);
}
