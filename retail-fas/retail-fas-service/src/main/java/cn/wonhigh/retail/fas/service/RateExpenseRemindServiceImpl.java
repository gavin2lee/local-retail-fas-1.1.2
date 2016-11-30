package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.dal.database.RateExpenseRemindMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-27 11:56:41
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
@Service("rateExpenseRemindService")
class RateExpenseRemindServiceImpl extends BaseCrudServiceImpl implements RateExpenseRemindService {
    @Resource
    private RateExpenseRemindMapper rateExpenseRemindMapper;

    @Override
    public BaseCrudMapper init() {
        return rateExpenseRemindMapper;
    }

	@Override
	public int selectTwoMonthsInvalidCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return rateExpenseRemindMapper.selectTwoMonthsInvalidCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<ShopBalanceDate> selectTwoMonthsInvalidByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return rateExpenseRemindMapper.selectTwoMonthsInvalidByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}