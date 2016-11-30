package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.common.model.InsidePurchaseBalanceDate;
import cn.wonhigh.retail.fas.dal.database.InsidePurchaseBalanceDateMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-02-02 20:00:10
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("insidePurchaseBalanceDateService")
class InsidePurchaseBalanceDateServiceImpl extends BaseCrudServiceImpl implements InsidePurchaseBalanceDateService {
    @Resource
    private InsidePurchaseBalanceDateMapper insidePurchaseBalanceDateMapper;

    @Override
    public BaseCrudMapper init() {
        return insidePurchaseBalanceDateMapper;
    }

	@Override
	public void updateInvoiceFlagByCondition(InsidePurchaseBalanceDate insidePurchaseBalanceDate)throws ServiceException {
		insidePurchaseBalanceDateMapper.updateInvoiceFlagByCondition(insidePurchaseBalanceDate);
	}
}