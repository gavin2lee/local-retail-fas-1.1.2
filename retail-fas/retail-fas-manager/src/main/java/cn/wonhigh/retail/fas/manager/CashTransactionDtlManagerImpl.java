package cn.wonhigh.retail.fas.manager;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.CashTransactionDtl;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.service.CashTransactionDtlService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 17:36:27
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
@Service("cashTransactionDtlManager")
class CashTransactionDtlManagerImpl extends BaseCrudManagerImpl implements CashTransactionDtlManager {
    @Resource
    private CashTransactionDtlService cashTransactionDtlService;

    @Override
    public BaseCrudService init() {
        return cashTransactionDtlService;
    }

	@Override
	public boolean uploadListTransactionDtl(List<CashTransactionDtl> list) 
			throws ManagerException {
		try {
			if(list != null && list.size() > 0) {
				for(CashTransactionDtl dtl : list) {
					dtl.setId(UUIDGenerator.getUUID());
					this.add(dtl);
				}
			}
		} catch(ManagerException e) {
			throw e;
		}
		return true;
	}
}