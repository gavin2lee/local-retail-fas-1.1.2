package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.service.BillBalanceInvoiceRegisterService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-17 12:13:49
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
@Service("billBalanceInvoiceRegisterManager")
class BillBalanceInvoiceRegisterManagerImpl extends BaseCrudManagerImpl implements BillBalanceInvoiceRegisterManager {
    @Resource
    private BillBalanceInvoiceRegisterService billBalanceInvoiceRegisterService;

    @Override
    public BaseCrudService init() {
        return billBalanceInvoiceRegisterService;
    }
}