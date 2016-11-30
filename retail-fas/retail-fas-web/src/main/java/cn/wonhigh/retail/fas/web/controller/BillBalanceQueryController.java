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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.BalanceDetailDto;
import cn.wonhigh.retail.fas.common.dto.BalanceGatherDto;
import cn.wonhigh.retail.fas.common.enums.BalanceTableEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.BillBalanceManager;
import cn.wonhigh.retail.fas.manager.BillBalanceQueryManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 结算查询Controller
 * @author wang.m1
 * @date  2014-09-05 10:33:45
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 */
@Controller
@RequestMapping("/bill_balance/query")
public class BillBalanceQueryController{
	protected static final XLogger logger = XLoggerFactory.getXLogger(BaseController.class);

	@Resource
	private BillBalanceQueryManager billBalanceQueryManager;

	@Resource
	private BillBalanceManager billBalanceManager;
	
	/**
	 * 明细列表 
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/detail_list.json")
	@ResponseBody
	public Map<String, Object> queryDetailList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<BalanceDetailDto> list = new ArrayList<BalanceDetailDto>();
		List<BalanceDetailDto> footerList = new ArrayList<BalanceDetailDto>();
		if(setTableName(params)){
			total = billBalanceQueryManager.selectDetailCount(params);
			if (total > 0) {
				SimplePage page = new SimplePage(pageNo, pageSize, total);
				list = billBalanceQueryManager.selectDetailList(page, sortColumn, sortOrder, params);
				footerList = billBalanceQueryManager.selectDetailFooter(params);
			}
		}
		map.put("total", total);
		map.put("rows", list);
		map.put("footer", footerList);
		return map;
	}
	
	/**
	 * 汇总列表 
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/gather_list.json")
	@ResponseBody
	public Map<String, Object> queryGatherList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<BalanceGatherDto> list = new ArrayList<BalanceGatherDto>();
		List<BalanceGatherDto> footerList = new ArrayList<BalanceGatherDto>();
		if(setTableName(params)){
			params.put("queryCondition", " AND (balance_no IS NOT NULL OR balance_no != '')");
			total = billBalanceQueryManager.selectGatherCount(params);
			if (total > 0) {
				SimplePage page = new SimplePage(pageNo, pageSize, total);
				list = billBalanceQueryManager.selectGatherList(page, sortColumn, sortOrder, params);
				footerList = billBalanceQueryManager.selectGatherFooter(params);
			}
		}
		map.put("total", total);
		map.put("rows", list);
		map.put("footer", footerList);
		return map;
	}
	
	/**
	 * 汇总列表 
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/entry_gather_list.json")
	@ResponseBody
	public Map<String, Object> queryEntryGatherList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<BalanceGatherDto> list = new ArrayList<BalanceGatherDto>();
		List<BalanceGatherDto> footerList = new ArrayList<BalanceGatherDto>();
		if(setTableName(params)){
			total = billBalanceQueryManager.selectEntryGatherCount(params);
			if (total > 0) {
				SimplePage page = new SimplePage(pageNo, pageSize, total);
				list = billBalanceQueryManager.selectEntryGatherList(page, sortColumn, sortOrder, params);
				footerList = billBalanceQueryManager.selectEntryGatherFooter(params);
			}
		}
		map.put("total", total);
		map.put("rows", list);
		map.put("footer", footerList);
		return map;
	}
	
	/**
	 * 结算单列表 
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/balance_list.json")
	@ResponseBody
	public Map<String, Object> queryBalanceList(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		Map<String, Object> map = new HashMap<String, Object>();
		List<BillBalance> list = new ArrayList<BillBalance>();
		List<BillBalance> footerList = new ArrayList<BillBalance>();
		int	total = billBalanceQueryManager.selectBalanceCount(params);
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = billBalanceQueryManager.selectBalanceList(page, sortColumn, sortOrder, params);
			footerList = billBalanceQueryManager.selectBalanceFooter(params);
		}
		map.put("total", total);
		map.put("rows", list);
		map.put("footer", footerList);
		return map;
	}
	
	/**
	 * 明细汇总列表
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/item_gather.json")
	@ResponseBody
	public Map<String, Object> queryItemGatherList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String balanceNo = req.getParameter("balanceNo");
		Map<String, Object> params = builderParams(req, model);
		int total = 0;
		List<BalanceDetailDto> list = new ArrayList<BalanceDetailDto>();
		List<BalanceDetailDto> listFooter = new ArrayList<BalanceDetailDto>();
		if(StringUtils.isNotBlank(balanceNo) && setTableName(params)){
			total = billBalanceQueryManager.selectItemGatherCount(params);
			if (total > 0) {
				SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
				list = billBalanceQueryManager.selectItemGatherList(page, sortColumn, sortOrder, params);
				listFooter = billBalanceQueryManager.selectItemGatherFooter(params);
			}
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", listFooter);
		return obj;
	}
	
	/**
	 * 设置查询表名
	 * @param params 
	 * @return
	 */
	private boolean setTableName(Map<String, Object> params){
		String balanceType = String.valueOf(params.get("balanceType"));
		String isReceive = String.valueOf(params.get("isReceive"));
		if(StringUtils.isNotBlank(balanceType) && !"null".equals(balanceType)){
			String tableName = BalanceTableEnums.getSendTableByNo(Integer.parseInt(balanceType));
			if(StringUtils.isNotBlank(isReceive) && !"true".equals(balanceType)){
				tableName = BalanceTableEnums.getReceiveTableByNo(Integer.parseInt(balanceType));
			}
			if(StringUtils.isNotBlank(tableName)){
				params.put("tableName", tableName);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 参数组装
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException 
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> builderParams(HttpServletRequest req, Model model) throws ManagerException{
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
	
	/**
	 * 结算单导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/balance_export")
	public void balanceExport(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String billNo = req.getParameter("billNo");
		if(StringUtils.isNotBlank(billNo)){
			List<Map> ColumnsList =  ExportUtils.getColumnList(req.getParameter("exportColumns"));
			String[] arrNo = billNo.split(",");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("exportFlag", true);
			params.put("balanceType", req.getParameter("balanceType"));
			if(setTableName(params)){
				SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
				List<Map> dataList = new ArrayList<Map>();
				for (String str : arrNo) {
					params.put("balanceNo", ""); //解决参数循环加载。
					Map<String, Object> map = new HashMap<String, Object>();
					params.put("billNo", str);
					BillBalance bill =  (BillBalance)billBalanceManager.findByBiz(null, params).get(0);
					params.put("balanceNo", str);
					List dtlList =  billBalanceQueryManager.selectItemGatherList(page, "", "", params);
					List<Map> dtlMapList = ExportUtils.getDataList(dtlList);
					String buyerName = bill.getBuyerName();
					String salerName = bill.getSalerName();
					String brandName = bill.getBrandName();
					String categoryName = bill.getCategoryName();
					String balanceNo = bill.getBillNo();
					Date startDate = bill.getBalanceStartDate();
					Date endDate = bill.getBalanceEndDate();
					String title = buyerName.concat("-").concat(salerName).concat(" ")
							.concat(brandName).concat(" 品牌  ").concat(categoryName).concat(" 类").concat("结算单").concat(balanceNo)
							.concat("(").concat(DateUtil.format(startDate))
							.concat("-").concat(DateUtil.format(endDate)).concat(")");
					map.put("title", title);
					map.put("dtl", dtlMapList);
					dataList.add(map);
				}
				ExportUtils.ExportMainData(req.getParameter("fileName"), ColumnsList, dataList, response);
			}
		}
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
		List<Map> ColumnsList =  ExportUtils.getColumnList(req.getParameter("exportColumns"));
		List<Map> dataMapList = ExportUtils.getDataList(this.getDataList(req, model));
		ExportUtils.ExportData(req.getParameter("fileName"), ColumnsList, dataMapList, response);
	}
	
	/**
	 * 获取导出数据
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getDataList(HttpServletRequest req, Model model) throws Exception{
		List dataList = null;
		List footerList = null;
		String type = req.getParameter("type");	
		if(StringUtils.isNotBlank(type)){
			int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
			int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
			Map<String, Object> params = builderParams(req, model);
			if(setTableName(params)){
				SimplePage page = new SimplePage(pageNumber, pageSize, pageSize);
				page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
				if(type.equals("enter")){
					dataList =   billBalanceQueryManager.selectDetailList(page, "", "", params);
					footerList = billBalanceQueryManager.selectDetailFooter(params);
				}else if(type.equals("gather")){
					dataList =  billBalanceQueryManager.selectGatherList(page, "", "", params);
					footerList = billBalanceQueryManager.selectGatherFooter(params);
				}else if(type.equals("balance")){
					dataList =  billBalanceQueryManager.selectBalanceList(page, "", "", params);
					footerList = billBalanceQueryManager.selectBalanceFooter(params);
				}else if(type.equals("itemGather")){
					params.put("exportFlag", true);
					dataList =  billBalanceQueryManager.selectItemGatherList(page, "", "", params);
				}
			}
		}
		if(!CollectionUtils.isEmpty(footerList) && !CollectionUtils.isEmpty(dataList)){
			dataList.add(footerList.get(0));
		}
		return dataList;
	}
	
}