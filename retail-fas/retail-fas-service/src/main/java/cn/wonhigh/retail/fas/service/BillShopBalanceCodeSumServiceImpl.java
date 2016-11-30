package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.dal.database.BillShopBalanceCodeSumMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-06-26 19:11:14
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
@Service("billShopBalanceCodeSumService")
class BillShopBalanceCodeSumServiceImpl extends BaseCrudServiceImpl implements BillShopBalanceCodeSumService {
    @Resource
    private BillShopBalanceCodeSumMapper billShopBalanceCodeSumMapper;

    @Override
    public BaseCrudMapper init() {
        return billShopBalanceCodeSumMapper;
    }
}