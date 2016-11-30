package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.SettlePathDto;
import cn.wonhigh.retail.fas.common.dto.SettlePathQueryExportDto;
import cn.wonhigh.retail.fas.common.model.BillSplitSettlePathDtl;
import cn.wonhigh.retail.fas.common.model.SettlePath;
import cn.wonhigh.retail.fas.common.model.SettlePathDtlQueryVo;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface SettlePathService extends BaseCrudService {

	BillSplitSettlePathDtl querySettlePathDtl(SettlePathDtlQueryVo vo);
	
	int updateStatus(Map<String, Object> params) throws ServiceException;

	int doAudit(SettlePath model) throws ServiceException;

	int doStatus(SettlePathDto model) throws ServiceException;
	
	int findRelationCount(Map<String, Object> params) throws ServiceException;

	List<SettlePathQueryExportDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException;

}