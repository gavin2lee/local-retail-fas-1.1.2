package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.WholesaleMarginInit;
import cn.wonhigh.retail.fas.manager.WholesaleMarginInitManager;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;


/**
 * 客户保证金及预收款初始化
 * @author admin
 * @date  2014-09-19 15:00:52
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
@RequestMapping("/wholesale_margin_init")
public class WholesaleMarginInitController extends BaseController<WholesaleMarginInit> {
	
	private Logger logger = Logger.getLogger(WholesaleMarginInitController.class);
   
	@Resource
    private WholesaleMarginInitManager wholesaleMarginInitManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("wholesale_margin_init/",wholesaleMarginInitManager);
    }
    
    /**
	 * 转到弹出页面
	 * @return String
	 */
	@RequestMapping("/select")
	public String select() {
		return "wholesale_margin_init/select";
	}
	
	@RequestMapping("/finish_bill")
	@ResponseBody
	public Boolean finishBill(HttpServletRequest request) {
		try {
			String finishedList = StringUtils.isEmpty(request.getParameter("finished")) ? "" 
					: request.getParameter("finished");
			JsonUtil<WholesaleMarginInit> util = new JsonUtil<WholesaleMarginInit>();
			List<WholesaleMarginInit> list = util.convertListWithTypeReference(finishedList,
					request, WholesaleMarginInit.class);
			return wholesaleMarginInitManager.finishBill(list);
		} catch(Exception e) {
			logger.debug(e.getMessage(), e);
		}
		return null;
	}
	
	/**
     * 校验数据
     * @param id 主键
     * @param styleNo 编码
     * @return boolean true or false
     * @throws Exception  异常
     */
    @RequestMapping(value="/validate_data", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validateData( HttpServletRequest request)
    		throws Exception {
    	Map<String, Object> result = new HashMap<String, Object>();
    	String id = request.getParameter("id");
    	String companyNo = request.getParameter("companyNo");
    	String customerNo = request.getParameter("customerNo");
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", companyNo);
		params.put("customerNo", customerNo);
		List<WholesaleMarginInit> list = wholesaleMarginInitManager.findByBiz(null, params);
		if(list != null && list.size() > 0
				&& !list.get(0).getId().toString().equals(id)) {
			result.put("success", false);
			result.put("message", "同一个公司和客户只能对应一条数据");
			return result;
		}
		result.put("success", true);
		return result;
    }
}