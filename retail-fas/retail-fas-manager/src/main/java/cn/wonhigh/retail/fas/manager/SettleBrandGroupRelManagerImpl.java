package cn.wonhigh.retail.fas.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.service.SettleBrandGroupRelService;

import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 品牌组与品牌的关联 
 * @author yang.y
 * @date  2014-08-26 10:36:46
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
@Service("settleBrandGroupRelManager")
class SettleBrandGroupRelManagerImpl extends BaseCrudManagerImpl implements SettleBrandGroupRelManager {
   
	@Resource
    private SettleBrandGroupRelService settleBrandGroupRelService;

    @Override
    public BaseCrudService init() {
        return settleBrandGroupRelService;
    }
}