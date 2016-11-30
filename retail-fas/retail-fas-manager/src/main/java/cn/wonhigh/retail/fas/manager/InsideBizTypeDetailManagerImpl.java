package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.service.InsideBizTypeDetailService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-12 16:39:33
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
@Service("insideBizTypeDetailManager")
class InsideBizTypeDetailManagerImpl extends BaseCrudManagerImpl implements InsideBizTypeDetailManager {
    @Resource
    private InsideBizTypeDetailService insideBizTypeDetailService;

    @Override
    public BaseCrudService init() {
        return insideBizTypeDetailService;
    }
}