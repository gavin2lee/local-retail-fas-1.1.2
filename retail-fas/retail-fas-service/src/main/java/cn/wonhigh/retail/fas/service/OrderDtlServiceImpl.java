package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.MemberOrderDetail;
import cn.wonhigh.retail.fas.common.model.MemberOrderSummary;
import cn.wonhigh.retail.fas.dal.database.OrderDtlMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-23 15:21:34
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
@Service("orderDtlService")
class OrderDtlServiceImpl extends BaseCrudServiceImpl implements OrderDtlService {

	@Resource
	private OrderDtlMapper orderDtlMapper;

	@Override
	public BaseCrudMapper init() {
		return orderDtlMapper;
	}

	@Override
	public int selectOrderSummaryCount(Map<String, Object> params) throws ServiceException {
		try {
			return orderDtlMapper.selectOrderSummaryCount(params);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}

	@Override
	public List<MemberOrderSummary> selectOrderSummaryByPage(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		try {
			return orderDtlMapper.selectOrderSummaryByPage(page, orderByField, orderBy, params, null);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}

	@Override
	public List<MemberOrderSummary> selectOrderSummaryOperateByPage(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException {
		try {
			return orderDtlMapper.selectOrderSummaryOperateByPage(page, orderByField, orderBy, params, null);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}

	@Override
	public int financeConfirm(Map<String, Object> params) throws ServiceException {
		try {
			return orderDtlMapper.financeConfirm(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int financeAntiConfirm(Map<String, Object> params) throws ServiceException {
		try {
			return orderDtlMapper.financeAntiConfirm(params);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}

	@Override
	public List<MemberOrderDetail> selectOrderMemberList(Map<String, Object> params) throws ServiceException {
		try {
			return orderDtlMapper.selectOrderMemberList(params);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}
}