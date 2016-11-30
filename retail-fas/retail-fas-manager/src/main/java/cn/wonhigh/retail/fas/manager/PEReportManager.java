package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.PEAPDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;


/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-25 11:34:16
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
public interface PEReportManager {

	int findAPAgingCount(Map<String, Object> params) throws ManagerException;

	List<PEAPDto> findAPAgingByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ManagerException;

	List<PEAPDto> findAPAgingFooter(
			Map<String, Object> params)throws ManagerException;

	int findAPPlanCount(Map<String, Object> params)throws ManagerException;

	List<Map<String, Object>> findAPPlanByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ManagerException;

	List<Map<String, Object>> findAPPlanFooter(
			Map<String, Object> params)throws ManagerException;

	List<Map<String, Object>> findAPPlanColumn(Map<String, Object> params)throws ManagerException;

	int findReportSendCount(Map<String, Object> params)throws ManagerException;

	List<PEAPDto> findReportSendByPage(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params)throws ManagerException;

	List<PEAPDto> findReportSendFooter(Map<String, Object> params)throws ManagerException;
	
	
	
}