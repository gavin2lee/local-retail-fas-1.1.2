package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingDtl;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-07-06 12:15:59
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
public interface WholesaleCustomerRemainingDtlManager extends BaseCrudManager {
	
	WholesaleCustomerRemainingDtl selectCustomerRemainingTotalByDate(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 分页查询客户余额明细
	 * @param SimplePage 分页对象
	 * @param params 查询参数
	 * @return 明细数据集合
	 * @throws Exception 异常
	 */
	List<WholesaleCustomerRemainingDtl> selectDtlByPage(SimplePage page, Map<String,Object> params)throws ManagerException ;
	
}