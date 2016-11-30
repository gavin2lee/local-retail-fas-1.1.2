package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.dal.database.BillInvCostAdjustMapper;
import cn.wonhigh.retail.fas.dal.database.PeriodBalanceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-31 16:10:13
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
@Service("billInvCostAdjustService")
class BillInvCostAdjustServiceImpl extends BaseCrudServiceImpl implements BillInvCostAdjustService {
	@Resource
    private BillInvCostAdjustMapper billInvCostAdjustMapper;
	
	@Resource
	private PeriodBalanceMapper periodBalanceMapper;

    @Override
    public BaseCrudMapper init() {
        return billInvCostAdjustMapper;
    }

	@Override
	public int findCompanyCount(Map<String, Object> params) throws ServiceException {
		try {
			return billInvCostAdjustMapper.findCompanyCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public List<Company> findCompanyByPage(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		try {
			return billInvCostAdjustMapper.findCompanyByPage(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public List<PeriodBalance> findPeriodBalance(Map<String, Object> params)
			throws ServiceException {
		try {
			SimplePage page = new SimplePage(1, 1, 1);
			return periodBalanceMapper.queryCompanyPeriodByPage(page, null, null, params, null);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}
	
	@Override
	public PeriodBalance findPeriodBalanceBLK(Map<String, Object> params)
			throws ServiceException {
		try {
			return periodBalanceMapper.getPeriodBalanceBLK(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	/**
	 * 获取开关控制
	 * @return Map
	 * @throws ServiceException 异常
	 */
	@Override
	public Map<String, String> getControllerFlag() throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("controllerFlag", "false");
		return map;
	}

	/**
	 * 通过公司编码、货号、年、月汇总差额
	 * @param params 限制参数
	 * @return BigDecimal
	 * @throws ServiceException 异常
	 */
	@Override
	public BigDecimal sumDiverAmount(Map<String, Object> params)
			throws ServiceException {
		try {
			return billInvCostAdjustMapper.sumDiverAmount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}
}