package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.FinancialAccountApiMapper;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;

import com.yougou.logistics.base.common.exception.ServiceException;

/**
 * 请写出类的用途 
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
@Service("financialAccountApiService")
class FinancialAccountApiServiceImpl implements FinancialAccountApiService {
    
	@Resource
    private FinancialAccountApiMapper financialAccountApiMapper;

	@Override
	public FinancialAccount findByCompanyNo(String companyNo) throws ServiceException {
		try {
			List<FinancialAccount> list = financialAccountApiMapper.findByCompanyNo(companyNo);
			if(list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<FinancialAccount> findByParams(Map<String, Object> params)
			throws ServiceException {
		try {
			return financialAccountApiMapper.selectByParams(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public String selectLeadRoleCompanyNos() 
			throws ServiceException {
		try {
			return financialAccountApiMapper.selectLeadRoleCompanyNos();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}