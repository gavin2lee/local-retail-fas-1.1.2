package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.CompanyBalanceBill;
import cn.wonhigh.retail.fas.manager.CompanyBalanceBillManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-02-25 10:05:41
 * @version 1.0.1
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
@RequestMapping("/company_balance_bill")
public class CompanyBalanceBillController extends BaseCrudController<CompanyBalanceBill> {
    @Resource
    private CompanyBalanceBillManager companyBalanceBillManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("company_balance_bill/",companyBalanceBillManager);
    }
}