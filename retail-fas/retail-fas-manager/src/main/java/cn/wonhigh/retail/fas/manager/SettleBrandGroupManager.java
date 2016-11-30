package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.SettleBrandGroupDto;
import cn.wonhigh.retail.fas.common.model.SettleBrandGroupRel;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 品牌组
 * @author yang.y
 * @date  2014-08-26 10:36:46
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
public interface SettleBrandGroupManager extends BaseCrudManager {
	
	int findRelationCount(Map<String, Object> params) throws ManagerException;

	List<SettleBrandGroupDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;
	
	int save(SettleBrandGroupDto dto, Map<CommonOperatorEnum, List<SettleBrandGroupRel>> params) 
			throws ManagerException;

	int delete(List<SettleBrandGroupDto> list) throws ManagerException;
}