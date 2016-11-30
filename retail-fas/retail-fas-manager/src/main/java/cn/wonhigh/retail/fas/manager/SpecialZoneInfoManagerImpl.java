package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.service.SpecialZoneInfoService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-04-07 18:18:17
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("specialZoneInfoManager")
class SpecialZoneInfoManagerImpl extends BaseCrudManagerImpl implements SpecialZoneInfoManager {
    @Resource
    private SpecialZoneInfoService specialZoneInfoService;

    @Override
    public BaseCrudService init() {
        return specialZoneInfoService;
    }
}