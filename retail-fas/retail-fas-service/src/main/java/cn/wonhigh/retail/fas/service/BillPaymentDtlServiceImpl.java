package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.BillPaymentDtlMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-27 10:56:55
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
@Service("billPaymentDtlService")
class BillPaymentDtlServiceImpl extends BaseServiceImpl implements BillPaymentDtlService {
    @Resource
    private BillPaymentDtlMapper billPaymentDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return billPaymentDtlMapper;
    }
}