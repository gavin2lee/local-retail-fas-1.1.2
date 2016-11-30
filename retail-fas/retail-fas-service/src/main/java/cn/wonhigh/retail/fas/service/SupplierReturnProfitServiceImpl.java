package cn.wonhigh.retail.fas.service;

import java.util.Map;

import cn.wonhigh.retail.fas.dal.database.SupplierReturnProfitMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-19 10:35:13
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
@Service("supplierReturnProfitService")
class SupplierReturnProfitServiceImpl extends BaseCrudServiceImpl implements SupplierReturnProfitService {
    @Resource
    private SupplierReturnProfitMapper supplierReturnProfitMapper;

    @Override
    public BaseCrudMapper init() {
        return supplierReturnProfitMapper;
    }

	@Override
	public void deleteSupplierReturnProfit(Map<String, Object> params) throws ServiceException {
		try {
			supplierReturnProfitMapper.deleteSupplierReturnProfit(params);
		} catch (Exception e) {
			throw new ServiceException();
		}
	}
}