package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.SettleNewStyleDto;
import cn.wonhigh.retail.fas.common.model.SettleNewStyleDtl;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-26 15:42:01
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
public interface SettleNewStyleManager extends BaseCrudManager {
	
	int findRelationCount(Map<String, Object> params) throws ManagerException;

	List<SettleNewStyleDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;
	
	int save(SettleNewStyleDto dto, Map<CommonOperatorEnum, List<SettleNewStyleDtl>> params) 
			throws ManagerException;

	int delete(List<SettleNewStyleDto> list) throws ManagerException;
}