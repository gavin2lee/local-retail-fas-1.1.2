package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingOccurDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingSendaDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingSumDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 批发客户余额
 * @author Administrator
 * @date  2015-07-06 15:41:34
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
public interface WholesaleCustomerRemainingSumManager extends BaseCrudManager {
	/**
	 * 查询客户余额
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	List<WholesaleCustomerRemainingSumDto> findCustomerRemainningByPage(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ManagerException;
	
	/**
	 * 查询森达客户余额明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	List<WholesaleCustomerRemainingSendaDto> findSendaListByPage(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ManagerException;
	
	/**
	 * 客户余额发生查询
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	List<WholesaleCustomerRemainingOccurDto> findCustomerRemainningOccurByPage(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ManagerException;
	
	/**
	 * 根据客户编号修改余额
	 * @return
	 */
	int updateByCustomerNo(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 查询森达客户余额明细总条数
	 * @return
	 */
	int findSendaCount(Map<String, Object> params) throws ManagerException;
}