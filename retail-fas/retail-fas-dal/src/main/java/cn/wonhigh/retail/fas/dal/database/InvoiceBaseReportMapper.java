package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-11-23 12:12:04
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
public interface InvoiceBaseReportMapper extends BaseCrudMapper {
	
	/**
	 * 查询总条数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectCount(@Param("params")Map<String, Object> params);
	
	/**
	 * 合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> selectTotal(@Param("params")Map<String, Object> params);
	
	/**
	 * 数据行
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Map<String, Object>> selectData(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
}