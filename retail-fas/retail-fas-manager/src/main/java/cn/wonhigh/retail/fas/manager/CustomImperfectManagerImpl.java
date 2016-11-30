package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.service.CustomImperfectService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-08-20 16:24:24
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
@Service("customImperfectManager")
class CustomImperfectManagerImpl extends BaseCrudManagerImpl implements CustomImperfectManager {
    @Resource
    private CustomImperfectService customImperfectService;

    @Override
    public BaseCrudService init() {
        return customImperfectService;
    }
}