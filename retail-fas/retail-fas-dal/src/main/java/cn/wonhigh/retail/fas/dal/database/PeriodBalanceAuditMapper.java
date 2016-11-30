package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.PeriodBalanceAudit;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-28 17:10:06
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
public interface PeriodBalanceAuditMapper extends BaseCrudMapper {
	
	/**
	 * 插入生成成本日志
	 * @param params
	 * @return
	 */
	public int insertPeriodBalanceAudit(@Param("params") Map<String, Object> params);
	
	/**
	 * 更新生成成本日志
	 * @param params
	 * @return
	 */
	public int updatePeriodBalanceAudit(@Param("params") Map<String, Object> params);
	
	/**
	 * 统计生成成本日志数量
	 * @param params
	 * @return
	 */
	public int countPeriodBalanceAudit(@Param("params") Map<String, Object> params);
	
	/**
	 * 查询生成成本日志
	 * @param page 
	 * @param params
	 * @return
	 */
	public List<PeriodBalanceAudit> queryPeriodBalanceAuditByPage(@Param("page") SimplePage page, @Param("params") Map<String, Object> params);
	
}