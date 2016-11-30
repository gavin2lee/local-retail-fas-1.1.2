package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
public interface SettlePathDtlMapper extends BaseCrudMapper {

	int findCompanyCount(@Param("params")Map<String, Object> params) throws Exception;

	List<Object> findCompanyPage(@Param("page")SimplePage page, @Param("orderByField")String orderByField,
			@Param("orderBy")String orderBy, @Param("params")Map<String, Object> params) throws Exception;

	int deleteByPathNo(@Param("pathNo")String pathNo) throws Exception;
}