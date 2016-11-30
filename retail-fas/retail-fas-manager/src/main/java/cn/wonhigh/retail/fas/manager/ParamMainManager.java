package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ParamMainDto;
import cn.wonhigh.retail.fas.common.model.ParamDtl;
import cn.wonhigh.retail.fas.common.model.ParamMain;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-21 10:32:05
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
public interface ParamMainManager extends BaseCrudManager {
	int save(ParamMain dto, Map<CommonOperatorEnum, List<ParamDtl>> params) 
			throws ManagerException;

	/**
	 * @param params
	 * @return
	 */
	int findRelationCount(Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<ParamMainDto> findRelationByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * @param list
	 * @throws ServiceException 
	 */
	void delete(List<ParamMainDto> list) throws ServiceException;
}