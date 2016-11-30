package cn.wonhigh.retail.fas.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.BizCity;
import cn.wonhigh.retail.fas.manager.BizCityManager;

import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-11 10:58:55
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
@RequestMapping("/biz_city")
public class BizCityController extends BaseCrudController<BizCity> {
    @Resource
    private BizCityManager bizCityManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("biz_city/",bizCityManager);
    }
}