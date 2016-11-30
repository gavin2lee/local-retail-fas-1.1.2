package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.InvoiceInfo;
import cn.wonhigh.retail.fas.dal.database.InvoiceInfoMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
@Service("invoiceInfoService")
class InvoiceInfoServiceImpl extends BaseServiceImpl implements InvoiceInfoService {
    @Resource
    private InvoiceInfoMapper invoiceInfoMapper;

    @Override
    public BaseCrudMapper init() {
        return invoiceInfoMapper;
    }

	@Override
	public Integer selectDistinctClientCount(Map<String, Object> params)
			throws ServiceException {
		return invoiceInfoMapper.selectDistinctClientCount(params);
	}

	@Override
	public List<InvoiceInfo> selectDistinctClientByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return invoiceInfoMapper.selectDistinctClientByPage(page, orderByField, orderBy, params);
	}
}