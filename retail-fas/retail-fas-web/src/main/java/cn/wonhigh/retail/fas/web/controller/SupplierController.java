package cn.wonhigh.retail.fas.web.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.Supplier;
import cn.wonhigh.retail.fas.manager.SupplierManager;

import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 供应商
 * @author ouyang.zm
 * @date  2014-08-25 13:52:36
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
@RequestMapping("/base_setting/supplier")
public class SupplierController extends BaseCrudController<Supplier> {
    @Resource
    private SupplierManager supplierManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("base_setting/supplier/",supplierManager);
    }
    
    /**
	 * 转到查询供应商
	 * @return
	 */
	@RequestMapping("/toSearchSupplier")
	public String toSearchCompany() {
		return "base_setting/supplier/searchSupplier";
	}
	
	/**
	 * 转到查询供应商(多选)
	 * @return
	 */
	@RequestMapping("/toMultiSearch")
	public String toMultiSearch() {
		return "base_setting/supplier/multiSearch";
	}
}