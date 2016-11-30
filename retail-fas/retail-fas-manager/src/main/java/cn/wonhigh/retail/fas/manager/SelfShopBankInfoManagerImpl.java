package cn.wonhigh.retail.fas.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.SelfShopBankInfo;
import cn.wonhigh.retail.fas.service.SelfShopBankInfoService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-10 11:20:13
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
@Service("selfShopBankInfoManager")
class SelfShopBankInfoManagerImpl extends BaseCrudManagerImpl implements SelfShopBankInfoManager {
    @Resource
    private SelfShopBankInfoService selfShopBankInfoService;

    @Override
    public BaseCrudService init() {
        return selfShopBankInfoService;
    }

	@Override
	public boolean addListShopBankInfo(
			List<SelfShopBankInfo> list)
			throws ManagerException {
		try {
			if(list != null && list.size() > 0) {
				for(SelfShopBankInfo bankInfo : list){
					this.add(bankInfo);
				}
			}
		} catch(ManagerException e) {
			throw e;
		}
		return true;
	}
}