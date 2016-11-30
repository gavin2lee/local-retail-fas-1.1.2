package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.RegionCostMaintainManager;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@RequestMapping("/area_price_report")
@ModuleVerify("30120024")
public class AreaPriceReportControll extends BaseController<RegionCostMaintain>{
	@Resource
    private RegionCostMaintainManager regionCostMaintainManager;
	
	@Override
	protected CrudInfo init() {
		// TODO Auto-generated method stub
		return new CrudInfo("area_price_report/",regionCostMaintainManager);
	}
	
	 @RequestMapping(value = "/list.htm")
	    public ModelAndView  selectAreaPrice(){
	    	ModelAndView  mav=new ModelAndView();
	    	mav.setViewName("area_price_report/list");
	    	return mav;
	    }
	 
	 @Override
		public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
	            int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
	            int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
	            String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
	            String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
	            Map<String,Object> params = builderParams(req, model);
	            String brandNos = "";
				if (params.get("brandNo") != null) {
					brandNos = FasUtil.formatInQueryCondition(params.get("brandNo").toString());
					params.put("brandNo", null);
					params.put("multiBrandNo", brandNos);
				}
	            int total = regionCostMaintainManager.findCount(params);
	            SimplePage page = new SimplePage(pageNo, pageSize, total);
	            List<RegionCostMaintain> list = regionCostMaintainManager.findRegionCostReport(page, sortColumn, sortOrder, params);
	            for(RegionCostMaintain regionCostMaintain:list){
	            	if(regionCostMaintain.getTagPrice()==null){
	            		regionCostMaintain.setTagPrice(regionCostMaintain.getHeadquarterCost());
	            	}
	            }
	            Map obj = new HashMap();
	            obj.put("total", total);
	            obj.put("rows", list);
	            return obj;
	        }
	    
	    
		@RequestMapping(value = "/do_exports")
		public void saleExport(HttpServletRequest request, Model model,HttpServletResponse response)
				throws ManagerException {

			Map<String, Object> params = builderParams(request, model);
			
			// 设置多选的查询条件
			String brandNos = "";
			if (params.get("brandNo") != null) {
				brandNos = FasUtil.formatInQueryCondition(params.get("brandNo").toString());
				params.put("brandNo", null);
				params.put("multiBrandNo", brandNos);
			}
			
			params.put("areaPriceReport", "yes");
			String exportColumns = (String) params.get("exportColumns");
			String firstHeaderColumns = (String) params.get("firstHeaderColumns");
			String fileName = (String) params.get("fileName");
			//增加参数，该参数可以不指定，使用默认值
			String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
			if (!StringUtils.isNotEmpty(exportColumns))
				return;
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				ExportComplexVo exportVo = new ExportComplexVo();
				exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
				Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
				exportVo.setRowAccessWindowSize(rowAccessWindowSize);
				List<Map> columnsList = mapper.readValue(exportColumns, new TypeReference<List<Map>>() {
				});
				
				// 对手动隐藏的列进行处理，把手动隐藏的列过滤掉，不导出来
				List<Map> tempColumnsList = new ArrayList<Map>();
				for (Map map : columnsList) {
					Object hid = map.get("hidden");
					if(null == hid || !hid.equals(true)){
						tempColumnsList.add(map);
					}
				}
				
				tempColumnsList = this.sortExportColumns(tempColumnsList);
				exportVo.setColumnsMapList(tempColumnsList);
				if (StringUtils.isNotEmpty(firstHeaderColumns)) {
					List<Map> headerColumnsList = mapper.readValue(firstHeaderColumns, new TypeReference<List<Map>>() {
					});
					exportVo.setHeaderColumnsMapList(headerColumnsList);
				}

				final HSSFExportComplex exportExcel = new HSSFExportComplex(exportVo);
				Function<Object, Boolean> handler = new Function<Object, Boolean>() {
					@SuppressWarnings({ "rawtypes" })
					@Override
					public Boolean apply(Object input) {
						Map vals = (Map) input;
						
						BigDecimal tagPrice=(BigDecimal) vals.get("tagPrice");
						if(tagPrice==null){
							vals.put("tagPrice", vals.get("headquarterCost"));
						}
						
						exportExcel.write(vals);
						return true;
					}
				};
				SimplePage page = new SimplePage();
				page.setPageSize(Integer.MAX_VALUE);
				this.regionCostMaintainManager.findAreaPriceExport(page, params, handler);
			

				exportExcel.flush(response);
				exportExcel.close();
			} catch (Exception e) {
				throw new ManagerException(e);
			}
		}
	
}
