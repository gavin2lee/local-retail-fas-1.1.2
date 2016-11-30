package cn.wonhigh.retail.fas.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.ReportDto;
import cn.wonhigh.retail.fas.common.dto.ReportFinanceDto;
import cn.wonhigh.retail.fas.common.dto.ReportGatherDto;
import cn.wonhigh.retail.fas.common.dto.ReportInventoryDto;
import cn.wonhigh.retail.fas.common.dto.ReportTransferDto;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.ReportManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-25 11:34:16
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
@RequestMapping("/report")
public class ReportController {
	protected static final XLogger logger = XLoggerFactory.getXLogger(BaseController.class);

    @Resource
    private ReportManager reportManager;
    
	@Resource
	private FinancialAccountManager financialAccountManager;
	
	private  Map<String, String> exportFlagMap = new HashMap<String, String>();
	
    @RequestMapping(method = RequestMethod.GET ,value ="/list")
    public ModelAndView list(HttpServletRequest req) {
    	ModelAndView mav = new ModelAndView();
    	String type = req.getParameter("type");
    	if(StringUtils.isNotBlank(type)){
    		if(type.equals("1")){
        		mav.setViewName("report/report_detail");
        	}else if(type.equals("2")){
        		mav.setViewName("report/report_gather");
        	}else if(type.equals("3")){
        		mav.setViewName("report/report_detail_area");
        	}else if(type.equals("4")){
        		mav.setViewName("report/report_gather_area");
        	}else if(type.equals("5")){
        		mav.setViewName("report/report_business");
        	}else if(type.equals("6")){
        		mav.setViewName("report/report_finance");
        	}else if(type.equals("7")){
        		mav.setViewName("report/report_inventory");
        	}else if(type.equals("8")){
        		mav.setViewName("report/report_all_finance");
        	}else if(type.equals("9")){
        		mav.setViewName("report/report_transfer");
        	}
    		mav.addObject("type", type);
    	}
    	return mav;
    }
    
