package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.PrintManager;
import cn.wonhigh.retail.fas.web.utils.BalanceExportUtils;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
@RequestMapping("/print_balance")
public class PrintController {
	
    @Resource
    private PrintManager printManager;

	
	private  Map<String, String> exportFlagMap = new HashMap<String, String>();
	
    @SuppressWarnings("rawtypes")
	@ResponseBody
  	@RequestMapping(value = "/export_balance_data")
  	public Map<String, Object> exportBalanceData(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
    	exportFlagMap.clear();
    	List<Map<String, Object>> balanceData = new ArrayList<Map<String,Object>>();
    	Map<String, Object> map = new HashMap<String,Object>();
    	String checkRows = req.getParameter("checkRows");
    	String mergeFlag = req.getParameter("mergeFlag");
    	if (StringUtils.isNotBlank(checkRows)) {
    		ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(checkRows, new TypeReference<List<Map>>(){});
			List<BillBalance> oList=(List<BillBalance>)convertListWithTypeReference(mapper,list);
	    	if("true".equals(mergeFlag)){
	    			Map<String, BillBalance> supplierMap = new HashMap<String, BillBalance>();
	    			for (BillBalance billBalance : oList) {
	    				String salerNo = billBalance.getSalerNo();
	    				String balanceNo = billBalance.getBillNo();
	    				if(null == supplierMap.get(salerNo)){
	    					BillBalance newBalance = new BillBalance();
	    					newBalance.setBillNo(billBalance.getBillNo());
	    					newBalance.setSalerNo(billBalance.getSalerNo());
	    					newBalance.setSalerName(billBalance.getSalerName());
	    					newBalance.setBalanceEndDate(billBalance.getBalanceEndDate());
	    					newBalance.setBrandUnitNo(billBalance.getBrandUnitNo());
	    					newBalance.setDeductionAmount(billBalance.getDeductionAmount());
	    					supplierMap.put(salerNo, newBalance);
	    				}else{
	    					BillBalance obj = supplierMap.get(salerNo);
	    					obj.setBillNo(obj.getBillNo()+","+balanceNo);
	    					obj.setDeductionAmount(obj.getDeductionAmount().add(billBalance.getDeductionAmount()));
	    				}
	    			}
	    			for (Entry<String, BillBalance> entry : supplierMap.entrySet()) {
	    				BillBalance billBalance = entry.getValue();
	    				Map<String, Object> balanceMap = printManager.getPrintListByBalanceNo(FasUtil.formatInQueryCondition(billBalance.getBillNo()));
	    				Calendar cal = Calendar.getInstance();
	    				cal.setTime(billBalance.getBalanceEndDate());
	    				int year = cal.get(Calendar.YEAR);
	    				int month = cal.get(Calendar.MONTH)+1;
	    				String title = year+"年"+month+"月 （全月）"+billBalance.getBrandUnitNo()+"品牌对账单";
	    				balanceMap.put("title", title);
	    				balanceMap.put("balance", billBalance);
	    				balanceData.add(balanceMap);
	    			}
	    	}else{
	    		for (BillBalance billBalance : oList) {
	    			Map<String, Object> balanceMap = printManager.getPrintListByBalanceNo(FasUtil.formatInQueryCondition(billBalance.getBillNo()));
	    			Calendar cal = Calendar.getInstance();
    				cal.setTime(billBalance.getBalanceEndDate());
    				int year = cal.get(Calendar.YEAR);
    				int month = cal.get(Calendar.MONTH)+1;
    				String title = year+"年"+month+"月 （全月）"+billBalance.getBrandUnitNo()+"品牌对账单";
    				balanceMap.put("title", title);
    				balanceMap.put("balance", billBalance);
    				balanceData.add(balanceMap);
	    		}
	    	}
			BalanceExportUtils.ExportBalanceData("对账单明细导出", balanceData, response);
			exportFlagMap.put(CurrentUser.getCurrentUser().getUserid()+"", "true");
		}
    	return map;
    }
    
    @SuppressWarnings("rawtypes")
	@ResponseBody
  	@RequestMapping(value = "/export_balance_gather_data")
  	public Map<String, Object> exportBalanceGatherData(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
    	exportFlagMap.put(CurrentUser.getCurrentUser().getUserid()+"", "");
    	Map<String, Object> balanceData = new HashMap<String, Object>();
    	Map<String, Object> map = new HashMap<String,Object>();
    	String checkRows = req.getParameter("checkRows");
		if (StringUtils.isNotBlank(checkRows)) {
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(checkRows, new TypeReference<List<Map>>(){});
			List<BillBalance> oList=(List<BillBalance>)convertListWithTypeReference(mapper,list);
			String billNo = "";
			Date balanceEndDate = oList.get(0).getBalanceEndDate();
			for (BillBalance billBalance : oList) {
				if(!"06".equals(billBalance.getCategoryNo())){
					billNo += billBalance.getBillNo()+",";
				}
			}
			balanceData = printManager.getBalanceGatherListByBalanceNo(billNo.substring(0, billNo.length()-1),balanceEndDate);
			BalanceExportUtils.ExportBalanceGatherData("对账单汇总导出", balanceData, response);
			exportFlagMap.put(CurrentUser.getCurrentUser().getUserid()+"", "true");
		}
    	return map;
    }
  
	@ResponseBody
  	@RequestMapping(value = "/export_balance_diff_data")
  	public Map<String, Object> exportBalanceDiffData(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
    	exportFlagMap.put(CurrentUser.getCurrentUser().getUserid()+"", "");
    	Map<String, Map<String, Object>> lstData = new HashMap<String, Map<String, Object>>();
    	Map<String, Object> map = new HashMap<String,Object>();
    	String companyNo = req.getParameter("companyNo");
    	String brandNo = req.getParameter("brandNo");
    	String year = req.getParameter("year");
    	String month = req.getParameter("month");
		if (StringUtils.isNotBlank(companyNo)
				&& StringUtils.isNotBlank(brandNo)
				&& StringUtils.isNotBlank(year)
				&& StringUtils.isNotBlank(month)) {
			lstData = printManager.getBalanceDiffData(companyNo,brandNo,year,month);
			if(CollectionUtils.isEmpty(lstData)){
				map.put("errorMessage", "未查询到当月对账数据!");
			}
			BalanceExportUtils.ExportBalanceDiffData("对账差异导出", lstData, response);
			exportFlagMap.put(CurrentUser.getCurrentUser().getUserid()+"", "true");
		}
		map.put("success", true);
    	return map;
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
		String userId = CurrentUser.getCurrentUser().getUserid()+"";
		resultMap.put(CurrentUser.getCurrentUser().getUserid()+"", exportFlagMap.get(userId));
		return resultMap;
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
	@SuppressWarnings("rawtypes")
	private List<BillBalance> convertListWithTypeReference(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
		List<BillBalance> tl=new ArrayList<BillBalance>(list.size());
		for (int i = 0; i < list.size(); i++) {
			BillBalance type=mapper.readValue(mapper.writeValueAsString(list.get(i)),BillBalance.class);
			tl.add(type);
		}
		return tl;
	}
}