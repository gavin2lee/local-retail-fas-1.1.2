package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.InitialAmount;
import cn.wonhigh.retail.fas.service.InitialAmountService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-18 16:38:06
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
@Service("initialAmountManager")
class InitialAmountManagerImpl extends BaseCrudManagerImpl implements InitialAmountManager {
    @Resource
    private InitialAmountService initialAmountService;

    @Override
    public BaseCrudService init() {
        return initialAmountService;
    }

	@Override
	public List<InitialAmount> findFooter(Map<String, Object> params)
			throws ManagerException {
		try {
			return initialAmountService.findFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}