package cn.wonhigh.retail.fas.web.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.model.PayRelationship;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.PayRelationshipManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-18 16:58:06
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
@RequestMapping("/pay_relationship")
@ModuleVerify("40001011")
public class PayRelationshipController extends BaseCrudController<PayRelationship> {
    @Resource
    private PayRelationshipManager payRelationshipManager;
    
    @Resource
    private CommonUtilController commonUtilController;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("pay_relationship/",payRelationshipManager);
    }
    
    private Map<String, Object> getQueryCondition(HttpServletRequest req, Model model){
    	Map<String, Object> params = builderParams(req, model);
		String brandNos = req.getParameter("brandNos");
		if(StringUtils.isNotBlank(brandNos)){
			params.put("multiBrandNo", FasUtil.formatInQueryCondition(brandNos));
		}
		return params;
    }
    
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public  Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String searchType = req.getParameter("searchType");
		Map<String, Object> params = this.getQueryCondition(req, model);
		int total = 0;
		List<PayRelationship> lstItem = new ArrayList<PayRelationship>();
		List<PayRelationship> lstFooter = new ArrayList<PayRelationship>();
		if("item".equals(searchType)){
			total = payRelationshipManager.findItemCount(params);
			if(total >0){
				SimplePage page = new SimplePage(pageNo, pageSize, total);
				lstItem = payRelationshipManager.findItemList(page, sortColumn, sortOrder, params);
				lstFooter = payRelationshipManager.findFooter(params);
			}
		}else{
			total = payRelationshipManager.findCount(params);
			if(total >0){
				SimplePage page = new SimplePage(pageNo, pageSize, total);
				lstItem = payRelationshipManager.findByPage(page, sortColumn, sortOrder, params);
				lstFooter = payRelationshipManager.findFooter(params);
			}
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstItem);
		obj.put("footer", lstFooter);
		return obj;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export_data")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String searchType = req.getParameter("searchType");
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		Map<String, Object> params = this.getQueryCondition(req, model);
		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List dataList = null;
		if("item".equals(searchType)){
			dataList = payRelationshipManager.findItemList(page, "", "", params);
		}else{
			dataList = payRelationshipManager.findByPage(page, "", "", params);
		}
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
	
	/**
	 * 更新折扣
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/update_discount")
	@ResponseBody
	public Map<String, Object> updateDiscount(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		Map<String, Object> params = this.getQueryCondition(req, model);
		int count = payRelationshipManager.updateDiscountNew(params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("count", count);
		return map;
	}
	
	/**
	 * 更新价格
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/update_cost")
	@ResponseBody
	public Map<String, Object> updatePrice(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		Map<String, Object> params = this.getQueryCondition(req, model);
		int count = payRelationshipManager.updateCostNew(params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("count", count);
		return map;
	}
	
	/**
	 * 更新到期日
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/update_due_date")
	@ResponseBody
	public Map<String, Object> updateDueDate(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		Map<String, Object> params = this.getQueryCondition(req, model);
		int	count = payRelationshipManager.updateDueDate(params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("count", count);
		return map;
	}
	
	/**
	 * 生成结算单
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/generate_balance")
	@ResponseBody
	public Map<String, Object> generatePrice(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		Map<String, Object> params = this.getQueryCondition(req, model);
		int count = payRelationshipManager.generateBalanceNew(params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("count", count);
		return map;
	}
	
	/**
	 * 导入金额
	 * @param file
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/do_import_cost")
	@ResponseBody
	public Map<String, Object>  doImportCost(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
		String[] fieldNames= new String[]{"id","settlementNumber","businessBillNo","supplierAmount","notTaxSupplierAmount"};
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "supplierAmount", "","含税厂商金额", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "notTaxSupplierAmount", "","不含税厂商金额", false));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), PayRelationship.class, fieldNames);
		List<ValidateResultVo>  listValidate = commonUtilController.doValidate(lstItem, lstValidate, PayRelationship.class);
		List<PayRelationship> errorList = payRelationshipManager.doImportCostNew(listValidate);
		map.put("success", true);
		map.put("errorRows", errorList);
		return map;
	}
	
	/**
	 * 导入折扣
	 * @param file
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/do_import_discount")
	@ResponseBody
	public Map<String, Object>  doImportDiscount(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
		String[] fieldNames= new String[]{"id","settlementNumber","businessBillNo","balanceDiscount1","balanceDiscount2","zoneOriginalDiscount1","zoneOriginalDiscount2","supplierSendDate"};
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "balanceDiscount1", "","对账折扣1", false));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "balanceDiscount2", "","对账折扣2", false));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "zoneOriginalDiscount1", "","地区折扣1", false));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "zoneOriginalDiscount2", "","地区折扣2", false));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "supplierSendDate", "","厂商发货日",false));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), PayRelationship.class, fieldNames);
		List<ValidateResultVo>  listValidate = commonUtilController.doValidate(lstItem, lstValidate, PayRelationship.class);
		List<PayRelationship> errorList = payRelationshipManager.doImportDiscountNew(listValidate);
		map.put("success", true);
		map.put("errorRows", errorList);
		return map;
	}

	/**
	 * 导入总部收购折扣
	 * @param file
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/do_import_discount_buy")
	@ResponseBody
	public Map<String, Object>  doImportDiscountBuy(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
		String importType = req.getParameter("type");
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		String[] fieldNames= null;
		if("1".equals(importType)){
			fieldNames= new String[]{"id","businessBillNo","balanceDiscount1","zoneOriginalDiscount1"};
			lstValidate.add(new ValidateVo(ValidateTypeEnums.NULL.getTypeNo(), "businessBillNo", "","单据编号", true));
		}else if("2".equals(importType)){
			fieldNames= new String[]{"id","businessBillNo","itemCode","tagPrice","balanceDiscount1","zoneOriginalDiscount1"};
			lstValidate.add(new ValidateVo(ValidateTypeEnums.NULL.getTypeNo(), "businessBillNo", "","单据编号", true));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.ITEM.getTypeNo(), "itemCode", "","商品编码", true));
		}else{
			return null;
		}
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "balanceDiscount1", "","厂商折扣", false));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "zoneOriginalDiscount1", "","地区折扣", false));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), PayRelationship.class, fieldNames);
		List<ValidateResultVo>  listValidate = commonUtilController.doValidate(lstItem, lstValidate, PayRelationship.class);
		List<PayRelationship> errorList = payRelationshipManager.doImportDiscountBuyNew(listValidate);
		map.put("success", true);
		map.put("errorRows", errorList);
		return map;
	}
	
	/**
	 * 导入牌价
	 * @param file
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/do_import_tag_price")
	@ResponseBody
	public Map<String, Object>  doImportTagPrice(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
		String[] fieldNames= new String[]{"id","settlementNumber","businessBillNo","itemCode","tagPrice"};
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		lstValidate.add(new ValidateVo(ValidateTypeEnums.ITEM.getTypeNo(), "itemCode", "","商品编码", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "tagPrice", "","牌价", true));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), PayRelationship.class, fieldNames);
		List<ValidateResultVo>  listValidate = commonUtilController.doValidate(lstItem, lstValidate, PayRelationship.class);
		List<PayRelationship> errorList = payRelationshipManager.doImportTagPriceNew(listValidate);
		map.put("success", true);
		map.put("errorRows", errorList);
		return map;
	}
	
}