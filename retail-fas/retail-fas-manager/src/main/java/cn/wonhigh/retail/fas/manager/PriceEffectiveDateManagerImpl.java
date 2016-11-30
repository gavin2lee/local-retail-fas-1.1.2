package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.service.PriceEffectiveDateService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-03-10 11:07:51
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
@Service("priceEffectiveDateManager")
class PriceEffectiveDateManagerImpl extends BaseCrudManagerImpl implements PriceEffectiveDateManager {
    @Resource
    private PriceEffectiveDateService priceEffectiveDateService;

    @Override
    public BaseCrudService init() {
        return priceEffectiveDateService;
    }
}