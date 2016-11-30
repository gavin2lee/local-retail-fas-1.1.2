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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.manager.InventoryClassifyManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@RequestMapping("/pe_inventory_classify")
@ModuleVerify("30510426")
public class PeInventoryClassifyController  extends BaseController<PeriodBalance> {
	
	@Resource
    private InventoryClassifyManager inventoryClassifyManager;
	
	@Override
	protected CrudInfo init() {
		return new CrudInfo("pe_inventory_classify/",inventoryClassifyManager);
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
	
	/////////////////////////////以下为体育存货分类汇总业务处理 wang.yj /////////////////////////////////////////////////////
	@RequestMapping(value = "/pe_column_list.json")
	@ResponseBody
	public  Map<String, Object> queryPeColumnList(HttpServletRequest req, Model model) throws Exception {
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
			List<PeriodBalance> lstCateGory = new ArrayList<PeriodBalance>();
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
			}
			obj.put("CateGoryColumn", lstCateGory);
			return obj;
        }
        return null;
	}
	
	@RequestMapping(value = "/pe_list.json")
	@ResponseBody
	public Map<String, Object> queryPeList(HttpServletRequest req, Model model) throws ManagerException {
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
			int total = inventoryClassifyManager.findPeInventoryClassInicationCount(params);
			if(total > 0){
	            SimplePage page = new SimplePage(pageNo, pageSize, total);
	            List<Map<String, Object>> list = inventoryClassifyManager.findPeInventoryClassInicationByPage(page, params);
	            List<Map<String, Object>> footList=inventoryClassifyManager.findPeInventoryClassInicationTotal(params);
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
	
	@RequestMapping(value = "/to_peList")
    public ModelAndView  toPeList(){
    	ModelAndView  mav=new ModelAndView();
    	mav.setViewName("inventory_classification_summary/pe_list");
    	return mav;
    }
	
	/**
   	 * 导出
   	 * @param modelType 实体对象
   	 * @param req HttpServletRequest
   	 * @param model Model
   	 * @param response HttpServletResponse
   	 * @throws ManagerException 异常
   	 */
   	@RequestMapping(value = "/do_pe_exports")
   	public void doPeExport(BillBalanceInvoiceApply modelType, HttpServletRequest req, Model model,
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
   	        List<Map<String, Object>> list = inventoryClassifyManager.findPeInventoryClassInicationByPage(null, params);
   	        List<Map<String, Object>> footList = inventoryClassifyManager.findPeInventoryClassInicationTotal(params);
   	        list.add(footList.get(0));
   	        exportData(params, response, list);
		} catch (RpcException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);   		}
  
   	}
   	
}
