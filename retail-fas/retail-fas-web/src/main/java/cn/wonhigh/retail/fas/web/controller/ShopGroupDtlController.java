package cn.wonhigh.retail.fas.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.ShopGroupDtl;
import cn.wonhigh.retail.fas.manager.ShopGroupDtlManager;

import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-01-22 11:41:25
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
@RequestMapping("/shop_group_dtl")
public class ShopGroupDtlController extends BaseCrudController<ShopGroupDtl> {
    @Resource
    private ShopGroupDtlManager shopGroupDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("shop_group_dtl/",shopGroupDtlManager);
    }
}