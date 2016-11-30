package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.InventoryBook;
import cn.wonhigh.retail.fas.manager.InventoryBookManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-03-22 10:50:44
 * @version 1.0.3
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
@RequestMapping("/inventory_book")
public class InventoryBookController extends BaseCrudController<InventoryBook> {
    @Resource
    private InventoryBookManager inventoryBookManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("inventory_book/",inventoryBookManager);
    }
}