package cn.wonhigh.retail.fas.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillInvCostAdjust;
import cn.wonhigh.retail.fas.common.model.BillInvCostAdjustDtl;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.common.model.SystemUser;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.dal.database.BillBuyBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BillInvCostAdjustDtlMapper;
import cn.wonhigh.retail.fas.dal.database.BillInvCostAdjustMapper;
import cn.wonhigh.retail.fas.dal.database.ItemCostMapper;
import cn.wonhigh.retail.fas.dal.database.PeriodBalanceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途
 * 
 * @author wangxy
 * @date 2015-12-31 16:10:14
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("billInvCostAdjustDtlService")
class BillInvCostAdjustDtlServiceImpl extends BaseCrudServiceImpl implements BillInvCostAdjustDtlService {
	@Resource
	private BillInvCostAdjustDtlMapper billInvCostAdjustDtlMapper;
	
	@Override
	public BaseCrudMapper init() {
		return billInvCostAdjustDtlMapper;
	}

	/**
	 * 通过表体的单据编码删除明细数据
	 * 
	 * @param billNo
	 *            单据编码
	 * @return 删除的记录数
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int deleteByBillNo(String billNo) throws ServiceException {
		try {
			return billInvCostAdjustDtlMapper.deleteByBillNo(billNo);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BillInvCostAdjustDtl findLastJoinHeaderByParams(
			Map<String, Object> params) throws ServiceException {
		try {
			return billInvCostAdjustDtlMapper
					.findLastJoinHeaderByParams(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillInvCostAdjustDtl> findListBLK(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ServiceException {
		try {
			return billInvCostAdjustDtlMapper.findListBLK(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int findListBLKcount(Map<String, Object> params) throws ServiceException {
		try {
			return billInvCostAdjustDtlMapper.findListBLKcount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

}