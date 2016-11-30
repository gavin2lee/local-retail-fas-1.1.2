package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.BillAskPaymentDtlMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
@Service("billAskPaymentDtlService")
class BillAskPaymentDtlServiceImpl extends BaseServiceImpl implements BillAskPaymentDtlService {
    @Resource
    private BillAskPaymentDtlMapper billAskPaymentDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return billAskPaymentDtlMapper;
    }
}