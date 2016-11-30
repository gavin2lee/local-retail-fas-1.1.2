package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerOrderRemainDto;
import cn.wonhigh.retail.fas.common.model.CustomerOrderRemain;
import cn.wonhigh.retail.fas.dal.database.CustomerOrderRemainMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 批发订单余额 
 * @author user
 * @date  2016-06-08 18:09:42
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
@Service("customerOrderRemainService")
class CustomerOrderRemainServiceImpl extends BaseCrudServiceImpl implements CustomerOrderRemainService {
    @Resource
    private CustomerOrderRemainMapper customerOrderRemainMapper;

    @Override
    public BaseCrudMapper init() {
        return customerOrderRemainMapper;
    }

	@Override
	public List<WholesaleCustomerOrderRemainDto> queryWholesaleCustomerOrderRemain(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params) throws ServiceException {
		
		return customerOrderRemainMapper.queryWholesaleCustomerOrderRemain(page, sortColumn, sortOrder, params);
	}

	@Override
	public List<CustomerOrderRemain> selectLatestOrdersByParams(Map<String, Object> params)
			throws ServiceException {
		// TODO Auto-generated method stub
		return customerOrderRemainMapper.selectLatestOrdersByParams(params);
	}
}