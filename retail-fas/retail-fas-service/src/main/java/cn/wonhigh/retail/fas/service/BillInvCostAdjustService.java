package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillInvCostAdjustDtl;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface BillInvCostAdjustService extends BaseCrudService {
	
	int findCompanyCount(Map<String, Object> params) throws ServiceException;

	List<Company> findCompanyByPage(SimplePage page, String orderByField, String orderBy, Map<String, Object> params)
		throws ServiceException;

	List<PeriodBalance> findPeriodBalance(Map<String, Object> params) throws ServiceException;

	Map<String, String> getControllerFlag() throws ServiceException;
	
	BigDecimal sumDiverAmount(@Param("params")Map<String, Object> params) throws ServiceException;

	PeriodBalance findPeriodBalanceBLK(Map<String, Object> params)throws ServiceException;

}