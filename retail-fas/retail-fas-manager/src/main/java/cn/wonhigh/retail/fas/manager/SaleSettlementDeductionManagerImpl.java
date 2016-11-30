package cn.wonhigh.retail.fas.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.service.SaleSettlementDeductionService;

import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-11 11:11:49
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
@Service("saleSettlementDeductionManager")
class SaleSettlementDeductionManagerImpl extends BaseCrudManagerImpl implements SaleSettlementDeductionManager {
    @Resource
    private SaleSettlementDeductionService saleSettlementDeductionService;

    @Override
    public BaseCrudService init() {
        return saleSettlementDeductionService;
    }
}