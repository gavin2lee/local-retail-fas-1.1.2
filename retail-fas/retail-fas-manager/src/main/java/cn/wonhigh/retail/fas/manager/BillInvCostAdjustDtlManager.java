package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillInvCostAdjustDtl;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-31 16:10:14
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
public interface BillInvCostAdjustDtlManager extends BaseCrudManager {
	
	/**
	 * 公共的保存操作
	 * @param params key:增、删、改操作枚举 value:对象列表
	 * @return 影响条数
	 * @throws ServiceException 
	 */
	int save(Map<CommonOperatorEnum, List<BillInvCostAdjustDtl>> params, Map<String, String> paramTemp)
			throws ManagerException;

	public List<BillInvCostAdjustDtl> findListBLK(SimplePage page, String orderByField,String orderBy, Map<String, Object> params) throws ManagerException;

	public int findListBLKcount(Map<String, Object> params) throws ManagerException;

}