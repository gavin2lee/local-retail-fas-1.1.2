package cn.wonhigh.retail.fas.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.Company;

import com.yougou.logistics.base.common.exception.ServiceException;
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
public interface CompanyService extends BaseCrudService {
	
	String findCompanyNameByNo(String companyNo) throws ServiceException;
	
	/**
	 * 根据店铺编码获取结算公司信息
	 * @param params
	 * @return
	 */
	List<Company> getCompanyByShopNo(@Param("params")Map<String, Object> params);

	List<Company> findAllCompanyWithoutPermission() throws ServiceException;
	
	/**
	 * 根据店铺编号及日期获取对应结算公司信息
	 * @param shopNo
	 * @param date
	 * @return
	 */
	Company getCompanyByShopNoWithDate(String shopNo, Date date) throws ServiceException;
	
}