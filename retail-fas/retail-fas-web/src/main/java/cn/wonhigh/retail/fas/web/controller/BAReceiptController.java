/**
 * title:PayableAccountController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:验收单列表
 * auther:user
 * date:2016-4-7 下午4:45:22
 */
package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
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
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.backend.core.SpringContext;
import cn.wonhigh.retail.fas.common.dto.BAReceiptDto;
import cn.wonhigh.retail.fas.manager.BAReceiptManager;
import cn.wonhigh.retail.fas.manager.BaroquePurchaseRegionCostManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.domain.ITask;
import cn.wonhigh.retail.fas.manager.domain.ITaskArgs;
import cn.wonhigh.retail.fas.manager.domain.ITaskResult;
import cn.wonhigh.retail.fas.manager.domain.TagPriceArgument;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@ModuleVerify("34000004")
@RequestMapping("/ba_receipt")
public class BAReceiptController {

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(BAReceiptController.class);
	
	@Resource
	private BAReceiptManager baReceiptManager;

	@Resource
	private BaroquePurchaseRegionCostManager baroquePurchaseRegionCostManager;

	@Resource
	private FinancialAccountManager financialAccountManager;

	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		params.put("HQCompanyNo", companyNos);
		int total = baReceiptManager.findReceiptCount(params);
		List<BAReceiptDto> lstFooter = new ArrayList<BAReceiptDto>();
		List<BAReceiptDto> lstItem = new ArrayList<BAReceiptDto>();
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			lstItem = baReceiptManager.findReceiptList(page, sortColumn, sortOrder, params);
			lstFooter = baReceiptManager.findReceiptFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstItem);
		obj.put("footer", lstFooter);
		return obj;
	}

	/**
	 * 导出
	 * 
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export_data")
	public void export(HttpServletRequest req, Model model, HttpServletResponse response) throws Exception {
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		Map<String, Object> params = builderParams(req, model);
		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		params.put("HQCompanyNo", companyNos);
		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List<BAReceiptDto> dateList = baReceiptManager.findReceiptList(page, "", "", params);
		String searchType = String.valueOf(params.get("searchType"));

		List<BAReceiptDto> footer = baReceiptManager.findReceiptFooter(params);
		if(null != footer){
			dateList.add(footer.get(0));
		}
		
		ExportUtils.doExport(fileName, exportColumns, dateList, response);
	}

	/**
	 * 修改价格
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateCost")
	@ResponseBody
	public Map<String, Object> updateCost(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String ids = request.getParameter("ids");
			if (!StringUtils.isEmpty(ids)) {
				try {
					Map<String,Integer> result = baroquePurchaseRegionCostManager.updateRegionCost(ids);
					map.put("success", true);
					Integer total = result.get("total");
					Integer pass = result.get("pass");
					map.put("mgr",String.format("成功更新 %s笔数据,由于关账日限制过滤 %s 笔数据",total-pass,pass));
				} catch (Exception e) {
					throw new ManagerException(e.getMessage(), e);
				}
			}
			map.put("success", true);

		} catch (Exception e) {
     		LOGGER.error(e.getMessage());
			map.put("success", false);
		}
		return map;
	}
	
	/**
	 * 修改价格
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateTagPrice")
	@ResponseBody
	public Map<String, Object> updateTagPrice(HttpServletRequest request, Model model) {		
		Map<String, Object> params = new HashMap<String, Object>();
		
		try {
			ITask task = SpringContext.getBean("tagPriceUpdateTask");
			TagPriceArgument args = new TagPriceArgument();
			params = builderParams(request, model);

			args.setCompany(params.get("multiCompanyNo").toString());
			args.setStartDate(params.get("receiveDateStart").toString());
			args.setEndDate(params.get("receiveDateEnd").toString());
			
			ITaskResult taskResult = task.start((ITaskArgs)args);
		
			params.put("ticket",taskResult.getValue());
			if( "-1".equals(taskResult.getValue())){
				params.put("success",true);
				params.put("info", "牌价正在更新,请勿重复操作。");
				params.put("status", "2");
			}
			else{
				params.put("success",true);
				params.put("info", "牌价正在更新");
				params.put("status", "1");
			}
		} catch (Exception e) {
			params.put("success", false);
			params.put("message", "牌价更新异常。");
			params.put("info", "牌价更新异常。");
			params.put("status", "-1");
			LOGGER.error(e.getMessage());
		}
		return params;
	}

	/**
	 * 生成结算单
	 * 
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/generate_balance")
	@ResponseBody
	public Map<String, Object> generateBalance(HttpServletRequest req, Model model, HttpServletResponse response)
			throws Exception {
		List<BAReceiptDto> lstChecked = this.getCheckedList(req.getParameter("checkedRows"));
		Map<String, Object> params = builderParams(req, model);
		int count = 0;
		if (!CollectionUtils.isEmpty(lstChecked)) {
			count = baReceiptManager.generateBalance(lstChecked, params);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("count", count);
		return map;
	}

	/**
	 * 转换选中行
	 * 
	 * @param checkedRows
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<BAReceiptDto> getCheckedList(String checkedRows) throws Exception {
		if (StringUtils.isBlank(checkedRows)) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		List<Map> itemList = mapper.readValue(checkedRows, new TypeReference<List<Map>>() {
		});
		return convertListWithTypeReference(mapper, itemList);
	}

	/**
	 * 转换成泛型列表
	 * 
	 * @param mapper
	 * @param list
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List convertListWithTypeReference(ObjectMapper mapper, List<Map> list) throws JsonParseException,
			JsonMappingException, JsonGenerationException, IOException {
		List tl = new ArrayList<Object>(list.size());
		if (!CollectionUtils.isEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				Object type = mapper.readValue(mapper.writeValueAsString(list.get(i)), BAReceiptDto.class);
				tl.add(type);
			}
		}
		return tl;
	}

	/**
	 * 组装查询参数
	 * 
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> builderParams(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> retParams = new HashMap<String, Object>(req.getParameterMap().size());
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
						throw new ManagerException(e.getMessage(), e);
					}
				} else if (p.getKey().equals("queryCondition") && model.asMap().containsKey("queryCondition")) {
					retParams.put(p.getKey(), model.asMap().get("queryCondition"));
				} else {
					retParams.put(p.getKey(), values[0]);
				}
			}
		}
		return retParams;
	}
}
