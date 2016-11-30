package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.BillBuyBalanceAdditional;
import cn.wonhigh.retail.fas.manager.BillBuyBalanceAdditionalManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-06-06 10:02:44
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/bill_buy_balance_additional")
public class BillBuyBalanceAdditionalController extends BaseCrudController<BillBuyBalanceAdditional> {
    @Resource
    private BillBuyBalanceAdditionalManager billBuyBalanceAdditionalManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_buy_balance_additional/",billBuyBalanceAdditionalManager);
    }
}