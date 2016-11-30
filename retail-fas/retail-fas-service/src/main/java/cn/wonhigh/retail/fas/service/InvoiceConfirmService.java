/**  
 *   
 * 项目名称：retail-fas-service  
 * 类名称：InvoiceConfirmService  
 * 类描述：
 * 创建人：ouyang.zm  
 * 创建时间：2014-12-3 下午3:08:31  
 * @version       
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.InvoiceConfirm;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

public interface InvoiceConfirmService extends BaseCrudService {

	/**
	 * @param idList
	 * @param invoiceConfirm
	 * @return
	 * @throws ServiceException 
	 */
	int updateById(String[] idList, InvoiceConfirm invoiceConfirm,Integer flag) throws ServiceException;

	/**
	 * 生成采购发票
	 * 
	 * @param oList
	 * @param loginName
	 * @return
	 * @throws ServiceException
	 */
	Map<String,Object> generateBill(List<BillCommonInvoiceRegister> oList, String loginName)
			throws ServiceException;

}
