package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.ShopBrand;
import cn.wonhigh.retail.fas.manager.ShopBrandManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-01-22 10:14:42
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
@RequestMapping("/shop_brand")
public class ShopBrandController extends BaseCrudController<ShopBrand> {
    @Resource
    private ShopBrandManager shopBrandManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("shop_brand/",shopBrandManager);
    }
}