package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.ConBillType;
import cn.wonhigh.retail.fas.manager.ConBillTypeManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-02-24 15:57:53
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
@RequestMapping("/con_bill_type")
public class ConBillTypeController extends BaseCrudController<ConBillType> {
    @Resource
    private ConBillTypeManager conBillTypeManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("con_bill_type/",conBillTypeManager);
    }
}