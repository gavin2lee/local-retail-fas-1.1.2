/**
 * title:OweTheGuestController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:欠客销售跟踪表
 * auther:user
 * date:2015-7-1 下午5:25:41
 */
package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.OweTheGuestInventoryDto;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.OweTheGuestManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

@Controller
@RequestMapping("/owe_the_guest")
@ModuleVerify("30510404") 
public class OweTheGuestController extends	BaseCrudController<OweTheGuestInventoryDto> {
	@Resource
	private OweTheGuestManager oweTheGuestManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("owe_the_guest/", oweTheGuestManager);
	}
	
	/**
	 * 本期欠客明细
	 * @return
	 */
	@RequestMapping(value = "/dtl_list")
	public String toListDtl() {
		return "oweTheGuest/current_list";
	}
	
	/**
	 * 前期欠客本期发出
	 * @return
	 */
	@RequestMapping(value = "/list_tabMain")
	public String mainTab() {
		return "oweTheGuest/earlier_list";
	}
	
	/**
	 * 参数设置
	 * @param req
	 * @param map
	 * @return
	 */
	public Map<String, Object> setParams(HttpServletRequest req,Map<String, Object> map){
		String shopNos = "";
		if (map.get("shopNo") != null) {
			shopNos = FasUtil.formatInQueryCondition(map.get("shopNo").toString());
		}
		String brandNos="";
		if(map.get("brandNo")!=null){
			brandNos = FasUtil.formatInQueryCondition(map.get("brandNo").toString());
		}
		map.put("shopNo", shopNos);
		map.put("brandNo", brandNos);
		return map;
	}
	
	/**
	 * 明细查询
	 */
	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		setParams(req,params);
		int total = this.oweTheGuestManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<OweTheGuestInventoryDto> list = this.oweTheGuestManager.findByPage(page, sortColumn, sortOrder, params);
		List<OweTheGuestInventoryDto> totalList=this.oweTheGuestManager.findTotalRow(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		if(totalList.size()>0){
			obj.put("footer", totalList);
		}
		return obj;
	}
	
	/**
	 * 明细导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		//判断导出哪个明细表
		String flag = StringUtils.isEmpty(req.getParameter("flag")) ? "" : String.valueOf(req.getParameter("flag"));
		if(flag.equals("current")){
			params.put("resultFlag", "current");
		}else if(flag.equals("earlier")){
			params.put("resultFlag", "earlier");
		}
		setParams(req,params);
		int total = this.oweTheGuestManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<OweTheGuestInventoryDto> dataList = this.oweTheGuestManager.findByPage(page, sortColumn, sortOrder, params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}

}
