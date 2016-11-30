package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import cn.wonhigh.retail.fas.manager.HqShipmentColletManager;

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
@RequestMapping("/hq_return_supplier")
public class HqReturnSupplierController extends BaseController<HqShipmentCollet> {
    @Resource
    private HqShipmentColletManager hqShipmentColletManger;
	
    @Override
    public CrudInfo init() {
        return new CrudInfo("hq_return_supplier/",hqShipmentColletManger);
    }
    
    @RequestMapping("/list")
   	public String list() {
   		return "hq_return_supplier/collet_list";
   	}

    @RequestMapping("/to_detail")
	public String toDetail() {
		return "hq_return_supplier/detail_list";
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
		//设置多选条件
		setMultipleParams(params);
		String exportType = (String)params.get("exportType");
		List<HqShipmentCollet> hqShipmentColletList = null;
		if("1".equals(exportType)){
			hqShipmentColletList = hqShipmentColletManger.findReturnSupplierList(params, null);
		}else{
			hqShipmentColletList = hqShipmentColletManger.findRecallSupplierList(params, null);
		}
		return hqShipmentColletList;
	}

	@RequestMapping(value = "/find_recall_list")
	@ResponseBody
	public Map<String, Object> findReturnSupplierDetailList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> maps = new HashMap<String, Object>();
		
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		//设置多选条件
		setMultipleParams(params);
//		String isHq = params.get("isHQ") == null ? "" : params.get("isHQ").toString();
   		List<HqShipmentCollet> footer = new ArrayList<HqShipmentCollet>();
   		HqShipmentCollet hqShipmentCollet = hqShipmentColletManger.findRecallSupplierCount(params);
   		List<HqShipmentCollet> list = new ArrayList<HqShipmentCollet>();
   		if(null != hqShipmentCollet ){
   			SimplePage page = new SimplePage(pageNo, pageSize, hqShipmentCollet.getTotal());
   			list = hqShipmentColletManger.findRecallSupplierList(params, page);
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
	
	
	@RequestMapping(value = "/find_return_list")
	@ResponseBody
	public Map<String, Object> findReturnSupplierColletList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> maps = new HashMap<String, Object>();
		
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		//设置多选条件
		setMultipleParams(params);
   		List<HqShipmentCollet> footer = new ArrayList<HqShipmentCollet>();
   		HqShipmentCollet hqShipmentCollet = hqShipmentColletManger.findReturnSupplierCount(params);
   		List<HqShipmentCollet> list = new ArrayList<HqShipmentCollet>();
   		if(null != hqShipmentCollet ){
   			SimplePage page = new SimplePage(pageNo, pageSize, hqShipmentCollet.getTotal());
   			list = hqShipmentColletManger.findReturnSupplierList(params, page);
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
	 * 设置多选择的查询条件
	 * @param params
	 */
	private void setMultipleParams(Map<String, Object> params){
		// 管理城市
		String organNo = params.get("organNo") == null ? "" : params.get("organNo").toString();
		List<String> organNoList = new ArrayList<String>();
		if (StringUtils.isNotBlank(organNo)) {
			if (organNo.contains(",")) {
				String[] organNos = organNo.split(",");
				for (String organNoTemp : organNos) {
					organNoList.add(organNoTemp);
				}
			} else {
				organNoList.add(organNo);
			}

			params.put("organNos", organNoList);
		}

		// 大类
		String categoryNo = params.get("categoryNo") == null ? "" : params.get("categoryNo").toString();
		List<String> categoryNoList = new ArrayList<String>();
		if (StringUtils.isNotBlank(categoryNo)) {
			if (categoryNo.contains(",")) {
				String[] categoryNos = categoryNo.split(",");
				for (String categoryNoTemp : categoryNos) {
					categoryNoList.add(categoryNoTemp);
				}
			} else {
				categoryNoList.add(categoryNo);
			}

			params.put("categoryNos", categoryNoList);
		}

		String brandNo = params.get("brandNo") == null ? "" : params.get("brandNo").toString();
		List<String> brandNoList = new ArrayList<String>();
		if (StringUtils.isNotBlank(brandNo)) {
			if (brandNo.contains(",")) {
				String[] brandNos = brandNo.split(",");
				for (String brandNoTemp : brandNos) {
					brandNoList.add(brandNoTemp);
				}
			} else {
				brandNoList.add(brandNo);
			}
			params.put("brandNos", brandNoList);
		}

		// 公司
		String companyNo = params.get("salerNo") == null ? "" : params.get("salerNo").toString();
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

			params.put("salerNos", companyNoList);
			params.put("salerNo", null);
		}
		List<String> tempList = null;
		if (params.get("businessType") != null && !"".equals(params.get("businessType").toString())) {
			String[] temps = params.get("businessType").toString().split(",");
			tempList = Arrays.asList(temps);
		}
		params.put("businessTypes", tempList);
	}
	
}