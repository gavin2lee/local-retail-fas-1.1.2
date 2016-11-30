package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.FinancialAccount;

import com.yougou.logistics.base.service.BaseCrudService;

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
public interface FinancialAccountService extends BaseCrudService {

 
	
	/**
	 * 查询基础数据列表
	 * @param params 参数
	 * @return 数据集合
	 */
	List<FinancialAccount> findBaseInfo(Map<String,Object> params);
 

	String findLeadRoleCompanyNos(String organTypeNo);

	String findNotLeadRoleCompanyNos(String organTypeNo);
}