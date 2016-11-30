package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.CustomerMultiDataSourceDto;
import cn.wonhigh.retail.fas.common.model.Customer;
import cn.wonhigh.retail.fas.manager.CustomerManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
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
@RequestMapping("/base_setting/customer")
public class CustomerController extends BaseCrudController<Customer> {
	
    @Resource
    private CustomerManager customerManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("base_setting/customer/",customerManager);
    }
    
    /**
   	 * 转到查询供应商
   	 * 
   	 * @return
   	 */
   	@RequestMapping("/toSearchCustomer")
   	public String toSearchCompany() {
   		return "base_setting/customer/searchCustomer";
   	}
   	
   	@RequestMapping("/toMultiSearch")
	public String toMultiSearch() {
		return "base_setting/customer/multiSearch";
	}
   	
   	/**
	 * 多数据源查询客户数据
	 * @param req HttpServletRequest
	 * @param model Model
	 * @return Map<String, Object>
	 * @throws ManagerException 异常
	 */
   	@RequestMapping("/query_multi_data_source")
   	@ResponseBody
   	public Map<String, Object> queryMultiDataSrouce(HttpServletRequest req, Model model) throws ManagerException {
   		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = customerManager.queryMultiDataSrouceCount(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		if(total < 1) {
			obj.put("total", total);
			obj.put("rows", new ArrayList<CustomerMultiDataSourceDto>());
			return obj;
		} 
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<CustomerMultiDataSourceDto> list = customerManager.queryMultiDataSrouce(page, sortColumn, sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
   	}
}