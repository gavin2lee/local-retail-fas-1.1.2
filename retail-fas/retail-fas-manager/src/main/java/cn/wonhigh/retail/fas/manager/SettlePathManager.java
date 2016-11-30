package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.SettlePathDto;
import cn.wonhigh.retail.fas.common.dto.SettlePathQueryExportDto;
import cn.wonhigh.retail.fas.common.model.SettlePathBrandRel;
import cn.wonhigh.retail.fas.common.model.SettlePathDtl;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
public interface SettlePathManager extends BaseCrudManager {
	
	/**
	 * 保存
	 * @param dto SettlePathDto
	 * @param params Map<CommonOperatorEnum, List<SettlePathDtl>>
	 * @param brandList List<SettlePathBrandRel>
	 * @return 保存的数量
	 * @throws ManagerException 异常
	 */
	int save(SettlePathDto dto, Map<CommonOperatorEnum, List<SettlePathDtl>> params, 
			List<SettlePathBrandRel> brandList) 
			throws ManagerException;
	
	/**
	 * 修改状态
	 * @param params Map<String, Object>
	 * @return 修改的数量
	 * @throws ManagerException 异常
	 */
	int updateStatus(Map<String, Object> params) throws ManagerException;

	/**
	 * 审核结算路径
	 * @param oList List<SettlePathDto>
	 * @return 修改的数量
	 * @throws ManagerException 异常
	 */
	int doAudit(List<SettlePathDto> oList) throws ManagerException;

	/**
	 * 修改结算路径状态
	 * @param oList List<SettlePathDto>
	 * @return 修改的数量
	 * @throws ManagerException 异常
	 */
	int doStatus(List<SettlePathDto> oList) throws ManagerException;

	int findRelationCount(Map<String, Object> params) throws ManagerException;

	List<SettlePathQueryExportDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;

	/**
	 * 删除操作
	 * @param list 待删除的数据集合
	 * @return 删除的数量
	 * @throws ManagerException 异常
	 */
	int delete(List<SettlePathDto> list) throws ManagerException;
	
}