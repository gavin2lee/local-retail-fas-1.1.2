package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CompanyPeriodSalesSum;
import cn.wonhigh.retail.fas.dal.database.CompanyPeriodSalesSumMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-15 16:12:30
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
@Service("companyPeriodSalesSumService")
class CompanyPeriodSalesSumServiceImpl extends BaseCrudServiceImpl implements CompanyPeriodSalesSumService {

	@Resource
    private CompanyPeriodSalesSumMapper companyPeriodSalesSumMapper;

    @Override
    public BaseCrudMapper init() {
        return companyPeriodSalesSumMapper;
    }

	@Override
	public int deleteCompanySalesSum(Map<String, Object> params)
			throws ServiceException {
		try {
			return companyPeriodSalesSumMapper.deleteCompanySalesSum(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int transferCompanySalesSum(Map<String, Object> params)
			throws ServiceException {
		try {
			return companyPeriodSalesSumMapper.transferCompanySalesSum(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int countTransferCompanySalesSum(Map<String, Object> params)
			throws ServiceException {
		try {
			return companyPeriodSalesSumMapper.countTransferCompanySalesSum(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<CompanyPeriodSalesSum> getTransferCompanySalesSumByPage(
			SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		try {
			return companyPeriodSalesSumMapper.getTransferCompanySalesSumByPage(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int batchInsertPeriodSalesSum(
			List<CompanyPeriodSalesSum> backOrderSales) throws ServiceException {
		try {
			return companyPeriodSalesSumMapper.batchInsertPeriodSalesSum(backOrderSales);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}