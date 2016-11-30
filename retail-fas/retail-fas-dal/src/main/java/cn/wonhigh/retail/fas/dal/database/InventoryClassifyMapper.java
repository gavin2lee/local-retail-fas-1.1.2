package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.PeriodBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-05-06 19:05:06
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface InventoryClassifyMapper extends BaseCrudMapper {

	public int selectInventoryClassInicationCount(@Param("params") Map<String, Object> params);
	public List<Map<String, Object>> selectInventoryClassInicationByPage(@Param("page") SimplePage page,@Param("params") Map<String, Object> params);
	public List<Map<String, Object>> selectInventoryClassInicationTotal(@Param("params") Map<String, Object> params); 
	
	public List<PeriodBalance> selectCateGoryCloumn(@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);
	
	/**
	 * 查询体育的存货分类汇总记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public int findPeInventoryClassInicationCount(@Param("params") Map<String, Object> params);
	
	/**
	 * 查询体育的存货分类汇总记信息
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<Map<String, Object>> findPeInventoryClassInicationByPage(@Param("page") SimplePage page,@Param("params") Map<String, Object> params);
	
	/**
	 * 查询体育的存货分类汇总记底部合计
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<Map<String, Object>> findPeInventoryClassInicationTotal(@Param("params") Map<String, Object> params); 
	
}