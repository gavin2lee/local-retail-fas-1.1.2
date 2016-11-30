package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.DepositCashCumulativeDifference;
import cn.wonhigh.retail.fas.common.model.IndependShopDayReport;
import cn.wonhigh.retail.fas.dal.database.DepositCashCumulativeDifferenceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2015-04-22 09:29:45
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
@Service("depositCashCumulativeDifferenceService")
class DepositCashCumulativeDifferenceServiceImpl extends BaseCrudServiceImpl implements DepositCashCumulativeDifferenceService {
    @Resource
    private DepositCashCumulativeDifferenceMapper depositCashCumulativeDifferenceMapper;

    @Override
    public BaseCrudMapper init() {
        return depositCashCumulativeDifferenceMapper;
    }

	@Override
	public BigDecimal selectCumulativeDifferenceByShopNo(String shopNo)
			throws ServiceException {
		return depositCashCumulativeDifferenceMapper.selectCumulativeDifferenceByShopNo(shopNo);
	}

	@Override
	public List<IndependShopDayReport> findByPayName(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return depositCashCumulativeDifferenceMapper.findByPayName(page, orderByField, orderBy, params);
	}

	@Override
	public List<IndependShopDayReport> findByPayNameFromPOS(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return depositCashCumulativeDifferenceMapper.findByPayNameFromPOS(page, orderByField, orderBy, params);
	}

	@Override
	public List<IndependShopDayReport> findByPayNameOther(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return depositCashCumulativeDifferenceMapper.findByPayNameOther(page, orderByField, orderBy, params);
	}
}