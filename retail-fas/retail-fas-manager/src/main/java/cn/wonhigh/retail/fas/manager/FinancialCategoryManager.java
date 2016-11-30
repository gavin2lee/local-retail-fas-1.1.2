package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.FinancialCategoryCommonDto;
import cn.wonhigh.retail.fas.common.dto.FinancialCategoryDto;
import cn.wonhigh.retail.fas.common.model.FinancialCategory;
import cn.wonhigh.retail.fas.common.model.FinancialCategoryDtl;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface FinancialCategoryManager extends BaseCrudManager {
	
	int findRelationCount(Map<String, Object> params) throws ManagerException;

	List<FinancialCategoryDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;
	
	int save(FinancialCategoryDto dto, Map<CommonOperatorEnum, List<FinancialCategoryDtl>> params) 
			throws ManagerException;

	int delete(List<FinancialCategoryDto> list) throws ManagerException;
	
	List<FinancialCategoryCommonDto> getAllCateInfo(Map<String, Object> params);
	
	/**
	 * 根据公司及大类编号查询财务大类信息
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	public List<FinancialCategory> findCateInfoByCateNo(Map<String, Object> params);
}