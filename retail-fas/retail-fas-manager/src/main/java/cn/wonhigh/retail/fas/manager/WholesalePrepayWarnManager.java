package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.WholesalePrepayWarn;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface WholesalePrepayWarnManager extends BaseCrudManager {
	
	/**
	 * 查询保证金额
	 * @param params 查询参数
	 * @return Map<String, BigDecimal>
	 * @throws ManagerException 异常
	 */
	Map<String, BigDecimal> selectMarginAmount(Map<String, Object> params)
			throws ManagerException;
	
	/**
	 * 查询可冲销的数据集合
	 * @param params 查询参数
	 * @return List<WholesalePrepayWarn>
	 */
	List<WholesalePrepayWarn> selectReversal(Map<String, Object> params)
			throws ManagerException;
}