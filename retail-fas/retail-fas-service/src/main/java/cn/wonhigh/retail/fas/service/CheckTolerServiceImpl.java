package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.CheckTolerMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 对账容差设置
 * @author tan.y
 * @date  2016-04-12 15:44:38
 * @version 1.0.0
 * @copyright (C) 2016 Belle Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the Belle technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("checkTolerService")
class CheckTolerServiceImpl extends BaseServiceImpl implements CheckTolerService {
    @Resource
    private CheckTolerMapper checkTolerMapper;

    @Override
    public BaseCrudMapper init() {
        return checkTolerMapper;
    }
}