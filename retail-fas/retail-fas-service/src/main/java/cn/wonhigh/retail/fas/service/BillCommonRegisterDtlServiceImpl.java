package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;
import cn.wonhigh.retail.fas.dal.database.BillCommonRegisterDtlMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
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
@Service("billCommonRegisterDtlService")
class BillCommonRegisterDtlServiceImpl extends BaseCrudServiceImpl implements BillCommonRegisterDtlService {
    @Resource
    private BillCommonRegisterDtlMapper billCommonRegisterDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return billCommonRegisterDtlMapper;
    }

	@Override
	public BillCommonRegisterDtl getInvoiceAmount(String invoiceNo) throws ServiceException {
		return billCommonRegisterDtlMapper.getInvoiceAmount(invoiceNo);
	}

	@Override
	public int deleteByBillNo(String billNo) {
		return billCommonRegisterDtlMapper.deleteByBillNo(billNo);
	}
	
}