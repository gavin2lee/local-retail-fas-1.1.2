package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceInfo;
import cn.wonhigh.retail.fas.service.BillBalanceInvoiceInfoService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

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
@Service("billBalanceInvoiceInfoManager")
class BillBalanceInvoiceInfoManagerImpl extends BaseCrudManagerImpl implements BillBalanceInvoiceInfoManager {
    @Resource
    private BillBalanceInvoiceInfoService billBalanceInvoiceInfoService;

    @Override
    public BaseCrudService init() {
        return billBalanceInvoiceInfoService;
    }

	@Override
	public List<BillBalanceInvoiceInfo> findByCustomerNo(SimplePage page, Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceInvoiceInfoService.findByCustomerNo(page, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int getCount(Map<String, Object> params) throws ManagerException {
		try {
			return billBalanceInvoiceInfoService.getCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public BillBalanceInvoiceInfo getInvoiceAmount(String invoiceNo) throws ManagerException {
		try {
			return billBalanceInvoiceInfoService.getInvoiceAmount(invoiceNo);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}