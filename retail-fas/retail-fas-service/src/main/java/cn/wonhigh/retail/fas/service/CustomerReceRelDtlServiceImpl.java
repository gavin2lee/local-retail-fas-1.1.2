package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.dal.database.CustomerReceRelDtlMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-01-29 14:26:35
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
@Service("customerReceRelDtlService")
class CustomerReceRelDtlServiceImpl extends BaseCrudServiceImpl implements CustomerReceRelDtlService {
    @Resource
    private CustomerReceRelDtlMapper customerReceRelDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return customerReceRelDtlMapper;
    }
}