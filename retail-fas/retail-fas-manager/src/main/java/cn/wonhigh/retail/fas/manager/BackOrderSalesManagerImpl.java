package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.service.BackOrderSalesService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-09-01 18:15:52
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
@Service("backOrderSalesManager")
class BackOrderSalesManagerImpl extends BaseCrudManagerImpl implements BackOrderSalesManager {
    @Resource
    private BackOrderSalesService backOrderSalesService;

    @Override
    public BaseCrudService init() {
        return backOrderSalesService;
    }
}