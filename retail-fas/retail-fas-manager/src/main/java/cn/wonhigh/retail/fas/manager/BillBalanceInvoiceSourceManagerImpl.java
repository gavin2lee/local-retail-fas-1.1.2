package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.service.BillBalanceInvoiceSourceService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-30 11:19:51
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
@Service("billBalanceInvoiceSourceManager")
class BillBalanceInvoiceSourceManagerImpl extends BaseCrudManagerImpl implements BillBalanceInvoiceSourceManager {
    @Resource
    private BillBalanceInvoiceSourceService billBalanceInvoiceSourceService;

    @Override
    public BaseCrudService init() {
        return billBalanceInvoiceSourceService;
    }
}