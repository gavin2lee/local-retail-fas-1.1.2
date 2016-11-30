package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.InvoiceBaseReportMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @date  2015-11-23 10:01:20
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
@Service("invoiceBaseReportService")
class InvoiceBaseReportServiceImpl extends BaseCrudServiceImpl implements InvoiceBaseReportService {
    @Resource
    private InvoiceBaseReportMapper invoiceBaseReportMapper;

    @Override
    public BaseCrudMapper init() {
        return invoiceBaseReportMapper;
    }

	@Override
	public int selectCount(Map<String, Object> params) throws ServiceException {
		return invoiceBaseReportMapper.selectCount(params);
	}

	@Override
	public Map<String, Object> selectTotal(Map<String, Object> params)
			throws ServiceException {
		return invoiceBaseReportMapper.selectTotal(params);
	}

	@Override
	public List<Map<String, Object>> selectData(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return invoiceBaseReportMapper.selectData(page, orderByField, orderBy, params);
	}

	
}