package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ReportDto;
import cn.wonhigh.retail.fas.common.dto.ReportGatherDto;

import com.yougou.logistics.base.common.utils.SimplePage;


/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-28 13:49:07
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
public interface ReportMapper {

	List<ReportDto> findRowByPage(@Param("page")SimplePage page, @Param("params")Map<String,Object> params);

	List<ReportDto> findColumnByPage(@Param("page")SimplePage page, @Param("params")Map<String,Object> params);

	List<ReportDto> findQtyByPage(@Param("page")SimplePage page, @Param("params")Map<String,Object> params);

	int findReportCount(@Param("params")Map<String,Object> params);
	
	List<ReportDto> findReportFooter(@Param("page")SimplePage page, @Param("params")Map<String,Object> params);

	int findReportGatherCount(@Param("params")Map<String,Object> params);

	List<ReportGatherDto> findReportGatherByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	int findReportDetailCount(@Param("params")Map<String,Object> params);

	List<ReportDto> findReportDetailByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	int findReportGatherAreaCount(@Param("params")Map<String,Object> params);

	List<ReportGatherDto> findReportGatherAreaByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
	
	int findReportBusinessCount(@Param("params")Map<String,Object> params);
	
	List<ReportDto> findReportBusinessByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	int findReportFinanceCount(@Param("params")Map<String,Object> params);

	List<ReportDto> findReportFinanceByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	List<ReportDto> findReportFinanceFooter(@Param("params")Map<String,Object> params);

	List<ReportDto> findReportDetailFooter(@Param("params")Map<String,Object> params);

	int findReportGatherFinanceCount(@Param("params")Map<String,Object> params);

	List<ReportGatherDto> findReportGatherFinanceByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	int findReportTotalCount(@Param("params")Map<String,Object> params);

	List<Map<String, Object>> findReportTotalByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	List<Map<String, Object>> findReportToalFooter(@Param("params")Map<String,Object> params);

	List<ReportGatherDto> findSumBalanceList(@Param("params")Map<String,Object> params);
	
	List<ReportGatherDto> findSumBalanceFooter(@Param("params")Map<String,Object> params);

}