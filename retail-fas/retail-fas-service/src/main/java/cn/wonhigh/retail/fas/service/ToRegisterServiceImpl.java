package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SalesSummary;
import cn.wonhigh.retail.fas.common.model.ToRegister;
import cn.wonhigh.retail.fas.dal.database.ToRegisterMapper;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-03-31 14:45:27
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
@Service("toRegisterService")
class ToRegisterServiceImpl extends BaseCrudServiceImpl implements ToRegisterService {
    @Resource
    private ToRegisterMapper toRegisterMapper;

    @Override
    public BaseCrudMapper init() {
        return toRegisterMapper;
    }

}