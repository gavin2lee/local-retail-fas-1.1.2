package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.ExpectCash;
import cn.wonhigh.retail.fas.common.vo.CurrentShopVo;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * ExpectCashManager
 * @author tang.yc
 * @date  2014-08-26 16:05:20
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
public interface ExpectCashManager extends BaseCrudManager {
	
	/**
	 * 新增预收款信息
	 * @param ids
	 * @param user
	 * @param currentShopParams
	 * @return
	 * @throws ManagerException
	 */
	public int add(ExpectCash depositCash, SystemUser user, CurrentShopVo currentShopParams) throws ManagerException;
	
	/**
	 * 修改预收款信息
	 * @param ids
	 * @param user
	 * @param currentShopParams
	 * @return
	 * @throws ManagerException
	 */
	public int modify(ExpectCash depositCash, SystemUser user, CurrentShopVo currentShopParams) throws ManagerException;
	
	/**
	 * 删除预收款信息
	 * @param ids
	 * @return
	 * @throws ManagerException
	 */
	public void deleteByIds(String[] ids) throws ManagerException;

	/**
	 * 确认预收款信息
	 * @param ids
	 * @return
	 * @throws ManagerException
	 */
	public Map<String,String> verifyByIds(String[] ids, String flag,SystemUser user,String userName) throws ManagerException;

	/**
	 * 验证结算编码唯一（同一类别）
	 * @param expectCash
	 * @return
	 * @throws ManagerException
	 */
	public boolean verifyTheOnly(ExpectCash expectCash) throws ManagerException;

	/**
	 * 验证退款金额是否大于收款金额
	 * @param expectCash
	 * @return
	 * @throws ManagerException
	 */
	public boolean verifyPrice(ExpectCash expectCash)throws ManagerException;
	
	/**
	 * 修改退款时，验证退款金额是否大于收款金额
	 * @param expectCash
	 * @return
	 * @throws ManagerException
	 */
	public boolean verifyRefundPrice(ExpectCash expectCash)throws ManagerException;
	
	/**
	 * 查询预收款单(打印)
	 * @Title: findExpectCashByParams  
	 * @param params
	 * @param @throws ManagerException 
	 * @return List<ExpectCash> 
	 * @author: zhou.q1 
	 * @throws
	 */
	public List<ExpectCash> findExpectCashByParams(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 查询预收款单(打印)
	 * @Title: findExpectCashByParams  
	 * @param page
	 * @param sortColumn
	 * @param orderBy
	 * @param params
	 * @param @throws ManagerException 
	 * @return List<ExpectCash> 
	 * @author: zhou.q1 
	 * @throws
	 */
	public List<ExpectCash> findExpectCashByParams(SimplePage page,
			String sortColumn, String orderBy, Map<String, Object> params) throws ManagerException;
	
	
	/**
	 * 查询预收款单(打印)
	 * @Title: countExpectCashByParams  
	 * @param @param params
	 * @param @return
	 * @param @throws ManagerException 
	 * @return List<ExpectCash> 
	 * @author: zhou.q1 
	 * @throws
	 */
	public int countExpectCashByParams(Map<String, Object> params) throws ManagerException;
	
    public List<ExpectCash> processExpectCashBalanceDiff(Map<String, Object> params) throws ManagerException;
	
	public List<ExpectCash> processUseExpectCashBalanceDiff(Map<String, Object> params) throws ManagerException;
	
	public ExpectCash getExpectCashAmount(Map<String, Object> params) throws ManagerException;

}