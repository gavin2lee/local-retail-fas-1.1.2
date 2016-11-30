package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceInfo;
import cn.wonhigh.retail.fas.dal.database.BillBalanceInvoiceInfoMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
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
@Service("billBalanceInvoiceInfoService")
class BillBalanceInvoiceInfoServiceImpl extends BaseCrudServiceImpl implements BillBalanceInvoiceInfoService {
    @Resource
    private BillBalanceInvoiceInfoMapper billBalanceInvoiceInfoMapper;

    @Override
    public BaseCrudMapper init() {
        return billBalanceInvoiceInfoMapper;
    }

	@Override
	public List<BillBalanceInvoiceInfo> findByCustomerNo(SimplePage page, Map<String, Object> params)
			throws ServiceException {
		return billBalanceInvoiceInfoMapper.selectByCustomerNo(page, params);
	}

	@Override
	public int getCount(Map<String, Object> params) throws ServiceException {
		return billBalanceInvoiceInfoMapper.getCount(params);
	}

	@Override
	public BillBalanceInvoiceInfo getInvoiceAmount(String invoiceNo) throws ServiceException {
		return billBalanceInvoiceInfoMapper.getInvoiceAmount(invoiceNo);
	}

	@Override
	public void deleteInvoiceInfo(String billNo) {
		billBalanceInvoiceInfoMapper.deleteInvoiceInfo(billNo);
	}
}