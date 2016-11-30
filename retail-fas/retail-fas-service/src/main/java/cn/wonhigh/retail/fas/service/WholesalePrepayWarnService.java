package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.WholesalePrepayWarn;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-23 11:02:19
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
public interface WholesalePrepayWarnService extends BaseCrudService {
	
	Map<String, BigDecimal> selectTotalAmount(Map<String, Object> params)
			throws ServiceException;
	
	/**
	 * 查询保证金额
	 * @param params 查询参数
	 * @return Map<String, BigDecimal>
	 * @throws ServiceException 异常
	 */
	Map<String, BigDecimal> selectMarginAmount(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 查询可冲销的数据集合
	 * @param params 查询参数
	 * @return List<WholesalePrepayWarn>
	 */
	List<WholesalePrepayWarn> selectReversal(Map<String, Object> params)
			throws ServiceException;
	
	/**
	 * 修改指定公司和客户的保证金余额和保证金是否足额标志
	 * @param params 参数
	 * @return 影响的记录数
	 * @throws ServiceException 异常
	 */
	int updateMargin(Map<String, Object> params) throws ServiceException;
}