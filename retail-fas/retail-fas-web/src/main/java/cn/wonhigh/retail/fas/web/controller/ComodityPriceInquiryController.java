package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.manager.ComodityPriceInquiryManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.utils.SimplePage;



@Controller
@RequestMapping("/commodity_price_inquiry")
@ModuleVerify("30120025")
public class ComodityPriceInquiryController extends BaseController<PurchasePrice> {
	
    @Resource
    private ComodityPriceInquiryManager comodityPriceInquiryManager;
    

    @Override
    public CrudInfo init() {
        return new CrudInfo("commodity_price_inquiry/",comodityPriceInquiryManager);
    }
    
    @RequestMapping(method = RequestMethod.GET ,value ="/list_business")
    public ModelAndView list(HttpServletRequest req) {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("commodity_price_inquiry/list_business");
    	return mav;
    }
    
    @Override
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
            int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
            int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
            String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
            String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
            Map<String,Object> params = builderParams(req, model);
            if (StringUtils.isNotEmpty(req.getParameter("brandNo"))) {
    			params.put("brandNo", Arrays.asList(req.getParameter("brandNo").split(",")));
    		}
            if(params.get("isTrue").equals("true")){
            	params.put("tagPrice", "0");
            }
            int total = comodityPriceInquiryManager.findCount(params);
            SimplePage page = new SimplePage(pageNo, pageSize, total);
            List<PurchasePrice> list = comodityPriceInquiryManager.findByPage(page, sortColumn, sortOrder, params);
            for(PurchasePrice purchasePrice:list){
            	if(purchasePrice.getHeadquarterCost()==null){
	    			purchasePrice.setHeadquarterCost(new BigDecimal("0"));
	    		}
            }
            Map obj = new HashMap();
            obj.put("total", total);
            obj.put("rows", list);
            return obj;
        }
    
    
    /**
   	 * 导出
   	 * @param modelType 实体对象
   	 * @param req HttpServletRequest
   	 * @param model Model
   	 * @param response HttpServletResponse
     * @throws Exception 
   	 */
   	@RequestMapping(value = "/do_exports")
   	public void doFasExport(BillBalanceInvoiceApply modelType, HttpServletRequest req, Model model,
   			HttpServletResponse response) throws Exception {
   	  String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
      String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
   	
   		String fileName = req.getParameter("fileName");
   		String exportColumns = req.getParameter("exportColumns");
   		
   		Map<String, Object> params = builderParams(req, model);
   		if (StringUtils.isNotEmpty(req.getParameter("brandNo"))) {
			params.put("brandNo", Arrays.asList(req.getParameter("brandNo").split(",")));
		}
   		if(params.get("isTrue").equals("true")){
        	params.put("tagPrice", "0");
        }
   		int total = comodityPriceInquiryManager.findCount(params);
   		SimplePage page = new SimplePage(0, total,  total);
   		List<PurchasePrice> list = comodityPriceInquiryManager.findByPage(page, sortColumn, sortOrder, params);
		for(PurchasePrice purchasePrice:list){
        	if(purchasePrice.getHeadquarterCost()==null){
    			purchasePrice.setHeadquarterCost(new BigDecimal("0"));
    		}if(null != purchasePrice.getFactoryPrice()){
    				purchasePrice.setHeadquarterAddPrice(purchasePrice.getHeadquarterCost().subtract(purchasePrice.getFactoryPrice()));
    		}
        }
    	ExportUtils.doExport(fileName, exportColumns, list, response);
  
   	}
}