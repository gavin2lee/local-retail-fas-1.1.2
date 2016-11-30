package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerOrderRemainDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 批发订单余额
 * @author user
 * @date  2016-06-08 18:09:42
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
public interface CustomerOrderRemainManager extends BaseCrudManager {
	
	/**
	 * 查询批发订单余额
	 * @param page 分页对象
	 * @param sortColumn 排序字段
	 * @param sortOrder 排序条件
	 * @param params 查询参数
	 * @return List<WholesaleCustomerOrderRemainDto>
	 * @throws ManagerException 异常
	 */
	List<WholesaleCustomerOrderRemainDto> queryWholesaleCustomerOrderRemain(SimplePage page, String sortColumn, String sortOrder, 
			Map<String, Object> params) throws ManagerException;
	
}