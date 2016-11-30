package cn.wonhigh.retail.fas.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.service.BillShopBalanceInvoiceInfoService;

import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:37
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
@Service("billShopBalanceInvoiceInfoManager")
class BillShopBalanceInvoiceInfoManagerImpl extends BaseCrudManagerImpl implements BillShopBalanceInvoiceInfoManager {
    @Resource
    private BillShopBalanceInvoiceInfoService billShopBalanceInvoiceInfoService;

    @Override
    public BaseCrudService init() {
        return billShopBalanceInvoiceInfoService;
    }
}