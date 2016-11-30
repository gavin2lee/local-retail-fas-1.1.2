package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.PaySaleCheck;
import cn.wonhigh.retail.fas.dal.database.PaySaleCheckMapper;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-18 10:55:57
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
@Service("paySaleCheckService")
class PaySaleCheckServiceImpl extends BaseCrudServiceImpl implements PaySaleCheckService {
    @Resource
    private PaySaleCheckMapper paySaleCheckMapper;

    @Override
    public BaseCrudMapper init() {
        return paySaleCheckMapper;
    }

	@Override
	public PaySaleCheck findPaySaleCheckCount(Map<String, Object> params)
			throws ManagerException {
		return paySaleCheckMapper.findPaySaleCheckCount(params);
	}

	@Override
	public List<PaySaleCheck> findPaySaleCheckList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ManagerException {
		return paySaleCheckMapper.findPaySaleCheckList(page, orderByField, orderBy, params);
	}

	@Override
	@Transactional(rollbackFor=ServiceException.class)
	public void updateData(String id, BigDecimal poundage, BigDecimal rate) {
		paySaleCheckMapper.updateData(id, poundage, rate);
	}
}