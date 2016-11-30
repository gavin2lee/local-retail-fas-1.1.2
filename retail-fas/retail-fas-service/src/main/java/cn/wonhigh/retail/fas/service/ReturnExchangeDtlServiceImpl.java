package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.dal.database.ReturnExchangeDtlMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:13:46
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
@Service("returnExchangeDtlService")
class ReturnExchangeDtlServiceImpl extends BaseCrudServiceImpl implements ReturnExchangeDtlService {
    @Resource
    private ReturnExchangeDtlMapper returnExchangeDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return returnExchangeDtlMapper;
    }
}