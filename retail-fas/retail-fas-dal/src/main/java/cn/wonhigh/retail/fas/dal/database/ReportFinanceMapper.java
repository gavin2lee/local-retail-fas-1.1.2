package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ReportFinanceDto;
import cn.wonhigh.retail.fas.common.dto.ReportInventoryDto;
import cn.wonhigh.retail.fas.common.dto.ReportTransferDto;

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
public interface ReportFinanceMapper {
	
	int findReportAllFinanceCount(@Param("params")Map<String,Object> params);

	List<ReportFinanceDto> findReportAllFinanceByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	List<ReportFinanceDto> findReportAllFinanceFooter(@Param("params")Map<String,Object> params);
	
	int findReportInventoryCount(@Param("params")Map<String,Object> params);

	List<ReportInventoryDto> findReportInventoryByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	List<ReportInventoryDto> findReportInventoryFooter(@Param("params")Map<String,Object> params);

	int findReportTransferCount(@Param("params")Map<String,Object> params);

	List<ReportTransferDto> findReportTransferByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	List<ReportTransferDto> findReportTransferFooter(@Param("params")Map<String,Object> params);
	
}