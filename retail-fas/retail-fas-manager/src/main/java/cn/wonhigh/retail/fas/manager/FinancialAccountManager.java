package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.FinancialAccount;

import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-28 10:14:44
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
public interface FinancialAccountManager extends BaseCrudManager {

	/**
	 * 查询承担总部职能的结算公司
	 * @return
	 */
	String findLeadRoleCompanyNos();
	
	/**
	 * 查询没有承担总部职能的结算公司
	 * @return
	 */
	String findNotLeadRoleCompanyNos();
	
	/**
	 * 查询基础数据列表
	 * @param params 参数
	 * @return 数据集合
	 */
	List<FinancialAccount> findBaseInfo(Map<String,Object> params);
	
	/**
	 * 查询基础数据对象
	 * @param params 参数
	 * @return 单个数据对象
	 */
	FinancialAccount getBaseInfo(Map<String, Object> params);
}