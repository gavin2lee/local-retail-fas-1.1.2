package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.FinancialAccount;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
public interface FinancialAccountMapper extends BaseCrudMapper {

	/**
	 * @return
	 */
	String selectLeadRoleCompanyNos(@Param("organTypeNo")String organTypeNo);
	
	
	/**
	 * @return
	 */
	String selectNotLeadRoleCompanyNos(@Param("organTypeNo") String organTypeNo);
	
	/**
	 * 查询基础数据列表
	 * @param params 参数
	 * @return 数据集合
	 */
	List<FinancialAccount> findBaseInfo(@Param("params")Map<String,Object> params);


}