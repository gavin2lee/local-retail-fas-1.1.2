package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillInvCostAdjustDtl;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-31 16:10:14
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
public interface BillInvCostAdjustDtlService extends BaseCrudService {
	
	int deleteByBillNo(String billNo) throws ServiceException;
	
	BillInvCostAdjustDtl findLastJoinHeaderByParams(Map<String, Object> params) throws ServiceException;
	
	public List<BillInvCostAdjustDtl> findListBLK(SimplePage page, String orderByField,String orderBy, Map<String, Object> params) throws ServiceException;

	public int findListBLKcount(Map<String, Object> params) throws ServiceException;
	
}