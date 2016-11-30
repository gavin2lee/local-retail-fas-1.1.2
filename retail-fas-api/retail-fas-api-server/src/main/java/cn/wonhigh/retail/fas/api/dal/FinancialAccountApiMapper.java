package cn.wonhigh.retail.fas.api.dal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.FinancialAccount;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-12-05 16:36:16
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
public interface FinancialAccountApiMapper {

	List<FinancialAccount> findByCompanyNo(@Param("companyNo")String companyNo) throws Exception;
	
	/**
	 * 查询承担总部职能的公司
	 * @return 公司编号字符集，格式：'Z0001','Z0002'
	 */
	String selectLeadRoleCompanyNos();

	/**
	 * 根据参数查询公司帐套信息
	 * @return FinancialAccount列表
	 */
	List<FinancialAccount> selectByParams(@Param("params") Map<String, Object> params);
	
}