	@RequestMapping(value = "/column_list.json")
	@ResponseBody
	public  Map<String, Object> queryColumnList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		params.put("zoneCompanyNo", companyNos);
		SimplePage page = new SimplePage(pageNo, pageSize, Integer.MAX_VALUE);
		List<ReportDto> lstItem = reportManager.findColumn(page, params);
		if(lstItem.size() > 0){
			Map<String, Object> obj = new HashMap<String, Object>();
			Map<String, Integer> zoneData = new HashMap<String, Integer>();
			Map<String, Integer> organData = new HashMap<String, Integer>();
			List<ReportDto> lstZone = new ArrayList<ReportDto>();
			List<ReportDto> lstOrgan = new ArrayList<ReportDto>();
			for (ReportDto reportDto : lstItem) {
				if(null != zoneData.get(reportDto.getZoneNo())){
					Integer index = zoneData.get(reportDto.getZoneNo());
					zoneData.put(reportDto.getZoneNo(), index+1);
				}else{
					zoneData.put(reportDto.getZoneNo(), 1);
					ReportDto zoneDto = new ReportDto();
					zoneDto.setZoneNo(reportDto.getZoneNo());
					zoneDto.setZoneName(reportDto.getZoneName());
					lstZone.add(zoneDto);
				}
				if(null != organData.get(reportDto.getOrganNo())){
					Integer index = organData.get(reportDto.getOrganNo());
					organData.put(reportDto.getOrganNo(), index+1);
				}else{
					organData.put(reportDto.getOrganNo(), 1);
					ReportDto organDto = new ReportDto();
					organDto.setOrganNo(reportDto.getOrganNo());
					organDto.setOrganName(reportDto.getOrganName());
					lstOrgan.add(organDto);
				}
			}
			for (ReportDto zoneDto : lstZone) {
				zoneDto.setIndex(zoneData.get(zoneDto.getZoneNo()));
			}
			for (ReportDto organDto : lstOrgan) {
				organDto.setIndex(organData.get(organDto.getOrganNo()));
			}
			obj.put("orderUnitColumn", lstItem);
			obj.put("organColumn", lstOrgan);
			obj.put("zoneColumn", lstZone);
			return obj;
		}
		return null;
	}

	/**
	 * 总部厂商明细对账表
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report_detail.json")
	@ResponseBody
	public  Map<String, Object> queryReportDetailList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 0 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		params.put("zoneCompanyNo", companyNos);
		int total = reportManager.findReportCount(params);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listFooter = new ArrayList<Map<String, Object>>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			if(StringUtils.isNotBlank(req.getParameter("exportFlag"))){
				page = new SimplePage(0, total, total);
			}
			list = reportManager.findReportByPage(page, sortColumn, sortOrder, params);
			listFooter = reportManager.findReportFooter(page, params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", listFooter);
		return obj;
	}
	
	/**
	 * 总部厂商汇总对账表
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report_gather.json")
	@ResponseBody
	public  Map<String, Object> queryReportGatherList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 0 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		params.put("zoneCompanyNo", companyNos);
		int total = reportManager.findReportGatherCount(params);
		List<ReportGatherDto> list = new ArrayList<ReportGatherDto>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			if(StringUtils.isNotBlank(req.getParameter("exportFlag"))){
				page = new SimplePage(0, total, total);
			}
			list = reportManager.findReportGatherByPage(page, sortColumn, sortOrder, params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 总部地区明细对账表
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report_detail_area.json")
	@ResponseBody
	public  Map<String, Object> queryReportDetailAreaList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 0 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		params.put("zoneCompanyNo", companyNos);
		int total = reportManager.findReportDetailCount(params);
		List<ReportDto> list = new ArrayList<ReportDto>();
		List<ReportDto> footerlist = new ArrayList<ReportDto>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			if(StringUtils.isNotBlank(req.getParameter("exportFlag"))){
				page = new SimplePage(0, total, total);
			}
			list = reportManager.findReportDetailByPage(page, sortColumn, sortOrder, params);
			footerlist = reportManager.findReportDetailFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", footerlist);
		return obj;
	}
	
	/**
	 * 总部地区汇总对账表
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report_gather_area.json")
	@ResponseBody
	public  Map<String, Object> queryReportGatherAreaList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 0 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		params.put("zoneCompanyNo", companyNos);
		int total = reportManager.findReportGatherAreaCount(params);
		List<ReportGatherDto> list = new ArrayList<ReportGatherDto>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			if(StringUtils.isNotBlank(req.getParameter("exportFlag"))){
				page = new SimplePage(0, total, total);
			}
			list = reportManager.findReportGatherAreaByPage(page, sortColumn, sortOrder, params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 总部业务对账表
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report_business.json")
	@ResponseBody
	public  Map<String, Object> queryReportBusinessList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 0 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		params.put("zoneCompanyNo", companyNos);
		int total = reportManager.findReportBusinessCount(params);
		List<ReportDto> list = new ArrayList<ReportDto>();
		List<ReportDto> footerlist = new ArrayList<ReportDto>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			if(StringUtils.isNotBlank(req.getParameter("exportFlag"))){
				page = new SimplePage(0, total, total);
			}
			list = reportManager.findReportBusinessByPage(page, sortColumn, sortOrder, params);
			footerlist.add(list.get(list.size()-1));
			list.remove(list.size()-1);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", footerlist);
		return obj;
	}

	/**
	 * 财务明细核对表
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report_finance.json")
	@ResponseBody
	public  Map<String, Object> queryReportFinanceList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 0 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		params.put("zoneCompanyNo", companyNos);
		int total = reportManager.findReportFinanceCount(params);
		List<ReportDto> list = new ArrayList<ReportDto>();
		List<ReportDto> footerlist = new ArrayList<ReportDto>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			if(StringUtils.isNotBlank(req.getParameter("exportFlag"))){
				page = new SimplePage(0, total, total);
			}
			list = reportManager.findReportFinanceByPage(page, sortColumn, sortOrder, params);
			footerlist = reportManager.findReportFinanceFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", footerlist);
		return obj;
	}
	
	/**
	 * 财务进销存表
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report_inventory.json")
	@ResponseBody
	public  Map<String, Object> queryReportInventoryList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 0 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		params.put("zoneCompanyNo", companyNos);
		int total = reportManager.findReportInventoryCount(params);
		List<ReportInventoryDto> list = new ArrayList<ReportInventoryDto>();
		List<ReportInventoryDto> footerlist = new ArrayList<ReportInventoryDto>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			if(StringUtils.isNotBlank(req.getParameter("exportFlag"))){
				page = new SimplePage(0, total, total);
			}
			list = reportManager.findReportInventoryByPage(page, sortColumn, sortOrder, params);
			footerlist = reportManager.findReportInventoryFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", footerlist);
		return obj;
	}
	
	/**
	 * 财务核对总表
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report_all_finance.json")
	@ResponseBody
	public  Map<String, Object> queryReportAllFinanceList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 0 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		params.put("zoneCompanyNo", companyNos);
		int total = reportManager.findReportAllFinanceCount(params);
		List<ReportFinanceDto> list = new ArrayList<ReportFinanceDto>();
		List<ReportFinanceDto> footerlist = new ArrayList<ReportFinanceDto>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			if(StringUtils.isNotBlank(req.getParameter("exportFlag"))){
				page = new SimplePage(0, total, total);
			}
			list = reportManager.findReportAllFinanceByPage(page, sortColumn, sortOrder, params);
			//footerlist = reportManager.findReportAllFinanceFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", footerlist);
		return obj;
	}
	
	/**
	 * 参数设置
	 * @param req
	 * @param map
	 * @return
	 */
	public Map<String, Object> setParams(HttpServletRequest req,Map<String, Object> map){
		String brandNos = "";
		if (map.get("multiBrandNo") != null) {
			brandNos = FasUtil.formatInQueryCondition(map.get("multiBrandNo").toString());
		}
		map.put("multiBrandNo", brandNos);
		return map;
	}
	
	/**
	 * 总部地区调货明细对账表
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report_transfer.json")
	@ResponseBody
	public  Map<String, Object> queryReportTransferList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 0 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		setParams(req,params);
		String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		params.put("zoneCompanyNo", companyNos);
		int total = reportManager.findReportTransferCount(params);
		List<ReportTransferDto> list = new ArrayList<ReportTransferDto>();
		List<ReportTransferDto> listFooter = new ArrayList<ReportTransferDto>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			if(StringUtils.isNotBlank(req.getParameter("exportFlag"))){
				page = new SimplePage(0, total, total);
			}
			list = reportManager.findReportTransferByPage(page, sortColumn, sortOrder, params);
			listFooter = reportManager.findReportTransferFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", listFooter);
		return obj;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		String exportHeadColumns = req.getParameter("exportHeadColumns");
		List<Map> columnsList =  ExportUtils.getColumnList(exportColumns);
		List<Map> columnsHeadList =  ExportUtils.getColumnList(exportHeadColumns);
		List<Map> dataMapList = ExportUtils.getDataList(getDataList(req, model));
		ExportUtils.ExportComplexHeadData(fileName, columnsHeadList, columnsList, dataMapList, response);
	}
	
	/**
	 * 获取导出标识
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExportFlag")
	@ResponseBody
	public Map<String, Object> getExportFlag(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("flag", exportFlagMap.get("success"));
		return resultMap;
	}
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private List getDataList(HttpServletRequest req, Model model) throws Exception{
		String type = req.getParameter("type");
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		Map dataMap = new HashMap();
		if(type.equals("detail")){
			dataMap = this.queryReportDetailList(req, model);
		}else if(type.equals("gather")){
			dataMap = this.queryReportGatherList(req, model);
		}else if(type.equals("detail_area")){
			dataMap = this.queryReportDetailAreaList(req, model);
		}else if(type.equals("gather_area")){
			dataMap = this.queryReportGatherAreaList(req, model);
		}else if(type.equals("business")){
			dataMap = this.queryReportBusinessList(req, model);
		}else if(type.equals("finance")){
			dataMap = this.queryReportFinanceList(req, model);
		}else if(type.equals("inventory")){
			dataMap = this.queryReportInventoryList(req, model);
		}else if(type.equals("all_finance")){
			dataMap = this.queryReportAllFinanceList(req, model);
		}else if(type.equals("transfer")){
			dataMap = this.queryReportTransferList(req, model);
		}
		List lstItem = (List)dataMap.get("rows");
		List lstFooter = (List) dataMap.get("footer");
		if(!CollectionUtils.isEmpty(lstFooter)){
			lstItem.addAll(lstFooter);
		}
		return lstItem;
	}
	
	/**
	 * 扣项明细
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/dtl_list")
	@ResponseBody
	public  List<Object> queryDtlList(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		if(StringUtils.isNotBlank(req.getParameter("buyerNo")) && StringUtils.isNotBlank(req.getParameter("salerNo"))){
			Map<String, Object> params = builderParams(req, model);
			String companyNos=financialAccountManager.findLeadRoleCompanyNos();
			params.put("zoneCompanyNo", companyNos);
			return reportManager.queryDtlList(params);
		}
		return null;
	}
	
	/**
	 * 组装查询参数
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException 
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> builderParams(HttpServletRequest req,
			Model model) throws ManagerException {
		Map<String, Object> retParams = new HashMap<String,Object>(req.getParameterMap().size());
		Map<String, String[]> params = req.getParameterMap();
		if (null != params && params.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Entry<String, String[]> p : params.entrySet()) {
				if (null == p.getValue() || StringUtils.isEmpty(p.getValue().toString()))
					continue;
				// 只转换一个参数，多个参数不转换
				String values[] = (String[]) p.getValue();
				String match = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
				if (values[0].matches(match)) {
					try {
						retParams.put(p.getKey(), sdf.parse(values[0]));
					} catch (ParseException e) {
						retParams.put(p.getKey(), values);
						logger.error(e.getMessage(), e);
						throw new ManagerException(e.getMessage(), e);
					}
				}else if(p.getKey().equals("queryCondition")&&model.asMap().containsKey("queryCondition")){
					retParams.put(p.getKey(), model.asMap().get("queryCondition"));
				} else {
					retParams.put(p.getKey(), values[0]);
				}
			}
		}
		return retParams;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/do_exports")
	public void doExports(HttpServletRequest request, Model model,HttpServletResponse response)
			throws ManagerException {
			String fileName = request.getParameter("fileName");
			String columns = request.getParameter("exportColumns");
		try {
			exportFlagMap.clear();
			ExportComplexVo exportVo = new ExportComplexVo();
			exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
			List<Map> columnsList =  ExportUtils.getColumnList(columns);
			exportVo.setColumnsMapList(columnsList);
			final HSSFExportComplex exportExcel = new HSSFExportComplex(exportVo);
			Function<Object, Boolean> handler = new Function<Object, Boolean>() {
				@Override
				public Boolean apply(Object input) {
					Map vals = (Map) input;
					
					exportExcel.write(vals);
					return true;
				}
			};
			SimplePage page = new SimplePage();
			page.setPageSize(Integer.MAX_VALUE);
			Map<String, Object> params = builderParams(request, model);
			String companyNos=financialAccountManager.findLeadRoleCompanyNos();
			params.put("zoneCompanyNo", companyNos);
			this.reportManager.findReportExport(page, params, handler);
			exportExcel.flush(response);
			exportExcel.close();
			exportFlagMap.put("success", "true");
		} catch (Exception e) {
			exportFlagMap.put("success", "true");
			throw new ManagerException(e);
		}
	}
}