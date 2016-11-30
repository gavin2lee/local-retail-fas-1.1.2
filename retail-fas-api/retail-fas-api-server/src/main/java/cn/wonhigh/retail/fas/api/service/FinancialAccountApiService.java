package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.FinancialAccount;

import com.yougou.logistics.base.common.exception.ServiceException;

/**
 * 公司账套
 * @author yang.y
 * @date  2014-12-05 16:36:16
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
public interface FinancialAccountApiService {
	
	FinancialAccount findByCompanyNo(String companyNo) throws ServiceException;

	List<FinancialAccount> findByParams(Map<String, Object> params) throws ServiceException;
	

	String selectLeadRoleCompanyNos()throws ServiceException;
	
}