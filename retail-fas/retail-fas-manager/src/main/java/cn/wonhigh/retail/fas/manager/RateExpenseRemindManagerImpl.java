package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.service.RateExpenseRemindService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
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
@Service("rateExpenseRemindManager")
class RateExpenseRemindManagerImpl extends BaseCrudManagerImpl implements RateExpenseRemindManager {
    @Resource
    private RateExpenseRemindService rateExpenseRemindService;

    @Override
    public BaseCrudService init() {
        return rateExpenseRemindService;
    }

	@Override
	public int selectTwoMonthsInvalidCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return rateExpenseRemindService.selectTwoMonthsInvalidCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<ShopBalanceDate> selectTwoMonthsInvalidByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return rateExpenseRemindService.selectTwoMonthsInvalidByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}