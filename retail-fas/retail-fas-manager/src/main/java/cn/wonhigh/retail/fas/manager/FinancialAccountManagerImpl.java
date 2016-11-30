package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.service.FinancialAccountService;

import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
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
@Service("financialAccountManager")
class FinancialAccountManagerImpl extends BaseCrudManagerImpl implements FinancialAccountManager {
    @Resource
    private FinancialAccountService financialAccountService;

    @Override
    public BaseCrudService init() {
        return financialAccountService;
    }

	@Override
	public String findLeadRoleCompanyNos() {
		String organTypeNo = null;
		SystemUser user = Authorization.getUser();
		if( user != null  )
			organTypeNo = user.getOrganTypeNo();
		return financialAccountService.findLeadRoleCompanyNos(organTypeNo);
	}

	/**
	 * 查询基础数据对象
	 * @param params 参数
	 * @return 单个数据对象
	 */
	@Override
	public FinancialAccount getBaseInfo(Map<String, Object> params) {
		List<FinancialAccount> list = this.findBaseInfo(params);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 查询基础数据列表
	 * @param params 参数
	 * @return 数据集合
	 */
	@Override
	public List<FinancialAccount> findBaseInfo(Map<String, Object> params) {
		return financialAccountService.findBaseInfo(params);
	}

	@Override
	public String findNotLeadRoleCompanyNos() {
		String organTypeNo = null;
		SystemUser user = Authorization.getUser();
		if( user != null  )
			organTypeNo = user.getOrganTypeNo();
		return financialAccountService.findNotLeadRoleCompanyNos(organTypeNo);
	}
}