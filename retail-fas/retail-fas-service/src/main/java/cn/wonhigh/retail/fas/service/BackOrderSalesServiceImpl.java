package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BackOrderSales;
import cn.wonhigh.retail.fas.dal.database.BackOrderSalesMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-09-01 18:15:52
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
@Service("backOrderSalesService")
class BackOrderSalesServiceImpl extends BaseCrudServiceImpl implements BackOrderSalesService {
    @Resource
    private BackOrderSalesMapper backOrderSalesMapper;

    @Override
    public BaseCrudMapper init() {
        return backOrderSalesMapper;
    }

	@Override
	public int selectCompanyOwerGuestCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return backOrderSalesMapper.selectCompanyOwerGuestCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BackOrderSales> selectCompanyOwerGuestByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return backOrderSalesMapper.selectCompanyOwerGuestByPage(page,sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int batchInsertCompanyOwerGuest(List<BackOrderSales> backOrderSales)
			throws ServiceException {
		try {
			return backOrderSalesMapper.batchInsertCompanyOwerGuest(backOrderSales);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int deleteCompanyOwerGuestCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return backOrderSalesMapper.deleteCompanyOwerGuestCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}