package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.BaroqueItemCostDto;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.BaroqueItemCostManager;
import cn.wonhigh.retail.fas.manager.HeadquarterCostMaintainManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;

@Controller
@ModuleVerify("34000008")
@RequestMapping("/baroque_item_cost")
public class BaroqueItemCostController extends BaseController<HeadquarterCostMaintain> {

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(BaroqueItemCostController.class);

	@Resource
	private HeadquarterCostMaintainManager headquarterCostMaintainManager;

	@Resource
	private BaroqueItemCostManager baroqueItemCostManager;

	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {

		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		// 选单操作
		Map<String, Object> params = builderParams(req, model);
		Map<String, Object> obj = new HashMap<String, Object>();
		String year = StringUtils.isEmpty(req.getParameter("year")) ? "" : req.getParameter("year");
		String month = StringUtils.isEmpty(req.getParameter("month")) ? "" : req.getParameter("month");
		if (StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(month)) {
			params.put("effectiveTime", DateUtil.getFirstDayOfMonthStr(Integer.valueOf(year), Integer.valueOf(month)));
		}
		try {
			int total = this.baroqueItemCostManager.getBaroqueHeadquarterCostListCount(params);
			List<BaroqueItemCostDto> list = null;
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.baroqueItemCostManager.getBaroqueHeadquarterCostList(page, sortColumn, sortOrder, params);
			obj.put("total", total);
			obj.put("rows", list);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return obj;
	}
	
	/**
	 * TODO 暂时的实现
	 * @param brandNo
	 * @return
	 */
	private String[] getRegionCompanyMapping(String brandNo){
		String[] regionCompany = null;
		if(!StringUtils.isEmpty(brandNo)){
			if(brandNo.startsWith("BA")){
				regionCompany = new String[]{"B0011"};
			}else if(brandNo.startsWith("RE")){
				regionCompany = new String[]{"B0012"};
			}else if(brandNo.startsWith("FF")){
				regionCompany = new String[]{"B0000"};
			}
		}
		return regionCompany;
	}
	
	private void checkFFHeadquarterCost(String year,String month) throws ManagerException{
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("receiveTimeLst", DateUtil.getLastDayOfMonthStr(Integer.valueOf(year), Integer.valueOf(month)));
		params.put("receiveTime", DateUtil.getFirstDayOfMonthStr(Integer.valueOf(year), Integer.valueOf(month)));
		
		List<BaroqueItemCostDto> diffHeadquarterCost =  this.baroqueItemCostManager.getDiffHeadquarterCostForFF(params);
		this.baroqueItemCostManager.updateHeadquarterCost(diffHeadquarterCost);
	}
	
	@RequestMapping(value = "/generateItemCost")
	@ResponseBody
	public Map<String, Object> generateBaroqueHeadquarterCost(HttpServletRequest req, Model model)
			throws ManagerException {
		Map<String, Object> params = builderParams(req, model);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String year = StringUtils.isEmpty(req.getParameter("year")) ? "" : req.getParameter("year");
			String month = StringUtils.isEmpty(req.getParameter("month")) ? "" : req.getParameter("month");
			params.put("effectiveTimeLst", DateUtil.getLastDayOfMonthStr(Integer.valueOf(year), Integer.valueOf(month)));
			params.put("effectiveTime", DateUtil.getFirstDayOfMonthStr(Integer.valueOf(year), Integer.valueOf(month)));
			params.put("preMonthFirstDay", DateUtil.getFirstDayOfMonthStr(Integer.valueOf(year), Integer.valueOf(month)-1));
			params.put("billType", BillTypeEnums.RECEIPT.getRequestId().intValue());
			String brandNo = params.get("brandUnitNo").toString();
			params.put("regionCompany", getRegionCompanyMapping(params.get("brandUnitNo").toString()));
			List<BaroqueItemCostDto> list = null;
			
			this.checkFFHeadquarterCost(year, month);
			
			
			if(!StringUtils.isEmpty(brandNo)){
				if(brandNo.startsWith("BA")||brandNo.startsWith("RE")){
					list = this.baroqueItemCostManager.generateBaroqueRegionHeadquarterCost(params);
				}else if(brandNo.startsWith("FF")){
					list = this.baroqueItemCostManager.generateBaroqueRegionHeadquarterCostForFF(params);
				}
			}
			if (null != list && list.size() > 0) {
				List<HeadquarterCostMaintain> batchInsert = this.ToHeadquarterCostMaintainList(list);
				this.headquarterCostMaintainManager.batchAddNUpdate(batchInsert);
			}
			result.put("success", true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			result.put("success", false);
		}
		return result;
	}

	private List<HeadquarterCostMaintain> ToHeadquarterCostMaintainList(List<BaroqueItemCostDto> list) {
		List<HeadquarterCostMaintain> result = null;
		if (null != list) {
			result = new ArrayList<HeadquarterCostMaintain>();
			for (BaroqueItemCostDto dto : list) {
				HeadquarterCostMaintain temp = new HeadquarterCostMaintain();
				BeanUtils.copyProperties(dto, temp);
				temp.setId(UUIDHexGenerator.generate());
				temp.setUpdateTime(new Date());
				result.add(temp);
			}
		}
		return result;
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
		int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
		Map<String, Object> params = builderParams(req, model);
		String year = StringUtils.isEmpty(req.getParameter("year")) ? "" : req.getParameter("year");
		String month = StringUtils.isEmpty(req.getParameter("month")) ? "" : req.getParameter("month");
		if (StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(month)) {
			params.put("effectiveTime", DateUtil.getFirstDayOfMonthStr(Integer.valueOf(year), Integer.valueOf(month)));
		}
		int total = this.baroqueItemCostManager.getBaroqueHeadquarterCostListCount(params);
		SimplePage page = new SimplePage(pageNumber, pageSize, (int) total);
		page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List<BaroqueItemCostDto> list = null;
		list = this.baroqueItemCostManager.getBaroqueHeadquarterCostList(page, null, null, params);
		ExportUtils.doExport(fileName, exportColumns, list, response);
	}
	

	@Override
	protected CrudInfo init() {
		return new CrudInfo("baroque_item_cost/", this.headquarterCostMaintainManager);
	}
}
