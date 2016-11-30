package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SelfShopTerminalAccount;
import cn.wonhigh.retail.fas.common.model.SelfShopTerminalAccount;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.service.SelfShopTerminalAccountService;

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
@Service("selfShopTerminalAccountManager")
class SelfShopTerminalAccountManagerImpl extends BaseCrudManagerImpl implements SelfShopTerminalAccountManager {
    @Resource
    private SelfShopTerminalAccountService selfShopTerminalAccountService;

    @Override
    public BaseCrudService init() {
        return selfShopTerminalAccountService;
    }

	@Override
	public boolean addListShopTerminalAccount(
			List<SelfShopTerminalAccount> selfShopTerminalAccountList)
			throws ManagerException {
		try {
			if(selfShopTerminalAccountList!=null &&selfShopTerminalAccountList.size()>0){
				for (SelfShopTerminalAccount selfShopTerminalAccount:selfShopTerminalAccountList) {
					this.add(selfShopTerminalAccount);
				}
			}
		} catch (ManagerException e) {
			throw e;
		}
		return true;
	}

	@Override
	public Map<String, String> validationTerminalAccount(
			SelfShopTerminalAccount selfShopTerminalAccount)
			throws ManagerException {
		try {
			Map<String, String> respInfo = new HashMap<String, String>();
			Map<String, Object> params =  new HashMap<String, Object>();
			params.put("shopNoLists", FasUtil.formatInQueryCondition(selfShopTerminalAccount.getShopNo()));
			params.put("terminalNumber", selfShopTerminalAccount.getTerminalNumber());
			params.put("creditCardAccount", selfShopTerminalAccount.getCreditCardAccount());
			String id = selfShopTerminalAccount.getId();
			if(id!=null){
				params.put("queryCondition", " and id != '" + id + "'");
			}
			List<SelfShopTerminalAccount> list = selfShopTerminalAccountService.findByBiz(null, params);
			if(list.size()>0){
				respInfo.put("error",
						"店铺终端账号已存在!<br />[店铺:"
								+ selfShopTerminalAccount.getShopNo() + ",终端账号:"
								+ selfShopTerminalAccount.getTerminalNumber() + "]");
			}
			return respInfo;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}