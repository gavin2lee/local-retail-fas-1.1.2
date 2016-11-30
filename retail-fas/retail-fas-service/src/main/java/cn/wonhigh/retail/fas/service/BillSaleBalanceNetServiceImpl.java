package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.dal.database.BillSaleBalanceNetMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-07-25 11:17:18
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
@Service("billSaleBalanceNetService")
class BillSaleBalanceNetServiceImpl extends BaseCrudServiceImpl implements BillSaleBalanceNetService {
    @Resource
    private BillSaleBalanceNetMapper billSaleBalanceNetMapper;

    @Override
    public BaseCrudMapper init() {
        return billSaleBalanceNetMapper;
    }
}