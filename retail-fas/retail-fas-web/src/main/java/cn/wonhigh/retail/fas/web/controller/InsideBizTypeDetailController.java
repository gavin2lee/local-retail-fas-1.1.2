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

import cn.wonhigh.retail.fas.common.model.InsideBizTypeDetail;
import cn.wonhigh.retail.fas.manager.InsideBizTypeDetailManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-12 16:39:33
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
@RequestMapping("/inside_biz_type_detail")
public class InsideBizTypeDetailController extends BaseCrudController<InsideBizTypeDetail> {
    @Resource
    private InsideBizTypeDetailManager insideBizTypeDetailManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("inside_biz_type_detail/",insideBizTypeDetailManager);
    }
    
    @RequestMapping("/to_client_list")
	public String toClientDetail() {
		return "inside_biz_type/client_list";
	}
    
    @RequestMapping("/to_client_list_view")
   	public String toClientViewDetail() {
   		return "inside_biz_type/client_list_view";
   	}

    @RequestMapping(value = "/client_list")
	@ResponseBody
	public Map<String, Object> findClientDetailList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> maps = new HashMap<String, Object>();
		
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
//		String isHq = params.get("isHQ") == null ? "" : params.get("isHQ").toString();
   		List<InsideBizTypeDetail> list = new ArrayList<InsideBizTypeDetail>();
   		int count = insideBizTypeDetailManager.findCount(params);
   		if(count > 0 ){
   			SimplePage page = new SimplePage(pageNo, pageSize, count);
   			list = insideBizTypeDetailManager.findByPage(page, null, null,params);
   		}
		maps.put("total", count);
		maps.put("rows", list);
		return maps;
	}
	
    
    
}