package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.service.MemorandumService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-06 15:58:08
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
@Service("memorandumManager")
class MemorandumManagerImpl extends BaseCrudManagerImpl implements MemorandumManager {
    @Resource
    private MemorandumService memorandumService;

    @Override
    public BaseCrudService init() {
        return memorandumService;
    }
}