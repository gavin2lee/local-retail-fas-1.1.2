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

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.InitialAmount;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.InitialAmountManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-18 16:38:06
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
@RequestMapping("/initial_amount")
public class InitialAmountController extends BaseCrudController<InitialAmount> {
    @Resource
    private InitialAmountManager initialAmountManager;
    
    @Resource
    private CommonUtilController commonUtilController;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("initial_amount/",initialAmountManager);
    }
    
    @RequestMapping("/list_payable")
    public String listPayable(){
    	return "/initial_amount/list_payable";
    }
    
    @RequestMapping("/list_receivable")
    public String listReceivable(){
    	return "/initial_amount/list_receivable";
    }
    
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public  Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = initialAmountManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<InitialAmount> list = new ArrayList<InitialAmount>();
		List<InitialAmount> footerList = new ArrayList<InitialAmount>();
		if(total > 0){
			list = initialAmountManager.findByPage(page, sortColumn, sortOrder, params);
			footerList = initialAmountManager.findFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		obj.put("footer", footerList);
		return obj;
	}
	
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public 	Map<String, Object> doImport(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
		int iBalanceType = Integer.parseInt(req.getParameter("balanceType"));
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "companyNo", "companyName","公司编码", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.SUPPLIER.getTypeNo(), "supplierNo", "supplierName","供应商编码",true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.ITEM.getTypeNo(), "itemCode", "itemName","商品编码", false));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "initialDate", "","初始日期", true));
		String[] fieldNames= new String[]{"companyNo","supplierNo","itemCode","initialDate","cost","qty","amount","remark"};
		if(BalanceTypeEnums.HQ_WHOLESALE.getTypeNo() == iBalanceType){
			lstValidate.clear();
			lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "companyNo", "companyName", "公司编码", true));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "customerNo", "customerName","客户编码", true));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.ITEM.getTypeNo(), "itemCode","itemName", "商品编码",false));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "initialDate", "","初始日期", true));
			fieldNames = new String[]{"companyNo","customerNo","itemCode","initialDate","cost","qty","amount","remark"};
		}
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), InitialAmount.class, fieldNames);
		List<ValidateResultVo>  listValidate = commonUtilController.doValidate(lstItem, lstValidate, InitialAmount.class);
		for (Object object : listValidate) {
			ValidateResultVo vo = (ValidateResultVo)object;
			if(vo.getPass() == YesNoEnum.YES.getValue()){
				InitialAmount initialAmount = (InitialAmount)vo.getValidateObj();
				initialAmount.setBalanceType(iBalanceType);
				this.add(initialAmount);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("data", listValidate);
		return map;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(pageNumber, pageSize, pageSize);
		page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE); // 导出全部
		List<InitialAmount> dataList = initialAmountManager.findByPage(page, "", "", params);
		List<LookupVo> lstCurrency = commonUtilController.getCurrency();
		for (InitialAmount obj : dataList) {
			for (LookupVo vo : lstCurrency) {
				if(vo.getCode().equals(obj.getCurrency())){
					obj.setCurrency(vo.getName());
					break;
				}
			}
		}
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
}