package cn.wonhigh.retail.fas.web.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.ExportTypeEnum;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.manager.InventoryClassifyManager;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@RequestMapping("/inventory_classification_summary")
@ModuleVerify("30510411")
public class InventoryClassificationSummaryController  extends BaseController<PeriodBalance> {
	
	@Resource
    private InventoryClassifyManager inventoryClassifyManager;
	
	@Override
	protected CrudInfo init() {
		return new CrudInfo("inventory_classification_summary/",inventoryClassifyManager);
	}
	
	@RequestMapping(value = "/list.html")
    public ModelAndView  selectAreaPrice(){
    	ModelAndView  mav=new ModelAndView();
    	mav.setViewName("inventory_classification_summary/list");
    	return mav;
    }
	
	@Override
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
            int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
            int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
            try {
            	 Map<String, Object> obj = new HashMap<String, Object>();
	            Map<String,Object> params = builderParams(req, model);
	            Map<String,Object> ColumnMap=new HashMap<String,Object>();
	            if (StringUtils.isNotEmpty(req.getParameter("brandNo"))) {
	    			params.put("multiBrands", Arrays.asList(req.getParameter("brandNo").split(",")));
	    		}
	            if (StringUtils.isNotEmpty(req.getParameter("categoryNo"))) {
	    			params.put("multiCategorys", Arrays.asList(req.getParameter("categoryNo").split(",")));
	    		}
				ColumnMap=queryColumnList(req,model);
				params.put("CateGoryColumn", ColumnMap.get("CateGoryColumn"));
				params.put("OrderFromColumn", ColumnMap.get("OrderFromColumn"));
				int total = inventoryClassifyManager.findInventoryClassInicationCount(params);
				if(total > 0){
		            SimplePage page = new SimplePage(pageNo, pageSize, total);
		            List<Map<String, Object>> list = inventoryClassifyManager.findInventoryClassInicationByPage(page, params);
		            List<Map<String, Object>> footList=inventoryClassifyManager.findInventoryClassInicationTotal(params);
		            obj.put("rows", list);
		            obj.put("footer", footList);
				}
				
		         obj.put("total", total);
	            return obj;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new ManagerException(e.getMessage(), e);
			}
        }
	
	@RequestMapping(value = "/column_list.json")
	@ResponseBody
	public  Map<String, Object> queryColumnList(HttpServletRequest req, Model model) throws Exception {
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
        String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
        Map<String,Object> params = builderParams(req, model);
        if (StringUtils.isNotEmpty(req.getParameter("brandNo"))) {
			params.put("multiBrands", Arrays.asList(req.getParameter("brandNo").split(",")));
		}
        if (StringUtils.isNotEmpty(req.getParameter("categoryNo"))) {
			params.put("multiCategorys", Arrays.asList(req.getParameter("categoryNo").split(",")));
		}
        List<PeriodBalance> list = inventoryClassifyManager.findCateGoryCloumn(sortColumn, sortOrder, params);
        if(list.size() > 0){
        	Map<String, Object> obj = new HashMap<String, Object>();
        	Map<String, Integer> CateGoryData = new HashMap<String, Integer>();
			Map<String, Integer> OrderFromData = new HashMap<String, Integer>();
			List<PeriodBalance> lstCateGory = new ArrayList<PeriodBalance>();
			List<PeriodBalance> lstOrderFrom = new ArrayList<PeriodBalance>();
			for(PeriodBalance periodBalance:list){
				if(CateGoryData.get(periodBalance.getCategoryNo())!=null){
					Integer index = CateGoryData.get(periodBalance.getCategoryNo());
					CateGoryData.put(periodBalance.getCategoryNo(),index+1);
				}else{
					CateGoryData.put(periodBalance.getCategoryNo(), 1);
					PeriodBalance CateGoryDto = new PeriodBalance();
					CateGoryDto.setCategoryNo(periodBalance.getCategoryNo());
					if(periodBalance.getCategoryName()!=null && !periodBalance.getCategoryName().equals("")){
						CateGoryDto.setCategoryName(periodBalance.getCategoryName());
					}else{
						CateGoryDto.setCategoryName("未知");
					}
					lstCateGory.add(CateGoryDto);
				}
				if(OrderFromData.get(periodBalance.getOrderfrom())!=null){
					
				}else{
					OrderFromData.put(periodBalance.getOrderfrom(), 1);
					PeriodBalance OrderFromDto = new PeriodBalance();
					OrderFromDto.setOrderfrom(periodBalance.getOrderfrom());
					if(periodBalance.getGender()!=null && !periodBalance.getGender().equals("")){
						OrderFromDto.setGender(periodBalance.getGender());
					}else{
						OrderFromDto.setGender("~");
					}
					lstOrderFrom.add(OrderFromDto);
				}
			}
			//获取所有大类拥有订货类型的数量
			List<Integer> maxNum = new ArrayList<Integer>();
			for(int i=0;i<lstCateGory.size();i++){
				int num1 = CateGoryData.get(lstCateGory.get(i).getCategoryNo());
				maxNum.add(num1);	
			}
			//得到最大的订货数量的大类
			for(int i=1;i<maxNum.size();i++){
				if(maxNum.get(0)<maxNum.get(i)){
					maxNum.set(0, maxNum.get(i));
				}
			}
			//给大类赋最大订货数量
			for(PeriodBalance lstCateGoryDto:lstCateGory){
				lstCateGoryDto.setOpeningQty(maxNum.get(0));
			}
			obj.put("CateGoryColumn", lstCateGory);
			obj.put("OrderFromColumn", lstOrderFrom);
			return obj;
        }
        return null;
	}
	
	/**
   	 * 导出
   	 * @param modelType 实体对象
   	 * @param req HttpServletRequest
   	 * @param model Model
   	 * @param response HttpServletResponse
   	 * @throws ManagerException 异常
   	 */
   	@RequestMapping(value = "/do_exports")
   	public void queryExcelExportData(BillBalanceInvoiceApply modelType, HttpServletRequest req, Model model,
   			HttpServletResponse response) throws ManagerException {
   	
   		try {
   			Map<String,Object> params = builderParams(req, model);
   	   		Map<String,Object> ColumnMap=new HashMap<String,Object>();
   	        if (StringUtils.isNotEmpty(req.getParameter("brandNo"))) {
   				params.put("multiBrands", Arrays.asList(req.getParameter("brandNo").split(",")));
   			}
   	        if (StringUtils.isNotEmpty(req.getParameter("categoryNo"))) {
   				params.put("multiCategorys", Arrays.asList(req.getParameter("categoryNo").split(",")));
   			}
   			ColumnMap=queryColumnList(req,model);
   			params.put("CateGoryColumn", ColumnMap.get("CateGoryColumn"));
   			params.put("OrderFromColumn", ColumnMap.get("OrderFromColumn"));
   	        List<Map<String, Object>> list = inventoryClassifyManager.findInventoryClassInicationByPage(null, params);
   	        List<Map<String, Object>> footList = inventoryClassifyManager.findInventoryClassInicationTotal(params);
   	        list.add(footList.get(0));
   	        exportDatas(params, response, list);
		} catch (RpcException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);   		}
  
   	}
   	
   	
   	/**
	 * 导出类型为Map的数据
	 * @param params
	 * @param response
	 * @param dataList 需要导出的数据
	 * @throws ManagerException
	 */
	protected void exportDatas(Map<String, Object> params, HttpServletResponse response,
			List<?> dataList) throws ManagerException {
		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String)params.get("firstHeaderColumns");
		String lastHeaderColumns = (String)params.get("lastHeaderColumns");
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
				
				if(StringUtils.isNotEmpty(lastHeaderColumns)) {
					List<Map> footerHeaderColumns = mapper.readValue(lastHeaderColumns, new TypeReference<List<Map>>() {});
					exportVo.setFooterColumnsMapList(footerHeaderColumns);
				}
				
				List<Map> list = (List<Map>) dataList;
				if(list != null && list.size() > 0) {
					for(Map map : list) {
						map.put("subExportData", new ArrayList<Map>(1));
					}
					Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
					exportVo.setRowAccessWindowSize(rowAccessWindowSize);
					exportVo.setDataMapList(list);
					exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
					HSSFExportComplex.commonExportDataThreeLine(exportVo, response);
				}
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
				throw new ManagerException(e.getMessage(), e);
			}
		} else {

		}
	}

}
