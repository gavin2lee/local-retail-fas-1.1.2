package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.SettleCategoryDto;
import cn.wonhigh.retail.fas.common.model.SettleCategoryDtl;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-22 14:57:26
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
public interface SettleCategoryManager extends BaseCrudManager {
	
	int findRelationCount(Map<String, Object> params) throws ManagerException;

	List<SettleCategoryDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;
	
	int save(SettleCategoryDto dto, Map<CommonOperatorEnum, List<SettleCategoryDtl>> params) 
			throws ManagerException;

	int delete(List<SettleCategoryDto> list) throws ManagerException;
}