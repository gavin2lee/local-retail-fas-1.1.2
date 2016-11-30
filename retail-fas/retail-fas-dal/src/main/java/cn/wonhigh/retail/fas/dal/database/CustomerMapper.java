package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.CustomerMultiDataSourceDto;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-10-22 15:09:15
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
public interface CustomerMapper extends BaseCrudMapper {

	/**
	 * 多数据源查询客户数据
	 * @param page 分页对象
	 * @param sortColumn 排序字段
	 * @param sortOrder 排序条件
	 * @param params 查询参数
	 * @return List<CustomerMultiDataSourceDto>
	 * @throws Exception 异常
	 */
	List<CustomerMultiDataSourceDto> queryMultiDataSrouce(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params")Map<String,Object> params) throws Exception;

	/**
	 * 多数据源查询客户数量
	 * @param params 查询参数
	 * @return 数量
	 * @throws Exception 异常
	 */
	int queryMultiDataSrouceCount(@Param("params")Map<String, Object> params) throws Exception;
}