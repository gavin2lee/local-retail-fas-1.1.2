package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.BalanceDateMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-27 16:17:53
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
@Service("balanceDateService")
class BalanceDateServiceImpl extends BaseCrudServiceImpl implements BalanceDateService {
    @Resource
    private BalanceDateMapper balanceDateMapper;
    
    @Override
    public BaseCrudMapper init() {
        return balanceDateMapper;
    }
}