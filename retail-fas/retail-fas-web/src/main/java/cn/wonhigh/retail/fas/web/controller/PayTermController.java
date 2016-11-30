package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.enums.PayTermTypeEnums;
import cn.wonhigh.retail.fas.common.model.PayTerm;
import cn.wonhigh.retail.fas.manager.PayTermManager;
import cn.wonhigh.retail.fas.manager.PayTermSupplierManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-18 16:58:07
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
@RequestMapping("/pay_term")
@ModuleVerify("40001001")
public class PayTermController extends BaseCrudController<PayTerm> {
    @Resource
    private PayTermManager payTermManager;

    @Resource
    private PayTermSupplierManager payTermSupplierManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("pay_term/",payTermManager);
    }
    
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export_data")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List dataList = payTermManager.findByPage(page, "", "", params);
		for (Object obj : dataList) {
			PayTerm term = (PayTerm)obj;
			term.setTermType(PayTermTypeEnums.getNameByNo(Integer.parseInt(term.getTermType())));
		}
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}

	/**
	 * 检查是否只读
	 * @param req
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkIsReadonly")
	@ResponseBody
	public Boolean checkIsReadonly(HttpServletRequest req,Model model, HttpServletResponse response)throws Exception{
		String termNo = req.getParameter("termNo");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("termNo", termNo);
		int count = payTermSupplierManager.findCount(params);
		if(count >0){
			return true;
		}
		return false;
	}
}