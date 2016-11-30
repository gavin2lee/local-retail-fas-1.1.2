package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.OperateLog;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-11-20 11:53:39
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
public interface OperateLogMapper extends BaseCrudMapper {
	
	 /**
     * 通过单据编码和模块编码删除数据
     * @param params 限制参数
     * @return 删除的数量
     * @throws Exception 异常
     */
	int deleteByDataAndModuleNo(@Param("params")Map<String, Object> params) throws Exception;

	/**
	 * 通过单据编码和模块编码查询group by后的数据
	 * @param params 查询参数
	 * @return 数据集合
	 * @throws Exception 异常
	 */
	List<OperateLog> selectProcessData(@Param("params")Map<String, Object> params) throws Exception;
}