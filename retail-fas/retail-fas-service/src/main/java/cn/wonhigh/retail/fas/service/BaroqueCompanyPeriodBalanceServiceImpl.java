package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BLKPeriodBalance;
import cn.wonhigh.retail.fas.dal.database.BaroqueCompanyPeriodBalanceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("baroqueCompanyPeriodBalanceService")
class BaroqueCompanyPeriodBalanceServiceImpl extends BaseCrudServiceImpl implements BaroqueCompanyPeriodBalanceService {

	@Resource
	private BaroqueCompanyPeriodBalanceMapper baroqueCompanyPeriodBalanceMapper; 
	
	@Override
	public List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceByPage(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException {
		try {
			return this.baroqueCompanyPeriodBalanceMapper.getBaroqueCompanyPeriodBalanceByPage(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceFooter(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException {
		try {
			return this.baroqueCompanyPeriodBalanceMapper.getBaroqueCompanyPeriodBalanceFooter(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceFooterItem(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException {
		try {
			return this.baroqueCompanyPeriodBalanceMapper.getBaroqueCompanyPeriodBalanceFooterItem(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public Integer getBaroqueCompanyPeriodBalanceCount(Map<String, Object> params) throws ServiceException {
		try {
			return this.baroqueCompanyPeriodBalanceMapper.getBaroqueCompanyPeriodBalanceCount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public BaseCrudMapper init() {
		return baroqueCompanyPeriodBalanceMapper;
	}

	@Override
	public List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceByItemNO(
			SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			return this.baroqueCompanyPeriodBalanceMapper.getBaroqueCompanyPeriodBalanceByItemNo(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Integer getBaroqueCompanyPeriodBalanceCountItem(
			Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			return this.baroqueCompanyPeriodBalanceMapper.getBaroqueCompanyPeriodBalanceCountItem(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


}
