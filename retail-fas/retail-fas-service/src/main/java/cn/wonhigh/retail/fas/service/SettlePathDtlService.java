package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface SettlePathDtlService extends BaseCrudService {
	
	/**
	 * 查询可选择的结算公司数量
	 * @param params 查询参数
	 * @return 数量
	 * @throws ServiceException 异常
	 */
	int findCompanyCount(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 分页查询可选择的结算公司
	 * @param page 分页对象
	 * @param orderByField 排序字段
	 * @param orderBy 排序
	 * @param params 查询参数
	 * @return 结算公司集合
	 * @throws ServiceException 异常
	 */
	List<Object> findCompanyPage(SimplePage page, String orderByField, String orderBy, Map<String,Object> params)
			throws ServiceException;

	/**
	 * 通过结算路径编码删除明细数据
	 * @param pathNo 结算大类编码
	 * @return 删除的记录数
	 * @throws ServiceException 异常
	 */
	int deleteByPathNo(String pathNo) throws ServiceException;
	
}