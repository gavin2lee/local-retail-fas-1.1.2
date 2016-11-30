package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSet;
import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSetDtl;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.InvoiceTemplateSetManager;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:37
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
@RequestMapping("/invoice_template_set")
@ModuleVerify("30140009")
public class InvoiceTemplateSetController extends BaseController<InvoiceTemplateSet> {
    @Resource
    private InvoiceTemplateSetManager invoiceTemplateSetManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("invoice_template_set/",invoiceTemplateSetManager);
    }
    
    @RequestMapping("/list")
   	public String list() {
   		return "mallshop_balance/invoice_template_set";
   	}
    
    /**
 	 * 新增/修改(主表和明细数据)
 	 * 
 	 * @param model 待新增的主表数据
 	 * @param request HttpServletRequest
 	 * @return ResponseEntity
     * @throws ManagerException 
 	 */
 	@RequestMapping(value = "/save_all")
 	@ResponseBody
 	public Map<String, Boolean> saveAll(@ModelAttribute("model") InvoiceTemplateSet model, 
 			HttpServletRequest request) throws ManagerException {
 		Map<String, Boolean> map = new HashMap<String, Boolean>();
 		try {
 			SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
 			if(StringUtils.isNotBlank(model.getId())) {
 				model.setUpdateUser(loginUser.getUsername());
 				model.setUpdateTime(DateUtil.getCurrentDateTime());
 			} else {
 				model.setCreateUser(loginUser.getUsername());
 				model.setCreateTime(DateUtil.getCurrentDateTime());
 			}
 			JsonUtil<InvoiceTemplateSetDtl> util = new JsonUtil<InvoiceTemplateSetDtl>();
 			Map<CommonOperatorEnum, List<InvoiceTemplateSetDtl>> params = util.convertToMap(request, 
 					InvoiceTemplateSetDtl.class);
 			invoiceTemplateSetManager.save(model, params);
 			
 			map.put("success", true);
 		} catch(Exception e) {
 			map.put("success", false);
 			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
 		}
 		return map;
 	}
 	
 	/**
   	 * 删除
   	 * @return
   	 * @throws Exception 
   	 */
   	@RequestMapping(value = "/deleteInvoiceTemplateSet")
   	@ResponseBody
   	public Integer remove(@RequestParam("idList")String strIds) throws Exception{
   		String[] ids = strIds.split(";");
   		return  invoiceTemplateSetManager.remove(ids);
   	}

   	/**
	 * 校验是否可被修改、删除
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/check_update")
    @ResponseBody
    public Map<String, Object> checkUpdate(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("isUpdate", invoiceTemplateSetManager.checkIsUseData(request.getParameter("invoiceTempNo")));
		String invoiceTempNo = request.getParameter("invoiceTempNo");
		int useTempSet = invoiceTemplateSetManager.checkIsUseData(invoiceTempNo);
		result.put("useTempSetCount", useTempSet);
		return result;
    }
}