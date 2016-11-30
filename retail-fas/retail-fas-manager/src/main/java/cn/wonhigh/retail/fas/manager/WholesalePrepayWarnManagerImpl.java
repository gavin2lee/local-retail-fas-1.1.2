package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.WholesalePrepayWarn;
import cn.wonhigh.retail.fas.service.WholesalePrepayWarnService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-23 11:02:19
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
@Service("wholesalePrepayWarnManager")
class WholesalePrepayWarnManagerImpl extends BaseCrudManagerImpl implements WholesalePrepayWarnManager {
	
    @Resource
    private WholesalePrepayWarnService wholesalePrepayWarnService;

    @Override
    public BaseCrudService init() {
        return wholesalePrepayWarnService;
    }
    
    /**
	 * 查询保证金额
	 * @param params 查询参数
	 * @return Map<String, BigDecimal>
	 * @throws ManagerException 异常
	 */
	@Override
	public Map<String, BigDecimal> selectMarginAmount(Map<String, Object> params)
			throws ManagerException {
		try {
			return wholesalePrepayWarnService.selectMarginAmount(params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<WholesalePrepayWarn> selectReversal(Map<String, Object> params)
			throws ManagerException {
		try {
			return wholesalePrepayWarnService.selectReversal(params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}