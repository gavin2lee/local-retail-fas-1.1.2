package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.MemberOrderDetail;
import cn.wonhigh.retail.fas.common.model.MemberOrderSummary;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface OrderDtlService extends BaseCrudService {

	public int selectOrderSummaryCount(Map<String, Object> params) throws ServiceException;

	public List<MemberOrderSummary> selectOrderSummaryByPage(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException;

	public List<MemberOrderSummary> selectOrderSummaryOperateByPage(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException;

	public int financeConfirm(Map<String, Object> params) throws ServiceException;

	public int financeAntiConfirm(Map<String, Object> params) throws ServiceException;

	public List<MemberOrderDetail> selectOrderMemberList(Map<String, Object> params) throws ServiceException;
}