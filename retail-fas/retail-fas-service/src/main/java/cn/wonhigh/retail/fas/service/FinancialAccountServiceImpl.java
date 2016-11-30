package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.dal.database.FinancialAccountMapper;

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
@Service("financialAccountService")
class FinancialAccountServiceImpl extends BaseServiceImpl implements FinancialAccountService {
    @Resource
    private FinancialAccountMapper financialAccountMapper;
    
    @Override
    public BaseCrudMapper init() {
        return financialAccountMapper;
    }

	@Override
	public String findLeadRoleCompanyNos(String organTypeNo) {
		return financialAccountMapper.selectLeadRoleCompanyNos(organTypeNo);
	}

	/**
	 * 查询基础数据列表
	 * @param params 参数
	 * @return 数据集合
	 */
	@Override
	public List<FinancialAccount> findBaseInfo(Map<String, Object> params) {
		return financialAccountMapper.findBaseInfo(params);
	}

	@Override
	public String findNotLeadRoleCompanyNos(String organTypeNo) {
		return financialAccountMapper.selectNotLeadRoleCompanyNos(organTypeNo);
	}

 

}