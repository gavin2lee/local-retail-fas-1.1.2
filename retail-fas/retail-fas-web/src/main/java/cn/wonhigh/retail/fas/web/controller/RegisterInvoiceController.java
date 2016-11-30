package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.RegisterInvoice;
import cn.wonhigh.retail.fas.manager.RegisterInvoiceManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-22 13:51:56
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
@RequestMapping("/register_invoice")
@ModuleVerify("30170009")
public class RegisterInvoiceController extends ExcelImportController<RegisterInvoice> {
    @Resource
    private RegisterInvoiceManager registerInvoiceManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("IndepShop_management/register_invoice/",registerInvoiceManager);
    }

	@Override
	protected String[] getImportProperties() {
		// TODO Auto-generated method stub
		return new String[]{"shopNo","shopName","createTime","shouldAmount","actualAmount","remark"};
	}

	@Override
	protected boolean doBatchAdd(List<RegisterInvoice> list) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Map<String, Object> queryList(HttpServletRequest req, Model model)
			throws ManagerException {
		
		Map<String, Object> params = builderParams(req, model);
		Map<String, Object> obj = registerInvoiceManager.queryRegisterInvoiceListRemote(params);
		if(obj == null || obj.isEmpty()){
			return null;
		}
		return obj;
	}
	
	/**
	 * 店铺终端账号查询
	 */
	@RequestMapping(value = "/findByBillNo.json")
	@ResponseBody
	public Map<String, Object> findByBillNo(HttpServletRequest req, Model model){
		Map<String, Object> params = builderParams(req, model);
		Map<String, Object> obj = new HashMap<String, Object>();
		List<RegisterInvoice> list = registerInvoiceManager.findByBillNo(params);
		obj.put("total", list.size());
		obj.put("rows", list);
		return obj;
	}
	
	@Override
	protected List<RegisterInvoice> queryExportData(Map<String, Object> params)
			throws ManagerException {
		// TODO Auto-generated method stub
		
		List<RegisterInvoice> registerInvoiceList = null;
		
		params.put("pageSize", Integer.MAX_VALUE);
		params.put("pageNumber", 1);
		Map<String, Object> obj = registerInvoiceManager.queryRegisterInvoiceListRemote(params);
		if(obj != null && !obj.isEmpty()){
			registerInvoiceList = (List<RegisterInvoice>) obj.get("rows");
		}
		return registerInvoiceList;
	}
	
}