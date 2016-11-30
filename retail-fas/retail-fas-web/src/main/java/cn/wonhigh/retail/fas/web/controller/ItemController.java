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

import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.manager.ItemManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 商品资料
 * 
 * @author ouyang.zm
 * @date 2014-09-02 12:17:21
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Controller
@RequestMapping("/base_setting/item")
public class ItemController extends BaseCrudController<Item> {
	@Resource
	private ItemManager itemManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("base_setting/item/", itemManager);
	}

	/**
	 * 转到查询商品
	 * 
	 * @return
	 */
	@RequestMapping("/toSearchItem")
	public String toSearchCompany() {
		return "base_setting/item/searchItem";
	}

	@RequestMapping(value = "/chooseItem.json")
	@ResponseBody
	public Map<String, Object> chooseItem(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 50 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));

		Map<String, Object> params = builderParams(req, model);
		Map<String, Object> obj = new HashMap<String, Object>();
		List<Item> list = null;
		int total = 500;
		if (params.containsKey("q")) {
			if (StringUtils.isNotBlank(params.get("q").toString())) {
				// total =
				// this.itemManager.findComboGridDataCountByCondition(params,
				// null);
				if (total > 0) {
					SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
					list = this.itemManager.findComboGridDataByCondition(page, sortColumn, sortOrder, params, null);
				} else {
					list = new ArrayList<>();
				}
			}
		}
		if (list == null) {
			list = new ArrayList<>();
		}
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}

	@RequestMapping("/getStyle")
	@ResponseBody
	public Map<String, Object> getStyle(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req,model);
		this.setMulitParams(req, params);
		int total = this.itemManager.findStyleCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<Item> list = this.itemManager.findStyleList(page, sortColumn, sortOrder, params);
		List<String> styleNoList = new ArrayList<String>();
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		for (Item item : list) {
			LookupVo vo = new LookupVo();
			vo.setId(String.valueOf(item.getStyleNo()));
			vo.setCode(item.getStyleNo());
			styleNoList.add(item.getStyleNo());
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}
	
	private void setMulitParams(HttpServletRequest req, Map<String, Object> retParams){
		String multiq = req.getParameter("q");
		if(StringUtils.isNotBlank(multiq)){
			multiq = multiq.toUpperCase();
			if(multiq.indexOf(",")!=-1){
				retParams.clear();
				retParams.put("multiq", FasUtil.formatInQueryConditionByChar(multiq,","));
			}else if(multiq.indexOf("，")!=-1){
				retParams.clear();
				retParams.put("multiq", FasUtil.formatInQueryConditionByChar(multiq,"，"));
			}else if(multiq.indexOf(";")!=-1){
				retParams.clear();
				retParams.put("multiq", FasUtil.formatInQueryConditionByChar(multiq,";"));
			}else if(multiq.indexOf("；")!=-1){
				retParams.clear();
				retParams.put("multiq", FasUtil.formatInQueryConditionByChar(multiq,"；"));
			}else if(multiq.indexOf("/")!=-1){
				retParams.clear();
				retParams.put("multiq", FasUtil.formatInQueryConditionByChar(multiq,"/"));
			}
		}
	}
	@RequestMapping(value = "/chooseStyleNo.json")
	@ResponseBody
	public Map<String, Object> chooseStyle(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 50 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));

		Map<String, Object> params = builderParams(req, model);
		Map<String, Object> obj = new HashMap<String, Object>();
		List<Item> list = null;

		Integer total = this.itemManager.findStyleNoCount(params);

		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.itemManager.findStyleNo(page, sortColumn, sortOrder, params);
		} else {
			list = new ArrayList<>();
		}

		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}

}