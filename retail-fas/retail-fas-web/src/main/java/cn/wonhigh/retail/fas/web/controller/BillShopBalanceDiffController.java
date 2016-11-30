package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffExport;
import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffFooterDto;
import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffTrackDto;
import cn.wonhigh.retail.fas.common.enums.ExportTypeEnum;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;
import cn.wonhigh.retail.fas.common.utils.AnnotationReflectUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.BillShopBalanceDiffManager;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.BeanUtilsCommon;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-15 10:52:13
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
@RequestMapping("/bill_shop_balance_diff")
@ModuleVerify("30140014")
public class BillShopBalanceDiffController extends BaseController<BillShopBalanceDiffExport> {
	
    @Resource
    private BillShopBalanceDiffManager billShopBalanceDiffManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_shop_balance_diff/",billShopBalanceDiffManager);
    }
    
    @RequestMapping("/list")
	public String list() {
		return "mallshop_balance/search_shopbalance_difflist";
	}
    
    /**
     * 结算差异跟踪，结算差异查询  菜单 公用方法
     * @throws Exception 
     */
   	@RequestMapping(value = "/querylist.json")
	@ResponseBody
	public Map<String, Object> querySerchList(HttpServletRequest req, Model model) throws Exception {
   		Map<String, Object> obj = new HashMap<String, Object>();
   		String balaceDeduType = req.getParameter("balaceDiffType");
   		String shopNos = req.getParameter("shopNos");
   		String shopNo = req.getParameter("shopNo");
   		String noDiffAmount = req.getParameter("noDiffAmount");

   		if(("1").equals(balaceDeduType) && StringUtils.isEmpty(shopNo)) {
   			obj.put("total", 0);
   			obj.put("rows", new ArrayList<BillShopBalanceDiff>());
   			return obj;
   		}
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		 
  		String companyNos = params.get("companyNos") == null ? null : params.get("companyNos").toString();
        String organNos = params.get("organNos") == null ? null : params.get("organNos").toString();
        String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
        
		if (StringUtils.isNotEmpty(shopNos)) {
			params.put("shopNos", Arrays.asList(shopNos.split(",")));
		}
		 if (StringUtils.isNotEmpty(companyNos)) {
	        	params.put("companyNos", FasUtil.formatInQueryCondition(companyNos));
			}
	        if(StringUtils.isNotEmpty(organNos)){
	        	params.put("organNos",  FasUtil.formatInQueryCondition(organNos));
//	        	params.put("organNos",  null);
			}
	        if(StringUtils.isNotEmpty(brandNos)){
				params.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
			}	
		if (StringUtils.isNotEmpty(noDiffAmount)) {
			if("0".equals(noDiffAmount)){
				params.put("noDiffAmount",noDiffAmount);
			}else if("1".equals(noDiffAmount)){
				params.put("diffAmount","diffAmount");
			}
		}
		
		int total = this.billShopBalanceDiffManager.selectlistSearchCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillShopBalanceDiff> list = this.billShopBalanceDiffManager.selectlistSearchByPage(page, sortColumn, sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		// 组装footer
		if(list != null && list.size() > 0) {
			Map<String, Object> brparams = new HashMap<String, Object>();
			brparams.put("shopNo", shopNo);
			brparams.put("month", "");
			brparams.put("balanceNo",  req.getParameter("balanceNo"));
			BillShopBalanceDiffFooterDto footerDto = billShopBalanceDiffManager.getFooterDto(brparams);
			if(footerDto != null) {
				BillShopBalanceDiff billShopBalanceDiff = new BillShopBalanceDiff();
				billShopBalanceDiff.setDiffTypeName("合计");
				// 复制属性值
				AnnotationReflectUtil.copyProperties(billShopBalanceDiff, footerDto);
				List<BillShopBalanceDiff> billShopBalanceDiffTotal = new ArrayList<BillShopBalanceDiff>(1);
				billShopBalanceDiffTotal.add(billShopBalanceDiff);
				obj.put("footer", billShopBalanceDiffTotal);
			}
		}
		return obj;
	}
    
    /**
     * 结算差异跟踪，结算差异查询  菜单 公用方法
     */
   	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
   		Map<String, Object> obj = new HashMap<String, Object>();
   		String balaceDeduType = req.getParameter("balaceDiffType");
   		String shopNos = req.getParameter("shopNos");
   		String shopNo = req.getParameter("shopNo");
   		String noDiffAmount = req.getParameter("noDiffAmount");

   		if(("1").equals(balaceDeduType) && StringUtils.isEmpty(shopNo)) {
   			obj.put("total", 0);
   			obj.put("rows", new ArrayList<BillShopBalanceDiff>());
   			return obj;
   		}
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		 
  		String companyNos = params.get("companyNos") == null ? null : params.get("companyNos").toString();
        String organNos = params.get("organNos") == null ? null : params.get("organNos").toString();
        String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
        
		if (StringUtils.isNotEmpty(shopNos)) {
			params.put("shopNos", Arrays.asList(shopNos.split(",")));
		}
		 if (StringUtils.isNotEmpty(companyNos)) {
	        	params.put("companyNos", FasUtil.formatInQueryCondition(companyNos));
			}
	        if(StringUtils.isNotEmpty(organNos)){
	        	params.put("organNos",  FasUtil.formatInQueryCondition(organNos));
//	        	params.put("organNos",  null);
			}
	        if(StringUtils.isNotEmpty(brandNos)){
				params.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
			}	
		if (StringUtils.isNotEmpty(noDiffAmount)) {
			if("0".equals(noDiffAmount)){
				params.put("noDiffAmount",noDiffAmount);
			}else if("1".equals(noDiffAmount)){
				params.put("diffAmount","diffAmount");
			}
		}
		
		int total = this.billShopBalanceDiffManager.selectlistDiffTrackCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillShopBalanceDiff> list = this.billShopBalanceDiffManager.selectlistDiffTrackByPage(page, sortColumn, sortOrder, params);
//		List<BillShopBalanceDiff> listReturn = new ArrayList<BillShopBalanceDiff>();
//		BillShopBalanceDiff  billShopBalanceDiff = new BillShopBalanceDiff();
		/**if (!CollectionUtils.isEmpty(list)) {
			for(BillShopBalanceDiff billShopBalanceDiffBef : list){
//				billShopBalanceDiffBef.setMarkId(billShopBalanceDiffBef.getMarkId());
				Integer markid=billShopBalanceDiffBef.getMarkId();
			    if(markid != null && markid > 0){
//			    	累计差异回款 = 本期差异回款 + 第一期父节点MARKID累计差异回款
					billShopBalanceDiffBef.setDiffBackAmountSum(billShopBalanceDiffManager.getDiffBackAmountSum(billShopBalanceDiffBef));
					
//					累计差异余额  = 第一期父节点MARKID累计差异余额  - 累计差异回款 
					if (null!=billShopBalanceDiffManager.getDiffBalanceSum(billShopBalanceDiffBef) && null!=billShopBalanceDiffBef.getDiffBackAmountSum()){ 
					billShopBalanceDiffBef.setDiffBalanceSum(billShopBalanceDiffManager.getDiffBalanceSum(billShopBalanceDiffBef).subtract(billShopBalanceDiffBef.getDiffBackAmountSum()));
				}}
				
//				billShopBalanceDiffBef.setId(billShopBalanceDiffBef.getId());
				
//				listReturn.add(billShopBalanceDiffBef);
			}
		}
		**/
		obj.put("total", total);
		obj.put("rows", list);
		// 组装footer
		if(list != null && list.size() > 0) {
			Map<String, Object> brparams = new HashMap<String, Object>();
			brparams.put("shopNo", shopNo);
			brparams.put("month", "");
			brparams.put("balanceNo",  req.getParameter("balanceNo"));
			BillShopBalanceDiffFooterDto footerDto = billShopBalanceDiffManager.getFooterDto(brparams);
			if(footerDto != null) {
				BillShopBalanceDiff billShopBalanceDiff = new BillShopBalanceDiff();
				billShopBalanceDiff.setDiffTypeName("合计");
				// 复制属性值
				try {
					AnnotationReflectUtil.copyProperties(billShopBalanceDiff, footerDto);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);

				}
				List<BillShopBalanceDiff> billShopBalanceDiffTotal = new ArrayList<BillShopBalanceDiff>(1);
				billShopBalanceDiffTotal.add(billShopBalanceDiff);
				obj.put("footer", billShopBalanceDiffTotal);
			}
		}
		return obj;
	}
   	
	@RequestMapping("/getDiffBackAmountSum")
   	@ResponseBody
   	protected BigDecimal getDiffBackAmountSum(@RequestParam("balanceObj")String balanceObjs,BillShopBalanceDiff  billShopBalanceDiff) throws Exception {
		String[] shopBalances = balanceObjs.split(";");
		String balanceNo = shopBalances[0];
		String  balanceStartDate = shopBalances[1];
		String  balanceEndDate= shopBalances[2];
		String  id= shopBalances[3];
		billShopBalanceDiff.setBalanceNo(balanceNo);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		billShopBalanceDiff.setBalanceStartDate(dateFormat.parse(balanceStartDate));
		billShopBalanceDiff.setBalanceEndDate(dateFormat.parse(balanceEndDate));
//		billShopBalanceDiff.setId(id);
		billShopBalanceDiff.setBillNo(id);
		BigDecimal diffBackAmountSum = billShopBalanceDiffManager.getDiffBackAmountSum(billShopBalanceDiff);
		if( null != diffBackAmountSum){
			return diffBackAmountSum;
		}
        return new BigDecimal("0");
   	}
	
	/**
	 * 进入差异跟踪页面
	 * @return 返回请求地址
	 */
	@RequestMapping("/diffTrack")
	public String diffTrack() {
		return "mallshop_balance/diff_track";
	}
	
	@RequestMapping(value = "/listDiffTrack.json")
	@ResponseBody
	public Map<String, Object> queryDiffTrack(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = billShopBalanceDiffManager.findDiffTrackCount(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		if(total <= 0) {
			obj.put("total", total);
			obj.put("rows", new ArrayList<BillShopBalanceDiffTrackDto>(1));
			return obj;
		}
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillShopBalanceDiffExport> list = billShopBalanceDiffManager.findDiffTrackPage(page, sortColumn, 
				sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	} 

	/**
	 * 导出
	 * @param request
	 * @param model
	 * @param response
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/export")
	public void doExport(HttpServletRequest request, Model model,
			HttpServletResponse response) throws Exception {
		Map<String, Object> params = builderParams(request, model);
		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String)params.get("firstHeaderColumns");
		String subExportColumns = (String) params.get("exportSubColumns");
		String fileName = (String) params.get("fileName");
		String exportType = (String)params.get("exportType");
		//增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		ObjectMapper mapper = new ObjectMapper();
		if(StringUtils.isNotEmpty(exportColumns)) {
			try {
				//字段名列表
				List<Map> columnsList = mapper.readValue(exportColumns, new TypeReference<List<Map>>() {});

				List<Map> subColumnsList = new ArrayList<Map>();
				if(StringUtils.isNotEmpty(subExportColumns)) {
					subColumnsList = mapper.readValue(subExportColumns, new TypeReference<List<Map>>() {});
					
					//如果是混合表头，则将subColumnsList加入columnsList集合
					if(ExportTypeEnum.FIX_HEADER.getType().equalsIgnoreCase(exportType)) {
						columnsList.addAll(subColumnsList);
						subColumnsList = new ArrayList<Map>(1);
					}
				}
				columnsList = this.sortExportColumns(columnsList);
				ExportComplexVo exportVo = new ExportComplexVo();
				exportVo.setColumnsMapList(columnsList);
				exportVo.setSubColumnsMapList(subColumnsList);
				
				if(StringUtils.isNotEmpty(firstHeaderColumns)) {
					List<Map> headerColumnsList = mapper.readValue(firstHeaderColumns, new TypeReference<List<Map>>() {});
					exportVo.setHeaderColumnsMapList(headerColumnsList);
				}
				
		   		String shopNos = request.getParameter("shopNos");
		   		String companyNos = params.get("companyNos") == null ? null : params.get("companyNos").toString();
		        String organNos = params.get("organNos") == null ? null : params.get("organNos").toString();
		        String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
		        String noDiffAmount = request.getParameter("noDiffAmount");
		        
				String sortColumn = StringUtils.isEmpty(request.getParameter("sort")) ? "" : String.valueOf(request.getParameter("sort"));
				String sortOrder = StringUtils.isEmpty(request.getParameter("order")) ? "" : String.valueOf(request.getParameter("order"));
				
				if (StringUtils.isNotEmpty(shopNos)) {
					params.put("shopNos", Arrays.asList(shopNos.split(",")));
				}
				
				if (StringUtils.isNotEmpty(companyNos)) {
		        	params.put("companyNos", FasUtil.formatInQueryCondition(companyNos));
				}
		        if(StringUtils.isNotEmpty(organNos)){
		        	params.put("organNos",  FasUtil.formatInQueryCondition(organNos));
//		        	params.put("organNos",  null);
				}
		        if(StringUtils.isNotEmpty(brandNos)){
					params.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
				}	
				if (StringUtils.isNotEmpty(noDiffAmount)) {
					if("0".equals(noDiffAmount)){
						params.put("noDiffAmount",noDiffAmount);
					}else if("1".equals(noDiffAmount)){
						params.put("diffAmount","diffAmount");
					}
				}
				/*int total = this.billShopBalanceDiffManager.findCount(params);
				SimplePage page = new SimplePage(0,(int) total, (int) total);*/
				List<BillShopBalanceDiffExport> list = this.billShopBalanceDiffManager.findDiffTrackExport(params);
				List<Map> listArrayList = new ArrayList<Map>();
				if(list != null && list.size() > 0) {
					List<String> fields = new ArrayList<String>();
					for(Map map : columnsList) {
						fields.add(map.get("field").toString());
					}
					boolean flag = true;
					ExportFormat formatAnnotation = null;
					AbstractExportFormat<BillShopBalanceDiffExport> format = null;
					for(BillShopBalanceDiffExport vo : list) {
						if(vo.getAdjustType()!=null&&vo.getAdjustType()==1){
							vo.setAdjustTypeName("差异调整");
						}else if(vo.getAdjustType()!=null&&vo.getAdjustType()==2){
							vo.setStatusName("差异回款");
						}
						if(vo.getStatus()!=null&&vo.getStatus()==0){
							vo.setStatusName("未完成");
						}else if(vo.getStatus()!=null&&vo.getStatus()==1){
							vo.setStatusName("已完成");
						}
						if(vo.getStatusDiff()!=null&&vo.getStatusDiff()==0){
							vo.setStatusDiffName("未完成");
						}else if(vo.getStatusDiff()!=null&&vo.getStatusDiff()==1){
							vo.setStatusDiffName("已完成");
						}
						Map map = null;
						if(flag) {
							formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
							flag = false;
						}
						if(formatAnnotation != null) {
							format = (AbstractExportFormat<BillShopBalanceDiffExport>) formatAnnotation.className().newInstance();
							map = format.format(fields, vo);
						} else {
							map = new HashMap();
							BeanUtilsCommon.object2MapWithoutNull(vo, map);
						}
						if(subColumnsList != null && subColumnsList.size() > 0) {
							List<Map> subExportData = this.findComplexHeaderData(vo);
							map.put("subExportData", subExportData);
						} else {
							map.put("subExportData", new ArrayList<Map>(1));
						}
						listArrayList.add(map);
					}
					Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
					exportVo.setRowAccessWindowSize(rowAccessWindowSize);
					exportVo.setDataMapList(listArrayList);
					exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
					HSSFExportComplex.commonExportData(exportVo, response);
				}
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
				throw new ManagerException(e.getMessage(), e);
			}
		} else {

		}
	}

	@Override
	protected List<BillShopBalanceDiffExport> queryExportData(Map<String, Object> params) throws Exception {

   		Map<String, Object> obj = new HashMap<String, Object>();
   		String balaceDeduType = (String) params.get("balaceDiffType");
   		String shopNos = (String) params.get("shopNos");
   		String shopNo = (String) params.get("shopNo");
   		String noDiffAmount = (String) params.get("noDiffAmount");

   		if(("1").equals(balaceDeduType) && StringUtils.isEmpty(shopNo)) {
   			obj.put("total", 0);
   			obj.put("rows", new ArrayList<BillShopBalanceDiff>());
   			return null;
   		}
		String sortColumn = StringUtils.isEmpty((String) params.get("sort")) ? "" : String.valueOf((String) params.get("sort"));
		String sortOrder = StringUtils.isEmpty((String) params.get("order")) ? "" : String.valueOf((String) params.get("order"));
		 
  		String companyNos = params.get("companyNos") == null ? null : params.get("companyNos").toString();
        String organNos = params.get("organNos") == null ? null : params.get("organNos").toString();
        String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
        String billStatus = params.get("billStatus") == null ? null : params.get("billStatus").toString();
		if (StringUtils.isNotEmpty(shopNos)) {
			params.put("shopNos", Arrays.asList(shopNos.split(",")));
		}
		 if (StringUtils.isNotEmpty(companyNos)) {
	        	params.put("companyNos", FasUtil.formatInQueryCondition(companyNos));
			}
	        if(StringUtils.isNotEmpty(organNos)){
	        	params.put("organNos",  FasUtil.formatInQueryCondition(organNos));
//	        	params.put("organNos",  null);
			}
	        if(StringUtils.isNotEmpty(brandNos)){
				params.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
			}	
		if (StringUtils.isNotEmpty(noDiffAmount)) {
			if("0".equals(noDiffAmount)){
				params.put("noDiffAmount",noDiffAmount);
			}else if("1".equals(noDiffAmount)){
				params.put("diffAmount","diffAmount");
			}
		}
		 List  list  = new ArrayList();
		if("all".equals(billStatus)){
			int total = this.billShopBalanceDiffManager.selectlistSearchCount(params);
			SimplePage page = new SimplePage(0,(int)total, (int) total);
			list = this.billShopBalanceDiffManager.selectlistSearchByPage(page, sortColumn, sortOrder, params);
		}else if("overAll".equals(billStatus)){
			int total = this.billShopBalanceDiffManager.selectlistDiffTrackCount(params);
			SimplePage page = new SimplePage(0,(int)total, (int) total);
			list = this.billShopBalanceDiffManager.selectlistDiffTrackByPage(page, sortColumn, sortOrder, params); 
		}
		
		
		// 组装footer
		if(list != null && list.size() > 0) {
			Map<String, Object> brparams = new HashMap<String, Object>();
			brparams.put("shopNo", shopNo);
			brparams.put("month", "");
			brparams.put("balanceNo",  params.get("balanceNo"));
			BillShopBalanceDiffFooterDto footerDto = billShopBalanceDiffManager.getFooterDto(params);
			if(footerDto != null) {
				BillShopBalanceDiff billShopBalanceDiff = new BillShopBalanceDiff();
				billShopBalanceDiff.setDiffTypeName("合计");
				// 复制属性值
				AnnotationReflectUtil.copyProperties(billShopBalanceDiff, footerDto);
				list.add(billShopBalanceDiff);
			}
		}
		return list;
	
	}
	
	
}