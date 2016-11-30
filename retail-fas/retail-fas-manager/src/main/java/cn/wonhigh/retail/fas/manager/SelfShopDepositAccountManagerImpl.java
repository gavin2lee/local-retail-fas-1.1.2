package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SelfShopDepositAccount;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.service.SelfShopDepositAccountService;

import com.mongodb.util.Hash;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-17 16:59:10
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
@Service("selfShopDepositAccountManager")
class SelfShopDepositAccountManagerImpl extends BaseCrudManagerImpl implements SelfShopDepositAccountManager {
    @Resource
    private SelfShopDepositAccountService selfShopDepositAccountService;

    @Override
    public BaseCrudService init() {
        return selfShopDepositAccountService;
    }

	@Override
	public boolean addListShopDepositAccount(
			List<SelfShopDepositAccount> selfShopDepositAccountList)
			throws ManagerException {
		try {
			if(selfShopDepositAccountList!=null &&selfShopDepositAccountList.size()>0){
				for (SelfShopDepositAccount selfShopDepositAccount:selfShopDepositAccountList) {
					this.add(selfShopDepositAccount);
				}
			}
		} catch (ManagerException e) {
			throw e;
		}
		return true;
	}

	@Override
	public Map<String, String> validationDepositAccount(
			SelfShopDepositAccount selfShopDepositAccount)
			throws ManagerException {
		try {
			Map<String, String> respInfo = new HashMap<String, String>();
			Map<String, Object> params =  new HashMap<String, Object>();
			if (null != selfShopDepositAccount.getId()
					&& !"".equals(selfShopDepositAccount.getId())) {
				params.put("id", selfShopDepositAccount.getId());
			}
			params.put("shopNoLists", FasUtil.formatInQueryCondition(selfShopDepositAccount.getShopNo()));
			params.put("depositAccount", selfShopDepositAccount.getDepositAccount());
			String queryCondition = " and id <> '"+selfShopDepositAccount.getId()+"'";
			params.put("queryCondition", queryCondition);
			int count = selfShopDepositAccountService.findCount(params);
			if(count>0){
				respInfo.put("error",
						"店铺存现账号已存在!<br />[店铺:"
								+ selfShopDepositAccount.getShopNo() + ",存现账号:"
								+ selfShopDepositAccount.getDepositAccount() + "]");
			}
			return respInfo;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}