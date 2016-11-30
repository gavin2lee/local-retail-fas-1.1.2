package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.CustomerReceRelDtl;
import cn.wonhigh.retail.fas.manager.CustomerReceRelDtlManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-01-29 14:26:35
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
@RequestMapping("/customer_rece_rel_dtl")
public class CustomerReceRelDtlController extends BaseCrudController<CustomerReceRelDtl> {
    @Resource
    private CustomerReceRelDtlManager customerReceRelDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("customer_rece_rel_dtl/",customerReceRelDtlManager);
    }
}