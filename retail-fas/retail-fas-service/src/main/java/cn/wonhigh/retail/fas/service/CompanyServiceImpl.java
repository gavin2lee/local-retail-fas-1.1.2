package cn.wonhigh.retail.fas.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.dal.database.CompanyMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 13:52:36
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
@Service("companyService")
class CompanyServiceImpl extends BaseCrudServiceImpl implements CompanyService {
    @Resource
    private CompanyMapper companyMapper;

    @Override
    public BaseCrudMapper init() {
        return companyMapper;
    }

	@Override
	public String findCompanyNameByNo(String companyNo) throws ServiceException {
		return companyMapper.findCompanyNameByNo(companyNo);
	}

	@Override
	public List<Company> getCompanyByShopNo(Map<String, Object> params) {
		return companyMapper.getCompanyByShopNo(params);
	}

	@Override
	public List<Company> findAllCompanyWithoutPermission()
			throws ServiceException {
		try {
			return companyMapper.findAllCompanyWithoutPermission();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Company getCompanyByShopNoWithDate(String shopNo, Date date) throws ServiceException {
		
		if(shopNo == null || date == null) return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", shopNo);
		params.put("date", date);
		return companyMapper.getCompanyByShopNoWithDate(params);
	}
}