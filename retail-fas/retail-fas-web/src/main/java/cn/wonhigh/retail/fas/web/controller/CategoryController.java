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
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.manager.CategoryManager;

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
@RequestMapping("/category")
public class CategoryController extends BaseCrudController<Category> {
	
    @Resource
    private CategoryManager categoryManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("category/",categoryManager);
    }
    
    @RequestMapping("/select")
    public ModelAndView select(HttpServletRequest request) {
    	String levelid = request.getParameter("levelid");
    	String existCategoryNos = request.getParameter("existCategoryNos"); 
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("category/select");
    	mav.addObject("levelid", levelid);
    	mav.addObject("existCategoryNos", existCategoryNos);
    	return mav;
    }
    
    @RequestMapping("/getJsonData")
    @ResponseBody
    public List<Category> getJsonData(HttpServletRequest req) throws ManagerException {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("levelid", 1);
    	return categoryManager.findByBiz(null, params);
    }
    
    @RequestMapping("/single_select")
    public ModelAndView singleSelect(HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("category/singleSelect");
    	return mav;
    }
    
    @RequestMapping("/multi_select")
    public ModelAndView multiSelect(HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
    	String existCategoryNos = request.getParameter("existCategoryNos");
    	mav.addObject("existCategoryNos", existCategoryNos);
    	mav.setViewName("category/multiSelect");
    	return mav;
    }
    
    /**
     * 过滤已经存在的结算大类
     * @param request HttpServletRequest
     * @return 结算大类集合
     * @throws Exception 异常
     */
    @RequestMapping("/filter_exist_category")
    @ResponseBody
    public Map<String, Object> filterExistCategory(HttpServletRequest request, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));
		int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 10 : Integer.parseInt(request.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(request.getParameter("sort")) ? "" : String.valueOf(request.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(request.getParameter("order")) ? "" : String.valueOf(request.getParameter("order"));
		Map<String, Object> params = builderParams(request, model);
		String existCategoryNos = request.getParameter("existCategoryNos");
		if(StringUtils.isNotEmpty(existCategoryNos)) {
			params.put("existCategoryNos", existCategoryNos.split(","));
		}
		int total = this.categoryManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<Category> list = this.categoryManager.findByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
    }
    
	@RequestMapping("/getCombotree")
    @ResponseBody
    public List<Map<String, Object>> getCombotree() throws ManagerException {
    	List<Map<String, Object>> list = new ArrayList<>();
    	Map<String, Object> params = new HashMap<>();
    	params.put("levelid", 1);
    	List<Category> lstRootCategory = categoryManager.findByBiz(null, params);
    	List<Category> lstCategory = categoryManager.findByBiz(null, null);
    	String rootCategoryNo = "";
    	String categoryNo = "";
    	for (Category rootCategory : lstRootCategory) {
    		rootCategoryNo = rootCategory.getCategoryNo();
			Map<String, Object> map = new HashMap<>();
			map.put("id", rootCategoryNo);
			map.put("categoryNo", rootCategoryNo);
			map.put("text", rootCategory.getName());
			map.put("state", "closed");
			List<Map<String, Object>> lstChild = new ArrayList<>();
			for (Category category : lstCategory) {
				categoryNo = category.getCategoryNo();
				if(categoryNo.length() > 2){
					if(categoryNo.substring(0, 2).equals(rootCategoryNo)){
						this.setChildCategory(category ,lstChild);
					}
				}
			}
			map.put("children", lstChild);
			list.add(map);
		}
    	
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	private void setChildCategory(Category childCategory, List<Map<String, Object>> lstChild){
    	String categoryNo = childCategory.getCategoryNo();
    	// 二级
    	if(categoryNo.length() ==4){
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", categoryNo);
			map.put("categoryNo", categoryNo);
			map.put("text", childCategory.getName());
			map.put("children",null);
			lstChild.add(map);
    		return ;
    	}
    	// 三级
    	for (Map<String, Object> map : lstChild) {
			if(String.valueOf(map.get("categoryNo")).equals(categoryNo.substring(0, 4))){
				List<Map<String, Object>> lstItem = (List<Map<String, Object>>) map.get("children");
				Map<String, Object> newMap = new HashMap<>();
				newMap.put("id", categoryNo);
				newMap.put("categoryNo", categoryNo);
				newMap.put("text", childCategory.getName());
				if(null == lstItem){
					lstItem = new ArrayList<>();
					lstItem.add(newMap);
					map.put("children", lstItem);
					map.put("state", "closed");
				}else{
					lstItem.add(newMap);
				}
			}
		}
    	
    }
    
}