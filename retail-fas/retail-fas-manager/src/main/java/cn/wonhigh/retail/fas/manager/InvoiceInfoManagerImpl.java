package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.InvoiceInfo;
import cn.wonhigh.retail.fas.service.InvoiceInfoService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2015-02-03 11:06:43
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
@Service("invoiceInfoManager")
class InvoiceInfoManagerImpl extends BaseCrudManagerImpl implements InvoiceInfoManager {
    @Resource
    private InvoiceInfoService invoiceInfoService;

    @Override
    public BaseCrudService init() {
        return invoiceInfoService;
    }

	@Override
	public Integer selectDistinctClientCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return invoiceInfoService.selectDistinctClientCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<InvoiceInfo> selectDistinctClientByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return invoiceInfoService.selectDistinctClientByPage(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}