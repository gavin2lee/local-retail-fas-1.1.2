package cn.wonhigh.retail.fas.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.service.BillAskPaymentDtlService;

import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-25 11:34:16
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
@Service("billAskPaymentDtlManager")
class BillAskPaymentDtlManagerImpl extends BaseCrudManagerImpl implements BillAskPaymentDtlManager {
    @Resource
    private BillAskPaymentDtlService billAskPaymentDtlService;

    @Override
    public BaseCrudService init() {
        return billAskPaymentDtlService;
    }
}