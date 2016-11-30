package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.TransferingCheckDto;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.TransferingCheckManager;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 收发货在途对账报表
 * @author ning.ly
 * @date  2015-04-24 10:10:28
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 */
@RequestMapping("/transfering_check")
@Controller
public class TransferingCheckController extends BaseController<TransferingCheckDto> {
    @Resource
    private TransferingCheckManager transferingCheckManager;
    
    @Resource
	private FinancialAccountManager financialAccountManager;
    
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("transfering_check/",transferingCheckManager);
    }
 
    //月份间隔
    int months_interval = -6;
    
    @RequestMapping("/transferingCheck")
   	public ModelAndView transferingCheck(HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("report/transferCheckDtl");
    	String type = request.getParameter("type");
    	String zoneFlag = request.getParameter("senda");
    	if(StringUtils.isNotEmpty(type)) {
    		if(zoneFlag.equals("no")){
    			mav.addObject("type", type);
    			mav.addObject("zoneFlag","no");
    		}else if(zoneFlag.equals("yes")){
    			mav.addObject("type", type);
    			mav.addObject("zoneFlag","yes");
    		}
    	}
    	return mav;
   	}
    
    /**
	 * 转到收发汇总表
	 * @return
	 */
	@RequestMapping(value = "/list_tabMain")
	public ModelAndView areaAmongListTabMain(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("report/transferCheckSum");
    	String zoneFlag = req.getParameter("senda");
		if (StringUtils.isNotBlank(zoneFlag)) {
			if(zoneFlag.equals("no")){
    			mav.addObject("zoneFlag","no");
    		}else if(zoneFlag.equals("yes")){
    			mav.addObject("zoneFlag","yes");
    		}
		}
		return mav;
	}
    
    /**
     * 月末
     * dateStr :日期字符串
     * i : 正数向前推月份，负数像后推
     * @return
     * @throws IOException 
     */
    private static String getMonthLastDay(String dateStr,int i) throws IOException {
     	String day_last = "1970-01-01";
    	Date dateTime;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	try {
				dateTime = df.parse(dateStr);
				Calendar calendar = Calendar.getInstance();
		        calendar.setTime(dateTime);
		        calendar.add(Calendar.MONTH, i);
		        //上个月最后一天
		        calendar.add(Calendar.MONTH, 1);    //加一个月
		        calendar.set(Calendar.DATE, 1);     //设置为该月第一天
		        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
		        day_last = df.format(calendar.getTime());
		        StringBuffer endStr = new StringBuffer().append(day_last);
		        day_last = endStr.toString();
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new IOException(e.getMessage(), e);
		}
    	return day_last;
    }
    
    /**
     * dateStr :日期字符串
     * i : 正数向前推月份，负数像后推
     * 月第一天
     * @return
     * @throws IOException 
     */
    private static String getMonthFirstDay(String dateStr,int i) throws IOException{
    	String day_first = "1970-01-01";
    	SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-" +
    			"dd" );
    	Date dateTime;
		try {
				dateTime = sdf.parse(dateStr);
			    Calendar calendar = Calendar.getInstance();
		        calendar.setTime(dateTime);
		        calendar.add(Calendar.MONTH, i);
		        
		        int index = calendar.get(Calendar.DAY_OF_MONTH);
		        calendar.add(Calendar.DATE, (1 - index));
		        day_first =sdf.format(calendar.getTime());
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new IOException(e.getMessage(), e);
		}
    	return day_first;
    }
    
    /**
     * 将当前日期往指定间隔推
     * dateStr :日期字符串
     * i : 正数向前推月份，负数像后推
     * @return
     * @throws IOException 
     */
    private static String getAssignDate(String dateStr,int i) throws IOException{
    	String putDate = "";
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date date;
		try {
			date = sdf.parse(dateStr);
		    Calendar calendar = new GregorianCalendar();
		    calendar.setTime(date);
		    calendar.add(Calendar.DATE,i);//把日期往后增加一天.整数往后推,负数往前移动
		    date= calendar.getTime(); 	  //这个时间就是日期往后推一天的结果
		    putDate = sdf.format(date);   //增加一天后的日期
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new IOException(e.getMessage(), e);
		}
		return putDate;
    }
    
    /**
     * 查询收发货明细
     * 按收发方公司，订货单位,大类，商品分组
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @RequestMapping(value = "/selectDtl.json")
  	@ResponseBody
  	public Map<String, Object> selectTransferingCheckDtl(HttpServletRequest req, Model model) 
  			throws ManagerException {
    	long startTime=System.currentTimeMillis();
    	List<TransferingCheckDto> list = new ArrayList<TransferingCheckDto>();
		List<TransferingCheckDto> footerList = new ArrayList<TransferingCheckDto>();
  		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
  		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
  		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
  		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
  		Map<String, Object> params = builderParams(req, model);
  		constructParams(params);
  		Map<String, Object> obj = new HashMap<String, Object>();
  		int iDtlCount = transferingCheckManager.selectTransferingCheckDtlCount(params);
  		SimplePage page = new SimplePage(pageNo, pageSize, (int) iDtlCount);
  		if(iDtlCount>0){
  			list = transferingCheckManager.selectTransferingCheckDtl(page, sortColumn, sortOrder, params);
  			footerList = transferingCheckManager.selectTransferingCheckDtlFooter(null, params);
  		}
  		obj.put("total", iDtlCount);
  		obj.put("footer", footerList);
  		obj.put("rows", list);
  		long endTime=System.currentTimeMillis(); 
  		System.out.println("【查询明细】程序运行时间： "+(endTime-startTime)+"ms"); 
  		return obj;
  	}
    
    /**
     * 查询收发货汇总
     * 按收发方公司，订货单位分组
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @RequestMapping(value = "/selectTotal.json")
  	@ResponseBody
  	public Map<String, Object> selectTransferingCheckTotal(HttpServletRequest req, Model model) 
  			throws ManagerException {
    	long startTime=System.currentTimeMillis();
      	List<TransferingCheckDto> list = new ArrayList<TransferingCheckDto>();
    	List<TransferingCheckDto> footerList = new ArrayList<TransferingCheckDto>();
  		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
  		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
  		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
  		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
  		Map<String, Object> params = builderParams(req, model);
  		constructParams(params);
  		Map<String, Object> obj = new HashMap<String, Object>();
  		int iTotalCount = transferingCheckManager.findCount(params);
  		SimplePage page = new SimplePage(pageNo, pageSize, (int) iTotalCount);
  		if(iTotalCount>0){
  			list = transferingCheckManager.selectTransferingCheckTotal(page, sortColumn, sortOrder, params);
  			footerList = transferingCheckManager.selectTransferingCheckGatherFooter(null, params);
  		}
  		obj.put("total", iTotalCount);
  		obj.put("footer", footerList);
  		obj.put("rows", list);
  		long endTime=System.currentTimeMillis(); 
  		System.out.println("【查询汇总】程序运行时间： "+(endTime-startTime)+"ms"); 
  		return obj;
  	}
    
    /**
     * 
     * @param list 基础数据
     * @param flag 合计，小计标记
     * @return
     */
    private List<TransferingCheckDto> sumFooterList(List<TransferingCheckDto> list,String flag){
		List<TransferingCheckDto> footerList = new ArrayList<TransferingCheckDto>();
		int currMonSenNum = 0;
		BigDecimal currMonSenRMB =new BigDecimal(0);
		int currMonRecNum= 0;
		BigDecimal currMonRecRMB =new BigDecimal(0);
		int nextMonRecNum= 0;
		BigDecimal nextMonRecRMB =new BigDecimal(0);
		int currMonYetRecNum= 0;
		BigDecimal currMonYetRecRMB =new BigDecimal(0);
		int currMonDiffNum= 0;
		BigDecimal currMonDiffRMB =new BigDecimal(0);
		int lastMonSenNum= 0;
		BigDecimal lastMonSenRMB =new BigDecimal(0);
		int lastMonLastRecNum= 0;
		BigDecimal lastMonLastRecRMB =new BigDecimal(0);
		int LastMonRecNum= 0;
		BigDecimal lastMonRecRMB =new BigDecimal(0);
		int LastMonYetRecNum= 0;
		BigDecimal lastMonYetRecRMB =new BigDecimal(0);
		int LastMonDiffNum= 0;
		BigDecimal lastMonDiffRMB =new BigDecimal(0);
		TransferingCheckDto dto = new TransferingCheckDto();
	    for(int i =0;i<list.size();i++){
	    	TransferingCheckDto item = list.get(i);
	    	
			currMonSenNum += list.get(i).getCurrMonSenNum();
			currMonSenRMB = BigDecimalUtil.add(currMonSenRMB, item.getCurrMonSenRMB());
			
			currMonRecNum += list.get(i).getCurrMonRecNum();
			currMonRecRMB = BigDecimalUtil.add(currMonRecRMB, item.getCurrMonRecRMB());
			
			nextMonRecNum += list.get(i).getNextMonRecNum();
			nextMonRecRMB = BigDecimalUtil.add(nextMonRecRMB, item.getNextMonRecRMB());
			
			currMonYetRecNum += list.get(i).getCurrMonYetRecNum();
			currMonYetRecRMB = BigDecimalUtil.add(currMonYetRecRMB, item.getCurrMonYetRecRMB());
			
			currMonDiffNum += list.get(i).getCurrMonDiffNum();
			currMonDiffRMB = BigDecimalUtil.add(currMonDiffRMB, item.getCurrMonDiffRMB());
			
			lastMonSenNum += list.get(i).getLastMonSenNum();
			lastMonSenRMB = BigDecimalUtil.add(lastMonSenRMB, item.getLastMonSenRMB());
			
			lastMonLastRecNum += list.get(i).getLastMonLastRecNum();
			lastMonLastRecRMB = BigDecimalUtil.add(lastMonLastRecRMB, item.getLastMonLastRecRMB());
			
			LastMonRecNum += list.get(i).getLastMonRecNum();
			lastMonRecRMB = BigDecimalUtil.add(lastMonRecRMB, item.getLastMonRecRMB());
			
			LastMonYetRecNum += list.get(i).getLastMonYetRecNum();
			lastMonYetRecRMB = BigDecimalUtil.add(lastMonYetRecRMB, item.getLastMonYetRecRMB());
			
			LastMonDiffNum += list.get(i).getLastMonDiffNum();
			lastMonDiffRMB = BigDecimalUtil.add(lastMonDiffRMB, item.getLastMonDiffRMB());
	    }
	    dto.setZoneName(flag);
	   	dto.setCurrMonSenNum(currMonSenNum);
		dto.setCurrMonSenRMB(currMonSenRMB);
		dto.setCurrMonRecNum(currMonRecNum);
		dto.setCurrMonRecRMB(currMonRecRMB);
		dto.setNextMonRecNum(nextMonRecNum);
		dto.setNextMonRecRMB(nextMonRecRMB);
		dto.setCurrMonYetRecNum(currMonYetRecNum);
		dto.setCurrMonYetRecRMB(currMonYetRecRMB);
		dto.setCurrMonDiffNum(currMonDiffNum);
		dto.setCurrMonDiffRMB(currMonDiffRMB);
		dto.setLastMonSenNum(lastMonSenNum);
		dto.setLastMonSenRMB(lastMonSenRMB);
		dto.setLastMonLastRecNum(lastMonLastRecNum);
		dto.setLastMonLastRecRMB(lastMonLastRecRMB);
		dto.setLastMonRecNum(LastMonRecNum);
		dto.setLastMonRecRMB(lastMonRecRMB);
		dto.setLastMonYetRecNum(LastMonYetRecNum);
		dto.setLastMonYetRecRMB(lastMonYetRecRMB);
		dto.setLastMonDiffNum(LastMonDiffNum);
		dto.setLastMonDiffRMB(lastMonDiffRMB);
		footerList.add(dto);
	    return footerList;
   }
    
    /**
     * 根据synsetId在集合中进行分组
     * @return
     */
    public List<TransferingCheckDto> subTotal(List<TransferingCheckDto> list,String flag){
    	  //返回的处理好的集合对象
		  List<TransferingCheckDto> newSynList = new ArrayList<TransferingCheckDto>();
    	  //定义一个map集合用于分组
		  Map<String, List<TransferingCheckDto>> mapList = new HashMap<String, List<TransferingCheckDto>>();
		  //分组数据
		  List<TransferingCheckDto> groupList = null;
		  //遍历集合以Synset_id为键，以chinese为值保存到mapList中
		  for (Iterator<TransferingCheckDto> it = list.iterator(); it.hasNext();){
			   //将循环读取的结果放入对象中
				TransferingCheckDto synsetcn = (TransferingCheckDto) it.next();
			   //如果在这个map中包含有相同的键，这创建一个集合将其存起来
			   if (mapList.containsKey(synsetcn.getOneLevelCategoryNo())) {
				   groupList.add(synsetcn);
			   //如果没有包含相同的键，在创建一个集合保存数据
			   } else {
				    groupList = new ArrayList<TransferingCheckDto>();
				    groupList.add(synsetcn);
				    mapList.put(synsetcn.getOneLevelCategoryNo(), groupList);
			   }
		  }
		  //遍历map集合
		  if(mapList.size()<=1){
			  return list;
		  }
		  for (Map.Entry<String, List<TransferingCheckDto>> m : mapList.entrySet()){
			    //获取所有的值
			  	List<TransferingCheckDto> synList = m.getValue();
			  	//String categoryStr=  m.getKey();
			  	newSynList.addAll(synList);
			  	newSynList.addAll(sumFooterList(synList,flag));
		  }
		  return newSynList;
    } 
    
    /**
     * 多选参数组装
     * @param params
     */
    public void multipelParams(Map<String, Object> params){
    	String categoryNo="";
  		String brandNo="";
  		String orderUnitNo="";
  		String orderUnitNoFrom="";
  		String organNo="";
  		String organNoFrom="";
  		List<String> tempList = null;
  		if(params.get("categoryNo")!=null){
  			categoryNo = params.get("categoryNo").toString();
  			categoryNo = FasUtil.formatInQueryCondition(categoryNo);
  		}
  		if(params.get("orderUnitNo")!=null){
  			orderUnitNo = params.get("orderUnitNo").toString();
  			orderUnitNo = FasUtil.formatInQueryCondition(orderUnitNo);
  		}
  		if(params.get("orderUnitNoFrom")!=null){
  			orderUnitNoFrom = params.get("orderUnitNoFrom").toString();
  			orderUnitNoFrom = FasUtil.formatInQueryCondition(orderUnitNoFrom);
  		}
  		if(params.get("organNo")!=null){
  			organNo = params.get("organNo").toString();
  			organNo = FasUtil.formatInQueryCondition(organNo);
  		}
  		if(params.get("organNoFrom")!=null){
  			organNoFrom = params.get("organNoFrom").toString();
  			organNoFrom = FasUtil.formatInQueryCondition(organNoFrom);
  		}
  		if(params.get("brandNo")!=null){
  			brandNo = params.get("brandNo").toString();
  	  		brandNo = FasUtil.formatInQueryCondition(brandNo);
  		}
  		if(params.get("balanceTypes") != null && !"".equals(params.get("balanceTypes").toString())) {
			String[] temps = params.get("balanceTypes").toString().split(",");
			tempList = Arrays.asList(temps);
		}
  		params.put("balanceTypes", tempList);
  		params.put("brandNo", brandNo);
  		params.put("organNoFrom", organNoFrom);
  		params.put("organNo", organNo);
  		params.put("orderUnitNo", orderUnitNo);
  		params.put("orderUnitNoFrom", orderUnitNoFrom);
  		params.put("categoryNo", categoryNo);
    }
    
    /**
     * 构造参数
     *@param params
     */
    public void constructParams(Map<String, Object> params){
    	multipelParams(params);
    	String companyNos=financialAccountManager.findLeadRoleCompanyNos();
		params.put("hqCompanyData",companyNos);
    	if(params.get("currentDateStart")!=null && params.get("currentDateEnd")!=null){
    		//上月区间
    		String LStartDate=params.get("lastDateStart").toString();
    		//本月区间
			String start = params.get("currentDateStart").toString();
			String end = params.get("currentDateEnd").toString();
			//下月区间
    		String NEndDate=params.get("nextDateEnd").toString();
    		//取价格
			String priceFrom = params.get("priceFrom").toString();
			if(priceFrom == "" || priceFrom==null){
				priceFrom = "0";
				params.put("priceFrom",priceFrom);
			}
			//上月区间
			params.put("lastDateStart",LStartDate);
			//本月区间
			params.put("currentDateStart",start);
			params.put("currentDateEnd",end);
			//下月区间
			params.put("nextDateEnd",NEndDate);
		}
    }
    
    /**
     * 导出汇总与明细数据源
     */
	protected List<TransferingCheckDto> queryExportData(Map<String, Object> params) throws ManagerException {
		 int exportType = 0;
		 if(params.get("exportType")!=null){
			 exportType = Integer.parseInt(params.get("exportType").toString());
		}
		List<TransferingCheckDto> dataList = null;
		List<TransferingCheckDto> footerList = null;
		constructParams(params);
    	//汇总
    	if(exportType == 1){
    		 
	    		SimplePage page = new SimplePage(1, 200000,  200000);
	      		dataList = transferingCheckManager.selectTransferingCheckTotal(page, null, null, params);
	    		footerList = sumFooterList(dataList,"合计:");
				dataList.add(footerList.get(0));
    	 
      	//明细
    	}else if(exportType == 2){ 
     	 
     			SimplePage page = new SimplePage(1, 200000,  200000);
          		dataList = transferingCheckManager.selectTransferingCheckDtl(page, "", null, params);
          		//先汇总合计
          		footerList = sumFooterList(dataList,"合计:");
          		//再汇总小计
          		dataList=subTotal(dataList,"小计:");
				dataList.add(footerList.get(0));
     	 
    	}
    	return dataList;
	 }
}