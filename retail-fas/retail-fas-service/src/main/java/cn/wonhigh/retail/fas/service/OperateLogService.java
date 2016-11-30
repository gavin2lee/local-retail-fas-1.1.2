package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.OperateLog;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface OperateLogService extends BaseCrudService {
	
	 /**
     * 通过单据编码和模块编码删除数据
     * @param params 限制参数
     * @return 删除的数量
     * @throws ServiceException 异常
     */
	int deleteByDataAndModuleNo(Map<String, Object> params) throws ServiceException;
	
	 /**
     * 通过单据编码和模块编码删除数据
     * @param params 限制参数
     * @return 删除的数量
     * @throws ServiceException 异常
     */
	int deleteByDataAndModuleNo(String dataNo, String moduleNo) throws ServiceException;
	
	/**
	 * 通过单据编码和模块编码查询group by后的数据
	 * @param params 查询参数
	 * @return 数据集合
	 * @throws ServiceException 异常
	 */
	List<OperateLog> selectProcessData(Map<String, Object> params) throws ServiceException;
}