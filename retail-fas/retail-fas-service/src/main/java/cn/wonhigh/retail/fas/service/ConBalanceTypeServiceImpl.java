package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.dal.database.ConBalanceTypeMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-02-24 10:31:04
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
@Service("conBalanceTypeService")
class ConBalanceTypeServiceImpl extends BaseCrudServiceImpl implements ConBalanceTypeService {
    @Resource
    private ConBalanceTypeMapper conBalanceTypeMapper;

    @Override
    public BaseCrudMapper init() {
        return conBalanceTypeMapper;
    }
}