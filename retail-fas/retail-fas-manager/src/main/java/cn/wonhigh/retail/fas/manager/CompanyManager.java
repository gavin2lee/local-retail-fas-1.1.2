package cn.wonhigh.retail.fas.manager;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.Company;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface CompanyManager extends BaseCrudManager {
	
	/**
	 * 根据店铺编码获取结算公司信息
	 * @param shopNo
	 * @return
	 */
	public List<Company> getCompanyByShopNo(String shopNo);
	
	public Company getCompany(String companyNo) throws ManagerException;

	public List<Company> findAllCompanyWithoutPermission() throws ManagerException;
	
}