package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.service.InventoryBookService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-03-22 10:50:44
 * @version 1.0.3
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("inventoryBookManager")
class InventoryBookManagerImpl extends BaseCrudManagerImpl implements InventoryBookManager {
    @Resource
    private InventoryBookService inventoryBookService;

    @Override
    public BaseCrudService init() {
        return inventoryBookService;
    }
}