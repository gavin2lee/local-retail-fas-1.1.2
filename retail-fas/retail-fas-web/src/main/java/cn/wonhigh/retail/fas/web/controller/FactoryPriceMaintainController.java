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

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.manager.PurchasePriceManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.utils.BeanUtilsCommon;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-02 11:22:09
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
@RequestMapping("/factory_price_maintain")
@ModuleVerify("30120010")
public class FactoryPriceMaintainController extends BaseController<PurchasePrice> {
	
    @Resource
    private PurchasePriceManager purchasePriceManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("factory_price_maintain/",purchasePriceManager);
    }
    
    @Override
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
            int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
            int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
            String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
            String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
            Map<String,Object> params = builderParams(req, model);
            if (StringUtils.isNotEmpty(req.getParameter("brandNo"))) {
    			params.put("brandNos", Arrays.asList(req.getParameter("brandNo").split(",")));
    			params.put("brandNo", null);
    		}
            int total = purchasePriceManager.findCount(params);
            SimplePage page = new SimplePage(pageNo, pageSize, total);
            List<PurchasePrice> list = purchasePriceManager.findByPage(page, sortColumn, sortOrder, params);
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
   	 * @throws ManagerException 异常
   	 */
   	@RequestMapping(value = "/do_exports")
   	public void doFasExport(BillBalanceInvoiceApply modelType, HttpServletRequest req, Model model,
   			HttpServletResponse response) throws ManagerException {
   		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
   		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
   	
   		String fileName = req.getParameter("fileName");
   		String exportColumns = req.getParameter("exportColumns");
   		
   		Map<String, Object> params = builderParams(req, model);
   		if (StringUtils.isNotEmpty(req.getParameter("brandNo"))) {
   			params.put("brandNos", Arrays.asList(req.getParameter("brandNo").split(",")));
			params.put("brandNo", null);
		}
   		int total = purchasePriceManager.findCount(params);
   		SimplePage page = new SimplePage(0, total,  total);
   		List<PurchasePrice> list = purchasePriceManager.findByPage(page, sortColumn, sortOrder, params);
   		try {
   			List<Map> columnsList =  ExportUtils.getColumnList(exportColumns);
   			List<Object> dataList = new ArrayList<Object>();
   			for (PurchasePrice object : list) {
				dataList.add(object);
			}
   			List<Map> dataMapList = new ArrayList<Map>();
   			List<String> fields = new ArrayList<String>();
			for (Map map : columnsList) {
				fields.add(map.get("field").toString());
			}
   			boolean flag = true;
			ExportFormat formatAnnotation = null;
			AbstractExportFormat<PurchasePrice> format = null;
			for (PurchasePrice vo : list) {
				Map map = null;
				if (flag) {
					formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
					flag = false;
				}
				if (formatAnnotation != null) {
					format = (AbstractExportFormat<PurchasePrice>) formatAnnotation.className().newInstance();
					map = format.format(fields, vo);
				} else {
					map = new HashMap();
					BeanUtilsCommon.object2MapWithoutNull(vo, map);
				}
				dataMapList.add(map);
			}
   			
			ExportUtils.ExportData(fileName, columnsList, dataMapList, response);
		} catch (RpcException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);   		}
  
   	}
    
}