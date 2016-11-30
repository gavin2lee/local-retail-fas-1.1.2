package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.Company;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-10-22 15:09:15
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
public interface CompanyMapper extends BaseCrudMapper {
	
	String findCompanyNameByNo(String companyNo);
	
	/**
	 * 根据店铺获取结算公司信息
	 * @param params
	 * @return
	 */
	List<Company> getCompanyByShopNo(@Param("params")Map<String, Object> params);

	List<Company> findAllCompanyWithoutPermission();
	
	/**
	 * 根据店铺编号及日期获取对应结算公司信息
	 * @param params
	 * @return
	 */
	Company getCompanyByShopNoWithDate(@Param("params")Map<String, Object> params);
	
}