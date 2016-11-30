package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.BillShopBalanceCodeSum;
import cn.wonhigh.retail.fas.manager.BillShopBalanceCodeSumManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Controller
@RequestMapping("/bill_shop_balance_code_sum")
public class BillShopBalanceCodeSumController extends BaseCrudController<BillShopBalanceCodeSum> {
    @Resource
    private BillShopBalanceCodeSumManager billShopBalanceCodeSumManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_shop_balance_code_sum/",billShopBalanceCodeSumManager);
    }
}