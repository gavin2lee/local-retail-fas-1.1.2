package cn.wonhigh.retail.fas.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.logistics.base.common.annotation.ModuleVerify;

import cn.wonhigh.retail.fas.common.model.PriceEffectiveDate;
import cn.wonhigh.retail.fas.manager.PriceEffectiveDateManager;

/**
 * 价格生效日期
 * @author user
 * @date  2016-03-10 11:07:51
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
@RequestMapping("/price_control/effective_date")
@ModuleVerify("30100021")
public class PriceEffectiveDateController extends BaseController<PriceEffectiveDate> {
    @Resource
    private PriceEffectiveDateManager priceEffectiveDateManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("price_control/effective_date/",priceEffectiveDateManager);
    }
}