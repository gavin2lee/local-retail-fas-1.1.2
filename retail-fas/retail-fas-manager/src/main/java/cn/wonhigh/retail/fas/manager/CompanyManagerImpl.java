package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.service.CompanyService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

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
@Service("companyManager")
class CompanyManagerImpl extends BaseCrudManagerImpl implements CompanyManager {
    @Resource
    private CompanyService companyService;

    @Override
    public BaseCrudService init() {
        return companyService;
    }

	@Override
	public List<Company> getCompanyByShopNo(String shopNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("shopNo", shopNo);
		return companyService.getCompanyByShopNo(param);
	}

	@Override
	public List<Company> findAllCompanyWithoutPermission()
			throws ManagerException {
		try {
			return companyService.findAllCompanyWithoutPermission();
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(),e);
		}
	}

	@Override
	public Company getCompany(String companyNo) throws ManagerException {
		 Company c = new Company();
		 c.setCompanyNo(companyNo);
		 return this.findById(c);
	}
}