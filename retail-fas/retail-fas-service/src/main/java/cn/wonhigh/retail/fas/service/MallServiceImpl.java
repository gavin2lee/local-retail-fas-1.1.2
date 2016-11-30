package cn.wonhigh.retail.fas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.Mall;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.dal.database.MallMapper;
import cn.wonhigh.retail.fas.dal.database.ShopMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-11 10:59:29
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
@Service("mallService")
class MallServiceImpl extends BaseCrudServiceImpl implements MallService {
    @Resource
    private MallMapper mallMapper;

    @Override
    public BaseCrudMapper init() {
        return mallMapper;
    }

    
}