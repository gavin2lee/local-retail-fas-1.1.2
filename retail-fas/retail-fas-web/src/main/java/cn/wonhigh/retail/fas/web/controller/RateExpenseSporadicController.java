package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.RateExpenseSporadic;
import cn.wonhigh.retail.fas.manager.RateExpenseSporadicManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-27 11:56:41
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
@RequestMapping("/rate_expense_sporadic")
public class RateExpenseSporadicController extends BaseCrudController<RateExpenseSporadic> {
    @Resource
    private RateExpenseSporadicManager rateExpenseSporadicManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("rate_expense_sporadic/",rateExpenseSporadicManager);
    }
}