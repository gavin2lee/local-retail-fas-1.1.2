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

import cn.wonhigh.retail.fas.common.model.HqShipmentCollet;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.HqShipmentColletManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-10 14:40:44
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
@RequestMapping("/hq_shipment_collet")
@ModuleVerify("40001016")
public class HqShipmentColletController extends BaseController<HqShipmentCollet> {
    @Resource
    private HqShipmentColletManager hqShipmentColletManger;
    
    @Resource
    private FinancialAccountManager financialAccountManager;
	
    @Override
    public CrudInfo init() {
        return new CrudInfo("hq_shipment_collet/",hqShipmentColletManger);
    }
    
    @RequestMapping("/list")
   	public String list() {
   		return "hq_shipment_collet/list";
   	}

	@Override
	protected List<HqShipmentCollet> queryExportData(Map<String, Object> params) throws ManagerException {
//		String isHq = params.get("isHQ") == null ? "" : params.get("isHQ").toString();
//		String queryCondition = "AND balance_type in(2,7,14,99) ";
//   		if(StringUtils.isBlank(isHq)){
//   			queryCondition = "AND balance_type in(5,7,8,10,11,99) ";
//   		}
//   		params.put("queryCondition", queryCondition);
//		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		setCommonParams(params);
		List<HqShipmentCollet> hqShipmentColletList = hqShipmentColletManger.findHqShipmentColletByCondition(params, null);
		return hqShipmentColletList;
	}

	@RequestMapping(value = "/find_shipment_list")
	@ResponseBody
	public Map<String, Object> findInvoiceRegisterList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> maps = new HashMap<String, Object>();
		
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		setCommonParams(params);
//		String isHq = params.get("isHQ") == null ? "" : params.get("isHQ").toString();
   		List<HqShipmentCollet> footer = new ArrayList<HqShipmentCollet>();
   		HqShipmentCollet hqShipmentCollet = hqShipmentColletManger.findHqShipmentColletCount(params);
   		List<HqShipmentCollet> list = new ArrayList<HqShipmentCollet>();
   		if(null != hqShipmentCollet ){
   			SimplePage page = new SimplePage(pageNo, pageSize, hqShipmentCollet.getTotal());
   			list = hqShipmentColletManger.findHqShipmentColletByCondition(params, page);
   		}
   		if (null != hqShipmentCollet) {
   			hqShipmentCollet.setSupplierName("合计");
			footer.add(hqShipmentCollet);
		}
		maps.put("total", hqShipmentCollet.getTotal());
		maps.put("rows", list);
		maps.put("footer", footer);
		return maps;
	}
	
	/**
	 * 设置参数
	 * @param params
	 */
	private void setCommonParams(Map<String,Object> params){
		String hqCompanyNo = financialAccountManager.findLeadRoleCompanyNos();
		params.put("hqCompanyData",hqCompanyNo);
		// 公司
		String companyNo = params.get("buyerNo") == null ? "" : params.get("buyerNo").toString();
		List<String> companyNoList = new ArrayList<String>();
		if (StringUtils.isNotBlank(companyNo)) {
			if (companyNo.contains(",")) {
				String[] companyNos = companyNo.split(",");
				for (String companyNoTemp : companyNos) {
					companyNoList.add(companyNoTemp);
				}
			} else {
				companyNoList.add(companyNo);
			}

			params.put("buyerNos", companyNoList);
			params.put("buyerNo", null);
		}
		
		// 供应商
		String supplierNo = params.get("supplierNo") == null ? "" : params.get("supplierNo").toString();
		List<String> supplierNoList = new ArrayList<String>();
		if (StringUtils.isNotBlank(supplierNo)) {
			if (supplierNo.contains(",")) {
				String[] supplierNos = supplierNo.split(",");
				for (String supplierNoTemp : supplierNos) {
					supplierNoList.add(supplierNoTemp);
				}
			} else {
				supplierNoList.add(supplierNo);
			}

			params.put("supplierNos", supplierNoList);
			params.put("supplierNo", null);
		}
	}
	
}