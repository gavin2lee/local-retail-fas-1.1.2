package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.BillInvoiceApplyDto;
import cn.wonhigh.retail.fas.common.model.BillInvoiceApplyDtl;
import cn.wonhigh.retail.fas.manager.BillInvoiceApplyManager;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 地区批发-开票申请单
 * @author admin
 * @date  2014-09-16 14:05:12
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
@RequestMapping("/bill_invoice_apply")
public class BillInvoiceApplyController extends BaseController<BillInvoiceApplyDto> {
    
	@Resource
    private BillInvoiceApplyManager billInvoiceApplyManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_invoice_apply/",billInvoiceApplyManager);
    }
    
    @RequestMapping("/bill_tab")
    public String billTab() {
    	return "bill_invoice_apply/bill_tab";
    }
    
    /**
	 * 新增/修改(主表和明细数据)
	 * 
	 * @param model 待新增的主表数据
	 * @param request HttpServletRequest
	 * @return Map<String, Boolean>
     * @throws ManagerException 
	 */
	@RequestMapping(value = "/save_all")
	@ResponseBody
	public Map<String, Boolean> saveAll(@ModelAttribute("model") BillInvoiceApplyDto model, 
			HttpServletRequest request) throws ManagerException {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			this.setDefaulValues(model, request);
			JsonUtil<BillInvoiceApplyDtl> util = new JsonUtil<BillInvoiceApplyDtl>();
			Map<CommonOperatorEnum, List<BillInvoiceApplyDtl>> params = util.convertToMap(request, 
					BillInvoiceApplyDtl.class);
			billInvoiceApplyManager.save(model, params);
			map.put("success", true);
		} catch(Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
	}
}