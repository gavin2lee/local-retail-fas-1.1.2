package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CreditCardCheck;
import cn.wonhigh.retail.fas.dal.database.CreditCardCheckMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
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
@Service("creditCardCheckService")
class CreditCardCheckServiceImpl extends BaseCrudServiceImpl implements CreditCardCheckService {
    @Resource
    private CreditCardCheckMapper creditCardCheckMapper;

    @Override
    public BaseCrudMapper init() {
        return creditCardCheckMapper;
    }
    
	@Override
	public CreditCardCheck findCreditCardCheckCount(Map<String, Object> params) throws ServiceException {
		try {
			return creditCardCheckMapper.findCreditCardCheckCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public List<CreditCardCheck> findCreditCardCheckList(SimplePage page,
			String orderBy, String orderByField, Map<String, Object> params) throws ServiceException {
		try {
			return creditCardCheckMapper.findCreditCardCheckList(page, orderBy, orderByField, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public List<CreditCardCheck> getShopSaleDetail(Map<String, Object> params) throws ServiceException {
		try {
			return creditCardCheckMapper.getShopSaleDetail(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public BigDecimal getOnlieIncomeAmount(Map<String, Object> params) throws ServiceException {
		try {
			return creditCardCheckMapper.getOnlieIncomeAmount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}