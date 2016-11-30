package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.SettleNewStyleDto;
import cn.wonhigh.retail.fas.manager.SettleNewStyleManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 新旧款查询
 * @author yang.y
 * @date  2014-08-06 15:33:47
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
@RequestMapping("/settle_new_style_query")
public class SettleNewStyleQueryController extends BaseController<SettleNewStyleDto> {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(SettleNewStyleQueryController.class); 
	
    @Resource
    private SettleNewStyleManager settleNewStyleManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("settle_new_style_query/",settleNewStyleManager);
    }
    
    /**
     * 进入新旧款查询页面
     * 
     * @return String
     */
    @RequestMapping("/list")
    public String list() {
    	return "settle_new_style/query_page";
    }
    
    /**
     * 分页查询新旧款数据，需关联明细数据
     * 
     * @param model Model
     * @param request HttpServletRequest
     * @return 存放total和row数据的map
     */
    @RequestMapping("/query.json")
    @ResponseBody
    public Map<String, Object> query(Model model, HttpServletRequest request) {
    	try {
	    	int pageNo = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));
			int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 10 : Integer.parseInt(request.getParameter("rows"));
			String sortColumn = StringUtils.isEmpty(request.getParameter("sort")) ? "" : String.valueOf(request.getParameter("sort"));
			String sortOrder = StringUtils.isEmpty(request.getParameter("order")) ? "" : String.valueOf(request.getParameter("order"));
			Map<String, Object> params = builderParams(request, model);
			int total = settleNewStyleManager.findRelationCount(params);
			
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			List<SettleNewStyleDto> list = settleNewStyleManager.findRelationByPage(page, sortColumn, sortOrder, params);
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("total", total);
			obj.put("rows", list);
			return obj;
    	} catch (ManagerException e) {
    		logger.error("查询结算新旧款错误。。");
		}
    	return null;
    }
    
   /**
    * 重写导出时的查询数据方法
    * 
    * @param params 请求参数
    * @return List<BrandGroupDto>
    * @throws ManagerException 异常
    */
	@Override
	protected List<SettleNewStyleDto> queryExportData(Map<String, Object> params) throws ManagerException {
		int total = settleNewStyleManager.findRelationCount(params);
		SimplePage page = new SimplePage(1, total, (int) total);
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		List<SettleNewStyleDto> list = settleNewStyleManager.findRelationByPage(page, orderByField, orderBy, params);
		return list;
	} 
}