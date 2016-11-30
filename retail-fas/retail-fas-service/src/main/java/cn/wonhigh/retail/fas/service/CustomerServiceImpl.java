package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.CustomerMultiDataSourceDto;
import cn.wonhigh.retail.fas.dal.database.CustomerMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 13:52:36
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
@Service("customerService")
class CustomerServiceImpl extends BaseCrudServiceImpl implements CustomerService {
  
	@Resource
    private CustomerMapper customerMapper;

    @Override
    public BaseCrudMapper init() {
        return customerMapper;
    }

    /**
	 * 多数据源查询客户数据
	 * @param page 分页对象
	 * @param sortColumn 排序字段
	 * @param sortOrder 排序条件
	 * @param params 查询参数
	 * @return List<CustomerMultiDataSourceDto>
	 * @throws ServiceException 异常
	 */
	@Override
	public List<CustomerMultiDataSourceDto> queryMultiDataSrouce(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try {
			return customerMapper.queryMultiDataSrouce(page, sortColumn, sortOrder, params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 多数据源查询客户数量
	 * @param params 查询参数
	 * @return 数量
	 * @throws ServiceException 异常
	 */
	@Override
	public int queryMultiDataSrouceCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return customerMapper.queryMultiDataSrouceCount(params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}