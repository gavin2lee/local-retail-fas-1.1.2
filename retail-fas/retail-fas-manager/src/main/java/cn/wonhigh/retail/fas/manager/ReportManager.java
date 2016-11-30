package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ReportDto;
import cn.wonhigh.retail.fas.common.dto.ReportFinanceDto;
import cn.wonhigh.retail.fas.common.dto.ReportGatherDto;
import cn.wonhigh.retail.fas.common.dto.ReportInventoryDto;
import cn.wonhigh.retail.fas.common.dto.ReportTransferDto;

import com.google.common.base.Function;
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
public interface ReportManager {

	int findReportCount(Map<String, Object> params)throws Exception;

	List<Map<String, Object>> findReportByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws Exception;

	List<Map<String, Object>> findReportFooter(SimplePage page, Map<String, Object> params)throws Exception;

	List<ReportDto> findColumn(SimplePage page, Map<String, Object> params)throws Exception;

	int findReportGatherCount(Map<String, Object> params)throws Exception;

	List<ReportGatherDto> findReportGatherByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws Exception;

	int findReportDetailCount(Map<String, Object> params)throws Exception;

	List<ReportDto> findReportDetailByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws Exception;

	int findReportGatherAreaCount(Map<String, Object> params)throws Exception;

	List<ReportGatherDto> findReportGatherAreaByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws Exception;
	
	int findReportBusinessCount(Map<String, Object> params)throws Exception;
	
	List<ReportDto> findReportBusinessByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws Exception;

	int findReportFinanceCount(Map<String, Object> params)throws Exception;

	List<ReportDto> findReportFinanceByPage(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params)throws Exception;

	List<ReportDto> findReportFinanceFooter(Map<String, Object> params)throws Exception;

	List<ReportDto> findReportDetailFooter(Map<String, Object> params)throws Exception;

	List<Object> queryDtlList(Map<String, Object> params)throws Exception;

	int findReportInventoryCount(Map<String, Object> params)throws Exception;

	List<ReportInventoryDto> findReportInventoryByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws Exception;

	List<ReportInventoryDto> findReportInventoryFooter(
			Map<String, Object> params)throws Exception;

	int findReportAllFinanceCount(Map<String, Object> params)throws Exception;

	List<ReportFinanceDto> findReportAllFinanceByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws Exception;

	List<ReportFinanceDto> findReportAllFinanceFooter(Map<String, Object> params)throws Exception;

	int findReportTransferCount(Map<String, Object> params)throws Exception;

	List<ReportTransferDto> findReportTransferByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws Exception;

	List<ReportTransferDto> findReportTransferFooter(Map<String, Object> params)throws Exception;

	void findReportExport(SimplePage page, Map<String, Object> params,
			Function<Object, Boolean> handler)throws Exception;

}