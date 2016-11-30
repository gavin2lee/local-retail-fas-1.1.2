package cn.wonhigh.retail.fas.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.service.CheckTolerService;

import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

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
@Service("check_tolerManager")
class CheckTolerManagerImpl extends BaseCrudManagerImpl implements CheckTolerManager {
    @Resource
    private CheckTolerService check_tolerService;

    @Override
    public BaseCrudService init() {
        return check_tolerService;
    }

}