package cn.wonhigh.retail.fas.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.PEAPDto;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.PEReportManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

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
@RequestMapping("/pe_report")
public class PEReportController {
	protected static final XLogger logger = XLoggerFactory.getXLogger(BaseController.class);

    @Resource
    private PEReportManager peReportManager;
    
    @RequestMapping(method = RequestMethod.GET ,value ="/list")
    public ModelAndView list(HttpServletRequest req) {
    	ModelAndView mav = new ModelAndView();
    	String type = req.getParameter("type");
    	if(StringUtils.isNotBlank(type)){
    		if(type.equals("1")){
        		mav.setViewName("pe_report/report_ap_aging");
        	}else if(type.equals("2")){
        		mav.setViewName("pe_report/report_ap_plan");
        	}else if(type.equals("3")){
        		mav.setViewName("pe_report/report_send");
        	}
    		mav.addObject("type", type);
    	}
    	return mav;
    }
	
	/**
	 * 出货统计表
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report_send.json")
	@ResponseBody
	public  Map<String, Object> queryReportSend(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 0 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String brandNos = req.getParameter("brandNos");
		if(StringUtils.isNotBlank(brandNos)){
			params.put("multiBrandNo", FasUtil.formatInQueryCondition(brandNos));
		}
		int total = peReportManager.findReportSendCount(params);
		List<PEAPDto> list = new ArrayList<PEAPDto>();
		List<PEAPDto> listFooter = new ArrayList<PEAPDto>();
		if(total > 0){
			params.put("currentDate", DateUtil.format1(new Date()));
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = peReportManager.findReportSendByPage(page, sortColumn, sortOrder, params);
			listFooter = peReportManager.findReportSendFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", listFooter);
		return obj;
	}
	
	/**
	 * 应付账龄分析表
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report_ap_aging.json")
	@ResponseBody
	public  Map<String, Object> queryReportAPAging(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 0 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = peReportManager.findAPAgingCount(params);
		List<PEAPDto> list = new ArrayList<PEAPDto>();
		List<PEAPDto> listFooter = new ArrayList<PEAPDto>();
		if(total > 0){
			params.put("currentDate", DateUtil.format1(new Date()));
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = peReportManager.findAPAgingByPage(page, sortColumn, sortOrder, params);
			listFooter = peReportManager.findAPAgingFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", listFooter);
		return obj;
	}
	
	/**
	 * 付款计划表列记录
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report_ap_plan_column.json")
	@ResponseBody
	public  List<Map<String, Object>> queryReportAPPlanColumn(HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> params = builderParams(req, model);
		return peReportManager.findAPPlanColumn(params);
	}
	
	
	/**
	 * 付款计划表行记录
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report_ap_plan_List.json")
	@ResponseBody
	public  Map<String, Object> queryReportAPPlanList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 0 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = peReportManager.findAPPlanCount(params);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listFooter = new ArrayList<Map<String, Object>>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = peReportManager.findAPPlanByPage(page, sortColumn, sortOrder, params);
			listFooter = peReportManager.findAPPlanFooter(params);
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
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export_data")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String exportType = req.getParameter("exportType");
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		Map<String, Object> params = builderParams(req, model);
		String brandNos = req.getParameter("brandNos");
		if(StringUtils.isNotBlank(brandNos)){
			params.put("multiBrandNo", FasUtil.formatInQueryCondition(brandNos));
		}
		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List dataList = null;
		if("plan".equals(exportType)){
			dataList = peReportManager.findAPPlanByPage(page, "", "", params);
		}else if("aging".equals(exportType)){
			dataList = peReportManager.findAPAgingByPage(page, "", "", params);
		}else if("send".equals(exportType)){
			dataList = peReportManager.findReportSendByPage(page, "", "", params);
		}
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
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
						throw new ManagerException(e.getMessage(), e);					}
				}else if(p.getKey().equals("queryCondition")&&model.asMap().containsKey("queryCondition")){
					retParams.put(p.getKey(), model.asMap().get("queryCondition"));
				} else {
					retParams.put(p.getKey(), values[0]);
				}
			}
		}
		return retParams;
	}

}