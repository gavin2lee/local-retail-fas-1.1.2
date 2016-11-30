/**  
* 项目名称：retail-fas-web  
* 类名称：AreaPrivatePurchaseController  
* 类描述：自购入库明细表
* 创建人：ouyang.zm  
* 创建时间：2014-9-24 上午9:53:47  
* @version       
*/ 
package cn.wonhigh.retail.fas.web.controller;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.SelfPurchaseDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.AreaPrivatePurchaseManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;
@Controller
@RequestMapping(value="/area_private_purchase")
@ModuleVerify("30134001")
public class AreaPrivatePurchaseController extends BaseCrudController<BillBuyBalance> {
	@Resource
	private   AreaPrivatePurchaseManager areaPrivatePurchaseManager;
	@Resource
	private   FinancialAccountManager financialAccountManager;
	@Override
	public CrudInfo init() {
		return new CrudInfo("area_private_purchase/", areaPrivatePurchaseManager);
	}
	
	/**
	 * 地区自购入库明细表
	 * @return
	 */
	@RequestMapping(value = "/purchase_in_list")
	public String toStorageIn() {
		return "area_purchase_balance/purchase_storage_list";
	}
	
	/**
	 * 参数设置
	 * @param req
	 * @param map
	 * @return
	 */
	public Map<String, Object> setParams(HttpServletRequest req,Map<String, Object> map){
		String brandUnitNos = "";
		if (map.get("brandUnitNoLike") != null) {
			brandUnitNos = FasUtil.formatInQueryCondition(map.get("brandUnitNoLike").toString());
		}
		String brandNos = "";
		if (map.get("brandNoLike") != null) {
			brandNos = FasUtil.formatInQueryCondition(map.get("brandNoLike").toString());
		}
		map.put("brandUnitNoLike", brandUnitNos);
		map.put("brandNoLike", brandNos);
		return map;
	}
	
	/**
	 * 自购入库明细查询
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
		//设置选单操作传的参数
		String balanceFlag = StringUtils.isEmpty(req.getParameter("balanceNoFlag")) ? "" : String.valueOf(req.getParameter("balanceNoFlag"));
		String withoutDiff = StringUtils.isEmpty(req.getParameter("withoutDiff")) ? "" : String.valueOf(req.getParameter("withoutDiff"));
		Map<String, Object> params = builderParams(req, model);
		setParams(req,params);
		params.put("balanceNoFlag", balanceFlag);
		params.put("withoutDiff", withoutDiff);
		//过滤承担总部职能的结算公司
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(companyNos)) {
			params.put("queryCondition", "AND B.buyer_no NOT IN (" + companyNos + ") and B.saler_no not in ("+companyNos+")");
		}
		int total = this.areaPrivatePurchaseManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillBuyBalance> list = this.areaPrivatePurchaseManager.findByPage(page, sortColumn, sortOrder, params);
		List<BillBuyBalance> totalList=this.areaPrivatePurchaseManager.findTotalRow(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		if(totalList.size()>0){
			obj.put("footer", totalList);
		}
		return obj;
	}
	
	/**
	 * 导出
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
		setParams(req,params);
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		if(StringUtils.isNotEmpty(companyNos)){
			params.put("queryCondition", "AND B.buyer_no NOT IN ("+companyNos +") and B.saler_no not in ("+companyNos+")");
		}
		int total = this.areaPrivatePurchaseManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<BillBuyBalance> dataList = this.areaPrivatePurchaseManager.findByPage(page, sortColumn, sortOrder, params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
	
	/**
	 * 导入 更新单价
	 * @param request
	 * @param modelType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_import")
	public ModelAndView doImport(HttpServletRequest request, SelfPurchaseDto modelType)throws Exception{
	    ModelAndView mv = new ModelAndView("/print/import");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile excelFile = multipartRequest.getFile("fileData");
		Class<SelfPurchaseDto> entityClass = (Class<SelfPurchaseDto>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		List<String> ignoreProperties = Lists.newArrayList();
		for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,entityClass.getSimpleName()).entrySet()) {
			ignoreProperties.add(entry.getKey());
		}
		List<SelfPurchaseDto> datas = ExcelImporterUtils.importSheet(excelFile.getInputStream(), SelfPurchaseDto.class,entityClass.getSimpleName());
		//判断必填项
		int lineNum = 2;
		String errorMessage = "";
		List<Object> errors = new ArrayList<Object>();
		if (datas != null && datas.size() > 0) {
			for (SelfPurchaseDto pDto : datas) {
				if (pDto.getOriginalBillNo() == null || pDto.getCost() == null || pDto.getItemCode() == null) {
					errorMessage = "第" + lineNum + "行数据有空记录";
					errors.add(errorMessage);
					lineNum++;
					continue;
				}
			}
		}
		StringBuffer errorTips = new StringBuffer();
		if (null != errors && errors.size() > 0) {
			for (int i = 0; i < errors.size(); i++) {
				errorTips.append(errors.get(i) + "<br/>");
			}
			mv.addObject("error", errorTips);
			return mv;
		}
		
		//循环取数，更新单价
		if (datas != null && datas.size() > 0) {
			int total = 0;
			for (SelfPurchaseDto dto : datas) {
				int count = areaPrivatePurchaseManager.modifyCost(dto);
				total += count;
			}
			if (total > 0) {
				mv.addObject("success", "成功更新" + total + "条记录！");
			} else {
				mv.addObject("error", "没有可更新的单据！");
			}
		} else {
			mv.addObject("error", "没有单据信息，不能更新单价！");
		}
		return mv;
	}
}
