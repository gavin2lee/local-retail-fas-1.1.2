package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.FasBillCodeEnums;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.DataHqAreaExchangeManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 调货单收发方对调 
 * @author yang.y
 * @date  2014-08-29 13:20:36
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Led 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/data_hq_area_exchange")
public class DataHqAreaExchangeController extends BaseCrudController<BillBuyBalance> {
	
    @Resource
    private DataHqAreaExchangeManager dataHqAreaExchangeManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("data_hq_area_exchange/",dataHqAreaExchangeManager);
    }
    
    @RequestMapping("/list")
	public String list() {
		return "data_hq_area_exchange/list";
	}
    
	@RequestMapping(value = "/list_tabMain")
	public ModelAndView areaAmongListTabMain(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("data_hq_area_exchange/list_tabMain");
		return mav;
	}
    
    /**
     * 查询需要对调的跨区调货出库单
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @RequestMapping(value = "/selectTransferData")
	@ResponseBody
	public  Map<String, Object>  selectTransferData(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		
		//查询总部与地区之间的调货出库单
		if(params.get("fasBillCodes") == null || params.get("fasBillCodes").toString() == "") {
			String fasBillCodes = FasBillCodeEnums.FG13710403.getCode() + ","
					+ FasBillCodeEnums.FG13710404.getCode() + ","
					+ FasBillCodeEnums.FG13710503.getCode() + ","
					+ FasBillCodeEnums.FG13710504.getCode() + ','
					+ FasBillCodeEnums.FG13710401.getCode() + ','
					+ FasBillCodeEnums.FG13710501.getCode() + ','
					+ FasBillCodeEnums.FG13710405.getCode() + ','
					+ FasBillCodeEnums.FG13710505.getCode();
			params.put("fasBillCodes", fasBillCodes);
		}
		
		//格式化参数
		params = this.formatParams(params);
		
		int iTotal = dataHqAreaExchangeManager.selectSaleTransferBillCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) iTotal);
		List<BillBuyBalance> buyListTemp = dataHqAreaExchangeManager.selectSaleTransferBill(page, sortColumn, sortOrder, params);
		List<BillBuyBalance> totalList=dataHqAreaExchangeManager.findTotalRow(params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", buyListTemp);
		map.put("total",iTotal);
		if (totalList.size() > 0) {
			map.put("footer", totalList);
		}
   		return map;
	}
    
    /**
     * 查询需要对调的跨区调货入库单
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @RequestMapping(value = "/selectEntryData")
	@ResponseBody
	public  Map<String, Object>  selectEntryData(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		
		if(params.get("fasBillCodes") == null || params.get("fasBillCodes").toString() == "") {
			String fasBillCodes = FasBillCodeEnums.FG13720403.getCode() + ","
					+ FasBillCodeEnums.FG13720404.getCode() + ","
					+ FasBillCodeEnums.FG13720503.getCode() + ","
					+ FasBillCodeEnums.FG13720504.getCode() + ','
					+ FasBillCodeEnums.FG13720401.getCode() + ','
					+ FasBillCodeEnums.FG13720501.getCode() + ','
					+ FasBillCodeEnums.FG13720405.getCode() + ','
					+ FasBillCodeEnums.FG13720505.getCode();
			params.put("fasBillCodes", fasBillCodes);
		}
		
		//格式化参数
		params = this.formatParams(params);
		
		int iTotal = dataHqAreaExchangeManager.selectBuyTransferBillCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) iTotal);
		List<BillBuyBalance> buyListTemp = dataHqAreaExchangeManager.selectBuyTransferBill(page, sortColumn, sortOrder, params);
		List<BillBuyBalance> totalList=dataHqAreaExchangeManager.findBuyTotalRow(params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", buyListTemp);
		map.put("total",iTotal);
		if (totalList.size() > 0) {
			map.put("footer", totalList);
		}
   		return map;
	}
    
    /**
     * 格式化查询参数
     * @param params
     * @return
     */
    private Map<String, Object> formatParams(Map<String, Object> params) {
    	
    	if(params.get("fasBillCodes") != null && !"".equals(params.get("fasBillCodes").toString())) {
			params.put("fasBillCodes", FasUtil.formatInQueryCondition(params.get("fasBillCodes").toString()));
		}
    	
    	if(params.get("originalBillNos") != null && !"".equals(params.get("originalBillNos").toString())) {
			params.put("originalBillNos", FasUtil.formatInQueryCondition(params.get("originalBillNos").toString()));
		}
    	
    	if(params.get("salerNos") != null && !"".equals(params.get("salerNos").toString())) {
			params.put("salerNos", FasUtil.formatInQueryCondition(params.get("salerNos").toString()));
		}
    	
    	if(params.get("buyerNos") != null && !"".equals(params.get("buyerNos").toString())) {
			params.put("buyerNos", FasUtil.formatInQueryCondition(params.get("buyerNos").toString()));
		}
    	
    	if(params.get("supplierNos") != null && !"".equals(params.get("supplierNos").toString())) {
			params.put("supplierNos", FasUtil.formatInQueryCondition(params.get("supplierNos").toString()));
		}
    	
    	if(params.get("brandNos") != null && !"".equals(params.get("brandNos").toString())) {
			params.put("brandNos", FasUtil.formatInQueryCondition(params.get("brandNos").toString()));
		}
    	
    	if(params.get("categoryNos") != null && !"".equals(params.get("categoryNos").toString())) {
			params.put("categoryNos", FasUtil.formatInQueryCondition(params.get("categoryNos").toString()));
		}
    	
		return params;
    }
    
    /**
     * 调整结算主体（重新拆单）
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changeBillType")
	@ResponseBody
	public Map<String, Object> changeBillType(HttpServletRequest req, Model model) throws Exception {
    	String checkedRows = req.getParameter("checkedRows");
    	List<Object> billList = convertListWithTypeReference(checkedRows, BillBuyBalance.class);
    	
    	String changeBillType = req.getParameter("changeBillType");
    	
    	Map<String, Object> map = dataHqAreaExchangeManager.changeBillType(billList, changeBillType);
    	
		return map;
    }
    
    /**
	 * 转换成泛型列表
	 * @param mapper
	 * @param list
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */					 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Object> convertListWithTypeReference(String rows, Class clazz)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Map> list = mapper.readValue(rows, new TypeReference<List<Map>>() {});
		List<Object> tl = new ArrayList<Object>(list.size());
		if(!CollectionUtils.isEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Object type = mapper.readValue(mapper.writeValueAsString(list.get(i)), clazz);
				tl.add(type);
			}
		}
		return tl;
	}
}