package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.CustomerMultiDataSourceDto;
import cn.wonhigh.retail.fas.service.CustomerService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

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
@Service("customerManager")
class CustomerManagerImpl extends BaseCrudManagerImpl implements CustomerManager {
	
    @Resource
    private CustomerService customerService;

    @Override
    public BaseCrudService init() {
        return customerService;
    }

    /**
	 * 多数据源查询客户数据
	 * @param page 分页对象
	 * @param sortColumn 排序字段
	 * @param sortOrder 排序条件
	 * @param params 查询参数
	 * @return List<CustomerMultiDataSourceDto>
	 * @throws ManagerException 异常
	 */
	@Override
	public List<CustomerMultiDataSourceDto> queryMultiDataSrouce(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return customerService.queryMultiDataSrouce(page, sortColumn, sortOrder, params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 多数据源查询客户数量
	 * @param params 查询参数
	 * @return 数量
	 * @throws ManagerException 异常
	 */
	@Override
	public int queryMultiDataSrouceCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return customerService.queryMultiDataSrouceCount(params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}