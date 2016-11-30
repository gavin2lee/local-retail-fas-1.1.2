/**  
*   
* 项目名称：retail-fas-manager  
* 类名称：InvoiceConfirmManagerImpl  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-12-3 下午3:06:32  
* @version       
*/ 
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.InvoiceConfirm;
import cn.wonhigh.retail.fas.service.InvoiceConfirmService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
@Service("InvoiceConfirmManager")
public class InvoiceConfirmManagerImpl extends BaseCrudManagerImpl implements
		InvoiceConfirmManager {
  @Resource
    private InvoiceConfirmService invoiceConfirmService;

	@Override
	protected BaseCrudService init() {
		return invoiceConfirmService;
	}

	@Override
	public int updateById(String[] idList, InvoiceConfirm invoiceConfirm,Integer flag) throws ServiceException {
		return invoiceConfirmService.updateById(idList,invoiceConfirm,flag);
	}
	
	@Override
	public Map<String,Object> generateBill(List<BillCommonInvoiceRegister> oList, String loginName)
			throws ManagerException {
		try {
			return invoiceConfirmService.generateBill(oList, loginName);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

}
