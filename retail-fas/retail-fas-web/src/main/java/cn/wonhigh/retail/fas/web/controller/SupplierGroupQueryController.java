package cn.wonhigh.retail.fas.web.controller;

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

import cn.wonhigh.retail.fas.common.model.SupplierGroupRel;
import cn.wonhigh.retail.fas.manager.SupplierGroupRelManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-08-25 17:35:45
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
@RequestMapping("/supplier_group_query")
public class SupplierGroupQueryController extends BaseController<SupplierGroupRel> {
    @Resource
    private SupplierGroupRelManager supplierGroupRelManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("supplier_group_rel/",supplierGroupRelManager);
    }
    
    /**
     * 分页查询结算大类数据，需关联明细数据
     * 
     * @param model Model
     * @param request HttpServletRequest
     * @return 存放total和row数据的map
     * @throws ManagerException 
     */
    @RequestMapping("/query.json")
    @ResponseBody
    public Map<String, Object> query(Model model, HttpServletRequest request) throws ManagerException {
    	try {
	    	int pageNo = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));
			int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 10 : Integer.parseInt(request.getParameter("rows"));
			String sortColumn = StringUtils.isEmpty(request.getParameter("sort")) ? "" : String.valueOf(request.getParameter("sort"));
			String sortOrder = StringUtils.isEmpty(request.getParameter("order")) ? "" : String.valueOf(request.getParameter("order"));
			Map<String, Object> params = builderParams(request, model);
			int total = supplierGroupRelManager.findRelationCount(params);
			
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			List<SupplierGroupRel> list = supplierGroupRelManager.findRelationByPage(page, sortColumn, sortOrder, params);
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("total", total);
			obj.put("rows", list);
			return obj;
    	} catch (ManagerException e) {
    		logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
    }
    
   /**
    * 重写导出时的查询数据方法
    * 
    * @param params 请求参数
    * @return List<BrandGroupDto>
    * @throws ManagerException 异常
    */
	@Override
	protected List<SupplierGroupRel> queryExportData(Map<String, Object> params) throws ManagerException {
		int total = supplierGroupRelManager.findRelationCount(params);
		SimplePage page = new SimplePage(1, total, (int) total);
		List<SupplierGroupRel> list = supplierGroupRelManager.findRelationByPage(page, "", "", params);
		return list;
	} 
		
}