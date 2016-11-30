package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CashCheck;
import cn.wonhigh.retail.fas.common.model.CreditCardCheck;
import cn.wonhigh.retail.fas.dal.database.CashCheckMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialException;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 17:36:27
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
@Service("cashCheckService")
class CashCheckServiceImpl extends BaseCrudServiceImpl implements CashCheckService {
    @Resource
    private CashCheckMapper cashCheckMapper;

    @Override
    public BaseCrudMapper init() {
        return cashCheckMapper;
    }

	@Override
	public CashCheck findShopSaleDetailCount(Map<String, Object> params) throws ServiceException {
		try {
			return cashCheckMapper.findShopSaleDetailCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<CashCheck> findShopSaleDetailList(SimplePage page,
			String orderBy, String orderByField, Map<String, Object> params) throws ServiceException {
		try {
			return cashCheckMapper.findShopSaleDetailList(page, orderBy, orderByField, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<CashCheck> queryCashCheckDetail(Map<String, Object> params) throws ServiceException {
		try {
			return cashCheckMapper.queryCashCheckDetail(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BigDecimal queryActualIncomeAmount(Map<String, Object> params) throws ServiceException {
		try {
			return cashCheckMapper.queryActualIncomeAmount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BigDecimal querySystemIncomeAmount(Map<String, Object> params)
			throws ServiceException {
		try {
			return cashCheckMapper.querySystemIncomeAmount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

}