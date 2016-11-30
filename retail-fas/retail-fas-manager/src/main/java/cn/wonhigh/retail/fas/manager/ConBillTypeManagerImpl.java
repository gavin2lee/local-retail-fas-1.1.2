package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.service.ConBillTypeService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-02-24 15:57:53
 * @version 1.0.1
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("conBillTypeManager")
class ConBillTypeManagerImpl extends BaseCrudManagerImpl implements ConBillTypeManager {
    @Resource
    private ConBillTypeService conBillTypeService;

    @Override
    public BaseCrudService init() {
        return conBillTypeService;
    }
}