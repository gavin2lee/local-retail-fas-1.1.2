package cn.wonhigh.retail.fas.web.controller;

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

import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.vo.TreeJson;
import cn.wonhigh.retail.fas.manager.BrandManager;

import com.yougou.logistics.base.common.exception.ManagerException;
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
@RequestMapping("/brand")
public class BrandController extends BaseCrudController<Brand> {
    @Resource
    private BrandManager brandManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("brand/",brandManager);
    }
    
    @RequestMapping("/getJsonData")
    @ResponseBody
    public List<Brand> getJsonData() throws ManagerException{
    	return brandManager.findByBiz(null, null);
    }
    
    /**
	 * 转到查询结算公司(多选)
	 * @return
	 */
	@RequestMapping("/toMultiSearch")
	public ModelAndView toMultiSearch(HttpServletRequest req) {
		String params = StringUtils.isEmpty(req.getParameter("params")) ? "" : String.valueOf(req.getParameter("params"));
		ModelAndView view=new ModelAndView();
		view.setViewName("brand/multiSearch");
		view.addObject("params", params);
		return view;
	}
	
	@RequestMapping("/selectBrandWithBrandUnit")
    @ResponseBody
	public List<TreeJson> selectBrandWithBrandUnit(HttpServletRequest req, Model model) throws ManagerException {
		
		Map<String, Object> params = builderParams(req, model);
		List<TreeJson> result = brandManager.selectBrandWithBrandUnit(params);
		return result;
	}
}