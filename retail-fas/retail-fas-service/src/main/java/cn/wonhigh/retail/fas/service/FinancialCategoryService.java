package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;


import cn.wonhigh.retail.fas.common.dto.FinancialCategoryCommonDto;
import cn.wonhigh.retail.fas.common.dto.FinancialCategoryDto;
import cn.wonhigh.retail.fas.common.model.FinancialCategory;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 财务大类 
 * @author Administrator
 * @date  2014-12-23 10:38:39
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
public interface FinancialCategoryService extends BaseCrudService {
	
	int findRelationCount(Map<String, Object> params) throws ServiceException;

	List<FinancialCategoryDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException;
	
	List<FinancialCategory> selectCateInfoByCateNo(Map<String, Object> params);
	
	/**
	 * 获取财务大类和商品一级大类信息
	 * @param params
	 * @return
	 */
	List<FinancialCategoryCommonDto> getAllCateInfo(Map<String, Object> params);
}