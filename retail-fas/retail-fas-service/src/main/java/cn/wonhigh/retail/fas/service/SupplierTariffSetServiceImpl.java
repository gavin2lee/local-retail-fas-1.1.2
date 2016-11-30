package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.dal.database.SupplierTariffSetMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-07-08 15:43:31
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
@Service("supplierTariffSetService")
class SupplierTariffSetServiceImpl extends BaseCrudServiceImpl implements SupplierTariffSetService {
    @Resource
    private SupplierTariffSetMapper supplierTariffSetMapper;

    @Override
    public BaseCrudMapper init() {
        return supplierTariffSetMapper;
    }
}