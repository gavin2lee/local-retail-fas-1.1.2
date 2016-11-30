package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ReportDto;
import cn.wonhigh.retail.fas.common.dto.ReportFinanceDto;
import cn.wonhigh.retail.fas.common.dto.ReportGatherDto;
import cn.wonhigh.retail.fas.common.dto.ReportInventoryDto;
import cn.wonhigh.retail.fas.common.dto.ReportTransferDto;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ServiceException;
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
public interface ReportService {

	int findReportCount(Map<String, Object> params)throws ServiceException;

	List<ReportDto> findReportFooter(SimplePage page, Map<String, Object> params)throws ServiceException;

	List<ReportDto> findColumnByPage(SimplePage page, Map<String, Object> params)throws ServiceException;

	List<ReportDto> findRowByPage(SimplePage page, Map<String, Object> params)throws ServiceException;

	List<ReportDto> findQtyByPage(SimplePage page, Map<String, Object> params)throws ServiceException;

	int findReportGatherCount(Map<String, Object> params)throws ServiceException;

	List<ReportGatherDto> findReportGatherByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;

	int findReportDetailCount(Map<String, Object> params)throws ServiceException;

	List<ReportDto> findReportDetailByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;

	int findReportGatherAreaCount(Map<String, Object> params)throws ServiceException;

	List<ReportGatherDto> findReportGatherAreaByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;
	
	List<ReportDto> findReportDetailFooter(Map<String, Object> params)throws ServiceException;
	
	int findReportBusinessCount(Map<String, Object> params)throws ServiceException;

	List<ReportDto> findReportBusinessByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;

	int findReportFinanceCount(Map<String, Object> params)throws ServiceException;

	List<ReportDto> findReportFinanceByPage(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params)throws ServiceException;

	List<ReportDto> findReportFinanceFooter(Map<String, Object> params)throws ServiceException;

	int findReportTotalCount(Map<String, Object> params)throws ServiceException;

	List<Map<String, Object>> findReportTotalByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;

	List<Map<String, Object>> findReportToalFooter(Map<String, Object> params)throws ServiceException;

	List<Object> queryDtlList(Map<String, Object> params)throws ServiceException;

	int findReportInventoryCount(Map<String, Object> params)throws ServiceException;

	List<ReportInventoryDto> findReportInventoryByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;

	List<ReportInventoryDto> findReportInventoryFooter(
			Map<String, Object> params)throws ServiceException;

	int findReportAllFinanceCount(Map<String, Object> params)throws ServiceException;

	List<ReportFinanceDto> findReportAllFinanceByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;

	List<ReportFinanceDto> findReportAllFinanceFooter(Map<String, Object> params)throws ServiceException;

	int findReportTransferCount(Map<String, Object> params)throws ServiceException;

	List<ReportTransferDto> findReportTransferByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;

	List<ReportTransferDto> findReportTransferFooter(Map<String, Object> params)throws ServiceException;

	void findReportExport(SimplePage page, Map<String, Object> params,
			Function<Object, Boolean> handler)throws ServiceException;

}