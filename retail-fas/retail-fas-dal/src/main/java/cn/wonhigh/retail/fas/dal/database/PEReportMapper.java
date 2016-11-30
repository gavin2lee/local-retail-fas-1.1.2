package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.PEAPDto;

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
public interface PEReportMapper {

	int findAPAgingCount(@Param("params")Map<String, Object> params);

	List<PEAPDto> findAPAgingByPage(@Param("page")SimplePage page, @Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params);

	List<PEAPDto> findAPAgingFooter(@Param("params")Map<String, Object> params);

	int findAPPlanCount(@Param("params")Map<String, Object> params);

	List<PEAPDto> findAPPlanColumn(@Param("params")Map<String, Object> params);

	List<PEAPDto> findAPPlanListByPage(@Param("page")SimplePage page, @Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params);

	List<PEAPDto> findAPPlanFooter(@Param("params")Map<String, Object> params);

	List<PEAPDto> findPlanPageCondition(@Param("page")SimplePage page, @Param("params")Map<String, Object> params);

	int findReportSendCount(@Param("params")Map<String, Object> params);

	List<PEAPDto> findReportSendFooter(@Param("params")Map<String, Object> params);

	List<PEAPDto> findSendPageCondition(@Param("page")SimplePage page, @Param("params")Map<String, Object> params);

	List<PEAPDto> findSendListByPage(@Param("page")SimplePage page, @Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params);
	
}