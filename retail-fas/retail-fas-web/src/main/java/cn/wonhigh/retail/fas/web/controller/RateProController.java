package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.RatePro;
import cn.wonhigh.retail.fas.manager.RateProManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2014-12-18 14:08:20
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
@RequestMapping("/rate_pro")
public class RateProController extends BaseCrudController<RatePro> {
    @Resource
    private RateProManager rateProManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("rate_pro/",rateProManager);
    }
}