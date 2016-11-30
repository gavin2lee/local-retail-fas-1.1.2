package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.Supplier;

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
public interface SupplierMapper extends BaseCrudMapper {
	/**
	 *  查询未分组的供应商列表（状态为正常）
	 * @param status
	 * @return
	 */
	public List<Supplier> findByNoGroup(@Param("page")SimplePage page,@Param("params")Map<String, Object> params);
	/**
	 *  查询未分组的供应商总数（状态为正常）
	 * @param status
	 * @return
	 */
	public int findByNoGroupCount(@Param("params")Map<String, Object> params);

}