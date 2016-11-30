package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.RegisterInvoice;
import cn.wonhigh.retail.fas.dal.database.RegisterInvoiceMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-22 13:51:56
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
@Service("registerInvoiceService")
class RegisterInvoiceServiceImpl extends BaseCrudServiceImpl implements RegisterInvoiceService {
    @Resource
    private RegisterInvoiceMapper registerInvoiceMapper;

    @Override
    public BaseCrudMapper init() {
        return registerInvoiceMapper;
    }

	@Override
	public List<RegisterInvoice> findByBillNo(Map<String, Object> params) {
		return registerInvoiceMapper.findByBillNo(params);
	}
}