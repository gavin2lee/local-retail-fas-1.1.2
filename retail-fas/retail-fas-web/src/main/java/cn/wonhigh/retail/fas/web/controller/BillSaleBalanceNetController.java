package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.BillSaleBalanceNet;
import cn.wonhigh.retail.fas.manager.BillSaleBalanceNetManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Controller
@RequestMapping("/bill_sale_balance_net")
public class BillSaleBalanceNetController extends BaseCrudController<BillSaleBalanceNet> {
    @Resource
    private BillSaleBalanceNetManager billSaleBalanceNetManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_sale_balance_net/",billSaleBalanceNetManager);
    }
}