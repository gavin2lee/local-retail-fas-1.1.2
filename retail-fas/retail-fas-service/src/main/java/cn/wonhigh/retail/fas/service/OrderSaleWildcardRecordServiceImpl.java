package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.dal.database.OrderSaleWildcardRecordMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:10:28
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
@Service("orderSaleWildcardRecordService")
class OrderSaleWildcardRecordServiceImpl extends BaseCrudServiceImpl implements OrderSaleWildcardRecordService {
    @Resource
    private OrderSaleWildcardRecordMapper orderSaleWildcardRecordMapper;

    @Override
    public BaseCrudMapper init() {
        return orderSaleWildcardRecordMapper;
    }
}