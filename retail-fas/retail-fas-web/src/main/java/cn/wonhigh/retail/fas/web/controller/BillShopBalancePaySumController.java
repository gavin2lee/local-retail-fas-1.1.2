package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.BillShopBalancePaySum;
import cn.wonhigh.retail.fas.manager.BillShopBalancePaySumManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-12-02 14:50:43
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
@RequestMapping("/bill_shop_balance_pay_sum")
public class BillShopBalancePaySumController extends BaseCrudController<BillShopBalancePaySum> {
    @Resource
    private BillShopBalancePaySumManager billShopBalancePaySumManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_shop_balance_pay_sum/",billShopBalancePaySumManager);
    }
}