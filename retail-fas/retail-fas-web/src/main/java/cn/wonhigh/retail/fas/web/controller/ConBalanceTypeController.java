package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.ConBalanceType;
import cn.wonhigh.retail.fas.manager.ConBalanceTypeManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-02-24 10:31:04
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
@RequestMapping("/con_balance_type")
public class ConBalanceTypeController extends BaseCrudController<ConBalanceType> {
    @Resource
    private ConBalanceTypeManager conBalanceTypeManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("con_balance_type/",conBalanceTypeManager);
    }
}