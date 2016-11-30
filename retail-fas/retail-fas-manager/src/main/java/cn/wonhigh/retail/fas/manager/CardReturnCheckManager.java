package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CardReturnCheck;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-19 20:33:19
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
public interface CardReturnCheckManager extends BaseCrudManager {
	/**
	 * 根据查询条件，查询银行卡退款核对明细
	 * @param page
	 * @param params
	 * @return
	 * @author wangsm
	 */
	public List<CardReturnCheck> findCardReturnCheckList(SimplePage page,String orderByField,String orderBy,Map<String, Object> params) throws ManagerException;
	
	/**
	 * 根据查询条件，查询银行卡退款核对总记录数
	 * @param params
	 * @return
	 * @author wangsm
	 */
	public CardReturnCheck findCardReturnCheckCount(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 修改手续费
	 * @throws ServiceException 
	 * 
	 * 
	 */
	public void updatePoundage(List<CardReturnCheck> cardReturnCheckList) throws ManagerException, ServiceException;
}